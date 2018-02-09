package com.mbl.security.service;


import com.google.gson.Gson;
import com.mbl.common.dao.CommonDAO;
import com.mbl.security.bean.TokenBean;
import com.mbl.security.service.interfaces.AuthAuthTokenService;
import com.mbl.util.Constants;
import net.softengine.util.ActionResult;
import net.softengine.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Copyright &copy; 2017-2018 Soft Engine Inc.
 * <p>
 * Original author: Khomeni
 * Date: 21/11/2017 1:56 PM
 * Last modification by: Khomeni: Khomeni
 * Last modification on 21/11/2017: 21/11/2017 1:56 PM
 * Current revision: 1.0.0: 1.1 $
 * <p>
 * Revision History:
 * ------------------
 */

@Service
//@Transactional //(propagation = Propagation.REQUIRED, readOnly = true)
public class AuthAuthTokenServiceImpl implements AuthAuthTokenService {
    @Autowired
    public CommonDAO commonDAO;

    /**
     * Core Method for Authenticate and Authorize an User
     * Inside this system Use "authenticateAndLoadCredentials(TokenBean tokenBean)"
     * From outside from this system use sesaaResponseByGET(TokenBean tokenBean)
     * or sesaaResponseByPOST(TokenBean tokenBean)
     *
     * @param tokenBean TokenBean
     * @param request   HttpServletRequest
     * @return ActionResult
     */
    public ActionResult sesaaAuthentication(TokenBean tokenBean, HttpServletRequest request) {
        ActionResult actionResult;
        try {

//            actionResult = authenticateAndLoadCredentials(tokenBean, request);
            actionResult = getStaticSesaaResponse();
//            actionResult = sesaaResponseByGET(tokenBean);
            //  sesaaResponse = sesaaResponseByPOST(tokenBean);
            if (actionResult.isSuccess()) {
                SecurityUtil.setAuthenticationAndAuthorizationToken(actionResult);
                actionResult.setMsg("Logged in successfully");
            }
        } catch (Exception e) {
            actionResult = new ActionResult(false);
            actionResult.putError("errors", e.getMessage());
        }
        return actionResult;
    }


    public ActionResult sesaaResponseByGET(TokenBean tokenBean){
        Gson gson = new Gson();
        try {
            String USER_AGENT = "Mozilla/5.0";
            String secretKey = tokenBean.getSecretKey();
            StringBuilder sb = new StringBuilder(Constants.USER_AUTH_PATH);
            sb.append("?projectId=");
            sb.append(Constants.PROJECT_ID);
            sb.append("&secretKey=");
            sb.append(secretKey);
            sb.append("&username=");
            sb.append(tokenBean.getUsername());
            sb.append("&password=");
            sb.append(tokenBean.getPassword());
            sb.append("&encrypted=");
            sb.append(tokenBean.isEncrypted());

            URL obj = new URL(new String(sb));
            // URL obj = new URL("http://127.0.0.1:8082/caamp/auth/userAuthenticationService.se?projectId=102&secretKey="+secretKey+"&username="+);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            // System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            String data = response.toString();
            Christopher c = new Christopher(Constants.SECRET_KEY);
            data = c.decrypt(data);

            return gson.fromJson(data, ActionResult.class);
        } catch (Exception e) {
            ActionResult result = new ActionResult(false);
            if (e instanceof FileNotFoundException) {
                result.putError("errors", "CAAMP Link not found/active !");
            } else {
                result.putError("errors", "Remote Server is down. CAAMP Link not found/active");
            }
            return result;
        }

    }

    public ActionResult sesaaResponseByPOST(TokenBean tokenBean) throws Exception {
        String USER_AGENT = "Mozilla/5.0";
        String url = "https://103.239.252.90:4343/sesaam/auth/authenticateUserService.se";
        String urlParameters = "username=" + tokenBean.getUsername() + "&password=" + tokenBean.getPassword();

        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        String data = response.toString();
        Gson gson = new Gson();
        return gson.fromJson(data, ActionResult.class);
    }

    private static ActionResult getStaticSesaaResponse() {
        String data = "{\"success\":true, \"dataObject\":\"Staff\",\"map\":{\"session_user_id\":1,\"session_user_eid\":\"20170103001\",\"session_user_n_name\":\"Joti\",\"session_user_br_code\":\"0101\",\"session_user_br_name\":\"Khulna Branch\",\"session_user_groups\":[1,3,4],\"session_user\":{\"id\":1,\"active\":true,\"name\":\"Masura Akter Joti\"}}}";
        Gson gson = new Gson();
        return gson.fromJson(data, ActionResult.class);
    }
}
