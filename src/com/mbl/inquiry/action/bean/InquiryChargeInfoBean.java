package com.mbl.inquiry.action.bean;

import java.util.Date;

/**
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p>
 * Original author: Ayatullah Khomeni<br/>
 * Date: 31/01/2016
 * Last modification by: ayat $
 * Last modification on 31/01/2016: 5:13 PM
 * Current revision: : 1.1.1.1
 * <p>
 * Revision History:
 * ------------------
 */
public class InquiryChargeInfoBean {

    private String accNo= null;
    private Date when = null;
    private double totalCharge = 0.0;
    private double totalVat = 0.0;
    private String instrNo = "F4";      // "F4"
    private String originIBCode;

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public Date getWhen() {
        return when;
    }

    public void setWhen(Date when) {
        this.when = when;
    }

    public double getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(double totalCharge) {
        this.totalCharge = totalCharge;
    }

    public double getTotalVat() {
        return totalVat;
    }

    public void setTotalVat(double totalVat) {
        this.totalVat = totalVat;
    }

    public String getInstrNo() {
        return instrNo;
    }

    public void setInstrNo(String instrNo) {
        this.instrNo = instrNo;
    }

    public String getOriginIBCode() {
        return originIBCode;
    }

    public void setOriginIBCode(String originIBCode) {
        this.originIBCode = originIBCode;
    }
}
