package com.bowen.doctor.homepage.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bowen.commonlib.utils.ScreenSizeUtil;
import com.bowen.commonlib.widget.mypagerindicator.CommonNavigator;
import com.bowen.commonlib.widget.mypagerindicator.CommonNavigatorAdapter;
import com.bowen.commonlib.widget.mypagerindicator.IPagerIndicator;
import com.bowen.commonlib.widget.mypagerindicator.IPagerTitleView;
import com.bowen.commonlib.widget.mypagerindicator.MagicIndicator;
import com.bowen.commonlib.widget.mypagerindicator.SimplePagerTitleView;
import com.bowen.commonlib.widget.mypagerindicator.ViewPagerHelper;
import com.bowen.commonlib.widget.mypagerindicator.WrapPagerIndicator;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseActivity;
import com.bowen.doctor.homepage.adapter.MyPrescriptionPagerAdapter;
import com.bowen.doctor.homepage.fragment.CommonUsedPrescriptionPageFragment;
import com.bowen.doctor.homepage.fragment.OnlineFolkPrescriptionPageFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Describe:中药方剂
 * Created by zhuzhipeng on 2018/7/16.
 */
public class PrescriptionActivity extends BaseActivity {
    @BindView(R.id.prescriptionTitleIndicator)
    MagicIndicator mIndicator;
    @BindView(R.id.mPrescriptionVp)
    ViewPager mPrescriptionVp;
    private final String[] TITLES = {"常用方剂", "在线偏方"};
    private MyPrescriptionPagerAdapter mMyPrescriptionPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_prescription);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        CommonUsedPrescriptionPageFragment page0 = new CommonUsedPrescriptionPageFragment();
        OnlineFolkPrescriptionPageFragment page1 = new OnlineFolkPrescriptionPageFragment();
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(page0);
        fragmentList.add(page1);
        mMyPrescriptionPagerAdapter = new MyPrescriptionPagerAdapter(getSupportFragmentManager(), fragmentList);
        mPrescriptionVp.setAdapter(mMyPrescriptionPagerAdapter);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setScrollPivotX(0.35f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return 2;
            }
            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(TITLES[index]);
                simplePagerTitleView.setNormalColor(Color.parseColor("#7F98FB"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#ffffff"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPrescriptionVp.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }
            @Override
            public IPagerIndicator getIndicator(Context context) {
                WrapPagerIndicator indicator = new WrapPagerIndicator(context);
                indicator.setFillColor(Color.parseColor("#7F98FB"));
                indicator.setBackgroundResource(R.drawable.my_prescription_title_border);
                indicator.setRoundRadius(ScreenSizeUtil.dp2px(3));
                return indicator;
            }
        });
        mIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mIndicator, mPrescriptionVp);
    }

    @OnClick(R.id.backLayout)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.backLayout:
                onBackPressed();
                break;
        }
    }
}