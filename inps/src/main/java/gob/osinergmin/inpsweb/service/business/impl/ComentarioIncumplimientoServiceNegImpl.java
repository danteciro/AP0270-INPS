/**
 * Resumen 
 * Objeto				: ComentarioIncumplimientoServiceNegImpl.java 
 * Descripción	        : 
 * Fecha de Creación	: 
 * PR de Creación		: 
 * Autor				: 
 * ---------------------------------------------------------------------------------------------------
 * Modificaciones   Fecha             Nombre               Descripción
 * ---------------------------------------------------------------------------------------------------
 * OSINE_MAN_DSR_0037  | 18/06/2017     | Carlos Quijano Chavez::ADAPTER  | Agrega comentarios adicionales de incumplimiento
 */
package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.service.business.ComentarioIncumplimientoServiceNeg;
import gob.osinergmin.inpsweb.service.dao.ComentarioIncumplimientoDAO;
import gob.osinergmin.mdicommon.domain.dto.ComentarioIncumplimientoDTO;
import gob.osinergmin.mdicommon.domain.ui.ComentarioIncumplimientoFilter;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
/* OSINE_MAN_DSR_0037 - Inicio */
import gob.osinergmin.inpsweb.dto.ComentarioDetalleSupervisionOpcionalDTO;
import javax.servlet.http.HttpServletRequest;
import gob.osinergmin.inpsweb.util.Constantes;
/* OSINE_MAN_DSR_0037 - Fin */

/**
 *
 * @author jpiro
 */
@Service("comentarioIncumplimientoServiceNeg")
public class ComentarioIncumplimientoServiceNegImpl implements ComentarioIncumplimientoServiceNeg{
    private static final Logger LOG = LoggerFactory.getLogger(ComentarioIncumplimientoServiceNegImpl.class);
    @Inject
    ComentarioIncumplimientoDAO comentarioIncumplimientoDAO;
    
    @Override
    public List<ComentarioIncumplimientoDTO> listarComentarioIncumplimiento(ComentarioIncumplimientoFilter filtro){
        LOG.info("listarComentarioIncumplimiento");
        List<ComentarioIncumplimientoDTO> retorno=null;
        try{
            retorno=comentarioIncumplimientoDAO.findByFilter(filtro);
        }catch(Exception e){
            LOG.error("Error en listarComentarioIncumplimiento",e);
        }
        return retorno;
    }
	
	/* OSINE_MAN_DSR_0037 - Inicio */
	@Override
	public Long agregarComentarioDetalleSupervisionOpcional(ComentarioDetalleSupervisionOpcionalDTO comentario,HttpServletRequest request) {
		LOG.info("agregarComentarioDetalleSupervisionOpcional");
		Long idComentarioDetalleSupervisionOpcional=0L;
		try{
			idComentarioDetalleSupervisionOpcional=comentarioIncumplimientoDAO.agregarComentarioDetalleSupervisionOpcional(comentario,Constantes.getUsuarioDTO(request));
        }catch(Exception e){
            LOG.error("Error en agregarComentarioDetalleSupervisionOpcional",e);
        }
		return idComentarioDetalleSupervisionOpcional;
	}

	@Override
	public Long editarComentarioDetalleSupervisionOpcional(ComentarioDetalleSupervisionOpcionalDTO comentario,HttpServletRequest request) {
		LOG.info("editarComentarioDetalleSupervisionOpcional");
		Long idComentarioDetalleSupervisionOpcional=comentario.getIdComentarioDetalleSupervisionOpcional();
		try{
			idComentarioDetalleSupervisionOpcional=comentarioIncumplimientoDAO.actualizarComentarioDetalleSupervisionOpcional(comentario,Constantes.getUsuarioDTO(request));
        }catch(Exception e){
            LOG.error("Error en editarComentarioDetalleSupervisionOpcional",e);
        }
		return idComentarioDetalleSupervisionOpcional;
	}
	/* OSINE_MAN_DSR_0037 - Fin */
}
