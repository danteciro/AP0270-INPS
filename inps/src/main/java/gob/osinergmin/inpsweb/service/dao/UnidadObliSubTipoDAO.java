package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.mdicommon.domain.dto.UnidadObliSubTipoDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;

import java.util.List;

public interface UnidadObliSubTipoDAO {

	UnidadObliSubTipoDTO guardarUnidadMuestral(UnidadObliSubTipoDTO unidadObliSubTipo, UsuarioDTO usuarioDTO);

	List<UnidadObliSubTipoDTO> listarPruebaMuestralxPeriodoxSubTipo(UnidadObliSubTipoDTO filtroPruebaMuestral);

	UnidadObliSubTipoDTO updateUnidadMuestral(UnidadObliSubTipoDTO unidadMuestraltoUpdate, UsuarioDTO usuarioDTO);

}
