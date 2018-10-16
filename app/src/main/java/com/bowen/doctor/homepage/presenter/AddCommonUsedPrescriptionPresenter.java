package com.bowen.doctor.homepage.presenter;

import android.content.Context;
import android.widget.EditText;

import com.bowen.commonlib.base.BasePresenter;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.doctor.common.bean.network.OutpatientSetRecord;
import com.bowen.doctor.homepage.contract.AddCommonPrescriptionContract;
import com.bowen.doctor.homepage.model.AddCommonUsedPrescriptionModel;

/**
 * 提交方剂
 * Created by zzp on 2017/5/21.
 */

public class AddCommonUsedPrescriptionPresenter extends BasePresenter {
    private AddCommonUsedPrescriptionModel mModel;
    private AddCommonPrescriptionContract.View mView;

    public AddCommonUsedPrescriptionPresenter(Context context, AddCommonPrescriptionContract.View view) {
        super(context);
        mModel = new AddCommonUsedPrescriptionModel(context);
        mContext = context;
        mView = view;
    }

    public void savePrescription(EditText name, String prescriptionStr) {
        if(name.length()==0) {showToast("请填写方剂名称"); return;}
        if(prescriptionStr.length()==0) {showToast("请填写方剂成分和剂量"); return;}
        mModel.savePrescription(name.getText().toString(), prescriptionStr, new HttpTaskCallBack<OutpatientSetRecord>() {
            @Override
            public void onSuccess(HttpResult<OutpatientSetRecord> result) {
                mView.onUpLoadSuccess();
            }

            @Override
            public void onFail(HttpResult<OutpatientSetRecord> result) {
//                showToast(result.getMsg());
                mView.onUpLoadFail();
            }
        });
    }

    public void editPrescription(String id, EditText name, String prescriptionStr) {
        if(name.length()==0) {showToast("请填写方剂名称"); return;}
        mModel.editPrescription(id, name.getText().toString(), prescriptionStr, new HttpTaskCallBack<OutpatientSetRecord>() {
            @Override
            public void onSuccess(HttpResult<OutpatientSetRecord> result) {
                mView.onUpLoadSuccess();
            }

            @Override
            public void onFail(HttpResult<OutpatientSetRecord> result) {
//                showToast(result.getMsg());
                mView.onUpLoadFail();
            }
        });
    }
}
