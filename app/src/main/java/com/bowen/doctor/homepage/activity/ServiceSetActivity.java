package com.bowen.doctor.homepage.activity;

import android.os.Bundle;
import android.view.View;

import com.bowen.commonlib.utils.RouterActivityUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Describe:服务设置
 * Created by zhuzhipeng on 2018/7/16.
 */
public class ServiceSetActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_service_set);
        ButterKnife.bind(this);
        setTitle("服务设置");
    }
    @OnClick({R.id.tuwenzixunLayout, R.id.menzhenyuyueLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tuwenzixunLayout:
                RouterActivityUtil.startActivity(this, GraphicConsultSetActivity.class);
                break;
            case R.id.menzhenyuyueLayout:
                RouterActivityUtil.startActivity(this, OutpatientAppiontmentSetActivity.class);
                break;
        }
    }
}