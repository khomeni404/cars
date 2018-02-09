package com.mbl.inquiry.action;

import com.google.gson.Gson;
import com.ibbl.cib.bl.CIBBLActionList;
import com.ibbl.cib.bl.CIBBLDataList;
import com.ibbl.cib.util.CIBUtil;
import com.ibbl.common.biz.CommonBL;
import com.ibbl.common.model.deposit.AccountPO;
import com.ibbl.core.common.DataCarrier;
import com.ibbl.core.exception.BusinessException;
import com.ibbl.core.exception.SystemException;
import com.ibbl.inquiry.action.bean.CustomerDataBean;
import com.ibbl.inquiry.action.bean.InquirySearchBean;
import com.ibbl.inquiry.biz.InquiryBL;
import com.ibbl.inquiry.biz.SubDataGroup;
import com.ibbl.inquiry.model.Inquiry;
import com.ibbl.security.bl.co.SecurityGateCO;
import com.ibbl.security.ui.bean.LoginBean;
import com.ibbl.util.DataKey;
import ibbl.common.util.FinAmount;
import ibbl.deposit.common.bl.CustomerManagerBL;
import ibbl.deposit.common.util.codeformat.CustNoFactory;
import ibbl.deposit.common.util.codeformat.ICustNo;
import ibbl.security.common.bean.SecuritySessionBean;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * package ibbl.law.common.web.action;
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p>
 * Original author: Ayatullah Khomeni
 * Date: October 14, 2015(11:05:05 AM)
 * Last modification by: $Author: ayat $
 * Last modification on $Date: 2017/12/19 10:09:33 $
 * Current revision: $Revision: 1.11 $
 * <p>
 * Revision History:
 * ------------------
 */
public class AjaxAction extends Action {
    private static final String dataCustId = CIBBLDataList.DATA_CUST_ID;
    int actionKeyList = CIBBLActionList.ACTION_INQUIRY_LIST;
    String dataLoginBean = CIBBLDataList.DATA_LOGIN;
    String dataKeySearchBean = CIBBLDataList.DATA_INQUIRY_SEARCH_BEAN;

