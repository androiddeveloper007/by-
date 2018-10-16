package com.bowen.doctor.homepage.model;

import android.content.Context;

import com.bowen.commonlib.base.BaseModel;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.GsonUtil;
import com.bowen.doctor.common.bean.network.OutpatientSetRecord;
import com.bowen.doctor.common.http.HttpContants;
import com.bowen.doctor.common.http.NetworkRequest;
import com.bowen.doctor.common.model.UserInfo;

/**
 * Describe:获取该医生门诊预约服务设置
 * Created by zzp on 2018/7/10.
 */

public class AddCommonUsedPrescriptionModel extends BaseModel {

    public AddCommonUsedPrescriptionModel(Context mContext) {
        super(mContext);
    }

    public void savePrescription(String name, String prescriptionStr, final HttpTaskCallBack<OutpatientSetRecord> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("doctorId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("name", name);
        networkRequest.setParam("remark", prescriptionStr);
        networkRequest.requestSRV(HttpContants.CMD_SAVE_PRESCRIPTION, "", new HttpTaskCallBack<OutpatientSetRecord>() {
            @Override
            public void onSuccess(HttpResult<OutpatientSetRecord> httpResult) {
                HttpResult<OutpatientSetRecord> result = new HttpResult<>();
                result.copy(httpResult);
                OutpatientSetRecord record = GsonUtil.fromJson(GsonUtil.toJson(httpResult.getData()), OutpatientSetRecord.class);
                if (record != null) {
                    result.setData(record);
                }
                if (mListener != null) {
                    mListener.onSuccess(result);
                }
            }

            @Override
            public void onFail(HttpResult<OutpatientSetRecord> httpResult) {
                if (mListener != null) {
                    mListener.onFail(httpResult);
                }
            }
        });
    }

    public void editPrescription(String id, String name, String prescriptionStr, final HttpTaskCallBack<OutpatientSetRecord> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("doctorId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("prescriptionId", id);
        networkRequest.setParam("name", name);
        networkRequest.setParam("remark", prescriptionStr);
        networkRequest.requestSRV(HttpContants.CMD_UPDATE_PRESCRIPTION, "", new HttpTaskCallBack<OutpatientSetRecord>() {
            @Override
            public void onSuccess(HttpResult<OutpatientSetRecord> httpResult) {
                HttpResult<OutpatientSetRecord> result = new HttpResult<>();
                result.copy(httpResult);
                OutpatientSetRecord record = GsonUtil.fromJson(GsonUtil.toJson(httpResult.getData()), OutpatientSetRecord.class);
                if (record != null) {
                    result.setData(record);
                }
                if (mListener != null) {
                    mListener.onSuccess(result);
                }
            }

            @Override
            public void onFail(HttpResult<OutpatientSetRecord> httpResult) {
                if (mListener != null) {
                    mListener.onFail(httpResult);
                }
            }
        });
    }

}