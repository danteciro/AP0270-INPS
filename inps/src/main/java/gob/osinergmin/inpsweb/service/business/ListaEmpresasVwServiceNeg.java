package gob.osinergmin.inpsweb.service.business;

import java.util.List;
import gob.osinergmin.inpsweb.service.exception.ListaEmpresasVwException;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresaViewDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.SupeCampRechCargaDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionRechCargaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.EmpresaViewFilter;

public interface ListaEmpresasVwServiceNeg {
 
	public List<EmpresaViewDTO> findEmpresasVw(EmpresaViewFilter filtro) throws ListaEmpresasVwException;
	public ExpedienteDTO generarExpedienteRechazoCarga(DocumentoAdjuntoDTO archivoDTO,String ruc,ExpedienteDTO expedienteDTO,Long idPersonalSiged) throws ListaEmpresasVwException;
	public SupervisionRechCargaDTO registrarSupervisionRechazoCarga(SupervisionRechCargaDTO supervisionRechCargaDTO,UsuarioDTO usuarioDTO) throws ListaEmpresasVwException;
	
 
}
