package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.inpsweb.service.exception.MotivoNoSupervisionException;
import gob.osinergmin.mdicommon.domain.dto.MotivoNoSupervisionDTO;
import gob.osinergmin.mdicommon.domain.ui.MotivoNoSupervisionFilter;

import java.util.List;

public interface MotivoNoSupervisionDAO {
	public List<MotivoNoSupervisionDTO> findAll() throws MotivoNoSupervisionException;
	public List<MotivoNoSupervisionDTO> findMotivoNoSupervision (MotivoNoSupervisionFilter filtro) throws MotivoNoSupervisionException;
}
