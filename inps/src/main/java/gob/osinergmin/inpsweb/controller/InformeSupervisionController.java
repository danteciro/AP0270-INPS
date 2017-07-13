/**
 * Resumen Objeto		: InformeSupervisionController.java 
 * Descripción	        :  
 * Fecha de Creación	: 14/09/2016 
 * OSINE_SFS-480 Autor	: Victor Rojas Barboza
 * ---------------------------------------------------------------------------------------------------
 * Modificaciones   Fecha             Nombre               Descripción
 * ---------------------------------------------------------------------------------------------------
 *
*/

package gob.osinergmin.inpsweb.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gob.osinergmin.inpsweb.service.business.ArchivoServiceNeg;
import gob.osinergmin.inpsweb.service.business.DocumentoAdjuntoServiceNeg;
import gob.osinergmin.inpsweb.service.business.EmpresaSupervisadaServiceNeg;
import gob.osinergmin.inpsweb.service.business.EmpresaSupervisionViewServiceNeg;
import gob.osinergmin.inpsweb.service.business.EmpresasZonaServiceNeg;
import gob.osinergmin.inpsweb.service.business.InformeSupeRechCargaServiceNeg;
import gob.osinergmin.inpsweb.service.business.MaestroColumnaServiceNeg;
import gob.osinergmin.inpsweb.service.business.PersonalServiceNeg;
import gob.osinergmin.inpsweb.service.exception.EmpresaSupervisionViewException;
import gob.osinergmin.inpsweb.service.exception.ListaEmpresasVwException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.ConstantesWeb;
import gob.osinergmin.inpsweb.util.Utiles;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresaSupDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresaViewDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresasZonaVwDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.FechaHoraDTO;
import gob.osinergmin.mdicommon.domain.dto.FlujoSigedDTO;
import gob.osinergmin.mdicommon.domain.dto.InformeSupeRechCargaDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.OrdenServicioDTO;
import gob.osinergmin.mdicommon.domain.dto.PersonalDTO;
import gob.osinergmin.mdicommon.domain.dto.ProductoDTO;
import gob.osinergmin.mdicommon.domain.dto.SupeCampRechCargaDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionRechCargaDTO;
import gob.osinergmin.mdicommon.domain.ui.EmpresaViewFilter;
import gob.osinergmin.mdicommon.domain.ui.EmpresasZonaVwFilter;
import gob.osinergmin.mdicommon.domain.ui.PersonalFilter;
import gob.osinergmin.mdicommon.domain.ui.ProductoFilter;
import gob.osinergmin.siged.remote.rest.ro.in.DocumentoInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.cxf.helpers.IOUtils;
import org.apache.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;



@Controller
@RequestMapping("/informeSupervision")
public class InformeSupervisionController {

	 private static final Logger LOG = LoggerFactory.getLogger(InformeSupervisionController.class);
	  
	 @Inject
	 private EmpresaSupervisionViewServiceNeg empresaSupervisionViewService;
	 @Inject
	 private EmpresaSupervisadaServiceNeg empresaSupervisadaServiceNeg;
	 @Inject
	 private MaestroColumnaServiceNeg maestroColumnaServiceNeg;
	 @Inject
	 private EmpresasZonaServiceNeg empresasZonaServiceNeg;
	 @Inject
	 private ArchivoServiceNeg archivoServiceNeg;
	 @Inject
	 private DocumentoAdjuntoServiceNeg documentoAdjuntoServiceNeg;
	 @Inject
	 private ArchivoController archivoController ;
	 @Inject
	 private InformeSupeRechCargaServiceNeg informeSupeRechCargaServiceNeg;
	 @Inject
	 private PersonalServiceNeg	personalServiceNeg;
	 
