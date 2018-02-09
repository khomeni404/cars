package com.mbl.inquiry.action;

import com.ibbl.cib.bl.CIBBLActionList;
import com.ibbl.cib.bl.CIBBLDataList;
import com.ibbl.cib.dl.IDGenerator;
import com.ibbl.cib.dl.hibernate.BranchPO;
import com.ibbl.common.biz.CommonBL;
import com.ibbl.core.common.DataCarrier;
import com.ibbl.core.exception.BusinessException;
import com.ibbl.core.exception.MsgDictionary;
import com.ibbl.core.exception.SystemException;
import com.ibbl.inquiry.action.form.CIBOperatorForm;
import com.ibbl.inquiry.biz.OperatorBL;
import com.ibbl.inquiry.biz.ReportServiceImpl;
import com.ibbl.inquiry.model.CIBOperator;
import com.ibbl.inquiry.model.OperatorHistLog;
import com.ibbl.security.bl.co.SecurityGateCO;
import com.ibbl.security.ui.bean.LoginBean;
import com.ibbl.util.CIBDictionary;
import com.ibbl.util.CIBUtil;
import com.ibbl.util.SessionUtil;
import com.ibbl.util.WebDictionary;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.*;
import org.apache.struts.actions.DispatchAction;
import org.hibernate.criterion.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Copyright &copy; 2002-2003 Islami Bank Bangladesh Limited
 * <p>
 * Original author: Ayatullah Khomeni
 * Date: 23/12/2015
 * Last modification by: ayat $
 * Last modification on 23/12/2015: 12:42 PM
 * Current revision: : 1.1.1.1
 * <p>
 * Revision History:
 * ------------------
 */
public class CibOperatorAction extends DispatchAction implements CIBBLActionList, CIBBLDataList {

    public static String ACTION_METHOD_CREATE = "create";

    public ActionForward create(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) {
        HttpSession session = request.getSession();
        if (isCancelled(request)) {
            return actionMapping.findForward(CIBBLDataList.FORWARD_CANCEL);
        }
        ActionMessages errors = new ActionMessages();
        try {
            LoginBean loginBean = SessionUtil.getSessionUser(session);
            DataCarrier reqCarrier = new DataCarrier(ACTION_USER_CREATE);
            reqCarrier.addData(CIBBLDataList.DATA_LOGIN, loginBean);

            SecurityGateCO.verifyAccess(reqCarrier);
            return actionMapping.findForward("success");
        } catch (Exception e) {
            errors.add(MsgDictionary.ERROR_TITLE, new ActionMessage("errors.details", CIBUtil.getMessage(e)));
            saveErrors(request, errors);
            return actionMapping.findForward(CIBBLDataList.FORWARD_ERROR);
        }
    }

    public static String ACTION_SAVE = "save";

    public ActionForward save(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        HttpSession session = httpServletRequest.getSession();
        LoginBean loginBean = SessionUtil.getSessionUser(session);
        ActionMessages errors = new ActionMessages();

        if (!(loginBean.isCibOperator() && loginBean.isSuperUser())) {
            errors.add(ERROR_TITLE, new ActionMessage("errors.detail", "Only CIB HO super user have this privilege"));
            saveErrors(httpServletRequest, errors);
            return actionMapping.findForward("error");
        }

        CIBOperatorForm operatorForm = (CIBOperatorForm) actionForm;
        if (isCancelled(httpServletRequest)) {
            operatorForm.reset(actionMapping, httpServletRequest);
            return actionMapping.findForward(CIBBLDataList.FORWARD_CANCEL);
        }

        try {
            CommonBL commonService = new CommonBL();
            OperatorBL operatorBL = new OperatorBL();

            /** Checking-up Duplicate CIB Operator*/
            CIBOperator duplicate = commonService.get(CIBOperator.class, "userId", operatorForm.getUserId());

            if (duplicate == null) {
                CIBOperator operatorToSave = operatorForm.toModelBean();
                operatorToSave.setDataOperator(loginBean.getUserID());
                DataCarrier reqCarrierToSave = new DataCarrier(ACTION_USER_SAVE);
                reqCarrierToSave.addData(CIBBLDataList.DATA_LOGIN, loginBean);
                SecurityGateCO.verifyAccess(reqCarrierToSave);
                operatorToSave.setOid(new IDGenerator().generate());
                boolean saved = operatorBL.save(operatorToSave, loginBean);

                reqCarrierToSave.addData(DATA_CIB_OPERATOR_BEAN, operatorToSave);

                if (saved) {
                    CIBOperator savedOperator = commonService.get(CIBOperator.class, "userId", operatorForm.getUserId());
                    session.setAttribute(WebDictionary.CIB_OPERATOR_BEAN, savedOperator);
                    operatorForm.reset(actionMapping, httpServletRequest);

                    List<CIBOperator> cibOperatorList = commonService.findAll(CIBOperator.class, Order.desc("active"));

                    session.setAttribute(WebDictionary.CIB_OPERATOR_BEAN_LIST, cibOperatorList);
                    return actionMapping.findForward("success");


                } else {
                    errors.add(MsgDictionary.ERROR_TITLE, new ActionMessage("Operator Cant Saved."));
                    saveErrors(httpServletRequest, errors);
                    return actionMapping.findForward(CIBBLDataList.FORWARD_ERROR);
                }
            } else {
                String active = duplicate.getActive() == 1 ? "Active" : "Inactive";
                errors.add(MsgDictionary.ERROR_TITLE, new ActionMessage("cib.error.registered.user", new String[]{duplicate.getName(), active}));
                saveErrors(httpServletRequest, errors);
                return actionMapping.findForward(CIBBLDataList.FORWARD_ERROR);
            }
        } catch (Exception e) {
            errors.add(MsgDictionary.ERROR_TITLE, new ActionMessage("errors.details", CIBUtil.getMessage(e)));
            saveErrors(httpServletRequest, errors);
            return actionMapping.findForward(CIBBLDataList.FORWARD_ERROR);
        }
    }

