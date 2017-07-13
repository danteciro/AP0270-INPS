/**
* Resumen
* Objeto			: DetaConfRelesBuilder.java
* Descripción		: Objeto que transmite datos del domain al DTO y viceversa.
* PR de Creación	: OSINE_SFS-1063.
* Fecha de Creación	: 06/10/2016.
* Autor				: Hernan Torres Saenz.
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* 
*/

package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.dse_common.domain.dto.EtapaDTO;
import gob.osinergmin.inpsweb.domain.NpsConfiguracionReles;
import gob.osinergmin.inpsweb.domain.NpsDetaConfReles;
import gob.osinergmin.mdicommon.domain.dto.DetaConfRelesDTO;

import java.util.ArrayList;
import java.util.List;

public class DetaConfRelesBuilder {
	
	public static List<DetaConfRelesDTO> toListDetaConfRelesDto(List<NpsDetaConfReles> lista){
		DetaConfRelesDTO detaConfRelesDTO = null;
		List<DetaConfRelesDTO> retorno = null;
	    if(lista != null && lista.size()>0){
	    	retorno = new ArrayList<DetaConfRelesDTO>();
	    	detaConfRelesDTO = new DetaConfRelesDTO();
	    	for (NpsDetaConfReles maestro : lista) {
	    		detaConfRelesDTO = toDetaConfRelesDto(maestro);
                retorno.add(detaConfRelesDTO);
            }
	    }
	    return retorno;
	}
	
	public static DetaConfRelesDTO toDetaConfRelesDto(NpsDetaConfReles registro) {
		DetaConfRelesDTO registroDTO = null;
        if(registro!=null){
        	registroDTO = new DetaConfRelesDTO();
        	registroDTO.setIdDetaConfReles(registro.getIdDetaConfReles());
//    		if(registro.getIdConfiguracionReles()!=null && 	registro.getIdConfiguracionReles().getIdConfiguracionReles()!=null){
//    			registroDTO.setIdConfiguracionReles(registro.getIdConfiguracionReles().getIdConfiguracionReles());
//    		}		
    		registroDTO.setIdEtapa(registro.getIdEtapa());
    		registroDTO.setEstado(registro.getEstado());
        }		
		
        return registroDTO;
    }
	
	public static NpsDetaConfReles getNpsDetaConfReles(DetaConfRelesDTO registroDTO) {
		NpsDetaConfReles registro = new NpsDetaConfReles();
        
		registro.setIdDetaConfReles(registroDTO.getIdDetaConfReles());
		
		if(registroDTO.getIdConfiguracionReles()!=null){
			NpsConfiguracionReles configuracion= new NpsConfiguracionReles();
			configuracion.setIdConfiguracionReles(registroDTO.getIdConfiguracionReles());
			registro.setIdConfiguracionReles(configuracion);
		}
		registro.setIdEtapa(registroDTO.getIdEtapa());
		registro.setEstado(registroDTO.getEstado());
		
        return registro;
    }

	public static List<EtapaDTO> toEtapaDto(List<DetaConfRelesDTO> detalleConfiguracion) {
		 List<EtapaDTO> retorno=null;
		if(detalleConfiguracion!=null){
			retorno = new ArrayList<EtapaDTO>();
			for(DetaConfRelesDTO deta:detalleConfiguracion){
				EtapaDTO etapa = new EtapaDTO();
				etapa.setIdEtapa(deta.getIdEtapa().intValue());
				retorno.add(etapa);
			}
		}
		
		return retorno;
	}
	
}
