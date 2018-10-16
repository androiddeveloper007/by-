package com.bowen.doctor.mine.model;

import android.content.Context;

import com.bowen.commonlib.base.BaseModel;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.GsonUtil;
import com.bowen.doctor.common.bean.network.WithdrawRecord;
import com.bowen.doctor.common.http.HttpContants;
import com.bowen.doctor.common.http.NetworkRequest;
import com.bowen.doctor.common.model.UserInfo;

/**
 * Describe:提现记录
 * Created by zzp on 2018/5/15.
 */

public class WithdrawRecordModel extends BaseModel {

    public WithdrawRecordModel(Context mContext) {
        super(mContext);
    }

    public void loadData(int index, int pageSize, final HttpTaskCallBack<WithdrawRecord> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("pageNo", index);
        networkRequest.setParam("pageSize", pageSize);
        networkRequest.requestSRV(HttpContants.CMD_GET_MY_WITHDRAW_RECORD, "", new HttpTaskCallBack<WithdrawRecord>() {
            @Override
            public void onSuccess(HttpResult<WithdrawRecord> httpResult) {
                HttpResult<WithdrawRecord> result = new HttpResult<>();
                result.copy(httpResult);
                WithdrawRecord record = GsonUtil.fromJson(GsonUtil.toJson(httpResult.getData()), WithdrawRecord.class);
                if (record != null) {
                    result.setData(record);
                }
                if (mListener != null) {
                    mListener.onSuccess(result);
                }
            }

            @Override
            public void onFail(HttpResult<WithdrawRecord> httpResult) {
                HttpResult<WithdrawRecord> result = new HttpResult<>();
                result.copy(httpResult);
                if (mListener != null) {
                    mListener.onFail(result);
                }
            }
        });
    }
}
