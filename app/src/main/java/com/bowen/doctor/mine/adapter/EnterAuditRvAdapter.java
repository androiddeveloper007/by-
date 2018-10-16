package com.bowen.doctor.mine.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.base.BaseViewHolder;
import com.bowen.commonlib.utils.DateUtil;
import com.bowen.commonlib.utils.ImageLoaderUtil;
import com.bowen.commonlib.widget.CircleImageView;
import com.bowen.doctor.R;
import com.bowen.doctor.common.bean.network.MyEnterHospitalBean;
import com.bowen.doctor.mine.presenter.MyEnterAuditPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 入驻审核中rv的adapter
 * created by zhuzp at 2018/05/22
 */
public class EnterAuditRvAdapter extends BaseQuickAdapter<MyEnterHospitalBean> {
    @BindView(R.id.certificateTitleTimeText)
    TextView certificateTitleTimeText;
    @BindView(R.id.certificateTitleStateIcon)
    TextView certificateTitleStateIcon;
    @BindView(R.id.hospitalTv)
    TextView hospitalTv;
    @BindView(R.id.departmentTv)
    TextView departmentTv;
    @BindView(R.id.rejectBtn)
    TextView rejectBtn;
    @BindView(R.id.passBtn)
    TextView passBtn;
    @BindView(R.id.enterAuditDoctorImg)
    CircleImageView enterAuditDoctorImg;
    @BindView(R.id.enterAuditDoctorNameTv)
    TextView enterAuditDoctorNameTv;
    @BindView(R.id.enterAuditDoctorLevelTv)
    TextView enterAuditDoctorLevelTv;
    private final static String AUTH_IN = "1", AUTH_SUC = "2", AUTH_FAIL = "3", CANCLE = "4";
    private MyEnterAuditPresenter mPresenter;

    public EnterAuditRvAdapter(Context cxt, MyEnterAuditPresenter mPresenter) {
        super(cxt);
        this.mPresenter = mPresenter;
    }

    @Override
    protected int setItemLayout() {
        return R.layout.adapter_enter_audit;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MyEnterHospitalBean item, final int position) {
        ButterKnife.bind(this, helper.convertView);
        certificateTitleTimeText.setText(DateUtil.date2String(item.getCreateTime(), DateUtil.DEFAULT_FORMAT_DATETIME_WITHOUT_SECOND));
        switch (item.getAuthStatus()) {
            case AUTH_IN:
                certificateTitleStateIcon.setText("待审核");
                certificateTitleStateIcon.setTextColor(Color.parseColor("#f22727"));
                rejectBtn.setVisibility(View.VISIBLE);
                passBtn.setVisibility(View.VISIBLE);
                break;
            case AUTH_SUC:
                certificateTitleStateIcon.setText("审核通过");
                certificateTitleStateIcon.setTextColor(Color.parseColor("#14C251"));
                rejectBtn.setVisibility(View.GONE);
                passBtn.setVisibility(View.GONE);
                break;
            case AUTH_FAIL:
                certificateTitleStateIcon.setText("已驳回");
                certificateTitleStateIcon.setTextColor(Color.parseColor("#a4a4a4"));
                rejectBtn.setVisibility(View.GONE);
                passBtn.setVisibility(View.GONE);
                break;
            case CANCLE:
                certificateTitleStateIcon.setText("已取消");
                certificateTitleStateIcon.setTextColor(Color.parseColor("#a4a4a4"));
                rejectBtn.setVisibility(View.GONE);
                passBtn.setVisibility(View.GONE);
                break;
        }
        hospitalTv.setText(item.getAuthRemark());
        departmentTv.setText(item.getAuthRemark());
        ImageLoaderUtil.getInstance().show(helper.convertView.getContext(), item.getDoctorImgUrl(), enterAuditDoctorImg, R.drawable.doctor_default, true);
        if (!TextUtils.isEmpty(item.getDoctorName()))
            enterAuditDoctorNameTv.setText(item.getDoctorName());
        if (!TextUtils.isEmpty(item.getDoctorName()))
            enterAuditDoctorLevelTv.setText(item.getPositionStr());

        rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.handleEnterApply(false, item.getDoctorHospitalDeptId());
            }
        });
        passBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.handleEnterApply(true, item.getDoctorHospitalDeptId());
            }
        });
    }
}