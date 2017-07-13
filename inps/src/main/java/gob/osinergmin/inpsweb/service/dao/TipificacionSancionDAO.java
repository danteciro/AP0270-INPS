package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.inpsweb.service.exception.TipificacionSancionException;
import gob.osinergmin.mdicommon.domain.dto.TipificacionSancionDTO;
import gob.osinergmin.mdicommon.domain.ui.TipificacionSancionFilter;

import java.util.List;

public interface TipificacionSancionDAO {
	public List<TipificacionSancionDTO> find(TipificacionSancionFilter filtro) throws TipificacionSancionException;
}
