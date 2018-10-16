package com.bowen.doctor.common.bean.network;

import java.io.Serializable;

public class OnlinePrescriptionBean implements Serializable {
    String isCollect;
    String prescriptionId;
    String prescriptionName;
    String applyCrowdStr;
    String applyDisease;
    String prescriptionSectionStr;
    String usageDosage;
    String shareUrl;
    String shareContent;
    String commentCount;
    String prescriptionSource;

    public String getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(String isCollect) {
        this.isCollect = isCollect;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getPrescriptionName() {
        return prescriptionName;
    }

    public void setPrescriptionName(String prescriptionName) {
        this.prescriptionName = prescriptionName;
    }

    public String getApplyCrowdStr() {
        return applyCrowdStr;
    }

    public void setApplyCrowdStr(String applyCrowdStr) {
        this.applyCrowdStr = applyCrowdStr;
    }

    public String getApplyDisease() {
        return applyDisease;
    }

    public void setApplyDisease(String applyDisease) {
        this.applyDisease = applyDisease;
    }

    public String getPrescriptionSectionStr() {
        return prescriptionSectionStr;
    }

    public void setPrescriptionSectionStr(String prescriptionSectionStr) {
        this.prescriptionSectionStr = prescriptionSectionStr;
    }

    public String getUsageDosage() {
        return usageDosage;
    }

    public void setUsageDosage(String usageDosage) {
        this.usageDosage = usageDosage;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getPrescriptionSource() {
        return prescriptionSource;
    }

    public void setPrescriptionSource(String prescriptionSource) {
        this.prescriptionSource = prescriptionSource;
    }
}
