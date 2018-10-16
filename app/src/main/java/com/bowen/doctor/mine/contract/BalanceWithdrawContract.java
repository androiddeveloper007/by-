package com.bowen.doctor.mine.contract;

/**
 * Created by zhuzhipeng on 2017/6/2.
 */
public interface BalanceWithdrawContract {
    interface View {
        void onUploadSuccess();
        void onUploadFailed();
    }

    interface Presenter{

    }
}