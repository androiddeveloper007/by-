package com.bowen.doctor.homepage.presenter;

import android.content.Context;

import com.bowen.commonlib.base.BasePresenter;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.doctor.common.bean.AppointmentSet;
import com.bowen.doctor.common.bean.network.AppointmentPeriodInfo;
import com.bowen.doctor.common.bean.network.OutpatientSetRecord;
import com.bowen.doctor.homepage.contract.OutpatientAppointmentSetContract;
import com.bowen.doctor.homepage.model.OutpatientAppointmentModel;

import java.util.ArrayList;

/**
 * 门诊预约的数据提供者
 * Created by zzp on 2017/5/21.
 */

public class OutpatientAppointmentSetPresenter extends BasePresenter implements OutpatientAppointmentSetContract.Presenter {
    private OutpatientAppointmentModel mOutpatientAppointmentSetModel;
    private OutpatientAppointmentSetContract.View mView;

    public OutpatientAppointmentSetPresenter(Context context, OutpatientAppointmentSetContract.View view) {
        super(context);
        mOutpatientAppointmentSetModel = new OutpatientAppointmentModel(context);
        mContext = context;
        mView = view;
    }

    @Override
    public void getOutpatientAppointmentSetInfo(int week) {
        mOutpatientAppointmentSetModel.getOutpatientAppointmentSetInfo(week,new HttpTaskCallBack<OutpatientSetRecord>() {
            @Override
            public void onSuccess(HttpResult<OutpatientSetRecord> result) {
                mView.onGetOutpatientAppointmentInfoSuccess(result.getData());
            }

            @Override
            public void onFail(HttpResult<OutpatientSetRecord> result) {
//                ToastUtil.getInstance().showToastDialog(result.getMsg());
            }
        });
    }

    @Override
    public void getAppointmentPeriod(long date, int type) {
        mOutpatientAppointmentSetModel.getAppointmentPeriod(date, type, new HttpTaskCallBack<AppointmentPeriodInfo>() {
            @Override
            public void onSuccess(HttpResult<AppointmentPeriodInfo> result) {
                mView.onGetAppointmentPeriodSuccess(result.getData());
            }

            @Override
            public void onFail(HttpResult<AppointmentPeriodInfo> result) {
//                ToastUtil.getInstance().showToastDialog(result.getMsg());
            }
        });
    }

    @Override
    public void saveOutpatientAppointmentSetInfo(boolean askSwitch, String registFee, ArrayList<AppointmentSet> data) {
        mOutpatientAppointmentSetModel.saveOutpatientAppointmentSetInfo(askSwitch, registFee, data, new HttpTaskCallBack() {
            @Override
            public void onSuccess(HttpResult result){
                mView.onSaveOutpatientAppointmentSetInfoSuccess();
            }

            @Override
            public void onFail(HttpResult result) {
//                ToastUtil.getInstance().showToastDialog(result.getMsg());
            }
        });
    }
}
