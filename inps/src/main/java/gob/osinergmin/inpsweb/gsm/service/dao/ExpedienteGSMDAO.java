/**
* Resumen		
* Objeto			: ExpedienteGSMDAO.java
* Descripción		: Clase que proporciona una interfaz para la implementación de métodos en ExpedienteGSMDAOImpl
* Fecha de Creación	: 25/10/2016.
* PR de Creación	: OSINE_SFS-1344.
* Autor				: GMD.
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
* 
*/

package gob.osinergmin.inpsweb.gsm.service.dao;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteGSMDTO;
import gob.osinergmin.mdicommon.domain.dto.PersonalDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.ExpedienteFilter;
import gob.osinergmin.inpsweb.service.exception.ExpedienteException;
import java.util.List;

public interface ExpedienteGSMDAO {
    public ExpedienteGSMDTO veriActuFlgTramFinalizado(Long idExpediente,Long idOrdenServicio,UsuarioDTO usuarioDTO) throws ExpedienteException;
    public List<ExpedienteGSMDTO> findForGrid(ExpedienteFilter filtro) throws ExpedienteException;
    public List<ExpedienteGSMDTO> findByFilter(ExpedienteFilter filtro) throws ExpedienteException;
    public ExpedienteGSMDTO cambiarEstadoProceso(Long idExpediente, Long idPersonal,Long idPersonalOri,Long idPersonalDest,Long idEstadoProceso,String motivoReasignacion,UsuarioDTO usuarioDTO) throws ExpedienteException;
    public List<ExpedienteGSMDTO> findDerivadosByIdPersonal(ExpedienteFilter filtro) throws ExpedienteException;
    public ExpedienteGSMDTO asignarUnidadSupervisada(ExpedienteGSMDTO expedienteDTO, UsuarioDTO usuarioDTO) throws ExpedienteException;
    /* OSINE791 - RSIS1 - Inicio */
    public ExpedienteGSMDTO asignarOrdenServicio(ExpedienteGSMDTO expedienteDTO,String codigoTipoSupervisor,UsuarioDTO usuarioDTO,String sigla) throws ExpedienteException;
    public ExpedienteGSMDTO generarExpedienteOrdenServicio(ExpedienteGSMDTO expedienteDTO,String codigoTipoSupervisor,UsuarioDTO usuarioDTO,String sigla) throws ExpedienteException;
    /* OSINE791 - RSIS1 - Fin */
    public Long obtenerIteracionOrdenServicio(Long idExpediente) throws ExpedienteException;
    public List<ExpedienteGSMDTO> findExpedienteOperativo(Long idExpediente) throws ExpedienteException;
    /* OSINE_SFS-480 - RSIS 47 - Inicio */
    public ExpedienteGSMDTO editarExpedienteOrdenServicio(ExpedienteGSMDTO expedienteDTO, String codigoTipoSupervisor, PersonalDTO personalDest, UsuarioDTO usuarioDTO) throws ExpedienteException;
    /* OSINE_SFS-480 - RSIS 47 - Fin */
    /* OSINE791 - RSIS 20 - Inicio */
    public ExpedienteGSMDTO veriActuFlgTramFinalizadoDsr(Long idExpediente,Long idOrdenServicio,Long idResultadoSupervision,UsuarioDTO usuarioDTO) throws ExpedienteException;
    /* OSINE791 - RSIS 20 - Fin */
    /* OSINE791 - RSIS 47 - Inicio */
    public ExpedienteGSMDTO actualizar(ExpedienteGSMDTO expediente, UsuarioDTO usuario) throws ExpedienteException;
    /* OSINE791 - RSIS 47 - Fin */;
    /* OSINE_791 - RSIS 41 - Inicio */
    public ExpedienteGSMDTO actualizarExpediente(ExpedienteGSMDTO expedienteDTO,UsuarioDTO usuarioDTO) throws ExpedienteException;
    /* OSINE_791 - RSIS 41 - Fin */
    
}
