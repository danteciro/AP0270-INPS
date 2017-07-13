package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.inpsweb.service.exception.ConfObligacionException;
import gob.osinergmin.mdicommon.domain.dto.ConfObligacionDTO;
import gob.osinergmin.mdicommon.domain.ui.ConfObligacionFilter;

import java.util.List;

public interface ConfObligacionDAO {

	public List<ConfObligacionDTO> listarConfObligacion(ConfObligacionFilter filtro) throws ConfObligacionException;
}
