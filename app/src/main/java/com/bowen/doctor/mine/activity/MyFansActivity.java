package com.bowen.doctor.mine.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseActivity;
import com.bowen.doctor.common.bean.Page;
import com.bowen.doctor.common.bean.network.EmrDoctorFanRecord;
import com.bowen.doctor.mine.adapter.MyFansRvAdapter;
import com.bowen.doctor.mine.contract.MyFansContract;
import com.bowen.doctor.mine.presenter.MyFansPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Describe:我的粉丝列表
 * Created by zhuzhipeng on 2018/6/29.
 */

public class MyFansActivity extends BaseActivity implements MyFansContract.View, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.mPtrFrameLayoutMyFans)
    PtrClassicFrameLayout mPtrFrameLayout;
    @BindView(R.id.myFansRv)
    RecyclerView myFansRv;
    private MyFansRvAdapter mAdapter;
    private MyFansPresenter mPresenter;
    private int page = 1;
    private final int pageSize = 10;
    private boolean isMore = false;
    private boolean isLoadMore = false;
    private View emptyView;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_my_fans);
        ButterKnife.bind(this);
        mActivity = this;
        setTitle("我的粉丝");
        mPresenter = new MyFansPresenter(this,this);
        initRv();
    }

    private void initRv() {
        mAdapter = new MyFansRvAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        myFansRv.setLayoutManager(layoutManager);
        myFansRv.setAdapter(mAdapter);
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, myFansRv, header);
            }
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(false);
            }
        });
        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        });
        mAdapter.setOnLoadMoreListener(this);
        getData(false);
    }

    /***
     * 加载列表
     * @param isLoad
     */
    public void getData(boolean isLoad) {
        isMore = isLoad;
        if (isMore && isLoadMore) {
            return;
        }
        if (isMore) {
            isLoadMore = true;
        } else {
            page = 1;
        }
        mPresenter.loadData(page, pageSize);
    }

    @Override
    public void onLoadSuccess(EmrDoctorFanRecord record) {
        mPtrFrameLayout.refreshComplete();
        if(emptyView == null) {
            emptyView = getLayoutInflater().inflate(R.layout.viewstub_no_folk_prescription, (ViewGroup) mActivity.findViewById(android.R.id.content), false);
            mAdapter.setEmptyView(true, emptyView);
        }
        Page pageBean = record.getPage();
        if (isMore) {
            mAdapter.notifyDataChangedAfterLoadMore(record.getFansList(), true);
        } else {
            mAdapter.setNewData(record.getFansList());
        }
        mAdapter.openLoadMore(pageBean.getPageSize(), pageBean.getPageNo() < pageBean.getTotalPages());
        page = pageBean.getNextPage();
        isLoadMore = false;
    }

    @Override
    public void onLoadFail() {
        if(emptyView==null) {
            emptyView = getLayoutInflater().inflate(R.layout.viewstub_no_folk_prescription, (ViewGroup) mActivity.findViewById(android.R.id.content), false);
            mAdapter.setEmptyView(true, emptyView);
        }
        mPtrFrameLayout.refreshComplete();
    }

    @Override
    public void onLoadMoreRequested() {
        getData(true);
    }
}