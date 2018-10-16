package com.bowen.doctor.login.model;

import android.content.Context;

import com.bowen.commonlib.base.BaseModel;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.DataCacheUtil;
import com.bowen.commonlib.utils.DeviceUtil;
import com.bowen.commonlib.utils.GsonUtil;
import com.bowen.doctor.common.bean.network.Login;
import com.bowen.doctor.common.bean.network.ThirdLogin;
import com.bowen.doctor.common.http.HttpContants;
import com.bowen.doctor.common.http.NetworkRequest;
import com.bowen.doctor.common.model.UserInfo;
import com.bowen.doctor.common.util.LoginStatusUtil;
import com.bowen.doctor.consult.model.chat.ChatModel;
import com.bowen.doctor.consult.model.chat.ChatServerManager;
import com.bowen.doctor.consult.model.chat.ChatServerManager.ChatServerConnectListener;
import com.bowen.doctor.consult.model.chat.ChatServerManager.ChatServerLoginListener;

/**
 * Describe:
 * Created by AwenZeng on 2018/3/19.
 */

public class LoginModel extends BaseModel {


    public LoginModel(Context mContext) {
        super(mContext);
    }


    public void login(final String userAcount, String userPassword, boolean isLoadingStr, final HttpTaskCallBack<Login> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("userMobile", userAcount);
//        networkRequest.setParam("userPassword", RSAUtil.getInstance().encode(userPassword));
        networkRequest.setParam("userPassword", userPassword);
        networkRequest.setParam("phoneMode", DeviceUtil.getModel());
        networkRequest.setParam("phoneName", DeviceUtil.getBrand());
        networkRequest.setParam("userType", "1");//0:普通用户 1: 医生
        networkRequest.requestSRV(HttpContants.CMD_LOGIN, isLoadingStr?"正在登录,请稍后...":"", new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                HttpResult<Login> result = new HttpResult<Login>();
                result.copy(httpResult);
                Login loginBean = GsonUtil.fromJson(GsonUtil.toJson(httpResult.getData()), Login.class);
                if (loginBean != null) {
                    result.setData(loginBean);
                    setLoginData(loginBean);
                }
                if (mListener != null) {
                    mListener.onSuccess(result);
                }
            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                Login loginBean = GsonUtil.fromJson(GsonUtil.toJson(httpResult.getData()), Login.class);
                HttpResult<Login> result = new HttpResult<Login>();
                result.copy(httpResult);
                if (loginBean != null) {
                    result.setData(loginBean);
                    LoginStatusUtil.getInstance().setLoginErroCount(loginBean.getFailCount());
                }
                if (mListener != null) {
                    mListener.onFail(result);
                }
            }
        });
    }


    public void loginAuthCode(final String userAcount, String verifyCode, final HttpTaskCallBack<Login> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("userMobile", userAcount);
        networkRequest.setParam("verifyCode", verifyCode);
        networkRequest.setParam("phoneMode", DeviceUtil.getModel());
        networkRequest.setParam("phoneName", DeviceUtil.getBrand());
        networkRequest.setParam("userType", "1");//0：用户，1：医生
        networkRequest.requestSRV(HttpContants.CMD_AUTHCODE_LOGIN, "正在登录,请稍后...", new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                HttpResult<Login> result = new HttpResult<Login>();
                result.copy(httpResult);
                Login loginBean = GsonUtil.fromJson(GsonUtil.toJson(httpResult.getData()), Login.class);
                if (loginBean != null) {
                    result.setData(loginBean);
                    setLoginData(loginBean);
                }
                if (mListener != null) {
                    mListener.onSuccess(result);
                }
            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                Login loginBean = GsonUtil.fromJson(GsonUtil.toJson(httpResult.getData()), Login.class);
                HttpResult<Login> result = new HttpResult<Login>();
                result.copy(httpResult);
                if (loginBean != null) {
                    result.setData(loginBean);
                    LoginStatusUtil.getInstance().setLoginErroCount(loginBean.getFailCount());
                }
                if (mListener != null) {
                    mListener.onFail(result);
                }
            }
        });
    }

    public void quickLogin(String token, final HttpTaskCallBack<Login> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", token);
        networkRequest.setParam("phoneMode", DeviceUtil.getModel());
        networkRequest.setParam("phoneName", DeviceUtil.getBrand());
        networkRequest.requestSRV(HttpContants.CMD_QUICK_LOGIN, "", new HttpTaskCallBack<Object>() {//正在登录,请稍后...
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                HttpResult<Login> result = new HttpResult<Login>();
                result.copy(httpResult);
                Login loginBean = GsonUtil.fromJson(GsonUtil.toJson(httpResult.getData()), Login.class);
                if (loginBean != null) {
                    result.setData(loginBean);
                    setLoginData(loginBean);
                }
                if (mListener != null) {
                    mListener.onSuccess(result);
                }
            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                Login loginBean = GsonUtil.fromJson(GsonUtil.toJson(httpResult.getData()), Login.class);
                HttpResult<Login> result = new HttpResult<Login>();
                result.copy(httpResult);
                if (loginBean != null) {
                    result.setData(loginBean);
                    LoginStatusUtil.getInstance().setLoginErroCount(loginBean.getFailCount());
                }
                if (mListener != null) {
                    mListener.onFail(result);
                }
            }
        });
    }


    private void setLoginData(Login loginBean) {
        UserInfo.getInstance().setToken(loginBean.getToken());
        UserInfo.getInstance().setUserId(loginBean.getUserId());
        UserInfo.getInstance().setUserLoginName(loginBean.getUserLoginName());
        UserInfo.getInstance().setUserMobile(loginBean.getUserMobile());
        UserInfo.getInstance().setPicUrl(loginBean.getPicUrl());
        UserInfo.getInstance().setUserNickname(loginBean.getUserNickname());
        UserInfo.getInstance().setFamilyId(loginBean.getFamilyId());
        UserInfo.getInstance().setBindPhone(true);
        UserInfo.getInstance().setData(true);
        UserInfo.getInstance().setHospital(loginBean.getHospital());
        UserInfo.getInstance().setHospitalDepartments(loginBean.getHospitalDepartments());
        UserInfo.getInstance().setIdentify(loginBean.getIdentify());
        UserInfo.getInstance().setPositionStr(loginBean.getPositionStr());


        LoginStatusUtil.getInstance().setLogin(true);
        DataCacheUtil.getInstance().putBoolean(DataCacheUtil.LOGIN_AGAIN, true);
        DataCacheUtil.getInstance().putString(DataCacheUtil.LOGIN_ACCOUNT, loginBean.getUserMobile());
        DataCacheUtil.getInstance().putString(DataCacheUtil.LOGIN_TOKEN, loginBean.getToken());

        if (loginBean.isHasUndoneOrder()) {//有咨询单，就登录聊天服务器
            //登录聊天服务器
            loginChatServer();
        }
    }


    public void loginOut(final HttpTaskCallBack mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.requestSRV(HttpContants.CMD_LOGIN_OUT, "正在退出,请稍后...", new HttpTaskCallBack<Object>() {
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

    /**
     * 注册并登陆
     *
     * @param userAcount
     * @param userPassword
     * @param mAuthCode
     * @param mListener
     */
    public void registAndLogin(String userAcount, String userPassword, String mAuthCode, final HttpTaskCallBack mListener) {
        ThirdLogin login = LoginStatusUtil.getInstance().getThirdLogin();
        NetworkRequest request = new NetworkRequest(mContext);
        request.setParam("userMobile", userAcount);
//        request.setParam("userPassword", RSAUtil.getInstance().encode(userPassword));
        request.setParam("userPassword", userPassword);
        request.setParam("verifyCode", mAuthCode);
        /*if(login.getPlatfrom().equals(QQ.NAME)){
            request.setParam("loginType", Constants.TYPE_LOGIN_ACCOUNT_QQ);
        }else{
            request.setParam("loginType", Constants.TYPE_LOGIN_ACCOUNT_WECHAT);
        }*/
        request.setParam("accessToken", login.getToken());
        request.setParam("openid", login.getOpenId());
        request.setParam("picUrl",UserInfo.getInstance().getPicUrl());
        request.setParam("userNickname",UserInfo.getInstance().getUserNickname());
        request.setParam("phoneMode", DeviceUtil.getModel());
        request.setParam("phoneName", DeviceUtil.getBrand());
        request.setParam("userType", "1");//0:普通用户 1: 医生
        request.requestSRV(HttpContants.CMD_REGIST_AND_LOGIN, "正在验证,请稍后...", new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                HttpResult<Login> result = new HttpResult<Login>();
                result.copy(httpResult);
                Login loginBean = GsonUtil.fromJson(GsonUtil.toJson(httpResult.getData()), Login.class);
                if (loginBean != null) {
                    result.setData(loginBean);
                    setLoginData(loginBean);
                }
                if (mListener != null) {
                    mListener.onSuccess(result);
                }
            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                Login loginBean = GsonUtil.fromJson(GsonUtil.toJson(httpResult.getData()), Login.class);
                HttpResult<Login> result = new HttpResult<Login>();
                result.copy(httpResult);
                if (loginBean != null) {
                    result.setData(loginBean);
                    LoginStatusUtil.getInstance().setLoginErroCount(loginBean.getFailCount());
                }
                if (mListener != null) {
                    mListener.onFail(result);
                }
            }
        });
    }


    private void loginChatServer() {
        if(!UserInfo.getInstance().isChatServerLoginSuccess()){
            ChatServerManager.init(new ChatServerConnectListener() {
                @Override
                public void onConnectStatus(boolean isSuccess) {
                    if (isSuccess) {
                        ChatModel chatModel = new ChatModel(mContext);
                        chatModel.addChatListener();
                    }
                }
            });
            ChatServerManager.login(new ChatServerLoginListener() {
                @Override
                public void backLoginSucessStatus(boolean isSuccess) {
                    if (isSuccess) {
                        ChatModel chatModel = new ChatModel(mContext);
                        chatModel.addChatListener();
                    }
                }
            });
        }
    }

}
