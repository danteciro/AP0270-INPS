/**
* Resumen
* Objeto		: ExpedienteBuilder.java
* Descripción		: Constructor de Expediente
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     19/05/2016      Luis García Reyna           Crear la interfaz "devolver asignaciones" de acuerdo a especificaciones.
* OSINE_SFS-480     23/05/2016      Hernán Torres Saenz         Implementar la funcionalidad de anular orden de servicio de acuerdo a especificaciones 
* OSINE_SFS-791     26/08/2016      Gullet Alvites Pisco   		Se modifica el metodo toExpedienteDto() para agregar los campos osIdentificadorEstadoSgt, idActividad
* OSINE_SFS-791     06/10/2016      Mario Dioses Fernandez      Crear la funcionalidad "verificar levantamientos" que permita verificar el tipo de asignación para la orden de levantamiento DSR-CRITICIDAD.
* OSINE_SFS-791     17/10/2016      Mario Dioses Fernandez      Crear la tarea automática que cancele el registro de hidrocarburos
* OSINE791–RSIS41   | 11/10/2016 | Zosimo Chaupis Santur | Crear la funcionalidad para generar los resultados de una supervisión de orden de levantamiento DSR-CRITICIDAD. 

*/

package gob.osinergmin.inpsweb.domain.builder;
import gob.osinergmin.inpsweb.domain.MdiEmpresaSupervisada;
import gob.osinergmin.inpsweb.domain.MdiMaestroColumna;
import gob.osinergmin.inpsweb.domain.MdiUnidadSupervisada;
import gob.osinergmin.inpsweb.domain.PghEstadoProceso;
import gob.osinergmin.inpsweb.domain.PghExpediente;
import gob.osinergmin.inpsweb.domain.PghFlujoSiged;
import gob.osinergmin.inpsweb.domain.PghObligacionSubTipo;
import gob.osinergmin.inpsweb.domain.PghObligacionTipo;
import gob.osinergmin.inpsweb.domain.PghPersonal;
import gob.osinergmin.inpsweb.domain.PghProceso;
import gob.osinergmin.inpsweb.domain.PghTramite;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.Utiles;
import gob.osinergmin.mdicommon.domain.dto.ActividadDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresaSupDTO;
import gob.osinergmin.mdicommon.domain.dto.EstadoProcesoDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.FlujoSigedDTO;
import gob.osinergmin.mdicommon.domain.dto.LocadorDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.ObligacionSubTipoDTO;
import gob.osinergmin.mdicommon.domain.dto.ObligacionTipoDTO;
import gob.osinergmin.mdicommon.domain.dto.OrdenServicioDTO;
import gob.osinergmin.mdicommon.domain.dto.PersonalDTO;
import gob.osinergmin.mdicommon.domain.dto.ProcesoDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisoraEmpresaDTO;
import gob.osinergmin.mdicommon.domain.dto.TramiteDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadSupervisadaDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jpiro
 */
