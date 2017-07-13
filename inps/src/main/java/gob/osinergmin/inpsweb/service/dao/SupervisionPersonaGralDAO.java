package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.inpsweb.service.exception.SupervisionException;
import gob.osinergmin.inpsweb.service.exception.SupervisionPersonaGralException;
import gob.osinergmin.mdicommon.domain.dto.SupervisionPersonaGralDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.SupervisionPersonaGralFilter;

import java.util.List;

public interface SupervisionPersonaGralDAO {

	public List<SupervisionPersonaGralDTO> find(SupervisionPersonaGralFilter filtro) throws SupervisionPersonaGralException;
	public SupervisionPersonaGralDTO registrar(SupervisionPersonaGralDTO supervisionPersonaGralDTO,UsuarioDTO usuarioDTO) throws SupervisionException;
	public SupervisionPersonaGralDTO actualizar(SupervisionPersonaGralDTO supervisionPersonaGralDTO,UsuarioDTO usuarioDTO) throws SupervisionException;
	public void eliminar(SupervisionPersonaGralDTO supervisionPersonaGralDTO)throws SupervisionException;
}
