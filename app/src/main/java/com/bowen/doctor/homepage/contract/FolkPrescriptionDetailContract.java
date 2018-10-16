package com.bowen.doctor.homepage.contract;

import com.bowen.doctor.common.bean.network.InfoFolkPrescription;

/**
 * Created by zzp on 2018/5/15.
 */

public interface FolkPrescriptionDetailContract {

    interface View {
        void onLoadSuccess(InfoFolkPrescription list);

        void onLoadFail(InfoFolkPrescription list);

//        void getDoctorCommentSuccess(PrescriptionDoctorCommentRecord list);
//
//        void getDoctorCommentFail(PrescriptionDoctorCommentRecord list);
//
//        void getUserCommentSuccess(PrescriptionUserCommentRecord list);
//
//        void getUserCommentFail(PrescriptionUserCommentRecord list);
    }

    interface Presenter {

    }
}
