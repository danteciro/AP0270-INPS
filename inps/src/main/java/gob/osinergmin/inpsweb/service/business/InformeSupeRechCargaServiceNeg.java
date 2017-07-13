package gob.osinergmin.inpsweb.service.business;

import gob.osinergmin.inpsweb.domain.NpsInformeSupeRechCarga;
import gob.osinergmin.inpsweb.service.exception.InformeSupeRechCargaException;
import gob.osinergmin.mdicommon.domain.dto.InformeSupeRechCargaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;

public interface InformeSupeRechCargaServiceNeg {
	public void registrar(InformeSupeRechCargaDTO npsInformeSupeRechCarga, UsuarioDTO usuarioDTO) throws InformeSupeRechCargaException;
}
