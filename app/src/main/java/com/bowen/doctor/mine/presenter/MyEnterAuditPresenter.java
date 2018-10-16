package com.bowen.doctor.mine.presenter;

import android.content.Context;

import com.bowen.commonlib.base.BasePresenter;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.doctor.common.bean.network.MyEnterAuditRecord;
import com.bowen.doctor.mine.contract.MyEnterAuditContract;
import com.bowen.doctor.mine.model.MyEnterAuditModel;

/**
 * Created by zzp on 2017/5/21.
 * 我的医馆数据提供类
 */
public class MyEnterAuditPresenter extends BasePresenter {

    private MyEnterAuditModel myEnterAuditModel;
    private MyEnterAuditContract.View mView;

    public MyEnterAuditPresenter(Context context, MyEnterAuditContract.View view) {
        super(context);
        myEnterAuditModel = new MyEnterAuditModel(context);
        mContext = context;
        mView = view;
    }

    public void loadData(int index, int pageSize, String hospitalId, int authStatus, boolean isLoad) {
        myEnterAuditModel.loadData(index, pageSize, hospitalId,authStatus, isLoad, new HttpTaskCallBack<MyEnterAuditRecord>() {
            @Override
            public void onSuccess(HttpResult<MyEnterAuditRecord> result) {
                mView.onLoadSuccess(result.getData());
            }

            @Override
            public void onFail(HttpResult<MyEnterAuditRecord> result) {
//                showToast(result.getMsg());
                mView.onLoadFail();
            }
        });
    }

    public void handleEnterApply(boolean isPass, String hospitalId) {
        myEnterAuditModel.handleEnterApply(isPass, hospitalId, new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> result) {
                mView.onHandleSuccess();
//                showToast(result.getMsg());
            }

            @Override
            public void onFail(HttpResult<Object> result) {
//                showToast(result.getMsg());
                mView.onHandleFail();
            }
        });
    }
}
