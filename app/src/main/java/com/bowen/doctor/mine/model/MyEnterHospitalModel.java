package com.bowen.doctor.mine.model;

import android.content.Context;

import com.bowen.commonlib.base.BaseModel;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.GsonUtil;
import com.bowen.doctor.common.bean.network.MyEnterHospitalRecord;
import com.bowen.doctor.common.model.UserInfo;
import com.bowen.doctor.common.http.HttpContants;
import com.bowen.doctor.common.http.NetworkRequest;

/**
 * Describe:我入驻的医馆
 * Created by zzp on 2018/5/15.
 */

public class MyEnterHospitalModel extends BaseModel {

    public MyEnterHospitalModel(Context mContext) {
        super(mContext);
    }

    public void cancelApplyToEnter(String doctorHospitalDeptId, final HttpTaskCallBack<Object> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("doctorHospitalDeptId", doctorHospitalDeptId);
        networkRequest.requestSRV(HttpContants.CMD_CANCEL_APPLY_TO_ENTER, "", new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                HttpResult<Object> result = new HttpResult<>();
                if (mListener != null) {
                    mListener.onSuccess(result);
                }
            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                HttpResult<Object> result = new HttpResult<>();
                if (mListener != null) {
                    mListener.onFail(result);
                }
            }
        });
    }

    /**
     * 医生申请入驻医馆记录(已经申请入驻的医馆)
     * @param index
     * @param pageSize
     * @param mListener
     */
    public void loadData(int index, int pageSize, final HttpTaskCallBack<MyEnterHospitalRecord> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("pageNo", index);
        networkRequest.setParam("pageSize", pageSize);
        networkRequest.setParam("authStatus", 2);//已经入驻的医馆的列表传入2，申请记录不传
        networkRequest.requestSRV(HttpContants.CMD_DOCTOR_APPLY_LIST, "", new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                HttpResult<MyEnterHospitalRecord> result = new HttpResult<>();
                result.copy(httpResult);
                MyEnterHospitalRecord record = GsonUtil.fromJson(GsonUtil.toJson(httpResult.getData()), MyEnterHospitalRecord.class);
                if (record != null) {
                    result.setData(record);
                }
                if (mListener != null) {
                    mListener.onSuccess(result);
                }
            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                HttpResult<MyEnterHospitalRecord> result = new HttpResult<>();
                result.copy(httpResult);
                if (mListener != null) {
                    mListener.onFail(result);
                }
            }
        });
    }

    public void loadRecord(int index, int pageSize, final HttpTaskCallBack<MyEnterHospitalRecord> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("pageNo", index);
        networkRequest.setParam("pageSize", pageSize);
        networkRequest.requestSRV(HttpContants.CMD_DOCTOR_APPLY_LIST, "", new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                HttpResult<MyEnterHospitalRecord> result = new HttpResult<>();
                result.copy(httpResult);
                MyEnterHospitalRecord record = GsonUtil.fromJson(GsonUtil.toJson(httpResult.getData()), MyEnterHospitalRecord.class);
                if (record != null) {
                    result.setData(record);
                }
                if (mListener != null) {
                    mListener.onSuccess(result);
                }
            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                HttpResult<MyEnterHospitalRecord> result = new HttpResult<>();
                result.copy(httpResult);
                if (mListener != null) {
                    mListener.onFail(result);
                }
            }
        });
    }
}
