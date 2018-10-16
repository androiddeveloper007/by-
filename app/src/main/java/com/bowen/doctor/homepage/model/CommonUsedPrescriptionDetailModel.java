package com.bowen.doctor.homepage.model;

import android.content.Context;

import com.bowen.commonlib.base.BaseModel;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.doctor.common.http.HttpContants;
import com.bowen.doctor.common.http.NetworkRequest;
import com.bowen.doctor.common.model.UserInfo;

/**
 * Describe:方剂详情页，删除方剂
 * Created by zzp on 2018/7/10.
 */
public class CommonUsedPrescriptionDetailModel extends BaseModel {

    public CommonUsedPrescriptionDetailModel(Context mContext) {
        super(mContext);
    }

    public void deletePrescription(String id, final HttpTaskCallBack<Object> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("prescriptionId", id);
        networkRequest.requestSRV(HttpContants.CMD_DELETE_PRESCRIPTION, "", new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                HttpResult<Object> result = new HttpResult<>();
                result.copy(httpResult);
                if (mListener != null) {
                    mListener.onSuccess(httpResult);
                }
            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                HttpResult<Object> result = new HttpResult<>();
                result.copy(httpResult);
                if (mListener != null) {
                    mListener.onFail(httpResult);
                }
            }
        });
    }
}