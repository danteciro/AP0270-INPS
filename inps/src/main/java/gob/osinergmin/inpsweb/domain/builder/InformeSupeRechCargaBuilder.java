package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.NpsInformeSupeRechCarga;
import gob.osinergmin.inpsweb.domain.NpsSupervisionRechCarga;
import gob.osinergmin.mdicommon.domain.dto.InformeSupeRechCargaDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InformeSupeRechCargaBuilder {
	private static final Logger LOG = LoggerFactory.getLogger(HistoricoEstadoBuilder.class);  
    
	public static InformeSupeRechCargaDTO toInformeSupeRechCargaDTO(NpsInformeSupeRechCarga npsInformeSupeRechCarga){
		InformeSupeRechCargaDTO informeSupeRechCargaDTO = new InformeSupeRechCargaDTO();
		if(npsInformeSupeRechCarga!=null){
			if(npsInformeSupeRechCarga.getFlgObservado()!=null){
				informeSupeRechCargaDTO.setFlgObservado(npsInformeSupeRechCarga.getFlgObservado());
			}
			if(npsInformeSupeRechCarga.getIdInformeDoc()!=null){
				informeSupeRechCargaDTO.setIdInformeDoc(npsInformeSupeRechCarga.getIdInformeDoc());
			}
			if(npsInformeSupeRechCarga.getIdInformeSupeRechCarga()!=null){
				informeSupeRechCargaDTO.setIdInformeSupeRechCarga(npsInformeSupeRechCarga.getIdInformeSupeRechCarga());
			}
			if(npsInformeSupeRechCarga.getIdSupervisionRechCarga()!=null){
				informeSupeRechCargaDTO.setIdInformeSupeRechCarga(npsInformeSupeRechCarga.getIdSupervisionRechCarga().getIdSupervisionRechCarga());
			}
			if(npsInformeSupeRechCarga.getNombreInformeDoc()!=null){
				informeSupeRechCargaDTO.setNombreInformeDoc(npsInformeSupeRechCarga.getNombreInformeDoc());
			}
			if(npsInformeSupeRechCarga.getObservacion()!=null){
				informeSupeRechCargaDTO.setObservacion(npsInformeSupeRechCarga.getObservacion());
			}
		}
		return informeSupeRechCargaDTO;
	}
	
	public static NpsInformeSupeRechCarga toNpsInformeSupeRechCarga(InformeSupeRechCargaDTO informeSupeRechCargaDTO){
		NpsInformeSupeRechCarga npsInformeSupeRechCarga = new NpsInformeSupeRechCarga();
		if( informeSupeRechCargaDTO!=null){
			if(informeSupeRechCargaDTO.getFlgObservado()!=null){
				npsInformeSupeRechCarga.setFlgObservado(informeSupeRechCargaDTO.getFlgObservado());
			}
			if(informeSupeRechCargaDTO.getIdInformeDoc()!=null){
				npsInformeSupeRechCarga.setIdInformeDoc(informeSupeRechCargaDTO.getIdInformeDoc());
			}
			if(informeSupeRechCargaDTO.getIdInformeSupeRechCarga()!=null){
				npsInformeSupeRechCarga.setIdInformeSupeRechCarga(informeSupeRechCargaDTO.getIdInformeSupeRechCarga());
			}
			if(informeSupeRechCargaDTO.getIdSupervisionRechCarga()!=null){
				NpsSupervisionRechCarga npsSupervisionRechCarga = new NpsSupervisionRechCarga();
				npsSupervisionRechCarga.setIdSupervisionRechCarga(informeSupeRechCargaDTO.getIdSupervisionRechCarga().getIdSupervisionRechCarga());
				npsInformeSupeRechCarga.setIdSupervisionRechCarga(npsSupervisionRechCarga);
			}
			if(informeSupeRechCargaDTO.getNombreInformeDoc()!=null){
				npsInformeSupeRechCarga.setNombreInformeDoc(informeSupeRechCargaDTO.getNombreInformeDoc());
			}
			if(informeSupeRechCargaDTO.getObservacion()!=null){
				npsInformeSupeRechCarga.setObservacion(informeSupeRechCargaDTO.getObservacion());
			}
		}
		return npsInformeSupeRechCarga;
	}
	
}
