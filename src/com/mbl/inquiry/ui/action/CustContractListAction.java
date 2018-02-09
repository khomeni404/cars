package com.mbl.inquiry.ui.action;

import com.ibbl.cib.bl.*;
import com.ibbl.common.*;
import com.ibbl.context.*;
import com.ibbl.inquiry.ui.form.ReportForm;
import com.ibbl.inquiry.util.GenericReport;
import org.apache.struts.action.*;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;

/**
 * package com.ibbl.report.ui.action;
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p/>
 * Original author: Khomeni
 * Date: 3/25/13(3:02 PM)
 * Last modification by: $Author: ayat $
 * Last modification on $Date: 2017/08/01 02:24:08 $
 * Current revision: $Revision: 1.2 $
 * <p/>
 * Revision History:
 * ------------------
 */
public class CustContractListAction extends Action
{
    public ActionForward execute(ActionMapping actionMapping,
                                 ActionForm actionForm,
                                 HttpServletRequest httpServletRequest,
                                 HttpServletResponse httpServletResponse) throws Exception
    {
        ReportForm reportForm = (ReportForm) actionForm;
        GenericReport greport = new GenericReport();
        Map parameters = new HashMap();
        if (isCancelled(httpServletRequest))
        {
            reportForm.reset(actionMapping, httpServletRequest);
            return actionMapping.findForward(CIBBLDataList.FORWARD_CANCEL);
        }
        if(!StringUtil.isBlankOrNull(reportForm.getBrCode()))
        {
            parameters.put("brCode", reportForm.getBrCode());
        }
        if(!StringUtil.isBlankOrNull(reportForm.getFromDate()))
        {
            parameters.put("refDate", reportForm.getFromDate());
        }
        if(!StringUtil.isBlankOrNull(reportForm.getCustID()))
        {
            parameters.put("custID", reportForm.getCustID());
        }

        if (reportForm.getFormat().equals("pdf"))
        {
            httpServletResponse.setContentType("application/pdf");
        }
        else if (reportForm.getFormat().equals("excel"))
        {
            httpServletResponse.setContentType("application/vnd.ms-excel");
        }
        else if (reportForm.getFormat().equals("html"))
        {
            httpServletResponse.setContentType("text/html");
        }
        String context = ContextLocator.getConfigRoot();

        String filename=reportForm.getFilename();
        String format =reportForm.getFormat();
        parameters.put("REPORT_TITLE", "Contract List");
        parameters.put("filename", filename);
        parameters.put("format", format);
        parameters.put("printDate", DateUtil.getCurrentDate());

        ByteArrayOutputStream baos = greport.generateReport(parameters, context, filename);
        httpServletResponse.setContentLength(baos.size());
        httpServletResponse.getOutputStream().write(baos.toByteArray());
        return null;
    }
}