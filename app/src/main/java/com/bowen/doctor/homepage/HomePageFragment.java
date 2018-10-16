package com.bowen.doctor.homepage;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.event.LocationEvent;
import com.bowen.commonlib.utils.EmptyUtils;
import com.bowen.commonlib.utils.ImageLoaderUtil;
import com.bowen.commonlib.utils.RouterActivityUtil;
import com.bowen.commonlib.widget.CircleImageView;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseFragment;
import com.bowen.doctor.common.bean.network.ConsultItem;
import com.bowen.doctor.common.bean.network.DoctorInfo;
import com.bowen.doctor.common.bean.network.Login;
import com.bowen.doctor.common.event.LoginEvent;
import com.bowen.doctor.common.event.UploadQualityCertificateSuccEvent;
import com.bowen.doctor.common.model.AppConfigInfo;
import com.bowen.doctor.common.util.LocationUtil;
import com.bowen.doctor.common.util.LoginStatusUtil;
import com.bowen.doctor.consult.activity.ChatActivity;
import com.bowen.doctor.homepage.activity.OutpatientAppointmentActivity;
import com.bowen.doctor.homepage.activity.PrescriptionActivity;
import com.bowen.doctor.homepage.activity.ServiceSetActivity;
import com.bowen.doctor.homepage.adapter.WaitReplyConsultAdapter;
import com.bowen.doctor.homepage.presenter.HomePagePresenter;
import com.bowen.doctor.mine.activity.FinishInformationActivity;
import com.bowen.doctor.mine.activity.MyHospitalActivity;
import com.bowen.doctor.mine.activity.QualityCertificateActivity;
import com.bowen.doctor.mine.contract.HomePageContract;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Describe:首页
 * Created by zhuzhipeng on 2018/7/11
 */
