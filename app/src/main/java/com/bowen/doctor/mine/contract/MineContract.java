package com.bowen.doctor.mine.contract;

import com.bowen.doctor.common.bean.network.BindBankCardBean;
import com.bowen.doctor.common.bean.network.DoctorInfo;

/**
 * Created by zzp on 2018/5/15.
 */

public interface MineContract {

    interface View {
        void onLoadSuccess(DoctorInfo info);
        void onLoadFail();
        void onLoadBindCardSuccess(BindBankCardBean bean);
    }

    interface Presenter {

    }
}
