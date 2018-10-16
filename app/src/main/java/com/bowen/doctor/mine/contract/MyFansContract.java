package com.bowen.doctor.mine.contract;

import com.bowen.doctor.common.bean.network.EmrDoctorFanRecord;

/**
 * Created by zzp on 2018/5/15.
 */

public interface MyFansContract {

    interface View {
        void onLoadSuccess(EmrDoctorFanRecord list);

        void onLoadFail();

    }

    interface Presenter {

    }
}
