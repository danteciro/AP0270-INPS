/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.controller;

import gob.osinergmin.inpsweb.service.business.DocumentoAdjuntoServiceNeg;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import java.io.InputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author jpiro
 */
@Controller
@RequestMapping("/documentoAdjunto")
public class DocumentoAdjuntoController {
    private static final Logger LOG = LoggerFactory.getLogger(DocumentoAdjuntoController.class);
    @Autowired
    private DocumentoAdjuntoServiceNeg documentoAdjuntoServiceNeg;
    
    @RequestMapping(value="/descargaArchivoAlfresco",method= RequestMethod.GET)
    //public @ResponseBody Map<String, Object> descargaArchivoAlfresco(DocumentoAdjuntoDTO filtro){
    public void descargaArchivoAlfresco( DocumentoAdjuntoDTO filtro, HttpServletResponse response) {
        LOG.info("procesando descargaArchivoAlfresco--->"+filtro.getNombreArchivo());        
        InputStream is =documentoAdjuntoServiceNeg.descargarDatosAlfresco(filtro);
        
        try {        	
    		 if(is==null){
    	        response.getWriter()
    			.write("Error al insertar Documento");
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
}
