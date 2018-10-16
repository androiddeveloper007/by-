package com.bowen.doctor.common.dialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseDialog;
import com.bowen.commonlib.model.PermissionsModel;
import com.bowen.commonlib.utils.BitmapUtil;
import com.bowen.commonlib.utils.ImageLoaderUtil;
import com.bowen.commonlib.utils.LogUtil;
import com.bowen.commonlib.utils.ScreenSizeUtil;
import com.bowen.commonlib.widget.CircleImageView;
import com.bowen.doctor.R;
import com.bowen.doctor.common.bean.ShareData;
import com.bowen.doctor.common.bean.network.DoctorInfo;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;

/**
 * @author zhuzhipeng
 * @time 2018/9/4 16:47
 * 医生名片分享
 */
public class DoctorBusinessCardDialog extends BaseDialog {
    @BindView(R.id.recommendDoctorTipImg)
    ImageView recommendDoctorTipImg;
    @BindView(R.id.doctorCardIcon)
    CircleImageView doctorCardIcon;
    @BindView(R.id.doctorCardNameTv)
    TextView doctorCardNameTv;
    @BindView(R.id.doctorCardDepartmentTv)
    TextView doctorCardDepartmentTv;
    @BindView(R.id.doctorCardLevelTv)
    TextView doctorCardLevelTv;
    @BindView(R.id.doctorCardHospitalTv)
    TextView doctorCardHospitalTv;
    @BindView(R.id.doctorCardTelTv)
    TextView doctorCardTelTv;
    @BindView(R.id.doctorCardAddressTv)
    TextView doctorCardAddressTv;
    @BindView(R.id.icLauncher)
    ImageView icLauncher;
    @BindView(R.id.mTipsQRcodeImg)
    ImageView mTipsQRcodeImg;
    @BindView(R.id.myBusinessCardLayout)
    LinearLayout myBusinessCardLayout;
    private Bitmap mShareBitmap;
    private String mSharePath;
    private DoctorInfo doctorInfo;

    public DoctorBusinessCardDialog(Context context, DoctorInfo doctor) {
        super(context, R.style.dialog_transparent_style);
        setTranslucentStatus();
        doctorInfo = doctor;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_my_business_card);
        getWindow().getAttributes().width = ScreenSizeUtil.getScreenWidth();
        getWindow().getAttributes().height = ScreenSizeUtil.getScreenHeight();
        getWindow().getAttributes().gravity = Gravity.CENTER;
        ButterKnife.bind(this);
        recommendDoctorTipImg.setVisibility(doctorInfo.isRecommend()? View.VISIBLE:View.GONE);
        ImageLoaderUtil.getInstance().show(getContext(), doctorInfo.getFileLink(), doctorCardIcon);
        ImageLoaderUtil.getInstance().show(getContext(), doctorInfo.getAppPicUrl(), mTipsQRcodeImg);
        doctorCardNameTv.setText(doctorInfo.getName());
        doctorCardDepartmentTv.setText(doctorInfo.getHospitalDepartments());
        doctorCardLevelTv.setText(doctorInfo.getPositionStr());
        doctorCardHospitalTv.setText(doctorInfo.getHospital());
        doctorCardTelTv.setText(doctorInfo.getUserPhone());
        doctorCardAddressTv.setText(doctorInfo.getAddress());
    }

    private void shareTips() {
        try {
            mSharePath = BitmapUtil.saveBitmap(mShareBitmap);
            LogUtil.androidLog("分享图片：" + mSharePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ShareData shareData = new ShareData();
        shareData.setShareType(Platform.SHARE_IMAGE);
        shareData.setImgUrl(mSharePath);
        ShareDialog.getBuilder().shareData(shareData).build(mContext).show();
    }

    @OnClick({R.id.mCloseImg, R.id.mShareBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mCloseImg:
                dismiss();
                break;
            case R.id.mShareBtn:
                myBusinessCardLayout.setDrawingCacheEnabled(true);
                mShareBitmap = myBusinessCardLayout.getDrawingCache();
                mShareBitmap = mShareBitmap.createBitmap(mShareBitmap); // 拷贝图片，否则在setDrawingCacheEnabled(false)以后该图片会被释放掉
                myBusinessCardLayout.setDrawingCacheEnabled(false);
                PermissionsModel permissionsModel = new PermissionsModel(mContext);
                permissionsModel.checkWriteSDCardPermission(new PermissionsModel.PermissionListener() {
                    @Override
                    public void onPermission(boolean isPermission) {
                        if (isPermission) {
                            shareTips();
                        }
                    }
                });
                break;
        }
    }
}