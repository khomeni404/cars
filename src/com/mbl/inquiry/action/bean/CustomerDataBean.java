package com.mbl.inquiry.action.bean;

import com.ibbl.common.model.deposit.AccountPO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p>
 * Original author: Ayatullah Khomeni<br/>
 * Date: 09/02/2016
 * Last modification by: ayat $
 * Last modification on 09/02/2016: 4:08 PM
 * Current revision: : 1.1.1.1
 * <p>
 * Revision History:
 * ------------------
 */
public class CustomerDataBean implements Serializable {
    private String customerId;
    private String customerName;
    private int custType;
    private String fatherName;
    private String motherName;
    private String peAddress;
    private String peDistrict;
    private String peDC;
    private String peCountry;
    private String peCC;
    private String prAddress;
    private String prDistrict;
    private String prDC;
    private String prCountry;
    private String prCC;
    private String nid;
    private String passNo;
    private String dob;
    private String birthCountry;
    private String birthPlace;
    private String birthPlaceDC;
    private String compRegNo;
    private String compRegDate;
    private List<AccountPO> accBeanList = new ArrayList<>();

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public int getCustType() {
        return custType;
    }

    public void setCustType(int custType) {
        this.custType = custType;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getPeAddress() {
        return peAddress;
    }

    public void setPeAddress(String peAddress) {
        this.peAddress = peAddress;
    }

    public String getPeDistrict() {
        return peDistrict;
    }

    public void setPeDistrict(String peDistrict) {
        this.peDistrict = peDistrict;
    }

    public String getPeCountry() {
        return peCountry;
    }

    public String getBirthPlaceDC() {
        return birthPlaceDC;
    }

    public void setBirthPlaceDC(String birthPlaceDC) {
        this.birthPlaceDC = birthPlaceDC;
    }

    public void setPeCountry(String peCountry) {
        this.peCountry = peCountry;
    }

    public String getPrAddress() {
        return prAddress;
    }

    public String getPeDC() {
        return peDC;
    }

    public void setPeDC(String peDC) {
        this.peDC = peDC;
    }

    public String getPeCC() {
        return peCC;
    }

    public void setPeCC(String peCC) {
        this.peCC = peCC;
    }

    public String getPrDC() {
        return prDC;
    }

    public void setPrDC(String prDC) {
        this.prDC = prDC;
    }

    public String getPrCC() {
        return prCC;
    }

    public void setPrCC(String prCC) {
        this.prCC = prCC;
    }

    public void setPrAddress(String prAddress) {
        this.prAddress = prAddress;
    }

    public String getPrDistrict() {
        return prDistrict;
    }

    public void setPrDistrict(String prDistrict) {
        this.prDistrict = prDistrict;
    }

    public String getPrCountry() {
        return prCountry;
    }

    public void setPrCountry(String prCountry) {
        this.prCountry = prCountry;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getPassNo() {
        return passNo;
    }

    public void setPassNo(String passNo) {
        this.passNo = passNo;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getBirthCountry() {
        return birthCountry;
    }

    public void setBirthCountry(String birthCountry) {
        this.birthCountry = birthCountry;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getCompRegNo() {
        return compRegNo;
    }

    public void setCompRegNo(String compRegNo) {
        this.compRegNo = compRegNo;
    }

    public String getCompRegDate() {
        return compRegDate;
    }

    public void setCompRegDate(String compRegDate) {
        this.compRegDate = compRegDate;
    }

    public List<AccountPO> getAccBeanList() {
        return accBeanList;
    }

    public void setAccBeanList(List<AccountPO> accBeanList) {
        this.accBeanList = accBeanList;
    }
}
