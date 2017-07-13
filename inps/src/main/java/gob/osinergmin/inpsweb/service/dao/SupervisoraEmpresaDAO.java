package gob.osinergmin.inpsweb.service.dao;
import gob.osinergmin.inpsweb.service.exception.SupervisoraEmpresaException;
import gob.osinergmin.mdicommon.domain.dto.ConfFiltroEmpSupDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisoraEmpresaDTO;
import gob.osinergmin.mdicommon.domain.ui.SupervisoraEmpresaFilter;
import java.util.List;

/**
*
* @author mdiosesf
*/
public interface SupervisoraEmpresaDAO {
	public List<SupervisoraEmpresaDTO> listarSupervisoraEmpresaConfFiltros(SupervisoraEmpresaFilter filtro, List<ConfFiltroEmpSupDTO>listaConfFiltroEmpSup) throws SupervisoraEmpresaException;
        public SupervisoraEmpresaDTO getById(Long idSupervisoraEmpresa) throws SupervisoraEmpresaException;
}
