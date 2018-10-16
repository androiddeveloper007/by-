package com.bowen.doctor.homepage.model;

import android.content.Context;

import com.bowen.commonlib.base.BaseModel;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.EmptyUtils;
import com.bowen.commonlib.utils.GsonUtil;
import com.bowen.commonlib.utils.LogUtil;
import com.bowen.doctor.common.bean.AppointmentSet;
import com.bowen.doctor.common.bean.network.AppointmentInfo;
import com.bowen.doctor.common.bean.network.AppointmentPeriodInfo;
import com.bowen.doctor.common.bean.network.AppointmentUserInfo;
import com.bowen.doctor.common.bean.network.OutpatientSetRecord;
import com.bowen.doctor.common.bean.network.SitClinic;
import com.bowen.doctor.common.http.HttpContants;
import com.bowen.doctor.common.http.NetworkRequest;
import com.bowen.doctor.common.model.AppConfigInfo;
import com.bowen.doctor.common.model.UserInfo;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Describe:获取该医生门诊预约服务设置
 * Created by zzp on 2018/7/10.
 */

public class OutpatientAppointmentModel extends BaseModel {

    public OutpatientAppointmentModel(Context mContext) {
        super(mContext);
    }

    public void saveOutpatientAppointmentSetInfo(boolean askSwitch, String registerFee, ArrayList<AppointmentSet> data,final HttpTaskCallBack mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("doctorId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("serviceSwitch", askSwitch?1:2);
        networkRequest.setParam("registerFee", registerFee);
        try{
            LogUtil.androidLog(GsonUtil.toJson(data));
            networkRequest.setParam("jsonData", URLEncoder.encode(GsonUtil.toJson(data), "UTF-8"));
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        networkRequest.requestSRV(HttpContants.CMD_SAVE_OUTPATIENT_APPIONTMENT_SET_INFO, "", new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                HttpResult<Object> result = new HttpResult<>();
                if (mListener != null) {
                    mListener.onSuccess(result);
                }
            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                if (mListener != null) {
                    mListener.onFail(httpResult);
                }
            }
        });
    }


    public void getOutpatientAppointmentSetInfo(int week,final HttpTaskCallBack<OutpatientSetRecord> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("doctorId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("weekStatus", week);
        networkRequest.requestSRV(HttpContants.CMD_GET_OUTPATIENT_APPIONTMENT_SET_INFO, "", new HttpTaskCallBack<OutpatientSetRecord>() {
            @Override
            public void onSuccess(HttpResult<OutpatientSetRecord> httpResult) {
                HttpResult<OutpatientSetRecord> result = new HttpResult<>();
                result.copy(httpResult);
                OutpatientSetRecord record = GsonUtil.fromJson(GsonUtil.toJson(httpResult.getData()), OutpatientSetRecord.class);
                if (record != null) {
                    result.setData(record);
                }
                if (mListener != null) {
                    mListener.onSuccess(result);
                }
            }

            @Override
            public void onFail(HttpResult<OutpatientSetRecord> httpResult) {
                if (mListener != null) {
                    mListener.onFail(httpResult);
                }
            }
        });
    }

    /**
     * 获取预约医生的时间段
     * @param date
     * @param type 1:上午 2：下午 3：晚上
     * @param mListener
     */
    public void getAppointmentPeriod(long date,int type,final HttpTaskCallBack<AppointmentPeriodInfo> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("doctorId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("appointmentDate", date);
        networkRequest.setParam("type", type);
        networkRequest.requestSRV(HttpContants.CMD_GET_OUTPATIENT_APPIONTMENT_DAYTIME, new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                HttpResult<AppointmentPeriodInfo> result = new HttpResult<AppointmentPeriodInfo>();
                result.copy(httpResult);
                AppointmentPeriodInfo data = GsonUtil.fromJson(GsonUtil.toJson(httpResult.getData()), AppointmentPeriodInfo.class);
                if (data != null) {
                    result.setData(data);
                }
                if (mListener != null) {
                    mListener.onSuccess(result);
                }
            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                HttpResult<AppointmentPeriodInfo> result = new HttpResult<AppointmentPeriodInfo>();
                result.copy(httpResult);
                if (mListener != null) {
                    mListener.onFail(result);
                }
            }
        });
    }


