package com.mbl.inquiry.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p/>
 * Original author: Ayatullah Khomeni<br/>
 * Date: 24/03/2016
 * Last modification by: ayat $
 * Last modification on 24/03/2016: 12:59 PM
 * Current revision: : 1.1.1.1
 * <p/>
 * Revision History:
 * ------------------
 */
public class ActionResult  implements Serializable {
    public ActionResult(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public ActionResult() {
        this.success = false;
    }
    public ActionResult(Boolean bool) {
        this.success = bool;
    }

    private boolean success;

    private String msg;

    private Object dataObject;

    private Exception exception;

    private Map<String, Object> map = new HashMap<>();

    public Map<String, Object> getModelMap() {
        this.map.put("message", this.msg);
        this.map.put("success", this.success);
        return this.map;
    }

    public boolean hasMapValue() {
        return map != null && map.size() > 0;
    }

    public void put(String key, Object value) {
        map.put(key, value);
    }

    public Object get(String key) {
        return map.get(key);
    }

    public Object getDataObject() {
        return this.dataObject;
    }

    public void setDataObject(Object dataObject) {
        this.dataObject = dataObject;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }




    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    /*private int actionKey;
    private long txID;
    private boolean success;
    private String msg;
    private int msgType;  // 0 = fail, 1 = exception
    private Map<String, Object> data = new HashMap<>(0);
    private Exception exception;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public Object getData(String key) {
        return this.data.get(key);
    }

    public void addData(String key, Object data) {
        this.data.put(key, data);
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
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
*/
}
