/**
* Resumen		
* Objeto		: OrdenServicioController.java
* Descripción		: Controla el flujo de datos del objeto OrdenServicio
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     09/05/2016      Mario Dioses Fernandez      Registrar en BD campo FECHA_INICIO_PLAZO y FECHA_FIN_PLAZO luego de CONCLUIR_OS, considerando Plazo_Descargo por Rubro (MDI_ACTIVIDAD)
* OSINE_SFS-480     23/05/2016      Luis García Reyna           Implementar la funcionalidad de devolución de asignaciones de acuerdo a especificaciones
* OSINE_SFS-480     23/05/2016      Hernán Torres Saenz         Implementar la funcionalidad de anular orden de servicio de acuerdo a especificaciones.
* OSINE791-RSIS21   29/08/2016      Alexander Vilca Narvaez     Implementar la funcionalidad de archivar para el flujo DSR
* OSINE_SFS-791-RSIS48  |   21/10/2016   |   Luis García Reyna   |  Registrar Detalle Levantamiento
* OSINE791–RSIS40   | 11/10/2016 | Zosimo Chaupis Santur | Crear la funcionalidad para generar los resultados de una supervisión de orden de levantamiento DSR-CRITICIDAD la cual tenga todas sus obligaciones incumplidas subsanadas. 
  
*/ 

package gob.osinergmin.inpsweb.controller;
import gob.osinergmin.inpsweb.service.business.ExpedienteServiceNeg;
import gob.osinergmin.inpsweb.service.business.HistoricoEstadoServiceNeg;
import gob.osinergmin.inpsweb.service.business.MaestroColumnaServiceNeg;
import gob.osinergmin.inpsweb.service.business.OrdenServicioServiceNeg;
// htorress - RSIS 18 - Inicio
import gob.osinergmin.inpsweb.service.business.PersonalAsignadoServiceNeg;
import gob.osinergmin.mdicommon.domain.dto.PersonalAsignadoDTO;
import gob.osinergmin.inpsweb.service.business.PeriodoMedidaSeguridadServiceNeg;
// htorress - RSIS 18 - Fin
import gob.osinergmin.inpsweb.service.business.PersonalServiceNeg;
import gob.osinergmin.inpsweb.service.business.SupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.exception.OrdenServicioException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.ConstantesWeb;
import gob.osinergmin.inpsweb.util.StringUtil;
import gob.osinergmin.inpsweb.util.Utiles;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.HistoricoEstadoDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.OrdenServicioDTO;
import gob.osinergmin.mdicommon.domain.dto.PeriodoMedidaSeguridadDTO;
import gob.osinergmin.mdicommon.domain.dto.PersonalDTO;
import gob.osinergmin.mdicommon.domain.ui.HistoricoEstadoFilter;
import gob.osinergmin.mdicommon.domain.ui.OrdenServicioFilter;
import gob.osinergmin.mdicommon.domain.ui.PeriodoMedidaSeguridadFilter;
import gob.osinergmin.mdicommon.domain.ui.PersonalFilter;
import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.cxf.common.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author jpiro
 */
@Controller
@RequestMapping("/ordenServicio")
public class OrdenServicioController {
    private static final Logger LOG = LoggerFactory.getLogger(OrdenServicioController.class);
    @Inject
    OrdenServicioServiceNeg ordenServicioServiceNeg;
    @Inject
    HistoricoEstadoServiceNeg historicoEstadoServiceNeg;
    @Inject
    private SupervisionServiceNeg supervisionServiceNeg;
    // htorress - RSIS 18 - Inicio
    @Inject
    private ExpedienteServiceNeg expedienteServiceNeg;
    @Inject
    private PersonalServiceNeg personalServiceNeg;
    @Inject
    private PersonalAsignadoServiceNeg personalAsignadoServiceNeg;
    // htorress - RSIS 18 - Fin
    /* OSINE_SFS-791 - RSIS 48 - Inicio */  
    /* OSINE_SFS-791 - RSIS 48 - Fin */
    /* OSINE_SFS-791 - RSIS 40 - Inicio */  
    @Inject
    private PeriodoMedidaSeguridadServiceNeg periodoMedidaSeguridadServiceNeg;
    /* OSINE_SFS-791 - RSIS 40 - Fin */
    @Inject
    private MaestroColumnaServiceNeg maestroColumnaServiceNeg;    
    
