package com.bowen.doctor.common.bean.network;

import java.io.Serializable;

public class AddPrescriptionDetail implements Serializable {
    String name;
    String gram;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGram() {
        return gram;
    }

    public void setGram(String gram) {
        this.gram = gram;
    }

    public AddPrescriptionDetail(String name, String gram) {
        this.name = name;
        this.gram = gram;
    }
}
