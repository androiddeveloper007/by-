package com.bowen.doctor.homepage.adapter;

import android.content.Context;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.base.BaseViewHolder;
import com.bowen.doctor.R;
import com.bowen.doctor.common.bean.network.FolkPrescription;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 在线偏方fragment中rv的adapter
 * created by zhuzhipeng at 2018/05/22
 */
public class OnlineFolkPrescriptionRvAdapter extends BaseQuickAdapter<FolkPrescription> {
    @BindView(R.id.onlineFolkPrescriptionNameTv)
    TextView onlineFolkPrescriptionNameTv;
    @BindView(R.id.fitPeopleTv)
    TextView fitPeopleTv;
    @BindView(R.id.fitDiseaseTv)
    TextView fitDiseaseTv;
    @BindView(R.id.introduceTv)
    TextView introduceTv;

    public OnlineFolkPrescriptionRvAdapter(Context cxt) {
        super(cxt);
    }

    @Override
    protected int setItemLayout() {
        return R.layout.rv_item_online_folk_prescription;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final FolkPrescription item, final int position) {
        ButterKnife.bind(this,helper.convertView);
        onlineFolkPrescriptionNameTv.setText(item.getPrescriptionName());
        fitPeopleTv.setText(item.getApplyCrowdStr());
        fitDiseaseTv.setText(item.getApplyDisease());
        introduceTv.setText(item.getUsageDosage());
    }
}
