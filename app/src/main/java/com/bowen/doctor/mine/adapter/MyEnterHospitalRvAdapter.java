package com.bowen.doctor.mine.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.base.BaseViewHolder;
import com.bowen.commonlib.utils.ImageLoaderUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.bean.network.MyEnterHospitalBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的订单中rv的adapter
 * created by zhuzp at 2018/05/22
 */
public class MyEnterHospitalRvAdapter extends BaseQuickAdapter<MyEnterHospitalBean> {
    @BindView(R.id.myEnterHospitalNameTv)
    TextView myEnterHospitalNameTv;
    @BindView(R.id.myEnterHospitalLocationTv)
    TextView myEnterHospitalLocationTv;
    @BindView(R.id.myEnterHospitalImg)
    ImageView myEnterHospitalImg;

    public MyEnterHospitalRvAdapter(Context cxt) {
        super(cxt);
    }

    @Override
    protected int setItemLayout() {
        return R.layout.rv_item_my_enter_hospital;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MyEnterHospitalBean item, final int position) {
        ButterKnife.bind(this,helper.convertView);
        myEnterHospitalNameTv.setText(item.getHospitalName());
        myEnterHospitalLocationTv.setText(item.getAddress());
        ImageLoaderUtil.getInstance().show(helper.convertView.getContext(), item.getHospitalImgUrl(), myEnterHospitalImg, R.drawable.img_defalult_bg);
    }
}