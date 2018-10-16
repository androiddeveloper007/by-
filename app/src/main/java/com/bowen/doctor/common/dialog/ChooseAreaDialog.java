package com.bowen.doctor.common.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bowen.commonlib.base.BaseDialog;
import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.utils.EmptyUtils;
import com.bowen.doctor.R;
import com.bowen.doctor.common.bean.location.City;
import com.bowen.doctor.common.bean.location.County;
import com.bowen.doctor.common.bean.location.Province;
import com.bowen.doctor.common.util.CityChooseUtil;
import com.bowen.doctor.common.dialog.adapter.CityAdapter;
import com.bowen.doctor.common.dialog.adapter.CountyAdapter;
import com.bowen.doctor.common.dialog.adapter.ProvinceAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 选择省市区的对话框
 * Created by zzp on 2016/12/13.
 */

public class ChooseAreaDialog extends BaseDialog {

    @BindView(R.id.mProvinceRecyclerView)
    RecyclerView mProvinceRecyclerView;
    @BindView(R.id.mCityRecyclerView)
    RecyclerView mCityRecyclerView;
    @BindView(R.id.mCountyRecyclerView)
    RecyclerView mCountyRecyclerView;
    private ProvinceAdapter mProvinceAdapter;
    private CityAdapter mCityAdapter;
    private CountyAdapter mCountyAdapter;
    private List<Province> mProvinceList;
    private List<City> mCityList;
    private List<County> mCountyList;

    private String mProviceCode;
    private String mCityCode;
    private String mCountyCode;
    private String mProvinceName;
    private String mCityName;
    private String mCountyName;
    private int provincePos;
    public ChooseAreaDialog(Context context) {
        super(context, R.style.dialog_transparent_style);
    }

    public ChooseAreaDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_window_choose_address);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(true);

        mProvinceAdapter = new ProvinceAdapter(mContext);
        mCityAdapter = new CityAdapter(mContext);
        mCountyAdapter = new CountyAdapter(mContext);

        mProvinceList = CityChooseUtil.getInstance().getProvincesData();
        mCityList = CityChooseUtil.getInstance().getCitiesData(0);
        mCountyList = CityChooseUtil.getInstance().getCountyData(0,0);

        LinearLayoutManager provinceLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        final LinearLayoutManager cityLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager countyLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mProvinceAdapter.setNewData(mProvinceList);
        mProvinceRecyclerView.setLayoutManager(provinceLayoutManager);
        mProvinceRecyclerView.setAdapter(mProvinceAdapter);

        mCityAdapter.setNewData(mCityList);
        mCityRecyclerView.setLayoutManager(cityLayoutManager);
        mCityRecyclerView.setAdapter(mCityAdapter);

        mCountyAdapter.setNewData(mCountyList);
        mCountyRecyclerView.setLayoutManager(countyLayoutManager);
        mCountyRecyclerView.setAdapter(mCountyAdapter);
        mProvinceAdapter.setSelectPos(0);
        mCityAdapter.setSelectPos(0);

        mProvinceAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                provincePos = position;
                mProviceCode = mProvinceAdapter.getItem(position).getCode();
                mProvinceName = mProvinceAdapter.getItem(position).getProvince();
                mProvinceAdapter.setSelectPos(position);
                mCityList = CityChooseUtil.getInstance().getCitiesData(position);
                mCityAdapter.setNewData(mCityList);
                mCountyList = CityChooseUtil.getInstance().getCountyData(provincePos,0);
                mCountyAdapter.setNewData(mCountyList);
                mCountyRecyclerView.scrollToPosition(0);
                mCityAdapter.setSelectPos(0);
                mProvinceAdapter.notifyDataSetChanged();
                mCityAdapter.notifyDataSetChanged();
                mCountyAdapter.notifyDataSetChanged();
            }
        });
        mCityAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mCityAdapter.setSelectPos(position);
                mCityCode = mCityAdapter.getItem(position).getCode();
                mCityName = mCityAdapter.getItem(position).getCity();
                mCountyList = CityChooseUtil.getInstance().getCountyData(provincePos,position);
                mCountyAdapter.setNewData(mCountyList);
                mCountyRecyclerView.scrollToPosition(0);
                mCityAdapter.notifyDataSetChanged();
                mCountyAdapter.notifyDataSetChanged();
            }
        });
        mCountyAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mCountyCode = mCountyAdapter.getItem(position).getCode();
                mCountyName = mCountyAdapter.getItem(position).getCounty();
                if(EmptyUtils.isNotEmpty(mListener)){
                    mListener.onDataCallBack(mProviceCode,mCityCode,mCountyCode);
                }
                dismiss();
            }
        });
    }

}
