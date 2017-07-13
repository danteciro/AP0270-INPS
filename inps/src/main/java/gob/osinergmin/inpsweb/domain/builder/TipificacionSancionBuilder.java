package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghTipificacionSancion;
import gob.osinergmin.mdicommon.domain.dto.TipificacionSancionDTO;
import gob.osinergmin.mdicommon.domain.dto.TipoSancionDTO;

import java.util.ArrayList;
import java.util.List;

public class TipificacionSancionBuilder {

	public static List<TipificacionSancionDTO> toListTipificacionSancionDto(List<PghTipificacionSancion> lista) {
		TipificacionSancionDTO registroDTO;
        List<TipificacionSancionDTO> retorno = new ArrayList<TipificacionSancionDTO>();
        if (lista != null) {
            for (PghTipificacionSancion maestro : lista) {
                registroDTO = toTipificacionSancionDTO(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    } 
	
	public static TipificacionSancionDTO toTipificacionSancionDTO(PghTipificacionSancion registro) {
		TipificacionSancionDTO registroDTO = new TipificacionSancionDTO();
		if(registro!=null){
			registroDTO.setIdTipiSanc(registro.getIdTipiSanc());
			if(registro.getIdTipoSancion()!=null && registro.getIdTipoSancion().getIdTipoSancion()!=null){
				TipoSancionDTO tipoSancionDTO = new TipoSancionDTO();
				tipoSancionDTO.setIdTipoSancion(registro.getIdTipoSancion().getIdTipoSancion());
				tipoSancionDTO.setDescripcion(registro.getIdTipoSancion().getDescripcion());
				tipoSancionDTO.setAbreviatura(registro.getIdTipoSancion().getAbreviatura());
				registroDTO.setTipoSancion(tipoSancionDTO);
			}
		}
		return registroDTO;
	}
}
