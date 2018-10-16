package com.bowen.doctor.mine.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.bowen.commonlib.base.BasePresenter;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.doctor.common.bean.network.Department;
import com.bowen.doctor.mine.contract.EnterHospitalEditContract;
import com.bowen.doctor.mine.model.EnterHospitalEditModel;

import java.util.List;

/**
 * Created by zzp on 2017/5/21.
 * 我入驻的医馆数据提供类
 */
public class EnterHospitalEditPresenter extends BasePresenter {

    private EnterHospitalEditModel enterHospitalEditModel;
    private EnterHospitalEditContract.View mView;

    public EnterHospitalEditPresenter(Context context, EnterHospitalEditContract.View view) {
        super(context);
        enterHospitalEditModel = new EnterHospitalEditModel(context);
        mContext = context;
        mView = view;
    }

    public void loadDepartmentList(String hospitalId) {
        enterHospitalEditModel.getAllDepartments(hospitalId, new HttpTaskCallBack<List<Department>>() {


            @Override
            public void onSuccess(HttpResult<List<Department>> result) {
                mView.loadDepartmentListSuccess(result.getData());
            }

            @Override
            public void onFail(HttpResult<List<Department>> result) {
//                showToast(result.getMsg());
            }
        });
    }

    public void applyToEnter(String hospitalId,String hospitalDeptId,String departmentsId) {
        if(TextUtils.isEmpty(hospitalId)){ showToast("请选择医馆"); return; }
        if(TextUtils.isEmpty(hospitalDeptId)){ showToast("请选择科室"); return; }
        enterHospitalEditModel.applyToEnter(hospitalId, hospitalDeptId, departmentsId, new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> result) {
                mView.uploadSuccess();
            }

            @Override
            public void onFail(HttpResult<Object> result) {
//                showToast(result.getMsg());
            }
        });
    }
}