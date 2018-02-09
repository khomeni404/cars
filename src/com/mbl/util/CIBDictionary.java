package com.mbl.util;

import java.util.*;

/**
 * Copyright (C) 2002-2003 Mercantile Bank Limited
 * <p>
 * Original author: Khomeni
 * Date: 10/18/12(10:40 AM)
 * Last modification by: $Author: ayat $
 * Last modification on $Date: 2017/12/20 05:46:57 $
 * Current revision: $Revision: 1.21 $
 * <p>
 * Revision History:
 * ------------------
 */
public class CIBDictionary {

    public static final String CIB_CONFIG_STATE = PropertyUtil.get("CIB_CONFIG_STATE");
    public static final String DOC_PATH;
    public static final String DATA_FILE_PATH;
    public static final String DEPOSIT_URL;

    public static final String REMOTE_CHARGE_USER_ID = PropertyUtil.get("REMOTE_CHARGE_USER_ID");
    public static final String REMOTE_CHARGE_PASSWORD = PropertyUtil.get("REMOTE_CHARGE_PASSWORD");
    public static final String REMOTE_CHARGE_SECRET_KEY = PropertyUtil.get("REMOTE_CHARGE_SECRET_KEY");

    public static final int MAX_NUMBER_OF_EXIM_THREAD;
    public static final int EXIM_TASK_QUEUE_INTERVAL;
    public static final long MINIMUM_FREE_RUNTIME_MEMORY;
    public static final long THRESHOLD_USED_RUNTIME_MEMORY;

    static {
        if (CIB_CONFIG_STATE.equals(FinalData.CIB_CONFIG_STATE_LIVE)) {
            DOC_PATH = PropertyUtil.get("CIB_RPT_DOC_PATH_LIVE");
            DATA_FILE_PATH = PropertyUtil.get("CIB_DATA_FILE_PATH_LIVE");
            DEPOSIT_URL = PropertyUtil.get("DEPOSIT_URL_LIVE");
            MAX_NUMBER_OF_EXIM_THREAD = Integer.parseInt(PropertyUtil.get("MAX_NUMBER_OF_EXIM_THREAD_LIVE"));
            EXIM_TASK_QUEUE_INTERVAL = Integer.parseInt(PropertyUtil.get("EXIM_TASK_QUEUE_INTERVAL_LIVE"));
            MINIMUM_FREE_RUNTIME_MEMORY = Long.parseLong(PropertyUtil.get("MINIMUM_FREE_RUNTIME_MEMORY_LIVE"));
            THRESHOLD_USED_RUNTIME_MEMORY = Long.parseLong(PropertyUtil.get("THRESHOLD_USED_RUNTIME_MEMORY_LIVE"));
        } else {
            DOC_PATH = PropertyUtil.get("CIB_RPT_DOC_PATH_TEST");
            DATA_FILE_PATH = PropertyUtil.get("CIB_DATA_FILE_PATH_TEST");
            DEPOSIT_URL = PropertyUtil.get("DEPOSIT_URL_TEST");
            MAX_NUMBER_OF_EXIM_THREAD = Integer.parseInt(PropertyUtil.get("MAX_NUMBER_OF_EXIM_THREAD_TEST"));
            EXIM_TASK_QUEUE_INTERVAL = Integer.parseInt(PropertyUtil.get("EXIM_TASK_QUEUE_INTERVAL_TEST"));
            MINIMUM_FREE_RUNTIME_MEMORY = Long.parseLong(PropertyUtil.get("MINIMUM_FREE_RUNTIME_MEMORY_TEST"));
            THRESHOLD_USED_RUNTIME_MEMORY = Long.parseLong(PropertyUtil.get("THRESHOLD_USED_RUNTIME_MEMORY_TEST"));
        }

    }


    public static final String CUST_ENQUIRY_INVALID = "INVALID";
    public static final String CIB_RESPONSE_ERR = "CIB_ERR";
    public static final String CIB_NOT_RESPONSED = "NOT_RESPONSED";
    public static final String ERROR_TITLE = "error_title";
    public static final String ERROR_GENERAL = "errors.general";


