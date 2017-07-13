/**
* Resumen
* Objeto		: OrdenServicioBuilder.java
* Descripción		: Constructor de OrdenServicio
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     09/05/2016      Mario Dioses Fernandez      Registrar en BD campo FECHA_INICIO_PLAZO y FECHA_FIN_PLAZO luego de CONCLUIR_OS, considerando Plazo_Descargo por Rubro (MDI_ACTIVIDAD)
* OSINE_SFS-480     18/05/2016      Luis García Reyna           Implementar la funcionalidad de devolución de asignaciones de acuerdo a especificaciones
* OSINE_SFS-791     10/10/2016      Mario Dioses Fernandez          Crear la funcionalidad "verificar levantamientos" que permita verificar el tipo de asignación para la orden de levantamiento DSR-CRITICIDAD. 
*
*/

package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.MdiLocador;
import gob.osinergmin.inpsweb.domain.MdiMaestroColumna;
import gob.osinergmin.inpsweb.domain.MdiSupervisoraEmpresa;
import gob.osinergmin.inpsweb.domain.PghEstadoProceso;
import gob.osinergmin.inpsweb.domain.PghExpediente;
import gob.osinergmin.inpsweb.domain.PghOrdenServicio;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.Utiles;
import gob.osinergmin.mdicommon.domain.dto.ActividadDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresaSupDTO;
import gob.osinergmin.mdicommon.domain.dto.EstadoProcesoDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.LocadorDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.ObligacionTipoDTO;
import gob.osinergmin.mdicommon.domain.dto.OrdenServicioDTO;
import gob.osinergmin.mdicommon.domain.dto.ProcesoDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisoraEmpresaDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadSupervisadaDTO;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jpiro
 */
