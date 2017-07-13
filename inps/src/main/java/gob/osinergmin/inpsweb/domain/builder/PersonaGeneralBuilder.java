package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghPersonaGeneral;
import gob.osinergmin.mdicommon.domain.dto.PersonaGeneralDTO;
import java.util.ArrayList;
import java.util.List;

public class PersonaGeneralBuilder {

	public static List<PersonaGeneralDTO> toListPersonaGeneralDTO(List<PghPersonaGeneral> lista) {
		PersonaGeneralDTO registroDTO;
        List<PersonaGeneralDTO> retorno = new ArrayList<PersonaGeneralDTO>();
        if (lista != null) {
            for (PghPersonaGeneral maestro : lista) {
                registroDTO = toPersonaGeneralDTO(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    } 
	
	public static PersonaGeneralDTO toPersonaGeneralDTO(PghPersonaGeneral registro) {
		PersonaGeneralDTO registroDTO = new PersonaGeneralDTO();
		if(registro!=null){
			registroDTO.setIdPersonaGeneral(registro.getIdPersonaGeneral());
			registroDTO.setNombresPersona(registro.getNombresPersona());
			registroDTO.setApellidoPaternoPersona(registro.getApellidoPaternoPersona());
			registroDTO.setApellidoMaternoPersona(registro.getApellidoMaternoPersona());
			registroDTO.setIdTipoDocumento(registro.getIdTipoDocumento());
			registroDTO.setNumeroDocumento(registro.getNumeroDocumento());
			registroDTO.setFlagUltimo(registro.getFlagUltimo());
		}
		return registroDTO;
	}
	
	public static PghPersonaGeneral toPersonaGeneralDomain(PersonaGeneralDTO registroDTO) {
		PghPersonaGeneral registro = new PghPersonaGeneral();
		if(registroDTO!=null){
			if(registroDTO.getIdPersonaGeneral()!=null){
				registro.setIdPersonaGeneral(registroDTO.getIdPersonaGeneral());
			}
			registro.setNombresPersona(registroDTO.getNombresPersona());
			registro.setApellidoPaternoPersona(registroDTO.getApellidoPaternoPersona());
			registro.setApellidoMaternoPersona(registroDTO.getApellidoMaternoPersona());
			registro.setIdTipoDocumento(registroDTO.getIdTipoDocumento());
			registro.setNumeroDocumento(registroDTO.getNumeroDocumento());
			registro.setFlagUltimo(registroDTO.getFlagUltimo());
			registro.setEstado(registroDTO.getEstado());
		}
		return registro;
	}
}
