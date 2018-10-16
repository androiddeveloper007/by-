package com.bowen.doctor.mine.contract;

import com.bowen.doctor.common.bean.network.MyEnterAuditRecord;

/**
 * Created by zzp on 2018/5/15.
 */

public interface MyEnterAuditContract {

    interface View {
        void onLoadSuccess(MyEnterAuditRecord list);
        void onLoadFail();
        void onHandleSuccess();
        void onHandleFail();
    }

    interface Presenter {

    }
}
