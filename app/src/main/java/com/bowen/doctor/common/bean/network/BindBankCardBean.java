package com.bowen.doctor.common.bean.network;

import java.io.Serializable;

/**
 * Describe:
 * Created by zhuzhipeng on 2018/7/9.
 */
public class BindBankCardBean implements Serializable{

    private String bankStatus;
    private String bankCard;
    private String usable;

    public String getBankStatus() {
        return bankStatus;
    }

    public void setBankStatus(String bankStatus) {
        this.bankStatus = bankStatus;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getUsable() {
        return usable;
    }

    public void setUsable(String usable) {
        this.usable = usable;
    }
}
