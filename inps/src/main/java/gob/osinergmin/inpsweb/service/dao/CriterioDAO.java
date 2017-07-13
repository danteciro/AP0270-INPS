package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.inpsweb.service.exception.CriterioException;
import gob.osinergmin.mdicommon.domain.dto.CriterioDTO;

import java.util.List;

public interface CriterioDAO {

	public List<CriterioDTO> listarCriterio(Long idObligacion,Long idTipificacion,Long idCriterio) throws CriterioException;
}
