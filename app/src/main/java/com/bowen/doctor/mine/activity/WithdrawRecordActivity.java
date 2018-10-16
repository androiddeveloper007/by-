package com.bowen.doctor.mine.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseActivity;
import com.bowen.doctor.common.bean.Page;
import com.bowen.doctor.common.bean.network.WithdrawRecord;
import com.bowen.doctor.mine.adapter.WithdrawRecordAdapter;
import com.bowen.doctor.mine.contract.WithdrawRecordContract;
import com.bowen.doctor.mine.presenter.WithdrawRecordPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * @author zhuzhipeng
 * @time 2018/8/19 13:08
 * 提现记录
 */
public class WithdrawRecordActivity extends BaseActivity implements WithdrawRecordContract.View, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.mPtrFrameLayout)
    PtrClassicFrameLayout mPtrFrameLayout;
    @BindView(R.id.withdrawRecordRv)
    RecyclerView withdrawRecordRv;
    private WithdrawRecordPresenter mPresenter;
    private WithdrawRecordAdapter mAdapter;
    private int page = 1;
    private final int pageSize = 10;
    private boolean isMore = false;
    private boolean isLoadMore = false;
    private View emptyView,headerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_withdraw_record);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setTitle("提现记录");
        mPresenter = new WithdrawRecordPresenter(this, this);
        mAdapter = new WithdrawRecordAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        withdrawRecordRv.setLayoutManager(layoutManager);
        withdrawRecordRv.setAdapter(mAdapter);
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, withdrawRecordRv, header);
            }
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(false);
            }
        });
        mAdapter.setOnLoadMoreListener(this);
        getData(false);
    }

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
    public void onLoadSuccess(WithdrawRecord record) {
        mPtrFrameLayout.refreshComplete();
        if(emptyView == null) {
            emptyView = getLayoutInflater().inflate(R.layout.viewstub_no_folk_prescription, (ViewGroup) findViewById(android.R.id.content), false);
            mAdapter.setEmptyView(true, emptyView);
        }
        Page pageBean = record.getPage();
        if (isMore) {
            mAdapter.notifyDataChangedAfterLoadMore(record.getList(), true);
        } else {
            mAdapter.setNewData(record.getList());
            if(record.getList().size()>0){
                if(headerView == null) {
                    headerView = getLayoutInflater().inflate(R.layout.withdraw_record_header, (ViewGroup) findViewById(android.R.id.content), false);
                }
                mAdapter.addHeaderView(headerView);
            }
        }
        mAdapter.openLoadMore(pageBean.getPageSize(), pageBean.getPageNo() < pageBean.getTotalPages());
        page = pageBean.getNextPage();
        isLoadMore = false;
    }

    @Override
    public void onLoadFail() {
        if(emptyView==null) {
            emptyView = getLayoutInflater().inflate(R.layout.viewstub_no_folk_prescription, (ViewGroup) findViewById(android.R.id.content), false);
            mAdapter.setEmptyView(true, emptyView);
        }
        mPtrFrameLayout.refreshComplete();
    }

    @Override
    public void onLoadMoreRequested() {
        getData(true);
    }
}
