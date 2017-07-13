package gob.osinergmin.inpsweb.service.business;

import java.util.List;

import gob.osinergmin.inpsweb.service.exception.EmpresaSupervisionViewException;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresaViewDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.ui.EmpresaViewFilter;

public interface EmpresaSupervisionViewServiceNeg {
	public List<EmpresaViewDTO> findEmpresas(EmpresaViewFilter empresaViewFilter) throws EmpresaSupervisionViewException;
	public DocumentoAdjuntoDTO adjuntarInformeSiged(DocumentoAdjuntoDTO documentoAdjuntoDTO, ExpedienteDTO expedienteDTO, EmpresaViewFilter empresaViewFilter) throws EmpresaSupervisionViewException;
	
}
