package com.bowen.doctor.mine.model;

import android.content.Context;

import com.bowen.commonlib.base.BaseModel;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.GsonUtil;
import com.bowen.doctor.common.bean.network.DiseaseInfoRecord;
import com.bowen.doctor.common.model.UserInfo;
import com.bowen.doctor.common.http.HttpContants;
import com.bowen.doctor.common.http.NetworkRequest;

/**
 * Describe:模糊搜索常见疾病
 * Created by zzp on 2018/5/15.
 */

public class FitDiseaseSelectModel extends BaseModel {

    public FitDiseaseSelectModel(Context mContext) {
        super(mContext);
    }

    public void loadDataByStr(String diseaseName, int page, int pageSize, final HttpTaskCallBack<DiseaseInfoRecord> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("pageNo", page);
        networkRequest.setParam("pageSize", pageSize);
        networkRequest.setParam("diseaseName", diseaseName);
        networkRequest.requestSRV(HttpContants.CMD_LIKE_INFO_DISEASE_DEPT, "", new HttpTaskCallBack<DiseaseInfoRecord>() {
            @Override
            public void onSuccess(HttpResult<DiseaseInfoRecord> httpResult) {
                HttpResult<DiseaseInfoRecord> result = new HttpResult<>();
                result.copy(httpResult);
                DiseaseInfoRecord record = GsonUtil.getGson().fromJson(GsonUtil.toJson(httpResult.getData()), DiseaseInfoRecord.class);
                if (record != null) {
                    result.setData(record);
                }
                if (mListener != null) {
                    mListener.onSuccess(result);
                }
            }

            @Override
            public void onFail(HttpResult<DiseaseInfoRecord> httpResult) {
                HttpResult<DiseaseInfoRecord> result = new HttpResult<>();
                if (mListener != null) {
                    mListener.onFail(result);
                }
            }
        });
    }
}
