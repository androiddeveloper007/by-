package com.bowen.doctor.common.bean.network;

import java.io.Serializable;

/**
 * Describe:门诊预约分页实体类
 * Created by zhuzhipeng on 2018/4/4.
 */

public class DoctorUploadPhoto implements Serializable {

    private String fileLink;
    private String fileCode;

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }
}
