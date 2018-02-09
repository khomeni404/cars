package com.mbl.inquiry.action.form;

import com.ibbl.data.ImportAction;
import com.ibbl.util.CIBDictionary;
import ibbl.deposit.common.bl.AccountBL;
import ibbl.deposit.common.bl.IAccount;
import ibbl.deposit.common.util.codeformat.AccNoFactory;
import ibbl.deposit.common.util.codeformat.IAccNo;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p>
 * Original author: Ayatullah Khomeni
 * Date: 13/12/2015
 * Last modification by: ayat $
 * Last modification on 13/12/2015: 4:50 PM
 * Current revision: : 1.1.1.1
 * <p>
 * Revision History:
 * ------------------
 */
public class InquiryChargeForm extends ActionForm {
    private String inquiryOid;
    private String inquiryId;
    private String targetAccount;
    private Double keyCost;
    private Double linkingCost;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        this.inquiryOid = null;
        this.targetAccount = null;
        this.keyCost = null;
        this.linkingCost = null;
        this.inquiryId = null;
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (GenericValidator.isBlankOrNull(inquiryOid)) {
            errors.add(CIBDictionary.ERROR_TITLE, new ActionMessage("errors.required", "No valid Inquiry Found !"));
        }

        if (GenericValidator.isBlankOrNull(targetAccount)) {
            errors.add(CIBDictionary.ERROR_TITLE, new ActionMessage("errors.required", "Targeted A/C No."));
        } else {
            IAccNo i = AccNoFactory.getAccNoObject();
            if (!i.verify(targetAccount)) {
                errors.add(CIBDictionary.ERROR_TITLE, new ActionMessage("errors.details", "A/C no is "+targetAccount+" invalid."));
            }
        }
        if (keyCost + linkingCost <= 0) {
            errors.add(CIBDictionary.ERROR_TITLE, new ActionMessage("errors.required", "Key Cost / Linking Cost"));
        }

        return errors;
    }

    public String getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(String targetAccount) {
        this.targetAccount = targetAccount;
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

    public String getInquiryOid() {
        return inquiryOid;
    }

    public void setInquiryOid(String inquiryOid) {
        this.inquiryOid = inquiryOid;
    }
}
