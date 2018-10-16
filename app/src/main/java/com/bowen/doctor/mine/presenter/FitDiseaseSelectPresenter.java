package com.bowen.doctor.mine.presenter;

import android.content.Context;

import com.bowen.commonlib.base.BasePresenter;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.doctor.common.bean.network.DiseaseInfoRecord;
import com.bowen.doctor.mine.contract.FitDiseaseSelectContract;
import com.bowen.doctor.mine.model.FitDiseaseSelectModel;

/**
 * Created by zzp on 2017/5/21.
 * 选择病症，数据提供类
 */
public class FitDiseaseSelectPresenter extends BasePresenter {

    private FitDiseaseSelectModel fitDiseaseSelectModel;
    private FitDiseaseSelectContract.View mView;

    public FitDiseaseSelectPresenter(Context context, FitDiseaseSelectContract.View view) {
        super(context);
        fitDiseaseSelectModel = new FitDiseaseSelectModel(context);
        mContext = context;
        mView = view;
    }

    /***
     * 根据关键字搜病症
     * @param diseaseName
     */
    public void loadDataByStr(String diseaseName, int page, int pageSize) {
        fitDiseaseSelectModel.loadDataByStr(diseaseName, page, pageSize, new HttpTaskCallBack<DiseaseInfoRecord>() {
            @Override
            public void onSuccess(HttpResult<DiseaseInfoRecord> result) {
                mView.onLoadSuccess(result.getData());
            }

            @Override
            public void onFail(HttpResult<DiseaseInfoRecord> result) {
//                showToast(result.getMsg());
                mView.onLoadFail();
            }
        });
    }
}