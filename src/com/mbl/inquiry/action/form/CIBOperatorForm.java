package com.mbl.inquiry.action.form;

import com.ibbl.common.DateUtil;
import com.ibbl.inquiry.model.CIBOperator;
import com.ibbl.inquiry.model.OperatorHistLog;
import com.ibbl.util.CIBUtil;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * package ibbl.law.common.web.action;
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p>
 * Original author: Ayatullah Khomeni
 * Date: October 25, 2015(11:19:15 AM)
 * Last modification by: $Author: ayat $
 * Last modification on $Date: 2017/04/22 07:33:41 $
 * Current revision: $Revision: 1.2 $
 * <p>
 * Revision History:
 * ------------------
 */

public class CIBOperatorForm extends ActionForm {
    private String oid;
    private String name;
    private String userId;                  // CASM ID
    private String userType;                // CIBDictionary.CIB_USER_GENERAL/SUPER;
    private String enlistedBr;              // 109,213,175
    private Integer active;                 // 0 = Inactive, 1 = Active
    private String recordDate;
    private String dataOperator;
    private String cellPhone;
    private String ipPhone;
    private Integer histLogType;             // 1 = Charge/Discharge, 2 = Branch Assignment, 0 = Record Operator

    private Set<OperatorHistLog> histLogSet = new HashSet<OperatorHistLog>(0);

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (GenericValidator.isBlankOrNull(name)) {
            errors.add("name", new ActionMessage("errors.required", "Name"));
        }
        String regex = "^[a-zA-Z][a-zA-Z0-9_]*$";
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(userId);
        if (GenericValidator.isBlankOrNull(userId)) {
            errors.add("userId", new ActionMessage("errors.required", "CASM User ID"));
        } else if (!m.matches()) {
            errors.add("userId", new ActionMessage("errors.invalid", "CASM User ID"));
        }

        if (GenericValidator.isBlankOrNull(cellPhone)) {
            errors.add("cellPhone", new ActionMessage("errors.required", "11 Digit Cell No"));
        } else if (!CIBUtil.isValidCellNo(cellPhone)) {
            errors.add("errors", new ActionMessage("errors.details", "Cell No. " + CIBUtil.verifyCellNo(cellPhone)));
        }

        if (GenericValidator.isBlankOrNull(ipPhone)) {
            errors.add("ipPhone", new ActionMessage("errors.required", "IP Phone No."));
        }
        if (GenericValidator.isBlankOrNull(userType)) {
            errors.add("userType", new ActionMessage("errors.required", "User Type selection"));
        }
        return errors;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        this.oid = null;
        this.name = null;
        this.userId = null;
        this.userType = null;
        this.enlistedBr = null;
        this.active = null;
        this.recordDate = null;
        this.dataOperator = null;
        this.cellPhone = null;
        this.ipPhone = null;
        this.histLogType = null;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEnlistedBr() {
        return enlistedBr;
    }

    public void setEnlistedBr(String enlistedBr) {
        this.enlistedBr = enlistedBr;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getDataOperator() {
        return dataOperator;
    }

    public void setDataOperator(String dataOperator) {
        this.dataOperator = dataOperator;
    }

    public Integer getHistLogType() {
        return histLogType;
    }

    public void setHistLogType(Integer histLogType) {
        this.histLogType = histLogType;
    }

    public Set<OperatorHistLog> getHistLogSet() {
        return histLogSet;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setHistLogSet(Set<OperatorHistLog> histLogSet) {
        this.histLogSet = histLogSet;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getIpPhone() {
        return ipPhone;
    }

    public void setIpPhone(String ipPhone) {
        this.ipPhone = ipPhone;
    }

    public CIBOperator toModelBean() {
        CIBOperator bean = new CIBOperator();
        bean.setOid(this.oid);
        bean.setUserId(this.userId);
        bean.setUserType(this.userType);
        bean.setName(this.name);
        bean.setEnlistedBr(this.enlistedBr);
        bean.setActive(this.active);
        bean.setCellPhone(this.cellPhone);
        bean.setIpPhone(this.ipPhone);
        bean.setDataOperator(this.dataOperator);
        bean.setHistLogType(this.histLogType);
        try {
            bean.setRecordDate(DateUtil.toDate(this.recordDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // bean.setHistLogSet(this.histLogSet);
        return bean;
    }


}
