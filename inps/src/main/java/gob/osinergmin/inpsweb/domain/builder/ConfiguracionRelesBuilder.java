/**
* Resumen
* Objeto			: ConfiguracionRelesBuilder.java
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

import gob.osinergmin.inpsweb.domain.NpsConfiguracionReles;
import gob.osinergmin.inpsweb.domain.NpsDetaConfReles;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.Utiles;
import gob.osinergmin.mdicommon.domain.dto.ConfiguracionRelesDTO;
import gob.osinergmin.mdicommon.domain.dto.DetaConfRelesDTO;

import java.util.ArrayList;
import java.util.List;

public class ConfiguracionRelesBuilder {
	
	public static List<ConfiguracionRelesDTO> toListConfiguracionRelesDto(List<NpsConfiguracionReles> lista){
		ConfiguracionRelesDTO configuracionRelesDTO;
		List<ConfiguracionRelesDTO> retorno = new ArrayList<ConfiguracionRelesDTO>();
	    if(lista != null && lista.size()>0){
	    	for (NpsConfiguracionReles maestro : lista) {
	    		configuracionRelesDTO = toConfiguracionRelesDto(maestro);
                retorno.add(configuracionRelesDTO);
            }
	    }
	    return retorno;
	}
	
	public static ConfiguracionRelesDTO toConfiguracionRelesDto(NpsConfiguracionReles registro) {
		ConfiguracionRelesDTO registroDTO = new ConfiguracionRelesDTO();
        
		registroDTO.setIdConfiguracionReles(registro.getIdConfiguracionReles());
		
		if(registro.getAnio()!=null){
			registroDTO.setAnio(Utiles.DateToString(registro.getAnio(), Constantes.FORMATO_FECHA_CORTA));
		}
		List<DetaConfRelesDTO> detalle = new ArrayList<DetaConfRelesDTO>();
		
		if(registro.getNpsDetaConfRelesList()!=null ){
			for(NpsDetaConfReles npsDetaConfReles : registro.getNpsDetaConfRelesList()) {
				DetaConfRelesDTO detaConfRelesDTO = new DetaConfRelesDTO();

				detaConfRelesDTO.setIdConfiguracionReles(npsDetaConfReles.getIdConfiguracionReles().getIdConfiguracionReles());
				
				detaConfRelesDTO.setIdDetaConfReles(npsDetaConfReles.getIdDetaConfReles());
				detaConfRelesDTO.setEstado(npsDetaConfReles.getEstado());
				detaConfRelesDTO.setIdEtapa(npsDetaConfReles.getIdEtapa());
				detalle.add(detaConfRelesDTO);
			}
			registroDTO.setDetaConfRelesList(detalle);
		}
		
		registroDTO.setEmpCodemp(registro.getEmpCodemp());
		registroDTO.setIdSubestacion(registro.getIdSubestacion());
		registroDTO.setEstado(registro.getEstado());
		registroDTO.setIdZona(registro.getIdZona());
		
        return registroDTO;
    }
	
	public static NpsConfiguracionReles getNpsConfiguracionReles(ConfiguracionRelesDTO registroDTO) {
		NpsConfiguracionReles registro = new NpsConfiguracionReles();
        
		registro.setIdConfiguracionReles(registroDTO.getIdConfiguracionReles());
		
		if(registroDTO.getAnio()!=null){
			registro.setAnio(Utiles.stringToDate(registroDTO.getAnio(),Constantes.FORMATO_FECHA_CORTA));
		}
		
		registro.setEmpCodemp(registroDTO.getEmpCodemp());
		registro.setIdSubestacion(registroDTO.getIdSubestacion());
		registro.setEstado(registroDTO.getEstado());
		registro.setIdZona(registroDTO.getIdZona());
		
        return registro;
    }

	public static ConfiguracionRelesDTO toRelesote(NpsConfiguracionReles npsConfiguracionReles) {
		ConfiguracionRelesDTO configuracionRelesDTO = null;
		if(npsConfiguracionReles!=null){
			configuracionRelesDTO = new ConfiguracionRelesDTO();
			configuracionRelesDTO.setIdConfiguracionReles(npsConfiguracionReles.getIdConfiguracionReles());
			
			if(npsConfiguracionReles.getIdSubestacion()!= null){
				configuracionRelesDTO.setIdSubestacion(npsConfiguracionReles.getIdSubestacion());
			}
			
		}
		return configuracionRelesDTO;
	}

	public static List<ConfiguracionRelesDTO> toListRelesote(List<NpsConfiguracionReles> lista) {
		List<ConfiguracionRelesDTO> retorno = null;
		ConfiguracionRelesDTO retornoDTO =null;
		if(lista!=null){
			retorno = new ArrayList<ConfiguracionRelesDTO>();
			for(NpsConfiguracionReles maestro:lista){
				retornoDTO = new ConfiguracionRelesDTO();
				retornoDTO = toRelesote(maestro);
			retorno.add(retornoDTO);
			}
			
		}

		return retorno;
	}
		
}
