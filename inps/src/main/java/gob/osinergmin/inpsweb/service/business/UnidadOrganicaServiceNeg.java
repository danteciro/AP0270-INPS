package gob.osinergmin.inpsweb.service.business;

import gob.osinergmin.mdicommon.domain.dto.UnidadOrganicaDTO;
import gob.osinergmin.mdicommon.domain.ui.UnidadOrganicaFilter;

import java.util.List;

/**
 *
 * @author jpiro
 */
public interface UnidadOrganicaServiceNeg {
    public List<UnidadOrganicaDTO> obtenerListadoUnidadOrganica(UnidadOrganicaFilter filtro);
    public Long obtenerIdUnidadOrganicaByIdEntidad(Long idEntidad);
    public List<UnidadOrganicaDTO> findUnidadOrganica(UnidadOrganicaFilter filtro);
	public String getBandejaDefault(Long idPersonal);
}
