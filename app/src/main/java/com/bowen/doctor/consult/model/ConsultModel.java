package com.bowen.doctor.consult.model;


import android.content.Context;

import com.bowen.commonlib.base.BaseModel;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.GsonUtil;
import com.bowen.doctor.common.bean.network.ConsultInfo;
import com.bowen.doctor.common.model.UserInfo;
import com.bowen.doctor.common.http.HttpContants;
import com.bowen.doctor.common.http.NetworkRequest;

/**
 * Describe:
 * Created by AwenZeng on 2018/8/2.
 */
public class ConsultModel extends BaseModel {

    public ConsultModel(Context mContext) {
        super(mContext);
    }

    public void getConsultData(int index, int pageSize, final HttpTaskCallBack<ConsultInfo> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("pageNo", index);
        networkRequest.setParam("pageSize", pageSize);
        networkRequest.requestSRV(HttpContants.CMD_GET_DOCTOR_CONSULT_DATA, "", new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                HttpResult<ConsultInfo> result = new HttpResult<>();
                result.copy(httpResult);
                ConsultInfo record = GsonUtil.fromJson(GsonUtil.toJson(httpResult.getData()), ConsultInfo.class);
                if (record != null) {
                    result.setData(record);
                }
                if (mListener != null) {
                    mListener.onSuccess(result);
                }
            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                HttpResult<ConsultInfo> result = new HttpResult<>();
                if(mListener!=null){
                    mListener.onFail(result);
                }
            }
        });
    }

    /**
     * 删除聊天信息
     * @param sendUserId
     */
    public void removeConsultInfo(String sendUserId,final HttpTaskCallBack<String> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("sendUserId", sendUserId);
        networkRequest.requestSRV(HttpContants.CMD_REMOVE_CONSULT_INFO, new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                HttpResult<String> result = new HttpResult<>();
                result.copy(httpResult);
                if (mListener != null) {
                    mListener.onSuccess(result);
                }
            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                HttpResult<String> result = new HttpResult<>();
                result.copy(httpResult);
                if (mListener != null) {
                    mListener.onFail(result);
                }
            }
        });
    }
}
