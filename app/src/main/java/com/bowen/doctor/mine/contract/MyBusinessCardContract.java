package com.bowen.doctor.mine.contract;

import com.bowen.doctor.common.bean.network.DoctorInfoRecord;

/**
 * Created by AwenZeng on 2017/6/2.
 */

public interface MyBusinessCardContract {
    interface View {
        void loadDataSuccess(DoctorInfoRecord record);
        void loadFailed();
    }

    interface Presenter{

    }
}
