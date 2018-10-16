package com.bowen.doctor.mine.presenter;

import android.content.Context;

import com.bowen.commonlib.base.BasePresenter;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.doctor.common.bean.network.EmrDoctorFanRecord;
import com.bowen.doctor.mine.contract.MyFansContract;
import com.bowen.doctor.mine.model.MyFansModel;

/**
 * Created by zzp on 2017/5/21.
 * 我的数据提供类
 */
public class MyFansPresenter extends BasePresenter {

    private MyFansModel myFansModel;
    private MyFansContract.View mView;

    public MyFansPresenter(Context context, MyFansContract.View view) {
        super(context);
        myFansModel = new MyFansModel(context);
        mContext = context;
        mView = view;
    }

    public void loadData(int index, int pageSize) {
        myFansModel.loadData(index, pageSize, new HttpTaskCallBack<EmrDoctorFanRecord>() {
            @Override
            public void onSuccess(HttpResult<EmrDoctorFanRecord> result) {
                mView.onLoadSuccess(result.getData());
            }

            @Override
            public void onFail(HttpResult<EmrDoctorFanRecord> result) {
//                showToast(result.getMsg());
                mView.onLoadFail();
            }
        });
    }
}
