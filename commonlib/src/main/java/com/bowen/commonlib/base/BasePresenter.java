
package com.bowen.commonlib.base;

import android.content.Context;

import com.bowen.commonlib.utils.ToastUtil;

public class BasePresenter {
    public Context mContext;

    public BasePresenter(Context mContext) {
        this.mContext = mContext;
    }

    public void showToast(String erro) {
        ToastUtil.getInstance().showToastDialog(erro);
    }
}
