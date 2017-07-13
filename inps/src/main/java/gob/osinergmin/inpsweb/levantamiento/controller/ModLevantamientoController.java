/**
* Resumen		
* Objeto			: PVOInfraccionesController.java
* Descripción		: Clase Controller PVOInfraccionesController
* Fecha de Creación	: 25/10/2016
* PR de Creación	: OSINE_SFS-791
* Autor				: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------				
* OSINE_SFS-791     25/10/2016      Mario Dioses Fernandez      Crear una funcionalidad de bandeja de expedientes con infracciones de supervisión DSR-CRITICIDAD para uso del Agente
* OSINE_SFS-791     25/10/2016      Mario Dioses Fernandez      Crear una funcionalidad que permita al Agente registrar el levantamiento de infracciones para expedientes de supervisión DSR-CRITICIDAD.
* OSINE_SFS-791     25/10/2016      Mario Dioses Fernandez      Crear una funcionalidad que permita al Agente consultar el levantamiento de infracciones para expedientes de supervisión DSR-CRITICIDAD.
*/ 
package gob.osinergmin.inpsweb.levantamiento.controller;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gob.osinergmin.inpsweb.levantamiento.service.business.ModLevantamientoServiceNeg;
import gob.osinergmin.inpsweb.service.business.ComentarioDetSupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.business.ComentarioIncumplimientoServiceNeg;
import gob.osinergmin.inpsweb.service.business.ComplementoDetSupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.business.DetalleLevantamientoServiceNeg;
import gob.osinergmin.inpsweb.service.business.DetalleSupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.business.DocumentoAdjuntoServiceNeg;
import gob.osinergmin.inpsweb.service.business.EscenarioIncumplidoServiceNeg;
import gob.osinergmin.inpsweb.service.business.EscenarioIncumplimientoServiceNeg;
import gob.osinergmin.inpsweb.service.business.ExpedienteServiceNeg;
import gob.osinergmin.inpsweb.service.business.MaestroColumnaServiceNeg;
import gob.osinergmin.inpsweb.service.business.SupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.business.UnidadSupervisadaServiceNeg;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.ConstantesWeb;
import gob.osinergmin.mdicommon.domain.dto.ComentarioDetSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.ComentarioIncumplimientoDTO;
import gob.osinergmin.mdicommon.domain.dto.ComplementoDetSupValorDTO;
import gob.osinergmin.mdicommon.domain.dto.ComplementoDetSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleLevantamientoDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.EscenarioIncumplidoDTO;
import gob.osinergmin.mdicommon.domain.dto.EscenarioIncumplimientoDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.InfraccionDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.ResultadoSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.UbigeoDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.dto.busqueda.BusquedaUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.ui.ComentarioDetSupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.ComentarioIncumplimientoFilter;
import gob.osinergmin.mdicommon.domain.ui.ComplementoDetSupValorFilter;
import gob.osinergmin.mdicommon.domain.ui.ComplementoDetSupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.DetalleLevantamientoFilter;
import gob.osinergmin.mdicommon.domain.ui.DetalleSupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.DocumentoAdjuntoFilter;
import gob.osinergmin.mdicommon.domain.ui.EscenarioIncumplidoFilter;
import gob.osinergmin.mdicommon.domain.ui.EscenarioIncumplimientoFilter;
import gob.osinergmin.mdicommon.domain.ui.ExpedienteFilter;
import gob.osinergmin.mdicommon.domain.ui.SupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.UnidadSupervisadaFilter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/modLevantamientos")
public class ModLevantamientoController {
	private static final Logger LOG = LoggerFactory.getLogger(ModLevantamientoController.class);
	private UsuarioDTO usuario;
	@Inject
	private SupervisionServiceNeg SupervisionServiceNeg;
	@Inject 
	private DetalleSupervisionServiceNeg detalleSupervisionServiceNeg;
	@Inject
	private DetalleLevantamientoServiceNeg detalleLevantamientoServiceNeg;
	@Inject
	private EscenarioIncumplimientoServiceNeg escenarioIncumplimientoServiceNeg;
	@Inject
	private EscenarioIncumplidoServiceNeg escenarioIncumplidoServiceNeg;
	@Inject
	private DocumentoAdjuntoServiceNeg documentoAdjuntoServiceNeg;
	@Inject
	private ComentarioIncumplimientoServiceNeg comentarioIncumplimientoServiceNeg;
	@Inject
	private ComentarioDetSupervisionServiceNeg comentarioDetSupervisionServiceNeg;
	@Inject
	private ComplementoDetSupervisionServiceNeg complementoDetSupervisionServiceNeg;
	@Inject
	private MaestroColumnaServiceNeg maestroColumnaServiceNeg;
	@Inject
	private UnidadSupervisadaServiceNeg unidadSupervisadaServiceNeg;
	@Inject
	private ExpedienteServiceNeg expedienteServiceNeg;
	@Inject
	private SupervisionServiceNeg supervisionServiceNeg;
	@Inject
	private ModLevantamientoServiceNeg modLevantamientoServiceNeg;
	
