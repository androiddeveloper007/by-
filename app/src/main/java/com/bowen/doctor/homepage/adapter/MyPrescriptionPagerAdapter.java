package com.bowen.doctor.homepage.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 描述：中药方剂viewpager的adapter
 * Created by zhuzhipeng on 2018/6/26
 */

public class MyPrescriptionPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;
    private final String[] TITLES = {"常用方剂", "在线偏方"};


    public MyPrescriptionPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mFragmentList = fragmentList;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}