    public static final int REPAY_INSTALLMENT = 1001;
    public static final int REPAY_NON_INSTALLMENT = 1002;
    public static final int REPAY_CARD = 1003; // 1002;

    public static final int INTERVAL_UNIT_UNKNOWN = 3000;
    public static final int INTERVAL_UNIT_DAY = 3001;
    public static final int INTERVAL_UNIT_MONTH = 3002;
    public static final int INTERVAL_UNIT_YEAR = 3003;


    public static final String ACTIVE = "Active";
    public static final String INACTIVE = "Inactive";


    public static final String[][] MONTH_ARRAY_2D = {
            {"01", "January"},
            {"02", "February"},
            {"03", "March"},
            {"04", "April"},
            {"05", "May"},
            {"06", "June"},
            {"07", "July"},
            {"08", "August"},
            {"09", "September"},
            {"10", "October"},
            {"11", "November"},
            {"12", "December"},
    };
    public static final Map<String, String> MONTH_LIST_MAP = new HashMap<String, String>() {{
        put("01", "January");
        put("02", "February");
        put("03", "March");
        put("04", "April");
        put("05", "May");
        put("06", "June");
        put("07", "July");
        put("08", "August");
        put("09", "September");
        put("10", "October");
        put("11", "November");
        put("12", "December");
    }};

    /**
     * Report Inquiry Arr_01 : StatusIndex, StatusName
     */
    public static final Integer CIB_COST_STATUS_PENDING = 0;
    public static final Integer CIB_COST_STATUS_DEBITED = 1;
    public static final String[][] CIB_COST_STATUS_ARR = {{"", ""}};
    public static final List<String> CIB_COST_STATUS_LIST = new ArrayList<String>() {{
        add(CIB_COST_STATUS_PENDING, "Pending");
        add(CIB_COST_STATUS_DEBITED, "Debited");
        add(2, "Refunded");
        add(3, "Reported");
    }};

    public static final Integer INQ_FINAL_STATUS_NOT_REPORTED = 10;
    public static final Integer INQ_FINAL_STATUS_REPORTED = 11;
    public static final Integer INQ_FINAL_STATUS_REJECTED = 12;
    public static final Map<Integer, String> INQ_FINAL_STATUS_LIST = new HashMap<Integer, String>() {{
        put(INQ_FINAL_STATUS_NOT_REPORTED, "Not Reported");
        put(INQ_FINAL_STATUS_REPORTED, "Reported");
        put(INQ_FINAL_STATUS_REJECTED, "Rejected");
    }};

    public static final Integer CIB_OPERATOR_ACTIVE = 1;
    /**
     * 0 = Record Operator, 1 = Activate / Deactivate,  2 = Branch Assignment
     * CIB_OPERATOR_HIST_LOG Array
     */
    public static final String[][] CIB_OPERATOR_HIST_LOG = {
            {"0", "Record Operator"},
            {"1", "Activate / Deactivate"},
            {"2", "Branch Assignment"}
    };

    public static final String[][] CIB_INQ_TYPE = {
            {"0", "New"},
            {"1", "Renewal"},
            {"2", "Enhancement"},
            {"3", "Others"}
    };


    public static void main(String[] args) {
        System.out.println("CIB_INQUIRY_STATUS_ARR[0][0] = " + RectifyUtil.cutOrPadRight(BB_CONTRACT_STATUS.get(21), 1));
        /*for (int i = 0; i < CIB_INQUIRY_STATUS_ARR.length; i++) {
            System.out.println("CIB_INQUIRY_STATUS_ARR[0][1] = "+ CIB_INQUIRY_STATUS_ARR[i][0]+" : " + CIB_INQUIRY_STATUS_ARR[i][1]);

        }*/
    }

