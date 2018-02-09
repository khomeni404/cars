package com.mbl.inquiry.model;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.*;

/**
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p/>
 * Original author: Ayatullah Khomeni
 * Date: 22/12/2015
 * Last modification by: ayat $
 * Last modification on 22/12/2015: 6:10 PM
 * Current revision: : 1.1.1.1
 * <p/>
 * Revision History:
 * ------------------
 */

public class CIBOperator  implements Serializable {
    private String oid;
    private String name;
    private String userId;                  // CASM ID
    /**
     * @see com.ibbl.util.CIBDictionary#CIB_USER_GENERAL/SUPER;
     */
    private String userType;
    private String enlistedBr;              // 109,213,175
    private Integer active;                 // 0 = Inactive, 1 = Active
    private Date recordDate;
    private String dataOperator;
    private String cellPhone;
    private String ipPhone;


    // Transient
    private Integer histLogType;        // To carry History Log Type for saving History
                                        // 1 = Charge/Discharge, 2 = Branch Assignment, 0 = Record Operator

    private Set<OperatorHistLog> histLogSet = new HashSet<OperatorHistLog>(0);

    /**
     * Getting sorted history set on record date
     * @return <code>Set<OperatorHistLog></code>
     */
    public Set<OperatorHistLog> getSortedHistLogSet() {
        List<OperatorHistLog> list = Lists.newArrayList(this.histLogSet);//
        if (list.size() > 0) {
            Collections.sort(list, new Comparator<OperatorHistLog>() {
                public int compare(OperatorHistLog o1, OperatorHistLog o2) {
                    return o1.getActionDate().compareTo(o2.getActionDate());
                }
            });
        }
        return ImmutableSet.copyOf(list);
    }


    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
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

    public String getDataOperator() {
        return dataOperator;
    }

    public Integer getHistLogType() {
        return histLogType;
    }

    public void setHistLogType(Integer histLogType) {
        this.histLogType = histLogType;
    }

    public void setDataOperator(String dataOperator) {
        this.dataOperator = dataOperator;
    }

    public Integer getActive() {
        return active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public String getUserType() {
        return userType;
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

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Set<OperatorHistLog> getHistLogSet() {
        return histLogSet;
    }

    public void setHistLogSet(Set<OperatorHistLog> histLogSet) {
        this.histLogSet = histLogSet;
    }
}
