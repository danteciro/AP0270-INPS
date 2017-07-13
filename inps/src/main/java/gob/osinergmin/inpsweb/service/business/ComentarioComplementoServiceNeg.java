package gob.osinergmin.inpsweb.service.business;

import gob.osinergmin.inpsweb.service.exception.ComentarioComplementoException;
import gob.osinergmin.mdicommon.domain.dto.ComentarioComplementoDTO;
import gob.osinergmin.mdicommon.domain.ui.ComentarioComplementoFilter;
import java.util.List;

/**
 *
 * @author jpiro
 */
public interface ComentarioComplementoServiceNeg {
    public List<ComentarioComplementoDTO> getDataLstComentarioComplemento(ComentarioComplementoFilter filtro) throws ComentarioComplementoException;
}
