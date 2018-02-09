package com.mbl.inquiry.dao;

import com.ibbl.cib.dl.hibernate.BranchPO;
import com.ibbl.core.exception.SystemException;
import com.ibbl.exception.PersistentLayerException;
import ibbl.core.util.IDGenerator;
import ibbl.remote.tx.EibsDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.sql.Connection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p>
 * Original author: Ayatullah Khomeni<br/>
 * Date: 27/03/2016
 * Last modification by: ayat $
 * Last modification on 27/03/2016: 10:30 AM
 * Current revision: : 1.1.1.1
 * <p>
 * Revision History:
 * ------------------
 */
public class BranchDL extends EibsDAO {

    public BranchDL(Session session) {
        super(session);
    }

    public BranchDL(int txID) throws PersistentLayerException {
        super(txID);
    }


    private static Log log = LogFactory.getLog(BranchDL.class);
    public IDGenerator idGenerator = new IDGenerator();

    private Connection conn = null;


    @SuppressWarnings("unchecked")
    private List<Map<String, String>> getEnlistedBrListMap(String bankCode, List<String> brCodeList) throws PersistentLayerException, ParseException, SystemException {
        @SuppressWarnings("unchecked")
        List<Map<String, String>> branchPOList = new ArrayList<Map<String, String>>();
        Map<String, String> map;
        try {
            String queryString = session.getNamedQuery("getEnlistedBrListMap").getQueryString();
            Query query = session.createQuery(queryString);
            query.setString("bank_code", bankCode);
            query.setParameterList("br_code_list", brCodeList);
            List<Object[]> rows = query.list();
            for (Object[] row : rows) {
                map = new HashMap<String, String>(2);
                map.put("code", (String) row[0]);
                map.put("name", (String) row[1]);
                branchPOList.add(map);
            }
        } catch (Exception ex) {
            log.error("Hibernate exception for getting br list on getEnlistedBrListMap()" + this.getClass().getSimpleName());
            return null;
        }
        return branchPOList;

    }


    @SuppressWarnings("unchecked")
    private List<Map<String, String>> getAllBrListMap(String bankCode) throws PersistentLayerException, ParseException, SystemException {
        @SuppressWarnings("unchecked")
        List<Map<String, String>> branchPOList = new ArrayList<Map<String, String>>();
        Map<String, String> map;
        try {
            //Query query = session.createQuery("select b.ibCode, b.branchName from BranchPO b where b.bankCode = :bank_code");
            String queryString = session.getNamedQuery("getAllBranchListMap").getQueryString();
            Query query = session.createQuery(queryString);
            query.setString("bank_code", bankCode);
            List<Object[]> rows = query.list();
            for (Object[] row : rows) {
                map = new HashMap<String, String>(2);
                map.put("code", (String) row[0]);
                map.put("name", (String) row[1]);
                branchPOList.add(map);
            }
        } catch (Exception ex) {
            log.error("Hibernate exception for getting br list on getAllBrListMap()" + this.getClass().getSimpleName());
            return null;
        }
        return branchPOList;

    }

    @SuppressWarnings("unchecked")
    public List<String> getAllBrCodeList(String bankCode) throws PersistentLayerException, ParseException, SystemException {
        List<String> branchCodeList = new ArrayList<String>();
        try {
            Object result = session.createCriteria(BranchPO.class).setProjection(Projections.property("ibCode"))
                    .add(Restrictions.eq("bankCode", bankCode)).list();
            if (result != null) {
                branchCodeList = (List<String>) result;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return branchCodeList;
    }

    /**
     * This method is used to get <code>Map<String, Object></code>
     *
     * @param bankCode <code>String</code> Bank Code e.g "42"
     * @param operator <code>CIBOperator</code>
     * @return <code>List<BranchPO></code>
     * @throws PersistentLayerException
     * @throws java.text.ParseException
     * @throws com.ibbl.core.exception.SystemException
     */
    /*public Map<String, Object> getAllBrAndEnlistedBrDataMap(String bankCode, CIBOperator operator) throws PersistentLayerException, ParseException, SystemException {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, String>> nonEnlistedBrList = new ArrayList<Map<String, String>>(0);
        List<Map<String, String>> allEnlistedBrList;
        try {
            nonEnlistedBrList = getAllBrListMap(bankCode); // Temporary Assigned all br
            conn = session.connection();
            List<String> brCodeList = getAllEnlistedBrCodeList(1);
            allEnlistedBrList = getEnlistedBrListMap(bankCode, brCodeList);
            nonEnlistedBrList.removeAll(allEnlistedBrList);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Hibernate exception for getting br list on getAllBrAndEnlistedBrDataMap()" + this.getClass().getSimpleName());
        }
        map.put(WebDictionary.DATA_NON_ENLISTED_BR_LIST, nonEnlistedBrList);

        List<Map<String, String>> operatorBrList = new ArrayList<Map<String, String>>();
        try {
            if (operator.getEnlistedBr() != null) {
                List<String> l2 = Arrays.asList(operator.getEnlistedBr().split(","));
                operatorBrList = getEnlistedBrListMap(bankCode, l2);
            }
        } catch (Exception ex) {
            log.error("Hibernate exception for getting br list on " + this.getClass().getSimpleName());
        }
        map.put(WebDictionary.DATA_ENLISTED_BR_LIST, operatorBrList);

        session.flush();
        session.clear();
        return map;

    }*/


    /*@SuppressWarnings("unchecked")
    private List<String> getAllEnlistedBrCodeList(Integer active) throws Exception {
        String queryString = session.getNamedQuery("getAllCIBOperator").getQueryString();
        Query query = session.createQuery(queryString);
        query.setInteger("active", active);
        List<CIBOperator> operatorList = query.list();

        String s = "";
        for (CIBOperator o : operatorList) {
            if (o.getEnlistedBr() != null)
                s += o.getEnlistedBr() + ",";
        }
        String[] brArr = s.split(",");
        return Arrays.asList(brArr);
    }

    public void closeConnection(Connection conn) throws SQLException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            throw e;
        }
        this.conn = null;
    }*/
}
