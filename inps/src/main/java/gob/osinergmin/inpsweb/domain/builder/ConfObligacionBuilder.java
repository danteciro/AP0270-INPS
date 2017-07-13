package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghConfObligacion;
import gob.osinergmin.mdicommon.domain.dto.ConfObligacionDTO;
import gob.osinergmin.mdicommon.domain.dto.ObligacionDTO;

import java.util.ArrayList;
import java.util.List;

public class ConfObligacionBuilder {

	public static List<ConfObligacionDTO> toListConfObligacionDto(List<PghConfObligacion> lista) {
		ConfObligacionDTO registroDTO;
        List<ConfObligacionDTO> retorno = new ArrayList<ConfObligacionDTO>();
        if (lista != null) {
            for (PghConfObligacion maestro : lista) {
                registroDTO = toConfObligacionDto(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }
	
	public static ConfObligacionDTO toConfObligacionDto(PghConfObligacion registro){
		ConfObligacionDTO registroDTO = new ConfObligacionDTO();
		if(registro!=null){
			registroDTO.setIdConfObligacion(registro.getIdConfObligacion());
			registroDTO.setCodTrazabilidad(registro.getCodTrazabilidad());
			registroDTO.setCodAccion(registro.getCodAccion());
			if(registro.getIdObligacion()!=null && registro.getIdObligacion().getIdObligacion()!=null){
				registroDTO.setObligacion(new ObligacionDTO(registro.getIdObligacion().getIdObligacion()));
                            }
			}
		return registroDTO;
	}
}
