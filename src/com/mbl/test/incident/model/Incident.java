package com.mbl.test.incident.model;

import com.ibbl.security.model.User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * Copyright &copy; 2002-2003 Islami Bank Bangladesh Limited
 * <p>
 * Original author: Khomeni
 * Date: 16/05/2017 11:08 AM
 * Last modification by: Khomeni: Khomeni
 * Last modification on 13/08/2017: 13/08/2017 11:08 AM
 * Current revision: 1.0.1: 1.1 $
 * <p>
 * Revision History:
 * ------------------
 */

//@Entity
//@Table(name = "TAIMS_INC_INCIDENT")
public class Incident implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Reference no is required")
    @Column(name = "ref_No")
    private Integer refNo;

    @ManyToOne(fetch = FetchType.EAGER)
    private User initiator;

    private Integer type;

    // @Pattern(regexp = "^[0-9]{2}$", message = "Only amount Expected with max two digits")
    // @Digits(integer = 10, fraction = 2, message = "Only amount Expected with max two digits")
    @Column(name = "loss_amt")
    private Double lossAmt;

    @NotNull(message = "Incident Date is Required")
    @Temporal(TemporalType.DATE)
    @Column(name = "inc_date")
    private Date incidentDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "inc_time")
    private Date incidentTime;

    @NotNull(message = "Reporting Date is Required")
    @Temporal(TemporalType.DATE)
    @Column(name = "rpt_date")
    private Date reportingDate;

    // @Column(columnDefinition = "text") // TODO
    @NotNull(message = "Details is Required")
    @Size(min = 50, max = 255, message = "Must be 50-255 Character")
    @Column(length = 255)
    private String details;

    @Column(name = "hca")
    private Boolean hasControllingApparatus = true;

    @Column(name = "failure_reasons", length = 255)
    // @Column(columnDefinition = "text") // TODO
    // @Size(min = 0, max = 255)
    private String failureReasons;

    @Column(name = "preventative_controls")
    // @Column(columnDefinition = "text") // TODO
    private String preventativeControls;

    @Column(name = "start_from")
    private String startedFrom;

    @Column(name = "stopped_at")
    private String stoppedAt;


/*
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "incident")
    @Fetch(FetchMode.SELECT)
    private List<IncidentReview> reviewList = new ArrayList<>();
*/

    public void validate(BindingResult result) {
        if (refNo != null && refNo <= 0) {
            result.rejectValue("refNo", "negativeValue", new Object[]{"'refNo'"}, "Ref. No. can't be negative");
        }

        if (type != null && type <= 0) {
            result.rejectValue("type", "", "Please select a Type");
        }

        if (lossAmt == null || lossAmt <= 0) {
            result.rejectValue("lossAmt", "", "Loss Amount can't be Zero");
        }

        if (hasControllingApparatus != null && hasControllingApparatus) {
            ValidationUtils.rejectIfEmptyOrWhitespace(result, "failureReasons", "failureReasons.required", "Failure Reasons Required while \"Preventative Control Was Existed\" is checked.");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRefNo() {
        return refNo;
    }

    public void setRefNo(Integer refNo) {
        this.refNo = refNo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getLossAmt() {
        return lossAmt;
    }

    public void setLossAmt(Double lossAmt) {
        this.lossAmt = lossAmt;
    }

    public Date getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(Date incidentDate) {
        this.incidentDate = incidentDate;
    }

    public User getInitiator() {
        return initiator;
    }

    public void setInitiator(User initiator) {
        this.initiator = initiator;
    }

    public Date getReportingDate() {
        return reportingDate;
    }

    public void setReportingDate(Date reportingDate) {
        this.reportingDate = reportingDate;
    }

    public Date getIncidentTime() {
        return incidentTime;
    }

    public void setIncidentTime(Date incidentTime) {
        this.incidentTime = incidentTime;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Boolean getHasControllingApparatus() {
        return hasControllingApparatus;
    }

    public void setHasControllingApparatus(Boolean hasControllingApparatus) {
        this.hasControllingApparatus = hasControllingApparatus;
    }

    public String getFailureReasons() {
        return failureReasons;
    }

    public void setFailureReasons(String failureReasons) {
        this.failureReasons = failureReasons;
    }

    public String getPreventativeControls() {
        return preventativeControls;
    }

    public void setPreventativeControls(String preventativeControls) {
        this.preventativeControls = preventativeControls;
    }

    public String getStartedFrom() {
        return startedFrom;
    }

    public void setStartedFrom(String startedFrom) {
        this.startedFrom = startedFrom;
    }

    public String getStoppedAt() {
        return stoppedAt;
    }

    public void setStoppedAt(String stoppedAt) {
        this.stoppedAt = stoppedAt;
    }

}
