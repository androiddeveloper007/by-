package com.bowen.doctor.mine.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.base.BaseViewHolder;
import com.bowen.commonlib.utils.ImageLoaderUtil;
import com.bowen.commonlib.widget.CircleImageView;
import com.bowen.doctor.R;
import com.bowen.doctor.common.bean.network.MyOrder;
import com.bowen.doctor.common.model.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的订单中rv的adapter
 * created by zhuzp at 2018/05/22
 */
public class MyOrderRvAdapter extends BaseQuickAdapter<MyOrder> {
    @BindView(R.id.orderNumTv)
    TextView orderNumTv;
    @BindView(R.id.orderStatusTv)
    TextView orderStatusTv;
    @BindView(R.id.orderTypeTv)
    TextView orderTypeTv;
    @BindView(R.id.orderFeeTv)
    TextView orderFeeTv;
    @BindView(R.id.doctorImg)
    CircleImageView doctorImg;
    @BindView(R.id.doctorNameTv)
    TextView doctorNameTv;
    @BindView(R.id.orderHasPayTv)
    TextView orderHasPayTv;
    @BindView(R.id.orderPassAwayTv)
    TextView orderPassAwayTv;
    public OrderPassAwayListener listener;

    public MyOrderRvAdapter(Context cxt) {
        super(cxt);
    }

    @Override
    protected int setItemLayout() {
        return R.layout.rv_item_my_order;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MyOrder item, final int position) {
        ButterKnife.bind(this, helper.convertView);
        orderNumTv.setText(item.getOrderId());
        orderStatusTv.setText(item.getOrderStatusStr());
        orderTypeTv.setText(item.getOrderTypeStr());//1：图文问诊，2：送心意，3：门诊预约
        switch (item.getOrderType()) {
            case Constants.ORDER_TYPE_PICTURE_COMMEND:
                ImageLoaderUtil.getInstance().setTextViewDrawableLeft(orderFeeTv.getContext(), orderTypeTv, R.drawable.picture_word_comment);
                break;
//            case Constants.ORDER_TYPE_SEND_GIFT:
//                ImageLoaderUtil.getInstance().setTextViewDrawableLeft(orderFee.getContext(), orderType, R.drawable.user_mind);
//                break;
            case Constants.ORDER_TYPE_OUTPATIENT_APPOINTMENT:
                ImageLoaderUtil.getInstance().setTextViewDrawableLeft(orderFeeTv.getContext(), orderTypeTv, R.drawable.outpatient_appointment);
                break;
        }
        orderFeeTv.setText("￥" + item.getTotalAmount());
        doctorNameTv.setText(item.getUserNickname());
        ImageLoaderUtil.getInstance().show(mContext.get(), item.getUserImg(), doctorImg, R.drawable.default_user, true);
        orderHasPayTv.setText("￥" + item.getRealAmount());
        switch (item.getOrderStatus()) {//1：待支付，2已支付，3：已取消，4：已结束，5：已删除
            case Constants.PAY_ORDER_STATUS_FINISH:
                orderStatusTv.setTextColor(Color.parseColor("#14C251"));
                orderPassAwayTv.setVisibility(TextUtils.equals(item.getOrderType(), "1") ? View.VISIBLE : View.GONE);
                break;
            case Constants.PAY_ORDER_STATUS_OVER:
                orderStatusTv.setTextColor(Color.parseColor("#a4a4a4"));
                orderPassAwayTv.setVisibility(View.GONE);
                break;
            case Constants.PAY_ORDER_STATUS_COMMENT:
                orderStatusTv.setTextColor(Color.parseColor("#a4a4a4"));
                orderPassAwayTv.setVisibility(View.GONE);
                break;
        }
        orderPassAwayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) listener.orderPassAway(position);
            }
        });
    }

    public void setOrderPassAwayListener(OrderPassAwayListener listener) {
        this.listener = listener;
    }

    public interface OrderPassAwayListener {
        void orderPassAway(int pos);
    }
}
