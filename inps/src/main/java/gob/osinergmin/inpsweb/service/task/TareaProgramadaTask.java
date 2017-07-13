/**
* Resumen		
* Objeto			: TareaProgramadaTask.java
* Descripción		: Clase Controller TareaProgramadaTask
* Fecha de Creación	: 11/10/2016
* PR de Creación	: OSINE_SFS-791
* Autor				: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------				
* OSINE_SFS-791     11/10/2016      Mario Dioses Fernandez      Crear la tarea automática que notifique vía correo que se debe elaborar el oficio por obligaciones incumplidas sin subsanar
* OSINE_SFS-791     11/10/2016      Mario Dioses Fernandez      Crear la tarea automática que cancele el registro de hidrocarburos
*/ 
package gob.osinergmin.inpsweb.service.task;
import java.net.UnknownHostException;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import gob.osinergmin.inpsweb.service.business.MaestroColumnaServiceNeg;
import gob.osinergmin.inpsweb.service.business.ObligacionDsrServiceNeg;
import gob.osinergmin.inpsweb.service.business.SupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.business.UnidadSupervisadaServiceNeg;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteTareaDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.OrdenServicioDTO;
import gob.osinergmin.mdicommon.domain.dto.PersonalDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.UbigeoDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.dto.busqueda.BusquedaUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.ui.SupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.UnidadSupervisadaFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ServletContextAware;

public class TareaProgramadaTask  implements  ServletContextAware{
	private static final Logger LOG = LoggerFactory.getLogger(TareaProgramadaTask.class);
	@Inject
	private MaestroColumnaServiceNeg maestroColumnaServiceNeg;		
	@Inject
	private UnidadSupervisadaServiceNeg unidadSupervisadaServiceNeg;	
	@Inject
	private ObligacionDsrServiceNeg obligacionDsrServiceNeg;    
    @Inject 
	private SupervisionServiceNeg  supervisionServiceNeg;        
	private ServletContext context;	
	private UsuarioDTO usuario;
	
	public void tareaProgNotiOblIncumpSubsana() {
		LOG.info("Procesando tareaProgNotiOblIncumpSubsana");
		//OSINE_SFS-791 - RSIS 46
		SupervisionFilter filtroSup=new SupervisionFilter();
		try {
			filtroSup.setPlazoNroDias(Constantes.PLAZO_NOTIFICACION_OFICIO_OBLIGACIONES_INCUMPLIDAS);
			filtroSup.setFlagTareaNotiCancelacion(Constantes.ESTADO_ACTIVO);
			filtroSup.setFlagTareaProgramada(Constantes.ESTADO_ACTIVO);
			List<SupervisionDTO> supervisionList = supervisionServiceNeg.listarSupSinSubSanar(filtroSup);
			if(supervisionList!=null && supervisionList.size()!=0){
				//Obtener Lista que no se culmina el levantamiento de obligaciones incumplidas - Emitir oficio 
				for(SupervisionDTO detalle: supervisionList){
					if(detalle.getOrdenServicioDTO()!=null && detalle.getOrdenServicioDTO().getExpediente()!=null && 
						detalle.getOrdenServicioDTO().getExpediente().getUnidadSupervisada()!=null && detalle.getOrdenServicioDTO().getExpediente().getPersonal()!=null &&
						detalle.getOrdenServicioDTO().getExpediente().getPersonal().getNombreUsuarioSiged()!=null){
						OrdenServicioDTO ordenServicio=detalle.getOrdenServicioDTO();
						ExpedienteDTO expediente=ordenServicio.getExpediente();
						UnidadSupervisadaDTO unidadSupervisada=expediente.getUnidadSupervisada();
						ExpedienteTareaDTO expedienteTarea=expediente.getExpedienteTareaDTO();
						UbigeoDTO ubigeo=getUbigeo(unidadSupervisada);
						setUsuario(expediente.getPersonal());
						if(ubigeo!=null){	
							//procesar tarea
					        obligacionDsrServiceNeg.procesarTareaNotiOblIncumpSubsana(expediente, expedienteTarea, ubigeo, ordenServicio, getUsuario());					            	
						}
					}
				}
			}
		} catch (Exception ex) {
            LOG.error("Error tareaProgNotiOblIncumpSubsana", ex);
        }
	}
	