    @SuppressWarnings("unchecked")
    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpResponse) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        ActionMessages errors = new ActionMessages();
        String ajaxKey = httpServletRequest.getParameter("ajaxKey");
        httpResponse.setContentType("text/text;charset=utf-8");
        httpResponse.setHeader("cache-control", "no-cache");
        PrintWriter out = httpResponse.getWriter();
        LoginBean loginBean = (LoginBean) session.getAttribute(CIBBLDataList.DATA_LOGIN);

        if (ajaxKey.equals(DataKey.AJAX_KEY_GCD)) { // GET Customer Data
            String custId = httpServletRequest.getParameter("custId");
            CustomerManagerBL managerBL = new CustomerManagerBL();

            ICustNo custNoObj = CustNoFactory.getCustNoObject();
            String custBrCode = custNoObj.getBranchCode(custId);

            /*if (!custBrCode.equals(loginBean.getBrCode())) {
                String row = "<tr class=\"td-color4\"> <td colspan='15' style='color: red'>Sorry, Br Codes are not same.</td></tr>";
                out.println("<table class=\"table table-bordered\">" + row + "</table>");
                return null;
            }

            if (!managerBL.verifyCustID(custId)) {

                String row = "<tr class=\"td-color4\"> <td colspan='15' style='color: red'>Sorry, Customer ID is invalid !</td></tr>";

                out.println("<table class=\"table table-bordered\">" + row + "</table>");
                return null;
            }*/
            try {
                DataCarrier reqCarrier = new DataCarrier(CIBBLActionList.ACTION_INQUIRY_GET_CUSTOMER_LIST_MAP);
                reqCarrier.addData(CIBBLDataList.DATA_LOGIN, loginBean);
                reqCarrier.addData(dataCustId, custId);
                SecurityGateCO.verifyAccess(reqCarrier);

                SubDataGroup sdg = new SubDataGroup();
//                @SuppressWarnings("unchecked")
//                List<CustomerDataBean> dataBeanList = sdg.getCustomerDataBeanGroup(loginBean.getBrCode(), custId);//  (List<CustomerDataBean>) resCarrier.retrieveData(CIBDLDataList.DATA_CUST_BEAN_LIST);
                FileInputStream fin = new FileInputStream("D:\\cloud\\doc\\taims\\CustomerDataBean_list.ser");
                // FileInputStream fin = new FileInputStream();
                ObjectInputStream oisBC = new ObjectInputStream(fin);
                List<CustomerDataBean> dataBeanList = (List<CustomerDataBean>) oisBC.readObject();

                String row = "";
                int counter = 0;
                for (CustomerDataBean member : dataBeanList) {
                    row += "<tr>";/*<td>"+(counter+1)+"</td>*/
                    row += "<td ><input type='checkbox' class=\"cust-checker\" " + (counter == 0 ? "checked" : "") + " value='" + member.getCustomerId() + "'/></td>";
                    //row += "<td rowspan=''><b>" + member.getCustomerId() + "</b></td>";
                    row += "<td msg='Name'>" + member.getCustomerName() + "</td>";
                    row += "<td msg='Father'>" + member.getFatherName() + "</td>";
                    row += "<td msg='Mother'>" + member.getMotherName() + "</td>";
                    row += "<td msg='Birth Date'>" + member.getDob() + "</td>";
                    row += "<td msg='Birth Place'>" + (GenericValidator.isBlankOrNull(member.getBirthPlaceDC()) ? member.getBirthPlace() : member.getBirthPlaceDC()) + "</td>";
                    row += "<td msg='Present Address'>" + member.getPrAddress() + "</td>";
                    row += "<td msg='Present District'>" + member.getPrDC() + "</td>";
                    row += "<td msg='Present Country'>" + member.getPrCC() + "</td>";
                    row += "<td msg='Permanent Address'>" + member.getPeAddress() + "</td>";
                    row += "<td msg='Permanent District'>" + member.getPeDC() + "</td>";
                    row += "<td msg='Permanent Country'>" + member.getPeCC() + "</td>";
                    row += "<td msg='NID / Pass No.' rowspan=''>" + (GenericValidator.isBlankOrNull(member.getNid()) ? "" : member.getNid() + ", ") + member.getPassNo() + "</td>";
                    row += "<td msg='Reg No'>" + member.getCompRegNo() + "</td>";
                    //row += "</tr>";
                    //row += "<tr class=\"td-color3\">";
                    row += "<td msg='Reg Date'>" + member.getCompRegDate() + "</td>";
                    row += "</tr>";
                    counter++;
                }

                if (row.equals(""))
                    out.println("<tr class=\"td-color4\"> <td colspan='15' style='color: red'>No Customer Found !</td></tr>");
                else {
                    String th = "<tr><thead><th>CHQ</th>" +
                            "<th>Name</th>" +
                            "<th>Father</th>" +
                            "<th>Mother</th>" +
                            "<th>DOB</th>" +
                            "<th>B.Place</th>" +
                            "<th>Pr.Address</th>" +
                            "<th>PrDC</th>" +
                            "<th>PrCC</th>" +
                            "<th>Pe.Address</th>" +
                            "<th>PeDC</th>" +
                            "<th>PeCC</th>" +
                            "<th>NID/Pass</th>" +
                            "<th>Reg.No</th>" +
                            "<th>Reg.Date</th>" +
                            "</thead></tr>";

                    out.println("<table class=\"table table-bordered\">" + th + row + "</table>");
                }
                return null;

            } catch (BusinessException e) {
                out.println("Business Problem" + e.getMessage());
            } catch (SystemException e) {
                out.println("System Problem" + e.getMessage());
            } catch (Exception e) {
                out.println(e.getMessage());
            }
        } else if (ajaxKey.equals(DataKey.AJAX_KEY_AIL_DEP_AC_LIST)) { // GET Account Data
            String custId = httpServletRequest.getParameter("custId");
            CustomerManagerBL managerBL = new CustomerManagerBL();

            ICustNo custNoObj = CustNoFactory.getCustNoObject();
            String custBrCode = custNoObj.getBranchCode(custId);

            String options = "<option value=\"\">--Select an Account--</option>";
            if (!custBrCode.equals(loginBean.getBrCode())) {
                out.println(options);
                return null;
            }

            if (!managerBL.verifyCustID(custId)) {
                out.println(options);
                return null;
            }
            try {
                CommonBL commonBL = new CommonBL();
                for (int i = 0; i < 5; i++) {
                        options += "<option value='2050213100132671" + i + "'>2050213100132671" + i + " (Bal-" + (new FinAmount(54069*i)) + "/-) : PRAN FOODS LIMITED </option>";
                }
                //List<AccountPO> accountPOList =  commonBL.getDepositAcBeanList(custBrCode, custId);
                /*for (AccountPO ac : accountPOList) {
                    if (CIBUtil.isChargeable(ac)) {
                        options += "<option value='" + ac.getAccNo() + "'>" + ac.getAccNo() + " (Bal-" + (new FinAmount(ac.getBalance())) + "/-) : " + ac.getAccTitle() + "</option>";
                    }
                }*/
                out.println(options);

                return null;

            } catch (Exception e) {
                out.println(e.getMessage());
            }
        } else if (ajaxKey.equals(DataKey.AJAX_KEY_AIL_INQ_LIST)) {
            int start = 0;
            int limit = 0;
            try {
                start = Integer.parseInt(httpServletRequest.getParameter("start"));
                limit = Integer.parseInt(httpServletRequest.getParameter("limit"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            InquirySearchBean searchBean = new InquirySearchBean();
            searchBean.setLoginBean(loginBean);
            searchBean.setStart(start);
            searchBean.setLimit(limit);
            String brCode = loginBean.getBrCode();

            searchBean.setBrCode(brCode);
            searchBean.setLoginBean(loginBean);
            DataCarrier reqCarrier = new DataCarrier(CIBBLActionList.ACTION_INQUIRY_LIST);

            reqCarrier.addData(dataLoginBean, loginBean);
            SecurityGateCO.verifyAccess(reqCarrier);

            InquiryBL bl = new InquiryBL();

            List<Inquiry> requestList = bl.getInquiryList(searchBean);
            List<Inquiry> dataList = new ArrayList<>();
            for (Inquiry i : requestList) {
                i.setHistorySet(null);
                i.setDocSet(null);
                dataList.add(i);
            }

            Map<String, Object> modelMap = new HashMap<String, Object>(3);
            modelMap.put("totalRecord", 878555);
            modelMap.put("dataList", dataList);
            modelMap.put("success", true);
            Gson gson = new Gson();
            String json = gson.toJson(modelMap);
            out.println(json);
            out.flush();
            return null;

        } else {
            out.println("No Action Key Found !");
        }
        out.flush();
        return null;
    }
}
