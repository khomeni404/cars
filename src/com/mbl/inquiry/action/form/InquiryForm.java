package com.mbl.inquiry.action.form;

import com.ibbl.cib.ui.form.GenericForm;
import com.ibbl.util.CIBDictionary;
import com.ibbl.common.DateUtil;
import com.ibbl.inquiry.model.Inquiry;
import com.ibbl.util.WebDictionary;
import com.ibbl.util.enums.InquiryStatus;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * package ibbl.law.common.web.action;
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p>
 * Original author: Ayatullah Khomeni
 * Date: October 25, 2015(11:19:15 AM)
 * Last modification by: $Author: ayat $
 * Last modification on $Date: 2017/11/12 10:06:40 $
 * Current revision: $Revision: 1.8 $
 * <p>
 * Revision History:
 * ------------------
 */

public class InquiryForm extends GenericForm {
    private String oid;
    private String inqNo;   // 15-213-0001 -> 15:year, 213:brCode, 0001:Sl No
    private String requestDate;
    private String inqType; // New, etc
    private Double proposedAmt;
    private String financingType;
    private String repayMode;
    private Integer installIntervalValue;
    private Integer installIntervalUnit;
    private Integer noOfInstallment;
    private String custId;
    private String brCode;
    private String year;
    private Integer inqStatus;
    private String lastUpdateDate;
    private Double keyCost;
    private Double linkingCost;
    private Integer costStatus;      //Default :  CIBDictionary.RRCS_PENDING
    private String proposedAc;
    private String chargedFrom;
    private String dataOperator;
    private String recordDate;
    private String note;
    private String reqCustIds;

    private String dispatchedBy;
    private String reportedBy;
    //Transient
    private String chargeableAccount;

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (GenericValidator.isBlankOrNull(custId)) {
            errors.add("custId", new ActionMessage("errors.required", "Cust Id"));
        }
        if (GenericValidator.isBlankOrNull(reqCustIds)) {
            errors.add("reqCustIds", new ActionMessage("errors.details", "You must select at least one customer."));
        }/* else {
            ICustomer util = new ICustomerImpl();
            String[] custIdArr = reqCustIds.split(",");
            for (String custID : custIdArr) {
                if (!util.verifyCustID(custID.trim())) {
                    errors.add("reqCustIds", new ActionMessage("errors.details", "Report On : '"+custID+"' is invalid."));
                }
            }
        }*/
        if (GenericValidator.isBlankOrNull(inqType)) {
            errors.add("inqType", new ActionMessage("errors.required", "Inq Type"));
        }
        if (GenericValidator.isBlankOrNull(financingType)) {
            errors.add("financingType", new ActionMessage("errors.required", "Financing Type"));
        }
        if (proposedAmt == null || proposedAmt <= 0.0) {
            errors.add("proposedAmt", new ActionMessage("errors.required", "Proposed Amount"));
        }
        if (GenericValidator.isBlankOrNull(proposedAc)) {
            errors.add("proposedAc", new ActionMessage("errors.required", "Service Charge A/C"));
        } else if (proposedAc.length() != 17 || !NumberUtils.isDigits(proposedAc)) {
            errors.add("proposedAc", new ActionMessage("errors.invalid", "Service Charge A/C"));
        }
        request.getSession().setAttribute(WebDictionary.BR_CODE, this.brCode);
        return errors;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
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

    public String getRequestDate() {
        return requestDate;
    }

    public String getReqCustIds() {
        return reqCustIds;
    }