	@RequestMapping(method = RequestMethod.POST)
    public String abrirModLevantamientos(HttpSession session, HttpServletRequest request, Model model) {
		LOG.info("procesando POST para RequestMapping /abrirModLevantamientos");
		String navegacion = ConstantesWeb.Navegacion.PAGE_405_MOD_LEVANTAMIENTO;
		try {			
			String p_usuario = request.getParameter("p_usuario");
			String p_pagina = request.getParameter("p_pagina");
			String codigo_osinergmin = request.getParameter("codigo_osinergmin");			
			if(p_usuario!=null && p_pagina!=null && codigo_osinergmin!=null && !p_usuario.equals("") && !p_pagina.equals("") && !codigo_osinergmin.equals("")){
				if(p_usuario!=null) {	
					setUsuario(p_usuario);
	                UnidadSupervisadaFilter unidadfilter = new UnidadSupervisadaFilter();
	                unidadfilter.setCodigoOsinerg(codigo_osinergmin);
	                List<UnidadSupervisadaDTO> ltaUnidad = unidadSupervisadaServiceNeg.getUnidadSupervisadaDTO(unidadfilter);
	                if (ltaUnidad.size() == Constantes.LISTA_UNICO_VALIR) {
	                   UnidadSupervisadaDTO unidadDTO = ltaUnidad.get(Constantes.PRIMERO_EN_LISTA);
	                     model.addAttribute("usuario",unidadDTO.getActividad().getNombre());
	                }
				}
				LOG.info("p_usuario: " + p_usuario + " || p_pagina: " + p_pagina);		
				SupervisionFilter filtro = new SupervisionFilter();
				filtro.setCodigoOsinergmin(codigo_osinergmin);
				filtro.setFlagModLevantamiento(Constantes.ESTADO_ACTIVO);
				List<SupervisionDTO> supervisionList=SupervisionServiceNeg.listarSupSinSubSanar(filtro);
				
				
				List<MaestroColumnaDTO> estadoLevantamientoList=maestroColumnaServiceNeg.findMaestroColumnaByCodigo(Constantes.DOMINIO_ESTADO_LEVANTAMIENTO, Constantes.APLICACION_INPS, Constantes.CODIGO_ESTADO_LEVANTAMIENTO_POREVALUAR);
				String codigoEstadoLevEvaluar=(!CollectionUtils.isEmpty(estadoLevantamientoList)) ? estadoLevantamientoList.get(0).getCodigo() : null;
				model.addAttribute("codigoEstadoLevEvaluar", codigoEstadoLevEvaluar);
				
		        model.addAttribute("fecha", Constantes.getFECHA());
		        
		        model.addAttribute("p_usuario", p_usuario);
		        model.addAttribute("p_pagina", p_pagina);
		        model.addAttribute("codigo_osinergmin", codigo_osinergmin);
		        
				model.addAttribute("supervisionList", supervisionList);	
				
				navegacion = ConstantesWeb.Navegacion.PAGE_INPS_MOD_LEVANTAMIENTO;
			}
		} catch (Exception e) {
			LOG.error("error abrirModLevantamientos", e);
		}
		return navegacion;
	}
	
	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(String p_usuario) throws UnknownHostException {
		this.usuario = new UsuarioDTO();
		this.usuario.setCodigo(p_usuario);
		this.usuario.setTerminal(Constantes.getTERMINAL());	
	}
	
