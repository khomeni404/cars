package com.mbl.inquiry.action.bean;

import com.ibbl.util.enums.InquiryStatus;
import com.ibbl.security.ui.bean.LoginBean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p/>
 * Original author: Ayatullah Khomeni
 * Date: 13/12/2015
 * Last modification by: ayat $
 * Last modification on 13/12/2015: 4:50 PM
 * Current revision: : 1.1.1.1
 * <p/>
 * Revision History:
 * ------------------
 */
public class InquirySearchBean {
    private String oid = "%";
    private String inqNo = "%";   // 15-213-0001 -> 15:year, 213:brCode, 0001:Sl No
    private String custId = "%";
    private String brCode = "%";
    private Date fromDate;
    private Date toDate;
    private String year = "%";
    private String inqStatus = "%";      //Default :  CIBDictionary.CIB_INQUIRY_STATUS_ARR[0][0])
    private String costStatus = "%";      //Default :  CIBDictionary.CIB_COST_STATUS_ARR[0][0])
    private String dataOperator = "%";
    private String dispatchedBy = "%";
    private String reportedBy = "%";

    private int start = 0;
    private int limit = 0;

    private LoginBean loginBean;
    private List<String> enlistedBrList = new ArrayList<String>(0);

    public InquirySearchBean(){}

    public InquirySearchBean(String prop, String value) {
        Field field;
        Class c = this.getClass();
        try {
            field = c.getDeclaredField(prop);
            field.setAccessible(true);
            field.set(this, value);
            //System.out.println((String) field.get(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getInqNo() {
        return inqNo;
    }

    public void setInqNo(String inqNo) {
        this.inqNo = inqNo;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public List<String> getEnlistedBrList() {
        return enlistedBrList;
    }

    public void setEnlistedBrList(List<String> enlistedBrList) {
        this.enlistedBrList = enlistedBrList;
    }

    public int getStart() {
        return start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getBrCode() {
        return brCode;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public void setBrCode(String brCode) {
        this.brCode = brCode;
    }

    public String getYear() {
        return year;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getInqStatus() {
        return inqStatus;
    }

    public void setInqStatus(String inqStatus) {
        this.inqStatus = inqStatus;
    }
    public void setInqStatus(InquiryStatus inqStatus) {
        this.inqStatus = String.valueOf(inqStatus.CODE);
    }

    public String getCostStatus() {
        return costStatus;
    }

    public void setCostStatus(String costStatus) {
        this.costStatus = costStatus;
    }

    public String getDataOperator() {
        return dataOperator;
    }

    public void setDataOperator(String dataOperator) {
        this.dataOperator = dataOperator;
    }

    public String getDispatchedBy() {
        return dispatchedBy;
    }

    public void setDispatchedBy(String dispatchedBy) {
        this.dispatchedBy = dispatchedBy;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }


}
