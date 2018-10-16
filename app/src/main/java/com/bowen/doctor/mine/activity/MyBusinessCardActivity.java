package com.bowen.doctor.mine.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bowen.commonlib.utils.ImageLoaderUtil;
import com.bowen.commonlib.widget.CircleImageView;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseActivity;
import com.bowen.doctor.common.bean.network.DoctorInfo;
import com.bowen.doctor.common.bean.network.DoctorInfoRecord;
import com.bowen.doctor.common.dialog.DoctorBusinessCardDialog;
import com.bowen.doctor.mine.contract.MyBusinessCardContract;
import com.bowen.doctor.mine.presenter.MyBusinessCardPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhuzhipeng
 * @time 2018/7/11 16:08
 * 我的名片
 */
public class MyBusinessCardActivity extends BaseActivity implements MyBusinessCardContract.View {
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
    private Activity mActivity;
    private MyBusinessCardPresenter mPresenter;
    private DoctorInfo doctorInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_business_card);
        ButterKnife.bind(this);
        mActivity = this;
        mPresenter = new MyBusinessCardPresenter(this, this);
        init();
    }

    private void init() {
        setTitle("我的名片");
        mPresenter.getDoctorInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void loadDataSuccess(DoctorInfoRecord record) {
        doctorInfo = record.getEmrDoctor();
        recommendDoctorTipImg.setVisibility(record.getEmrDoctor().isRecommend()? View.VISIBLE:View.GONE);
        ImageLoaderUtil.getInstance().show(mActivity, record.getEmrDoctor().getFileLink(), doctorCardIcon);
        doctorCardNameTv.setText(record.getEmrDoctor().getName());
        doctorCardDepartmentTv.setText(record.getEmrDoctor().getHospitalDepartments());
        doctorCardLevelTv.setText(record.getEmrDoctor().getPositionStr());
        doctorCardHospitalTv.setText(record.getEmrDoctor().getHospital());
        doctorCardTelTv.setText(record.getEmrDoctor().getUserPhone());
        doctorCardAddressTv.setText(record.getEmrDoctor().getAddress());
    }

    @Override
    public void loadFailed() {

    }

    @OnClick(R.id.sendCardBtn)
    public void onViewClicked() {
        if(doctorInfo==null){
            doctorInfo = new DoctorInfo();
        }
        new DoctorBusinessCardDialog(mActivity, doctorInfo).show();
    }
}
