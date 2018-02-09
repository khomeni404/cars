package com.mbl.util;

import java.io.File;
import java.util.*;

/**
 * Copyright (C) 2002-2003 Mercantile Bank Limited
 * <p>
 * Original author: Ayatullah Khomeni
 * <br/> Date: 24/05/2016
 * <br/> Last modification by: ayat $
 * <br/> Last modification on 24/05/2016: 10:52 AM
 * <br/> Current revision: : 1.1.1.1
 * </p>
 * Revision History:
 * ------------------
 */
public interface FinalData {
    String DATA_JOIN_SEPARATOR = "";
    String DATA_TYPE_SUB = "sub";
    String DATA_TYPE_CON = "con";
    String DATA_TYPE_LINK = "link";
    String SUB_FILE_NAME = "042SJF.txt";
    String CON_FILE_NAME = "042CNF.txt";
    String ZIP_FILE_NAME_EXTENSION = "_042.zip";
    boolean TN_USE_BR = true;  //IS 'thread name' use br-code as suffix ?
    String THREAD_SUFFIX_EXIM_SUB = "_sub"; //Thread Suffix
    String THREAD_SUFFIX_EXIM_CON = "_con";
    String TN_SUB_WRITER = "thread_name_sd_writer";
    String TN_CON_WRITER = "thread_name_cd_writer";
    String TN_EXIM_TASK_QUEUE = "exim_task_queue";
    Double MIN_CREDIT_LIMIT = 50000.0;
    String CIB_CONFIG_STATE_TEST = "TEST";
    String CIB_CONFIG_STATE_LIVE = "LIVE";


    /*String BB_ERROR_FILE_TYPE_CTA = "1";
    String BB_ERROR_FILE_TYPE_CTC = "2";
    String BB_ERROR_FILE_TYPE_CTL = "3";*/

    Integer BB_ERROR_DATA_TYPE_SUB = 0;
    Integer BB_ERROR_DATA_TYPE_CON = 1;
    Integer BB_ERROR_DATA_TYPE_LINK = 2;
    List<String> BB_ERROR_DATA_TYPE_LIST = new ArrayList<String>() {{
        add(BB_ERROR_DATA_TYPE_SUB, "Subject Data");
        add(BB_ERROR_DATA_TYPE_CON, "Contract Data");
        add(BB_ERROR_DATA_TYPE_LINK, "Link Data");
    }};

    String COUNTRY_CODE_NULL = "0";
    String COUNTRY_CODE_BD = "BD";
    String COUNTRY_NAME_BD = "BANGLADESH";

    Integer REL_GUARANTOR = 20;
    Integer REL_BORROWER = 98;
    Integer REL_CO_BORROWER = 99;


    String CTX = "cribs";
    String LINK_BB_DIST_LIST = "<a href=\"/" + CTX + "/cibBBDistList.do\">District List</a>";

    String FAD_BR_CODE = "101";
    List<String> CIB_AUTH_BR_CODES = Arrays.asList(/*"0", "00", "000"*/"889", "041", "042", "043", "055");
    Integer ACC_STATE_REGULAR = 1;
    Integer ACC_STATE_TRANSFERRED_OUT = 9;
    Integer ACC_STATE_CLOSED = 10;
    Integer ACC_STATE_OVER_DUE = 16;
    Integer ACC_STATE_SS = 17;
    Integer ACC_STATE_DF = 18;
    Integer ACC_STATE_BL = 19;
    Integer ACC_STATE_SM = 21;
    Integer ACC_STATE_WRITE_OFF = 22;
    List<Integer> CLASSIFIED_STATE_LIST = Arrays.asList(ACC_STATE_SS, ACC_STATE_DF, ACC_STATE_BL, ACC_STATE_WRITE_OFF);
    List<Integer> NON_CLASSIFIED_STATE_LIST = Arrays.asList(ACC_STATE_REGULAR, ACC_STATE_OVER_DUE, ACC_STATE_TRANSFERRED_OUT, ACC_STATE_CLOSED, ACC_STATE_SM);


    String INV_ACC_TYPE_LC = "LC";
    String INV_ACC_TYPE_BG = "GU";

    /**
     * Remind that, the record line type is 'D' for all Contract.
     * Here we use I, N, L, B, C is to identify the the contracts.
     * So. I, N, L, B, C all is D
     */
    String REC_TYPE_CUSTOMIZED_INST = "I";
    String REC_TYPE_CUSTOMIZED_NONI = "N";
    String REC_TYPE_CUSTOMIZED_LC = "L";
    String REC_TYPE_CUSTOMIZED_BG = "B";
    String REC_TYPE_CUSTOMIZED_CARD = "C";


    Integer EXIM_DATA_CLEAN = 10;
    Integer EXIM_DATA_ERROR = 11;
    Integer EXIM_DATA_MASTER_ERROR = 12;
    Integer EXIM_DATA_GROUP_ERROR = 13;
    Integer EXIM_DATA_EXIM_ERROR = 14;
    Integer EXIM_DATA_UN_IDENTIFY = 15;

    Integer ERROR_CODE_IMPORT_FAILED_11 = 11;
    Integer ERROR_CODE_IMPORT_FAILED_GD = 12;
    Integer ERROR_CODE_IMPORT_FAILED_PD = 13;
    Integer ERROR_CODE_IMPORT_FAILED_IDF = 14;
    Integer ERROR_CODE_IMPORT_FAILED_CIDF = 15;
    Integer ERROR_CODE_NC_UN_EXPECTED_WORD = 16;
    Integer ERROR_CODE_NC_UN_EXPECTED_CHAR = 17;
    Integer ERROR_CODE_MISS_MATCH_NID_N_DOB = 18;
    Integer ERROR_CODE_MANDATORY_DATA_NOT_FOUND = 19;
    Integer ERROR_CODE_NO_GRP_MEMBER_FOUND = 20;
    Integer ERROR_CODE_IMPERFECT_CON_DATA = 21;
    Integer ERROR_CODE_SUSPICIOUS_INST_NOS = 22;
    Integer ERROR_CODE_UNDER_LIMIT = 23;
    Integer ERROR_CODE_OTHERS = 99;
    Integer ERROR_CODE_BB_1116 = 1116;
    Integer ERROR_CODE_BB_1103 = 1103;

    Integer EXIM_DATA_STATUS_NOT_SENT = 20;
    Integer EXIM_DATA_STATUS_SENT = 21;
    Integer EXIM_DATA_STATUS_ARCH = 22;

    Integer EXIM_DATA_STATUS_WRITE = 31;
    Integer EXIM_DATA_STATUS_NOT_WRITE = 30;

    Integer EXIM_DATA_STATUS_EDITED = 40;
    Integer EXIM_DATA_STATUS_UPDATED = 41;

    Integer RECTIFICATION_LEVEL_CODE_LEVEL_1 = 51;
    Integer RECTIFICATION_LEVEL_CODE_LEVEL_2 = 52;
    Integer RECTIFICATION_LEVEL_CODE_LEVEL_3 = 53;
    Integer RECTIFICATION_LEVEL_CODE_LEVEL_4 = 54;
    Integer RECTIFICATION_LEVEL_CODE_LEVEL_5 = 55;
    Integer RECTIFICATION_LEVEL_CODE_FINAL = 56;

