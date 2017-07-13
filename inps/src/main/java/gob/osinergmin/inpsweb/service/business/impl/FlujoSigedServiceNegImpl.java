/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.mdicommon.domain.ui.FlujoSigedFilter;
import gob.osinergmin.inpsweb.service.business.FlujoSigedServiceNeg;
import gob.osinergmin.inpsweb.service.dao.FlujoSigedDAO;
import gob.osinergmin.mdicommon.domain.dto.FlujoSigedDTO;

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
@Service("flujoSigedNeg")
public class FlujoSigedServiceNegImpl implements FlujoSigedServiceNeg{
    private static final Logger LOG = LoggerFactory.getLogger(FlujoSigedServiceNegImpl.class); 
    
    @Inject
    private FlujoSigedDAO flujoSigedDAO;
    
    @Override
    @Transactional(readOnly = true)
    public List<FlujoSigedDTO> listarFlujoSiged(FlujoSigedFilter filtro){
        List<FlujoSigedDTO> retorno=null;
        try{
            retorno = flujoSigedDAO.find(filtro);
            LOG.info("cuenta listarFlujoSiged: "+retorno.size());
        }catch(Exception ex){
            LOG.error("Error en listarFlujoSiged",ex);
        }
        return retorno;
    }  
}
