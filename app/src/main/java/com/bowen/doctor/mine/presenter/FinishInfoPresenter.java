package com.bowen.doctor.mine.presenter;

import android.content.Context;

import com.bowen.commonlib.base.BasePresenter;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.doctor.common.bean.network.Department;
import com.bowen.doctor.common.bean.network.DoctorInfoRecord;
import com.bowen.doctor.main.model.DataUploadModel;
import com.bowen.doctor.mine.contract.FinishInfoContract;
import com.bowen.doctor.mine.model.FinishInfoModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 完善资料数据提供类
 * Created by zhuzhipeng on 2018/7/17.
 */

public class FinishInfoPresenter extends BasePresenter {
    private FinishInfoModel finishInfoModel;
    private DataUploadModel dataUploadModel;
    private FinishInfoContract.View mView;
    public FinishInfoPresenter(Context mContext, FinishInfoContract.View view) {
        super(mContext);
        finishInfoModel = new FinishInfoModel(mContext);
        dataUploadModel = new DataUploadModel(mContext);
        mView = view;
    }

    public void uploadIconAndSaveDoctorInfo(final Map<String,String> params,ArrayList<String> feedbackPics) {
        dataUploadModel.saveDoctorInfo(params, filterList(feedbackPics), new HttpTaskCallBack() {
            @Override
            public void onSuccess(HttpResult result) {
                mView.onUploadSuccess();
            }
            @Override
            public void onFail(HttpResult result) {
//                showToast(result.getMsg());
            }
        });
    }

    //加载提交过的数据
    public void getDoctorInfo() {
        finishInfoModel.getDoctorInfo(new HttpTaskCallBack<DoctorInfoRecord>() {
            @Override
            public void onSuccess(HttpResult<DoctorInfoRecord> result) {
                mView.loadDataSuccess(result.getData());
            }

            @Override
            public void onFail(HttpResult<DoctorInfoRecord> result) {
//                showToast(result.getMsg());
                mView.loadFailed();
            }
        });
    }

    //加载科室列表
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

    //提交数据时，将列表中没用的拍照删掉
    public ArrayList<String> filterList(ArrayList<String> list){
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
}
