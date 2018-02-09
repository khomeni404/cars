package com.mbl.inquiry.biz;

import com.ibbl.cib.bl.CIBBLDataList;
import com.ibbl.cib.common.NotifyDictionary;
import com.ibbl.cib.dl.CIBDLActionList;
import com.ibbl.cib.dl.CIBDLDataList;
import com.ibbl.cib.util.CIBUtil;
import com.ibbl.common.dao.CommonDL;
import com.ibbl.core.common.DataCarrier;
import com.ibbl.core.exception.BusinessException;
import com.ibbl.core.exception.SystemException;
import com.ibbl.data.model.SubjectData;
import com.ibbl.exception.PersistentLayerException;
import com.ibbl.inquiry.action.bean.InquirySearchBean;
import com.ibbl.inquiry.dao.InquiryDL;
import com.ibbl.inquiry.model.Inquiry;
import com.ibbl.inquiry.model.InquiryHistory;
import com.ibbl.inquiry.model.ReportDoc;
import com.ibbl.inquiry.util.ActionResult;
import com.ibbl.remote.bean.InquiryBean;
import com.ibbl.remote.bean.TransCarrier;
import com.ibbl.security.bl.co.SecurityGateCO;
import com.ibbl.security.ui.bean.LoginBean;
import com.ibbl.util.CIBDictionary;
import com.ibbl.util.DataKey;
import com.ibbl.util.FinalData;
import com.ibbl.util.WebDictionary;
import com.ibbl.util.enums.InquiryStatus;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import ibbl.cib.remote.bean.RemoteTrResultBean;
import ibbl.investment.common.util.IInvActionDataList;
import ibbl.remote.connection.hibernate.SchemaFactory;
import ibbl.remote.tx.TxController;
import org.apache.struts.action.ActionMessage;

import java.io.*;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p>
 * Original author: Ayatullah Khomeni
 * Date: December 8, 2015(11:19:15 AM)
 * Last modification by: $Author: ayat $
 * Last modification on $Date: 2017/11/12 10:06:40 $
 * Current revision: $Revision: 1.9 $
 * <p>
 * Revision History:
 * ------------------
 */
public class InquiryBL extends SecurityGateCO implements IInvActionDataList, DataKey {
    private int txID;

