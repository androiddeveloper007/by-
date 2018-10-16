package com.bowen.doctor.homepage.fragment;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.utils.RouterActivityUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseFragment;
import com.bowen.doctor.common.bean.Page;
import com.bowen.doctor.common.bean.network.CommonUsedPrescriptionRecord;
import com.bowen.doctor.common.bean.network.PrescriptionBean;
import com.bowen.doctor.common.event.AddCommonUsePrescSuccEvent;
import com.bowen.doctor.common.event.DeleteCommonUsePrescSuccEvent;
import com.bowen.doctor.common.event.EditCommonUsePrescSuccEvent;
import com.bowen.doctor.homepage.activity.AddCommonUsedPrescriptionActivity;
import com.bowen.doctor.homepage.activity.CommonUsedPrescriptionDetailActivity;
import com.bowen.doctor.homepage.adapter.CommonUsedPrescriptionRvAdapter;
import com.bowen.doctor.homepage.contract.CommonUsedPrescriptionContract;
import com.bowen.doctor.homepage.presenter.CommonUsedPrescriptionPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Describe: 常用方剂
 * Created by zhuzhipeng on 2018/6/29.
 */
public class CommonUsedPrescriptionPageFragment extends BaseFragment implements CommonUsedPrescriptionContract.View, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.commonPrescriptionRv)
    RecyclerView commonPrescriptionRv;
    @BindView(R.id.mPtrFrameLayoutCommonPrescription)
    PtrClassicFrameLayout mPtrFrameLayout;
    @BindView(R.id.createPrescriptionTv)
    TextView createPrescriptionTv;
    private CommonUsedPrescriptionRvAdapter mAdapter;
    private CommonUsedPrescriptionPresenter mPresenter;
    private int page = 1;
    private final int pageSize = 10;
    private boolean isMore = false;
    private boolean isLoadMore = false;
    private View emptyView;
    private int distance;
    private boolean visible = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = mInflater.inflate(R.layout.fragment_common_used_prescription, null);
        ButterKnife.bind(this, mView);
        mPresenter = new CommonUsedPrescriptionPresenter(mActivity, this);
        initView();
    }

    private void initView() {
        mAdapter = new CommonUsedPrescriptionRvAdapter(mActivity);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        commonPrescriptionRv.setLayoutManager(layoutManager);
        commonPrescriptionRv.setAdapter(mAdapter);
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, commonPrescriptionRv, header);
            }

            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(false);
            }
        });
        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(RouterActivityUtil.FROM_TAG, (PrescriptionBean) mAdapter.getData().get(position));
                RouterActivityUtil.startActivity(mActivity, CommonUsedPrescriptionDetailActivity.class, bundle);
            }
        });
        mAdapter.setOnLoadMoreListener(this);
        getData(false);
        if (emptyView == null) {
            emptyView = getLayoutInflater().inflate(R.layout.viewstub_no_folk_prescription, (ViewGroup) mActivity.findViewById(android.R.id.content), false);
            mAdapter.setEmptyView(true, emptyView);
        }


        commonPrescriptionRv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (distance < -ViewConfiguration.getTouchSlop() && !visible) {
                    //显示fab
                    showFABAnimation(createPrescriptionTv);
                    distance = 0;
                    visible = true;
                } else if (distance > ViewConfiguration.getTouchSlop() && visible) {
                    //隐藏
                    hideFABAnimation(createPrescriptionTv);
                    distance = 0;
                    visible = false;
                }
                if ((dy > 0 && visible) || (dy < 0 && !visible))//向下滑并且可见  或者  向上滑并且不可见
                    distance += dy;
            }
        });
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
    public void onLoadSuccess(CommonUsedPrescriptionRecord record) {
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
            mAdapter.setNewData(record.getPrescriptionList());
        }
        mPtrFrameLayout.refreshComplete();
    }

    @Override
    public void onLoadFail(CommonUsedPrescriptionRecord list) {
        mPtrFrameLayout.refreshComplete();
    }

    @Override
    public void onLoadMoreRequested() {
        getData(true);
    }

    @OnClick({R.id.createPrescriptionTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.createPrescriptionTv:
                Bundle bundle = new Bundle();
                bundle.putBoolean(RouterActivityUtil.FROM_TAG, true);
                RouterActivityUtil.startActivity(mActivity, AddCommonUsedPrescriptionActivity.class, bundle);
                break;
        }
    }

    public void showFABAnimation(View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(500).start();
    }

    public void hideFABAnimation(View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 0f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(500).start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AddCommonUsePrescSuccEvent event) {
        getData(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DeleteCommonUsePrescSuccEvent event) {
        getData(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EditCommonUsePrescSuccEvent event) {
        getData(false);
    }
}