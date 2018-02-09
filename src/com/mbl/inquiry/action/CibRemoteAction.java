package com.mbl.inquiry.action;

import com.ibbl.cib.bl.CIBBLDataList;
import com.ibbl.inquiry.action.bean.CIBRemoteTransReqBean;
import com.ibbl.inquiry.action.bean.ResultBean;
import com.ibbl.inquiry.model.Inquiry;
import com.ibbl.security.ui.bean.LoginBean;
import com.ibbl.util.CIBDictionary;
import com.ibbl.util.WebDictionary;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p/>
 * Original author: Ayatullah Khomeni
 * Date: December 8, 2015(11:19:15 AM)
 * Last modification by: $Author: ayat $
 * Last modification on $Date: 2017/10/02 09:38:41 $
 * Current revision: $Revision: 1.3 $
 * <p/>
 * Revision History:
 * ------------------
 */

public class CibRemoteAction extends DispatchAction {
   // private static final String actionKeyCibFadTransaction = RemoteActionList.ACTION_REMOTE_CIB_DEPOSIT_TRANSACTION;

    public ActionForward remote(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String ctx = request.getContextPath();
        LoginBean loginBean = (LoginBean) session.getAttribute(CIBBLDataList.DATA_LOGIN);
        Inquiry inquiryBean = (Inquiry) session.getAttribute(WebDictionary.INQUIRY_BEAN);
        @SuppressWarnings("unchecked")
        Map<String, Object> customerListMap = (Map<String, Object>) session.getAttribute(WebDictionary.CUSTOMER_LIST_MAP);
        @SuppressWarnings("unchecked")
        Map<String, Object> customer = (Map<String, Object>) customerListMap.get(WebDictionary.MAP_KEY_CUSTOMER_MAP);
        @SuppressWarnings("unchecked")
        List<String> accList = (List<String>) customer.get(WebDictionary.MAP_KEY_ACC_NO_LIST);

        inquiryBean.setChargeableAccount("");

        makeTransaction(loginBean, inquiryBean);

        return actionMapping.findForward(CIBBLDataList.FORWARD_SUCCESS);
    }

    public void makeTransaction(LoginBean loginBean, Inquiry inquiry) {
        XStream xStream = new XStream(new DomDriver("UTF-8"));
        Class[] depClasses = new Class[]{
                LoginBean.class, Inquiry.class
        };
        xStream.processAnnotations(depClasses);
        byte[] bytes = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>".getBytes();

        OutputStream stream = new ByteArrayOutputStream();
        try {
            stream.write(bytes);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        CIBRemoteTransReqBean reqBean = new CIBRemoteTransReqBean();
        reqBean.getInquiryList().add(inquiry);
       // reqBean.setLoginBean(loginBean);
        //reqBean.setActionKey(actionKeyCibFadTransaction);

        String XML = xStream.toXML(reqBean);
        System.out.println("XML = " + XML);
        XML = XML.replaceAll("(\r\n|\n)", "");
        XML = XML.replaceAll("(\r|\n)", "");


        // TODO...
        String urlString = CIBDictionary.DEPOSIT_URL + "/remoteRequestFromCIB.do";
        //String urlString = "http://localhost:8081/depositNew/remoteRequestFromCIB.do";
        //String urlString = "http://localhost:8082/legal/remoteRequestFromCIB.do";

        /*if (urlString == null) {
            log.cError("cError.missing.url");
            returnMsg = "FALSE|".concat(WSDictionary.OTHER_ERROR);
        }*/

        URLConnection uConn = null;
        URL url = null;
        BufferedReader brIn = null;
        String responseXML = null;
        ResultBean resultBean = null;

        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            uConn = url.openConnection();

            // Request time out value is set sothat remote req. process doesn't keep
            // user long time waiting in case of network connection failure
            uConn.setConnectTimeout(600000);

            uConn.setDoOutput(true);
            PrintWriter pwOut = null;
            pwOut = new PrintWriter(uConn.getOutputStream());
            pwOut.print("remoteMessage" + "=" + java.net.URLEncoder.encode(XML));
            pwOut.close();

            /*Receiving response*/
            brIn = new BufferedReader(new InputStreamReader(uConn.getInputStream()));
            StringBuffer sbResp = new StringBuffer();
            String line = null;
            while ((line = brIn.readLine()) != null) {
                sbResp.append(line);
            }
            brIn.close();
            responseXML = sbResp.toString();
            resultBean = (ResultBean) xStream.fromXML(responseXML);
            /*Write Message*/
            System.out.println(resultBean.getMessage());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
