package com.mbl.inquiry.action.bean;

import com.ibbl.inquiry.model.Inquiry;
import com.ibbl.util.CIBDictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p>
 * Original author: Ayatullah Khomeni<br/>
 * Date: 01/02/2016
 * Last modification by: ayat $
 * Last modification on 01/02/2016: 6:06 PM
 * Current revision: : 1.1.1.1
 * <p>
 * Revision History:
 * ------------------
 */

//@XStreamAlias("")
public class CIBRemoteTransReqBean {
    private String crHead = "10067500";
    private String desc = "CIB Report Inquiry Charge";
    private String trCode = "147";
    private String trType = "102";
    private String refAccNo = "";
    private String originIBCode = "101";

    private String remoteUserId = CIBDictionary.REMOTE_CHARGE_USER_ID;
    private String remotePassword = CIBDictionary.REMOTE_CHARGE_PASSWORD;
    private String remoteSecretKey = CIBDictionary.REMOTE_CHARGE_SECRET_KEY;

    private String actionKey;
    //private LoginBean loginBean;

    private List<Inquiry> inquiryList = new ArrayList<>(0);


    public List<Inquiry> getInquiryList() {
        return inquiryList;
    }

    public void setInquiryList(List<Inquiry> inquiryList) {
        this.inquiryList = inquiryList;
    }

    public String getCrHead() {
        return crHead;
    }

    public void setCrHead(String crHead) {
        this.crHead = crHead;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTrCode() {
        return trCode;
    }

    public void setTrCode(String trCode) {
        this.trCode = trCode;
    }

    public String getTrType() {
        return trType;
    }

    public void setTrType(String trType) {
        this.trType = trType;
    }

    public String getRefAccNo() {
        return refAccNo;
    }

    public void setRefAccNo(String refAccNo) {
        this.refAccNo = refAccNo;
    }

    public String getRemoteUserId() {
        return remoteUserId;
    }

    public void setRemoteUserId(String remoteUserId) {
        this.remoteUserId = remoteUserId;
    }

    public String getRemotePassword() {
        return remotePassword;
    }

    public void setRemotePassword(String remotePassword) {
        this.remotePassword = remotePassword;
    }

    public String getOriginIBCode() {
        return originIBCode;
    }

    public void setOriginIBCode(String originIBCode) {
        this.originIBCode = originIBCode;
    }

    public String getRemoteSecretKey() {
        return remoteSecretKey;
    }

    public void setRemoteSecretKey(String remoteSecretKey) {
        this.remoteSecretKey = remoteSecretKey;
    }

    public String getActionKey() {
        return actionKey;
    }

    public void setActionKey(String actionKey) {
        this.actionKey = actionKey;
    }


    public CIBRemoteTransReqBean toLightBean() {
        CIBRemoteTransReqBean bean = new CIBRemoteTransReqBean();
        bean.setCrHead(this.crHead);
        bean.setDesc(this.desc);
        bean.setRefAccNo(this.refAccNo);
        bean.setRemoteUserId(this.remoteUserId);
        bean.setRemotePassword(this.remotePassword);
        bean.setRemoteSecretKey(this.remoteSecretKey);
        bean.setTrCode(this.trCode);
        bean.setTrType(this.trType);
        return bean;
    }
}
