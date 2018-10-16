package com.bowen.doctor.homepage.presenter;

import android.content.Context;

import com.bowen.commonlib.base.BasePresenter;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.doctor.common.bean.network.InfoFolkPrescription;
import com.bowen.doctor.homepage.contract.FolkPrescriptionDetailContract;
import com.bowen.doctor.homepage.model.FolkPrescriptionDetailModel;

/**
 * Created by zzp on 2017/5/21.
 *偏方详情页，根据偏方id获取偏方分享的一些数据，数据提供类
 */
public class FolkPrescriptionDetailPresenter extends BasePresenter {

    private FolkPrescriptionDetailModel folkPrescriptionDetailModel;
    private FolkPrescriptionDetailContract.View mView;

    public FolkPrescriptionDetailPresenter(Context context, FolkPrescriptionDetailContract.View view) {
        super(context);
        folkPrescriptionDetailModel = new FolkPrescriptionDetailModel(context);
        mContext = context;
        mView = view;
    }

    public void loadData(String id) {
        folkPrescriptionDetailModel.loadData(id, new HttpTaskCallBack<InfoFolkPrescription>() {
            @Override
            public void onSuccess(HttpResult<InfoFolkPrescription> result) {
                mView.onLoadSuccess(result.getData());
            }

            @Override
            public void onFail(HttpResult<InfoFolkPrescription> result) {
//                showToast(result.getMsg());
                mView.onLoadFail(result.getData());
            }
        });
    }
}
