package com.bowen.doctor.mine.adapter;

import android.content.Context;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.base.BaseViewHolder;
import com.bowen.commonlib.utils.ImageLoaderUtil;
import com.bowen.commonlib.widget.CircleImageView;
import com.bowen.doctor.R;
import com.bowen.doctor.common.bean.network.EmrDoctorFan;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的粉丝中rv的adapter
 * created by zhuzp at 2018/05/22
 */
public class MyFansRvAdapter extends BaseQuickAdapter<EmrDoctorFan> {
    @BindView(R.id.fanImg)
    CircleImageView fanImg;
    @BindView(R.id.fanNameTv)
    TextView fanNameTv;

    public MyFansRvAdapter(Context cxt) {
        super(cxt);
    }

    @Override
    protected int setItemLayout() {
        return R.layout.rv_item_my_fans;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final EmrDoctorFan item, final int position) {
        ButterKnife.bind(this, helper.convertView);
        ImageLoaderUtil.getInstance().show(helper.convertView.getContext(), item.getFileLink(), fanImg, R.drawable.default_user, true);
        fanNameTv.setText(item.getUserNickname());
    }
}