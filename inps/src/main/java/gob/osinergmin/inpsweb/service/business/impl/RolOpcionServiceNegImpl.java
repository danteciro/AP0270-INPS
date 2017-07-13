/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.service.business.RolOpcionServiceNeg;
import gob.osinergmin.inpsweb.service.dao.RolOpcionDAO;
import gob.osinergmin.mdicommon.domain.dto.RolOpcionDTO;
import gob.osinergmin.mdicommon.domain.ui.RolOpcionFilter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jpiro
 */
@Service("rolOpcionServiceNeg")
public class RolOpcionServiceNegImpl implements RolOpcionServiceNeg{
    private static final Logger LOG = LoggerFactory.getLogger(RolOpcionServiceNegImpl.class);    
    @Inject
    private RolOpcionDAO rolOpcionDAO;
    
    @Override
    @Transactional(readOnly = true)
    public List<RolOpcionDTO> findRolOpcion(RolOpcionFilter filtro){
        List<RolOpcionDTO> retorno= new ArrayList<RolOpcionDTO>();
        try{
            retorno=rolOpcionDAO.find(filtro);
        }catch(Exception e){
            LOG.error("Error findRolOpcion",e);
        }
        return retorno;
    }    
}
