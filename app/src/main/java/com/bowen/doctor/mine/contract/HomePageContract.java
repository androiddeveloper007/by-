package com.bowen.doctor.mine.contract;

import com.bowen.doctor.common.bean.network.ConsultItem;
import com.bowen.doctor.common.bean.network.DoctorInfo;
import com.bowen.doctor.common.bean.network.Login;

import java.util.List;

/**
 * Created by zzp on 2018/5/15.
 */

public interface HomePageContract {

    interface View {
        void onLoadSuccess(DoctorInfo info);
        void onLoadFail();
        void loadConsultSuccess(List<ConsultItem> consultList);
        void loadConsultFail();
        void quickLoginSuccess(Login loginBean);
    }

    interface Presenter {

    }
}
