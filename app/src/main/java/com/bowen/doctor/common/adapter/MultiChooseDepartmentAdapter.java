package com.bowen.doctor.common.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.base.BaseViewHolder;
import com.bowen.doctor.R;
import com.bowen.doctor.common.bean.network.Department;
import com.bowen.doctor.common.event.SelectedDepartmentListChangeEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe:多选科室弹框
 * Created by zhuzhipeng on 2018/7/4.
 */

public class MultiChooseDepartmentAdapter extends BaseQuickAdapter<Department> {

    private List<Boolean> booleanList;
    public MultiChooseDepartmentAdapter(Context cxt) {
        super(cxt);
    }

    @Override
    public void setNewData(List<Department> data) {
        super.setNewData(data);
        booleanList = new ArrayList<>(getData().size());
        for(int i=0;i<getData().size();i++){
            booleanList.add(i,false);
        }
    }

    @Override
    protected int setItemLayout() {
        return R.layout.adapter_multi_choose_department;
    }

    @Override
    protected void convert(BaseViewHolder helper, Department item, int position) {
        final TextView mContentTv = helper.getView(R.id.mContentTv);
        if(booleanList.size()>0){
            if(booleanList.get(position)){
                mContentTv.setSelected(true);
                mContentTv.setTextColor(mContext.get().getResources().getColor(R.color.color_white));
            }else{
                mContentTv.setSelected(false);
                mContentTv.setTextColor(Color.parseColor("#b8b8b8"));
            }
        }
        mContentTv.setText(item.getDepartmentsName());
    }

    public void setBooleanListByPos(int pos, boolean isSelected){
        if(booleanList!=null&& booleanList.size()>0){
            booleanList.set(pos, isSelected);
        }
    }

    public boolean isSelectedByPos(int pos) {
        if(booleanList!=null&& booleanList.size()>0){
            return booleanList.get(pos);
        }
        return false;
    }

    public String getSelectedStr(){
        StringBuilder str = new StringBuilder();
        for(int i=0;i<getItemCount();i++){
            if(booleanList.get(i)){
                str.append(((Department)getData().get(i)).getDepartmentsName());
                str.append(",");
            }
        }
        if(str.length()>0){
            str.deleteCharAt(str.length()-1);
        }
        return str.toString();
    }

    public String getSelectedId(){
        StringBuilder str = new StringBuilder();
        for(int i=0;i<getItemCount();i++){
            if(booleanList.get(i)){
                str.append(((Department)getData().get(i)).getDepartmentsId());
                str.append(",");
            }
        }
        if(str.length()>0){
            str.deleteCharAt(str.length()-1);
        }
        return str.toString();
    }

    public ArrayList<Department> getSelectList(){
        ArrayList<Department> list = new ArrayList<>();
        for(int i=0;i<getItemCount();i++){
            if(booleanList.get(i)){
                list.add((Department)getData().get(i));
            }
        }
        return list;
    }
}