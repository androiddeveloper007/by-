package com.bowen.doctor.mine.presenter;

import android.content.Context;

import com.bowen.commonlib.base.BasePresenter;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.doctor.common.bean.network.DoctorInfoRecord;
import com.bowen.doctor.mine.contract.MyBusinessCardContract;
import com.bowen.doctor.mine.model.FinishInfoModel;

/**
 * Created by zhuzhipeng on 2018/7/17.
 */

public class MyBusinessCardPresenter extends BasePresenter {
    private FinishInfoModel finishInfoModel;
    private MyBusinessCardContract.View mView;
    public MyBusinessCardPresenter(Context mContext, MyBusinessCardContract.View view) {
        super(mContext);
        finishInfoModel = new FinishInfoModel(mContext);
        mView = view;
    }

    //加载提交过的数据
    public void getDoctorInfo() {
        finishInfoModel.getDoctorInfo(new HttpTaskCallBack<DoctorInfoRecord>() {
            @Override
            public void onSuccess(HttpResult<DoctorInfoRecord> result) {
                mView.loadDataSuccess(result.getData());
            }

            @Override
            public void onFail(HttpResult<DoctorInfoRecord> result) {
//                showToast(result.getMsg());
                mView.loadFailed();
            }
        });
    }
}
