package com.bowen.doctor.mine.presenter;

import android.app.Activity;
import android.content.Context;

import com.bowen.commonlib.base.BasePresenter;
import com.bowen.commonlib.dialog.AffirmDialog;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.DataCacheUtil;
import com.bowen.commonlib.utils.EmptyUtils;
import com.bowen.commonlib.utils.RouterActivityUtil;
import com.bowen.doctor.common.bean.network.BindBankCardBean;
import com.bowen.doctor.common.bean.network.DoctorInfo;
import com.bowen.doctor.common.util.LoginStatusUtil;
import com.bowen.doctor.login.activity.LoginActivity;
import com.bowen.doctor.mine.activity.QualityCertificateActivity;
import com.bowen.doctor.mine.contract.MineContract;
import com.bowen.doctor.mine.model.MineModel;

/**
 * Created by zzp on 2017/5/21.
 * “我的”数据提供类
 */
public class MinePresenter extends BasePresenter {

    private MineModel mineModel;
    private MineContract.View mView;

    public MinePresenter(Context context, MineContract.View view) {
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
        if(DoctorInfo.getInstance().getIdentify() == 3
                || DoctorInfo.getInstance().getIdentify() == 4
                && DoctorInfo.getInstance().getIdentify() != 0){//3：未认证(包含已驳回) 4：待审核 5：已认证
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

    public void checkBindCard() {
        mineModel.checkBindCard(new HttpTaskCallBack<BindBankCardBean>() {
            @Override
            public void onSuccess(HttpResult<BindBankCardBean> result) {
                mView.onLoadBindCardSuccess(result.getData());
            }

            @Override
            public void onFail(HttpResult<BindBankCardBean> result) {

            }
        });
    }
}
