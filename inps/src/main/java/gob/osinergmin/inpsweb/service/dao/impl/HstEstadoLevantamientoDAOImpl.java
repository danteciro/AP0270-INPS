/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.PghHstEstadoLevantamiento;
import gob.osinergmin.inpsweb.domain.builder.HstEstadoLevantamientoBuilder;
import gob.osinergmin.inpsweb.domain.builder.SupervisionBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.HstEstadoLevantamientoDAO;
import gob.osinergmin.inpsweb.service.exception.HstEstadoLevantamientoException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.HstEstadoLevantamientoDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author zchaupis
 */
@Repository("hstEstadoLevantamientoDAO")
public class HstEstadoLevantamientoDAOImpl implements HstEstadoLevantamientoDAO {

    private static final Logger LOG = LoggerFactory.getLogger(HstEstadoLevantamientoDAOImpl.class);
    @Inject
    private CrudDAO crud;

    @Override
    @Transactional(rollbackFor = HstEstadoLevantamientoException.class)
    public HstEstadoLevantamientoDTO registrarHstEstadoLevantamiento(HstEstadoLevantamientoDTO hstEstadoLevantamientoDTO, UsuarioDTO usuarioDTO) throws HstEstadoLevantamientoException {
      LOG.info("HstEstadoLevantamientoDAOImpl:registrarHstEstadoLevantamiento-inicio");
	    HstEstadoLevantamientoDTO retorno=null;
        try{            
            PghHstEstadoLevantamiento hstEstadoLevantamiento = HstEstadoLevantamientoBuilder.toHstEstadoLevantamientoDomain(hstEstadoLevantamientoDTO);
            hstEstadoLevantamiento.setDatosAuditoria(usuarioDTO);
            hstEstadoLevantamiento.setEstado(Constantes.ESTADO_ACTIVO);
            crud.create(hstEstadoLevantamiento);
            retorno=HstEstadoLevantamientoBuilder.toHstEstadoLevantamientoDTO(hstEstadoLevantamiento);
        }catch(Exception e){
            LOG.error("Error en registrarHstEstadoLevantamiento",e);
            HstEstadoLevantamientoException ex = new HstEstadoLevantamientoException(e.getMessage(), e);
            throw ex;
        }
        LOG.info("HstEstadoLevantamientoDAOImpl:registrarHstEstadoLevantamiento-fin");
        return retorno;  
    }
}
