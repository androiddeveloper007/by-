package com.bowen.doctor.mine.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.bowen.commonlib.base.BasePresenter;
import com.bowen.commonlib.dialog.LoadingDialog;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.BitmapUtil;
import com.bowen.commonlib.utils.FileUtil;
import com.bowen.commonlib.utils.ToastUtil;
import com.bowen.doctor.common.bean.network.DoctorUploadPhoto;
import com.bowen.doctor.common.bean.network.PhotoFile;
import com.bowen.doctor.common.model.Constants;
import com.bowen.doctor.mine.contract.QualityCertificateContract;
import com.bowen.doctor.mine.model.QualityCertificateModel;
import com.bowen.doctor.main.model.DataUploadModel;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 完善资料数据提供类
 * Created by zhuzhipeng on 2018/7/17.
 */

public class QualityCertificatePresenter extends BasePresenter {
    private DataUploadModel mDataUploadModel;
    private QualityCertificateModel qualityCertificateModel;
    private QualityCertificateContract.View mView;
    private int mapIndex = 0;
    private SoftReference<LoadingDialog> mLoadingDialog;

    public QualityCertificatePresenter(Context mContext, QualityCertificateContract.View view) {
        super(mContext);
        mDataUploadModel = new DataUploadModel(mContext);
        qualityCertificateModel = new QualityCertificateModel(mContext);
        mView = view;
    }

