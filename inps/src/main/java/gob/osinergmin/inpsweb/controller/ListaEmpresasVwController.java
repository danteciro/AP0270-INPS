package gob.osinergmin.inpsweb.controller;

import gob.osinergmin.inpsweb.service.business.ArchivoServiceNeg;
import gob.osinergmin.inpsweb.service.business.DocumentoAdjuntoServiceNeg;
import gob.osinergmin.inpsweb.service.business.EmpresaSupervisionViewServiceNeg;
import gob.osinergmin.inpsweb.service.business.ExpedienteServiceNeg;
import gob.osinergmin.inpsweb.service.business.ListaEmpresasVwServiceNeg;
import gob.osinergmin.inpsweb.service.business.MaestroColumnaServiceNeg;
import gob.osinergmin.inpsweb.service.business.PersonalServiceNeg;
import gob.osinergmin.inpsweb.service.dao.ListaEmpresasVwDAO;
import gob.osinergmin.inpsweb.service.exception.ListaEmpresasVwException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.ConstantesWeb;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresaViewDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.FechaHoraDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.SupeCampRechCargaDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionRechCargaDTO;
import gob.osinergmin.mdicommon.domain.ui.EmpresaViewFilter;
import gob.osinergmin.siged.remote.rest.ro.out.ArchivoOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.ClienteOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.list.ListDocumentoOutRO;
import gob.osinergmin.siged.rest.util.ExpedienteInvoker;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.hql.internal.CollectionSubqueryFactory;
import org.hibernate.mapping.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/empresasVw")
public class ListaEmpresasVwController {
	 private static final Logger LOG = LoggerFactory.getLogger(ListaEmpresasVwController.class);

	    @Value("${tamanio.maximo.archivo.megas}")
	    private Long TAMANIO_MAXIMO_ARCHIVO_MEGAS;
		 @Inject
		 private EmpresaSupervisionViewServiceNeg empresaSupervisionViewService;
	    @Value("${alfresco.space.dir.obligaciones}")
	    private String ALFRESCO_SPACE_OBLIGACIONES;
	    @Inject
	    private PersonalServiceNeg personalServiceNeg;
	    @Inject
	    private ExpedienteServiceNeg expedienteServiceNeg;
	    @Inject
	    private MaestroColumnaServiceNeg maestroColumnaServiceNeg;
	    @Inject
	    private ListaEmpresasVwDAO listaEmpresasVwService;
	    @Inject
	    private ListaEmpresasVwServiceNeg listaEmpresasVwServiceNeg;
	    @Inject
	    private DocumentoAdjuntoServiceNeg documentoAdjuntoServiceNeg;
	    @Inject
	    private ArchivoServiceNeg archivoServiceNeg;
	    
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
	    
	    @RequestMapping(value = "/findEmpresaVw", method = RequestMethod.POST)
		public @ResponseBody Map<String, Object> findEmpresaVw(EmpresaViewFilter filtro,int rows, int page, HttpSession session,HttpServletRequest request) {
			LOG.info("procesando POST para RequestMapping /findEmpresaVw");
	    	Map<String, Object> retorno = new HashMap<String, Object>();    	
			try {
			    if(filtro.getEmpresa()!=null && filtro.getEmpresa()!="")
	            	filtro.setEmpresa(filtro.getEmpresa().toUpperCase());
			    
			    if(filtro.getNumeroExpediente()!=null && filtro.getNumeroExpediente()!="")
	            	filtro.setNumeroExpediente(filtro.getNumeroExpediente().toUpperCase());
			    
			    if(filtro.getFlagObservado()!=null && filtro.getFlagObservado()!="")
	            	filtro.setFlagObservado(filtro.getFlagObservado().toUpperCase());
			    
			    LOG.info("::::::::"+filtro.getNumeroExpediente());
				LOG.info("procesando POST para RequestMapping de /findEmpresaVw");			
				List<EmpresaViewDTO> listaEmpresasVw = new ArrayList<EmpresaViewDTO>();
				listaEmpresasVw=listaEmpresasVwService.findEmpresasVw(filtro);
				
				/*
				  for (EmpresaViewDTO registro : listaEmpresasVw) {
				 		 registro.setFlagObservado(listaEmpresasVwService.obtenerArchivosObservados(registro.getNumeroExpediente()));
			            }  
				 */
		    	
				int indiceInicial = -1;
				int indiceFinal = -1;
				Long contador = (long) 0;
				if(listaEmpresasVw!=null && listaEmpresasVw.size()!=0)
					contador = (long)listaEmpresasVw.size();
				Long numeroFilas = (contador / rows);
				if ((contador % rows) > 0) {numeroFilas = numeroFilas + 1L;}
				if(numeroFilas<page){page = numeroFilas.intValue();}
	            if(page == 0){rows = 0;}
	            indiceInicial = (page - 1) * rows;
	            indiceFinal = indiceInicial + rows;
				List<EmpresaViewDTO> listaDetallaEmpresaVw = listaEmpresasVw.subList(indiceInicial > listaEmpresasVw.size() ? listaEmpresasVw.size() : indiceInicial, indiceFinal > listaEmpresasVw.size() ? listaEmpresasVw.size() : indiceFinal);
				
			 
				
				retorno.put("total", numeroFilas);
				retorno.put("pagina", page);
				retorno.put("registros", contador);
				retorno.put("filas", listaDetallaEmpresaVw);
			} catch (Exception e) {
				LOG.error("error findEmpresaVw ", e);
		    }
		    return retorno;
		}
	    
