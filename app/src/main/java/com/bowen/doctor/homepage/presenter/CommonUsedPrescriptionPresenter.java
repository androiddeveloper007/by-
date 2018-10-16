package com.bowen.doctor.homepage.presenter;

import android.content.Context;

import com.bowen.commonlib.base.BasePresenter;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.doctor.common.bean.network.CommonUsedPrescriptionRecord;
import com.bowen.doctor.homepage.contract.CommonUsedPrescriptionContract;
import com.bowen.doctor.homepage.model.CommonUsedPrescriptionModel;

/**
 * Created by zzp on 2017/5/21.
 * 数据提供类
 */
public class CommonUsedPrescriptionPresenter extends BasePresenter {

    private CommonUsedPrescriptionModel commonUsedPrescriptionModel;
    private CommonUsedPrescriptionContract.View mView;

    public CommonUsedPrescriptionPresenter(Context context, CommonUsedPrescriptionContract.View view) {
        super(context);
        commonUsedPrescriptionModel = new CommonUsedPrescriptionModel(context);
        mContext = context;
        mView = view;
    }

    public void loadData(int index, int pageSize) {
        commonUsedPrescriptionModel.loadData(index, pageSize, new HttpTaskCallBack<CommonUsedPrescriptionRecord>() {
            @Override
            public void onSuccess(HttpResult<CommonUsedPrescriptionRecord> result) {
                mView.onLoadSuccess(result.getData());
            }

            @Override
            public void onFail(HttpResult<CommonUsedPrescriptionRecord> result) {
                showToast(result.getMsg());
                mView.onLoadFail(result.getData());
            }
        });
    }
}
