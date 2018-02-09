package com.mbl.util;

import com.mbl.ir.model.Inquiry;
import com.mbl.ir.model.SubjectData;
import net.softengine.util.PropertyUtil;

import java.util.HashMap;
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


public interface Constants {
    PropertyUtil CONFIG = PropertyUtil.getInstance("local-config");

    Long PROJECT_ID = Long.valueOf(CONFIG.getPropertyValue("PROJECT_ID"));
    String SECRET_KEY = CONFIG.getPropertyValue("SECRET_KEY");
    String USER_AUTH_PATH = CONFIG.getPropertyValue("USER_AUTH_PATH");
    String PROJECT_AUTH_PATH = CONFIG.getPropertyValue("PROJECT_AUTH_PATH");
    String CAAMP_REGISTER_LINK = CONFIG.getPropertyValue("CAAMP_REGISTER_LINK");

    String INQUIRY = Inquiry.class.getSimpleName();
    String SUBJECT_DATA = SubjectData.class.getSimpleName();


    String DOC_PATH = CONFIG.getPropertyValue("DOC_PATH");
    String DOC_SRC_LEADER = CONFIG.getPropertyValue("DOC_SRC_LEADER");
    String BACK_UP_DOC_URI = CONFIG.getPropertyValue("BACK_UP_DOC_URI");

    Integer MIN_FILE_SIZE = Integer.valueOf(CONFIG.getPropertyValue("MIN_FILE_SIZE"));
    Integer MAX_FILE_SIZE = Integer.valueOf(CONFIG.getPropertyValue("MAX_FILE_SIZE"));

    String OTP_PASSPHRASE_PREFIX = "sTUDENT$%cEll&$$#*Number#";
    String CONSTANT_MAP_GENERIC = "java.util.Map<java.lang.Integer, java.lang.String>";


    Integer INQ_CR_MODE_INST = 1;
    Integer INQ_CR_MODE_NON_INST = 2;
    Integer INQ_CR_MODE_CARD = 3;

    Integer INQ_COST_STATUS_PENDING = 0;
    Integer INQ_COST_STATUS_POSTED = 1;

    Integer INQ_FIN_STATUS_NOT_REPORTED = 10;
    Integer INQ_FIN_STATUS_REPORTED = 11;
    Integer INQ_FIN_STATUS_REJECTED = 12;

}
