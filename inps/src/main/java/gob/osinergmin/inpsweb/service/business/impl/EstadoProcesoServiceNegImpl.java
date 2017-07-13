/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.service.business.EstadoProcesoServiceNeg;
import gob.osinergmin.inpsweb.service.dao.EstadoProcesoDAO;
import gob.osinergmin.mdicommon.domain.dto.EstadoProcesoDTO;
import gob.osinergmin.mdicommon.domain.ui.EstadoProcesoFilter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author jpiro
 */
@Service("estadoProcesoServiceNeg")
public class EstadoProcesoServiceNegImpl implements EstadoProcesoServiceNeg {
    private static final Logger LOG = LoggerFactory.getLogger(EstadoProcesoServiceNeg.class);    
    @Inject
    EstadoProcesoDAO estadoProcesoDAO;
    
    @Override
    public List<EstadoProcesoDTO> find(EstadoProcesoFilter filtro){
        LOG.info("Neg find");
        List<EstadoProcesoDTO> retorno=new ArrayList<EstadoProcesoDTO>();
        try{
            retorno=estadoProcesoDAO.find(filtro);
        }catch(Exception ex){
            LOG.error("Error en find",ex);
        }
        return retorno;
    }
}
