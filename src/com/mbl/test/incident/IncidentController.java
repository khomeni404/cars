package com.mbl.test.incident;

import com.ibbl.common.service.GenericController;
import com.ibbl.incident.model.Incident;
import com.ibbl.security.service.SessionUtil;
import com.ibbl.util.Constants;
import org.apache.commons.validator.GenericValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Copyright &copy; 2002-2003 Islami Bank Bangladesh Limited
 * <p>
 * Original author: Khomeni
 * Date: 16/05/2017 11:08 AM
 * Last modification by: Khomeni: Khomeni
 * Last modification on 13/08/2017: 13/08/2017 11:08 AM
 * Current revision: 1.0.1: 1.1 $
 * <p>
 * Revision History:
 * ------------------
 */
@Controller
@RequestMapping("/incident/")
public class IncidentController extends GenericController {

    @RequestMapping(value = "/createIncident.ibbl")
    public ModelAndView createIncident(Model model) {
        model.addAllAttributes(getModelMap("Incident Create"));
        if (!model.containsAttribute(Constants.INCIDENT)) {
            Incident incident = new Incident();
            incident.setRefNo(incidentService.maxRefNo() + 1);
            incident.setDetails("This is a test details.");
            incident.setFailureReasons("This is a reason to fails");
            incident.setPreventativeControls("This controls has been implemented. Do u like it !");
            incident.setReportingDate(new Date());
            model.addAttribute(Constants.INCIDENT, incident);
        }

        return new ModelAndView("/incident/incident_create");
    }

    @RequestMapping(value = "/saveIncident.ibbl", method = RequestMethod.POST)
    public ModelAndView saveIncident(@ModelAttribute("incident") @Valid Incident incident,
                                     BindingResult result, RedirectAttributes attributes) throws IOException {

        if (!incident.getHasControllingApparatus()) {
            incident.setFailureReasons("");
        }

        incident.setInitiator(SessionUtil.getSessionUser());
        incident.validate(result);
        if( result.hasErrors() ) {
            addError(incident, result, attributes);
            return new ModelAndView("redirect:/incident/createIncident.ibbl");
        }
        commonService.save(incident);

        return new ModelAndView("redirect:/incident/viewIncident.ibbl?message=Successfully Saved&id="+incident.getId());
    }

    @RequestMapping(value = "/incidentList.ibbl")
    public ModelAndView incidentList(@RequestParam(required = false) String message) {
        Map<String, Object> modelMap = getModelMap("Incident List");
        modelMap.put("message", message);
        modelMap.put("list", commonService.search(Incident.class));
        return new ModelAndView("/incident/incident_list", modelMap);
    }


    @RequestMapping(value = "/viewIncident.ibbl")
    public ModelAndView viewIncident(@RequestParam Long id,
                                     @RequestParam(required = false) String message) {
        Map<String, Object> modelMap = getModelMap("View Incident");
        if (!GenericValidator.isBlankOrNull(message))
            modelMap.put("message", message);
        Incident incident = commonService.get(Incident.class, id);
        modelMap.put("incident", incident);
        //modelMap.put("reviewList", commonService.search(IncidentReview.class, "incident", incident)); // TODO
        return new ModelAndView("/incident/incident_view", modelMap);
    }


    @RequestMapping(value = "/editIncident.ibbl")
    public ModelAndView editIncident(@RequestParam Long id, Model model) {
        model.addAllAttributes(getModelMap("Edit Incident Review List"));
        if (!model.containsAttribute(Constants.INCIDENT)) {
            Incident incident = commonService.get(Incident.class, id);
            model.addAttribute(Constants.INCIDENT, incident);
        }

        return new ModelAndView("incident/incident_edit");
    }



    @RequestMapping(value = "/updateIncident.ibbl", method = RequestMethod.POST)
    public ModelAndView updateIncident(@ModelAttribute("incident") @Valid Incident incident,
                                     BindingResult result, RedirectAttributes attributes) throws IOException {

        if (!incident.getHasControllingApparatus()) {
            incident.setFailureReasons("");
        }

        incident.setInitiator(SessionUtil.getSessionUser());
        incident.validate(result);
        if( result.hasErrors() ) {
            addError(incident, result, attributes);
            return new ModelAndView("redirect:/incident/editIncident.ibbl?id="+incident.getId());
        }
        commonService.update(incident);

        return new ModelAndView("redirect:/incident/viewIncident.ibbl?message=Successfully Updated&id="+incident.getId());
    }

}
