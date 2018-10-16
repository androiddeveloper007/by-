package com.bowen.doctor.mine.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.utils.RouterActivityUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseActivity;
import com.bowen.doctor.common.bean.Page;
import com.bowen.doctor.common.bean.network.ConsultItem;
import com.bowen.doctor.common.bean.network.MyOrder;
import com.bowen.doctor.common.bean.network.MyOrderRecord;
import com.bowen.doctor.consult.activity.ChatActivity;
import com.bowen.doctor.mine.adapter.MyOrderRvAdapter;
import com.bowen.doctor.mine.contract.MyOrderContract;
import com.bowen.doctor.mine.presenter.MyOrderPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Describe:我的订单
 * Created by zhuzhipeng on 2018/6/29.
 */
public class MyOrderActivity extends BaseActivity implements MyOrderContract.View, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.mPtrFrameLayout_my_order)
    PtrClassicFrameLayout mPtrFrameLayout;
    @BindView(R.id.myOrderRv)
    RecyclerView myOrderRv;
    private MyOrderRvAdapter mAdapter;
    private MyOrderPresenter mPresenter;
    private int page = 1;//页码
    private final int pageSize = 10;
    private boolean isMore = false;
    private boolean isLoadMore = false;
    private View emptyView;
    private Activity mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
        mActivity = this;
        setTitle("我的订单");
        mPresenter = new MyOrderPresenter(this,this);
        initRv();
    }

    private void initRv() {
        mAdapter = new MyOrderRvAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        myOrderRv.setLayoutManager(layoutManager);
        myOrderRv.setAdapter(mAdapter);
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, myOrderRv, header);
            }
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(false);
            }
        });
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setOrderPassAwayListener(new MyOrderRvAdapter.OrderPassAwayListener() {
            @Override
            public void orderPassAway(int pos) {
                Bundle bundle = new Bundle();
                ConsultItem consultItem = new ConsultItem();
                consultItem.setHeadImgUrl(((MyOrder) mAdapter.getData().get(pos)).getUserImg());
                consultItem.setUserId(((MyOrder) mAdapter.getData().get(pos)).getUserId());
                consultItem.setUserNickname(((MyOrder) mAdapter.getData().get(pos)).getUserNickname());
                consultItem.setOrderId(((MyOrder) mAdapter.getData().get(pos)).getOrderId());
                bundle.putSerializable(RouterActivityUtil.FROM_TAG, consultItem);
                RouterActivityUtil.startActivity(MyOrderActivity.this, ChatActivity.class, bundle);
            }
        });
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
    public void onLoadSuccess(MyOrderRecord record) {
        mPtrFrameLayout.refreshComplete();
        if(emptyView == null) {
            emptyView = getLayoutInflater().inflate(R.layout.viewstub_no_folk_prescription, (ViewGroup) mActivity.findViewById(android.R.id.content), false);
            mAdapter.setEmptyView(true, emptyView);
        }
        Page pageBean = record.getPage();
        if (isMore) {
            mAdapter.notifyDataChangedAfterLoadMore(record.getTraOrderList(), true);
        } else {
            mAdapter.setNewData(record.getTraOrderList());
        }
        mAdapter.openLoadMore(pageBean.getPageSize(), pageBean.getPageNo() < pageBean.getTotalPages());
        if (pageBean.getTotalCount() != 0 && mAdapter.getData().size() == pageBean.getTotalCount()) {
            View footView = getLayoutInflater().inflate(R.layout.view_no_more, null);
            mAdapter.addFooterView(footView);
        }
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