    //压缩图片
    public void uploadQualityCertification(final ArrayList<String> pics1, final ArrayList<String> pics2, final ArrayList<String> pics3
            , final ArrayList<String> pics4, final ArrayList<String> pics5) {
        Map<String, ArrayList<String>> map = new HashMap<>();
        map.put("pics1", pics1);
        map.put("pics2", pics2);
        map.put("pics3", pics3);
        map.put("pics4", pics4);
        map.put("pics5", pics5);
        Observable.just(map).map(new Func1<Map<String,ArrayList<String>>, Map<String,ArrayList<String>>>() {
            @Override
            public Map<String,ArrayList<String>> call(Map<String,ArrayList<String>> map0) {
                Map<String,ArrayList<String>> newMap = new HashMap<>();
                for (HashMap.Entry<String, ArrayList<String>> entry : map0.entrySet()) {
                    String tempPath = FileUtil.getSavePicPath("compress").getPath();
                    ArrayList<String> list = new ArrayList<>();
                    list.add(compressBitmap(entry.getValue().get(0),tempPath));
                    newMap.put(entry.getKey(), list);
                }
                return newMap;
            }
        }).subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Map<String,ArrayList<String>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Map<String,ArrayList<String>> list) {
                        if (list.size() > 0) {
                            uploadQualityCertificationReal(list.get("pics1"), list.get("pics2"),
                                    list.get("pics3"), list.get("pics4"), list.get("pics5"));
                        }
                    }
                });
    }

    public void uploadQualityCertificationReal(ArrayList<String> pics1, final ArrayList<String> pics2, final ArrayList<String> pics3
            , final ArrayList<String> pics4, final ArrayList<String> pics5) {
        mLoadingDialog = new SoftReference<>(new LoadingDialog(mContext));
        mLoadingDialog.get().setContentStr("数据上传中");
        mLoadingDialog.get().show();
        mDataUploadModel.uploadPhoto(11, filterList(pics1), new HttpTaskCallBack<List<PhotoFile>>() {
            @Override
            public void onSuccess(HttpResult<List<PhotoFile>> result) {
                mDataUploadModel.uploadPhoto(12, filterList(pics2), new HttpTaskCallBack<List<PhotoFile>>() {
                    @Override
                    public void onSuccess(HttpResult<List<PhotoFile>> result) {
                        mDataUploadModel.uploadPhoto(13, filterList(pics3), new HttpTaskCallBack<List<PhotoFile>>() {
                            @Override
                            public void onSuccess(HttpResult<List<PhotoFile>> result) {
                                mDataUploadModel.uploadPhoto(14, filterList(pics4), new HttpTaskCallBack<List<PhotoFile>>() {
                                    @Override
                                    public void onSuccess(HttpResult<List<PhotoFile>> result) {
                                        if (pics5.size() == 1 && TextUtils.equals("拍照", pics5.get(0))) {
                                            mView.onUploadSuccess();
                                            if (mLoadingDialog != null && mLoadingDialog.get() != null)
                                                mLoadingDialog.get().dismiss();
                                            return;
                                        }
                                        mDataUploadModel.uploadPhoto(15, filterList(pics5), new HttpTaskCallBack<List<PhotoFile>>() {
                                            @Override
                                            public void onSuccess(HttpResult<List<PhotoFile>> result) {
                                                mView.onUploadSuccess();
                                                if (mLoadingDialog != null && mLoadingDialog.get() != null)
                                                    mLoadingDialog.get().dismiss();
                                            }

                                            @Override
                                            public void onFail(HttpResult<List<PhotoFile>> result) {
                                                mLoadingDialog.get().dismiss();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onFail(HttpResult<List<PhotoFile>> result) {
                                        mLoadingDialog.get().dismiss();
                                    }
                                });
                            }

                            @Override
                            public void onFail(HttpResult<List<PhotoFile>> result) {
                                mLoadingDialog.get().dismiss();
                            }
                        });
                    }

                    @Override
                    public void onFail(HttpResult<List<PhotoFile>> result) {
                        mLoadingDialog.get().dismiss();
                    }
                });
            }

            @Override
            public void onFail(HttpResult<List<PhotoFile>> result) {
                mLoadingDialog.get().dismiss();
            }
        });
    }


    //压缩图片
    public boolean updateQualityCertification(ArrayList<String> pics1, final ArrayList<String> pics2, final ArrayList<String> pics3
            , final ArrayList<String> pics4, final ArrayList<String> pics5) {
        final Map<String, ArrayList<String>> allList = new LinkedHashMap<>();
        if (!pics5.get(0).contains("http") && !pics5.get(0).contains("拍照")) {
            allList.put("15",pics5);
        }
        if (!pics4.get(0).contains("http")) {
            allList.put( "14",pics4);
        }
        if (!pics3.get(0).contains("http")) {
            allList.put("13",pics3);
        }
        if (!pics2.get(0).contains("http")) {
            allList.put( "12",pics2);
        }
        if (!pics1.get(0).contains("http")) {
            allList.put( "11",pics1);
        }
        if (allList.size() == 0) {
            return true;
        }
        Observable.just(allList).map(new Func1<Map<String, ArrayList<String>>, Map<String, ArrayList<String>>>() {
            @Override
            public Map<String, ArrayList<String>> call(Map<String,ArrayList<String>> map0) {
                Map<String, ArrayList<String>> newMap = new LinkedHashMap<>();
                for (HashMap.Entry<String, ArrayList<String>> entry : map0.entrySet()) {
                    String tempPath = FileUtil.getSavePicPath("compress").getPath();
                    ArrayList<String> list = new ArrayList<>();
                    list.add(compressBitmap(entry.getValue().get(0),tempPath));
                    newMap.put(entry.getKey(),list);
                }
                return newMap;
            }
        }).subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Map<String, ArrayList<String>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Map<String, ArrayList<String>> list) {
                        if (list.size() > 0) {
                            updateQualityCertificationReal(list.get("pics1"), list.get("pics2"),
                                    list.get("pics3"), list.get("pics4"), list.get("pics5"));
                        }
                    }
                });
        return false;
    }

    /**
     * 修改审核图片
     */
    public boolean updateQualityCertificationReal(ArrayList<String> pics1, final ArrayList<String> pics2, final ArrayList<String> pics3
            , final ArrayList<String> pics4, final ArrayList<String> pics5) {
        final Map<ArrayList<String>, String> allList = new LinkedHashMap<>();
        if (!pics5.get(0).contains("http") && !pics5.get(0).contains("拍照")) {
            allList.put(pics5, "15");
        }
        if (!pics4.get(0).contains("http")) {
            allList.put(pics4, "14");
        }
        if (!pics3.get(0).contains("http")) {
            allList.put(pics3, "13");
        }
        if (!pics2.get(0).contains("http")) {
            allList.put(pics2, "12");
        }
        if (!pics1.get(0).contains("http")) {
            allList.put(pics1, "11");
        }
        if (allList.size() == 0) {
            return true;
        }
        mapIndex = 0;
        mLoadingDialog = new SoftReference<>(new LoadingDialog(mContext));
        mLoadingDialog.get().setContentStr("数据上传中");
        mLoadingDialog.get().show();
        final Iterator it = allList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entity = (Map.Entry) it.next();
            mDataUploadModel.uploadPhoto(Integer.parseInt((String) entity.getValue()), (ArrayList<String>) entity.getKey(), new HttpTaskCallBack<List<PhotoFile>>() {
                @Override
                public void onSuccess(HttpResult<List<PhotoFile>> result) {
                    mapIndex++;
                    if (allList.size() == 1) {
                        mView.onUploadSuccess();
                        if (mLoadingDialog != null && mLoadingDialog.get() != null)
                            mLoadingDialog.get().dismiss();
                    } else {
                        if (mapIndex == allList.size() - 1) {
                            mView.onUploadSuccess();
                            if (mLoadingDialog != null && mLoadingDialog.get() != null)
                                mLoadingDialog.get().dismiss();
                        }
                    }
                }

                @Override
                public void onFail(HttpResult<List<PhotoFile>> result) {
                    if (mLoadingDialog != null && mLoadingDialog.get() != null)
                        mLoadingDialog.get().dismiss();
                }
            });
        }
        return false;
    }

    public void getDoctorFile() {
        qualityCertificateModel.getDoctorFile(new HttpTaskCallBack<List<DoctorUploadPhoto>>() {
            @Override
            public void onSuccess(HttpResult<List<DoctorUploadPhoto>> result) {
                mView.loadSuccess(result.getData());
            }

            @Override
            public void onFail(HttpResult<List<DoctorUploadPhoto>> result) {
//                showToast(result.getMsg());
                mView.loadFail();
            }
        });
    }

    //提交数据时，将列表中没用的拍照删掉
    public ArrayList<String> filterList(ArrayList<String> list) {
        ArrayList<String> temp = new ArrayList<>();
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals("拍照")) {
                iterator.remove();
            }
        }
        temp.addAll(list);
        return temp;
    }

    private String compressBitmap(String fromPath, String outPath) {
        if (FileUtil.getFileSize(new File(fromPath)) > 1.5 * 1024 * 1024) {//大于1.5M就进行压缩
            try {
                BitmapUtil.compressByResolution(fromPath, outPath, Constants.DEFAULT_PHOTO_WIDTH, Constants.DEFAULT_PHOTO_HEIGHT, false);
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