	@RequestMapping(value = "/mostrarInfraccion", method = RequestMethod.GET)
    public String mostrarInfraccion(DetalleSupervisionFilter filtro, String numeroExpediente, Long idExpediente, String navegacion, String modoSupervision, Model model) {
		LOG.info("procesando GET para RequestMapping /mostrarInfraccion");
		DocumentoAdjuntoFilter filtroDocAdjunto = null;
		try {
			//levantamientoDetalle
			if(filtro.getIdDetalleSupervision()!=null && modoSupervision!=null && (modoSupervision.equals(Constantes.SUPERVISION_MODO_REGISTRO) || modoSupervision.equals(Constantes.SUPERVISION_MODO_CONSULTA))){
				Map<String, Object> detalleMap=getDetalleSupervisionLev(filtro.getIdDetalleSupervision());
				model.addAttribute("detalleSupervision", detalleMap.get("detalleSupervision"));
				model.addAttribute("detalleLevantamiento", detalleMap.get("detalleLevantamiento"));
				
				filtroDocAdjunto = new DocumentoAdjuntoFilter();
				filtroDocAdjunto.setIdDetalleSupervision(filtro.getIdDetalleSupervision());
				List<DocumentoAdjuntoDTO> docAdjuntoDetSupList=documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(filtroDocAdjunto);
				model.addAttribute("docAdjuntoDetSupList", docAdjuntoDetSupList);
				
				filtroDocAdjunto = new DocumentoAdjuntoFilter();
				filtroDocAdjunto.setIdDetalleLevantamiento(((DetalleLevantamientoDTO)detalleMap.get("detalleLevantamiento")).getIdDetalleLevantamiento());
				List<DocumentoAdjuntoDTO> docAdjuntoDetLevList=documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(filtroDocAdjunto);
				model.addAttribute("docAdjuntoDetLevList", docAdjuntoDetLevList);			
				
				if(((DetalleSupervisionDTO) detalleMap.get("detalleSupervision"))!=null && ((DetalleSupervisionDTO) detalleMap.get("detalleSupervision")).getInfraccion()!=null && 
						((DetalleSupervisionDTO) detalleMap.get("detalleSupervision")).getInfraccion().getIdInfraccion()!=null &&
						((DetalleSupervisionDTO) detalleMap.get("detalleSupervision")).getIdDetalleSupervision()!=null){
					EscenarioIncumplimientoFilter filtroEscInc = new EscenarioIncumplimientoFilter();
					filtroEscInc.setIdInfraccion(((DetalleSupervisionDTO) detalleMap.get("detalleSupervision")).getInfraccion().getIdInfraccion());
					filtroEscInc.setIdDetalleSupervision((((DetalleSupervisionDTO) detalleMap.get("detalleSupervision")).getIdDetalleSupervision()));
					filtroEscInc.setFlagBuscaIncumplido(Constantes.ESTADO_ACTIVO);
					List<EscenarioIncumplimientoDTO> escenarioIncumplimientoList=getEscenarioIncumplimiento(filtroEscInc);
					model.addAttribute("escenarioIncumplimientoList", escenarioIncumplimientoList);
				}				
			}
			//bandejaIngresoLevantamientos - bandejaSupervisionInfraccion
			if(filtro.getIdSupervision()!=null){
				List<DetalleSupervisionDTO> detalleSupervisionList=listarInfaccionLevantamiento(filtro.getIdSupervision());
				if(!CollectionUtils.isEmpty(detalleSupervisionList)){
					for(DetalleSupervisionDTO detalle : detalleSupervisionList){
						DetalleLevantamientoFilter filtroLev=new DetalleLevantamientoFilter();
			            filtroLev.setIdDetalleSupervision(detalle.getIdDetalleSupervision());
			            filtroLev.setFlagUltimoRegistro(Constantes.ESTADO_ACTIVO);
			            List<DetalleLevantamientoDTO> detalleLevantamientoist = detalleLevantamientoServiceNeg.find(filtroLev);
			            if(!CollectionUtils.isEmpty(detalleLevantamientoist)){
			            	detalle.setFlagDescLevantamiento(Constantes.ESTADO_INACTIVO);
			            	if(((DetalleLevantamientoDTO)detalleLevantamientoist.get(0)).getDescripcion()!=null){
			            		detalle.setFlagDescLevantamiento(Constantes.ESTADO_ACTIVO);
			            	}		            	
			            }     
					}
					model.addAttribute("detalleSupervisionList", detalleSupervisionList);
					model.addAttribute("idSupervision", filtro.getIdSupervision());
				}
			}			
			
			model.addAttribute("idExpediente",  idExpediente);
			model.addAttribute("nroExpediente", numeroExpediente);
			model.addAttribute("modoSupervision", modoSupervision);
			if(filtro.getIdDetalleSupervision()!=null){
				model.addAttribute("idDetalleSupervision", filtro.getIdDetalleSupervision());
			}
		} catch (Exception e) {
			LOG.error("error mostrarInfraccion", e);
		}
		return navegacion;
	}
	
