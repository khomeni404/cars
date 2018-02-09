package com.mbl.test.incident.service;

import com.ibbl.common.dao.DaoFactory;
import org.springframework.stereotype.Service;

/**
 * @author Khomeni
 * Created on : 16-May-17
 */
@Service
public class IncidentServiceImpl extends DaoFactory implements IncidentService  {
    @Override
    public Integer maxRefNo() {
        return incidentDAO.maxRefNo();
    }
}
