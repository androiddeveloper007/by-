package com.bowen.doctor.common.bean.network;

import com.bowen.doctor.common.bean.Page;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Describe:我的订单
 * Created by AwenZeng on 2018/4/4.
 */

public class MyOrderRecord implements Serializable {

    private ArrayList<MyOrder> orderList;
    private Page page;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<MyOrder> getTraOrderList() {
        return orderList;
    }

    public void setTraOrderList(ArrayList<MyOrder> traOrderList) {
        this.orderList = traOrderList;
    }
}
