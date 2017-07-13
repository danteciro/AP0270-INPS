/**
* Resumen
* Objeto		: ArchivoBuilder.java
* Descripción		: Constructor de Archivo
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     15/05/2016      Hernán Torres Saénz         Mostrar campos Nro. Documento y Firmado (Si/No) de archivos
* 
*/

package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.siged.remote.rest.ro.out.ArchivoOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.VersionArchivoOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.list.ListArchivoOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.query.DocumentoConsultaOutRO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author jpiro
 */
public class ArchivoBuilder {
    public static List<DocumentoAdjuntoDTO> toListDocumentoAdjuntoDto(List<DocumentoConsultaOutRO> lista) {
        DocumentoAdjuntoDTO registroDTO;
        List<DocumentoAdjuntoDTO> retorno = new ArrayList<DocumentoAdjuntoDTO>();
        if (lista != null) {
            for (DocumentoConsultaOutRO documento : lista) {
                ListArchivoOutRO archivosList = documento.getArchivos();
                // htorress - RSIS 9 - Inicio
                //if (archivosList != null) {
                if (archivosList.getArchivo() != null) {
                // htorress - RSIS 9 - Fin
                	List<ArchivoOutRO> archivos = archivosList.getArchivo();
                 // htorress - RSIS 9 - Inicio
                    Collections.sort(archivos, new Comparator<ArchivoOutRO>(){
                    	@Override
                    	public int compare(ArchivoOutRO arch1, ArchivoOutRO arch2) {                    		
                    		return arch1.getIdArchivo().compareTo(arch2.getIdArchivo());
                    	}});
                    Collections.reverse(archivos);
                    int maximo= archivos.size()-1;
                    int contador=-1;
                 // htorress - RSIS 9 - Fin
                    for (ArchivoOutRO archivo : archivos) {
                    	
                    	// htorress - RSIS 9 - Inicio
                    	contador++;
                    	if ((archivo.getVersiones() != null) && (archivo.getVersiones().getVersion() != null))
                            for (VersionArchivoOutRO versionArchivoOutRO : archivo.getVersiones().getVersion()) {
                            	registroDTO = toDocumentoAdjuntoDtoVersiones(archivo, versionArchivoOutRO,documento);
                            	retorno.add(registroDTO);
                        } else {
                        // htorress - RSIS 9 - Fin	
                        	registroDTO = toDocumentoAdjuntoDto(archivo,documento);
                        	// htorress - RSIS 9 - Inicio
                        	if(contador == maximo){
                    			registroDTO.getIdTipoDocumento().setDescripcion(registroDTO.getIdTipoDocumento().getDescripcion() + " - Nro. " +documento.getNroDocumento());
                    			registroDTO.getIdTipoDocumento().setCodigo(String.valueOf(documento.getIdTipoDocumento()));
                    			registroDTO.setEnumerado(documento.getEnumerado());
                    			registroDTO.setFirmado(documento.getFirmado());
                        		registroDTO.setAsunto(documento.getAsunto());
                        		/* OSINE_SFS-480 - RSIS 08 - Inicio */
                                        registroDTO.setNroDocumento(documento.getNroDocumento());
                                        /* OSINE_SFS-480 - RSIS 08 - Fin */
                        	}else{
                        		registroDTO.getIdTipoDocumento().setDescripcion("");
                        	}
                        	// htorress - RSIS 9 - Fin
                        	retorno.add(registroDTO);
                        // htorress - RSIS 9 - Inicio
                        }
                    	// htorress - RSIS 9 - Fin
                    }                    
                }
                
                
            }
        }
        return retorno;
    }
    // htorress - RSIS 9 - Inicio
    public static DocumentoAdjuntoDTO toDocumentoAdjuntoDtoVersiones(ArchivoOutRO archivo,VersionArchivoOutRO versionArchivo,DocumentoConsultaOutRO documento) {
        DocumentoAdjuntoDTO registroDTO = new DocumentoAdjuntoDTO();
    
        registroDTO.setIdDocumento(new Long(documento.getIdDocumento()));
        MaestroColumnaDTO tipoDoc = new MaestroColumnaDTO();
        tipoDoc.setIdMaestroColumna(new Long(documento.getIdTipoDocumento()));
        tipoDoc.setDescripcion(documento.getNombreTipoDocumento());
        registroDTO.setIdTipoDocumento(tipoDoc);        
        registroDTO.setIdArchivo(new Long(archivo.getIdArchivo()));
        registroDTO.setNombreArchivo(archivo.getNombre());
        registroDTO.setFechaCarga(archivo.getFechaCreacion());
        registroDTO.setVersion(versionArchivo.getLabel());
        registroDTO.setAutor(versionArchivo.getAutor());
        
        return registroDTO;
    }
    // htorress - RSIS 9 - Fin
    public static DocumentoAdjuntoDTO toDocumentoAdjuntoDto(ArchivoOutRO archivo,DocumentoConsultaOutRO documento) {
        DocumentoAdjuntoDTO registroDTO = new DocumentoAdjuntoDTO();
    
        registroDTO.setIdDocumento(new Long(documento.getIdDocumento()));
        MaestroColumnaDTO tipoDoc = new MaestroColumnaDTO();
        tipoDoc.setIdMaestroColumna(new Long(documento.getIdTipoDocumento()));
        tipoDoc.setDescripcion(documento.getNombreTipoDocumento());
        registroDTO.setIdTipoDocumento(tipoDoc);
        
        // htorress - RSIS 9 - Inicio
        registroDTO.setAsunto(documento.getAsunto());
        // htorress - RSIS 9 - Fin
        registroDTO.setIdArchivo(new Long(archivo.getIdArchivo()));
        registroDTO.setNombreArchivo(archivo.getNombre());
        registroDTO.setFechaCarga(archivo.getFechaCreacion());

        
        return registroDTO;
    }

}
