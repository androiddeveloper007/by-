package com.bowen.doctor.common.dialog;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bowen.commonlib.base.BasePopWindow;
import com.bowen.commonlib.utils.EmptyUtils;
import com.bowen.commonlib.utils.ScreenSizeUtil;
import com.bowen.doctor.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zzp on 2018/6/1.
 * 选择订单审核状态
 */

public class EnterAuditTypeSelectPopwindow extends BasePopWindow {

    TextView all, waitExamine, examined, finished;
    private List<View> viewList;

    public EnterAuditTypeSelectPopwindow(Context context) {
        super(context);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mView = mInflater.inflate(R.layout.pop_window_enter_audit_state, null);
        ButterKnife.bind(this, mView);
        mPopWindow = new PopupWindow(mView, ScreenSizeUtil.dp2px(130f), ScreenSizeUtil.dp2px(250f));
        mPopWindow.setOutsideTouchable(true);
        all = mView.findViewById(R.id.all);
        waitExamine = mView.findViewById(R.id.waitPay);
        examined = mView.findViewById(R.id.payed);
        finished = mView.findViewById(R.id.finished);
        viewList = new ArrayList<>();
        viewList.add(all);
        viewList.add(waitExamine);
        viewList.add(examined);
        viewList.add(finished);
        all.setSelected(true);
    }


    public void show(View v) {
        if (mPopWindow != null && !mPopWindow.isShowing()) {
            mPopWindow.showAsDropDown(v, ScreenSizeUtil.dp2px(-50), ScreenSizeUtil.dp2px(-20));
        }
    }

    public boolean isShowing() {
        if (!EmptyUtils.isEmpty(mPopWindow)) {
            return mPopWindow.isShowing();
        }
        return false;
    }


    @OnClick({R.id.all, R.id.waitPay, R.id.payed, R.id.finished})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.all:
                mListener.onDataCallBack("全部");
                setPositionState(0);
                break;
            case R.id.waitPay:
                mListener.onDataCallBack("待审核");
                setPositionState(1);
                break;
            case R.id.payed:
                mListener.onDataCallBack("已审核");
                setPositionState(2);
                break;
            case R.id.finished:
                mListener.onDataCallBack("已驳回");
                setPositionState(3);
                break;
        }
        mPopWindow.dismiss();
    }

    public void setPositionState(int position){
        for(int i=0;i<viewList.size();i++){
            if(i==position){
                viewList.get(i).setSelected(true);
            }else{
                viewList.get(i).setSelected(false);
            }
        }
    }
}
