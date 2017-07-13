/**
* Resumen		
* Objeto		: ArchivoController.java
* Descripcion		: Controla el flujo de datos del objeto Archivo
* Fecha de Creacion	: 
* PR de Creacion	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripcion
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     09/05/2016      Mario Dioses Fernandez      Registrar en BD campo FECHA_INICIO_PLAZO y FECHA_FIN_PLAZO luego de CONCLUIR_OS, considerando Plazo_Descargo por Rubro (MDI_ACTIVIDAD)
* OSINE_SFS-480     06/06/2016      Mario Dioses Fernandez      Construir formulario de envio a Mensajeria, consumiendo WS
* OSINE_791-RSIS8   08/31/2016      Cristopher Paucar Torre     Registrar Medio Probatorio.
* OSINE_MANT_DSHL_003  27/06/2017   Claudio Chaucca Umana::ADAPTER   Omite funcionalidad en la funcion para validar la subida de documentos adjuntos.
*/ 

package gob.osinergmin.inpsweb.controller;
import gob.osinergmin.inpsweb.service.business.ArchivoServiceNeg;
import gob.osinergmin.inpsweb.service.business.DocumentoAdjuntoServiceNeg;
import gob.osinergmin.inpsweb.service.business.MaestroColumnaServiceNeg;
import gob.osinergmin.inpsweb.service.business.PlantillaResultadoServiceNeg;
import gob.osinergmin.inpsweb.service.business.SupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.business.UnidadSupervisadaServiceNeg;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.ConstantesWeb;
import gob.osinergmin.inpsweb.util.Utiles;
import gob.osinergmin.mdicommon.domain.dto.ClienteDetalleDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
//htorress - RSIS 8 - Inicio
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.FlujoSigedDTO;
import gob.osinergmin.mdicommon.domain.dto.OrdenServicioDTO;
//htorress - RSIS 8 - Fin
//htorress - RSIS 9 - Inicio
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadSupervisadaDTO;

import org.springframework.ui.Model;


//htorress - RSIS 9 - Fin
import gob.osinergmin.mdicommon.domain.ui.DetalleSupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.DocumentoAdjuntoFilter;
import gob.osinergmin.mdicommon.domain.ui.UnidadSupervisadaFilter;
import gob.osinergmin.siged.remote.rest.ro.in.EnviarMensajeriaIn;
import gob.osinergmin.siged.remote.rest.ro.out.ConsultaMensajeriaDocumentosItemOut;
import gob.osinergmin.siged.remote.rest.ro.out.EnviarMensajeriaOut;
import gob.osinergmin.siged.remote.rest.ro.out.ObtenerDestinatariosOut;
import gob.osinergmin.siged.remote.rest.ro.out.ObtenerOficinaRegionalOut;
import gob.osinergmin.siged.remote.rest.ro.out.ValidarDocumentoOut;
import gob.osinergmin.siged.remote.rest.ro.out.obtenerClienteDetalleOut;
import gob.osinergmin.siged.remote.rest.ro.out.query.ExpedienteConsultaOutRO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.cxf.helpers.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author jpiro
 */
@Controller
@RequestMapping("/archivo")
public class ArchivoController {
    private static final Logger LOG = LoggerFactory.getLogger(ArchivoController.class);

    @Value("${tamanio.maximo.archivo.megas}")
    private Long TAMANIO_MAXIMO_ARCHIVO_MEGAS;
    
    @Value("${alfresco.space.dir.obligaciones}")
    private String ALFRESCO_SPACE_OBLIGACIONES;
    @Inject
	private DocumentoAdjuntoServiceNeg documentoServiceNeg;
    @Inject
    private ArchivoServiceNeg archivoServiceNeg;
    @Inject
    private DocumentoAdjuntoServiceNeg documentoAdjuntoServiceNeg;
    @Inject
    private SupervisionServiceNeg supervisionServiceNeg;
    @Inject
    private PlantillaResultadoServiceNeg plantillaResultadoServiceNeg;
    /* OSINE_SFS-480 - RSIS 17 - Inicio */
    @Inject
    private MaestroColumnaServiceNeg maestroColumnaServiceNeg; 
    /* OSINE_SFS-480 - RSIS 17 - Fin */
    @Inject
    private UnidadSupervisadaServiceNeg unidadSupervisadaServiceNeg;
    
