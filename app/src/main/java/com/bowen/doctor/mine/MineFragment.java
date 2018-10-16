package com.bowen.doctor.mine;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bowen.commonlib.utils.EmptyUtils;
import com.bowen.commonlib.utils.ImageLoaderUtil;
import com.bowen.commonlib.utils.RouterActivityUtil;
import com.bowen.commonlib.widget.CircleImageView;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseFragment;
import com.bowen.doctor.common.bean.network.BindBankCardBean;
import com.bowen.doctor.common.bean.network.DoctorInfo;
import com.bowen.doctor.common.event.UploadQualityCertificateSuccEvent;
import com.bowen.doctor.common.model.Constants;
import com.bowen.doctor.common.util.LoginStatusUtil;
import com.bowen.doctor.mine.activity.BalanceWithdrawActivity;
import com.bowen.doctor.mine.activity.BindBankCardActivity;
import com.bowen.doctor.mine.activity.FinishInformationActivity;
import com.bowen.doctor.mine.activity.MyAccountActivity;
import com.bowen.doctor.mine.activity.MyBusinessCardActivity;
import com.bowen.doctor.mine.activity.MyEnterHospitalActivity;
import com.bowen.doctor.mine.activity.MyFansActivity;
import com.bowen.doctor.mine.activity.MyHospitalActivity;
import com.bowen.doctor.mine.activity.MyOrderActivity;
import com.bowen.doctor.mine.activity.QualityCertificateActivity;
import com.bowen.doctor.mine.activity.SafeSettingActivity;
import com.bowen.doctor.mine.contract.MineContract;
import com.bowen.doctor.mine.presenter.MinePresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Describe:我的
 * Created by zhuzhipeng on 2018/7/19.
 */
