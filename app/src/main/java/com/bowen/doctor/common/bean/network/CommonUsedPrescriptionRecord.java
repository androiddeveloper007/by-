package com.bowen.doctor.common.bean.network;

import com.bowen.doctor.common.bean.Page;

import java.io.Serializable;
import java.util.ArrayList;

public class CommonUsedPrescriptionRecord implements Serializable {

    private ArrayList<PrescriptionBean> prescriptionList;
    private com.bowen.doctor.common.bean.Page page;

    public com.bowen.doctor.common.bean.Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<PrescriptionBean> getPrescriptionList() {
        return prescriptionList;
    }

    public void setPrescriptionList(ArrayList<PrescriptionBean> traOrderList) {
        this.prescriptionList = traOrderList;
    }
}
