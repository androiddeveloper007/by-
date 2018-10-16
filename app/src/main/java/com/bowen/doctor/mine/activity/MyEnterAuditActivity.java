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
import com.bowen.doctor.common.bean.network.MyEnterAuditRecord;
import com.bowen.doctor.common.dialog.EnterAuditTypeSelectPopwindow;
import com.bowen.doctor.mine.adapter.EnterAuditRvAdapter;
import com.bowen.doctor.mine.contract.MyEnterAuditContract;
import com.bowen.doctor.mine.presenter.MyEnterAuditPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Describe:入驻审核列表
 * Created by zhuzhipeng on 2018/6/29.
 */
public class MyEnterAuditActivity extends BaseActivity implements MyEnterAuditContract.View, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.myEnterAuditRv)
    RecyclerView myEnterAuditRv;
    @BindView(R.id.mPtrFrameLayout)
    PtrClassicFrameLayout mPtrFrameLayout;
    private MyEnterAuditPresenter mPresenter;
    private int page = 1;//页码
    private final int pageSize = 10;
    private boolean isMore = false;
    private boolean isLoadMore = false;
    private Activity mActivity;
    private EnterAuditRvAdapter mAdapter;
    private String hospitalId;
//    private RecyclerView myEnterAuditRv;
//    private PtrClassicFrameLayout mPtrFrameLayout;
    //    private boolean hasInflate0;
//    private boolean hasInflate1;
    private EnterAuditTypeSelectPopwindow popwindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_my_enter_audit);
        ButterKnife.bind(this);
        mActivity = this;
        setTitle("入驻审核");
        mPresenter = new MyEnterAuditPresenter(this, this);
//        getTitleBar().getRightButton().setVisibility(View.VISIBLE);
//        getTitleBar().getRightButton().setBackgroundResource(R.drawable.enter_audit_switch);
        hospitalId = RouterActivityUtil.getString(this);
        popwindow = new EnterAuditTypeSelectPopwindow(this);
        inflateViews();
        getData(false);
    }

//    @Override
//    public void onRightButtonPressed() {
//        super.onRightButtonPressed();
//        showEditWindow(getTitleBar().getRightButton());
//    }


//    private void showEditWindow(View v) {
//        popwindow.setBaseDialogListener(new BasePopWindow.BasePopWindowListener() {
//            @Override
//            public void onDataCallBack(Object... obj) {
//                String temp = (String) obj[0];
//                if (temp.equals("全部")) {
//                    authStatus = Constants.AUTH_IN;
//                } else if (temp.equals("待审核")) {
//                    authStatus = Constants.AUTH_SUC;
//                } else if (temp.equals("已审核")) {
//                    authStatus = Constants.AUTH_FAIL;
//                } else if (temp.equals("已驳回")) {
//                    authStatus = Constants.CANCLE;
//                }
//                startRefresh();
//            }
//        });
//        popwindow.show(v);
//    }

    private void inflateEmptyViews() {
//        findViewById(R.id.viewStubEnterAuditEmpty).setVisibility(View.VISIBLE);
//        findViewById(R.id.inviteOtherDoctor).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //邀请医生入驻
//                RouterActivityUtil.startActivity(mActivity, MyBusinessCardActivity.class);
//            }
//        });
    }

    private void inflateViews() {
//        findViewById(R.id.viewStubMyEnterAudit).setVisibility(View.VISIBLE);
//        mPtrFrameLayout = findViewById(R.id.mPtrFrameLayout);
//        myEnterAuditRv = findViewById(R.id.myEnterAuditRv);
        mAdapter = new EnterAuditRvAdapter(mActivity, mPresenter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        myEnterAuditRv.setLayoutManager(layoutManager);
        myEnterAuditRv.setAdapter(mAdapter);
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, myEnterAuditRv, header);
            }

            public void onRefreshBegin(PtrFrameLayout frame) {
//                authStatus = Constants.AUTH_IN;
                getData(false);
            }
        });
        mAdapter.setOnLoadMoreListener(this);
        View emptyView = getLayoutInflater().inflate(R.layout.viewstub_enter_audit_empty,
                (ViewGroup) mActivity.findViewById(android.R.id.content), false);
        mAdapter.setEmptyView(true, emptyView);
        mAdapter.setEmptyView(emptyView);
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
        mPresenter.loadData(page, pageSize, hospitalId, 0, isLoad);
    }

    @Override
    public void onLoadSuccess(MyEnterAuditRecord record) {
        if (record != null && record.getApplyList() != null && record.getApplyList().size() > 0) {
//            if(!hasInflate0){
//                inflateViews();
//                hasInflate0 = true;
//            }
            Page pageBean = record.getPage();
            if (isMore) {
                mAdapter.notifyDataChangedAfterLoadMore(record.getApplyList(), true);
            } else {
                mAdapter.setNewData(record.getApplyList());
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
//            if(!hasInflate1){
//                inflateEmptyViews();
//                hasInflate1 = true;
//            }
            mAdapter.setNewData(record.getApplyList());
        }
        if (mPtrFrameLayout != null) mPtrFrameLayout.refreshComplete();
    }

    @Override
    public void onLoadFail() {
//        if(findViewById(R.id.viewStubMyEnterAudit)!=null){
//            if(!hasInflate1){
//                inflateEmptyViews();
//                hasInflate1 = true;
//            }
//        }
        if (mPtrFrameLayout != null) mPtrFrameLayout.refreshComplete();
    }

    @Override
    public void onHandleSuccess() {
        startRefresh();
    }

    @Override
    public void onHandleFail() {

    }

    @Override
    public void onLoadMoreRequested() {

    }

    private void startRefresh() {
//        if(mPtrFrameLayout==null){
//            if(EmptyUtils.isEmpty(hospitalId)){
//                getData(false);
//            }
//        }else {
//        }

        mPtrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrameLayout.autoRefresh();
            }
        }, 50);
    }

}