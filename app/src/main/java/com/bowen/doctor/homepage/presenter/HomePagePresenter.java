package com.bowen.doctor.homepage.presenter;

import android.app.Activity;
import android.content.Context;

import com.bowen.commonlib.base.BasePresenter;
import com.bowen.commonlib.dialog.AffirmDialog;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.DataCacheUtil;
import com.bowen.commonlib.utils.EmptyUtils;
import com.bowen.commonlib.utils.RouterActivityUtil;
import com.bowen.doctor.common.bean.network.ConsultItem;
import com.bowen.doctor.common.bean.network.DoctorInfo;
import com.bowen.doctor.common.util.LoginStatusUtil;
import com.bowen.doctor.homepage.activity.ServiceSetActivity;
import com.bowen.doctor.login.activity.LoginActivity;
import com.bowen.doctor.mine.activity.QualityCertificateActivity;
import com.bowen.doctor.mine.contract.HomePageContract;
import com.bowen.doctor.mine.model.MineModel;

import java.util.List;

/**
 * Created by zzp on 2017/5/21.
 * “首页”数据提供类
 */
public class HomePagePresenter extends BasePresenter {

    private MineModel mineModel;
    private HomePageContract.View mView;

    public HomePagePresenter(Context context, HomePageContract.View view) {
        super(context);
        mineModel = new MineModel(context);
        mContext = context;
        mView = view;
    }

    public void getDoctorInfo() {
        mineModel.getDoctorInfo(new HttpTaskCallBack<DoctorInfo>() {
            @Override
            public void onSuccess(HttpResult<DoctorInfo> result) {
                mView.onLoadSuccess(result.getData());
            }

            @Override
            public void onFail(HttpResult<DoctorInfo> result) {
//                showToast(result.getMsg());
                mView.onLoadFail();
            }
        });
    }

    //待处理资讯列表
    public void getConsult() {//int index, int pageSize
        mineModel.getWaitReplyConsult(new HttpTaskCallBack<List<ConsultItem>>() {//index, pageSize,
            @Override
            public void onSuccess(HttpResult<List<ConsultItem>> result) {
                mView.loadConsultSuccess(result.getData());
            }

            @Override
            public void onFail(HttpResult<List<ConsultItem>> result) {
//                showToast(result.getMsg());
                mView.loadConsultFail();
            }
        });
    }

    public boolean hasNotLogin() {
        if (LoginStatusUtil.getInstance().isLogin()) {
            return false;
        } else {
            if (EmptyUtils.isNotEmpty(DataCacheUtil.getInstance().getString(DataCacheUtil.LOGIN_TOKEN, ""))) {
                RouterActivityUtil.startActivity((Activity) mContext, LoginActivity.class);
            }
            return true;
        }
    }

    public boolean hasQualityCertificate(){
        if(DoctorInfo.getInstance().getIdentify() == 3 || DoctorInfo.getInstance().getIdentify() == 4 && DoctorInfo.getInstance().getIdentify() != 0){//3：未认证(包含已驳回) 4：待审核 5：已认证
            String content,okStr;
            if(DoctorInfo.getInstance().getIdentify()==3){
                content = "您还没有完成资质认证，现在去认证？";
                okStr = "去认证";
            }else{
                content = "您提交的资质认证待审核，前往查看？";
                okStr = "去查看";
            }
            AffirmDialog dialog = new AffirmDialog(mContext, "提醒",
                    content, "以后再说", okStr);
            dialog.setAffirmDialogListenner(new AffirmDialog.AffirmDialogListenner() {
                @Override
                public void onCancle() {
                }
                @Override
                public void onOK() {
                    RouterActivityUtil.startActivity((Activity) mContext, QualityCertificateActivity.class);
                }
            });
            dialog.show();
            return true;
        }else{
            return false;
        }
    }

    //判断没有设置过图文咨询和门诊预约服务，已认证，则弹出设置的界面
    public void showSetDialog(){
        AffirmDialog dialog = new AffirmDialog(mContext, "提醒",
                "您还没有开通服务，现在去设置？", "不了", "去设置");
        dialog.setAffirmDialogListenner(new AffirmDialog.AffirmDialogListenner() {
            @Override
            public void onCancle() {
            }
            @Override
            public void onOK() {
                RouterActivityUtil.startActivity((Activity) mContext, ServiceSetActivity.class);
            }
        });
        dialog.show();
    }
}
