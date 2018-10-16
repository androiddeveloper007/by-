package com.bowen.doctor.main.activity;


import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;

import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.DataCacheUtil;
import com.bowen.commonlib.utils.EmptyUtils;
import com.bowen.commonlib.utils.RouterActivityUtil;
import com.bowen.doctor.BowenApplication;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseActivity;
import com.bowen.doctor.common.bean.network.Login;
import com.bowen.doctor.common.dialog.ChooseServerDialog;
import com.bowen.doctor.common.event.LoginEvent;
import com.bowen.doctor.consult.model.chat.ChatServerManager;
import com.bowen.doctor.login.activity.LoginActivity;
import com.bowen.doctor.login.model.LoginModel;
import com.bowen.doctor.mine.activity.FinishInformationActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * 闪屏页面
 * Created by AwenZeng on 2016/07/28
 */
public class SplashActivity extends BaseActivity {

    private LoginModel mLoginModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setSystemStatusBar(R.color.color_00);
        mLoginModel = new LoginModel(this);
        setMarginStatusBar();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showChooseServerDialog();
            }
        }, 1500);
    }

    private void showChooseServerDialog(){
        //1.测试情况下弹出 2.应用只有进程完全退出时弹出
        if(BowenApplication.DEBUG){
            ChooseServerDialog chooseServerDialog = new ChooseServerDialog(SplashActivity.this);
            chooseServerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    firstStartApp();
                }
            });
            chooseServerDialog.show();
        }else{
            firstStartApp();
        }
    }

    private void firstStartApp() {
        if (!DataCacheUtil.getInstance().getBoolean(DataCacheUtil.FIRST_START_APP, false)) {
            DataCacheUtil.getInstance().putBoolean(DataCacheUtil.FIRST_START_APP, true);
            RouterActivityUtil.startActivity(SplashActivity.this, GuideActivity.class, true);
        } else {
            goNextPage();
        }
    }

    private void goNextPage() {
        if (EmptyUtils.isNotEmpty(DataCacheUtil.getInstance().getString(DataCacheUtil.LOGIN_TOKEN, ""))) {
            mLoginModel.quickLogin(DataCacheUtil.getInstance().getString(DataCacheUtil.LOGIN_TOKEN, ""), new HttpTaskCallBack<Login>() {
                @Override
                public void onSuccess(HttpResult<Login> result) {
                    EventBus.getDefault().post(new LoginEvent());
                    if (!result.getData().getIsData()) {
                        RouterActivityUtil.startActivity(SplashActivity.this, FinishInformationActivity.class, true);
                    }else{
                        RouterActivityUtil.startActivity(SplashActivity.this, MainActivity.class, true);
                    }
                }

                @Override
                public void onFail(HttpResult<Login> result) {
                    RouterActivityUtil.startActivity(SplashActivity.this, LoginActivity.class, true);
                }
            });

        } else {
            RouterActivityUtil.startActivity(SplashActivity.this, LoginActivity.class, true);
        }
    }

}