    public void getDoctorOutpatientAppointmentData(int week,final HttpTaskCallBack<ArrayList<AppointmentInfo>> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("doctorId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("weekStatus", week);
        networkRequest.requestSRV(HttpContants.CMD_GET_DOCTOR_OUTPATIENT_APPIONTMENT, new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                HttpResult<ArrayList<AppointmentInfo>> result = new HttpResult<>();
                result.copy(httpResult);
                try {
                    JSONObject jsonObject = new JSONObject(GsonUtil.toJson(httpResult.getData()));
                    Type typelist = new TypeToken<ArrayList<AppointmentInfo>>() {
                    }.getType();
                    ArrayList<AppointmentInfo> list = GsonUtil.getGson().fromJson(jsonObject.getString("emrAppointmentList"), typelist);
                    if (EmptyUtils.isNotEmpty(list)) {
                        result.setData(list);
                    }
                    if (mListener != null) {
                        mListener.onSuccess(result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                HttpResult<ArrayList<AppointmentInfo>> result = new HttpResult<>();
                result.copy(httpResult);
                if (mListener != null) {
                    mListener.onFail(result);
                }
            }
        });
    }

    public void getOutpatientAppointmentPeroidInfo(long date,int type,int pageNo,int pageSize,final HttpTaskCallBack<AppointmentUserInfo> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("doctorId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("appointmentDate", date);
        networkRequest.setParam("type", type);
        networkRequest.setParam("pageNo", pageNo);
        networkRequest.setParam("pageSize", pageSize);
        networkRequest.requestSRV(HttpContants.CMD_GET_OUTPATIENT_APPIONTMENT_PEROID_INFO, new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                HttpResult<AppointmentUserInfo> result = new HttpResult<AppointmentUserInfo>();
                result.copy(httpResult);
                AppointmentUserInfo data = GsonUtil.fromJson(GsonUtil.toJson(httpResult.getData()), AppointmentUserInfo.class);
                if (data != null) {
                    result.setData(data);
                }
                if (mListener != null) {
                    mListener.onSuccess(result);
                }
            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                HttpResult<AppointmentUserInfo> result = new HttpResult<AppointmentUserInfo>();
                result.copy(httpResult);
                if (mListener != null) {
                    mListener.onFail(result);
                }
            }
        });
    }

    public void getDoctoClinicList(final HttpTaskCallBack<ArrayList<SitClinic>> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("doctorId", UserInfo.getInstance().getUserId());
        networkRequest.requestSRV(HttpContants.CMD_GET_DOCTOR_CLINICLSIT, new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                HttpResult<ArrayList<SitClinic>> result = new HttpResult<>();
                result.copy(httpResult);
                try {
                    JSONObject jsonObject = new JSONObject(GsonUtil.toJson(httpResult.getData()));
                    Type typelist = new TypeToken<ArrayList<SitClinic>>() {
                    }.getType();
                    ArrayList<SitClinic> list = GsonUtil.getGson().fromJson(jsonObject.getString("hospitalVoList"), typelist);
                    if (EmptyUtils.isNotEmpty(list)) {
                        result.setData(list);
                    }
                    if (mListener != null) {
                        mListener.onSuccess(result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                HttpResult<ArrayList<SitClinic>> result = new HttpResult<>();
                result.copy(httpResult);
                if (mListener != null) {
                    mListener.onFail(result);
                }
            }
        });
    }


    /**
     * 设置就诊状态
     * @param appointmentOrderId
     */
    public void setTreatementStatus(String appointmentOrderId,final HttpTaskCallBack<Boolean> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("appointmentOrderId", appointmentOrderId);
        networkRequest.requestSRV(HttpContants.CMD_SET_TREATMENT_STATUS, new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                HttpResult<Boolean> result = new HttpResult<>();
                result.copy(httpResult);
                try {
                    JSONObject jsonObject = new JSONObject(GsonUtil.toJson(httpResult.getData()));
                    boolean isSuccess = jsonObject.optBoolean("isSuccess",false);
                    result.setData(isSuccess);
                    if (mListener != null) {
                        mListener.onSuccess(result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                HttpResult<Boolean> result = new HttpResult<>();
                result.copy(httpResult);
                if (mListener != null) {
                    mListener.onFail(result);
                }
            }
        });
    }

}