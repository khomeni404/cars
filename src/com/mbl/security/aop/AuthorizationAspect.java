package com.mbl.security.aop;

import com.mbl.common.service.GenericController;
import com.mbl.security.service.AuthAuthTokenServiceImpl;
import net.softengine.util.SecurityConstants;
import net.softengine.util.SecurityUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author Khomeni
 *         Created on : 16-May-17
 */

@Aspect
public class AuthorizationAspect{


    @Autowired
    private AuthAuthTokenServiceImpl authService;

    String actionName = null;
    String requestMapping = null;

    @Pointcut("within(@org.springframework.stereotype.Controller *) " +
            "&& !within(@org.springframework.stereotype.Controller com.mbl.security.AuthenticationController)" +
            "&& !within(@org.springframework.stereotype.Controller com.ibbl..home.HomeController)")
    public void allControllers() {
    }

    @Pointcut("execution(* *(..))")
    public void allOperations() {

    }


    @Around("allControllers() && allOperations()")
    public Object proceedToAction(ProceedingJoinPoint proceedJoinPoint) throws Throwable {
        //boolean isActiveCASMSec = SecurityClientConfig.getInstance().isActiveSecurity();
        if (/*isActiveCASMSec && */SecurityConstants.ACTIVE_ASPECTS) {
            int annotationIndex = 0;
            Annotation[] annotations = proceedJoinPoint.getTarget().getClass().getAnnotations();
            int annotationLength = annotations.length;
            for (int i = 0; i < annotationLength; i++) {
                if (annotations[i].toString().contains(SecurityConstants.REQUEST_MAPPING)) {
                    annotationIndex = i;
                    break;
                }
            }

            requestMapping = annotations[annotationIndex].toString();
            int start = requestMapping.indexOf("value=[/") + 8;
            requestMapping = requestMapping.substring(start);
            int end = requestMapping.indexOf("/]");
            String componentValue = requestMapping.substring(0, end);
            System.out.println(componentValue);

            actionName = proceedJoinPoint.getSignature().getName();
//        boolean isAllowed = authService.checkAuthorizationUsingAction(actionName);
            boolean isAllowed = SecurityUtil.checkAuthorizationUsingActionName(actionName);
//        boolean isAllowed = authService.checkAuthorizationUsingMapping(actionName);

            if (isAllowed) {
                return proceedJoinPoint.proceed();
            } else {
                Map<String, Object> map = GenericController.getModelMap("No Privilege !", "You have no permission to execute this action", false);
                return new ModelAndView("/template/open/no_privilege", map);

            }
        } else {
            return proceedJoinPoint.proceed();
        }
    }

}