    Map<Integer, String> RECTIFICATION_LEVEL_CODE_MAP = new HashMap<Integer, String>() {{
        put(RECTIFICATION_LEVEL_CODE_LEVEL_1, "Initial Rectification on ExIm.");
        put(RECTIFICATION_LEVEL_CODE_LEVEL_2, "Rectification for Imperfect Sub.");
        put(RECTIFICATION_LEVEL_CODE_LEVEL_3, "Rectification for Data Eligibility.");
        put(RECTIFICATION_LEVEL_CODE_LEVEL_4, "Rectification for Credit Limitation.");
        put(RECTIFICATION_LEVEL_CODE_LEVEL_5, "Rectification for Imperfect Con.");
        put(RECTIFICATION_LEVEL_CODE_FINAL, "Final Rectification");
    }};

    Integer SUB_DATA_NOT_ELIGIBLE = 90;
    Integer DATA_ELIGIBLE = 91;
    Integer SUB_DATA_LINK_ELIGIBLE = 92;
//    Integer CON_DATA_LINK_ELIGIBLE = 93;
//    Integer CON_DATA_ELIGIBLE = 96;
//    Integer CON_DATA_NOT_ELIGIBLE = 96;
//    Integer SUB_DATA_NEW_DIRECTOR = 97;


    String SECTOR_TYPE_PUBLIC = "1"; // BB Defined
    String SECTOR_TYPE_PRIVATE = "9"; // BB Defined
    String SECTOR_CODE_DEFAULT_PUBLIC = "112099"; // Defined by Mizan Sir
    String SECTOR_CODE_DEFAULT_PRIVATE = "915059"; // Defined by Mizan Sir  // in Old System DEFAULT_IB_SECTOR_CODE= "08";
    String SECURITY_CODE_DEFAULT_BB = "079"; // From Old System
    String EP_CODE_DEFAULT_BB = "9909"; // From Old System

    Integer ID_TYPE_PASS = 1;
    Integer ID_TYPE_DL = 2;
    Integer ID_TYPE_BRC = 3;

    Integer NID_LENGTH_OLD_13 = 13;
    Integer NID_LENGTH_NEW_17 = 17;
    Integer NID_NOT_AVAILABLE = 0;
    Integer NID_AVAILABLE = 1;


    static void main(String[] args) {

        // System.out.println(str1.toUpperCase().contains(str2.toUpperCase()));

        String country = "BORGUNA";
        checkDistrict("District = " + country);

        String str2 = "";
        String str3 = "SCHOOL OF ACES,UNIVERSITY OF SOUTHEREN QUEENSLAND, TOOWOOMBA, Q4350, AUSTRALIA,";
        String str1 = "MUSLIM WORLD LEAGUE,P.O.BOX NO -538,MAKKA ,KSA";
        String[] sa = str3.split("[ ]");
        System.out.println("sa = " + sa.length);
        for (String text : sa) {
            System.out.println(text);
            if (text.length() > 2) {
                checkCountry(text);
            }
        }
    }

    static void checkDistrict(String text) {
        text = text.toUpperCase();
        String rectifiedName = "Not Found !";
        for (List<String> l : DISTRICT_LIST_COMPLEX) {
            if (l.contains(text) || text.startsWith(l.get(2))) {
                rectifiedName = l.get(0) + "-" + l.get(1);
                break;
            }
        }
        System.out.println(rectifiedName);
    }

    static void checkCountry(String text) {
        text = text.toUpperCase();
        String rectifiedName = "Not Found !";
        for (List<String> l : COUNTRY_LIST_COMPLEX) {
            if (l.contains(text) || text.startsWith(l.get(2))) {
                rectifiedName = l.get(0) + "-" + l.get(1);
                break;
            }
        }
        System.out.println(rectifiedName);
    }

    List<String> SUSPICIOUS_TEXT_LIST = Arrays.asList(
            " ", "-", "--", ".", "..", "?", "X", "XX", "XXX", "NO",
            "NULL", "NONE", "EMPTY",
            "MOTHER", "FATHER", "NAME",
            "OK", "YES", "FINE"
    );

    List<String> FORBIDDEN_WORD_LIST = Arrays.asList("LATE", "S/O", "SO", "D/O", "DO", "W/O", "WO");
    List<String> UNEXPECTED_TEXT_LIST_IN_PERSON_NAME = Arrays.asList(
            "1", "/", File.separator, "\\", "&", ",", "#", "@", "!", "$", "+", " SO ", " DO ", " WO ", "LIMITED", "LTD", "TRADING", "AGENCY", "ENTERPRISE", "ENTERP", "NATIONAL", "PROKASHON", "SPINNING", "CHEMICALS", "ENGINEER", "GERMENT", "TEXTILE", "KNITWEAR", "BOSTRALOY", "AUDIO", "SHOES", "FABRICS", "STORE", "FOOD", "PRODUCT", "MILL ", "BRICKS", "WORLD", "BROTHERS", "AUTOMOBILE", "CROCKERIES", "HOUSE", "PHARMECY", "HARDWARE", "ELECTRO", "TRADER", "STATION", "FILLING", "LIBRARY", "CENTER", "CENTRE", " HALL ", "POULTRY", "FIRM", "FURNITURE", "BITAN", "CORPORATION", "TELECOM", "MEDICAL", "TAILOR", " AND ", "CONFECTIONARY", "PROP", "TAILER", "FOUNDATION", "SHOE ", "COMPUTER"
    );

    Map<String, String> MESSAGE_MAP = new HashMap<String, String>() {
        {
            put("FOUND", "Already exist.");
            put("NOT_FOUND", "Not available.");
            put("INVALID_FORMAT", "Invalid Data.");
            put("ZERO_LENGTH", "Privilege ID is missing.");
            put("NOT_EQUAL", "Values doesn't match.");
            put("EQUAL", "Values are same");
            put("SESSION_EXPIRED", "User Session expired. Please sign in again.");
            put("INSUFFICIENT_PRIVILEGE", "You have no privilege to execute this operation");
            put("ACCESS_DENIED", "Access denied.");
            put("NOT_LOGGED_IN", "User is not signed in. Please Sign in");
            put("IRREGULAR_STATE", "User Account state is inactive.");
            put("VALUE_MIS_MATCH", "Values doesn't match.");
            put("CASM_SERVER_CONNECTION_FAILED", "Unable to connect with CASM Server.");
            put("ITEM_ZERO_LENGTH", "Something is missing.");
            put("TYPE_MIS_MATCH", "doesn't match. Please check!!");

            /*CASM Messages*/
            put("MSG_FOUND", "Already exist.");
            put("MSG_NOT_FOUND", "Not available.");
            put("MSG_INVALID_FORMAT", "Invalid Data.");
            put("MSG_ZERO_LENGTH", "XML configuration missing. / Privilege ID is missing. / MSG_ZERO_LENGTH");
            put("MSG_NOT_EQUAL", "Values doesn't match.");
            put("MSG_EQUAL", "Values are same");
            put("MSG_SESSION_EXPIRED", "User Session expired. Please sign in again.");
            put("MSG_INSUFFICIENT_PRIVILEGE", "You have no privilege to execute this operation");
            put("MSG_ACCESS_DENIED", "Access denied.");
            put("MSG_NOT_LOGGED_IN", "User is not signed in. Please Sign in");
            put("MSG_IRREGULAR_STATE", "User Account state is inactive.");
            put("MSG_VALUE_MIS_MATCH", "Values doesn't match.");
            put("MSG_CASM_SERVER_CONNECTION_FAILED", "Unable to connect with CASM Server.");
            put("MSG_ITEM_ZERO_LENGTH", "Something is missing.");
            put("MSG_TYPE_MIS_MATCH", "doesn't match. Please check!!");
        }
    };

