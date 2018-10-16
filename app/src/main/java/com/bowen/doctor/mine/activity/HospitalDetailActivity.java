package com.bowen.doctor.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.dialog.AffirmDialog;
import com.bowen.commonlib.model.PermissionsModel;
import com.bowen.commonlib.utils.EmptyUtils;
import com.bowen.commonlib.utils.ImageLoaderUtil;
import com.bowen.commonlib.utils.RouterActivityUtil;
import com.bowen.commonlib.utils.ToastUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseActivity;
import com.bowen.doctor.common.bean.Page;
import com.bowen.doctor.common.bean.ShareData;
import com.bowen.doctor.common.bean.network.EmrHospitalBean;
import com.bowen.doctor.common.bean.network.HospitalDetailInfo;
import com.bowen.doctor.common.bean.network.MyEnterHospitalBean;
import com.bowen.doctor.common.dialog.ShareDialog;
import com.bowen.doctor.common.dialog.ShowBigPicDialog;
import com.bowen.doctor.common.widget.AppBarStateChangeListener;
import com.bowen.doctor.common.widget.CollapsedTextView;
import com.bowen.doctor.mine.adapter.HospitalDetailDoctorRvAdapter;
import com.bowen.doctor.mine.contract.HospitalDetailContract;
import com.bowen.doctor.mine.presenter.HospitalDetailPresenter;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;

/**
 * Describe:医馆详情
 * Created by zhuzhipeng on 2018/7/3.
 */
