package com.bowen.doctor.homepage.presenter;

import android.content.Context;

import com.bowen.commonlib.base.BasePresenter;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.doctor.common.bean.network.OnlineFolkPrescriptionRecord;
import com.bowen.doctor.homepage.contract.OnlineFolkPrescriptionContract;
import com.bowen.doctor.homepage.model.OnlineFolkPrescriptionModel;

/**
 * Created by zzp on 2017/5/21.
 * 在线偏方数据提供类
 */
public class OnlineFolkPrescriptionPresenter extends BasePresenter {

    private OnlineFolkPrescriptionModel onlineFolkPrescriptionModel;
    private OnlineFolkPrescriptionContract.View mView;

    public OnlineFolkPrescriptionPresenter(Context context, OnlineFolkPrescriptionContract.View view) {
        super(context);
        onlineFolkPrescriptionModel = new OnlineFolkPrescriptionModel(context);
        mContext = context;
        mView = view;
    }

    public void loadData(int index, int pageSize) {
        onlineFolkPrescriptionModel.loadData(index, pageSize, new HttpTaskCallBack<OnlineFolkPrescriptionRecord>() {
            @Override
            public void onSuccess(HttpResult<OnlineFolkPrescriptionRecord> result) {
                mView.onLoadSuccess(result.getData());
            }

            @Override
            public void onFail(HttpResult<OnlineFolkPrescriptionRecord> result) {
//                showToast(result.getMsg());
                mView.onLoadFail(result.getData());
            }
        });
    }
}
