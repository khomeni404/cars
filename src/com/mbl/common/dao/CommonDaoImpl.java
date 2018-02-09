package com.mbl.common.dao;

import com.google.gson.internal.Primitives;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Copyright &copy; 2017-2018 Soft Engine Inc.  (www.soft-engine.net)
 * <p>
 * Original author: Khomeni
 * Date: 16/06/2017 3:15 PM
 * Last modification by: Khomeni: Khomeni
 * Last modification on : 16/06/2017 3:15 PM
 * Current revision: : 1.1 $
 * <p>
 * Revision History:
 * ------------------
 */


@Repository
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class CommonDaoImpl implements CommonDAO {
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean save(Object model) {
        hibernateTemplate.persist(model);
        return true;
    }

    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean saveAll(List modelList) {
        modelList.stream().forEach(this::save);
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean update(Object model) {
        hibernateTemplate.merge(model);
        return true;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @SuppressWarnings("unchecked")
    public boolean updateAll(List<?> modelList) {
        modelList.stream().forEach(this::update);
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean delete(Object model) {
        hibernateTemplate.delete(model);
        return true;
    }

    @Override
    public <PO> PO get(Class<PO> clazz, Serializable id) {
        Object model = hibernateTemplate.get(clazz, id);
        return Primitives.wrap(clazz).cast(model);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <PO> PO get(DetachedCriteria detachedCriteria) {
        detachedCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List modelList = hibernateTemplate.findByCriteria(detachedCriteria);
        return CollectionUtils.isEmpty(modelList) ? null : (PO) modelList.get(0);
    }

    @Override
    public <PO> PO get(Class<PO> clazz, Long id, List<String> projections) {
        try {
            DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
            criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            Criteria executableCriteria = criteria.getExecutableCriteria(hibernateTemplate.getSessionFactory().openSession());

            if (!CollectionUtils.isEmpty(projections)) {
                ProjectionList projectionList = Projections.projectionList();
                for (String property : projections) {
                    projectionList.add(Projections.property(property), property);
                }
                executableCriteria.setProjection(projectionList);
            }
            executableCriteria.add(Restrictions.eq("id", id));
            executableCriteria.setResultTransformer(Transformers.aliasToBean(clazz));
            Object model = executableCriteria.uniqueResult();
            return Primitives.wrap(clazz).cast(model);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public <PO> PO getLight(Class<PO> clazz,  String propertyName, Object propertyValue, List<String> projections) {
        try {
            DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
            criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            Criteria executableCriteria = criteria.getExecutableCriteria(hibernateTemplate.getSessionFactory().openSession());

            if (!CollectionUtils.isEmpty(projections)) {
                ProjectionList projectionList = Projections.projectionList();
                for (String property : projections) {
                    projectionList.add(Projections.property(property), property);
                }
                executableCriteria.setProjection(projectionList);
            }
            executableCriteria.add(Restrictions.eq(propertyName, propertyValue));
            executableCriteria.setResultTransformer(Transformers.aliasToBean(clazz));
            Object model = executableCriteria.uniqueResult();
            return Primitives.wrap(clazz).cast(model);
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public <PO> PO get(Class<PO> clazz, String propertyName, Object propertyValue) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .add(Restrictions.eq(propertyName, propertyValue));

        List modelList = hibernateTemplate.findByCriteria(criteria);
        return CollectionUtils.isEmpty(modelList) ? null : Primitives.wrap(clazz).cast(modelList.get(0));

    }

    @Override
    public <PO> PO get(Class<PO> clazz, String propertyName1, Object propertyValue1, String propertyName2, Object propertyValue2) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .add(Restrictions.eq(propertyName1, propertyValue1))
                .add(Restrictions.eq(propertyName2, propertyValue2));
        List modelList = hibernateTemplate.findByCriteria(criteria);
        return CollectionUtils.isEmpty(modelList) ? null : Primitives.wrap(clazz).cast(modelList.get(0));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <PO> PO get(Class<PO> clazz, String aliasModel, String propertyName, Object propertyValue) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .createAlias(aliasModel, "model2")
                .add(Restrictions.eq("model2." + propertyName, propertyValue));
        List modelList = hibernateTemplate.findByCriteria(criteria);
        return CollectionUtils.isEmpty(modelList) ? null : Primitives.wrap(clazz).cast(modelList.get(0));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <PO> PO get(Class<PO> clazz, String aliasModel, String aliasPropertyName, Object aliasPropertyValue, String propertyName, Object propertyValue) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .createAlias(aliasModel, "model2")
                .add(Restrictions.eq("model2." + aliasPropertyName, aliasPropertyValue))
                .add(Restrictions.eq(propertyName, propertyValue));
        List modelList = hibernateTemplate.findByCriteria(criteria);
        return CollectionUtils.isEmpty(modelList) ? null : Primitives.wrap(clazz).cast(modelList.get(0));
    }

    @Override
    public <PO> PO get(Class<PO> clazz, Long id, Boolean active) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .add(Restrictions.eq("id", id))
                .add(Restrictions.eq("active", active));

        List modelList = hibernateTemplate.findByCriteria(criteria);
        return CollectionUtils.isEmpty(modelList) ? null : Primitives.wrap(clazz).cast(modelList.get(0));

    }


    @Override
    @SuppressWarnings("unchecked")
    public <PO> List<PO> search(DetachedCriteria detachedCriteria) {
        detachedCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<PO>) hibernateTemplate.findByCriteria(detachedCriteria);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <PO> List<PO> search(Class<PO> clazz, DetachedCriteria detachedCriteria) {
        detachedCriteria.setResultTransformer(Transformers.aliasToBean(clazz));
        return (List<PO>) hibernateTemplate.findByCriteria(detachedCriteria);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <PO> List<PO> search(Class<PO> clazz) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
        return (List<PO>) hibernateTemplate.findByCriteria(criteria);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <PO> List<PO> search(Class<PO> clazz, Order order) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .addOrder(order);
        return (List<PO>) hibernateTemplate.findByCriteria(criteria);
    }


    @Override
    @SuppressWarnings("unchecked")
    public <PO> List<PO> searchByIdList(Class<PO> clazz, List<String> serializableIdList) {
        List<Long> longList = serializableIdList.stream().map(NumberUtils::toLong).collect(Collectors.toList());
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .add(Restrictions.in("id", longList));
        return (List<PO>) hibernateTemplate.findByCriteria(criteria);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <PO> List<PO> searchByIdArray(Class<PO> clazz, String[] serializableIdArray) {
        List<String> idList = Arrays.asList(serializableIdArray);
        return searchByIdList(clazz, idList);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <PO> List<PO> search(Class<PO> clazz, int start, int limit) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        Criteria executableCriteria = criteria.getExecutableCriteria(hibernateTemplate.getSessionFactory().openSession());

        executableCriteria.setFirstResult(start);
        executableCriteria.setMaxResults(limit);
        return (List<PO>) executableCriteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <PO> List<PO> search(Class<PO> clazz, int start, int limit, List<String> projections) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        Criteria executableCriteria = criteria.getExecutableCriteria(hibernateTemplate.getSessionFactory().openSession());

        if (!CollectionUtils.isEmpty(projections)) {
            ProjectionList projectionList = Projections.projectionList();
            for (String property : projections) {
                projectionList.add(Projections.property(property), property);
            }
            executableCriteria.setProjection(projectionList);
        }

        executableCriteria.setResultTransformer(Transformers.aliasToBean(clazz));
        executableCriteria.setFirstResult(start);
        executableCriteria.setMaxResults(limit);
        return (List<PO>) executableCriteria.list();

    }

    @Override
    @SuppressWarnings("unchecked")
    public <PO> List<PO> search(Class<PO> clazz, List<String> projections) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        Criteria executableCriteria = criteria.getExecutableCriteria(hibernateTemplate.getSessionFactory().openSession());

        if (!CollectionUtils.isEmpty(projections)) {
            ProjectionList projectionList = Projections.projectionList();
            for (String property : projections) {
                projectionList.add(Projections.property(property), property);
            }
            executableCriteria.setProjection(projectionList);
        }
        executableCriteria.setResultTransformer(Transformers.aliasToBean(clazz));
        return (List<PO>) executableCriteria.list();

    }


    @Override
    @SuppressWarnings("unchecked")
    public <PO> List<PO> search(Class<PO> clazz, Boolean active) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .add(Restrictions.eq("active", active));
        return (List<PO>) hibernateTemplate.findByCriteria(criteria);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <PO> List<PO> search(Class<PO> clazz, String propertyName, Object propertyValue) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .add(Restrictions.eq(propertyName, propertyValue));
        return (List<PO>) hibernateTemplate.findByCriteria(criteria);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <PO> List<PO> search(Class<PO> clazz, String propertyName1, Object propertyValue1, String propertyName2, Object propertyValue2) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .add(Restrictions.eq(propertyName1, propertyValue1))
                .add(Restrictions.eq(propertyName2, propertyValue2));
        return (List<PO>) hibernateTemplate.findByCriteria(criteria);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <PO> List<PO> search(Class<PO> clazz, String aliasModel, String propertyName, Object propertyValue) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .createAlias(aliasModel, "model2")
                .add(Restrictions.eq("model2." + propertyName, propertyValue));
        return (List<PO>) hibernateTemplate.findByCriteria(criteria);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <PO> List<PO> search(Class<PO> clazz, Map<String, Object> propertyValueMap) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
        for (String property : propertyValueMap.keySet()) {
            criteria.add(Restrictions.eq(property, propertyValueMap.get(property)));
        }
        return (List<PO>) hibernateTemplate.findByCriteria(criteria);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <PO> List<PO> searchBy_name(Class<PO> clazz, String text) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .add(Restrictions.like("name", text, MatchMode.ANYWHERE));
        return (List<PO>) hibernateTemplate.findByCriteria(criteria);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <PO> List<PO> searchBy_name_nameBN(Class<PO> clazz, String text) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .add(Restrictions.disjunction()
                        .add(
                                Restrictions.or(
                                        Restrictions.like("name", text, MatchMode.ANYWHERE),
                                        Restrictions.like("nameBN", text, MatchMode.ANYWHERE)
                                )
                        )
                );
        return (List<PO>) hibernateTemplate.findByCriteria(criteria);
    }


    @Override
    public Integer count(Class clazz) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .setProjection(Projections.rowCount());
        Object o = hibernateTemplate.findByCriteria(criteria).get(0);
        if (o == null) {
            return 0;
        } else {
            return Integer.valueOf(o.toString());
        }
    }

    @Override
    public Integer count(Class clazz, String propName, Object propVal) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .add(Restrictions.eq(propName, propVal))
                .setProjection(Projections.rowCount());
        Object o = hibernateTemplate.findByCriteria(criteria).get(0);
        if (o == null) {
            return 0;
        } else {
            return Integer.valueOf(o.toString());
        }
    }

    @Override
    public Integer count(Class clazz, Boolean active) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .add(Restrictions.eq("active", active))
                .setProjection(Projections.rowCount());
        Object o = hibernateTemplate.findByCriteria(criteria).get(0);
        if (o == null) {
            return 0;
        } else {
            return Integer.valueOf(o.toString());
        }
    }
}
