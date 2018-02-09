package com.mbl.inquiry.action.bean;

import com.ibbl.security.ui.bean.LoginBean;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p/>
 * Original author: Ayatullah Khomeni
 * Date: 13/12/2015
 * Last modification by: ayat $
 * Last modification on 13/12/2015: 4:50 PM
 * Current revision: : 1.1.1.1
 * <p/>
 * Revision History:
 * ------------------
 */
public class OperatorSearchBean {
    private String name = "%";
    private String userId = "%";                  // CASM ID
    private String userType = "%";                // CIBDictionary.CIB_USER_GENERAL/SUPER;
    private String enlistedBr = "%";              // 109,213,175
    private String active = "%";                   // 0 = Inactive, 1 = Active

    public OperatorSearchBean(){}

    public OperatorSearchBean(String prop, String value) {
        Field field;
        Class c = this.getClass();
        try {
            field = c.getDeclaredField(prop);
            field.setAccessible(true);
            field.set(this, value);
            //System.out.println((String) field.get(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEnlistedBr() {
        return enlistedBr;
    }

    public void setEnlistedBr(String enlistedBr) {
        this.enlistedBr = enlistedBr;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