    @RequestMapping(value="/atender",method= RequestMethod.GET)
    public @ResponseBody Map<String,Object> atenderOrdenServicio(ExpedienteDTO expedienteDTO,Long idPersonalOri,String flagSupervision,Long nroIteracion,HttpSession session,HttpServletRequest request){
        LOG.info("atender");
        Map<String,Object> retorno=new HashMap<String,Object>();
    	try{
            String codFlujoSup=supervisionServiceNeg.buscarCodigoFlujoSupervINPS(
                    expedienteDTO.getObligacionTipo().getIdObligacionTipo(), 
                    expedienteDTO.getUnidadSupervisada().getActividad().getIdActividad(), 
                    expedienteDTO.getProceso().getIdProceso());   
            if(!StringUtil.isEmpty(codFlujoSup) && codFlujoSup.equals(Constantes.CODIGO_FLUJO_SUPERV_INPS_DSR_CRI)){
                LOG.info("-->codFlujoSup "+Constantes.CODIGO_FLUJO_SUPERV_INPS_DSR_CRI);
                retorno=atenderDsr(expedienteDTO,idPersonalOri,flagSupervision,nroIteracion,request);                                
            }else{
                LOG.info("-->codFlujoSup Default");
                retorno=atenderDefault(expedienteDTO,idPersonalOri,flagSupervision,request);
            }
        }catch(Exception e){
            LOG.error("Error en atender",e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }
    
    public Map<String,Object> atenderDefault(ExpedienteDTO expedienteDTO,Long idPersonalOri,String flagSupervision,HttpServletRequest request){
        LOG.info("atenderDefault");
        Map<String,Object> retorno=new HashMap<String,Object>();
    	try{
            Long idExpediente=expedienteDTO.getIdExpediente();
            Long idOrdenServicio=expedienteDTO.getOrdenServicio().getIdOrdenServicio();
            //enviar archivos a Siged segun expediente
            // htorress - RSIS 8 - Inicio
            /*
            DocumentoAdjuntoFilter filtro=new DocumentoAdjuntoFilter();
            filtro.setIdOrdenServicio(idOrdenServicio);
            List<DocumentoAdjuntoDTO> listDocEnviarSiged=documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(filtro);
            boolean flgEnvioDocSiged=true;
            if(listDocEnviarSiged!=null && listDocEnviarSiged.size()>0){
                for(DocumentoAdjuntoDTO doc : listDocEnviarSiged){
                    DocumentoAdjuntoDTO docEnviado=documentoAdjuntoServiceNeg.enviarPghDocOrdenServicioSiged(doc,expedienteDTO,Constantes.getIDPERSONALSIGED(request));
                    //temporal - inicio por servicios Siged
//                    DocumentoAdjuntoDTO docEnviado=new DocumentoAdjuntoDTO();
                    //temporal - fin
                    if(docEnviado==null){flgEnvioDocSiged=false;break;}
                }
            }
            //
            if(flgEnvioDocSiged){
            */	
            // htorress - RSIS 8 - Fin
            // htorress - RSIS 18 - Inicio
        	PersonalDTO destinatario=(PersonalDTO)obtenerUsuarioOrigen(idOrdenServicio);
        	
        	/* OSINE_SFS-Ajustes - mdiosesf - Inicio */
        	//Ajuste al reenviar expediente SIGED >> dominio COM_REEN_EXP_SIGED.
        	List<MaestroColumnaDTO> listComRenExpSiged=maestroColumnaServiceNeg.findByDominioAplicacionCodigo(Constantes.DOMINIO_COM_REEN_EXP_SIGED, Constantes.APLICACION_INPS, Constantes.CODIGO_COM_REEN_EXP_SIGED_ATE_OS_DEFAULT);
	        String comRenExpSiged=(!CollectionUtils.isEmpty(listComRenExpSiged)) ? listComRenExpSiged.get(0).getDescripcion().replace(Constantes.VARIABLE_TEXT_REEN_EXP_SIG, expedienteDTO.getNumeroExpediente()) : "--";
	        comRenExpSiged=(expedienteDTO.getOrdenServicio()!=null && expedienteDTO.getOrdenServicio().getNumeroOrdenServicio()!=null) ? comRenExpSiged.replace(Constantes.VARIABLE_TEXT_REEN_ORD_SIG, expedienteDTO.getOrdenServicio().getNumeroOrdenServicio()) : comRenExpSiged;
	        
	        ExpedienteDTO expedienteReenviarSiged=expedienteServiceNeg.reenviarExpedienteSIGED(expedienteDTO, destinatario.getIdPersonalSiged(), comRenExpSiged, true);
            //ExpedienteDTO expedienteReenviarSiged=expedienteServiceNeg.reenviarExpedienteSIGED(expedienteDTO, destinatario.getIdPersonalSiged(), "Reenvio para Aprobar", true);
	        /* OSINE_SFS-Ajustes - mdiosesf - Fin */
            if(expedienteReenviarSiged.getNumeroExpediente()!=null){     
             // htorress - RSIS 18 - Fin
            	OrdenServicioDTO respuesta=ordenServicioServiceNeg.atender(idExpediente,idOrdenServicio,idPersonalOri,flagSupervision,Constantes.getUsuarioDTO(request));
            	retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
                retorno.put(ConstantesWeb.VV_MENSAJE, "Cambios realizados");
                retorno.put("expediente",respuesta);
            // htorress - RSIS 8 - Inicio
            }else{
                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                retorno.put(ConstantesWeb.VV_MENSAJE, expedienteReenviarSiged.getMensajeServicio());
            }   
    		// htorress - RSIS 8 - Fin
        }catch(Exception e){
            LOG.error("Error en atenderDefault",e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }
    
    public Map<String,Object> atenderDsr(ExpedienteDTO expedienteDTO,Long idPersonalOri,String flagSupervision,Long nroIteracion,HttpServletRequest request){
        LOG.info("atenderDsr");
        Map<String,Object> retorno=new HashMap<String,Object>();
    	try{
            Long idOrdenServicio=expedienteDTO.getOrdenServicio().getIdOrdenServicio();
            
            PersonalDTO destinatario=(PersonalDTO)obtenerUsuarioOrigen(idOrdenServicio);   
            /* OSINE_SFS-791 - RSIS 42 - Inicio */ 
            if (destinatario!=null ){
            /* OSINE_SFS-791 - RSIS 42 - Fin */ 
            	/* OSINE_SFS-Ajustes - mdiosesf - Inicio */
            	//Ajuste al reenviar expediente SIGED >> dominio COM_REEN_EXP_SIGED.
            	List<MaestroColumnaDTO> listComRenExpSiged=maestroColumnaServiceNeg.findByDominioAplicacionCodigo(Constantes.DOMINIO_COM_REEN_EXP_SIGED, Constantes.APLICACION_INPS, Constantes.CODIGO_COM_REEN_EXP_SIGED_ATE_OS_DSR);
    	        String comRenExpSiged=(!CollectionUtils.isEmpty(listComRenExpSiged)) ? listComRenExpSiged.get(0).getDescripcion().replace(Constantes.VARIABLE_TEXT_REEN_EXP_SIG, expedienteDTO.getNumeroExpediente()) : "--";
    	        comRenExpSiged=(expedienteDTO.getOrdenServicio()!=null && expedienteDTO.getOrdenServicio().getNumeroOrdenServicio()!=null) ? comRenExpSiged.replace(Constantes.VARIABLE_TEXT_REEN_ORD_SIG, expedienteDTO.getOrdenServicio().getNumeroOrdenServicio()) : comRenExpSiged;
    	        
    	        ExpedienteDTO expedienteReenviarSiged=expedienteServiceNeg.reenviarExpedienteSIGED(expedienteDTO, destinatario.getIdPersonalSiged(), comRenExpSiged, true);
                //ExpedienteDTO expedienteReenviarSiged=expedienteServiceNeg.reenviarExpedienteSIGED(expedienteDTO, destinatario.getIdPersonalSiged(), "Reenvio para Aprobar", true);
    	        /* OSINE_SFS-Ajustes - mdiosesf - Fin */
                if(expedienteReenviarSiged.getNumeroExpediente()!=null){     
                	OrdenServicioDTO respuesta=ordenServicioServiceNeg.atenderDsr(expedienteDTO,idPersonalOri,flagSupervision,nroIteracion,Constantes.getUsuarioDTO(request));
                    retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
                    retorno.put(ConstantesWeb.VV_MENSAJE, "Cambios realizados");
                    retorno.put("expediente",respuesta);
                }else{
                    retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                    retorno.put(ConstantesWeb.VV_MENSAJE, expedienteReenviarSiged.getMensajeServicio());
                } 
            /* OSINE_SFS-791 - RSIS 42 - Inicio */ 
            }else{
                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_SIN_DESTINATARIO);
            } 
            /* OSINE_SFS-791 - RSIS 42 - Fin */ 	
    	}catch(Exception e){
            LOG.error("Error en atenderDsr",e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
        
    }
    
    @RequestMapping(value="/revisar",method= RequestMethod.GET)
    // htorress - RSIS 18 - Inicio
    //public @ResponseBody Map<String,Object> revisarOrdenServicio(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,HttpSession session,HttpServletRequest request){
    public @ResponseBody Map<String,Object> revisarOrdenServicio(Long idExpediente,Long idOrdenServicio,Long idPersonalOri, Long numeroExpediente, String asuntoSiged,HttpSession session,HttpServletRequest request){	
    // htorress - RSIS 18 - Fin
        LOG.info("revisar");
        Map<String,Object> retorno=new HashMap<String,Object>();
    	try{
            
            // htorress - RSIS 18 - Inicio
    		Long idJefe=0L;
    		Long idPersonalDest=0L;
    		if(Constantes.getIDENTIFICADORROL(request).equals(Constantes.CONSTANTE_ROL_ESPECIALISTA) ||
    		   Constantes.getIDENTIFICADORROL(request).equals(Constantes.CONSTANTE_ROL_SUPERVISOR_REGIONAL)){
    			
	    		PersonalAsignadoDTO personal = new PersonalAsignadoDTO();
	        	personal.setIdPersonalSubordinado(new PersonalDTO());
	        	personal.setIdPersonalJefe(new PersonalDTO());
	        	personal.getIdPersonalSubordinado().setIdPersonal(idPersonalOri);
	        	List<PersonalAsignadoDTO> listPersona=personalAsignadoServiceNeg.findPersonalAsignado(personal);
	        	if(listPersona != null && listPersona.size()>0){
	    		idJefe = listPersona.get(0).getIdPersonalJefe().getIdPersonalSiged();
	    		idPersonalDest= listPersona.get(0).getIdPersonalJefe().getIdPersonal();
	        	}else{
	        	       throw new OrdenServicioException("Se requiere configuracion previa: Usuario no tiene Jefe Asignado solicitar configuracion al servicio tecnico ", null); 
	        	}
    		}else if(Constantes.getIDENTIFICADORROL(request).equals(Constantes.CONSTANTE_ROL_JEFE_REGIONAL) ||
    				Constantes.getIDENTIFICADORROL(request).equals(Constantes.CONSTANTE_ROL_JEFE_UNIDAD)){
    			
    			idJefe=Constantes.getIDPERSONALSIGED(request);
    			idPersonalDest = Constantes.getIDPERSONAL(request);
    		}
            ExpedienteDTO expedienteDTO = new ExpedienteDTO();
            expedienteDTO.setNumeroExpediente(String.valueOf(numeroExpediente));
            expedienteDTO.setAsuntoSiged(asuntoSiged);

            /* OSINE_SFS-Ajustes - mdiosesf - Inicio */
            //Ajuste al reenviar expediente SIGED >> dominio COM_REEN_EXP_SIGED.            
            List<OrdenServicioDTO> listOrdenServicio = ordenServicioServiceNeg.findByFilter(new OrdenServicioFilter(idOrdenServicio));
        	List<MaestroColumnaDTO> listComRenExpSiged=maestroColumnaServiceNeg.findByDominioAplicacionCodigo(Constantes.DOMINIO_COM_REEN_EXP_SIGED, Constantes.APLICACION_INPS, Constantes.CODIGO_COM_REEN_EXP_SIGED_REV_OS);
	        String comRenExpSiged=(!CollectionUtils.isEmpty(listComRenExpSiged)) ? listComRenExpSiged.get(0).getDescripcion().replace(Constantes.VARIABLE_TEXT_REEN_EXP_SIG, expedienteDTO.getNumeroExpediente()) : "--";
	        comRenExpSiged=(!CollectionUtils.isEmpty(listOrdenServicio)) ? comRenExpSiged.replace(Constantes.VARIABLE_TEXT_REEN_ORD_SIG, listOrdenServicio.get(0).getNumeroOrdenServicio()) : comRenExpSiged;
            
	        ExpedienteDTO expedienteAprobar=expedienteServiceNeg.aprobarExpedienteSIGED(expedienteDTO, idJefe, comRenExpSiged, true);
            //ExpedienteDTO expedienteAprobar=expedienteServiceNeg.aprobarExpedienteSIGED(expedienteDTO, idJefe, "Aprobar", true);
	        /* OSINE_SFS-Ajustes - mdiosesf - Fin */
            if(expedienteAprobar.getNumeroExpediente()!=null){
	            //ExpedienteDTO expedienteReenviarSiged=expedienteServiceNeg.reenviarExpedienteSIGED(expedienteDTO, idJefe, "Prueba de Contenido Reenvio Revisado Esp a Jefe.", true);
		        //if(expedienteReenviarSiged.getNumeroExpediente()!=null){     
		        	//OrdenServicioDTO respuesta=ordenServicioServiceNeg.revisar(idExpediente,idOrdenServicio,idPersonalOri,Constantes.getUsuarioDTO(request));
            		OrdenServicioDTO respuesta=ordenServicioServiceNeg.revisar(idExpediente,idOrdenServicio,idPersonalOri,idPersonalDest,Constantes.getUsuarioDTO(request));
		        // htorress - RSIS 18 - Fin
		            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
		            retorno.put(ConstantesWeb.VV_MENSAJE, "Cambios realizados");
		            retorno.put("expediente",respuesta);
		        // htorress - RSIS 18 - Inicio
		        /*}else{
		        	LOG.error("Error en revisar");
	                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
	                retorno.put(ConstantesWeb.VV_MENSAJE, expedienteReenviarSiged.getMensajeServicio());
		        }*/
            }else{
                //como fallo el aprobar, seria porque el Jefe Unidad le hizo DEVOLVER
                //entonces usamos el reenviar para aprobar, que es el mismo de atender del supervisor
            	
            	ExpedienteDTO expedienteReenviarSiged=expedienteServiceNeg.reenviarExpedienteSIGED(expedienteDTO, idJefe, comRenExpSiged, true);
                //ExpedienteDTO expedienteReenviarSiged=expedienteServiceNeg.reenviarExpedienteSIGED(expedienteDTO, idJefe, "Reenvio para Aprobar", true);
                if(expedienteReenviarSiged.getNumeroExpediente()!=null){ 
                    OrdenServicioDTO respuesta=ordenServicioServiceNeg.revisar(idExpediente,idOrdenServicio,idPersonalOri,idPersonalDest,Constantes.getUsuarioDTO(request));
                    retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
                    retorno.put(ConstantesWeb.VV_MENSAJE, "Cambios realizados");
                    retorno.put("expediente",respuesta);
                }else{
                    LOG.error("Error en aprobar para su revisión");
                    retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                    retorno.put(ConstantesWeb.VV_MENSAJE, expedienteAprobar.getMensajeServicio());
                }   
            }
            // htorress - RSIS 18 - Fin
        }catch(Exception e){
            LOG.error("Error en derivar",e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }
    
    @RequestMapping(value = "/aprobar", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> aprobarOrdenServicio(OrdenServicioDTO ordenServicioDTO, Long idPersonalOri, String motivoReasignacion, HttpSession session, HttpServletRequest request) {
        LOG.info("aprobar");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            String codFlujoSup = supervisionServiceNeg.buscarCodigoFlujoSupervINPS(
                    ordenServicioDTO.getExpediente().getObligacionTipo().getIdObligacionTipo(),
                    ordenServicioDTO.getExpediente().getUnidadSupervisada().getActividad().getIdActividad(),
                    ordenServicioDTO.getExpediente().getProceso().getIdProceso());
            if (!StringUtil.isEmpty(codFlujoSup) && codFlujoSup.equals(Constantes.CODIGO_FLUJO_SUPERV_INPS_DSR_CRI)) {
                LOG.info("-->codFlujoSup " + Constantes.CODIGO_FLUJO_SUPERV_INPS_DSR_CRI);
                /*OSINE_SFS-791 - RSIS 40 - Inicio */
                PeriodoMedidaSeguridadFilter periodoFilter = new PeriodoMedidaSeguridadFilter();
                periodoFilter.setIdexpediente(ordenServicioDTO.getExpediente().getIdExpediente());
                List<PeriodoMedidaSeguridadDTO> ltaperiodo = periodoMedidaSeguridadServiceNeg.getListPeriodoMedidaSeguridad(periodoFilter);
                LOG.info("la lista de periodo medida tiene |"+ltaperiodo.size()+"|");
                if (ltaperiodo != null && ltaperiodo.size() > Constantes.LISTA_VACIA) {
                    PeriodoMedidaSeguridadDTO periodoDTO = new PeriodoMedidaSeguridadDTO();
                    periodoDTO = ltaperiodo.get(Constantes.PRIMERO_EN_LISTA);
                    Date fecha = new Date();
                    String fechaActual = Utiles.DateToString(fecha, Constantes.FORMATO_FECHA_CORTA);
                    String fechaplaneadafin = Utiles.DateToString(periodoDTO.getFechaFinPlaneado(), Constantes.FORMATO_FECHA_CORTA);
                    SimpleDateFormat formateador = new SimpleDateFormat(Constantes.FORMATO_FECHA_CORTA);
                    Date fechaSistema = formateador.parse(fechaActual);
                    Date fechaFinPlaneado = formateador.parse(fechaplaneadafin);
                    if (periodoDTO.getFlagActualizarAuto() == null || periodoDTO.getFlagActualizarAuto().equals("")) {
                        retorno = expedienteServiceNeg.aprobarOrdenServicioDsrCri(ordenServicioDTO.getExpediente().getIdExpediente(), ordenServicioDTO.getIdOrdenServicio(), idPersonalOri, ordenServicioDTO.getExpediente().getNumeroExpediente(), ordenServicioDTO.getExpediente().getAsuntoSiged(), request);
                    } else {
                        if (!fechaSistema.after(fechaFinPlaneado) && periodoDTO.getFlagActualizarAuto() != null) {
                            LOG.error("La orden de servicio aun no cumple con el periodo de medida de seguridad");
                            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                            retorno.put(ConstantesWeb.VV_MENSAJE, "La orden de servicio a&uacute;n no cumple con el periodo de medida de seguridad.");
                        }else{
                            if (fechaSistema.after(fechaFinPlaneado) && periodoDTO.getFlagActualizarAuto().equals(Constantes.FLAG_AUTOMATICO_EJECUCION_TAREA_SI)) {
                                LOG.error("Aun no se realizaron las habilitaciones correspondientes para la orden de servicio.");
                                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                                retorno.put(ConstantesWeb.VV_MENSAJE, "A&uacute;n no se realizaron las habilitaciones correspondientes para la orden de servicio.");
                            }else{
                                retorno = expedienteServiceNeg.aprobarOrdenServicioDsrCri(ordenServicioDTO.getExpediente().getIdExpediente(), ordenServicioDTO.getIdOrdenServicio(), idPersonalOri, ordenServicioDTO.getExpediente().getNumeroExpediente(), ordenServicioDTO.getExpediente().getAsuntoSiged(), request);
                            }
                        }
                    }
                } else {
                    retorno = expedienteServiceNeg.aprobarOrdenServicioDsrCri(ordenServicioDTO.getExpediente().getIdExpediente(), ordenServicioDTO.getIdOrdenServicio(), idPersonalOri, ordenServicioDTO.getExpediente().getNumeroExpediente(), ordenServicioDTO.getExpediente().getAsuntoSiged(), request);
                }
                /*OSINE_SFS-791 - RSIS 40 - Fin */
            } else {
                LOG.info("-->codFlujoSup Default");
                retorno = aprobarOrdenServicioDefault(ordenServicioDTO.getExpediente().getIdExpediente(), ordenServicioDTO.getIdOrdenServicio(), idPersonalOri, ordenServicioDTO.getExpediente().getNumeroExpediente(), ordenServicioDTO.getExpediente().getAsuntoSiged(), request);
            }
        } catch (Exception e) {
            LOG.error("Error en aprobar", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }
    
    private Map<String,Object> aprobarOrdenServicioDefault(Long idExpediente,Long idOrdenServicio,Long idPersonalOri, String numeroExpediente, String asuntoSiged,HttpServletRequest request){
        Map<String,Object> retorno=new HashMap<String,Object>();
        try{
            // htorress - RSIS 18 - Inicio
            ExpedienteDTO expediente = new ExpedienteDTO();
            expediente.setIdExpediente(idExpediente);
            expediente.setAsuntoSiged(asuntoSiged);
            expediente.setNumeroExpediente(numeroExpediente);

            PersonalDTO destinatario=(PersonalDTO) obtenerUsuarioOrigen(idOrdenServicio);
            
            //Ajuste al reenviar expediente SIGED >> dominio COM_REEN_EXP_SIGED.
            List<OrdenServicioDTO> listOrdenServicio = ordenServicioServiceNeg.findByFilter(new OrdenServicioFilter(idOrdenServicio));
        	List<MaestroColumnaDTO> listComRenExpSiged=maestroColumnaServiceNeg.findByDominioAplicacionCodigo(Constantes.DOMINIO_COM_REEN_EXP_SIGED, Constantes.APLICACION_INPS, Constantes.CODIGO_COM_REEN_EXP_SIGED_APR_OS_DEFAULT);
	        String comRenExpSiged=(!CollectionUtils.isEmpty(listComRenExpSiged)) ? listComRenExpSiged.get(0).getDescripcion().replace(Constantes.VARIABLE_TEXT_REEN_EXP_SIG, expediente.getNumeroExpediente()) : "--";
	        comRenExpSiged=(!CollectionUtils.isEmpty(listOrdenServicio)) ? comRenExpSiged.replace(Constantes.VARIABLE_TEXT_REEN_ORD_SIG, listOrdenServicio.get(0).getNumeroOrdenServicio()) : comRenExpSiged;
            
	        ExpedienteDTO expedienteAprobar=expedienteServiceNeg.aprobarExpedienteSIGED(expediente, destinatario.getIdPersonalSiged(), comRenExpSiged, true);
            //ExpedienteDTO expedienteAprobar=expedienteServiceNeg.aprobarExpedienteSIGED(expediente, destinatario.getIdPersonalSiged(), "Aprobar.", true);
            if(expedienteAprobar.getNumeroExpediente()!=null){ 
            // htorress - RSIS 18 - Fin
                OrdenServicioDTO respuesta=ordenServicioServiceNeg.aprobar(idExpediente,idOrdenServicio,idPersonalOri,Constantes.getUsuarioDTO(request));

                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
                retorno.put(ConstantesWeb.VV_MENSAJE, "Cambios realizados");
                retorno.put("expediente",respuesta);
            // htorress - RSIS 18 - Inicio
            }else{
                LOG.error("Error en aprobar");
                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                retorno.put(ConstantesWeb.VV_MENSAJE, expedienteAprobar.getMensajeServicio());
            }
        }catch(Exception e){
            LOG.error("Error en aprobarOrdenServicioDefault",e);
        }
        // htorress - RSIS 18 - Fin
        return retorno;
    }
//    /* OSINE791 - RSIS21 - Inicio */
//    public OrdenServicioDTO ejecutarOtrasFunciones(String funcion,Long idExpediente,Long idOrdenServicio,Long numeroExpediente,Long idPersonalOri,HttpServletRequest request){
//        LOG.info("ejecutarOtrasFunciones");
//        OrdenServicioDTO retorno = null;        
//        try{
//            if(funcion!=null && funcion.equals(Constantes.ORDEN_SERVICIO_CONCLUIR)){
//                DetalleSupervisionFilter filtro=new DetalleSupervisionFilter();
//                filtro.setIdOrdenServicio(idOrdenServicio);
//                filtro.setCodigoResultadoSupervision(Constantes.CODIGO_RESULTADO_DETSUPERVISION_INCUMPLE);
//                List<DetalleSupervisionDTO> listaIncumplidas=supervisionServiceNeg.findDetalleSupervision(filtro);
//                LOG.info("---------->"+listaIncumplidas);
//                if(listaIncumplidas!=null && listaIncumplidas.size()==0){
//                    ExpedienteDTO expedienteArchivar=expedienteServiceNeg.archivarExpedienteSIGED(String.valueOf(numeroExpediente),"Archivar.");
//                    if(expedienteArchivar.getNumeroExpediente()!=null){
//                        retorno=ordenServicioServiceNeg.concluir(idExpediente,idOrdenServicio,idPersonalOri,Constantes.getUsuarioDTO(request), null, null); 
//                    }
//                }
//            }
//        }catch(Exception e){
//            LOG.error("Error en ejecutarOtrasFunciones",e);
//        }
//        return retorno;
//    }
//    /* OSINE791 - RSIS21 - Fin */
            
    @RequestMapping(value="/notificar",method= RequestMethod.GET)
    public @ResponseBody Map<String,Object> notificarOrdenServicio(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,HttpSession session,HttpServletRequest request){
        LOG.info("notificar");
        Map<String,Object> retorno=new HashMap<String,Object>();
    	try{
            OrdenServicioDTO respuesta=ordenServicioServiceNeg.notificar(idExpediente,idOrdenServicio,idPersonalOri,Constantes.getUsuarioDTO(request));
            
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put(ConstantesWeb.VV_MENSAJE, "Cambios realizados");
            retorno.put("expediente",respuesta);
        }catch(Exception e){
            LOG.error("Error en derivar",e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }
    
    @RequestMapping(value="/concluir",method= RequestMethod.GET)
    /* OSINE_SFS-480 - RSIS 17 - Inicio*/
    public @ResponseBody Map<String,Object> concluirOrdenServicio(Long idExpediente,Long idOrdenServicio,Long idPersonalOri, Long numeroExpediente, Long idArchivo, String fechaInicioPlazoDescargo, HttpSession session,HttpServletRequest request){
    /* OSINE_SFS-480 - RSIS 17 - Fin */
        LOG.info("concluir");
        Map<String,Object> retorno=new HashMap<String,Object>();
    	try{
            // htorress - RSIS 18 - Inicio
            ExpedienteDTO expedienteArchivar=expedienteServiceNeg.archivarExpedienteSIGED(String.valueOf(numeroExpediente),"Archivar.");
            if(expedienteArchivar.getNumeroExpediente()!=null){
            // htorress - RSIS 18 - Fin
                /* OSINE_SFS-480 - RSIS 17 - Inicio */
            	OrdenServicioDTO respuesta=ordenServicioServiceNeg.concluir(idExpediente,idOrdenServicio,idPersonalOri,Constantes.getUsuarioDTO(request), idArchivo, fechaInicioPlazoDescargo); 
	        /* OSINE_SFS-480 - RSIS 17 - Fin */    
                    retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
	            retorno.put(ConstantesWeb.VV_MENSAJE, "Cambios realizados");
	            retorno.put("expediente",respuesta);
            // htorress - RSIS 18 - Inicio
            }else{
            	LOG.error("Error en concluir");
                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                retorno.put(ConstantesWeb.VV_MENSAJE, expedienteArchivar.getMensajeServicio());
            }
            // htorress - RSIS 18 - Fin
        }catch(Exception e){
            LOG.error("Error en concluir",e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }
    
    @RequestMapping(value="/observar",method= RequestMethod.POST)
    // htorress - RSIS 18 - Inicio
    //public @ResponseBody Map<String,Object> observarOrdenServicio(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,String motivoReasignacion,HttpSession session,HttpServletRequest request){
    public @ResponseBody Map<String,Object> observarOrdenServicio(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,String asuntoSiged, Long numeroExpediente,String motivoReasignacion,HttpSession session,HttpServletRequest request){	
    // htorress - RSIS 18 - Fin
        LOG.info("observar");
        Map<String,Object> retorno=new HashMap<String,Object>();
    	try{
            // htorress - RSIS 18 - Inicio
            ExpedienteDTO expediente = new ExpedienteDTO();
            expediente.setAsuntoSiged(asuntoSiged);
            expediente.setNumeroExpediente(String.valueOf(numeroExpediente));
            expediente.setPersonal(new PersonalDTO());
            /* OSINE_SFS-480 - RSIS 40 - Inicio */
            expediente.getPersonal().setIdPersonalSiged(Constantes.getIDPERSONALSIGED(request));  
            /* OSINE_SFS-480 - RSIS 40 - Fin */
            PersonalDTO personaOrigen=(PersonalDTO) obtenerUsuarioOrigen(idOrdenServicio);

            
            // Usuario Origen: Jefe, entonces el especialista o Jefe regional devuelven a su vez al supervisor (reenvian).
            if(!(personaOrigen.getRol().getNombreRol().equals(Constantes.CONSTANTE_ROL_SUPERVISOR))){

            	// Obtención del idPersonalSiged del Supervisor
            	HistoricoEstadoFilter filtro = new HistoricoEstadoFilter();
                filtro.setIdOrdenServicio(idOrdenServicio);
                List<HistoricoEstadoDTO> listado=historicoEstadoServiceNeg.listarHistoricoEstado(filtro);
                
                Collections.sort(listado, new Comparator<HistoricoEstadoDTO>(){
                	@Override
                	public int compare(HistoricoEstadoDTO hist1, HistoricoEstadoDTO hist2) {                    		
                		return hist1.getFechaCreacion().compareTo(hist2.getFechaCreacion());
                	}});
                Collections.reverse(listado);
            	int cantidad = listado.size();
            	
            	List<PersonalDTO> destinatarioSupervisor = personalServiceNeg.findPersonal(new PersonalFilter(listado.get(cantidad-2).getPersonalOri().getIdPersonal()));
            	Long idSupervisor=destinatarioSupervisor.get(0).getIdPersonalSiged();
            	
            	ExpedienteDTO expedienteReenviarSiged=expedienteServiceNeg.reenviarExpedienteSIGED(expediente, idSupervisor , motivoReasignacion, false);
            	if(expedienteReenviarSiged.getNumeroExpediente()!=null){
	            	OrdenServicioDTO respuesta=ordenServicioServiceNeg.observar(idExpediente,idOrdenServicio,idPersonalOri,motivoReasignacion,Constantes.getUsuarioDTO(request));
		            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
		            retorno.put(ConstantesWeb.VV_MENSAJE, "Observación enviada.");
		            retorno.put("expediente",respuesta);
	            }else{
	            	LOG.error("Error en devolver");
	                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
	                retorno.put(ConstantesWeb.VV_MENSAJE, expedienteReenviarSiged.getMensajeServicio());
	            }
            	
            }else{
            	/*HistoricoEstadoFilter filtro = new HistoricoEstadoFilter();
                filtro.setIdOrdenServicio(idOrdenServicio);
            	List<HistoricoEstadoDTO> listado=historicoEstadoServiceNeg.listarHistoricoEstado(filtro);
            	if(Constantes.getIDPERSONALSIGED(request).equals(listado.get)){
            		
            	}*/
	            ExpedienteDTO expedienteDevolver=expedienteServiceNeg.rechazarExpedienteSIGED(expediente, motivoReasignacion);
	            if(expedienteDevolver.getEstado().equals(Constantes.FLAG_REGISTRADO_SI)){
		        // htorress - RSIS 18 - Fin
	            	OrdenServicioDTO respuesta=ordenServicioServiceNeg.observar(idExpediente,idOrdenServicio,idPersonalOri,motivoReasignacion,Constantes.getUsuarioDTO(request));
		            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
		            retorno.put(ConstantesWeb.VV_MENSAJE, "Observación enviada.");
		            retorno.put("expediente",respuesta);
		        // htorress - RSIS 18 - Inicio
	            }else{
	            	LOG.error("Error en devolver");
	                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
	                retorno.put(ConstantesWeb.VV_MENSAJE, expedienteDevolver.getMensajeServicio());
	            }

	            
            }
            // htorress - RSIS 18 - Fin
        }catch(Exception e){
            LOG.error("Error en observar",e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }
    
    @RequestMapping(value="/observarAprobar",method= RequestMethod.POST)
    //public @ResponseBody Map<String,Object> observAprobar(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,String asuntoSiged, Long numeroExpediente,String motivoReasignacion,HttpSession session,HttpServletRequest request){	
    public @ResponseBody Map<String,Object> observAprobar(OrdenServicioDTO ordenServicioDTO,Long idPersonalOri,String motivoReasignacion,HttpSession session,HttpServletRequest request){	
        LOG.info("observAprobar");
        Map<String,Object> retorno=new HashMap<String,Object>();
    	try{
            String codFlujoSup=supervisionServiceNeg.buscarCodigoFlujoSupervINPS(
                    ordenServicioDTO.getExpediente().getObligacionTipo().getIdObligacionTipo(), 
                    ordenServicioDTO.getExpediente().getUnidadSupervisada().getActividad().getIdActividad(), 
                    ordenServicioDTO.getExpediente().getProceso().getIdProceso());   
            if(!StringUtil.isEmpty(codFlujoSup) && codFlujoSup.equals(Constantes.CODIGO_FLUJO_SUPERV_INPS_DSR_CRI)){
                LOG.info("-->codFlujoSup "+Constantes.CODIGO_FLUJO_SUPERV_INPS_DSR_CRI);
                retorno=observAprobarDsrCri(ordenServicioDTO.getExpediente().getIdExpediente(),ordenServicioDTO.getIdOrdenServicio(),idPersonalOri,ordenServicioDTO.getExpediente().getAsuntoSiged(), ordenServicioDTO.getExpediente().getNumeroExpediente(),motivoReasignacion,request);
            }else{
                LOG.info("-->codFlujoSup Default");
                retorno=observAprobarDefault(ordenServicioDTO.getExpediente().getIdExpediente(),ordenServicioDTO.getIdOrdenServicio(),idPersonalOri,ordenServicioDTO.getExpediente().getAsuntoSiged(), ordenServicioDTO.getExpediente().getNumeroExpediente(),motivoReasignacion,request);
            }
        }catch(Exception e){
            LOG.error("Error en observAprobar",e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }

    // htorress - RSIS 16 - Inicio
    public Map<String,Object> observAprobarDefault(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,String asuntoSiged, String numeroExpediente,String motivoReasignacion,HttpServletRequest request){	
        LOG.info("observAprobarDefault");
        Map<String,Object> retorno=new HashMap<String,Object>();
    	try{
            ExpedienteDTO expediente = new ExpedienteDTO();
            expediente.setAsuntoSiged(asuntoSiged);
            expediente.setNumeroExpediente(numeroExpediente);
            expediente.setPersonal(new PersonalDTO());
            /* OSINE_SFS-480 - RSIS 40 - Inicio */ 
            expediente.getPersonal().setIdPersonalSiged(Constantes.getIDPERSONALSIGED(request)); 
            /* OSINE_SFS-480 - RSIS 40 - Fin */ 
            /*ExpedienteDTO expedienteAprobar=expedienteServiceNeg.aprobarExpedienteSIGED(expediente, idPersSIGED, "Validar Contenido a enviar.", false);
            if(expedienteAprobar.getNumeroExpediente()!=null){*/
            
	            ExpedienteDTO expedienteDevolver=expedienteServiceNeg.rechazarExpedienteSIGED(expediente, motivoReasignacion);
	            if(expedienteDevolver.getEstado().equals(Constantes.FLAG_REGISTRADO_SI)){
	            	OrdenServicioDTO respuesta=ordenServicioServiceNeg.observarAprobar(idExpediente,idOrdenServicio,idPersonalOri,motivoReasignacion,Constantes.getUsuarioDTO(request));
	            	retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
		            retorno.put(ConstantesWeb.VV_MENSAJE, "Observación enviada.");
		            retorno.put("expediente",respuesta);
	            }else{
	            	LOG.error("Error en devolver");
	                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
	                retorno.put(ConstantesWeb.VV_MENSAJE, expedienteDevolver.getMensajeServicio());
	            }
            /*}else{
            	LOG.error("Error en aprobar");
                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                retorno.put(ConstantesWeb.VV_MENSAJE, expedienteAprobar.getMensajeServicio());
            }*/
        }catch(Exception e){
            LOG.error("Error en observAprobarDefault",e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }
    // htorress - RSIS 16 - Fin
    
    public Map<String,Object> observAprobarDsrCri(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,String asuntoSiged, String numeroExpediente,String motivoReasignacion,HttpServletRequest request){	
        LOG.info("observAprobarDsrCri");
        Map<String,Object> retorno=new HashMap<String,Object>();
    	try{
            ExpedienteDTO expediente = new ExpedienteDTO();
            expediente.setAsuntoSiged(asuntoSiged);
            expediente.setNumeroExpediente(numeroExpediente);
            expediente.setPersonal(new PersonalDTO());
            expediente.getPersonal().setIdPersonalSiged(Constantes.getIDPERSONALSIGED(request));  
            
            ExpedienteDTO expedienteDevolver=expedienteServiceNeg.rechazarExpedienteSIGED(expediente, motivoReasignacion);
            if(expedienteDevolver.getEstado().equals(Constantes.FLAG_REGISTRADO_SI)){
                OrdenServicioDTO respuesta=ordenServicioServiceNeg.observar(idExpediente,idOrdenServicio,idPersonalOri,motivoReasignacion,Constantes.getUsuarioDTO(request));
                    retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
                    retorno.put(ConstantesWeb.VV_MENSAJE, "Observación enviada.");
                    retorno.put("expediente",respuesta);
            }else{
                LOG.error("Error en devolver");
                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                retorno.put(ConstantesWeb.VV_MENSAJE, expedienteDevolver.getMensajeServicio());
            }            
        }catch(Exception e){
            LOG.error("Error en observAprobarDsrCri",e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }
    
    // htorress - RSIS 18 - Inicio
    public PersonalDTO obtenerUsuarioOrigen(Long idOrdenServicio){
    	LOG.info("obtenerUsuarioOrigen");
    	PersonalDTO usuarioSiged=null;
    	
    	HistoricoEstadoFilter filtro = new HistoricoEstadoFilter();
        filtro.setIdOrdenServicio(idOrdenServicio);
        List<HistoricoEstadoDTO> listado=historicoEstadoServiceNeg.listarHistoricoEstado(filtro);
        
        Collections.sort(listado, new Comparator<HistoricoEstadoDTO>(){
        	@Override
        	public int compare(HistoricoEstadoDTO hist1, HistoricoEstadoDTO hist2) {                    		
        		return hist1.getFechaCreacion().compareTo(hist2.getFechaCreacion());
        	}});
        Collections.reverse(listado);
        List<PersonalDTO> destinatario = personalServiceNeg.findPersonal(new PersonalFilter(listado.get(0).getPersonalOri().getIdPersonal()));
        /* OSINE_SFS-791 - RSIS 42 - Inicio */ 
        if( destinatario!=null && destinatario.size()>0) {
        	usuarioSiged=(PersonalDTO)destinatario.get(0);	
        }
        /* OSINE_SFS-791 - RSIS 42 - Fin */ 
    	return usuarioSiged;
    }
    // htorress - RSIS 18 - Fin
    @RequestMapping(value="/confirmarDescargo",method= RequestMethod.GET)
    public @ResponseBody Map<String,Object> confirmarDescargoOrdenServicio(Long idExpediente,Long idOrdenServicio,Long idPersonalOri, Long numeroExpediente,HttpSession session,HttpServletRequest request){
        LOG.info("concluir");
        Map<String,Object> retorno=new HashMap<String,Object>();
    	try{
    	
    		ExpedienteDTO expedienteReabrir=expedienteServiceNeg.reabrirExpedienteSIGED(String.valueOf(numeroExpediente));
    		
    		if(expedienteReabrir.getNumeroExpediente()!=null){
            	
            OrdenServicioDTO respuesta=ordenServicioServiceNeg.confirmarDescargo(idExpediente,idOrdenServicio,idPersonalOri,Constantes.getUsuarioDTO(request));
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put(ConstantesWeb.VV_MENSAJE, "Cambios realizados");
            retorno.put("expediente",respuesta);
            }else{
            	LOG.error("Error en reabrir");
                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                retorno.put(ConstantesWeb.VV_MENSAJE, expedienteReabrir.getMensajeServicio());
            }
        }catch(Exception e){
            LOG.error("Error en concluir",e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }
    
    /* OSINE_SFS-480 - RSIS 43 - Inicio */
    @RequestMapping(value="/anular",method= RequestMethod.GET)
    public @ResponseBody Map<String,Object> anularOrdenServicio(ExpedienteDTO expedienteDTO,Long idPersonalOri,String flagSupervision,HttpSession session,HttpServletRequest request){
        LOG.info("anular");
        Map<String,Object> retorno=new HashMap<String,Object>();
    	try{
        	OrdenServicioDTO respuesta=ordenServicioServiceNeg.anularExpedienteOrden(expedienteDTO.getIdExpediente(), expedienteDTO.getOrdenServicio().getIdOrdenServicio(),Constantes.getUsuarioDTO(request),expedienteDTO.getOrdenServicio().getComentarioDevolucion(),Constantes.getIDPERSONAL(request));            	
        	retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_ANULAR_OS);
            retorno.put("expediente",respuesta);
        }catch(Exception e){
            LOG.error("Error en anular",e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
    	return retorno;
    }  
    /* OSINE_SFS-480 - RSIS 43 - Fin */
    
    @RequestMapping(value="/cargarTrazabilidad",method= RequestMethod.GET)
    public @ResponseBody Map<String,Object> cargarTrazabilidad(HistoricoEstadoFilter filtro,int rows, int page,HttpSession session,HttpServletRequest request){
        LOG.info("cargarTrazabilidad");
    	
        Map<String,Object> retorno=new HashMap<String,Object>();
        try{
        	int indiceInicial=-1;
            int indiceFinal=-1;
            List<HistoricoEstadoDTO> listadoPaginado = null;
        	
            List<HistoricoEstadoDTO> listado=historicoEstadoServiceNeg.listarHistoricoEstado(filtro);
            
            Long contador = (long) listado.size();
            Long numeroFilas = (contador / rows);
            if ((contador % rows) > 0) { numeroFilas = numeroFilas + 1L;}
            if(numeroFilas<page){page = numeroFilas.intValue();}
            if(page==0){rows=0;}
            indiceInicial = (page - 1) * rows;
            indiceFinal = indiceInicial + rows;
            listadoPaginado = listado.subList(indiceInicial > listado.size() ? listado.size() : indiceInicial, indiceFinal > listado.size() ? listado.size() : indiceFinal );           
            retorno.put("total", numeroFilas);
            retorno.put("pagina", page);
            retorno.put("registros", contador);
            retorno.put("filas", listadoPaginado);
        }catch(Exception ex){
            LOG.error("Error cargarTrazabilidad",ex);
        }
        return retorno;
    }
    
    @RequestMapping(value ="/buscarProcesoFlag", method = RequestMethod.GET)
    public String buscarProcesoFlag(Long IdObligacionTipo,Long idactividad,Long IdProceso,HttpSession sesion,Model model){
        String retorno="0";
    	Integer flagSupervision=supervisionServiceNeg.buscarFlag(IdObligacionTipo, idactividad, IdProceso);
    	retorno=flagSupervision.toString();
    	return retorno;
    }
    
    @RequestMapping(value="/findOrdenServicio",method= RequestMethod.POST)
    public @ResponseBody Map<String,Object> findOrdenServicio(OrdenServicioFilter filtro,int rows, int page,HttpSession session,HttpServletRequest request){
        LOG.info("findOrdenServicio");
        LOG.info("Finicio->"+filtro.getFechaInicioEstadoProceso());
        LOG.info("Ffin-"+filtro.getFechaFinEstadoProceso());
        
        Map<String,Object> retorno=new HashMap<String,Object>();
        try{
        	int indiceInicial=-1;
            int indiceFinal=-1;
            List<OrdenServicioDTO> listadoPaginado= null;
            List<OrdenServicioDTO> listado=ordenServicioServiceNeg.listarOrdenServicio(filtro);
            
            Long contador = (long) listado.size();
            Long numeroFilas = (contador / rows);
            if ((contador % rows) > 0) {numeroFilas = numeroFilas + 1L;}
            if(numeroFilas<page){page = numeroFilas.intValue();}
            if(page==0){rows=0;}
            indiceInicial = (page - 1) * rows;
            indiceFinal = indiceInicial + rows;
            listadoPaginado = listado.subList(indiceInicial > listado.size() ? listado.size() : indiceInicial,indiceFinal > listado.size() ? listado.size() : indiceFinal );
               
            retorno.put("total", numeroFilas);
            retorno.put("pagina", page);
            retorno.put("registros", contador);
            retorno.put("filas", listadoPaginado);
        }catch(Exception ex){
            LOG.error("Error findOrdenServicio",ex);
        }
        return retorno;
    }
    
    /* OSINE_SFS-480 - RSIS 40 - Inicio */    
    @RequestMapping(value = "/devolverOrdenServicio", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> devolverOrdenServicio(OrdenServicioFilter osFilter,Long idPeticion,Long idMotivo,String comentarioDevolucion,HttpSession session,HttpServletRequest request){
        LOG.info("procesando devolver");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try { 
            List<OrdenServicioDTO> registroDevueltos=ordenServicioServiceNeg.devolverOrdenServicioSupe(osFilter.getOrdenesServicio(),Constantes.getIDPERSONAL(request),Constantes.getIDPERSONALSIGED(request),idPeticion,idMotivo,comentarioDevolucion,Constantes.getUsuarioDTO(request));;                

            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put(ConstantesWeb.VV_MENSAJE, "Cambios realizados");
            retorno.put("orden servicio",registroDevueltos);
        }catch(Exception e){
            LOG.error("Error en devolver",e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;    
    }
    /* OSINE_SFS-480 - RSIS 40 - Fin */    
    
    @InitBinder
    public void binder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
        public void setAsText(String value) {
            try {
                setValue(new SimpleDateFormat("dd/MM/yyyy").parse(value));
            } catch (ParseException e) {
                setValue(null);
            }
        }   

        public String getAsText() {
            return new SimpleDateFormat("dd/MM/yyyy")
                    .format((Date) getValue());
        }

        });
    }    
}