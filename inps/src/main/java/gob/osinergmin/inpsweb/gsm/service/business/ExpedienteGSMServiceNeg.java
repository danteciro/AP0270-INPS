/**
* Resumen
* Objeto			: ExpedienteGSMServiceNeg.java
* Descripción		: Clase Interfaz de la capa de negocio que contiene la declaración de métodos a implementarse en el ExpedienteServiceNegImpl
* Fecha de Creación	: 25/10/2016.
* PR de Creación	: OSINE_SFS-1344.
* Autor				: GMD.
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* 
*/

package gob.osinergmin.inpsweb.gsm.service.business;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteGSMDTO;
import gob.osinergmin.mdicommon.domain.dto.PersonalDTO;
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
public interface ExpedienteGSMServiceNeg {
    public List<ExpedienteGSMDTO> listarExpediente(ExpedienteFilter filtro);
    public PersonalDTO derivar(List<ExpedienteGSMDTO> expedientes,Long idPersonalOri,Long idPersonalDest,String motivoReasignacion,UsuarioDTO usuarioDTO) throws ExpedienteException;
    public List<ExpedienteGSMDTO> listarDerivadosByIdPersonal(ExpedienteFilter filtro) throws ExpedienteException;
    public ExpedienteGSMDTO asignarUnidadSupervisada(ExpedienteGSMDTO expedienteDto,UsuarioDTO usuarioDTO) throws ExpedienteException;
    /* OSINE791 - RSIS1 - Inicio */
    public ExpedienteGSMDTO asignarOrdenServicio(ExpedienteGSMDTO expedienteDto,String codigoTipoSupervisor,UsuarioDTO usuarioDTO,String sigla) throws ExpedienteException;
    public ExpedienteGSMDTO generarExpedienteOrdenServicio(ExpedienteGSMDTO expedienteDto,String codigoTipoSupervisor,UsuarioDTO usuarioDTO,String sigla) throws ExpedienteException;
    /* OSINE791 - RSIS1 - Fin */
    public ExpedienteGSMDTO generarExpedienteSiged(ExpedienteGSMDTO expedienteDTO,Long idPersonalSiged) throws ExpedienteException;
 // htorress - RSIS 18 - Inicio
    public ExpedienteGSMDTO reenviarExpedienteSIGED(ExpedienteGSMDTO expedienteDTO,Long destinatario, String contenido, boolean aprobacion) throws ExpedienteException;
    public ExpedienteGSMDTO aprobarExpedienteSIGED(ExpedienteGSMDTO expedienteDTO,Long destinatario, String contenido, boolean aprobacion) throws ExpedienteException;
    public ExpedienteGSMDTO archivarExpedienteSIGED(String nroExpediente, String contenido) throws ExpedienteException;
    public ExpedienteGSMDTO rechazarExpedienteSIGED(ExpedienteGSMDTO expedienteDTO, String motivo) throws ExpedienteException;
 // htorress - RSIS 18 - Fin
	public ExpedienteGSMDTO reabrirExpedienteSIGED(String nroExpediente) throws ExpedienteException;
    /* OSINE_SFS-480 - RSIS 47 - Inicio */
    public ExpedienteGSMDTO editarExpedienteOrdenServicio(ExpedienteGSMDTO expedienteDTO, String codigoTipoSupervisor,PersonalDTO personalDest,UsuarioDTO usuarioDTO) throws ExpedienteException;
    public ExpedienteActualizarTipoProcesoOut editarExpedienteSIGED(ExpedienteGSMDTO expedienteDTO) throws ExpedienteException;
    /* OSINE_SFS-480 - RSIS 47 - Fin */
    /* OSINE_SFS-480 - RSIS 06 - Inicio */
    public List<ListaMensajeriaItemOut> listarMensajeria(String idUsuarioSiged);
    public ConsultarMensajeriaDatosCargoOut cargaDatosCargo(String idMensajeria);
    /* OSINE_SFS-480 - RSIS 06 - Fin */
    public Map<String,Object> aprobarOrdenServicioDsrCri(Long idExpediente,Long idOrdenServicio,Long idPersonalOri, String numeroExpediente, String asuntoSiged,HttpServletRequest request) throws ExpedienteException;
    /* OSINE_SFS-791 - RSIS 47 - Inicio */ 
    public List<ExpedienteGSMDTO> findByFilter(ExpedienteFilter filtro) throws ExpedienteException;
    public ExpedienteGSMDTO veriActuFlgTramFinalizadoDsr(Long idExpediente,Long idOrdenServicio,Long idResultadoSupervision,UsuarioDTO usuarioDTO) throws ExpedienteException;
    public ExpedienteGSMDTO actualizar(ExpedienteGSMDTO expediente, UsuarioDTO usuario) throws ExpedienteException;
    /* OSINE_SFS-791 - RSIS 47 - Fin */ 
    /* OSINE_791 - RSIS 41 - Inicio */
    public ExpedienteGSMDTO actualizarExpedienteHabModuloLevantamiento(ExpedienteGSMDTO expedienteDTO,UsuarioDTO usuarioDTO) throws ExpedienteException;
    /* OSINE_791 - RSIS 41 - Fin */

}
