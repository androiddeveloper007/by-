package com.bowen.doctor.homepage.activity;


import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.bowen.commonlib.utils.EmptyUtils;
import com.bowen.commonlib.utils.RouterActivityUtil;
import com.bowen.commonlib.utils.SpannableStringUtils;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseActivity;
import com.bowen.doctor.common.bean.ShareData;
import com.bowen.doctor.common.bean.network.FolkPrescription;
import com.bowen.doctor.common.bean.network.InfoFolkPrescription;
import com.bowen.doctor.common.dialog.ShareDialog;
import com.bowen.doctor.common.model.Constants;
import com.bowen.doctor.homepage.contract.FolkPrescriptionDetailContract;
import com.bowen.doctor.homepage.presenter.FolkPrescriptionDetailPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sharesdk.framework.Platform;

/**
 * Describe:偏方详情
 * Created by zzp on 2018/3/23.
 */
public class FolkPrescriptionDetailActivity extends BaseActivity implements FolkPrescriptionDetailContract.View {
    @BindView(R.id.folkDetailFitPeopleTv)
    TextView folkDetailFitPeopleTv;
    @BindView(R.id.folkDetailApplyDiseaseTv)
    TextView folkDetailApplyDiseaseTv;
    @BindView(R.id.folkDetailUsageDosageTv)
    TextView folkDetailUsageDosageTv;
    @BindView(R.id.folkDetailFromTv)
    TextView folkDetailFromTv;
    private FolkPrescription mFolkPrescription;
    private InfoFolkPrescription mInfoFolkPrescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_folk_prescription_detail);
        ButterKnife.bind(this);
//        getTitleBar().getRightTextButton().setVisibility(View.VISIBLE);
//        getTitleBar().getRightTextButton().setText("分享");
        Bundle bundle = RouterActivityUtil.getBundle(this);
        if (EmptyUtils.isNotEmpty(bundle)) {
            mFolkPrescription = (FolkPrescription) bundle.getSerializable(RouterActivityUtil.FROM_TAG);
            setTitle(mFolkPrescription.getPrescriptionName());
            folkDetailFitPeopleTv.setText(mFolkPrescription.getApplyCrowdStr());
            folkDetailApplyDiseaseTv.setText(mFolkPrescription.getApplyDisease());
            folkDetailUsageDosageTv.setText(mFolkPrescription.getUsageDosage());
        }
        FolkPrescriptionDetailPresenter mPresenter = new FolkPrescriptionDetailPresenter(this, this);
        mPresenter.loadData(mFolkPrescription.getPrescriptionId());
    }

    @Override
    public void onRightButtonPressed() {
        super.onRightButtonPressed();
        ShareData shareData = new ShareData();
        shareData.setShareType(Platform.SHARE_WEBPAGE);
        shareData.setContent(mInfoFolkPrescription.getShareContent());
        shareData.setLinkUrl(mInfoFolkPrescription.getShareUrl().replace("\\", ""));
        shareData.setTitile(mInfoFolkPrescription.getPrescriptionName());
        ShareDialog.getBuilder().shareData(shareData).build(this).show();
    }

    @Override
    public void onLoadSuccess(InfoFolkPrescription info) {
        mInfoFolkPrescription = info;
        updateUI(info);
    }

    private void updateUI(InfoFolkPrescription info) {
        String sourceTypeStr;
        if (TextUtils.equals(Constants.TYPE_USER, info.getPreSourceType())) {
            sourceTypeStr = "用户";
        } else if (TextUtils.equals(Constants.TYPE_DOCTOR, info.getPreSourceType())) {
            sourceTypeStr = "医生";
        } else {
            sourceTypeStr = "";
        }
        if (EmptyUtils.isNotEmpty(info.getPrescriptionSource())) {
            folkDetailFromTv.setText(SpannableStringUtils.getBuilder("来自 ")
                    .setForegroundColor(getResources().getColor(R.color.color_main_black))
                    .append(sourceTypeStr)
                    .setForegroundColor(getResources().getColor(R.color.color_main_black))
                    .append(info.getPrescriptionSource())
                    .setForegroundColor(getResources().getColor(R.color.color_main))
                    .create());
        }
    }

    @Override
    public void onLoadFail(InfoFolkPrescription info) {

    }
}