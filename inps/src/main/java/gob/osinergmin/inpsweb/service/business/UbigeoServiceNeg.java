package gob.osinergmin.inpsweb.service.business;

import gob.osinergmin.mdicommon.domain.dto.DepartamentoDTO;
import gob.osinergmin.mdicommon.domain.dto.DistritoDTO;
import gob.osinergmin.mdicommon.domain.dto.ProvinciaDTO;
import gob.osinergmin.mdicommon.domain.dto.UbigeoDTO;
import gob.osinergmin.mdicommon.domain.ui.UbigeoFilter;

import java.util.List;

public interface UbigeoServiceNeg {

	List<DepartamentoDTO> obtenerDepartamentos();

	List<ProvinciaDTO> obtenerProvincias(String idDepartamento);

	List<DistritoDTO> obtenerDistritos(String idProvincia, String idProvincia2);
       
        List<UbigeoDTO> obtenerUbigeo(UbigeoFilter ubigeoFilter);
        
        
}
