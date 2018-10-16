package com.bowen.doctor.homepage.adapter;

import android.content.Context;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.base.BaseViewHolder;
import com.bowen.commonlib.utils.GsonUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.bean.network.AddPrescriptionDetail;
import com.bowen.doctor.common.bean.network.PrescriptionBean;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 收藏偏方fragment中rv的adapter
 * created by zhuzp at 2018/05/22
 */
public class CommonUsedPrescriptionRvAdapter extends BaseQuickAdapter<PrescriptionBean> {
    @BindView(R.id.commonUsePrescriptionNameTv)
    TextView commonUsePrescriptionNameTv;
    @BindView(R.id.commonUsePrescriptionIntroduceTv)
    TextView commonUsePrescriptionIntroduceTv;

    public CommonUsedPrescriptionRvAdapter(Context cxt) {
        super(cxt);
    }

    @Override
    protected int setItemLayout() {
        return R.layout.rv_item_common_used_prescription;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final PrescriptionBean item, final int position) {
        ButterKnife.bind(this, helper.convertView);
        commonUsePrescriptionNameTv.setText(item.getName());
        Type type = new TypeToken<List<AddPrescriptionDetail>>() {
        }.getType();
        ArrayList<AddPrescriptionDetail> list = GsonUtil.getGson().fromJson(item.getRemark(), type);
        Iterator<AddPrescriptionDetail> iterator = list.iterator();
        StringBuilder strBuilder = new StringBuilder();
        while (iterator.hasNext()) {
            AddPrescriptionDetail bean = iterator.next();
            strBuilder.append(bean.getName()).append(bean.getGram()).append("克、");
        }
        strBuilder.setCharAt(strBuilder.length() - 1, '。');
        commonUsePrescriptionIntroduceTv.setText(strBuilder);
    }
}