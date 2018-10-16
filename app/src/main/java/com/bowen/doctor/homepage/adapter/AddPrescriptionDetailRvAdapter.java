package com.bowen.doctor.homepage.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.base.BaseViewHolder;
import com.bowen.commonlib.utils.GsonUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.bean.network.AddPrescriptionDetail;

import java.util.Iterator;
import java.util.List;

/**
 * 添加方剂中rv的adapter
 * created by zhuzp at 2018/05/22
 */
public class AddPrescriptionDetailRvAdapter extends BaseQuickAdapter<AddPrescriptionDetail> {

    private DeleteClickListener deleteListener;

    public AddPrescriptionDetailRvAdapter(Context cxt) {
        super(cxt);
    }

    @Override
    protected int setItemLayout() {
        return R.layout.rv_item_add_prescription_detail;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final AddPrescriptionDetail item, final int position) {
        ((TextView) helper.convertView.findViewById(R.id.prescriptionDetailNameEdit)).setText(mData.get(position).getName());
        ((TextView) helper.convertView.findViewById(R.id.prescriptionDetailCountEdit)).setText(mData.get(position).getGram());
        helper.convertView.findViewById(R.id.addPrescriptionDetailDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deleteListener != null) deleteListener.delete(position);
            }
        });
        ((TextView) helper.convertView.findViewById(R.id.prescriptionDetailNameEdit)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                (mData.get(getViewHolderPosition(helper))).setName(editable.toString());
            }
        });
        ((TextView) helper.convertView.findViewById(R.id.prescriptionDetailCountEdit)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                (mData.get(getViewHolderPosition(helper))).setGram(editable.toString());
            }
        });
    }

    public interface DeleteClickListener {
        void delete(int position);
    }

    public void setDeleteClickListener(DeleteClickListener listener) {
        deleteListener = listener;
    }

    //获取方剂列表的json字符串
    public String getPrescriptionStr() {
        List<AddPrescriptionDetail> tempData = mData;
        Iterator<AddPrescriptionDetail> iterator = tempData.iterator();
        while(iterator.hasNext()){
            AddPrescriptionDetail item = iterator.next();
            if(item.getGram().trim().equals("")||item.getName().trim().equals("")) {
                iterator.remove();   //注意这个地方
            }
        }
        if (getData() != null && getData().size() > 0) {
            return GsonUtil.toJson(getData()).replace("\"","");
        } else {
            return "";
        }
    }
}
