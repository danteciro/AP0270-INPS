/**
 * Resumen 
 * Objeto				: ComentarioIncumplimientoDAO.java 
 * Descripción	        : DAO de los comentarios de incumplimiento. 
 * Fecha de Creación	: 
 * PR de Creación		: 
 * Autor				: 
 * ---------------------------------------------------------------------------------------------------
 * Modificaciones   Fecha             Nombre               Descripción
 * ---------------------------------------------------------------------------------------------------
 * OSINE_MAN_DSR_0037  | 18/06/2017     | Carlos Quijano Chavez::ADAPTER  | Agrega comentarios adicionales de incumplimiento
 */
package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.inpsweb.service.exception.ComentarioIncumplimientoException;
import gob.osinergmin.mdicommon.domain.dto.ComentarioIncumplimientoDTO;
import gob.osinergmin.mdicommon.domain.ui.ComentarioIncumplimientoFilter;
/* OSINE_MAN_DSR_0037 - Inicio */
import gob.osinergmin.inpsweb.dto.ComentarioDetalleSupervisionOpcionalDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
/* OSINE_MAN_DSR_0037 - Fin */

import java.util.List;

/**
 *
 * @author jpiro
 */
public interface ComentarioIncumplimientoDAO {
    public List<ComentarioIncumplimientoDTO> findByFilter(ComentarioIncumplimientoFilter filtro) throws ComentarioIncumplimientoException;
    /* OSINE_MAN_DSR_0037 - Inicio */
    public Long agregarComentarioDetalleSupervisionOpcional(ComentarioDetalleSupervisionOpcionalDTO comentario,UsuarioDTO usuarioDTO) throws ComentarioIncumplimientoException;
    public Long actualizarComentarioDetalleSupervisionOpcional(ComentarioDetalleSupervisionOpcionalDTO comentario,UsuarioDTO usuarioDTO) throws ComentarioIncumplimientoException;
    public List<ComentarioDetalleSupervisionOpcionalDTO> listarComentarioOpcionales(Long idInfraccion,Long idEscenarioInc)throws ComentarioIncumplimientoException;
    /* OSINE_MAN_DSR_0037 - Fin */
}