    public static String ACTION_SEARCH = "search";

    public ActionForward search(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return actionMapping.findForward("search");
    }

    public static String ACTION_LIST = "list";

    public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        HttpSession session = httpServletRequest.getSession();

        if (isCancelled(httpServletRequest)) {
            return actionMapping.findForward(CIBBLDataList.FORWARD_CANCEL);
        }

        ActionMessages errors = new ActionMessages();

        try {
            LoginBean loginBean = SessionUtil.getSessionUser(session);
            DataCarrier reqCarrier = new DataCarrier(ACTION_USER_LIST);
            reqCarrier.addData(CIBBLDataList.DATA_LOGIN, loginBean);
            SecurityGateCO.verifyAccess(reqCarrier);

            CommonBL commonBL = new CommonBL();
            List<CIBOperator> cibOperatorList = commonBL.findAll(CIBOperator.class, Order.desc("active"));

            if (!CollectionUtils.isEmpty(cibOperatorList)) {
                session.setAttribute(WebDictionary.CIB_OPERATOR_BEAN_LIST, cibOperatorList);
                return actionMapping.findForward("success");
            } else {
                errors.add(MsgDictionary.ERROR_TITLE, new ActionMessage("No Operator Found !"));
                saveErrors(httpServletRequest, errors);
                return actionMapping.findForward(CIBBLDataList.FORWARD_ERROR);
            }
        } catch (Exception e) {
            errors.add(MsgDictionary.ERROR_TITLE, new ActionMessage("errors.details", CIBUtil.getMessage(e)));
            saveErrors(httpServletRequest, errors);
            return actionMapping.findForward(CIBBLDataList.FORWARD_ERROR);
        }
    }

    public static String ACTION_EDIT = "edit";

    public ActionForward edit(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        HttpSession session = httpServletRequest.getSession();
        ActionMessages errors = new ActionMessages();
        LoginBean loginBean = SessionUtil.getSessionUser(session);
        if (!(loginBean.isCibOperator() && loginBean.isSuperUser())) {
            errors.add(ERROR_TITLE, new ActionMessage("errors.detail", "Only CIB HO super user have this privilege"));
            saveErrors(httpServletRequest, errors);
            return actionMapping.findForward("error");
        }

        String userId;
        CIBOperator operator;
        CommonBL c = new CommonBL();
        /**Part 1*/
        try {
            userId = String.valueOf(httpServletRequest.getParameter("userId"));
            operator = c.get(CIBOperator.class, "userId", userId);
        } catch (NumberFormatException ex) {
            errors.add(MsgDictionary.ERROR_TITLE, new ActionMessage("errors.details", CIBUtil.getMessage(ex)));
            saveErrors(httpServletRequest, errors);
            return actionMapping.findForward(CIBBLDataList.FORWARD_ERROR);
        }
        session.setAttribute(WebDictionary.CIB_OPERATOR_BEAN, operator);

        if (isCancelled(httpServletRequest)) {
            return actionMapping.findForward(CIBBLDataList.FORWARD_CANCEL);
        }


        /**Part 2 : Pooling up All branch and Enlisted Br List Map*/
        try {
            DataCarrier reqCarrier = new DataCarrier(ACTION_USER_EDIT);
            reqCarrier.addData(CIBBLDataList.DATA_LOGIN, loginBean);

            SecurityGateCO.verifyAccess(reqCarrier);

            reqCarrier.addData(DATA_CIB_OPERATOR_BEAN, operator);

            DataCarrier resCarrier = new ReportServiceImpl().getEnlistedBrData(reqCarrier);

            //DataCarrier resCarrier = // BLGateWay.forward(reqCarrier);
            if (resCarrier.isSuccess()) {
                @SuppressWarnings("unchecked")
                Map<String, List<BranchPO>> allBrAndEnlistedBrData = (Map<String, List<BranchPO>>) resCarrier.retrieveData(CIBBLDataList.DATA_ALL_BR_AND_ENLISTED_BR_MAP);
                session.setAttribute(WebDictionary.ALL_BR_AND_ENLISTED_BR_MAP, allBrAndEnlistedBrData);

                session.setAttribute("historySet", c.findAll(OperatorHistLog.class, "cibOperator", operator));
                return actionMapping.findForward(CIBBLDataList.FORWARD_SUCCESS);
            } else {
                errors.add(MsgDictionary.ERROR_TITLE, new ActionMessage(resCarrier.getMsg().getMsg()));
                saveErrors(httpServletRequest, errors);
                return actionMapping.findForward(CIBBLDataList.FORWARD_ERROR);
            }
        } catch (Exception e) {
            errors.add(MsgDictionary.ERROR_TITLE, new ActionMessage("errors.details", CIBUtil.getMessage(e)));
            saveErrors(httpServletRequest, errors);
            return actionMapping.findForward(CIBBLDataList.FORWARD_ERROR);
        }
    }

    public static String ACTION_UPDATE = "update";

    public ActionForward update(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        HttpSession session = httpServletRequest.getSession();
        LoginBean loginBean = SessionUtil.getSessionUser(session);
        ActionMessages errors = new ActionMessages();

        if (!(loginBean.isCibOperator() && loginBean.isSuperUser())) {
            errors.add(ERROR_TITLE, new ActionMessage("errors.detail", "Only CIB HO super user have this privilege"));
            saveErrors(httpServletRequest, errors);
            return actionMapping.findForward("error");
        }

        String userId;
        CIBOperator operator;
        /**Part 1*/
        try {
            userId = String.valueOf(httpServletRequest.getParameter("userId"));
            CommonBL c = new CommonBL();
            operator = c.get(CIBOperator.class, "userId", userId);
        } catch (NumberFormatException ex) {
            errors.add(MsgDictionary.ERROR_TITLE, new ActionMessage("errors.details", CIBUtil.getMessage(ex)));
            saveErrors(httpServletRequest, errors);
            return actionMapping.findForward(CIBBLDataList.FORWARD_ERROR);
        }

        String histLogType = httpServletRequest.getParameter("histLogType");
        String enlistedBr = httpServletRequest.getParameter("enlistedBr");
        if (GenericValidator.isBlankOrNull(enlistedBr) || enlistedBr.equals("null")) {
            enlistedBr = "";
        }
        String active = httpServletRequest.getParameter("active");
        try {

            if (histLogType.equals(CIBDictionary.CIB_OPERATOR_HIST_LOG[1][0])) {        //Set Active / Inactive
                operator.setActive(Integer.valueOf(active));
                operator.setHistLogType(Integer.valueOf(histLogType));
                operator.setDataOperator(loginBean.getUserID());
                if (active.equals("0")) {
                    operator.setEnlistedBr("");
                }
            } else if (histLogType.equals(CIBDictionary.CIB_OPERATOR_HIST_LOG[2][0])) { // Branch Assignment
                operator.setEnlistedBr(enlistedBr);
                operator.setHistLogType(Integer.valueOf(histLogType));
                operator.setDataOperator(loginBean.getUserID());
            } else {
                return actionMapping.findForward(CIBBLDataList.FORWARD_CANCEL);
            }
            DataCarrier reqCarrier = new DataCarrier(ACTION_USER_SAVE);
            reqCarrier.addData(CIBBLDataList.DATA_LOGIN, loginBean);
//            reqCarrier.addData(DATA_CIB_OPERATOR_BEAN, operator);
            SecurityGateCO.verifyAccess(reqCarrier);
            CommonBL commonBL = new CommonBL();
            OperatorBL operatorBL = new OperatorBL();
            boolean updated = operatorBL.update(operator, loginBean);
            //DataCarrier resCarrier = // BLGateWay.forward(reqCarrier); // old

            if (updated) {
                session.removeAttribute(WebDictionary.INQUIRY_BEAN);
                CIBOperator updatedOperator = commonBL.get(CIBOperator.class, operator.getOid());
                session.setAttribute(WebDictionary.CIB_OPERATOR_BEAN, updatedOperator);
                return actionMapping.findForward(CIBBLDataList.FORWARD_SUCCESS);
            } else {
                errors.add(MsgDictionary.ERROR_TITLE, new ActionMessage("Can't Updated !"));
                saveErrors(httpServletRequest, errors);
                return actionMapping.findForward(CIBBLDataList.FORWARD_ERROR);
            }
        } catch (BusinessException e) {
            errors.add(MsgDictionary.ERROR_TITLE, new ActionMessage(e.getMessage(), e.getParams()));
            saveErrors(httpServletRequest, errors);
            return actionMapping.findForward(CIBBLDataList.FORWARD_ERROR);
        } catch (SystemException e) {
            errors.add(MsgDictionary.ERROR_TITLE, new ActionMessage(e.getMessage(), e.getParams()));
            saveErrors(httpServletRequest, errors);
            return actionMapping.findForward(CIBBLDataList.FORWARD_ERROR);
        } catch (Exception e) {
            errors.add(MsgDictionary.ERROR_TITLE, new ActionMessage(e.getMessage()));
            saveErrors(httpServletRequest, errors);
            return actionMapping.findForward(CIBBLDataList.FORWARD_ERROR);
        }
    }
}