    public List<DetalleSupervisionDTO> listarInfaccionLevantamiento(Long idSupervision) {       
        LOG.info("procesando /listarInfaccionLevantamiento");
        List<DetalleSupervisionDTO> detalleSupervisionList=null;
        try {        	
        	DetalleSupervisionFilter filtro=new DetalleSupervisionFilter();
        	filtro.setIdSupervision(idSupervision);
        	detalleSupervisionList = detalleSupervisionServiceNeg.listarDetSupInfLevantamiento(filtro);  
            
        } catch (Exception ex) {
            LOG.error("Error listarInfaccionLevantamiento", ex);
        }
        return detalleSupervisionList;
    }
		
    public Map<String, Object> getDetalleSupervisionLev(Long idDetalleSupervision) {
		LOG.info("procesando /getDetalleSupervisionLev");
		Map<String, Object> retorno = new HashMap<String, Object>();
		DetalleSupervisionDTO detalleSupervision=null;
		DetalleLevantamientoDTO detalleLevantamiento=null;
		try{
			//getDetalleSupervision
			DetalleSupervisionFilter filtro=new DetalleSupervisionFilter();
			filtro.setIdDetalleSupervision(idDetalleSupervision);
			List<DetalleSupervisionDTO> detalleSupervisionList = detalleSupervisionServiceNeg.listarDetalleSupervision(filtro);
            if (detalleSupervisionList!=null && detalleSupervisionList.size()!=0) {
            	detalleSupervision=detalleSupervisionList.get(0);
            	if(detalleSupervision.getSupervision()!=null && detalleSupervision.getSupervision().getIdSupervision()!=null){
            		List<DetalleSupervisionDTO> totalDetaSupeList = detalleSupervisionServiceNeg.listarDetalleSupervision(new DetalleSupervisionFilter(detalleSupervision.getSupervision().getIdSupervision(), null));
            		if (totalDetaSupeList!=null) {
            			detalleSupervision.setTotalPrioridad(new Long(totalDetaSupeList.size()));  
            		}
            	}                 
                if(detalleSupervision.getResultadoSupervisionAnt() == null ){
                    ResultadoSupervisionDTO resultadoAnt = new ResultadoSupervisionDTO();
                    resultadoAnt.setIdResultadosupervision(Constantes.ID_RESULTADO_ANTERIOR_DEFAULT);
                    resultadoAnt.setCodigo(Constantes.CODIGO_RESULTADO_ANTERIOR_DEFAULT);                
                    detalleSupervision.setResultadoSupervisionAnt(resultadoAnt);
                }                
            }      
        	//getDetalleLevantamiento
            DetalleLevantamientoFilter filtroLev=new DetalleLevantamientoFilter();
            filtroLev.setIdDetalleSupervision(idDetalleSupervision);
            filtroLev.setFlagUltimoRegistro(Constantes.ESTADO_ACTIVO);
            List<DetalleLevantamientoDTO> detalleLevantamientoist = detalleLevantamientoServiceNeg.find(filtroLev);
            if(detalleLevantamientoist!=null && detalleLevantamientoist.size()!=0){
            	detalleLevantamiento=detalleLevantamientoist.get(0);
            }           
            
            retorno.put("detalleSupervision", detalleSupervision);
            retorno.put("detalleLevantamiento", detalleLevantamiento);
		} catch (Exception ex) {
            LOG.error("Error getDetalleSupervisionLev", ex);
        }
		return retorno;
    }
    
