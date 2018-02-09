package com.mbl.ir.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * package com.ibbl.cib.dao.model;
 * Copyright (C) 2002-2003 Mercantile Bank Limited
 * <p>
 * Original author: Khomeni
 * Date: Aug 1, 2011(11:11:06 AM)
 * Last modification by: $Author: ayat $
 * Last modification on $Date: 2017/10/18 10:52:50 $
 * Current revision: $Revision: 1.7 $
 * <p>
 * Revision History:
 * ------------------
 */

@Entity
@Table(name = "CRIB_DATA_SUB_EXIM")
public class SubjectData implements Serializable {
    public static final String CRIB_DATA_SUB_EXIM = "CRIB_DATA_SUB_EXIM";

    @Id
    @Column(name = "OID", length = 60)
    private String oid;
    @Column(name = "RT", length = 1)
    private String recordType;
    
    @Column(name = "BRC", length = 6)
    private String brCode;
    @Column(name = "BB_BRC", length = 6)
    private String bbBrCode;
    @Column(name = "CUST_ID", length = 13)
    private String custId;
    @Column(name = "grp_cust_id", length = 13)
    private String grpCustId;
    @Column(name = "borr_cust_id", length = 13)
    private String borrCustId;
    @Column(name = "GS")
    private Integer groupSerial;
    @Column(name = "REL")
    private Integer relation;
    @Column(name = "CT")
    private Integer custType;
    @Column(name = "CUST_TITLE", length = 20)
    private String title;
    @Column(name = "CUST_NAME", length = 130)
    private String name;
    @Column(name = "FH_TITLE", length = 20)
    private String fatherTitle;
    @Column(name = "FH_NAME", length = 70)
    private String fatherName;
    @Column(name = "MOTHER_TITLE", length = 20)
    private String motherTitle;
    @Column(name = "MOTHER_NAME", length = 70)
    private String motherName;
    @Column(name = "SPOUSE_TITLE", length = 20)
    private String spouseTitle;
    @Column(name = "SPOUSE_NAME", length = 70)
    private String spouseName;
    @Column(name = "SECTOR_CODE", length = 6)
    private String sectorCode;
    @Column(name = "GENDER")
    private Integer sex;
    @Column(name = "DOB")
    @Temporal(TemporalType.DATE)
    private Date dob;
    /**
     * @see com.mbl.util.FinalData#DISTRICT_LIST_COMPLEX
     */
    @Column(name = "BPDC", length = 2)
    private String bpdCode;
    @Column(name = "BIRTH_PLACE", length = 20)
    private String birthPlace;
    /**
     * @see com.mbl.util.FinalData#COUNTRY_LIST_COMPLEX
     */
    @Column(name = "BPCC", length = 2)
    private String bpcCode;
    @Column(name = "BIRTH_COUNTRY", length = 50)
    private String birthCountry;
    @Column(name = "NATIONAL_ID", length = 17)
    private String nid;
    @Column(name = "NID_AV")
    private Integer nidAvailable;
    @Column(name = "TIN", length = 30)
    private String tin;
    /**
     * @see com.mbl.util.FinalData#LEGAL_FORM_MAP
     */
    @Column(name = "LEGAL_FORM")
    private Integer legalForm;



    @Column(name = "PA_ADDR", length = 120)
    private String permanentAddress;
    @Column(name = "PA_PC", length = 4)
    private String permanentPC;
    @Column(name = "PADC", length = 2)
    private String pedCode;
    @Column(name = "PA_DIST", length = 20)
    private String permanentDist;
    @Column(name = "PACC", length = 2)
    private String pecCode;
    @Column(name = "PA_COUNTRY", length = 50)
    private String permanentCountry;


    @Column(name = "PR_ADDR", length = 120)
    private String presentAddress;
    @Column(name = "PR_PC", length = 4)
    private String presentPC;
    @Column(name = "PRDC", length = 2)
    private String prdCode;
    @Column(name = "PR_DIST", length = 20)
    private String presentDist;
    @Column(name = "PRCC", length = 2)
    private String prcCode;
    @Column(name = "PR_COUNTRY", length = 50)
    private String presentCountry;