    @RequestMapping(value="/findArchivoExpediente",method= RequestMethod.GET)
    public @ResponseBody Map<String,Object> findArchivoExpediente(String numeroExpediente,Long idOrdenServicio, String flgCargaTemporales,int rows, int page,HttpSession session,HttpServletRequest request){
        LOG.info("findArchivoExpediente");
    	
        Map<String,Object> retorno=new HashMap<String,Object>();
        try{
        	List<DocumentoAdjuntoDTO> listado=archivoServiceNeg.listarDocumentosSiged(numeroExpediente);

        	if(listado!=null && listado.size()>0){
                for(DocumentoAdjuntoDTO reg : listado){
                    reg.setEstadoOrigen(Constantes.ESTADO_ORIGEN_ARCHIVO_SIGED);
                }
            }
            
            // htorress - RSIS 9 - Inicio
            /*
            if(flgCargaTemporales.equals(Constantes.ESTADO_ACTIVO)){
                LOG.info("cargando temporales de OS-->"+idOrdenServicio);
                DocumentoAdjuntoFilter filtro=new DocumentoAdjuntoFilter();
                filtro.setIdOrdenServicio(idOrdenServicio);
                List<DocumentoAdjuntoDTO> listadoTmp=documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(filtro);
                LOG.info("listadoTmp-->"+listadoTmp);
                if(listadoTmp!=null && listadoTmp.size()>0){
                    LOG.info("listadoTmp size-->"+listadoTmp.size());
                    for(DocumentoAdjuntoDTO reg : listadoTmp){
                        reg.setEstadoOrigen(Constantes.ESTADO_ORIGEN_ARCHIVO_INPS);
                    }
                    listado.addAll(listadoTmp);
                }
            }
            */
            // htorress - RSIS 9 - Fin
            
            int indiceInicial = -1;
            int indiceFinal = -1;
            Long contador = (long) listado.size();
            Long numeroFilas = (contador / rows);
            if ((contador % rows) > 0) {numeroFilas = numeroFilas + 1L;}
            if(numeroFilas<page){page = numeroFilas.intValue();}
            if(page == 0){rows = 0;}
            indiceInicial = (page - 1) * rows;
            indiceFinal = indiceInicial + rows;
            List<DocumentoAdjuntoDTO> listadoPaginado = listado.subList(indiceInicial > listado.size() ? listado.size() : indiceInicial, indiceFinal > listado.size() ? listado.size() : indiceFinal );                       
            retorno.put("total", numeroFilas);
            retorno.put("pagina", page);
            retorno.put("registros", contador);
            retorno.put("filas", listadoPaginado);
        }catch(Exception ex){
            LOG.error("",ex);
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
    
    @RequestMapping(value = "/subirPghDocumentoAdjunto", method = RequestMethod.POST)
    public void subirPghDocumentoAdjunto(
        @RequestParam("archivos[0]") MultipartFile file,DocumentoAdjuntoDTO documentoAdjuntoDTO,
        HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
	    String validaDocumento = "";
	    response.setContentType("text/html;charset=utf-8");
	    try {
	    	validaDocumento = validarArchivo(file);
	    	if(!validaDocumento.trim().isEmpty()){
	    		response.getWriter().write(validaDocumento);
	    		return;
	    	}
	        documentoAdjuntoDTO.setArchivoAdjunto(file.getBytes());
	        documentoAdjuntoDTO.setNombreArchivo(file.getOriginalFilename().toUpperCase());
	        documentoAdjuntoDTO.setDescripcionDocumento(documentoAdjuntoDTO.getDescripcionDocumento());
	        documentoAdjuntoDTO = documentoAdjuntoServiceNeg.registrarPghDocumentoAdjunto(documentoAdjuntoDTO, Constantes.getUsuarioDTO(request));
	        response.getWriter().write("{\"error\":false,\"mensaje\":\""+ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_CREATE+"\"}");
	        return;
		} catch (Exception e) {
			LOG.error("Error subiendo archivo BL", e);
	        try {
		        response.getWriter()
		            .write("{\"error\":true,\"mensaje\":\"Error al subir archivo\"}");
	        } catch (IOException ex) {
		        LOG.debug("error al escribir en response", ex);
		        ex.printStackTrace();
	        }
	        return;
		}
	}
    
    @RequestMapping(value = "/subirPghDocumentoAdjuntoDetalleSuper", method = RequestMethod.POST)
    public void subirPghDocumentoAdjuntoDetalleSuper(
        @RequestParam("archivos[0]") MultipartFile file,DocumentoAdjuntoDTO documentoAdjuntoDTO,
        HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
	    String validaDocumento = "";
	    Long idDetalleSupervision = documentoAdjuntoDTO.getDetalleSupervision().getIdDetalleSupervision();
	    String flagResultado = documentoAdjuntoDTO.getDetalleSupervision().getFlagResultado();
	    List<DetalleSupervisionDTO> listaDetalleSupervision=null;
	    DetalleSupervisionDTO detalleSupervision=null;
	    response.setContentType("text/html;charset=utf-8");
	    try {
	    	validaDocumento = validarArchivo(file);
	    	if(!validaDocumento.trim().isEmpty()){
	    		response.getWriter().write(validaDocumento);
	    		return;
	    	}
	    	DetalleSupervisionDTO detalleSupervisionAnt = supervisionServiceNeg.findDetalleSupervision(new DetalleSupervisionFilter(idDetalleSupervision))
					.get(Constantes.PRIMERO_EN_LISTA);
	    	if(!detalleSupervisionAnt.getFlagResultado().equals(flagResultado)){
	    		DocumentoAdjuntoFilter filtro = new DocumentoAdjuntoFilter();
	    		filtro.setIdDetalleSupervision(idDetalleSupervision);
	    		List<DocumentoAdjuntoDTO> listaDocumento = documentoServiceNeg.listarPghDocumentoAdjunto(filtro);
            	if(!listaDocumento.isEmpty()){
            		for(DocumentoAdjuntoDTO documento:listaDocumento){
            			documentoServiceNeg.eliminarPghDocumentoAdjunto(documento);
            		}
            	}
	    	}
	        documentoAdjuntoDTO.setArchivoAdjunto(file.getBytes());
	        documentoAdjuntoDTO.setNombreArchivo(file.getOriginalFilename().toUpperCase());
	        documentoAdjuntoDTO.setDescripcionDocumento(documentoAdjuntoDTO.getDescripcionDocumento());
	        documentoAdjuntoDTO = documentoAdjuntoServiceNeg.registrarPghDocumentoAdjunto(documentoAdjuntoDTO, Constantes.getUsuarioDTO(request));
	        listaDetalleSupervision=supervisionServiceNeg.findDetalleSupervision(new DetalleSupervisionFilter(idDetalleSupervision));
	        if(!listaDetalleSupervision.isEmpty()){
	        	detalleSupervision=listaDetalleSupervision.get(Constantes.PRIMERO_EN_LISTA);
	        	detalleSupervision.setEstado(Constantes.ESTADO_ACTIVO);
	        	detalleSupervision.setFlagRegistrado(Constantes.FLAG_REGISTRADO_SI);
	        	detalleSupervision.setFlagResultado(flagResultado);
	        	if(!detalleSupervisionAnt.getFlagResultado().equals(flagResultado)){
	        		detalleSupervision.setDescripcionResultado(null);
	        		detalleSupervision.setTipificacion(null);
	        		detalleSupervision.setCriterio(null);
	        	}
	        	supervisionServiceNeg.actualizarDetalleSupervision(detalleSupervision,Constantes.getUsuarioDTO(request));
	        }
	        response.getWriter().write("{\"error\":false,\"mensaje\":\""+ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_CREATE+"\"}");
	        return;
		} catch (Exception e) {
			LOG.error("Error subiendo archivo BL", e);
	        try {
		        response.getWriter()
		            .write("{\"error\":true,\"mensaje\":\"Error al subir archivo\"}");
	        } catch (IOException ex) {
		        LOG.debug("error al escribir en response", ex);
		        ex.printStackTrace();
	        }
	        return;
		}
	}
    
    @RequestMapping(value = "/subirPghDocuAdjuntoOS", method = RequestMethod.POST)
    public void subirPghDocuAdjuntoOS(@RequestParam("archivo") MultipartFile file,DocumentoAdjuntoDTO documentoAdjuntoDTO, 
        Long idDirccionUnidadSuprvisada, Long idExpediente, String asuntoSiged, Long codigoSiged, Long idOrdenServicio, Long numeroExpediente, 
        HttpServletRequest request, HttpServletResponse response, HttpSession session) {
	LOG.info("subirPghDocuAdjuntoOS");	
        String validaDocumento = "";
        response.setContentType("text/html;charset=utf-8");
        try {
            validaDocumento = validaSubirDocuAdjuntoOS(file,Constantes.CONSTANTE_TIPOS_ARCHIVOS_ORDEN_SERVICIO,null);
            if(!validaDocumento.trim().isEmpty()){
                response.getWriter().write(validaDocumento);
                return;
            }
            documentoAdjuntoDTO.setArchivoAdjunto(file.getBytes());
            documentoAdjuntoDTO.setNombreArchivo(file.getOriginalFilename().toUpperCase());
            // htorress - RSIS 8 - Inicio
            //documentoAdjuntoDTO = documentoAdjuntoServiceNeg.registrarPghDocumentoAdjunto(documentoAdjuntoDTO, Constantes.getUsuarioDTO(request));
            documentoAdjuntoDTO.setDescripcionDocumento(documentoAdjuntoDTO.getDescripcionDocumento().toUpperCase());
            ExpedienteDTO expedienteDTO = new ExpedienteDTO();
//            expedienteDTO.setEmpresaSupervisada(new EmpresaSupDTO());
            expedienteDTO.setOrdenServicio(new OrdenServicioDTO());
            expedienteDTO.setFlujoSiged(new FlujoSigedDTO());
            
            expedienteDTO.setAsuntoSiged(asuntoSiged);
//            expedienteDTO.getEmpresaSupervisada().setIdEmpresaSupervisada(idEmpresaSupervisada);
            expedienteDTO.getFlujoSiged().setCodigoSiged(codigoSiged);
            expedienteDTO.setIdExpediente(idExpediente);
            expedienteDTO.setNumeroExpediente(String.valueOf(numeroExpediente));
            expedienteDTO.getOrdenServicio().setIdOrdenServicio(idOrdenServicio);
            
            UnidadSupervisadaFilter unidadFilt=new UnidadSupervisadaFilter();
            unidadFilt.setIdUnidadSupervisada(documentoAdjuntoDTO.getIdUnidadSupervisada().getIdUnidadSupervisada());
            UnidadSupervisadaDTO unidad=unidadSupervisadaServiceNeg.getUnidadSupervisadaDTO(unidadFilt).get(0);
            expedienteDTO.setUnidadSupervisada(unidad);
            
            DocumentoAdjuntoDTO docEnviado=documentoAdjuntoServiceNeg.enviarPghDocOrdenServicioSiged(documentoAdjuntoDTO,expedienteDTO,Constantes.getIDPERSONALSIGED(request),idDirccionUnidadSuprvisada);
            
            if(docEnviado.getEstado().equals(Constantes.FLAG_REGISTRADO_SI)){
            	response.getWriter().write("{\"error\":false,\"mensaje\":\""+ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_CARGAR_DOCUMENTO+"\"}");
            }else{
            	response.getWriter().write("{\"error\":true,\"mensaje\":\""+docEnviado.getComentario()+"\"}");
            }
            //response.getWriter().write("{\"error\":false,\"mensaje\":\"Se cargo archivo satisfactoriamente\"}");
            // htorress - RSIS 8 - Fin
            
            return;
        } catch (Exception e) {
                LOG.error("Error subiendo archivo BL", e);
        try {
                response.getWriter()
                    .write("{\"error\":true,\"mensaje\":\"Error al subir archivo\"}");
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
            	
            	//OSINE_MANT_DSHL_003 - inicio
            	/*
                String[] lstExtensiones = extenciones.split("\\|");
                if (!Utiles.validaFormatoPermitido(lstExtensiones, file.getOriginalFilename())) {
                    mensaje="{\"error\":true,\"mensaje\":\"Formato no permitido. ( Extenciones permitidas: "+ extenciones.replace(".", "").replace("|", ", ") + ") \"}";
		            return mensaje;
		        }
		        */
                //OSINE_MANT_DSHL_003 - fin
                
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
    
    @RequestMapping(value = "/eliminarPghDocumentoAdjunto", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> eliminarPghDocumentoAdjunto(DocumentoAdjuntoDTO documentoAdjuntoDTO, HttpSession session) {
		LOG.info("eliminarPghDocumentoAdjunto - inicio");
		Map<String, Object> retorno = new HashMap<String, Object>();
		try {
			int bandera=documentoAdjuntoServiceNeg.eliminarPghDocumentoAdjunto(documentoAdjuntoDTO);
			if(bandera==1){
				retorno.put(ConstantesWeb.VV_MENSAJE,
						ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_DELETE);
				retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
			}else{
				retorno.put(ConstantesWeb.VV_MENSAJE,"Error al eliminar el archivo seleccionado.");
				retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
			}
		} catch (Exception ex) {
			retorno.put(ConstantesWeb.VV_MENSAJE,"Error al eliminar Documento Adjunto");
			retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
			LOG.error("Error eliminarDocumentoAdjunto",ex);
		}
		LOG.info("eliminarPghDocumentoAdjunto - fin");
		return retorno;
	}
    
    @RequestMapping(value = "/eliminarPghDocAdjDetalleSuper", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> eliminarPghDocAdjDetalleSuper(DocumentoAdjuntoDTO documentoAdjuntoDTO, HttpServletRequest request, HttpSession session) {
		LOG.info("eliminarPghDocAdjDetalleSuper - inicio");
		Map<String, Object> retorno = new HashMap<String, Object>();
		List<DetalleSupervisionDTO> listaDetalleSupervision=null;
	    DetalleSupervisionDTO detalleSupervision=null;
	    List<DocumentoAdjuntoDTO> listaDocumento=null;
		try {
			int bandera=documentoAdjuntoServiceNeg.eliminarPghDocumentoAdjunto(documentoAdjuntoDTO);
			if(bandera==1){
				//validamos si la lista esta llena
				DocumentoAdjuntoFilter filtro = new DocumentoAdjuntoFilter();
            	filtro.setIdDetalleSupervision(documentoAdjuntoDTO.getDetalleSupervision().getIdDetalleSupervision());
            	listaDocumento = documentoServiceNeg.listarPghDocumentoAdjunto(filtro);
            	if(listaDocumento.isEmpty()){
            		//encontramos el detalle para actualizar
            		listaDetalleSupervision=supervisionServiceNeg.findDetalleSupervision(new DetalleSupervisionFilter(documentoAdjuntoDTO.getDetalleSupervision().getIdDetalleSupervision()));
            		if(!listaDetalleSupervision.isEmpty()){
    		        	detalleSupervision=listaDetalleSupervision.get(Constantes.PRIMERO_EN_LISTA);
    		        	//validamos el registrado
    		    		if((detalleSupervision.getTipificacion()==null || detalleSupervision.getTipificacion().getIdTipificacion()==null) && 
    		    				(detalleSupervision.getDescripcionResultado()==null || Constantes.CONSTANTE_VACIA.equals(detalleSupervision.getDescripcionResultado().trim()))){
    		    			detalleSupervision.setFlagRegistrado(Constantes.FLAG_REGISTRADO_NO);
    		    			detalleSupervision.setEstado(Constantes.ESTADO_ACTIVO);
    		    			supervisionServiceNeg.actualizarDetalleSupervision(detalleSupervision,Constantes.getUsuarioDTO(request));
    		            }
    		        }
            	}
            	retorno.put(ConstantesWeb.VV_MENSAJE,ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_DELETE);
				retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
			}else{
				retorno.put(ConstantesWeb.VV_MENSAJE,"Error al eliminar el archivo seleccionado.");
				retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
			}
		} catch (Exception ex) {
			retorno.put(ConstantesWeb.VV_MENSAJE,"Error al eliminar Documento Adjunto");
			retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
			LOG.error("Error eliminarPghDocAdjDetalleSuper",ex);
		}
		LOG.info("eliminarPghDocAdjDetalleSuper - fin");
		return retorno;
	}
    
    @RequestMapping(value="/findPghDocumentoAdjunto",method= RequestMethod.GET)
    public @ResponseBody Map<String,Object> findPghDocumentoAdjunto(DocumentoAdjuntoFilter filtro,int rows, int page,HttpSession session,HttpServletRequest request){
        LOG.info("findPghDocumentoAdjunto - inicio");
        Map<String,Object> retorno=new HashMap<String,Object>();
        try{
            int indiceInicial = -1;
            int indiceFinal = -1;
            List<DocumentoAdjuntoDTO> listadoPaginado = null;
            
        	List<DocumentoAdjuntoDTO> listado=documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(filtro);
        	Long contador = (long) listado.size();
            Long numeroFilas = (contador / rows);
            if ((contador % rows) > 0) { numeroFilas = numeroFilas + 1L; } 
            if(numeroFilas<page){page = numeroFilas.intValue(); }
            if(page==0){rows=0;}
            indiceInicial = (page - 1) * rows;
            indiceFinal = indiceInicial + rows;
            listadoPaginado = listado.subList(indiceInicial > listado.size() ? listado.size() : indiceInicial, indiceFinal > listado.size() ? listado.size() : indiceFinal );  
            retorno.put("total", numeroFilas);
            retorno.put("pagina", page);
            retorno.put("registros", contador);
            retorno.put("filas", listadoPaginado);
        }catch(Exception ex){
            LOG.error("Error findPghDocumentoAdjunto",ex);
        }
        LOG.info("findPghDocumentoAdjunto - fin");
        return retorno;
    }
    
    @RequestMapping(value="/obtenerPghDocumentoAdjunto",method= RequestMethod.GET)
    public @ResponseBody Map<String,Object> obtenerPghDocumentoAdjunto(Long idSupervision,HttpSession session,HttpServletRequest request){
        LOG.info("findPghDocumentoAdjunto - inicio");
        Map<String,Object> retorno=new HashMap<String,Object>();
        DocumentoAdjuntoFilter filtro = new DocumentoAdjuntoFilter();
        List<DocumentoAdjuntoDTO> listado = new ArrayList<DocumentoAdjuntoDTO>();
        filtro.setIdSupervision(idSupervision);
        try{
        	listado = documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(filtro);
            retorno.put("total", listado.size());
            retorno.put("registros", listado);
        }catch(Exception ex){
            LOG.error("Error obtenerPghDocumentoAdjunto",ex);
        }
        LOG.info("obtenerPghDocumentoAdjunto - fin");
        return retorno;
    }
    
    private String validarArchivo(MultipartFile file){
    	LOG.info("validarDocumentoAdjunto");
	    LOG.info("nombre = " + file.getOriginalFilename().toUpperCase());
	    LOG.info("tamanio file=" + file.getSize());
	    String mensaje = "";
	    try {
	        if (file.getSize() > new Long(TAMANIO_MAXIMO_ARCHIVO_MEGAS*Constantes.VALOR_MEGA_BYTE)) {
	        	mensaje="{\"error\":true,\"mensaje\":\"El archivo por agregar debe tener un tama&ntilde;o inferior a "+ TAMANIO_MAXIMO_ARCHIVO_MEGAS+"MB, corregir\"}";
	            return mensaje;
	        }
		} catch (Exception e) {
			LOG.error("Error validarDocumentoAdjunto archivo BL", e);
		}
	    return mensaje;
    }
    
    @RequestMapping(value="/descargaMdiArchivoAlfresco",method= RequestMethod.GET)
    public void descargaMdiArchivoAlfresco( DocumentoAdjuntoDTO filtro, HttpServletResponse response) {
        LOG.info("procesando descargaArchivoAlfresco--->"+filtro.getNombreArchivo());        
        LOG.info("procesando descargaArchivoAlfresco--->"+filtro.getRutaAlfresco());
        DocumentoAdjuntoDTO documentoAdjuntoDTO=documentoServiceNeg.buscarMdiDocumentoAdjuntoDescripcion(filtro.getIdDocumentoAdjunto());
        filtro.setRutaAlfresco(documentoAdjuntoDTO.getRutaAlfresco());
        filtro.setAplicacionSpace(ALFRESCO_SPACE_OBLIGACIONES);
        filtro.setNombreArchivo(documentoAdjuntoDTO.getNombreArchivo());
        InputStream is =documentoServiceNeg.descargarDatosAlfresco(filtro);
        try {        	
    		 if(is==null){
    	        response.getWriter()
    			.write("ERROR: AL DESCARGAR ARCHIVO..!!");
    		    return;
    		 }       	
        	String nombreFichero = filtro.getNombreArchivo();        	
            response.setHeader("Content-Disposition", "attachment; filename=\""
                        + nombreFichero+ "\"");
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();

        }catch (Exception ex) {
            LOG.info("--->"+ex.getMessage());
            LOG.info("Error writing file to output stream. Filename was '" + filtro.getNombreArchivo() + "'");
            throw new RuntimeException("IOError writing file to output stream");
        }   
	
    }
    
    
    @RequestMapping(value="/descargaPghArchivoBD",method= RequestMethod.GET)
    public void descargaPghArchivoBD( DocumentoAdjuntoFilter filtro, HttpServletResponse response) throws IOException {
    	List<DocumentoAdjuntoDTO> documentoAdjunto= documentoAdjuntoServiceNeg.buscaPghDocumentoAdjunto(filtro);
    	byte[] bytes = documentoAdjunto.get(0).getArchivoAdjunto();
        File someFile = new File(documentoAdjunto.get(0).getNombreArchivo());
    	InputStream is = null;
	    	if (bytes!=null) {
	            FileOutputStream fos = new FileOutputStream(someFile);
	            fos.write(bytes);
	            fos.flush();
	            fos.close();
	            LOG.info("file file FOS::>" + fos);
	            LOG.info("file file SOMEFILE::>" + someFile);
	    	    is = FileUtils.openInputStream(someFile);	
			}

    	    try {
			    if (is == null) {
				response.getWriter().write("ERROR: AL DESCARGAR ARCHIVO..!!");
				return;
			    }
			    response.setHeader("Content-Disposition", "attachment; filename=\""+ someFile + "\"");
			    IOUtils.copy(is, response.getOutputStream());
			    response.flushBuffer();

			} catch (Exception ex) {
			    LOG.info("--->" + ex.getMessage());
			    LOG.info("Error writing file to output stream. Filename was '"+ documentoAdjunto.get(0).getNombreArchivo() + "'");
			    throw new RuntimeException("IOError writing file to output stream");
			}
    	}
    
    @RequestMapping(value = "/listaPghDocumentoAdjunto", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> listaPghDocumentoAdjunto(DocumentoAdjuntoFilter filtro, HttpSession session) {
    	LOG.info("listaPghDocumentoAdjunto - inicio");
        Map<String,Object> retorno=new HashMap<String,Object>();
        try{
            List<DocumentoAdjuntoDTO> listado=documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(filtro);
            retorno.put("listaDocumentoAdjunto", listado);
			retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
        }catch(Exception ex){
        	retorno.put(ConstantesWeb.VV_MENSAJE,"Error al traer Documento Adjunto");
			retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            LOG.error("Error listaPghDocumentoAdjunto",ex);
        }
        LOG.info("listaPghDocumentoAdjunto - fin");
        return retorno;
    }
    
    @RequestMapping(value="/descargaResultadoGenerado",method= RequestMethod.GET)
    public void descargaResultadoGenerado( Long idPlantillaResultado,Long idSupervision, HttpServletResponse response) throws IOException {
        LOG.info("descargaResultadoGenerado,"+idPlantillaResultado+"-"+idSupervision);
    	try {
            DocumentoAdjuntoDTO documentoAdjunto= plantillaResultadoServiceNeg.generaDocumentoPlantilla(idPlantillaResultado,idSupervision);
            if(documentoAdjunto==null){
                response.getWriter().write("Error al Descargar, Plantilla no existe.");
                return;
            }
        
            byte[] bytes = documentoAdjunto.getArchivoAdjunto();
            File someFile = new File(documentoAdjunto.getNombreArchivo());
            InputStream is = null;
            if (bytes!=null) {
                FileOutputStream fos = new FileOutputStream(someFile);
                fos.write(bytes);
                fos.flush();
                fos.close();
                LOG.info("file file SOMEFILE::>" + someFile);
                is = FileUtils.openInputStream(someFile);	
            }            
            if (is == null) {
                response.getWriter().write("Error al descargar archivo.");
                return;
            }
            response.setHeader("Content-Disposition", "attachment; filename=\""+ someFile + "\"");
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();

        } catch (Exception ex) {
            LOG.error("Error descargaResultadoGenerado",ex);
            //throw new RuntimeException("IOError writing file to output stream"+ex.getMessage());
            response.getWriter().write(ex.getMessage());
            return;
        }
    }
    // htorress - RSIS 13 - Inicio
    @RequestMapping(value="/findVersionesArchivosSIGED",method= RequestMethod.GET)
    public @ResponseBody Map<String,Object> findVersionesArchivosSIGED(String numeroExpediente,String idDocumento, int rows, int page,HttpSession session,HttpServletRequest request){
        LOG.info("findVersionesArchivosSIGED");
    	
        Map<String,Object> retorno=new HashMap<String,Object>();
        try{
            List<DocumentoAdjuntoDTO> listado=archivoServiceNeg.consultaVersionesArchivoSIGED(numeroExpediente, idDocumento);
            if(listado!=null && listado.size()>0){
                for(DocumentoAdjuntoDTO reg : listado){
                    reg.setEstadoOrigen(Constantes.ESTADO_ORIGEN_ARCHIVO_SIGED);
                }
            }
                      
            int indiceInicial = -1;
            int indiceFinal = -1;
            Long contador = (long) listado.size();
            Long numeroFilas = (contador / rows);
            if ((contador % rows) > 0) {numeroFilas = numeroFilas + 1L;}
            if(numeroFilas<page){page = numeroFilas.intValue();}
            if(page == 0){rows = 0;}
            indiceInicial = (page - 1) * rows;
            indiceFinal = indiceInicial + rows;
            List<DocumentoAdjuntoDTO> listadoPaginado = listado.subList(indiceInicial > listado.size() ? listado.size() : indiceInicial, indiceFinal > listado.size() ? listado.size() : indiceFinal );                       
            retorno.put("total", numeroFilas);
            retorno.put("pagina", page);
            retorno.put("registros", contador);
            retorno.put("filas", listadoPaginado);
        }catch(Exception ex){
            LOG.error("",ex);
        }
        return retorno;
    }
    
    @RequestMapping(value = "/consultarVersionesArchivo", method = RequestMethod.GET)
    public String consultarVersionesArchivo(String tipo, Long idArchivo, Long idDocumento, String tipoDocumento, 
    	String nombreArchivo, String estadoOrigen, HttpServletRequest request, HttpServletResponse response, HttpSession session,Model model) {
	
    	LOG.info("consultarVersionesArchivo");
        model.addAttribute("tipo", tipo);
        model.addAttribute("idArchivo", idArchivo);
        model.addAttribute("idDocumento", idDocumento);
        model.addAttribute("tipoDocumento", tipoDocumento);
        model.addAttribute("nombreArchivo", nombreArchivo);
        model.addAttribute("estadoOrigen", estadoOrigen);
        
        return ConstantesWeb.Navegacion.PAGE_INPS_SUPERVISION_GEST_ARCHIVO;
    
	}
    // htorress - RSIS 13 - Fin
    // htorress - RSIS 9 - Inicio
    @RequestMapping(value = "/cargarArchivo", method = RequestMethod.POST)
    public String cargarArchivo(String tipo, Long idArchivo, Long idDocumento, String tipoDocumento, 
    	String nombreArchivo, Long numeroExpediente, HttpServletRequest request, HttpServletResponse response, HttpSession session,Model model) {
	
    	LOG.info("cargarArchivo");
    	String[] numerosComoArray = tipoDocumento.split("-");
    	String tipoDocumentoCarga=numerosComoArray[0];
        model.addAttribute("tipo", tipo);
        model.addAttribute("idArchivo", idArchivo);
        model.addAttribute("idDocumento", idDocumento);
        model.addAttribute("tipoDocumento", tipoDocumentoCarga);
        model.addAttribute("nombreArchivo", nombreArchivo);
        model.addAttribute("numeroExpediente", numeroExpediente);
        
        return ConstantesWeb.Navegacion.PAGE_INPS_SUPERVISION_GEST_ARCHIVO;
    
	}
    
    @RequestMapping(value = "/subirArchivo", method = RequestMethod.POST)
    public void subirArchivo(@RequestParam("archivoCargar") MultipartFile file, String tipo, Long idArchivo, Long idDocumento, 
    	String tipoDocumento, String nombreArchivo, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
        String validaDocumento = "";
        boolean tipoCarga=false;
        DocumentoAdjuntoDTO archivoEnviado= new DocumentoAdjuntoDTO();
        response.setContentType("text/html;charset=utf-8");
        try {
            validaDocumento = validaSubirDocuAdjuntoOS(file,Constantes.CONSTANTE_TIPOS_ARCHIVOS_ORDEN_SERVICIO,null);
            if(!validaDocumento.trim().isEmpty()){
                response.getWriter().write(validaDocumento);
                return;
            }
            
            DocumentoAdjuntoDTO archivo = new DocumentoAdjuntoDTO();
            archivo.setTipoDocumentoCarga(new MaestroColumnaDTO());
            
            archivo.setArchivoAdjunto(file.getBytes());
            archivo.setNombreArchivo(file.getOriginalFilename().toUpperCase());
            archivo.setIdDocumento(idDocumento);
            archivo.getTipoDocumentoCarga().setCodigo(tipoDocumento);
            
            if(tipo.equals(Constantes.GESTION_ARCHIVO_CARGAR_ARCHIVO)){
            	tipoCarga=false;
            }
            
            if(tipo.equals(Constantes.GESTION_ARCHIVO_VERSIONAR_ARCHIVO)){
            	archivo.setIdArchivo(idArchivo);
            	tipoCarga=true;
            	String fileName= (file.getOriginalFilename()).toUpperCase();
            	nombreArchivo=nombreArchivo.toUpperCase();
            	if(!(fileName.equals(nombreArchivo))){
            		response.getWriter().write("{\"error\":true,\"mensaje\":\"Archivo a versionar con nombre diferente al del seleccionado.\"}");
            		return;
            	}
            	
            }
            
            archivoEnviado=archivoServiceNeg.agregarArchivoSiged(archivo, Constantes.getIDPERSONALSIGED(request), tipoCarga);
            if(archivoEnviado.getEstado().equals(Constantes.FLAG_REGISTRADO_SI)){
            	response.getWriter().write("{\"error\":false,\"mensaje\":\""+ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_CARGAR_ARCHIVO+"\"}");
            }else{
            	response.getWriter().write("{\"error\":true,\"mensaje\":\""+archivoEnviado.getComentario()+"\"}");
            }
            
            
            return;
        } catch (Exception e) {
                LOG.error("Error subiendo archivo BL", e);
        try {
                response.getWriter()
                    .write("{\"error\":true,\"mensaje\":\"Error al subir archivo\"}");
        } catch (IOException ex) {
                LOG.debug("error al escribir en response", ex);
                ex.printStackTrace();
        }
            return;
        }
    }
    // htorress - RSIS 9 - Fin
    // htorress - RSIS 15 - Inicio
    @RequestMapping(value = "/enumerarDocumento", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> enumerarDocumento(Long idArchivo, Long idDocumento, Long idTipoDocumento, String tipoDocumento, 
	    	String nombreArchivo, Long numeroExpediente, HttpSession session, HttpServletRequest request) {
		LOG.info("enumerarDocumento - inicio");
		Map<String, Object> retorno = new HashMap<String, Object>();
		try {
			DocumentoAdjuntoDTO documentoAdjuntoDTO = new DocumentoAdjuntoDTO();
			documentoAdjuntoDTO.setIdTipoDocumento(new MaestroColumnaDTO());
			documentoAdjuntoDTO.setIdArchivo(idArchivo);
			documentoAdjuntoDTO.setIdDocumento(idDocumento);
			documentoAdjuntoDTO.getIdTipoDocumento().setCodigo(String.valueOf(idTipoDocumento));
			
			DocumentoAdjuntoDTO bandera=archivoServiceNeg.enumerarDocumentoSiged(String.valueOf(numeroExpediente),documentoAdjuntoDTO.getIdDocumento().intValue(),Constantes.getIDPERSONALSIGED(request).intValue());
			
			if(bandera.getEstado().equals(Constantes.FLAG_ENUMERADO_SI)){
				retorno.put(ConstantesWeb.VV_MENSAJE,
						ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_ENUMERAR);
				retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
			}else{
				retorno.put(ConstantesWeb.VV_MENSAJE, bandera.getComentario());
				retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ADVERTENCIA);
			}
			
		} catch (Exception ex) {
			retorno.put(ConstantesWeb.VV_MENSAJE, "Error al Enumerar Documento Adjunto");
			retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
			LOG.error("Error enumerarDocumento",ex);
		}
		LOG.info("enumerarDocumento - fin");
		return retorno;
	}
    
    @RequestMapping(value = "/firmarEnumerarDocumento", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> firmarEnumerarDocumento(Long idArchivo, Long idDocumento, Long idTipoDocumento, String tipoDocumento, 
	    	String nombreArchivo, Long numeroExpediente, HttpSession session, HttpServletRequest request) {
		LOG.info("firmarEnumerarDocumento - inicio");
		Map<String, Object> retorno = new HashMap<String, Object>();
		try {
			DocumentoAdjuntoDTO documentoAdjuntoDTO = new DocumentoAdjuntoDTO();
			documentoAdjuntoDTO.setIdTipoDocumento(new MaestroColumnaDTO());
			documentoAdjuntoDTO.setIdArchivo(idArchivo);
			documentoAdjuntoDTO.setIdDocumento(idDocumento);
			documentoAdjuntoDTO.getIdTipoDocumento().setCodigo(String.valueOf(idTipoDocumento));
			
			DocumentoAdjuntoDTO bandera1=archivoServiceNeg.cambiarFirmante(String.valueOf(numeroExpediente),documentoAdjuntoDTO.getIdDocumento().intValue(),Constantes.getIDPERSONALSIGED(request).intValue());
			if(bandera1.getEstado().equals(Constantes.FLAG_ENUMERADO_FIRMADO_SI)){
				
				DocumentoAdjuntoDTO bandera2=archivoServiceNeg.firmarEnumerarDocumentoSiged(String.valueOf(numeroExpediente),documentoAdjuntoDTO.getIdDocumento().intValue(),Constantes.getIDPERSONALSIGED(request).intValue());
				if(bandera2.getEstado().equals(Constantes.FLAG_ENUMERADO_FIRMADO_SI)){
					retorno.put(ConstantesWeb.VV_MENSAJE,
							ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_FIRMAR_ENUMERAR);
					retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
				}else{
					retorno.put(ConstantesWeb.VV_MENSAJE,bandera2.getComentario());
					retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
				}
			}else{
				retorno.put(ConstantesWeb.VV_MENSAJE,bandera1.getComentario());
				retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
			}
		} catch (Exception ex) {
			retorno.put(ConstantesWeb.VV_MENSAJE,"Error al Enumerar y Firmar Documento Adjunto");
			retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
			LOG.error("Error firmarEnumerarDocumento",ex);
		}
		LOG.info("firmarEnumerarDocumento - fin");
		return retorno;
	}
    
    @RequestMapping(value = "/anularArchivo", method = RequestMethod.POST)
	public void anularArchivo(Long idArchivo, Long idDocumento, String tipoDocumento, 
	    	String nombreArchivo, Long numeroExpediente, String motivo, HttpSession session, HttpServletResponse response,HttpServletRequest request) {
		LOG.info("anularArchivo - inicio");
		response.setContentType("text/html;charset=utf-8");
		try {
			DocumentoAdjuntoDTO documentoAdjuntoDTO = new DocumentoAdjuntoDTO();
			documentoAdjuntoDTO.setIdArchivo(idArchivo);
			documentoAdjuntoDTO.setIdDocumento(idDocumento);
			
			DocumentoAdjuntoDTO bandera=archivoServiceNeg.anularArchivoSiged(documentoAdjuntoDTO, motivo.toUpperCase(),String.valueOf(numeroExpediente),Constantes.getIDPERSONALSIGED(request));
			
			if(bandera.getEstado().equals(Constantes.FLAG_ANULADO_SI)){
				response.getWriter().write("{\"error\":false,\"mensaje\":\""+ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_ANULAR+"\"}");
			}else{
				response.getWriter().write("{\"error\":true,\"mensaje\":\""+bandera.getComentario()+"\"}");
			}
			LOG.info("anularArchivo - fin");
			return;
        } catch (Exception e) {
                LOG.error("Error anulando archivo BL", e);
        try {
                response.getWriter()
                    .write("{\"error\":true,\"mensaje\":\"Error al anular archivo\"}");
        } catch (IOException ex) {
                LOG.debug("error al escribir en response", ex);
                ex.printStackTrace();
        }
            return;
        }	
		
	}
    // htorress - RSIS 15 - Fin
    /* OSINE_SFS-480 - RSIS 17 - Inicio */
    @RequestMapping(value="/findArchivoExpedienteOS",method= RequestMethod.GET) 
    public @ResponseBody Map<String, Object> findArchivoExpedienteOS(String numeroExpediente,Long idOrdenServicio, HttpSession session,HttpServletRequest request){
        LOG.info("findArchivoExpedienteOS");   
        Map<String, Object> retorno=new HashMap<String, Object>();
        List<DocumentoAdjuntoDTO> listaDocumentos=new ArrayList<DocumentoAdjuntoDTO>();
        try{
        	listaDocumentos=archivoServiceNeg.listarDocumentosSiged(numeroExpediente);
            if(listaDocumentos!=null && listaDocumentos.size()>0){
                for(DocumentoAdjuntoDTO reg : listaDocumentos){
                    reg.setEstadoOrigen(Constantes.ESTADO_ORIGEN_ARCHIVO_SIGED);
                }
            }      
            List<MaestroColumnaDTO> listaMaestroPlazo = maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_ARCHIVO_PLAZO_DESC, Constantes.APLICACION_INPS);
            retorno.put("ArchivoExpediente", listaDocumentos);
            retorno.put("MaestroPlazo", listaMaestroPlazo);
        }catch(Exception ex){
            LOG.error("error findArchivoExpedienteOS", ex);
        }
        return retorno;
    } 
    /* OSINE_SFS-480 - RSIS 17 - Fin */
    /* OSINE_SFS-480 - RSIS 03 - Inicio */
    @RequestMapping(value="/findArchivoExpedienteMensajeriaOS",method= RequestMethod.GET) 
    public @ResponseBody Map<String, Object> findArchivoExpedienteMensajeriaOS(String numeroExpediente,Long idOrdenServicio, HttpSession session,HttpServletRequest request){
        LOG.info("findArchivoExpedienteOS");   
        Map<String, Object> retorno=new HashMap<String, Object>();
        List<DocumentoAdjuntoDTO> listaDocumentos=new ArrayList<DocumentoAdjuntoDTO>();
        List<DocumentoAdjuntoDTO> listaMensajeria=new ArrayList<DocumentoAdjuntoDTO>();
        try{
        	listaDocumentos=archivoServiceNeg.listarDocumentosSiged(numeroExpediente);
            if(listaDocumentos!=null && listaDocumentos.size()>0){
                for(DocumentoAdjuntoDTO reg : listaDocumentos){                	
                	String nombre = reg.getNombreArchivo();
                	String formato = nombre.substring(nombre.lastIndexOf("."));
                	reg.setEstadoOrigen(Constantes.ESTADO_ORIGEN_ARCHIVO_SIGED);
                	if(reg.getIdDocumento()!=null && reg.getNroDocumento()!=null && reg.getEnumerado().toString().equals(Constantes.ENUMERADO_SI_SIGED)){
                		listaMensajeria.add(reg);
                	} else if (reg.getIdArchivo()!=null && Constantes.CONSTANTE_TIPOS_ARCHIVOS_MENSAJERIA.toUpperCase().contains(formato.toUpperCase())){
                		listaMensajeria.add(reg);
                	}	                	                    
                }
            }      
            List<MaestroColumnaDTO> listaMaestroPlazo = maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_ARCHIVO_PLAZO_DESC, Constantes.APLICACION_INPS);
            retorno.put("tiposArhivos", Constantes.CONSTANTE_TIPOS_ARCHIVOS_MENSAJERIA.toUpperCase());
            retorno.put("ArchivoExpediente", listaMensajeria);
            retorno.put("MaestroPlazo", listaMaestroPlazo);
        }catch(Exception ex){
            LOG.error("error findArchivoExpedienteOS", ex);
        }
        return retorno;
    } 
    
    @RequestMapping(value="/validarDocumentoSIGED",method= RequestMethod.GET) 
    public @ResponseBody Map<String, Object> validarDocumentoSIGED(String esTipo, Long iIdDoc, HttpSession session,HttpServletRequest request){
        LOG.info("validarDocumentoSIGED");   
        Map<String, Object> retorno=new HashMap<String, Object>();
        try{        
        	ValidarDocumentoOut validarDocumentoOut=archivoServiceNeg.validarDocumentoSIGED(esTipo, iIdDoc);
            if(validarDocumentoOut!=null && validarDocumentoOut.getValidarDocumentoItemOut()!=null && validarDocumentoOut.getValidarDocumentoItemOut().size()>0){
            	retorno.put("listaTipoEnvio", validarDocumentoOut.getValidarDocumentoItemOut());
            	retorno.put(ConstantesWeb.VV_RESULTADO, validarDocumentoOut.getResultCode());
            	if(!validarDocumentoOut.getResultCode().toString().equals(Constantes.FLAG_VALIDAR_SI))
            		retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_VALIDAR_DOCUMENTO_OS);
            } else {
            	retorno.put(ConstantesWeb.VV_RESULTADO, validarDocumentoOut.getResultCode());
            	retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_VALIDAR_DOCUMENTO_OS);
            }
        }catch(Exception ex){
            LOG.error("error validarDocumentoSIGED", ex);
        }
        return retorno;
    } 
    
    @RequestMapping(value="/listarOficinaRegionalSIGED",method= RequestMethod.GET) 
    public @ResponseBody Map<String, Object> listarOficinaRegionalSIGED(Long IdPersonalSIGED, HttpSession session,HttpServletRequest request){
        LOG.info("listarOficinaRegionalSIGED");   
        Map<String, Object> retorno=new HashMap<String, Object>();
        try{        
        	ObtenerOficinaRegionalOut obtenerOficinaRegionalOut=archivoServiceNeg.listarOficinaRegionalSIGED(IdPersonalSIGED);
            if(obtenerOficinaRegionalOut!=null && obtenerOficinaRegionalOut.getObtenerOficinaRegionalItemOut()!=null &&  obtenerOficinaRegionalOut.getObtenerOficinaRegionalItemOut().size()>0){
            	retorno.put("listaOficinaRegional", obtenerOficinaRegionalOut.getObtenerOficinaRegionalItemOut());
            	retorno.put(ConstantesWeb.VV_RESULTADO, obtenerOficinaRegionalOut.getResultCode());
            	if(!obtenerOficinaRegionalOut.getResultCode().toString().equals(Constantes.FLAG_VALIDAR_SI))
            		retorno.put(ConstantesWeb.VV_MENSAJE, obtenerOficinaRegionalOut.getMessage());
            }         
        }catch(Exception ex){
            LOG.error("error listarOficinaRegionalSIGED", ex);
        }
        return retorno;
    } 
    
    @RequestMapping(value="/listarDestinatarioSIGED",method= RequestMethod.GET) 
    public @ResponseBody Map<String, Object> listarDestinatarioSIGED(String filtro, HttpSession session,HttpServletRequest request){
        LOG.info("listarDestinatarioSIGED");   
        Map<String, Object> retorno=new HashMap<String, Object>();
        try{        
        	ObtenerDestinatariosOut obtenerDestinatariosOut=archivoServiceNeg.listarDestinatarioSIGED(filtro);
            if(obtenerDestinatariosOut!=null && obtenerDestinatariosOut.getObtenerDestinatariosItemOut()!=null && obtenerDestinatariosOut.getObtenerDestinatariosItemOut().size()>0){
            	retorno.put("listaDestinatario", obtenerDestinatariosOut.getObtenerDestinatariosItemOut());
            	retorno.put(ConstantesWeb.VV_RESULTADO, obtenerDestinatariosOut.getResultCode());
            	if(!obtenerDestinatariosOut.getResultCode().toString().equals(Constantes.FLAG_VALIDAR_SI))
            		retorno.put(ConstantesWeb.VV_MENSAJE, obtenerDestinatariosOut.getMessage());
            }       
        }catch(Exception ex){
            LOG.error("error listarDestinatarioSIGED", ex);
        }
        return retorno;
    } 
    
    @RequestMapping(value="/registrarMensajeriaSIGED",method= RequestMethod.GET) 
    public @ResponseBody Map<String, Object> registrarMensajeriaSIGED(EnviarMensajeriaIn paramMensajeria, HttpSession session,HttpServletRequest request){
        LOG.info("registrarMensajeriaSIGED");   
        Map<String, Object> retorno=new HashMap<String, Object>();
        try{  
        	EnviarMensajeriaOut enviarMensajeria=archivoServiceNeg.registrarMensajeriaSIGED(paramMensajeria);
            if(enviarMensajeria!=null){
            	retorno.put("EnviarMensajeriaOut", enviarMensajeria);
            	retorno.put(ConstantesWeb.VV_RESULTADO, enviarMensajeria.getResultCode());
            	LOG.info("getResultCode() " + enviarMensajeria.getResultCode());   
            	if(!enviarMensajeria.getResultCode().toString().equals(Constantes.FLAG_VALIDAR_SI))
            		retorno.put(ConstantesWeb.VV_MENSAJE, enviarMensajeria.getMessage());
            }       
        }catch(Exception ex){
            LOG.error("error registrarMensajeriaSIGED", ex);
        }
        return retorno;
    } 
    
    @RequestMapping(value="/listarDestalleDestinatarioSIGED",method= RequestMethod.GET) 
    public @ResponseBody Map<String, Object> listarDestalleDestinatarioSIGED(Long nroIdentificacion, boolean estadoFlujo, String nroExpediente, HttpSession session,HttpServletRequest request){
        LOG.info("listarDestalleDestinatarioSIGED");   
        Map<String, Object> retorno=new HashMap<String, Object>();
        try{        
        	obtenerClienteDetalleOut clienteDetalleOut=archivoServiceNeg.listarDetalleDestinatarioSIGED(nroIdentificacion, estadoFlujo, nroExpediente);
            if(clienteDetalleOut!=null){
            	retorno.put("clienteDetalle", clienteDetalleOut);
            	retorno.put(ConstantesWeb.VV_RESULTADO, clienteDetalleOut.getResultCode());
            	if(!clienteDetalleOut.getResultCode().toString().equals(Constantes.FLAG_VALIDAR_SI))
            		retorno.put(ConstantesWeb.VV_MENSAJE, clienteDetalleOut.getMessage());
            }        
        }catch(Exception ex){
            LOG.error("error listarDestalleDestinatarioSIGED", ex);
        }
        return retorno;
    }
    
    @RequestMapping(value="/GridDestalleDestinatarioSIGED",method= RequestMethod.GET) 
    public @ResponseBody Map<String, Object> GridDestalleDestinatarioSIGED(Long idDestinatario, Long nroIdentificacion, boolean estadoFlujo, String nroExpediente, int rows, int page, HttpSession session,HttpServletRequest request){
        LOG.info("listarDestalleDestinatarioSIGED");
        Map<String,Object> retorno=new HashMap<String,Object>();
        List<ClienteDetalleDTO> listado=new ArrayList<ClienteDetalleDTO>();
        try{
        	obtenerClienteDetalleOut clienteDetalleOut=archivoServiceNeg.listarDetalleDestinatarioSIGED(nroIdentificacion, estadoFlujo, nroExpediente);
        	if(clienteDetalleOut!=null){
        		ClienteDetalleDTO cliente=new ClienteDetalleDTO();
        		cliente.setIdDestinatario(idDestinatario);
        		cliente.setNroIdentificacion(nroIdentificacion.toString());
        		cliente.setRazonSocial(clienteDetalleOut.getRazonSocial());
        		cliente.setDepartamento(clienteDetalleOut.getDepartamento());
        		cliente.setProvincia(clienteDetalleOut.getProvincia());
        		cliente.setDistrito(clienteDetalleOut.getDistrito());
        		cliente.setDireccion(clienteDetalleOut.getDireccion());
        		cliente.setReferencia(clienteDetalleOut.getReferencia());
        		listado.add(cliente);
        	}        	
            int indiceInicial = -1;
            int indiceFinal = -1;
            Long contador = (long) listado.size();
            Long numeroFilas = (contador / rows);
            if ((contador % rows) > 0) {numeroFilas = numeroFilas + 1L;}
            if(numeroFilas<page){page = numeroFilas.intValue();}
            if(page == 0){rows = 0;}
            indiceInicial = (page - 1) * rows;
            indiceFinal = indiceInicial + rows;
            List<ClienteDetalleDTO> listadoPaginado = listado.subList(indiceInicial > listado.size() ? listado.size() : indiceInicial, indiceFinal > listado.size() ? listado.size() : indiceFinal );                       
            retorno.put("total", numeroFilas);
            retorno.put("pagina", page);
            retorno.put("registros", contador);
            retorno.put("filas", listadoPaginado);
        }catch(Exception ex){
            LOG.error("error listarDestalleDestinatarioSIGED",ex);
        }
        return retorno;
    }
    
    @RequestMapping(value="/findExpedienteSIGED",method= RequestMethod.GET)
    public @ResponseBody Map<String,Object> findExpedienteSIGED(String nroExpediente, HttpSession session,HttpServletRequest request){
        LOG.info("findExpedienteSIGED");    	
        Map<String,Object> retorno=new HashMap<String,Object>();
        try{
        	ExpedienteConsultaOutRO expedienteConsultaOutRO=archivoServiceNeg.findExpedienteSIGED(nroExpediente);
        	if(expedienteConsultaOutRO!=null){
	        	retorno.put("expediente", expedienteConsultaOutRO);
	        	retorno.put(ConstantesWeb.VV_RESULTADO, expedienteConsultaOutRO.getResultCode());
	        	if(!expedienteConsultaOutRO.getResultCode().toString().equals(Constantes.FLAG_VALIDAR_SI))
	        		retorno.put(ConstantesWeb.VV_MENSAJE, expedienteConsultaOutRO.getMessage());   
        	}
        }catch(Exception ex){
            LOG.error("error findExpedienteSIGED",ex);
        }
        return retorno;
    } 
    /* OSINE_SFS-480 - RSIS03 - Fin */
    
    /* OSINE_SFS-480 - RSIS 06 - Inicio */
    @RequestMapping(value="/findArchivoConsultaMensajeria",method= RequestMethod.GET)
    public @ResponseBody Map<String,Object> findArchivoConsultaMensajeria(String idMensajeria,int rows, int page,HttpSession session,HttpServletRequest request){
        LOG.info("findArchivoExpediente");
    	
        Map<String,Object> retorno=new HashMap<String,Object>();
        try{
        	List<ConsultaMensajeriaDocumentosItemOut> nuevoListado=archivoServiceNeg.listarArchivosMensajeria(idMensajeria, Constantes.getIDPERSONALSIGED(request));
        	String nombreArchivo=null;
        	for(ConsultaMensajeriaDocumentosItemOut archivo: nuevoListado){
        		nombreArchivo=archivo.getNombreArchivo();
        		String[] arrayColores = nombreArchivo.split(">");
        		int fin=arrayColores[1].length()-3;
        		System.out.println("PRUEBA : : : "+arrayColores[1].length() +" Y -3 : : "+fin);
        		nombreArchivo= arrayColores[1].substring(0, fin);
        		archivo.setNombreArchivo(nombreArchivo);
        	}
            int indiceInicial = -1;
            int indiceFinal = -1;
            Long contador = (long) nuevoListado.size();
            Long numeroFilas = (contador / rows);
            if ((contador % rows) > 0) {numeroFilas = numeroFilas + 1L;}
            if(numeroFilas<page){page = numeroFilas.intValue();}
            if(page == 0){rows = 0;}
            indiceInicial = (page - 1) * rows;
            indiceFinal = indiceInicial + rows;
            List<ConsultaMensajeriaDocumentosItemOut> listadoPaginado = nuevoListado.subList(indiceInicial > nuevoListado.size() ? nuevoListado.size() : indiceInicial, indiceFinal > nuevoListado.size() ? nuevoListado.size() : indiceFinal );                       
            retorno.put("total", numeroFilas);
            retorno.put("pagina", page);
            retorno.put("registros", contador);
            retorno.put("filas", listadoPaginado);
        }catch(Exception ex){
            LOG.error("",ex);
        }
        return retorno;
    }
    /* OSINE_SFS-480 - RSIS 06 - Fin */
    
    /*/<!--  OSINE791 - RSIS8 - Inicio -->*/
    @RequestMapping(value = "/subirMedioProbatorioDetalleSuperDsr", method = RequestMethod.POST)
    public void subirMedioProbatorioDetalleSuperDsr(@RequestParam("archivos[0]") MultipartFile file,DocumentoAdjuntoDTO documentoAdjuntoDTO,HttpServletRequest request, HttpServletResponse response, HttpSession session) {		
        String validaDocumento = "";
        response.setContentType("text/html;charset=utf-8");
        try {
            validaDocumento = validarArchivo(file);
            if(!validaDocumento.trim().isEmpty()){
                response.getWriter().write(validaDocumento);
                return;
            }

            documentoAdjuntoDTO.setArchivoAdjunto(file.getBytes());
            documentoAdjuntoDTO.setNombreArchivo(file.getOriginalFilename().toUpperCase());
            documentoAdjuntoDTO.setDescripcionDocumento(documentoAdjuntoDTO.getDescripcionDocumento().toUpperCase());
            documentoAdjuntoDTO = documentoAdjuntoServiceNeg.registrarPghDocumentoAdjunto(documentoAdjuntoDTO, Constantes.getUsuarioDTO(request));

            response.getWriter().write("{\"error\":false,\"mensaje\":\""+ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_CREATE+"\"}");
            return;
        } catch (Exception e) {
            LOG.error("Error subiendo archivo BL", e);
            try {
                response.getWriter().write("{\"error\":true,\"mensaje\":\"Error al subir archivo\"}");
            } catch (IOException ex) {
                LOG.debug("error al escribir en response", ex);
            }
            return;
        }
    }
    /* OSINE791 - RSIS17 - Inicio */
    @RequestMapping(value="/descargaResultadoGeneradoDsr",method= RequestMethod.GET)
    public void descargaResultadoGeneradoDsr( Long idDocumentoAdjunto, HttpServletResponse response) throws IOException {
        LOG.info("idDocumentoAdjunto-->"+idDocumentoAdjunto);
        DocumentoAdjuntoFilter filtro = new DocumentoAdjuntoFilter();
        filtro.setIdDocumentoAdjunto(idDocumentoAdjunto);
        List<DocumentoAdjuntoDTO> documentoAdjunto= documentoAdjuntoServiceNeg.buscaPghDocumentoAdjunto(filtro);
        
        byte[] bytes = documentoAdjunto.get(0).getArchivoAdjunto();
        File someFile = new File(documentoAdjunto.get(0).getNombreArchivo());
        InputStream is = null;
            if (bytes!=null) {
                FileOutputStream fos = new FileOutputStream(someFile);
                fos.write(bytes);
                fos.flush();
                fos.close();
                LOG.info("file file FOS::>" + fos);
                LOG.info("file file SOMEFILE::>" + someFile);
                is = FileUtils.openInputStream(someFile);   
            }

            try {
                if (is == null) {
                response.getWriter().write("ERROR: AL DESCARGAR ARCHIVO..!!");
                return;
                }
                response.setHeader("Content-Disposition", "attachment; filename=\""+ someFile + "\"");
                IOUtils.copy(is, response.getOutputStream());
                response.flushBuffer();

            } catch (Exception ex) {
                LOG.info("--->" + ex.getMessage());
                LOG.info("Error writing file to output stream. Filename was '"+ documentoAdjunto.get(0).getNombreArchivo() + "'");
                throw new RuntimeException("IOError writing file to output stream");
            }
        }
    /* OSINE791 - RSIS17 - Fin */
}
/*<!--  OSINE791 - RSIS8 - Fin -->*/