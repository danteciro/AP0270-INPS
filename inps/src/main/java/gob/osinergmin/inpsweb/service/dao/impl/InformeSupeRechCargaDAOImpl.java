package gob.osinergmin.inpsweb.service.dao.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import gob.osinergmin.inpsweb.domain.NpsInformeSupeRechCarga;
import gob.osinergmin.inpsweb.domain.builder.InformeSupeRechCargaBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.InformeSupeRechCargaDAO;
import gob.osinergmin.inpsweb.service.exception.InformeSupeRechCargaException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.InformeSupeRechCargaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;

@Repository("InformeSupeRechCargaDAOImpl")
public class InformeSupeRechCargaDAOImpl implements InformeSupeRechCargaDAO{

	@Inject
	private CrudDAO crudDAO;
	
	@Override
	public InformeSupeRechCargaDTO registrar(InformeSupeRechCargaDTO informeSupeRechCargaDTO, UsuarioDTO usuarioDTO) throws InformeSupeRechCargaException  {
		NpsInformeSupeRechCarga npsInformeSupeRechCarga = InformeSupeRechCargaBuilder.toNpsInformeSupeRechCarga(informeSupeRechCargaDTO);
		npsInformeSupeRechCarga.setDatosAuditoria(usuarioDTO);
		npsInformeSupeRechCarga.setEstado(Constantes.ESTADO_ACTIVO);
		crudDAO.create(npsInformeSupeRechCarga);
		return informeSupeRechCargaDTO;
	}

	
}
