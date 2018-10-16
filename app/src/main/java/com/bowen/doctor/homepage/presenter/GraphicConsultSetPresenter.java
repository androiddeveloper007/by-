package com.bowen.doctor.homepage.presenter;

import android.content.Context;

import com.bowen.commonlib.base.BasePresenter;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.doctor.common.bean.network.GraphicConsultSetBean;
import com.bowen.doctor.homepage.contract.GraphicConsultSetContract;
import com.bowen.doctor.homepage.model.GraphicConsultSetModel;

/**
 * 图文资讯服务设置
 * Created by zzp on 2018/7/21.
 */

public class GraphicConsultSetPresenter extends BasePresenter {
    private GraphicConsultSetModel graphicConsultSetModel;
    private GraphicConsultSetContract.View mView;

    public GraphicConsultSetPresenter(Context context, GraphicConsultSetContract.View view) {
        super(context);
        graphicConsultSetModel = new GraphicConsultSetModel(context);
        mContext = context;
        mView = view;
    }

    public void getAppointServe() {
        graphicConsultSetModel.getAppointServe(new HttpTaskCallBack<GraphicConsultSetBean>() {
            @Override
            public void onSuccess(HttpResult<GraphicConsultSetBean> result) {
                mView.onLoadSuccess(result.getData());
            }

            @Override
            public void onFail(HttpResult<GraphicConsultSetBean> result) {
            }
        });
    }

    public void saveAskServe(int askSwitch, double consultFee) {
        graphicConsultSetModel.saveAskServe(askSwitch, consultFee, new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> result) {
                mView.onSaveSuccess();
            }

            @Override
            public void onFail(HttpResult<Object> result) {
                mView.onSaveFailed();
            }
        });
    }
}
