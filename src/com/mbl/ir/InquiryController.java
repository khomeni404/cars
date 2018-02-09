package com.mbl.ir;

import com.ibbl.common.DateUtil;
import com.mbl.common.service.GenericController;
import com.mbl.ir.model.Inquiry;
import com.mbl.ir.model.SubjectData;
import com.mbl.ir.service.InquiryStatus;
import com.mbl.util.Constants;
import net.softengine.bean.SessionUserBean;
import net.softengine.util.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

/**
 * Copyright @ Soft Engine [www.soft-engine.net].
 * Created on 05-Feb-18 at 4:07 PM
 * Created By : Khomeni
 * Edited By : Khomeni &
 * Last Edited on : 05-Feb-18
 * Version : 1.0
 */

@Controller
@RequestMapping("/ir/")
public class InquiryController  extends GenericController {
    @RequestMapping(value = "/viewInquiry.mbl")
    public ModelAndView viewInquiry(@RequestParam Long id) {
        List<SubjectData> subjectDataList1 = commonService.search(SubjectData.class);
        Inquiry inquiry1 = commonService.get(Inquiry.class, id);
        Map<String, Object> map = getModelMap("Inquiry");// new HashMap<>();
        map.put("subjectDataList", subjectDataList1);
        map.put("Inquiry", inquiry1);
        map.put("cibOperator", true);
        map.put("historyList", new ArrayList<>());

        return new ModelAndView("/inquiry/inquiry_view", map);
//        return new ModelAndView("redirect:/auth/login.mbl");
    }

    @RequestMapping(value = "/createInquiry.mbl", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView createInquiry(Model model) {
        model.addAllAttributes(getModelMap("Inquiry Create"));
        if (!model.containsAttribute(Constants.INQUIRY)) {
            Inquiry bean = new Inquiry();// commonService.get(Inquiry.class, "98713586321063938");
            model.addAttribute(Constants.INQUIRY, bean);
        }

        return new ModelAndView("/inquiry/inquiry_create");
    }

    @RequestMapping(value = "/saveInquiry.mbl", method = RequestMethod.POST)
    public ModelAndView saveInquiry(@ModelAttribute("inquiry") @Valid Inquiry inquiry,
                                     BindingResult result, RedirectAttributes attributes) throws IOException {
        inquiry.validate(result);
        if( result.hasErrors() ) {
            addRedirectAttributes(inquiry, result, attributes);
            return new ModelAndView("redirect:/ir/createInquiry.mbl");
        }
        inquiry.setStatus(InquiryStatus.CREATED.CODE);
        inquiry.setCostStatus(Constants.INQ_COST_STATUS_PENDING);
        inquiry.setFinalStatus(Constants.INQ_FIN_STATUS_NOT_REPORTED);
        Date today = new Date();
        inquiry.setLastUpdateDate(today);
        inquiry.setRecordDate(today);
        inquiry.setRequestDate(today);
        inquiry.setYear(String.valueOf(DateUtil.getYear(today)));

        SessionUserBean sessionUserBean = SessionUtil.getSessionUserBean();
        inquiry.setInqNo(inquiryService.getNewInquiryNo(sessionUserBean.getBrCode()));
        inquiry.setBrCode(sessionUserBean.getBrCode());

        inquiry.setCreator(SessionUtil.getSessionUserName());
        boolean saved = commonService.save(inquiry);
        attributes.addAttribute("id", inquiry.getId());
        return new ModelAndView("redirect:/ir/viewInquiry.mbl");
    }


    @RequestMapping(value = "/addBorrower.mbl", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView addBorrower(Model model, @RequestParam String inqFor) {
        model.addAllAttributes(getModelMap("Inquiry Create"));
        if (!model.containsAttribute(Constants.SUBJECT_DATA)) {
            SubjectData bean = commonService.get(SubjectData.class, "98713586321063938");
            model.addAttribute(Constants.SUBJECT_DATA, bean);
        }

        return new ModelAndView("/inquiry/inq_add_borrower_man");
    }
}
