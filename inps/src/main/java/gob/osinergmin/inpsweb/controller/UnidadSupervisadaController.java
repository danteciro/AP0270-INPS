/**
* Resumen		
* Objeto		: UnidadSupervisadaController.java
* Descripción		: Controla el flujo de datos del objeto UnidadSupervisada
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     09/05/2016      Mario Dioses Fernandez      Registrar en BD campo FECHA_INICIO_PLAZO y FECHA_FIN_PLAZO luego de CONCLUIR_OS, considerando Plazo_Descargo por Rubro (MDI_ACTIVIDAD)
* OSINE_SFS-480     13/05/2016      Mario Dioses Fernandez          Listar Empresas Supervisoras según filtros definidos para Gerencia (Unidad Organica)
* OSINE_SFS-480     24/05/2016      Giancarlo Villanueva Andrade    Crear componente de selección de "subtipo de supervisión".Relacionar y adecuar el subtipo de supervisión, el cual deberá depender del tipo de supervisión seleccionado
* OSINE_MANT_DSHL_003 27/06/2017    Percy Quispe Huarcaya::ADAPTER  Correccion del funcionamiento de la opcion de busqueda en la pantalla generar orden de servicio 
*/ 

package gob.osinergmin.inpsweb.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gob.osinergmin.inpsweb.service.business.EmpresaSupervisadaServiceNeg;
import gob.osinergmin.inpsweb.service.business.MaestroColumnaServiceNeg;
import gob.osinergmin.inpsweb.service.business.UnidadSupervisadaServiceNeg;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.ConstantesWeb;
import gob.osinergmin.mdicommon.domain.dto.DireccionUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.dto.busqueda.BusquedaDireccionxUnidadSupervidaDTO;
import gob.osinergmin.mdicommon.domain.dto.busqueda.BusquedaUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.ui.UnidadSupervisadaFilter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author jpiro
 */
@Controller
@RequestMapping("/unidadSupervisada")
public class UnidadSupervisadaController {
    private static final Logger LOG = LoggerFactory.getLogger(UnidadSupervisadaController.class);
    
    @Inject
    private UnidadSupervisadaServiceNeg unidadSupervisadaServiceNeg;
    
    @Inject
    private EmpresaSupervisadaServiceNeg empresaSupervisadaServiceNeg;
    /* OSINE_SFS-480 - RSIS 17 - Inicio */
    @Inject
    private MaestroColumnaServiceNeg maestroColumnaServiceNeg; 
    /* OSINE_SFS-480 - RSIS 17 - Fin */
    @RequestMapping(value="/cargaDataUnidadOperativa",method= RequestMethod.GET)
    public @ResponseBody Map<String, Object> cargaDataUnidadOperativa(UnidadSupervisadaFilter filtro,HttpSession session,HttpServletRequest request){ 
        LOG.info("cargaDataUnidadOperativa");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            /* OSINE_SFS-480 - RSI S11 - Inicio */
            List<MaestroColumnaDTO> listaDireUoDl = maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_DIRE_INPS_UO, Constantes.APLICACION_INPS);
            if(listaDireUoDl!=null && listaDireUoDl.size()!=0) {
                filtro.setCadCodigosTipoDireccion(listaDireUoDl.get(0).getCodigo());
            }
            /* OSINE_SFS-480 - RSIS 11 - Fin */
                     
            List<BusquedaUnidadSupervisadaDTO> listaBusqueda=unidadSupervisadaServiceNeg.cargaDataUnidadOperativaMasiva(filtro);
            BusquedaUnidadSupervisadaDTO registro=null;
            if(listaBusqueda!=null && listaBusqueda.size()>0){ 
                registro=listaBusqueda.get(0);
            }
            
