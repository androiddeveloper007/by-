package com.bowen.doctor.mine.contract;

import com.bowen.doctor.common.bean.network.SearchHospitalRecord;

/**
 * Created by zzp on 2018/5/15.
 */

public interface HospitalSearchContract {

    interface View {
        void onLoadSuccess(SearchHospitalRecord list);

        void onLoadFail();
    }

    interface Presenter {

    }
}
