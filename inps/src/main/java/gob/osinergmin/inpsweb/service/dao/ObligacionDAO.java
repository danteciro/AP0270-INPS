package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.inpsweb.service.exception.ObligacionException;
import gob.osinergmin.mdicommon.domain.dto.ObligacionDTO;

public interface ObligacionDAO {
	public ObligacionDTO buscarObligacion(ObligacionDTO obligacionDTO) throws ObligacionException;
}
