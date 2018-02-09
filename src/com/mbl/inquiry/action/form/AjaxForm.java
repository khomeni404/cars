package com.mbl.inquiry.action.form;

import org.apache.struts.action.ActionForm;

/**
 * package ibbl.law.common.web.action;
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p/>
 * Original author: Ayatullah Khomeni
 * Date: October 25, 2015(11:19:15 AM)
 * Last modification by: $Author: ayat $
 * Last modification on $Date: 2017/01/10 08:09:12 $
 * Current revision: $Revision: 1.1.1.1 $
 * <p/>
 * Revision History:
 * ------------------
 */

public class AjaxForm extends ActionForm {
    private String ajaxKey;

    public String getAjaxKey() {
        return ajaxKey;
    }

    public void setAjaxKey(String ajaxKey) {
        this.ajaxKey = ajaxKey;
    }
}
