package com.bowen.doctor.consult;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bowen.commonlib.base.BaseQuickAdapter.OnRecyclerViewItemClickListener;
import com.bowen.commonlib.base.BaseQuickAdapter.RequestLoadMoreListener;
import com.bowen.commonlib.utils.RouterActivityUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseFragment;
import com.bowen.doctor.common.bean.Page;
import com.bowen.doctor.common.bean.network.ConsultInfo;
import com.bowen.doctor.common.bean.network.ConsultItem;
import com.bowen.doctor.common.widget.SwipeDividerDecoration;
import com.bowen.doctor.consult.activity.ChatActivity;
import com.bowen.doctor.consult.adapter.ConsultFgAdapter;
import com.bowen.doctor.consult.adapter.ConsultFgAdapter.DeleteListener;
import com.bowen.doctor.consult.contract.ConsultFragmentContract;
import com.bowen.doctor.consult.presenter.ConsultFragmentPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Describe:咨询
 * Created by zhuzhipeng on 2018/7/11
 */
public class ConsultFragment extends BaseFragment implements RequestLoadMoreListener,ConsultFragmentContract.View {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mPtrFrameLayout)
    PtrClassicFrameLayout mPtrFrameLayout;
    private ConsultFgAdapter mAdapter;
    private ConsultFragmentPresenter mPresenter;

    private int pageNo = 1;
    private final int pageSize = 10;
    private boolean isMore = false;
    private boolean isLoadMore = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = mInflater.inflate(R.layout.fragment_consult, null);
        ButterKnife.bind(this, mView);
        initView();
    }


    private void initView() {
        mAdapter = new ConsultFgAdapter(mActivity);
        mPresenter = new ConsultFragmentPresenter(mActivity,this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new SwipeDividerDecoration());
        mRecyclerView.setAdapter(mAdapter);
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mRecyclerView, header);
            }

            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(false);
            }
        });
        mAdapter.setItemOnClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(RouterActivityUtil.FROM_TAG,mAdapter.getItem(position));
                RouterActivityUtil.startActivity(mActivity, ChatActivity.class,bundle);
            }
        });
        mAdapter.setmDeleteListen(new DeleteListener() {
            @Override
            public void onDeleteListener(ConsultItem item) {
                mPresenter.removeConsultInfo(item.getUserId());
            }
        });

        mAdapter.setOnLoadMoreListener(this);
        startRefresh();
    }

    private void startRefresh() {
        mPtrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrameLayout.autoRefresh();
            }
        }, 100);
    }

    public void getData(boolean isLoad) {
        isMore = isLoad;
        if (isMore && isLoadMore) {
            return;
        }
        if (isMore) {
            isLoadMore = true;
        } else {
            pageNo = 1;
        }
        mPresenter.getConsultData(pageNo, pageSize);
    }

    @Override
    public void onResume() {
        super.onResume();
        getData(false);
    }

    @Override
    public void onLoadMoreRequested() {
        getData(true);
    }

    @Override
    public void onGetConsultDataSuccess(ConsultInfo consultInfo) {
        mPtrFrameLayout.refreshComplete();
        Page pageBean = consultInfo.getPage();
        if (isMore) {
            mAdapter.notifyDataChangedAfterLoadMore(consultInfo.getConsultList(), true);
        } else {
            mAdapter.setNewData(consultInfo.getConsultList());
        }
        mAdapter.openLoadMore(pageBean.getPageSize(), pageBean.getPageNo() < pageBean.getTotalPages());
        if (pageBean.getTotalCount() >= 5 && mAdapter.getData().size() == pageBean.getTotalCount()) {
            View footView = getLayoutInflater().inflate(R.layout.view_no_more, null);
            mAdapter.addFooterView(footView);
        }
        if (mAdapter.getData() != null && mAdapter.getData().size() == 0) {
           View emptyView = getLayoutInflater().inflate(R.layout.empty_view_no_consult_fg,
                    (ViewGroup) mActivity.findViewById(android.R.id.content), false);
            mAdapter.setEmptyView(true, emptyView);
        }
        pageNo = pageBean.getNextPage();
        isLoadMore = false;
    }

    @Override
    public void onGetConsultDataFailed() {
        mPtrFrameLayout.refreshComplete();
    }

    @Override
    public void onRemoveConsultInfo() {
        getData(false);
    }
}
