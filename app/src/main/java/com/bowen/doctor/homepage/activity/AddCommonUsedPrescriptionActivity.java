package com.bowen.doctor.homepage.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.bowen.commonlib.dialog.AffirmDialog;
import com.bowen.commonlib.utils.ToastUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseActivity;
import com.bowen.doctor.common.bean.network.AddPrescriptionDetail;
import com.bowen.doctor.common.event.AddCommonUsePrescSuccEvent;
import com.bowen.doctor.homepage.adapter.AddPrescriptionDetailRvAdapter;
import com.bowen.doctor.homepage.contract.AddCommonPrescriptionContract;
import com.bowen.doctor.homepage.presenter.AddCommonUsedPrescriptionPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加常用方剂
 * Created by zzp on 2018/7/27.
 */

public class AddCommonUsedPrescriptionActivity extends BaseActivity implements AddCommonPrescriptionContract.View {
    @BindView(R.id.addPrescriptionDetailRv)
    RecyclerView addPrescriptionDetailRv;
    @BindView(R.id.prescriptionNameEdit)
    EditText prescriptionNameEdit;
    private AddPrescriptionDetailRvAdapter mAdapter;
    private List<AddPrescriptionDetail> mList;
    private AddCommonUsedPrescriptionPresenter mPrescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_add_common_prescription);
        ButterKnife.bind(this);
        initView();
        mPrescription = new AddCommonUsedPrescriptionPresenter(this, this);
    }

    private void initView() {
        setTitle("新增常用方剂");
        addPrescriptionDetailRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AddPrescriptionDetailRvAdapter(this);
        mList = new ArrayList<>();
        mList.add(new AddPrescriptionDetail("", ""));
        addPrescriptionDetailRv.setAdapter(mAdapter);
        mAdapter.setNewData(mList);
        mAdapter.setDeleteClickListener(new AddPrescriptionDetailRvAdapter.DeleteClickListener() {
            @Override
            public void delete(int position) {
                if (mAdapter.getData().size() == 1) {
                    ToastUtil.getInstance().showToastDialog("至少保留一项");
                    return;
                }
                View currentFocus = addPrescriptionDetailRv.getFocusedChild();
                if (currentFocus != null) {
                    currentFocus.clearFocus();
                }
                mAdapter.getData().remove(position);
                mAdapter.notifyItemRemoved(position);
                if (position < mAdapter.getData().size()) {
                    mAdapter.notifyItemRangeChanged(position, mAdapter.getData().size() - position);
                }
            }
        });
    }

    @OnClick({R.id.addPrescriptionDetail, R.id.submitPrescription})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addPrescriptionDetail:
                mAdapter.getData().add(mAdapter.getData().size(), new AddPrescriptionDetail("", ""));
                mAdapter.notifyItemInserted(mAdapter.getData().size() - 1);
                break;
            case R.id.submitPrescription:
                mPrescription.savePrescription(prescriptionNameEdit, mAdapter.getPrescriptionStr());
                break;
        }
    }

    @Override
    public void onUpLoadSuccess() {
        EventBus.getDefault().post(new AddCommonUsePrescSuccEvent());
        finish();
    }

    @Override
    public void onUpLoadFail() {

    }

    private boolean checkParamsChanged() {
        if (prescriptionNameEdit.getText().length() > 0) {
            return true;
        }
        if (mAdapter.getPrescriptionStr().length() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (checkParamsChanged()) {
            AffirmDialog affirmDialog = new AffirmDialog(this, "提醒",
                    "方剂数据未保存，是否保存?", "不了", "保存");
            affirmDialog.setAffirmDialogListenner(new AffirmDialog.AffirmDialogListenner() {
                @Override
                public void onCancle() {
                    finish();
                }

                @Override
                public void onOK() {
                    mPrescription.savePrescription(prescriptionNameEdit, mAdapter.getPrescriptionStr());
                }
            });
            affirmDialog.show();
        } else {
            super.onBackPressed();
        }
    }
}
