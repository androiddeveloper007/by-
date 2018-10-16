package com.bowen.doctor.mine.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.utils.ApplicationUtils;
import com.bowen.doctor.R;
import com.bowen.doctor.common.adapter.FitDiseasePopupWindowRvAdapter;
import com.bowen.doctor.common.base.BaseActivity;
import com.bowen.doctor.common.bean.Page;
import com.bowen.doctor.common.bean.network.DiseaseInfoRecord;
import com.bowen.doctor.common.event.AddFitDiseaseEvent;
import com.bowen.doctor.common.widget.SearchActionBar;
import com.bowen.doctor.mine.contract.FitDiseaseSelectContract;
import com.bowen.doctor.mine.presenter.FitDiseaseSelectPresenter;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Describe:选择病症，搜索页面
 * Created by zzp on 2018/5/22.
 */
public class FitDiseaseSelectActivity extends BaseActivity implements FitDiseaseSelectContract.View ,BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.searchActionBar)
    SearchActionBar searchActionBar;
    @BindView(R.id.popupFitDiseaseRv)
    RecyclerView popupFitDiseaseRv;
    private FitDiseaseSelectPresenter mPresenter;
    private Context mContext;
    private FitDiseasePopupWindowRvAdapter mAdapter;
    private View emptyView;
    private int page = 1;
    private final int pageSize = 10;
    private boolean isMore = false;
    private boolean isLoadMore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_fit_disease_search);
        ButterKnife.bind(this);
        mContext = this;
        mPresenter = new FitDiseaseSelectPresenter(this, this);
        initView();
        getData(false);
    }

    private void initView() {
        searchActionBar.setEditTextHint("输入病症关键字查找");
        searchActionBar.getRightTextButton().setVisibility(View.VISIBLE);
        searchActionBar.getRightTextButton().setText("取消");
        searchActionBar.getRightTextButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ApplicationUtils.isKeyboardOpen()) {
                    popupFitDiseaseRv.requestFocus();
                    ApplicationUtils.closeKeyboard(searchActionBar.action_bar_edit_text);//收起键盘
                }
                onBackPressed();
            }
        });
        searchActionBar.action_bar_edit_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getData(false);
                    return true;
                }
                return false;
            }
        });
        searchActionBar.action_bar_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                getData(false);
            }
        });
        initRv();
    }

    private void initRv() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        popupFitDiseaseRv.setLayoutManager(manager);
        mAdapter = new FitDiseasePopupWindowRvAdapter(mContext);
        if (emptyView == null) {
            emptyView = getLayoutInflater().inflate(R.layout.empty_view_disease_search, (ViewGroup) findViewById(android.R.id.content), false);
            mAdapter.setEmptyView(true, emptyView);
        }
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationUtils.closeKeyboard(searchActionBar.action_bar_edit_text);
            }
        });
        popupFitDiseaseRv.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mAdapter.setBooleanListByPos(position, !mAdapter.isSelectedByPos(position));
                mAdapter.notifyDataSetChanged();
                selectItem();
            }
        });
        popupFitDiseaseRv.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {//触摸到列表时，隐藏键盘
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                if (e.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    if (ApplicationUtils.isKeyboardOpen()) {
                        popupFitDiseaseRv.requestFocus();
                        ApplicationUtils.closeKeyboard(searchActionBar.action_bar_edit_text);//收起键盘
                    }
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        popupFitDiseaseRv.setOnTouchListener(new View.OnTouchListener() {//收起键盘
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    if (ApplicationUtils.isKeyboardOpen()) {
                        popupFitDiseaseRv.requestFocus();
                        ApplicationUtils.closeKeyboard(searchActionBar.action_bar_edit_text);
                    }
                }
                return false;
            }
        });
        mAdapter.setOnLoadMoreListener(this);
    }

    private void selectItem(){
        if (mAdapter.hasChecked()) {
            searchActionBar.setRightButtonText("确定");
            searchActionBar.getRightTextButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(new AddFitDiseaseEvent(mAdapter.getDiseaseList()));
                    if (ApplicationUtils.isKeyboardOpen()) {
                        popupFitDiseaseRv.requestFocus();
                        ApplicationUtils.closeKeyboard(searchActionBar.action_bar_edit_text);//收起键盘
                    }
                    finish();
                }
            });
        } else {
            searchActionBar.setRightButtonText("取消");
            searchActionBar.getRightTextButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ApplicationUtils.isKeyboardOpen()) {
                        popupFitDiseaseRv.requestFocus();
                        ApplicationUtils.closeKeyboard(searchActionBar.action_bar_edit_text);//收起键盘
                    }
                    onBackPressed();
                }
            });
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
        mPresenter.loadDataByStr(searchActionBar.action_bar_edit_text.getText().toString(), page, pageSize);
    }

    @Override
    public void onLoadSuccess(DiseaseInfoRecord record) {
        Page pageBean = record.getPage();
        if (isMore) {
            mAdapter.notifyDataChangedAfterLoadMore(record.getDiseaseInfoList(), true);
        } else {
            if (record.getDiseaseInfoList() != null && record.getDiseaseInfoList().size() > 0) {
                mAdapter.setNewData(record.getDiseaseInfoList());
            }
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

    }

    @Override
    public void onLoadMoreRequested() {
        getData(true);
    }
}