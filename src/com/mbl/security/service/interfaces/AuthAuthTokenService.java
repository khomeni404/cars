package com.mbl.security.service.interfaces;

import com.mbl.security.bean.TokenBean;
import net.softengine.util.ActionResult;

import javax.servlet.http.HttpServletRequest;

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

public interface AuthAuthTokenService {

    ActionResult sesaaAuthentication(TokenBean tokenBean, HttpServletRequest request);

    ActionResult sesaaResponseByPOST(TokenBean tokenBean) throws Exception;

    ActionResult sesaaResponseByGET(TokenBean tokenBean) throws Exception;


}
