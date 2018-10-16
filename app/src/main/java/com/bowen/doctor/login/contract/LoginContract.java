package com.bowen.doctor.login.contract;


import com.bowen.doctor.common.bean.network.Login;

/**
 * Created by AwenZeng on 2017/5/27.
 */

public interface LoginContract {
    interface View {
        void onLoginSuccess(Login result);
        void onGetAuthCodeFailed();
        void onCheckAccountSuccess(boolean isRegist);
    }

    interface Presenter{
        void login(int loginType, String phone, String password);
        void getAuthCode(String phone, int codeType, int businessType);
        void checkAccount(String account, String checkType);
    }
}
