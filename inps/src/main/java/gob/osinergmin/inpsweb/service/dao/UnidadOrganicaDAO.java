package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.inpsweb.service.exception.UnidadOrganicaException;
import gob.osinergmin.mdicommon.domain.dto.UnidadOrganicaDTO;
import gob.osinergmin.mdicommon.domain.ui.UnidadOrganicaFilter;

import java.util.List;

/**
*
* @author l_garcia
*/

public interface UnidadOrganicaDAO {

    public List<UnidadOrganicaDTO> findUnidadOrganica(UnidadOrganicaFilter filtro) throws UnidadOrganicaException;

}
