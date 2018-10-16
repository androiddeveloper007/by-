package com.bowen.doctor.mine.model;

import android.content.Context;

import com.bowen.commonlib.base.BaseModel;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.GsonUtil;
import com.bowen.doctor.common.bean.network.SearchHospitalRecord;
import com.bowen.doctor.common.model.UserInfo;
import com.bowen.doctor.common.http.HttpContants;
import com.bowen.doctor.common.http.NetworkRequest;

/**
 * Describe:模糊搜索常见疾病
 * Created by zzp on 2018/5/15.
 */

public class HospitalSearchModel extends BaseModel {

    public HospitalSearchModel(Context mContext) {
        super(mContext);
    }

    public void loadDataByStr(String searchInfo, int page, int pageSize, final HttpTaskCallBack<SearchHospitalRecord> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("pageNo", page);
        networkRequest.setParam("pageSize", pageSize);
        networkRequest.setParam("searchInfo", searchInfo);
        networkRequest.requestSRV(HttpContants.CMD_GET_ALL_HOSPITAL, "", new HttpTaskCallBack<SearchHospitalRecord>() {
            @Override
            public void onSuccess(HttpResult<SearchHospitalRecord> httpResult) {
                HttpResult<SearchHospitalRecord> result = new HttpResult<>();
                result.copy(httpResult);
                SearchHospitalRecord record = GsonUtil.getGson().fromJson(GsonUtil.toJson(httpResult.getData()), SearchHospitalRecord.class);
                if (record != null) {
                    result.setData(record);
                }
                if (mListener != null) {
                    mListener.onSuccess(result);
                }
            }

            @Override
            public void onFail(HttpResult<SearchHospitalRecord> httpResult) {
                HttpResult<SearchHospitalRecord> result = new HttpResult<>();
                if (mListener != null) {
                    mListener.onFail(result);
                }
            }
        });
    }
}
