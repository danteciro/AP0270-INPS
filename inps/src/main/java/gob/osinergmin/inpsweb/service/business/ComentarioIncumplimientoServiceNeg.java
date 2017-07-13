/**
 * Resumen 
 * Objeto				: ComentarioIncumplimientoServiceNeg.java 
 * Descripción	        : 
 * Fecha de Creación	: 
 * PR de Creación		: 
 * Autor				: 
 * ---------------------------------------------------------------------------------------------------
 * Modificaciones   Fecha             Nombre               Descripción
 * ---------------------------------------------------------------------------------------------------
 * OSINE_MAN_DSR_0022  | 18/06/2017     | Carlos Quijano Chavez::ADAPTER | Agrega comentario personalizado de incumplimiento
 */
package gob.osinergmin.inpsweb.service.business;

import gob.osinergmin.mdicommon.domain.dto.ComentarioIncumplimientoDTO;
import gob.osinergmin.mdicommon.domain.ui.ComentarioIncumplimientoFilter;
import java.util.List;
/* OSINE_MAN_DSR_0022 - Inicio */
import gob.osinergmin.inpsweb.dto.ComentarioDetalleSupervisionOpcionalDTO;
import javax.servlet.http.HttpServletRequest;
/* OSINE_MAN_DSR_0022 - Fin */

/**
 *
 * @author jpiro
 */
public interface ComentarioIncumplimientoServiceNeg {
    public List<ComentarioIncumplimientoDTO> listarComentarioIncumplimiento(ComentarioIncumplimientoFilter filtro);
	/* OSINE_MAN_DSR_0022 - Inicio */
    public Long agregarComentarioDetalleSupervisionOpcional(ComentarioDetalleSupervisionOpcionalDTO comentario,HttpServletRequest request);
    public Long editarComentarioDetalleSupervisionOpcional(ComentarioDetalleSupervisionOpcionalDTO comentario,HttpServletRequest request);
    /* OSINE_MAN_DSR_0022 - Fin */
}