    @Column(name = "BZ_ADDR", length = 120)
    private String businessAddress;
    @Column(name = "BZ_PC", length = 4)
    private String businessPC;
    @Column(name = "BZDC", length = 2)
    private String bzdCode;
    @Column(name = "BZ_DIST", length = 20)
    private String businessDist;
    @Column(name = "BZCC", length = 2)
    private String bzcCode;
    @Column(name = "BZ_COUNTRY", length = 50)
    private String businessCountry;

    @Column(name = "ID_TYPE")
    private Integer idType;
    /**
     * @see com.mbl.util.CIBDictionary#ID_TYPE
     */
    @Column(name = "ID_REF_NO", length = 20)
    private String idRefNo;
    @Column(name = "ID_ISSUE_ON")
    @Temporal(TemporalType.DATE)
    private Date idIssueDate;
    @Column(name = "ID_ISS_CC", length = 2)
    private String idIssueCountry;
    @Column(name = "ID_ISS_COUNTRY", length = 50)
    private String idIssueCC;

    @Column(name = "PH_NO", length = 40)
    private String phoneNo;
    @Column(name = "cell_NO", length = 15)
    private String cellNo;
    @Column(name = "REG_NO", length = 15)
    private String regNo;
    @Column(name = "REG_DATE")
    @Temporal(TemporalType.DATE)
    private Date regDate;

    @Column(name = "CRG_SCORE")
    private Integer crgScore;
    @Column(name = "CR_RATING")
    private Integer creditRating;


    @Override
    public String toString() {
        return this.custId;
    }

    public String getSexText() {
        return sex == null ? "---" : sex == 1 ? "Male" : sex == 2 ? "Female" : "---";
    }


    //==============================================================


    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getBrCode() {
        return brCode;
    }

    public void setBrCode(String brCode) {
        this.brCode = brCode;
    }

    public String getBbBrCode() {
        return bbBrCode;
    }

