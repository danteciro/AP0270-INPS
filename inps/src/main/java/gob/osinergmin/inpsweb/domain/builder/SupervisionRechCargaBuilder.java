package gob.osinergmin.inpsweb.domain.builder;

 
import gob.osinergmin.inpsweb.domain.NpsSupervisionRechCarga;
import gob.osinergmin.mdicommon.domain.dto.SupervisionRechCargaDTO;

import java.util.ArrayList;
import java.util.List;

public class SupervisionRechCargaBuilder {
	

	
    public static SupervisionRechCargaDTO toSupervisionRechCargaDTO(NpsSupervisionRechCarga registro) {
    	SupervisionRechCargaDTO registroDTO = new SupervisionRechCargaDTO();
        if(registro!=null){
        	
        	registroDTO.setIdSupervisionRechCarga(registro.getIdSupervisionRechCarga());
        	registroDTO.setAnio(registro.getAnio());
        	registroDTO.setEmpCodemp(registro.getEmpCodemp());
        	registroDTO.setEstado(registro.getEstado());
        	registroDTO.setNombreOficioDoc(registro.getNombreOficioDoc());
        	registroDTO.setNumeroExpediente(registro.getNumeroExpediente());
        	registroDTO.setIdOficioDoc(registro.getIdOficioDoc());
      
        }
        return registroDTO;
    }
    
	
    public static NpsSupervisionRechCarga toSupervisionRechCargaDomain(SupervisionRechCargaDTO registro) {
    	NpsSupervisionRechCarga registroDomain = new NpsSupervisionRechCarga();
        if(registro!=null){
    
        	registroDomain.setIdSupervisionRechCarga(registro.getIdSupervisionRechCarga());
        	registroDomain.setAnio(registro.getAnio());
        	registroDomain.setEmpCodemp(registro.getEmpCodemp());
        	registroDomain.setEstado(registro.getEstado());
        	registroDomain.setNombreOficioDoc(registro.getNombreOficioDoc());
        	registroDomain.setNumeroExpediente(registro.getNumeroExpediente());
        	registroDomain.setIdOficioDoc(registro.getIdOficioDoc());
        }
        return registroDomain;
    }
    
    public static List<SupervisionRechCargaDTO> toListSupervisionRechCargaDTO(List<NpsSupervisionRechCarga> lista) {
    	SupervisionRechCargaDTO registroDTO;
        List<SupervisionRechCargaDTO> retorno = new ArrayList<SupervisionRechCargaDTO>();
        if (lista != null) {
            for (NpsSupervisionRechCarga maestro : lista) {
                registroDTO = toSupervisionRechCargaDTO(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }
}