public class HomePageFragment extends BaseFragment implements HomePageContract.View {
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.unreadConsultMoreTv)
    TextView unreadConsultMoreTv;
    @BindView(R.id.mineDoctorIcon)
    CircleImageView mineDoctorIcon;
    @BindView(R.id.nameTv)
    TextView nameTv;
    @BindView(R.id.authenticationStateTv)
    TextView authenticationStateTv;
    @BindView(R.id.homeDepartmentTv)
    TextView homeDepartmentTv;
    @BindView(R.id.homeLevelTv)
    TextView homeLevelTv;
    @BindView(R.id.homeHospitalTv)
    TextView homeHospitalTv;
    @BindView(R.id.homeUnAuthenticTipTv)
    TextView homeUnAuthenticTipTv;
    private HomePagePresenter mPresenter;
    private WaitReplyConsultAdapter mAdapter;
    private View emptyView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = mInflater.inflate(R.layout.fragment_homepage, null);
        ButterKnife.bind(this, mView);
        mPresenter = new HomePagePresenter(mActivity, this);
        LocationUtil.getInstance().startLocation();
        initView();
        if (LoginStatusUtil.getInstance().isLogin()) {
            mPresenter.getDoctorInfo();
        }
    }

    private void initView() {
        mAdapter = new WaitReplyConsultAdapter(mActivity);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
        if (emptyView == null) {
            emptyView = getLayoutInflater().inflate(R.layout.empty_view_unread_consult, (ViewGroup) mActivity.findViewById(android.R.id.content), false);
            mAdapter.setEmptyView(true, emptyView);
        }

        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                Bundle bundle = new Bundle();
                ConsultItem consultItem = new ConsultItem();
                consultItem.setHeadImgUrl(((ConsultItem) mAdapter.getData().get(pos)).getHeadImgUrl());
                consultItem.setUserId(((ConsultItem) mAdapter.getData().get(pos)).getUserId());
                consultItem.setUserNickname(((ConsultItem) mAdapter.getData().get(pos)).getUserNickname());
                consultItem.setOrderId(((ConsultItem) mAdapter.getData().get(pos)).getOrderId());
                bundle.putSerializable(RouterActivityUtil.FROM_TAG, consultItem);
                RouterActivityUtil.startActivity(mActivity, ChatActivity.class, bundle);
            }
        });
    }

    public void getData() {
        mPresenter.getConsult();
    }

    private void updateUI(DoctorInfo info) {
        if (EmptyUtils.isNotEmpty(info.getName())) nameTv.setText(info.getName());
        if (EmptyUtils.isNotEmpty(info.getHospitalDepartments()))
            homeDepartmentTv.setText(info.getHospitalDepartments());
        if (EmptyUtils.isNotEmpty(info.getPositionStr())) homeLevelTv.setText(info.getPositionStr());
        if (EmptyUtils.isNotEmpty(info.getHospital())) homeHospitalTv.setText(info.getHospital());
        if (EmptyUtils.isNotEmpty(info.getFileLink()))
            ImageLoaderUtil.getInstance().show(mActivity, info.getFileLink(), mineDoctorIcon, R.drawable.doctor_default);
        String identifyState = "";
        int identifyStateIcon;
        if (info.getIdentify() == 3) {
            identifyState = "未认证";
            homeUnAuthenticTipTv.setVisibility(View.VISIBLE);
        } else if (info.getIdentify() == 4) {
            identifyState = "待审核";
            identifyStateIcon = R.drawable.unidentify;
            homeUnAuthenticTipTv.setVisibility(View.GONE);
            ImageLoaderUtil.getInstance().setTextViewDrawableLeft(mActivity, authenticationStateTv, identifyStateIcon);
        } else if (info.getIdentify() == 5) {
            identifyState = "已认证";
            identifyStateIcon = R.drawable.hasidentify;
            homeUnAuthenticTipTv.setVisibility(View.GONE);
            ImageLoaderUtil.getInstance().setTextViewDrawableLeft(mActivity, authenticationStateTv, identifyStateIcon);
        }
        if (EmptyUtils.isNotEmpty(identifyState))
            authenticationStateTv.setText(identifyState);
    }

    @OnClick({R.id.authenticationStateTv, R.id.serviceSetLayout, R.id.outpatientAppointmentLayout
            , R.id.chinesePrescriptionLayout, R.id.homeHospitalLayout, R.id.homeUnAuthenticTipTv})
    public void onViewClicked(View view) {
        if (mPresenter.hasNotLogin()) return;
        switch (view.getId()) {
            case R.id.authenticationStateTv:
                RouterActivityUtil.startActivity(mActivity, QualityCertificateActivity.class);
                break;
            case R.id.serviceSetLayout:
                if (mPresenter.hasQualityCertificate()) return;
                RouterActivityUtil.startActivity(mActivity, ServiceSetActivity.class);
                break;
            case R.id.homeUnAuthenticTipTv:
                RouterActivityUtil.startActivity(mActivity, QualityCertificateActivity.class);
                break;
            case R.id.outpatientAppointmentLayout:
                if (mPresenter.hasQualityCertificate()) return;
                RouterActivityUtil.startActivity(mActivity, OutpatientAppointmentActivity.class);
                break;
            case R.id.chinesePrescriptionLayout:
                if (mPresenter.hasQualityCertificate()) return;
                RouterActivityUtil.startActivity(mActivity, PrescriptionActivity.class);
                break;
            case R.id.homeHospitalLayout:
                if (mPresenter.hasQualityCertificate()) return;
                RouterActivityUtil.startActivity(mActivity, MyHospitalActivity.class);
                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            if (LoginStatusUtil.getInstance().isLogin()) {
                mPresenter.getDoctorInfo();
                getData();
            }
        }
    }

    @Override
    public void onLoadSuccess(DoctorInfo info) {
        if (info != null) {
            updateUI(info);
            DoctorInfo.setDoctorInstanceInfo(info);//设置单例中的数据
            getData();
            //判断没有设置过图文咨询和门诊预约服务，已认证，则弹出设置的界面
            if(info.getIdentify()==5 &&!info.isSet()){
                mPresenter.showSetDialog();
            }
        }
    }

    @Override
    public void loadConsultSuccess(List<ConsultItem> consultList) {
        if (consultList.size() > 3) {
            List<ConsultItem> newList = consultList.subList(0, 3);
            mAdapter.setNewData(newList);
            unreadConsultMoreTv.setVisibility(View.VISIBLE);
        } else {
            mAdapter.setNewData(consultList);
        }
    }

    @Override
    public void onLoadFail() {

    }

    @Override
    public void loadConsultFail() {
    }

    @Override
    public void quickLoginSuccess(Login loginBean) {
        //快速登录成功后跳到，完善资料页
        if (!loginBean.getIsData()) {
            RouterActivityUtil.startActivity(mActivity, FinishInformationActivity.class, true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LocationEvent event) {
        AppConfigInfo.getInstance().setLocationEvent(event);
    }

    /**
     * 完善资料、上传资质认证信息成功后，刷新个人中心数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UploadQualityCertificateSuccEvent event) {
        mPresenter.getDoctorInfo();
    }

    //快速登录成功，获取医生信息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LoginEvent event) {
        mPresenter.getDoctorInfo();
    }
}
