package com.bowen.doctor.homepage.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.base.BaseViewHolder;
import com.bowen.commonlib.utils.EmptyUtils;
import com.bowen.doctor.R;
import com.bowen.doctor.common.bean.network.AppointmentPeriod;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Describe:
 * Created by AwenZeng on 2018/8/17.
 */
public class OutpatientAppiontmentSetTimeAdapter extends BaseQuickAdapter<AppointmentPeriod> {
    @BindView(R.id.mTimePeriodTv)
    TextView mTimePeriodTv;
    @BindView(R.id.mPeopleCountEditText)
    EditText mPeopleCountEditText;


    public OutpatientAppiontmentSetTimeAdapter(Context cxt) {
        super(cxt);
    }

    @Override
    protected int setItemLayout() {
        return R.layout.adapter_outpatient_appiontment_set_time;
    }

    @Override
    protected void convert(BaseViewHolder helper, AppointmentPeriod item, int position) {
        ButterKnife.bind(this, helper.convertView);
        mTimePeriodTv.setText(item.getTypeName());
        if (EmptyUtils.isNotEmpty(item.getMaxNumStr())) {
            mPeopleCountEditText.setText(item.getMaxNumStr());
        } else {
            mPeopleCountEditText.setText("");
        }
        item.setPos(position);
        mPeopleCountEditText.setTag(item);
        mPeopleCountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mPeopleCountEditText.hasFocus()) {
                    AppointmentPeriod item = (AppointmentPeriod) mPeopleCountEditText.getTag();
                    item.setMaxNumStr(s.toString());
                    getData().set(item.getPos(), item);
                }
            }
        });
    }
}
