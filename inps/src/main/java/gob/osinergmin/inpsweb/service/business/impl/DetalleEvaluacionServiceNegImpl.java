/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.business.impl;
import gob.osinergmin.inpsweb.service.business.DetalleEvaluacionServiceNeg;
import gob.osinergmin.inpsweb.service.business.EstadoProcesoServiceNeg;
import gob.osinergmin.inpsweb.service.dao.DetalleEvaluacionDAO;
import gob.osinergmin.inpsweb.service.exception.DetalleEvaluacionException;
import gob.osinergmin.inpsweb.service.exception.SupervisionException;
import gob.osinergmin.mdicommon.domain.dto.DetalleEvaluacionDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.DetalleEvaluacionFilter;

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

@Service("DetalleEvaluacionServiceNeg")
public class DetalleEvaluacionServiceNegImpl implements DetalleEvaluacionServiceNeg {
    private static final Logger LOG = LoggerFactory.getLogger(EstadoProcesoServiceNeg.class);    
    @Inject
    DetalleEvaluacionDAO detalleEvaluacionDAO;
    
    @Override
    public List<DetalleEvaluacionDTO> listarDetalleEvaluacion(DetalleEvaluacionFilter detalleFilter){
    	LOG.info("DetalleEvaluacionServiceNegImpl--listarDetalleEvaluacion - inicio");
        List<DetalleEvaluacionDTO> retorno = new ArrayList<DetalleEvaluacionDTO>();
        try{
            retorno=detalleEvaluacionDAO.listarDetalleEvaluacion(detalleFilter);
        }catch(Exception ex){
            LOG.error("Error en find",ex);
        }
        return retorno;
    }
    
    @Override
    public List<DetalleEvaluacionDTO> findDetalleEvaluacion(DetalleEvaluacionFilter detalleFilter){
    	LOG.info("DetalleEvaluacionServiceNegImpl--findDetalleEvaluacion - inicio");
        List<DetalleEvaluacionDTO> retorno = new ArrayList<DetalleEvaluacionDTO>();
        try{
            retorno=detalleEvaluacionDAO.findDetalleEvaluacion(detalleFilter);
        }catch(Exception ex){
            LOG.error("Error en find",ex);
        }
        return retorno;
    }
    
    @Override
	public DetalleEvaluacionDTO actualizarDetalleEvaluacion(DetalleEvaluacionDTO detalleEvaluacionDTO, UsuarioDTO usuarioDTO)
			throws DetalleEvaluacionException {
		LOG.info("DetalleEvaluacionServiceNegImpl--actualizarDetalleEvaluacion - inicio");
		DetalleEvaluacionDTO retorno=null;
        try{
            retorno = detalleEvaluacionDAO.actualizar(detalleEvaluacionDTO, usuarioDTO);
        }catch(Exception ex){
            LOG.error("Error en actualizarDetalleSupervision",ex);
            throw new DetalleEvaluacionException(ex.getMessage(),ex);
        }
        LOG.info("DetalleEvaluacionServiceNegImpl--actualizarDetalleEvaluacion - fin");
        return retorno;
	}
    
    @Override
    @Transactional(rollbackFor=DetalleEvaluacionException.class)
	public DetalleEvaluacionDTO registrarDetalleEvaluacion(DetalleEvaluacionDTO detalleEvaluacionDTO, UsuarioDTO usuarioDTO) throws DetalleEvaluacionException {
		LOG.info("DetalleEvaluacionServiceNegImpl--registrarDetalleEvaluacion - inicio");
		DetalleEvaluacionDTO retorno=null;
        try{
            retorno = detalleEvaluacionDAO.registrar(detalleEvaluacionDTO, usuarioDTO);
        }catch(Exception ex){
            LOG.error("Error en registrarDetalleSupervision",ex);
            throw new DetalleEvaluacionException(ex.getMessage(),ex);
        }
        LOG.info("DetalleEvaluacionServiceNegImpl--registrarDetalleEvaluacion - fin");
        return retorno;
	}
}