public class ExpedienteBuilder {
    public static List<ExpedienteDTO> toListExpedienteDto(List<PghExpediente> lista) {
        ExpedienteDTO registroDTO;
        List<ExpedienteDTO> retorno = new ArrayList<ExpedienteDTO>();
        if (lista != null) {
            for (PghExpediente maestro : lista) {
                registroDTO = toExpedienteDto(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    } 
    
    public static ExpedienteDTO toExpedienteDto(PghExpediente registro) {
        ExpedienteDTO registroDTO = new ExpedienteDTO();
        
        /* OSINE_SFS-791 - RSIS 47 - Inicio */ 
        if(registro!=null){
	        registroDTO.setIdSiged(registro.getIdSiged());
	        registroDTO.setObservacion(registro.getObservacion());
	        registroDTO.setFechaPrueba(registro.getFechaPrueba());
	        registroDTO.setNumeroFoleos(registro.getNumeroFolios());
	        registroDTO.setObservacionSolAnt(registro.getObservacionSolAnt());
	        registroDTO.setEstadoSolicitudSiged(registro.getEstadoSolicitudSiged());
	        registroDTO.setUsuarioSiged(registro.getUsuarioSiged());
	        registroDTO.setFechaEstadoProceso(registro.getFechaEstadoProceso());
	        registroDTO.setFlagMuestral(registro.getFlagMuestral());
	        registroDTO.setComentarios(registro.getComentarios());
	           if (registro.getTieneCodigoOsinergmin() != null) {
                registroDTO.setTieneCodigoOsinergmin(registro.getTieneCodigoOsinergmin().toString());
            }
            if (registro.getIdEstadoProceso() != null && registro.getIdEstadoProceso().getIdEstadoProceso() != null) {
                EstadoProcesoDTO estadoProceso = new EstadoProcesoDTO();
                estadoProceso.setIdEstadoProceso(registro.getIdEstadoProceso().getIdEstadoProceso());
                registroDTO.setEstadoProceso(estadoProceso);
            }
            /* OSINE_SFS-791 - RSIS 41 - Inicio */
            if (registro.getIdEstadoLevantamiento() != null) {
                if (registro.getIdEstadoLevantamiento().getIdMaestroColumna() != null) {
                    registroDTO.setEstadoLevantamiento(new MaestroColumnaDTO(registro.getIdEstadoLevantamiento().getIdMaestroColumna(), registro.getIdEstadoLevantamiento().getDescripcion(), registro.getIdEstadoLevantamiento().getCodigo()));
                }
            }
            /* OSINE_SFS-791 - RSIS 41 - Fin */
            /* OSINE_SFS-791 - RSIS 47 - Fin */

	        registroDTO.setIdExpediente(registro.getIdExpediente());
	        registroDTO.setNumeroExpediente(registro.getNumeroExpediente());
	        registroDTO.setFlagOrigen(registro.getFlagOrigen());
	        registroDTO.setAsuntoSiged(registro.getAsuntoSiged()!=null?registro.getAsuntoSiged().toUpperCase():registro.getAsuntoSiged());
	        registroDTO.setFechaCreacionSiged(registro.getFechaCreacionSiged());
	        registroDTO.setFlagTramiteFinalizado(registro.getFlagTramiteFinalizado());
	        registroDTO.setNroOrdenesServicio(registro.getNroOrdenesServicio());
	        registroDTO.setEstado(registro.getEstado());
	        if(registro.getIdFlujoSiged()!=null){
	            registroDTO.setFlujoSiged(new FlujoSigedDTO(registro.getIdFlujoSiged().getIdFlujoSiged(),registro.getIdFlujoSiged().getNombreFlujoSiged()));
	        }
	        if(registro.getIdEmpresaSupervisada()!=null){
	            EmpresaSupDTO empresaSupDTO=new EmpresaSupDTO();
	            empresaSupDTO.setIdEmpresaSupervisada(registro.getIdEmpresaSupervisada().getIdEmpresaSupervisada());
	            empresaSupDTO.setRazonSocial(registro.getIdEmpresaSupervisada().getRazonSocial());
	            empresaSupDTO.setRuc(registro.getIdEmpresaSupervisada().getRuc());
	            empresaSupDTO.setNroIdentificacion(registro.getIdEmpresaSupervisada().getNumeroIdentificacion());
	            if(registro.getIdEmpresaSupervisada().getTipoDocumentoIdentidad()!=null){
	                MaestroColumnaDTO tipoDocumentoIdentidad=new MaestroColumnaDTO();
	                tipoDocumentoIdentidad.setIdMaestroColumna(registro.getIdEmpresaSupervisada().getTipoDocumentoIdentidad().getIdMaestroColumna());
	                tipoDocumentoIdentidad.setDescripcion(registro.getIdEmpresaSupervisada().getTipoDocumentoIdentidad().getDescripcion());
	                empresaSupDTO.setTipoDocumentoIdentidad(tipoDocumentoIdentidad);
	            }
	            registroDTO.setEmpresaSupervisada(empresaSupDTO);
	        }
	        if(registro.getIdUnidadSupervisada()!=null){
	            UnidadSupervisadaDTO unidadSupervisadaDTO=new UnidadSupervisadaDTO();
	            unidadSupervisadaDTO.setIdUnidadSupervisada(registro.getIdUnidadSupervisada().getIdUnidadSupervisada());
	            unidadSupervisadaDTO.setCodigoOsinergmin(registro.getIdUnidadSupervisada().getCodigoOsinergmin());
	            /* OSINE_SFS-480 - RSIS 38 - Inicio */
	            unidadSupervisadaDTO.setNroRegistroHidrocarburo(registro.getIdUnidadSupervisada().getNumeroRegistroHidrocarburo());
	            unidadSupervisadaDTO.setNombreUnidad(registro.getIdUnidadSupervisada().getNombreUnidad());
	            if(registro.getIdUnidadSupervisada().getIdActividad()!=null){
	                ActividadDTO actividadDTO=new ActividadDTO();
	                actividadDTO.setNombre(registro.getIdUnidadSupervisada().getIdActividad().getNombre());
	                /* OSINE791 - RSIS 21 - Inicio */
	                actividadDTO.setIdActividad(registro.getIdUnidadSupervisada().getIdActividad().getIdActividad());
	                /* OSINE791 - RSIS 21 - Fin */
	                registroDTO.setActividad(actividadDTO);
	            }
	            /* OSINE_SFS-480 - RSIS 38 - Fin */       
                    unidadSupervisadaDTO.setRuc(registro.getIdUnidadSupervisada().getRuc());
	            registroDTO.setUnidadSupervisada(unidadSupervisadaDTO);
	        }
	        if(registro.getIdPersonal()!=null){
	            PersonalDTO personalDTO=new PersonalDTO();
	            personalDTO.setIdPersonal(registro.getIdPersonal().getIdPersonal());
	            personalDTO.setNombre(registro.getIdPersonal().getNombre());
	            personalDTO.setApellidoPaterno(registro.getIdPersonal().getApellidoPaterno());
	            personalDTO.setApellidoMaterno(registro.getIdPersonal().getApellidoMaterno());
	            personalDTO.setIdPersonalSiged(registro.getIdPersonal().getIdPersonalSiged());
	            registroDTO.setPersonal(personalDTO);
	        }
	        
	        if(registro.getOrdenServicio()!=null){
	            OrdenServicioDTO ordenServicioDTO= new OrdenServicioDTO();
	            ordenServicioDTO.setIdOrdenServicio(registro.getOrdenServicio().getIdOrdenServicio());
	            ordenServicioDTO.setFechaCreacion(registro.getOrdenServicio().getFechaCreacion());
	            ordenServicioDTO.setFechaCreacionOS(Utiles.DateToString(registro.getOrdenServicio().getFechaCreacion(), Constantes.FORMATO_FECHA_CORTA));
	            ordenServicioDTO.setNumeroOrdenServicio(registro.getOrdenServicio().getNumeroOrdenServicio());
	            ordenServicioDTO.setIdTipoAsignacion(registro.getOrdenServicio().getIdTipoAsignacion());
	            /* OSINE_SFS-791 - RSIS 33 - Inicio */
	            ordenServicioDTO.setFechaHoraAnalogicaCreacionOS(Utiles.DateToString(registro.getOrdenServicio().getFechaCreacion(), Constantes.FORMATO_FECHA_LARGE));
	            ordenServicioDTO.setFlagConfirmaTipoAsignacion(registro.getOrdenServicio().getFlagConfirmaTipoAsignacion());
	            /* OSINE_SFS-791 - RSIS 33 - Fin */
	            if(registro.getOrdenServicio().getIdLocador()!=null){
	                LocadorDTO locadorDTO=new LocadorDTO();
	                locadorDTO.setIdLocador(registro.getOrdenServicio().getIdLocador().getIdLocador());
	                locadorDTO.setNombreCompletoArmado(registro.getOrdenServicio().getIdLocador().getNombreCompletoArmado());
	                ordenServicioDTO.setLocador(locadorDTO);
	            }
	            if(registro.getOrdenServicio().getIdSupervisoraEmpresa()!=null){
	                SupervisoraEmpresaDTO supervisoraEmpresaDTO=new SupervisoraEmpresaDTO();
	                supervisoraEmpresaDTO.setIdSupervisoraEmpresa(registro.getOrdenServicio().getIdSupervisoraEmpresa().getIdSupervisoraEmpresa());
	                supervisoraEmpresaDTO.setRazonSocial(registro.getOrdenServicio().getIdSupervisoraEmpresa().getRazonSocial());
	                supervisoraEmpresaDTO.setNombreConsorcio(registro.getOrdenServicio().getIdSupervisoraEmpresa().getNombreConsorcio());
	                ordenServicioDTO.setSupervisoraEmpresa(supervisoraEmpresaDTO);
	            }
	            
	            if(registro.getOrdenServicio().getIdEstadoProceso()!=null){
	                EstadoProcesoDTO estadoProcesoDTO=new EstadoProcesoDTO();
	                estadoProcesoDTO.setIdEstadoProceso(registro.getOrdenServicio().getIdEstadoProceso().getIdEstadoProceso());
	                estadoProcesoDTO.setIdentificadorEstado(registro.getOrdenServicio().getIdEstadoProceso().getIdentificadorEstado());
	                estadoProcesoDTO.setNombreEstado(registro.getOrdenServicio().getIdEstadoProceso().getNombreEstado());
	                ordenServicioDTO.setEstadoProceso(estadoProcesoDTO);
	            }
	            ordenServicioDTO.setIteracion(registro.getOrdenServicio().getIteracion());
	            ordenServicioDTO.setFlagCumplimiento(registro.getOrdenServicio().getFlagCumplimiento());
	            ordenServicioDTO.setMotivoReasignacion(registro.getOrdenServicio().getMotivoReasignacion());
	            /* OSINE_SFS-480 - RSIS 43 - Inicio */
	            if(registro.getOrdenServicio().getIdPeticion()!=null){
	            	ordenServicioDTO.setIdPeticion(new MaestroColumnaDTO(registro.getOrdenServicio().getIdPeticion().getIdMaestroColumna(), registro.getOrdenServicio().getIdPeticion().getDescripcion(), registro.getOrdenServicio().getIdPeticion().getCodigo()));
	            }            
	            ordenServicioDTO.setIdMotivo(registro.getOrdenServicio().getIdMotivo());
	            ordenServicioDTO.setComentarioDevolucion(registro.getOrdenServicio().getComentarioDevolucion());
	            /* OSINE_SFS-480 - RSIS 43 - Fin */
	            /* OSINE791 - RSIS21 - Inicio */
	            ordenServicioDTO.setOsIdentificadorEstadoSgt(registro.getOsIdentificadorEstadoSgt());
	            /* OSINE791 - RSIS24 - Fin */
	            registroDTO.setOrdenServicio(ordenServicioDTO);
	            registroDTO.setIteracionExpediente(registro.getIteracionExpediente());
	        }
	        
	        if(registro.getIdTramite()!=null){
	            TramiteDTO tramiteDTO=new TramiteDTO();
	            tramiteDTO.setIdTramite(registro.getIdTramite().getIdTramite());
	            if(registro.getIdTramite().getIdEtapa()!=null){
	                tramiteDTO.setIdEtapa(registro.getIdTramite().getIdEtapa().getIdEtapa());
	                if(registro.getIdTramite().getIdEtapa().getIdProceso()!=null){
	                    tramiteDTO.setIdProceso(registro.getIdTramite().getIdEtapa().getIdProceso().getIdProceso());
	                }
	            }
	            registroDTO.setTramite(tramiteDTO);
	        }
	        
	        if(registro.getIdProceso()!=null){
	            registroDTO.setProceso(new ProcesoDTO(registro.getIdProceso().getIdProceso()));
	        }
	        
	        if(registro.getIdObligacionTipo()!=null){
	            registroDTO.setObligacionTipo(new ObligacionTipoDTO(registro.getIdObligacionTipo().getIdObligacionTipo()));
	        }
	        if(registro.getIdObligacionSubTipo()!=null){
	            registroDTO.setObligacionSubTipo(new ObligacionSubTipoDTO(registro.getIdObligacionSubTipo().getIdObligacionSubTipo()));
	        }
	        if(registro.getIdObligacionSubTipo()!=null){
	        	registroDTO.setObligacionSubTipo(new ObligacionSubTipoDTO(registro.getIdObligacionSubTipo().getIdObligacionSubTipo()));
	        }
	        registroDTO.setFechaDerivacion(registro.getFechaDerivacion());
	        registroDTO.setFlagMuestral(registro.getFlagMuestral());
	        registroDTO.setCodigoFlujoSupervInps(registro.getCodigoFlujoSupervInps());
	        
	        /* OSINE_SFS-791 - RSIS 33 - Inicio */ 
	        registroDTO.setFlagEvaluaTipoAsignacion(registro.getFlagEvaluaTipoAsignacion());
	        /* OSINE_SFS-791 - RSIS 33 - Fin */ 
        }        
        return registroDTO;
    }
    
    public static PghExpediente toExpedienteDomain(ExpedienteDTO registroDTO) {
        PghExpediente registro=new PghExpediente();        
        /* OSINE_SFS-791 - RSIS 47 - Inicio */ 
        if(registroDTO!=null){
	        registro.setIdExpediente(registroDTO.getIdExpediente());          
	        registro.setObservacion(registroDTO.getObservacion());
	        registro.setFechaPrueba(registroDTO.getFechaPrueba());
	        registro.setNumeroFolios(registroDTO.getNumeroFoleos());
	        registro.setEstadoSolicitudSiged(registroDTO.getEstadoSolicitudSiged());
	        registro.setObservacionSolAnt(registroDTO.getObservacionSolAnt());
	        if(registroDTO.getTieneCodigoOsinergmin()!=null){
	        	registro.setTieneCodigoOsinergmin(registroDTO.getTieneCodigoOsinergmin().charAt(0));
	        }
	        registro.setUsuarioSiged(registroDTO.getUsuarioSiged());
	        registro.setIdSiged(registroDTO.getIdSiged());
	        registro.setFlagTramiteFinalizado(registroDTO.getFlagTramiteFinalizado());
	        registro.setEstado(registroDTO.getEstado());
	        if(registroDTO.getEstadoLevantamiento()!=null && registroDTO.getEstadoLevantamiento().getIdMaestroColumna()!=null){
	        	registro.setIdEstadoLevantamiento(new MdiMaestroColumna(registroDTO.getEstadoLevantamiento().getIdMaestroColumna()));
	        }
	        /* OSINE_SFS-791 - RSIS 47 - Fin */ 
	        	        
	        registro.setNumeroExpediente(registroDTO.getNumeroExpediente());
	        registro.setAsuntoSiged(registroDTO.getAsuntoSiged()!=null?registroDTO.getAsuntoSiged().toUpperCase():registroDTO.getAsuntoSiged());
	        registro.setFechaCreacionSiged(registroDTO.getFechaCreacionSiged());	        
	        registro.setFlagOrigen(registroDTO.getFlagOrigen());
	        if(registroDTO.getEstadoProceso()!=null){
	            registro.setIdEstadoProceso(new PghEstadoProceso(registroDTO.getEstadoProceso().getIdEstadoProceso()));
	        }
	        if(registroDTO.getPersonal()!=null){
	            registro.setIdPersonal(new PghPersonal(registroDTO.getPersonal().getIdPersonal()));
	        }
	        if(registroDTO.getEmpresaSupervisada()!=null){
	            registro.setIdEmpresaSupervisada(new MdiEmpresaSupervisada(registroDTO.getEmpresaSupervisada().getIdEmpresaSupervisada()));
	        }
	        if(registroDTO.getUnidadSupervisada()!=null){
	            registro.setIdUnidadSupervisada(new MdiUnidadSupervisada(registroDTO.getUnidadSupervisada().getIdUnidadSupervisada()));
	        }
	        if(registroDTO.getFlujoSiged()!=null){
	            registro.setIdFlujoSiged(new PghFlujoSiged(registroDTO.getFlujoSiged().getIdFlujoSiged()));
	        }
	        if(registroDTO.getTramite()!=null){
	            registro.setIdTramite(new PghTramite(registroDTO.getTramite().getIdTramite()));
	        }
	        if(registroDTO.getProceso()!=null){
	            registro.setIdProceso(new PghProceso(registroDTO.getProceso().getIdProceso()));
	        }
	        if(registroDTO.getObligacionTipo()!=null){
	            registro.setIdObligacionTipo(new PghObligacionTipo(registroDTO.getObligacionTipo().getIdObligacionTipo()));
	        }
	        if(registroDTO.getObligacionSubTipo()!=null && registroDTO.getObligacionSubTipo().getIdObligacionSubTipo()!=null){
	        	registro.setIdObligacionSubTipo(new PghObligacionSubTipo(registroDTO.getObligacionSubTipo().getIdObligacionSubTipo()));
	        }
	        registro.setFlagMuestral(registroDTO.getFlagMuestral());
        }
        return registro;
    }
}
