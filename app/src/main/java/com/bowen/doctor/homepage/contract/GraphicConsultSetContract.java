package com.bowen.doctor.homepage.contract;

import com.bowen.doctor.common.bean.network.GraphicConsultSetBean;

public interface GraphicConsultSetContract {
    interface View {
        void onLoadSuccess(GraphicConsultSetBean bean);
        void onLoadFail();
        void onSaveSuccess();
        void onSaveFailed();
    }

    interface Presenter {

    }
}
