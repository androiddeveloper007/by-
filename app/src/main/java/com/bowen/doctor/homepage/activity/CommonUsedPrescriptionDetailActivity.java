package com.bowen.doctor.homepage.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bowen.commonlib.dialog.AffirmDialog;
import com.bowen.commonlib.utils.EmptyUtils;
import com.bowen.commonlib.utils.GsonUtil;
import com.bowen.commonlib.utils.RouterActivityUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseActivity;
import com.bowen.doctor.common.bean.network.AddPrescriptionDetail;
import com.bowen.doctor.common.bean.network.PrescriptionBean;
import com.bowen.doctor.common.event.DeleteCommonUsePrescSuccEvent;
import com.bowen.doctor.common.event.EditCommonUsePrescSuccEvent;
import com.bowen.doctor.homepage.contract.CommonPrescriptionDetailContract;
import com.bowen.doctor.homepage.presenter.CommonUsedPrescriptionDetailPresenter;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 常用方剂详情
 * Created by zzp on 2018/7/27.
 */

public class CommonUsedPrescriptionDetailActivity extends BaseActivity implements CommonPrescriptionDetailContract.View{
    @BindView(R.id.prescriptionNameTv)
    TextView prescriptionNameTv;
    @BindView(R.id.prescriptionDetailTv)
    TextView prescriptionDetailTv;
    private PrescriptionBean bean;
    private CommonUsedPrescriptionDetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_common_prescription_detail);
        ButterKnife.bind(this);
        Bundle bundle = RouterActivityUtil.getBundle(this);
        if (EmptyUtils.isNotEmpty(bundle)) {
            bean = (PrescriptionBean) bundle.getSerializable(RouterActivityUtil.FROM_TAG);
        }
        mPresenter = new CommonUsedPrescriptionDetailPresenter(this, this);
        initView();
    }

    private void initView() {
        setTitle("编辑常用方剂");
        prescriptionNameTv.setText(bean.getName());
        setRemark(bean.getRemark());
    }

    @OnClick( {R.id.deletePrescription,R.id.editPrescription} )
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.deletePrescription:
                showDeleteDia(view);
                break;
            case R.id.editPrescription:
                showEditActivity();
                break;
        }
    }

    private void showEditActivity() {
        if(bean!=null){
            Bundle bundle = new Bundle();
            bundle.putSerializable(RouterActivityUtil.FROM_TAG, bean);
            RouterActivityUtil.startActivity(CommonUsedPrescriptionDetailActivity.this, EditCommonUsedPrescriptionActivity.class,bundle);
        } else {
            RouterActivityUtil.startActivity(CommonUsedPrescriptionDetailActivity.this, EditCommonUsedPrescriptionActivity.class);
        }
    }

    private void showDeleteDia(View view) {
        AffirmDialog affirmDialog = new AffirmDialog(view.getContext(), "", "确认删除该方剂？", "取消", "确定");
        affirmDialog.setAffirmDialogListenner(new AffirmDialog.AffirmDialogListenner() {
            @Override
            public void onCancle() {}

            @Override
            public void onOK() {
                mPresenter.deletePrescription(bean.getPrescriptionId());
            }
        });
        affirmDialog.show();
    }

    @Override
    public void onDeleteSuccess() {
        EventBus.getDefault().post(new DeleteCommonUsePrescSuccEvent());
        finish();
    }

    @Override
    public void onDeleteFail() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EditCommonUsePrescSuccEvent event) {
        prescriptionNameTv.setText(event.mBean.getName());
        setRemark(event.mBean.getRemark());
    }

    private void setRemark(String remark) {
        Type type = new TypeToken<List<AddPrescriptionDetail>>() {}.getType();
        ArrayList<AddPrescriptionDetail> list = GsonUtil.getGson().fromJson(remark, type);
        Iterator<AddPrescriptionDetail> iterator = list.iterator();
        StringBuilder strBuilder = new StringBuilder();
        while(iterator.hasNext()){
            AddPrescriptionDetail bean = iterator.next();
            strBuilder.append(bean.getName()).append(bean.getGram()).append("克、");
        }
        strBuilder.setCharAt(strBuilder.length()-1, '。');
        prescriptionDetailTv.setText(strBuilder);
    }
}