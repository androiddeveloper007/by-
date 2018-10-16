package com.bowen.doctor.common.dialog.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.base.BaseViewHolder;
import com.bowen.doctor.R;
import com.bowen.doctor.common.bean.network.SitClinic;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Describe:
 * Created by AwenZeng on 2018/4/9.
 */

public class ChooseSitClinicAdapter extends BaseQuickAdapter<SitClinic> {

    @BindView(R.id.mChooseImg)
    ImageView mChooseImg;
    @BindView(R.id.mClinicNameTv)
    TextView mClinicNameTv;

    public ChooseSitClinicAdapter(Context cxt) {
        super(cxt);
    }

    @Override
    protected int setItemLayout() {
        return R.layout.adapter_choose_sit_clinic;
    }

    @Override
    protected void convert(BaseViewHolder helper, SitClinic item, int position) {
        ButterKnife.bind(this, helper.convertView);
        mChooseImg.setSelected(item.isChoose());
        mClinicNameTv.setText(item.getHospital());
    }
}
