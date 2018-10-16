package com.bowen.doctor.mine.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.bowen.commonlib.utils.ApplicationUtils;
import com.bowen.commonlib.utils.DataCacheUtil;
import com.bowen.commonlib.utils.RouterActivityUtil;
import com.bowen.commonlib.utils.StackUtils;
import com.bowen.doctor.BowenApplication;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseActivity;
import com.bowen.doctor.common.model.UserInfo;
import com.bowen.doctor.common.util.LoginStatusUtil;
import com.bowen.doctor.consult.model.chat.ChatServerManager;
import com.bowen.doctor.login.activity.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Describe:
 * Created by AwenZeng on 2018/4/1.
 */
public class SafeSettingActivity extends BaseActivity {
    @BindView(R.id.appVersionTv)
    TextView appVersionTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_safe_setting);
        ButterKnife.bind(this);
        setTitle("设置");
        if (BowenApplication.DEBUG) {
            appVersionTv.setText("版本号:v" + ApplicationUtils.getVersionName() + "." + ApplicationUtils.getVersionCode());
        } else {
            appVersionTv.setText("版本号:v" + ApplicationUtils.getVersionName());
        }
    }

    @OnClick(R.id.mExitBtn)
    public void onViewClicked() {
        LoginStatusUtil.getInstance().setLogin(false);
        ChatServerManager.closeConnection();
        UserInfo.getInstance().setChatServerLoginSuccess(false);
        DataCacheUtil.getInstance().putString(DataCacheUtil.LOGIN_TOKEN,"");
        StackUtils.getInstanse().finishAllActivity();
        RouterActivityUtil.startActivity(SafeSettingActivity.this, LoginActivity.class);
    }
}