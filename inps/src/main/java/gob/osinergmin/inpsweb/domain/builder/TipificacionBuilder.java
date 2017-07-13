package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghTipificacion;
import gob.osinergmin.mdicommon.domain.dto.TipificacionDTO;
import java.util.ArrayList;
import java.util.List;

public class TipificacionBuilder {

	public static TipificacionDTO toTipificacionDTO(PghTipificacion registro) {
		TipificacionDTO registroDTO = new TipificacionDTO();
		if(registro!=null){ 
			registroDTO.setIdTipificacion(registro.getIdTipificacion());
			registroDTO.setDescripcion(registro.getDescripcion());
			registroDTO.setIdTipoMoneda(registro.getIdTipoMoneda());
			registroDTO.setSancionMonetaria(registro.getSancionMonetaria());
			registroDTO.setCodTipificacion(registro.getCodTipificacion());
		}
		return registroDTO;
	}
	
	public static List<TipificacionDTO> toListTipificacionDto(List<PghTipificacion> lista) {
		TipificacionDTO registroDTO;
        List<TipificacionDTO> retorno = new ArrayList<TipificacionDTO>();
        if (lista != null) {
            for (PghTipificacion maestro : lista) {
                registroDTO = toTipificacionDTO(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    } 
}
