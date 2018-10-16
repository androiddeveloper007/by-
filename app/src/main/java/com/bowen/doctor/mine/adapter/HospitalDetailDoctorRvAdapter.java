package com.bowen.doctor.mine.adapter;

import android.content.Context;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.base.BaseViewHolder;
import com.bowen.commonlib.utils.ImageLoaderUtil;
import com.bowen.commonlib.widget.CircleImageView;
import com.bowen.doctor.R;
import com.bowen.doctor.common.bean.network.EmrDoctorListBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 名医馆详情中的医生列表rv的adapter
 * created by zhuzp at 2018/05/22
 */
public class HospitalDetailDoctorRvAdapter extends BaseQuickAdapter<EmrDoctorListBean> {
    @BindView(R.id.doctorImg)
    CircleImageView doctorImg;
    @BindView(R.id.doctorNameTv)
    TextView doctorNameTv;
    @BindView(R.id.departmentTv)
    TextView departmentTv;
    @BindView(R.id.doctorLevelTv)
    TextView doctorLevelTv;
    @BindView(R.id.doctorGoodAtTv)
    TextView doctorGoodAtTv;
    public MySubscribeConsultClick mSubscribeConsultClick;

    public HospitalDetailDoctorRvAdapter(Context cxt) {
        super(cxt);
    }

    public HospitalDetailDoctorRvAdapter(Context cxt, boolean isPreviewMode) {
        super(cxt);
    }

    @Override
    protected int setItemLayout() {
        return R.layout.adapter_hospital_detail_doctor;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final EmrDoctorListBean item, final int position) {
        ButterKnife.bind(this, helper.convertView);
        ImageLoaderUtil.getInstance().show(doctorImg.getContext(), item.getHeadImgUrl(), doctorImg, R.drawable.doctor_default, true);
        doctorNameTv.setText(item.getName());
        departmentTv.setText(item.getHospitalDepartments());
        doctorLevelTv.setText(item.getPositionStr());
        doctorGoodAtTv.setText("擅长："+item.getDiseases());
    }

    public interface MySubscribeConsultClick{
        void mySubscribeConsult(int pos);
    }
    public void setOnSubscribeConsultListener (MySubscribeConsultClick listener) {
        mSubscribeConsultClick = listener;
    }
}
