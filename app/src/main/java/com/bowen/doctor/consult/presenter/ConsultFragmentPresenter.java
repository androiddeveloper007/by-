package com.bowen.doctor.consult.presenter;

import android.content.Context;

import com.bowen.commonlib.base.BasePresenter;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.doctor.common.bean.network.ConsultInfo;
import com.bowen.doctor.consult.contract.ConsultFragmentContract;
import com.bowen.doctor.consult.model.ConsultModel;

/**
 * Describe:
 * Created by AwenZeng on 2018/8/14.
 */
public class ConsultFragmentPresenter extends BasePresenter implements ConsultFragmentContract.Presenter{
    private ConsultModel mConsultModel;
    private ConsultFragmentContract.View mView;

    public ConsultFragmentPresenter(Context mContext,ConsultFragmentContract.View view) {
        super(mContext);
        mConsultModel = new ConsultModel(mContext);
        mView = view;
    }

    @Override
    public void getConsultData(int pageNo, int pageSize) {
        mConsultModel.getConsultData(pageNo, pageSize, new HttpTaskCallBack<ConsultInfo>() {
            @Override
            public void onSuccess(HttpResult<ConsultInfo> result) {
                    mView.onGetConsultDataSuccess(result.getData());
            }

            @Override
            public void onFail(HttpResult<ConsultInfo> result) {
                  mView.onGetConsultDataFailed();
            }
        });
    }

    @Override
    public void removeConsultInfo(String sendUserId) {
        mConsultModel.removeConsultInfo(sendUserId, new HttpTaskCallBack<String>() {
            @Override
            public void onSuccess(HttpResult<String> result) {
                mView.onRemoveConsultInfo();
            }

            @Override
            public void onFail(HttpResult<String> result) {

            }
        });
    }
}
