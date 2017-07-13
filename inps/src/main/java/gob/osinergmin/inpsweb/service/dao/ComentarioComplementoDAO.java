package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.inpsweb.service.exception.ComentarioComplementoException;
import gob.osinergmin.mdicommon.domain.dto.ComentarioComplementoDTO;
import gob.osinergmin.mdicommon.domain.dto.ComplementoDTO;
import gob.osinergmin.mdicommon.domain.ui.ComentarioComplementoFilter;
import java.util.List;

/**
 *
 * @author jpiro
 */
public interface ComentarioComplementoDAO {
   public List<ComentarioComplementoDTO> getLstComentarioComplemento(ComentarioComplementoFilter filtro) throws ComentarioComplementoException; 
   public List<ComplementoDTO.OpcionDTO> getOpcionVistaComplemento(ComplementoDTO complemento,ComentarioComplementoFilter filtro) throws ComentarioComplementoException; 
}
