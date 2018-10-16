package com.bowen.doctor.common.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseDialog;
import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.utils.EmptyUtils;
import com.bowen.commonlib.utils.ScreenSizeUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.adapter.ChooseContentAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 选择科室对话框
 * Created by zzp on 2016/12/13.
 */
public class ChooseDepartmentDialog extends BaseDialog {
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mTitleTv)
    TextView mTitleTv;
    private ArrayList<String> list;
    private String titleStr;
    private String selectStr = "";
    private ChooseContentAdapter mAdapter;
    private String selectDepartmentName;

    public ChooseDepartmentDialog(Context context) {
        super(context, R.style.dialog_transparent_style);
        list = new ArrayList<>();
    }

    public ChooseDepartmentDialog(Context context, String departmentName) {
        super(context, R.style.dialog_transparent_style);
        list = new ArrayList<>();
        selectDepartmentName = departmentName;
    }

    public ChooseDepartmentDialog(Context context, int themeResId) {
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
        mAdapter = new ChooseContentAdapter(mContext);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 4, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(list);
        if(selectDepartmentName!=null && selectDepartmentName.length()>0){
            mAdapter.setChoosePosByName(selectDepartmentName);
        }
        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String temp = list.get(position);
                mListener.onDataCallBack(temp, position);
                mAdapter.setChoosePos(position);
                dismiss();
            }
        });
        if(EmptyUtils.isNotEmpty(titleStr)){
            mTitleTv.setText(titleStr);
        }
        setCanceledOnTouchOutside(true);
        Window window = getWindow();
        WindowManager.LayoutParams attributesParams = window.getAttributes();
        attributesParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        attributesParams.dimAmount = 0.4f;
        int sreemWidth = window.getWindowManager().getDefaultDisplay().getWidth();
        int windowWidth = sreemWidth * 1;
        window.setLayout(windowWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @OnClick({R.id.submitBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submitBtn:
                dismiss();
                break;
        }
    }

    public void setSelectPosition(String name) {
        if(mAdapter!=null){
            mAdapter.setChoosePosByName(name);
        }
    }

    public void setList(ArrayList<String> mList) {
        this.list = mList;
    }

    public void setTitleStr(String mTitleStr) {
        this.titleStr = mTitleStr;
    }

    public void setSelectStr(String selectStr){
        this.selectStr = selectStr;
    }
}
