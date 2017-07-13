/**
*
* Resumen		
* Objeto		: DetalleSupervisionBuilder.java
* Descripción		: Constructor DetalleSupervision
* Fecha de Creación	: 01/09/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-791  |  02/09/2016   |   Luis García Reyna          |     Ejecucion Medida - Listar Obligaciones
* OSINE_SFS-791  |  11/10/2016   |   Mario Dioses Fernandez     |     Crear la tarea automática que notifique vía correo que se debe elaborar el oficio por obligaciones incumplidas sin subsanar
* OSINE_SFS-791  |  11/10/2016   |   Mario Dioses Fernandez     |     Crear la tarea automática que cancele el registro de hidrocarburos
* OSINE_SFS-791  |  12/10/2016   |   Luis García Reyna          |     Terminar Supervision - Listar Infracciones No Subsanadas
* 
*/

package gob.osinergmin.inpsweb.domain.builder;
import gob.osinergmin.inpsweb.domain.PghCriterio;
import gob.osinergmin.inpsweb.domain.PghDetalleSupervision;
import gob.osinergmin.inpsweb.domain.PghInfraccion;
import gob.osinergmin.inpsweb.domain.PghObligacion;
import gob.osinergmin.inpsweb.domain.PghResultadoSupervision;
import gob.osinergmin.inpsweb.domain.PghSupervision;
import gob.osinergmin.inpsweb.domain.PghTipificacion;
import gob.osinergmin.mdicommon.domain.dto.CriterioDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteTareaDTO;
import gob.osinergmin.mdicommon.domain.dto.InfraccionDTO;
import gob.osinergmin.mdicommon.domain.dto.ObligacionDTO;
import gob.osinergmin.mdicommon.domain.dto.OrdenServicioDTO;
import gob.osinergmin.mdicommon.domain.dto.PersonalDTO;
import gob.osinergmin.mdicommon.domain.dto.ResultadoSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.TipificacionDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadSupervisadaDTO;

import java.util.ArrayList;
import java.util.List;

public class DetalleSupervisionBuilder {

    public static PghDetalleSupervision toDetalleSupervisionDomain(DetalleSupervisionDTO registro) {
        PghDetalleSupervision registroDomain = new PghDetalleSupervision();
        if (registro != null) {
            if (registro.getIdDetalleSupervision() != null) {
                registroDomain.setIdDetalleSupervision(registro.getIdDetalleSupervision());
            }
            if (registro.getIdMedidaSeguridad()!= null) {
                registroDomain.setIdMedidaSeguridad(registro.getIdMedidaSeguridad());
            }
            if(registro.getInfraccion()!=null && registro.getInfraccion().getIdInfraccion()!=null){
                registroDomain.setIdInfraccion(new PghInfraccion(registro.getInfraccion().getIdInfraccion()));
            }
            registroDomain.setFlagResultado(registro.getFlagResultado());
            registroDomain.setDescripcionResultado(registro.getDescripcionResultado());
            registroDomain.setFlagRegistrado(registro.getFlagRegistrado());
            registroDomain.setIdDetalleSupervisionAnt(registro.getIdDetalleSupervisionAnt());
            registroDomain.setEstado(registro.getEstado());
            if (registro.getSupervision() != null && registro.getSupervision().getIdSupervision() != null) {
                registroDomain.setIdSupervision(new PghSupervision(registro.getSupervision().getIdSupervision()));
            }
            if (registro.getObligacion() != null && registro.getObligacion().getIdObligacion() != null) {
                registroDomain.setIdObligacion(new PghObligacion(registro.getObligacion().getIdObligacion()));
            }
            if (registro.getTipificacion() != null && registro.getTipificacion().getIdTipificacion() != null) {
                registroDomain.setIdTipificacion(new PghTipificacion(registro.getTipificacion().getIdTipificacion()));
            }
            if (registro.getCriterio() != null && registro.getCriterio().getIdCriterio() != null) {
                registroDomain.setIdCriterio(new PghCriterio(registro.getCriterio().getIdCriterio()));
            }
            if (registro.getResultadoSupervision() != null && registro.getResultadoSupervision().getIdResultadosupervision() != null) {
                registroDomain.setIdResultadoSupervision(new PghResultadoSupervision(registro.getResultadoSupervision().getIdResultadosupervision()));
            }
            if (registro.getResultadoSupervisionAnt()!= null && registro.getResultadoSupervisionAnt().getIdResultadosupervision() != null) {
                registroDomain.setIdResultadoSupervisionAnt(new PghResultadoSupervision(registro.getResultadoSupervisionAnt().getIdResultadosupervision()));
            }
            if (registro.getPrioridad() != null) {
                registroDomain.setPrioridad(registro.getPrioridad());
            }
        }
        return registroDomain;
    }

