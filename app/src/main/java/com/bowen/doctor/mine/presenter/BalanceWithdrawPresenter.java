package com.bowen.doctor.mine.presenter;

import android.content.Context;

import com.bowen.commonlib.base.BasePresenter;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.doctor.mine.contract.BalanceWithdrawContract;
import com.bowen.doctor.mine.model.BalanceWithdrawModel;

/**
 * Created by ZP-PC on 2018/7/19.
 */
public class BalanceWithdrawPresenter extends BasePresenter {
    private BalanceWithdrawModel balanceWithdrawModel;
    private BalanceWithdrawContract.View mView;
    private boolean firstTimeRequest=true;
    private boolean requestFinished;

    public BalanceWithdrawPresenter(Context mContext, BalanceWithdrawContract.View view) {
        super(mContext);
        balanceWithdrawModel = new BalanceWithdrawModel(mContext);
        mView = view;
    }

    public void applyWithdraw(String withdrawAmt) {
        if(withdrawAmt.length()==0){
            showToast("请输入提现金额");
            return;
        } else if(Double.parseDouble(withdrawAmt)==0){
            showToast("提现金额不能等于零");
            return;
        }
        if(firstTimeRequest || requestFinished) {
            balanceWithdrawModel.applyWithdraw(withdrawAmt, new HttpTaskCallBack<Object>() {
                @Override
                public void onSuccess(HttpResult<Object> result) {
                    showToast(result.getMsg());
                    mView.onUploadSuccess();
                    firstTimeRequest=false;
                    requestFinished=true;
                }

                @Override
                public void onFail(HttpResult<Object> result) {
                    showToast(result.getMsg());
                    mView.onUploadFailed();
                    firstTimeRequest=false;
                    requestFinished=true;
                }
            });
        }
    }

    //中间部分替换成*，保留前四位和后四位
    public String replaceWithStar(String str) {
        if (str.isEmpty() || str == null) {
            return null;
        } else {
            return str.replaceAll("(?<=\\d{4})\\d(?=\\d{4})", "*");
        }
    }
}
