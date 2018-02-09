package com.mbl.common.dao;

import com.mbl.ir.dao.InquiryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Khomeni
 * Created on : 17-May-17 at 12:56 AM
 */

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class DaoFactory {
    @Autowired
    public CommonDAO commonDAO;

    @Autowired
    public InquiryDAO inquiryDAO;


}
