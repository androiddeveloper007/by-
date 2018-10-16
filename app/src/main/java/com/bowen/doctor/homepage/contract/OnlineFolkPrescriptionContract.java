package com.bowen.doctor.homepage.contract;

import com.bowen.doctor.common.bean.network.OnlineFolkPrescriptionRecord;

/**
 * Created by zzp on 2018/5/15.
 */

public interface OnlineFolkPrescriptionContract {

    interface View {
        void onLoadSuccess(OnlineFolkPrescriptionRecord record);

        void onLoadFail(OnlineFolkPrescriptionRecord record);
    }

    interface Presenter {

    }
}
