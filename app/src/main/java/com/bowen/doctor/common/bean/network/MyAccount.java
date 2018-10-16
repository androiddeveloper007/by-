package com.bowen.doctor.common.bean.network;

import java.io.Serializable;

/**
 * Describe:我的订单
 * Created by AwenZeng on 2018/4/4.
 */

public class MyAccount implements Serializable {
    private double totalAmount;
    private double usable;
    private double backable;

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getUsable() {
        return usable;
    }

    public void setUsable(double usable) {
        this.usable = usable;
    }

    public double getBackable() {
        return backable;
    }

    public void setBackable(double backable) {
        this.backable = backable;
    }
}
