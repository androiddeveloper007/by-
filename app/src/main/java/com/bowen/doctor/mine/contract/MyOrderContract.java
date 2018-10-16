package com.bowen.doctor.mine.contract;

import com.bowen.doctor.common.bean.network.MyOrderRecord;

/**
 * Created by zzp on 2018/5/15.
 */

public interface MyOrderContract {

    interface View {
        void onLoadSuccess(MyOrderRecord list);
        void onLoadFail();
    }

    interface Presenter {

    }
}
