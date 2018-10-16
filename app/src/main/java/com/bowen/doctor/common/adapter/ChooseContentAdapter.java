package com.bowen.doctor.common.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.base.BaseViewHolder;
import com.bowen.doctor.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Describe:
 * Created by AwenZeng on 2018/3/21.
 */

public class ChooseContentAdapter extends BaseQuickAdapter<String> {

    @BindView(R.id.mContentTv)
    TextView mContentTv;

    private int choosePos = -1;

    public ChooseContentAdapter(Context cxt) {
        super(cxt);
    }

    @Override
    protected int setItemLayout() {
        return R.layout.adapter_choose_disease_name;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item, int position) {
        ButterKnife.bind(this, helper.convertView);
        if(choosePos == position){
            mContentTv.setSelected(true);
            mContentTv.setTextColor(mContext.get().getResources().getColor(R.color.color_white));
        }else{
            mContentTv.setSelected(false);
            mContentTv.setTextColor(Color.parseColor("#b8b8b8"));
        }
        mContentTv.setText(item);
    }

    public void setChoosePos(int choosePos) {
        this.choosePos = choosePos;
        notifyDataSetChanged();
    }

    public void setChoosePosByName(String name) {
        for(int i=0;i<mData.size();i++){
            if(TextUtils.equals(mData.get(i), name)){
                this.choosePos = i;
                notifyDataSetChanged();
            }
        }
    }
}
