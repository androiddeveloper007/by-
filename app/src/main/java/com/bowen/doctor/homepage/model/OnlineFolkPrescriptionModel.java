package com.bowen.doctor.homepage.model;

import android.content.Context;

import com.bowen.commonlib.base.BaseModel;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.GsonUtil;
import com.bowen.doctor.common.bean.network.OnlineFolkPrescriptionRecord;
import com.bowen.doctor.common.http.HttpContants;
import com.bowen.doctor.common.http.NetworkRequest;
import com.bowen.doctor.common.model.UserInfo;

/**
 * Describe:
 * Created by zzp on 2018/5/15.
 */

public class OnlineFolkPrescriptionModel extends BaseModel {

    public OnlineFolkPrescriptionModel(Context mContext) {
        super(mContext);
    }

    public void loadData(int index, int pageSize, final HttpTaskCallBack<OnlineFolkPrescriptionRecord> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("pageNo", index);
        networkRequest.setParam("pageSize", pageSize);
        networkRequest.requestSRV(HttpContants.CMD_GET_ONLINE_PRESCRIPTION, "", new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                HttpResult<OnlineFolkPrescriptionRecord> result = new HttpResult<>();
                result.copy(httpResult);
                OnlineFolkPrescriptionRecord record = GsonUtil.fromJson(GsonUtil.toJson(httpResult.getData()), OnlineFolkPrescriptionRecord.class);
                if (record != null) {
                    result.setData(record);
                }
                if (mListener != null) {
                    mListener.onSuccess(result);
                }
            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                HttpResult<OnlineFolkPrescriptionRecord> result = new HttpResult<>();
                result.copy(httpResult);
                OnlineFolkPrescriptionRecord record = GsonUtil.fromJson(GsonUtil.toJson(httpResult.getData()), OnlineFolkPrescriptionRecord.class);
                if (record != null) {
                    result.setData(record);
                }
                if (mListener != null) {
                    mListener.onFail(result);
                }
            }
        });
    }
}
