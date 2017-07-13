package gob.osinergmin.inpsweb.service.business;

import gob.osinergmin.inpsweb.service.exception.ComentarioDetSupervisionException;
import gob.osinergmin.mdicommon.domain.dto.ComentarioDetSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.ComentarioDetSupervisionFilter;
import java.util.List;

/**
 *
 * @author jpiro
 */
public interface ComentarioDetSupervisionServiceNeg {
    public ComentarioDetSupervisionDTO asignarComentarioDetSupervision(ComentarioDetSupervisionDTO comenDetSuperDto,UsuarioDTO usuarioDTO) throws ComentarioDetSupervisionException;
    public ComentarioDetSupervisionDTO desasignarComentarioDetSupervision(ComentarioDetSupervisionDTO comenDetSuperDto,UsuarioDTO usuarioDTO) throws ComentarioDetSupervisionException;
    public List<ComentarioDetSupervisionDTO> findComentarioDetSupervision(ComentarioDetSupervisionFilter filtro) throws ComentarioDetSupervisionException;    
    public ComentarioDetSupervisionDTO cambiaEstadoComentarioDetSupervision(ComentarioDetSupervisionDTO comenDetSuperDto,UsuarioDTO usuarioDTO) throws ComentarioDetSupervisionException;
}
