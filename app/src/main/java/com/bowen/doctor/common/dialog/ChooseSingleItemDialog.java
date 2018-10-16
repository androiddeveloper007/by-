package com.bowen.doctor.common.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseDialog;
import com.bowen.commonlib.utils.ScreenSizeUtil;
import com.bowen.commonlib.widget.wheel.WheelView;
import com.bowen.doctor.R;
import com.bowen.doctor.common.adapter.SingleItemAdapter;

import java.util.ArrayList;

/**
 * 单选选择弹框
 */
public class ChooseSingleItemDialog extends BaseDialog {
    private WheelView mWheelView;
    private TextView mCancleBtn;
    private TextView mOkBtn;
    private SingleItemAdapter wheelViewAdapter;
    private String[] mContentStrArra;
    private String mChooseContentStr;
    private String itemName;

    public ChooseSingleItemDialog(Context context) {
        super(context, R.style.dialog_transparent_style);
    }

    public ChooseSingleItemDialog(Context context, String name) {
        super(context, R.style.dialog_transparent_style);
        itemName = name;
    }

    public ChooseSingleItemDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_choose_single_item);
        getWindow().getAttributes().width = ScreenSizeUtil.getScreenWidth();
        getWindow().getAttributes().gravity = Gravity.BOTTOM;
        mWheelView = getView(R.id.wheelView);
        mCancleBtn = getView(R.id.dialogChooseContentCancle);
        mOkBtn = getView(R.id.dialogChooseContentOk);
        wheelViewAdapter = new SingleItemAdapter(mContext, mContentStrArra);
        mWheelView.setAdapter(wheelViewAdapter);
        if(itemName!=null)
            mWheelView.setSelection(getNamePosition(itemName));
        mCancleBtn.setOnClickListener(this);
        mOkBtn.setOnClickListener(this);
        setCanceledOnTouchOutside(true);
    }


    public void setmContentStrArra(String[] mContentStrArra) {
        this.mContentStrArra = mContentStrArra;
    }

    public void setmContentStrList(ArrayList<String> list) {
        mContentStrArra = new String[list.size()];
        for(int i=0;i<list.size();i++){
            mContentStrArra[i] = list.get(i);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.dialogChooseContentCancle:
                dismiss();
                break;
            case R.id.dialogChooseContentOk:
                TextView textView = (TextView) mWheelView.getSelectedView();
                mListener.onDataCallBack(textView.getText().toString(), textView.getTag(), mWheelView.getSelectedItemPosition());
                dismiss();
                break;
        }
    }

    private int getNamePosition(String name){
        String[] names = wheelViewAdapter.getDatas();
        int i = 0;
        for (String temp:names){
            if(TextUtils.equals(temp, name)){
                return i;
            }
            i++;
        }
        return 0;
    }
}