    /**
     * Khomeni
     * CIB, BB recommended District Spelling
     * in the inner list...
     * 1. 1st element of each list is DISTRICT CODE (CIB, IBBL Specified)
     * 2. 2nd one is RIGHT SPELL of District name (CIB, BB Specified)
     * 3. 3rd one is unique prifix of district name
     * 4. And the rest are miss spelled of district name,
     * these misspelled name will be replace with the RIGHT SPELLED text temporarily.
     */
    List<List<String>> DISTRICT_LIST_COMPLEX = new ArrayList<List<String>>() {{
        add(Arrays.asList("1", "BAGERHAT", "BAG", "BAGHERHAT"));
        add(Arrays.asList("2", "BANDARBAN", "BAND", "BANDORBON", "BANDARBON", "BONDORBON"));
        add(Arrays.asList("3", "BARGUNA", "BARG", "BORGUNA", "BARGHUNA", "BORGONA"));
        add(Arrays.asList("4", "BARISHAL", "BARI", "BORISAL", "BORISHAL", "BARISAL"));
        add(Arrays.asList("5", "BHOLA", "BHO", "VOLA", "VHOLA", "BHULA", "VULA"));
        add(Arrays.asList("6", "BOGRA", "BOG", "BOGURA", "BAGURA", "BAGORA"));
        add(Arrays.asList("7", "BRAHMANBARIA", "BRA", "BRAMMONBARIA", "B.BARIA", "B BARIA", "BBARIA"));
        add(Arrays.asList("8", "CHANDPUR", "CHAN", "CHANPUR", "CHANDPOR", "CANDPUR", "CANPUR", "CANPOR"));
        add(Arrays.asList("9", "CHAPAINAWABGANJ", "CHAP", "NAWABGANJ", "CN GONJ", "C.N.GONJ", "NAWABGONJ", "C.GONJ", "C GONJ"));
        add(Arrays.asList("10", "CHITTAGONG", "CHI", "CTG", "C.T.G"));
        add(Arrays.asList("11", "CHUADANGA", "CHU", "CUADANGA"));
        add(Arrays.asList("12", "COMILLA", "COM", "CUMILLA", "KUMILLA"));
        add(Arrays.asList("13", "COX'S BAZAR", "COX", "COXS BAZAR", " COX'S BAJAR", "Coxâ¿¿s Bazar"));
        add(Arrays.asList("14", "DHAKA", "DHA", "DACCA", "DAKA", "DHK", "DKH", "DAHAKA"));
        add(Arrays.asList("15", "DINAJPUR", "DIN"));
        add(Arrays.asList("16", "FARIDPUR", "FAR", "FORIDPUR"));
        add(Arrays.asList("17", "FENI", "FEN"));
        add(Arrays.asList("18", "GAIBANDHA", "GAI", "GAYBANDHA"));
        add(Arrays.asList("19", "GAZIPUR", "GAZ", "GAJIPUR"));
        add(Arrays.asList("20", "GOPALGANJ", "GOP", "GUPALGONJ", "GUPALGANJ"));
        add(Arrays.asList("21", "HOBIGANJ", "HOB", "HABIGANJ"));
        add(Arrays.asList("22", "JAMALPUR", "JAM"));
        add(Arrays.asList("23", "JESSORE", "JES", "JOSOR", "JSOHOR"));
        add(Arrays.asList("24", "JHALOKATHI", "JHA", "JALKATI", "JALOKATHI", "JALOKATI"));
        add(Arrays.asList("25", "JINAIDAHA", "JIN", "JINAIDA", "JINAIDAH", "JHINAIDAHA", "JHINAIDAH", "JHENAIDAH", "JHENIDAH")); //JHINAIDAH JINAIDAHA
        add(Arrays.asList("26", "JOYPURHAT", "JOY", "JAIPURHAT"));
        add(Arrays.asList("27", "KHAGRACHARI", "KHA"));
        add(Arrays.asList("28", "KHULNA", "KHU"));
        add(Arrays.asList("29", "KISHOREGANJ", "KIS"));
        add(Arrays.asList("30", "KURIGRAM", "KUR"));
        add(Arrays.asList("31", "KUSTIA", "KUS"));
        add(Arrays.asList("32", "LAKSHMIPUR", "LAK", "LAXMIPUR", "LAXMIPOR", "LOKKHIPUR", "LUXMIPUR", "LUXMIPOR"));
        add(Arrays.asList("33", "LALMONIRHAT", "LAL"));
        add(Arrays.asList("34", "MADARIPUR", "MAD"));
        add(Arrays.asList("35", "MAGURA", "MAG"));
        add(Arrays.asList("36", "MANIKGANJ", "MAN"));
        add(Arrays.asList("37", "MEHERPUR", "MEH"));
        add(Arrays.asList("38", "MOULVIBAZAR", "MOU"));
        add(Arrays.asList("39", "MUNSHIGANJ", "MUN"));
        add(Arrays.asList("40", "MYMENSINGH", "MYM", "MAYMONSINGH", "MAYMENSINGH", "MAYMONSING", "MAYMENSING"));
        add(Arrays.asList("41", "NAOGAON", "NAO"));
        add(Arrays.asList("42", "NARAIL", "NARAI", "NORAIL"));
        add(Arrays.asList("43", "NARAYANGANJ", "NARAY", "N.GANJ"));
        add(Arrays.asList("44", "NARSHINGDI", "NARS"));
        add(Arrays.asList("45", "NATORE", "NAT"));
        add(Arrays.asList("46", "NETRAKONA", "NET"));
        add(Arrays.asList("47", "NILPHAMARI", "NIL"));
        add(Arrays.asList("48", "NOAKHALI", "NOA"));
        add(Arrays.asList("49", "PABNA", "PAB"));
        add(Arrays.asList("50", "PANCHAGARH", "PAN", "PONCHAGARH"));
        add(Arrays.asList("51", "PATUAKHALI", "PAT", "POTUAKHALI"));
        add(Arrays.asList("52", "PIROJPUR", "PIR"));
        add(Arrays.asList("53", "RAJBARI", "RAJB", "RAZBARI", "RAZBARRI", "RAZBARIA"));
        add(Arrays.asList("54", "RAJSHAHI", "RAJS"));
        add(Arrays.asList("55", "RANGAMATI", "RANGA"));
        add(Arrays.asList("56", "RANGPUR", "RANGP", "RONGPUR"));
        add(Arrays.asList("57", "SATKHIRA", "SAT", "SATKIRA", "SHATKHIRA", "SHATKIRA"));
        add(Arrays.asList("58", "SHARIATPUR", "SHA", "SHORIATPUR", "SHORIOTPUR", "SARIATPUR"));
        add(Arrays.asList("59", "SHERPUR", "SHE", "SERPUR", "SER PUR", "SHARPUR"));
        add(Arrays.asList("60", "SIRAJGANJ", "SIR", "SHIRAJGANJ", "SHIRAJGONJ"));
        add(Arrays.asList("61", "SUNAMGANJ", "SUN", "SHUNAMGANJ"));
        add(Arrays.asList("62", "SYLHET", "SYL", "SHILATE", "SHILHET"));
        add(Arrays.asList("63", "TANGAIL", "TAN"));
        add(Arrays.asList("64", "THAKURGAON", "THA", "TAKURGAON"));

    }};

