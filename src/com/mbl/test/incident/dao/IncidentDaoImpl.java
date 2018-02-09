package com.mbl.test.incident.dao;

import com.ibbl.incident.model.Incident;
import com.ibbl.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Khomeni
 *         Created on : 19-May-17 at 5:17 PM
 */

@Repository
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class IncidentDaoImpl implements IncidentDAO {

    @Autowired
    @Qualifier(value = "hibernateTemplate")
    private HibernateTemplate hibernateTemplate;

    @Override
    public Integer maxRefNo() {
        Integer yy = Integer.valueOf(Constants.SDF_YY.format(new Date()));
        Integer smallest = Integer.valueOf(yy + "001");
        try {
            Integer largest = Integer.valueOf(yy + "999");
            DetachedCriteria dc = DetachedCriteria.forClass(Incident.class)
                    .setProjection(Projections.max("refNo")).add(Restrictions.between("refNo", smallest, largest));
            List<?> result = hibernateTemplate.findByCriteria(dc);
            return CollectionUtils.isEmpty(result) ? smallest - 1 : (Integer) result.get(0);
        } catch (Exception e) {
            return smallest - 1;
        }
    }
}
