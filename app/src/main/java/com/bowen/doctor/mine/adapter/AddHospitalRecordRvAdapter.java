package com.bowen.doctor.mine.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.base.BaseViewHolder;
import com.bowen.commonlib.utils.DateUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.bean.network.MyEnterHospitalBean;
import com.bowen.doctor.mine.presenter.AddHospitalRecordPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的订单中rv的adapter
 * created by zhuzp at 2018/05/22
 */
public class AddHospitalRecordRvAdapter extends BaseQuickAdapter<MyEnterHospitalBean> {
    @BindView(R.id.certificateTitleTimeText)
    TextView certificateTitleTimeText;
    @BindView(R.id.certificateTitleStateIcon)
    TextView certificateTitleStateIcon;
    @BindView(R.id.hospitalTv)
    TextView hospitalTv;
    @BindView(R.id.departmentTv)
    TextView departmentTv;
    @BindView(R.id.cancelApplyBtn)
    TextView cancelApplyBtn;
    @BindView(R.id.rejectReasonTv)
    TextView rejectReasonTv;
    @BindView(R.id.rejectReasonLayout)
    LinearLayout rejectReasonLayout;
    @BindView(R.id.rejectReasonTitleTv)
    TextView rejectReasonTitleTv;
    private final static String AUTH_IN = "1",AUTH_SUC = "2",AUTH_FAIL = "3",CANCLE = "4";
    private AddHospitalRecordPresenter mPresenter;

    public AddHospitalRecordRvAdapter(Context cxt, AddHospitalRecordPresenter mPresenter) {
        super(cxt);
        this.mPresenter = mPresenter;
    }

    @Override
    protected int setItemLayout() {
        return R.layout.rv_item_add_hospital_record;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MyEnterHospitalBean item, final int position) {
        ButterKnife.bind(this,helper.convertView);
        certificateTitleTimeText.setText(DateUtil.date2String(item.getCreateTime(), DateUtil.DEFAULT_FORMAT_DATETIME_WITHOUT_SECOND));
        //AUTH_IN("1", "待审核"),AUTH_SUC("2", "审核通过"),AUTH_FAIL("3", "已驳回"),CANCLE("4", "已取消")
        switch (item.getAuthStatus()){
            case AUTH_IN:
                certificateTitleStateIcon.setText("待审核");
                certificateTitleStateIcon.setTextColor(Color.parseColor("#f22727"));
                cancelApplyBtn.setVisibility(View.VISIBLE);
                rejectReasonLayout.setVisibility(View.GONE);
                rejectReasonTitleTv.setVisibility(View.GONE);
                break;
            case AUTH_SUC:
                rejectReasonTitleTv.setVisibility(View.GONE);
                certificateTitleStateIcon.setText("审核通过");
                certificateTitleStateIcon.setTextColor(Color.parseColor("#14C251"));
                cancelApplyBtn.setVisibility(View.GONE);
                rejectReasonLayout.setVisibility(View.GONE);
                break;
            case AUTH_FAIL:
                rejectReasonTitleTv.setVisibility(View.VISIBLE);
                certificateTitleStateIcon.setText("已驳回");
                certificateTitleStateIcon.setTextColor(Color.parseColor("#a4a4a4"));
                cancelApplyBtn.setVisibility(View.GONE);
                rejectReasonLayout.setVisibility(View.GONE);
                break;
            case CANCLE:
                rejectReasonTitleTv.setVisibility(View.GONE);
                certificateTitleStateIcon.setText("已取消");
                certificateTitleStateIcon.setTextColor(Color.parseColor("#a4a4a4"));
                cancelApplyBtn.setVisibility(View.GONE);
                rejectReasonLayout.setVisibility(View.VISIBLE);

                break;
        }
        hospitalTv.setText(item.getHospitalName());
        departmentTv.setText(item.getDepartmentsName());
        rejectReasonTv.setText(item.getAuthRemark());
        cancelApplyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.cancelApplyToEnter(item.getDoctorHospitalDeptId());
            }
        });
    }
}