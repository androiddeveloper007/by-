package com.bowen.doctor.homepage.contract;

import com.bowen.doctor.common.bean.AppointmentSet;
import com.bowen.doctor.common.bean.network.AppointmentPeriodInfo;
import com.bowen.doctor.common.bean.network.OutpatientSetRecord;

import java.util.ArrayList;

public interface OutpatientAppointmentSetContract {
    interface View {
        void onGetOutpatientAppointmentInfoSuccess(OutpatientSetRecord recordInfo);
        void onGetAppointmentPeriodSuccess(AppointmentPeriodInfo info);
        void onSaveOutpatientAppointmentSetInfoSuccess();
    }

    interface Presenter {
        void saveOutpatientAppointmentSetInfo(boolean askSwitch, String registFee, ArrayList<AppointmentSet> data);
        void getOutpatientAppointmentSetInfo(int week);
        void getAppointmentPeriod(long date,int type);
    }
}
