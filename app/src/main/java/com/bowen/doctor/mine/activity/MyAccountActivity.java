package com.bowen.doctor.mine.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bowen.commonlib.utils.RouterActivityUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseActivity;
import com.bowen.doctor.common.bean.network.BindBankCardBean;
import com.bowen.doctor.common.bean.network.MyAccount;
import com.bowen.doctor.common.model.Constants;
import com.bowen.doctor.mine.contract.MyAccountContract;
import com.bowen.doctor.mine.presenter.MyAccountPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhuzhipeng
 * @time 2018/7/11 16:08
 * 我的账户
 */
public class MyAccountActivity extends BaseActivity implements MyAccountContract.View {
    @BindView(R.id.totalAmountTv)
    TextView totalAmountTv;
    @BindView(R.id.availableAmountTv)
    TextView availableAmountTv;
    @BindView(R.id.availableRemainingTv)
    TextView availableRemainingTv;
    private MyAccountPresenter mPresenter;
    private boolean isOnCreate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_my_account);
        ButterKnife.bind(this);
        setTitle("我的账户");
        mPresenter = new MyAccountPresenter(this, this);
        mPresenter.loadData();
    }

    @OnClick({R.id.withdraw, R.id.withdrawRecord})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.withdraw:
                mPresenter.checkBindCard(true);
                break;
            case R.id.withdrawRecord:
                mPresenter.checkBindCard(false);
                break;
        }
    }

    private void init(MyAccount list) {
        totalAmountTv.setText("￥"+list.getTotalAmount());
        availableAmountTv.setText("￥"+list.getUsable());
        availableRemainingTv.setText("￥"+list.getBackable());
    }

    @Override
    public void onLoadSuccess(MyAccount list) {
        init(list);
    }

    @Override
    public void onLoadFail() {

    }

    @Override
    public void onLoadBindCardSuccess(BindBankCardBean bean, boolean isWithdrawBtn) {
        String bindCard = bean.getBankStatus();
        if(TextUtils.equals(bindCard, Constants.HAS_NOT_BIND_CARD)){
            RouterActivityUtil.startActivity(MyAccountActivity.this, BindBankCardActivity.class);
        }else if(TextUtils.equals(bindCard, Constants.HAS_BIND_CARD)){
            if(isWithdrawBtn) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(RouterActivityUtil.FROM_TAG, bean);
                RouterActivityUtil.startActivity(MyAccountActivity.this, BalanceWithdrawActivity.class, bundle);
            }else{
                RouterActivityUtil.startActivity(MyAccountActivity.this, WithdrawRecordActivity.class);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mPresenter!=null && !isOnCreate){
            mPresenter.loadData();
        }
        isOnCreate = false;
    }
}
