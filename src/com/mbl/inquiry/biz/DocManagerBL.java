package com.mbl.inquiry.biz;

import com.google.common.io.Files;
import com.ibbl.cib.util.CIBUtil;
import com.ibbl.exception.BLException;
import com.ibbl.inquiry.model.Inquiry;
import com.ibbl.inquiry.model.ReportDoc;
import com.ibbl.inquiry.util.ActionResult;
import com.ibbl.util.CIBDictionary;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.commons.validator.GenericValidator;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * package com.ibbl.biz;
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p>
 * Original author: reza
 * Date: Sep 4, 2008 (3:45:01 PM)
 * Last modification by: reza $
 * Last modification on: Sep 4, 2008 $ (3:45:01 PM) $
 * Current revision: $Revision: 1.7 $
 * <p>
 * Revision History:
 * ------------------
 */
public class DocManagerBL {

    public DocManagerBL() {
    }


    public void upload(ReportDoc docBean) {
    }

    public void modify() {

    }

    public void remove() {

    }

    public void view() {

    }

    public boolean verify() {
        return true;
    }


    public static ActionResult verifyDoc(ReportDoc docBean) {
        ActionResult r = new ActionResult(false);
        if (docBean == null) {
            r.setMsg("Document Info missing.<br>");
            return r;
        } else {
            if (GenericValidator.isBlankOrNull(docBean.getFileName())) {
                r.setMsg("Document File Name missing.<br>");
                return r;
            } else {
                String fileName = docBean.getFileName();

                if (docBean.getDocStream() == null || docBean.getDocStream().length == 0) {
                    r.setMsg("Document has Empty Size. (" + docBean.getFileName() + ")<br>See " + fileName);
                    return r;
                } else if (docBean.getDocStream().length > (CIBDictionary.CIB_RPT_ALLOWED_FILE_SIZE * 1000000)) {
                    r.setMsg("Maximum file size is " + CIBDictionary.CIB_RPT_ALLOWED_FILE_SIZE + " kb.<br>See "+fileName);
                    return r;
                }

                String ext = Files.getFileExtension(fileName);
                ext = GenericValidator.isBlankOrNull(ext) ? "" : ext.toLowerCase();
                if (!CIBDictionary.CIB_RPT_ALLOWED_FILE_TYPE_LIST.contains(ext)) {
                    r.setMsg("Only " + CIBDictionary.CIB_RPT_ALLOWED_FILE_TYPE_LIST.toString() + " are allowed.<br>See "+fileName);
                    return r;
                }
            }
        }
        r.setSuccess(true);
        return r;
    }

