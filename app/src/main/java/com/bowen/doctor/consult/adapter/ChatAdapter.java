package com.bowen.doctor.consult.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.base.BaseViewHolder;
import com.bowen.commonlib.utils.EmptyUtils;
import com.bowen.commonlib.utils.FileUtil;
import com.bowen.commonlib.utils.ImageLoaderUtil;
import com.bowen.commonlib.utils.SpannableStringUtils;
import com.bowen.commonlib.widget.CircleImageView;
import com.bowen.doctor.R;
import com.bowen.doctor.common.bean.network.PhotoFile;
import com.bowen.doctor.common.model.UserInfo;
import com.bowen.doctor.common.database.entity.ChatMessage;
import com.bowen.doctor.common.dialog.ShowBigPicDialog;
import com.bowen.doctor.common.util.Base64Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Describe:
 * Created by AwenZeng on 2018/7/18.
 */
public class ChatAdapter extends BaseQuickAdapter<ChatMessage> {

    @BindView(R.id.mChatTimeTv)
    TextView mChatTimeTv;
    @BindView(R.id.mOtherHeadPortraitImg)
    CircleImageView mOtherHeadPortraitImg;
    @BindView(R.id.mOtherChatContentTv)
    TextView mOtherChatContentTv;
    @BindView(R.id.mOtherChatImg)
    ImageView mOtherChatImg;
    @BindView(R.id.mOtherChatLayout)
    RelativeLayout mOtherChatLayout;
    @BindView(R.id.mSelfHeadPortraitImg)
    CircleImageView mSelfHeadPortraitImg;
    @BindView(R.id.mSelfChatContentTv)
    TextView mSelfChatContentTv;
    @BindView(R.id.mSelfChatImg)
    ImageView mSelfChatImg;
    @BindView(R.id.mSelfChatLayout)
    RelativeLayout mSelfChatLayout;

    private String receiveUserPicUrl;

    private ChatOverListener mListener;

    public static final int TYPE_CHAT_SEND = 0;//发送
    public static final int TYPE_CHAT_RECEIVE = 1;//接收
    public static final int TYPE_CHAT_OVER = 2;//结束



    public ChatAdapter(Context cxt) {
        super(cxt);
    }

    @Override
    protected int setItemLayout() {
        return R.layout.adapter_chat_item;
    }


    public void setListener(ChatOverListener mListener) {
        this.mListener = mListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, ChatMessage item, int position) {
        ButterKnife.bind(this, helper.convertView);
        switch (item.getType()) {
            case TYPE_CHAT_SEND:
                mChatTimeTv.setText(item.getDate());
                mOtherChatLayout.setVisibility(View.GONE);
                mSelfChatLayout.setVisibility(View.VISIBLE);
                ImageLoaderUtil.getInstance().show(mContext.get(), UserInfo.getInstance().getPicUrl(), mSelfHeadPortraitImg, R.drawable.man);
                if (EmptyUtils.isNotEmpty(item.getImgPath())) {
                    mSelfChatContentTv.setVisibility(View.GONE);
                    mSelfChatImg.setVisibility(View.VISIBLE);
                    ImageLoaderUtil.getInstance().show(mContext.get(), item.getImgPath(), mSelfChatImg, R.drawable.img_bg);
                    mSelfChatLayout.setTag(item.getImgPath());
                } else {
                    mSelfChatContentTv.setVisibility(View.VISIBLE);
                    mSelfChatImg.setVisibility(View.GONE);
                    mSelfChatContentTv.setText(item.getContent());
                }
                break;
            case TYPE_CHAT_RECEIVE:
                mChatTimeTv.setText(item.getDate());
                mOtherChatLayout.setVisibility(View.VISIBLE);
                mSelfChatLayout.setVisibility(View.GONE);
                ImageLoaderUtil.getInstance().show(mContext.get(), receiveUserPicUrl, mOtherHeadPortraitImg, R.drawable.man);
                if (EmptyUtils.isNotEmpty(item.getImgPath())) {
                    mOtherChatContentTv.setVisibility(View.GONE);
                    mOtherChatImg.setVisibility(View.VISIBLE);
                    ImageLoaderUtil.getInstance().show(mContext.get(),item.getImgPath(), mOtherChatImg, R.drawable.img_bg);
                    mOtherChatLayout.setTag(item.getImgPath());
                } else {
                    mOtherChatContentTv.setVisibility(View.VISIBLE);
                    mOtherChatImg.setVisibility(View.GONE);
                    if(item.getContent().contains("BOWEN")){
                        String[] temp = item.getContent().split("BOWEN");
                        mOtherChatContentTv.setVisibility(View.GONE);
                        mOtherChatImg.setVisibility(View.VISIBLE);
                        ImageLoaderUtil.getInstance().show(mContext.get(),temp[1], mOtherChatImg, R.drawable.img_bg);
                    }else{
                        mOtherChatContentTv.setText(item.getContent());
                    }
                }
                break;
            case TYPE_CHAT_OVER:
                if(EmptyUtils.isNotEmpty(mListener)){
                    mListener.onChatOverStatus();
                }
                mOtherChatLayout.setVisibility(View.GONE);
                mSelfChatLayout.setVisibility(View.GONE);
                mChatTimeTv.setText(SpannableStringUtils.getBuilder("本次服务已结束")
                        .setForegroundColor(mContext.get().getResources().getColor(R.color.color_main_gray))
                        .create());

                break;
        }

    }

    public void setReceiveUserPicUrl(String receiveUserPicUrl) {
        this.receiveUserPicUrl = receiveUserPicUrl;
    }

    @OnClick({R.id.mOtherChatLayout, R.id.mSelfChatLayout})
    public void onViewClicked(View view) {
        String path = (String)view.getTag();
        if(EmptyUtils.isEmpty(path))
            return;
        List<PhotoFile> list = new ArrayList<>();
        PhotoFile photoFile = new PhotoFile();
        switch (view.getId()) {
            case R.id.mOtherChatLayout:
                photoFile.setFileLink(path);
                list.add(photoFile);
                ShowBigPicDialog showBigPicDialog1 = new ShowBigPicDialog(mContext.get(),null,1,list);
                showBigPicDialog1.show();
                break;
            case R.id.mSelfChatLayout:
                photoFile.setFileLink(path);
                list.add(photoFile);
                ShowBigPicDialog showBigPicDialog = new ShowBigPicDialog(mContext.get(),null,1,list);
                showBigPicDialog.show();
                break;
        }
    }

    public interface ChatOverListener{
        void onChatOverStatus();
    }
}
