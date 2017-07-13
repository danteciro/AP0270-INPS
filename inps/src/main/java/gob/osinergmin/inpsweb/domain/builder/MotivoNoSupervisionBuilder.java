package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghMotivoNoSupervision;
import gob.osinergmin.mdicommon.domain.dto.MotivoNoSupervisionDTO;
import java.util.ArrayList;
import java.util.List;

public class MotivoNoSupervisionBuilder {

	public static List<MotivoNoSupervisionDTO> toListMotivoNoSupervisionDto(List<PghMotivoNoSupervision> lista) {
		MotivoNoSupervisionDTO registroDTO;
        List<MotivoNoSupervisionDTO> retorno = new ArrayList<MotivoNoSupervisionDTO>();
        if (lista != null) {
            for (PghMotivoNoSupervision maestro : lista) {
                registroDTO = toMotivoNoSupervisionDTO(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    } 
	
	public static MotivoNoSupervisionDTO toMotivoNoSupervisionDTO(PghMotivoNoSupervision registro) {
		MotivoNoSupervisionDTO registroDTO = new MotivoNoSupervisionDTO();
		if(registro!=null){
			registroDTO.setIdMotivoNoSupervision(registro.getIdMotivoNoSupervision());
			registroDTO.setDescripcion(registro.getDescripcion());
			registroDTO.setFlagEspecificar(registro.getFlagEspecificar());
		}
		return registroDTO;
	}
}
