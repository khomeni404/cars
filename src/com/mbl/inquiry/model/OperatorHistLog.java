package com.mbl.inquiry.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p/>
 * Original author: Ayatullah Khomeni
 * Date: 22/12/2015
 * Last modification by: ayat $
 * Last modification on 22/12/2015: 6:10 PM
 * Current revision: : 1.1.1.1
 * <p/>
 * Revision History:
 * ------------------
 */


public class OperatorHistLog  implements Serializable {
    private String oid;
    private Integer historyType;        // 1 = Charge/Discharge, 2 = Branch Assignment, 0 = Record Operator
    private Integer chargeState;        // 0 = Discharge, 1 = Charge
    private Date actionDate;            // Date of charge/discharge/Assignmet
    private String oldBrList;
    private String newBrList;
    private Date recordDate;            // Date of data record
    private String note;
    private String dataOperator;        // Who change the state
    private CIBOperator cibOperator;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Integer getChargeState() {
        return chargeState;
    }

    public void setChargeState(Integer chargeState) {
        this.chargeState = chargeState;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public Integer getHistoryType() {
        return historyType;
    }

    public void setHistoryType(Integer historyType) {
        this.historyType = historyType;
    }

    public String getOldBrList() {
        return oldBrList;
    }

    public void setOldBrList(String oldBrList) {
        this.oldBrList = oldBrList;
    }

    public String getNewBrList() {
        return newBrList;
    }

    public void setNewBrList(String newBrList) {
        this.newBrList = newBrList;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getDataOperator() {
        return dataOperator;
    }

    public void setDataOperator(String dataOperator) {
        this.dataOperator = dataOperator;
    }

    public CIBOperator getCibOperator() {
        return cibOperator;
    }

    public void setCibOperator(CIBOperator cibOperator) {
        this.cibOperator = cibOperator;
    }
}
