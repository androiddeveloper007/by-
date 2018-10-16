package com.bowen.doctor.mine.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bowen.commonlib.utils.DateUtil;
import com.bowen.commonlib.utils.RouterActivityUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseActivity;
import com.bowen.doctor.common.bean.network.Withdraw;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhuzhipeng
 * @time 2018/8/19 13:08
 * 提现成功，信息展示页面
 */
public class WithdrawSuccessActivity extends BaseActivity {
    @BindView(R.id.regardBankCardNoTv)
    TextView regardBankCardNoTv;
    @BindView(R.id.withdrawAmountTv)
    TextView withdrawAmountTv;
    @BindView(R.id.applyTimeTv)
    TextView applyTimeTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_withdraw_success);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public void onRightButtonPressed() {
        super.onRightButtonPressed();
        finish();
    }

    private void init() {
        setTitle("提现结果");
        getTitleBar().getRightTextButton().setVisibility(View.VISIBLE);
        getTitleBar().getRightTextButton().setText("完成");
        if(RouterActivityUtil.getSerializable(this)!=null){
            Withdraw bean = (Withdraw) RouterActivityUtil.getSerializable(this);
            if(bean!=null){
                regardBankCardNoTv.setText(bean.getWithdrawBankCardNo());
                withdrawAmountTv.setText("￥"+bean.getApplyAmount());
                applyTimeTv.setText(DateUtil.date2String(bean.getApplyTime(), DateUtil.DEFAULT_FORMAT_DATETIME_WITHOUT_SECOND));
            }
        }
    }
}