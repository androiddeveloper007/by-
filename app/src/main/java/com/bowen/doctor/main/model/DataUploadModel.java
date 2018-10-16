package com.bowen.doctor.main.model;

import android.content.Context;

import com.bowen.commonlib.base.BaseModel;
import com.bowen.commonlib.dialog.LoadingDialog;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.BitmapUtil;
import com.bowen.commonlib.utils.EmptyUtils;
import com.bowen.commonlib.utils.FileUtil;
import com.bowen.commonlib.utils.GsonUtil;
import com.bowen.commonlib.utils.LogUtil;
import com.bowen.doctor.BowenApplication;
import com.bowen.doctor.common.bean.network.PhotoFile;
import com.bowen.doctor.common.http.HttpContants;
import com.bowen.doctor.common.http.NetworkRequest;
import com.bowen.doctor.common.model.Constants;
import com.bowen.doctor.common.model.UserInfo;
import com.bowen.doctor.common.util.ChooseTypeUtil;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Describe:
 * Created by AwenZeng on 2018/4/2.
 */

public class DataUploadModel extends BaseModel {

    public DataUploadModel(Context mContext) {
        super(mContext);
    }

    /**
     * 上传头像(业务，资料上传)
     *
     * @param bizId
     * @param fileCode
     */
    public void uploadPhoto(String bizId, int fileCode, final ArrayList<String> pics, final HttpTaskCallBack<List<PhotoFile>> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("fileCode", fileCode);
        networkRequest.setParam("bizId", bizId);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.uploadPhotos(HttpContants.CMD_UPLOAD_PHOTO, "", pics, new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                HttpResult<List<PhotoFile>> result = new HttpResult<List<PhotoFile>>();
                result.copy(httpResult);
                try {
                    JSONObject jsonObject = new JSONObject(GsonUtil.toJson(httpResult.getData()));
                    Type typelist = new TypeToken<List<PhotoFile>>() {
                    }.getType();
                    List<PhotoFile> list = GsonUtil.getGson().fromJson(jsonObject.getString("comFileInfoList"), typelist);
                    if (EmptyUtils.isNotEmpty(list)) {
                        result.setData(list);
                    }
                    if (mListener != null) {
                        mListener.onSuccess(result);
                    }
                    FileUtil.deleteFiles(pics);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                HttpResult<List<PhotoFile>> result = new HttpResult<List<PhotoFile>>();
                result.copy(httpResult);
                if (mListener != null) {
                    mListener.onFail(result);
                }
            }
        });
    }

    public void uploadPhoto(int fileCode, final ArrayList<String> pics, final HttpTaskCallBack<List<PhotoFile>> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("fileCode", fileCode);
        networkRequest.setParam("bizId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("uploadTime", System.currentTimeMillis());
        networkRequest.uploadPhotos(HttpContants.CMD_UPLOAD_PHOTO, "", pics, new HttpTaskCallBack<Object>() {//图片上传中,请稍后...
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                HttpResult<List<PhotoFile>> result = new HttpResult<List<PhotoFile>>();
                result.copy(httpResult);
                try {
                    JSONObject jsonObject = new JSONObject(GsonUtil.toJson(httpResult.getData()));
                    Type typelist = new TypeToken<List<PhotoFile>>() {
                    }.getType();
                    List<PhotoFile> list = GsonUtil.getGson().fromJson(jsonObject.getString("comFileInfoList"), typelist);
                    if (EmptyUtils.isNotEmpty(list)) {
                        result.setData(list);
                    }
                    if (mListener != null) {
                        mListener.onSuccess(result);
                    }
                    FileUtil.deleteFiles(pics);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                HttpResult<List<PhotoFile>> result = new HttpResult<List<PhotoFile>>();
                result.copy(httpResult);
                if (mListener != null) {
                    mListener.onFail(result);
                }
            }
        });
    }

    /**
     * 添加家庭成员，并上传头像
     */
    public void addFamilyMember(String familyType, String familyNickname, int familyFileType,
                                final ArrayList<String> pics, final HttpTaskCallBack mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("familyFileType", familyFileType);
        networkRequest.setParam("familyNickname", familyNickname);
        networkRequest.setParam("familyType", familyType);
        networkRequest.uploadPhotos(HttpContants.CMD_SAVE_USER_FAMILY_INFO, "数据上传中，请稍后...", pics, new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                if (mListener != null) {
                    mListener.onSuccess(httpResult);
                }
                FileUtil.deleteFiles(pics);

            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                if (mListener != null) {
                    mListener.onFail(httpResult);
                }
            }
        });
    }

    /**
     * 提交反馈
     */
    public void submitFeedBack(String feedbackContent, final ArrayList<String> pics, final HttpTaskCallBack mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("suggestion", feedbackContent);
        networkRequest.uploadPhotos(HttpContants.CMD_SUBMIT_FEEDBACK, "数据上传中，请稍后...", pics, new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                if (mListener != null) {
                    mListener.onSuccess(httpResult);
                }
                FileUtil.deleteFiles(pics);

            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                if (mListener != null) {
                    mListener.onFail(httpResult);
                }
            }
        });
    }



    public void uploadCompressPhoto(final String bizId, final int fileCode, List<String> pics, final HttpTaskCallBack<List<PhotoFile>> mListener) {
        final LoadingDialog mLoadingDialog = new LoadingDialog(mContext);
        Observable.just(pics).map(new Func1<List<String>, ArrayList<String>>() {
            @Override
            public ArrayList<String> call(List<String> photoList) {
                ArrayList<String> imgList = new ArrayList<String>();
                for (String path : photoList) {
                    String tempPath = FileUtil.getSavePicPath("compress").getPath();
                    imgList.add(compressBitmap(path, tempPath));
                }
                return imgList;
            }
        }).subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mLoadingDialog.setContentStr("正在压缩图片，请稍后...");
                        mLoadingDialog.show();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<String>>() {
                    @Override
                    public void onCompleted() {
                        mLoadingDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<String> list) {
                        if (list.size() > 0) {
                            uploadPhoto(bizId, fileCode, list, mListener);
                        }
                    }
                });
    }


    public void uploadFamilyMemberData(final String familyType, final String familyNickname, final int familyFileType,
                                    final ArrayList<String> pics, final HttpTaskCallBack mListener) {
        final LoadingDialog mLoadingDialog = new LoadingDialog(mContext);
        Observable.just(pics).map(new Func1<List<String>, ArrayList<String>>() {
            @Override
            public ArrayList<String> call(List<String> photoList) {
                ArrayList<String> imgList = new ArrayList<String>();
                for (String path : photoList) {
                    String tempPath = FileUtil.getSavePicPath("compress").getPath();
                    imgList.add(compressBitmap(path, tempPath));
                }
                return imgList;
            }
        }).subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mLoadingDialog.setContentStr("正在压缩图片，请稍后...");
                        mLoadingDialog.show();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<String>>() {
                    @Override
                    public void onCompleted() {
                        mLoadingDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<String> list) {
                        if (list.size() > 0) {
                            addFamilyMember(familyType, familyNickname, familyFileType, list, mListener);
                        }
                    }
                });
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

    public void uploadIconAndSaveDoctorInfo(final Map<String,String> params, ArrayList<String> pics, final HttpTaskCallBack mListener) {
        if(pics.size()>0){
            Observable.just(pics).map(new Func1<List<String>, ArrayList<String>>() {
                @Override
                public ArrayList<String> call(List<String> photoList) {
                    ArrayList<String> imgList = new ArrayList<>();
                    ArrayList<String> picLIst = ChooseTypeUtil.filterPhotoList(photoList);
                    for (String path : picLIst) {
                        String tempPath = FileUtil.getSavePicPath("compress").getPath();
                        imgList.add(compressBitmap(path, tempPath));
                    }
                    return imgList;
                }
            }).subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                        }
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ArrayList<String>>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(ArrayList<String> list) {
                            if (list.size() > 0) {
                                saveDoctorInfo(params, list, mListener);
                            }
                        }
                    });
        }else {
            saveDoctorInfo(params, pics, mListener);
        }
    }

    public void saveDoctorInfoReal(Map<String,String> params,final ArrayList<String> pics, final HttpTaskCallBack mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        for (HashMap.Entry<String, String> entry : params.entrySet()) {
            networkRequest.setParam(entry.getKey(), entry.getValue().toString());
        }
        if(pics.size()>0 && !pics.get(0).contains("http")){
            networkRequest.uploadPhotos(HttpContants.CMD_SAVE_DOCTOR_INFO, "数据上传中，请稍后...", pics, new HttpTaskCallBack<Object>() {
                @Override
                public void onSuccess(HttpResult<Object> httpResult) {
                    if (mListener != null) {
                        mListener.onSuccess(httpResult);
                    }
                    FileUtil.deleteFiles(pics);
                }

                @Override
                public void onFail(HttpResult<Object> httpResult) {
                    if (mListener != null) {
                        mListener.onFail(httpResult);
                    }
                }
            });
        } else {
            networkRequest.requestSRV(HttpContants.CMD_SAVE_DOCTOR_INFO, "数据上传中，请稍后...", new HttpTaskCallBack<Object>() {
                @Override
                public void onSuccess(HttpResult<Object> httpResult) {
                    if (mListener != null) {
                        mListener.onSuccess(httpResult);
                    }
                    FileUtil.deleteFiles(pics);
                }

                @Override
                public void onFail(HttpResult<Object> httpResult) {
                    if (mListener != null) {
                        mListener.onFail(httpResult);
                    }
                }
            });
        }
    }

    public void saveDoctorInfo(final Map<String,String> params, final ArrayList<String> pics, final HttpTaskCallBack mListener) {
        Observable.just(pics).map(new Func1<ArrayList<String>, ArrayList<String>>() {
            @Override
            public ArrayList<String> call(ArrayList<String> photoList) {
                ArrayList<String> imgList = new ArrayList<String>();
                for (String path : photoList) {
                    String tempPath = FileUtil.getSavePicPath("compress").getPath();
                    imgList.add(compressBitmap(path, tempPath));
                }
                return imgList;
            }
        }).subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<String>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(ArrayList<String> list) {
//                        if (list.size() > 0) {
//                        }
                        saveDoctorInfoReal(params, list, mListener);
                    }
                });
    }

    public void deletePhoto(String fileId, final HttpTaskCallBack mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("fileId", fileId);
        networkRequest.requestSRV(HttpContants.CMD_DELETE_PHOTO, "", new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                if (mListener != null) {
                    mListener.onSuccess(httpResult);
                }

            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                if (mListener != null) {
                    mListener.onFail(httpResult);
                }
            }
        });
    }


}
