package com.bowen.doctor.mine.contract;

import com.bowen.doctor.common.bean.network.DiseaseInfoRecord;

/**
 * Created by zzp on 2018/5/15.
 */

public interface FitDiseaseSelectContract {

    interface View {
        void onLoadSuccess(DiseaseInfoRecord list);

        void onLoadFail();
    }

    interface Presenter {

    }
}
