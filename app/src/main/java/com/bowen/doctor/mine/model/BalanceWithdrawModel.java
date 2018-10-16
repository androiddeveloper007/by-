package com.bowen.doctor.mine.model;

import android.content.Context;

import com.bowen.commonlib.base.BaseModel;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.doctor.common.http.HttpContants;
import com.bowen.doctor.common.http.NetworkRequest;
import com.bowen.doctor.common.model.UserInfo;

/**
 * Describe:余额提现
 * Created by zhuzhipeng on 2018/7/9.
 */
public class BalanceWithdrawModel extends BaseModel {

    public BalanceWithdrawModel(Context mContext) {
        super(mContext);
    }

    public void applyWithdraw(String withdrawAmt, final HttpTaskCallBack<Object> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("doctorId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("withdrawAmt", withdrawAmt);
        networkRequest.requestSRV(HttpContants.CMD_APPLY_WITHDRAW, new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                HttpResult<Object> result = new HttpResult<>();
                result.copy(httpResult);
                if (mListener != null) {
                    mListener.onSuccess(result);
                }
            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                HttpResult<Object> result = new HttpResult<>();
                result.copy(httpResult);
                if (mListener != null) {
                    mListener.onFail(result);
                }
            }
        });
    }
}