public class OrdenServicioBuilder {
    public static List<OrdenServicioDTO> toListOrdenServicioDto(List<PghOrdenServicio> lista) {
        OrdenServicioDTO registroDTO;
        List<OrdenServicioDTO> retorno = new ArrayList<OrdenServicioDTO>();
        if (lista != null) {
            for (PghOrdenServicio maestro : lista) {
                registroDTO = toOrdenServicioDto(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    } 
    
    public static OrdenServicioDTO toOrdenServicioDto(PghOrdenServicio registro) {
        OrdenServicioDTO registroDTO = new OrdenServicioDTO();
        if(registro!=null){
	        registroDTO.setIdOrdenServicio(registro.getIdOrdenServicio());
	        registroDTO.setNumeroOrdenServicio(registro.getNumeroOrdenServicio());
	        registroDTO.setFechaCreacion(registro.getFechaCreacion());
	        registroDTO.setFechaHoraCreacionOS(Utiles.DateToString(registro.getFechaCreacion(), Constantes.FORMATO_FECHA_HORA_LARGE));
	        registroDTO.setFechaHoraAnalogicaCreacionOS(Utiles.DateToString(registro.getFechaCreacion(), Constantes.FORMATO_FECHA_LARGE));
	        registroDTO.setFechaEstadoProceso(registro.getFechaEstadoProceso());
	        registroDTO.setFlagCumplimiento(registro.getFlagCumplimiento());
	        registroDTO.setFlagPresentoDescargo(registro.getFlagPresentoDescargo());
	        registroDTO.setIteracion(registro.getIteracion());
	        registroDTO.setIdTipoAsignacion(registro.getIdTipoAsignacion());        
	        /* OSINE_SFS-480 - RSIS 17 - Inicio */
	        registroDTO.setFechaIniPlazoDescargo(registro.getFechaIniPlazoDescargo()); 
	        registroDTO.setFechaFinPlazoDescargo(registro.getFechaFinPlazoDescargo());
	        registroDTO.setIdArchivoPlazoDescargo(registro.getIdArchivoPlazoDescargo()); 
	        /* OSINE_SFS-480 - RSIS 17 - Fin */
	        /* OSINE_SFS-480 - RSIS 40 - Inicio */  
	        if(registro.getIdPeticion()!=null) {
	        	registroDTO.setIdPeticion(new MaestroColumnaDTO(registro.getIdPeticion().getIdMaestroColumna(),registro.getIdPeticion().getDescripcion(), registro.getIdPeticion().getCodigo()));
	        }
	        registroDTO.setIdMotivo(registro.getIdMotivo());
	        registroDTO.setComentarioDevolucion(registro.getComentarioDevolucion());
	        /* OSINE_SFS-480 - RSIS 40 - Fin */  
	        if(registro.getIdEstadoProceso()!=null && registro.getIdEstadoProceso().getIdEstadoProceso()!=null ){
	            EstadoProcesoDTO estadoProcesoDTO=new EstadoProcesoDTO();
	            estadoProcesoDTO.setIdEstadoProceso(registro.getIdEstadoProceso().getIdEstadoProceso());
	            estadoProcesoDTO.setNombreEstado(registro.getIdEstadoProceso().getNombreEstado());
	            registroDTO.setEstadoProceso(estadoProcesoDTO);
	        }
	        if(registro.getIdLocador()!=null){
	            LocadorDTO locador=new LocadorDTO();
	            locador.setIdLocador(registro.getIdLocador().getIdLocador());
	            registroDTO.setLocador(locador);
	        }
	        if(registro.getIdSupervisoraEmpresa()!=null){
	            SupervisoraEmpresaDTO supervisoraEmpresa=new SupervisoraEmpresaDTO();
	            supervisoraEmpresa.setIdSupervisoraEmpresa(registro.getIdSupervisoraEmpresa().getIdSupervisoraEmpresa());
	            registroDTO.setSupervisoraEmpresa(supervisoraEmpresa);
	        }
	        if(registro.getIdExpediente()!=null){
	            ExpedienteDTO expedienteDTO = new ExpedienteDTO();
	            expedienteDTO.setIdExpediente(registro.getIdExpediente().getIdExpediente());
	            expedienteDTO.setNumeroExpediente(registro.getIdExpediente().getNumeroExpediente());
	            /* OSINE_SFS-791 - RSIS 33 - Inicio */ 
	            expedienteDTO.setAsuntoSiged(registro.getIdExpediente().getAsuntoSiged());
	        	/* OSINE_SFS-791 - RSIS 33 - Fin */ 
	            expedienteDTO.setAsuntoSiged(registro.getIdExpediente().getAsuntoSiged());
	            if(registro.getIdExpediente().getIdEmpresaSupervisada()!=null){
	                EmpresaSupDTO empresaSupervisadaDTO = new EmpresaSupDTO();
	                empresaSupervisadaDTO.setRazonSocial(registro.getIdExpediente().getIdEmpresaSupervisada().getRazonSocial());
	                expedienteDTO.setEmpresaSupervisada(empresaSupervisadaDTO);
	            }
	            if(registro.getIdExpediente().getIdUnidadSupervisada()!=null){
	                UnidadSupervisadaDTO unidadSupervisadaDTO = new UnidadSupervisadaDTO();
	                unidadSupervisadaDTO.setCodigoOsinergmin(registro.getIdExpediente().getIdUnidadSupervisada().getCodigoOsinergmin());
	                if(registro.getIdExpediente().getIdUnidadSupervisada().getIdActividad()!=null && 
	                		registro.getIdExpediente().getIdUnidadSupervisada().getIdActividad().getIdActividad()!=null){
	                	ActividadDTO actividadDTO = new ActividadDTO();
	                	actividadDTO.setIdActividad(registro.getIdExpediente().getIdUnidadSupervisada().getIdActividad().getIdActividad());
	                	unidadSupervisadaDTO.setActividad(actividadDTO);
	                }
	                expedienteDTO.setUnidadSupervisada(unidadSupervisadaDTO);
	            }
	            if(registro.getIdExpediente().getIdProceso()!=null && registro.getIdExpediente().getIdProceso().getIdProceso()!=null){
	            	ProcesoDTO procesoDTO = new ProcesoDTO();
	            	procesoDTO.setIdProceso(registro.getIdExpediente().getIdProceso().getIdProceso());
	            	expedienteDTO.setProceso(procesoDTO);
	            }
	            if(registro.getIdExpediente().getIdObligacionTipo()!=null && registro.getIdExpediente().getIdObligacionTipo().getIdObligacionTipo()!=null){
	            	ObligacionTipoDTO obligacionTipoDTO = new ObligacionTipoDTO();
	            	obligacionTipoDTO.setIdObligacionTipo(registro.getIdExpediente().getIdObligacionTipo().getIdObligacionTipo());
	            	expedienteDTO.setObligacionTipo(obligacionTipoDTO);
	            }
	            registroDTO.setExpediente(expedienteDTO);	            
	        }
	        /* OSINE_SFS-791 - RSIS 33 - Inicio */ 
            registroDTO.setEstado(registro.getEstado());
            registroDTO.setFechaFiscalizacion(registro.getFechaFiscalizacion());
            registroDTO.setIdDepartPlazoDescargo(registro.getIdDepartPlazoDescargo());
            registroDTO.setFlagConfirmaTipoAsignacion(registro.getFlagConfirmaTipoAsignacion());
            /* OSINE_SFS-791 - RSIS 33 - Fin */ 
        }
        return registroDTO;
    }
    
    /* OSINE_SFS-791 - RSIS 33 - Inicio */    
    public static PghOrdenServicio toOrdenServicioPgh(OrdenServicioDTO registro) {
    	PghOrdenServicio registroPgh = new PghOrdenServicio();
        if(registro!=null){
        	registroPgh.setIdOrdenServicio(registro.getIdOrdenServicio());
        	registroPgh.setNumeroOrdenServicio(registro.getNumeroOrdenServicio());
        	registroPgh.setFechaCreacion(registro.getFechaCreacion());
        	registroPgh.setFechaEstadoProceso(registro.getFechaEstadoProceso());
        	registroPgh.setFlagCumplimiento(registro.getFlagCumplimiento());
        	registroPgh.setFlagPresentoDescargo(registro.getFlagPresentoDescargo());
        	registroPgh.setIteracion(registro.getIteracion());
        	registroPgh.setIdTipoAsignacion(registro.getIdTipoAsignacion());     
        	registroPgh.setFechaIniPlazoDescargo(registro.getFechaIniPlazoDescargo()); 
        	registroPgh.setFechaFinPlazoDescargo(registro.getFechaFinPlazoDescargo());
        	registroPgh.setIdArchivoPlazoDescargo(registro.getIdArchivoPlazoDescargo());  
	        if(registro.getIdPeticion()!=null) {
	        	registroPgh.setIdPeticion(new MdiMaestroColumna(registro.getIdPeticion().getIdMaestroColumna()));
	        }
	        registroPgh.setIdMotivo(registro.getIdMotivo());
	        registroPgh.setComentarioDevolucion(registro.getComentarioDevolucion());
	        if(registro.getEstadoProceso()!=null && registro.getEstadoProceso().getIdEstadoProceso()!=null ){
	            PghEstadoProceso pghEstadoProceso=new PghEstadoProceso();
	            pghEstadoProceso.setIdEstadoProceso(registro.getEstadoProceso().getIdEstadoProceso());
	            registroPgh.setIdEstadoProceso(pghEstadoProceso);
	        }
	        if(registro.getLocador()!=null && registro.getLocador().getIdLocador()!=null){
	            MdiLocador mdiLocador=new MdiLocador();
	            mdiLocador.setIdLocador(registro.getLocador().getIdLocador());
	            registroPgh.setIdLocador(mdiLocador);
	        }
	        if(registro.getSupervisoraEmpresa()!=null && registro.getSupervisoraEmpresa().getIdSupervisoraEmpresa()!=null){
	            MdiSupervisoraEmpresa mdiSupervisoraEmpresa=new MdiSupervisoraEmpresa();
	            mdiSupervisoraEmpresa.setIdSupervisoraEmpresa(registro.getSupervisoraEmpresa().getIdSupervisoraEmpresa());
	            registroPgh.setIdSupervisoraEmpresa(mdiSupervisoraEmpresa);
	        }
	        if(registro.getExpediente()!=null && registro.getExpediente().getIdExpediente()!=null){
	            PghExpediente pghExpediente = new PghExpediente();
	            pghExpediente.setIdExpediente(registro.getExpediente().getIdExpediente());	            
	            registroPgh.setIdExpediente(pghExpediente);	            
	        }
	        registroPgh.setEstado(registro.getEstado());
            registroPgh.setFechaFiscalizacion(registro.getFechaFiscalizacion());
            registroPgh.setIdDepartPlazoDescargo(registro.getIdDepartPlazoDescargo());
            registroPgh.setFlagConfirmaTipoAsignacion(registro.getFlagConfirmaTipoAsignacion());
        }
        return registroPgh;
    }
    /* OSINE_SFS-791 - RSIS 33 - Fin */
}
