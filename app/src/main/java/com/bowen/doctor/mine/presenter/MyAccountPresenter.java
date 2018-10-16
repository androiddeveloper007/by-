package com.bowen.doctor.mine.presenter;

import android.content.Context;

import com.bowen.commonlib.base.BasePresenter;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.doctor.common.bean.network.BindBankCardBean;
import com.bowen.doctor.common.bean.network.MyAccount;
import com.bowen.doctor.mine.contract.MyAccountContract;
import com.bowen.doctor.mine.model.MineModel;
import com.bowen.doctor.mine.model.MyAccountModel;

/**
 * Created by zzp
 * 我的账户
 */
public class MyAccountPresenter extends BasePresenter {

    private MyAccountModel myAccountModel;
    private MineModel mineModel;
    private MyAccountContract.View mView;

    public MyAccountPresenter(Context context, MyAccountContract.View view) {
        super(context);
        myAccountModel = new MyAccountModel(context);
        mineModel = new MineModel(context);
        mContext = context;
        mView = view;
    }

    public void loadData() {
        myAccountModel.loadData(new HttpTaskCallBack<MyAccount>() {
            @Override
            public void onSuccess(HttpResult<MyAccount> result) {
                mView.onLoadSuccess(result.getData());
            }

            @Override
            public void onFail(HttpResult<MyAccount> result) {
                mView.onLoadFail();
            }
        });
    }

    /**
     * 检查是否绑卡
     * @param isWithdrawBtn 是否点击提现按钮
     */
    public void checkBindCard(final boolean isWithdrawBtn) {
        mineModel.checkBindCard(new HttpTaskCallBack<BindBankCardBean>() {
            @Override
            public void onSuccess(HttpResult<BindBankCardBean> result) {
                mView.onLoadBindCardSuccess(result.getData(), isWithdrawBtn);
            }

            @Override
            public void onFail(HttpResult<BindBankCardBean> result) {

            }
        });
    }
}
