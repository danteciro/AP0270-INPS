package gob.osinergmin.inpsweb.service.business;

import gob.osinergmin.inpsweb.service.exception.ComentarioDetSupervisionException;
import gob.osinergmin.inpsweb.service.exception.ComplementoDetSupervisionException;
import gob.osinergmin.mdicommon.domain.dto.ComentarioDetSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.ComplementoDetSupValorDTO;
import gob.osinergmin.mdicommon.domain.dto.ComplementoDetSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.ComplementoDetSupValorFilter;
import gob.osinergmin.mdicommon.domain.ui.ComplementoDetSupervisionFilter;
import java.util.List;

/**
 *
 * @author jpiro
 */
public interface ComplementoDetSupervisionServiceNeg {
    public ComentarioDetSupervisionDTO guardarComplementoDetSupervision(ComentarioDetSupervisionDTO comenDetSuperDto,UsuarioDTO usuarioDTO) throws ComentarioDetSupervisionException;
    public List<ComplementoDetSupervisionDTO> findComplementoDetSupervision(ComplementoDetSupervisionFilter filtro) throws ComplementoDetSupervisionException;
    public List<ComplementoDetSupValorDTO> findComplementoDetSupValor(ComplementoDetSupValorFilter filtro) throws ComplementoDetSupervisionException;
}