            if(registro==null){
                retorno.put("resultado",ConstantesWeb.VV_ERROR);
            }else{
                retorno.put("resultado",ConstantesWeb.VV_EXITO);
                retorno.put("registro", registro);
            }            
	}catch(Exception e){
            LOG.error("Error cargaDataUnidadOperativa",e);
            retorno.put("resultado",ConstantesWeb.VV_ERROR);
        }
        return retorno;
    }
    /* OSINE_SFS-480 - RSIS 26 - Inicio */
    @RequestMapping(value="/cargaDataUnidadOperativaMasivaInd",method= RequestMethod.POST)
    public @ResponseBody Map<String, Object> cargaDataUnidadOperativaMasivaInd(UnidadSupervisadaFilter filtro,HttpSession session,HttpServletRequest request){ 
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
        	List<MaestroColumnaDTO> listaDireUoDl = maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_DIRE_INPS_UO, Constantes.APLICACION_INPS);
        	String cadenaNueva="";
        	List<BusquedaUnidadSupervisadaDTO> registro = new ArrayList<BusquedaUnidadSupervisadaDTO>();
        	for (String codigo: filtro.getCadCodigoOsinerg().split(",")){
                if(codigo.trim() != null && codigo.trim() != "" && codigo.length()>0){
                	cadenaNueva=cadenaNueva+codigo+",";
                }
            }  
        	if(cadenaNueva!=""){
        		cadenaNueva=cadenaNueva.substring(0, cadenaNueva.length()-1);
        		filtro.setCadCodigoOsinerg(cadenaNueva);
	        	if(listaDireUoDl!=null && listaDireUoDl.size()!=0) { filtro.setCadCodigosTipoDireccion(listaDireUoDl.get(0).getCodigo()); }
	        	
	        	//OSINE_MANT_DSHL_003 : inicio
				//registro=unidadSupervisadaServiceNeg.cargaDataUnidadOperativaMasiva(filtro);
				Long idPersonal=(Long)request.getSession().getAttribute("idPersonal");
	        	registro=unidadSupervisadaServiceNeg.cargaDataUnidadOperativaMasivaUsuario(filtro,idPersonal);
	        	//OSINE_MANT_DSHL_003 : fin
	        	if(registro!=null && registro.size()>0){
	            	List<BusquedaUnidadSupervisadaDTO> listaSesion = new ArrayList<BusquedaUnidadSupervisadaDTO>();
	            	listaSesion = getListSesionUnidadOperativaOS(session);
	            	for(BusquedaUnidadSupervisadaDTO unidadSupervisada:registro){
	            		
		            	Boolean existe = false;
		            	if(listaSesion!=null && listaSesion.size()>0){
		            		for(BusquedaUnidadSupervisadaDTO lista:listaSesion){
		                		if(lista.getIdUnidadSupervisada().longValue()==unidadSupervisada.getIdUnidadSupervisada().longValue()){
		                			existe=true; 
		                		}
		                	}
		                	if(!existe){
		                		listaSesion.add(unidadSupervisada);
		                	}
		            	}else{
		            		if(!existe){
		                		listaSesion.add(unidadSupervisada);
		                	}
		            	}
	            	}
	            	setListSesionUnidadOperativaOS(session,listaSesion);
	                
	                retorno.put("resultado",ConstantesWeb.VV_EXITO);
	                retorno.put("registro", registro);
	            }else{                   
	            	retorno.put("resultado",ConstantesWeb.VV_ADVERTENCIA);
	            }
        	}else{
        		LOG.error("Error al cargaDataUnidadOperativa");
        		retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR); 
        	}
	}catch(Exception e){
            LOG.error("Error cargaDataUnidadOperativa",e);
            retorno.put("resultado",ConstantesWeb.VV_ERROR);
        }
        return retorno;
    }
    @RequestMapping(value="/cargaDataUnidadOperativaMasivaMul",method= RequestMethod.POST)
    public @ResponseBody Map<String, Object> cargaDataUnidadOperativaMasivaMul(UnidadSupervisadaFilter filtro,HttpSession session,HttpServletRequest request){ 
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
        	MaestroColumnaDTO periodo=maestroColumnaServiceNeg.buscarByDominioByAplicacionByCodigo(Constantes.DOMINIO_SUPERV_MUEST_PERIODO, Constantes.APLICACION_INPS,Constantes.CODIGO_PERIODO).get(0);
        	Long cantPeriodos = new Long(periodo.getDescripcion());
        	Calendar calendar = Calendar.getInstance();
        	int year = calendar.get(Calendar.YEAR);
        	int mes = calendar.get(Calendar.MONTH);
        	Long per = obtenerPeriodoActual(cantPeriodos,mes);
        	String perUnidad = year+""+per;
        	filtro.setPeriodo(perUnidad);
        	List<MaestroColumnaDTO> listaDireUoDl = maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_DIRE_INPS_UO, Constantes.APLICACION_INPS);
        	if(listaDireUoDl!=null && listaDireUoDl.size()!=0) {
        		filtro.setCadCodigosTipoDireccion(listaDireUoDl.get(0).getCodigo());
        	}
            List<BusquedaUnidadSupervisadaDTO> registro=unidadSupervisadaServiceNeg.cargaDataUnidadOperativaMasiva(filtro);
            if(registro!=null){
            	List<BusquedaUnidadSupervisadaDTO> listaSesionTemporal = new ArrayList<BusquedaUnidadSupervisadaDTO>();
                for(BusquedaUnidadSupervisadaDTO unidad:registro){                		
                			listaSesionTemporal.add(unidad);
                }
                setListSesionUnidadOperativaOS(session,listaSesionTemporal);
                retorno.put("resultado",ConstantesWeb.VV_EXITO);
                retorno.put("registro", registro);
            }else{
                retorno.put("resultado",ConstantesWeb.VV_ERROR);
            }            
	}catch(Exception e){
            LOG.error("Error cargaDataUnidadOperativa",e);
            retorno.put("resultado",ConstantesWeb.VV_ERROR);
        }
        return retorno;
    }
    @RequestMapping(value="/cargaDataUnidadOperativaMasivaGrid",method= RequestMethod.POST)
    public @ResponseBody Map<String, Object> cargaDataUnidadOperativaMasivaGrid(HttpSession sesion,UnidadSupervisadaFilter filtro,int rows, int page,HttpSession session,HttpServletRequest request){ 
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
        	/* OSINE_SFS-480 - RSIS 26 - Inicio */
        	MaestroColumnaDTO periodo=maestroColumnaServiceNeg.buscarByDominioByAplicacionByCodigo(Constantes.DOMINIO_SUPERV_MUEST_PERIODO, Constantes.APLICACION_INPS,Constantes.CODIGO_PERIODO).get(0);
        	Long cantPeriodos = new Long(periodo.getDescripcion());
        	Calendar calendar = Calendar.getInstance();
        	int year = calendar.get(Calendar.YEAR);
        	int mes = calendar.get(Calendar.MONTH);
        	Long per = obtenerPeriodoActual(cantPeriodos,mes);
        	String perUnidad = year+""+per;
        	filtro.setPeriodo(perUnidad);
        	/* OSINE_SFS-480 - RSIS 26 - Fin */
        	int indiceInicial=-1;
            int indiceFinal=-1;
            List<BusquedaUnidadSupervisadaDTO> registro = new ArrayList<BusquedaUnidadSupervisadaDTO>();    
            
            
            // Busqueda de Unidades Supervisadas
            if(filtro.getFlagBusqueda().equals(Constantes.ESTADO_ACTIVO)) {           	
            	//List<MaestroColumnaDTO> listaDireUoDl = maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_DIRE_INPS_SM, Constantes.APLICACION_INPS);
            	List<MaestroColumnaDTO> listaDireUoDl = maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_DIRE_INPS_UO, Constantes.APLICACION_INPS);
            	if(listaDireUoDl!=null && listaDireUoDl.size()!=0) {
	        		filtro.setCadCodigosTipoDireccion(listaDireUoDl.get(0).getCodigo());
	        	}
            	//OSINE_MANT_DSHL_003 - Inicio
				//esto hace la busqueda
            	registro=unidadSupervisadaServiceNeg.cargaDataUnidadOperativaMasiva(filtro);
            	
            	//obtiene lo que ya hay en session
            	List<BusquedaUnidadSupervisadaDTO> listaSesion = new ArrayList<BusquedaUnidadSupervisadaDTO>();
            	listaSesion = getListSesionUnidadOperativaOS(session);
            	
            	for(BusquedaUnidadSupervisadaDTO unidadSupervisada:registro){
            		
	            	Boolean existe = false;
	            	if(listaSesion!=null && listaSesion.size()>0){
	            		for(BusquedaUnidadSupervisadaDTO lista:listaSesion){
	                		if(lista.getIdUnidadSupervisada().longValue()==unidadSupervisada.getIdUnidadSupervisada().longValue()){
	                			existe=true; 
	                		}
	                	}
	                	if(!existe){
	                		listaSesion.add(unidadSupervisada);
	                	}
	            	}else{
	            		if(!existe){
	                		listaSesion.add(unidadSupervisada);
	                	}
	            	}
            	}
            	setListSesionUnidadOperativaOS(session,listaSesion);
            	
            	registro = listaSesion;
            	/*
            	String cadenaIds="";
	        	
	            for (BusquedaUnidadSupervisadaDTO unidadDTO: getListSesionUnidadOperativaOS(session)){
	            	cadenaIds=cadenaIds+unidadDTO.getIdUnidadSupervisada()+",";
	            }  
	            cadenaIds=cadenaIds.substring(0, cadenaIds.length()-1);
	            LOG.info("Ids Filtro: : : ["+cadenaIds+"]");
	            filtro.setIdsBusqueda(cadenaIds);
	            
	            registro=unidadSupervisadaServiceNeg.cargaDataUnidadOperativaMasiva(filtro);
	            */
            	//OSINE_MANT_DSHL_003 - Fin
            }else{
            	registro = getListSesionUnidadOperativaOS(session);
            	String cadenaIds="";
            	for (BusquedaUnidadSupervisadaDTO unidadDTO: registro){
	            	cadenaIds=cadenaIds+unidadDTO.getIdUnidadSupervisada()+",";
	            } 
            	LOG.info("Ids Metro: : : ["+cadenaIds+"]");
            }
            
            if(registro!=null){
            	Long contador = new Long(registro.size());
            	Long numeroFilas = (contador / rows);
	            if ((contador % rows) > 0) {numeroFilas = numeroFilas + 1L; }
	            if(numeroFilas<page){page = numeroFilas.intValue(); }
	            if(page == 0){rows = 0;}
            	indiceInicial = (page - 1) * rows;
	            indiceFinal = indiceInicial + rows;
	            List<BusquedaUnidadSupervisadaDTO> listaUnidadesSupervisadas = new ArrayList<BusquedaUnidadSupervisadaDTO>();
	            listaUnidadesSupervisadas = registro.subList(
	            		indiceInicial > registro.size() ? registro.size() : indiceInicial , indiceFinal > registro
	                    .size() ? registro.size()
	                    : indiceFinal);	        
	            retorno.put("total", numeroFilas);
	            retorno.put("pagina", page);
	            retorno.put("registros", contador);
	            retorno.put("filas", listaUnidadesSupervisadas);	            
                retorno.put("resultado",ConstantesWeb.VV_EXITO);

            }else{            	
                retorno.put("resultado",ConstantesWeb.VV_ERROR);
            }

           
	}catch(Exception e){
            LOG.error("Error cargaDataUnidadOperativaMasiva",e);
            retorno.put("resultado",ConstantesWeb.VV_ERROR);
        }
        return retorno;
    }
    private List<BusquedaUnidadSupervisadaDTO> getListSesionUnidadOperativaOS(HttpSession sesion) {
        List<BusquedaUnidadSupervisadaDTO> listaSesion = (List<BusquedaUnidadSupervisadaDTO>) sesion.getAttribute("LISTA_UNIDAD_OPERATIVA_SESION");
        if(listaSesion == null){
        	listaSesion = new ArrayList<BusquedaUnidadSupervisadaDTO>();
        }
	return listaSesion;
    }
    
    private void setListSesionUnidadOperativaOS(HttpSession sesion, List<BusquedaUnidadSupervisadaDTO> listaSesion) {
	sesion.setAttribute("LISTA_UNIDAD_OPERATIVA_SESION", listaSesion);
    }
    
    @RequestMapping(value="/removeSesionLista",method= RequestMethod.GET)
    public @ResponseBody Map<String, Object> removeSesionLista(HttpServletRequest request, HttpSession sesion, Model model) {    	
    	List<BusquedaUnidadSupervisadaDTO> lista = new ArrayList<BusquedaUnidadSupervisadaDTO>();
    	setListSesionUnidadOperativaOS(sesion,lista);
        sesion.removeAttribute("LISTA_UNIDAD_OPERATIVA_SESION");
		return null;
    }
    
    @RequestMapping(value = "/quitarUO", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> quitarUO(BusquedaUnidadSupervisadaDTO unidadSupervisada, HttpSession session, HttpServletResponse response,HttpServletRequest request) {
		LOG.info("quitarUO - inicio");
		Map<String, Object> retorno = new HashMap<String, Object>();
		try {
			List<BusquedaUnidadSupervisadaDTO> listaSesion = getListSesionUnidadOperativaOS(session);
			int n=-1, indice=-1;
			for (BusquedaUnidadSupervisadaDTO unidadDTO: listaSesion){
            	n++;
				if(unidadDTO.getIdUnidadSupervisada().longValue()==unidadSupervisada.getIdUnidadSupervisada().longValue()){
					indice=n;
					break;
            	}
            }  
			listaSesion.remove(indice);
			
			LOG.info("IdUnidadBorrada : : : ["+unidadSupervisada.getIdUnidadSupervisada()+"]");
			String cadenaIds="";
			for (BusquedaUnidadSupervisadaDTO unidadDTO: listaSesion){
            	cadenaIds=cadenaIds+unidadDTO.getIdUnidadSupervisada()+",";
            } 
        	LOG.info("Ids Nuevos: : : ["+cadenaIds+"]");
			session.setAttribute("LISTA_UNIDAD_OPERATIVA_SESION", listaSesion);
			
			if(indice!=-1){
				retorno.put(ConstantesWeb.VV_MENSAJE,
						ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_DELETE);
				retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
			}else{
				retorno.put(ConstantesWeb.VV_MENSAJE,"Error al quitar la Unidad Operativa seleccionada.");
				retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
			}
			
        } catch (Exception e) {
                LOG.error("Error quitando la Unidad Operativa", e);
        }	
		return retorno;
	}
    /* OSINE_SFS-480 - RSIS 26 - Fin */
    
    @RequestMapping(value = "/listarUnidadesSupervisadas", method = RequestMethod.GET)
    public @ResponseBody
	Map<String, Object> listarUnidadSupervisadaEmpresa(Long idEmpresaSupervisada, int rows, int page, HttpSession session,HttpServletRequest request) {
		LOG.info("Listar Unidades Supervisadas por Empresa");
        Map<String, Object> listaResultado = new HashMap<String, Object>();
        List<UnidadSupervisadaDTO> listado = new ArrayList<UnidadSupervisadaDTO>();
        try{
        	int indiceInicial=-1;
            int indiceFinal=-1;
        	UnidadSupervisadaFilter filtro = new UnidadSupervisadaFilter();
        	filtro.setIdEmpresaSupervisada(idEmpresaSupervisada);
            listado=unidadSupervisadaServiceNeg.listarUnidadSupervisada(filtro);
            LOG.info("listado de direcciones" + listado);
            if(listado!=null){
            	Long contador = new Long(listado.size());
            	Long numeroFilas = (contador / rows);
	            if ((contador % rows) > 0) {numeroFilas = numeroFilas + 1L; }
	            if(numeroFilas<page){page = numeroFilas.intValue(); }
	            if(page == 0){rows = 0;}
            	indiceInicial = (page - 1) * rows;
	            indiceFinal = indiceInicial + rows;
	            List<UnidadSupervisadaDTO> listaUnidadesSupervisadas = new ArrayList<UnidadSupervisadaDTO>();
	            listaUnidadesSupervisadas = listado.subList(
	            		indiceInicial > listado.size() ? listado.size() : indiceInicial , indiceFinal > listado
	                    .size() ? listado.size()
	                    : indiceFinal);	        
	            listaResultado.put("total", numeroFilas);
	            listaResultado.put("pagina", page);
	            listaResultado.put("registros", contador);
	            listaResultado.put("filas", listaUnidadesSupervisadas);
            }
        }catch(Exception ex){
        	LOG.error("error controller",ex);
        }
        
        return listaResultado;
    }
    
    @RequestMapping(value="/obtenerUnidadSupervisada", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> obtenerUnidadSupervisada(Long idUnidadSupervisada, HttpSession session,HttpServletRequest request){
    	LOG.info("Obtener Unidad Supervisada");
    	Map<String, Object> salida = new HashMap<String, Object>();
    	BusquedaUnidadSupervisadaDTO unidad = new BusquedaUnidadSupervisadaDTO();
    	try {
    		if(idUnidadSupervisada!=null){
    		UnidadSupervisadaFilter filtro = new UnidadSupervisadaFilter();
    		filtro.setCadIdUnidadSupervisada(idUnidadSupervisada.toString());
    		LOG.info(" Id Unidad Supervisada : " + idUnidadSupervisada);
    		LOG.info(" Id Unidad Supervisada : " + filtro.getIdUnidadSupervisada());
    		unidad=unidadSupervisadaServiceNeg.obtenerUnidadSupervisada(filtro);
    		salida.put("unidad", unidad);
    		salida.put("resultado",ConstantesWeb.VV_EXITO);
			}else{
			salida.put("resultado",ConstantesWeb.VV_ADVERTENCIA);
			}
			
		} catch (Exception e) {
			LOG.error("error controller",e);
			salida.put("resultado",ConstantesWeb.VV_ERROR);
		}
		return salida;
    }
    
    @RequestMapping(value="/eliminarUnidadSupervisada", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> eliminarUnidadSupervisada(Long idUnidadSupervisada, HttpSession session,HttpServletRequest request){
    	LOG.info("Eliminar Unidad Supervisada");
    	Map<String, Object> salida = new HashMap<String, Object>();
    	UnidadSupervisadaDTO unidad = new UnidadSupervisadaDTO();
    	try {
    		if(idUnidadSupervisada!=null){
    		UnidadSupervisadaFilter filtro = new UnidadSupervisadaFilter();
    		filtro.setIdUnidadSupervisada(idUnidadSupervisada);
    		unidad=unidadSupervisadaServiceNeg.eliminarUnidadSupervisada(filtro);
    		salida.put("unidad", unidad);
    		salida.put("resultado",ConstantesWeb.VV_EXITO);
			}else{
			salida.put("resultado",ConstantesWeb.VV_ADVERTENCIA);
			}
			
		} catch (Exception e) {
			LOG.error("error controller",e);
			salida.put("resultado",ConstantesWeb.VV_ERROR);
		}
		return salida;
    }
    //Direciones de Unidad Supervisada
    @RequestMapping(value = "/listarDireccionUnidadSupervisada", method = RequestMethod.GET)
    public @ResponseBody
	Map<String, Object> listarDireccionUnidadSupervisada(Long idUnidadSupervisada, int rows, int page, HttpSession session,HttpServletRequest request) {
		LOG.info("Listar Direcciones por Unidad Supervisada");
        Map<String, Object> listaResultado = new HashMap<String, Object>();
        List<BusquedaDireccionxUnidadSupervidaDTO> listado = new ArrayList<BusquedaDireccionxUnidadSupervidaDTO>();
        try{
        	UnidadSupervisadaDTO unidadSupervisadaDTO = new UnidadSupervisadaDTO();
        	unidadSupervisadaDTO.setIdUnidadSupervisada(idUnidadSupervisada);
            listado=unidadSupervisadaServiceNeg.listarDireccionUnidadSupervisada(unidadSupervisadaDTO);
            LOG.info("listado de direcciones" + listado);
            if(listado!=null){
            	 int indiceInicial=-1;
                 int indiceFinal=-1;
            	 Long contador = new Long(listado.size());
            	 Long numeroFilas = (contador / rows);
 	             if ((contador % rows) > 0) { numeroFilas = numeroFilas + 1L;}
 	             if(numeroFilas<page){page = numeroFilas.intValue();}
 	             if(page == 0){rows = 0;}
            	indiceInicial = (page - 1) * rows;
	             indiceFinal = indiceInicial + rows;
	            List<BusquedaDireccionxUnidadSupervidaDTO> listaDirecciones = new ArrayList<BusquedaDireccionxUnidadSupervidaDTO>();
	            listaDirecciones = listado.subList(indiceInicial > listado.size() ? listado.size():indiceInicial, indiceFinal > listado .size() ? listado.size() : indiceFinal);	   
	            listaResultado.put("total", numeroFilas);
	            listaResultado.put("pagina", page);
	            listaResultado.put("registros", contador);
	            listaResultado.put("filas", listaDirecciones);
            }
        }catch(Exception ex){
        	LOG.error("error controller",ex);
        }
        
        return listaResultado;
    }
    @RequestMapping(value="/obtenerDireccionUnidadSupervisada", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> obtenerDireccionUnidadSupervisada(Long idDirccionUnidadSuprvisada, HttpSession session,HttpServletRequest request){
    	LOG.info("Obtener Unidad Supervisada");
    	Map<String, Object> salida = new HashMap<String, Object>();
    	BusquedaDireccionxUnidadSupervidaDTO direccion = new BusquedaDireccionxUnidadSupervidaDTO();
    	try {
    		if(idDirccionUnidadSuprvisada!=null){

    		LOG.info(" Id direccion : " + idDirccionUnidadSuprvisada);
    		DireccionUnidadSupervisadaDTO direccionUnidadSupervisada = new DireccionUnidadSupervisadaDTO();
    		direccionUnidadSupervisada.setIdDirccionUnidadSuprvisada(idDirccionUnidadSuprvisada);
    		direccion=unidadSupervisadaServiceNeg.obtenerDireccionUnidadSupervisada(direccionUnidadSupervisada);
    		salida.put("direccion", direccion);
    		salida.put("resultado",ConstantesWeb.VV_EXITO);
			}else{
			salida.put("resultado",ConstantesWeb.VV_ADVERTENCIA);
			}
			
		} catch (Exception e) {
			LOG.error("error controller",e);
			salida.put("resultado",ConstantesWeb.VV_ERROR);
		}
		return salida;
    }
    
    @RequestMapping(value="/eliminarDireccionUnidadSupervisada", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> eliminarDireccionUnidadSupervisada(Long idDireccionUnidadSupervisada, HttpSession session,HttpServletRequest request){
    	LOG.info("Eliminar Unidad Supervisada");
    	Map<String, Object> salida = new HashMap<String, Object>();
    	DireccionUnidadSupervisadaDTO direccionUnidad = new DireccionUnidadSupervisadaDTO();
    	try {
    		if(idDireccionUnidadSupervisada!=null){
    			UsuarioDTO usuario = new UsuarioDTO();
				usuario.setCodigo(Constantes.getUsuarioDTO(request).getCodigo());
				usuario.setTerminal(Constantes.getUsuarioDTO(request).getTerminal());
				DireccionUnidadSupervisadaDTO direccion = new DireccionUnidadSupervisadaDTO();
    			direccion.setIdDirccionUnidadSuprvisada(idDireccionUnidadSupervisada);    			
    			direccionUnidad = unidadSupervisadaServiceNeg.eliminardireccionUnidadSupervisada(direccion,usuario);
    			
    		salida.put("resultado",ConstantesWeb.VV_EXITO);
			}else{
			salida.put("resultado",ConstantesWeb.VV_ADVERTENCIA);
			}
			
		} catch (Exception e) {
			LOG.error("error controller",e);
			salida.put("resultado",ConstantesWeb.VV_ERROR);
		}
		return salida;
    }
    
    @RequestMapping(value="/guardarDireccionUnidadSupervisada", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> guardarDireccionUnidadSupervisada(DireccionUnidadSupervisadaDTO direccionUnidad, HttpSession session,HttpServletRequest request){
    	LOG.info("Guardar Direccion Unidad Supervisada");    	
    	Map<String, Object> salida = new HashMap<String, Object>();
    	DireccionUnidadSupervisadaDTO direccionUnidadSupervisada = new DireccionUnidadSupervisadaDTO();
    	try {
    		if(direccionUnidad!=null){
    			LOG.info("Chalet -> "+direccionUnidad.getBlockChallet());
    			UsuarioDTO usuario = new UsuarioDTO();
				usuario.setCodigo(Constantes.getUsuarioDTO(request).getCodigo());
				usuario.setTerminal(Constantes.getUsuarioDTO(request).getTerminal());
				direccionUnidad.setEstado(Constantes.ESTADO_ACTIVO);
				direccionUnidadSupervisada = unidadSupervisadaServiceNeg.guardarDireccionUnidadSupervisada(direccionUnidad,usuario);
	    		salida.put("resultado",ConstantesWeb.VV_EXITO);
			}else{
				salida.put("resultado",ConstantesWeb.VV_ADVERTENCIA);
			}
			
		} catch (Exception e) {
			LOG.error("error controller",e);
			salida.put("resultado",ConstantesWeb.VV_ERROR);
		}
		return salida;
    }
    
    @RequestMapping(value="/actualizarDireccionUnidadSupervisada", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> actualizarDireccionUnidadSupervisada(DireccionUnidadSupervisadaDTO direccionUnidad, HttpSession session,HttpServletRequest request){
    	LOG.info("Actualizar Direccion Unidad Supervisada");
    	Map<String, Object> salida = new HashMap<String, Object>();
    	DireccionUnidadSupervisadaDTO direccionUnidadSupervisada = new DireccionUnidadSupervisadaDTO();
    	try {
    		if(direccionUnidad.getIdDirccionUnidadSuprvisada()!=null){
    			UsuarioDTO usuario = new UsuarioDTO();
				usuario.setCodigo(Constantes.getUsuarioDTO(request).getCodigo());
				usuario.setTerminal(Constantes.getUsuarioDTO(request).getTerminal());
				direccionUnidad.setEstado(Constantes.ESTADO_ACTIVO);
				direccionUnidadSupervisada = unidadSupervisadaServiceNeg.actualizarDireccionUnidadSupervisada(direccionUnidad,usuario);
	    		salida.put("resultado",ConstantesWeb.VV_EXITO);
			}else{
				salida.put("resultado",ConstantesWeb.VV_ADVERTENCIA);
			}
			
		} catch (Exception e) {
			LOG.error("error controller",e);
			salida.put("resultado",ConstantesWeb.VV_ERROR);
		}
		return salida;
    }
    @RequestMapping(value="/guardarUnidadSupervisada", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> guardarUnidadSupervisada(UnidadSupervisadaDTO unidad, HttpSession session,HttpServletRequest request){
    	LOG.info("Registrar Unidad Supervisada");
    	Map<String, Object> salida = new HashMap<String, Object>();
    	UnidadSupervisadaDTO unidadRetorno = new UnidadSupervisadaDTO();
    	try {
    		if(unidad.getIdUnidadSupervisada()==null){
    			UsuarioDTO usuario = new UsuarioDTO();
				usuario.setCodigo(Constantes.getUsuarioDTO(request).getCodigo());
				usuario.setTerminal(Constantes.getUsuarioDTO(request).getTerminal());
				unidad.setEstado(Constantes.ESTADO_ACTIVO);
				unidadRetorno = unidadSupervisadaServiceNeg.guardarUnidadSupervisada(unidad,usuario);
				salida.put("unidadSupervisada",unidadRetorno);
	    		salida.put("resultado",ConstantesWeb.VV_EXITO);
			}else{
				salida.put("resultado",ConstantesWeb.VV_ADVERTENCIA);
			}
			
		} catch (Exception e) {
			LOG.error("error controller",e);
			salida.put("resultado",ConstantesWeb.VV_ERROR);
		}
		return salida;
    }  
    @RequestMapping(value="/actualizarUnidadSupervisada", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> actualizarUnidadSupervisada(UnidadSupervisadaDTO unidad, HttpSession session,HttpServletRequest request){
    	LOG.info("Actualizar Unidad Supervisada");
    	LOG.info("Id Unidad  xxx> " + unidad.getIdUnidadSupervisada());
    	Map<String, Object> salida = new HashMap<String, Object>();
    	UnidadSupervisadaDTO unidadRetorno = new UnidadSupervisadaDTO();
    	try {
    		if(unidad.getIdUnidadSupervisada()!=null){
    			UsuarioDTO usuario = new UsuarioDTO();
				usuario.setCodigo(Constantes.getUsuarioDTO(request).getCodigo());
				usuario.setTerminal(Constantes.getUsuarioDTO(request).getTerminal());
				unidad.setEstado(Constantes.ESTADO_ACTIVO);
				unidadRetorno = unidadSupervisadaServiceNeg.actualizarUnidadSupervisada(unidad,usuario);
				salida.put("unidadSupervisada",unidadRetorno);
	    		salida.put("resultado",ConstantesWeb.VV_EXITO);
			}else{
				salida.put("resultado",ConstantesWeb.VV_ADVERTENCIA);
			}
			
		} catch (Exception e) {
			LOG.error("error controller",e);
			salida.put("resultado",ConstantesWeb.VV_ERROR);
		}
		return salida;
    }
    private Long obtenerPeriodoActual(Long cantPeriodos,int mes) {
		Long per = new Long(0);
		Long cantMeses = 12/cantPeriodos;
    	Long contPeriodo = new Long(0);
    	Long longPeriodo=new Long(0);
    	ArrayList<Long> dominio = new ArrayList<Long>();
    	for(int i=0;i<cantPeriodos+1;i++){        		
    		longPeriodo=cantMeses*contPeriodo;
    		contPeriodo++;
    		dominio.add(longPeriodo);
    	}
    	contPeriodo=new Long(0);
    	for (int i = 0; i < cantPeriodos; i++){
    		contPeriodo++;
    		if(mes>dominio.get(i) && (mes<dominio.get(i+1)||mes==dominio.get(i+1))){
    			per=contPeriodo;
    		}
    	}
    	LOG.info("Array Periodos: "+dominio);
    	LOG.info("Periodo: "+per);
    	return per;
	}
    
}
