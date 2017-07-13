package gob.osinergmin.inpsweb.service.business;

import gob.osinergmin.mdicommon.domain.dto.ActividadDTO;
import gob.osinergmin.mdicommon.domain.ui.ActividadFilter;

import java.util.List;

public interface ActividadServiceNeg {

	List<ActividadDTO> listarActividad(int[] auxiliar);
        public List<ActividadDTO> findBy(ActividadFilter filtro);

}
