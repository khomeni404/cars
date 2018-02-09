package com.mbl.util;

import com.ibbl.common.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.validator.GenericValidator;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p>
 * Original author: Ayatullah Khomeni
 * <br/> Date: 29/05/2016
 * <br/> Last modification by: ayat $
 * <br/> Last modification on 29/05/2016: 4:59 PM
 * <br/> Current revision: : 1.1.1.1
 * </p>
 * Revision History:
 * ------------------
 */

public interface RectifyUtil {
    DecimalFormat DF_ZERO = new DecimalFormat(".");
    Boolean ERROR = true;
    String PAD_STR_S = " ";
    String PAD_STR_3S = "   ";
    String PAD_STR_0 = "0";
    String DEFAULT_DATE = "00000000";
    String TWELVE_ZERO = "000000000000";
    String REG_EX_PERSON_NAME_TEXT = "[a-zA-Z .-]+";
    Pattern FORBIDDEN_WORD_PATTERN = Pattern.compile(String.join("|", FinalData.FORBIDDEN_WORD_LIST.stream().map(word -> "\\b" + word + "\\b").collect(Collectors.toList())), Pattern.CASE_INSENSITIVE); //Pattern.compile("\\bLATE\\b|\\bS/O\\b|\\bSO\\b|\\bD/O\\b|\\bDO\\b|\\bW/O\\b|\\bWO\\b", Pattern.CASE_INSENSITIVE);

    static String cutOrPadRight(String str, int size) {
        return cutOrPadRight(str, size, PAD_STR_S);
    }

    static String cutOrPadRight(String str, int size, String padStr) {
        str = str == null ? "" : str.trim();
        int len = str.length();
        str = len > size ? str.substring(0, size) : str;
        return StringUtils.rightPad(str, size, padStr);
    }

    static String cutOrPadLeft(String str, int size) {
        return cutOrPadLeft(str, size, PAD_STR_0);
    }

    static String cutOrPadLeft(String str, int size, String padStr) {
        str = str == null ? "" : str.trim();
        int len = str.trim().length();
        str = len > size ? str.substring(0, size) : str;
        return StringUtils.leftPad(str, size, padStr);
    }

    /**
     * @param accType   String type
     * @param repayMode int repay mode
     * @return Contract Type
     * @see com.ibbl.util.enums.IACT#getContractType(String, int)
     * Updated from last patch on 192.168.36.231:cibNew/
     * @see com.ibbl.cib.util.CIBUtil#getContractType(String, String, int)
     * @deprecated
     */
    /*static String getContractType(String accType, int repayMode) {
        boolean nonInst = repayMode == CIBDictionary.REPAY_NON_INSTALLMENT;
        if (!NumberUtils.isDigits(accType)) {
            return Arrays.asList("LC", "GU", "BU").contains(accType) ? accType : (nonInst ? "ON" : "OI");
        }
        Integer type = Integer.valueOf(accType);
        if (type == InvestmentDictionary.INVESTMENT_TYPE_BAI_MUAJJAL) {
            return nonInst ? "BB" : "BI";
        } else if (type == InvestmentDictionary.INVESTMENT_TYPE_BAI_MURABAHA) {
            return nonInst ? "BC" : "OI";
        } else if (type == InvestmentDictionary.INVESTMENT_TYPE_HPSM) {
            return nonInst ? "ON" : "HS";
        } else if (type == InvestmentDictionary.INVESTMENT_TYPE_MUSHARAKA) {
            return nonInst ? "MS" : "OI";
        } else if (type == InvestmentDictionary.INVESTMENT_TYPE_BAI_SALAM) {
            return nonInst ? "BS" : "OI";
        } else if (type == InvestmentDictionary.INVESTMENT_TYPE_MPI) {
            return nonInst ? "MP" : "OI";
        } else if (type == InvestmentDictionary.INVESTMENT_TYPE_MUDARABA) {
            return nonInst ? "ON" : "OI";
        } else if (type == InvestmentDictionary.INVESTMENT_TYPE_BAI_MURABAHA_TR) {
            return nonInst ? "TR" : "OI";
        } else if (Arrays.asList(46, 51, 52, 53, 54, 55, 56, 59, 60).contains(type)) {
            return nonInst ? "QA" : "OI";
        } else if (type == InvestmentDictionary.INVESTMENT_TYPE_BAI_AS_SARF) {
            return nonInst ? "FB" : "OI";
        } else if (type == InvestmentDictionary.INVESTMENT_TYPE_IBP) {
            return nonInst ? "LB" : "OI";
        } else if (Arrays.asList(InvestmentDictionary.INVESTMENT_TYPE_BAI_MURABAHA_BOE, InvestmentDictionary.INVESTMENT_TYPE_MIB).contains(type)) {
            return nonInst ? "MB" : "OI";
        } else if (type == InvestmentDictionary.INVESTMENT_TYPE_BAI_MUAJJAL_FCBILLS) {
            return nonInst ? "BB" : "OI";
        } else if (Arrays.asList(InvestmentDictionary.INVESTMENT_TYPE_MDB_INLAND, InvestmentDictionary.INVESTMENT_TYPE_MDB_FOREIGN).contains(type)) {
            return nonInst ? "MD" : "OI";
        } else if (type == InvestmentDictionary.INVESTMENT_TYPE_MURA_FCI) {
            return nonInst ? "BU" : "OI";
        } else {
            return nonInst ? "ON" : "OI";
        }
    }*/

