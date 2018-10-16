package com.bowen.doctor.mine.presenter;

import android.content.Context;

import com.bowen.commonlib.base.BasePresenter;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.doctor.common.bean.network.BindBankCardBean;
import com.bowen.doctor.mine.contract.BindBankCardContract;
import com.bowen.doctor.mine.model.BindBankCardModel;
import com.bowen.doctor.mine.model.MineModel;

/**
 * @author zhuzhipeng
 * @time 2018/9/7 14:32
 * 绑卡
 */
public class BindBankCardPresenter extends BasePresenter {
    private BindBankCardModel bindBankCardModel;
    private MineModel mineModel;
    private BindBankCardContract.View mView;

    public BindBankCardPresenter(Context mContext, BindBankCardContract.View view) {
        super(mContext);
        bindBankCardModel = new BindBankCardModel(mContext);
        mineModel = new MineModel(mContext);
        mView = view;
    }

    public void bindCard(String idCard, String bankCard) {
        if(idCard.length()==0){
            showToast("请输入身份证号");
            return;
        }
        if(bankCard.length()==0){
            showToast("请输入银行卡号");
            return;
        }
        bindBankCardModel.bindCard(idCard, bankCard, new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> result) {
                mView.onUploadSuccess();
            }

            @Override
            public void onFail(HttpResult<Object> result) {
                showToast(result.getMsg());
                mView.onUploadFailed();
            }
        });
    }


    public void checkBindCard() {
        mineModel.checkBindCard(new HttpTaskCallBack<BindBankCardBean>() {
            @Override
            public void onSuccess(HttpResult<BindBankCardBean> result) {
                mView.onLoadBindCardSuccess(result.getData());
            }

            @Override
            public void onFail(HttpResult<BindBankCardBean> result) {

            }
        });
    }
}
