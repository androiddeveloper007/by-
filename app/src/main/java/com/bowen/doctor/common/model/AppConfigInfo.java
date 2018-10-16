package com.bowen.doctor.common.model;


import com.bowen.commonlib.event.LocationEvent;

/**
 * 应用配置信息
 * <p>
 * Created by AwenZeng on 2017/5/6.<p>
 */

public class AppConfigInfo {
    private static AppConfigInfo instance = null;

    private String warmWishes;

    private String licenseUrl;

    private LocationEvent locationEvent;


    public static AppConfigInfo getInstance() {
        if (instance == null) {
            synchronized (UserInfo.class) {
                if (instance == null) {
                    instance = new AppConfigInfo();
                }
            }
        }
        return instance;
    }


    public String getWarmWishes() {
        return warmWishes;
    }

    public void setWarmWishes(String warmWishes) {
        this.warmWishes = warmWishes;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    public LocationEvent getLocationEvent() {
        return locationEvent;
    }

    public void setLocationEvent(LocationEvent locationEvent) {
        this.locationEvent = locationEvent;
    }
}