public class HospitalDetailActivity extends BaseActivity implements HospitalDetailContract.View, BaseQuickAdapter.RequestLoadMoreListener {
    TextView hospitalNameTv;
    TextView hospitalAddressTv;
    CollapsedTextView hospitalIntroduceTv;
    ImageView hospitalDetailImg;
    RecyclerView hospitalDetailDoctorRv;
    private HospitalDetailDoctorRvAdapter mAdapter;
    private HospitalDetailPresenter mPresenter;
    private int page = 1;
    private final int pageSize = 10;
    private boolean isMore = false;
    private boolean isLoadMore = false;
    private View emptyView;
    private String hospitalId;
    private View headView;
    private EmrHospitalBean mHospital;
    private MyEnterHospitalBean hospitalBean;
    private Activity mActivity;
    private PermissionsModel mPermissionsModel;
    private String phone;
    private boolean isPreviewMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_hospital_detail);
        ButterKnife.bind(this);
        mActivity = this;
        initView();
    }

    private void initView() {
        if (RouterActivityUtil.getSerializable(this) != null) {
            hospitalBean = (MyEnterHospitalBean) RouterActivityUtil.getSerializable(this);
            hospitalId = hospitalBean.getHospitalId();
            isPreviewMode = RouterActivityUtil.getBundle(this).getBoolean(RouterActivityUtil.FROM_TAG1);
        }
        initHeaderView();
        hospitalDetailImg = getView(R.id.hospitalDetailImg);
        TextView toolbarTitleTv = getView(R.id.toolbarTitleTv);
        toolbarTitleTv.setText(hospitalBean != null ? hospitalBean.getHospitalName() : "");
        final AppBarLayout appbar = getView(R.id.appbar);
        final ButtonBarLayout playButton = getView(R.id.playButton);
        final Toolbar toolbar = getView(R.id.toolbar);
        ImageLoaderUtil.getInstance().show(this, hospitalBean.getHospitalImgUrl(), hospitalDetailImg, R.drawable.img_defalult_bg);
        appbar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    playButton.setVisibility(View.GONE);
                    toolbar.setVisibility(View.GONE);
                } else if (state == State.COLLAPSED) {
                    playButton.setVisibility(View.VISIBLE);
                } else {
                    toolbar.setVisibility(View.VISIBLE);
                }
            }
        });
        hospitalDetailDoctorRv = getView(R.id.hospitalDetailDoctorRv);
        mPresenter = new HospitalDetailPresenter(this, this);
        if (isPreviewMode)
            mAdapter = new HospitalDetailDoctorRvAdapter(this, true);
        else
            mAdapter = new HospitalDetailDoctorRvAdapter(this);
        mAdapter.addHeaderView(headView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        hospitalDetailDoctorRv.setLayoutManager(layoutManager);
        hospitalDetailDoctorRv.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(this);
        if (emptyView == null) {
            emptyView = getLayoutInflater().inflate(R.layout.empty_view_no_doctor,
                    (ViewGroup) findViewById(android.R.id.content), false);
            mAdapter.setEmptyView(true, emptyView);
        }
        getData(false);
    }

    private void initHeaderView() {
        headView = getLayoutInflater().inflate(R.layout.header_hospital_detail, null);
        hospitalNameTv = headView.findViewById(R.id.hospitalNameTv);
        hospitalAddressTv = headView.findViewById(R.id.hospitalAddressTv);
        hospitalIntroduceTv = headView.findViewById(R.id.hospitalIntroduceTv);
        if (hospitalBean != null) {
            if (!TextUtils.isEmpty(hospitalBean.getHospitalName())) {
                hospitalNameTv.setText(hospitalBean.getHospitalName());
            }
            if (!TextUtils.isEmpty(hospitalBean.getAddressStr())) {
                hospitalAddressTv.setText(hospitalBean.getAddressStr());
            }
        }
        mPermissionsModel = new PermissionsModel(mActivity);
        headView.findViewById(R.id.hospitalCall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = hospitalBean.getPhone();
                AffirmDialog affirmDialog = new AffirmDialog(mActivity, "", "拨打电话 " + phone, "取消", "确定");
                affirmDialog.setAffirmDialogListenner(new AffirmDialog.AffirmDialogListenner() {
                    @Override
                    public void onCancle() {
                    }

                    @Override
                    public void onOK() {
                        mPermissionsModel.checkCallPhonePermission(new PermissionsModel.PermissionListener() {
                            @Override
                            public void onPermission(boolean isPermission) {
                                if (isPermission) {
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_CALL);
                                    Uri data = Uri.parse("tel:" + phone);
                                    intent.setData(data);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                });
                affirmDialog.show();
            }
        });
    }

    public void getData(boolean isLoad) {
        isMore = isLoad;
        if (isMore && isLoadMore) {
            return;
        }
        if (isMore) {
            isLoadMore = true;
        } else {
            page = 1;
        }
        mPresenter.getHospitalDetail(hospitalId, page, pageSize);
    }

    @Override
    public void onLoadSuccess(HospitalDetailInfo info) {
        if (page == 1) {
            hospitalIntroduceTv.setText(info.getEmrHospital().getIntroduction());
            mHospital = info.getEmrHospital();
        }
        Page pageBean = info.getPage();
        if (isMore) {
            mAdapter.notifyDataChangedAfterLoadMore(info.getEmrDoctorList(), true);
        } else {
            mAdapter.setNewData(info.getEmrDoctorList());
        }
        mAdapter.openLoadMore(pageBean.getPageSize(), pageBean.getPageNo() < pageBean.getTotalPages());
        if (mAdapter.getData() != null && mAdapter.getData().size() == 0) {
            mAdapter.addFooterView(null);
        }
        page = pageBean.getNextPage();
        isLoadMore = false;
    }

    @Override
    public void onLoadFail(HospitalDetailInfo info) {
    }

    @Override
    public void onLoadMoreRequested() {
        getData(true);
    }

    @OnClick({R.id.backIcon0, R.id.shareIcon0, R.id.backIcon1, R.id.shareIcon1, R.id.hospitalDetailImg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backIcon1:
                onBackPressed();
                break;
            case R.id.shareIcon1:
                shareHospital();
                break;
            case R.id.backIcon0:
                onBackPressed();
                break;
            case R.id.shareIcon0:
                shareHospital();
                break;
            case R.id.hospitalDetailImg:
                if (EmptyUtils.isNotEmpty(hospitalBean)) {
                    ShowBigPicDialog showBigPicDialog = new ShowBigPicDialog(mActivity,
                            null, 1, mPresenter.transfromPhotos(hospitalBean.getHospitalImgUrl()));
                    showBigPicDialog.show();
                }
                break;
        }
    }

    private void shareHospital() {
        if (mHospital != null) {
            ShareData shareData = new ShareData();
            shareData.setShareType(Platform.SHARE_WEBPAGE);
            shareData.setContent(mHospital.getIntroduction());
            shareData.setLinkUrl(mHospital.getIntroduction().replace("\\", ""));
            shareData.setTitile(mHospital.getHospitalName());
            ShareDialog.getBuilder().shareData(shareData).build(this).show();
        } else {
            ToastUtil.getInstance().showToastDialog("获取分享数据失败");
        }
    }
}