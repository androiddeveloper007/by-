package com.bowen.doctor.mine.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bowen.commonlib.utils.RouterActivityUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseActivity;
import com.bowen.doctor.common.bean.network.BindBankCardBean;
import com.bowen.doctor.common.bean.network.Withdraw;
import com.bowen.doctor.common.util.AvoidFastClickUtil;
import com.bowen.doctor.mine.contract.BalanceWithdrawContract;
import com.bowen.doctor.mine.presenter.BalanceWithdrawPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Describe:余额提现
 * Created by zhuzhipeng on 2018/6/29.
 */
public class BalanceWithdrawActivity extends BaseActivity implements BalanceWithdrawContract.View {
    @BindView(R.id.bankcardTv)
    TextView bankcardTv;
    @BindView(R.id.withdrawInputEdit)
    EditText withdrawInputEdit;
    @BindView(R.id.withdrawAvailableAmountTv)
    TextView withdrawAvailableAmountTv;
    private Activity mActivity;
    private BindBankCardBean bindCardBean;
    private BalanceWithdrawPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_balance_withdraw);
        ButterKnife.bind(this);
        mActivity = this;
        setTitle("提现");
        getTitleBar().getRightButton().setVisibility(View.VISIBLE);
        getTitleBar().getRightButton().setBackgroundResource(R.drawable.withdraw_record);
        mPresenter = new BalanceWithdrawPresenter(this, this);
        if (RouterActivityUtil.getBundle(this) != null) {
            bindCardBean = (BindBankCardBean) RouterActivityUtil.getSerializable(this);
            bankcardTv.setText(mPresenter.replaceWithStar(bindCardBean.getBankCard()));
            withdrawAvailableAmountTv.setText(bindCardBean.getUsable());
        }
    }

    @Override
    public void onRightButtonPressed() {
        super.onRightButtonPressed();
        RouterActivityUtil.startActivity(mActivity, WithdrawRecordActivity.class);
    }

    @OnClick({R.id.withdrawAll, R.id.withdrawBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.withdrawAll:
                if (bindCardBean != null) {
                    double usableAmount = Double.parseDouble(bindCardBean.getUsable());
                    if (usableAmount > 0) {
                        withdrawInputEdit.setText(bindCardBean.getUsable());
                    }
                }
                break;
            case R.id.withdrawBtn:
                if (!AvoidFastClickUtil.isFastClick()) {
                    mPresenter.applyWithdraw(withdrawInputEdit.getText().toString());
                }
                break;
        }
    }

    @Override
    public void onUploadSuccess() {
        Withdraw bean = new Withdraw();
        String amountStr = withdrawInputEdit.getText().toString();
        double amount = Double.parseDouble(amountStr);
        bean.setApplyAmount(amount);
        bean.setApplyTime(System.currentTimeMillis());
        if (bindCardBean != null) {
            bean.setWithdrawBankCardNo(mPresenter.replaceWithStar(bindCardBean.getBankCard()));
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(RouterActivityUtil.FROM_TAG, bean);
        RouterActivityUtil.startActivity(mActivity, WithdrawSuccessActivity.class, bundle, true);
    }

    @Override
    public void onUploadFailed() {

    }
}