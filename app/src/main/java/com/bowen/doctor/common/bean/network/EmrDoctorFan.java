package com.bowen.doctor.common.bean.network;

import java.io.Serializable;

/**
 * Created by zzp on 2018/5/15.
 * 粉丝的bean
 */

public class EmrDoctorFan implements Serializable {
    private String fanId;
    private String userNickname;
    private String fileLink;

    public String getFanId() {
        return fanId;
    }

    public void setFanId(String fanId) {
        this.fanId = fanId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }
}
