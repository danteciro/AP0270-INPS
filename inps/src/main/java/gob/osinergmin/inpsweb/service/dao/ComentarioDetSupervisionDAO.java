package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.inpsweb.service.exception.ComentarioDetSupervisionException;
import gob.osinergmin.mdicommon.domain.dto.ComentarioDetSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.ComentarioDetSupervisionFilter;
import java.util.List;

/**
 *
 * @author jpiro
 */
public interface ComentarioDetSupervisionDAO {
    public ComentarioDetSupervisionDTO guardarComentarioDetSupervision(ComentarioDetSupervisionDTO comentarioDetSupervisionDTO, UsuarioDTO usuarioDTO) throws ComentarioDetSupervisionException;
    public ComentarioDetSupervisionDTO cambiaEstadoComentarioDetSupervision(ComentarioDetSupervisionDTO comentarioDetSupervisionDTO, UsuarioDTO usuarioDTO) throws ComentarioDetSupervisionException;
    public List<ComentarioDetSupervisionDTO> find(ComentarioDetSupervisionFilter filtro) throws ComentarioDetSupervisionException;
}
