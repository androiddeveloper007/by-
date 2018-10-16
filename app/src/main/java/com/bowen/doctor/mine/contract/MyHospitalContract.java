package com.bowen.doctor.mine.contract;

import com.bowen.doctor.common.bean.network.MyHospitalRecord;

/**
 * Created by zzp on 2018/5/15.
 */

public interface MyHospitalContract {

    interface View {
        void onLoadSuccess(MyHospitalRecord list);

        void onLoadFail();

    }

    interface Presenter {

    }
}
