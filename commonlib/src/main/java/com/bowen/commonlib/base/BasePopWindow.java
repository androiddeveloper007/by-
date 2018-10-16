package com.bowen.commonlib.base;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

/**
 * PopWindow基类
 * Created by AwenZeng on 2016/9/20.
 */
public class BasePopWindow implements View.OnClickListener{

    public PopupWindow mPopWindow;
    public View mView;
    public Context mContext;
    public LayoutInflater mInflater;
    public BasePopWindowListener mListener;

    public interface BasePopWindowListener {
        void onDataCallBack(Object... obj);
    }


    public void setBaseDialogListener(BasePopWindowListener listener){
        mListener = listener;
    }


    public BasePopWindow(Context context) {
        mContext = context;
        mInflater = ((Activity)mContext).getLayoutInflater();
        onCreate();
    }

    public void onCreate() {
    }

    @Override
    public void onClick(View v) {

    }

    public <T extends View> T getView(int resId){
        return (T)mView.findViewById(resId);
    }

    /**
     * 关闭对话框
     */
    public  void disMissWindow() {
        if (mPopWindow != null && mPopWindow.isShowing()) {
            mPopWindow.dismiss();
            mPopWindow = null;
        }
    }
}
