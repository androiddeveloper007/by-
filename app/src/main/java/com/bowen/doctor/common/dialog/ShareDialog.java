package com.bowen.doctor.common.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseDialog;
import com.bowen.commonlib.model.PermissionsModel;
import com.bowen.commonlib.model.PermissionsModel.PermissionListener;
import com.bowen.commonlib.utils.ScreenSizeUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.bean.ShareData;
import com.bowen.doctor.common.util.ShareUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 分享框（构造者模式）
 */
public class ShareDialog extends BaseDialog {
    @BindView(R.id.mMainTitleTv)
    TextView mMainTitleTv;
    @BindView(R.id.mShareWechatImg)
    ImageView mShareWechatImg;
    @BindView(R.id.mShareWechatTv)
    TextView mShareWechatTv;
    @BindView(R.id.mSharWechatLayout)
    LinearLayout mSharWechatLayout;
    @BindView(R.id.mShareWechatCircleImg)
    ImageView mShareWechatCircleImg;
    @BindView(R.id.mShareWechatCircleTv)
    TextView mShareWechatCircleTv;
    @BindView(R.id.mShareWechatCircleLayout)
    LinearLayout mShareWechatCircleLayout;
    @BindView(R.id.mShareWeiboImg)
    ImageView mShareWeiboImg;
    @BindView(R.id.mShareWeiboTv)
    TextView mShareWeiboTv;
    @BindView(R.id.mShareWeiboLayout)
    LinearLayout mShareWeiboLayout;
    @BindView(R.id.shareQQImg)
    ImageView shareQQImg;
    @BindView(R.id.shareQQTv)
    TextView shareQQTv;
    @BindView(R.id.mShareQQLayout)
    LinearLayout mShareQQLayout;

    private int mShareDialogType;
    private ShareData mShareData;
    private PermissionsModel mPermissionsModel;
    public static final int TYPE_SHARE_DIALOG_NORMAL = 0;//正常分享
    public static final int TYPE_SHARE_DIALOG_REMIND = 1;//提醒

    public static Builder getBuilder() {
        return new Builder();
    }

    public ShareDialog(Context context, int shareDialogType, ShareData mShareData) {
        super(context, R.style.dialog_transparent_style);
        this.mShareData = mShareData;
        this.mShareDialogType = shareDialogType;
        mPermissionsModel = new PermissionsModel(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_share);
        getWindow().getAttributes().width = ScreenSizeUtil.getScreenWidth();
        getWindow().getAttributes().gravity = Gravity.CENTER;
        ButterKnife.bind(this);
    }

    @OnClick({R.id.mSharWechatLayout, R.id.mShareWechatCircleLayout, R.id.mShareWeiboLayout, R.id.mShareQQLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mSharWechatLayout:
                ShareUtil.getInstance().shareWechat(mShareData);
                break;
            case R.id.mShareWechatCircleLayout:
                    ShareUtil.getInstance().shareWechatMoments(mShareData);

                break;
            case R.id.mShareWeiboLayout:
                    ShareUtil.getInstance().shareWeiBo(mShareData);
                break;
            case R.id.mShareQQLayout:
                ShareUtil.getInstance().shareQQ(mShareData);
                break;
        }
        dismiss();
    }

    public static class Builder {
        private int shareDialogType = TYPE_SHARE_DIALOG_NORMAL;
        private ShareData shareData;
        public ShareDialog build(Context context) {
            return new ShareDialog(context, shareDialogType, shareData);
        }
        public Builder shareData(ShareData shareData) {
            this.shareData = shareData;
            return this;
        }
        public Builder shareDialogType(int shareDialogType) {
            this.shareDialogType = shareDialogType;
            return this;
        }
    }
}