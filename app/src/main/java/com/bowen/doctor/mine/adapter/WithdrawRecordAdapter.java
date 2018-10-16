package com.bowen.doctor.mine.adapter;

import android.content.Context;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.base.BaseViewHolder;
import com.bowen.commonlib.utils.DateUtil;
import com.bowen.commonlib.utils.ImageLoaderUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.bean.network.Withdraw;
import com.bowen.doctor.common.model.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 提现记录中rv的adapter
 * created by zhuzp at 2018/05/22
 */
public class WithdrawRecordAdapter extends BaseQuickAdapter<Withdraw> {
    @BindView(R.id.withdrawTitleTv)
    TextView withdrawTitleTv;
    @BindView(R.id.withdrawTimeTv)
    TextView withdrawTimeTv;
    @BindView(R.id.withdrawAmountTv)
    TextView withdrawAmountTv;
    @BindView(R.id.withdrawUsableTv)
    TextView withdrawUsableTv;

    public WithdrawRecordAdapter(Context cxt) {
        super(cxt);
    }

    @Override
    protected int setItemLayout() {
        return R.layout.rv_item_withdraw_record;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final Withdraw item, final int position) {
        ButterKnife.bind(this, helper.convertView);
        Context mContext = withdrawTitleTv.getContext();
        switch (item.getWithdrawStatus()) {
            case Constants.WITHDRAW_WAIT:
                withdrawTitleTv.setText("申请中");
                ImageLoaderUtil.getInstance().setTextViewDrawableLeft(mContext, withdrawTitleTv, R.drawable.withdraw_wait);
                break;
            case Constants.WITHDRAW_SUCCESS:
                withdrawTitleTv.setText("成功");
                ImageLoaderUtil.getInstance().setTextViewDrawableLeft(mContext, withdrawTitleTv, R.drawable.withdraw_success);
                break;
            case Constants.WITHDRAW_FAIL:
                withdrawTitleTv.setText("失败");
                ImageLoaderUtil.getInstance().setTextViewDrawableLeft(mContext, withdrawTitleTv, R.drawable.withdraw_fail);
                break;
        }
        withdrawTimeTv.setText(DateUtil.date2String(item.getApplyTime(), DateUtil.DEFAULT_FORMAT_DATETIME_WITHOUT_SECOND));
        withdrawAmountTv.setText("- " + item.getApplyAmount());
        withdrawUsableTv.setText("余额 " + item.getUseable());
    }

}
