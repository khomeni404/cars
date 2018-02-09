package com.mbl.inquiry.ui.form;

import org.apache.struts.validator.ValidatorForm;

/**
 * package com.ibbl.report.ui.form;
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p/>
 * Original author: Khomeni
 * Date: 3/5/13(12:31 PM)
 * Last modification by: $Author: ayat $
 * Last modification on $Date: 2017/08/01 02:24:08 $
 * Current revision: $Revision: 1.2 $
 * <p/>
 * Revision History:
 * ------------------
 */
public class ReportForm extends ValidatorForm
{
    private String accState = null;
    private String accType = null;
    private String custID = null;
    private String reqDate = null;
    private String toDate = null;
    private String fromDate = null;
    private String userID = null;

    private String filename = null;
    private String[] filenames = {};
    private String format = null;
    private String reportTitle = null;
    private String bankName = null;
    private String brName = null;
    private String brCode = null;
    private String brAddress = null;
    private String groupCustID = null;
    private String year = null;
    private String toYear = null;
    private String fromYear = null;
    private String month = null;
    private String status = null;

    public String getAccState()
    {
        return accState;
    }

    public void setAccState(String accState)
    {
        this.accState = accState;
    }

    public String getAccType()
    {
        return accType;
    }

    public void setAccType(String accType)
    {
        this.accType = accType;
    }

    public String getCustID()
    {
        return custID;
    }

    public void setCustID(String custID)
    {
        this.custID = custID;
    }

    public String getReqDate()
    {
        return reqDate;
    }

    public void setReqDate(String reqDate)
    {
        this.reqDate = reqDate;
    }

    public String getToDate()
    {
        return toDate;
    }

    public void setToDate(String toDate)
    {
        this.toDate = toDate;
    }

    public String getFromDate()
    {
        return fromDate;
    }

    public void setFromDate(String fromDate)
    {
        this.fromDate = fromDate;
    }

    public String getUserID()
    {
        return userID;
    }

    public void setUserID(String userID)
    {
        this.userID = userID;
    }

    public String getFilename()
    {
        return filename;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    public String[] getFilenames()
    {
        return filenames;
    }

    public void setFilenames(String[] filenames)
    {
        this.filenames = filenames;
    }

    public String getFormat()
    {
        return format;
    }

    public void setFormat(String format)
    {
        this.format = format;
    }

    public String getReportTitle()
    {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle)
    {
        this.reportTitle = reportTitle;
    }

    public String getBankName()
    {
        return bankName;
    }

    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    public String getBrName()
    {
        return brName;
    }

    public void setBrName(String brName)
    {
        this.brName = brName;
    }

    public String getBrCode()
    {
        return brCode;
    }

    public void setBrCode(String brCode)
    {
        this.brCode = brCode;
    }

    public String getBrAddress()
    {
        return brAddress;
    }

    public void setBrAddress(String brAddress)
    {
        this.brAddress = brAddress;
    }

    public String getGroupCustID()
    {
        return groupCustID;
    }

    public void setGroupCustID(String groupCustID)
    {
        this.groupCustID = groupCustID;
    }

    public String getYear()
    {
        return year;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    public String getToYear()
    {
        return toYear;
    }

    public void setToYear(String toYear)
    {
        this.toYear = toYear;
    }

    public String getFromYear()
    {
        return fromYear;
    }

    public void setFromYear(String fromYear)
    {
        this.fromYear = fromYear;
    }

    public String getMonth()
    {
        return month;
    }

    public void setMonth(String month)
    {
        this.month = month;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
}