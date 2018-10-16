package com.bowen.doctor.mine.contract;

import com.bowen.doctor.common.bean.network.WithdrawRecord;

/**
 * Created by zzp on 2018/5/15.
 */

public interface WithdrawRecordContract {

    interface View {
        void onLoadSuccess(WithdrawRecord list);
        void onLoadFail();
    }

    interface Presenter {

    }
}
