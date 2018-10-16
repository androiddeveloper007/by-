package com.bowen.doctor.mine.presenter;

import android.content.Context;

import com.bowen.commonlib.base.BasePresenter;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.EmptyUtils;
import com.bowen.doctor.common.bean.network.HospitalDetailInfo;
import com.bowen.doctor.common.bean.network.PhotoFile;
import com.bowen.doctor.mine.contract.HospitalDetailContract;
import com.bowen.doctor.mine.model.HospitalDetailModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe:根据省市区搜索医馆分页(用户端)
 * Created by zhuzhipeng on 2018/7/9.
 */
public class HospitalDetailPresenter extends BasePresenter {
    private HospitalDetailModel hospitalDetailModel;
    private HospitalDetailContract.View mView;

    public HospitalDetailPresenter(Context mContext, HospitalDetailContract.View view) {
        super(mContext);
        hospitalDetailModel = new HospitalDetailModel(mContext);
        mView = view;
    }

    public void getHospitalDetail(String hospitalId, int page, int pageSize) {
        hospitalDetailModel.getHospitalDetail(hospitalId, page, pageSize, new HttpTaskCallBack<HospitalDetailInfo>() {
            @Override
            public void onSuccess(HttpResult<HospitalDetailInfo> result) {
                mView.onLoadSuccess(result.getData());
            }

            @Override
            public void onFail(HttpResult<HospitalDetailInfo> result) {
               mView.onLoadFail(result.getData());
            }
        });
    }
    public List<PhotoFile> transfromPhotos(String photos){
        ArrayList<PhotoFile> photoFiles = new ArrayList<>();
        if(EmptyUtils.isNotEmpty(photos)){
            PhotoFile photoFile = new PhotoFile();
            photoFile.setFileLink(photos);
            photoFiles.add(photoFile);
            return photoFiles;
        }
        return photoFiles;
    }
}
