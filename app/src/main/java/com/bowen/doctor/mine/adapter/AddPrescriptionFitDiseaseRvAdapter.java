package com.bowen.doctor.mine.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.base.BaseViewHolder;
import com.bowen.doctor.R;
import com.bowen.doctor.common.bean.network.DiseaseInfo;

import java.util.List;

/**
 * 添加偏方中选择病症列表adapter
 * created by zhuzp at 2018/05/22
 */
public class AddPrescriptionFitDiseaseRvAdapter extends BaseQuickAdapter<DiseaseInfo> {
    public AddPrescriptionFitDiseaseRvAdapter(Context c){
        super(c);
    }

    @Override
    protected int setItemLayout() {
        return R.layout.add_disease_rv_item;
    }

    @Override
    protected void convert(final BaseViewHolder helper, DiseaseInfo item, final int position) {
        ((TextView) helper.convertView.findViewById(R.id.mContentTv)).setText(item.getDiseaseName());
        helper.convertView.findViewById(R.id.iv_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAndNotify(helper.getAdapterPosition());
            }
        });
    }

    public String getSelectedDisease(){
        if(getData()!=null && getData().size()>0){
            List<DiseaseInfo> list = getData();
            if(list!=null && list.size()>0){
                StringBuilder stringBuilder = new StringBuilder();
                for(DiseaseInfo item : list){
                    stringBuilder.append(item.getDiseaseName());
                    stringBuilder.append(",");
                }
                stringBuilder.deleteCharAt(stringBuilder.length()-1);
                return stringBuilder.toString();
            }
        }
        return "";
    }

    private void removeAndNotify(int position){
        getData().remove(position);
        notifyDataSetChanged();
    }
}
