package com.mbl.inquiry.action.form;

import com.ibbl.cib.ui.form.GenericForm;
import com.ibbl.common.DateUtil;
import com.ibbl.inquiry.action.CibInquiryAction;
import com.ibbl.inquiry.model.Inquiry;
import com.ibbl.inquiry.model.ReportDoc;
import com.ibbl.util.CIBDictionary;
import com.ibbl.util.DataKey;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * package ibbl.law.common.web.action;
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p>
 * Original author: Ayatullah Khomeni
 * Date: October 25, 2015(11:19:15 AM)
 * Last modification by: $Author: ayat $
 * Last modification on $Date: 2017/10/31 13:55:30 $
 * Current revision: $Revision: 1.3 $
 * <p>
 * Revision History:
 * ------------------
 */

public class ReportDocForm extends GenericForm {

    private String oid;
    private String inqOid;
    private String docName;
    private String note;
    private String givenName;
    private String uploadDate;
    private String operator;
    private Inquiry inquiry;
    private Integer isValid;
    private FormFile formFile = null;
    private List<FormFile> formFiles = new ArrayList<>();

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        oid = null;
        //inqOid = null; this is to use for next file
        //docName = null; this is to use for next file
        note = null;
        givenName = null;
        uploadDate = null;
        operator = null;
        inquiry = null;
        isValid = null;
        formFile = null;
        formFiles = null;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public List<FormFile> getFormFiles() {
        return formFiles;
    }

    public void setFormFiles(List<FormFile> formFiles) {
        this.formFiles = formFiles;
    }

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

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Inquiry getInquiry() {
        return inquiry;
    }

    public void setInquiry(Inquiry inquiry) {
        this.inquiry = inquiry;
    }

    public FormFile getFormFile() {
        return formFile;
    }

    public void setFormFile(FormFile formFile) {
        this.formFile = formFile;
    }

    public String getInqOid() {
        return inqOid;
    }

    public void setInqOid(String inqOid) {
        this.inqOid = inqOid;
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        String action = super.getAction();
        if (action.equals(CibInquiryAction.ACTION_METHOD_ADD)) {

        } else {
            if (GenericValidator.isBlankOrNull(inqOid)) {
                errors.add(CIBDictionary.ERROR_TITLE, new ActionMessage("errors.required", "No valid Inquiry Found !"));
            }
            if (GenericValidator.isBlankOrNull(docName)) {
                errors.add("docName", new ActionMessage("errors.required", "Doc Name"));
            }
            if (GenericValidator.isBlankOrNull(inqOid)) {
                errors.add("inqOid", new ActionMessage("errors.required", "Valid Inquiry"));
            }

            List<FormFile> newFileList = new ArrayList<>();

            for (FormFile ff : formFiles) {
                if (!GenericValidator.isBlankOrNull(ff.getFileName())) {
                    newFileList.add(ff);
                }
            }

            if (CollectionUtils.isEmpty(newFileList)) {
                errors.add("formFiles", new ActionMessage("errors.details", "At least one file required"));
            }
        }

        request.getSession().

                setAttribute(DataKey.CIB_REPORT_DOC_FORM, this);

        return errors;
    }


    public ReportDoc toModelBean() {
        ReportDoc bean = new ReportDoc();
        bean.setOid(this.oid);
        bean.setDocName(this.docName);
        bean.setNote(this.note);
        bean.setOperator(this.operator);
        bean.setGivenName(this.givenName);
        bean.setFormFile(this.formFile);
        bean.setInquiry(this.inquiry);
        try {
            bean.setUploadDate(DateUtil.toDate(this.uploadDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // bean.setHistLogSet(this.histLogSet);
        return bean;
    }
}
