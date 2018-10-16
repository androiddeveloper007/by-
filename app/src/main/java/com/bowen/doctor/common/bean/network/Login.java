package com.bowen.doctor.common.bean.network;

/**
 * Created by AwenZeng on 2016/12/24.
 */

public class Login {
    private String userMobile;
    private String userLoginName;
    private String userId;
    private String token;
    private String userNickname;
    private String familyId;
    private String picUrl;

    private int failCount;
    private int isIdentificated;
    private boolean isData;
    private boolean hasUndoneOrder;//是否还有在咨询的单
    /**
     * identify : 5
     * positionStr : 主任医师
     * isData : true
     * hospitalDepartments : 男科
     * userType : 1
     * hospital : 河北人民医院
     */

    private int identify;
    private String positionStr;
    private String hospitalDepartments;
    private int userType;
    private String hospital;


    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }


    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getIsIdentificated() {
        return isIdentificated;
    }

    public void setIsIdentificated(int isIdentificated) {
        this.isIdentificated = isIdentificated;
    }

    public boolean getIsData() {
        return isData;
    }

    public void setIsData(boolean isData) {
        this.isData = isData;
    }

    public boolean isHasUndoneOrder() {
        return hasUndoneOrder;
    }

    public void setHasUndoneOrder(boolean hasUndoneOrder) {
        this.hasUndoneOrder = hasUndoneOrder;
    }

    public int getIdentify() {
        return identify;
    }

    public void setIdentify(int identify) {
        this.identify = identify;
    }

    public String getPositionStr() {
        return positionStr;
    }

    public void setPositionStr(String positionStr) {
        this.positionStr = positionStr;
    }

    public String getHospitalDepartments() {
        return hospitalDepartments;
    }

    public void setHospitalDepartments(String hospitalDepartments) {
        this.hospitalDepartments = hospitalDepartments;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }
}
