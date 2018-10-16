package com.bowen.doctor.common.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseDialog;
import com.bowen.commonlib.base.BaseQuickAdapter.OnRecyclerViewItemClickListener;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.EmptyUtils;
import com.bowen.commonlib.utils.ScreenSizeUtil;
import com.bowen.commonlib.utils.ToastUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.bean.network.SitClinic;
import com.bowen.doctor.common.dialog.adapter.ChooseSitClinicAdapter;
import com.bowen.doctor.homepage.model.OutpatientAppointmentModel;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Describe:选择家庭成员
 * Created by AwenZeng on 2018/4/9.
 */

public class ChooseSitClinicDialog extends BaseDialog {
    @BindView(R.id.mFinishTv)
    TextView mFinishTv;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    private ChooseSitClinicAdapter mAdapter;
    private OutpatientAppointmentModel mMemberModel;

    public ChooseSitClinicDialog(Context context) {
        super(context, R.style.dialog_transparent_style);
    }

    public ChooseSitClinicDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_choose_sit_clinic);
        ButterKnife.bind(this);
        getWindow().getAttributes().width = ScreenSizeUtil.getScreenWidth();
        getWindow().getAttributes().gravity = Gravity.BOTTOM;
        getWindow().setWindowAnimations(R.style.dialogAnim);  //添加动画
        mMemberModel = new OutpatientAppointmentModel(mContext);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new ChooseSitClinicAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
        setCanceledOnTouchOutside(true);
        mAdapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mAdapter.getItem(position).isChoose()) {
                    mAdapter.getItem(position).setChoose(false);
                    mAdapter.notifyDataSetChanged();
                } else {
                    mAdapter.getItem(position).setChoose(true);
                    mAdapter.notifyDataSetChanged();
                }
                changeData(false, mAdapter.getItem(position));
            }
        });

        getDoctoClinicList();
    }

    private void getDoctoClinicList() {
        mMemberModel.getDoctoClinicList(new HttpTaskCallBack<ArrayList<SitClinic>>() {
            @Override
            public void onSuccess(HttpResult<ArrayList<SitClinic>> result) {
                mAdapter.setNewData(result.getData());
                changeData(false, null);
            }

            @Override
            public void onFail(HttpResult<ArrayList<SitClinic>> result) {

            }
        });
    }

    @OnClick({R.id.mFinishTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mFinishTv:
                SitClinic familyMember = chooseMember();
                if (EmptyUtils.isNotEmpty(familyMember)) {
                    mListener.onDataCallBack(familyMember);
                    dismiss();
                } else {
                    ToastUtil.getInstance().showToastDialog("请选择坐诊医馆");
                }
                break;
        }
    }

    private void changeData(boolean isChoose, SitClinic item) {
        List<SitClinic> list = mAdapter.getData();
        if (EmptyUtils.isNotEmpty(item)) {
            for (SitClinic clinic : list) {
                if (!item.getHospital().equals(clinic.getHospital())) {
                    clinic.setChoose(false);
                }
            }
            mAdapter.setNewData(list);
        } else {
            for (SitClinic person : list) {
                person.setChoose(isChoose);
            }
            mAdapter.setNewData(list);
        }

    }

    private SitClinic chooseMember() {
        List<SitClinic> list = mAdapter.getData();
        for (SitClinic clinic : list) {
            if (clinic.isChoose()) {
                return clinic;
            }
        }
        return null;
    }
}
