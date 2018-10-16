package com.bowen.doctor.mine.contract;

import com.bowen.doctor.common.bean.network.Department;

import java.util.List;

/**
 * Created by zzp on 2018/5/15.
 */

public interface EnterHospitalEditContract {

    interface View {
        void loadDepartmentListSuccess(List<Department> list);
        void uploadSuccess();
        void uploadFailed();
    }

    interface Presenter {

    }
}
