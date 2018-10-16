package com.bowen.doctor.consult.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.utils.ToastUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseActivity;
import com.bowen.doctor.common.bean.Page;
import com.bowen.doctor.common.bean.network.CommonUsedPrescriptionRecord;
import com.bowen.doctor.common.event.GeneralPrescriptionEvent;
import com.bowen.doctor.homepage.adapter.SelectCommonUsePrescriptionAdapter;
import com.bowen.doctor.homepage.contract.CommonUsedPrescriptionContract;
import com.bowen.doctor.homepage.presenter.CommonUsedPrescriptionPresenter;

import org.greenrobot.eventbus.EventBus;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * @author zhuzhipeng
 * @time 2018/8/17 14:55
 * 选择常用方剂
 */
public class SelectCommonUsePrescriptionActivity extends BaseActivity implements CommonUsedPrescriptionContract.View, BaseQuickAdapter.RequestLoadMoreListener  {

    private RecyclerView mRecyclerView;
    PtrClassicFrameLayout mPtrFrameLayout;
    private Activity mActivity;
    private int page = 1;
    private final int pageSize = 10;
    private boolean isMore = false;
    private boolean isLoadMore = false;
    private View emptyView;
    private SelectCommonUsePrescriptionAdapter mAdapter;
    private CommonUsedPrescriptionPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_select_common_use_prescription);
        mActivity = this;
        mPresenter = new CommonUsedPrescriptionPresenter(mActivity, this);
        init();
    }

    @Override
    public void onRightButtonPressed() {
        super.onRightButtonPressed();
        GeneralPrescriptionEvent event = new GeneralPrescriptionEvent();
        event.setTypeString(mAdapter.getSelectStr());
        EventBus.getDefault().post(event);
        finish();
    }

    private void init() {
        setTitle("选择常用方剂");
        getTitleBar().getRightTextButton().setVisibility(View.VISIBLE);
        getTitleBar().getRightTextButton().setText("确定");
        getTitleBar().getBackTextView().setVisibility(View.VISIBLE);
        getTitleBar().getBackTextView().setText("取消");
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mPtrFrameLayout = findViewById(R.id.mPtrFrameLayout);
        mAdapter = new SelectCommonUsePrescriptionAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mRecyclerView, header);
            }
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(false);
            }
        });
        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mAdapter.setChoosePos(position);
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
    public void onLoadMoreRequested() {
        getData(true);
    }

    @Override
    public void onLoadSuccess(CommonUsedPrescriptionRecord record) {
        if (emptyView == null) {
            emptyView = getLayoutInflater().inflate(R.layout.viewstub_no_folk_prescription, (ViewGroup) mActivity.findViewById(android.R.id.content), false);
            mAdapter.setEmptyView(true, emptyView);
        }
        if(record.getPrescriptionList()!=null && record.getPrescriptionList().size()>0){
            Page pageBean = record.getPage();
            if (isMore) {
                mAdapter.notifyDataChangedAfterLoadMore(record.getPrescriptionList(), true);
            } else {
                mAdapter.setNewData(record.getPrescriptionList());
            }
            mAdapter.openLoadMore(pageBean.getPageSize(), pageBean.getPageNo() < pageBean.getTotalPages());
//            if (pageBean.getTotalCount() != 0 && mAdapter.getData().size() == pageBean.getTotalCount()) {
//                View footView = getLayoutInflater().inflate(R.layout.view_no_more, null);
//                mAdapter.addFooterView(footView);
//            }
//            if (mAdapter.getData() != null && mAdapter.getData().size() == 0) {
//                mAdapter.addFooterView(null);
//            }
            page = pageBean.getNextPage();
            isLoadMore = false;
        }
        mPtrFrameLayout.refreshComplete();
    }

    @Override
    public void onLoadFail(CommonUsedPrescriptionRecord record) {
        mPtrFrameLayout.refreshComplete();
        if (emptyView == null) {
            emptyView = getLayoutInflater().inflate(R.layout.viewstub_no_folk_prescription, (ViewGroup) mActivity.findViewById(android.R.id.content), false);
            mAdapter.setEmptyView(true, emptyView);
        }
    }
}
