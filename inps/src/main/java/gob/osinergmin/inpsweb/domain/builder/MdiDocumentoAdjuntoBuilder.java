package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.MdiDocumentoAdjunto;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MdiDocumentoAdjuntoBuilder {
	
	public static MdiDocumentoAdjunto getDocumentoAdjunto(DocumentoAdjuntoDTO registroDTO) throws UnsupportedEncodingException {
        MdiDocumentoAdjunto registro = null;
        if(registroDTO!=null){
            registro = new MdiDocumentoAdjunto();
            
            registro.setIdDocumentoAdjunto(registroDTO.getIdDocumentoAdjunto());
            registro.setNombreArchivo(registroDTO.getNombreArchivo());
            registro.setRutaAlfresco(registroDTO.getRutaAlfresco());
            registro.setTitulo(registroDTO.getTitulo());
            registro.setComentario(registroDTO.getComentario());
            registro.setFechaDocumento(registroDTO.getFechaDocumento());
            registro.setFechaCarga(registroDTO.getFechaCarga());
            registro.setFechaCaptura(registroDTO.getFechaCaptura());
        }
                
        return registro;
    }
    
    public static List<DocumentoAdjuntoDTO> toListDocumentoAdjuntoDto(List<MdiDocumentoAdjunto> lista) {
       DocumentoAdjuntoDTO registroDTO;
        List<DocumentoAdjuntoDTO> listaDTO = new ArrayList<DocumentoAdjuntoDTO>();
        if (lista != null) {
            for (MdiDocumentoAdjunto maestro : lista) {
                registroDTO = toDocumentoAdjuntoDto(maestro);
                listaDTO.add(registroDTO);
            }
        }
        return listaDTO;
    } 
    

    
    public static DocumentoAdjuntoDTO toDocumentoAdjuntoDto(MdiDocumentoAdjunto registro) {
        DocumentoAdjuntoDTO registroDTO = new DocumentoAdjuntoDTO();
        
        registroDTO.setIdDocumentoAdjunto(registro.getIdDocumentoAdjunto());
        registroDTO.setNombreArchivo(registro.getNombreArchivo());
        registroDTO.setRutaAlfresco(registro.getRutaAlfresco());
        registroDTO.setTitulo(registro.getTitulo());
        registroDTO.setComentario(registro.getComentario());
        registroDTO.setFechaDocumento(registro.getFechaDocumento());
        registroDTO.setFechaCarga(registro.getFechaCarga());
        registroDTO.setFechaCaptura(registro.getFechaCaptura());               
        return  registroDTO;
    }

}
