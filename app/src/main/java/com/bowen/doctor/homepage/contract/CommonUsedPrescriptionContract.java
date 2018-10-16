package com.bowen.doctor.homepage.contract;

import com.bowen.doctor.common.bean.network.CommonUsedPrescriptionRecord;

/**
 * Created by zzp on 2018/5/15.
 */

public interface CommonUsedPrescriptionContract {

    interface View {
        void onLoadSuccess(CommonUsedPrescriptionRecord record);

        void onLoadFail(CommonUsedPrescriptionRecord record);
    }

    interface Presenter {

    }
}
