/**
 * Resumen 
 * Objeto				: EscenarioIncumplimientoServiceNeg.java 
 * Descripción	        :  
 * Fecha de Creación	: 
 * PR de Creación		: 
 * Autor				: 
 * ---------------------------------------------------------------------------------------------------
 * Modificaciones   Fecha             Nombre               Descripción
 * ---------------------------------------------------------------------------------------------------
 * OSINE_MAN_DSR_0037  | 18/06/2017     | Carlos Quijano Chavez::ADAPTER  | Actualiza comentarios adicionales
 */
package gob.osinergmin.inpsweb.service.business.impl;
/* OSINE_MAN_DSR_0037 - Inicio */
import gob.osinergmin.inpsweb.dto.ComentarioDetalleSupervisionOpcionalDTO;
/* OSINE_MAN_DSR_0037 - Fin */
import gob.osinergmin.inpsweb.service.business.EscenarioIncumplimientoServiceNeg;
import gob.osinergmin.inpsweb.service.dao.EscenarioIncumplimientoDAO;
/*OSINE_SFS-791 - RSIS 16 - Inicio*/
import gob.osinergmin.inpsweb.service.dao.InfraccionDAO;
/*OSINE_SFS-791 - RSIS 16 - Fin*/
import gob.osinergmin.mdicommon.domain.dto.EscenarioIncumplimientoDTO;
/*OSINE_SFS-791 - RSIS 16 - Inicio*/
import gob.osinergmin.mdicommon.domain.dto.InfraccionDTO;

/*OSINE_SFS-791 - RSIS 16 - Fin*/
/* OSINE_MAN_DSR_0037 - Inicio */
import gob.osinergmin.inpsweb.service.dao.ComentarioIncumplimientoDAO;
/* OSINE_MAN_DSR_0037 - Fin */
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gob.osinergmin.mdicommon.domain.ui.EscenarioIncumplimientoFilter;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author jpiro
 */

@Service("escenarioIncumplimientoServiceNeg")
public class EscenarioIncumplimientoServiceNegImpl implements EscenarioIncumplimientoServiceNeg{
    private static final Logger LOG = LoggerFactory.getLogger(EscenarioIncumplimientoServiceNegImpl.class);
    
    @Inject
    EscenarioIncumplimientoDAO escenarioIncumplimientoDAO;
    /*OSINE_SFS-791 - RSIS 16 - Inicio*/
    @Inject
    InfraccionDAO infraccionDAO;
    /*OSINE_SFS-791 - RSIS 16 - Fin*/
    /* OSINE_MAN_DSR_0037 - Inicio */
    @Inject
    ComentarioIncumplimientoDAO comentarioIncumplimientoDAO;
    /* OSINE_MAN_DSR_0037 - Fin */
    @Override
    @Transactional(readOnly = true)
    public List<EscenarioIncumplimientoDTO> listarEscenarioIncumplimiento(EscenarioIncumplimientoFilter filtro){
        LOG.info("listarEscenarioIncumplimiento");
        
        List<EscenarioIncumplimientoDTO> retorno=null;
        try{
            retorno=escenarioIncumplimientoDAO.findByFilter(filtro);
        }catch(Exception e){
            LOG.error("Error en listarEscenarioIncumplimiento",e);
        }
        return retorno;
    }
    
    @Override
    @Transactional(readOnly = true)
    public EscenarioIncumplimientoDTO getEscenarioIncumplimiento(EscenarioIncumplimientoFilter filtro){
        LOG.info("getEscenarioIncumplimiento");
        EscenarioIncumplimientoDTO retorno=null;        
        try{
            List<EscenarioIncumplimientoDTO> lst=escenarioIncumplimientoDAO.findByFilter(filtro);
            if(!CollectionUtils.isEmpty(lst)){
                retorno=lst.get(0);
            }
        }catch(Exception e){
            LOG.error("Error en getEscenarioIncumplimiento",e);
        }
        return retorno;
    }
    /*OSINE_SFS-791 - RSIS 16 - Inicio*/
    @Override
    public InfraccionDTO getInfraccion(EscenarioIncumplimientoFilter filtro) {
        LOG.info("getInfraccion");
        InfraccionDTO retorno=null;        
        try{
            List<InfraccionDTO> lstInfracciones=infraccionDAO.findByFilter(filtro);
            if(!CollectionUtils.isEmpty(lstInfracciones)){
                retorno=lstInfracciones.get(0);
            }
        }catch(Exception e){
            LOG.error("Error en getEscenarioIncumplimiento",e);
        }
        return retorno;
    }
    /*OSINE_SFS-791 - RSIS 16 - Fin*/
    /* OSINE_MAN_DSR_0037 - Inicio */
    @Override
	public ComentarioDetalleSupervisionOpcionalDTO getComentarioOpciona(Long idDetSupervision, Long idEscenario) {
		LOG.info("getComentarioOpciona");
		ComentarioDetalleSupervisionOpcionalDTO retorno=null;
		try{
			List<ComentarioDetalleSupervisionOpcionalDTO> lstComentarioOpc=comentarioIncumplimientoDAO.listarComentarioOpcionales(idDetSupervision, idEscenario);
			if(!CollectionUtils.isEmpty(lstComentarioOpc))
    		{
				retorno=lstComentarioOpc.get(0);
    		}
		}
		catch(Exception e){
			LOG.error("Error en getComentarioOpciona",e);
		}
		return retorno;
	}
    /* OSINE_MAN_DSR_0037 - Fin */
}
