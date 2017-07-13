package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.inpsweb.service.exception.ComplementoDetSupervisionException;
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
public interface ComplementoDetSupervisionDAO {
    public ComplementoDetSupervisionDTO guardarComplementoDetSupervision(ComplementoDetSupervisionDTO complementoDetSupervisionDTO, UsuarioDTO usuarioDTO) throws ComplementoDetSupervisionException;
    public ComplementoDetSupValorDTO guardarComplementoDetSupValor(ComplementoDetSupValorDTO complementoDetSupValorDTO, UsuarioDTO usuarioDTO) throws ComplementoDetSupervisionException;
    public Long eliminaComplementoDetSupValor(Long idComplementoDetSupervision) throws ComplementoDetSupervisionException;
    public List<ComplementoDetSupervisionDTO> find(ComplementoDetSupervisionFilter filtro) throws ComplementoDetSupervisionException;
    public List<ComplementoDetSupValorDTO> findComplementoDetSupValor(ComplementoDetSupValorFilter filtro) throws ComplementoDetSupervisionException;
}
