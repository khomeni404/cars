package com.mbl.home;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mbl.util.Constants;
import net.softengine.util.SecurityUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;


/**
 * Copyright &copy; 2017-2018 Soft Engine Inc.
 * <p>
 * Original author: Khomeni
 * Date: 16/5/2017 1:56 PM
 * Last modification by: Khomeni: Khomeni
 * Last modification on 16/5/2017: 16/5/2017 2:50 PM
 * Current revision: 1.0.0: 1.1 $
 * <p>
 * Revision History:
 * ------------------
 */

@Component
public class BootstrapEngine {


    @PostConstruct
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    private void loadGroupActions() {
        try {
            String url = Constants.PROJECT_AUTH_PATH+"?key="+ Constants.PROJECT_ID; // "?key=nr/lNAET94A=";
            String response = SecurityUtil.responseFromGET(url);
            Gson gson = new Gson();

            Type type = new TypeToken<Map<Long, List<String>>>(){}.getType();
            // Map<Long, List<String>> m = g.fromJson(response, type);
            SecurityUtil.GROUP_ACTION_LIST_MAP = gson.fromJson(response, type);

            System.out.println(SecurityUtil.GROUP_ACTION_LIST_MAP.size());
        } catch (Exception e) {
            System.out.println("Group-Actions can't initialized. May be CAAMP is Down. Or wrong configuration in local-config.properties");
            // throw e; // todo active this line to prevent run this on exception
        }
    }

}
