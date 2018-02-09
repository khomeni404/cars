package com.mbl.inquiry.model;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.ibbl.util.enums.InquiryStatus;

import java.io.Serializable;
import java.util.*;

/**
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p/>
 * Original author: Ayatullah Khomeni
 * Date: December 8, 2015(11:19:15 AM)
 * Last modification by: $Author: ayat $
 * Last modification on $Date: 2017/11/12 10:06:40 $
 * Current revision: $Revision: 1.6 $
 * <p/>
 * Revision History:
 * ------------------
 */
public class Inquiry  implements Serializable {
    private String oid;
    private String inqNo;   //  15-213-1001 -> 15:year, 213:brCode, 1001:Sl No
    private String custId;
    private String custName;
    private Integer inqType;
    private Double proposedAmt;
    private Integer financingType;
    private Integer repayMode;
    private Integer installIntervalValue;
    private Integer installIntervalUnit;
    private Integer noOfInstallment;
    private String brCode;

    private Date requestDate;
    private String year;
    private Integer status;       //Default :  InquiryStatus.REQUESTED.CODE
    private Date lastUpdateDate;
    private Double keyCost;
    private Double linkingCost;
    private Integer costStatus;      //Default :  CIBDictionary.RRCS_PENDING
    private String proposedAc;
    private String chargedFrom;
    private String drTrID;
    private String crTrID;  // Transient
    private String dataOperator;
    private Date recordDate;
    private String note;
    private String reqCustIds;



    private String dispatchedBy;
    private String chargedBy;
    private String reportedBy;
    private Date reportDate;
    private String complainedBy;

    private Integer finalStatus;

    //Transient
    private String chargeableAccount;
    //Transient
    private String instrumentNo;

    private Set<InquiryHistory> historySet = new HashSet<InquiryHistory>(0);

    private Set<ReportDoc> docSet = new HashSet<ReportDoc>(0);

    public Double getTotalCost() {
        return keyCost+linkingCost;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    /**
     * Getting sorted history set on record date
     * @return <code>Set<InquiryHistory></code>
     */
    public Set<InquiryHistory> getSortedHistorySet() {
        if(historySet == null) return null;
        List<InquiryHistory> list = Lists.newArrayList(historySet);//
        if (list.size() > 0) {
            Collections.sort(list, new Comparator<InquiryHistory>() {
                public int compare(InquiryHistory o1, InquiryHistory o2) {
                    return o2.getRecordDate().compareTo(o1.getRecordDate());
                }
            });
        }
        return ImmutableSet.copyOf(list);
    }

    public String getInstrumentNo() {
        return year+brCode+inqNo;
    }


    public void setInstrumentNo(String instrumentNo) {
        this.instrumentNo = instrumentNo;
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

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public Integer getFinalStatus() {
        return finalStatus;
    }

    public void setFinalStatus(Integer finalStatus) {
        this.finalStatus = finalStatus;
    }

    public Double getProposedAmt() {
        return proposedAmt;
    }

    public void setProposedAmt(Double proposedAmt) {
        this.proposedAmt = proposedAmt;
    }

    public String getChargeableAccount() {
        return chargeableAccount;
    }

    public void setChargeableAccount(String chargeableAccount) {
        this.chargeableAccount = chargeableAccount;
    }

    public String getDrTrID() {
        return drTrID;
    }

    public String getChargedBy() {
        return chargedBy;
    }

    public void setChargedBy(String chargedBy) {
        this.chargedBy = chargedBy;
    }

    public void setDrTrID(String drTrID) {
        this.drTrID = drTrID;
    }

    public Integer getInqType() {
        return inqType;
    }

    public void setInqType(Integer inqType) {
        this.inqType = inqType;
    }

    public Integer getFinancingType() {
        return financingType;
    }

    public void setFinancingType(Integer financingType) {
        this.financingType = financingType;
    }

    public Integer getRepayMode() {
        return repayMode;
    }

    public void setRepayMode(Integer repayMode) {
        this.repayMode = repayMode;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setKeyCost(Double keyCost) {
        this.keyCost = keyCost;
    }

    public void setLinkingCost(Double linkingCost) {
        this.linkingCost = linkingCost;
    }

    public Integer getCostStatus() {
        return costStatus;
    }

    public void setCostStatus(Integer costStatus) {
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

    public String getCrTrID() {
        return crTrID;
    }

    public void setCrTrID(String crTrID) {
        this.crTrID = crTrID;
    }

    public String getComplainedBy() {
        return complainedBy;
    }

    public void setComplainedBy(String complainedBy) {
        this.complainedBy = complainedBy;
    }

    public String getReqCustIds() {
        return reqCustIds;
    }

    public void setReqCustIds(String reqCustIds) {
        this.reqCustIds = reqCustIds;
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

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getBrCode() {
        return brCode;
    }

    public void setBrCode(String brCode) {
        this.brCode = brCode;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Double getKeyCost() {
        return keyCost;
    }

    public Double getLinkingCost() {
        return linkingCost;
    }

    public Set<ReportDoc> getDocSet() {
        return docSet;
    }

    public void setDocSet(Set<ReportDoc> docSet) {
        this.docSet = docSet;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public Set<InquiryHistory> getHistorySet() {
        return historySet;
    }

    public void setHistorySet(Set<InquiryHistory> historySet) {
        this.historySet = historySet;
    }

    public String getDataOperator() {
        return dataOperator;
    }

    public void setDataOperator(String dataOperator) {
        this.dataOperator = dataOperator;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Inquiry toLightBean() {
        Inquiry inquiry = new Inquiry();
        inquiry.setInqNo(this.inqNo);
        inquiry.setYear(this.year);
        inquiry.setBrCode(this.brCode);
        inquiry.setLinkingCost(this.linkingCost);
        inquiry.setKeyCost(this.keyCost);
        inquiry.setCustId(this.custId);
        inquiry.setChargeableAccount(this.getChargeableAccount());
        return inquiry;
    }
}
