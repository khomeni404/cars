package com.mbl.inquiry.dao;

import com.ibbl.core.exception.SystemException;
import com.ibbl.exception.PersistentLayerException;
import com.ibbl.inquiry.action.bean.InquirySearchBean;
import com.ibbl.inquiry.model.CIBOperator;
import com.ibbl.inquiry.model.Inquiry;
import com.ibbl.inquiry.model.ReportDoc;
import com.ibbl.security.ui.bean.LoginBean;
import com.ibbl.util.CIBDictionary;
import com.ibbl.util.DateUtil;
import com.ibbl.util.RectifyUtil;
import com.ibbl.util.WebDictionary;
import com.ibbl.util.enums.InquiryStatus;
import ibbl.core.util.IDGenerator;
import ibbl.remote.tx.EibsDAO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

/**
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p>
 * Original author: Ayatullah Khomeni<br/>
 * Date: 27/03/2016
 * Last modification by: ayat $
 * Last modification on 27/03/2016: 10:19 AM
 * Current revision: : 1.1.1.1
 * <p>
 * Revision History:
 * ------------------
 */
public class InquiryDL extends EibsDAO {
    private static Log log = LogFactory.getLog(InquiryDL.class);
    public IDGenerator idGenerator = new IDGenerator();
    private Connection conn = null;

    public InquiryDL(int txSessionID) throws PersistentLayerException {
        super(txSessionID);
    }


