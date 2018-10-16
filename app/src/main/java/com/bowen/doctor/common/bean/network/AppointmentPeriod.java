package com.bowen.doctor.common.bean.network;

/**
 * Describe:
 * Created by AwenZeng on 2018/7/23.
 */
public class AppointmentPeriod {
    /**
     * appointmentId :
     * typeName :
     * applyNum :
     * maxNum :
     */

    private String appointmentId;
    private String typeName;
    private int applyNum;
    private String maxNumStr;
    private int pos;//adpter中的位置

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(int applyNum) {
        this.applyNum = applyNum;
    }

    public String getMaxNumStr() {
        return maxNumStr;
    }

    public void setMaxNumStr(String maxNumStr) {
        this.maxNumStr = maxNumStr;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
