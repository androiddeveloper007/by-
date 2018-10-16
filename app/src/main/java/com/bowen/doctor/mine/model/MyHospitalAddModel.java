package com.bowen.doctor.mine.model;

import android.content.Context;

import com.bowen.commonlib.base.BaseModel;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.BitmapUtil;
import com.bowen.commonlib.utils.FileUtil;
import com.bowen.commonlib.utils.LogUtil;
import com.bowen.doctor.BowenApplication;
import com.bowen.doctor.common.http.HttpContants;
import com.bowen.doctor.common.http.NetworkRequest;
import com.bowen.doctor.common.model.Constants;
import com.bowen.doctor.common.model.UserInfo;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Describe:
 * Created by zzp on 2018/5/15.
 */

public class MyHospitalAddModel extends BaseModel {

    public MyHospitalAddModel(Context mContext) {
        super(mContext);
    }

    public void uploadData(final Map<String, String> params, final Map<String, String> pictureParams, final HttpTaskCallBack<Object> mListener) {
        Observable.just(pictureParams).map(new Func1<Map<String, String>, Map<String, String>>() {
            @Override
            public Map<String, String> call(Map<String, String> photoList) {
                Map<String, String> newMap = new HashMap<>();
                for (HashMap.Entry<String, String> entry : params.entrySet()) {
                    String tempPath = FileUtil.getSavePicPath("compress").getPath();
                    newMap.put(entry.getKey(), compressBitmap(entry.getValue(),tempPath));
                }
                return photoList;
            }
        }).subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Map<String, String>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Map<String, String> list) {
                        if (list.size() > 0) {
                            uploadDataReal(params, list, mListener);
                        }
                    }
                });
    }

    public void uploadDataReal(final Map<String, String> params, final Map<String, String> pictureParams, final HttpTaskCallBack<Object> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        for (HashMap.Entry<String, String> entry : params.entrySet()) {
            networkRequest.setParam(entry.getKey(), entry.getValue());
        }
        networkRequest.uploadPhotos(HttpContants.CMD_SAVE_EMR_HOSPITAL, "正在提交数据，请稍候。", pictureParams, new HttpTaskCallBack<Object>() {//图片上传中,请稍后...
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                HttpResult<Object> result = new HttpResult<>();
                result.copy(httpResult);
                if (mListener != null) {
                    mListener.onSuccess(result);
                }
            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                HttpResult<Object> result = new HttpResult<>();
                result.copy(httpResult);
                if (mListener != null) {
                    mListener.onFail(result);
                }
            }
        });
    }

    public void updateEmrHospital(final Map<String, String> params, final Map<String, String> pictureParams, final HttpTaskCallBack<Object> mListener) {
        Observable.just(pictureParams).map(new Func1<Map<String, String>, Map<String, String>>() {
            @Override
            public Map<String, String> call(Map<String, String> photoList) {
                Map<String, String> newMap = new HashMap<String, String>();
                for (HashMap.Entry<String, String> entry : params.entrySet()) {
                    String tempPath = FileUtil.getSavePicPath("compress").getPath();
                    newMap.put(entry.getKey(), compressBitmap(entry.getValue(),tempPath));
                }
                return photoList;
            }
        }).subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Map<String, String>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Map<String, String> list) {
                        if (list.size() > 0) {
                            updateEmrHospitalReal(params, list, mListener);
                        }
                    }
                });
    }

    public void updateEmrHospitalReal(final Map<String, String> params, final Map<String, String> pictureParams, final HttpTaskCallBack<Object> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        for (HashMap.Entry<String, String> entry : params.entrySet()) {
            networkRequest.setParam(entry.getKey(), entry.getValue().toString());
        }
        Iterator<String> iter = pictureParams.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            if (pictureParams.get(key).contains("http"))
                iter.remove();
        }
        if (pictureParams.size() == 0) {
            networkRequest.requestSRV(HttpContants.CMD_UPDATE_EMR_HOSPITAL, "正在提交数据，请稍候。", new HttpTaskCallBack<Object>() {
                @Override
                public void onSuccess(HttpResult<Object> httpResult) {
                    HttpResult<Object> result = new HttpResult<>();
                    result.copy(httpResult);
                    if (mListener != null) {
                        mListener.onSuccess(result);
                    }
                }

                @Override
                public void onFail(HttpResult<Object> httpResult) {
                    HttpResult<Object> result = new HttpResult<>();
                    result.copy(httpResult);
                    if (mListener != null) {
                        mListener.onFail(result);
                    }
                }
            });
        } else {
            networkRequest.uploadPhotos(HttpContants.CMD_UPDATE_EMR_HOSPITAL, "正在提交数据，请稍候。", pictureParams, new HttpTaskCallBack<Object>() {
                @Override
                public void onSuccess(HttpResult<Object> httpResult) {
                    HttpResult<Object> result = new HttpResult<>();
                    result.copy(httpResult);
                    if (mListener != null) {
                        mListener.onSuccess(result);
                    }
                }

                @Override
                public void onFail(HttpResult<Object> httpResult) {
                    HttpResult<Object> result = new HttpResult<>();
                    result.copy(httpResult);
                    if (mListener != null) {
                        mListener.onFail(result);
                    }
                }
            });
        }
    }

    private String compressBitmap(String fromPath, String outPath) {
        if (FileUtil.getFileSize(new File(fromPath)) > 1.5 * 1024 * 1024) {//大于1.5M就进行压缩
            try {
                BitmapUtil.compressByResolution(fromPath, outPath, Constants.DEFAULT_PHOTO_WIDTH, Constants.DEFAULT_PHOTO_HEIGHT, false);
                if (BowenApplication.DEBUG) {
                    LogUtil.show("图片压缩大小：" + FileUtil.getFileSize(new File(outPath)) / 1024 + "k");
                }
                outPath = compressBitmap(outPath, outPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            outPath = fromPath;
        }
        return outPath;
    }
}
