package com.bowen.doctor.mine.presenter;

import android.app.Activity;
import android.content.Context;

import com.bowen.commonlib.base.BasePresenter;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.DataCacheUtil;
import com.bowen.commonlib.utils.EmptyUtils;
import com.bowen.commonlib.utils.RouterActivityUtil;
import com.bowen.doctor.common.bean.network.MyEnterHospitalRecord;
import com.bowen.doctor.common.util.LoginStatusUtil;
import com.bowen.doctor.login.activity.LoginActivity;
import com.bowen.doctor.mine.contract.AddHospitalRecordContract;
import com.bowen.doctor.mine.model.MyEnterHospitalModel;

/**
 * Created by zzp on 2017/5/21.
 * 入驻申请的记录数据提供类
 */
public class AddHospitalRecordPresenter extends BasePresenter {

    private MyEnterHospitalModel myEnterHospitalModel;
    private AddHospitalRecordContract.View mView;

    public AddHospitalRecordPresenter(Context context, AddHospitalRecordContract.View view) {
        super(context);
        myEnterHospitalModel = new MyEnterHospitalModel(context);
        mContext = context;
        mView = view;
    }

    public void loadData(int index, int pageSize) {
        if(hasNotLogin()) return;
        myEnterHospitalModel.loadData(index, pageSize, new HttpTaskCallBack<MyEnterHospitalRecord>() {
            @Override
            public void onSuccess(HttpResult<MyEnterHospitalRecord> result) {
                mView.onLoadSuccess(result.getData());
            }

            @Override
            public void onFail(HttpResult<MyEnterHospitalRecord> result) {
//                showToast(result.getMsg());
                mView.onLoadFail();
            }
        });
    }

    public void loadRecord(int index, int pageSize) {
        if(hasNotLogin()) return;
        myEnterHospitalModel.loadRecord(index, pageSize, new HttpTaskCallBack<MyEnterHospitalRecord>() {
            @Override
            public void onSuccess(HttpResult<MyEnterHospitalRecord> result) {
                mView.onLoadSuccess(result.getData());
            }

            @Override
            public void onFail(HttpResult<MyEnterHospitalRecord> result) {
//                showToast(result.getMsg());
                mView.onLoadFail();
            }
        });
    }

    public void cancelApplyToEnter(String doctorHospitalDeptId) {
        if(hasNotLogin()) return;
        myEnterHospitalModel.cancelApplyToEnter(doctorHospitalDeptId, new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> result) {
                mView.onCancelSuccess();
            }

            @Override
            public void onFail(HttpResult<Object> result) {
//                showToast(result.getMsg());
                mView.onCancelFail();
            }
        });
    }

    public boolean hasNotLogin() {
        if (LoginStatusUtil.getInstance().isLogin()) {
            return false;
        } else {
            if (EmptyUtils.isNotEmpty(DataCacheUtil.getInstance().getString(DataCacheUtil.LOGIN_TOKEN, ""))) {
                RouterActivityUtil.startActivity((Activity) mContext, LoginActivity.class);
            }
            return true;
        }
    }
}