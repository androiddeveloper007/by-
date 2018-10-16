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
import com.bowen.doctor.mine.contract.MyEnterHospitalContract;
import com.bowen.doctor.mine.model.MyEnterHospitalModel;

/**
 * Created by zzp on 2017/5/21.
 * 我入驻的医馆数据提供类
 */
public class MyEnterHospitalPresenter extends BasePresenter {

    private MyEnterHospitalModel myEnterHospitalModel;
    private MyEnterHospitalContract.View mView;

    public MyEnterHospitalPresenter(Context context, MyEnterHospitalContract.View view) {
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
