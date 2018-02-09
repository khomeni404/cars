package com.mbl.ir.dao;

import com.mbl.ir.model.Inquiry;
import com.mbl.util.RectifyUtil;
import net.softengine.util.DateUtil;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Copyright @ Soft Engine [www.soft-engine.net].
 * Created on 08-Feb-18 at 12:13 PM
 * Created By : Khomeni
 * Edited By : Khomeni &
 * Last Edited on : 08-Feb-18
 * Version : 1.0
 */

@Repository
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class InquiryDaoImpl implements InquiryDAO {
    @Autowired
    private HibernateTemplate hibernateTemplate;

    public String getNewInquiryNo(String brCode) {
        String result = "0";
        try {
            Criteria c = hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Inquiry.class)
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
            result = RectifyUtil.cutOrPadLeft(String.valueOf(seq), 3, "0");
        }
        return result;
    }
}
