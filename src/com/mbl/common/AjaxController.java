package com.mbl.common;


import com.mbl.common.model.District;
import com.mbl.common.model.PoliceStation;
import com.mbl.common.service.GenericController;
import org.dom4j.Branch;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Encoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Copyright &copy; 2017-2018 Soft Engine Inc.  (www.soft-engine.net)
 * <p>
 * Original author: Khomeni
 * Date: 26/11/2017 4:10 PM
 * Last modification by: Khomeni: Khomeni
 * Last modification on 26/11/2017: 26/11/2017 4:10 PM
 * Current revision: 1.0.0: 1.1 $
 * <p>
 * Revision History:
 * ------------------
 */

@Controller
@RequestMapping("/ajax/")
public class AjaxController extends GenericController {

    private static Encoder encoder = ESAPI.encoder();
    private static String SELECT_OPTION = "<option value=\"\">--Select--</option>";

    @RequestMapping(method = RequestMethod.GET, value = "getDistrictListSelector.se")
    public @ResponseBody
    String getDistrictListSelector(@RequestParam String divisionId) {
        List<District> districtList = commonService.search(District.class, "divisionId", divisionId);
        String s = SELECT_OPTION;
        for (District d : districtList) {
            s += "<option value=\"" + encoder.encodeForHTMLAttribute(d.getId().toString()) + "\">" + encoder.encodeForHTML(d.getName()) + "</option>";
        }
        return s;
    }

    @RequestMapping(method = RequestMethod.GET, value = "getPoliceStationListSelector.se")
    public @ResponseBody
    String getPoliceStationListSelector(@RequestParam Long districtId) {
        List<PoliceStation> policeStationList = commonService.search(PoliceStation.class, "district", "id", districtId);
        String s = SELECT_OPTION;
        for (PoliceStation d : policeStationList) {
            s += "<option value=\"" + encoder.encodeForHTMLAttribute(d.getId().toString()) + "\">" + encoder.encodeForHTML(d.getName()) + "</option>";
        }
        return s;
    }


}
