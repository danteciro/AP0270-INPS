/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.service.business.DatoPlantillaServiceNeg;
import gob.osinergmin.inpsweb.service.business.PlantillaResultadoServiceNeg;
import gob.osinergmin.inpsweb.service.dao.PlantillaResultadoDAO;
import gob.osinergmin.inpsweb.service.exception.PlantillaResultadoException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.Utiles;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.PlantillaResultadoDTO;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.inject.Inject;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author jpiro
 */
@Service("PlantillaResultadoServiceNeg")
public class PlantillaResultadoServiceNegImpl implements PlantillaResultadoServiceNeg {
    private static final Logger LOG = LoggerFactory.getLogger(PlantillaResultadoServiceNegImpl.class);
    
    @Inject
    private PlantillaResultadoDAO plantillaResultadoDAO;
    @Inject
    private DatoPlantillaServiceNeg datoPlantillaServiceNeg;
    @Value("${ruta.plantillas}")
    private String RUTA_PLANTILLAS;
    
    @Override
    public DocumentoAdjuntoDTO generaDocumentoPlantilla(Long idPlantillaResultado, Long idSupervision) throws PlantillaResultadoException{
        DocumentoAdjuntoDTO retorno=null;
        try{
            PlantillaResultadoDTO plantilla=plantillaResultadoDAO.getPlantillaResultado(idPlantillaResultado);
            LOG.info("nombrePlantilla:"+plantilla.getNombreDocumento());
            LOG.info("identificadorPlantilla:"+plantilla.getIdentificadorPlantilla());
            //obteniendo plantilla con POI
            XWPFDocument document = armarPlantilla(plantilla,idSupervision);
            //Generando Byte[] para salida desde XWPFDocument
            ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
            document.write(outByteStream);
            byte[] outArray = outByteStream.toByteArray();
            //seteando retorno
            retorno=new DocumentoAdjuntoDTO();
            retorno.setArchivoAdjunto(outArray);
            retorno.setNombreArchivo(plantilla.getNombreDocumento().substring(0, plantilla.getNombreDocumento().lastIndexOf("."))+new SimpleDateFormat("_yyyyMMdd_hhmmss").format(new Date())+plantilla.getNombreDocumento().substring(plantilla.getNombreDocumento().lastIndexOf(".")));                
        }catch(Exception e){
            LOG.error("Error en generaDocumentoPlantilla",e);
            throw new PlantillaResultadoException(e.getMessage(),e);
        }           
        return retorno;
    }

    private XWPFDocument armarPlantilla(PlantillaResultadoDTO plantilla,Long idSupervision) throws PlantillaResultadoException{
        LOG.info("armarPlantilla");
        XWPFDocument document=null;
        try {    
            String direccion=RUTA_PLANTILLAS+plantilla.getCarpeta()+plantilla.getNombreDocumento();
            FileInputStream fis = new FileInputStream(direccion);
            document = new XWPFDocument(OPCPackage.open(fis));
            fis.close();
            
            Map<String, Map<String, Object>> datos=null;
            if(plantilla.getIdentificadorPlantilla().equals(Constantes.IDENT_PLANTILLA_UNO)){
                datos = datoPlantillaServiceNeg.obtenerDatosPlantillaUno(idSupervision);                
            }else if(plantilla.getIdentificadorPlantilla().equals(Constantes.IDENT_PLANTILLA_DOS)){
            	datos = datoPlantillaServiceNeg.obtenerDatosPlantillaDos(idSupervision);
            }else if(plantilla.getIdentificadorPlantilla().equals(Constantes.IDENT_PLANTILLA_TRES)){
            	datos = datoPlantillaServiceNeg.obtenerDatosPlantillaTres(idSupervision);
            }else if(plantilla.getIdentificadorPlantilla().equals(Constantes.IDENT_PLANTILLA_CUATRO)){
            	datos = datoPlantillaServiceNeg.obtenerDatosPlantillaCuatro(idSupervision);
            }else if(plantilla.getIdentificadorPlantilla().equals(Constantes.IDENT_PLANTILLA_CINCO)){
            	datos = datoPlantillaServiceNeg.obtenerDatosPlantillaCinco(idSupervision);
            }else if(plantilla.getIdentificadorPlantilla().equals(Constantes.IDENT_PLANTILLA_SEIS)){
            	datos = datoPlantillaServiceNeg.obtenerDatosPlantillaSeis(idSupervision);
            }else if(plantilla.getIdentificadorPlantilla().equals(Constantes.IDENT_PLANTILLA_SIETE)){
            	datos = datoPlantillaServiceNeg.obtenerDatosPlantillaSiete(idSupervision);
            }else if(plantilla.getIdentificadorPlantilla().equals(Constantes.IDENT_PLANTILLA_OCHO) || //mdiosesf - RSIS5 
            		plantilla.getIdentificadorPlantilla().equals(Constantes.IDENT_PLANTILLA_NUEVE) ||
            		plantilla.getIdentificadorPlantilla().equals(Constantes.IDENT_PLANTILLA_DIEZ) ||
            		plantilla.getIdentificadorPlantilla().equals(Constantes.IDENT_PLANTILLA_ONCE) ||
            		plantilla.getIdentificadorPlantilla().equals(Constantes.IDENT_PLANTILLA_DOCE) ||
            		plantilla.getIdentificadorPlantilla().equals(Constantes.IDENT_PLANTILLA_TRECE) ||
            		plantilla.getIdentificadorPlantilla().equals(Constantes.IDENT_PLANTILLA_CATORCE)){
            	datos = datoPlantillaServiceNeg.obtenerDatosPlantillaNoSupervisada(idSupervision);            
            }else{          
            	throw new PlantillaResultadoException("Plantilla no existe",null);
            }
            
            document=Utiles.Poi.actualizaPlantilla(document,datos);
        } catch (Exception ex) {
            LOG.error("armaPlantilla",ex);
            throw new PlantillaResultadoException(ex.getMessage(),ex);
        }
        return document;
    }

}