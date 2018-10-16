package com.bowen.doctor.mine.contract;

import com.bowen.doctor.common.bean.network.BindBankCardBean;

/**
 * Created by zhuzhipeng on 2017/6/2.
 */
public interface BindBankCardContract {
    interface View {
        void onUploadSuccess();
        void onUploadFailed();
        void onLoadBindCardSuccess(BindBankCardBean o);
    }

    interface Presenter{

    }
}
