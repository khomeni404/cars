package com.mbl.security;

import com.mbl.common.service.GenericController;
import com.mbl.security.bean.TokenBean;
import com.mbl.security.service.AuthAuthTokenServiceImpl;
import com.mbl.util.Constants;
import net.softengine.util.ActionResult;
import net.softengine.util.GValidator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;


/**
 * Copyright &copy; 2017-2018 Soft Engine Inc.
 * <p>
 * Original author: Khomeni
 * Date: 07/12/2017 2:22 PM
 * Last modification by: Khomeni: Khomeni
 * Last modification on 04/12/2017: 07/12/2017 2:22 PM
 * Current revision: 1.0.0: 1.1 $
 * <p>
 * Revision History:
 * ------------------
 */

@Controller
@RequestMapping(value = "/auth/", method = {RequestMethod.GET, RequestMethod.HEAD})
public class AuthenticationController  extends GenericController {
    @Autowired
    public AuthAuthTokenServiceImpl authService;

    @RequestMapping(value = "/login.mbl", method = {RequestMethod.GET, RequestMethod.HEAD})
    public ModelAndView login(Model model, @RequestParam(required = false) String message) {
        model.addAllAttributes(getModelMap("Login", message));
        if (!model.containsAttribute("TokenBean")) {
            TokenBean token = new TokenBean();

            //Default username and pass in developing mode
            token.setUsername("mak");
            token.setPassword("man");
            token.setUsername("01717659287");
            token.setPassword("4569287");
            token.setUsername("jamil420");
            token.setPassword("jaMil@548");
            token.setBrCode("1005");
            model.addAttribute("TokenBean", token);
        }

        model.addAttribute("caampRegisterLink", Constants.CAAMP_REGISTER_LINK);
        return new ModelAndView("/home/login");
    }


    /**
     * For Direct Login from login page of this system
     * The Authentication and Authorization both are required in this action.
     * @param tokenBean TokenBean
     * @param result BindingResult
     * @param attributes RedirectAttributes
     * @param request HttpServletRequest
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/authenticateUser.mbl", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView authenticateUser(@ModelAttribute @Valid TokenBean tokenBean,
                                         BindingResult result, RedirectAttributes attributes, HttpServletRequest request) throws Exception {
        tokenBean.validateStaffToken(result);
        if (result.hasErrors()) {
            addRedirectAttributes(tokenBean, result, attributes);
            return new ModelAndView("redirect:/auth/login.mbl");
        }
        tokenBean.setSecretKey(Constants.SECRET_KEY);
        tokenBean.setProjectId(Constants.PROJECT_ID);
        tokenBean.setEncrypted(false);

        ActionResult actionResult = authService.sesaaAuthentication(tokenBean, request);
        if (actionResult.isSuccess()) {
            String principalType = (String) actionResult.getDataObject();
            if (principalType.equals("Staff")) {
                return new ModelAndView("redirect:/home/dashboard.mbl");
            }
            return new ModelAndView("redirect:/");
        } else {
            actionResult.copyError(result);
            addRedirectAttributes(tokenBean, result, attributes);
            return new ModelAndView("redirect:/auth/login.mbl");
        }
    }


    /**
     * For remote Login from CAAMP
     * Only Authorization is mandatory in this action.
     * But in preliminary stage I implement Authentication too.
     * @param tokenBean TokenBean
     * @param result BindingResult
     * @param attributes RedirectAttributes
     * @param request HttpServletRequest
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/authorizeUser.mbl", method = RequestMethod.POST)
    public ModelAndView authorizeUser(@ModelAttribute @Valid TokenBean tokenBean,
                                      BindingResult result, RedirectAttributes attributes, HttpServletRequest request) throws Exception {
        attributes.addAttribute("dataTarget", "login-box");
        tokenBean.validateStaffToken(result);
        if (result.hasErrors()) {
            addRedirectAttributes(tokenBean, result, attributes);
            return new ModelAndView("redirect:/auth/login.mbl");
        }

        ActionResult actionResult = verifySystemAccess(tokenBean, request);
        if (actionResult.hasError()){
            actionResult.copyError(result);
            addRedirectAttributes(tokenBean, result, attributes);
            return new ModelAndView("redirect:/auth/login.mbl");
        }

        Long projectId = tokenBean.getProjectId();
        if (!Constants.PROJECT_ID.equals(projectId)) {
            GValidator.rejectValue(result, "errors", "Project ID doesn't match.");
            addRedirectAttributes(tokenBean, result, attributes);
            return new ModelAndView("redirect:/auth/login.mbl");
        }

        actionResult = authService.sesaaAuthentication(tokenBean, request);
        if (actionResult.isSuccess()) {
            String principalType = (String) actionResult.getDataObject();
            if (principalType.equals("Staff")) {
                return new ModelAndView("redirect:/home/dashboard.mbl");
            }
            return new ModelAndView("redirect:/");
        } else {
            actionResult.copyError(result);
            addRedirectAttributes(tokenBean, result, attributes);
            return new ModelAndView("redirect:/auth/login.mbl");
        }
    }

    private ActionResult verifySystemAccess(TokenBean tokenBean, HttpServletRequest request) {
        ActionResult result = new ActionResult(false);
        try {
            String remoteIP = request.getHeader("X-FORWARDED-FOR");
            if (GenericValidator.isBlankOrNull(remoteIP)) {
                remoteIP = request.getRemoteAddr();
            }

            List<String> ipAddressList = Arrays.asList("127.0.0.1", "192.168.85.125", "0:0:0:0:0:0:0:1");
            if (CollectionUtils.isEmpty(ipAddressList) || GenericValidator.isBlankOrNull(remoteIP)) {
                return result.returnError("errors", " No associated Server IP address found with the Project");
            } else if (!ipAddressList.contains(remoteIP)) {
                return result.returnError("errors", "No Access ! Requested from an Unauthorized source/machine.");
            }


            if (Constants.SECRET_KEY.equals(tokenBean.getSecretKey())) {
                result.setSuccess(true);
                return result;
            } else {
                return result.returnError("errors", "Unauthorized Access. [Secret Key]");
            }
        } catch (Exception e) {
            result = new ActionResult(false);
            result.setMsg("Developer Msg: " + e.getMessage());
            result.putError("errors", "System Failed !.");
            return result;
        }

    }
}
