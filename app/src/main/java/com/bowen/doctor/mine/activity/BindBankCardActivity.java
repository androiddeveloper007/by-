package com.bowen.doctor.mine.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.bowen.commonlib.utils.RouterActivityUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseActivity;
import com.bowen.doctor.common.bean.network.BindBankCardBean;
import com.bowen.doctor.common.bean.network.DoctorInfo;
import com.bowen.doctor.common.model.Constants;
import com.bowen.doctor.common.util.AvoidFastClickUtil;
import com.bowen.doctor.mine.contract.BindBankCardContract;
import com.bowen.doctor.mine.presenter.BindBankCardPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhuzhipeng
 * @time 2018/8/19 13:08
 * 绑卡
 */
public class BindBankCardActivity extends BaseActivity implements BindBankCardContract.View {
    @BindView(R.id.bindCardNameTv)
    TextView bindCardNameTv;
    @BindView(R.id.idCardEdit)
    EditText idCardEdit;
    @BindView(R.id.bankCardEdit)
    EditText bankCardEdit;
    private BindBankCardPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_bind_bank);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setTitle("实名绑卡");
        bindCardNameTv.setText(DoctorInfo.getInstance().getName());
    }

    @OnClick(R.id.mSubmit)
    public void onViewClicked() {
        if(mPresenter==null)
            mPresenter = new BindBankCardPresenter(this, this);
        if(!AvoidFastClickUtil.isFastClick())
            mPresenter.bindCard(idCardEdit.getText().toString(), bankCardEdit.getText().toString());
    }

    @Override
    public void onUploadSuccess() {
        mPresenter.checkBindCard();
    }

    @Override
    public void onUploadFailed() {

    }

    @Override
    public void onLoadBindCardSuccess(BindBankCardBean bean) {
        String bindCard = bean.getBankStatus();
        if(TextUtils.equals(bindCard, Constants.HAS_BIND_CARD)){
            Bundle bundle = new Bundle();
            bundle.putSerializable(RouterActivityUtil.FROM_TAG, bean);
            RouterActivityUtil.startActivity(BindBankCardActivity.this, BalanceWithdrawActivity.class, bundle,true);
        }else{
            finish();
        }
    }

}