    public List<EscenarioIncumplimientoDTO> getEscenarioIncumplimiento(EscenarioIncumplimientoFilter filtro) {
    	LOG.info("procesando  /getEscenarioIncumplimiento");
        List<EscenarioIncumplimientoDTO> escenarioIncumplimientoList = null;
        try {
            if (filtro.getIdInfraccion() != null && filtro.getIdInfraccion() > 0L) {
            	escenarioIncumplimientoList = escenarioIncumplimientoServiceNeg.listarEscenarioIncumplimiento(filtro);
            }
            
            if(!CollectionUtils.isEmpty(escenarioIncumplimientoList) && filtro.getFlagBuscaIncumplido()!=null && filtro.getFlagBuscaIncumplido().equals(Constantes.ESTADO_ACTIVO) && filtro.getIdDetalleSupervision()!=null){
                List<EscenarioIncumplidoDTO> listIncdo=escenarioIncumplidoServiceNeg.findEscenarioIncumplido(new EscenarioIncumplidoFilter(null,filtro.getIdDetalleSupervision(),null));
                if(!CollectionUtils.isEmpty(listIncdo)){
                    for(EscenarioIncumplidoDTO regIncdo : listIncdo){
                        for(EscenarioIncumplimientoDTO reg : escenarioIncumplimientoList){
                            if(reg.getIdEsceIncumplimiento().equals(regIncdo.getEscenarioIncumplimientoDTO().getIdEsceIncumplimiento())){
                                reg.setFlagIncumplidoEnDetSup(Constantes.ESTADO_ACTIVO);
                                break;
                            }
                        }                        
                    }
                }
            }
        } catch (Exception ex) {
            LOG.error("Error getEscenarioIncumplimiento", ex);
        }
        return escenarioIncumplimientoList;
    }

