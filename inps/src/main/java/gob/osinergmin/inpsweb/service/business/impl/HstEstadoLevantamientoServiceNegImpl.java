/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.service.business.HstEstadoLevantamientoServiceNeg;
import gob.osinergmin.inpsweb.service.dao.HstEstadoLevantamientoDAO;
import gob.osinergmin.inpsweb.service.exception.HstEstadoLevantamientoException;
import gob.osinergmin.mdicommon.domain.dto.HstEstadoLevantamientoDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author zchaupis
 */
@Service("hstEstadoLevantamientoServiceNeg")
public class HstEstadoLevantamientoServiceNegImpl implements HstEstadoLevantamientoServiceNeg {
    private static Logger LOG = LoggerFactory.getLogger(HstEstadoLevantamientoServiceNegImpl.class);
    
    @Inject
    private HstEstadoLevantamientoDAO hstEstadoLevantamientoDAO;

    @Override
    public HstEstadoLevantamientoDTO registrarHstEstadoLevantamiento(HstEstadoLevantamientoDTO hstEstadoLevantamientoDTO, UsuarioDTO usuarioDTO) throws HstEstadoLevantamientoException {
      LOG.info("registrarHstEstadoLevantamiento");
        HstEstadoLevantamientoDTO retorno=new HstEstadoLevantamientoDTO();
        try {
            retorno=hstEstadoLevantamientoDAO.registrarHstEstadoLevantamiento(hstEstadoLevantamientoDTO,usuarioDTO);  
        } catch (Exception ex) {
            LOG.error("Error en registrarHstEstadoLevantamiento", ex);
            throw new HstEstadoLevantamientoException(ex.getMessage(), ex);
        }
        return retorno; 
    }
}