    public void setReqCustIds(String reqCustIds) {
        this.reqCustIds = reqCustIds;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getInqType() {
        return inqType;
    }

    public void setInqType(String inqType) {
        this.inqType = inqType;
    }

    public String getFinancingType() {
        return financingType;
    }

    public void setFinancingType(String financingType) {
        this.financingType = financingType;
    }

    public String getRepayMode() {
        return repayMode;
    }

    public void setRepayMode(String repayMode) {
        this.repayMode = repayMode;
    }

    public int getInqStatus() {
        return inqStatus;
    }

    public void setInqStatus(int inqStatus) {
        this.inqStatus = inqStatus;
    }

    public void setCostStatus(int costStatus) {
        this.costStatus = costStatus;
    }

    public String getProposedAc() {
        return proposedAc;
    }

    public void setProposedAc(String proposedAc) {
        this.proposedAc = proposedAc;
    }

    public String getChargedFrom() {
        return chargedFrom;
    }

    public void setChargedFrom(String chargedFrom) {
        this.chargedFrom = chargedFrom;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public double getKeyCost() {
        return keyCost;
    }

    public void setKeyCost(double keyCost) {
        this.keyCost = keyCost;
    }

    public double getLinkingCost() {
        return linkingCost;
    }

    public void setLinkingCost(double linkingCost) {
        this.linkingCost = linkingCost;
    }

    public String getDataOperator() {
        return dataOperator;
    }

    public void setDataOperator(String dataOperator) {
        this.dataOperator = dataOperator;
    }

    public String getInqNo() {
        return inqNo;
    }

    public void setInqNo(String inqNo) {
        this.inqNo = inqNo;
    }

    public int getCostStatus() {
        return costStatus;
    }

    public String getChargeableAccount() {
        return chargeableAccount;
    }

    public void setChargeableAccount(String chargeableAccount) {
        this.chargeableAccount = chargeableAccount;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
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

    public Double getProposedAmt() {
        return proposedAmt;
    }

    public void setProposedAmt(Double proposedAmt) {
        this.proposedAmt = proposedAmt;
    }

    public Integer getInstallIntervalValue() {
        return installIntervalValue;
    }

    public void setInstallIntervalValue(Integer installIntervalValue) {
        this.installIntervalValue = installIntervalValue;
    }

    public Integer getInstallIntervalUnit() {
        return installIntervalUnit;
    }

    public void setInstallIntervalUnit(Integer installIntervalUnit) {
        this.installIntervalUnit = installIntervalUnit;
    }

    public Integer getNoOfInstallment() {
        return noOfInstallment;
    }

    public void setNoOfInstallment(Integer noOfInstallment) {
        this.noOfInstallment = noOfInstallment;
    }

    public void setInqStatus(Integer inqStatus) {
        this.inqStatus = inqStatus;
    }

    public void setKeyCost(Double keyCost) {
        this.keyCost = keyCost;
    }

    public void setLinkingCost(Double linkingCost) {
        this.linkingCost = linkingCost;
    }

    public void setCostStatus(Integer costStatus) {
        this.costStatus = costStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public Inquiry toModelBean() {
        Inquiry bean = new Inquiry();
        bean.setFinalStatus(CIBDictionary.INQ_FINAL_STATUS_NOT_REPORTED);
        bean.setOid(this.oid);
        bean.setInqNo(this.inqNo);
        bean.setBrCode(this.brCode);
        bean.setCustId(this.custId);
        bean.setInqType(GenericValidator.isBlankOrNull(this.inqType) ? 0 : Integer.valueOf(this.inqType));
        bean.setProposedAmt(proposedAmt);
        bean.setFinancingType(GenericValidator.isBlankOrNull(this.financingType) ? 0 : Integer.valueOf(this.financingType));
        bean.setRepayMode(GenericValidator.isBlankOrNull(this.repayMode) ? 0 : Integer.valueOf(this.repayMode));
        bean.setInstallIntervalValue(this.installIntervalValue == null ? 0 : this.installIntervalValue);
        bean.setInstallIntervalUnit(this.installIntervalUnit == null ? 0 : this.installIntervalUnit);
        bean.setNoOfInstallment(this.noOfInstallment == null ? 0 : this.noOfInstallment);
        try {
            bean.setRecordDate(DateUtil.toDate(this.recordDate));
            bean.setLastUpdateDate(DateUtil.toDate(this.recordDate));
            bean.setRequestDate(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        bean.setKeyCost(this.keyCost);
        bean.setLinkingCost(this.linkingCost);
        bean.setChargeableAccount(this.chargeableAccount);
        bean.setProposedAc(this.proposedAc);
        bean.setNote(this.note);
        bean.setReqCustIds(this.reqCustIds);
        bean.setYear(this.recordDate.split("/")[2]);

        bean.setStatus(InquiryStatus.CREATED.CODE);  // Default : 11
        //bean.setInqStatus(InquiryStatus.REQUESTED.CODE);  // Default : 11
        //bean.setInqStatus(Integer.parseInt(CIBDictionary.CIB_INQUIRY_STATUS_ARR[0][0]));  // Default : 11

        bean.setCostStatus(CIBDictionary.CIB_COST_STATUS_PENDING);   // Default : 11

        return bean;
    }
}