public class MineFragment extends BaseFragment implements MineContract.View {
    Unbinder unbinder;
    @BindView(R.id.mineDoctorIcon)
    CircleImageView mineDoctorIcon;
    @BindView(R.id.nameTv)
    TextView nameTv;
    @BindView(R.id.authenticationStateTv)
    TextView authenticationStateTv;
    @BindView(R.id.mineDepartmentTv)
    TextView mineDepartmentTv;
    @BindView(R.id.mineLevelTv)
    TextView mineLevelTv;
    @BindView(R.id.mineHospitalTv)
    TextView mineHospitalTv;
    @BindView(R.id.mineUnAuthenticTipTv)
    TextView mineUnAuthenticTipTv;
    private MinePresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = mInflater.inflate(R.layout.fragment_mine, null);
        unbinder = ButterKnife.bind(this, mView);
        mPresenter = new MinePresenter(mActivity, this);
        if (LoginStatusUtil.getInstance().isLogin()) {
            mPresenter.getDoctorInfo();
        }
    }

    private void updateUI(DoctorInfo info) {
        if (EmptyUtils.isNotEmpty(info.getName())) nameTv.setText(info.getName());
        if (EmptyUtils.isNotEmpty(info.getHospitalDepartments()))
            mineDepartmentTv.setText(info.getHospitalDepartments());
        if (EmptyUtils.isNotEmpty(info.getPositionStr())) mineLevelTv.setText(info.getPositionStr());
        if (EmptyUtils.isNotEmpty(info.getHospital())) mineHospitalTv.setText(info.getHospital());
        if (EmptyUtils.isNotEmpty(info.getFileLink())) ImageLoaderUtil.getInstance().show(mActivity,
                info.getFileLink(), mineDoctorIcon, R.drawable.doctor_default);
        String identifyState = "";
        int identifyStateIcon;//3：未认证 4：待审核 5：已认证
        if (info.getIdentify() == 3) {
            identifyState = "未认证";
            mineUnAuthenticTipTv.setVisibility(View.VISIBLE);
        } else if (info.getIdentify() == 4) {
            identifyState = "待审核";
            identifyStateIcon = R.drawable.unidentify;
            mineUnAuthenticTipTv.setVisibility(View.GONE);
            ImageLoaderUtil.getInstance().setTextViewDrawableLeft(mActivity, authenticationStateTv, identifyStateIcon);
        } else if (info.getIdentify() == 5) {
            identifyState = "已认证";
            identifyStateIcon = R.drawable.hasidentify;
            mineUnAuthenticTipTv.setVisibility(View.GONE);
            ImageLoaderUtil.getInstance().setTextViewDrawableLeft(mActivity, authenticationStateTv, identifyStateIcon);
        }
        if (EmptyUtils.isNotEmpty(identifyState)) authenticationStateTv.setText(identifyState);
    }

    @OnClick({R.id.myHospitalLayout, R.id.mineUnAuthenticTipTv, R.id.myBusinessCardLayout,
            R.id.myAccount, R.id.myOrder, R.id.balanceWithdraw, R.id.authenticationStateTv,
            R.id.mineEnterHospital, R.id.myFans, R.id.setLayout, R.id.mineEditLayout})
    public void onViewClicked(View view) {
        if (mPresenter.hasNotLogin()) return;
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.mineUnAuthenticTipTv:
                RouterActivityUtil.startActivity(mActivity, QualityCertificateActivity.class);
                break;
            case R.id.myAccount:
                if (mPresenter.hasQualityCertificate()) return;
                RouterActivityUtil.startActivity(mActivity, MyAccountActivity.class);
                break;
            case R.id.myOrder:
                if (mPresenter.hasQualityCertificate()) return;
                RouterActivityUtil.startActivity(mActivity, MyOrderActivity.class);
                break;
            case R.id.myFans:
                if (mPresenter.hasQualityCertificate()) return;
                RouterActivityUtil.startActivity(mActivity, MyFansActivity.class);
                break;
            case R.id.balanceWithdraw:
                if (mPresenter.hasQualityCertificate()) return;
                mPresenter.checkBindCard();
                break;
            case R.id.authenticationStateTv:
                RouterActivityUtil.startActivity(mActivity, QualityCertificateActivity.class);
                break;
            case R.id.myHospitalLayout:
                if (mPresenter.hasQualityCertificate()) return;
                RouterActivityUtil.startActivity(mActivity, MyHospitalActivity.class);
                break;
            case R.id.mineEnterHospital:
                if (mPresenter.hasQualityCertificate()) return;
                RouterActivityUtil.startActivity(mActivity, MyEnterHospitalActivity.class);
                break;
            case R.id.myBusinessCardLayout:
                if (mPresenter.hasQualityCertificate()) return;
                RouterActivityUtil.startActivity(mActivity, MyBusinessCardActivity.class);
                break;
            case R.id.setLayout:
                RouterActivityUtil.startActivity(mActivity, SafeSettingActivity.class);
                break;
            case R.id.mineEditLayout:
                bundle.putString(RouterActivityUtil.FROM_TAG, "isFromSet");
                RouterActivityUtil.startActivity(mActivity, FinishInformationActivity.class, bundle);
                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (LoginStatusUtil.getInstance().isLogin()) {
                mPresenter.getDoctorInfo();
            }
        }
    }

    @Override
    public void onLoadSuccess(DoctorInfo info) {
        if (info != null) {
            updateUI(info);
            DoctorInfo.setDoctorInstanceInfo(info);//设置单例中的数据
        }
    }

    @Override
    public void onLoadFail() {

    }

    @Override
    public void onLoadBindCardSuccess(BindBankCardBean bean) {
        //1：未绑卡 2：已绑卡
        String bindCard = bean.getBankStatus();
        if (TextUtils.equals(bindCard, Constants.HAS_NOT_BIND_CARD)) {
            RouterActivityUtil.startActivity(mActivity, BindBankCardActivity.class);
        } else if (TextUtils.equals(bindCard, Constants.HAS_BIND_CARD)) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(RouterActivityUtil.FROM_TAG, bean);
            RouterActivityUtil.startActivity(mActivity, BalanceWithdrawActivity.class, bundle);
        }
    }

    /**
     * 登录成功后，刷新个人中心数据
     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEvent(LoginEvent event) {
//        mPresenter.getDoctorInfo();
//    }

    /**
     * 完善资料、上传资质认证信息成功后，刷新个人中心数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UploadQualityCertificateSuccEvent event) {
        mPresenter.getDoctorInfo();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}