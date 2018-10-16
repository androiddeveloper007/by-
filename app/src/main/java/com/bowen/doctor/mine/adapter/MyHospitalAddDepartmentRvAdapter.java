package com.bowen.doctor.mine.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.base.BaseViewHolder;
import com.bowen.doctor.R;
import com.bowen.doctor.common.bean.network.Department;

/**
 * 我的医馆上传审核页面，科室选择列表adapter
 * created by zhuzp at 2018/05/22
 */
public class MyHospitalAddDepartmentRvAdapter extends BaseQuickAdapter<Department> {
    public interface ItemAllDeleteListener {
        void onAllDelete();
    }

    private ItemAllDeleteListener listener;

    public MyHospitalAddDepartmentRvAdapter(Context c) {
        super(c);
    }

    @Override
    protected int setItemLayout() {
        return R.layout.choose_department_rv_item;
    }

    @Override
    protected void convert(final BaseViewHolder helper, Department item, final int position) {
        ((TextView) helper.convertView.findViewById(R.id.mContentTv)).setText(item.getDepartmentsName());
        helper.convertView.findViewById(R.id.iv_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAndNotify(helper.getAdapterPosition());
                if (getData().size() == 0) {
                    if (listener != null) {
                        listener.onAllDelete();
                    }
                }
            }
        });
    }

    private void removeAndNotify(int position) {
        getData().remove(position);
        notifyDataSetChanged();
    }

    public void setOnItemAllDeleteListener(ItemAllDeleteListener listener) {
        this.listener = listener;
    }

    public String getSelectedDepartmentIdStr() {
        if (mData.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Department item : mData) {
                stringBuilder.append(item.getDepartmentsId());
                stringBuilder.append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            return stringBuilder.toString();
        }
        return "";
    }
}