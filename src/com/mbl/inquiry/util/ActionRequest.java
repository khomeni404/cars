package com.mbl.inquiry.util;

import com.ibbl.security.ui.bean.LoginBean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p/>
 * Original author: Ayatullah Khomeni<br/>
 * Date: 24/03/2016
 * Last modification by: ayat $
 * Last modification on 24/03/2016: 1:00 PM
 * Current revision: : 1.1.1.1
 * <p/>
 * Revision History:
 * ------------------
 */
public class ActionRequest  implements Serializable {
    private int actionKey;
    private LoginBean loginBean;
    private long txID;
    private Map<String, Object> data = new HashMap<>(0);

    public ActionRequest(int actionKey) {
        this.actionKey = actionKey;
    }

    public int getActionKey() {
        return actionKey;
    }

    public void setActionKey(int actionKey) {
        this.actionKey = actionKey;
    }

    public long getTxID() {
        return txID;
    }

    public void setTxID(long txID) {
        this.txID = txID;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public Object getData(String key) {
        return this.data.get(key);
    }

    public void addData(String key, Object data) {
        this.data.put(key, data);
    }


}