	    @RequestMapping(value = "/abrirGeneraOficio", method = RequestMethod.POST)
	    public String abrirGeneraOficio( String ruc,String idEmpresa,  String anio,String empresa,HttpSession sesion, Model model, HttpServletRequest request) {
	        LOG.info("abrirGeneraOficio");
	        
	        String firmaOficio = ""; 
	        model.addAttribute("ruc", ruc);
	        model.addAttribute("idEmpresa", idEmpresa);
	        model.addAttribute("empresa",empresa);
	        model.addAttribute("anio", anio);
	       
	        
	        List<MaestroColumnaDTO> lista= maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_FIRMA, Constantes.APLICACION_INPS);
	        for (MaestroColumnaDTO registro : lista) {
	        	firmaOficio += registro.getDescripcion();
            }        
	        model.addAttribute("firmaOficio",firmaOficio);
	        return ConstantesWeb.Navegacion.PAGE_INPS_GENERAR_OFICIO;
	    }
	    
 
	    @RequestMapping(value = "/generarExpedienteRechazoCarga", method = {RequestMethod.POST,RequestMethod.GET})
	    public @ResponseBody
	    Map<String, Object> generarExpedienteRechazoCarga(String ruc,String idEmpresa, String anio,String empresa, HttpSession session, HttpServletRequest request) {
	        LOG.info("generarExpedienteRechazoCarga");
	        Map<String, Object> retorno = new HashMap<String, Object>();
	        try {
	        	System.out.println("::generarExpedienteRechazoCarga:::"+ruc+" "+idEmpresa+" "+anio+" "+empresa);
             	ExpedienteDTO expedienteDto = new ExpedienteDTO();
	        	expedienteDto.setAsuntoSiged(Constantes.CONSTANTE_NOMBRE_PROCESO_DSE + " - " + empresa+ " - " + anio);
	        	 
	        	  DocumentoAdjuntoDTO archivoDTO =(DocumentoAdjuntoDTO) request.getSession().getAttribute("archivoDTO");
	        	  String nombreArchivo = archivoDTO.getNombreArchivo();
	        	  byte[] rutaArchivo = archivoDTO.getRutaAlfrescoTmp();
	 
	        	  
	        	 ExpedienteDTO expedienteSiged = listaEmpresasVwServiceNeg.generarExpedienteRechazoCarga(archivoDTO,ruc,expedienteDto, Constantes.getIDPERSONALSIGED(request));
	        	 expedienteDto.setNumeroExpediente(expedienteSiged.getNumeroExpediente()); 
 
	     
	        	 if (expedienteSiged == null || expedienteSiged.getNumeroExpediente() == null  ) {
	                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
	                retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_ERROR_SIGED);

	            } else {	     
	            	
	            	List<DocumentoAdjuntoDTO> lDocumentoAdjuntoDTO = archivoServiceNeg.listarDocumentosSiged(expedienteSiged.getNumeroExpediente());
	            	if(!CollectionUtils.isEmpty(lDocumentoAdjuntoDTO)){
	            		DocumentoAdjuntoDTO docAdj = lDocumentoAdjuntoDTO.get(0);
	            		DocumentoAdjuntoDTO bandera=archivoServiceNeg.enumerarDocumentoSiged(String.valueOf(expedienteSiged.getNumeroExpediente()),docAdj.getIdDocumento().intValue(),Constantes.getIDPERSONALSIGED(request).intValue());
	            		
	            		if(!bandera.getEstado().equals(Constantes.FLAG_ENUMERADO_SI)){
	        				//retorno.put(ConstantesWeb.VV_MENSAJE, bandera.getComentario());
	        				//retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ADVERTENCIA);
	        			}
	            	}	
	            	
	    			
	            	
	            	SupervisionRechCargaDTO registroDTO = new SupervisionRechCargaDTO();
		        	registroDTO.setEmpCodemp(idEmpresa);
		        	registroDTO.setIdOficioDoc(expedienteSiged.getIdExpediente());
		        	registroDTO.setNumeroExpediente(expedienteSiged.getNumeroExpediente());
		        	registroDTO.setNombreOficioDoc(nombreArchivo);
		        	 
		        	String fecha = "January 1, "+anio;
		        	DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
		        	Date date = format.parse(fecha);

		        	registroDTO.setAnio(date);
		        	listaEmpresasVwServiceNeg.registrarSupervisionRechazoCarga(registroDTO, Constantes.getUsuarioDTO(request));
	                expedienteDto.setNumeroExpediente(expedienteSiged.getNumeroExpediente());
	                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
		            retorno.put(ConstantesWeb.VV_MENSAJE, "Cambios realizados.");
	            }

	        } catch (Exception e) {
	            LOG.error("Error en generarExpedienteRechazoCarga", e);
	            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
	            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
	        }
	        return retorno;
	    }
	    
	    public boolean validaFormatoPermitido(String[] extenciones, String nombre){
	        boolean permitida = false;
	        int lastIndexOf = nombre.lastIndexOf(".");
	        String formato = nombre.substring(lastIndexOf);

	        for (int i = 0; i < extenciones.length; i++) {
	            if (formato.equalsIgnoreCase(extenciones[i])) {
	                permitida = true;
	                break;
	            }
	        }
	        return permitida;
	    }
	    

	    @RequestMapping(value = "/subirOficioRechazoCarga", method = RequestMethod.POST)
	    public void subirOficioRechazoCarga(@RequestParam("archivos[0]") MultipartFile file,HttpSession session, HttpServletRequest request, HttpServletResponse response) {
	       LOG.info("subirOficioRechazoCarga");
	       LOG.info("nombre = " + file.getOriginalFilename().toUpperCase());
	        request.getSession().removeAttribute("archivoDTO");
	        String tipoExtencionesArchivoBaseLegal=".docx|.doc";
	       response.setContentType("text/html;charset=utf-8");

	        try {
	        	if(file.getOriginalFilename().toUpperCase()=="" || file.getOriginalFilename().toUpperCase().length()==0){
	 	    	   response.getWriter().write("{\"error\":true,\"mensaje\":\"Por favor seleccione un archivo.\"}");
	 	    	   return;
	 	       }
	        	
	            LOG.info("formatos permitidos: "+tipoExtencionesArchivoBaseLegal);
	            LOG.info("tamaño file="+file.getSize());
	            String[] extenciones = tipoExtencionesArchivoBaseLegal.split("\\|");
	            String strExtenciones = "";
	            for (int i = 0; i < extenciones.length; i++) {
	                strExtenciones += extenciones[i] + ", ";
	           }
	            if(!strExtenciones.isEmpty()){
	                strExtenciones = strExtenciones.substring(0,strExtenciones.length()-2);
	            }
	            if (!validaFormatoPermitido(extenciones,file.getOriginalFilename())) {
	                response.getWriter().write("{\"error\":true,\"mensaje\":\"Formato no permitido. ( Extensiones permitidas: "+strExtenciones+") \"}");
	                return;
	            }
	            if(file.getSize()>new Long(4000000)){
	                response.getWriter().write("{\"error\":true,\"mensaje\":\"Tamaño maximo permitido 40 MB.\"}");
	                return;
	            }

	            
	            DocumentoAdjuntoDTO archivoDTO = new DocumentoAdjuntoDTO();
	            archivoDTO.setNombreArchivo(file.getOriginalFilename().toUpperCase());
	            archivoDTO.setRutaAlfrescoTmp(file.getBytes());
	           

	            request.getSession().setAttribute("archivoDTO", archivoDTO);
	       
	            response.getWriter().write("{\"error\":false,\"mensaje\":\"Se cargo el archivo\"}");
	   
	            return;
	            
	        } catch (Exception e) {
	            LOG.error("Error subiendo oficio", e);
	            try {
	                response.getWriter().write("{\"error\":true,\"mensaje\":\"Error al insertar Documento\"}");
	            } catch (IOException ex) {
	                LOG.debug("error al escribir en response", ex);
	                ex.printStackTrace();
	            }
	            return;
	        }
	    }
	    
	    
	    
	    @RequestMapping(value = "/abrirActaExpedientesZona", method = RequestMethod.POST)
	    public String abrirActaExpedientesZona( String numeroExpediente ,Model model, HttpSession session, HttpServletRequest request) throws ListaEmpresasVwException {
	    	 LOG.info("abrirActaExpedientesZona  "+numeroExpediente);	
	    	Map<String, Object> retorno = new HashMap<String, Object>();
	       String mensaje="";
	       mensaje="Aun no se ha realizado el Acta de Inspección por Zona";

		   List<SupeCampRechCargaDTO> supeCampRechCarga = new ArrayList<SupeCampRechCargaDTO>();
		   supeCampRechCarga=listaEmpresasVwService.listaActasZona(numeroExpediente);

		 	if(supeCampRechCarga.size()>0){
		 		  model.addAttribute("numeroExpediente", numeroExpediente);
		 		  return ConstantesWeb.Navegacion.PAGE_INPS_ACTA_EXPEDIENTES_ZONA;
	      }else if(supeCampRechCarga.size()==0){
	       	   retorno.put(ConstantesWeb.VV_RESULTADO, mensaje);
	           retorno.put(ConstantesWeb.VV_MENSAJE, mensaje);
	        }
		 	    
			 
			   return numeroExpediente;
	    	 
	    }
	    
	    @RequestMapping(value = "/listarActaZonaPorExpediente", method = RequestMethod.POST)
		 public @ResponseBody Map<String, Object> listarActaZonaPorExpediente(String numeroExpediente,int rows, int page,Model model, HttpSession session,HttpServletRequest request) throws ListaEmpresasVwException {
				LOG.info("procesando POST para RequestMapping /listarActaZonaPorExpediente");
 
		    	Map<String, Object> retorno = new HashMap<String, Object>();    	
				try {
			 
				List<SupeCampRechCargaDTO> listaDocumentosActaZona= new ArrayList<SupeCampRechCargaDTO>();
				listaDocumentosActaZona=listaEmpresasVwService.listaActasZona(numeroExpediente);
				
		            if (listaDocumentosActaZona == null ) {
		                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
		                retorno.put(ConstantesWeb.VV_MENSAJE, "El servicio SIGED no se encuentra disponible.");
		            } else {	                
		                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
			            retorno.put(ConstantesWeb.VV_MENSAJE, "Cambios realizados.");
		            }
		            
		            int indiceInicial = -1;
					int indiceFinal = -1;
					Long contador = (long) 0;
					if(listaDocumentosActaZona!=null && listaDocumentosActaZona.size()!=0)
						contador = (long)listaDocumentosActaZona.size();
					Long numeroFilas = (contador / rows);
					if ((contador % rows) > 0) {numeroFilas = numeroFilas + 1L;}
					if(numeroFilas<page){page = numeroFilas.intValue();}
		            if(page == 0){rows = 0;}
		            indiceInicial = (page - 1) * rows;
		            indiceFinal = indiceInicial + rows;
					List<SupeCampRechCargaDTO> listaDetalla = listaDocumentosActaZona.subList(indiceInicial > listaDocumentosActaZona.size() ? listaDocumentosActaZona.size() : indiceInicial, indiceFinal > listaDocumentosActaZona.size() ? listaDocumentosActaZona.size() : indiceFinal);
					
					retorno.put("total", numeroFilas);
					retorno.put("pagina", page);
					retorno.put("registros", contador);
					retorno.put("filas", listaDetalla);
				
			    } catch (Exception e) {
					LOG.error("error listarActaZonaPorExpediente ", e);
			    }
			    return retorno;
	}
	    

	   
}
