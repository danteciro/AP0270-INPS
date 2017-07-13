package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.inpsweb.service.exception.ComentarioIncumplimientoException;
import gob.osinergmin.mdicommon.domain.dto.EscenarioIncumplimientoDTO;
import gob.osinergmin.mdicommon.domain.ui.EscenarioIncumplimientoFilter;
import java.util.List;

/**
 *
 * @author jpiro
 */
public interface EscenarioIncumplimientoDAO {
    public List<EscenarioIncumplimientoDTO> findByFilter(EscenarioIncumplimientoFilter filtro) throws ComentarioIncumplimientoException;
}