    /**
     * As per BRPD Circular :
     * BB: Circular No-8 on 14/06/2012 AND
     * IBBL: Instruction Circular No-CIW/IAD/1282 on 25/09/2012 (Page 6, Section 6-e)
     * <p>
     * While reporting to the CIB, the rescheduled loans/advances should be shown
     * as RS-l for f,rrst time rescheduling, RS-2 for second time rescheduling and
     * RS-3 for third time rescheduling. If rescheduling facility is availed through
     * interest waiver, reporting should be RSIW-I for first time rescheduling,
     * RSIW-2 for second time rescheduling and RSIW-3 for third time
     * rescheduling.
     * <p>
     *
     * @param resX Total number of reschedule Times
     * @return RS formatted Reschedule Times
     */
    static String getRescheduleX(Integer resX) {
        if (resX == null || resX <= 0) {
            return "";
        } else {
            return resX == 1 ? "RS-1" : resX == 2 ? "RS-2" : "RS-3";
        }
    }

    @Deprecated // Can be deleted when not in use
    static String getContractType1(String accType, int repayMode) {
        boolean nonInst = repayMode == CIBDictionary.REPAY_NON_INSTALLMENT;
        if (!NumberUtils.isDigits(accType)) {
            return Arrays.asList("LC", "GU", "BU").contains(accType) ? accType : (nonInst ? "ON" : "OI");
        }
        Integer type = Integer.valueOf(accType);
        if (type == 41) {
            return nonInst ? "BB" : "BI";
        } else if (type == 42) {
            return nonInst ? "BC" : "OI";
        } else if (type == 43) {
            return nonInst ? "ON" : "HS";
        } else if (type == 44) {
            return nonInst ? "MS" : "OI";
        } else if (type == 45) {
            return nonInst ? "BS" : "OI";
        } else if (type == 47) {
            return nonInst ? "MP" : "OI";
        } else if (type == 48) {
            return nonInst ? "ON" : "OI";
        } else if (type == 49) {
            return nonInst ? "TR" : "OI";
        } else if (Arrays.asList(46, 51, 52, 53, 54, 55, 56, 59, 60).contains(type)) {
            return nonInst ? "QA" : "OI";
        } else if (type == 81) {
            return nonInst ? "FB" : "OI";
        } else if (type == 82) {
            return nonInst ? "LB" : "OI";
        } else if (Arrays.asList(83, 84).contains(type)) {
            return nonInst ? "MB" : "OI";
        } else if (type == 85) {
            return nonInst ? "BB" : "OI";
        } else if (Arrays.asList(86, 89).contains(type)) {
            return nonInst ? "MD" : "OI";
        } else if (type == 88) {
            return nonInst ? "BU" : "OI";
        } else {
            return nonInst ? "ON" : "OI";
        }
    }


    static String getDaysPaymentDelay(Date from, Date to) {
        int days = net.softengine.util.DateUtil.getDayDifference(from, to);
        if (days <= 29) {
            days = 0;
        } else if (days <= 59) {
            days = 30;
        } else if (days <= 89) {
            days = 60;
        } else if (days <= 119) {
            days = 90;
        } else if (days <= 149) {
            days = 120;
        } else if (days <= 179) {
            days = 150;
        } else {
            days = 180;
        }
        return cutOrPadLeft(String.valueOf(days), 3);
    }

    /*static String getContractPhase(Date closeDate, Date dueDate, Integer accState) {
        try {
            if (accState == InvestmentDictionary.INV_ACCOUNT_STATE_WRITE_OFF_BL) {
                return "LV";
            }
            if (closeDate == null) {
                return "LV";
            } else if (DateUtil.isBefore(dueDate, closeDate)) {
                return "TM";
            } else {
                return "TA";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "LV";
        }
    }*/


