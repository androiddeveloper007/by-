package com.bowen.doctor.mine.contract;

import com.bowen.doctor.common.bean.network.Department;
import com.bowen.doctor.common.bean.network.DoctorInfoRecord;

import java.util.List;

public interface FinishInfoContract {
    interface View {
        void onUploadSuccess();
        void onUploadFailed();
        void loadDepartmentListSuccess(List<Department> list);
        void loadDataSuccess(DoctorInfoRecord record);
        void loadFailed();
    }

    interface Presenter{

    }
}
