package com.bowen.doctor.mine.presenter;

import android.content.Context;

import com.bowen.commonlib.base.BasePresenter;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.doctor.common.bean.network.MyOrderRecord;
import com.bowen.doctor.mine.contract.MyOrderContract;
import com.bowen.doctor.mine.model.MyOrderModel;

/**
 * Created by zzp on 2017/5/21.
 * 我的订单数据提供类
 */
public class MyOrderPresenter extends BasePresenter {

    private MyOrderModel myOrderModel;
    private MyOrderContract.View mView;

    public MyOrderPresenter(Context context, MyOrderContract.View view) {
        super(context);
        myOrderModel = new MyOrderModel(context);
        mContext = context;
        mView = view;
    }

    public void loadData(int index, int pageSize) {
        myOrderModel.loadData(index, pageSize, new HttpTaskCallBack<MyOrderRecord>() {
            @Override
            public void onSuccess(HttpResult<MyOrderRecord> result) {
                mView.onLoadSuccess(result.getData());
            }

            @Override
            public void onFail(HttpResult<MyOrderRecord> result) {
//                showToast(result.getMsg());
                mView.onLoadFail();
            }
        });
    }
}
