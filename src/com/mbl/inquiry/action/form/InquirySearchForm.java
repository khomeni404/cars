package com.mbl.inquiry.action.form;

import com.ibbl.cib.bl.CIBBLDataList;
import com.ibbl.inquiry.action.CibInquiryAction;
import com.ibbl.util.DateUtil;
import com.ibbl.cib.ui.form.GenericForm;
import com.ibbl.inquiry.action.bean.InquirySearchBean;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

/**
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p>
 * Original author: Ayatullah Khomeni
 * Date: 13/12/2015
 * Last modification by: ayat $
 * Last modification on 13/12/2015: 4:50 PM
 * Current revision: : 1.1.1.1
 * <p>
 * Revision History:
 * ------------------
 */
public class InquirySearchForm extends GenericForm {

    private String oid;   // 15-213-0001 -> 15:year, 213:brCode, 0001:Sl No
    private String inqNo;   // 15-213-0001 -> 15:year, 213:brCode, 0001:Sl No
    private String custId;
    private String userId;
    private String brCode;
    private String month;
    private String year;
    private String inqStatus;      //Default :  CIBDictionary.RRS_REQUESTED
    private String costStatus;      //Default :  CIBDictionary.RRCS_PENDING
    private String dataOperator;
    private String dispatchedBy;
    private String reportedBy;

    public InquirySearchForm() {
    }

    public InquirySearchForm(String prop, String value) {
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

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        this.oid=null;
        this.inqNo=null;
        this.custId=null;
        this.userId=null;
        this.brCode=null;
        this.month=null;
        this.year=null;
        this.inqStatus=null;
        this.costStatus=null;
        this.dataOperator=null;
        this.dispatchedBy=null;
        this.reportedBy=null;
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        String action = super.getAction();
        if (!GenericValidator.isBlankOrNull(action)) {
            if (action.equals(CibInquiryAction.ACTION_METHOD_VIEW)) {
                if (GenericValidator.isBlankOrNull(oid)) {
                    errors.add(CIBBLDataList.ERROR_TITLE, new ActionMessage("errors.required", "Inquiry OID"));
                }
            }
        } else {
            if (GenericValidator.isBlankOrNull(custId)) {
                errors.add(CIBBLDataList.ERROR_TITLE, new ActionMessage("errors.required", "Action Parameter"));
            }
        }

        return errors;
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

    public String getBrCode() {
        return brCode;
    }

    public void setBrCode(String brCode) {
        this.brCode = brCode;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getInqStatus() {
        return inqStatus;
    }

    public void setInqStatus(String inqStatus) {
        this.inqStatus = inqStatus;
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

    public InquirySearchBean toSearchBean() {
        InquirySearchBean bean = new InquirySearchBean();
        bean.setInqNo(this.inqNo);
        bean.setBrCode(this.brCode);
        bean.setCustId(this.custId);
        bean.setInqStatus(this.inqStatus);
        bean.setCostStatus(this.costStatus);
        bean.setYear(this.year);
        try {
            if (GenericValidator.isBlankOrNull(year) || GenericValidator.isBlankOrNull(month)) {
                bean.setFromDate(null);
                bean.setToDate(null);
            } else {
                bean.setFromDate(DateUtil.getFirstDateByMMYY(this.month + this.year.substring(2, 4)));
                bean.setToDate(DateUtil.getLastDateByMMYY(this.month + this.year.substring(2, 4)));
            }
            //bean.setRecordDate(DateUtil.toDate(form.getRecordDate()));
            // bean.setLastUpdateDate(DateUtil.toDate(form.getRecordDate()));
            // bean.setRequestDate(DateUtil.toDate(form.getRequestDate()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    public InquirySearchBean toSearchBean2() {
        InquirySearchBean bean = new InquirySearchBean();
        bean.setInqNo(this.inqNo.equals("") ? "%" : this.inqNo);
        bean.setBrCode(this.brCode.equals("") ? "%" : this.brCode);
        bean.setCustId(this.custId.equals("") ? "%" : this.custId);
        bean.setInqStatus(this.inqStatus.equals("") ? "%" : this.inqStatus);
        bean.setCostStatus(this.costStatus.equals("") ? "%" : this.costStatus);
        bean.setYear(this.getYear().equals("") ? "%" : this.getYear());
        try {
            bean.setFromDate(DateUtil.getFirstDateByMMYY(this.month + this.year.substring(2, 4)));
            bean.setToDate(DateUtil.getLastDateByMMYY(this.month + this.year.substring(2, 4)));
            //bean.setRecordDate(DateUtil.toDate(form.getRecordDate()));
            // bean.setLastUpdateDate(DateUtil.toDate(form.getRecordDate()));
            // bean.setRequestDate(DateUtil.toDate(form.getRequestDate()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }
}