	public void tareaProgCancelacionRH(){
		LOG.info("Procesando tareaProgCancelacionRH");
		//OSINE_SFS-791 - RSIS 47
		SupervisionFilter filtroSup=new SupervisionFilter();
		try {
			filtroSup.setPlazoNroDias(Constantes.PLAZO_CANCELAR_RH);
			filtroSup.setFlagTareaCancelacion(Constantes.ESTADO_ACTIVO);
			filtroSup.setFlagTareaProgramada(Constantes.ESTADO_ACTIVO);
			List<SupervisionDTO> supervisionList = supervisionServiceNeg.listarSupSinSubSanar(filtroSup);
			if(supervisionList!=null && supervisionList.size()!=0){
				//Obtener Lista que no se culmino el levantamiento de obligaciones incumplidas - CancelacionRH
				for(SupervisionDTO detalle: supervisionList){
					if(detalle.getOrdenServicioDTO()!=null && detalle.getOrdenServicioDTO().getExpediente()!=null && 
							detalle.getOrdenServicioDTO().getExpediente().getUnidadSupervisada()!=null && detalle.getOrdenServicioDTO().getExpediente().getPersonal()!=null &&
							detalle.getOrdenServicioDTO().getExpediente().getPersonal().getNombreUsuarioSiged()!=null){
						SupervisionDTO supervision=detalle;
						OrdenServicioDTO ordenServicio=supervision.getOrdenServicioDTO();
						ExpedienteDTO expediente=ordenServicio.getExpediente();
						UnidadSupervisadaDTO unidadSupervisada=expediente.getUnidadSupervisada();
						ExpedienteTareaDTO expedienteTarea=expediente.getExpedienteTareaDTO();
						UbigeoDTO ubigeo=getUbigeo(unidadSupervisada);
						setUsuario(expediente.getPersonal());
						if(ubigeo!=null){							
							//procesar tarea
					        obligacionDsrServiceNeg.procesarTareaCancelacionRH(expediente, unidadSupervisada, supervision, ordenServicio, expedienteTarea, getUsuario(), ubigeo, context);
						}
					}
				}
			}
		} catch (Exception ex) {
            LOG.error("Error tareaProgCancelacionRH", ex);
        }
	}
	public UbigeoDTO getUbigeo(UnidadSupervisadaDTO unidadSupervisada){
		LOG.info("Procesando getUbigeo");
		//Obtener El Ubigeo Unidad Operativa	
		UbigeoDTO ubigeo=null;
		UnidadSupervisadaFilter filtro=new UnidadSupervisadaFilter();	
		if(unidadSupervisada.getIdUnidadSupervisada()!=null){
			filtro=new UnidadSupervisadaFilter();
			filtro.setCadIdUnidadSupervisada(unidadSupervisada.getIdUnidadSupervisada().toString());
			List<MaestroColumnaDTO> listaDireUoDl = maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_DIRE_INPS_UO, Constantes.APLICACION_INPS);
            if(listaDireUoDl!=null && listaDireUoDl.size()!=0) { filtro.setCadCodigosTipoDireccion(listaDireUoDl.get(0).getCodigo()); }
            List<BusquedaUnidadSupervisadaDTO> busquedaList=unidadSupervisadaServiceNeg.cargaDataUnidadOperativaMasiva(filtro);					            
            if(busquedaList!=null && busquedaList.size()!=0){ 
            	BusquedaUnidadSupervisadaDTO busqUnidadSupervisada=busquedaList.get(0);
            	if(busqUnidadSupervisada.getDireccionUnidadsupervisada()!=null && 
            			busqUnidadSupervisada.getDireccionUnidadsupervisada().getDepartamento()!=null && 
            			busqUnidadSupervisada.getDireccionUnidadsupervisada().getProvincia()!=null &&
            			busqUnidadSupervisada.getDireccionUnidadsupervisada().getDistrito()!=null){
            		ubigeo=new UbigeoDTO();
	            	ubigeo.setIdDepartamento(busqUnidadSupervisada.getDireccionUnidadsupervisada().getDepartamento().getIdDepartamento());
	            	ubigeo.setIdProvincia(busqUnidadSupervisada.getDireccionUnidadsupervisada().getProvincia().getIdProvincia());
	            	ubigeo.setIdDistrito(busqUnidadSupervisada.getDireccionUnidadsupervisada().getDistrito().getIdDistrito());
            	}
            }
		}
		return ubigeo;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(PersonalDTO personal) throws UnknownHostException {
		this.usuario = new UsuarioDTO();
		this.usuario.setCodigo(personal.getNombreUsuarioSiged());
		this.usuario.setTerminal(Constantes.getTERMINAL());	
	}
	