    public static ActionResult writeDocs(Inquiry inquiryBean) throws BLException {
        ActionResult result = new ActionResult(false);
        Set<ReportDoc> validDocSet = new HashSet<>();
        Set<ReportDoc> docSet = inquiryBean.getDocSet();

        if (docSet != null) {
            // Validating Doc
            for (ReportDoc doc : docSet) {
                ActionResult r = verifyDoc(doc);
                if (!r.isSuccess()) {
                    return r;
                }
            }


            String message = "";
            for (ReportDoc doc : docSet) {
                String originalName;
                String givenName;
                String docPath;

                try {
                    originalName = doc.getFileName();
                    String ext = Files.getFileExtension(originalName.toLowerCase());
                    givenName = CIBUtil.getUniqueFileName() + "." + ext;
                    docPath = CIBUtil.getDocPath(givenName, inquiryBean.getBrCode(), inquiryBean.getYear());
                    if (!GenericValidator.isBlankOrNull(docPath)) {
                        FileOutputStream is = new FileOutputStream(docPath);
                        doc.setGivenName(givenName);
                        if (doc.getOid() == null) {
                            // inquiryBean.getDocSet().add(doc);
                            is.write(doc.getDocStream());
                            validDocSet.add(doc);
                            is.flush();
                            is.close();
                        }
                    } else {
                        message += "File path is missing. (" + doc.getFileName() + ")<br>";
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            result.setSuccess(validDocSet.size() > 0);
            result.setDataObject(validDocSet);
            result.setMsg(message);
            return result;
        } else {
            result.setSuccess(false);
            result.setMsg("No Doc Found !");
            return result;
        }

    }


    /**
     * @param in  In Stream
     * @param out Out Stream
     * @throws java.io.IOException
     */
    public static void writeToOutputStream(InputStream in, OutputStream out) throws IOException {
        int readBytes = 0;
        while ((readBytes = in.read()) != -1) {
            out.write(readBytes);
        }
        out.flush();
        out.close();
        in.close();
    }

    public static BufferedImage toImage(int w, int h, byte[] data) {
        DataBuffer buffer = new DataBufferByte(data, w * h);

        int pixelStride = 4; //assuming r, g, b, skip, r, g, b, skip...
        int scanLineStride = 4 * w; //no extra padding
        int[] bandOffsets = {0, 1, 2}; //r, g, b
        WritableRaster raster = Raster.createInterleavedRaster(buffer, w, h, scanLineStride, pixelStride, bandOffsets, null);

        ColorSpace colorSpace = ColorSpace.getInstance(ColorSpace.CS_sRGB);
        boolean hasAlpha = false;
        boolean isAlphaPreMultiplied = false;
        int transparency = Transparency.OPAQUE;
        int transferType = DataBuffer.TYPE_BYTE;
        ColorModel colorModel = new ComponentColorModel(colorSpace, hasAlpha, isAlphaPreMultiplied, transparency, transferType);

        return new BufferedImage(colorModel, raster, isAlphaPreMultiplied, null);
    }

    /**
     * @param inputFiles
     * @param os
     * @throws com.lowagie.text.DocumentException
     * @throws java.io.IOException
     */
    public static void convertJpgToPDF(java.util.List<File> inputFiles, OutputStream os) throws DocumentException, IOException {

        //com.lowagie.text.Rectangle r =  new com.lowagie.text.Rectangle(200, 280);

        PdfWriter writer = null;
        Document document = null;

        for (File f : inputFiles) {

            // This method may be used to compress image
//                Image awtImage = Toolkit.getDefaultToolkit().createImage(f.getAbsolutePath());
//
//                 0.2f is to compress image. value may be between 0 (No Compression ) to 1 (Max Compression).
//                com.lowagie.text.Image img = com.lowagie.text.Image.getInstance(util, awtImage, 0.2f);

            //File fOut = new File();
            //ImageCompressionUtil imageCompressionUtil =  new ImageCompressionUtil();
            //imageCompressionUtil.compress(f,f,0.5f, ImageWriteParam.MODE_EXPLICIT, ImageDictionary.IMAGE_FORMAT_JPEG);
            com.lowagie.text.Image img = com.lowagie.text.Image.getInstance(f.getAbsolutePath());
            //very important attribute to convert image with same quality and full canvas
            img.setAlignment(com.lowagie.text.Image.ALIGN_CENTER);
//                img.setAlignment(com.lowagie.text.Image.ALIGN_MIDDLE);

            // This is executed for first loop. Allpages are same size. So, if doc object is created
            //with first image size, it will work for also other pages
            if (document == null) {

                /**
                 * Commented out the lines below, when changed iText-2.1.7.jar from itext-1.3.1.jar
                 */
                // com.lowagie.text.Rectangle r = new com.lowagie.text.Rectangle(img.plainWidth(), img.plainHeight());
                // document = new Document(r);

                //document.setPageSize(PageSize.A4);
                //document.setMargins(20, 20, 20, 20);
                writer = PdfWriter.getInstance(document, os);

                document.open();
            }
            document.add(img);
            document.newPage();
        }

        if (document != null) {
            document.close();
        } else {
            throw new IOException("System failed to convert image from Jpg to PDF");
        }


        if (writer != null) {
            writer.flush();
            writer.close();
        } else {
            throw new IOException("System failed to write pdf from image file");
        }

    }


    /**
     * @param inputFiles
     * @return byte[]
     * @throws com.lowagie.text.DocumentException
     * @throws java.io.IOException
     */
    public static byte[] convertJpgToPDFBytes(java.util.List<File> inputFiles) throws DocumentException, IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        convertJpgToPDF(inputFiles, os);
        return os.toByteArray();

    }


}
