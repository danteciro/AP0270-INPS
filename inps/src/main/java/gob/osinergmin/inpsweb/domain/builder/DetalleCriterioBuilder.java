package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghDetalleCriterio;
import gob.osinergmin.mdicommon.domain.dto.DetalleCriterioDTO;
import java.util.ArrayList;
import java.util.List;

public class DetalleCriterioBuilder {
	public static DetalleCriterioDTO toDetalleCriterioDTO(PghDetalleCriterio registro) {
		DetalleCriterioDTO registroDTO = new DetalleCriterioDTO();
		if(registro!=null){
			registroDTO.setIdDetalleCriterio(registro.getIdDetalleCriterio());
			registroDTO.setSancionEspecifica(registro.getSancionEspecifica());
		}
		return registroDTO;
	}
	
	public static List<DetalleCriterioDTO> toListDetalleCriterioDto(List<PghDetalleCriterio> lista) {
		DetalleCriterioDTO registroDTO;
        List<DetalleCriterioDTO> retorno = new ArrayList<DetalleCriterioDTO>();
        if (lista != null) {
            for (PghDetalleCriterio maestro : lista) {
                registroDTO = toDetalleCriterioDTO(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }

}
