package com.bowen.doctor.mine.contract;

import com.bowen.doctor.common.bean.network.MyEnterHospitalRecord;

/**
 * Created by zzp on 2018/5/15.
 */

public interface AddHospitalRecordContract {

    interface View {
        void onLoadSuccess(MyEnterHospitalRecord list);
        void onLoadFail();
        void onCancelSuccess();
        void onCancelFail();
    }

    interface Presenter {

    }
}
