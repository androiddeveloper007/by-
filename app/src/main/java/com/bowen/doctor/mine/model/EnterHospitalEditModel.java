package com.bowen.doctor.mine.model;

import android.content.Context;

import com.bowen.commonlib.base.BaseModel;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.GsonUtil;
import com.bowen.doctor.common.bean.network.Department;
import com.bowen.doctor.common.model.UserInfo;
import com.bowen.doctor.common.http.HttpContants;
import com.bowen.doctor.common.http.NetworkRequest;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Describe:我入驻的医馆
 * Created by zzp on 2018/5/15.
 */

public class EnterHospitalEditModel extends BaseModel {

    public EnterHospitalEditModel(Context mContext) {
        super(mContext);
    }

    public void getAllDepartments(String id, final HttpTaskCallBack<List<Department>> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("hospitalId", id);
        networkRequest.requestSRV(HttpContants.CMD_GET_ALL_DEPARTMENTS, "", new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                HttpResult<List<Department>> result = new HttpResult<>();
                result.copy(httpResult);
                try {
                    JSONObject jsonObject = new JSONObject(GsonUtil.toJson(httpResult.getData()));
                    Type typelist = new TypeToken<List<Department>>() {}.getType();
                    List<Department> list = GsonUtil.getGson().fromJson(jsonObject.getString("deptList"), typelist);
                    if (list != null) {
                        result.setData(list);
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
                if (mListener != null) {
                    mListener.onFail(result);
                }
            }
        });
    }

    public void applyToEnter(String hospitalId,String hospitalDeptId,String departmentsId, final HttpTaskCallBack mListener) {
        NetworkRequest request = new NetworkRequest(mContext);
        request.setParam("token", UserInfo.getInstance().getToken());
        request.setParam("userId", UserInfo.getInstance().getUserId());
        request.setParam("hospitalId", hospitalId);
        request.setParam("hospitalDeptId", hospitalDeptId);
        request.setParam("departmentsId", departmentsId);
        request.requestSRV(HttpContants.CMD_APPLY_TO_ENTER, "正在提交数据",new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                if (mListener != null) {
                    mListener.onSuccess(httpResult);
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
