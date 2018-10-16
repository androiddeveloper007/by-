package com.bowen.doctor.homepage.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseDialog.BaseDialogListener;
import com.bowen.commonlib.dialog.AffirmDialog;
import com.bowen.commonlib.utils.DateUtil;
import com.bowen.commonlib.utils.EmptyUtils;
import com.bowen.commonlib.utils.ToastUtil;
import com.bowen.commonlib.widget.SwitchButton;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseActivity;
import com.bowen.doctor.common.bean.AppointmentSet;
import com.bowen.doctor.common.bean.DateNum;
import com.bowen.doctor.common.bean.ReservationBean;
import com.bowen.doctor.common.bean.network.AppointmentInfo;
import com.bowen.doctor.common.bean.network.AppointmentPeriod;
import com.bowen.doctor.common.bean.network.AppointmentPeriodInfo;
import com.bowen.doctor.common.bean.network.OutpatientSetRecord;
import com.bowen.doctor.common.bean.network.SitClinic;
import com.bowen.doctor.common.dialog.ChooseSitClinicDialog;
import com.bowen.doctor.common.model.Constants;
import com.bowen.doctor.common.widget.ReservationView;
import com.bowen.doctor.common.widget.ReservationView.ReservationTimeListener;
import com.bowen.doctor.homepage.adapter.OutpatientAppiontmentSetTimeAdapter;
import com.bowen.doctor.homepage.contract.OutpatientAppointmentSetContract.View;
import com.bowen.doctor.homepage.presenter.OutpatientAppointmentSetPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Describe:
 * Created by zzp on 2018/3/16.
 */

public class OutpatientAppiontmentSetActivity extends BaseActivity implements View {
    @BindView(R.id.mOpenServiceSwitchBtn)
    SwitchButton mOpenServiceSwitchBtn;
    @BindView(R.id.openService)
    RelativeLayout openService;
    @BindView(R.id.mRegistFeeEditText)
    EditText mRegistFeeEditText;
    @BindView(R.id.mLastWeekTv)
    TextView mLastWeekTv;
    @BindView(R.id.mNextWeekTv)
    TextView mNextWeekTv;
    @BindView(R.id.mReservationView)
    ReservationView mReservationView;
    @BindView(R.id.mReceptionSwitchBtn)
    SwitchButton mReceptionSwitchBtn;
    @BindView(R.id.mChooseClinicTv)
    TextView mChooseClinicTv;
    @BindView(R.id.mServiceLayout)
    LinearLayout mServiceLayout;
    @BindView(R.id.mReservationPeriodLayout)
    LinearLayout mReservationPeriodLayout;
    @BindView(R.id.mTreatementLayout)
    LinearLayout mTreatementLayout;
    @BindView(R.id.mTimePeriod01Tv)
    TextView mTimePeriod01Tv;
    @BindView(R.id.mPeopleCount01EditText)
    EditText mPeopleCount01EditText;
    @BindView(R.id.mTimePeriod02Tv)
    TextView mTimePeriod02Tv;
    @BindView(R.id.mPeopleCount02EditText)
    EditText mPeopleCount02EditText;
    @BindView(R.id.mTimePeriod03Tv)
    TextView mTimePeriod03Tv;
    @BindView(R.id.mPeopleCount03EditText)
    EditText mPeopleCount03EditText;
    @BindView(R.id.mTimePeriod04Tv)
    TextView mTimePeriod04Tv;
    @BindView(R.id.mPeopleCount04EditText)
    EditText mPeopleCount04EditText;
    @BindView(R.id.mTimePeriod05Tv)
    TextView mTimePeriod05Tv;
    @BindView(R.id.mPeopleCount05EditText)
    EditText mPeopleCount05EditText;
    @BindView(R.id.mTimePeriod06Tv)
    TextView mTimePeriod06Tv;
    @BindView(R.id.mPeopleCount06EditText)
    EditText mPeopleCount06EditText;