    public void setServletContext(ServletContext servletContext) {
        this.context = servletContext;
    }
//OSINE_SFS-791 - RSIS 40 Inicio
    public void tareaProgEjecucionHabilitacionCierre() {
        LOG.info("Procesando tareaProgEjecucionCierre");
        SupervisionFilter supervisionFilter = new SupervisionFilter();
        supervisionFilter.setFlagTareaProgramadaHabCierre(Constantes.ESTADO_ACTIVO);
        supervisionFilter.setFlagTareaProgramadaResultadoSup(Constantes.ESTADO_ACTIVO);
        supervisionFilter.setFlagTareaProgramadaAuto(Constantes.ESTADO_INACTIVO);
        try {
            List<SupervisionDTO> SupervisionList = supervisionServiceNeg.verificarSupervisionCierreTotalParcialTareaProgramada(supervisionFilter);            
            if (SupervisionList != null && SupervisionList.size() != 0) {
            	LOG.info("HAY |" + SupervisionList.size() + "| elementos en la lista");
                for (SupervisionDTO supervisionDTO : SupervisionList) {
                    LOG.info("ID SUPERVISION |" + supervisionDTO.getIdSupervision() + "|");
                    if (supervisionDTO.getSupervisionAnterior().getIdSupervision() != null) {
                        UnidadSupervisadaDTO unidad = verUnidadSupervisadaDTO(supervisionDTO);
                        if (unidad != null && supervisionDTO.getOrdenServicioDTO() != null) {
                            OrdenServicioDTO ordenServicio=supervisionDTO.getOrdenServicioDTO();
                            ExpedienteDTO expediente=ordenServicio.getExpediente();
                            UbigeoDTO ubigeo = getUbigeo(unidad);
                            setUsuario(expediente.getPersonal());
                            if (ubigeo != null) {                              
                                obligacionDsrServiceNeg.procesarTareaHabilitacionCierre(unidad, supervisionDTO, getUsuario(), ubigeo, context);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOG.error("Error tareaProgEjecucionCierre", ex);
        }
    }
    
    public UnidadSupervisadaDTO verUnidadSupervisadaDTO(SupervisionDTO supervision) {
        UnidadSupervisadaDTO unidad = new UnidadSupervisadaDTO();
        unidad.setIdUnidadSupervisada(supervision.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getIdUnidadSupervisada());
        unidad.setCodigoOsinergmin(supervision.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getCodigoOsinergmin());
        unidad.setActividad(supervision.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getActividad());
        unidad.setNombreUnidad(supervision.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getNombreUnidad());
        return unidad;
    }
    //OSINE_SFS-791 - RSIS 40 Fin
}
