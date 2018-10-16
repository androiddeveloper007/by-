package com.bowen.doctor.mine.contract;

import com.bowen.doctor.common.bean.network.DoctorUploadPhoto;

import java.util.List;

/**
 * Created by AwenZeng on 2017/6/2.
 */

public interface QualityCertificateContract {
    interface View {
        void onUploadSuccess();
        void onUploadFailed();
        void loadSuccess(List<DoctorUploadPhoto> list);
        void loadFail();
    }

    interface Presenter{

    }
}
