package com.bowen.doctor.mine.contract;

import com.bowen.doctor.common.bean.network.HospitalDetailInfo;

/**
 * Created by zzp on 2018/5/15.
 */

public interface HospitalDetailContract {

    interface View {
        void onLoadSuccess(HospitalDetailInfo info);
        void onLoadFail(HospitalDetailInfo info);
    }

    interface Presenter {

    }
}
