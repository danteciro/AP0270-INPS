package gob.osinergmin.inpsweb.service.business.impl;

import javax.inject.Inject;
import gob.osinergmin.inpsweb.domain.NpsInformeSupeRechCarga;
import gob.osinergmin.inpsweb.domain.builder.InformeSupeRechCargaBuilder;
import gob.osinergmin.inpsweb.service.business.InformeSupeRechCargaServiceNeg;
import gob.osinergmin.inpsweb.service.dao.InformeSupeRechCargaDAO;
import gob.osinergmin.inpsweb.service.exception.InformeSupeRechCargaException;
import gob.osinergmin.mdicommon.domain.dto.InformeSupeRechCargaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("InformeSupeRechCargaServiceNegImpl")
public class InformeSupeRechCargaServiceNegImpl implements InformeSupeRechCargaServiceNeg{

	@Inject
	private InformeSupeRechCargaDAO informeSupeRechCargaDAO;
	
	@Override
	@Transactional
	public void registrar(InformeSupeRechCargaDTO informeSupeRechCargaDTO, UsuarioDTO usuarioDTO) throws InformeSupeRechCargaException {
		informeSupeRechCargaDAO.registrar(informeSupeRechCargaDTO, usuarioDTO);
	}
	
}
