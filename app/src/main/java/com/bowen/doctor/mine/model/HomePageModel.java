package com.bowen.doctor.mine.model;

import android.content.Context;

import com.bowen.commonlib.base.BaseModel;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.GsonUtil;
import com.bowen.doctor.common.bean.network.DoctorInfo;
import com.bowen.doctor.common.model.UserInfo;
import com.bowen.doctor.common.http.HttpContants;
import com.bowen.doctor.common.http.NetworkRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Describe:“首页”
 * Created by zzp on 2018/5/15.
 */

public class HomePageModel extends BaseModel {

    public HomePageModel(Context mContext) {
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
}
