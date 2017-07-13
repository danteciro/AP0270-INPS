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
package gob.osinergmin.inpsweb.service.business;

import gob.osinergmin.mdicommon.domain.dto.EscenarioIncumplimientoDTO;
/*OSINE_SFS-791 - RSIS 16 - Inicio*/
import gob.osinergmin.mdicommon.domain.dto.InfraccionDTO;
/*OSINE_SFS-791 - RSIS 16 - Fin*/
import gob.osinergmin.mdicommon.domain.ui.EscenarioIncumplimientoFilter;

import java.util.List;

/* OSINE_MAN_DSR_0037 - Inicio */
import gob.osinergmin.inpsweb.dto.ComentarioDetalleSupervisionOpcionalDTO;
/* OSINE_MAN_DSR_0037 - Fin */

/**
 *
 * @author jpiro
 */
public interface EscenarioIncumplimientoServiceNeg {
    public List<EscenarioIncumplimientoDTO> listarEscenarioIncumplimiento(EscenarioIncumplimientoFilter filtro);
    public EscenarioIncumplimientoDTO getEscenarioIncumplimiento(EscenarioIncumplimientoFilter filtro);
    /*OSINE_SFS-791 - RSIS 16 - Inicio*/
    public InfraccionDTO getInfraccion(EscenarioIncumplimientoFilter filtro);
    /*OSINE_SFS-791 - RSIS 16 - Fin*/
    /* OSINE_MAN_DSR_0037 - Inicio */
    public ComentarioDetalleSupervisionOpcionalDTO getComentarioOpciona(Long idDetSupervision,Long idEscenarioIncum);
    /* OSINE_MAN_DSR_0037 - Fin */
}