    static String getDistrictCode(String districtName) {
        for (List<String> l : DISTRICT_LIST_COMPLEX) {
            if (l.contains(districtName))
                return l.get(0);
        }
        return "";
    }

    static String getDistrictName(String districtCode) {
        for (List<String> l : DISTRICT_LIST_COMPLEX) {
            if (l.contains(districtCode))
                return l.get(1);
        }
        return "";
    }

    /**
     * Khomeni
     * CIB, BB recommended Country Code and Spelling
     * In the inner list...
     * 1. The first index (0) contains two character referred Unique Country Code
     * 2. The second index (1) is the right spelling of the Country
     * 3. The third index (2) is customized unique prefix of Country name that allow if match with any misspelled name  (and replaced with second index)
     * 4. From the fourth - last index contains those misspelled name that will allowed temporarily (and replaced with second index)
     */
    List<List<String>> COUNTRY_LIST_COMPLEX = new ArrayList<List<String>>() {{
        add(Arrays.asList("AF", "AFGHANISTAN", "AFG", "AFGANSTAN", "AFGANISTAN", "AFGANSTHAN"));
        add(Arrays.asList("AX", "ALAND ISLANDS", "ALA"));
        add(Arrays.asList("AL", "ALBANIA", "ALB"));
        add(Arrays.asList("DZ", "ALGERIA", "ALG"));
        add(Arrays.asList("AS", "AMERICAN SAMOA", "AME"));
        add(Arrays.asList("AD", "ANDORRA", "AND"));
        add(Arrays.asList("AO", "ANGOLA", "ANG"));
        add(Arrays.asList("AI", "ANGUILLA", "ANG"));
        add(Arrays.asList("AQ", "ANTARCTICA", "ANTA"));
        add(Arrays.asList("AG", "ANTIGUA AND BARBUDA", "ANTI"));
        add(Arrays.asList("AR", "ARGENTINA", "ARG"));
        add(Arrays.asList("AM", "ARMENIA", "ARM"));
        add(Arrays.asList("AW", "ARUBA", "ARU"));
        add(Arrays.asList("AU", "AUSTRALIA", "AUSTRA"));
        add(Arrays.asList("AT", "AUSTRIA", "AUSTRI"));
        add(Arrays.asList("AZ", "AZERBAIJAN", "AZE"));
        add(Arrays.asList("BS", "BAHAMAS", "BAHA"));
        add(Arrays.asList("BH", "BAHRAIN", "BAHR"));
        add(Arrays.asList(FinalData.COUNTRY_CODE_BD, FinalData.COUNTRY_NAME_BD, "BAN"));
        add(Arrays.asList("BB", "BARBADOS", "BAR"));
        add(Arrays.asList("BY", "BELARUS", "BELA"));
        add(Arrays.asList("BE", "BELGIUM", "BELG"));
        add(Arrays.asList("BZ", "BELIZE", "BELI"));
        add(Arrays.asList("BJ", "BENIN", "BEN"));
        add(Arrays.asList("BM", "BERMUDA", "BER"));
        add(Arrays.asList("BT", "BHUTAN", "BHU"));
        add(Arrays.asList("BO", "BOLIVIA", "BOL"));
        add(Arrays.asList("BA", "BOSNIA AND HERZEGOVINA", "BOS"));
        add(Arrays.asList("BW", "BOTSWANA", "BOT"));
        add(Arrays.asList("BV", "BOUVET ISLAND", "BOU"));
        add(Arrays.asList("BR", "BRAZIL", "BRA"));
        add(Arrays.asList("IO", "BRITISH INDIAN OCEAN TERRITORY", "BRI"));
        add(Arrays.asList("BN", "BRUNEI DARUSSALAM", "BRU"));
        add(Arrays.asList("BG", "BULGARIA", "BUL"));
        add(Arrays.asList("BF", "BURKINA FASO", "BURK"));
        add(Arrays.asList("BI", "BURUNDI", "BURU"));
        add(Arrays.asList("KH", "CAMBODIA", "CAMB"));
        add(Arrays.asList("CM", "CAMEROON", "CAME"));
        add(Arrays.asList("CA", "CANADA", "CAN"));
        add(Arrays.asList("CV", "CAPE VERDE", "CAP"));
        add(Arrays.asList("KY", "CAYMAN ISLANDS", "CAY"));
        add(Arrays.asList("CF", "CENTRAL AFRICAN REPUBLIC", "CEN"));
        add(Arrays.asList("TD", "CHAD", "CHA"));
        add(Arrays.asList("CL", "CHILE", "CHIL"));
        add(Arrays.asList("CN", "CHINA", "CHIN"));
        add(Arrays.asList("CX", "CHRISTMAS ISLAND", "CHR"));
        add(Arrays.asList("CC", "COCOS (KEELING) ISLANDS", "COC"));
        add(Arrays.asList("CO", "COLOMBIA", "COL"));
        add(Arrays.asList("KM", "COMOROS", "COM"));
        add(Arrays.asList("CG", "CONGO", "---"));
        add(Arrays.asList("CD", "CONGO, THE DEMOCRATIC REPUBLIC OF THE", "---"));
        add(Arrays.asList("CK", "COOK ISLANDS", "COO"));
        add(Arrays.asList("CR", "COSTA RICA", "COS"));
        add(Arrays.asList("CI", "COTE D'IVOIRE", "COT"));
        add(Arrays.asList("HR", "CROATIA", "CRO"));
        add(Arrays.asList("CU", "CUBA", "CUB"));
        add(Arrays.asList("CY", "CYPRUS", "CYP"));
        add(Arrays.asList("CZ", "CZECH REPUBLIC", "CZE"));
        add(Arrays.asList("DK", "DENMARK", "DEN"));
        add(Arrays.asList("DJ", "DJIBOUTI", "DJI"));
        add(Arrays.asList("DM", "DOMINICA", "---"));
        add(Arrays.asList("DO", "DOMINICAN REPUBLIC", "---"));
        add(Arrays.asList("TP", "EAST TIMOR", "EAS"));
        add(Arrays.asList("EC", "ECUADOR", "ECU"));
        add(Arrays.asList("EG", "EGYPT", "EGY"));
        add(Arrays.asList("SV", "EL SALVADOR", "EL "));
        add(Arrays.asList("GQ", "EQUATORIAL GUINEA", "EQU"));
        add(Arrays.asList("ER", "ERITREA", "ERI"));
        add(Arrays.asList("EE", "ESTONIA", "EST"));
        add(Arrays.asList("ET", "ETHIOPIA", "ETH"));
        add(Arrays.asList("FK", "FALKLAND ISLANDS (MALVINAS)", "FAL", "ISLAS", "ISLAS MALVINAS", "MALVINAS"));
        add(Arrays.asList("FO", "FAROE ISLANDS", "FAR"));
        add(Arrays.asList("FJ", "FIJI", "FIJ"));
        add(Arrays.asList("FI", "FINLAND", "FIN"));
        add(Arrays.asList("FR", "FRANCE", "FRA"));
        add(Arrays.asList("GF", "FRENCH GUIANA", "FRENCH G"));
        add(Arrays.asList("PF", "FRENCH POLYNESIA", "FRENCH P"));
        add(Arrays.asList("TF", "FRENCH SOUTHERN TERRITORIES", "FRENCH S"));
        add(Arrays.asList("GA", "GABON", "GAB"));
        add(Arrays.asList("GM", "GAMBIA", "GAM"));
        add(Arrays.asList("GE", "GEORGIA", "GEO"));
        add(Arrays.asList("DE", "GERMANY", "GER"));
        add(Arrays.asList("GH", "GHANA", "GHA"));
        add(Arrays.asList("GI", "GIBRALTAR", "GIB"));
        add(Arrays.asList("GR", "GREECE", "GREEC"));
        add(Arrays.asList("GL", "GREENLAND", "GREEN"));
        add(Arrays.asList("GD", "GRENADA", "GREN"));
        add(Arrays.asList("GP", "GUADELOUPE", "GUAD"));
        add(Arrays.asList("GU", "GUAM", "GUAM"));
        add(Arrays.asList("GT", "GUATEMALA", "GUAT"));
        add(Arrays.asList("GG", "GUERNSEY ISLANDS", "GUE"));
        add(Arrays.asList("GN", "GUINEA", "GUI"));
        add(Arrays.asList("GW", "GUINEA-BISSAU", "GUI"));
        add(Arrays.asList("GY", "GUYANA", "GUY"));
        add(Arrays.asList("HT", "HAITI", "HAI"));
        add(Arrays.asList("HM", "HEARD ISLAND AND MCDONALD ISLANDS", "HEA"));
        add(Arrays.asList("VA", "HOLY SEE (VATICAN CITY STATE)", "HOL"));
        add(Arrays.asList("HN", "HONDURAS", "HOND"));
        add(Arrays.asList("HK", "HONG KONG", "HONG"));
        add(Arrays.asList("HU", "HUNGARY", "HUN"));
        add(Arrays.asList("IS", "ICELAND", "ICE"));
        add(Arrays.asList("IN", "INDIA", "INDI"));
        add(Arrays.asList("ID", "INDONESIA", "INDO"));
        add(Arrays.asList("IR", "IRAN, ISLAMIC REPUBLIC OF", "IRAN"));
        add(Arrays.asList("IQ", "IRAQ", "IRAQ"));
        add(Arrays.asList("IE", "IRELAND", "IRE"));
        add(Arrays.asList("IL", "ISRAEL", "ISR"));
        add(Arrays.asList("IT", "ITALY", "ITA"));
        add(Arrays.asList("JM", "JAMAICA", "JAM"));
        add(Arrays.asList("JP", "JAPAN", "JAP"));
        add(Arrays.asList("JE", "JERSEY, ISLANDS", "JER"));
        add(Arrays.asList("JO", "JORDAN", "JOR"));
        add(Arrays.asList("KZ", "KAZAKSTAN", "KAZ"));
        add(Arrays.asList("KE", "KENYA", "KEN"));
        add(Arrays.asList("KI", "KIRIBATI", "KIR"));
        add(Arrays.asList("KP", "KOREA, DEMOCRATIC PEOPLE'S REPUBLIC OF", "NORTH K", "NORTH KORIA"));
        add(Arrays.asList("KR", "KOREA, REPUBLIC OF", "KOR", "SOUTH KORIA"));
        add(Arrays.asList("KW", "KUWAIT", "KUW"));
        add(Arrays.asList("KG", "KYRGYZSTAN", "KYR"));
        add(Arrays.asList("LA", "LAO PEOPLE'S DEMOCRATIC REPUBLIC", "LAO", "LAOS"));
        add(Arrays.asList("LV", "LATVIA", "LAT"));
        add(Arrays.asList("LB", "LEBANON", "LEB"));
        add(Arrays.asList("LS", "LESOTHO", "LES"));
        add(Arrays.asList("LR", "LIBERIA", "LIBE"));
        add(Arrays.asList("LY", "LIBYAN ARAB JAMAHIRIYA", "LIBY"));
        add(Arrays.asList("LI", "LIECHTENSTEIN", "LIE"));
        add(Arrays.asList("LT", "LITHUANIA", "LIT"));
        add(Arrays.asList("LU", "LUXEMBOURG", "LUX"));
        add(Arrays.asList("MO", "MACAU", "MACA"));
        add(Arrays.asList("MK", "MACEDONIA, THE FORMER YUGOSLAV REPUBLIC OF", "MACE"));
        add(Arrays.asList("MG", "MADAGASCAR", "MAD"));
        add(Arrays.asList("MW", "MALAWI", "MALAW"));
        add(Arrays.asList("MY", "MALAYSIA", "MALAY"));
        add(Arrays.asList("MV", "MALDIVES", "MALD"));
        add(Arrays.asList("ML", "MALI", "MALI"));
        add(Arrays.asList("MT", "MALTA", "MALT"));
        add(Arrays.asList("IM", "MAN ISLAND", "MAN"));
        add(Arrays.asList("MH", "MARSHALL ISLANDS", "MAR"));
        add(Arrays.asList("MQ", "MARTINIQUE", "MAR"));
        add(Arrays.asList("MR", "MAURITANIA", "MAURITA"));
        add(Arrays.asList("MU", "MAURITIUS", "MAURITI"));
        add(Arrays.asList("YT", "MAYOTTE", "MAY"));
        add(Arrays.asList("ME", "MONTENEGRO", "MON"));
        add(Arrays.asList("MX", "MEXICO", "MEX"));
        add(Arrays.asList("FM", "MICRONESIA, FEDERATED STATES OF", "MIC"));
        add(Arrays.asList("MD", "MOLDOVA, REPUBLIC OF", "MOL"));
        add(Arrays.asList("MC", "MONACO", "MONA"));
        add(Arrays.asList("MN", "MONGOLIA", "MONG"));
        add(Arrays.asList("MS", "MONTSERRAT", "MONT"));
        add(Arrays.asList("MA", "MOROCCO", "MOR"));
        add(Arrays.asList("MZ", "MOZAMBIQUE", "MOZ"));
        add(Arrays.asList("MM", "MYANMAR", "MYA"));
        add(Arrays.asList("NA", "NAMIBIA", "NAM"));
        add(Arrays.asList("NR", "NAURU", "NAU"));
        add(Arrays.asList("NP", "NEPAL", "NEP"));
        add(Arrays.asList("NL", "NETHERLANDS", "---"));
        add(Arrays.asList("AN", "NETHERLANDS ANTILLES", "---"));
        add(Arrays.asList("NC", "NEW CALEDONIA", "NEW C"));
        add(Arrays.asList("NZ", "NEW ZEALAND", "NEW Z"));
        add(Arrays.asList("NI", "NICARAGUA", "NIC"));
        add(Arrays.asList("NE", "NIGER", "---"));
        add(Arrays.asList("NG", "NIGERIA", "---"));
        add(Arrays.asList("NU", "NIUE", "NIU"));
        add(Arrays.asList("NF", "NORFOLK ISLAND", "NORF"));
        add(Arrays.asList("MP", "NORTHERN MARIANA ISLANDS", "NORT"));
        add(Arrays.asList("NO", "NORWAY", "NORW"));
        add(Arrays.asList("OM", "OMAN", "OMA"));
        add(Arrays.asList("PK", "PAKISTAN", "PAK"));
        add(Arrays.asList("PW", "PALAU", "PALA"));
        add(Arrays.asList("PS", "PALESTINIAN TERRITORY, OCCUPIED", "PALE", "FILISTIN"));
        add(Arrays.asList("PA", "PANAMA", "PAN"));
        add(Arrays.asList("PG", "PAPUA NEW GUINEA", "PAP"));
        add(Arrays.asList("PY", "PARAGUAY", "PAR"));
        add(Arrays.asList("PE", "PERU", "PER"));
        add(Arrays.asList("PH", "PHILIPPINES", "PHI"));
        add(Arrays.asList("PN", "PITCAIRN", "PIT"));
        add(Arrays.asList("PL", "POLAND", "POL"));
        add(Arrays.asList("PT", "PORTUGAL", "POR"));
        add(Arrays.asList("PR", "PUERTO RICO", "PUE"));
        add(Arrays.asList("QA", "QATAR", "QAT"));
        add(Arrays.asList("RE", "REUNION", "REU"));
        add(Arrays.asList("RO", "ROMANIA", "ROM"));
        add(Arrays.asList("RU", "RUSSIAN FEDERATION", "RUS"));
        add(Arrays.asList("RW", "RWANDA", "RWA"));
        add(Arrays.asList("BL", "SAINT BARTHÉLEMY", "SAINT B"));
        add(Arrays.asList("SH", "SAINT HELENA", "SAINT H"));
        add(Arrays.asList("KN", "SAINT KITTS AND NEVIS", "SAINT K"));
        add(Arrays.asList("LC", "SAINT LUCIA", "SAINT L"));
        add(Arrays.asList("MF", "SAINT MARTIN (FRENCH PART)", "SAINT M"));
        add(Arrays.asList("PM", "SAINT PIERRE AND MIQUELON", "SAINT P"));
        add(Arrays.asList("VC", "SAINT VINCENT AND THE GRENADINES", "SAINT V"));
        add(Arrays.asList("WS", "SAMOA", "SAM"));
        add(Arrays.asList("SM", "SAN MARINO", "SAN"));
        add(Arrays.asList("ST", "SAO TOME AND PRINCIPE", "SAO"));
        add(Arrays.asList("SA", "SAUDI ARABIA", "SAU", "K.S.A", "KSA", "SAUDI", "SAUDIARAB", "ARAB", "ARABIA", "K.S.A."));
        add(Arrays.asList("SN", "SENEGAL", "SEN"));
        add(Arrays.asList("CS", "SERBIA & MONTENEGRO", "MONT"));
        add(Arrays.asList("RS", "SERBIA", "---"));
        add(Arrays.asList("SC", "SEYCHELLES", "SEY"));
        add(Arrays.asList("SL", "SIERRA LEONE", "SIE"));
        add(Arrays.asList("SG", "SINGAPORE", "SIN"));
        add(Arrays.asList("SK", "SLOVAKIA", "SLOVA"));
        add(Arrays.asList("SI", "SLOVENIA", "SLOVE"));
        add(Arrays.asList("SB", "SOLOMON ISLANDS", "SOL"));
        add(Arrays.asList("SO", "SOMALIA", "SOM"));
        add(Arrays.asList("ZA", "SOUTH AFRICA", "SOUTH A"));
        add(Arrays.asList("GS", "SOUTH GEORGIA AND THE SOUTH SANDWICH ISLAND", "SOUTH G"));
        add(Arrays.asList("ES", "SPAIN", "SPA"));
        add(Arrays.asList("LK", "SRI LANKA", "SRI"));
        add(Arrays.asList("SD", "SUDAN", "SUD"));
        add(Arrays.asList("SR", "SURINAME", "SUR"));
        add(Arrays.asList("SJ", "SVALBARD AND JAN MAYEN", "SVA"));
        add(Arrays.asList("SZ", "SWAZILAND", "SWA"));
        add(Arrays.asList("SE", "SWEDEN", "SWE"));
        add(Arrays.asList("CH", "SWITZERLAND", "SWI"));
        add(Arrays.asList("SY", "SYRIAN ARAB REPUBLIC", "SYR"));
        add(Arrays.asList("TW", "TAIWAN, PROVINCE OF CHINA", "TAI"));
        add(Arrays.asList("TJ", "TAJIKISTAN", "TAJ"));
        add(Arrays.asList("TZ", "TANZANIA, UNITED REPUBLIC OF", "TAN"));
        add(Arrays.asList("TH", "THAILAND", "THA"));
        add(Arrays.asList("TL", "TIMOR-LESTE", "TIM"));
        add(Arrays.asList("TG", "TOGO", "TOG"));
        add(Arrays.asList("TK", "TOKELAU", "TOK"));
        add(Arrays.asList("TO", "TONGA", "TON"));
        add(Arrays.asList("TT", "TRINIDAD AND TOBAGO", "TRI"));
        add(Arrays.asList("TN", "TUNISIA", "TUN"));
        add(Arrays.asList("TR", "TURKEY", "TURKE"));
        add(Arrays.asList("TM", "TURKMENISTAN", "TURKM"));
        add(Arrays.asList("TC", "TURKS AND CAICOS ISLANDS", "TURKS"));
        add(Arrays.asList("TV", "TUVALU", "TUV"));
        add(Arrays.asList("UG", "UGANDA", "UGA"));
        add(Arrays.asList("UA", "UKRAINE", "UKR"));
        add(Arrays.asList("AE", "UNITED ARAB EMIRATES", "EMIR", "UAE", "U.A.E", "EMIRATES", "AMIRAT", "UNITEARAEMIRATES"));
        add(Arrays.asList("GB", "UNITED KINGDOM", "UNITED K", "UK", "U.K", "ENGLAND", "UNITEKINGDOM"));
        add(Arrays.asList("US", "UNITED STATES", "UNITED S", "USA", "U.S.A", "AMERICA"));
        add(Arrays.asList("UM", "UNITED STATES MINOR OUTLYING ISLANDS", "MINOR"));
        add(Arrays.asList("UY", "URUGUAY", "URU"));
        add(Arrays.asList("UZ", "UZBEKISTAN", "UZB"));
        add(Arrays.asList("VU", "VANUATU", "VAN"));

    }};

