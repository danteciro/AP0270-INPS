/**
* Resumen
* Objeto		: OrdenServicioServiceNeg.java
* Descripción		: Clase Interfaz de la capa de negocio que contiene la declaración de métodos a implementarse en el OrdenServicioServiceNegImpl
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     05/05/2016      Mario Dioses Fernandez      Registrar en BD campo FECHA_INICIO_PLAZO y FECHA_FIN_PLAZO luego de CONCLUIR_OS, considerando Plazo_Descargo por Rubro (MDI_ACTIVIDAD).
* OSINE_SFS-480     23/05/2016      Luis García Reyna           Implementar la funcionalidad de devolución de asignaciones de acuerdo a especificaciones
* OSINE_SFS-480     23/05/2016      Hernán Torres Saenz         Implementar la funcionalidad de anular orden de servicio de acuerdo a especificaciones.
* OSINE791-RSIS21   29/08/2016      Alexander Vilca Narvaez     Implementar la funcionalidad de archivar para el flujo DSR
* OSINE_SFS-791     06/10/2016      Mario Dioses Fernandez      Crear la funcionalidad "verificar levantamientos" que permita verificar el tipo de asignación para la orden de levantamiento DSR-CRITICIDAD.
* OSINE_SFS-791     13/10/2016      Mario Dioses Fernandez      Crear la tarea automática que cancele el registro de hidrocarburos
* 
*/

package gob.osinergmin.inpsweb.service.business;
import gob.osinergmin.inpsweb.service.exception.OrdenServicioException;
import gob.osinergmin.mdicommon.domain.dto.DireccionUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresaSupDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.OrdenServicioDTO;
import gob.osinergmin.mdicommon.domain.dto.UbigeoDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.dto.busqueda.BusquedaDireccionxEmpSupervisada;
import gob.osinergmin.mdicommon.domain.ui.OrdenServicioFilter;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;

import java.util.Date;
import java.util.List;

/**
 *
 * @author jpiro
 */
public interface OrdenServicioServiceNeg {
    public OrdenServicioDTO atender(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,String flagSupervision,UsuarioDTO usuarioDTO) throws OrdenServicioException;
    // htorress - RSIS 18 - Inicio
    //public OrdenServicioDTO revisar(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,UsuarioDTO usuarioDTO) throws OrdenServicioException;
    public OrdenServicioDTO revisar(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,Long idPersonalDest,UsuarioDTO usuarioDTO) throws OrdenServicioException;
    // htorress - RSIS 18 - Fin
    public OrdenServicioDTO aprobar(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,UsuarioDTO usuarioDTO) throws OrdenServicioException;
    public OrdenServicioDTO notificar(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,UsuarioDTO usuarioDTO) throws OrdenServicioException;
    /* OSINE_SFS-480 - RSIS 17 - Inicio */
    public OrdenServicioDTO concluir(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,UsuarioDTO usuarioDTO,Long idArchivo,String fechaInicioPlazoDescargo) throws OrdenServicioException; 
    /* OSINE_SFS-480 - RSIS 17 - Fin */
    public OrdenServicioDTO observar(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,String motivoReasignacion,UsuarioDTO usuarioDTO) throws OrdenServicioException;
    public OrdenServicioDTO observarAprobar(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,String motivoReasignacion,UsuarioDTO usuarioDTO) throws OrdenServicioException;
    public OrdenServicioDTO confirmarDescargo(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,UsuarioDTO usuarioDTO) throws OrdenServicioException;
    public List<OrdenServicioDTO> listarOrdenServicio(OrdenServicioFilter filtro);
    /* OSINE_SFS-480 - RSIS 40 - Inicio */    
    public List<OrdenServicioDTO> devolverOrdenServicioSupe(List<OrdenServicioDTO> ordenesServicio, Long idPersonalOri,Long idPersonalOriSIGED,Long idPeticion, Long idMotivo, String comentarioDevolucion, UsuarioDTO usuarioDTO) throws OrdenServicioException;
    /* OSINE_SFS-480 - RSIS 40 - Fin */    
    /* OSINE_SFS-480 - RSIS 43 - Inicio */
    public OrdenServicioDTO anularExpedienteOrden(Long idExpediente,Long idOrdenServicio,UsuarioDTO usuarioDTO, String motivoAnulacionOs, Long idPersonal) throws OrdenServicioException;
    /* OSINE_SFS-480 - RSIS 43 - Fin */
    public Date calculoFechaFin(String fechaInicio, String idDepartamento, Long nroDias) throws OrdenServicioException; 
    /* OSINE791 RSIS20 - Inicio */
    public OrdenServicioDTO atenderDsr(ExpedienteDTO expedienteDTO,Long idPersonalOri,String flagSupervision,Long nroIteracion,UsuarioDTO usuarioDTO) throws OrdenServicioException;
    /* OSINE791 RSIS20 - Fin */
    public OrdenServicioDTO aprobarCriticidad(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,UsuarioDTO usuarioDTO) throws OrdenServicioException;
    /* OSINE_SFS-791 - RSIS 33 - Inicio */
    public OrdenServicioDTO actualizar(OrdenServicioFilter filtro, UsuarioDTO usuarioDTO) throws OrdenServicioException;
	public OrdenServicioDTO confirmarOrdenServicioLevantamiento(OrdenServicioFilter filtro, UbigeoDTO ubigoDTO, UsuarioDTO usuarioDTO, Long idPersonal, Long idPersonalSiged) throws OrdenServicioException;
	public List<OrdenServicioDTO> findByFilter(OrdenServicioFilter filtro) throws OrdenServicioException;
    /* OSINE_SFS-791 - RSIS 33 - Fin */
	/* OSINE_SFS-791 - RSIS 47 - Fin */
	public ExpedienteInRO armarExpedienteInRoEnvioDocSiged(ExpedienteDTO expedienteDTO, String codTipoDoc,int usuarioCreador,DireccionUnidadSupervisadaDTO direUnid,UsuarioDTO usuarioDTO);
	/* OSINE_SFS-791 - RSIS 47 - Fin */
}
