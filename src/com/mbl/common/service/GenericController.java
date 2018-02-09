package com.mbl.common.service;

import com.mbl.ir.service.InquiryService;
import com.mbl.util.Utility;
import net.softengine.util.ActionResult;
import net.softengine.util.DateUtil;
import net.softengine.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Khomeni
 * Created on : 17-May-17 at 12:54 AM
 */

@Component
public class GenericController {

    @Autowired
    public CommonService commonService;


    @Autowired
    public InquiryService inquiryService;


    @Autowired
    public FileService fileService;


    public static ModelAndView redirectErrorWithAttributes(Object modelAttribute, BindingResult binding, RedirectAttributes redirectAttributes, String path) {
        String modelClassName = modelAttribute.getClass().getSimpleName();
        redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult." + modelClassName, binding);
        redirectAttributes.addFlashAttribute(modelClassName, modelAttribute);
        return new ModelAndView("redirect:"+path);
    }

    public static void addRedirectAttributes(Object modelAttribute, BindingResult binding, RedirectAttributes redirectAttributes) {
        String modelClassName = modelAttribute.getClass().getSimpleName();
        redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult." + modelClassName, binding);
        redirectAttributes.addFlashAttribute(modelClassName, modelAttribute);
    }

    public static void addRedirectAttributes(Object modelAttribute, String modelClassName, BindingResult binding, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult." + modelClassName, binding);
        redirectAttributes.addFlashAttribute(modelClassName, modelAttribute);
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    public static Map<String, Object> getModelMap(String pageTitle) {
        return getModelMap(pageTitle, null, true);
    }

    public static Map<String, Object> getModelMap(String pageTitle, String message) {
        return getModelMap(pageTitle, message, true);
    }
    
    public static Map<String, Object> getModelMap(String pageTitle, String message, boolean success) {
        Map<String, Object> map = new HashMap<>();
        map.put("title", pageTitle == null ? "---" : pageTitle);
        map.put("Utility", new Utility());
        map.put("DU", new DateUtil());
        map.put("NU", new NumberUtil());
        map.put("message", message);
        map.put("success", success);
        return map;
    }


    static ModelAndView redirectHome(Object controller, String message) {
        ActionResult actionResult = new ActionResult(Boolean.FALSE, message);
        return redirectHome(controller, actionResult);
    }

    static ModelAndView redirectHome(Object controller, ActionResult actionResult) {
        String requestMapping = controller.getClass().getAnnotation(RequestMapping.class).value()[0];
        Map<String, Object> map = getModelMap("TAIMS");
        map.put("success", actionResult.isSuccess());
        map.put("message", actionResult.getMsg());
        return new ModelAndView("redirect:" + requestMapping + "home.se", map);
    }
}
