package com.mbl.common.dao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Copyright &copy; 2017-2018 Soft Engine Inc.  (www.soft-engine.net)
 * <p>
 * Original author: Khomeni
 * Date: 03/12/2017 3:33 PM
 * Last modification by: Khomeni: Khomeni
 * Last modification on 03/12/2017: 03/12/2017 3:33 PM
 * Current revision: 1.0.0: 1.1 $
 * <p>
 * Revision History:
 * ------------------
 */

public interface CommonDAO {
    boolean save(Object model);

    boolean saveAll(List<?> modelList);

    boolean update(Object model);

    boolean updateAll(List<?> modelList);

    boolean delete(Object model);

    //<PO> PO get(Class<PO> clazz, Long id);

    <PO> PO get(Class<PO> clazz, Serializable id);

    <PO> PO get(DetachedCriteria detachedCriteria);

    <PO> PO get(Class<PO> clazz, Long id, List<String> projections);

    <PO> PO getLight(Class<PO> clazz, String propertyName, Object propertyValue, List<String> projections);

    <PO> PO get(Class<PO> clazz, String propertyName, Object propertyValue);

    <PO> PO get(Class<PO> clazz, String propertyName1, Object propertyValue1, String propertyName2, Object propertyValue2);

    <PO> PO get(Class<PO> clazz, String aliasModel, String propertyName, Object propertyValue);

    <PO> PO get(Class<PO> clazz, String aliasModel, String aliasPropertyName, Object aliasPropertyValue, String propertyName, Object propertyValue);

    <PO> PO get(Class<PO> clazz, Long id, Boolean active);


    <PO> List<PO> search(DetachedCriteria detachedCriteria);

    <PO> List<PO> search(Class<PO> clazz, DetachedCriteria detachedCriteria);

    <PO> List<PO> search(Class<PO> clazz);

    <PO> List<PO> search(Class<PO> clazz, Order order);

    <PO> List<PO> searchByIdList(Class<PO> clazz, List<String> serializableIdList);

    <PO> List<PO> searchByIdArray(Class<PO> clazz, String[] serializableIdArray);

    <PO> List<PO> search(Class<PO> clazz, int start, int limit);

    <PO> List<PO> search(Class<PO> clazz, int start, int limit, List<String> projections);

    <PO> List<PO> search(Class<PO> clazz, List<String> projections);

    <PO> List<PO> search(Class<PO> clazz, Boolean active);

    <PO> List<PO> search(Class<PO> clazz, String propertyName, Object propertyValue);

    <PO> List<PO> search(Class<PO> clazz, String propertyName1, Object propertyValue1, String propertyName2, Object propertyValue2);

    <PO> List<PO> search(Class<PO> clazz, String aliasModel, String propertyName, Object propertyValue);

    <PO> List<PO> search(Class<PO> clazz, Map<String, Object> propertyValueMap);

    <PO> List<PO> searchBy_name(Class<PO> clazz, String text);

    <PO> List<PO> searchBy_name_nameBN(Class<PO> clazz, String text);

    Integer count(Class clazz);

    Integer count(Class clazz, String propName, Object propVal);

    Integer count(Class clazz, Boolean active);
}
