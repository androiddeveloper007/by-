package com.bowen.doctor.common.bean.network;

import com.bowen.doctor.common.bean.Page;

import java.io.Serializable;
import java.util.ArrayList;

public class OnlineFolkPrescriptionRecord implements Serializable {

    private ArrayList<FolkPrescription> folkPrescriptionList;
    private com.bowen.doctor.common.bean.Page page;

    public com.bowen.doctor.common.bean.Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<FolkPrescription> getPrescriptionList() {
        return folkPrescriptionList;
    }

    public void setPrescriptionList(ArrayList<FolkPrescription> prescriptionList) {
        this.folkPrescriptionList = prescriptionList;
    }
}