    static String getCountryCode(String countryName) {
        for (List<String> l : COUNTRY_LIST_COMPLEX) {
            if (l.contains(countryName))
                return l.get(0);
        }
        return "";
    }

    static String getCountryName(String countryCode) {
        for (List<String> l : COUNTRY_LIST_COMPLEX) {
            if (l.contains(countryCode))
                return l.get(1);
        }
        return "";
    }

    Integer EDIT_LOG_CODE_NAMES = 21;
    Integer EDIT_LOG_CODE_ADDRESSES = 22;
    Integer EDIT_LOG_CODE_BIRTH_INFO = 23;
    Integer EDIT_LOG_CODE_IDENTITY_NO = 24;
    Integer EDIT_LOG_CODE_GRP_MEM = 25;
    Integer EDIT_LOG_CODE_GUARANTOR = 26;
    Integer EDIT_LOG_CODE_BORROWER = 27;

    List<Integer> CIB_UPDATABLE_EDIT_CODE_LIST = Arrays.asList(EDIT_LOG_CODE_NAMES, EDIT_LOG_CODE_ADDRESSES, EDIT_LOG_CODE_BIRTH_INFO, EDIT_LOG_CODE_GRP_MEM, EDIT_LOG_CODE_GUARANTOR, EDIT_LOG_CODE_BORROWER);

