package com.mbl.ir.model;

import com.mbl.util.Constants;
import net.softengine.util.GValidator;
import org.springframework.validation.BindingResult;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Copyright (C) 2002-2003 MBL
 * <p>
 * Original author: Ayatullah Khomeni
 * Date: December 8, 2015(11:19:15 AM)
 * Last modification by: $Author: ayat $
 * Last modification on $Date: 2017/11/12 10:06:40 $
 * Current revision: $Revision: 1.6 $
 * <p>
 * Revision History:
 * ------------------
 */

@Entity
@Table(name = "crap_inquiry")
public class Inquiry implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "INQUIRY_NO", length = 30)
    private String inqNo;   //  15-213-1001 -> 15:year, 213:brCode, 1001:Sl No
    @Column(name = "CUST_ID", length = 13)
    private String custId;
    @Column(name = "CUST_NAME", length = 120)
    private String custName;
    @Column(name = "INQ_TP")
    private Integer inqType; // new/living
    @Column(name = "INQ_FOR")
    private Integer inqFor;

    @Column(name = "SANC_AMT")
    private Double sanctionAmt;
    @Column(name = "FINANCE_TYPE", length = 200)
    private String financingType;
    @Column(name = "CR_MODE")
    private Integer crMode;
    @Column(name = "POP")
    private Integer pop;
    @Column(name = "EMI")
    private Integer emi;
    @Column(name = "BR_CODE", length = 6)
    private String brCode;

    @Column(name = "REQUEST_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestDate;
    @Column(name = "YEAR", length = 4)
    private String year;
    @Column(name = "INQ_STATUS")
    private Integer status;       //Default :  InquiryStatus.REQUESTED.CODE
    @Column(name = "LAST_UPDATE_ON")
    @Temporal(TemporalType.DATE)
    private Date lastUpdateDate;
    @Column(name = "KEY_COST")
    private Double keyCost;
    @Column(name = "LINKING_COST")
    private Double linkingCost;
    @Column(name = "COST_STATUS")
    private Integer costStatus;      //Default :  CIBDictionary.RRCS_PENDING


    @Column(name = "CREATOR", length = 50)
    private String creator;
    @Column(name = "RECORD_DATE")
    @Temporal(TemporalType.DATE)
    private Date recordDate;
    @Column(name = "Note", length = 255)
    private String note;
    @Column(name = "REQ_CUST_IDS", length = 255)
    private String reqCustIds;

    @Column(name = "DISPATCHER", length = 50)
    private String dispatchedBy;
    @Column(name = "CHARGED_BY", length = 50)
    private String chargedBy;
    @Column(name = "REPORTER", length = 50)
    private String reportedBy;
    @Column(name = "RPT_DATE")
    private Date reportDate;
    @Column(name = "FIN_STATUS")
    private Integer finalStatus;

    @Transient
    private String instrumentNo;

    /*@Column(name = "")
    private Set<InquiryHistory> historySet = new HashSet<>(0);

    @Column(name = "")
    private Set<ReportDoc> docSet = new HashSet<>(0);*/

    public Double getTotalCost() {
        return (keyCost == null ? 0 : keyCost) + (linkingCost == null ? 0 : linkingCost);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void validate(BindingResult result) {
        GValidator.rejectEmptyString(result, this.custId, "custId", "Type Customer ID");
        GValidator.rejectEmptyString(result, this.custName, "custName", "Type Customer Name");
        GValidator.rejectEmptyString(result, this.financingType, "financingType", "Type Financing Type");

        if (this.sanctionAmt == null) {
            GValidator.rejectValue(result, "sanctionAmt", "Sanction Amt. Required !");
        }
        if (this.crMode == null) {
            GValidator.rejectValue(result, "crMode", "Select a Mode");
        } else if (this.crMode.equals(Constants.INQ_CR_MODE_INST)) {
            if (this.pop == null) {
                GValidator.rejectValue(result, "pop", "Periodicity of Payment Required !");
            }
            if (this.emi == null) {
                GValidator.rejectValue(result, "emi", "No. of installment Required !");
            }
        }
        if (this.inqType == null) {
            GValidator.rejectValue(result, "inqType", "Select a Type !");
        }
        if (this.inqFor == null) {
            GValidator.rejectValue(result, "inqFor", "Select an Option !");
        }
        if (!result.hasErrors()) {
            GValidator.validateSafeString(result, this);
        }
    }

    /**
     * Getting sorted history set on record date
     *
     * @return <code>Set<InquiryHistory></code>
     */
    /*public Set<InquiryHistory> getSortedHistorySet() {
        if (historySet == null) return null;
        List<InquiryHistory> list = Lists.newArrayList(historySet);//
        if (list.size() > 0) {
            Collections.sort(list, new Comparator<InquiryHistory>() {
                public int compare(InquiryHistory o1, InquiryHistory o2) {
                    return o2.getRecordDate().compareTo(o1.getRecordDate());
                }
            });
        }
        return ImmutableSet.copyOf(list);
    }*/
    public String getInstrumentNo() {
        return year + brCode + inqNo;
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

    public Integer getInqType() {
        return inqType;
    }

    public void setInqType(Integer inqType) {
        this.inqType = inqType;
    }

    public Integer getInqFor() {
        return inqFor;
    }

    public void setInqFor(Integer inqFor) {
        this.inqFor = inqFor;
    }

    public Double getSanctionAmt() {
        return sanctionAmt;
    }

    public void setSanctionAmt(Double sanctionAmt) {
        this.sanctionAmt = sanctionAmt;
    }

    public String getFinancingType() {
        return financingType;
    }

    public void setFinancingType(String financingType) {
        this.financingType = financingType;
    }

    public Integer getCrMode() {
        return crMode;
    }

    public void setCrMode(Integer crMode) {
        this.crMode = crMode;
    }

    public Integer getPop() {
        return pop;
    }

    public void setPop(Integer pop) {
        this.pop = pop;
    }

    public Integer getEmi() {
        return emi;
    }

    public void setEmi(Integer emi) {
        this.emi = emi;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Double getKeyCost() {
        return keyCost;
    }

    public void setKeyCost(Double keyCost) {
        this.keyCost = keyCost;
    }

    public Double getLinkingCost() {
        return linkingCost;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public String getChargedBy() {
        return chargedBy;
    }

    public void setChargedBy(String chargedBy) {
        this.chargedBy = chargedBy;
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

    public Inquiry toLightBean() {
        Inquiry inquiry = new Inquiry();
        inquiry.setInqNo(this.inqNo);
        inquiry.setYear(this.year);
        inquiry.setBrCode(this.brCode);
        inquiry.setLinkingCost(this.linkingCost);
        inquiry.setKeyCost(this.keyCost);
        inquiry.setCustId(this.custId);
        return inquiry;
    }
}
