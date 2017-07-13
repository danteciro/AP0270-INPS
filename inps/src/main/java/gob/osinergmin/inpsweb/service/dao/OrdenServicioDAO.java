/**
* Resumen		
* Objeto		: OrdenServicioDAO.java
* Descripción		: Clase interfaz para la implementación de métodos en OrdenServicioDAOImpl
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                              Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     09/05/2016      Mario Dioses Fernandez              Registrar en BD campo FECHA_INICIO_PLAZO y FECHA_FIN_PLAZO luego de CONCLUIR_OS, considerando Plazo_Descargo por Rubro (MDI_ACTIVIDAD)
* OSINE_SFS-480     23/05/2016      Hernán Torres Saenz                 Implementar la funcionalidad de anular orden de servicio de acuerdo a especificaciones.
* OSINE_SFS-480     01/06/2016      Luis García Reyna                   Implementar la funcionalidad de editar asignaciones de acuerdo a especificaciones
* OSINE791          19/08/2016      Jose Herrera                        Agregar Sigla de Division al Numero Orden Servicio
*
*/

package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.inpsweb.service.exception.OrdenServicioException;
import gob.osinergmin.mdicommon.domain.dto.OrdenServicioDTO;
import gob.osinergmin.mdicommon.domain.ui.OrdenServicioFilter;

import java.util.Date;
import java.util.List;

/**
 *
 * @author jpiro
 */
public interface OrdenServicioDAO {
    public OrdenServicioDTO veriActuFlgCumplimiento(Long idOrdenServicio,UsuarioDTO usuarioDTO) throws OrdenServicioException;
    /* OSINE_SFS-480 - RSIS 40 - Inicio*/
    public OrdenServicioDTO cambiarEstadoProceso(Long idOrdenServicio, Long idPersonalOri,Long idPersonalDest,Long idEstadoProceso,String motivoReasignacion,UsuarioDTO usuarioDTO,Long idPeticion, Long idMotivo) throws OrdenServicioException; 
    /* OSINE_SFS-480 - RSIS 40 - Fin */
    /* OSINE791 - RSIS1 - Inicio */
    public OrdenServicioDTO registrar(Long idExpediente,Long idTipoAsignacion,Long idEstadoProceso,String codigoTipoSupervisor,Long idLocador,Long idSupervisoraEmpresa,UsuarioDTO usuarioDTO,String sigla) throws OrdenServicioException;
    /* OSINE791 - RSIS1 - Fin */
    public List<OrdenServicioDTO> findByFilter(OrdenServicioFilter filtro) throws OrdenServicioException;
    public List<OrdenServicioDTO> find(OrdenServicioFilter filtro) throws OrdenServicioException;
    public OrdenServicioDTO confirmarDescargo(Long idOrdenServicio,UsuarioDTO usuarioDTO) throws OrdenServicioException;
    /* OSINE_SFS-480 - RSIS 17 - Inicio */
    public Date calculoFechaFin(String fechaInicio, String idDepartamento, Long nroDias) throws OrdenServicioException; 
    public OrdenServicioDTO actualizar(OrdenServicioFilter filtro, UsuarioDTO usuarioDTO) throws OrdenServicioException; 
    /* OSINE_SFS-480 - RSIS 17 - Fin */

    /* OSINE_SFS-480 - RSIS 43 - Inicio */
    public OrdenServicioDTO anularExpedienteOrden(Long idExpediente,Long idOrdenServicio, UsuarioDTO usuarioDTO) throws OrdenServicioException;
    /* OSINE_SFS-480 - RSIS 43 - Fin */
    /* OSINE_SFS-480 - RSIS 47 - Inicio */
    public OrdenServicioDTO editarExpedienteOrdenServicio(Long idOrdenServicio, Long idTipoAsignacion, String codigoTipoSupervisor,Long idLocador, Long idSupervisoraEmpresa,UsuarioDTO usuarioDTO,String flagConfirmaTipoAsignacion ) throws OrdenServicioException;
    /* OSINE_SFS-480 - RSIS 47 - Fin */
}
