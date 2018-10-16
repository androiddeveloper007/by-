package com.bowen.doctor.common.model;

/**
 * Created by zzp on 2018/7/26.
 */

public class UserInfo {
    private static UserInfo instance = null;
    private String userMobile;
    private String userLoginName;
    private String userId;
    private String token;
    private String userNickname;
    private String picUrl;
    private String familyId;
    private boolean isBindPhone = false;
    private boolean isData = false;
    private boolean isStartChat = false;//是否开始聊天
    private boolean isChatServerLoginSuccess = false;
    /**
     * hasUndoneOrder : true
     * identify : 5
     * positionStr : 主任医师
     * isData : true
     * hospitalDepartments : 男科
     * userType : 1
     * hospital : 河北人民医院
     */

    private boolean hasUndoneOrder;
    private int identify;
    private String positionStr;
    private String hospitalDepartments;
    private int userType;
    private String hospital;


    public static UserInfo getInstance() {
        if (instance == null) {
            synchronized (UserInfo.class) {
                if (instance == null) {
                    instance = new UserInfo();
                }
            }
        }
        return instance;
    }

    public boolean isSelf(String userId){
        return userId.equals(userId);
    }


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


    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public boolean isBindPhone() {
        return isBindPhone;
    }

    public void setBindPhone(boolean bindPhone) {
        isBindPhone = bindPhone;
    }

    public boolean isData() {
        return isData;
    }

    public void setData(boolean data) {
        isData = data;
    }

    public boolean isStartChat() {
        return isStartChat;
    }

    public void setStartChat(boolean startChat) {
        isStartChat = startChat;
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

    public boolean isIsData() {
        return isData;
    }

    public void setIsData(boolean isData) {
        this.isData = isData;
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

    public boolean isChatServerLoginSuccess() {
        return isChatServerLoginSuccess;
    }

    public void setChatServerLoginSuccess(boolean chatServerLoginSuccess) {
        isChatServerLoginSuccess = chatServerLoginSuccess;
    }
}
