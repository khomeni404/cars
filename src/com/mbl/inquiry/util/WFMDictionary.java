package com.mbl.inquiry.util;

import com.ibbl.cib.common.util.CIBConfig;

import java.io.File;

/**
 * package ibbl.law.util;
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p/>
 * Original author: Reza
 * Date: 18/08/2015} 11:05 AM
 * Last modification by: core-khomeni:
 * Last modification on 18/08/2015:
 * Current revision: 1.0
 * <p/>
 * Revision History:
 * ------------------
 */
public interface WFMDictionary {

    public static final String DOC_PATH = CIBConfig.getInstance().getProperty("LEGAL_DOC_PATH");


    public static final String SEPERATOR = File.separator;
    public static final String DIR_SEPERATOR = File.separator;
    public static final String FILE_SEPERATOR = File.separator;

    public static final String PRODUCT_CODE_IILC = "IILC";
    public static final String PRODUCT_CODE_IFLC = "IFLC";
    public static final String PRODUCT_CODE_EILC = "EILC";
    public static final String PRODUCT_CODE_EFLC = "EFLC";
    public static final String PRODUCT_CODE_CTIN = "CTIN";

    public static final String TASK_CODE_IILCOP = "IILCOP";
    public static final String TASK_CODE_IFLCOP = "IFLCOP";
    public static final String TASK_CODE_EILCOP = "EILCOP";
    public static final String TASK_CODE_IILCBP = "IILCBP";
    public static final String TASK_CODE_IFLCBP = "IFLCBP";
    public static final String TASK_CODE_EIILCBP = "EIILCBP";
    public static final String TASK_CODE_EFLCBP = "EFLCBP";

    //.........
    public static final String DISCREPANCY_CODE = "1";

    public static final String TIN = "TIN";
    public static final String FILE_FORMAT_JPG = "jpg";
    public static final String FILE_FORMAT_PDF = "pdf";

    public static final String ACC_NOV_VERSION = "2";
    public static final String COUNTRY_CODE = "50";

    public static final String BR_CODE_IBW_FMD = CIBConfig.getInstance().getProperty("BR_CODE_IBW_FMD");

    public static final String APP_URL = CIBConfig.getInstance().getProperty("APP_URL_FILE");
    public static final String APP_URL_MAIN = CIBConfig.getInstance().getProperty("APP_URL");
    public static final String DOC_FILE_PATH_ARCHIVE_SUB_DIR = CIBConfig.getInstance().getProperty("DOC_FILE_PATH_ARCHIVE_SUB_DIR");
    public static final String DOC_FILE_PATH_TRACK_SUB_DIR = CIBConfig.getInstance().getProperty("DOC_FILE_PATH_TRACK_SUB_DIR");
    public static final String DOC_FILE_PATH_ARCHIVE = CIBConfig.getInstance().getProperty("DOC_FILE_PATH_ARCHIVE") + DOC_FILE_PATH_ARCHIVE_SUB_DIR;
    public static final String FILE_PATH_DOC_ARCHIVE = CIBConfig.getInstance().getProperty("FILE_PATH_DOC_ARCHIVE") + DOC_FILE_PATH_ARCHIVE_SUB_DIR;
    public static final String DOC_FILE_PATH_TRACK = CIBConfig.getInstance().getProperty("DOC_FILE_PATH_TRACK") + DOC_FILE_PATH_TRACK_SUB_DIR;
    public static final String XML_FILE_PATH = CIBConfig.getInstance().getProperty("XML_FILE_PATH");
    public static final String TMP_DIR = CIBConfig.getInstance().getProperty("TMP_PATH");

    //..............MODULE ID..............................
    public static final String MODULE_ID_GB = "GB";
    public static final String MODULE_ID_FEX = "FEX";
    public static final String MODULE_ID_INV = "INV";
    public static final String MODULE_ID_SWIFT = "SWIFT";
    public static final String MODULE_ID_WFM = "WFM";
    public static final String MODULE_ID_MIS = "MIS";
    //...................user group.................................
    public static final String USER_GROUP_BR = "BRN";
    public static final String USER_GROUP_SCR = "SCR";
    public static final String USER_GROUP_MKR = "MKR";
    public static final String USER_GROUP_CHK = "CHK";
    public static final String USER_GROUP_ADB = "ADB";
    public static final String USER_GROUP_SMKR = "SMK";

    public static final int LEN_TRACK_NO = 15;
    public static final int LEN_DOC_FILE_NAME_TRACK = 27;
    public static final int LEN_DOC_FILE_NAME_ARCHIVE = 33;
    public static final int LEN_PRODUCT_CODE = 4;
    public static final int LEN_DOC_CODE = 8;
    public static final int LEN_BR_CODE = 3;
    public static final int LEN_FILE_EXTN_WITH_DOT = 4;
    public static final int LEN_FILE_EXTN_WITHOUT_DOT = 3;

    //...................ARCHIVE CUST CATEGORY......................
    public static final String CUST_CATEGORY_OWN = "OWN";
    public static final String CUST_CATEGORY_FOREIGN = "FRN";
    public static final String CUST_CATEGORY_OTHER = "OTR";
    //.......................LIST OF ARCHIVE CONTENT GROUP.........................
    public static final String ARCH_CONTENT_GROUP_LCA = "LCA";
    public static final String ARCH_CONTENT_GROUP_CRA = "CRA";
    public static final String ARCH_CONTENT_GROUP_RCA = "RCA";
    public static final String ARCH_CONTENT_GROUP_ODA = "ODA";
    public static final String ARCH_CONTENT_GROUP_CSA = "CSA";
    public static final String ARCH_CONTENT_GROUP_SAA = "SAA";
    public static final String ARCH_CONTENT_GROUP_CCA = "CCA";

    // ARCHIVE DOCUMENT NATURE#################################
    public static final String ARCH_CONTENT_TYPE_CR_RPT = "00000099"; //CREDIT REPORT
    //.......................................................................
    public static final String SCAN_MOD_SIMPLEX = "1";
    public static final String SCAN_MOD_DUPLEX = "2";

    /**
     * The doc type input out
     */
    public static final String DOC_FLOW_TYPE_INPUT = "IN";
    public static final String DOC_FLOW_TYPE_OUTPUT = "OUT";

    //VIEW SIGNCARD
    public static final String KEY_VIEW_SIGNCARD = "viewSignCard";
    public static final String KEY_VIEW_SIGNCARD_ACC = "viewSignCardAccount";
    public static final String KEY_VIEW_SIGNCARD_ACC_NO = "viewSignCardAccNo";

}