	 @RequestMapping(value = "/cargarAnios", method = RequestMethod.GET)
	 public @ResponseBody List<MaestroColumnaDTO> cargarAnios(HttpSession session, HttpServletRequest request, Model model) {
	        LOG.info("cargarAnios");
	        List<MaestroColumnaDTO>  listaAnio = new ArrayList <MaestroColumnaDTO>(); 	
	        try {
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
	 

	 @RequestMapping(value="/buscarEmpresas", method=RequestMethod.POST)
	 public @ResponseBody Map<String, Object> buscarEmpresas( HttpSession session, HttpServletRequest request , Model model, EmpresaViewFilter empresaViewFilter,int rows, int page) throws EmpresaSupervisionViewException{
		 Map<String, Object> retorno =  new HashMap<String, Object>();
		 List<EmpresaViewDTO> listaEmpresa = new ArrayList<EmpresaViewDTO>();
		 
		try {
			 if( empresaViewFilter.getAnio().equals("")){empresaViewFilter.setAnio(null);}
			 if(empresaViewFilter.getEmpresa().equals("")){empresaViewFilter.setEmpresa(null);}
			 if(empresaViewFilter.getRuc().equals("")){empresaViewFilter.setRuc(null);}
			 if(empresaViewFilter.getNumeroExpediente().equals("")){empresaViewFilter.setNumeroExpediente(null);}
			 if(empresaViewFilter.getTipoEmpresa().equals("")){empresaViewFilter.setTipoEmpresa(null);}
			
			 listaEmpresa = this.empresaSupervisionViewService.findEmpresas(empresaViewFilter);
			 
			 int indiceInicial = -1;
			 int indiceFinal = -1;
			 Long contador = (long)listaEmpresa.size();
			 Long numeroFilas = (contador / rows);
			 if ((contador % rows) > 0) {numeroFilas = numeroFilas + 1L;}
			 if(numeroFilas<page){page = numeroFilas.intValue();}
		     if(page == 0){rows = 0;}
		     indiceInicial = (page - 1) * rows;
		     indiceFinal = indiceInicial + rows;
		     List<EmpresaViewDTO> listaPaginada = listaEmpresa.subList(indiceInicial > listaEmpresa.size() ? listaEmpresa.size() : indiceInicial, indiceFinal > listaEmpresa.size() ? listaEmpresa.size() : indiceFinal);
		     
		     retorno.put("filas", listaPaginada);
		     retorno.put("pagina",page);
		     retorno.put("registros",contador);
		     retorno.put("total", numeroFilas);	
		} catch (Exception e) {
			LOG.error("Error en buscarEmpresas");
		}
		 return retorno;
	 }
	 
	 
	 @RequestMapping(value="/abrirVerDocumento", method=RequestMethod.POST)
	 public String abrirVerDocumento(String numeroExpediente, HttpSession sesion, Model model, HttpServletRequest request){
		 model.addAttribute("nroExpedienteDoc", numeroExpediente);
		 return ConstantesWeb.Navegacion.PAGE_INPS_ESPECIALITA_VER_DOC;
	 }
	
	 @RequestMapping(value="/abrirAdjuntarInforme", method=RequestMethod.POST)
	 public String abrirAdjuntarInforme(InformeSupeRechCargaDTO informeSupeRechCargaDTO, Model model){
		 model.addAttribute("informe", informeSupeRechCargaDTO);
		 return ConstantesWeb.Navegacion.PAGE_INPS_ESPECIALITA_ADJUNTAR_INFORME;
	 }
	 
	 @RequestMapping(value="/abrirActaInspeccion", method=RequestMethod.POST)
	 public String abrirActaInspeccion(){
		 return ConstantesWeb.Navegacion.PAGE_INPS_ESPECIALITA_ACTA_INSPECCION;
	 }
	  
	 
	 
	 @RequestMapping(value="/listarZonasEmpresas", method=RequestMethod.POST)
	 public @ResponseBody Map<String, Object>  listarZonasEmpresas(HttpSession session, HttpServletRequest request, Model model, int page, int rows, Long idEmpresa, String numeroExpediente) throws EmpresaSupervisionViewException{
		 Map<String, Object> retorno =  new HashMap<String, Object>();
		 try {
			 EmpresasZonaVwFilter empresasZonaVwFilter = new EmpresasZonaVwFilter();
			 empresasZonaVwFilter.setIdEmpresa(idEmpresa);
			 List<EmpresasZonaVwDTO> listaEmpresaZona = empresasZonaServiceNeg.listarEmpresasZona(empresasZonaVwFilter);
			 int indiceInicial = -1;
			 int indiceFinal = -1;
			 Long contador = (long)listaEmpresaZona.size();
			 Long numeroFilas = (contador / rows);
			 if ((contador % rows) > 0) {numeroFilas = numeroFilas + 1L;}
			 if(numeroFilas<page){page = numeroFilas.intValue();}
		     if(page == 0){rows = 0;}
		     indiceInicial = (page - 1) * rows;
		     indiceFinal = indiceInicial + rows;
		     List<EmpresasZonaVwDTO> listaPaginada = listaEmpresaZona.subList(indiceInicial > listaEmpresaZona.size() ? listaEmpresaZona.size() : indiceInicial, indiceFinal > listaEmpresaZona.size() ? listaEmpresaZona.size() : indiceFinal);
		     retorno.put("filas", listaPaginada );
		     retorno.put("registros", contador);
		     retorno.put("pagina", page);
		     retorno.put("total", numeroFilas);
		     
		} catch (Exception e) {
			LOG.error("Error en listarZonasEmpresas");
		}
		 return retorno;
	 }
	 
	  @RequestMapping(value="/descargaArchivoSiged",method= RequestMethod.GET)
	  public void descargaArchivoAlfresco( DocumentoAdjuntoDTO filtro, HttpServletResponse response) {
	        LOG.info("procesando descargaArchivoAlfresco--->"+filtro.getIdArchivo());        
	        LOG.info("procesando descargaArchivoAlfresco--->"+filtro.getNombreArchivo());
	        try {        	
	            File archivo=archivoServiceNeg.descargarArchivoSiged(filtro);
	            InputStream is = FileUtils.openInputStream(archivo);
	    	
	            if(is==null){
	    	        response.getWriter().write("Error al Descargar Archivo.");
	                return;
	            }       	
	            String nombreFichero = filtro.getNombreArchivo();        	
	            response.setHeader("Content-Disposition", "attachment; filename=\""
	                        + nombreFichero+ "\"");
	            IOUtils.copy(is, response.getOutputStream());
	            response.flushBuffer();
	        }catch (Exception ex) {
	            LOG.info("error descargaArchivoSiged--->"+ex.getMessage());
	            LOG.info("Error writing file to output stream. Filename was '" + filtro.getNombreArchivo()+ "'");
	            throw new RuntimeException("IOError writing file to output stream");
	        }        
    }
	 
	  	@RequestMapping(value = "/adjuntarInforme", method = RequestMethod.POST)
	  	public void adjuntarInforme(@RequestParam("archivo") MultipartFile file, HttpServletRequest request, HttpServletResponse response, HttpSession session, 
	  										String ruc, String idSupervisionRechCargaInforme, String numeroExpediente, String flag, String observacion,
	  										String nombreEmpresaInf, String anioInf) {
			//LOG.info("AdjuntarInforme" + nombreEmpresaInf + "anio "+ anioInf);
			LOG.info("nombre = " + file.getOriginalFilename().toUpperCase());
		    String validaDocumento = "";
	        response.setContentType("text/html;charset=utf-8");
	        try {
	           	if( file.getOriginalFilename().toUpperCase()=="" || file.getOriginalFilename().toUpperCase().length()==0){
		 	    	   response.getWriter().write("{\"error\":true,\"mensaje\":\"Por favor seleccione un archivo.\"}");
		 	    	   return;
		 	       }
	            validaDocumento = validaSubirDocuAdjuntoOS(file,Constantes.CONSTANTE_TIPOS_ARCHIVOS_INFORME_SUPERVISION,null);
	            if(!validaDocumento.trim().isEmpty()){
	                response.getWriter().write(validaDocumento);
	                return;
	            }
	            EmpresaViewFilter empresaViewFilter = new EmpresaViewFilter();
	            empresaViewFilter.setRuc(ruc);
	            ExpedienteDTO expedienteDTO = new ExpedienteDTO();
	            	expedienteDTO.setEmpresaSupervisada(new EmpresaSupDTO());
	            	expedienteDTO.setOrdenServicio(new OrdenServicioDTO());
	            	expedienteDTO.setFlujoSiged(new FlujoSigedDTO());
	            	expedienteDTO.setNumeroExpediente(numeroExpediente);
	            	expedienteDTO.setAsuntoSiged(Constantes.CONSTANTE_NOMBRE_PROCESO_DSE + " - " + nombreEmpresaInf + " - "+anioInf);
	            DocumentoAdjuntoDTO documentoAdjuntoDTO = new DocumentoAdjuntoDTO();
	            	documentoAdjuntoDTO.setArchivoAdjunto(file.getBytes());
	            	documentoAdjuntoDTO.setNombreArchivo(file.getOriginalFilename().toUpperCase());
	            String username = Constantes.getUSUARIO(request);
	            List<PersonalDTO> listPersona = personalServiceNeg.findPersonal(new PersonalFilter(username, null));
	            PersonalDTO personalDTO = new PersonalDTO();
	            	personalDTO.setIdPersonalSiged(listPersona.get(0).getIdPersonalSiged());
	            	expedienteDTO.setPersonal(personalDTO);
	            DocumentoAdjuntoDTO docEnviado = this.empresaSupervisionViewService.adjuntarInformeSiged(documentoAdjuntoDTO, expedienteDTO, empresaViewFilter);
	            InformeSupeRechCargaDTO informeSupeRechCargaDTO = new InformeSupeRechCargaDTO();
	            if(flag==null) flag="0";
	            	informeSupeRechCargaDTO.setFlgObservado(flag);
	            	informeSupeRechCargaDTO.setObservacion(observacion);
	            	informeSupeRechCargaDTO.setIdInformeDoc(docEnviado.getIdArchivo());
	            	//informeSupeRechCargaDTO.setNombreInformeDoc(Constantes.DOCUMENTO_INFORME);
	            	informeSupeRechCargaDTO.setNombreInformeDoc(documentoAdjuntoDTO.getNombreArchivo());
	            SupervisionRechCargaDTO supervisionRechCargaDTO = new SupervisionRechCargaDTO();
	            	supervisionRechCargaDTO.setIdSupervisionRechCarga(Long.parseLong(idSupervisionRechCargaInforme));
	            	informeSupeRechCargaDTO.setIdSupervisionRechCarga(supervisionRechCargaDTO);
	            
	            
	            	List<DocumentoAdjuntoDTO> listado=archivoServiceNeg.listarDocumentosSiged(String.valueOf(numeroExpediente));
	            	if(listado!=null && listado.size()>0){
	            	for(DocumentoAdjuntoDTO documento : listado){
	            		if(documento!=null && docEnviado!=null && documento.getIdDocumento()!=null){
	            		if(documento.getIdDocumento().longValue()==docEnviado.getIdDocumento().longValue()){
	            			informeSupeRechCargaDTO.setIdInformeDoc(documento.getIdArchivo());
	            			break;
	            		}
	            	  }
	                }
	            	}
	        	informeSupeRechCargaServiceNeg.registrar(informeSupeRechCargaDTO, Constantes.getUsuarioDTO(request));
	            if(docEnviado.getEstado().equals(Constantes.FLAG_REGISTRADO_SI)){
	            	response.getWriter().write("{\"error\":false,\"mensaje\":\""+ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_CARGAR_DOCUMENTO+"\"}");
	            }else{
	            	response.getWriter().write("{\"error\":true,\"mensaje\":\""+docEnviado.getComentario()+"\"}");
	            }
	            return;
	        } catch (Exception e) {
	                LOG.error("Error subiendo archivo BL", e);
	        try {
	                response.getWriter().write("{\"error\":true,\"mensaje\":\"Error al subir archivo\"}");
	        } catch (IOException ex) {
	                LOG.debug("error al escribir en response", ex);
	                ex.printStackTrace();
	        }
	            return;
	        }
	    }
	   
	 
	  private String validaSubirDocuAdjuntoOS(MultipartFile file,String extenciones,Long sizeMax){
	    	LOG.info("validaSubirDocuAdjuntoOS");
		    LOG.info("nombre = " + file.getOriginalFilename().toUpperCase());
		    LOG.info("tamanio file=" + file.getSize());
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
	                        mensaje="{\"error\":true,\"mensaje\":\"No se puede adjuntar un archivo que supera los 2MB, revise el tama&ntilde;o del archivo que intenta adjuntar.\"}";
	                        return mensaje;
	                    }
	                }
	            } catch (Exception e) {
	                LOG.error("Error validaSubirDocuAdjuntoOS", e);
	            }
		    return mensaje;
	  }	 

	  
	 

	@RequestMapping(value="/listarDocumentos", method = RequestMethod.POST)
	  public @ResponseBody Map<String, Object>  listarDocumentosPorExpediente(	HttpSession session, HttpServletRequest request, int page, int rows,  String numeroExpediente ) throws EmpresaSupervisionViewException{
		  Map<String, Object> retorno = new HashMap<String, Object>();
		  try {
				List<DocumentoAdjuntoDTO> listaDocumentos =new ArrayList<DocumentoAdjuntoDTO>();
		    	listaDocumentos = archivoServiceNeg.listarDocumentosSiged(numeroExpediente);
		    	   
		    	if(listaDocumentos!=null && listaDocumentos.size()>0){
		    	   	for (DocumentoAdjuntoDTO documentoAdjuntoDTO : listaDocumentos) {
		    	  			System.out.println("##" + documentoAdjuntoDTO.getNombreArchivo());
		    	  			System.out.println("##" + documentoAdjuntoDTO.getFechaCarga());

				  		}
		    	   	int cuentaintercambios=0;
		    	   	int varUno=0;
		    		//Usamos un bucle anidado, saldra cuando este ordenado el array
		    		for (boolean ordenado=false;!ordenado;){
		    			for (int i=0;i<listaDocumentos.size()-1;i++){
		    				varUno = listaDocumentos.get(i).getFechaCarga().compareTo(listaDocumentos.get(i+1).getFechaCarga());
		    				System.out.println("##" + varUno);
		    				if (varUno==1){
		    					//Intercambiamos valores
		    					DocumentoAdjuntoDTO variableauxiliar=listaDocumentos.get(i);
		    					listaDocumentos.set(i,listaDocumentos.get(i+1));
		    					listaDocumentos.set(i+1,variableauxiliar);
		    					//indicamos que hay un cambio
		    					cuentaintercambios++;
		    				}
		    			}
		    			//Si no hay intercambios, es que esta ordenado.
		    			if (cuentaintercambios==0){
		    				ordenado=true;
		    			}
		    			//Inicializamos la variable de nuevo para que empiece a contar de nuevo
		    			cuentaintercambios=0;
		    		}
		    	}
		    	
    	
		    	     int indiceInicial = -1;
					 int indiceFinal = -1;
					 Long contador = (long)listaDocumentos.size();
					 Long numeroFilas = (contador / rows);
					 if ((contador % rows) > 0) {numeroFilas = numeroFilas + 1L;}
					 if(numeroFilas<page){page = numeroFilas.intValue();}
				     if(page == 0){rows = 0;}
				     indiceInicial = (page - 1) * rows;
				     indiceFinal = indiceInicial + rows;
				     List<DocumentoAdjuntoDTO> listaPaginada = listaDocumentos.subList(indiceInicial > listaDocumentos.size() ? listaDocumentos.size() : indiceInicial, indiceFinal > listaDocumentos.size() ? listaDocumentos.size() : indiceFinal);
				     retorno.put("filas", listaPaginada );
				     retorno.put("registros", contador);
				     retorno.put("pagina", page);
				     retorno.put("total", numeroFilas);
			} catch (Exception e) {
				LOG.error("Error en listarDocumentos");
			}
		  return retorno;
	  } 

}
