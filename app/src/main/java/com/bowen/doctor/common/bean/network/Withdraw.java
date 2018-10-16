package com.bowen.doctor.common.bean.network;

import java.io.Serializable;

/**
 * Created by zzp on 2018/5/15.
 * 提现记录bean
 */

public class Withdraw implements Serializable {

    /**
     * operAmount :
     * useable :
     * withdrawStatusStr :
     * createTime :
     */

    private double applyAmount;
    private double useable;
    private String withdrawStatusStr;
    private String withdrawStatus;
    private long applyTime;
    private String withdrawBankCardNo;

    public double getUseable() {
        return useable;
    }

    public void setUseable(double useable) {
        this.useable = useable;
    }

    public String getWithdrawStatusStr() {
        return withdrawStatusStr;
    }

    public void setWithdrawStatusStr(String withdrawStatusStr) {
        this.withdrawStatusStr = withdrawStatusStr;
    }

    public String getWithdrawStatus() {
        return withdrawStatus;
    }

    public void setWithdrawStatus(String withdrawStatus) {
        this.withdrawStatus = withdrawStatus;
    }

    public double getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(double applyAmount) {
        this.applyAmount = applyAmount;
    }

    public long getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(long applyTime) {
        this.applyTime = applyTime;
    }

    public String getWithdrawBankCardNo() {
        return withdrawBankCardNo;
    }

    public void setWithdrawBankCardNo(String withdrawBankCardNo) {
        this.withdrawBankCardNo = withdrawBankCardNo;
    }
}
