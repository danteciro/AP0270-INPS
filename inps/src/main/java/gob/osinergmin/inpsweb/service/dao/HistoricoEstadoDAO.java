/**
* Resumen		
* Objeto		: HistoricoEstadoDAO.java
* Descripción		: Clase que proporciona una interfaz para la implementación de métodos en HistoricoEstadoDAOImpl
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     23/05/2016      Luis García Reyna               Implementar la funcionalidad de devolución de asignaciones de acuerdo a especificaciones.
*
*/

package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.inpsweb.service.exception.HistoricoEstadoException;
import gob.osinergmin.mdicommon.domain.dto.HistoricoEstadoDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.HistoricoEstadoFilter;
import java.util.List;

/**
 *
 * @author jpiro
 */
public interface HistoricoEstadoDAO {
    public List<HistoricoEstadoDTO> find(HistoricoEstadoFilter filtro) throws HistoricoEstadoException;
    /* OSINE_SFS-480 - RSIS 40 - Inicio */
    public HistoricoEstadoDTO registrar(Long idExpediente,Long idOrdenServicio,Long idEstadoProceso,Long idPersonalOri,Long idPersonalDest,Long idTipoHistorico,Long idPeticion,Long idMotivo,String motivoReasignacion,UsuarioDTO usuarioDTO) throws HistoricoEstadoException; 
    /* OSINE_SFS-480 - RSIS 40 - Fin */
}
