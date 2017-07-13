package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.inpsweb.service.exception.DetalleCriterioException;
import gob.osinergmin.mdicommon.domain.dto.DetalleCriterioDTO;

import java.util.List;

public interface DetalleCriterioDAO {
	public List<DetalleCriterioDTO> listarDetalleCriterio(Long idCriterio,Long idDetalleCriterio)throws DetalleCriterioException;

}
