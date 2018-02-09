package com.mbl.util;

import com.mbl.ir.service.InquiryStatus;
import net.softengine.util.GValidator;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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

public class Utility {


    public static String getInquiryStatus(int CODE) {
        return InquiryStatus.get(CODE).STATUS;
    }

    public static String getInquiryStatusDesc(int CODE) {
        return InquiryStatus.get(CODE).DESC;
    }

    public static List<Map<String, String>> getInquiryStatusMapList(int CODE) {
        return InquiryStatus.STATUS_MAP_LIST;
    }

    public static void main(String[] args) {
        String c = getRandomChar(6).toUpperCase();
        System.out.println("c = " + c);
        System.out.println(c.replaceAll("[1I0O]", "X"));
    }

   /* public static Map<String, String> getIncidentTypeMap() {
        return Constants.INCIDENT_TYPE_MAP;
    }

    public static String getIncidentType(String key) {
        try {
            return Constants.INCIDENT_TYPE_MAP.get(key);
        } catch (Exception e) {
            return "";
        }
    }*/


    public static String getUniqueFileName() {
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = sdf.format(new Date());
        return dateString + "-" + uuid.version() + uuid.variant() + "-" + randomUUIDString;
    }

    public static String generateId_12() {
        DateFormat df = new SimpleDateFormat("yyMMddHHmmss");
        return df.format(new Date());
    }

    public static String getRandomNumber(String format) {
        DecimalFormat df = new DecimalFormat(format);
        Random rand = new Random();
        return df.format(rand.nextInt(50));
    }

    public static String getRandomChar(Integer len) {
        return RandomStringUtils.randomAlphanumeric(len);
    }

    public static String getOtpPassphrase(String cell) {
        StringBuilder sb = new StringBuilder(cell);
        sb.reverse();
        return Constants.OTP_PASSPHRASE_PREFIX + sb;
    }

