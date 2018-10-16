package com.bowen.doctor.common.base;

import android.os.Bundle;

import com.bowen.commonlib.base.BaseLibActivity;
import com.umeng.analytics.MobclickAgent;


/**
 * Created by AwenZeng on 2017/3/30.
 */

public class BaseActivity extends BaseLibActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);//友盟
    }


    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }




}
