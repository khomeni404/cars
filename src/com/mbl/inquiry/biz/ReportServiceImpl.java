package com.mbl.inquiry.biz;


import com.ibbl.cib.bl.CIBBLDataList;
import com.ibbl.cib.dl.hibernate.BranchPO;
import com.ibbl.core.common.DataCarrier;
import com.ibbl.core.exception.BusinessException;
import com.ibbl.core.exception.SystemException;
import com.ibbl.exception.PersistentLayerException;
import com.ibbl.inquiry.model.CIBOperator;
import com.ibbl.security.bl.co.SecurityGateCO;
import ibbl.remote.tx.TxController;

import java.util.List;
import java.util.Map;

/**
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p>
 * Original author: Ayatullah Khomeni<br/>
 * Date: 24/03/2016
 * Last modification by: ayat $
 * Last modification on 24/03/2016: 3:35 PM
 * Current revision: : 1.1.1.1
 * </p>
 * Revision History:
 * ------------------
 */

public class ReportServiceImpl extends SecurityGateCO implements CIBBLDataList {
    private int txID;

    public DataCarrier getEnlistedBrData(DataCarrier reqCarrier) throws SystemException, BusinessException {
        int actionKey = reqCarrier.getActionKey();
        verifyAccess(reqCarrier);
        TxController txController = TxController.createInstance();
        try {
            txID = txController.initPersistence();
        } catch (PersistentLayerException e) {
            throw new SystemException("System failed", e.getMessage());
        }

        DataCarrier resCarrier = new DataCarrier(actionKey);

        try {
            com.ibbl.common.dao.BranchDL branchDLCO = new com.ibbl.common.dao.BranchDL(txID);
            //ReportDBAccessDL reportDBAccessDL = new ReportDBAccessDL(txID);
            CIBOperator op = (CIBOperator) reqCarrier.retrieveData(DATA_CIB_OPERATOR_BEAN);
            if (op != null) {
                Map<String, List<BranchPO>> map = branchDLCO.getAllBrAndEnlistedBrPODataList("42", op);
                // OLD.... Map<String, Object> map = branchDLCO.getAllBrAndEnlistedBrDataMap("42", op);
                resCarrier.addData(DATA_CIB_OPERATOR_BEAN, op);
                resCarrier.addData(DATA_ALL_BR_AND_ENLISTED_BR_MAP, map);
            }
        } catch (Exception e) {
            resCarrier.addError("", new String[0]);
        }

        return resCarrier;
    }

   /* public DataCarrier getInquiryList2(DataCarrier reqCarrier) throws SystemException, BusinessException {
        int actionKey = reqCarrier.getActionKey();
        InquirySearchBean searchBean = (InquirySearchBean) reqCarrier.retrieveData(DATA_INQUIRY_SEARCH_BEAN);
        verifyAccess(reqCarrier);
        TxController txController = TxController.createInstance();
        try {
            txID = txController.initPersistance();
        } catch (PersistentLayerException e) {
            throw new SystemException("System failed", e.getMessage());
        }

        DataCarrier resCarrier = new DataCarrier(actionKey);
        try {
            ReportDBAccessDL reportDBAccessDL = new ReportDBAccessDL(txID);
            List<Inquiry> list = reportDBAccessDL.getInquiryList(searchBean);
            resCarrier.addData(DATA_INQUIRY_LIST, list);
        } catch (Exception e) {
            resCarrier.addError("", new String[0]);
        }

        return resCarrier;
    }*/




   /* public ActionResult getCibOperator2(ActionRequest actionRequest) throws SystemException, BusinessException {
        int actionKey = actionRequest.getActionKey();
        LoginBean loginBean = actionRequest.getLoginBean();

        verifyAccess(loginBean, actionKey);

        TxController txController = TxController.createInstance();
        try {
            txID = txController.initPersistance();
        } catch (PersistentLayerException e) {
            throw new SystemException("System failed", e.getMessage());
        }

        ActionResult result = new ActionResult(Boolean.FALSE);
        CIBOperator operator;
        try {
            ReportDBAccessDL reportDBAccessDL = new ReportDBAccessDL(txID);
            OperatorSearchBean searchBean = (OperatorSearchBean) actionRequest.getData(DATA_CIB_OPERATOR_SEARCH_BEAN);
            operator = reportDBAccessDL.getCibOperator(searchBean);
            if (operator != null) {
                Map<String, Object> map = reportDBAccessDL.getAllBrAndEnlistedBrDataMap("42", operator);
                result.put(DATA_CIB_OPERATOR_BEAN, operator);
                result.put(DATA_ALL_BR_AND_ENLISTED_BR_MAP, map);
                result.setSuccess(true);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setException(e);
        }

        return result;
    }*/


}
