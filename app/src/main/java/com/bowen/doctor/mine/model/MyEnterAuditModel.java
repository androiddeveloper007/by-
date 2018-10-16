package com.bowen.doctor.mine.model;

import android.content.Context;

import com.bowen.commonlib.base.BaseModel;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.GsonUtil;
import com.bowen.doctor.common.bean.network.MyEnterAuditRecord;
import com.bowen.doctor.common.model.UserInfo;
import com.bowen.doctor.common.http.HttpContants;
import com.bowen.doctor.common.http.NetworkRequest;

/**
 * Describe:
 * Created by zzp on 2018/5/15.
 */

public class MyEnterAuditModel extends BaseModel {

    public MyEnterAuditModel(Context mContext) {
        super(mContext);
    }

    public void loadData(int index, int pageSize, String hospitalId,int authStatus, boolean isLoad, final HttpTaskCallBack<MyEnterAuditRecord> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("hospitalId", hospitalId);
//        networkRequest.setParam("authStatus", authStatus);
        networkRequest.setParam("pageNo", index);
        networkRequest.setParam("pageSize", pageSize);
        networkRequest.requestSRV(HttpContants.CMD_SHOW_ENTER_AUDIT_LIST, isLoad? "":"数据加载中", new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                HttpResult<MyEnterAuditRecord> result = new HttpResult<>();
                result.copy(httpResult);
                MyEnterAuditRecord record = GsonUtil.fromJson(GsonUtil.toJson(httpResult.getData()), MyEnterAuditRecord.class);
                if (record != null) {
                    result.setData(record);
                }
                if (mListener != null) {
                    mListener.onSuccess(result);
                }
            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                HttpResult<MyEnterAuditRecord> result = new HttpResult<>();
                result.copy(httpResult);
                if (mListener != null) {
                    mListener.onFail(result);
                }
            }
        });
    }

    public void handleEnterApply(boolean isPass, String hospitalId, final HttpTaskCallBack<Object> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("handleResult", isPass ? "2":"3");//2为通过 3为驳回
        networkRequest.setParam("doctorHospitalDeptId", hospitalId);
        networkRequest.requestSRV(HttpContants.CMD_HANDLE_ENTER_APPLY, "", new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                HttpResult result = new HttpResult<>();
                result.copy(httpResult);
                if (mListener != null) {
                    mListener.onSuccess(result);
                }
            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                HttpResult<Object> result = new HttpResult<>();
                result.copy(httpResult);
                if (mListener != null) {
                    mListener.onFail(result);
                }
            }
        });
    }

}
