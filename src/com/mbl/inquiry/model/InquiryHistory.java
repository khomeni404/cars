package com.mbl.inquiry.model;

import com.ibbl.util.enums.InquiryStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p/>
 * Original author: Ayatullah Khomeni
 * Date: 13/12/2015
 * Last modification by: ayat $
 * Last modification on 13/12/2015: 2:17 PM
 * Current revision: : 1.1.1.1
 * <p/>
 * Revision History:
 * ------------------
 */
public class InquiryHistory  implements Serializable {
    private String oid;

    private String historyNote;

    private Date recordDate;

    private Integer active;

    private String operator;

    private Inquiry inquiry;

    private int inqStatus;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Inquiry getInquiry() {
        return inquiry;
    }

    public void setInquiry(Inquiry inquiry) {
        this.inquiry = inquiry;
    }

    public String getHistoryNote() {
        return historyNote;
    }

    public void setHistoryNote(String historyNote) {
        this.historyNote = historyNote;
    }

    public void setHistoryNote(InquiryStatus en) {
        this.historyNote = en.HISTORY;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public int getInqStatus() {
        return inqStatus;
    }

    public void setInqStatus(int inqStatus) {
        this.inqStatus = inqStatus;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
