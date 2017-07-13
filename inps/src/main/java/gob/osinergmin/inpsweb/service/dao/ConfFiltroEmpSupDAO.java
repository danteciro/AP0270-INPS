package gob.osinergmin.inpsweb.service.dao;
import gob.osinergmin.inpsweb.service.exception.ConfFiltroEmpSupException;
import gob.osinergmin.mdicommon.domain.dto.ConfFiltroEmpSupDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.ConfFiltroEmpSupFilter;
import java.util.List;

/**
 *
 * @author mdiosesf
 */
public interface ConfFiltroEmpSupDAO {
    public List<ConfFiltroEmpSupDTO> findConfFiltroEmpSup(ConfFiltroEmpSupFilter filtro) throws ConfFiltroEmpSupException;
    public ConfFiltroEmpSupDTO create(ConfFiltroEmpSupDTO confFiltroEmpSupDTO, UsuarioDTO usuarioDTO) throws ConfFiltroEmpSupException;
    public ConfFiltroEmpSupDTO update(ConfFiltroEmpSupDTO confFiltroEmpSupDTO, UsuarioDTO usuarioDTO) throws ConfFiltroEmpSupException;
}