    /**
     * CIB-BB Defined Contract Status
     * Note: Inv Acc State as Map Key
     * e.g. CIBDictionary.BB_CONTRACT_STATUS.get(accState)
     */
    public static Map<Integer, String> BB_CONTRACT_STATUS = new HashMap<Integer, String>() {{
        put(FinalData.ACC_STATE_REGULAR, "");
        put(FinalData.ACC_STATE_OVER_DUE, "");
        put(FinalData.ACC_STATE_SS, "S");
        put(FinalData.ACC_STATE_DF, "D");
        put(FinalData.ACC_STATE_BL, "B");
        put(FinalData.ACC_STATE_SM, "M");
        put(FinalData.ACC_STATE_WRITE_OFF, "W");
    }};

    /**
     * BB, CIB Defined (1-11) Customer Role. Ref table 4.3.14
     * IB Defined (20,98,99)
     */
    public static Map<Integer, String> CUSTOMER_ROLE_RELATION_MAP = new HashMap<Integer, String>() {{
        put(0, "N/A");
        put(1, "Chairman");
        put(2, "Managing director");
        put(3, "Sponsor director");
        put(4, "Elected director");
        put(5, "Nominated director (by Govt)");
        put(6, "Nominated director (by Pvt. Institution)");
        put(7, "Share Holder");
        put(8, "Owner company (Corporate Shareholder)");
        put(9, "Partner");
        put(10, "Owner of Proprietorship");
        put(11, "Others");
        put(20, "Guarantor");
        put(98, "Borrower");
        put(99, "Co-Borrower");
    }};


    public static final String[][] CIB_FIN_TYPE = {{"42", "Bai-Murabaha"}, {"43", "HPSM"}, {"44", "Musharaka"}, {"45", "Bai-Salam"}, {"46", "QTDR"}, {"47", "MPI"}, {"48", "Mudaraba"}, {"49", "Bai-Murabaha (TR)"}, {"51", "QBF"}, {"52", "QPF"}, {"53", "QH Staff"}, {"54", "QH General"}, {"55", "QSCA"}, {"56", "QMSS"}, {"57", "BAI ISTIJRAR"}, {"58", "ISTISNA"}, {"59", "QMNSB"}, {"60", "QMSB"}, {"61", "HPSM_FC"}, {"62", "MDB_FC (OBU)"}, {"63", "MURA UPAS"}, {"64", "HPSM UPAS"}, {"76", "Khidmah"}, {"81", "Bai as Sarf"}, {"82", "IBP"}, {"83", "Bai-Murabaha (BOE)"}, {"84", "MIB"}, {"85", "Bai-Muajjal BB Bills"}, {"86", "MDB (Inland)"}, {"87", "IJARA"}, {"88", "MFCI"}, {"89", "MDB (Foreign)"}};

    public static final String CIB_RPT_ALLOWED_FILE_TYPE = ".pdf, .zip";  // e.g.: .pdf, .jpg, .jpeg
    public static final List<String> CIB_RPT_ALLOWED_FILE_TYPE_LIST = Arrays.asList("pdf", "zip");
    public static final int CIB_RPT_ALLOWED_FILE_SIZE = 50; // in MB, note: 1 MB = 1000000 byte,

    public static final String CIB_USER_SUPER = "Super";
    public static final String CIB_USER_GENERAL = "General";

    //    public static final String CIB_UPLOAD_KEY_UNDERTAKE = "undertake";
    public static final String CIB_UPDATE_KEY_RESOLVE = "resolve";
    //    public static final String CIB_UPLOAD_KEY_CIB_RPT = "cib_report";
    public static final String CIB_DOC_NAME_UNDERTAKING = "Undertaking";
    public static final String CIB_DOC_NAME_CIB_REPORT = "CIB Report";


    public static final Integer EXIM_STATUS_CODE_eIBS_CON_PROB = 11;
    public static final Integer EXIM_STATUS_CODE_EXPORTED = 12;
    public static final Integer EXIM_STATUS_CODE_RECTIFIED_FINAL = FinalData.RECTIFICATION_LEVEL_CODE_FINAL;
    public static final Integer EXIM_STATUS_CODE_RECTIFIED_LEVEL_1 = FinalData.RECTIFICATION_LEVEL_CODE_LEVEL_1;
    public static final Integer EXIM_STATUS_CODE_RECTIFIED_LEVEL_2 = FinalData.RECTIFICATION_LEVEL_CODE_LEVEL_2;
    public static final Integer EXIM_STATUS_CODE_RECTIFIED_LEVEL_3 = FinalData.RECTIFICATION_LEVEL_CODE_LEVEL_3;
    public static final Integer EXIM_STATUS_CODE_SENT = 14;
    public static final Integer EXIM_STATUS_CODE_ARCHIVED = 15;

