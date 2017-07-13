package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.inpsweb.service.exception.PersonaGeneralException;
import gob.osinergmin.inpsweb.service.exception.SupervisionException;
import gob.osinergmin.mdicommon.domain.dto.PersonaGeneralDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.PersonaGeneralFilter;
import java.util.List;

public interface PersonaGeneralDAO {

	public List<PersonaGeneralDTO> find(PersonaGeneralFilter filtro) throws PersonaGeneralException;
	public PersonaGeneralDTO registrar(PersonaGeneralDTO personaGeneralDTO,UsuarioDTO usuarioDTO) throws SupervisionException;
	public PersonaGeneralDTO actualizar(PersonaGeneralDTO personaGeneralDTO, UsuarioDTO usuarioDTO) throws SupervisionException;
}
