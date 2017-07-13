package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.inpsweb.domain.NpsInformeSupeRechCarga;
import gob.osinergmin.inpsweb.service.exception.InformeSupeRechCargaException;
import gob.osinergmin.mdicommon.domain.dto.InformeSupeRechCargaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;

public interface InformeSupeRechCargaDAO {
	public InformeSupeRechCargaDTO registrar(InformeSupeRechCargaDTO informeSupeRechCargaDTO, UsuarioDTO usuarioDTO) throws InformeSupeRechCargaException;
}