    private int week = 0;
    private DateNum mDateNum;
    private String mClinicName;
    private String mRegistFee;
    private boolean isOpenServer = false;
    private boolean isReception = false;
    private SitClinic mSitClinic;
    private String mPeopleCount01Str;
    private String mPeopleCount02Str;
    private String mPeopleCount03Str;
    private String mPeopleCount04Str;
    private String mPeopleCount05Str;
    private String mPeopleCount06Str;

    private OutpatientAppointmentSetPresenter mPresenter;
    private OutpatientSetRecord mOutpatientSetRecord;
    private ArrayList<AppointmentPeriod> mAppointmentPeroidList;
    private ArrayList<AppointmentSet> mAppointmentSets;
    private HashMap<String, AppointmentSet> mAppointmentSetMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_outpatient_appiontment_set);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("门诊预约设置");
        getTitleBar().getRightTextButton().setVisibility(android.view.View.VISIBLE);
        getTitleBar().getRightTextButton().setText("保存");

        mAppointmentSetMap = new HashMap<>();
        mPresenter = new OutpatientAppointmentSetPresenter(this, this);
        mPresenter.getOutpatientAppointmentSetInfo(week);
        mReservationView.setListener(new ReservationTimeListener() {
            @Override
            public void onReservationTime(DateNum dateNum, int pos) {
                if (checkAppointmentPeroidContent()) {
                    mDateNum = dateNum;
                    ArrayList<ReservationBean> reservationBeans = mReservationView.getReservationBeans();
                    ReservationBean bean = reservationBeans.get(pos);
                    int[] temp = bean.getReserveStatus();
                    temp[dateNum.getDayType() - 1] = Constants.STATUS_APPIONTMENT_SET;
                    bean.setReserveStatus(temp);
                    reservationBeans.set(pos, bean);
                    mReservationView.setReservationBeans(reservationBeans);
                    mReservationView.setChooseItem(dateNum);
                    mTreatementLayout.setVisibility(android.view.View.VISIBLE);
                    mReceptionSwitchBtn.setChecked(true);
                    mPresenter.getAppointmentPeriod(dateNum.getDateTime(), dateNum.getDayType());
                }
            }
        });
        mOpenServiceSwitchBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mServiceLayout.setVisibility(android.view.View.VISIBLE);
                    isOpenServer = true;
                } else {
                    mServiceLayout.setVisibility(android.view.View.GONE);
                    isOpenServer = false;
                }
            }
        });
        mReceptionSwitchBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mReservationPeriodLayout.setVisibility(android.view.View.VISIBLE);
                    isReception = true;
                } else {
                    mReservationPeriodLayout.setVisibility(android.view.View.GONE);
                    isReception = false;
                }
            }
        });

    }

    private void updateUI() {
        if (EmptyUtils.isNotEmpty(mOutpatientSetRecord)) {
            if (mOutpatientSetRecord.getServiceSwitch() == Constants.SWITCH_OPEN) {
                mServiceLayout.setVisibility(android.view.View.VISIBLE);
                mOpenServiceSwitchBtn.setChecked(true);
                isOpenServer = true;
            } else {
                mServiceLayout.setVisibility(android.view.View.GONE);
                mOpenServiceSwitchBtn.setChecked(false);
                isOpenServer = false;
            }
            mRegistFeeEditText.setText(mOutpatientSetRecord.getRegisterFee());
            //预约
            ArrayList<ReservationBean> reserveList = new ArrayList<>();
            for (AppointmentInfo info : mOutpatientSetRecord.getEmrAppointmentList()) {
                ReservationBean bean = new ReservationBean();
                bean.setDate(DateUtil.date2String(info.getAppointmentDate(), DateUtil.DATE_MONTH_FORMAT));
                bean.setWeek(info.getWeek());
                bean.setDateTime(info.getAppointmentDate());
                bean.setDayTime(info.getTypeStr().split(",", 3));
                String[] status = info.getAppStatus().split(",", 3);
                int[] reserveStats = new int[3];
                for (int i = 0; i < status.length; i++) {
                    reserveStats[i] = Integer.parseInt(status[i]);
                }
                bean.setReserveStatus(reserveStats);
                reserveList.add(bean);
            }
            mReservationView.setReservationBeans(reserveList);
            if (week <= 0) {
                mLastWeekTv.setVisibility(android.view.View.GONE);
            } else {
                mLastWeekTv.setVisibility(android.view.View.VISIBLE);
            }
            mReceptionSwitchBtn.setChecked(isReception);
        }
    }


    private boolean checkImputContent() {
        mRegistFee = mRegistFeeEditText.getText().toString();
        if (EmptyUtils.isEmpty(mRegistFee)) {
            ToastUtil.getInstance().showToastDialog("请输入挂号费");
            return false;
        }

        return checkAppointmentPeroidContent();
    }


    private boolean checkAppointmentPeroidContent() {
        if (mTreatementLayout.getVisibility() == android.view.View.VISIBLE) {
            mClinicName = mChooseClinicTv.getText().toString();
            if(isReception){
                if (mClinicName.equals("请选择")) {
                    ToastUtil.getInstance().showToastDialog("请选择坐诊医馆");
                    return false;
                }

                mPeopleCount01Str = mPeopleCount01EditText.getText().toString();
                mPeopleCount02Str = mPeopleCount02EditText.getText().toString();
                mPeopleCount03Str = mPeopleCount03EditText.getText().toString();
                mPeopleCount04Str = mPeopleCount04EditText.getText().toString();
                mPeopleCount05Str = mPeopleCount05EditText.getText().toString();
                mPeopleCount06Str = mPeopleCount06EditText.getText().toString();

                if (EmptyUtils.isEmpty(mPeopleCount01Str)) {
                    ToastUtil.getInstance().showToastDialog(String.format("请输入%s接诊人数", mAppointmentPeroidList.get(0).getTypeName()));
                    return false;
                }else if(Integer.parseInt(mPeopleCount01Str) > 15){
                    ToastUtil.getInstance().showToastDialog(String.format("%s接诊人数最多15人", mAppointmentPeroidList.get(0).getTypeName()));
                    return false;
                }

                if (EmptyUtils.isEmpty(mPeopleCount02Str)) {
                    ToastUtil.getInstance().showToastDialog(String.format("请输入%s接诊人数", mAppointmentPeroidList.get(1).getTypeName()));
                    return false;
                }else if(Integer.parseInt(mPeopleCount02Str) > 15){
                    ToastUtil.getInstance().showToastDialog(String.format("%s接诊人数最多15人", mAppointmentPeroidList.get(1).getTypeName()));
                    return false;
                }


                if (EmptyUtils.isEmpty(mPeopleCount03Str)) {
                    ToastUtil.getInstance().showToastDialog(String.format("请输入%s接诊人数", mAppointmentPeroidList.get(2).getTypeName()));
                    return false;
                }else if(Integer.parseInt(mPeopleCount03Str) > 15){
                    ToastUtil.getInstance().showToastDialog(String.format("%s接诊人数最多15人", mAppointmentPeroidList.get(2).getTypeName()));
                    return false;
                }

                if (EmptyUtils.isEmpty(mPeopleCount04Str)) {
                    ToastUtil.getInstance().showToastDialog(String.format("请输入%s接诊人数", mAppointmentPeroidList.get(3).getTypeName()));
                    return false;
                }else if(Integer.parseInt(mPeopleCount04Str) > 15){
                    ToastUtil.getInstance().showToastDialog(String.format("%s接诊人数最多15人", mAppointmentPeroidList.get(3).getTypeName()));
                    return false;
                }


                if (EmptyUtils.isEmpty(mPeopleCount05Str)) {
                    ToastUtil.getInstance().showToastDialog(String.format("请输入%s接诊人数", mAppointmentPeroidList.get(4).getTypeName()));
                    return false;
                }else if(Integer.parseInt(mPeopleCount05Str) > 15){
                    ToastUtil.getInstance().showToastDialog(String.format("%s接诊人数最多15人", mAppointmentPeroidList.get(4).getTypeName()));
                    return false;
                }

                if (EmptyUtils.isEmpty(mPeopleCount06Str)) {
                    ToastUtil.getInstance().showToastDialog(String.format("请输入%s接诊人数", mAppointmentPeroidList.get(5).getTypeName()));
                    return false;
                }else if(Integer.parseInt(mPeopleCount06Str) > 15){
                    ToastUtil.getInstance().showToastDialog(String.format("%s接诊人数最多15人", mAppointmentPeroidList.get(5).getTypeName()));
                    return false;
                }
            }

            AppointmentSet appointmentSet = mAppointmentSetMap.get(mDateNum.getDateTime() + mDateNum.getDayType() + "");
            appointmentSet.setIsReception(isReception ? 1 : 0);
            if (isReception) {
                if (EmptyUtils.isNotEmpty(mSitClinic)) {
                    appointmentSet.setClinicName(mSitClinic.getHospital());
                    appointmentSet.setClinicAddress(mSitClinic.getAddress());
                }
                StringBuilder builder = new StringBuilder();
                if(EmptyUtils.isNotEmpty(mAppointmentPeroidList)){
                    for (int j = 0; j < mAppointmentPeroidList.size(); j++) {
                        if (j != 5) {
                            switch (j) {
                                case 0:
                                    builder.append(mPeopleCount01Str + ",");
                                    break;
                                case 1:
                                    builder.append(mPeopleCount02Str + ",");
                                    break;
                                case 2:
                                    builder.append(mPeopleCount03Str + ",");
                                    break;
                                case 3:
                                    builder.append(mPeopleCount04Str + ",");
                                    break;
                                case 4:
                                    builder.append(mPeopleCount05Str + ",");
                                    break;
                            }
                        } else {
                            builder.append(mPeopleCount06Str);
                        }
                  }
                }else{
                    for (int j = 0; j < 6; j++) {
                        if (j != 5) {
                            switch (j) {
                                case 0:
                                    builder.append(mPeopleCount01Str + ",");
                                    break;
                                case 1:
                                    builder.append(mPeopleCount02Str + ",");
                                    break;
                                case 2:
                                    builder.append(mPeopleCount03Str + ",");
                                    break;
                                case 3:
                                    builder.append(mPeopleCount04Str + ",");
                                    break;
                                case 4:
                                    builder.append(mPeopleCount05Str + ",");
                                    break;
                            }
                        } else {
                            builder.append(mPeopleCount06Str);
                        }
                    }
                }
                appointmentSet.setPeopleCount(builder.toString());
            }
            mAppointmentSetMap.put(mDateNum.getDateTime() + mDateNum.getDayType() + "", appointmentSet);
            return true;
        }else{
            return true;
        }
}


    @Override
    public void onRightButtonPressed() {
        super.onRightButtonPressed();
        saveSettingData();
    }

    private void saveSettingData(){
        if(EmptyUtils.isNotEmpty(mAppointmentSets)){
            mAppointmentSets.clear();
            mAppointmentSets = null;
            mAppointmentSets = new ArrayList<>();
        }else{
            mAppointmentSets = new ArrayList<>();
        }
        if (checkImputContent()) {
            Iterator iterator = mAppointmentSetMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, AppointmentSet> entry = (Entry<String, AppointmentSet>) iterator.next();
                mAppointmentSets.add(entry.getValue());
            }
            mPresenter.saveOutpatientAppointmentSetInfo(isOpenServer, mRegistFee, mAppointmentSets);
        }
    }


    @Override
    public void onGetOutpatientAppointmentInfoSuccess(OutpatientSetRecord recordInfo) {
        mOutpatientSetRecord = recordInfo;
        updateUI();
    }

    @Override
    public void onGetAppointmentPeriodSuccess(AppointmentPeriodInfo info) {
        mAppointmentPeroidList = info.getEmrAppointmentList();
        if (mAppointmentSetMap.containsKey(mDateNum.getDateTime() + mDateNum.getDayType() + "")){
            AppointmentSet tempSet =  mAppointmentSetMap.get(mDateNum.getDateTime() + mDateNum.getDayType() + "");
            String[] peopleCounts = tempSet.getPeopleCount().split(",", 6);
            String[] typeNames = tempSet.getTypeName().split(",", 6);
            mChooseClinicTv.setText(tempSet.getClinicName());
            for (int i = 0; i < peopleCounts.length; i++) {
                String typeName = typeNames[i];
                String peopleCount = peopleCounts[i];
                switch (i) {
                    case 0:
                        mTimePeriod01Tv.setText(typeName);
                        mPeopleCount01EditText.setText(peopleCount);
                        break;
                    case 1:
                        mTimePeriod02Tv.setText(typeName);
                        mPeopleCount02EditText.setText(peopleCount);
                        break;
                    case 2:
                        mTimePeriod03Tv.setText(typeName);
                        mPeopleCount03EditText.setText(peopleCount);
                        break;
                    case 3:
                        mTimePeriod04Tv.setText(typeName);
                        mPeopleCount04EditText.setText(peopleCount);
                        break;
                    case 4:
                        mTimePeriod05Tv.setText(typeName);
                        mPeopleCount05EditText.setText(peopleCount);
                        break;
                    case 5:
                        mTimePeriod06Tv.setText(typeName);
                        mPeopleCount06EditText.setText(peopleCount);
                        break;
                    default:
                        break;
                }
            }
        } else {
            AppointmentSet appointmentSet = new AppointmentSet();

            if (EmptyUtils.isNotEmpty(info.getHospital())) {
                mChooseClinicTv.setText(info.getHospital());
                mChooseClinicTv.setTextColor(getResources().getColor(R.color.color_main_black));
            }else{
                mChooseClinicTv.setText("请选择");
                mChooseClinicTv.setTextColor(getResources().getColor(R.color.color_main_gray));
            }
            if (EmptyUtils.isNotEmpty(mAppointmentPeroidList)) {
                for (int i = 0; i < mAppointmentPeroidList.size(); i++) {
                    AppointmentPeriod item = mAppointmentPeroidList.get(i);
                    switch (i) {
                        case 0:
                            mTimePeriod01Tv.setText(item.getTypeName());
                            mPeopleCount01EditText.setText(item.getMaxNumStr());
                            break;
                        case 1:
                            mTimePeriod02Tv.setText(item.getTypeName());
                            mPeopleCount02EditText.setText(item.getMaxNumStr());
                            break;
                        case 2:
                            mTimePeriod03Tv.setText(item.getTypeName());
                            mPeopleCount03EditText.setText(item.getMaxNumStr());
                            break;
                        case 3:
                            mTimePeriod04Tv.setText(item.getTypeName());
                            mPeopleCount04EditText.setText(item.getMaxNumStr());
                            break;
                        case 4:
                            mTimePeriod05Tv.setText(item.getTypeName());
                            mPeopleCount05EditText.setText(item.getMaxNumStr());
                            break;
                        case 5:
                            mTimePeriod06Tv.setText(item.getTypeName());
                            mPeopleCount06EditText.setText(item.getMaxNumStr());
                            break;
                        default:
                            break;
                    }
                }
                StringBuilder builder = new StringBuilder();
                StringBuilder builder1 = new StringBuilder();
                StringBuilder builder2 = new StringBuilder();
                for (int j = 0; j < mAppointmentPeroidList.size(); j++) {
                    AppointmentPeriod item1 = mAppointmentPeroidList.get(j);
                    if (j != 5) {
                        builder1.append(item1.getAppointmentId() + ",");
                        builder2.append(item1.getTypeName()+",");
                        switch (j) {
                            case 0:
                                builder.append(item1.getMaxNumStr() + ",");
                                break;
                            case 1:
                                builder.append(item1.getMaxNumStr() + ",");
                                break;
                            case 2:
                                builder.append(item1.getMaxNumStr() + ",");
                                break;
                            case 3:
                                builder.append(item1.getMaxNumStr() + ",");
                                break;
                            case 4:
                                builder.append(item1.getMaxNumStr() + ",");
                                break;
                        }
                    } else {
                        builder.append(item1.getMaxNumStr());
                        builder1.append(item1.getAppointmentId());
                        builder2.append(item1.getTypeName());
                    }
                }
                appointmentSet.setPeopleCount(builder.toString());
                if(EmptyUtils.isNotEmpty(builder1.toString())){
                    appointmentSet.setAppointmentId(builder1.toString());
                }
                if(EmptyUtils.isNotEmpty(builder2.toString())){
                    appointmentSet.setTypeName(builder2.toString());
                }
            }
            appointmentSet.setClinicName(info.getHospital());
            appointmentSet.setDate(mDateNum.getDateTime());
            appointmentSet.setDayType(mDateNum.getDayType());
            appointmentSet.setIsReception(isReception ? 1 : 0);
            mAppointmentSetMap.put(mDateNum.getDateTime() + mDateNum.getDayType() + "", appointmentSet);
        }
    }


    @Override
    public void onSaveOutpatientAppointmentSetInfoSuccess() {
        finish();
        ToastUtil.getInstance().toast("门诊预约设置成功");
    }


    @OnClick({R.id.mLastWeekTv, R.id.mNextWeekTv, R.id.mChooseClinicTv})
    public void onViewClicked(android.view.View view) {
        switch (view.getId()) {
            case R.id.mLastWeekTv:
                if (!checkAppointmentPeroidContent()) {
                    return;
                }
                mReservationView.setChooseItem(null);
                week--;
                mPresenter.getOutpatientAppointmentSetInfo(week);
                if (week <= 0) {
                    mLastWeekTv.setVisibility(android.view.View.GONE);
                } else {
                    mLastWeekTv.setVisibility(android.view.View.VISIBLE);
                }
                mTreatementLayout.setVisibility(android.view.View.GONE);
                break;
            case R.id.mNextWeekTv:
                if (!checkAppointmentPeroidContent()) {
                    return;
                }
                mReservationView.setChooseItem(null);
                week++;
                mPresenter.getOutpatientAppointmentSetInfo(week);
                if (week >= 1) {
                    mLastWeekTv.setVisibility(android.view.View.VISIBLE);
                } else {
                    mLastWeekTv.setVisibility(android.view.View.GONE);
                }
                mTreatementLayout.setVisibility(android.view.View.GONE);
                break;
            case R.id.mChooseClinicTv:
                ChooseSitClinicDialog chooseSitClinicDialog = new ChooseSitClinicDialog(this);
                chooseSitClinicDialog.show();
                chooseSitClinicDialog.setBaseDialogListener(new BaseDialogListener() {
                    @Override
                    public void onDataCallBack(Object... obj) {
                        mSitClinic = (SitClinic) obj[0];
                        mClinicName = mSitClinic.getHospital();
                        mChooseClinicTv.setText(mClinicName);
                        mChooseClinicTv.setTextColor(getResources().getColor(R.color.color_main_black));
                    }
                });

                break;
        }
    }

    @Override
    public void onBackPressed() {
        mRegistFee = mRegistFeeEditText.getText().toString();
        if(EmptyUtils.isNotEmpty(mAppointmentSetMap)||!mRegistFee.equals(mOutpatientSetRecord.getRegisterFee())){
            AffirmDialog affirmDialog = new AffirmDialog(this,
                    "温馨提示", "服务设置已变更，是否保存？"
                    , "不了", "保存设置");
            affirmDialog.setAffirmDialogListenner(new AffirmDialog.AffirmDialogListenner() {
                @Override
                public void onCancle() {
                    finish();
                }

                @Override
                public void onOK() {
                    saveSettingData();
                }
            });
            affirmDialog.show();
        }else{
            super.onBackPressed();
        }
    }
}