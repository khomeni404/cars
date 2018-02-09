package com.mbl.inquiry.model;

import org.apache.struts.upload.FormFile;

import java.io.Serializable;
import java.util.Date;

/**
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p/>
 * Original author: Ayatullah Khomeni
 * Date: 29/12/2015
 * Last modification by: ayat $
 * Last modification on 29/12/2015: 3:47 PM
 * Current revision: : 1.1.1.1
 * <p/>
 * Revision History:
 * ------------------
 */
public class ReportDoc  implements Serializable {

    private String oid;
    private String docName;
    private String note;
    private String fileName;
    private String givenName;
    private Date uploadDate;
    private String operator;
    private byte[] docStream;
    private Inquiry inquiry;
    private FormFile formFile = null;


    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public FormFile getFormFile() {
        return formFile;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFormFile(FormFile formFile) {
        this.formFile = formFile;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public byte[] getDocStream() {
        return docStream;
    }

    public void setDocStream(byte[] docStream) {
        this.docStream = docStream;
    }

    public Inquiry getInquiry() {
        return inquiry;
    }

    public void setInquiry(Inquiry inquiry) {
        this.inquiry = inquiry;
    }
}
