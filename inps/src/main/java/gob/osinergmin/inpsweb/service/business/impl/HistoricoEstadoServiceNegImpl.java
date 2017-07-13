/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.service.business.HistoricoEstadoServiceNeg;
import gob.osinergmin.inpsweb.service.dao.HistoricoEstadoDAO;
import gob.osinergmin.mdicommon.domain.dto.HistoricoEstadoDTO;
import gob.osinergmin.mdicommon.domain.ui.HistoricoEstadoFilter;
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
@Service("historicoEstadoServiceNeg")
public class HistoricoEstadoServiceNegImpl implements HistoricoEstadoServiceNeg{
    private static final Logger LOG = LoggerFactory.getLogger(HistoricoEstadoServiceNegImpl.class);    
    
    @Inject
    private HistoricoEstadoDAO historicoEstadoDAO;
    
    @Override
    @Transactional(readOnly = true)
    public List<HistoricoEstadoDTO> listarHistoricoEstado(HistoricoEstadoFilter filtro){
        LOG.info("listarHistoricoEstado");
        List<HistoricoEstadoDTO> retorno=null;
        try{
            retorno = historicoEstadoDAO.find(filtro);
        }catch(Exception ex){
            LOG.error("Error en listarHistoricoEstado",ex);
        }
        return retorno;
    } 
}
