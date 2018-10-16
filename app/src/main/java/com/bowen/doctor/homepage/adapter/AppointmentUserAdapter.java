package com.bowen.doctor.homepage.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.base.BaseViewHolder;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.ImageLoaderUtil;
import com.bowen.commonlib.widget.CircleImageView;
import com.bowen.doctor.R;
import com.bowen.doctor.common.bean.network.AppointmentUser;
import com.bowen.doctor.common.model.Constants;
import com.bowen.doctor.homepage.model.OutpatientAppointmentModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 门诊预约时间段人数rv的adapter
 * created by zhuzp at 2018/05/22
 */
public class AppointmentUserAdapter extends BaseQuickAdapter<AppointmentUser> {

    @BindView(R.id.mHeadPortraitImg)
    CircleImageView mHeadPortraitImg;
    @BindView(R.id.mUserNameTv)
    TextView mUserNameTv;
    @BindView(R.id.mPeroidTimeTv)
    TextView mPeroidTimeTv;
    @BindView(R.id.mTreatmentStatusTv)
    TextView mTreatmentStatusTv;

    private OutpatientAppointmentModel mOutpatientAppointmentModel;

    public AppointmentUserAdapter(Context cxt) {
        super(cxt);
        mOutpatientAppointmentModel = new OutpatientAppointmentModel(mContext.get());
    }

    @Override
    protected int setItemLayout() {
        return R.layout.adapter_outpateint_appointment_user;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final AppointmentUser item, final int position) {
        ButterKnife.bind(this, helper.convertView);
        ImageLoaderUtil.getInstance().show(mContext.get(), item.getFileLink(), mHeadPortraitImg, R.drawable.default_user, true);
        mUserNameTv.setText(item.getUserNickname());
        mPeroidTimeTv.setText("预约时段 " + item.getTypeName());
        switch (item.getAppointmentStatus()) {
            case Constants.STATUS_TREATEMENT_WAIT:
                mTreatmentStatusTv.setText("待就诊");
                mTreatmentStatusTv.setBackgroundResource(R.drawable.button_main_rectangle_red);
                break;
            case Constants.STATUS_TREATEMENT_ALREADY:
                mTreatmentStatusTv.setText("已就诊");
                mTreatmentStatusTv.setBackgroundResource(R.drawable.button_main_03);
                break;
        }
        mTreatmentStatusTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getAppointmentStatus() == Constants.STATUS_TREATEMENT_WAIT) {
                    setTreatementStatus(item, position);
                }
            }
        });

    }


    private void setTreatementStatus(final AppointmentUser item, final int pos) {
        mOutpatientAppointmentModel.setTreatementStatus(item.getAppointmentOrderId(), new HttpTaskCallBack<Boolean>() {
            @Override
            public void onSuccess(HttpResult<Boolean> result) {
                if (result.getData()) {
                    item.setAppointmentStatus(Constants.STATUS_TREATEMENT_ALREADY);
                    getData().set(pos, item);
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFail(HttpResult<Boolean> result) {

            }
        });
    }
}