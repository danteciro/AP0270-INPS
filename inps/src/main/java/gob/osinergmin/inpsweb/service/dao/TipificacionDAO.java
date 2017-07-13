package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.mdicommon.domain.dto.TipificacionDTO;
import gob.osinergmin.mdicommon.domain.ui.TipificacionFilter;

import java.util.List;

public interface TipificacionDAO {
	public List<TipificacionDTO> listarTipificacion (TipificacionFilter filtro);
	
}
