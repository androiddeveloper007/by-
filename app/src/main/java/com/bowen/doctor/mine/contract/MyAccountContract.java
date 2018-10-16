package com.bowen.doctor.mine.contract;

import com.bowen.doctor.common.bean.network.BindBankCardBean;
import com.bowen.doctor.common.bean.network.MyAccount;

/**
 * Created by zzp on 2018/5/15.
 */

public interface MyAccountContract {

    interface View {
        void onLoadSuccess(MyAccount list);
        void onLoadFail();
        void onLoadBindCardSuccess(BindBankCardBean o, boolean isWithdrawBtn);
    }

    interface Presenter {

    }
}