    public static List<String> generateListOfCode(Integer nos) {

        DecimalFormat df = new DecimalFormat("0000");
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 1; i < nos; i++) {
            list.add(df.format(i));
        }
        Collections.shuffle(list);
        return list;
    }

    public static String getFileExtension(MultipartFile file) {
        String name = file.getOriginalFilename();
        try {
            return name.substring(name.lastIndexOf("."));
        } catch (Exception e) {
            return "";
        }
    }

    public static String getFileNameWithoutExtension(MultipartFile file) {
        String name = file.getOriginalFilename();
        try {
            return name.substring(0, name.lastIndexOf("."));
        } catch (Exception e) {
            return "";
        }
    }

    public static String cutOrPadRight(String str, int size, String padStr) {
        str = str == null ? "" : str;
        int len = str.trim().length();
        str = len > size ? str.substring(0, size) : str;
        return StringUtils.rightPad(str, size, padStr);
    }

    public static String cutOrPadLeft(String str, int size, String padStr) {
        str = str == null ? "" : str;
        int len = str.trim().length();
        str = len > size ? str.substring(0, size) : str;
        return StringUtils.leftPad(str, size, padStr);
    }

    public static PrintWriter getWriter(HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        response.setContentType("text/html");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "86400");

        return writer;
    }

    /*public static String getMonth(Integer monthSerial) {
        try {
            return Constants.MONTH_LIST.get(monthSerial);
        } catch (Exception e) {
            return "";
        }
    }*/



    @SuppressWarnings("unchecked")
    public static Map<String, String> getOptions(String topOption, Map<String, String> optionMap) {
        Map<String, String> data = optionMap == null ? new HashMap() : new HashMap(optionMap);
        data.put("", "--" + topOption + "--");
        return data;
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> getOptions(String topOption, String ConstantsDotMapName) {
        Map<String, String> data;
        try {
            Class<?> clazz = this.getClass();
            Field field = clazz.getDeclaredField(ConstantsDotMapName);
            field.setAccessible(true);
            data = (Map<String, String>) field.get(this);
        } catch (Exception e) {
            data = new HashMap<>();
        }
        data.put("", "--" + topOption + "--");
        return data;
    }

    @SuppressWarnings("unchecked")
    public String getProp(String ConstantsDotMapName, String key) {
        Map<String, String> data;
        try {
            Class<?> clazz = this.getClass();
            Field field = clazz.getDeclaredField(ConstantsDotMapName);
            field.setAccessible(true);
            data = (Map<String, String>) field.get(this);
        } catch (Exception e) {
            data = new HashMap<>();
        }
        return data.get(key);
    }

    public Map<String, String> getLegalFormMap() {
        return LEGAL_FORMS;
    }

    public String getLegalForm(String key) {
        return GValidator.isBlankOrNull(key) ? null :  LEGAL_FORMS.get(key);
    }

    public Map<String, String> getPOPMap() {
        return POPS;
    }

    public String getPOP(String key) {
        return GValidator.isBlankOrNull(key) ? null :  POPS.get(key);
    }

    public Map<String, String> getInqTypesMap() {
        return INQ_TYPES;
    }

    public String getInqType(String key) {
        return GValidator.isBlankOrNull(key) ? null :  INQ_TYPES.get(key);
    }

    public Map<String, String> getCrModeMap() {
        return CR_MODES;
    }

    public String getCrMode(String key) {
        return GValidator.isBlankOrNull(key) ? null :  CR_MODES.get(key);
    }


    public Map<String, String> getCustomerTypeMap() {
        return CUSTOMER_TYPE;
    }

    public String getCustomerType(String key) {
        return GValidator.isBlankOrNull(key) ? null :  CUSTOMER_TYPE.get(key);
    }
    /*public static String getDocPath(Document document) {
        if (document == null) return Constants.BACK_UP_DOC_URI;
        try {

            return Constants.DOC_SRC_LEADER + "/"
                    + document.getDiscriminatorValue() + "/"
                    + document.getGivenName()+"."
                    + document.getExtension();
        } catch (Exception e) {
            return Constants.BACK_UP_DOC_URI;
        }
    }*/



    /**
     * These methods invoked inside this method, has been called from FTL pages
     * SO....oooooo Do not destroy these methods
     */
    private static void callConfirm() {
        getInquiryStatus(1);
        getInquiryStatusDesc(1);
        // getDocPath(null);

    }

    public static final Map<String, String> CR_MODES = new HashMap<String, String>() {{
        put("", "");
        put(Constants.INQ_CR_MODE_INST.toString(), "Installment");
        put(Constants.INQ_CR_MODE_NON_INST.toString(), "Non-Installment");
        put(Constants.INQ_CR_MODE_CARD.toString(), "Card");
    }};

    public static final Map<String, String> INQ_TYPES = new HashMap<String, String>() {{
        put("", "");
        put("2", "New / Requested");
        put("1", "Living");
        put("3", "Others");
    }};
    public static final Map<String, String> POPS = new HashMap<String, String>() {{
        put("", "");
        put("0", "Fortnightly");
        put("1", "Monthly");
        put("2", "Bi-Monthly");
        put("3", "Quarterly");
        put("6", "Half Yearly");
        put("12", "Yearly");
        put("-1", "Others");
    }};

    public static final Map<String, String> LEGAL_FORMS = new HashMap<String, String>() {{
        put("", "");
        put("0", "Proprietor");
        put("1", "Private Ltd. Co.");
        put("2", "Public Ltd. Co.");
        put("3", "Partnership");
        put("4", "Co-Operative");
        put("5", "Public Sector");
        put("6", "Multinational");
        put("7", "Others");
    }};

    public static final Map<String, String> CUSTOMER_TYPE = new HashMap<String, String>() {{
        put("", "");
        put("1", "Individual");
        put("2", "Proprietor");
        put("3", "Company");
    }};
}
