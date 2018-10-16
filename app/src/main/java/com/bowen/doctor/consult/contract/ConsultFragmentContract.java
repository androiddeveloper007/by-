package com.bowen.doctor.consult.contract;


import com.bowen.doctor.common.bean.network.ConsultInfo;

/**
 * Created by zzp on 2018/5/15.
 */

public interface ConsultFragmentContract {

    interface View {
        void onGetConsultDataSuccess(ConsultInfo consultInfo);
        void onGetConsultDataFailed();
        void onRemoveConsultInfo();
    }

    interface Presenter {
       void getConsultData(int pageNo,int pageSize);
        void removeConsultInfo(String sendUserId);
    }
}
