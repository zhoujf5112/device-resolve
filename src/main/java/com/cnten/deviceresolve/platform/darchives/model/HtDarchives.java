package com.cnten.deviceresolve.platform.darchives.model;

import javax.persistence.Table;

@Table(name="bd_ht_darchives")
public class HtDarchives {
    private String dCode;
    private String cNumber;

    public String getdCode() {
        return dCode;
    }

    public void setdCode(String dCode) {
        this.dCode = dCode;
    }

    public String getcNumber() {
        return cNumber;
    }

    public void setcNumber(String cNumber) {
        this.cNumber = cNumber;
    }
}
