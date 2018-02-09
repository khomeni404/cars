package com.mbl.inquiry.action.bean;

import java.util.List;

/**
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p>
 * Original author: Ayatullah Khomeni<br/>
 * Date: 01/02/2016
 * Last modification by: ayat $
 * Last modification on 01/02/2016: 12:53 PM
 * Current revision: : 1.1.1.1
 * <p>
 * Revision History:
 * ------------------
 */
public class InquiryChargeResponseBean {

    private int returnStatus;
    private String failReason;
    private List<ResultBean> resultBeanList;


    public int getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(int returnStatus) {
        this.returnStatus = returnStatus;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public List<ResultBean> getResultBeanList() {
        return resultBeanList;
    }

    public void setResultBeanList(List<ResultBean> resultBeanList) {
        this.resultBeanList = resultBeanList;
    }
}