    Map<Integer, String> EDIT_LOG_CODE_MAP = new HashMap<Integer, String>() {{
        put(EDIT_LOG_CODE_NAMES, "Changes in Name, Father Name, Mother Name, Spouse Name");
        put(EDIT_LOG_CODE_ADDRESSES, "Changes in Addresses");
        put(EDIT_LOG_CODE_BIRTH_INFO, "Changes in DOB, Birth Place (District), Birth Country");
        put(EDIT_LOG_CODE_IDENTITY_NO, "Changes in any Identity Number. (e.g. NID, TIN, DL, Passport etc.)");
        put(EDIT_LOG_CODE_GRP_MEM, "Adding or Removing Group Member. (e.g. Directors etc.)");
        put(EDIT_LOG_CODE_GUARANTOR, "Adding or Removing Guarantor of an investment.");
        put(EDIT_LOG_CODE_BORROWER, "Changes in Borrower/Co-Borrower of Joint Customer.");
    }};

    List<String> CURRENCY_CODE_LIST = Arrays.asList("AED", "AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AUD", "AWG", "AZN", "BAM", "BBD", "BDT", "BGN", "BHD", "BIF", "BMD", "BND", "BOB", "BOV", "BRL", "BSD", "BTN", "BWP", "BYR", "BZD", "CAD", "CDF", "CHE", "CHF", "CHW", "CLF", "CLP", "CNY", "COP", "COU", "CRC", "CUC", "CUP", "CVE", "CZK", "DJF", "DKK", "DOP", "DZD", "EEK", "EGP", "ERN", "ETB", "EUR", "FJD", "FKP", "GBP", "GEL", "GHS", "GIP", "GMD", "GNF", "GTQ", "GYD", "HKD", "HNL", "HRK", "HTG", "HUF", "IDR", "ILS", "INR", "IQD", "IRR", "ISK", "JMD", "JOD", "JPY", "KES", "KGS", "KHR", "KMF", "KPW", "KRW", "KWD", "KYD", "KZT", "LAK", "LBP", "LKR", "LRD", "LSL", "LTL", "LVL", "LYD", "MAD", "MDL", "MGA", "MKD", "MMK", "MNT", "MOP", "MRO", "MUR", "MVR", "MWK", "MXN", "MXV", "MYR", "MZN", "NAD", "NGN", "NIO", "NOK", "NPR", "NZD", "OMR", "PAB", "PEN", "PGK", "PHP", "PKR", "PLN", "PYG", "QAR", "RON", "RSD", "RUB", "RWF", "SAR", "SBD", "SCR", "SDG", "SEK", "SGD", "SHP", "SLL", "SOS", "SRD", "STD", "SYP", "SZL", "THB", "TJS", "TMT", "TND", "TOP", "TRY", "TTD", "TWD", "TZS", "UAH", "UGX", "USD", "USN", "USS", "UYU", "UZS", "VEF", "VND", "VUV", "WST", "XAF", "XAG", "XAU", "XBA", "XBB", "XBC", "XBD", "XCD", "XDR", "XFU", "XOF", "XPD", "XPF", "XPT", "XTS", "XXX", "YER", "ZAR", "ZMK", "ZWL");
    Map<String, String> CURRENCY_MAP = new HashMap<String, String>() {{
        put("AED", "United Arab Emirates dirham");
        put("AFN", "Afghani");
        put("ALL", "Lek");
        put("AMD", "Armenian dram");
        put("ANG", "Netherlands Antillean guilder");
        put("AOA", "Kwanza");
        put("ARS", "Argentine peso");
        put("AUD", "Australian dollar");
        put("AWG", "Aruban guilder");
        put("AZN", "Azerbaijanian manat");
        put("BAM", "Convertible marks");
        put("BBD", "Barbados dollar");
        put("BDT", "Bangladeshi taka");
        put("BGN", "Bulgarian lev");
        put("BHD", "Bahraini dinar");
        put("BIF", "Burundian franc");
        put("BMD", "Bermudian dollar (customarily known as Bermuda dollar)");
        put("BND", "Brunei dollar");
        put("BOB", "Boliviano");
        put("BOV", "Bolivian Mvdol (funds code)");
        put("BRL", "Brazilian real");
        put("BSD", "Bahamian dollar");
        put("BTN", "Ngultrum");
        put("BWP", "Pula");
        put("BYR", "Belarusian ruble");
        put("BZD", "Belize dollar");
        put("CAD", "Canadian dollar");
        put("CDF", "Franc Congolais");
        put("CHE", "WIR euro (complementary currency)");
        put("CHF", "Swiss franc");
        put("CHW", "WIR franc (complementary currency)");
        put("CLF", "Unidad de Fomento (funds code)");
        put("CLP", "Chilean peso");
        put("CNY", "Chinese Yuan");
        put("COP", "Colombian peso");
        put("COU", "Unidad de Valor Real");
        put("CRC", "Costa Rican colon");
        put("CUC", "Cuban convertible peso");
        put("CUP", "Cuban peso");
        put("CVE", "Cape Verde escudo");
        put("CZK", "Czech Koruna");
        put("DJF", "Djibouti franc");
        put("DKK", "Danish krone");
        put("DOP", "Dominican peso");
        put("DZD", "Algerian dinar");
        put("EEK", "Kroon");
        put("EGP", "Egyptian pound");
        put("ERN", "Nakfa");
        put("ETB", "Ethiopian birr");
        put("EUR", "euro");
        put("FJD", "Fiji dollar");
        put("FKP", "Falkland Islands pound");
        put("GBP", "Pound sterling");
        put("GEL", "Lari");
        put("GHS", "Cedi");
        put("GIP", "Gibraltar pound");
        put("GMD", "Dalasi");
        put("GNF", "Guinea franc");
        put("GTQ", "Quetzal");
        put("GYD", "Guyana dollar");
        put("HKD", "Hong Kong dollar");
        put("HNL", "Lempira");
        put("HRK", "Croatian kuna");
        put("HTG", "Haiti gourde");
        put("HUF", "Forint");
        put("IDR", "Rupiah");
        put("ILS", "Israeli new sheqel");
        put("INR", "Indian rupee");
        put("IQD", "Iraqi dinar");
        put("IRR", "Iranian rial");
        put("ISK", "Iceland krona");
        put("JMD", "Jamaican dollar");
        put("JOD", "Jordanian dinar");
        put("JPY", "Japanese yen");
        put("KES", "Kenyan shilling");
        put("KGS", "Som");
        put("KHR", "Riel");
        put("KMF", "Comoro franc");
        put("KPW", "North Korean won");
        put("KRW", "South Korean won");
        put("KWD", "Kuwaiti dinar");
        put("KYD", "Cayman Islands dollar");
        put("KZT", "Tenge");
        put("LAK", "Kip");
        put("LBP", "Lebanese pound");
        put("LKR", "Sri Lanka rupee");
        put("LRD", "Liberian dollar");
        put("LSL", "Lesotho loti");
        put("LTL", "Lithuanian litas");
        put("LVL", "Latvian lats");
        put("LYD", "Libyan dinar");
        put("MAD", "Moroccan dirham");
        put("MDL", "Moldovan leu");
        put("MGA", "Malagasy ariary");
        put("MKD", "Denar");
        put("MMK", "Kyat");
        put("MNT", "Tugrik");
        put("MOP", "Pataca");
        put("MRO", "Ouguiya");
        put("MUR", "Mauritius rupee");
        put("MVR", "Rufiyaa");
        put("MWK", "Kwacha");
        put("MXN", "Mexican peso");
        put("MXV", "Mexican Unidad de Inversion (UDI) (funds code)");
        put("MYR", "Malaysian ringgit");
        put("MZN", "Metical");
        put("NAD", "Namibian dollar");
        put("NGN", "Naira");
        put("NIO", "Cordoba oro");
        put("NOK", "Norwegian krone");
        put("NPR", "Nepalese rupee");
        put("NZD", "New Zealand dollar");
        put("OMR", "Rial Omani");
        put("PAB", "Balboa");
        put("PEN", "Nuevo sol");
        put("PGK", "Kina");
        put("PHP", "Philippine peso");
        put("PKR", "Pakistan rupee");
        put("PLN", "Zloty");
        put("PYG", "Guarani");
        put("QAR", "Qatari rial");
        put("RON", "Romanian new leu");
        put("RSD", "Serbian dinar");
        put("RUB", "Russian rouble");
        put("RWF", "Rwanda franc");
        put("SAR", "Saudi riyal");
        put("SBD", "Solomon Islands dollar");
        put("SCR", "Seychelles rupee");
        put("SDG", "Sudanese pound");
        put("SEK", "Swedish krona/kronor");
        put("SGD", "Singapore dollar");
        put("SHP", "Saint Helena pound");
        put("SLL", "Leone");
        put("SOS", "Somali shilling");
        put("SRD", "Surinam dollar");
        put("STD", "Dobra");
        put("SYP", "Syrian pound");
        put("SZL", "Lilangeni");
        put("THB", "Baht");
        put("TJS", "Somoni");
        put("TMT", "Manat");
        put("TND", "Tunisian dinar");
        put("TOP", "Pa'anga");
        put("TRY", "Turkish lira");
        put("TTD", "Trinidad and Tobago dollar");
        put("TWD", "New Taiwan dollar");
        put("TZS", "Tanzanian shilling");
        put("UAH", "Hryvnia");
        put("UGX", "Uganda shilling");
        put("USD", "US dollar");
        put("USN", "United States dollar (next day) (funds code)");
        put("USS", "United States dollar (same day) (funds code)");
        put("UYU", "Peso Uruguayo");
        put("UZS", "Uzbekistan som");
        put("VEF", "Venezuelan bolivar fuerte");
        put("VND", "Vietnamese d?ng");
        put("VUV", "Vatu");
        put("WST", "Samoan tala");
        put("XAF", "CFA franc BEAC");
        put("XAG", "Silver (one troy ounce)");
        put("XAU", "Gold (one troy ounce)");
        put("XBA", "European Composite Unit (EURCO) (bond market unit)");
        put("XBB", "European Monetary Unit (E.M.U.-6) (bond market unit)");
        put("XBC", "European Unit of Account 9 (E.U.A.-9) (bond market unit)");
        put("XBD", "European Unit of Account 17 (E.U.A.-17) (bond market unit)");
        put("XCD", "East Caribbean dollar");
        put("XDR", "Special Drawing Rights");
        put("XFU", "UIC franc (special settlement currency)");
        put("XOF", "CFA Franc BCEAO");
        put("XPD", "Palladium (one troy ounce)");
        put("XPF", "CFP franc");
        put("XPT", "Platinum (one troy ounce)");
        put("XTS", "Code reserved for testing purposes");
        put("XXX", "No currency");
        put("YER", "Yemeni rial");
        put("ZAR", "South African rand");
        put("ZMK", "Kwacha");
        put("ZWL", "Zimbabwe dollar");

    }};
    Map<String, String> ZONE_MAP = new HashMap<String, String>() {{
        put("00", "Head Office Controlled Branches");
        put("01", "Dhaka Central Zone");
        put("02", "Dhaka South Zone");
        put("03", "Dhaka North Zone");
        put("04", "Rangpur Zone");
        put("05", "Bogra Zone");
        put("06", "Khulna Zone");
        put("07", "Comilla Zone");
        put("08", "Sylhet Zone");
        put("09", "Rajshahi Zone");
        put("10", "Barishal Zone");
        put("11", "Mymensingh Zone");
        put("12", "Chittagong South Zone");
        put("13", "Chittagong North Zone");
        put("14", "Noakhali Zone");
        put("15", "Dhaka East Zone");
        put("16", "Jessore Zone");
    }};

    Integer LEGAL_FORM_OTHERS = 10;
    Map<Integer, String> LEGAL_FORM_MAP = new HashMap<Integer, String>() {{
        put(1, "Proprietorship");
        put(2, "Partnership");
        put(3, "Private Ltd. Co");
        put(4, "Public Ltd. Co.");
        put(5, "Co-operative");
        put(6, "Public sector");
        put(7, "Multinational");
        put(8, "NGO");
        put(9, "Trusty");
        put(LEGAL_FORM_OTHERS, "Others");
    }};
}
