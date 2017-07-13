/**
 * Resumen Objeto	: CorreoServiceNeg.java Descripción	: Clase Interfaz de la
 * capa de negocio que contiene la declaración de métodos a implementarse en el
 * CorreoServiceNegImpl Fecha de Creación	: 11/05/2016 PR de Creación	:
 * OSINE_SFS-480 Autor	: Luis García Reyna
 * ---------------------------------------------------------------------------------------------------
 * Modificaciones 
 *    Motivo            Fecha         Nombre                	Descripción
 * ---------------------------------------------------------------------------------------------------
 * OSINE_SFS-480      11/05/2016    Luis García Reyna      		Correo de Alerta a Empresa Supervisora cuando se le asigne Orden de Servicio.
 * OSINE_791-RSIS19   06/09/2016    Zosimo Chaupis Santur  		Correo de Notificacion al Supervisor Regional para supervisión de una orden de supervisión DSR-CRITICIDAD
 * OSINE_SFS-791      10/10/2016    Mario Dioses Fernandez   	Crear la funcionalidad "verificar levantamientos" que permita verificar el tipo de asignación para la orden de levantamiento DSR-CRITICIDAD.
 * OSINE_SFS-791      11/10/2016    Mario Dioses Fernandez      Crear la tarea automática que notifique vía correo que se debe elaborar el oficio por obligaciones incumplidas sin subsanar
 * OSINE_SFS-791      11/10/2016    Mario Dioses Fernandez      Crear la tarea automática que cancele el registro de hidrocarburos
 * OSINE791-RSIS34    03/11/2016    Cristopher Paucar Torre     Confirma Tipo de Asignación.
*/
package gob.osinergmin.inpsweb.service.business;

import gob.osinergmin.mdicommon.domain.dto.CorreoDTO;
import gob.osinergmin.mdicommon.domain.dto.DestinatarioCorreoDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.PersonalDTO;
import gob.osinergmin.mdicommon.domain.dto.RegistroHidrocarburoDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.ui.CorreoFilter;

import java.util.List;

public interface CorreoServiceNeg {
    /* OSINE_SFS-480 - RSIS 12 - Inicio */
	public List<CorreoDTO> findByFilter(CorreoFilter filter);

    public boolean enviarCorreoAsignarOS(PersonalDTO remitente, PersonalDTO destinatario, ExpedienteDTO expedienteDto);
    /* OSINE_SFS-480 - RSIS 12 - Fin */
    /* OSINE-791 - RSIS 34 - Inicio */
    public boolean enviarCorreoConfirmTipoAsig(PersonalDTO remitente, PersonalDTO destinatario, ExpedienteDTO expedienteDto);
    /* OSINE-791 - RSIS 34 - Fin */
    /* OSINE_SFS-791 - RSIS 19 - Inicio */
    public boolean enviarCorreoNotificacionOtrosIncumplimientos(List<DestinatarioCorreoDTO> listadestinatarioCorreoDTO, ExpedienteDTO expedienteDto, SupervisionDTO supervisionDTO);

    public boolean enviarCorreoNotificacionNoEjecucionMedida(List<DestinatarioCorreoDTO> listadestinatarioCorreoDTO, ExpedienteDTO expedienteDto);

    public boolean enviarCorreoNotificacionObstaculizacionObligaciones(List<DestinatarioCorreoDTO> listadestinatarioCorreoDTO, ExpedienteDTO expedienteDto, List<DetalleSupervisionDTO> detalleSupervisionDTO);

    /* OSINE_SFS-791 - RSIS 18 - Fin */
    public boolean enviarCorreoNotificacionMedidaSeguridad(List<DestinatarioCorreoDTO> milistaDestinos, ExpedienteDTO expediente);
    public boolean enviarCorreoNotificacionSuspensionRH(List<DestinatarioCorreoDTO> milistaDestinos, ExpedienteDTO expediente, RegistroHidrocarburoDTO registroHidrocarburoDTO);
    public boolean enviarCorreoNotificacionDesactivarRolComprasScop(List<DestinatarioCorreoDTO> milistaDestinos, ExpedienteDTO expediente, RegistroHidrocarburoDTO registroHidrocarburoDTO, UnidadSupervisadaDTO unidadSupervisadaDTO);
    public boolean enviarCorreoNotificacionDesactivarRolComprasScopProducto(List<DestinatarioCorreoDTO> milistaDestinos, ExpedienteDTO expediente, RegistroHidrocarburoDTO registroHidrocarburoDTO, UnidadSupervisadaDTO unidadSupervisadaDTO,byte[] reporteBytes);
    /* OSINE_SFS-791 - RSIS 18 - Fin */
    /* OSINE_SFS-791 - RSIS 04 - Inicio */
    public boolean EnvioNotificacionesSupervisionDsrObstaculizadaInicial(List<DestinatarioCorreoDTO> milistaDestinos, ExpedienteDTO expediente,SupervisionDTO supervisionDTO);
    /* OSINE_SFS-791 - RSIS 04 - Fin */
    /* OSINE_SFS-791 - RSIS 33 - Inicio */
    public boolean enviarCorreoNotificacionTipoAsignacion(List<DestinatarioCorreoDTO> milistaDestinos, ExpedienteDTO expediente);
    /* OSINE_SFS-791 - RSIS 33 - Fin */
    /* OSINE_SFS-791 - RSIS 46-47 - Inicio */
    public boolean enviarCorreoNotificacionTareaProg(List<DestinatarioCorreoDTO> listadestinatarioCorreoDTO, ExpedienteDTO expedienteDto, CorreoFilter filtro);
    /* OSINE_SFS-791 - RSIS 46-47 - Fin */
    /* OSINE_SFS-791 - RSIS 40 - Inicio */
    public boolean enviarCorreoNotificacionHabilitacionRH(List<DestinatarioCorreoDTO> milistaDestinos, ExpedienteDTO expediente, RegistroHidrocarburoDTO registroHidrocarburoDTO);
    public boolean enviarCorreoNotificacionHabilitacionRolComprasScop(List<DestinatarioCorreoDTO> listadestinatarioCorreoDTO, ExpedienteDTO expedienteDto, RegistroHidrocarburoDTO registroHidrocarburoDto, UnidadSupervisadaDTO unidadSupervisadaDto);
    public boolean enviarCorreoNotificacionHabilitarRolComprasScopProducto(List<DestinatarioCorreoDTO> listadestinatarioCorreoDTO, ExpedienteDTO expedienteDto, RegistroHidrocarburoDTO registroHidrocarburoDto, UnidadSupervisadaDTO unidadSupervisadaDto,byte[] reporteBytes);
    /* OSINE_SFS-791 - RSIS 40 - Fin */
    
}
