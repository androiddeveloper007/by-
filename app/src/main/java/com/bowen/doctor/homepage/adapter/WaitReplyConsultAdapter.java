package com.bowen.doctor.homepage.adapter;

import android.content.Context;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.base.BaseViewHolder;
import com.bowen.commonlib.utils.DateUtil;
import com.bowen.commonlib.utils.ImageLoaderUtil;
import com.bowen.commonlib.widget.CircleImageView;
import com.bowen.doctor.R;
import com.bowen.doctor.common.bean.network.ConsultItem;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 医生点评列表adapter
 * created by zhuzp at 2018/06/22
 */
public class WaitReplyConsultAdapter extends BaseQuickAdapter<ConsultItem> {
    @BindView(R.id.patientImg)
    CircleImageView patientImg;
    @BindView(R.id.patientNameTv)
    TextView patientNameTv;
    @BindView(R.id.dialogueText)
    TextView dialogueText;
    @BindView(R.id.dialogueTimeTv)
    TextView dialogueTimeTv;

    public WaitReplyConsultAdapter(Context cxt) {
        super(cxt);
    }

    @Override
    protected int setItemLayout() {
        return R.layout.rv_item_unread_commend;
    }

    @Override
    protected void convert(BaseViewHolder helper, ConsultItem item, int position) {
        ButterKnife.bind(this, helper.convertView);
        ImageLoaderUtil.getInstance().show(mContext.get(), item.getHeadImgUrl(), patientImg, R.drawable.default_user, true);
        patientNameTv.setText(item.getUserNickname());
        dialogueTimeTv.setText(DateUtil.date2String(item.getLastConsultTime(), DateUtil.DEFAULT_FORMAT_DATETIME_WITHOUT_SECOND));
        dialogueText.setText(item.getContent());
    }
}