    @Deprecated // use isNonClassified
    static boolean isDefaulter(String bbClCode, Double balance, Integer accState, Date lastResDate) {
        if (GenericValidator.isBlankOrNull(bbClCode)) return false;
        return (Arrays.asList("9012", "9013").contains(bbClCode) && Arrays.asList(18, 19).contains(accState))
                || (bbClCode.equals("9016") && Arrays.asList(17, 18, 19).contains(accState))
                || (Arrays.asList("9014", "9015").contains(bbClCode))
                ||
                ((lastResDate == null && balance > 1000000.0 && Arrays.asList(18, 19).contains(accState))
                        || (lastResDate == null && balance < 1000000.0 && Arrays.asList(17, 18, 19).contains(accState))
                        || lastResDate != null && accState == 19);
    }

   /* static boolean isNonClassified(double principal, String accType, Integer accState) throws Exception {
        return FinalData.NON_CLASSIFIED_STATE_LIST.contains(accState)
                ||
                accState.equals(FinalData.ACC_STATE_SS) && accType.equals(String.valueOf(IACT.HPSM.CODE)) && principal < 1000000.0D;
    }*/

    static String getSubsidizedCredit(String accType) {
        return accType.equals("55") ? "1" : "0";
    }

    static String getPreFinLoan(String accType) {

        return Arrays.asList("45", "84").contains(accType) ? "1" : "0";
    }

    static String getPGType(String bbSecurityCode) {
        return Arrays.asList("110", "072", "120", "130", "140").contains(bbSecurityCode) ?
                bbSecurityCode : cutOrPadRight("", 3, PAD_STR_S);
    }


    static String getSecurityType(String bbSecurityCode) {
        if (NumberUtils.isDigits(bbSecurityCode)) {
            int code = Integer.parseInt(bbSecurityCode);
            if (Arrays.asList(10, 20, 25, 30, 35, 36, 37, 38, 39,
                    40, 45, 50, 60, 71, 73, 74, 75, 76, 80).contains(code)) {
                return cutOrPadRight(bbSecurityCode, 3);
            } else if (Arrays.asList(79, 90, 100).contains(code)) {
                return "79 ";
            }
        }
        return cutOrPadRight("", 3, PAD_STR_S);
    }

    static void main(String[] args) {
        System.out.println(Arrays.asList(11, 12, 13, 21, 22, 23, 31, 32, 33, 43).contains("wq"));
    }

    static String getSmeCode(String smeCode) {
        if (GenericValidator.isBlankOrNull(smeCode)) {
            return null;
        } else {
            if (GenericValidator.isInt(smeCode)) {
                int sme = Integer.valueOf(smeCode);
                if (Arrays.asList(11, 12, 13, 21, 22, 23, 31, 32, 33, 43).contains(sme)) { // Table 4.3.25 : Enterprise Type
                    return smeCode;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
    }

    static String cibFormatDate(Date date) {
        try {
            if (date != null) {
                DateFormat formatter = new SimpleDateFormat("ddMMyyyy");
                return formatter.format(date);
            } else {
                return DEFAULT_DATE;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DEFAULT_DATE;
        }
    }

    static Double toZeroDecimal(Double d) throws NumberFormatException {
        String stringValue = DF_ZERO.format(d);
        return Double.valueOf(stringValue);
    }

    static Double toZeroDecimal(Integer i) {
        String stringValue = DF_ZERO.format(i);
        return Double.valueOf(stringValue);
    }


    static String removeDecimal(Double d) {
        try {
            return d == null ? "" : String.format("%.0f", d);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*static Double[] getAmounts(BreakupSummaryBean bean) {
        BreakupBean totalDr = bean.getTotalDr();
        BreakupBean totalCr = bean.getTotalCr();
        Double dr = 0.0;
        dr += totalDr.getPrincipal();
        dr += totalDr.getProfit();
        dr += totalDr.getRent();
        dr += totalDr.getRiskFund();
        dr += totalDr.getSupervisoryCommission();

        Double cr = 0.0;
        cr += totalCr.getPrincipal();
        cr += totalCr.getProfit();
        cr += totalCr.getRent();
        cr += totalCr.getRiskFund();
        cr += totalCr.getSupervisoryCommission();

        Double balance = dr - cr;
        return new Double[]{dr, cr, balance};
    }*/


}
