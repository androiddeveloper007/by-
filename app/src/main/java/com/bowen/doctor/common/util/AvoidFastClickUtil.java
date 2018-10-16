package com.bowen.doctor.common.util;

/**
 * @author zhuzhipeng
 * @time 2018/9/5 14:51
 * 禁止快速点击
 */
public class AvoidFastClickUtil {

    private static long lastClickTime;

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
