package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.inpsweb.service.exception.ListaEmpresasVwException;
import gob.osinergmin.mdicommon.domain.dto.EmpresaViewDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.SupeCampRechCargaDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionRechCargaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.EmpresaViewFilter;


import java.util.List;

public interface ListaEmpresasVwDAO {
	public List<EmpresaViewDTO> findEmpresasVw(EmpresaViewFilter filtro) throws ListaEmpresasVwException;
	public List<SupeCampRechCargaDTO> listaActasExpediente(String nroExpediente) throws ListaEmpresasVwException;
	public SupervisionRechCargaDTO registrarSupervisionRechazoCarga(SupervisionRechCargaDTO supervisionRechCargaDTO,UsuarioDTO usuarioDTO) throws ListaEmpresasVwException;
	public List<SupeCampRechCargaDTO> listaActasZona(String numeroExpediente) throws ListaEmpresasVwException;

}
