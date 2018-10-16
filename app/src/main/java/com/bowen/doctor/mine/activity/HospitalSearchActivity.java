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
import com.bowen.doctor.common.base.BaseActivity;
import com.bowen.doctor.common.bean.Page;
import com.bowen.doctor.common.bean.network.EmrHospitalBean;
import com.bowen.doctor.common.bean.network.SearchHospitalRecord;
import com.bowen.doctor.common.event.SelectHospitalClickEvent;
import com.bowen.doctor.common.widget.SearchActionBar;
import com.bowen.doctor.common.widget.SideBar;
import com.bowen.doctor.mine.adapter.HospitalSearchRvAdapter;
import com.bowen.doctor.mine.contract.HospitalSearchContract;
import com.bowen.doctor.mine.presenter.HospitalSearchPresenter;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Describe:选择医馆，搜索页面
 * Created by zzp on 2018/8/8.
 */
public class HospitalSearchActivity extends BaseActivity implements HospitalSearchContract.View ,BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.searchActionBar)
    SearchActionBar searchActionBar;
    @BindView(R.id.hospitalRv)
    RecyclerView hospitalRv;
    @BindView(R.id.searchHospitalSideBar)
    SideBar searchHospitalSideBar;
    @BindView(R.id.mDialogTv)
    TextView mDialogTv;
    private HospitalSearchPresenter mPresenter;
    private Context mContext;
    private HospitalSearchRvAdapter mAdapter;
    private View emptyView;
    private int page = 1;
    private final int pageSize = 50;
    private boolean isMore = false;
    private boolean isLoadMore = false;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_search_hospital);
        ButterKnife.bind(this);
        mContext = this;
        mPresenter = new HospitalSearchPresenter(this, this);
        initView();
        getData(false);
    }

    private void initView() {
        searchActionBar.setEditTextHint("输入医馆名称关键词查找");
        searchActionBar.getRightTextButton().setVisibility(View.VISIBLE);
        searchActionBar.getRightTextButton().setText("取消");
        searchActionBar.getRightTextButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ApplicationUtils.isKeyboardOpen()) {
                    hospitalRv.requestFocus();
                    ApplicationUtils.closeKeyboard(searchActionBar.action_bar_edit_text);
                }
                onBackPressed();
            }
        });
        searchActionBar.action_bar_edit_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (searchActionBar.action_bar_edit_text.getText().length() > 0) {
                        getData(false);
                    }
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
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        hospitalRv.setLayoutManager(mLayoutManager);
        mAdapter = new HospitalSearchRvAdapter(mContext);
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
        hospitalRv.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                finish();
                EventBus.getDefault().post(new SelectHospitalClickEvent((EmrHospitalBean) mAdapter.getData().get(position)));
            }
        });
        hospitalRv.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {//触摸到列表时，隐藏键盘
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                if (e.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    if (ApplicationUtils.isKeyboardOpen()) {
                        hospitalRv.requestFocus();
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
        hospitalRv.setOnTouchListener(new View.OnTouchListener() {//收起键盘
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    if (ApplicationUtils.isKeyboardOpen()) {
                        hospitalRv.requestFocus();
                        ApplicationUtils.closeKeyboard(searchActionBar.action_bar_edit_text);
                    }
                }
                return false;
            }
        });
        mAdapter.setOnLoadMoreListener(this);
        searchHospitalSideBar.setTextView(mDialogTv);
        //设置右侧触摸监听
        searchHospitalSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = mAdapter.getPositionForSection(s.charAt(0));
                if(position != -1){
                    moveToPosition(position);
                }
            }
        });
    }

    private boolean move = false;
    private int mIndex = 0;
    private void moveToPosition(int n) {
        int firstItem = mLayoutManager.findFirstVisibleItemPosition();
        int lastItem = mLayoutManager.findLastVisibleItemPosition();
        if (n <= firstItem ){
            hospitalRv.scrollToPosition(n);
        }else if ( n <= lastItem ){
            int top = hospitalRv.getChildAt(n - firstItem).getTop();
            hospitalRv.scrollBy(0, top);
        }else{
            hospitalRv.scrollToPosition(n);
            move = true;
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
    public void onLoadSuccess(SearchHospitalRecord record) {
        Page pageBean = record.getPage();
        if (isMore) {
            mAdapter.notifyDataChangedAfterLoadMore(record.getEmrHospitalList(), true);
        } else {
            mAdapter.setNewData(record.getEmrHospitalList());
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
        if(record.getEmrHospitalList()!=null && record.getEmrHospitalList().size()==0){
            if(emptyView == null) {
                emptyView = getLayoutInflater().inflate(R.layout.viewstub_no_folk_prescription, (ViewGroup) findViewById(android.R.id.content), false);
                mAdapter.setEmptyView(true, emptyView);
            }
        }
    }

    @Override
    public void onLoadFail() {

    }

    @Override
    public void onLoadMoreRequested() {
        getData(true);
    }
}