package com.bowen.doctor.mine.model;

import android.content.Context;

import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.GsonUtil;
import com.bowen.doctor.common.bean.network.DoctorUploadPhoto;
import com.bowen.doctor.common.model.UserInfo;
import com.bowen.doctor.common.http.HttpContants;
import com.bowen.doctor.common.http.NetworkRequest;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by zhuzhipeng on 2016/12/24.
 */
public class QualityCertificateModel {
    private Context mContext;

    public QualityCertificateModel(Context context) {
        mContext = context;
    }

    public void getDoctorFile(final HttpTaskCallBack<List<DoctorUploadPhoto>> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.requestSRV(HttpContants.CMD_GET_DOCTOR_FILE, "正在加载数据", new HttpTaskCallBack<List<DoctorUploadPhoto>>() {
            @Override
            public void onSuccess(HttpResult<List<DoctorUploadPhoto>> httpResult) {
                HttpResult<List<DoctorUploadPhoto>> result = new HttpResult<>();
                result.copy(httpResult);
                try {
                    JSONObject jsonObject = new JSONObject(GsonUtil.toJson(httpResult.getData()));
                    Type typeList = new TypeToken<List<DoctorUploadPhoto>>() {}.getType();
                    List<DoctorUploadPhoto> beanList = GsonUtil.getGson().fromJson(jsonObject.getString("fileList"), typeList);
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
            public void onFail(HttpResult<List<DoctorUploadPhoto>> httpResult) {
                HttpResult<List<DoctorUploadPhoto>> result = new HttpResult<>();
                if (mListener != null) {
                    mListener.onFail(result);
                }
            }
        });
    }
}
