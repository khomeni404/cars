package com.mbl.inquiry.action;

import com.google.common.io.Files;
import com.ibbl.cib.bl.CIBBLActionList;
import com.ibbl.cib.bl.CIBBLDataList;
import com.ibbl.cib.common.util.*;
import com.ibbl.common.biz.CommonBL;
import com.ibbl.common.model.deposit.AccountPO;
import com.ibbl.common.model.deposit.CustomerPO;
import com.ibbl.core.common.DataCarrier;
import com.ibbl.core.exception.BusinessException;
import com.ibbl.core.exception.SystemException;
import com.ibbl.data.model.SubjectData;
import com.ibbl.inquiry.action.bean.InquirySearchBean;
import com.ibbl.inquiry.action.form.InquiryChargeForm;
import com.ibbl.inquiry.action.form.InquiryForm;
import com.ibbl.inquiry.action.form.InquirySearchForm;
import com.ibbl.inquiry.action.form.ReportDocForm;
import com.ibbl.inquiry.biz.InquiryBL;
import com.ibbl.inquiry.biz.SubDataGroup;
import com.ibbl.inquiry.model.Inquiry;
import com.ibbl.inquiry.model.InquiryHistory;
import com.ibbl.inquiry.model.ReportDoc;
import com.ibbl.inquiry.util.ActionResult;
import com.ibbl.security.bl.co.SecurityGateCO;
import com.ibbl.security.ui.bean.LoginBean;
import com.ibbl.util.*;
import com.ibbl.util.CIBUtil;
import com.ibbl.util.enums.InquiryStatus;
import com.ibbl.util.enums.MimeType;
import ibbl.cib.remote.bean.RemoteTrResultBean;
import ibbl.core.util.IDGenerator;
import ibbl.deposit.common.bl.CustomerManagerBL;
import ibbl.deposit.common.util.IWebDataList;
import ibbl.deposit.common.util.codeformat.CustNoFactory;
import ibbl.deposit.common.util.codeformat.ICustNo;
import ibbl.remote.service.EibsService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.*;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p>
 * Original author: Ayatullah Khomeni
 * <br/>
 * Date: December 8, 2015(11:19:15 AM)
 * Last modification by: $Author: ayat $
 * Last modification on $Date: 2017/12/20 05:46:57 $
 * Current revision: $Revision: 1.18 $
 * </p>
 * Revision History:
 * ------------------
 */

public class CibInquiryAction extends DispatchAction implements CIBBLActionList {
    public static final String ERROR = CIBBLDataList.FORWARD_ERROR;
    public static final String SUCCESS = CIBBLDataList.FORWARD_SUCCESS;
    public static final String CANCEL = CIBBLDataList.FORWARD_CANCEL;
    public static final String ERROR_TITLE = CIBBLDataList.ERROR_TITLE;
    public static final String dataLoginBean = CIBBLDataList.DATA_LOGIN;
    public static final String dataKeyInquiryBean = CIBBLDataList.DATA_INQUIRY_BEAN;

    public static final String ACTION_METHOD_CREATE = "create";

    public ActionForward create(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) {
        HttpSession session = request.getSession();
        // TODO.. implement RectifiedSubjectBL for getting Customer Info
        ActionMessages errors = new ActionMessages();
        try {
            LoginBean loginBean = SessionUtil.getSessionUser(session);
            DataCarrier reqCarrier = new DataCarrier(CIBBLActionList.ACTION_INQUIRY_CREATE);
            reqCarrier.addData(dataLoginBean, loginBean);
            SecurityGateCO.verifyAccess(reqCarrier);

            session.setAttribute(WebDictionary.BR_CODE, loginBean.getBrCode());
            session.setAttribute(WebDictionary.SYS_YEAR, (new SimpleDateFormat("yy").format(new Date())));
            return actionMapping.findForward("success");

        } catch (Exception e) {
            errors.add(ERROR_TITLE, new ActionMessage("errors.detail", CIBUtil.getMessage(e)));
            saveErrors(request, errors);
            return actionMapping.findForward(ERROR);
        }
    }


    public static final String ACTION_METHOD_SAVE = "save";

