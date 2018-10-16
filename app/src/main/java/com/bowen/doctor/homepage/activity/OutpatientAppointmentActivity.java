package com.bowen.doctor.homepage.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.DateUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseActivity;
import com.bowen.doctor.common.bean.DateNum;
import com.bowen.doctor.common.bean.ReservationBean;
import com.bowen.doctor.common.bean.network.AppointmentInfo;
import com.bowen.doctor.common.bean.network.AppointmentUserInfo;
import com.bowen.doctor.common.model.Constants;
import com.bowen.doctor.common.widget.ReservationView;
import com.bowen.doctor.common.widget.ReservationView.ReservationTimeListener;
import com.bowen.doctor.homepage.adapter.AppointmentUserAdapter;
import com.bowen.doctor.homepage.model.OutpatientAppointmentModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Describe:门诊预约
 * Created by zzp on 2018/7/20.
 */

public class OutpatientAppointmentActivity extends BaseActivity {
    @BindView(R.id.mLastWeekTv)
    TextView mLastWeekTv;
    @BindView(R.id.mNextWeekTv)
    TextView mNextWeekTv;
    @BindView(R.id.mReservationView)
    ReservationView mReservationView;
    @BindView(R.id.mChooseTimeTv)
    TextView mChooseTimeTv;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private OutpatientAppointmentModel mModel;
    private AppointmentUserAdapter mAdapter;
    private int week = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_outpatient_appointment);
        ButterKnife.bind(this);
        initView();
        mModel = new OutpatientAppointmentModel(this);
        getData(week);
    }

    private void initView() {
        setTitle("门诊预约");
        mAdapter = new AppointmentUserAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);

        mReservationView.setListener(new ReservationTimeListener() {
            @Override
            public void onReservationTime(DateNum dateNum, int pos) {
                StringBuilder builder = new StringBuilder();
                builder.append(DateUtil.date2String(dateNum.getDateTime(),DateUtil.DATE_MONTH_FORMAT)).append(" ")
                        .append(dateNum.getWeek()).append(" ").append(dateNum.getDayTypeStr());
                mChooseTimeTv.setText(builder.toString());
                getDetailData(dateNum.getDateTime(),dateNum.getDayType());
                mReservationView.setChooseItem(dateNum);
            }
        });
    }

    @OnClick({R.id.mLastWeekTv, R.id.mNextWeekTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mLastWeekTv:
                week--;
                getData(week);
                mReservationView.setChooseItem(null);
                if (week <= 0) {
                    mLastWeekTv.setVisibility(android.view.View.GONE);
                } else {
                    mLastWeekTv.setVisibility(android.view.View.VISIBLE);
                }
                break;
            case R.id.mNextWeekTv:
                week++;
                getData(week);
                mReservationView.setChooseItem(null);
                if (week >= 1) {
                    mLastWeekTv.setVisibility(android.view.View.VISIBLE);
                } else {
                    mLastWeekTv.setVisibility(android.view.View.GONE);
                }
                break;
        }
    }


    private void getData(int week){
        mModel.getDoctorOutpatientAppointmentData(week, new HttpTaskCallBack<ArrayList<AppointmentInfo>>() {
            @Override
            public void onSuccess(HttpResult<ArrayList<AppointmentInfo>> result) {
                ArrayList<ReservationBean> reserveList = new ArrayList<>();
                ArrayList<AppointmentInfo> list = result.getData();
                for (AppointmentInfo info :list) {
                    ReservationBean bean = new ReservationBean();
                    bean.setDate(DateUtil.date2String(info.getAppointmentDate(), DateUtil.DATE_MONTH_FORMAT));
                    bean.setWeek(info.getWeek());
                    bean.setDateTime(info.getAppointmentDate());
                    bean.setAppNumStr(info.getAppNumStr().split(",",3));
                    bean.setDayTime(info.getTypeStr().split(",",3));
                    String[] status = info.getAppStatus().split(",",3);
                    int[] reserveStats = new int[3];
                    for (int i = 0; i < status.length; i++) {
                        if(Integer.parseInt(status[i]) == Constants.STATUS_APPIONTMENT_SET){
                            reserveStats[i] = Constants.STATUS_APPIONTMENT_PEOPLECOUNT_SHOW;
                        }else{
                            reserveStats[i] =Constants.STATUS_APPIONTMENT_NOT;
                        }
                    }
                    bean.setReserveStatus(reserveStats);
                    reserveList.add(bean);
                }
                mReservationView.setReservationBeans(reserveList);
            }

            @Override
            public void onFail(HttpResult<ArrayList<AppointmentInfo>> result) {

            }
        });
    }

    private void getDetailData(long time,int dayType){
        mModel.getOutpatientAppointmentPeroidInfo(time, dayType, 1, 10, new HttpTaskCallBack<AppointmentUserInfo>() {
            @Override
            public void onSuccess(HttpResult<AppointmentUserInfo> result) {
                mAdapter.setNewData(result.getData().getVoList());
            }

            @Override
            public void onFail(HttpResult<AppointmentUserInfo> result) {

            }
        });
    }
}
