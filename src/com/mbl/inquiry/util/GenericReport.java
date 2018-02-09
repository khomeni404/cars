package com.mbl.inquiry.util;

import com.ibbl.cib.util.*;
import com.ibbl.context.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.export.*;
import net.sf.jasperreports.engine.xml.*;

import java.io.*;
import java.sql.*;
import java.util.*;

/**
 * package com.ibbl.report.util;
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p/>
 * Original author: Khomeni
 * Date: 3/5/13(12:27 PM)
 * Last modification by: $Author: ayat $
 * Last modification on $Date: 2017/10/10 12:05:47 $
 * Current revision: $Revision: 1.3 $
 * <p/>
 * Revision History:
 * ------------------
 */
public class GenericReport
{
    public static final String LOGO_PATH = ContextLocator.getAppRoot()
            .concat("images").concat(File.separator).concat("logo.jpg");

    @SuppressWarnings("unchecked")
    public ByteArrayOutputStream generateReport(Map parameters, String context, String filename)
//            public ByteArrayOutputStream generateReport(Map parameters, String context, String filename, String userName)
    {
        Connection conn = null;
        DBConfig dbConfig = DBConfig.getInstance();
        try
        {
            conn = dbConfig.getJdBcConnection();
        }
        catch (Exception e)
        {
            return null;//resCarrier.addError("cError", new String[]{e.getMessage()});  //To change body of catch statement use File | Settings | File Templates.
        }
        try
        {
            JasperDesign jasperDesign = JRXmlLoader.load(context + "reportxml" + File.separator + filename + ".xml");

            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
//                RemittanceUtil remUtil = RemittanceUtil.getInstance();
//            parameters.put("BANK_NAME", remUtil.getBankName());
            //parameters.put("SOURCE_BRANCH_NAME", CipmsUtil.);
            //parameters.put("SOURCE_BRANCH_ADDRESS", remUtil.getBranchAddress());
            //parameters.put("REPORT_TITLE", parameters.get("REPORT_TITLE"));
            //parameters.put("OUTPUT_FILE_NAME", parameters.get("OUTPUT_FILE_NAME"));
            //parameters.put("REPORT_DATE", Calendar.getInstance().getTime().toString());
            //parameters.put("LOGO_PATH", LOGO_PATH);
            CIBUtil cibUtil = CIBUtil.getInstance();
            parameters.put("BANK_NAME", cibUtil.getBankName());
            parameters.put("SOURCE_BRANCH_NAME", cibUtil.getBranchName());
            parameters.put("SOURCE_BRANCH_ADDRESS", cibUtil.getBranchAddress());
            parameters.put("REPORT_TITLE", parameters.get("REPORT_TITLE"));
            parameters.put("OUTPUT_FILE_NAME", parameters.get("OUTPUT_FILE_NAME"));
            parameters.put("REPORT_DATE", Calendar.getInstance().getTime().toString());
//            parameters.put("LOGO_PATH", LOGO_PATH);


            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JasperPrint jasperPrint = null;
            try
            {
             jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);
            }
            catch(Exception e)
            {
                return null;
            }
            if (parameters.get("format").equals("pdf"))
            {
                JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
            }
            else if (parameters.get("format").equals("html"))
            {
                JRHtmlExporter htmlExporter = new JRHtmlExporter();
                htmlExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                htmlExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
                File imageDir = new File(context.substring(0, context.indexOf("WEB-INF"))+File.separator + "images");
                htmlExporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR, imageDir);
                htmlExporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.TRUE);
                htmlExporter.exportReport();
            }
            else if (parameters.get("format").equals("excel"))
            {
                JRXlsExporter exporter = new JRXlsExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
//                exporter.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
                exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
                exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, parameters.get("OUTPUT_FILE_NAME"));
                JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
                exporter.exportReport();
            }
            else if (parameters.get("format").equals("csv"))
            {
                JRCsvExporter exporter = new JRCsvExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
                exporter.exportReport();
            }
            else
            {
                JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
            }
            try
            {
                dbConfig.closeJdBcConnection(conn);
            }
            catch (Exception e)
            {
                return null;
            }
            return baos;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ByteArrayOutputStream generateReports(Map parameters, String context, String filename[])
    {
        Connection conn = null;
        DBConfig dbConfig = DBConfig.getInstance();
        try
        {
            conn = dbConfig.getJdBcConnection();
        }
        catch (Exception e)
        {
            return null;//resCarrier.addError("cError", new String[]{e.getMessage()});  //To change body of catch statement use File | Settings | File Templates.
        }
        try
        {
            JasperDesign []jasperDesign = new JasperDesign[filename.length];
            for(int i=0;i<filename.length;i++)
            {
                jasperDesign[i] = JRXmlLoader.load(context + "reportxml" + File.separator + filename[i] + ".xml");
            }

            JasperReport[] jasperReport = new JasperReport[filename.length];
            for(int i=0; i<jasperDesign.length;i++)
            {
                jasperReport[i] = JasperCompileManager.compileReport(jasperDesign[i]);
            }

            CIBUtil cibUtil = CIBUtil.getInstance();
            parameters.put("BANK_NAME", cibUtil.getBankName());
            parameters.put("SOURCE_BRANCH_NAME", cibUtil.getBranchName());
            parameters.put("SOURCE_BRANCH_ADDRESS", cibUtil.getBranchAddress());
            parameters.put("REPORT_TITLE", parameters.get("REPORT_TITLE"));
            parameters.put("OUTPUT_FILE_NAME", parameters.get("OUTPUT_FILE_NAME"));
            parameters.put("REPORT_DATE", Calendar.getInstance().getTime().toString());
            parameters.put("LOGO_PATH", LOGO_PATH);

            List printList = new ArrayList<JasperPrint>();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JasperPrint []jasperPrint = new JasperPrint[filename.length];
            for(int i=0;i<jasperReport.length;i++)
            {
                jasperPrint[i] = JasperFillManager.fillReport(jasperReport[i], parameters, conn);
                printList.add(jasperPrint[i]);
            }
            //printList.add(jasperPrint2);
            JasperPrint jasperFinalPrint = this.getCombinedReport(printList);

            if (parameters.get("format").equals("pdf"))
            {
                JasperExportManager.exportReportToPdfStream(jasperFinalPrint, baos);
            }
            else
            {
                JasperExportManager.exportReportToPdfStream(jasperFinalPrint, baos);
            }
            try
            {
                dbConfig.closeJdBcConnection(conn);
            }
            catch (Exception e)
            {
                return null;
            }
            return baos;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * Set the properties of the target report to the properties of the
     * source. You might want to change this up.
     *
     * @param source
     * @param target
     */
    private void copyProperties(final JasperPrint target, JasperPrint source)
    {
        /*target.setDefaultFont(source.getDefaultFont());
        target.setName(source.getName());
        target.setOrientation(source.getOrientation());
        target.setPageHeight(source.getPageHeight());
        target.setPageWidth(source.getPageWidth());*/
    }

    private void copyPages(final JasperPrint combinedReport, JasperPrint filledReport)
    {
        List pages = filledReport.getPages();

        if (pages == null)
        {
            return;
        }

        for (Iterator iter = pages.iterator(); iter.hasNext();)
        {
            JRPrintPage page = (JRPrintPage) iter.next();
            combinedReport.addPage(page);
        }
    }

    public JasperPrint getCombinedReport(List reports)
    {
        JasperPrint combinedReport = new JasperPrint();

//temp variable
        JasperPrint print = null;
        for (int i = 0; i < reports.size(); i++)
        {
            print = (JasperPrint) reports.get(i);

            this.copyPages(combinedReport, print);
            try
            {
                this.copyFonts(combinedReport, print);
            }
            catch (JRException e)
            {
                return null; //To change body of catch statement use File | Settings | File Templates.
            }
        }
        this.copyProperties(combinedReport, print);
        return combinedReport;
    }

    private void copyFonts(final JasperPrint combinedReport, JasperPrint filledReport) throws JRException
    {
        /*List fonts = filledReport.getFontsList();

        if (fonts == null)
        {
            return;
        }

        for (Iterator iter = fonts.iterator(); iter.hasNext();)
        {
            JRReportFont font = (JRReportFont) iter.next();
            if (!combinedReport.getFontsMap().containsKey(font.getName()))
            {
                combinedReport.addFont(font);
            }
        }*/
    }
}