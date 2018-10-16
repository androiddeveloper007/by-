package com.bowen.doctor.consult.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.base.BaseViewHolder;
import com.bowen.commonlib.utils.DateUtil;
import com.bowen.commonlib.utils.EmptyUtils;
import com.bowen.commonlib.utils.ImageLoaderUtil;
import com.bowen.commonlib.utils.SpannableStringUtils;
import com.bowen.commonlib.widget.CircleImageView;
import com.bowen.doctor.R;
import com.bowen.doctor.common.bean.network.ConsultItem;
import com.bowen.doctor.common.widget.SwipeMenuLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 医生点评列表adapter
 * created by zhuzp at 2018/06/22
 */
public class ConsultFgAdapter extends BaseQuickAdapter<ConsultItem> {


    @BindView(R.id.mHeadPortraitImg)
    CircleImageView mHeadPortraitImg;
    @BindView(R.id.mPatientNameTv)
    TextView mPatientNameTv;
    @BindView(R.id.mTimeTv)
    TextView mTimeTv;
    @BindView(R.id.mConsultContentTv)
    TextView mConsultContentTv;
    @BindView(R.id.contentLayout)
    CardView contentLayout;
    @BindView(R.id.mDeleteTv)
    TextView mDeleteTv;
    @BindView(R.id.rightMenu)
    CardView rightMenu;
    @BindView(R.id.mSwipeMenuLayout)
    SwipeMenuLayout mSwipeMenuLayout;


    private OnRecyclerViewItemClickListener mItemOnClickListener;


    public void setItemOnClickListener(OnRecyclerViewItemClickListener mItemOnClickListener) {
        this.mItemOnClickListener = mItemOnClickListener;
    }

    public interface DeleteListener {
        void onDeleteListener(ConsultItem item);
    }

    private DeleteListener mDeleteListener;

    public ConsultFgAdapter(Context cxt) {
        super(cxt);
    }

    @Override
    protected int setItemLayout() {
        return R.layout.adapter_hp_consult;
    }

    @Override
    protected void convert(BaseViewHolder helper, ConsultItem item, int position) {
        ButterKnife.bind(this, helper.convertView);
        mSwipeMenuLayout.setTag(position);
        mDeleteTv.setTag(item);
        ImageLoaderUtil.getInstance().show(mContext.get(), item.getHeadImgUrl(), mHeadPortraitImg, R.drawable.man);
        mPatientNameTv.setText(item.getUserNickname());
        mTimeTv.setText(DateUtil.date2String(item.getLastConsultTime(), DateUtil.DEFAULT_FORMAT_DAYTIME));
        mConsultContentTv.setText(item.getContent());
        String typeStr = item.isIsRead() ? "[已读] " : "[未读] ";
        mConsultContentTv.setText(SpannableStringUtils.getBuilder(typeStr)
                .setForegroundColor(mContext.get().getResources().getColor(item.isIsRead() ? R.color.color_main_black : R.color.color_main_red))
                .append(item.getContent())
                .setForegroundColor(mContext.get().getResources().getColor(R.color.color_main_gray))
                .create());
        mSwipeMenuLayout.smoothToCloseMenu();
    }

    public void setmDeleteListen(DeleteListener mDeleteListener) {
        this.mDeleteListener = mDeleteListener;
    }

    @OnClick({R.id.mDeleteTv, R.id.mSwipeMenuLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mDeleteTv:
                if (mSwipeMenuLayout.isMenuOpen()) {
                    mSwipeMenuLayout.smoothToCloseMenu();
                }
                if (EmptyUtils.isNotEmpty(mDeleteListener)) {
                    mDeleteListener.onDeleteListener((ConsultItem) view.getTag());
                }
                break;
            case R.id.mSwipeMenuLayout:
                if (mSwipeMenuLayout.isMenuOpen()) {
                    mSwipeMenuLayout.smoothToCloseMenu();
                }
                notifyDataSetChanged();
                if (mItemOnClickListener != null) {
                    mItemOnClickListener.onItemClick(view, (int) view.getTag());
                }
                break;
        }
    }
}