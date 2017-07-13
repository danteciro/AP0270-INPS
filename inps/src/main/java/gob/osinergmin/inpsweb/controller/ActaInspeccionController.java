/**
 * Resumen 
 * Objeto				: ActaInspeccionController.java 
 * Descripción	        : Controla la inspeccion de las actas. 
 * Fecha de Creación	: 14/09/2016.
 * PR de Creación		: OSINE_SFS-1063.
 * Autor				: Hernan Torres Saenz.
 * ---------------------------------------------------------------------------------------------------
 * Modificaciones   Fecha             Nombre               Descripción
 * ---------------------------------------------------------------------------------------------------
 *
 */
package gob.osinergmin.inpsweb.controller;

import gob.osinergmin.dse_common.domain.dto.EtapaDTO;
import gob.osinergmin.dse_common.domain.dto.ReleDTO;
import gob.osinergmin.dse_common.domain.dto.SubEstacionDTO;
import gob.osinergmin.dse_common.domain.dto.ZonaDTO;
import gob.osinergmin.inpsweb.domain.builder.DetaSupeCampRechCargaBuilder;
import gob.osinergmin.inpsweb.service.business.ArchivoServiceNeg;
import gob.osinergmin.inpsweb.service.business.EmpresasZonaServiceNeg;
import gob.osinergmin.inpsweb.service.business.MaestroColumnaServiceNeg;
import gob.osinergmin.inpsweb.service.business.UbigeoServiceNeg;
import gob.osinergmin.inpsweb.service.exception.SupeCampRechCargaException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.ConstantesWeb;
import gob.osinergmin.inpsweb.util.Utiles;
import gob.osinergmin.mdicommon.domain.dto.ConfiguracionRelesDTO;
import gob.osinergmin.mdicommon.domain.dto.DepartamentoDTO;
import gob.osinergmin.mdicommon.domain.dto.DetaConfRelesDTO;
import gob.osinergmin.mdicommon.domain.dto.DetaSupeCampRechCargaDTO;
import gob.osinergmin.mdicommon.domain.dto.DetaSupeCampRechCargaRepDTO;
import gob.osinergmin.mdicommon.domain.dto.DistritoDTO;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresaSupDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresasZonaVwDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.FechaHoraDTO;
import gob.osinergmin.mdicommon.domain.dto.FlujoSigedDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.OrdenServicioDTO;
import gob.osinergmin.mdicommon.domain.dto.ProvinciaDTO;
import gob.osinergmin.mdicommon.domain.dto.ReleServicioDTO;
import gob.osinergmin.mdicommon.domain.dto.SupeCampRechCargaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.ConfiguracionRelesFilter;
import gob.osinergmin.mdicommon.domain.ui.DetaSupeCampRechCargaFilter;
import gob.osinergmin.mdicommon.domain.ui.EmpresasZonaVwFilter;
import gob.osinergmin.mdicommon.domain.ui.SupeCampRechCargaFilter;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller 
@RequestMapping("/actaInspeccion")
public class ActaInspeccionController {

    private static final Logger LOG = LoggerFactory.getLogger(ActaInspeccionController.class);
    
    @Inject
    private MaestroColumnaServiceNeg maestroColumnaServiceNeg;
    
    @Inject
    private EmpresasZonaServiceNeg empresasZonaServiceNeg; 
    
    @Autowired
    private MaestroColumnaServiceNeg maestroColumnaService;
    
    @Inject
    private ArchivoServiceNeg archivoServiceNeg;
    @Inject
    private UbigeoServiceNeg ubigeoServiceNeg;
    
    @RequestMapping(value = "/cargarAnios", method = RequestMethod.GET)
    public @ResponseBody List<MaestroColumnaDTO> cargarAnios(HttpSession session, HttpServletRequest request, Model model) {
        LOG.info("cargarAnios");
        
        List<MaestroColumnaDTO>  listaAnio = new ArrayList <MaestroColumnaDTO>(); 	
        try {
        	// Combo listadoAnio
            SupervisionDsrController supervision = new SupervisionDsrController();
            FechaHoraDTO fecha=supervision.obtenerFechayHoraSistema();
            int anioMaximo=(Integer.valueOf((fecha.getFecha()).substring(6, 10)))+1;
            
            MaestroColumnaDTO anio = null;
            
            for(int i=2015;i<=anioMaximo;i++){
            	anio = new MaestroColumnaDTO();
            	anio.setCodigo(String.valueOf(i));
            	anio.setDescripcion(String.valueOf(i));
            	listaAnio.add(anio);
            }
            
        } catch (Exception e) {
            LOG.error("error cargarAnios", e);
        }
        return listaAnio;
    }
    
