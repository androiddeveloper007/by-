package com.bowen.doctor.mine.contract;

import com.bowen.doctor.common.bean.network.Department;

import java.util.List;

/**
 * Created by AwenZeng on 2017/6/2.
 */

public interface MyHospitalAddContract {
    interface View {
        void onUploadSuccess();
        void onUploadFailed();
        void loadDepartmentListSuccess(List<Department> list);
    }

    interface Presenter{

    }
}
