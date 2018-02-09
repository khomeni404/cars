package com.mbl.inquiry.action.form;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * package com.ibbl.report.util;
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p/>
 * Original author: Ayatullah Khomeni
 * Date: October 18, 2015(10:37:55 AM)
 * Last modification by: $Author: ayat $
 * Last modification on $Date: 2017/01/10 08:09:12 $
 * Current revision: $Revision: 1.1.1.1 $
 * <p/>
 * Revision History: This Form is used to dispatch an action to a particular Action Class
 *      e.g: For <code>"/contextPath/abc.do?action=delete"</code> can be invoked the method <code>delete()</code> of <code>MyAbcAction</code></code> Class
 * ------------------
 */

public class DispatchActionForm extends ActionForm {

    private String action = " ";

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        return super.validate(mapping, request);
    }
}
