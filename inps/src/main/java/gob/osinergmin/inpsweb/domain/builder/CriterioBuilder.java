package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghCriterio;
import gob.osinergmin.mdicommon.domain.dto.CriterioDTO;
import java.util.ArrayList;
import java.util.List;

public class CriterioBuilder {

	public static CriterioDTO toCriterioDTO(PghCriterio registro) {
		CriterioDTO registroDTO = new CriterioDTO();
		if(registro!=null){
			registroDTO.setIdCriterio(registro.getIdCriterio());
			registroDTO.setDescripcion(registro.getDescripcion());
			registroDTO.setSancionMonetaria(registro.getSancionMonetaria());
		}
		return registroDTO;
	}
	
	public static List<CriterioDTO> toListCriterioDto(List<PghCriterio> lista) {
		CriterioDTO registroDTO;
        List<CriterioDTO> retorno = new ArrayList<CriterioDTO>();
        if (lista != null) {
            for (PghCriterio maestro : lista) {
                registroDTO = toCriterioDTO(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }
}
