package com.bowen.doctor.mine.model;

import android.content.Context;

import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.GsonUtil;
import com.bowen.doctor.common.bean.network.Department;
import com.bowen.doctor.common.bean.network.DoctorInfoRecord;
import com.bowen.doctor.common.http.HttpContants;
import com.bowen.doctor.common.http.NetworkRequest;
import com.bowen.doctor.common.model.UserInfo;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by AwenZeng on 2016/12/24.
 */

public class FinishInfoModel {
    private Context mContext;

    public FinishInfoModel(Context context) {
        mContext = context;
    }

    public void loadDepartmentList(final HttpTaskCallBack<List<Department>> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.requestSRV(HttpContants.CMD_SHOW_PRESCRIPTION_SECTION_LIST, "", new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                HttpResult<List<Department>> result = new HttpResult<>();
                result.copy(httpResult);
                try {
                    JSONObject jsonObject = new JSONObject(GsonUtil.toJson(httpResult.getData()));
                    Type typeList = new TypeToken<List<Department>>() {
                    }.getType();
                    List<Department> beanList = GsonUtil.getGson().fromJson(jsonObject.getString("hospitalDepartmentsList"), typeList);
                    if (beanList != null) {
                        result.setData(beanList);
                    }
                    if (mListener != null) {
                        mListener.onSuccess(result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                HttpResult<List<Department>> result = new HttpResult<>();
                result.copy(httpResult);
                if(mListener != null) {
                    mListener.onFail(result);
                }
            }
        });
    }

    public void getDoctorInfo(final HttpTaskCallBack<DoctorInfoRecord> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.requestSRV(HttpContants.CMD_GET_DOCTOR_INFO, "", new HttpTaskCallBack<DoctorInfoRecord>() {
            @Override
            public void onSuccess(HttpResult<DoctorInfoRecord> httpResult) {
                HttpResult<DoctorInfoRecord> result = new HttpResult<>();
                result.copy(httpResult);
                DoctorInfoRecord record = GsonUtil.fromJson(GsonUtil.toJson(httpResult.getData()), DoctorInfoRecord.class);
                if (record != null) {
                    result.setData(record);
                }
                if (mListener != null) {
                    mListener.onSuccess(result);
                }
            }

            @Override
            public void onFail(HttpResult<DoctorInfoRecord> httpResult) {
                HttpResult<DoctorInfoRecord> result = new HttpResult<>();
                result.copy(httpResult);
                if(mListener != null) {
                    mListener.onFail(result);
                }
            }
        });
    }
}