    @SuppressWarnings("unchecked")
    public List<Inquiry> getInquiryList(int start, int limit, String brCode) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Inquiry.class);
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        Criteria executableCriteria = criteria.getExecutableCriteria(session);
        if (!GenericValidator.isBlankOrNull(brCode)) {
            criteria.add(Restrictions.eq("brCode", brCode));
        }
        criteria.add(Restrictions.ne("this.status", InquiryStatus.CLOSED.CODE));
        criteria.add(Restrictions.ne("this.status", InquiryStatus.REPORTED.CODE));
        criteria.add(Restrictions.ne("this.status", InquiryStatus.REJECTED.CODE));
        executableCriteria.setFirstResult(start)
                .setMaxResults(limit)
                .addOrder(Order.asc("status"))
                .addOrder(Order.desc("lastUpdateDate"));
        return (List<Inquiry>) executableCriteria.list();
    }

    @SuppressWarnings("unchecked")
    public List<Inquiry> getInquiryListBySearch(InquirySearchBean searchBean) throws PersistentLayerException, ParseException, SystemException {
        try {
            DetachedCriteria criteria = getInquiryCriteria(searchBean);
            ProjectionList projectionList = Projections.projectionList()
                    .add(Projections.property("oid"), "oid")
                    .add(Projections.property("inqNo"), "inqNo")
                    .add(Projections.property("custId"), "custId")
                    .add(Projections.property("custName"), "custName")
                    .add(Projections.property("status"), "status")
                    .add(Projections.property("brCode"), "brCode")
                    .add(Projections.property("recordDate"), "recordDate")
                    .add(Projections.property("year"), "year")
                    .add(Projections.property("lastUpdateDate"), "lastUpdateDate");
            criteria.setProjection(
                    projectionList
            );
//            criteria.addOrder(Order.asc("inqStatus"));
            criteria.addOrder(Order.desc("lastUpdateDate"));
            criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            criteria.setResultTransformer(Transformers.aliasToBean(Inquiry.class));
            return criteria.getExecutableCriteria(this.session).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    @SuppressWarnings("unchecked")
    public List<Object[]> getInquiryCountListBySearch(InquirySearchBean searchBean) throws PersistentLayerException, ParseException, SystemException {
        DetachedCriteria criteria = getInquiryCriteria(searchBean);
        criteria.setProjection(
                Projections.projectionList()
                        .add(Projections.groupProperty("status"))
                        .add(Projections.count("oid"))
        );
        return criteria.getExecutableCriteria(this.session).list();

    }


    @SuppressWarnings("unchecked")
    private DetachedCriteria getInquiryCriteria(InquirySearchBean searchBean) throws PersistentLayerException, ParseException, SystemException {
        String oid = searchBean.getOid();
        String inquiryNo = searchBean.getInqNo();
        String custID = searchBean.getCustId();
        List<String> enlistedBrList = searchBean.getEnlistedBrList();
        String brCode = searchBean.getBrCode();
        String year = searchBean.getYear();
        String status = searchBean.getInqStatus();
        String costStatus = searchBean.getCostStatus();
        Date from = searchBean.getFromDate();
        Date to = searchBean.getToDate();
        LoginBean loginBean = searchBean.getLoginBean();


        DetachedCriteria criteria = DetachedCriteria.forClass(Inquiry.class);

        if (!GenericValidator.isBlankOrNull(oid) && !oid.equals("%")) {
            criteria.add(Restrictions.like("this.oid", oid, MatchMode.ANYWHERE));
            return criteria;
        }
        if (!GenericValidator.isBlankOrNull(inquiryNo) && !inquiryNo.equals("%")) {
            String[] inqNoArr = inquiryNo.split("-");
            criteria.add(Restrictions.like("this.inqNo", inqNoArr[0], MatchMode.END));
            year = inqNoArr.length > 1 ? inqNoArr[1] : year;
        }
        if (!GenericValidator.isBlankOrNull(custID) && !custID.equals("%")) {
            criteria.add(Restrictions.eq("this.custId", custID));
        }

        if (loginBean.isCibOperator()) {
            if (!loginBean.isSuperUser()) {
                criteria.add(Restrictions.in("this.brCode", enlistedBrList));
            }
        } else {
            criteria.add(Restrictions.eq("this.brCode", brCode));
        }

        if (!GenericValidator.isBlankOrNull(year) && !year.equals("%")) {
            criteria.add(Restrictions.like("this.year", year, MatchMode.END));
        }

        if (!GenericValidator.isBlankOrNull(status) && NumberUtils.isDigits(status)) {
            criteria.add(Restrictions.eq("this.status", Integer.valueOf(status)));
        }

        if (!GenericValidator.isBlankOrNull(costStatus) && NumberUtils.isDigits(costStatus)) {
            criteria.add(Restrictions.eq("this.costStatus", Integer.valueOf(costStatus)));
        }

        if (from != null && to != null) {
            criteria.add(Restrictions.between("this.requestDate", from, to));
        } else if (from != null) {
            //c.add(Restrictions.lt("requestDate", from));
        } else if (to != null) {
            //c.add(Restrictions.gt("requestDate", to));
        }
        //criteria.add(Restrictions.ne("this.finalStatus", CIBDictionary.INQ_FINAL_STATUS_REJECTED));
        criteria.add(Restrictions.ne("this.status", InquiryStatus.REJECTED.CODE));
        return criteria;

    }

    public String getNewInquiryNo(String brCode) {
        String result = "0";
        try {
            Criteria c = session.createCriteria(Inquiry.class)
                    .setProjection(Projections.max("inqNo"))
                    .add(Restrictions.eq("year", DateUtil.getCurrentYear()))
                    .add(Restrictions.eq("brCode", brCode));
            Object object = c.uniqueResult();

            if (object != null && object instanceof String) {
                result = (String) object;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (NumberUtils.isDigits(result)) {
            int seq = Integer.valueOf(result);
            seq = seq + 1;
            result = RectifyUtil.cutOrPadLeft(String.valueOf(seq), 4, "0");
        }
        return result;
    }

    public Inquiry uploadInquiryDocument(Inquiry inquiry, LoginBean loginBean) throws PersistentLayerException, ParseException, SystemException {
        if (inquiry != null) {
            Set<ReportDoc> docSet = inquiry.getDocSet();
            try {
                /*if (inquiry.getOid() == null) {
                    inquiry.setInqNo(String.valueOf(getMaxInquiryNo(inquiry.getBrCode()) + 1));
                    inquiry.setOid(String.valueOf(idGenerator.generate()));
                    session.persist(inquiry);
                } else {
                    session.merge(inquiry);
                }*/

                for (ReportDoc doc : docSet) {
                    if (doc.getOid() == null) {
                        doc.setOid(String.valueOf(idGenerator.generate()));
                        session.persist(doc);
                    } else {
                        session.merge(doc);
                    }
                }

            } catch (Exception ex) {
                return null;
            }
        }

        return inquiry;
    }

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
                map = new HashMap<>(2);
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

    private List<String> getAllBrCodeList(String bankCode) throws PersistentLayerException, ParseException, SystemException {
        @SuppressWarnings("unchecked")
        List<String> branchPOList = new ArrayList<String>();
        try {
            String queryString = session.getNamedQuery("getAllBranchListMap").getQueryString();
            Query query = session.createQuery(queryString);
            query.setString("bank_code", bankCode);
            List<Object[]> rows = query.list();
            for (Object[] row : rows) {
                branchPOList.add((String) row[0]);
            }
        } catch (Exception ex) {
            log.error("Hibernate exception for getting br list on getAllBrListMap()" + this.getClass().getSimpleName());
            return null;
        }
        return branchPOList;
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
    public Map<String, Object> getAllBrAndEnlistedBrDataMap(String bankCode, CIBOperator operator) throws PersistentLayerException, ParseException, SystemException {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, String>> nonEnlistedBrList = new ArrayList<Map<String, String>>(0);
        List<Map<String, String>> allEnlistedBrList;
        try {
            nonEnlistedBrList = getAllBrListMap(bankCode); // Temporary Assigned all br
            conn = session.connection();
            List<String> brCodeList = getAllEnlistedBrCodeList(1);
            allEnlistedBrList = getEnlistedBrListMap(bankCode, brCodeList);
            if (!CollectionUtils.isEmpty(nonEnlistedBrList) && !CollectionUtils.isEmpty(allEnlistedBrList)) {
                nonEnlistedBrList.removeAll(allEnlistedBrList);
            }
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

    }


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



       /*
       <sql-query name="getAllEnlistedBr">
            SELECT wm_concat(enlisted_br) AS enlisted_br
            FROM T_CIB_OPERATOR
            where active = ?
        </sql-query>

        String queryString = session.getNamedQuery("getAllEnlistedBr").getQueryString();
        PreparedStatement st = conn.prepareStatement(queryString);
        st.setInt(1, active);

        List<String> list;
        String enlistedBr = "";
        if (st.execute()) {
            ResultSet rs = st.getResultSet();
            while (rs.next()) {
                enlistedBr = rs.getString("enlisted_br");
            }
            rs.close();
            st.close();
        }
        String[] brArr = enlistedBr.split(",");
        return Arrays.asList(brArr);*/
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
    }


}