    public static final Map<Integer, String> EXIM_STATUS_CODE_MAP = new HashMap<Integer, String>() {{
        put(10, "");
        put(EXIM_STATUS_CODE_eIBS_CON_PROB, "Failed to connect eIBS.");
        put(EXIM_STATUS_CODE_EXPORTED, "Data exported successfully.");
        put(EXIM_STATUS_CODE_RECTIFIED_FINAL, "Data Rectified (Finally).");
        put(EXIM_STATUS_CODE_RECTIFIED_LEVEL_1, "Data Rectified (Level 1).");
        put(EXIM_STATUS_CODE_RECTIFIED_LEVEL_2, "Data Rectified (Level 2).");
        put(EXIM_STATUS_CODE_RECTIFIED_LEVEL_3, "Data Rectified (Level 3).");
        put(EXIM_STATUS_CODE_SENT, "Data Sent.");
        put(EXIM_STATUS_CODE_ARCHIVED, "Data Archived.");
    }};

    public static final Map<Integer, String> ID_TYPE = new HashMap<Integer, String>() {{
        put(FinalData.ID_TYPE_PASS, "Passport");
        put(FinalData.ID_TYPE_DL, "Driving License");
        put(FinalData.ID_TYPE_BRC, "Birth registration Certificate");
    }};


    public static final Map<Integer, String> ERROR_TYPE = new HashMap<Integer, String>() {{
        put(FinalData.EXIM_DATA_CLEAN, "Clean Data");
        put(FinalData.EXIM_DATA_ERROR, "Error in Data");
        put(FinalData.EXIM_DATA_MASTER_ERROR, "Error in Master Data");
        put(FinalData.EXIM_DATA_GROUP_ERROR, "Error in Group Data");
        put(FinalData.EXIM_DATA_EXIM_ERROR, "Error on Import/Export");
    }};


    /**
     * Our defined Error Code range 11 - 99
     * CIB, BB defined error Code range 101 - 1329
     */
    public static final Map<Integer, String> ERROR_CODE_MAP = new HashMap<Integer, String>() {{
        put(FinalData.ERROR_CODE_IMPORT_FAILED_11, "Import Failed");
        put(FinalData.ERROR_CODE_IMPORT_FAILED_GD, "Failed to import general data");
        put(FinalData.ERROR_CODE_IMPORT_FAILED_PD, "Failed to import Personal data");
        put(FinalData.ERROR_CODE_IMPORT_FAILED_IDF, "No individual details found");
        put(FinalData.ERROR_CODE_IMPORT_FAILED_CIDF, "Failed to import company/Institution details");
        put(FinalData.ERROR_CODE_NC_UN_EXPECTED_WORD, "Name Contains Unexpected Word/Character.");
        put(FinalData.ERROR_CODE_NC_UN_EXPECTED_CHAR, "Name Contains Unexpected Character.");
        put(FinalData.ERROR_CODE_MISS_MATCH_NID_N_DOB, "Birth Year and NID matching invalid.");
        put(FinalData.ERROR_CODE_MANDATORY_DATA_NOT_FOUND, "No value found in a/some mandatory field(s)");
        put(FinalData.ERROR_CODE_NO_GRP_MEMBER_FOUND, "No Group Member Found !");
        put(FinalData.ERROR_CODE_IMPERFECT_CON_DATA, "Imperfect Contract Data.");
        put(FinalData.ERROR_CODE_SUSPICIOUS_INST_NOS, "Suspicious Installment number. May be bigger than 350.");
        put(FinalData.ERROR_CODE_UNDER_LIMIT, "Under Limit Credit. Min Credit Limit : " + FinalData.MIN_CREDIT_LIMIT);
        put(FinalData.ERROR_CODE_OTHERS, "Others / Error not Specified.");
    }};


}