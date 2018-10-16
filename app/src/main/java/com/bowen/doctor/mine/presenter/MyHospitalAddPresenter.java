package com.bowen.doctor.mine.presenter;

import android.content.Context;

import com.bowen.commonlib.base.BasePresenter;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.ToastUtil;
import com.bowen.doctor.common.bean.network.Department;
import com.bowen.doctor.mine.model.FinishInfoModel;
import com.bowen.doctor.mine.contract.MyHospitalAddContract;
import com.bowen.doctor.mine.model.MyHospitalAddModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by ZP-PC on 2018/7/19.
 */

public class MyHospitalAddPresenter extends BasePresenter {
    private FinishInfoModel finishInfoModel;
    private MyHospitalAddModel myHospitalAddModel;
    private MyHospitalAddContract.View mView;
    public MyHospitalAddPresenter(Context mContext, MyHospitalAddContract.View view) {
        super(mContext);
        finishInfoModel = new FinishInfoModel(mContext);
        myHospitalAddModel = new MyHospitalAddModel(mContext);
        mView = view;
    }
    public ArrayList<String> handleList(ArrayList<String> list){
        ArrayList<String> temp = new ArrayList<>();
        Iterator<String> iterator = list.iterator();
        while(iterator.hasNext()){
            if(iterator.next().equals("拍照")){
                iterator.remove();
            }
        }
        temp.addAll(list);
        return temp;
    }

    public void loadDepartmentList() {
        finishInfoModel.loadDepartmentList(new HttpTaskCallBack<List<Department>>() {
            @Override
            public void onSuccess(HttpResult<List<Department>> result) {
                mView.loadDepartmentListSuccess(result.getData());
            }

            @Override
            public void onFail(HttpResult<List<Department>> result) {
//                showToast(result.getMsg());
            }
        });
    }

    public void uploadData(Map<String,String> params, Map<String,String> pictureParams) {
        myHospitalAddModel.uploadData(params, pictureParams, new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> result) {
                mView.onUploadSuccess();
            }

            @Override
            public void onFail(HttpResult<Object> result) {
//                showToast(result.getMsg());
                mView.onUploadFailed();
            }
        });
    }

    public void updateEmrHospital(Map<String,String> params, Map<String,String> pictureParams) {
        myHospitalAddModel.updateEmrHospital(params, pictureParams, new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> result) {
                mView.onUploadSuccess();
            }

            @Override
            public void onFail(HttpResult<Object> result) {
//                showToast(result.getMsg());
                mView.onUploadFailed();
            }
        });
    }
}
