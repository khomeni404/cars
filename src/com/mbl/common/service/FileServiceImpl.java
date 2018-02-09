package com.mbl.common.service;

import com.google.common.io.Files;
import com.mbl.common.bean.DocumentBean;
import com.mbl.common.dao.DaoFactory;
import com.mbl.common.model.Document;
import com.mbl.util.Constants;
import com.mbl.util.Utility;
import net.softengine.model.User;
import net.softengine.util.ActionResult;
import net.softengine.util.SessionUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Copyright @ Soft Engine [www.soft-engine.net].
 * Created on 15-Jan-18 at 10:19 AM
 * Created By : Khomeni
 * Edited By : Khomeni &
 * Last Edited on : 15-Jan-18
 * Version : 1.0
 */

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class FileServiceImpl extends DaoFactory implements FileService {

    @Override
    @Transactional(readOnly = false)
    public ActionResult writeAndSaveDocument(DocumentBean documentBean) {
        ActionResult result = new ActionResult(false);
        User staffDetails = documentBean.getOwner();
        MultipartFile mpf = documentBean.getDocument();
        String type = documentBean.getType();
        String indicator = documentBean.getIndicator();
        /*if (type.equals(Constants.SIGNATURE)) {
            Signature signature = commonDAO.get(Signature.class, "owner", staffDetails);
            boolean deleteOldFile = signature != null;
            signature = signature == null ? new Signature() : signature;
            signature.setTitle("Staff Signature");
            signature.setOwner(staffDetails);
            signature.setIndicator(indicator);
            result = writeDocument(signature, mpf, deleteOldFile);

        } else if (type.equals(Constants.AVATAR)) {
            Avatar avatar = commonDAO.get(Avatar.class, "owner", staffDetails);
            boolean deleteOldFile = avatar != null;
            avatar = avatar == null ? new Avatar() : avatar;
            avatar.setTitle("Staff Photo");
            avatar.setOwner(staffDetails);
            avatar.setIndicator(indicator);
            result = writeDocument(avatar, mpf, deleteOldFile);

        } else {

        }*/

        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public ActionResult writeDocument(Document doc, MultipartFile mpf, boolean deleteOldDoc) {
        ActionResult result = new ActionResult(false);
        if (mpf.getSize() < 1) {
            result.setMsg("File/Image size is ZERO or File/Image is Missing.");
            return result;
        }
        String saveDirectory = Constants.DOC_PATH + "/" + doc.getDiscriminatorValue();
        String message = "";
        if (deleteOldDoc) {
            String gn = doc.getGivenName();
            String ext = doc.getExtension();
            File oldFile = new File(saveDirectory + File.separator + gn + "." + ext);
            try {
                boolean deleted = oldFile.delete();
                if (!deleted) {
                    message = "Old doc deleted Successfully";
                }
            } catch (Exception e) {
                result.setMsg("System failed to create Directory");
                return result;
            }
        }

        String fileName = mpf.getOriginalFilename();
        String uniqueName = Utility.getUniqueFileName();
        String originalName = Files.getNameWithoutExtension(fileName);

        try {
            File dir = new File(saveDirectory);
            if (!dir.exists()) {
                boolean dirCreated = dir.mkdirs();
                if (!dirCreated) {
                    result.setMsg("System failed to create Directory");
                }
            }
            String ext = Files.getFileExtension(fileName);// SEUtil.getFileExtension(mpf);
            File file = new File(saveDirectory + File.separator + uniqueName + "." + ext);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(mpf.getBytes());
            fos.close();
            fos.flush();

            doc.setOriginalName(originalName);
            doc.setGivenName(uniqueName);
            doc.setResourceDirectory(saveDirectory);
            doc.setExtension(ext);
            doc.setRecordDate(new Date());
            doc.setOperatorName(SessionUtil.getSessionUserName());
            commonDAO.save(doc);

            result.setSuccess(true);

            result.setMsg(doc.getClass().getSimpleName() + " saved Successfully");
            return result;
        } catch (IOException ed) {
            result.setMsg(ed.getMessage());

        }
        return result;
    }
}