    @RequestMapping(value = "/comentarioIncumplimiento", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> comentarioIncumplimiento(EscenarioIncumplimientoFilter filtro, DetalleSupervisionDTO detaSupe, HttpSession sesion, HttpServletRequest request, Model model) {
    	LOG.info("procesando POST para RequestMapping /comentarioIncumplimiento");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try{
            EscenarioIncumplimientoDTO esceInc= new EscenarioIncumplimientoDTO();                    
            if(filtro.getIdEsceIncumplimiento()!=null){
                esceInc=escenarioIncumplimientoServiceNeg.getEscenarioIncumplimiento(new EscenarioIncumplimientoFilter(filtro.getIdEsceIncumplimiento(),null));
            }
            InfraccionDTO infraccion= new InfraccionDTO();
            if(filtro.getIdInfraccion()!=null){
                infraccion=escenarioIncumplimientoServiceNeg.getInfraccion(new EscenarioIncumplimientoFilter(null,filtro.getIdInfraccion()));
            }            
            retorno.put("esceInc",esceInc);           
            retorno.put("infraccion", infraccion);            
        }catch(Exception e){
            LOG.error("Error en comentarioIncumplimiento", e);        
        }
        return retorno;
    }
    
    @RequestMapping(value = "/findComentarioIncumplimiento", method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody
    Map<String, Object> findComentarioIncumplimiento(ComentarioIncumplimientoFilter filtro, HttpSession session, HttpServletRequest request) {
    	LOG.info("procesando POST para RequestMapping /findComentarioIncumplimiento");
        Map<String, Object> retorno = new HashMap<String, Object>();        
        List<ComentarioIncumplimientoDTO> comentIncumplimientoList = null;
        boolean replace=false;
        List<ComentarioIncumplimientoDTO> comentarioIncumplimientoList = comentarioIncumplimientoServiceNeg.listarComentarioIncumplimiento(filtro);
        try{   
        	comentarioIncumplimientoList=setColorDescComentario(comentarioIncumplimientoList);
            if(!CollectionUtils.isEmpty(comentarioIncumplimientoList) && filtro.getFlagBuscaComentDetSup()!=null && filtro.getFlagBuscaComentDetSup().equals(Constantes.ESTADO_ACTIVO) && filtro.getIdDetalleSupervision()!=null){
                List<ComentarioDetSupervisionDTO> lstComenDetSup=comentarioDetSupervisionServiceNeg.findComentarioDetSupervision(new ComentarioDetSupervisionFilter(null,filtro.getIdDetalleSupervision(),filtro.getIdEsceIncumplimiento()));
                if(!CollectionUtils.isEmpty(lstComenDetSup)){
                    for(ComentarioDetSupervisionDTO regComenDetSup : lstComenDetSup){
                        for(ComentarioIncumplimientoDTO reg : comentarioIncumplimientoList){
                        	replace=false;
                            if(reg.getIdComentarioIncumplimiento().toString().equals(regComenDetSup.getComentarioIncumplimiento().getIdComentarioIncumplimiento().toString())){
                                reg.setFlagComentDetSupEnEsceIncdo(Constantes.ESTADO_ACTIVO);
                                reg.setIdComentarioDetSupervision(regComenDetSup.getIdComentarioDetSupervision());
                                //buscar complementoDetSupervision y sus valores para armar ComentarioDetSupArmado
                                String descripcion=reg.getDescripcion();
                                List<ComplementoDetSupervisionDTO> lstCompDetSup=complementoDetSupervisionServiceNeg.findComplementoDetSupervision(new ComplementoDetSupervisionFilter(reg.getIdComentarioDetSupervision()));
                                if(!CollectionUtils.isEmpty(lstCompDetSup)){
                                    for(ComplementoDetSupervisionDTO regCompDetSup : lstCompDetSup){
                                        List<ComplementoDetSupValorDTO> lstCompDetSupValor = complementoDetSupervisionServiceNeg.findComplementoDetSupValor(new ComplementoDetSupValorFilter(regCompDetSup.getIdComplementoDetSupervision()));
                                        if(!CollectionUtils.isEmpty(lstCompDetSupValor)){
                                            String comDetSup="";
                                            for(ComplementoDetSupValorDTO cdsv : lstCompDetSupValor){
                                                comDetSup=comDetSup+cdsv.getValorDesc()+", ";
                                            }
                                            comDetSup=comDetSup.substring(0, comDetSup.length()-2);
                                            if(regCompDetSup.getComentarioComplemento()!=null 
                                                && regCompDetSup.getComentarioComplemento().getComplemento()!=null 
                                                && regCompDetSup.getComentarioComplemento().getComplemento().getEtiquetaComentario()!=null){
                                                descripcion=descripcion.replace(regCompDetSup.getComentarioComplemento().getComplemento().getEtiquetaComentario(),comDetSup);
                                                replace=true;
                                            }
                                        }                                        
                                    }
                                }
                                reg.setDescripcion(descripcion);
                                if(replace){
                                	if(CollectionUtils.isEmpty(comentIncumplimientoList)){
                                		comentIncumplimientoList = new ArrayList<ComentarioIncumplimientoDTO>();
                                	}
                                	comentIncumplimientoList.add(reg);
                                }                                	
                                break;
                            }
                        }                        
                    }
                }
            }
            retorno.put("comentarioIncumplimientoList", comentIncumplimientoList);

        } catch (Exception ex) {
            LOG.error("Error findComentarioIncumplimiento", ex);
        }
        return retorno;
    }
    
    @RequestMapping(value = "/actualizarLevantamiento", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> actualizarLevantamiento(DetalleLevantamientoDTO detalleLevantamiento, Long idExpediente, HttpSession session, HttpServletRequest request) {
    	LOG.info("procesando POST para RequestMapping /actualizarLevantamiento");
        Map<String, Object> retorno = new HashMap<String, Object>();
       try {
    	   DetalleLevantamientoFilter fitro = new DetalleLevantamientoFilter();
    	   fitro.setIdDetalleLevantamiento(detalleLevantamiento.getIdDetalleLevantamiento());
    	   List<DetalleLevantamientoDTO> detalleLevantamientoList=detalleLevantamientoServiceNeg.find(fitro);
    	   List <ExpedienteDTO> expedienteList = expedienteServiceNeg.findByFilter(new ExpedienteFilter(idExpediente));
    	   if(!CollectionUtils.isEmpty(detalleLevantamientoList) && !CollectionUtils.isEmpty(expedienteList)){
    		   DetalleLevantamientoDTO detalleLev=detalleLevantamientoList.get(0);
    		   detalleLev.setDescripcion(detalleLevantamiento.getDescripcion());
    		   detalleLevantamientoServiceNeg.actualizarDetalleLevantamiento(detalleLev, Constantes.getUsuarioDTO(request));
    		   
    		   //El estado de LEVANTAMIENTOS del expediente cambiará a POR PROCESO. 					 
	        	List<MaestroColumnaDTO> estadoLevantamientoList = maestroColumnaServiceNeg.findByDominioAplicacionCodigo(Constantes.DOMINIO_ESTADO_LEVANTAMIENTO, Constantes.APLICACION_INPS, Constantes.CODIGO_ESTADO_LEVANTAMIENTO_ENPROCESO);
	            if(!CollectionUtils.isEmpty(estadoLevantamientoList)){	            	
	            	MaestroColumnaDTO estadoLevantamiento = estadoLevantamientoList.get(0);
	            	ExpedienteDTO expediente = expedienteList.get(0);
	            	expediente.setEstadoLevantamiento(estadoLevantamiento);
	            	expediente=expedienteServiceNeg.actualizar(expediente, usuario);
	            	retorno.put("expediente", expediente);
	            }
    		   
    		   retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
               retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_CREATE);
    	   } else {
    		   retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
               retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_CREATE);
    	   }
        } catch (Exception ex) {
            LOG.error("Error actualizarLevantamiento", ex);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, ex.getMessage());
        }
        return retorno;
    }
    
