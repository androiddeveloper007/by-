package com.bowen.doctor.homepage.model;

import android.content.Context;

import com.bowen.commonlib.base.BaseModel;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.GsonUtil;
import com.bowen.doctor.common.bean.network.GraphicConsultSetBean;
import com.bowen.doctor.common.model.UserInfo;
import com.bowen.doctor.common.http.HttpContants;
import com.bowen.doctor.common.http.NetworkRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Describe:图文资讯服务设置
 * Created by zzp on 2018/7/10.
 */

public class GraphicConsultSetModel extends BaseModel {

    public GraphicConsultSetModel(Context mContext) {
        super(mContext);
    }

    public void getAppointServe(final HttpTaskCallBack<GraphicConsultSetBean> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("doctorId", UserInfo.getInstance().getUserId());
        networkRequest.requestSRV(HttpContants.CMD_GET_ASK_SERVE_BY_ID, "", new HttpTaskCallBack<GraphicConsultSetBean>() {
            @Override
            public void onSuccess(HttpResult<GraphicConsultSetBean> httpResult) {
                HttpResult<GraphicConsultSetBean> result = new HttpResult<>();
                result.copy(httpResult);
                try {
                    JSONObject jsonObject = new JSONObject(GsonUtil.toJson(httpResult.getData()));
                    GraphicConsultSetBean record = GsonUtil.getGson().fromJson(jsonObject.getString("emrDoctor"), GraphicConsultSetBean.class);
                    if (record != null) {
                        result.setData(record);
                    }
                    if (mListener != null) {
                        mListener.onSuccess(result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(HttpResult<GraphicConsultSetBean> httpResult) {
                if (mListener != null) {
                    mListener.onFail(httpResult);
                }
            }
        });
    }

    public void saveAskServe(int askSwitch, double consultFee, final HttpTaskCallBack<Object> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("doctorId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("askSwitch", askSwitch);
        if(askSwitch==1) networkRequest.setParam("consultFee", consultFee);
        networkRequest.requestSRV(HttpContants.CMD_SAVE_ASK_SERVE, "", new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                HttpResult<Object> result = new HttpResult<>();
                if (mListener != null) {
                    mListener.onSuccess(result);
                }
            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                if (mListener != null) {
                    mListener.onFail(httpResult);
                }
            }
        });
    }

}