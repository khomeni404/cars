package com.mbl.ir.service;

import com.mbl.common.dao.DaoFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Copyright @ Soft Engine [www.soft-engine.net].
 * Created on 08-Feb-18 at 12:17 PM
 * Created By : Khomeni
 * Edited By : Khomeni &
 * Last Edited on : 08-Feb-18
 * Version : 1.0
 */

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class InquiryServiceImpl extends DaoFactory implements InquiryService {

   public String getNewInquiryNo(String brCode) {
       return inquiryDAO.getNewInquiryNo(brCode);
    }
}
