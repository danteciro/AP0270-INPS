package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.inpsweb.service.exception.EmpresaSupervisionViewException;
import gob.osinergmin.mdicommon.domain.dto.EmpresaSupervisadaViewDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresaViewDTO;
import gob.osinergmin.mdicommon.domain.ui.EmpresaViewFilter;

import java.util.List;

public interface EmpresaSupervisionViewDAO {
	public List<EmpresaViewDTO> findEmpresas(EmpresaViewFilter empresaViewFilter) throws EmpresaSupervisionViewException;

}
