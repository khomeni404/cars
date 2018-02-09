package com.mbl.common.service;

import com.mbl.common.dao.DaoFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Copyright @ Soft Engine [www.soft-engine.net].
 * Created on 15-Dec-17 at 10:15 AM
 * Created By : Khomeni
 * Edited By : Khomeni &
 * Last Edited on : 15-Dec-17
 * Version : 1.0
 */

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class CommonServiceImpl extends DaoFactory implements CommonService {

    @Override
    @Transactional(readOnly = false)
    public boolean save(Object model) {
        return commonDAO.save(model);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean update(Object model) {
        return commonDAO.update(model);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean delete(Object model) {
        return commonDAO.delete(model);
    }


    @Override
    public <PO> List<PO> search(DetachedCriteria detachedCriteria) {
        return commonDAO.search(detachedCriteria);
    }

    @Override
    public <PO> List<PO> search(Class<PO> clazz, DetachedCriteria detachedCriteria) {
        return commonDAO.search(clazz, detachedCriteria);
    }

    @Override
    public <PO> List<PO> search(Class<PO> clazz) {
        return commonDAO.search(clazz);
    }

    @Override
    public <PO> List<PO> search(Class<PO> clazz, Order order) {
        return commonDAO.search(clazz, order);
    }

    @Override
    public <PO> List<PO> searchByIdList(Class<PO> clazz, List<String> serializableIdList) {
        return commonDAO.searchByIdList(clazz, serializableIdList);
    }

    @Override
    public <PO> List<PO> searchByIdArray(Class<PO> clazz, String[] serializableIdArray) {
        return commonDAO.searchByIdArray(clazz, serializableIdArray);
    }

    @Override
    public <PO> List<PO> search(Class<PO> clazz, List<String> projections) {
        return commonDAO.search(clazz, projections);
    }

    @Override
    public <PO> List<PO> search(Class<PO> clazz, int start, int limit) {
        return commonDAO.search(clazz, start, limit);
    }

    @Override
    public <PO> List<PO> search(Class<PO> clazz, int start, int limit, List<String> projections) {
        return commonDAO.search(clazz, start, limit, projections);
    }

    @Override
    public <PO> List<PO> search(Class<PO> clazz, Boolean active) {
        return commonDAO.search(clazz, active);
    }

    @Override
    public <PO> List<PO> search(Class<PO> clazz, String propertyName, Object propertyValue) {
        return commonDAO.search(clazz, propertyName, propertyValue);
    }

    @Override
    public <PO> List<PO> search(Class<PO> clazz, String aliasModel, String propertyName, Object propertyValue) {
        return commonDAO.search(clazz, aliasModel, propertyName, propertyValue);
    }

    @Override
    public <PO> List<PO> search(Class<PO> clazz, Map<String, Object> propertyValueMap) {
        return commonDAO.search(clazz, propertyValueMap);
    }

    @Override
    public <PO> List<PO> searchBy_name(Class<PO> clazz, String text) {
        return commonDAO.searchBy_name(clazz, text);
    }

    @Override
    public <PO> List<PO> searchBy_name_nameBN(Class<PO> clazz, String text) {
        return commonDAO.searchBy_name_nameBN(clazz, text);
    }

    @Override
    public <PO> PO get(Class<PO> clazz, Serializable id) {
        return commonDAO.get(clazz, id);
    }

    public <PO> PO get(DetachedCriteria detachedCriteria) {
        return commonDAO.get(detachedCriteria);
    }

    @Override
    public <PO> PO get(Class<PO> clazz, Long id, List<String> projections) {
        return commonDAO.get(clazz, id, projections);
    }

    @Override
    public <PO> PO getLight(Class<PO> clazz, String propertyName, Object propertyValue, List<String> projections) {
        return commonDAO.getLight(clazz, propertyName, propertyValue, projections);
    }

    @Override
    public <PO> PO get(Class<PO> clazz, String propertyName, Object propertyValue) {
        return commonDAO.get(clazz, propertyName, propertyValue);
    }

    @Override
    public <PO> PO get(Class<PO> clazz, String propertyName1, Object propertyValue1, String propertyName2, Object propertyValue2) {
        return commonDAO.get(clazz, propertyName1, propertyValue1, propertyName2, propertyValue2);
    }


    @Override
    public <PO> PO get(Class<PO> clazz, String aliasModel, String propertyName, Object propertyValue) {
        return commonDAO.get(clazz, aliasModel, propertyName, propertyValue);
    }

    @Override
    public <PO> PO get(Class<PO> clazz, String aliasModel, String aliasPropertyName, Object aliasPropertyValue, String propertyName, Object propertyValue) {
        return commonDAO.get(clazz, aliasModel, aliasPropertyName, aliasPropertyValue, propertyName, propertyValue);
    }

    @Override
    public <PO> PO get(Class<PO> clazz, Long id, Boolean active) {
        return commonDAO.get(clazz, id, active);
    }

    @Override
    public Integer count(Class clazz) {
        return commonDAO.count(clazz);
    }

    @Override
    public Integer count(Class clazz, String propName, Object propVal) {
        return commonDAO.count(clazz, propName, propVal);
    }

    @Override
    public Integer count(Class clazz, Boolean active) {
        return commonDAO.count(clazz, active);
    }



}
