package gob.osinergmin.inpsweb.service.business;

import gob.osinergmin.mdicommon.domain.dto.PersonaGeneralDTO;
import gob.osinergmin.mdicommon.domain.ui.PersonaGeneralFilter;

import java.util.List;

public interface PersonaGeneralServiceNeg {
	
	public List<PersonaGeneralDTO> find(PersonaGeneralFilter filtro);

}
