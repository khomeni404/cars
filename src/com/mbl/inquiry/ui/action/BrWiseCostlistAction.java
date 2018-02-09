package com.mbl.inquiry.ui.action;

import com.ibbl.cib.bl.CIBBLDataList;
import com.ibbl.common.DateUtil;
import com.ibbl.common.StringUtil;
import com.ibbl.context.ContextLocator;
import com.ibbl.inquiry.util.GenericReport;
import com.ibbl.inquiry.ui.form.ReportForm;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * package com.ibbl.report.ui.action;
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p/>
 * Original author: Khomeni
 * Date: 3/24/13(11:53 AM)
 * Last modification by: $Author: ayat $
 * Last modification on $Date: 2017/08/01 02:24:08 $
 * Current revision: $Revision: 1.2 $
 * <p/>
 * Revision History:
 * ------------------
 */
public class BrWiseCostlistAction extends Action
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
            parameters.put("fromDate", reportForm.getFromDate());
        }
        if(!StringUtil.isBlankOrNull(reportForm.getToDate()))
        {
            parameters.put("toDate", reportForm.getToDate());
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

        String filename = reportForm.getFilename();
        String format = reportForm.getFormat();

        parameters.put("REPORT_TITLE", "Br Wise Cost List");
        parameters.put("filename", filename);
        parameters.put("format", format);
        parameters.put("printDate", DateUtil.getCurrentDate());

        ByteArrayOutputStream baos = greport.generateReport(parameters, context, filename);
        httpServletResponse.setContentLength(baos.size());
        httpServletResponse.getOutputStream().write(baos.toByteArray());
        return null;
    }
}