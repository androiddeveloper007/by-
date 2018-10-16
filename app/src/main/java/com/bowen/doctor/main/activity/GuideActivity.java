package com.bowen.doctor.main.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.awen.camera.util.ScreenSizeUtil;
import com.bowen.commonlib.utils.RouterActivityUtil;
import com.bowen.commonlib.widget.CirclePageIndicator;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseActivity;
import com.bowen.doctor.common.util.LoginStatusUtil;
import com.bowen.doctor.login.activity.LoginActivity;
import com.bowen.doctor.main.adapter.GuidePagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 引导页
 * Created by AwenZeng on 2016/12/05.
 */
public class GuideActivity extends BaseActivity {
    @BindView(R.id.mSkipTv)
    TextView mSkipTv;
    @BindView(R.id.mViewPage)
    ViewPager mViewPage;
    @BindView(R.id.mViewpagerIndicator)
    CirclePageIndicator mViewpagerIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        init();
    }

    protected void init() {
        ArrayList<View> viewLayouts = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            View viewLayout = null;
            switch (i) {
//                case 0:
//                    viewLayout = this.getLayoutInflater().inflate(R.layout.layout_guide_01, null);
//                    break;
//                case 1:
//                    viewLayout = this.getLayoutInflater().inflate(R.layout.layout_guide_02, null);
//                    break;
                case 2:
                    viewLayout = this.getLayoutInflater().inflate(R.layout.layout_guide_03, null);
                    TextView textView = (TextView) viewLayout.findViewById(R.id.mImmediateEnterTv);
                    textView.setOnClickListener(this);
                    textView.setVisibility(View.VISIBLE);
                    break;
            }
            if(viewLayout!=null) viewLayouts.add(viewLayout);
        }
        GuidePagerAdapter adapter = new GuidePagerAdapter(viewLayouts);
        mViewPage.setAdapter(adapter);
        mViewpagerIndicator.setViewPager(mViewPage);
        mViewpagerIndicator.setPadding(ScreenSizeUtil.dp2px(7));
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        goNextPage();
    }

    @OnClick(R.id.mSkipTv)
    public void onViewClicked() {
        goNextPage();
    }

    private void goNextPage() {
        if(LoginStatusUtil.getInstance().isLogin()){
            RouterActivityUtil.startActivity(GuideActivity.this, MainActivity.class, true);
        }else {
            RouterActivityUtil.startActivity(GuideActivity.this, LoginActivity.class, true);
        }
    }
}
