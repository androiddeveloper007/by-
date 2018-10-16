package com.bowen.doctor.mine.contract;

import com.bowen.doctor.common.bean.network.MyEnterHospitalRecord;
import com.bowen.doctor.common.bean.network.MyHospitalRecord;

/**
 * Created by zzp on 2018/5/15.
 */

public interface MyEnterHospitalContract {

    interface View {
        void onLoadSuccess(MyEnterHospitalRecord list);

        void onLoadFail();

    }

    interface Presenter {

    }
}