    public ActionForward save(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) {
        HttpSession session = request.getSession();
        LoginBean loginBean = SessionUtil.getSessionUser(session);

        DataCarrier req = new DataCarrier(ACTION_INQUIRY_SAVE_AND_UPDATE);
        req.addData(dataLoginBean, loginBean);
        ActionMessages errors = new ActionMessages();
        try {
            SecurityGateCO.verifyAccess(req);

            String ctx = request.getContextPath();
            InquiryForm inquiryForm = (InquiryForm) actionForm;

            // isValid(CustomerID) ?
            CustomerManagerBL managerBL = new CustomerManagerBL();
            String coreCustID = inquiryForm.getCustId();
            if (!managerBL.verifyCustID(coreCustID)) {
                errors.add(ERROR_TITLE, new ActionMessage("cib.warning.customer.id.invalid", inquiryForm.getCustId()));
                saveErrors(request, errors);
                return actionMapping.findForward(ERROR);
            }
            // isValid(BR) ?
            ICustNo custNoObj = CustNoFactory.getCustNoObject();
            String custBrCode = custNoObj.getBranchCode(inquiryForm.getCustId());
            if (!custBrCode.equals(loginBean.getBrCode())) {
                errors.add(ERROR_TITLE, new ActionMessage("cib.warning.other.br.user", new String[]{loginBean.getBrCode(), custBrCode}));
                saveErrors(request, errors);
                return actionMapping.findForward(ERROR);
            }

            EibsService eibs = EibsService.init(custBrCode);
            CustomerPO coreCustomer = eibs.get(CustomerPO.class, "custID", coreCustID);
            if (coreCustomer == null) {
                errors.add(ERROR_TITLE, new ActionMessage("errors.detail", "No core customer found !"));
                saveErrors(request, errors);
                return actionMapping.findForward(ERROR);
            }

            // TODO... isValid(Customer) ?

            if (isCancelled(request)) {
                inquiryForm.reset(actionMapping, request);
                return actionMapping.findForward(CANCEL);
            }
            ibbl.core.util.IDGenerator idGenerator = new ibbl.core.util.IDGenerator();

            Inquiry inquiry = inquiryForm.toModelBean();
            inquiry.setDataOperator(loginBean.getUserID());
            inquiry.setOid(String.valueOf(idGenerator.generate()));
            req.addData(dataKeyInquiryBean, inquiry);
            CommonBL commonBL = new CommonBL();
            InquiryBL inquiryBL = new InquiryBL();
            String newInquiryNo = inquiryBL.getNewInquiryNo(inquiry.getBrCode());
            inquiry.setInqNo(newInquiryNo);

            inquiry.setCustName(coreCustomer.getCustName());
            boolean saved = commonBL.save(inquiry);

            if (saved) {
                InquiryHistory history = com.ibbl.cib.util.CIBUtil.createInquiryHistory(inquiry, loginBean);
                commonBL.save(history);
                session.setAttribute(WebDictionary.DATA_SUCCESS_MSG,
                        CIBUtil.generateForwardMsg("Inquiry (" + inquiry.getInqNo() + ") " + InquiryStatus.CREATED.name() + " successfully. Upload Undertaking before Forward.", "View Inquiry", ctx + "/viewInquiry.do?action=" + ACTION_METHOD_VIEW + "&oid=" + inquiry.getOid()));

                return actionMapping.findForward(SUCCESS);
            } else {
                errors.add(ERROR_TITLE, new ActionMessage("Inquiry Can't be saved !"));
                saveErrors(request, errors);
                return actionMapping.findForward(ERROR);
            }
        } catch (BusinessException e) {
            errors.add(ERROR_TITLE, new ActionMessage(e.getMessage(), e.getParams()));
            saveErrors(request, errors);
            return actionMapping.findForward(ERROR);
        } catch (SystemException e) {
            errors.add(ERROR_TITLE, new ActionMessage(e.getMessage(), e.getParams()));
            saveErrors(request, errors);
            return actionMapping.findForward(ERROR);
        } catch (Exception e) {
            errors.add(ERROR_TITLE, new ActionMessage(e.getMessage()));
            saveErrors(request, errors);
            return actionMapping.findForward(ERROR);
        }
    }


    public static final String ACTION_METHOD_SEARCH = "search";

    public ActionForward search(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        HttpSession session = httpServletRequest.getSession();
        InquirySearchForm searchForm = (InquirySearchForm) actionForm;
        if (isCancelled(httpServletRequest)) {
            searchForm.reset(actionMapping, httpServletRequest);
            return actionMapping.findForward(CANCEL);
        }

        ActionMessages errors = new ActionMessages();
        try {
            LoginBean loginBean = SessionUtil.getSessionUser(session);
            InquirySearchBean searchBean = new InquirySearchBean();
            searchBean.setBrCode(loginBean.getBrCode());
            searchBean.setLoginBean(loginBean);
            //Date today = new Date();
            /*
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            searchBean.setFromDate(sdf.parse("01/09/2017"));
            searchBean.setToDate(sdf.parse("30/09/2017"));
            */

            searchBean.setFromDate(com.ibbl.util.DateUtil.getFirstDayOfMonth());
            searchBean.setToDate(com.ibbl.util.DateUtil.getLastDayOfMonth());

            if (loginBean.isCibOperator()) {
                String enlistedBrc = (String) session.getAttribute(CIBBLDataList.DATA_ENLISTED_BRANCH_LIST);
                if (!GenericValidator.isBlankOrNull(enlistedBrc)) {
                    searchBean.setEnlistedBrList(Arrays.asList(enlistedBrc.split(",")));
                }
            }

            InquiryBL bl = new InquiryBL();
            Map<InquiryStatus, Integer> data = bl.getInquiryCountList(searchBean);
            session.setAttribute("data", data);

            return actionMapping.findForward("search_home");
        } catch (Exception e) {
            errors.add(ERROR_TITLE, new ActionMessage("errors.detail", CIBUtil.getMessage(e)));
            saveErrors(httpServletRequest, errors);
            return actionMapping.findForward(ERROR);
        }
    }


    public static final String ACTION_METHOD_LIST = "list";

    public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        HttpSession session = httpServletRequest.getSession();
        InquirySearchForm searchForm = (InquirySearchForm) actionForm;
        if (isCancelled(httpServletRequest)) {
            searchForm.reset(actionMapping, httpServletRequest);
            return actionMapping.findForward(CANCEL);
        }
        ActionMessages errors = new ActionMessages();