    public void setBbBrCode(String bbBrCode) {
        this.bbBrCode = bbBrCode;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getGrpCustId() {
        return grpCustId;
    }

    public void setGrpCustId(String grpCustId) {
        this.grpCustId = grpCustId;
    }

    public String getBorrCustId() {
        return borrCustId;
    }

    public void setBorrCustId(String borrCustId) {
        this.borrCustId = borrCustId;
    }

    public Integer getRelation() {
        return relation;
    }

    public void setRelation(Integer relation) {
        this.relation = relation;
    }

    public Integer getGroupSerial() {
        return groupSerial;
    }

    public void setGroupSerial(Integer groupSerial) {
        this.groupSerial = groupSerial;
    }

    public Integer getCustType() {
        return custType;
    }

    public void setCustType(Integer custType) {
        this.custType = custType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherTitle() {
        return fatherTitle;
    }

    public void setFatherTitle(String fatherTitle) {
        this.fatherTitle = fatherTitle;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherTitle() {
        return motherTitle;
    }

    public void setMotherTitle(String motherTitle) {
        this.motherTitle = motherTitle;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getSpouseTitle() {
        return spouseTitle;
    }

    public void setSpouseTitle(String spouseTitle) {
        this.spouseTitle = spouseTitle;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public String getSectorCode() {
        return sectorCode;
    }

    public void setSectorCode(String sectorCode) {
        this.sectorCode = sectorCode;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getBpdCode() {
        return bpdCode;
    }

    public void setBpdCode(String bpdCode) {
        this.bpdCode = bpdCode;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getBpcCode() {
        return bpcCode;
    }

    public void setBpcCode(String bpcCode) {
        this.bpcCode = bpcCode;
    }

    public String getBirthCountry() {
        return birthCountry;
    }

    public void setBirthCountry(String birthCountry) {
        this.birthCountry = birthCountry;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public Integer getNidAvailable() {
        return nidAvailable;
    }

    public void setNidAvailable(Integer nidAvailable) {
        this.nidAvailable = nidAvailable;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public Integer getLegalForm() {
        return legalForm;
    }

    public void setLegalForm(Integer legalForm) {
        this.legalForm = legalForm;
    }

    public String getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(String presentAddress) {
        this.presentAddress = presentAddress;
    }

    public String getPresentPC() {
        return presentPC;
    }

    public void setPresentPC(String presentPC) {
        this.presentPC = presentPC;
    }

    public String getPrdCode() {
        return prdCode;
    }

    public void setPrdCode(String prdCode) {
        this.prdCode = prdCode;
    }

    public String getPresentDist() {
        return presentDist;
    }

    public void setPresentDist(String presentDist) {
        this.presentDist = presentDist;
    }

    public String getPrcCode() {
        return prcCode;
    }

    public void setPrcCode(String prcCode) {
        this.prcCode = prcCode;
    }

    public String getPresentCountry() {
        return presentCountry;
    }

    public void setPresentCountry(String presentCountry) {
        this.presentCountry = presentCountry;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getBusinessPC() {
        return businessPC;
    }

    public void setBusinessPC(String businessPC) {
        this.businessPC = businessPC;
    }

    public String getBzdCode() {
        return bzdCode;
    }

    public void setBzdCode(String bzdCode) {
        this.bzdCode = bzdCode;
    }

    public String getBusinessDist() {
        return businessDist;
    }

    public void setBusinessDist(String businessDist) {
        this.businessDist = businessDist;
    }

    public String getBzcCode() {
        return bzcCode;
    }

    public void setBzcCode(String bzcCode) {
        this.bzcCode = bzcCode;
    }

    public String getBusinessCountry() {
        return businessCountry;
    }

    public void setBusinessCountry(String businessCountry) {
        this.businessCountry = businessCountry;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getPermanentPC() {
        return permanentPC;
    }

    public void setPermanentPC(String permanentPC) {
        this.permanentPC = permanentPC;
    }

    public String getPedCode() {
        return pedCode;
    }

    public void setPedCode(String pedCode) {
        this.pedCode = pedCode;
    }

    public String getPermanentDist() {
        return permanentDist;
    }

    public void setPermanentDist(String permanentDist) {
        this.permanentDist = permanentDist;
    }

    public String getPecCode() {
        return pecCode;
    }

    public void setPecCode(String pecCode) {
        this.pecCode = pecCode;
    }

    public String getPermanentCountry() {
        return permanentCountry;
    }

    public void setPermanentCountry(String permanentCountry) {
        this.permanentCountry = permanentCountry;
    }

    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public String getIdRefNo() {
        return idRefNo;
    }

    public void setIdRefNo(String idRefNo) {
        this.idRefNo = idRefNo;
    }

    public Date getIdIssueDate() {
        return idIssueDate;
    }

    public void setIdIssueDate(Date idIssueDate) {
        this.idIssueDate = idIssueDate;
    }

    public String getIdIssueCountry() {
        return idIssueCountry;
    }

    public void setIdIssueCountry(String idIssueCountry) {
        this.idIssueCountry = idIssueCountry;
    }

    public String getIdIssueCC() {
        return idIssueCC;
    }

    public void setIdIssueCC(String idIssueCC) {
        this.idIssueCC = idIssueCC;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCellNo() {
        return cellNo;
    }

    public void setCellNo(String cellNo) {
        this.cellNo = cellNo;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Integer getCrgScore() {
        return crgScore;
    }

    public void setCrgScore(Integer crgScore) {
        this.crgScore = crgScore;
    }

    public Integer getCreditRating() {
        return creditRating;
    }

    public void setCreditRating(Integer creditRating) {
        this.creditRating = creditRating;
    }


}