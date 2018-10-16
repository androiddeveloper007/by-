package com.bowen.doctor.homepage.model;

import android.content.Context;
import android.text.TextUtils;

import com.bowen.commonlib.base.BaseModel;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.GsonUtil;
import com.bowen.doctor.common.bean.network.InfoFolkPrescription;
import com.bowen.doctor.common.http.HttpContants;
import com.bowen.doctor.common.http.NetworkRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Describe:
 * Created by zzp on 2018/5/15.
 */

public class FolkPrescriptionDetailModel extends BaseModel {

    public FolkPrescriptionDetailModel(Context mContext) {
        super(mContext);
    }

    public void loadData(String id, final HttpTaskCallBack<InfoFolkPrescription> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        if (!TextUtils.isEmpty(id)) {
            networkRequest.setParam("prescriptionId", id);
        }
        networkRequest.requestSRV(HttpContants.CMD_GET_INFO_FOLK_PRESCRIPTION, "", new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                HttpResult<InfoFolkPrescription> result = new HttpResult<>();
                result.copy(httpResult);
                try {
                    JSONObject jsonObject = new JSONObject(GsonUtil.toJson(httpResult.getData()));
                    InfoFolkPrescription info = GsonUtil.fromJson(jsonObject.optString("infoFolkPrescription"), InfoFolkPrescription.class);
                    if (info != null) {
                        result.setData(info);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (mListener != null) {
                    mListener.onSuccess(result);
                }
            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                HttpResult<InfoFolkPrescription> result = new HttpResult<>();
                result.copy(httpResult);
                InfoFolkPrescription info = GsonUtil.fromJson(GsonUtil.toJson(httpResult.getData()), InfoFolkPrescription.class);
                if (info != null) {
                    result.setData(info);
                }
                if (mListener != null) {
                    mListener.onFail(result);
                }
            }
        });
    }
}