        try {
            LoginBean loginBean = SessionUtil.getSessionUser(session);
            InquirySearchBean searchBean = searchForm.toSearchBean();
            String searchStatus = searchBean.getInqStatus();
            searchForm.reset(actionMapping, httpServletRequest);
            searchBean.setLoginBean(loginBean);
            String brCode = loginBean.getBrCode();
            searchBean.setBrCode(brCode); // For safety
            DataCarrier reqCarrier = new DataCarrier(CIBBLActionList.ACTION_INQUIRY_LIST);
            reqCarrier.addData(dataLoginBean, loginBean);
            SecurityGateCO.verifyAccess(reqCarrier);

            if (loginBean.isCibOperator()) {
                String enlistedBrc = (String) session.getAttribute(CIBBLDataList.DATA_ENLISTED_BRANCH_LIST);
                if (!GenericValidator.isBlankOrNull(enlistedBrc)) {
                    searchBean.setEnlistedBrList(Arrays.asList(enlistedBrc.split(",")));
                }
            }

            InquiryBL bl = new InquiryBL();
            List<Inquiry> requestList = bl.getInquiryList(searchBean);

            session.setAttribute(WebDictionary.INQUIRY_BEAN_LIST, requestList);

            if (GenericValidator.isBlankOrNull(searchStatus)) {
                return actionMapping.findForward("list_all");
            } else {
                session.setAttribute("status", searchStatus);
                return actionMapping.findForward("list_by_status");
            }

        } catch (Exception e) {
            errors.add(ERROR_TITLE, new ActionMessage("errors.detail", CIBUtil.getMessage(e)));
            saveErrors(httpServletRequest, errors);
            return actionMapping.findForward(ERROR);
        }
    }

    public static final String ACTION_METHOD_VIEW = "view";

    public ActionForward view(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        HttpSession session = httpServletRequest.getSession();
        ActionMessages errors = new ActionMessages();

        LoginBean loginBean = SessionUtil.getSessionUser(session);
        DataCarrier reqCarrier = new DataCarrier(CIBBLActionList.ACTION_INQUIRY_VIEW);
        reqCarrier.addData(dataLoginBean, loginBean);
        try {
            SecurityGateCO.verifyAccess(reqCarrier);
        } catch (SystemException e) {
            errors.add(ERROR_TITLE, new ActionMessage(e.getMessage(), e.getParams()));
            saveErrors(httpServletRequest, errors);
            return actionMapping.findForward(ERROR);
        } catch (BusinessException e) {
            errors.add(ERROR_TITLE, new ActionMessage(e.getMessage(), e.getParams()));
            saveErrors(httpServletRequest, errors);
            return actionMapping.findForward(ERROR);
        }


        InquirySearchForm searchForm = (InquirySearchForm) actionForm;
        /**Part 1 : Pulling IR from native DB*/
        String oid = searchForm.getOid();
        try {
            InquiryBL inquiryBL = new InquiryBL();

            DataCarrier res = inquiryBL.getInquiryInfo(oid);

            Object inquiryBean = res.retrieveData(CIBBLDataList.DATA_INQUIRY_BEAN);
            Object subjectDataList = res.retrieveData(WebDictionary.DATA_SUBJECT_DATA_BEAN_LIST);

            session.setAttribute(WebDictionary.INQUIRY_BEAN, inquiryBean);
            session.setAttribute(WebDictionary.DATA_SUBJECT_DATA_BEAN_LIST, subjectDataList);

            return actionMapping.findForward("view");
        } catch (Exception e) {
            errors.add(ERROR_TITLE, new ActionMessage("errors.detail", "No Inquiry Found !"));
            saveErrors(httpServletRequest, errors);
            return actionMapping.findForward(ERROR);
        }

    }


    public static final String ACTION_METHOD_UPDATE = "update";

    public ActionForward update(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) {
        HttpSession session = request.getSession();
        String ctx = request.getContextPath();
        if (isCancelled(request)) {
            return actionMapping.findForward(CANCEL);
        }
        ActionMessages errors = new ActionMessages();
        LoginBean loginBean = SessionUtil.getSessionUser(session);

        CommonBL commonBL = new CommonBL();
        String oid = request.getParameter("inqOid");
        Inquiry inquiryBeanToUpdate = null;
        if (!GenericValidator.isBlankOrNull(oid)) {
            inquiryBeanToUpdate = commonBL.get(Inquiry.class, oid);
        }


        if (inquiryBeanToUpdate == null) {
            errors.add(ERROR_TITLE, new ActionMessage("errors.detail", "No Inquiry Found !"));
            saveErrors(request, errors);
            return actionMapping.findForward("error");
        }

        // Updating Start
        String updateKey = request.getParameter("updateKey");

        //Set<ReportDoc> docSet = (Set<ReportDoc>) session.getAttribute(WebDictionary.INQUIRY_DOCS);


        try {
            InquiryBL inquiryBL = new InquiryBL();
            String successMsg;
            DataCarrier reqCarrier = new DataCarrier(0);
            reqCarrier.addData(dataLoginBean, loginBean);


            DataCarrier resCarrier;

            // TODO... All 'history note' should be produced from here
            if (updateKey.equals(InquiryStatus.FORWARDED.STATUS)) {
                ReportDoc undertaking = commonBL.get(ReportDoc.class, "inquiry", inquiryBeanToUpdate);
                if (undertaking == null) {
                    errors.add(ERROR_TITLE, new ActionMessage("errors.detail", "You must attach <b>Undertaking</b> before Forward !"));
                    saveErrors(request, errors);
                    return actionMapping.findForward("error");
                }
                reqCarrier.setActionKey(CIBBLActionList.ACTION_INQUIRY_FORWARD);
                SecurityGateCO.verifyAccess(reqCarrier);

                inquiryBeanToUpdate.setDispatchedBy(loginBean.getUserID());
                if (inquiryBeanToUpdate.getStatus().equals(InquiryStatus.COMPLAINED.CODE)) {
                    inquiryBeanToUpdate.setNote("Objection Resolved. And, Inquiry has been " + InquiryStatus.FORWARDED.name() + " to CIB, Head Office again."); //Redundant
                } else {
                    inquiryBeanToUpdate.setNote(InquiryStatus.FORWARDED.HISTORY); //Redundant
                }
                inquiryBeanToUpdate.setStatus(InquiryStatus.FORWARDED.CODE);
                successMsg = "Inquiry " + InquiryStatus.FORWARDED.name() + " to CIB Head Office successfully.";
            } else if (updateKey.equals(InquiryStatus.PROCESSING.STATUS)) {
                reqCarrier.setActionKey(CIBBLActionList.ACTION_INQUIRY_PROCESS);
                SecurityGateCO.verifyAccess(reqCarrier);
                inquiryBeanToUpdate.setStatus(InquiryStatus.PROCESSING.CODE);
                inquiryBeanToUpdate.setNote(InquiryStatus.PROCESSING.HISTORY); //Redundant
                successMsg = "Inquiry status changed to \"Processing\".";
            } else if (updateKey.equals(InquiryStatus.COMPLAINED.STATUS)) {
                reqCarrier.setActionKey(CIBBLActionList.ACTION_INQUIRY_COMPLAIN);
                SecurityGateCO.verifyAccess(reqCarrier);

                String complainNote = request.getParameter("compNote");
                inquiryBeanToUpdate.setNote(complainNote);
                inquiryBeanToUpdate.setStatus(InquiryStatus.COMPLAINED.CODE);
                inquiryBeanToUpdate.setComplainedBy(loginBean.getUserID());
                successMsg = "Complained on \"Inquiry\" successfully.";
            } else if (updateKey.equals(CIBDictionary.CIB_UPDATE_KEY_RESOLVE)) {
                reqCarrier.setActionKey(CIBBLActionList.ACTION_INQUIRY_SAVE_AND_UPDATE);
                SecurityGateCO.verifyAccess(reqCarrier);
                if (inquiryBeanToUpdate.getStatus().equals(InquiryStatus.COMPLAINED.CODE)) {
                    inquiryBeanToUpdate.setNote("Objection Resolved. And, Inquiry has been " + InquiryStatus.FORWARDED.name() + " to CIB, Head Office again."); //Redundant
                } else {
                    inquiryBeanToUpdate.setNote(InquiryStatus.FORWARDED.HISTORY); //Redundant
                }
                inquiryBeanToUpdate.setStatus(InquiryStatus.FORWARDED.CODE);
                successMsg = "Inquiry has been send to 'CIB, HO' again for process.";
            } else if (updateKey.equals(InquiryStatus.CLOSED.STATUS)) {
                reqCarrier.setActionKey(CIBBLActionList.ACTION_INQUIRY_SAVE_AND_UPDATE);
                SecurityGateCO.verifyAccess(reqCarrier);

                String note = "The Inquiry Story has been Closed.";
                inquiryBeanToUpdate.setNote(note);
                inquiryBeanToUpdate.setStatus(InquiryStatus.CLOSED.CODE);
                successMsg = "The Inquiry Story has been Closed.";
            } else {
                session.setAttribute(WebDictionary.INQUIRY_BEAN, inquiryBeanToUpdate);
                errors.add(ERROR_TITLE, new ActionMessage("errors.invalid", "Operation"));
                saveErrors(request, errors);
                return actionMapping.findForward(ERROR);
            }

            inquiryBeanToUpdate.setLastUpdateDate(new Date());
            reqCarrier.addData(dataKeyInquiryBean, inquiryBeanToUpdate);

            resCarrier = inquiryBL.updateStatus(reqCarrier);
            if (resCarrier.isSuccess()) {
                Inquiry updatedRequestBean = (Inquiry) resCarrier.retrieveData(dataKeyInquiryBean);
                session.setAttribute(WebDictionary.DATA_SUCCESS_MSG,
                        CIBUtil.generateForwardMsg(successMsg, "View Inquiry", ctx + "/viewInquiry.do?action=" + ACTION_METHOD_VIEW + "&oid=" + updatedRequestBean.getOid()));
                return actionMapping.findForward(SUCCESS);
            } else {
                errors.add(ERROR_TITLE, new ActionMessage(resCarrier.getMsg().getMsg()));
                saveErrors(request, errors);
                //return actionMapping.findForward(ERROR);
            }
        } catch (BusinessException e) {
            session.setAttribute(WebDictionary.INQUIRY_BEAN, inquiryBeanToUpdate);
            errors.add(ERROR_TITLE, new ActionMessage(e.getMessage(), e.getParams()));
            saveErrors(request, errors);
        } catch (SystemException e) {
            errors.add(ERROR_TITLE, new ActionMessage(e.getMessage(), e.getParams()));
            saveErrors(request, errors);
        } catch (Exception e) {
            errors.add(ERROR_TITLE, new ActionMessage(e.getMessage()));
            saveErrors(request, errors);
        }
        return actionMapping.findForward(ERROR);
    }


    public static final String ACTION_METHOD_REJECT = "reject";

    public ActionForward reject(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) {
        HttpSession session = request.getSession();
        String ctx = request.getContextPath();
        if (isCancelled(request)) {
            return actionMapping.findForward(CANCEL);
        }
        ActionMessages errors = new ActionMessages();
        LoginBean loginBean = SessionUtil.getSessionUser(session);

        CommonBL commonBL = new CommonBL();
        Inquiry inquiry = null;
        String oid = request.getParameter("oid");
        if (!GenericValidator.isBlankOrNull(oid)) {
            inquiry = commonBL.get(Inquiry.class, oid);
        }

        if (inquiry == null) {
            errors.add(ERROR_TITLE, new ActionMessage("errors.detail", "No Inquiry Found !"));
            saveErrors(request, errors);
            return actionMapping.findForward("search");
        }

        try {
            String successMsg;

            inquiry.setStatus(InquiryStatus.REJECTED.CODE);
            inquiry.setNote("Inquiry has been Rejected.");
            inquiry.setFinalStatus(CIBDictionary.INQ_FINAL_STATUS_REJECTED);
            successMsg = "Inquiry REJECTED successfully.";
            inquiry.setLastUpdateDate(new Date());
            boolean updated = commonBL.update(inquiry);

            if (updated) {
                /*History*/
                IDGenerator idGenerator = new IDGenerator();
                InquiryHistory history = new InquiryHistory();
                history.setOid(String.valueOf(idGenerator.generate()));
                history.setRecordDate(new Date());
                history.setActive(1);
                history.setInqStatus(inquiry.getStatus());
                history.setHistoryNote("Inquiry has been Rejected.");
                history.setInquiry(inquiry);
                history.setOperator(loginBean.getUserID());

                boolean saved = commonBL.save(history);
                if (saved) {
                    session.setAttribute(WebDictionary.DATA_SUCCESS_MSG,
                            CIBUtil.generateForwardMsg(successMsg, "View Inquiry", ctx + "/viewInquiry.do?action=" + ACTION_METHOD_VIEW + "&oid=" + oid));
                    return actionMapping.findForward(SUCCESS);
                } else {
                    errors.add(ERROR_TITLE, new ActionMessage("errors.detail", "Inquiry Rejected but no history generated !"));
                    saveErrors(request, errors);
                    return actionMapping.findForward("search");
                }
            } else {
                errors.add(ERROR_TITLE, new ActionMessage("errors.detail", "Inquiry can't be Rejected !"));
                saveErrors(request, errors);
                return actionMapping.findForward("search");
            }
        } catch (Exception e) {
            errors.add(ERROR_TITLE, new ActionMessage(e.getMessage()));
            saveErrors(request, errors);
        }
        return actionMapping.findForward(ERROR);
    }

    public static final String ACTION_METHOD_ADD = "add";

    public ActionForward add(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
        ReportDocForm form = (ReportDocForm) actionForm;
        HttpSession session = request.getSession(false);
        session.setAttribute(DataKey.CIB_REPORT_DOC_FORM, form);
        return actionMapping.findForward("add_more");
    }

    public static final String ACTION_METHOD_ATTACH = "attach";

    public ActionForward attach(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
        ReportDocForm form = (ReportDocForm) actionForm;
        Set<ReportDoc> reqDocsNew = new HashSet<>(1);
        HttpSession session = request.getSession(false);

        if (isCancelled(request)) {
            form.reset(actionMapping, request);
            return actionMapping.findForward(IWebDataList.FORWARD_CANCEL);
        }
        ActionMessages msg = form.validate(actionMapping, request);
        if (msg.size() > 0) {
            saveErrors(request, msg);
            return actionMapping.findForward(IWebDataList.FORWARD_ERROR);
        }
        LoginBean loginBean = SessionUtil.getSessionUser(session);
        try {
            ActionMessages errors = new ActionErrors();
            FormFile formFile = form.getFormFile();
            String ext;
            int fileSizeKB;
            String fileName = formFile.getFileName();
            try {
                ext = Files.getFileExtension(fileName);// fileName.substring(fileName.indexOf(".") + 1);
                fileSizeKB = formFile.getFileData().length;
                if (!CIBDictionary.CIB_RPT_ALLOWED_FILE_TYPE_LIST.contains(ext)) {
                    errors.add(ERROR_TITLE, new ActionMessage("info.file.type.allowed", CIBDictionary.CIB_RPT_ALLOWED_FILE_TYPE));
                    saveErrors(request, errors);
                    return actionMapping.findForward(ERROR);
                } else if (fileSizeKB == 0) {
                    errors.add(ERROR_TITLE, new ActionMessage("errors.detail", "Document has Empty Size. (" + fileName + ")"));
                    saveErrors(request, errors);
                    return actionMapping.findForward(ERROR);
                } else if (fileSizeKB > (CIBDictionary.CIB_RPT_ALLOWED_FILE_SIZE * 1000000)) {
                    errors.add(ERROR_TITLE, new ActionMessage("errors.detail", "Maximum file size is " + CIBDictionary.CIB_RPT_ALLOWED_FILE_SIZE + " MB"));
                    saveErrors(request, errors);
                    return actionMapping.findForward(ERROR);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }


            Inquiry inquiry = (Inquiry) session.getAttribute(WebDictionary.INQUIRY_BEAN);
            session.removeAttribute(WebDictionary.INQUIRY_BEAN);
            @SuppressWarnings("unchecked")
            Set<ReportDoc> reqDocs = (Set<ReportDoc>) session.getAttribute(WebDictionary.INQUIRY_DOCS);

            ReportDoc docBean = new ReportDoc();
            docBean.setFileName(formFile.getFileName());
            docBean.setDocStream(formFile.getFileData());
            docBean.setDocName(form.getDocName());
            docBean.setInquiry(inquiry);
            docBean.setUploadDate(new Date());
            docBean.setOperator(loginBean.getUserID());
            if (GenericValidator.isBlankOrNull(form.getNote())) {
                docBean.setNote(Files.getNameWithoutExtension(fileName));
            } else {
                docBean.setNote(form.getNote());
            }

            if (reqDocs == null) {
                reqDocsNew.add(docBean);
            } else {
                reqDocsNew.addAll(reqDocs);
                reqDocsNew.add(docBean);
            }
            session.setAttribute(WebDictionary.INQUIRY_BEAN, inquiry);
            session.setAttribute(WebDictionary.INQUIRY_DOCS, reqDocsNew);
            // load a fresh form to load new doc
            //form.reset(actionMapping, request);
            //form.setInqOid(inqOid);

            return actionMapping.findForward("add_more");
            //return actionMapping.findForward(IWebDataList.FORWARD_SUCCESS);
        } catch (Exception e) {
            // Remove file in case of ERROR
            // TODO .... Remove files
            //LawUtil.removeFile(fileList);
            msg.add(ERROR_TITLE, new ActionMessage(e.getMessage()));
            saveErrors(request, msg);
            return actionMapping.findForward(IWebDataList.FORWARD_ERROR);
        }

    }

    public static final String ACTION_METHOD_UPLOAD = "upload";

    public ActionForward upload(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) {
        HttpSession session = request.getSession();
        ActionMessages errors = new ActionMessages();
        ReportDocForm form = (ReportDocForm) actionForm;
        session.setAttribute(DataKey.CIB_REPORT_DOC_FORM, form);
        String ctx = request.getContextPath();
        if (isCancelled(request)) {
            return actionMapping.findForward(CANCEL);
        }
        LoginBean loginBean = SessionUtil.getSessionUser(session);
        String docName = form.getDocName();
//        Inquiry inquiryBean = (Inquiry) session.getAttribute(WebDictionary.INQUIRY_BEAN);
        CommonBL commonBL = new CommonBL();
        String inquiryOid = form.getInqOid();
        Inquiry inquiryBeanToUpdate = commonBL.get(Inquiry.class, inquiryOid);

        @SuppressWarnings("unchecked")
        Set<ReportDoc> docSet = new HashSet<>();// (Set<ReportDoc>) session.getAttribute(WebDictionary.INQUIRY_DOCS);

        List<FormFile> formFiles = form.getFormFiles();
        for (FormFile formFile : formFiles) {
            try {
                String fileName = formFile.getFileName();
                ReportDoc docBean = new ReportDoc();
                docBean.setFileName(formFile.getFileName());
                docBean.setDocStream(formFile.getFileData());
                docBean.setDocName(form.getDocName());
                docBean.setInquiry(inquiryBeanToUpdate);
                docBean.setUploadDate(new Date());
                docBean.setOperator(loginBean.getUserID());
                if (GenericValidator.isBlankOrNull(form.getNote())) {
                    docBean.setNote(Files.getNameWithoutExtension(fileName));
                } else {
                    docBean.setNote(form.getNote());
                }
                docSet.add(docBean);
            } catch (IOException e) {

            }

        }


        if (CollectionUtils.isEmpty(docSet)) {
            errors.add(ERROR_TITLE, new ActionMessage("errors.detail", new String[]{"No Document Found !"}));
            saveErrors(request, errors);
            return actionMapping.findForward("error");
        }
        try {

            // BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
            // BeanUtils.copyProperties(inquiryBeanToUpdate, inquiryBean);
            inquiryBeanToUpdate.setDocSet(docSet);
            session.removeAttribute(WebDictionary.INQUIRY_DOCS);
            inquiryBeanToUpdate.setLastUpdateDate(new Date());

            //String updateKey = request.getParameter("updateKey");
            DataCarrier reqCarrier;
            if (CIBDictionary.CIB_DOC_NAME_UNDERTAKING.equals(docName)) {
                String note = "Customer Undertaking has been uploaded for review.";
                inquiryBeanToUpdate.setNote(note);
                //Integer status = inquiryBeanToUpdate.getInqStatus();
                //if(status != null && status)
                //inquiryBeanToUpdate.setInqStatus(InquiryStatus.REQUESTED.CODE);
                reqCarrier = new DataCarrier(CIBBLActionList.ACTION_INQUIRY_UNDERTAKE_UPLOAD);
            } else if (CIBDictionary.CIB_DOC_NAME_CIB_REPORT.equals(docName)) {
                inquiryBeanToUpdate.setStatus(InquiryStatus.REPORTED.CODE);    // "14" is for "Docking & Reporting"
                reqCarrier = new DataCarrier(CIBBLActionList.ACTION_INQUIRY_CIB_RPT_UPLOAD);
                inquiryBeanToUpdate.setReportedBy(loginBean.getUserID());
                inquiryBeanToUpdate.setReportDate(new Date());
                inquiryBeanToUpdate.setFinalStatus(CIBDictionary.INQ_FINAL_STATUS_REPORTED);
            } else {
                errors.add(ERROR_TITLE, new ActionMessage("errors.detail", "No Update Key Found !"));
                saveErrors(request, errors);
                return actionMapping.findForward("error");
            }
            reqCarrier.addData(dataLoginBean, loginBean);
            reqCarrier.addData(dataKeyInquiryBean, inquiryBeanToUpdate);
            SecurityGateCO.verifyAccess(reqCarrier);
            InquiryBL bl = new InquiryBL();
            DataCarrier resCarrier = bl.upload(inquiryBeanToUpdate, loginBean);
            if (resCarrier.isSuccess()) {
                ActionResult result = (ActionResult) resCarrier.retrieveData("file_validation_action_result");
                if (!result.isSuccess()) {
                    errors.add(ERROR_TITLE, new ActionMessage("errors.detail", result.getMsg()));
                    saveErrors(request, errors);
                    return actionMapping.findForward("error");
                }
                session.setAttribute("file_validation_action_result", resCarrier.retrieveData("file_validation_action_result"));
                session.setAttribute(WebDictionary.DATA_SUCCESS_MSG,
                        CIBUtil.generateForwardMsg("DOCUMENT'S Uploaded successfully.", "View Inquiry", ctx + "/viewInquiry.do?action=" + ACTION_METHOD_VIEW + "&oid=" + inquiryOid));
                return actionMapping.findForward("success");
            } else {
                errors.add(ERROR_TITLE, new ActionMessage(resCarrier.getMsg().getMsg()));
                saveErrors(request, errors);
                return actionMapping.findForward(ERROR);
            }
        } catch (BusinessException e) {
            errors.add(ERROR_TITLE, new ActionMessage(e.getMessage(), e.getParams()));
            saveErrors(request, errors);
            return actionMapping.findForward(ERROR);
        } catch (SystemException e) {
            errors.add(ERROR_TITLE, new ActionMessage(e.getMessage(), e.getParams()));
            saveErrors(request, errors);
            return actionMapping.findForward(ERROR);
        } catch (Exception e) {
            errors.add(ERROR_TITLE, new ActionMessage(e.getMessage()));
            saveErrors(request, errors);
            return actionMapping.findForward(ERROR);
        }
    }

    public static final String ACTION_METHOD_BALANCE = "balance";

    public ActionForward balance(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String inqOid = request.getParameter("inqOid");
        CommonBL commonBL = new CommonBL();
        Inquiry inquiry = commonBL.get(Inquiry.class, inqOid);
        List<AccountPO> accountPOList = commonBL.getDepositAcBeanList(inquiry.getBrCode(), inquiry.getCustId());
        session.setAttribute("acc_list", accountPOList);
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (GenericValidator.isBlankOrNull(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        System.out.println("ipAddress = " + ipAddress);
        AccountPO proposedAc = commonBL.getDepositAcBean(inquiry.getBrCode(), inquiry.getProposedAc());
        session.setAttribute("proposed_ac", proposedAc);
        session.setAttribute(WebDictionary.INQUIRY_BEAN, inquiry);
        return actionMapping.findForward("balance_check");

    }

    public static final String ACTION_METHOD_CHARGE = "charge";

    public ActionForward charge(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String ctx = request.getContextPath();
        if (isCancelled(request)) {
            return actionMapping.findForward(CANCEL);
        }
        InquiryChargeForm form = (InquiryChargeForm) actionForm;
        Double newKeyCost = form.getKeyCost();
        Double newLinkingCost = form.getLinkingCost();
        String accNo = form.getTargetAccount();
        CommonBL commonBL = new CommonBL();
        LoginBean loginBean = SessionUtil.getSessionUser(session);
        Inquiry inquiryMain = commonBL.get(Inquiry.class, form.getInquiryOid());// (Inquiry) session.getAttribute(WebDictionary.INQUIRY_BEAN);

        ActionResult actionResult = new ActionResult(false);
        try {
            Inquiry inquiryForRemote = new Inquiry();
            BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
            BeanUtils.copyProperties(inquiryForRemote, inquiryMain);
            inquiryForRemote.setKeyCost(newKeyCost);
            inquiryForRemote.setLinkingCost(newLinkingCost);
            inquiryForRemote.setChargeableAccount(accNo);

            DataCarrier req = new DataCarrier(CIBBLActionList.ACTION_CHARGE_DEBIT); // 2602
            req.addData(dataLoginBean, loginBean);
            SecurityGateCO.verifyAccess(req);

            String ipAddress = request.getHeader("X-FORWARDED-FOR");
            if (GenericValidator.isBlankOrNull(ipAddress)) {
                ipAddress = request.getRemoteAddr();
            }
            req.addData(dataKeyInquiryBean, inquiryForRemote);
            req.addData("ipAddress", ipAddress);
            InquiryBL bl = new InquiryBL();

            actionResult = bl.charge(req);
            if (actionResult.isSuccess()) {
                /**This bean below fetch the transaction information from 'deposit module' remotely*/
                RemoteTrResultBean trResultBean = (RemoteTrResultBean) actionResult.getDataObject();

                if (trResultBean.isSuccess()) {
                    try {
                        Inquiry inquiryToUpdate = commonBL.get(Inquiry.class, inquiryMain.getOid());
                        BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
                        BeanUtils.copyProperties(inquiryToUpdate, inquiryMain);
                        inquiryToUpdate.setStatus(InquiryStatus.PROCESSING.CODE);
                        inquiryToUpdate.setKeyCost(inquiryMain.getKeyCost() + newKeyCost);
                        inquiryToUpdate.setLinkingCost(inquiryMain.getLinkingCost() + newLinkingCost);
                        inquiryToUpdate.setChargedBy(loginBean.getUserID());
                        inquiryToUpdate.setDrTrID(trResultBean.getDrTrID());
                        inquiryToUpdate.setChargedFrom(accNo);
                        inquiryToUpdate.setCostStatus(CIBDictionary.CIB_COST_STATUS_DEBITED); // Debited
                        inquiryToUpdate.setNote(trResultBean.getMessage() + "<br/>Charge Amount : " + inquiryForRemote.getTotalCost()); //Redundant

                        DataCarrier reqCarrier2;
                        reqCarrier2 = new DataCarrier(CIBBLActionList.ACTION_INQUIRY_SAVE_AND_UPDATE);
                        reqCarrier2.addData(dataLoginBean, loginBean);
                        inquiryToUpdate.setLastUpdateDate(new Date());
                        reqCarrier2.addData(dataKeyInquiryBean, inquiryToUpdate);

                        DataCarrier resCarrier2 = bl.updateStatus(reqCarrier2);
                        //DataCarrier resCarrier2 = service.saveOrUpdateInquiry(inquiryToUpdate);
                        if (resCarrier2.isSuccess()) {
                            Inquiry updatedInquiry = (Inquiry) resCarrier2.retrieveData(dataKeyInquiryBean);
                            session.removeAttribute(WebDictionary.INQUIRY_BEAN);
                            session.setAttribute(WebDictionary.INQUIRY_BEAN, updatedInquiry);
                        } else {
                            trResultBean.setMessage(trResultBean.getMessage().concat("<br>But Inquiry can't be updated"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                session.setAttribute(WebDictionary.DATA_SUCCESS_MSG,
                        CIBUtil.generateForwardMsg(trResultBean.getMessage() + "<br/>Charge Amount : " + inquiryForRemote.getTotalCost(), trResultBean.isSuccess(), "View Inquiry", ctx + "/viewInquiry.do?action=" + ACTION_METHOD_VIEW + "&oid=" + inquiryMain.getOid()));
                return actionMapping.findForward("success");

            }
        } catch (Exception e) {
            actionResult.setMsg(e.getMessage());
        }
        session.setAttribute(WebDictionary.DATA_SUCCESS_MSG,
                CIBUtil.generateForwardMsg(actionResult.getMsg(), actionResult.isSuccess(), "View Inquiry", ctx + "/viewInquiry.do?action=" + ACTION_METHOD_VIEW + "&oid=" + inquiryMain.getOid()));
        return actionMapping.findForward("success");
    }


    public static final String ACTION_METHOD_DOWNLOAD = "download";

    public ActionForward download(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String fileName = request.getParameter("fileName");
        String fullPath = request.getParameter("fullPath");
        String docName = request.getParameter("docName");
        ActionMessages errors = new ActionMessages();
        try {
            String ext = fileName.substring(fileName.indexOf(".") + 1);
            response.setContentType(MimeType.init(ext).MIME_TYPE);
            //response.setContentType(CIBUtil.getMimeType(ext));

            File file = new File(fullPath);
            FileInputStream fileInputStream = new FileInputStream(file);
            String headerKey = "Content-Disposition";
            response.setContentLength((int) file.length());
            String headerValue = String.format("attachment; filename=\"%s\"", docName + "." + ext);
            response.setHeader(headerKey, headerValue);
            OutputStream outStream = response.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }

            fileInputStream.close();
            outStream.close();
            return null;
        } catch (Exception e) {
            errors.add(ERROR_TITLE, new ActionMessage(e.getMessage()));
            saveErrors(request, errors);
            return actionMapping.findForward(ERROR);
        }

    }


    public static final String ACTION_METHOD_DISPLAY = "display";

    public ActionForward display(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String fileName = request.getParameter("fileName");
        String fullPath = request.getParameter("fullPath");
        ActionMessages errors = new ActionMessages();
        try {
            String ext = fileName.substring(fileName.indexOf(".") + 1);
            response.setContentType(MimeType.init(ext).MIME_TYPE);

            File file = new File(fullPath);
            FileInputStream fileInputStream = new FileInputStream(file);
            String headerKey = "Content-Disposition";

            response.addHeader(headerKey, "filename=" + fileName);
            ServletOutputStream stream = null;
            BufferedInputStream buf = null;
            response.setContentLength((int) file.length());

            buf = new BufferedInputStream(fileInputStream);
            int readBytes = 0;
            try {
                stream = response.getOutputStream();
                while ((readBytes = buf.read()) != -1)
                    stream.write(readBytes);
            } catch (IOException ioe) {
                errors.add(ERROR_TITLE, new ActionMessage("IOException"));
                saveErrors(request, errors);
                return actionMapping.findForward(ERROR);
            } finally {
                if (stream != null)
                    stream.close();
                if (buf != null)
                    buf.close();
            }
            return null;
        } catch (Exception e) {
            errors.add(ERROR_TITLE, new ActionMessage(e.getMessage()));
            saveErrors(request, errors);
            return actionMapping.findForward(ERROR);
        }
    }

}
