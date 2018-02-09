package com.mbl.inquiry.biz;

import com.ibbl.common.dao.CommonDL;
import com.ibbl.exception.PersistentLayerException;
import com.ibbl.inquiry.model.CIBOperator;
import com.ibbl.inquiry.model.OperatorHistLog;
import com.ibbl.security.bl.co.SecurityGateCO;
import com.ibbl.security.ui.bean.LoginBean;
import com.ibbl.util.CIBDictionary;
import com.ibbl.util.DataKey;
import ibbl.core.util.IDGenerator;
import ibbl.investment.common.util.IInvActionDataList;
import ibbl.remote.tx.TxController;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p>
 * Original author: Ayatullah Khomeni
 * Date: December 8, 2015(11:19:15 AM)
 * Last modification by: $Author: ayat $
 * Last modification on $Date: 2017/10/16 05:28:11 $
 * Current revision: $Revision: 1.4 $
 * <p>
 * Revision History:
 * ------------------
 */
public class OperatorBL extends SecurityGateCO implements IInvActionDataList, DataKey {
    private int txID;

    public boolean save(CIBOperator operator, LoginBean loginBean) {
        TxController txCO = TxController.createInstance();
        try {
            txID = txCO.initPersistence();
            CommonDL commonDL = new CommonDL(txID);
            commonDL.save(operator);

            OperatorHistLog log = createOperatorHistoryLog(operator, loginBean);
            commonDL.save(log);

            txCO.commitPersistence(txID);
            return true;
        } catch (PersistentLayerException e) {
            try {
                txCO.rollbackPersistence(txID);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }

    public boolean update(CIBOperator operator, LoginBean loginBean) {
        TxController txCO = TxController.createInstance();
        try {
            txID = txCO.initPersistence();
            CommonDL commonDL = new CommonDL(txID);
            commonDL.update(operator);

            OperatorHistLog log = createOperatorHistoryLog(operator, loginBean);
            commonDL.save(log);

            txCO.commitPersistence(txID);
            return true;
        } catch (PersistentLayerException e) {
            try {
                txCO.rollbackPersistence(txID);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }

    public Map<String, CIBOperator> getOperatorList(List<String> userIDList) {
        TxController txCO = TxController.createInstance();
        try {
            txID = txCO.initPersistence();
            CommonDL operatorDL = new CommonDL(txID);
            DetachedCriteria dc  = DetachedCriteria.forClass(CIBOperator.class)
                    .add(Restrictions.in("userId", userIDList))
                    .add(Restrictions.eq("active", CIBDictionary.CIB_OPERATOR_ACTIVE));
            List<CIBOperator> operatorList = operatorDL.search(dc);
            Map<String, CIBOperator> map = new HashMap<>();
            for (CIBOperator operator : operatorList) {
                map.put(operator.getUserId(), operator);
            }

            txCO.rollbackPersistence(txID);
            return map;
        } catch (PersistentLayerException e) {
            try {
                txCO.rollbackPersistence(txID);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    private OperatorHistLog createOperatorHistoryLog(CIBOperator operator, LoginBean loginBean) {
        IDGenerator idGenerator = new IDGenerator();
        OperatorHistLog log = new OperatorHistLog();
        Integer logType = operator.getHistLogType();
        log.setOid(String.valueOf(idGenerator.generate()));
        log.setHistoryType(logType);
        log.setActionDate(new Date());
        log.setRecordDate(new Date());
        if (logType == 1)
            log.setChargeState(operator.getActive());
        else if (logType == 2)
            log.setNewBrList(operator.getEnlistedBr());
        log.setNote(CIBDictionary.CIB_OPERATOR_HIST_LOG[logType][1]);

        log.setDataOperator(loginBean.getUserID());

        log.setCibOperator(operator);
        operator.getHistLogSet().add(log);
        return log;
    }

}