    @RequestMapping(value = "/cargarTipoEmpresas", method = RequestMethod.GET)
    public @ResponseBody List<MaestroColumnaDTO> cargarTipoEmpresas(HttpSession session, HttpServletRequest request, Model model) {
        LOG.info("cargarTipoEmpresas");
        List<MaestroColumnaDTO> retorno=new ArrayList <MaestroColumnaDTO>();;
        List<MaestroColumnaDTO> listaTipo = new ArrayList <MaestroColumnaDTO>(); 	
        try {
        	listaTipo = maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_EMPRESA,Constantes.APLICACION_INPS);
            if(listaTipo!=null && listaTipo.size()>0)
            	retorno.addAll(listaTipo);
            
        } catch (Exception e) {
            LOG.error("error cargarTipoEmpresas", e);
        }
        return listaTipo;
    }
    
    @RequestMapping(value = "/findEmpresasZona", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> findEmpresasZona(EmpresasZonaVwFilter filtro, int rows, int page, HttpSession session, HttpServletRequest request,Model model) {
        LOG.info("findEmpresasZona");
        
        Map<String, Object> retorno = new HashMap<String, Object>();
        int indiceInicial = -1;
        int indiceFinal = -1;
        
        try {
            List<EmpresasZonaVwDTO> listadoPaginado = null;
            if(filtro.getDescripcionEmpresa()!=null && filtro.getDescripcionEmpresa()!="")
            	filtro.setDescripcionEmpresa(filtro.getDescripcionEmpresa().toUpperCase());
            
            List<EmpresasZonaVwDTO> listado = empresasZonaServiceNeg.listarEmpresasZona(filtro);
            if(listado!=null){
	            String identificador="";
	            for(EmpresasZonaVwDTO empresa:listado){
	            	if(identificador.equals(empresa.getIdEmpresa())){
	            		empresa.setDescripcionEmpresa("");
	            		empresa.setNumeroExpediente("");
	            		empresa.setRuc("");
	            		empresa.setTipo("");
	            	}else{
	            		identificador=empresa.getIdEmpresa();
	            	}
	            }
	            
	            Long contador = (long) listado.size();
	            Long numeroFilas = (contador / rows);
	
	            if ((contador % rows) > 0) {
	                numeroFilas = numeroFilas + 1L;
	            }
	            if (numeroFilas < page) {
	                page = numeroFilas.intValue();
	            }
	            if (page == 0) {
	                rows = 0;
	            }
	            indiceInicial = (page - 1) * rows;
	            indiceFinal = indiceInicial + rows;
	            listadoPaginado = listado.subList(indiceInicial > listado.size() ? listado.size() : indiceInicial, indiceFinal > listado.size() ? listado.size() : indiceFinal);
	            retorno.put("total", numeroFilas);
	            retorno.put("pagina", page);
	            retorno.put("registros", contador);
	            retorno.put("filas", listadoPaginado);
	            
            }else{
            	retorno.put("error", ConstantesWeb.mensajes.MSG_OPERATION_ERROR_LISTAR_EMPRESAS_ZONA);
            }
        } catch (Exception ex) {
            LOG.error("Error findEmpresasZona", ex);

        }
        return retorno;
    }
    
    public int validarRelesServicioDetaSupe(String idEmpresa, String anio, SupeCampRechCargaDTO supeCampRechCargaDTO, UsuarioDTO usuario){
    	int resultado=1;
    	try{
	    	String etapas=obtenerEtapas(0L, idEmpresa, anio, Long.valueOf(supeCampRechCargaDTO.getIdZona()));
	    	
			int existe=0;
			if(!etapas.equals("")){
				ConfiguracionRelesFilter filtro=new ConfiguracionRelesFilter();
	        	filtro.setEmpCodemp(idEmpresa);
	        	filtro.setAnio(anio);
	        	filtro.setIdZona(Long.valueOf(supeCampRechCargaDTO.getIdZona()));
	        	filtro.setIdSubestacion(0L);
	        	List<ConfiguracionRelesDTO> listaConfiguracionSE = empresasZonaServiceNeg.listarConfiguracionReles(filtro);
	        	
	        	List<ReleDTO> listaRelesServicio=new ArrayList<ReleDTO>();
	        	
	        	
	        	if(listaConfiguracionSE!=null && listaConfiguracionSE.size()>0){
		        	for(ConfiguracionRelesDTO configuracion : listaConfiguracionSE){
		        		String etapasSubEstacion="";
		        		if(configuracion.getDetaConfRelesList()!=null && configuracion.getDetaConfRelesList().size()>0){
		        			int contador=0;
			        		for(DetaConfRelesDTO detalleConf : configuracion.getDetaConfRelesList()){
			        			if(contador==0){
			        				etapasSubEstacion=etapasSubEstacion+detalleConf.getIdEtapa();
				    			}else{
				    				etapasSubEstacion=etapasSubEstacion+","+detalleConf.getIdEtapa();
				    			}
				    			contador++;
			            	}
		        		}
		        		List<ReleDTO> listaRelesWS = empresasZonaServiceNeg.listarReles(String.valueOf(configuracion.getIdSubestacion()), idEmpresa, anio, etapasSubEstacion);
		        		if(listaRelesWS!=null && listaRelesWS.size()>0){
		        			listaRelesServicio.addAll(listaRelesWS);
		        		}
		        	}
	        	}
	        		        	
				DetaSupeCampRechCargaFilter filtroDetaSupe= new DetaSupeCampRechCargaFilter();
				SupeCampRechCargaFilter filterSupeCamp = new SupeCampRechCargaFilter();
				filterSupeCamp.setIdSupeCampRechCarga(supeCampRechCargaDTO.getIdSupeCampRechCarga());
				filtroDetaSupe.setIdSupeCampRechCarga(filterSupeCamp);
				filtroDetaSupe.setEstado(Constantes.ESTADO_ACTIVO);
			    List<DetaSupeCampRechCargaDTO> listaDetaSupe = empresasZonaServiceNeg.listarDetaSupeCampRechCarga(filtroDetaSupe);
			    
			    if(listaRelesServicio!=null && listaRelesServicio.size()>0 && listaDetaSupe!=null){
					
					List<DetaSupeCampRechCargaDTO> listaRelesInsertar = new ArrayList<DetaSupeCampRechCargaDTO>();
					DetaSupeCampRechCargaDTO nuevoRele=null;
					List<DetaSupeCampRechCargaDTO> listaRelesActualizar = new ArrayList<DetaSupeCampRechCargaDTO>();
					DetaSupeCampRechCargaDTO releActualizar=null;
					// Reles a Insertar
					for(ReleDTO maestroReles: listaRelesServicio){
						existe=0;
						for(DetaSupeCampRechCargaDTO detalleSupe: listaDetaSupe){
							if(detalleSupe.getIdRele()==maestroReles.getIdRele()){
								existe=1;
								break;
							}
						}
						if(existe==0){
							// Inserta en DetaSupeCampRechCarga.
							nuevoRele = new DetaSupeCampRechCargaDTO();
							SupeCampRechCargaDTO supe = new SupeCampRechCargaDTO();
							supe.setIdSupeCampRechCarga(supeCampRechCargaDTO.getIdSupeCampRechCarga());
							nuevoRele.setIdSupeCampRechCarga(supe);
							nuevoRele.setIdRele(maestroReles.getIdRele());
			                nuevoRele.setEstado(Constantes.ESTADO_ACTIVO);
							listaRelesInsertar.add(nuevoRele);
						}
					}
					
					// Reles a Actualizar Estado='0';
					for(DetaSupeCampRechCargaDTO detalleSupe: listaDetaSupe){
						existe=0;
						for(ReleDTO maestroReles: listaRelesServicio){
							if(detalleSupe.getIdRele()==maestroReles.getIdRele()){
								existe=1;
								break;
							}
						}
						if(existe==0){
							// Actualizar en DetaSupeCampRechCarga.
							releActualizar = new DetaSupeCampRechCargaDTO();
							detalleSupe.setIdSupeCampRechCarga(detalleSupe.getIdSupeCampRechCarga());
							
							releActualizar.setIdDetaSupeCampRechCarga(detalleSupe.getIdDetaSupeCampRechCarga());
			                releActualizar.setEstado(Constantes.ESTADO_INACTIVO);
							listaRelesActualizar.add(releActualizar);
						}
					}
					if(listaRelesInsertar.size()>0 || listaRelesActualizar.size()>0){
	    				// Mandar a Método 
	    				resultado = empresasZonaServiceNeg.gestionarRelesDetSupe(listaRelesInsertar, listaRelesActualizar, false, usuario);
					}
				}
			}
    	}catch(Exception e){
    		LOG.error("error validarRelesServicioDetaSupe", e);
    	}
		return resultado;
    }
    
    @RequestMapping(value = "/validarAbrirRegistroActa", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> validarAbrirRegistroActa(String idEmpresa, String anio, Long idZona, Long idSupeCamp, HttpSession sesion, Model model, HttpServletRequest request) {
        LOG.info("validarAbrirRegistroActa");
        Map<String, Object> retorno = new HashMap<String, Object>();
        
        try {
        	ConfiguracionRelesFilter filtro = new ConfiguracionRelesFilter();
        	filtro.setEmpCodemp(idEmpresa);
        	filtro.setAnio(anio);
        	filtro.setIdSubestacion(0L);
        	filtro.setIdZona(idZona);
        	List<ConfiguracionRelesDTO> validacion=empresasZonaServiceNeg.listarConfiguracionReles(filtro);
        	
        	if(validacion!=null && validacion.size()>0){
        		if(idSupeCamp!=null){
        			SupeCampRechCargaDTO supeCamp = new SupeCampRechCargaDTO();
            		supeCamp.setIdSupeCampRechCarga(idSupeCamp);
            		supeCamp.setIdZona(String.valueOf(idZona));
            		int resultadoValidarRelesServicioDetaSupe=validarRelesServicioDetaSupe(idEmpresa, anio, supeCamp, Constantes.getUsuarioDTO(request));
            		
            		if(resultadoValidarRelesServicioDetaSupe==1){
            			retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            		}else if(resultadoValidarRelesServicioDetaSupe==0){
            			retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                    	retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_ERROR_VALIDAR_RELES_SERVICIO_DETA_SUP);
            		}
        		}else{
        			retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
        		}
        		
            }else{
            	// Verificar SI hay Cabecera y Detalle Reles para ponerlos a Estado 0
            	if(idSupeCamp!=null){
            		SupeCampRechCargaFilter filtroSupe = new SupeCampRechCargaFilter();
            		filtroSupe.setIdSupeCampRechCarga(idSupeCamp);
            		List<SupeCampRechCargaDTO> listaSupe = empresasZonaServiceNeg.listarSupeCampRechCarga(filtroSupe);
            		if(listaSupe!=null && listaSupe.size()>0){
            			listaSupe.get(0).setEstado(Constantes.ESTADO_INACTIVO);
            			SupeCampRechCargaDTO retornoSupe = empresasZonaServiceNeg.inactivarSupeCampYDetaSupeCamp
            					(listaSupe.get(0), Constantes.getUsuarioDTO(request), Constantes.TIPO_PANTALLA_CAMBIAR_ESTADO);
            		}
            	}
            	retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            	retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_ERROR_NO_CONFIGURACION);
            }
            
        } catch (Exception e) {
            LOG.error("error validarAbrirRegistroActa", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
        	retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_ERROR_ABRIR_REGISTRO_ACTA);
        }
        
        return retorno;
    }
    
    @RequestMapping(value = "/abrirRegistroActa", method = RequestMethod.POST)
    public String abrirRegistroActa(String idEmpresa, String anio, SupeCampRechCargaDTO supeCampRechCargaDTO, HttpSession sesion, Model model, HttpServletRequest request) {
        LOG.info("abrirRegistroActa");
        
        try{
        	List<MaestroColumnaDTO> listaHoras = new ArrayList<MaestroColumnaDTO>();
        	List<MaestroColumnaDTO> listaMinutos= new ArrayList<MaestroColumnaDTO>();
        	
        	for(int i=0;i<=23;i++){
        		MaestroColumnaDTO maestro = new MaestroColumnaDTO();
        		
        		if(i<=9){
        			maestro.setCodigo("0"+String.valueOf(i));
        			maestro.setDescripcion("0"+String.valueOf(i));
        		}else{
        			maestro.setDescripcion(String.valueOf(i));
        			maestro.setCodigo(String.valueOf(i));
        		}
        		listaHoras.add(maestro);
        	}
        	
        	for(int x=0;x<=59;x++){
        		MaestroColumnaDTO maestro = new MaestroColumnaDTO();
        		
        		if(x<=9){
        			maestro.setCodigo("0"+String.valueOf(x));
        			maestro.setDescripcion("0"+String.valueOf(x));
        		}else{
        			maestro.setDescripcion(String.valueOf(x));
        			maestro.setCodigo(String.valueOf(x));
        		}
        		listaMinutos.add(maestro);
        	}
        	
        	model.addAttribute("listaHoras", listaHoras);
        	model.addAttribute("listaMinutos", listaMinutos);
        	
        	SupervisionDsrController supervision = new SupervisionDsrController();
            FechaHoraDTO fecha=supervision.obtenerFechayHoraSistema();
            
        	model.addAttribute("fechaActual", fecha.getFecha());
        	
        	List<SupeCampRechCargaDTO> supeCamp = new ArrayList<SupeCampRechCargaDTO>();
        	SupeCampRechCargaDTO supervisionCampo = new SupeCampRechCargaDTO();
        	
        	if(supeCampRechCargaDTO.getIdSupeCampRechCarga()!=null){
	        	
	        	SupeCampRechCargaFilter filter = new SupeCampRechCargaFilter();
	        	filter.setIdSupeCampRechCarga(supeCampRechCargaDTO.getIdSupeCampRechCarga());
	        	supeCamp = empresasZonaServiceNeg.listarSupeCampRechCarga(filter);
	        	if(supeCamp!=null && supeCamp.size()>0){
	        		supervisionCampo=(SupeCampRechCargaDTO) supeCamp.get(0);
		        	model.addAttribute("comboDepartamento",supervisionCampo.getIdDepartamento());
		        	model.addAttribute("comboProvincia",supervisionCampo.getIdProvincia());
		        	model.addAttribute("comboDistrito",supervisionCampo.getIdDistrito());
	        	}
	        	if((supervisionCampo.getFechaInicio()==null || supervisionCampo.getFechaInicio().equals("")) &&
	        	   (supervisionCampo.getFechaFin()==null || supervisionCampo.getFechaFin().equals(""))){
	        		supervisionCampo.setFechaInicio(String.valueOf(fecha.getFecha()));
	        		supervisionCampo.setFechaFin(String.valueOf(fecha.getFecha()));
	        	}
	        	model.addAttribute("supeCamp",supervisionCampo);
	        }else{
	        	// Insertamos en Tabla NPS_SUPE_CAMP_RECH_CARGA
        		
                supeCampRechCargaDTO.setFlgCerrado(Constantes.ESTADO_INACTIVO);
                supeCampRechCargaDTO.setEstado(Constantes.ESTADO_ACTIVO);
	        	SupeCampRechCargaDTO respuesta= empresasZonaServiceNeg.insertarSupeCampRechCarga(idEmpresa, anio,supeCampRechCargaDTO, Constantes.getUsuarioDTO(request));
	        	
	        	respuesta.setFechaInicio(String.valueOf(fecha.getFecha()));
	        	respuesta.setFechaFin(String.valueOf(fecha.getFecha()));
	        	model.addAttribute("supeCamp",respuesta);
	        }
        	
        	
        	model.addAttribute("idEmpresaRA", idEmpresa);
            model.addAttribute("idAnioRA", anio);
            model.addAttribute("idZonaRA",supeCampRechCargaDTO.getIdZona());
            
        } catch (Exception e) {
            LOG.error("error abrirRegistroActa", e);
        }
        
        return ConstantesWeb.Navegacion.PAGE_INPS_REGISTRO_ACTA_INSPECCION;
    }
    
    @RequestMapping(value = "/abrirAdjuntarActa", method = RequestMethod.POST)
    public String abrirAdjuntarActa(String idSupeCampRechCarga, String numeroExpediente, String descripcionEmpresa, String anio, String ruc,
    		HttpSession sesion, Model model, HttpServletRequest request) {
        LOG.info("abrirAdjuntarActa");
        model.addAttribute("idSupeCampRechCargaActa", idSupeCampRechCarga);
        model.addAttribute("numeroExpedienteActa", numeroExpediente);
        model.addAttribute("descripcionEmpresaActa", descripcionEmpresa);
        model.addAttribute("anioActa", anio);
        model.addAttribute("rucActa", ruc);
        return ConstantesWeb.Navegacion.PAGE_INPS_ADJUNTAR_ACTA_INSPECCION;
    }
    
    @RequestMapping(value = "/listarZonas", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> listarZonas(String anio,HttpSession session, HttpServletRequest request, Model model) {
        LOG.info("listarZonas");
        Map<String, Object> retorno = new HashMap<String, Object>();
        List<ZonaDTO> listaZonas = new ArrayList<ZonaDTO>();
        
        try {
            List<ZonaDTO> zonas = empresasZonaServiceNeg.listarZonasXAnio(anio);
            
            if(zonas!=null && zonas.size()>0){
            	listaZonas.addAll(zonas);
            	retorno.put("listaZonas", listaZonas);
            	retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            }else{
            	retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            	retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_ERROR_LISTAR_ZONAS);
            }
            
        } catch (Exception e) {
            LOG.error("error listarZonas", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
        	retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_ERROR_LISTAR_ZONAS);
        }
        
        return retorno;
    }
    
    @RequestMapping(value = "/cargarSubEstaciones", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> cargarSubEstaciones(String idEmpresa, String anio, String idZona, HttpSession session, HttpServletRequest request, Model model) {
        LOG.info("cargarSubEstaciones");
        Map<String, Object> retorno = new HashMap<String, Object>();
        List<SubEstacionDTO> listaSubEstaciones = new ArrayList<SubEstacionDTO>();
        
        try {
            List<SubEstacionDTO> estaciones = empresasZonaServiceNeg.listarSubEstaciones(idEmpresa, anio, idZona);
            
            if(estaciones!=null && estaciones.size()>0){
            	listaSubEstaciones.addAll(estaciones);
            	retorno.put("listaSubEstaciones", listaSubEstaciones);
            	retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            }else{
            	retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            	retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_ERROR_LISTAR_SUB_ESTACIONES);
            }
            
        } catch (Exception e) {
            LOG.error("error cargarSubEstaciones", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
        	retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_ERROR_LISTAR_SUB_ESTACIONES);
        }
        
        return retorno;
    }
    
    public String obtenerEtapas(Long subEstacion, String idEmpresa ,String anio, Long idZona){
    	String etapas="";
    	ConfiguracionRelesFilter filtroConfiguracion = new ConfiguracionRelesFilter();
    	filtroConfiguracion.setAnio(anio);
    	filtroConfiguracion.setIdSubestacion(subEstacion);
    	filtroConfiguracion.setEmpCodemp(idEmpresa);
    	if(idZona!=null){
    		filtroConfiguracion.setIdZona(idZona);
    	}
    	List<ConfiguracionRelesDTO> listaConfiguracion = empresasZonaServiceNeg.listarConfiguracionReles(filtroConfiguracion);
    	if(listaConfiguracion!=null && listaConfiguracion.size()>0){
    		int contador=0;
    		for(ConfiguracionRelesDTO configuracion:listaConfiguracion){
	    		for(DetaConfRelesDTO detalleConfiguracion : configuracion.getDetaConfRelesList()){
	    			if(contador==0){
	    				etapas=etapas+detalleConfiguracion.getIdEtapa();
	    			}else{
	    				etapas=etapas+","+detalleConfiguracion.getIdEtapa();
	    			}
	    			contador++;
	        	}
    		}
    	}
    	return etapas;
    }
    
    @RequestMapping(value = "/listarReles", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> listarReles(String subEstacion, String idEmpresa, String anio, Long idSupeCamp,
    		HttpSession session, HttpServletRequest request, Model model) {
        LOG.info("listarReles");
        Map<String, Object> map = new HashMap<String, Object>();
        List<DetaSupeCampRechCargaDTO> retorno = new ArrayList<DetaSupeCampRechCargaDTO>();
        
        try {
        	// Obtener Lista de Etapas.
        	String etapas=obtenerEtapas(Long.valueOf(subEstacion), idEmpresa, anio,null);
        	if(!etapas.equals("")){
        		List<ReleDTO> listaReles = empresasZonaServiceNeg.listarReles(subEstacion, idEmpresa, anio, etapas);
                
                ArrayList<BigInteger> listaIdRele = new ArrayList<BigInteger>();
                if(listaReles!=null && listaReles.size()>0){
    	            for(ReleDTO rele : listaReles){
    	            	listaIdRele.add((BigInteger.valueOf(rele.getIdRele())));
    	            }
    	            
    	            DetaSupeCampRechCargaFilter filtro= new DetaSupeCampRechCargaFilter();
    	            filtro.setListaIdRele(listaIdRele);
    	            filtro.setEstado(Constantes.ESTADO_ACTIVO);
    	            
    	            SupeCampRechCargaFilter filterSupeCamp = new SupeCampRechCargaFilter();
    				filterSupeCamp.setIdSupeCampRechCarga(idSupeCamp);
    				filtro.setIdSupeCampRechCarga(filterSupeCamp);
    				
    	            List<DetaSupeCampRechCargaDTO> listaDetaSupe = empresasZonaServiceNeg.listarDetaSupeCampRechCarga(filtro);
    	            List<MaestroColumnaDTO> lista = maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_DEMANDA, Constantes.APLICACION_INPS);
    	            if(listaDetaSupe!=null && listaDetaSupe.size()>0){
    	            	for(DetaSupeCampRechCargaDTO deta : listaDetaSupe){
    	            		if(lista!=null && lista.size()>0){
    	            			for(MaestroColumnaDTO maestro: lista){
    	            				if(maestro.getCodigo().equals(Constantes.CODIGO_DEMANDA_MW)){
    	            					deta.setDemandaMwFecha(maestro.getDescripcion());
    	            				} else if(maestro.getCodigo().equals(Constantes.CODIGO_DEMANDA_MAX)){
    	            					deta.setDemandaMaximaHora(maestro.getDescripcion());
    	            				} else if(maestro.getCodigo().equals(Constantes.CODIGO_DEMANDA_MED)){
    	            					deta.setDemandaMediaHora(maestro.getDescripcion());
    	            				} else if(maestro.getCodigo().equals(Constantes.CODIGO_DEMANDA_MIN)){
    	            					deta.setDemandaMinimaHora(maestro.getDescripcion());
    	            				}
    	            			}
    	                    }
    	            		for(ReleDTO rele : listaReles){
    	            			if(deta.getIdRele()==rele.getIdRele()){
    	            				ReleServicioDTO releServicio = new ReleServicioDTO();
    	            				releServicio.setIdRele(rele.getIdRele());
    	            				releServicio.setCodigoRele(rele.getCodigoRele());
    	            				releServicio.setMarca(rele.getMarca());
    	            				releServicio.setModelo(rele.getModelo());
    	            				releServicio.setSerie(rele.getSerie());
    	            				releServicio.setSubEstacion(rele.getSubEstacion());
    	            				releServicio.setkV(rele.getkV());
    	            				releServicio.setAlimentador(rele.getAlimentador());
    	            				releServicio.setCodInterrupcion(rele.getCodInterrupcion());
    	            				releServicio.setFechaImplementacion(rele.getFechaImplementacion());
    	            				
    	            				releServicio.setEtapa(rele.getEtapa());
    	            				releServicio.setReleUmbralHz(rele.getReleUmbralHz());
    	            				releServicio.setReleUmbralS(rele.getReleUmbralS());
    	            				releServicio.setReleDerivadaHZ(rele.getReleDerivadaHZ());
    	            				releServicio.setReleDerivadaHZS(rele.getReleDerivadaHZS());
    	            				releServicio.setReleDerivadaS(rele.getReleDerivadaS());
    	            				releServicio.setDemandaMax(rele.getDemandaMax());
    	            				releServicio.setDemandaMed(rele.getDemandaMed());
    	            				releServicio.setDemandaMin(rele.getDemandaMin());
    	            				releServicio.setPotR(rele.getPotR());
    	            				deta.setReleServicio(releServicio);
    	            				break;
    	            			}
    	            		}
    	            	}
    	            }
    	            
    	            Collections.sort(listaDetaSupe, new Comparator<DetaSupeCampRechCargaDTO>() {
    	                @Override
    	                public int compare(DetaSupeCampRechCargaDTO etapa1, DetaSupeCampRechCargaDTO etapa2) {
    	                    return etapa1.getReleServicio().getEtapa().compareTo(etapa2.getReleServicio().getEtapa());
    	                }
    	            });
    	            
    	            retorno = (List<DetaSupeCampRechCargaDTO>) listaDetaSupe;
    	            
    	            map.put("listaDetaSupe", retorno);
    	            map.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
                }
            
            }else{
            	map.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                map.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_ERROR_NO_CONFIGURACION);
            }
        } catch (Exception e) {
            LOG.error("error listarReles", e);
            map.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            map.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_ERROR_LISTAR_RELES);
        }
        
        return map;
    }
    
    @RequestMapping(value = "/actualizarRegistroActa", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> actualizarRegistroActa(String tabSeleccionado, SupeCampRechCargaDTO supeCampRechCarga, HttpServletRequest request, HttpSession sesion, Model model){
        LOG.info("procesando actualizarRegistroActa");
        Map<String, Object> salida = new HashMap<String, Object>();
        
        try {
            SupeCampRechCargaDTO resultado = null;
        	
            resultado=empresasZonaServiceNeg.actualizarSupeCampRechCarga(supeCampRechCarga, Constantes.getUsuarioDTO(request), tabSeleccionado);
            if(resultado!=null && resultado.getIdSupeCampRechCarga()!=null){
            	salida.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
        		salida.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_ACTUALIZAR_REGISTRO_ACTA);
            }
            
        } catch (Exception e) {
        	salida.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
        	salida.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_ERROR_ACTUALIZAR_REGISTRO_ACTA);
            LOG.error("Error en actualizarRegistroActa,",e);
        }
        return salida;
    }
    
    @RequestMapping(value = "/actualizarFlagRegistroActa", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> actualizarFlagRegistroActa(String idSupeCampRechCarga, String idEmpresa, String anio, 
    		String estadoFlagRegistro, boolean terminarRegistroReles, HttpServletRequest request, HttpSession sesion, Model model){
        LOG.info("procesando actualizarFlagRegistroActa");
        Map<String, Object> salida = new HashMap<String, Object>();
        String mensajeExito="";
        String mensajeError="";
        try {
        	SupeCampRechCargaDTO supervisionCampo = new SupeCampRechCargaDTO();
        	// Terminar la Supervisión
            if(estadoFlagRegistro.equals(Constantes.TIPO_ESTADO_REGISTRO_ACTA_TERMINAR)){
            	boolean relesIncompletos=false;
            	boolean informacionInicialIncompleto=false;
            	
            	if(!terminarRegistroReles){
	                // Verificando Pestaña Información Inicial.
            		SupeCampRechCargaFilter filtroSupervision = new SupeCampRechCargaFilter();
            		filtroSupervision.setIdSupeCampRechCarga(Long.valueOf(idSupeCampRechCarga));
            		List<SupeCampRechCargaDTO> listaSupe = empresasZonaServiceNeg.listarSupeCampRechCarga(filtroSupervision);
            		if(listaSupe!=null && listaSupe.size()>0){
            			for(SupeCampRechCargaDTO supeCamp : listaSupe){
            				if(supeCamp.getFechaInicio()== null || (supeCamp.getFechaInicio().isEmpty())){informacionInicialIncompleto=true; break;}
            				if(supeCamp.getFechaFin()== null || (supeCamp.getFechaFin().isEmpty())){informacionInicialIncompleto=true; break;}
            				
            				if(supeCamp.getNombreSupervisorEmpresa()== null || (supeCamp.getNombreSupervisorEmpresa().equals(""))){informacionInicialIncompleto=true; break;}
            				if(supeCamp.getCargoSupervisorEmpresa()== null || (supeCamp.getCargoSupervisorEmpresa().equals(""))){informacionInicialIncompleto=true; break;}
            				if(supeCamp.getNombreSupervisorOsinergmin()== null || (supeCamp.getNombreSupervisorOsinergmin().equals(""))){informacionInicialIncompleto=true; break;}
            				if(supeCamp.getCargoSupervisorOsinergmin()== null || (supeCamp.getCargoSupervisorOsinergmin().equals(""))){informacionInicialIncompleto=true; break;}
            				if(supeCamp.getIdUbigeo()== null || (supeCamp.getIdUbigeo().equals(""))){informacionInicialIncompleto=true; break;}
            			}
            		}
            		
            		if(!informacionInicialIncompleto){
	            		// Verificando Reles.
		                DetaSupeCampRechCargaFilter filtro= new DetaSupeCampRechCargaFilter();
		                SupeCampRechCargaFilter filtroSupe = new SupeCampRechCargaFilter();
		                filtroSupe.setIdSupeCampRechCarga(Long.valueOf(idSupeCampRechCarga));
		                filtro.setIdSupeCampRechCarga(filtroSupe);
		                filtro.setEstado(Constantes.ESTADO_ACTIVO);
		                List<DetaSupeCampRechCargaDTO> listaDetaSupe = empresasZonaServiceNeg.listarDetaSupeCampRechCarga(filtro);
		            	if(listaDetaSupe!=null && listaDetaSupe.size()>0){
		            		for(DetaSupeCampRechCargaDTO rele : listaDetaSupe){
		                		if(rele.getReleUmbralHz()== null || (rele.getReleUmbralHz().equals(BigDecimal.ZERO))){relesIncompletos=true; break;}
		                		if(rele.getReleUmbralS()== null || (rele.getReleUmbralS().equals(BigDecimal.ZERO))){relesIncompletos=true; break;}
		                		if(rele.getReleDerivadaHz()== null || (rele.getReleDerivadaHz().equals(BigDecimal.ZERO))){relesIncompletos=true; break;}
		                		if(rele.getReleDerivadaHzs()== null || (rele.getReleDerivadaHzs().equals(BigDecimal.ZERO))){relesIncompletos=true; break;}
		                		if(rele.getReleDerivadaS()== null || (rele.getReleDerivadaS().equals(BigDecimal.ZERO))){relesIncompletos=true; break;}
		                		if(rele.getDemandaMaxima()== null || (rele.getDemandaMaxima().equals(BigDecimal.ZERO))){relesIncompletos=true; break;}
		                		if(rele.getDemandaMedia()== null || (rele.getDemandaMedia().equals(BigDecimal.ZERO))){relesIncompletos=true; break;}
		                		if(rele.getDemandaMinima()== null || (rele.getDemandaMinima().equals(BigDecimal.ZERO))){relesIncompletos=true; break;}
		                		if(rele.getDemandaMw()== null || (rele.getDemandaMw().equals(BigDecimal.ZERO))){relesIncompletos=true; break;}
		                		
		                		//if(rele.getObservaciones()== null || (rele.getObservaciones().equals(""))){relesIncompletos=true; break;}
		                		if(rele.getHora()== null){relesIncompletos=true; break;}
		                		
		                		/*if(rele.getFlgFiscalizado()== null){relesIncompletos=true; break;}
		                		if(rele.getFlgExisteReleUmbral()== null){relesIncompletos=true; break;}
		                		if(rele.getFlgExisteReleDerivada()== null){relesIncompletos=true; break;}
		                		if(rele.getFlgAjusteReleUmbral()== null){relesIncompletos=true; break;}
		                		if(rele.getFlgAjusteReleDerivada()== null){relesIncompletos=true; break;}
		                		if(rele.getFlgOtroAjusteUmbral()== null){relesIncompletos=true; break;}
		                		if(rele.getFlgOtroAjusteDerivada()== null){relesIncompletos=true; break;}
		                		if(rele.getFlgProtocoloUmbral()== null){relesIncompletos=true; break;}
		                		if(rele.getFlgProtocoloDerivada()== null){relesIncompletos=true; break;}*/
		                		
		                		if(rele.getPotr()== null || (rele.getPotr().equals(BigDecimal.ZERO))){relesIncompletos=true; break;}
		                	}
		            	}
            		}
            	}
            	
            	if((relesIncompletos || informacionInicialIncompleto) && !terminarRegistroReles){
            		if(informacionInicialIncompleto){
            			salida.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.RESTRICT_INF_ADICIONAL);
                		salida.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_RESTRICT_COMPLETAR_INFORMACION_ADICIONAL);
            		}else if(relesIncompletos){
            			salida.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.RESTRICT);
                		salida.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_RESTRICT_TERMINAR_REGISTRO_ACTA);
            		}
            	}else{
            		supervisionCampo.setFlgCerrado(Constantes.ESTADO_ACTIVO);
                	mensajeExito=ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_TERMINAR_REGISTRO_ACTA;
                	mensajeError=ConstantesWeb.mensajes.MSG_OPERATION_ERROR_TERMINAR_REGISTRO_ACTA;
                	
                	supervisionCampo.setIdSupeCampRechCarga(Long.valueOf(idSupeCampRechCarga));
                    SupeCampRechCargaDTO retorno = empresasZonaServiceNeg.actualizarSupeCampRechCarga(supervisionCampo, Constantes.getUsuarioDTO(request), Constantes.TIPO_FLAG_REGISTRO);
                    
                    if(retorno!=null){
                    	salida.put("supeCampRechCargaDTO", retorno);
                    	salida.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.SUCCESS);
                    	salida.put(ConstantesWeb.VV_MENSAJE, mensajeExito);
                    }
            	}
            	// Reabrir la Supervisión.
            }else if(estadoFlagRegistro.equals(Constantes.TIPO_ESTADO_REGISTRO_ACTA_REABRIR)){
            	supervisionCampo.setFlgCerrado(Constantes.ESTADO_INACTIVO);
            	mensajeExito=ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_REABRIR_REGISTRO_ACTA;
            	mensajeError=ConstantesWeb.mensajes.MSG_OPERATION_ERROR_REABRIR_REGISTRO_ACTA;
            	
            	supervisionCampo.setIdSupeCampRechCarga(Long.valueOf(idSupeCampRechCarga));
                SupeCampRechCargaDTO retorno = empresasZonaServiceNeg.actualizarSupeCampRechCarga(supervisionCampo, Constantes.getUsuarioDTO(request), Constantes.TIPO_FLAG_REGISTRO);
                
                if(retorno!=null){
                	salida.put("supeCampRechCargaDTO", retorno);
                	salida.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
                	salida.put(ConstantesWeb.VV_MENSAJE, mensajeExito);
                }
            }
            
            
        } catch (Exception e) {
        	salida.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
        	salida.put(ConstantesWeb.VV_MENSAJE, mensajeError);
        	LOG.error("Error en actualizarFlagRegistroActa,",e);
        }
        return salida;
    }
    
    @RequestMapping(value = "/actualizarRele", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> actualizarRele(DetaSupeCampRechCargaDTO detaSupeCampRechCarga, HttpServletRequest request, HttpSession sesion, Model model){
        LOG.info("procesando actualizarRele");
        Map<String, Object> salida = new HashMap<String, Object>();
        
        try {
            DetaSupeCampRechCargaDTO resultado = null;
            detaSupeCampRechCarga.setEstado(Constantes.ESTADO_ACTIVO);
            resultado=empresasZonaServiceNeg.actualizarDetaSupeCampRechCarga(detaSupeCampRechCarga, Constantes.getUsuarioDTO(request));
            if(resultado!=null && resultado.getIdSupeCampRechCarga()!=null){
            	salida.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
        		salida.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_ACTUALIZAR_REGISTRO_ACTA);
            }else{
            	salida.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            	salida.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_ERROR_ACTUALIZAR_REGISTRO_ACTA);
            }
            
        } catch (Exception e) {
        	salida.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
        	salida.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_ERROR_ACTUALIZAR_REGISTRO_ACTA);
            LOG.error("Error en actualizarRele,",e);
        }
        return salida;
    }
    
    @RequestMapping(value = "/adjuntarActaInspeccion", method = RequestMethod.POST)
    public void adjuntarActaInspeccion(@RequestParam("acta") MultipartFile file,DocumentoAdjuntoDTO documentoAdjuntoDTO, 
    		Long idSupeCampRechCargaActa, String numeroExpedienteActa, String descripcionEmpresaActa, String anioActa, String rucActa, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
        String validaDocumento = "";
        response.setContentType("text/html;charset=utf-8");
        DocumentoAdjuntoDTO docEnviado= new DocumentoAdjuntoDTO();
        
        try {
            validaDocumento = validaAdjuntarActa(file,Constantes.CONSTANTE_TIPOS_ARCHIVOS_ACTA_INSPECCION, new Long(40));
            if(!validaDocumento.trim().isEmpty()){
                response.getWriter().write(validaDocumento);
                return;
            }
            documentoAdjuntoDTO.setArchivoAdjunto(file.getBytes());
            documentoAdjuntoDTO.setNombreArchivo(file.getOriginalFilename().toUpperCase());
            
            List<MaestroColumnaDTO> tipoDocumentoList = maestroColumnaServiceNeg.findByDominioAplicacionDescripcion(Constantes.DOMINIO_DOCUMENTO,Constantes.APLICACION_INPS, Constantes.DOCUMENTO_ACTA);
	        
            documentoAdjuntoDTO.setIdTipoDocumento(tipoDocumentoList.get(0));
            //documentoAdjuntoDTO.setDescripcionDocumento(documentoAdjuntoDTO.getDescripcionDocumento().toUpperCase());
            
            ExpedienteDTO expedienteDTO = new ExpedienteDTO();
            expedienteDTO.setEmpresaSupervisada(new EmpresaSupDTO());
            expedienteDTO.setOrdenServicio(new OrdenServicioDTO());
            expedienteDTO.setFlujoSiged(new FlujoSigedDTO());
            
            expedienteDTO.setAsuntoSiged(Constantes.CONSTANTE_NOMBRE_PROCESO_DSE + " - " + descripcionEmpresaActa + " - " + anioActa);
            //expedienteDTO.getEmpresaSupervisada().setIdEmpresaSupervisada(idEmpresaSupervisada);
            //expedienteDTO.getFlujoSiged().setCodigoSiged(codigoSiged);
            //expedienteDTO.setIdExpediente(idExpediente);
            expedienteDTO.setNumeroExpediente(numeroExpedienteActa);
            //expedienteDTO.getOrdenServicio().setIdOrdenServicio(idOrdenServicio);
            
            
            SupeCampRechCargaDTO supe = new SupeCampRechCargaDTO();
            supe.setIdSupeCampRechCarga(idSupeCampRechCargaActa);
            
            docEnviado=empresasZonaServiceNeg.enviarActaSiged(documentoAdjuntoDTO,expedienteDTO,Constantes.getIDPERSONALSIGED(request), descripcionEmpresaActa, rucActa);
            
            if(docEnviado.getEstado().equals(Constantes.FLAG_REGISTRADO_SI)){
            	SupeCampRechCargaDTO retorno = null;
            	
            	List<DocumentoAdjuntoDTO> listado=archivoServiceNeg.listarDocumentosSiged(numeroExpedienteActa);
            	for(DocumentoAdjuntoDTO documento : listado){
            		if(documento.getIdDocumento().longValue()==docEnviado.getIdDocumento().longValue()){
            			supe.setIdActaDoc(String.valueOf(documento.getIdArchivo()));
            			break;
            		}
            	}
            	
            	supe.setNombreActaDoc(documentoAdjuntoDTO.getNombreArchivo());
            	retorno = empresasZonaServiceNeg.actualizarSupeCampRechCarga(supe, Constantes.getUsuarioDTO(request), Constantes.TIPO_PANTALLA_ADJUNTAR);
            	if(retorno!=null){
            		response.getWriter().write("{\"error\":false,\"mensaje\":\""+ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_CARGAR_DOCUMENTO+"\"}");
            	}
            }else{
            	response.getWriter().write("{\"error\":true,\"mensaje\":\""+docEnviado.getComentario()+"\"}");
            }
            
            return;
        } catch (Exception e) {
            LOG.error("Error subiendo archivo BL", e);
            try {
                response.getWriter()
                .write("{\"error\":true,\"mensaje\":\""+e.getMessage()+"\"}");
	        } catch (IOException ex) {
	                LOG.debug("error al escribir en response", ex);
	        }
	            return;
        }
    }
    
    private String validaAdjuntarActa(MultipartFile file,String extenciones,Long sizeMax){
    	LOG.info("validaAdjuntarActa");
	    String mensaje = "";
            try {
                String[] lstExtensiones = extenciones.split("\\|");
                if (!Utiles.validaFormatoPermitido(lstExtensiones, file.getOriginalFilename())) {
                    mensaje="{\"error\":true,\"mensaje\":\"Formato no permitido. ( Extenciones permitidas: "+ extenciones.replace(".", "").replace("|", ", ") + ") \"}";
	            return mensaje;
	        }
                if(sizeMax!=null){
                    Long longSizeMax=sizeMax*Constantes.VALOR_MEGA_BYTE;
                    if (file.getSize() > longSizeMax) {
                        mensaje="{\"error\":true,\"mensaje\":\"No se puede adjuntar un archivo que supera los "+sizeMax+"MB, revise el tama&ntilde;o del Acta que intenta adjuntar.\"}";
                        return mensaje;
                    }
                }
            } catch (Exception e) {
                LOG.error("Error validaAdjuntarActa", e);
            }
	    return mensaje;
    }
    
 
    @RequestMapping(value="/imprimirPlantilla",method= RequestMethod.GET)
    public void imprimirPlantilla(int idSubestacion,String idEmpresa,String anio, boolean esPlantillas, Long idZona, 
    		String descripcionEmpresa,HttpSession session, HttpServletRequest request,HttpServletResponse response){
    
    	System.out.println("DATOS:::"+idSubestacion+" "+idEmpresa+" "+anio+" "+esPlantillas);
    	try {  
         
        List<ReleDTO> lReleDTO = new ArrayList<ReleDTO>();
        SupeCampRechCargaDTO objSupeCampRechCargaDTO = new SupeCampRechCargaDTO();
        List<DetaSupeCampRechCargaDTO> lDetaSupeCampRechCargaDTO = new ArrayList<DetaSupeCampRechCargaDTO>();
        
        ConfiguracionRelesFilter filtro = new ConfiguracionRelesFilter();
    	filtro.setEmpCodemp(idEmpresa);
    	filtro.setAnio(anio);
    	filtro.setIdSubestacion(0L);
    	filtro.setIdZona(idZona);
    	List<ConfiguracionRelesDTO> lConfiguracionRelesDTO=empresasZonaServiceNeg.listarConfiguracionReles(filtro);
    	if(lConfiguracionRelesDTO!=null && lConfiguracionRelesDTO.size()>0){
	    	//String cadena="";
    		//int contador=0;
	    	//if(lConfiguracionRelesDTO.size()>0){
		    	/*for(ConfiguracionRelesDTO dto: lConfiguracionRelesDTO){
		    		if(contador==0){
		    			cadena=cadena+dto.getIdSubestacion();
	    			}else{
	    				cadena=cadena+","+dto.getIdSubestacion();
	    			}
	    			contador++;
		    	}*/

            		for(ConfiguracionRelesDTO configuracion : lConfiguracionRelesDTO){
            			String etapasSubEstacion="";
            			if(configuracion.getDetaConfRelesList()!=null && configuracion.getDetaConfRelesList().size()>0){
		        			int contador=0;
			        		for(DetaConfRelesDTO detalleConf : configuracion.getDetaConfRelesList()){
			        			if(contador==0){
			        				etapasSubEstacion=etapasSubEstacion+detalleConf.getIdEtapa();
				    			}else{
				    				etapasSubEstacion=etapasSubEstacion+","+detalleConf.getIdEtapa();
				    			}
				    			contador++;
			            	}
		        		}
		        		List<ReleDTO> listaRelesWS = empresasZonaServiceNeg.listarReles(String.valueOf(configuracion.getIdSubestacion()), idEmpresa, anio, etapasSubEstacion);
		        		lReleDTO.addAll(listaRelesWS);
	    	}
	        
	        if(lReleDTO!=null && lReleDTO.size()>0){
	        	for(ReleDTO rele:lReleDTO){
	                List<MaestroColumnaDTO> datosDemandaRele = maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_DEMANDA, Constantes.APLICACION_INPS);
	                if(datosDemandaRele!=null && datosDemandaRele.size()>0){
	                	for(MaestroColumnaDTO maestro: datosDemandaRele){
	                		if(maestro.getCodigo().equals(Constantes.CODIGO_DEMANDA_MW)){
	                			rele.setDemandaMwFecha(maestro.getDescripcion());
	                		} else if(maestro.getCodigo().equals(Constantes.CODIGO_DEMANDA_MAX)){
	                			rele.setDemandaMaximaHora(maestro.getDescripcion());
	                		} else if(maestro.getCodigo().equals(Constantes.CODIGO_DEMANDA_MED)){
	                			rele.setDemandaMediaHora(maestro.getDescripcion());
	                		} else if(maestro.getCodigo().equals(Constantes.CODIGO_DEMANDA_MIN)){
	                			rele.setDemandaMinimaHora(maestro.getDescripcion());
	                		}
	                	}
	                }       	
	            }
	        }
	        
	        objSupeCampRechCargaDTO.setNombrEmpresa(descripcionEmpresa);
		   		lDetaSupeCampRechCargaDTO = null; //VACIO
		   		
		   	empresasZonaServiceNeg.ReporteActaInspeccion(lReleDTO, request,response, session, esPlantillas, objSupeCampRechCargaDTO, lDetaSupeCampRechCargaDTO);
        
    	}else{
        	LOG.info("no hay configuracion--->");
        }       	
   		
    	}catch (Exception ex) {
            LOG.info("error descargaArchivoSiged--->"+ex.getMessage());
            throw new RuntimeException("IOError writing file to output stream");
        }        
        
 
    }
    
    @RequestMapping(value="/imprimirActaInspeccion",method= RequestMethod.GET)
    public void imprimirActaInspeccion(int subEstacion,String idEmpresa,String anio,Long idSupeCampRechCarga,
    		boolean esPlantilla, Long idZona,String descripcionEmpresa, HttpSession session, HttpServletRequest request,HttpServletResponse response){
    	System.out.println("DATOS:::"+subEstacion+" "+idEmpresa+" "+anio+" "+esPlantilla+" ID_EMP "+idSupeCampRechCarga);
    	List<ReleDTO> lReleDTO = new ArrayList<ReleDTO>();
        SupeCampRechCargaDTO objSupeCampRechCargaDTO = new SupeCampRechCargaDTO();
        try{        	
            String etapas=obtenerEtapas(Long.valueOf(subEstacion), idEmpresa, anio,idZona);
            LOG.info("Etapas: "+ etapas);
            ConfiguracionRelesFilter filtroConf = new ConfiguracionRelesFilter();
            filtroConf.setEmpCodemp(idEmpresa);
            filtroConf.setAnio(anio);
            filtroConf.setIdSubestacion(0L);
            filtroConf.setIdZona(idZona);
            List<ConfiguracionRelesDTO> lConfiguracionRelesDTO=empresasZonaServiceNeg.listarConfiguracionReles(filtroConf);
            if(lConfiguracionRelesDTO!=null && lConfiguracionRelesDTO.size()>0){
            	//String cadena="";
            	//int contador=0;
            	//if(lConfiguracionRelesDTO.size()>0){
        	    	/*for(ConfiguracionRelesDTO dto: lConfiguracionRelesDTO){
        	    		if(contador==0){
        	    			cadena=cadena+dto.getIdSubestacion();
            			}else{
            				cadena=cadena+","+dto.getIdSubestacion();
            			}
            			contador++;
        	    	}*/
            		for(ConfiguracionRelesDTO configuracion : lConfiguracionRelesDTO){
            			String etapasSubEstacion="";
            			if(configuracion.getDetaConfRelesList()!=null && configuracion.getDetaConfRelesList().size()>0){
		        			int contador=0;
			        		for(DetaConfRelesDTO detalleConf : configuracion.getDetaConfRelesList()){
			        			if(contador==0){
			        				etapasSubEstacion=etapasSubEstacion+detalleConf.getIdEtapa();
				    			}else{
				    				etapasSubEstacion=etapasSubEstacion+","+detalleConf.getIdEtapa();
				    			}
				    			contador++;
			            	}
		        		}
		        		List<ReleDTO> listaRelesWS = empresasZonaServiceNeg.listarReles(String.valueOf(configuracion.getIdSubestacion()), idEmpresa, anio, etapasSubEstacion);
		        		lReleDTO.addAll(listaRelesWS);
            	}
            	
                //lReleDTO = empresasZonaServiceNeg.listarReles(Long.valueOf(subEstacion).toString(), idEmpresa, anio,etapas);
                LOG.info("lReleDTO: "+ lReleDTO);
                for(ReleDTO rele:lReleDTO){
                    List<MaestroColumnaDTO> datosDemandaRele = maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_DEMANDA, Constantes.APLICACION_INPS);
                    if(datosDemandaRele!=null && datosDemandaRele.size()>0){
                    	for(MaestroColumnaDTO maestro: datosDemandaRele){
                    		if(maestro.getCodigo().equals(Constantes.CODIGO_DEMANDA_MW)){
                    			rele.setDemandaMwFecha(maestro.getDescripcion());
                    		} else if(maestro.getCodigo().equals(Constantes.CODIGO_DEMANDA_MAX)){
                    			rele.setDemandaMaximaHora(maestro.getDescripcion());
                    		} else if(maestro.getCodigo().equals(Constantes.CODIGO_DEMANDA_MED)){
                    			rele.setDemandaMediaHora(maestro.getDescripcion());
                    		} else if(maestro.getCodigo().equals(Constantes.CODIGO_DEMANDA_MIN)){
                    			rele.setDemandaMinimaHora(maestro.getDescripcion());
                    		}
                    	}
                    }       	
                }
            List<DetaSupeCampRechCargaDTO> lDetaSupeCampRechCargaDTO = new ArrayList<DetaSupeCampRechCargaDTO>();
            List<DetaSupeCampRechCargaRepDTO> lDetaSupeCampRechCargaRepDTO = new ArrayList<DetaSupeCampRechCargaRepDTO>();
            
            DetaSupeCampRechCargaFilter filtro = new DetaSupeCampRechCargaFilter();
            SupeCampRechCargaFilter idSupeCampRechCargaFilter = new SupeCampRechCargaFilter();
            idSupeCampRechCargaFilter.setIdSupeCampRechCarga(idSupeCampRechCarga);
            filtro.setIdSupeCampRechCarga(idSupeCampRechCargaFilter);  
            filtro.setEstado(Constantes.ESTADO_ACTIVO);
            lDetaSupeCampRechCargaDTO = empresasZonaServiceNeg.listarDetaSupeCampRechCarga(filtro);
            
            for(DetaSupeCampRechCargaDTO detaSupe:lDetaSupeCampRechCargaDTO){
            	for(ReleDTO rele:lReleDTO){         		
            		if(detaSupe.getIdRele()==rele.getIdRele()){
            			ReleServicioDTO releServicio= new ReleServicioDTO();
            			releServicio = DetaSupeCampRechCargaBuilder.reletorele(rele);  			
            			detaSupe.setReleServicio(releServicio);
            		}
            	}
            }
            
            for(DetaSupeCampRechCargaDTO deta:lDetaSupeCampRechCargaDTO){
            	DetaSupeCampRechCargaRepDTO reporte = new DetaSupeCampRechCargaRepDTO();
            	reporte = DetaSupeCampRechCargaBuilder.reletoreporte(deta);
            	lDetaSupeCampRechCargaRepDTO.add(reporte);
            }

                   	
            	if(esPlantilla){
       		   		objSupeCampRechCargaDTO = null; //VACIO
       		   		lDetaSupeCampRechCargaDTO = null; //VACIO
            	}
            	else{
            		objSupeCampRechCargaDTO = null; //VACIO
            		objSupeCampRechCargaDTO =empresasZonaServiceNeg.obtenerRegistroEmpresa(idSupeCampRechCarga);  //llenar de la BD
//       		   		lDetaSupeCampRechCargaDTO = null; //llenar de la BD	   	
            	}
            	
             
            	   List<DepartamentoDTO> departamentos = ubigeoServiceNeg.obtenerDepartamentos();
            	   for(DepartamentoDTO dpto : departamentos){
            		   if(dpto.getIdDepartamento().equals(objSupeCampRechCargaDTO.getIdDepartamento())){
            			   objSupeCampRechCargaDTO.setDescripcionDepartamento(dpto.getNombre());
            		   }
            	   }
            	   
            	   List<ProvinciaDTO> provincia = ubigeoServiceNeg.obtenerProvincias(objSupeCampRechCargaDTO.getIdDepartamento());
            	   for(ProvinciaDTO prov : provincia){
            		   if(prov.getIdProvincia().equals(objSupeCampRechCargaDTO.getIdProvincia())){
            			   objSupeCampRechCargaDTO.setDescripcionProvincia(prov.getNombre());
            		   }
            	   }
            	   
            	   List<DistritoDTO> distrito = ubigeoServiceNeg.obtenerDistritos(objSupeCampRechCargaDTO.getIdDepartamento(), objSupeCampRechCargaDTO.getIdProvincia());
            	   for(DistritoDTO dist : distrito){
            		   if(dist.getIdDistrito().equals(objSupeCampRechCargaDTO.getIdDistrito())){
            			   objSupeCampRechCargaDTO.setDescripcionDistrito(dist.getNombre());
            		   }
            	   }
            	   
            	   objSupeCampRechCargaDTO.setNombrEmpresa(descripcionEmpresa);
       		   	empresasZonaServiceNeg.ReporteActaInspeccionRep(lReleDTO, request,response, session, esPlantilla, objSupeCampRechCargaDTO, lDetaSupeCampRechCargaRepDTO);
       		   	
            }else{
            	LOG.info("no hay configuracion--->");
            }
       
        }catch (Exception ex) {
            LOG.info("error descargaArchivoSiged--->"+ex.getMessage());
            throw new RuntimeException("IOError writing file to output stream");
        }        
        
 
    }    
    
    @RequestMapping(value = "/abrirGenerarActaInspeccion", method = RequestMethod.POST)
    public String abrirGenerarActaInspeccion( int subEstacion,String idEmpresa, String empresa,String anio,String idZona,String idSupeCampRechCarga,boolean esPlantilla,String descripcionZona,HttpSession sesion, Model model, HttpServletRequest request) {
        LOG.info("abrirGenerarActaInspeccion");
      	model.addAttribute("subEstacion", subEstacion);
        model.addAttribute("idEmpresa", idEmpresa);
        model.addAttribute("empresa",empresa);
        model.addAttribute("anio", anio);
        model.addAttribute("idZona", idZona);
        model.addAttribute("idSupeCampRechCarga", idSupeCampRechCarga);
        model.addAttribute("descripcionZona", descripcionZona);
        
        System.out.println("DATOS::: ANIO"+anio+" ZONAA"+idZona+ " subEst"+subEstacion);
        return ConstantesWeb.Navegacion.PAGE_INPS_GENERAR_ACTA_INSPECCION;
    }
    
    @RequestMapping(value = "/cargarEtapas", method = RequestMethod.POST)
    public @ResponseBody List<EtapaDTO> cargarEtapas(String idEmpresa, String anio, String idSubEstacion, String[] listaEtapas, HttpSession session, HttpServletRequest request, Model model) { 
       
    	
    	List<EtapaDTO> retorno = new ArrayList<EtapaDTO>();
        try {
            List<EtapaDTO> etapas = empresasZonaServiceNeg.listarEtapas(idEmpresa, anio, idSubEstacion);
            if(etapas!=null && etapas.size()>0)
            	retorno.addAll(etapas);
            
        } catch (Exception e) {
            LOG.error("error cargarEtapas", e);
        }
     
        return retorno;
    }
    
    @RequestMapping(value = "/cargarEtapasConfiguradas", method = RequestMethod.POST)
    // Test
    //public @ResponseBody List<DetaConfRelesDTO> cargarEtapasConfiguradas(String idEmpresa, String anio, String idSubEstacion, HttpSession session, HttpServletRequest request, Model model) {
    public @ResponseBody List<DetaConfRelesDTO> cargarEtapasConfiguradas(String idEmpresa, String anio, String idSubEstacion, Long idZona, HttpSession session, HttpServletRequest request, Model model) {
    // Test
       List<DetaConfRelesDTO> retorno = null;
        try {
        	if(idEmpresa!=null && anio!=null){
        		retorno = new ArrayList<DetaConfRelesDTO>();
//        		List<EtapaDTO> etapas = empresasZonaServiceNeg.listarEtapasConfiguradas(idEmpresa, anio, idSubEstacion);
        		// Test
        		//List<DetaConfRelesDTO> etpByEmpresaByAnio = empresasZonaServiceNeg.findAllEtapaByEmpresaByAnio(idEmpresa, anio);
        		List<DetaConfRelesDTO> etpByEmpresaByAnio = empresasZonaServiceNeg.findAllEtapaByEmpresaByAnio(idEmpresa, anio, idZona);
        		// Test
                if(etpByEmpresaByAnio!=null && etpByEmpresaByAnio.size()>0)
                	retorno.addAll(etpByEmpresaByAnio);
        	}            
            
        } catch (Exception e) {
            LOG.error("error cargarEtapas", e);
        }
     
        return retorno;
    }   
    
    
    @RequestMapping(value = "/guardarConfiguracionReles", method = {RequestMethod.POST})
    public @ResponseBody
    Map<String, Object> guardarConfiguracionReles(String idSupeCampR,String anio,String idEmpresa, String empresa,Long idZona,String idSubestacion, String idEtapas, HttpSession session, HttpServletRequest request) throws SupeCampRechCargaException {
        LOG.info("guardarConfiguracionReles");
        Map<String, Object> retorno = new HashMap<String, Object>();
        ConfiguracionRelesDTO configuracionRele = new ConfiguracionRelesDTO();
        DetaConfRelesDTO objDetaConfRelesDTO = new DetaConfRelesDTO();
        ConfiguracionRelesDTO resultado = new ConfiguracionRelesDTO();
        boolean relesIncompletos=false;
        LOG.info("ID_SUPE_ "+idSupeCampR);
        try {

        	/*Valida configuración registrada en rele*/
        	if(!idSupeCampR.equals("null")){
        		List<EtapaDTO> etpByEmpresaByAnioBySubEs = empresasZonaServiceNeg.listarEtapasConfiguradas(idEmpresa, anio,idSubestacion);
        		if(etpByEmpresaByAnioBySubEs!=null){
        			String etapasSubEstacion="";
            		int contador=0;
            		for(EtapaDTO etp : etpByEmpresaByAnioBySubEs){
            			if(contador==0){
            				etapasSubEstacion=etapasSubEstacion+etp.getIdEtapa();
    	    			}else{
    	    				etapasSubEstacion=etapasSubEstacion+","+etp.getIdEtapa();
    	    			}
    	    			contador++;
                	}        		
            		List<ReleDTO> listaRelesWS = empresasZonaServiceNeg.listarReles(String.valueOf(idSubestacion), idEmpresa, anio, etapasSubEstacion);
            		DetaSupeCampRechCargaFilter filtroDetaSupe= new DetaSupeCampRechCargaFilter();
    				SupeCampRechCargaFilter filterSupeCamp = new SupeCampRechCargaFilter();
    				filterSupeCamp.setIdSupeCampRechCarga(new Long(idSupeCampR));
    				filtroDetaSupe.setIdSupeCampRechCarga(filterSupeCamp);
    				filtroDetaSupe.setEstado(Constantes.ESTADO_ACTIVO);
    			    List<DetaSupeCampRechCargaDTO> listaRelesBD = empresasZonaServiceNeg.listarDetaSupeCampRechCarga(filtroDetaSupe);
    			    
    			    for(ReleDTO relews:listaRelesWS){
    			    	for(DetaSupeCampRechCargaDTO rele:listaRelesBD){
    			    		if(relews.getIdRele() == rele.getIdRele()){
    			    			/*Logica de Hernan*/  			

    			                		if(rele.getReleUmbralHz()!= null && (!rele.getReleUmbralHz().equals(BigDecimal.ZERO))){relesIncompletos=true; break;}
    			                		if(rele.getReleUmbralS()!= null && (!rele.getReleUmbralS().equals(BigDecimal.ZERO))){relesIncompletos=true; break;}
    			                		if(rele.getReleDerivadaHz()!= null && (!rele.getReleDerivadaHz().equals(BigDecimal.ZERO))){relesIncompletos=true; break;}
    			                		if(rele.getReleDerivadaHzs()!= null && (!rele.getReleDerivadaHzs().equals(BigDecimal.ZERO))){relesIncompletos=true; break;}
    			                		if(rele.getReleDerivadaS()!= null && (!rele.getReleDerivadaS().equals(BigDecimal.ZERO))){relesIncompletos=true; break;}
    			                		if(rele.getDemandaMaxima()!= null && (!rele.getDemandaMaxima().equals(BigDecimal.ZERO))){relesIncompletos=true; break;}
    			                		if(rele.getDemandaMedia()!= null && (!rele.getDemandaMedia().equals(BigDecimal.ZERO))){relesIncompletos=true; break;}
    			                		if(rele.getDemandaMinima()!= null && (!rele.getDemandaMinima().equals(BigDecimal.ZERO))){relesIncompletos=true; break;}
    			                		if(rele.getDemandaMw()!= null && (!rele.getDemandaMw().equals(BigDecimal.ZERO))){relesIncompletos=true; break;}
    			                		
    			                		if(rele.getObservaciones()!= null && (!rele.getObservaciones().equals(""))){relesIncompletos=true; break;}
    			                		if(rele.getHora()!= null){relesIncompletos=true; break;}
    			                		
    			                		if(rele.getFlgFiscalizado()!= null){relesIncompletos=true; break;}
    			                		if(rele.getFlgExisteReleUmbral()!= null){relesIncompletos=true; break;}
    			                		if(rele.getFlgExisteReleDerivada()!= null){relesIncompletos=true; break;}
    			                		if(rele.getFlgAjusteReleUmbral()!= null){relesIncompletos=true; break;}
    			                		if(rele.getFlgAjusteReleDerivada()!= null){relesIncompletos=true; break;}
    			                		if(rele.getFlgOtroAjusteUmbral()!= null){relesIncompletos=true; break;}
    			                		if(rele.getFlgOtroAjusteDerivada()!= null){relesIncompletos=true; break;}
    			                		if(rele.getFlgProtocoloUmbral()!= null){relesIncompletos=true; break;}
    			                		if(rele.getFlgProtocoloDerivada()!= null){relesIncompletos=true; break;}
    			                		
    			                		if(rele.getPotr()!= null && (!rele.getPotr().equals(BigDecimal.ZERO))){relesIncompletos=true; break;}

    			    		}
    			    	}
    			    }
    			    if(relesIncompletos){
    			    	retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ADVERTENCIA);
    			        retorno.put(ConstantesWeb.VV_MENSAJE, "No se puede cambiar la configuración porque existen registros en la supervisión de campo.");
    			    }else{			    	
    			    	//Setea valores      	
    		        	String fecha = "01/01/"+anio+" 00:00:00 AM";            	
    		       	 	configuracionRele.setAnio(anio);
    		       	 	configuracionRele.setIdSubestacion(Long.parseLong(idSubestacion));
    		       	 	configuracionRele.setEmpCodemp(idEmpresa);
    		       	 	configuracionRele.setIdZona(idZona);
    		       	 	configuracionRele.setEstado(Constantes.ESTADO_ACTIVO);
    		       	 	Long idCab = 0L;
    		       	 	//valida existe configuracion || 0 = no existe , valor<> 0 = existe
    		       	 	idCab = empresasZonaServiceNeg.existeConfiguracionRele(configuracionRele);
    		        	if(idEtapas!=null && !idEtapas.equals("")){//Se envia al menos una Etapa
    		        		String[] idEtapa= idEtapas.split(",");            	
    		        		LOG.info("Etapas" + idEtapa.length);
    		        		//si no existe configuracion
    			            if(idCab==0){
    			            	configuracionRele.setAnio(fecha);
    			            	//se registra cabecera
    			           		resultado = empresasZonaServiceNeg.registrarConfiguracionReles(configuracionRele, Constantes.getUsuarioDTO(request));		            
    			           	}else{
    			           		//existe configuracion
    			           		//se setea campos
    			           		resultado.setIdConfiguracionReles(idCab);
    			           		configuracionRele.setIdConfiguracionReles(idCab);
    						}	      
    			            //borra todos los detalles de la cabecera
    			            empresasZonaServiceNeg.borrarDetalleConfiguracionReles(configuracionRele);
    			            for(int i=0; i<idEtapa.length;i++){
    					    	objDetaConfRelesDTO.setIdEtapa(Long.parseLong(idEtapa[i].toString()));
    					    	objDetaConfRelesDTO.setIdConfiguracionReles(resultado.getIdConfiguracionReles());
    					    	//Se inserta nuevos detalles
    					    	empresasZonaServiceNeg.detalleConfiguracionReles(objDetaConfRelesDTO, Constantes.getUsuarioDTO(request));
    					    }  
    			            
    			            	            
    		        	}else{//No se envia Etapas
    			            if(idCab==0){
    			            	//No existe cabecera
    			            	LOG.info("No existe cabecera configurada");
    			            }else{
    			            	//Existe cabecera
    			           		configuracionRele.setIdConfiguracionReles(idCab);
    			           		//Se borra todos los detalles de la cabecera
    			           		boolean exitoDeta = empresasZonaServiceNeg.deleteConfig(configuracionRele);	           		
    						}	            
    			            
    		        	} 
    			    	retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
    			        retorno.put(ConstantesWeb.VV_MENSAJE, "Cambios realizados.");
    			    }
        		}else{
        			//Setea valores      	
		        	String fecha = "01/01/"+anio+" 00:00:00 AM";            	
		       	 	configuracionRele.setAnio(anio);
		       	 	configuracionRele.setIdSubestacion(Long.parseLong(idSubestacion));
		       	 	configuracionRele.setEmpCodemp(idEmpresa);
		       	 	configuracionRele.setIdZona(idZona);
		       	 	configuracionRele.setEstado(Constantes.ESTADO_ACTIVO);
		       	 	Long idCab = 0L;
		       	 	//valida existe configuracion || 0 = no existe , valor<> 0 = existe
		       	 	idCab = empresasZonaServiceNeg.existeConfiguracionRele(configuracionRele);
		        	if(idEtapas!=null && !idEtapas.equals("")){//Se envia al menos una Etapa
		        		String[] idEtapa= idEtapas.split(",");            	
		        		LOG.info("Etapas" + idEtapa.length);
		        		//si no existe configuracion
			            if(idCab==0){
			            	configuracionRele.setAnio(fecha);
			            	//se registra cabecera
			           		resultado = empresasZonaServiceNeg.registrarConfiguracionReles(configuracionRele, Constantes.getUsuarioDTO(request));		            
			           	}else{
			           		//existe configuracion
			           		//se setea campos
			           		resultado.setIdConfiguracionReles(idCab);
			           		configuracionRele.setIdConfiguracionReles(idCab);
						}	      
			            //borra todos los detalles de la cabecera
			            empresasZonaServiceNeg.borrarDetalleConfiguracionReles(configuracionRele);
			            for(int i=0; i<idEtapa.length;i++){
					    	objDetaConfRelesDTO.setIdEtapa(Long.parseLong(idEtapa[i].toString()));
					    	objDetaConfRelesDTO.setIdConfiguracionReles(resultado.getIdConfiguracionReles());
					    	//Se inserta nuevos detalles
					    	empresasZonaServiceNeg.detalleConfiguracionReles(objDetaConfRelesDTO, Constantes.getUsuarioDTO(request));
					    }  
			            
			            	            
		        	}else{//No se envia Etapas
			            if(idCab==0){
			            	//No existe cabecera
			            	LOG.info("No existe cabecera configurada");
			            }else{
			            	//Existe cabecera
			           		configuracionRele.setIdConfiguracionReles(idCab);
			           		//Se borra todos los detalles de la cabecera
			           		boolean exitoDeta = empresasZonaServiceNeg.deleteConfig(configuracionRele);	           		
						}	            
			            
		        	} 
			    	retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
			        retorno.put(ConstantesWeb.VV_MENSAJE, "Cambios realizados.");
        		}      		
        	}else{
        		//Setea valores      	
	        	String fecha = "01/01/"+anio+" 00:00:00 AM";            	
	       	 	configuracionRele.setAnio(anio);
	       	 	configuracionRele.setIdSubestacion(Long.parseLong(idSubestacion));
	       	 	configuracionRele.setEmpCodemp(idEmpresa);
	       	 	configuracionRele.setIdZona(idZona);
	       	 	configuracionRele.setEstado(Constantes.ESTADO_ACTIVO);
	       	 	Long idCab = 0L;
	       	 	//valida existe configuracion || 0 = no existe , valor<> 0 = existe
	       	 	idCab = empresasZonaServiceNeg.existeConfiguracionRele(configuracionRele);
	        	if(idEtapas!=null && !idEtapas.equals("")){//Se envia al menos una Etapa
	        		String[] idEtapa= idEtapas.split(",");            	
	        		LOG.info("Etapas" + idEtapa.length);
	        		//si no existe configuracion
		            if(idCab==0){
		            	configuracionRele.setAnio(fecha);
		            	//se registra cabecera
		           		resultado = empresasZonaServiceNeg.registrarConfiguracionReles(configuracionRele, Constantes.getUsuarioDTO(request));		            
		           	}else{
		           		//existe configuracion
		           		//se setea campos
		           		resultado.setIdConfiguracionReles(idCab);
		           		configuracionRele.setIdConfiguracionReles(idCab);
					}	      
		            //borra todos los detalles de la cabecera
		            empresasZonaServiceNeg.borrarDetalleConfiguracionReles(configuracionRele);
		            for(int i=0; i<idEtapa.length;i++){
				    	objDetaConfRelesDTO.setIdEtapa(Long.parseLong(idEtapa[i].toString()));
				    	objDetaConfRelesDTO.setIdConfiguracionReles(resultado.getIdConfiguracionReles());
				    	//Se inserta nuevos detalles
				    	empresasZonaServiceNeg.detalleConfiguracionReles(objDetaConfRelesDTO, Constantes.getUsuarioDTO(request));
				    }  
		            
		            	            
	        	}else{//No se envia Etapas
		            if(idCab==0){
		            	//No existe cabecera
		            	LOG.info("No existe cabecera configurada");
		            }else{
		            	//Existe cabecera
		           		configuracionRele.setIdConfiguracionReles(idCab);
		           		//Se borra todos los detalles de la cabecera
		           		boolean exitoDeta = empresasZonaServiceNeg.deleteConfig(configuracionRele);	           		
					}	            
		            
	        	} 
	        	
	        	retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
		        retorno.put(ConstantesWeb.VV_MENSAJE, "Cambios realizados.");
        	}
        	
        	// Test
        	//List<DetaConfRelesDTO> etpByEmpresaByAnio = empresasZonaServiceNeg.findAllEtapaByEmpresaByAnio(idEmpresa, anio);
        	List<DetaConfRelesDTO> etpByEmpresaByAnio = empresasZonaServiceNeg.findAllEtapaByEmpresaByAnio(idEmpresa, anio, idZona);
        	// Test

        	retorno.put("configuraciones", etpByEmpresaByAnio);
        	
        	if(!relesIncompletos && !idSupeCampR.equals("null")){
	        	if(etpByEmpresaByAnio.size()==0){
	        		
	        		// Verificar SI hay Cabecera y Detalle Reles para ponerlos a Estado 0
	        		SupeCampRechCargaFilter filtroSupe = new SupeCampRechCargaFilter();
	        		filtroSupe.setIdSupeCampRechCarga(new Long(idSupeCampR));
	        		List<SupeCampRechCargaDTO> listaSupe = empresasZonaServiceNeg.listarSupeCampRechCarga(filtroSupe);
	        		if(listaSupe!=null && listaSupe.size()>0){
	        			listaSupe.get(0).setEstado(Constantes.ESTADO_INACTIVO);
	        			empresasZonaServiceNeg.inactivarSupeCampYDetaSupeCamp(listaSupe.get(0), Constantes.getUsuarioDTO(request), Constantes.TIPO_PANTALLA_CAMBIAR_ESTADO);
	        			retorno.put("recargarGrillla", 1);
	        		}
	        		
	        	}else if (etpByEmpresaByAnio!=null && etpByEmpresaByAnio.size()>0){
	        		
        			SupeCampRechCargaDTO supeCamp = new SupeCampRechCargaDTO();
            		supeCamp.setIdSupeCampRechCarga(new Long(idSupeCampR));
            		supeCamp.setIdZona(String.valueOf(idZona));
            		int resultadoValidarRelesServicioDetaSupe=validarRelesServicioDetaSupe(idEmpresa, anio, supeCamp, Constantes.getUsuarioDTO(request));
            		
            		if(resultadoValidarRelesServicioDetaSupe==1){
            			retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            		}else if(resultadoValidarRelesServicioDetaSupe==0){
            			retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                    	retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_ERROR_VALIDAR_RELES_SERVICIO_DETA_SUP);
            		}
	        	}
        	}
        } catch (Exception e) {
            LOG.error("Error en guardarConfiguracionReles", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
            LOG.error("Error SupeCampRechCargaException: " + e);
	        throw new SupeCampRechCargaException(e);
        }
        return retorno;
    }
    
    @RequestMapping(value = "/validaImprimirActa", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> validaImprimirActa(int idSubestacion,String idEmpresa,String anio, boolean esPlantillas, Long idZona, HttpSession session, HttpServletRequest request,HttpServletResponse response){
        LOG.info("validando imprimir");
        Map<String, Object> salida = new HashMap<String, Object>();
        
        try {
        	ConfiguracionRelesFilter filtroConf = new ConfiguracionRelesFilter();
            filtroConf.setEmpCodemp(idEmpresa);
            filtroConf.setAnio(anio);
            filtroConf.setIdSubestacion(0L);
            filtroConf.setIdZona(idZona);
            List<ConfiguracionRelesDTO> lConfiguracionRelesDTO=empresasZonaServiceNeg.listarConfiguracionReles(filtroConf);
            if(lConfiguracionRelesDTO!=null && lConfiguracionRelesDTO.size()>0){
            	salida.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            	salida.put(ConstantesWeb.VV_MENSAJE, "La empresa tiene configuraciones asociadas");
            }else{
            	salida.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ADVERTENCIA);
            	salida.put(ConstantesWeb.VV_MENSAJE, "No existe configuración");
            }
            
        } catch (Exception e) {
        	salida.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
        	salida.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_ERROR_ACTUALIZAR_REGISTRO_ACTA);
            LOG.error("Error en validacion imprimir,",e);
        }
        return salida;
    }    
    
}
