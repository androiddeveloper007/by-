package com.bowen.doctor.mine.adapter;

import android.content.Context;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.base.BaseViewHolder;
import com.bowen.doctor.R;
import com.bowen.doctor.common.bean.network.MyHospitalBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的医馆rv的adapter
 * created by zhuzp at 2018/05/22
 */
public class MyHospitalRvAdapter extends BaseQuickAdapter<MyHospitalBean> {
    @BindView(R.id.myHospitalApplyTimeTv)
    TextView myHospitalApplyTimeTv;
    @BindView(R.id.applyStatusTv)
    TextView applyStatusTv;
    @BindView(R.id.hospitalNameTv)
    TextView hospitalNameTv;
    @BindView(R.id.hospitalAddressTv)
    TextView hospitalAddressTv;

    public MyHospitalRvAdapter(Context cxt) {
        super(cxt);
    }

    @Override
    protected int setItemLayout() {
        return R.layout.rv_item_my_hospital;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MyHospitalBean item, final int position) {
        ButterKnife.bind(this, helper.convertView);
        myHospitalApplyTimeTv.setText(item.getAddressStr());
        applyStatusTv.setText(item.getAddressStr());
        hospitalNameTv.setText(item.getAddressStr());
        hospitalAddressTv.setText(item.getAddressStr());
    }
}