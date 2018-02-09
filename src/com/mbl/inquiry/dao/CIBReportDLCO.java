package com.mbl.inquiry.dao;

import com.ibbl.core.common.DataCarrier;
import com.ibbl.core.exception.SystemException;

/**
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p/>
 * Original author: Ayatullah Khomeni
 * Date: December 8, 2015(11:19:15 AM)
 * Last modification by: $Author: ayat $
 * Last modification on $Date: 2017/10/16 05:28:11 $
 * Current revision: $Revision: 1.3 $
 * <p/>
 * Revision History:
 * ------------------
 */
public class CIBReportDLCO {
    public DataCarrier execute(DataCarrier reqCarrier) throws SystemException {
        int actionKey = reqCarrier.getActionKey();
        int txID = (int) reqCarrier.getTxID();

        DataCarrier response = new DataCarrier(actionKey);

        /*switch (reqCarrier.getActionKey()) {
            case CIBDLActionList.ACTION_CREATE_INQUIRY: {
                String brCode = (String) reqCarrier.retrieveData(CIBDLDataList.DATA_BR_CODE);
                try {
                    ReportDBAccessDL reportDBAccessDL = new ReportDBAccessDL(txID);
                    int maxNo = reportDBAccessDL.getMaxInquiryNo(brCode);
                    response.addSuccess();
                    response.addData(CIBDLDataList.DATA_MAX_INQUIRY_NO, String.valueOf(maxNo));
                } catch (PersistentLayerException e) {
                    throw new SystemException("DLCO " + e.getMessage());
                }
                break;
            }
            case CIBDLActionList.ACTION_SAVE_INQUIRY: {
                Inquiry inquiryBean = (Inquiry) reqCarrier.retrieveData(CIBDLDataList.DATA_INQUIRY_BEAN);
                try {
                    ReportDBAccessDL reportDBAccessDL = new ReportDBAccessDL(txID);
                    Inquiry inquiry = reportDBAccessDL.saveOrUpdateInquiry(inquiryBean);
                    if (inquiry != null) {
                        response.addSuccess();
                        response.addData(CIBDLDataList.DATA_INQUIRY_BEAN, inquiry);
                    } else {
                        //response.addError("Branch Contract Data missing",new String[]{});
                        response.addWarning("Inquiry", new String[]{});
                    }
                } catch (PersistentLayerException e) {
                    throw new SystemException("DLCO " + e.getMessage());
                } catch (ParseException e) {
                    throw new SystemException("DLCO parsing exception" + e.getMessage());
                }
                break;
            }
            case CIBDLActionList.ACTION_INQUIRY_LIST: {
                InquirySearchBean searchBean = (InquirySearchBean) reqCarrier.retrieveData(CIBDLDataList.DATA_INQUIRY_SEARCH_BEAN);
                try {
                    ReportDBAccessDL reportDBAccessDL = new ReportDBAccessDL(txID);
                    List<?> requestList = reportDBAccessDL.getInquiryList(searchBean);
                    //List<?> brList = reportRequestDL.getAllBrList("42");
                    response.addSuccess();
                    response.addData(CIBDLDataList.DATA_INQUIRY_LIST, requestList);
                } catch (PersistentLayerException e) {
                    throw new SystemException("DLCO " + e.getMessage());
                } catch (ParseException e) {
                    throw new SystemException("DLCO parsing exception" + e.getMessage());
                }
                break;
            }

            case CIBDLActionList.ACTION_PROCESS_INQUIRY: {
                String custId = (String) reqCarrier.retrieveData(CIBDLDataList.DATA_BR_CODE);
                try {
                    ReportDBAccessDL reportDBAccessDL = new ReportDBAccessDL(txID);
                    Map<String, Object> customerListMap = reportDBAccessDL.getCustGrpCustDataMap(custId);
                    response.addSuccess();
                    response.addData(CIBDLDataList.DATA_LIST_MAP_CUSTOMER, customerListMap);
                } catch (PersistentLayerException e) {
                    throw new SystemException("DLCO " + e.getMessage());
                } catch (ParseException e) {
                    throw new SystemException("DLCO parsing exception" + e.getMessage());
                } catch (Exception e) {
                    throw new SystemException("Exception" + e.getMessage());
                }
                break;
            }
            case CIBDLActionList.ACTION_REPORT_ON_INQUIRY: {
                Inquiry inquiryBean = (Inquiry) reqCarrier.retrieveData(CIBDLDataList.DATA_INQUIRY_BEAN);
                try {
                    ReportDBAccessDL reportDBAccessDL = new ReportDBAccessDL(txID);
                    Inquiry inquiry = reportDBAccessDL.uploadInquiryDocument(inquiryBean);
                    if (inquiry != null) {
                        response.addSuccess();
                        response.addData(CIBDLDataList.DATA_INQUIRY_BEAN, inquiry);
                    } else {
                        //response.addError("Branch Contract Data missing",new String[]{});
                        response.addWarning("Inquiry", new String[]{});
                    }
                } catch (PersistentLayerException e) {
                    throw new SystemException("DLCO " + e.getMessage());
                } catch (ParseException e) {
                    throw new SystemException("DLCO parsing exception" + e.getMessage());
                }
                break;
            }
            case CIBDLActionList.ACTION_SAVE_CIB_OPERATOR: {
                CIBOperator dataBean = (CIBOperator) reqCarrier.retrieveData(CIBBLDataList.DATA_CIB_OPERATOR_BEAN);
                try {
                    ReportDBAccessDL reportDBAccessDL = new ReportDBAccessDL(txID);
                    CIBOperator savedDataBean = reportDBAccessDL.saveOrUpdateCibOperator(dataBean);
                    if (savedDataBean != null) {
                        response.addSuccess();
                        response.addData(CIBDLDataList.DATA_CIB_OPERATOR_BEAN, savedDataBean);
                    } else {
                        //response.addError("Branch Contract Data missing",new String[]{});
                        response.addWarning("CIB Operator save failed", new String[]{});
                    }
                } catch (PersistentLayerException e) {
                    throw new SystemException("DLCO " + e.getMessage());
                } catch (ParseException e) {
                    throw new SystemException("DLCO parsing exception" + e.getMessage());
                }
                break;
            }
            case CIBDLActionList.ACTION_GET_CIB_OPERATOR: {
                OperatorSearchBean searchBean = (OperatorSearchBean) reqCarrier.retrieveData(CIBBLDataList.DATA_CIB_OPERATOR_SEARCH_BEAN);
                try {
                    ReportDBAccessDL reportDBAccessDL = new ReportDBAccessDL(txID);
                    CIBOperator operator = reportDBAccessDL.getCibOperator(searchBean);
                    if (operator != null) {
                        response.addSuccess();
                        response.addData(CIBDLDataList.DATA_CIB_OPERATOR_BEAN, operator);
                    } else {
                        response.addError("CIB Operator Not Found",new String[]{});
                    }
                } catch (PersistentLayerException e) {
                    throw new SystemException("DLCO " + e.getMessage());
                } catch (ParseException e) {
                    throw new SystemException("DLCO parsing exception" + e.getMessage());
                }
                break;
            }
            case CIBDLActionList.ACTION_CIB_OPERATOR_LIST: {
                try {
                    ReportDBAccessDL reportDBAccessDL = new ReportDBAccessDL(txID);
                    List<?> operatorList = reportDBAccessDL.getAllCibOperator();
                    response.addSuccess();
                    response.addData(CIBDLDataList.DATA_CIB_OPERATOR_LIST, operatorList);
                } catch (PersistentLayerException e) {
                    throw new SystemException("DLCO " + e.getMessage());
                } catch (ParseException e) {
                    throw new SystemException("DLCO parsing exception" + e.getMessage());
                }
                break;
            }
            case CIBDLActionList.ACTION_EDIT_CIB_OPERATOR: {
                CIBOperator operator = (CIBOperator) reqCarrier.retrieveData(CIBDLDataList.DATA_CIB_OPERATOR_BEAN);
                try {
                    ReportDBAccessDL reportDBAccessDL = new ReportDBAccessDL(txID);
                    Map<String, Object> allBrAndEnlistedBrMap = reportDBAccessDL.getAllBrAndEnlistedBrDataMap(CIBDictionary.BANK_CODE_IBBL, operator);
                    response.addSuccess();
                    response.addData(CIBDLDataList.DATA_ALL_BR_AND_ENLISTED_BR, allBrAndEnlistedBrMap);
                } catch (PersistentLayerException e) {
                    throw new SystemException("DLCO " + e.getMessage());
                } catch (ParseException e) {
                    throw new SystemException("DLCO parsing exception" + e.getMessage());
                } catch (Exception e) {
                    throw new SystemException("Exception" + e.getMessage());
                }
                break;
            }
            case CIBDLActionList.ACTION_CHECK_CIB_OPERATOR: {
                LoginBean operator = (LoginBean) reqCarrier.retrieveData(CIBBLDataList.DATA_LOGIN);
                try {
                    ReportDBAccessDL reportDBAccessDL = new ReportDBAccessDL(txID);
                    boolean isCibOperator = reportDBAccessDL.isCibOperator(operator);
                    response.addSuccess();
                    response.addData("is.cib.operator", isCibOperator);
                } catch (PersistentLayerException e) {
                    throw new SystemException("DLCO " + e.getMessage());
                } catch (ParseException e) {
                    throw new SystemException("DLCO parsing exception" + e.getMessage());
                } catch (Exception e) {
                    throw new SystemException("Exception" + e.getMessage());
                }
                break;
            }
            default:
                throw new IllegalArgumentException("BL to DL action: " + actionKey + " is invalid");
        }*/

        return response;
    }
}
