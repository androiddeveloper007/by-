package com.bowen.doctor.homepage.presenter;

import android.content.Context;

import com.bowen.commonlib.base.BasePresenter;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.doctor.homepage.contract.CommonPrescriptionDetailContract;
import com.bowen.doctor.homepage.model.CommonUsedPrescriptionDetailModel;

/**
 * 方剂详情页，删除方剂
 * Created by zzp on 2017/5/21.
 */

public class CommonUsedPrescriptionDetailPresenter extends BasePresenter {
    private CommonUsedPrescriptionDetailModel mModel;
    private CommonPrescriptionDetailContract.View mView;

    public CommonUsedPrescriptionDetailPresenter(Context context, CommonPrescriptionDetailContract.View view) {
        super(context);
        mModel = new CommonUsedPrescriptionDetailModel(context);
        mContext = context;
        mView = view;
    }

    public void deletePrescription(String id) {
        if(id.length()==0) {showToast("获取数据失败，请重新登录再试。"); return;}
        mModel.deletePrescription(id, new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> result) {
                showToast(result.getMsg());
                mView.onDeleteSuccess();
            }

            @Override
            public void onFail(HttpResult<Object> result) {
                showToast(result.getMsg());
                mView.onDeleteFail();
            }
        });
    }
}
