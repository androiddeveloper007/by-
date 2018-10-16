package com.bowen.doctor.mine.presenter;

import android.content.Context;

import com.bowen.commonlib.base.BasePresenter;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.doctor.common.bean.network.SearchHospitalRecord;
import com.bowen.doctor.mine.contract.HospitalSearchContract;
import com.bowen.doctor.mine.model.HospitalSearchModel;

/**
 * Created by zzp on 2017/5/21.
 * 选择病症，数据提供类
 */
public class HospitalSearchPresenter extends BasePresenter {

    private HospitalSearchModel hospitalSearchModel;
    private HospitalSearchContract.View mView;

    public HospitalSearchPresenter(Context context, HospitalSearchContract.View view) {
        super(context);
        hospitalSearchModel = new HospitalSearchModel(context);
        mContext = context;
        mView = view;
    }

    public void loadDataByStr(String searchInfo, int page, int pageSize) {
        hospitalSearchModel.loadDataByStr(searchInfo, page, pageSize, new HttpTaskCallBack<SearchHospitalRecord>() {
            @Override
            public void onSuccess(HttpResult<SearchHospitalRecord> result) {
                mView.onLoadSuccess(result.getData());
            }

            @Override
            public void onFail(HttpResult<SearchHospitalRecord> result) {
//                showToast(result.getMsg());
                mView.onLoadFail();
            }
        });
    }
}
