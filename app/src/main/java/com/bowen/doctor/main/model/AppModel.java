package com.bowen.doctor.main.model;

import android.content.Context;

import com.bowen.commonlib.base.BaseModel;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.EmptyUtils;
import com.bowen.commonlib.utils.GsonUtil;
import com.bowen.doctor.common.bean.network.ContantsNet;
import com.bowen.doctor.common.http.HttpContants;
import com.bowen.doctor.common.http.NetworkRequest;
import com.bowen.doctor.common.model.AppConfigInfo;

/**
 * Created by AwenZeng on 2017/1/11.
 */

public class AppModel extends BaseModel {


    public AppModel(Context mContext) {
        super(mContext);
    }

    /**
     * 获取应用配置
     */
    public void getAppConfig() {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setCache(true);
        networkRequest.requestSRV(HttpContants.CMD_GET_CONFIG_DATA, new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                ContantsNet contantsNet = GsonUtil.fromJson(GsonUtil.toJson(httpResult.getData()), ContantsNet.class);
                if (EmptyUtils.isNotEmpty(contantsNet)) {
                    AppConfigInfo.getInstance().setWarmWishes(contantsNet.getWarmWishes());
                    AppConfigInfo.getInstance().setLicenseUrl(contantsNet.getLicenseUrl());
                }
            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {

            }
        });
    }

}