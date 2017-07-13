/**
* Resumen
* Objeto		: ExpedienteServiceNeg.java
* Descripción		: Clase Interfaz de la capa de negocio que contiene la declaración de métodos a implementarse en el ExpedienteServiceNegImpl
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     01/06/2016      Luis García Reyna           Implementar la funcionalidad de editar asignaciones de acuerdo a especificaciones
* OSINE791          19/08/2016      Jose Herrera                Agregar Sigla de Division al Numero Orden Servicio
* OSINE_SFS-791     14/10/2016      Mario Dioses Fernandez      Crear la tarea automática que cancele el registro de hidrocarburos
* OSINE791–RSIS41   | 11/10/2016 | Zosimo Chaupis Santur | Crear la funcionalidad para generar los resultados de una supervisión de orden de levantamiento DSR-CRITICIDAD. 

*/

package gob.osinergmin.inpsweb.service.business;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.PersonalDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.ExpedienteFilter;
import gob.osinergmin.siged.remote.rest.ro.out.ConsultarMensajeriaDatosCargoOut;
import gob.osinergmin.siged.remote.rest.ro.out.ListaMensajeriaItemOut;
import gob.osinergmin.siged.remote.rest.ro.out.ExpedienteActualizarTipoProcesoOut;
import gob.osinergmin.inpsweb.service.exception.ExpedienteException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author jpiro
 */
public interface ExpedienteServiceNeg {
    public List<ExpedienteDTO> listarExpediente(ExpedienteFilter filtro);
    public PersonalDTO derivar(List<ExpedienteDTO> expedientes,Long idPersonalOri,Long idPersonalDest,String motivoReasignacion,UsuarioDTO usuarioDTO) throws ExpedienteException;
    public List<ExpedienteDTO> listarDerivadosByIdPersonal(ExpedienteFilter filtro) throws ExpedienteException;
    public ExpedienteDTO asignarUnidadSupervisada(ExpedienteDTO expedienteDto,UsuarioDTO usuarioDTO) throws ExpedienteException;
    /* OSINE791 - RSIS1 - Inicio */
    public ExpedienteDTO asignarOrdenServicio(ExpedienteDTO expedienteDto,String codigoTipoSupervisor,UsuarioDTO usuarioDTO,String sigla) throws ExpedienteException;
    public ExpedienteDTO generarExpedienteOrdenServicio(ExpedienteDTO expedienteDto,String codigoTipoSupervisor,UsuarioDTO usuarioDTO,String sigla) throws ExpedienteException;
    /* OSINE791 - RSIS1 - Fin */
//    public ExpedienteDTO generarExpedienteSiged(ExpedienteDTO expedienteDTO,Long idPersonalSiged) throws ExpedienteException;
    public ExpedienteDTO generarExpedienteSiged(ExpedienteDTO expedienteDTO, Long idDirccionUnidadSuprvisada, Long idPersonalSiged) throws ExpedienteException;
 // htorress - RSIS 18 - Inicio
    public ExpedienteDTO reenviarExpedienteSIGED(ExpedienteDTO expedienteDTO,Long destinatario, String contenido, boolean aprobacion) throws ExpedienteException;
    public ExpedienteDTO aprobarExpedienteSIGED(ExpedienteDTO expedienteDTO,Long destinatario, String contenido, boolean aprobacion) throws ExpedienteException;
    public ExpedienteDTO archivarExpedienteSIGED(String nroExpediente, String contenido) throws ExpedienteException;
    public ExpedienteDTO rechazarExpedienteSIGED(ExpedienteDTO expedienteDTO, String motivo) throws ExpedienteException;
 // htorress - RSIS 18 - Fin
	public ExpedienteDTO reabrirExpedienteSIGED(String nroExpediente) throws ExpedienteException;
    /* OSINE_SFS-480 - RSIS 47 - Inicio */
    public ExpedienteDTO editarExpedienteOrdenServicio(ExpedienteDTO expedienteDTO, String codigoTipoSupervisor,PersonalDTO personalDest,UsuarioDTO usuarioDTO) throws ExpedienteException;
    public ExpedienteActualizarTipoProcesoOut editarExpedienteSIGED(ExpedienteDTO expedienteDTO) throws ExpedienteException;
    /* OSINE_SFS-480 - RSIS 47 - Fin */
    /* OSINE_SFS-480 - RSIS 06 - Inicio */
    public List<ListaMensajeriaItemOut> listarMensajeria(String idUsuarioSiged);
    public ConsultarMensajeriaDatosCargoOut cargaDatosCargo(String idMensajeria);
    /* OSINE_SFS-480 - RSIS 06 - Fin */
    public Map<String,Object> aprobarOrdenServicioDsrCri(Long idExpediente,Long idOrdenServicio,Long idPersonalOri, String numeroExpediente, String asuntoSiged,HttpServletRequest request) throws ExpedienteException;
    /* OSINE_SFS-791 - RSIS 47 - Inicio */ 
    public List<ExpedienteDTO> findByFilter(ExpedienteFilter filtro) throws ExpedienteException;
    public ExpedienteDTO veriActuFlgTramFinalizadoDsr(Long idExpediente,Long idOrdenServicio,Long idResultadoSupervision,UsuarioDTO usuarioDTO) throws ExpedienteException;
    public ExpedienteDTO actualizar(ExpedienteDTO expediente, UsuarioDTO usuario) throws ExpedienteException;
    /* OSINE_SFS-791 - RSIS 47 - Fin */ 
    /* OSINE_791 - RSIS 41 - Inicio */
    public ExpedienteDTO actualizarExpedienteHabModuloLevantamiento(ExpedienteDTO expedienteDTO,UsuarioDTO usuarioDTO) throws ExpedienteException;
    /* OSINE_791 - RSIS 41 - Fin */

    /* OSINE_SFS-Ajustes - mdiosesf - Incio */
    public Map<String, Object> generarExpedienteOrdenServicioMasivoPrincipal(List<UnidadSupervisadaDTO> unidades, ExpedienteDTO expedienteDto, String codigoTipoSupervisor, String sigla, HttpServletRequest request) throws ExpedienteException;
//    public Map<String, Object> generarExpedienteOrdenServicioPrincipal(ExpedienteDTO expedienteDto, String codigoTipoSupervisor, HttpServletRequest request)  throws ExpedienteException;
    public Map<String, Object> asignarOrdenServicioPrincipal(ExpedienteDTO expedienteDto, String codigoTipoSupervisor, UsuarioDTO usuarioDTO, String sigla, HttpServletRequest request) throws ExpedienteException;
    /* OSINE_SFS-Ajustes - mdiosesf - Fin */
    public String generarNumeroOrdenServicio(String sigla);
}
