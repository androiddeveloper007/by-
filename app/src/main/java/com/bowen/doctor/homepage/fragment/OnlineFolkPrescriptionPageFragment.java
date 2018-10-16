package com.bowen.doctor.homepage.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.utils.RouterActivityUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseFragment;
import com.bowen.doctor.common.bean.network.OnlineFolkPrescriptionRecord;
import com.bowen.doctor.common.bean.Page;
import com.bowen.doctor.homepage.activity.FolkPrescriptionDetailActivity;
import com.bowen.doctor.homepage.adapter.OnlineFolkPrescriptionRvAdapter;
import com.bowen.doctor.homepage.contract.OnlineFolkPrescriptionContract;
import com.bowen.doctor.homepage.presenter.OnlineFolkPrescriptionPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Describe: “在线偏方”
 * Created by zhuzhipeng on 2018/6/29.
 */

public class OnlineFolkPrescriptionPageFragment extends BaseFragment implements OnlineFolkPrescriptionContract.View, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.onlinePrescriptionRv)
    RecyclerView onlinePrescriptionRv;
    @BindView(R.id.mPtrFrameLayoutOnlinePrescription)
    PtrClassicFrameLayout mPtrFrameLayout;
    private OnlineFolkPrescriptionRvAdapter mAdapter;
    private OnlineFolkPrescriptionPresenter mPresenter;
    private int page = 1;
    private final int pageSize = 10;
    private boolean isMore = false;
    private boolean isLoadMore = false;
    private View emptyView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = mInflater.inflate(R.layout.fragment_online_folk_prescription, null);
        ButterKnife.bind(this, mView);
        mPresenter = new OnlineFolkPrescriptionPresenter(mActivity, this);
        initView();
        getData(false);
    }

    private void initView() {
        mAdapter = new OnlineFolkPrescriptionRvAdapter(mActivity);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        onlinePrescriptionRv.setLayoutManager(layoutManager);
        onlinePrescriptionRv.setAdapter(mAdapter);
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, onlinePrescriptionRv, header);
            }

            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(false);
            }
        });
        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(RouterActivityUtil.FROM_TAG, mAdapter.getItem(position));
                bundle.putInt(RouterActivityUtil.FROM_TAG1, position);
                RouterActivityUtil.startActivity(mActivity, FolkPrescriptionDetailActivity.class, bundle);
            }
        });
        mAdapter.setOnLoadMoreListener(this);
        if (emptyView == null) {
            emptyView = getLayoutInflater().inflate(R.layout.viewstub_no_folk_prescription, (ViewGroup) mActivity.findViewById(android.R.id.content), false);
            mAdapter.setEmptyView(true, emptyView);
        }
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
    public void onLoadSuccess(OnlineFolkPrescriptionRecord record) {
        if (record.getPrescriptionList() != null && record.getPrescriptionList().size() > 0) {
            Page pageBean = record.getPage();
            if (isMore) {
                mAdapter.notifyDataChangedAfterLoadMore(record.getPrescriptionList(), true);
            } else {
                mAdapter.setNewData(record.getPrescriptionList());
            }
            mAdapter.openLoadMore(pageBean.getPageSize(), pageBean.getPageNo() < pageBean.getTotalPages());
            if (pageBean.getTotalCount() != 0 && mAdapter.getData().size() == pageBean.getTotalCount()) {
                View footView = getLayoutInflater().inflate(R.layout.view_no_more, null);
                mAdapter.addFooterView(footView);
            }
            if (mAdapter.getData() != null && mAdapter.getData().size() == 0) {
                mAdapter.addFooterView(null);
            }
            page = pageBean.getNextPage();
            isLoadMore = false;
        } else {

        }
        mPtrFrameLayout.refreshComplete();
    }

    @Override
    public void onLoadFail(OnlineFolkPrescriptionRecord list) {
        mPtrFrameLayout.refreshComplete();
    }

    @Override
    public void onLoadMoreRequested() {
        getData(true);
    }
}