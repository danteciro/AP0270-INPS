package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghPlantillaResultado;
import gob.osinergmin.mdicommon.domain.dto.PlantillaResultadoDTO;

import java.util.ArrayList;
import java.util.List;

public class PlantillaResultadoBuilder {
	public static List<PlantillaResultadoDTO> toListPlantillaResultadoDto(List<PghPlantillaResultado> lista) {
		PlantillaResultadoDTO plantillaResultadoDTO;
        List<PlantillaResultadoDTO> retorno = new ArrayList<PlantillaResultadoDTO>();
        if (lista != null) {
            for (PghPlantillaResultado maestro : lista) {
                plantillaResultadoDTO = toPlantillaResultadoDto(maestro);
                retorno.add(plantillaResultadoDTO);
            }
        }
        return retorno;
    }
	
	public static PlantillaResultadoDTO toPlantillaResultadoDto(PghPlantillaResultado registro){
		PlantillaResultadoDTO registroDTO = new PlantillaResultadoDTO();
		if(registro!=null){
			registroDTO.setIdPlantillaResultado(registro.getIdPlantillaResultado());
			registroDTO.setDescripcionDocumento(registro.getDescripcionDocumento());
                        registroDTO.setNombreDocumento(registro.getNombreDocumento());
			registroDTO.setFlagResultado(registro.getFlagResultado());
                        registroDTO.setCarpeta(registro.getCarpeta());
                        registroDTO.setIdentificadorPlantilla(registro.getIdentificadorPlantilla());
		}
		return registroDTO;
	}

}
