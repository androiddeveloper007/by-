package com.bowen.doctor.login.presenter;

import android.content.Context;

import com.bowen.commonlib.base.BasePresenter;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.doctor.common.bean.network.Login;
import com.bowen.doctor.common.util.CheckFieldUtil;
import com.bowen.doctor.login.activity.LoginActivity;
import com.bowen.doctor.login.contract.LoginContract;
import com.bowen.doctor.login.model.LoginModel;
import com.bowen.doctor.login.model.RegistModel;

/**
 * Created by AwenZeng on 2017/5/27.
 */

public class LoginPresenter extends BasePresenter implements LoginContract.Presenter {
    private LoginModel mLoginModel;
    private RegistModel mRegistModel;
    private LoginContract.View mView;

    public LoginPresenter(Context context, LoginContract.View view) {
        super(context);
        mLoginModel = new LoginModel(context);
        mRegistModel = new RegistModel(context);
        mContext = context;
        mView = view;
    }
    /**
     * 检测输入的字段
     *
     * @param phoneNum
     * @param password
     * @return
     */
    public boolean checkContent(int type,String phoneNum, String password) {

        if (!CheckFieldUtil.checkPhoneNum(phoneNum)) {
            return false;
        }
        if(type == LoginActivity.TYPE_AUTHCODE_LOGIN){
            if (!CheckFieldUtil.checkAuthCode(password)) {
                return false;
            }
        }else{
            if (!CheckFieldUtil.checkPassword(password)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void login(int loginType,String phone, String password) {
        if(loginType == LoginActivity.TYPE_AUTHCODE_LOGIN){
            mLoginModel.loginAuthCode(phone, password, new HttpTaskCallBack<Login>() {
                @Override
                public void onSuccess(HttpResult<Login> result) {
                    mView.onLoginSuccess(result.getData());
                }

                @Override
                public void onFail(HttpResult<Login> result) {
                    showToast(result.getMsg());
                }
            });
        }else{
            mLoginModel.login(phone, password, true, new HttpTaskCallBack<Login>() {
                @Override
                public void onSuccess(HttpResult<Login> result) {
                    mView.onLoginSuccess(result.getData());
                }

                @Override
                public void onFail(HttpResult<Login> result) {
                    showToast(result.getMsg());
                }
            });
        }
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
}
