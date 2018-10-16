package com.bowen.doctor.mine.model;

import android.content.Context;

import com.bowen.commonlib.base.BaseModel;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.GsonUtil;
import com.bowen.doctor.common.bean.network.BindBankCardBean;
import com.bowen.doctor.common.bean.network.ConsultItem;
import com.bowen.doctor.common.bean.network.DoctorInfo;
import com.bowen.doctor.common.http.HttpContants;
import com.bowen.doctor.common.http.NetworkRequest;
import com.bowen.doctor.common.model.UserInfo;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Describe:“我的”
 * Created by zzp on 2018/5/15.
 */

public class MineModel extends BaseModel {

    public MineModel(Context mContext) {
        super(mContext);
    }

    public void getDoctorInfo(final HttpTaskCallBack<DoctorInfo> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.requestSRV(HttpContants.CMD_GET_DOCTOR_INFO, "", new HttpTaskCallBack<DoctorInfo>() {
            @Override
            public void onSuccess(HttpResult<DoctorInfo> httpResult) {
                HttpResult<DoctorInfo> result = new HttpResult<>();
                result.copy(httpResult);
                try {
                    JSONObject jsonObj;
                    jsonObj = new JSONObject(GsonUtil.toJson(httpResult.getData()));
                    DoctorInfo record = GsonUtil.fromJson(jsonObj.getString("emrDoctor"), DoctorInfo.class);
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
            public void onFail(HttpResult<DoctorInfo> httpResult) {
                if (mListener != null) {
                    mListener.onFail(httpResult);
                }
            }
        });
    }

    //待处理资讯列表
    public void getWaitReplyConsult(final HttpTaskCallBack<List<ConsultItem>> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.requestSRV(HttpContants.CMD_WAIT_ANSWER_CONSULT, "", new HttpTaskCallBack<List<ConsultItem>>() {
            @Override
            public void onSuccess(HttpResult<List<ConsultItem>> httpResult) {
                HttpResult<List<ConsultItem>> result = new HttpResult<>();
                result.copy(httpResult);
                try {
                    JSONObject jsonObj;
                    jsonObj = new JSONObject(GsonUtil.toJson(httpResult.getData()));
                    Type typelist = new TypeToken<List<ConsultItem>>() {}.getType();
                    List<ConsultItem> record = GsonUtil.getGson().fromJson(jsonObj.getString("consultList"), typelist);
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
            public void onFail(HttpResult<List<ConsultItem>> httpResult) {
                HttpResult<List<ConsultItem>> result = new HttpResult<>();
                result.copy(httpResult);
                if (mListener != null) {
                    mListener.onFail(httpResult);
                }
            }
        });
    }

    //是否绑卡
    public void checkBindCard(final HttpTaskCallBack<BindBankCardBean> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("doctorId", UserInfo.getInstance().getUserId());
        networkRequest.requestSRV(HttpContants.CMD_CHECK_BIND_CARD, "加载中", new HttpTaskCallBack<BindBankCardBean>() {
            @Override
            public void onSuccess(HttpResult<BindBankCardBean> httpResult) {
                HttpResult<BindBankCardBean> result = new HttpResult<>();
                result.copy(httpResult);
                BindBankCardBean bean = GsonUtil.fromJson(GsonUtil.toJson(httpResult.getData()), BindBankCardBean.class);
                if (bean != null) {
                    result.setData(bean);
                }
                if (mListener != null) {
                    mListener.onSuccess(result);
                }
            }

            @Override
            public void onFail(HttpResult<BindBankCardBean> httpResult) {
                HttpResult<Object> result = new HttpResult<>();
                result.copy(httpResult);
                if (mListener != null) {
                    mListener.onFail(httpResult);
                }
            }
        });
    }
}