    public String getNewInquiryNo(String brCode) throws Exception {
        TxController txCO = TxController.createInstance();
        try {
            txID = txCO.initPersistence();
            InquiryDL commonDL = new InquiryDL(txID);
            String maxNo = commonDL.getNewInquiryNo(brCode);
            txCO.rollbackPersistence(txID);
            return maxNo;
        } catch (PersistentLayerException e) {
            try {
                txCO.rollbackPersistence(txID);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return null;
        }
    }

    public DataCarrier updateStatus(DataCarrier reqCarrier) throws SystemException, BusinessException {
        int actionKey = reqCarrier.getActionKey();
        DataCarrier resCarrier = new DataCarrier(actionKey);
        LoginBean loginBean = (LoginBean) reqCarrier.retrieveData(CIBBLDataList.DATA_LOGIN);

        Inquiry inquiry = (Inquiry) reqCarrier.retrieveData(CIBBLDataList.DATA_INQUIRY_BEAN);
        TxController txController = TxController.createInstance();

        try {
            txID = txController.initPersistence();
        } catch (PersistentLayerException e) {
            throw new SystemException("System failed", e.getMessage());  
        }
        try {
            CommonDL commonDL = new CommonDL(txID);
            commonDL.update(inquiry);
            InquiryHistory history = CIBUtil.createInquiryHistory(inquiry, loginBean);
            commonDL.save(history);

            txController.commitPersistence(txID);
            resCarrier.addSuccess();
            resCarrier.addData(CIBBLDataList.DATA_INQUIRY_BEAN, inquiry);
        } catch (Exception e) {
            try {
                txController.rollbackPersistence(txID);
            } catch (PersistentLayerException e1) {
                resCarrier.addError(NotifyDictionary.ERROR_DB, new String[]{"Database Failed"});  
            }
            resCarrier.addError(NotifyDictionary.ERROR_SYSTEM, new String[]{});
        }

        return resCarrier;
    }


    @SuppressWarnings("unchecked")
    public DataCarrier getInquiryInfo(String inqOid) throws SystemException, BusinessException {
        DataCarrier resCarrier = new DataCarrier(0);

        TxController localTxCO = TxController.createInstance();

        try {
            txID = localTxCO.initPersistence();
        } catch (PersistentLayerException e) {
            throw new SystemException("System failed", e.getMessage());
        }
        Inquiry inquiry;
        CommonDL commonDL;
        try {
            commonDL = new CommonDL(txID);
            inquiry = commonDL.get(Inquiry.class, inqOid);

            if (inquiry == null) {
                resCarrier.addError(NotifyDictionary.ERROR_DB, new String[]{"No inquiry Found !"});
                return resCarrier;
            }

            List<InquiryHistory> historyList = commonDL.findAll(InquiryHistory.class, "inquiry", inquiry);
            inquiry.setHistorySet(new HashSet<>(historyList));

            List<ReportDoc> docList = commonDL.findAll(ReportDoc.class, "inquiry", inquiry);
            inquiry.setDocSet(new HashSet<>(docList));

            localTxCO.commitPersistence(txID);
            resCarrier.addSuccess();
            resCarrier.addData(CIBBLDataList.DATA_INQUIRY_BEAN, inquiry);
        } catch (Exception e) {
            try {
                localTxCO.rollbackPersistence(txID);
            } catch (PersistentLayerException e1) {
                resCarrier.addError(NotifyDictionary.ERROR_DB, new String[]{"Database Failed"});
            }
            resCarrier.addError(NotifyDictionary.ERROR_SYSTEM, new String[]{});
            return resCarrier;
        }


        /**Pulling Data from CBS*/
        SubDataGroup sdg = new SubDataGroup();
        List<SubjectData> subjectDataList = sdg.getSubjectDataGroup(inquiry.getBrCode(), inquiry.getCustId());
        resCarrier.addData(WebDictionary.DATA_SUBJECT_DATA_BEAN_LIST, subjectDataList);

        resCarrier.addSuccess();
        resCarrier.addData(CIBBLDataList.DATA_INQUIRY_BEAN, inquiry);

        return resCarrier;
    }

    /**
     * CIB Inquiry Charge Debit
     */
    public ActionResult charge(DataCarrier reqCarrier) throws SystemException, BusinessException {
        LoginBean loginBean = (LoginBean) reqCarrier.retrieveData(CIBBLDataList.DATA_LOGIN);
        String ipAddress = (String) reqCarrier.retrieveData("ipAddress");
        ActionResult actionResult = new ActionResult(false);

        if (CIBDictionary.CIB_CONFIG_STATE.equals(FinalData.CIB_CONFIG_STATE_TEST)) {
            RemoteTrResultBean resultBeanFF = new RemoteTrResultBean();
            resultBeanFF.setSuccess(true);
            resultBeanFF.setCrTrID("20170000000000");
            resultBeanFF.setDrTrID("20170000000000");
            resultBeanFF.setMessage("TEST Charge has been debited from Customer Account (20502131001326712).<br/>Dr Transaction ID: 20170918000003<br/>Cr Transaction ID: 20170918000004<br/>");

            actionResult.setSuccess(true);
            actionResult.setDataObject(resultBeanFF);
            return actionResult;
        }

        Inquiry inquiry = (Inquiry) reqCarrier.retrieveData(CIBBLDataList.DATA_INQUIRY_BEAN);
        try {

            XStream xStream = new XStream(new DomDriver("UTF-8"));
            Class[] aliasClasses = new Class[]{
                    TransCarrier.class, InquiryBean.class, RemoteTrResultBean.class
            };
            xStream.processAnnotations(aliasClasses);
            byte[] bytes = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>".getBytes();

            OutputStream stream = new ByteArrayOutputStream();
            try {
                stream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
                actionResult.setMsg("Sorry Can't be charged right now || " + e.getMessage());
                actionResult.setException(e);
                return actionResult;
            }

            TransCarrier reqBean = new TransCarrier();
            reqBean.setInquiryBeanList(CIBUtil.toInquiryBeanList(Arrays.asList(inquiry))); // Can be sent list of IR to charge

            reqBean.setRemoteUserId(loginBean.getUserID());
            reqBean.setRemotePassword(CIBDictionary.REMOTE_CHARGE_PASSWORD);
            reqBean.setRemoteSecretKey(CIBDictionary.REMOTE_CHARGE_SECRET_KEY);
            reqBean.setActionKey("cib.to.br.cib.charge");

            String XML = xStream.toXML(reqBean);
            XML = XML.replaceAll("(\r\n|\n)", "");
            XML = XML.replaceAll("(\r|\n)", "");


            URLConnection eibsConnection;
            URL url;
            BufferedReader reader;
            String respondedXML;

            try {
                url = new URL(CIBDictionary.DEPOSIT_URL + "/remoteRequestFromCIB.do");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                actionResult.setMsg("Unable to connect eIBS for Invalid URL.");
                actionResult.setException(e);
                return actionResult;
            }
            try {
                eibsConnection = url.openConnection();
                eibsConnection.setConnectTimeout(600000);

                eibsConnection.setDoOutput(true);
                PrintWriter writer;
                writer = new PrintWriter(eibsConnection.getOutputStream());
                writer.print("remoteMessage" + "=" + java.net.URLEncoder.encode(XML));
                writer.close();

                /*Receiving response*/
                reader = new BufferedReader(new InputStreamReader(eibsConnection.getInputStream()));
                StringBuilder respondedString = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    respondedString.append(line);
                }
                reader.close();
                respondedXML = respondedString.toString();
                RemoteTrResultBean remoteTrResultBean = (RemoteTrResultBean) xStream.fromXML(respondedXML);

                System.out.println(remoteTrResultBean.getMessage());
                actionResult.setSuccess(true);
                actionResult.setDataObject(remoteTrResultBean);
                return actionResult;
            } catch (ConnectException e) {
                e.printStackTrace();
                actionResult.setMsg("Unable to connect eIBS(Deposit)");
                actionResult.setException(e);
                return actionResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
            actionResult.setMsg("Unable to connect eIBS(Deposit)");
            actionResult.setException(e);
            return actionResult;

        }
    }

    @SuppressWarnings("unchecked")
    public DataCarrier upload(Inquiry inquiryBean, LoginBean loginBean) throws SystemException, BusinessException {

        DataCarrier resCarrier = new DataCarrier(0);

        TxController txController = TxController.createInstance();
        try {
            txID = txController.initPersistence();
        } catch (PersistentLayerException e) {
            throw new SystemException("System failed", e.getMessage());
        }

        try {
            // Write doc (if exists) and return Bean with filtered doc set.
            ActionResult result = DocManagerBL.writeDocs(inquiryBean);
            if (result.isSuccess()) {
                Set<ReportDoc> validDocs = (Set<ReportDoc>) result.getDataObject();
                inquiryBean.setDocSet(validDocs);

                DataCarrier checkRetCarrier = new DataCarrier(CIBDLActionList.ACTION_REPORT_ON_INQUIRY);
                InquiryDL inquiryDL = new InquiryDL(txID);
                CommonDL commonDL = new CommonDL(txID);

                Inquiry inquiry = inquiryDL.uploadInquiryDocument(inquiryBean, loginBean);
                if (inquiry != null) {
                    checkRetCarrier.addSuccess();
                    checkRetCarrier.addData(CIBDLDataList.DATA_INQUIRY_BEAN, inquiry);
                } else {
                    checkRetCarrier.addWarning("Inquiry", new String[]{});
                }

                commonDL.update(inquiry);

                InquiryHistory history = com.ibbl.cib.util.CIBUtil.createInquiryHistory(inquiry, loginBean);
                commonDL.save(history);

                txController.commitPersistence(txID);
                resCarrier.addSuccess();
                resCarrier.addData(CIBBLDataList.DATA_INQUIRY_BEAN, inquiry);
                resCarrier.addData("file_validation_action_result", result);
            } else {
                resCarrier.addData("file_validation_action_result", result);
                resCarrier.addData(CIBBLDataList.DATA_INQUIRY_BEAN, inquiryBean);
                txController.rollbackPersistence(txID);
            }
        } catch (Exception e) {
            try {
                txController.rollbackPersistence(txID);
            } catch (PersistentLayerException e1) {
                resCarrier.addError(NotifyDictionary.ERROR_DB, new String[]{});  
            }
            resCarrier.addError(NotifyDictionary.ERROR_SYSTEM, new String[]{});
        }


        return resCarrier;
    }


    /**
     * This method returns "FILTERED" "Inquiry List" to the operator
     * based on which type are they.
     * 1. Type A : <code>CIBDictionary.CIB_USER_SUPER</code> - will get all the "Inquiry LIst"
     * 2. Type B : <code>CIBDictionary.CIB_USER_GENERAL</code> - will get those "Inquiry", that assigned to him
     * 3. Type C : <b>Branch Admin</b> will get the list of "Inquiry" of charged branch only.
     * 4. Type D : <b>Branch CIB Operator</b> will get as "Type C"
     *
     * @param searchBean InquirySearchBean
     * @return DataCarrier
     * @throws com.ibbl.core.exception.BusinessException
     * @throws com.ibbl.core.exception.SystemException
     */
    public List<Inquiry> getInquiryList(InquirySearchBean searchBean) throws BusinessException, SystemException {
        TxController txController = TxController.createInstance();
        List<Inquiry> list;
        try {
            txID = txController.initPersistence();
            InquiryDL inquiryDL = new InquiryDL(txID);

            LoginBean loginBean = searchBean.getLoginBean();
            boolean isCibOperator = loginBean.isCibOperator();

            Integer start = searchBean.getStart();
            Integer limit = searchBean.getLimit();
            String brCode = searchBean.getBrCode();
            if (start != 0 || limit != 0) {
                if (isCibOperator) {
                    list = inquiryDL.getInquiryList(start, limit, null);
                } else {
                    list = inquiryDL.getInquiryList(start, limit, brCode);
                }
            } else {
                searchBean = toUserSearchBean(searchBean);
                list = inquiryDL.getInquiryListBySearch(searchBean);
            }

            txController.rollbackPersistence(txID);
            return list;

        } catch (Exception e) {
            try {
                txController.rollbackPersistence(txID);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            throw new SystemException("Parse Exception", e.getMessage());
        }
    }

    public Map<InquiryStatus, Integer> getInquiryCountList(InquirySearchBean searchBean) throws BusinessException, SystemException {

        TxController txController = TxController.createInstance();
        List<Object[]> dataList;
        try {
            txID = txController.initPersistence();

            searchBean = toUserSearchBean(searchBean);
            if (searchBean != null) {
                InquiryDL inquiryDL = new InquiryDL(txID);
                dataList = inquiryDL.getInquiryCountListBySearch(searchBean);
            } else {
                dataList = new ArrayList<>(0);
            }
            txController.rollbackPersistence(txID);
        } catch (Exception e) {
            try {
                txController.rollbackPersistence(txID);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            throw new SystemException("Parse Exception", e.getMessage());
        }

        Map<InquiryStatus, Integer> map = new HashMap<>();
        for (Object[] os : dataList) {
            Integer status = (Integer) os[0];
            Integer count = ((Long) os[1]).intValue();
            map.put(InquiryStatus.get(status), count);
        }

        return map;
    }

    /**
     * This method is to convert normal InquirySearchBean to customized search bean
     * so that the CIB Operator and Branch Operator can search Inquiry to their won area.
     *
     * @param searchBean InquirySearchBean
     * @return InquirySearchBean
     */
    private InquirySearchBean toUserSearchBean(InquirySearchBean searchBean) {
        try {
            LoginBean loginBean = searchBean.getLoginBean();
            boolean isCibOperator = loginBean.isCibOperator();
            String operatorType = loginBean.getOperatorType();
            if (isCibOperator) {
                searchBean.setBrCode(null);
                if (CIBDictionary.CIB_USER_SUPER.equals(operatorType)) {
                    searchBean.setEnlistedBrList(null);
                }
            } else {
                searchBean.setBrCode(loginBean.getBrCode());
                searchBean.setEnlistedBrList(null);
            }
            return searchBean;
        } catch (Exception e) {
            return null;
        }
    }


}
