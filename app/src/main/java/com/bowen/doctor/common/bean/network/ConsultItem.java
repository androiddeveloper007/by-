package com.bowen.doctor.common.bean.network;

import java.io.Serializable;

/**
 * Describe:咨询
 * Created by AwenZeng on 2018/4/4.
 */

public class ConsultItem implements Serializable{

    /**
     * userId :
     * userNickname :
     * headImgUrl :
     * isRead :
     * lastConsultTime :
     * content :
     * orderId :
     */

    private String userId;
    private String userNickname;
    private String headImgUrl;
    private boolean isRead;
    private long lastConsultTime;
    private String content;
    private String orderId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public boolean isIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public long getLastConsultTime() {
        return lastConsultTime;
    }

    public void setLastConsultTime(long lastConsultTime) {
        this.lastConsultTime = lastConsultTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
