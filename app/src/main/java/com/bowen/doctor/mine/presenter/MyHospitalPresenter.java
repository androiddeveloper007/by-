package com.bowen.doctor.mine.presenter;

import android.app.Activity;
import android.content.Context;

import com.bowen.commonlib.base.BasePresenter;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.DataCacheUtil;
import com.bowen.commonlib.utils.EmptyUtils;
import com.bowen.commonlib.utils.RouterActivityUtil;
import com.bowen.doctor.common.bean.network.MyHospitalRecord;
import com.bowen.doctor.common.util.LoginStatusUtil;
import com.bowen.doctor.login.activity.LoginActivity;
import com.bowen.doctor.mine.contract.MyHospitalContract;
import com.bowen.doctor.mine.model.MyHospitalModel;

/**
 * Created by zzp on 2017/5/21.
 * 我的医馆数据提供类
 */
public class MyHospitalPresenter extends BasePresenter {

    private MyHospitalModel myHospitalModel;
    private MyHospitalContract.View mView;

    public MyHospitalPresenter(Context context, MyHospitalContract.View view) {
        super(context);
        myHospitalModel = new MyHospitalModel(context);
        mContext = context;
        mView = view;
    }

    public void loadData(int index, int pageSize) {
        if(hasNotLogin()) return;
        myHospitalModel.loadData(index, pageSize, new HttpTaskCallBack<MyHospitalRecord>() {
            @Override
            public void onSuccess(HttpResult<MyHospitalRecord> result) {
                mView.onLoadSuccess(result.getData());
            }

            @Override
            public void onFail(HttpResult<MyHospitalRecord> result) {
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
