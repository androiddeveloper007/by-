package com.bowen.doctor.common.bean.network;

import com.bowen.doctor.common.bean.Page;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zzp on 2018/5/15.
 * 提现记录bean
 */

public class WithdrawRecord implements Serializable {
    private Page page;
    private List<Withdraw> withdrawRecordList;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<Withdraw> getList() {
        return withdrawRecordList;
    }

    public void setList(List<Withdraw> list) {
        this.withdrawRecordList = list;
    }
}