    @RequestMapping(value = "/enviarLevantamiento", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> enviarLevantamiento(Long idExpediente, Long idSupervision, HttpSession session, HttpServletRequest request) {
    	LOG.info("procesando POST para RequestMapping /enviarLevantamiento");
        Map<String, Object> retorno = new HashMap<String, Object>();
       try { 
    	    List <ExpedienteDTO> expedienteList = expedienteServiceNeg.findByFilter(new ExpedienteFilter(idExpediente));
       		List <SupervisionDTO> supervisionList = supervisionServiceNeg.buscarSupervision(new SupervisionFilter(idSupervision));
       		List<DetalleSupervisionDTO> detalleSupervisionList=listarInfaccionLevantamiento(idSupervision);
       		if(!CollectionUtils.isEmpty(expedienteList) && !CollectionUtils.isEmpty(supervisionList) && !CollectionUtils.isEmpty(detalleSupervisionList)){   
       			ExpedienteDTO expediente = expedienteList.get(0);
       			SupervisionDTO supervision = supervisionList.get(0);
       			UnidadSupervisadaDTO unidadSupervisada=expediente.getUnidadSupervisada();
       			if(unidadSupervisada!=null){
       				modLevantamientoServiceNeg.enviarLevantamiento(expediente, detalleSupervisionList, supervision, getUbigeo(unidadSupervisada), getUsuario());
       				retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
       				retorno.put(ConstantesWeb.VV_MENSAJE, "Se ha concluido el ingreso de levantamiento de infraciones.");
       			}
    	   }
        } catch (Exception ex) {
            LOG.error("Error enviarLevantamiento", ex);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, ex.getMessage());
        }
        return retorno;
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
    
    @RequestMapping(value = "/listarSupSinSubSanar", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> listarSupSinSubSanar(SupervisionFilter filtro, HttpServletRequest request, Model model) {
		LOG.info("procesando POST para RequestMapping /listarSupSinSubSanar");
		Map<String, Object> retorno = new HashMap<String, Object>();
		try {
			filtro.setFlagModLevantamiento(Constantes.ESTADO_ACTIVO);
			List<SupervisionDTO> supervisionList=SupervisionServiceNeg.listarSupSinSubSanar(filtro);
			retorno.put("supervisionList", supervisionList);			
		} catch (Exception e) {
			LOG.error("error listarSupSinSubSanar", e);
		}
		return retorno;
	}
    
    public List<ComentarioIncumplimientoDTO> setColorDescComentario(List<ComentarioIncumplimientoDTO> listado){
    	List<ComentarioIncumplimientoDTO> listaNueva=new ArrayList<ComentarioIncumplimientoDTO>();
    	if(!CollectionUtils.isEmpty(listado)){
        	for(ComentarioIncumplimientoDTO reg : listado){
        		String descripcion = reg.getDescripcion();
        		descripcion=descripcion.replace("{","<span style='"+Constantes.STYLE_VARIABLE_COMENTARIO_COMPLEMENTO+"'>{");
            	descripcion=descripcion.replace("}","}</span>");                     	
            	reg.setDescripcion(descripcion);
            	listaNueva.add(reg);
        	}
    	}  	    	
    	return listaNueva;
    }
}
