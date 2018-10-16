package com.bowen.doctor.mine.presenter;

import android.content.Context;

import com.bowen.commonlib.base.BasePresenter;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.doctor.common.bean.network.WithdrawRecord;
import com.bowen.doctor.mine.contract.WithdrawRecordContract;
import com.bowen.doctor.mine.model.WithdrawRecordModel;

/**
 * Created by zzp on 2017/5/21.
 * 提现记录
 */
public class WithdrawRecordPresenter extends BasePresenter {

    private WithdrawRecordModel withdrawRecordModel;
    private WithdrawRecordContract.View mView;

    public WithdrawRecordPresenter(Context context, WithdrawRecordContract.View view) {
        super(context);
        withdrawRecordModel = new WithdrawRecordModel(context);
        mContext = context;
        mView = view;
    }

    public void loadData(int index, int pageSize) {
        withdrawRecordModel.loadData(index, pageSize, new HttpTaskCallBack<WithdrawRecord>() {
            @Override
            public void onSuccess(HttpResult<WithdrawRecord> result) {
                mView.onLoadSuccess(result.getData());
            }

            @Override
            public void onFail(HttpResult<WithdrawRecord> result) {
                showToast(result.getMsg());
                mView.onLoadFail();
            }
        });
    }
}
