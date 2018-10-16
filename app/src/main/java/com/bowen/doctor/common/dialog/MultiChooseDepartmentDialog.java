package com.bowen.doctor.common.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseDialog;
import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.utils.EmptyUtils;
import com.bowen.commonlib.utils.ScreenSizeUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.adapter.MultiChooseDepartmentAdapter;
import com.bowen.doctor.common.bean.network.Department;
import com.bowen.doctor.common.event.SelectedDepartmentListChangeEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by zhuzhipeng on 2018/7/4.
 * description:多选科室
 */

public class MultiChooseDepartmentDialog extends BaseDialog {
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mTitleTv)
    TextView mTitleTv;

    private ArrayList<Department> list;
    private ArrayList<Department> mSelectList;
    private String titleStr;
    private String selectStr = "";
    private MultiChooseDepartmentAdapter mAdapter;

    public MultiChooseDepartmentDialog(Context context) {
        super(context, R.style.dialog_transparent_style);
        list = new ArrayList<>();
    }

    public MultiChooseDepartmentDialog(Context context, ArrayList<Department> selectList) {
        super(context, R.style.dialog_transparent_style);
        list = new ArrayList<>();
        this.mSelectList = selectList;
    }

    public MultiChooseDepartmentDialog(Context context, int themeResId) {
        super(context, themeResId);
        list = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_choose_disease_name);
        getWindow().getAttributes().width = ScreenSizeUtil.getScreenWidth();
        getWindow().getAttributes().gravity = Gravity.BOTTOM;
        ButterKnife.bind(this);
        mAdapter = new MultiChooseDepartmentAdapter(mContext);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 4, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(list);
        if (mSelectList != null && mSelectList.size() > 0) {
            setSelectedList(mSelectList);
        }
        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mAdapter.isSelectedByPos(position)) {
                    mAdapter.setBooleanListByPos(position, false);
                } else {
                    mAdapter.setBooleanListByPos(position, true);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        if (EmptyUtils.isNotEmpty(titleStr)) {
            mTitleTv.setText(titleStr);
        }
        setCanceledOnTouchOutside(true);
    }

    @OnClick({R.id.submitBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submitBtn:
                dismiss();
                getSelectedId();
                break;
        }
    }

    public void getSelectedId() {
        ArrayList<Department> list = mAdapter.getSelectList();
        mListener.onDataCallBack(list);
    }

    public void setList(ArrayList<Department> mList) {
        this.list = mList;
    }

    public void setSelectedList(ArrayList<Department> mList) {
        //设置选中科室
        int i = 0;
        for (Department bean : (ArrayList<Department>) mAdapter.getData()) {
            for (Department bean1 : mList) {
                if (bean.getDepartmentsName().equals(bean1.getDepartmentsName())) {
                    mAdapter.setBooleanListByPos(i, true);
                }
            }
            i++;
        }
        mAdapter.notifyDataSetChanged();
    }

    public void setTitleStr(String mTitleStr) {
        this.titleStr = mTitleStr;
    }

    public void setSelectStr(String selectStr) {
        this.selectStr = selectStr;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SelectedDepartmentListChangeEvent event) {
        //设置选中科室
        int i = 0;
        for (Department bean : (ArrayList<Department>) mAdapter.getData()) {
            for (Department bean1 : event.getList()) {
                if (bean.getDepartmentsId().equals(bean1.getDepartmentsId())) {
                    mAdapter.setBooleanListByPos(i, true);
                }
            }
            i++;
        }
    }
}
