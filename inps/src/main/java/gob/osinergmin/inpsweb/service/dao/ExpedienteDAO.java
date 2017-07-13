/**
* Resumen		
* Objeto		: ExpedienteDAO.java
* Descripción		: Clase que proporciona una interfaz para la implementación de métodos en ExpedienteDAOImpl
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     01/06/2016      Luis García Reyna               Implementar la funcionalidad de editar asignaciones de acuerdo a especificaciones.
* OSINE791          19/08/2016      Jose Herrera                    Agregar Sigla de Division al Numero Orden Servicio
* OSINE_SFS-791     14/10/2016      Mario Dioses Fernandez          Crear la tarea automática que cancele el registro de hidrocarburos
* OSINE791–RSIS41   | 11/10/2016 | Zosimo Chaupis Santur | Crear la funcionalidad para generar los resultados de una supervisión de orden de levantamiento DSR-CRITICIDAD. 

*/

package gob.osinergmin.inpsweb.service.dao;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.PersonalDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.ExpedienteFilter;
import gob.osinergmin.inpsweb.service.exception.ExpedienteException;
import java.util.List;

/**
 *
 * @author jpiro
 */
public interface ExpedienteDAO {
    public ExpedienteDTO veriActuFlgTramFinalizado(Long idExpediente,Long idOrdenServicio,UsuarioDTO usuarioDTO) throws ExpedienteException;
    public List<ExpedienteDTO> findForGrid(ExpedienteFilter filtro) throws ExpedienteException;
    public List<ExpedienteDTO> findByFilter(ExpedienteFilter filtro) throws ExpedienteException;
    public ExpedienteDTO cambiarEstadoProceso(Long idExpediente, Long idPersonal,Long idPersonalOri,Long idPersonalDest,Long idEstadoProceso,String motivoReasignacion,UsuarioDTO usuarioDTO) throws ExpedienteException;
    public List<ExpedienteDTO> findDerivadosByIdPersonal(ExpedienteFilter filtro) throws ExpedienteException;
    public ExpedienteDTO asignarUnidadSupervisada(ExpedienteDTO expedienteDTO, UsuarioDTO usuarioDTO) throws ExpedienteException;
    /* OSINE791 - RSIS1 - Inicio */
    public ExpedienteDTO asignarOrdenServicio(ExpedienteDTO expedienteDTO,String codigoTipoSupervisor,UsuarioDTO usuarioDTO,String sigla) throws ExpedienteException;
    public ExpedienteDTO generarExpedienteOrdenServicio(ExpedienteDTO expedienteDTO,String codigoTipoSupervisor,UsuarioDTO usuarioDTO,String sigla) throws ExpedienteException;
    /* OSINE791 - RSIS1 - Fin */
    public Long obtenerIteracionOrdenServicio(Long idExpediente) throws ExpedienteException;
    public List<ExpedienteDTO> findExpedienteOperativo(Long idExpediente) throws ExpedienteException;
    /* OSINE_SFS-480 - RSIS 47 - Inicio */
    public ExpedienteDTO editarExpedienteOrdenServicio(ExpedienteDTO expedienteDTO, String codigoTipoSupervisor, PersonalDTO personalDest, UsuarioDTO usuarioDTO) throws ExpedienteException;
    /* OSINE_SFS-480 - RSIS 47 - Fin */
    /* OSINE791 - RSIS 20 - Inicio */
    public ExpedienteDTO veriActuFlgTramFinalizadoDsr(Long idExpediente,Long idOrdenServicio,Long idResultadoSupervision,UsuarioDTO usuarioDTO) throws ExpedienteException;
    /* OSINE791 - RSIS 20 - Fin */
    /* OSINE791 - RSIS 47 - Inicio */
    public ExpedienteDTO actualizar(ExpedienteDTO expediente, UsuarioDTO usuario) throws ExpedienteException;
    /* OSINE791 - RSIS 47 - Fin */;
    /* OSINE_791 - RSIS 41 - Inicio */
    public ExpedienteDTO actualizarExpediente(ExpedienteDTO expedienteDTO,UsuarioDTO usuarioDTO) throws ExpedienteException;
    public String generarNumeroOrdenServicio(String sigla);
    /* OSINE_791 - RSIS 41 - Fin */
    
}