    public static DetalleSupervisionDTO toDetalleSupervisionDTO(PghDetalleSupervision registro) {
        DetalleSupervisionDTO registroDTO = new DetalleSupervisionDTO();
        if (registro != null) {
            registroDTO.setIdDetalleSupervision(registro.getIdDetalleSupervision());
            registroDTO.setFlagResultado(registro.getFlagResultado());
            registroDTO.setDescripcionResultado(registro.getDescripcionResultado());
            registroDTO.setFlagRegistrado(registro.getFlagRegistrado());
            registroDTO.setIdDetalleSupervisionAnt(registro.getIdDetalleSupervisionAnt());
            /* OSINE_SFS-791 - mdiosesf - Inicio */ 
            if(registro.getFlagMostrarProducto()!=null){
            	registroDTO.setFlagMostrarProducto(registro.getFlagMostrarProducto());
            }
            /* OSINE_SFS-791 - mdiosesf - Inicio */
            if (registro.getIdSupervision() != null && registro.getIdSupervision().getIdSupervision() != null) {
                registroDTO.setSupervision(new SupervisionDTO(registro.getIdSupervision().getIdSupervision()));
            }
            if (registro.getIdObligacion() != null && registro.getIdObligacion().getIdObligacion() != null) {
                ObligacionDTO obligacionDTO = new ObligacionDTO();
                obligacionDTO.setIdObligacion(registro.getIdObligacion().getIdObligacion());
                obligacionDTO.setCodigoObligacion(registro.getIdObligacion().getCodigoObligacion());
                obligacionDTO.setDescripcion(registro.getIdObligacion().getDescripcion());
                
//                if(registro.getIdObligacion().getInfraccion()!=null){
//                    InfraccionDTO infraccion=new InfraccionDTO();
//                    infraccion.setIdInfraccion(registro.getIdObligacion().getInfraccion().getIdInfraccion());
//                    infraccion.setDescripcionInfraccion(registro.getIdObligacion().getInfraccion().getDescripcionInfraccion());
//                    if(registro.getIdObligacion().getInfraccion().getIdDocumentoAdjunto()!=null){
//                        infraccion.setDocumentoAdjunto(new DocumentoAdjuntoDTO(registro.getIdObligacion().getInfraccion().getIdDocumentoAdjunto().getIdDocumentoAdjunto(),registro.getIdObligacion().getInfraccion().getIdDocumentoAdjunto().getNombreArchivo(),registro.getIdObligacion().getInfraccion().getIdDocumentoAdjunto().getRutaAlfresco()));
//                    }
//                    obligacionDTO.setInfraccion(infraccion);
//                }
                registroDTO.setObligacion(obligacionDTO);
            }
            if(registro.getIdInfraccion()!=null){
                InfraccionDTO infraccion=new InfraccionDTO();
                infraccion.setIdInfraccion(registro.getIdInfraccion().getIdInfraccion());
                infraccion.setDescripcionInfraccion(registro.getIdInfraccion().getDescripcionInfraccion());
                if(registro.getIdInfraccion().getIdDocumentoAdjunto()!=null){
                    infraccion.setDocumentoAdjunto(new DocumentoAdjuntoDTO(registro.getIdInfraccion().getIdDocumentoAdjunto().getIdDocumentoAdjunto(),registro.getIdInfraccion().getIdDocumentoAdjunto().getNombreArchivo(),registro.getIdInfraccion().getIdDocumentoAdjunto().getRutaAlfresco()));
                }
                registroDTO.setInfraccion(infraccion);
            }
            if (registro.getIdSupervision()!= null && registro.getIdSupervision().getIdSupervision() != null) {
                SupervisionDTO supervisionDTO = new SupervisionDTO();
                supervisionDTO.setIdSupervision(registro.getIdSupervision().getIdSupervision());               
                /* OSINE_SFS-791 - RSIS 46-47 - Inicio */ 
                if(registro.getIdSupervision().getIdOrdenServicio()!=null && registro.getIdSupervision().getIdOrdenServicio().getIdOrdenServicio()!=null){
                	OrdenServicioDTO ordenServicioDTO = new OrdenServicioDTO();
                	ordenServicioDTO.setIdOrdenServicio(registro.getIdSupervision().getIdOrdenServicio().getIdOrdenServicio());
                	ordenServicioDTO.setNumeroOrdenServicio(registro.getIdSupervision().getIdOrdenServicio().getNumeroOrdenServicio());
                	if(registro.getIdSupervision().getIdOrdenServicio().getIdExpediente()!=null && registro.getIdSupervision().getIdOrdenServicio().getIdExpediente().getIdExpediente()!=null){
                		ExpedienteDTO expedienteDTO = new ExpedienteDTO();
                		expedienteDTO.setIdExpediente(registro.getIdSupervision().getIdOrdenServicio().getIdExpediente().getIdExpediente());
                		expedienteDTO.setNumeroExpediente(registro.getIdSupervision().getIdOrdenServicio().getIdExpediente().getNumeroExpediente());
                		if(registro.getIdSupervision().getIdOrdenServicio().getIdExpediente().getIdPersonal()!=null && registro.getIdSupervision().getIdOrdenServicio().getIdExpediente().getIdPersonal().getIdPersonal()!=null){
                			PersonalDTO personal=new PersonalDTO();
                			personal.setIdPersonal(registro.getIdSupervision().getIdOrdenServicio().getIdExpediente().getIdPersonal().getIdPersonal());
                			if(registro.getIdSupervision().getIdOrdenServicio().getIdExpediente().getIdPersonal().getNombreUsuarioSiged()!=null){
                				personal.setNombreUsuarioSiged(registro.getIdSupervision().getIdOrdenServicio().getIdExpediente().getIdPersonal().getNombreUsuarioSiged());
                			}
                			if(registro.getIdSupervision().getIdOrdenServicio().getIdExpediente().getIdPersonal().getIdPersonalSiged()!=null){
                				personal.setIdPersonalSiged(registro.getIdSupervision().getIdOrdenServicio().getIdExpediente().getIdPersonal().getIdPersonalSiged());
                			}
                			expedienteDTO.setPersonal(personal);
                		} 
                		if(registro.getIdSupervision().getIdOrdenServicio().getIdExpediente().getPghExpedienteTarea()!=null && registro.getIdSupervision().getIdOrdenServicio().getIdExpediente().getPghExpedienteTarea().getIdExpediente()!=null){
                			ExpedienteTareaDTO expedienteTarea=new ExpedienteTareaDTO();
                			expedienteTarea.setIdExpediente(registro.getIdSupervision().getIdOrdenServicio().getIdExpediente().getPghExpedienteTarea().getIdExpediente());
                			expedienteTarea.setFlagCorreoOficio(registro.getIdSupervision().getIdOrdenServicio().getIdExpediente().getPghExpedienteTarea().getFlagCorreoOficio());
                			expedienteTarea.setFlagEstadoReghMsfh(registro.getIdSupervision().getIdOrdenServicio().getIdExpediente().getPghExpedienteTarea().getFlagEstadoReghMsfh());
                			expedienteTarea.setFlagEstadoReghInps(registro.getIdSupervision().getIdOrdenServicio().getIdExpediente().getPghExpedienteTarea().getFlagEstadoReghInps());
                			expedienteTarea.setFlagEnviarConstanciaSiged(registro.getIdSupervision().getIdOrdenServicio().getIdExpediente().getPghExpedienteTarea().getFlagEnviarConstanciaSiged());
                			expedienteTarea.setFlagEstadoSiged(registro.getIdSupervision().getIdOrdenServicio().getIdExpediente().getPghExpedienteTarea().getFlagEstadoSiged());
                			expedienteTarea.setFlagCorreoEstadoRegh(registro.getIdSupervision().getIdOrdenServicio().getIdExpediente().getPghExpedienteTarea().getFlagCorreoEstadoRegh());
                			expedienteTarea.setFlagRegistraDocsInps(registro.getIdSupervision().getIdOrdenServicio().getIdExpediente().getPghExpedienteTarea().getFlagRegistraDocsInps());
                			expedienteTarea.setFlagCorreoScop(registro.getIdSupervision().getIdOrdenServicio().getIdExpediente().getPghExpedienteTarea().getFlagCorreoScop());
                			expedienteDTO.setExpedienteTareaDTO(expedienteTarea);
                		}                		
                		if(registro.getIdSupervision().getIdOrdenServicio().getIdExpediente().getIdUnidadSupervisada()!=null && registro.getIdSupervision().getIdOrdenServicio().getIdExpediente().getIdUnidadSupervisada().getIdUnidadSupervisada()!=null){
                			UnidadSupervisadaDTO UnidadSupervisadaDTO = new UnidadSupervisadaDTO();
                			UnidadSupervisadaDTO.setIdUnidadSupervisada(registro.getIdSupervision().getIdOrdenServicio().getIdExpediente().getIdUnidadSupervisada().getIdUnidadSupervisada());
                			if(registro.getIdSupervision().getIdOrdenServicio().getIdExpediente().getIdUnidadSupervisada()!=null && registro.getIdSupervision().getIdOrdenServicio().getIdExpediente().getIdUnidadSupervisada().getNumeroRegistroHidrocarburo()!=null){
                				UnidadSupervisadaDTO.setNroRegistroHidrocarburo(registro.getIdSupervision().getIdOrdenServicio().getIdExpediente().getIdUnidadSupervisada().getNumeroRegistroHidrocarburo());
                			}
                			if(registro.getIdSupervision().getIdOrdenServicio().getIdExpediente().getIdUnidadSupervisada()!=null && registro.getIdSupervision().getIdOrdenServicio().getIdExpediente().getIdUnidadSupervisada().getCodigoOsinergmin()!=null){
                				UnidadSupervisadaDTO.setCodigoOsinergmin(registro.getIdSupervision().getIdOrdenServicio().getIdExpediente().getIdUnidadSupervisada().getCodigoOsinergmin());
                			}
                			expedienteDTO.setUnidadSupervisada(UnidadSupervisadaDTO);
                		}                			
                		ordenServicioDTO.setExpediente(expedienteDTO);
                	}                	
                	supervisionDTO.setOrdenServicioDTO(ordenServicioDTO);
                }
                /* OSINE_SFS-791 - RSIS 46-47 - Inicio */ 
                registroDTO.setSupervision(supervisionDTO);
            }
            if (registro.getIdTipificacion() != null && registro.getIdTipificacion().getIdTipificacion() != null) {
                TipificacionDTO tipificacionDTO = new TipificacionDTO();
                tipificacionDTO.setIdTipificacion(registro.getIdTipificacion().getIdTipificacion());
                registroDTO.setTipificacion(tipificacionDTO);
            }
            if (registro.getIdCriterio() != null && registro.getIdCriterio().getIdCriterio() != null) {
                CriterioDTO criterioDTO = new CriterioDTO();
                criterioDTO.setIdCriterio(registro.getIdCriterio().getIdCriterio());
                registroDTO.setCriterio(criterioDTO);
            }            
            
            if (registro.getIdResultadoSupervision() != null && registro.getIdResultadoSupervision().getIdResultadoSupervision() != null) {
                ResultadoSupervisionDTO resultado = new ResultadoSupervisionDTO();
                resultado.setIdResultadosupervision(registro.getIdResultadoSupervision().getIdResultadoSupervision());
                resultado.setCodigo(registro.getIdResultadoSupervision().getCodigo());
                registroDTO.setResultadoSupervision(resultado);
            }
            if (registro.getIdMedidaSeguridad()!= null) {
                registroDTO.setIdMedidaSeguridad(registro.getIdMedidaSeguridad());
            }
            
            
            /* OSINE_SFS-791 - RSIS 16 - Inicio */
            if (registro.getPrioridad() != null) {
                registroDTO.setPrioridad(registro.getPrioridad());
            }
            if (registro.getComentario()!= null) {
                registroDTO.setComentario(registro.getComentario());
            }
            if(registro.getIdResultadoSupervisionAnt()!= null && registro.getIdResultadoSupervisionAnt().getIdResultadoSupervision()!= null){
                ResultadoSupervisionDTO resultadoAnt = new ResultadoSupervisionDTO();
                resultadoAnt.setIdResultadosupervision(registro.getIdResultadoSupervisionAnt().getIdResultadoSupervision());
                resultadoAnt.setCodigo(registro.getIdResultadoSupervisionAnt().getCodigo());                
                registroDTO.setResultadoSupervisionAnt(resultadoAnt);
            }
            registroDTO.setCountEscIncumplido(registro.getCountEscIncumplido());
            registroDTO.setCountComentarioDetSupervision(registro.getCountComentarioDetSupervision());
            /* OSINE_SFS-791 - RSIS 16 - Fin */
            /* OSINE_SFS-791 - RSIS 39 - Inicio */
            registroDTO.setCountEscIncumplidoAnt(registro.getCountEscIncumplidoAnt());
            /* OSINE_SFS-791 - RSIS 39 - Fin */
        }
        return registroDTO;
    }

    public static List<DetalleSupervisionDTO> toListDetalleSupervisionDto(List<PghDetalleSupervision> lista) {
        DetalleSupervisionDTO registroDTO;
        List<DetalleSupervisionDTO> retorno = new ArrayList<DetalleSupervisionDTO>();
        if (lista != null) {
            for (PghDetalleSupervision maestro : lista) {
                registroDTO = toDetalleSupervisionDTO(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }
   
  
}
