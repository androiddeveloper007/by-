package com.bowen.doctor.login.presenter;

import android.content.Context;

import com.bowen.commonlib.base.BasePresenter;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.doctor.common.bean.network.Login;
import com.bowen.doctor.common.util.CheckFieldUtil;
import com.bowen.doctor.login.contract.RegistContract;
import com.bowen.doctor.login.model.LoginModel;
import com.bowen.doctor.login.model.RegistModel;

/**
 * Created by AwenZeng on 2017/6/1.
 */

public class RegistPresenter extends BasePresenter implements RegistContract.Presenter {

    private RegistModel mRegistModel;
    private LoginModel mLoginModel;
    private RegistContract.View mView;


    public RegistPresenter(Context context, RegistContract.View view) {
        super(context);
        mRegistModel = new RegistModel(mContext);
        mLoginModel = new LoginModel(mContext);
        mView = view;
    }


    /**
     * 检测输入的字段
     *
     * @param phoneNum
     * @param password
     * @return
     */
    public boolean checkContent(String phoneNum, String password, String authCode,boolean isChooseProtocol) {
        if (!CheckFieldUtil.checkPhoneNum(phoneNum)) {
            return false;
        }
        if (!CheckFieldUtil.checkAuthCode(authCode)) {
            return false;
        }
        if (!CheckFieldUtil.checkPassword(password)) {
            return false;
        }

        if(!isChooseProtocol){
            showToast("请选择我已阅读并同意博闻《服务条款》");
            return false;
        }
        return true;
    }

    @Override
    public void checkAccount(String account,String checkType) {
        mRegistModel.checkAccount(account,checkType, new HttpTaskCallBack<Boolean>() {
            @Override
            public void onSuccess(HttpResult<Boolean> result) {
                mView.onCheckAccountSuccess(result.getData());
            }

            @Override
            public void onFail(HttpResult<Boolean> result) {
                showToast(result.getMsg());
            }
        });
    }

    //注册并登陆
    @Override
    public void regist(final String phone, final String password, String authCode) {
       mRegistModel.regist(phone, password, authCode, new HttpTaskCallBack() {
           @Override
           public void onSuccess(HttpResult result) {
               mLoginModel.login(phone, password, false, new HttpTaskCallBack<Login>() {
                   @Override
                   public void onSuccess(HttpResult<Login> result) {
                       mView.onRegistSuccess();
                   }

                   @Override
                   public void onFail(HttpResult<Login> result) {
                       showToast(result.getMsg());
                   }
               });
           }

           @Override
           public void onFail(HttpResult result) {
               showToast(result.getMsg());
           }
       });
    }

    @Override
    public void getAuthCode(String phone, int codeType, int businessType) {
       mRegistModel.getAuthCode(phone, codeType, businessType, new HttpTaskCallBack() {
           @Override
           public void onSuccess(HttpResult result) {
               showToast(result.getMsg());
           }

           @Override
           public void onFail(HttpResult result) {
               showToast(result.getMsg());
               mView.onGetAuthCodeFailed();
           }
       });
    }
}
