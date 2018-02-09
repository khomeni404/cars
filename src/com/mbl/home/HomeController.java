package com.mbl.home;

import com.mbl.common.service.GenericController;
import net.softengine.util.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;


/**
 * Copyright &copy; 2017-2018 Soft Engine Inc.
 * <p>
 * Original author: Khomeni
 * Date: 21/11/2017 12:38 PM
 * Last modification by: Khomeni: Khomeni
 * Last modification on 21/11/2017: 21/11/2017 12:38 PM
 * Current revision: 1.0.0: 1.1 $
 * <p>
 * Revision History:
 * ------------------
 */


@Controller
@RequestMapping(value = {"/home/", "/"}, method = {RequestMethod.GET, RequestMethod.HEAD})
public class HomeController extends GenericController {

    @RequestMapping(value = "/home.mbl", method = RequestMethod.HEAD)
    public ModelAndView home() {
//        return new ModelAndView("redirect:/home/dashboard.mbl");
        return new ModelAndView("redirect:/auth/login.mbl");
    }

    @RequestMapping(value = {"/dashboard.mbl", "/"})
    public ModelAndView dashboard(@RequestParam(required = false) String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("title", "IBBL Scholarship Program");
        map.put("message", message);


        //return new ModelAndView("/template/dashboard", map);
        return new ModelAndView("/template/dashboard", map);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/logout.mbl")
    public ModelAndView logout(@RequestParam(required = false) String message) {
        SessionUtil.invalidate();
        // return new ModelAndView("redirect:/auth/login.mbl", "message", message);
        return new ModelAndView("redirect:/");
    }
}
