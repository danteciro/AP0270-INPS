/**

* Resumen
* Objeto			: EmpresasZonaServiceNeg.java
* Descripción		: Clase Interfaz de la capa de negocio que contiene la declaración de métodos
* 					  a implementarse de EmpresasZona.
* Fecha de Creación	: 15/09/216.
* PR de Creación	: OSINE_SFS-1063
* Autor				: Hernan Torres Saenz.
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* 
*/

package gob.osinergmin.inpsweb.service.business;

import gob.osinergmin.dse_common.domain.dto.EtapaDTO;
import gob.osinergmin.dse_common.domain.dto.ReleDTO;
import gob.osinergmin.dse_common.domain.dto.SubEstacionDTO;
import gob.osinergmin.dse_common.domain.dto.ZonaDTO;
import gob.osinergmin.inpsweb.service.exception.DetaSupeCampRechCargaException;
import gob.osinergmin.inpsweb.service.exception.DocumentoAdjuntoException;
import gob.osinergmin.inpsweb.service.exception.ExpedienteException;
import gob.osinergmin.inpsweb.service.exception.SupeCampRechCargaException;
import gob.osinergmin.mdicommon.domain.dto.ConfiguracionRelesDTO;
import gob.osinergmin.mdicommon.domain.dto.DetaConfRelesDTO;
import gob.osinergmin.mdicommon.domain.dto.DetaSupeCampRechCargaDTO;
import gob.osinergmin.mdicommon.domain.dto.DetaSupeCampRechCargaRepDTO;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresasZonaVwDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.SupeCampRechCargaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.ConfiguracionRelesFilter;
import gob.osinergmin.mdicommon.domain.ui.DetaSupeCampRechCargaFilter;
import gob.osinergmin.mdicommon.domain.ui.EmpresasZonaVwFilter;
import gob.osinergmin.mdicommon.domain.ui.SupeCampRechCargaFilter;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface EmpresasZonaServiceNeg {

	public List<EmpresasZonaVwDTO> listarEmpresasZona(EmpresasZonaVwFilter filtro);
	
	public List<ZonaDTO> listarZonasXAnio(String anio);
	
	public List<SubEstacionDTO> listarSubEstaciones(String idEmpresa, String anio, String idZona);
	
	public List<ReleDTO> listarReles(String subEstacion, String idEmpresa, String anio, String etapas);
	
	public List<SupeCampRechCargaDTO> listarSupeCampRechCarga(SupeCampRechCargaFilter filtro);
	
	public SupeCampRechCargaDTO insertarSupeCampRechCarga(String idEmpresa, String anio, SupeCampRechCargaDTO supeCampRechCargaDTO, UsuarioDTO usuarioDTO) throws SupeCampRechCargaException;
	
	public SupeCampRechCargaDTO actualizarSupeCampRechCarga(SupeCampRechCargaDTO supeCampRechCargaDTO, UsuarioDTO usuarioDTO, String pantalla) throws SupeCampRechCargaException;
	
	public SupeCampRechCargaDTO inactivarSupeCampYDetaSupeCamp(SupeCampRechCargaDTO supeCampRechCargaDTO, UsuarioDTO usuarioDTO, String pantalla) throws SupeCampRechCargaException;
	
	public List<DetaSupeCampRechCargaDTO> listarDetaSupeCampRechCarga(DetaSupeCampRechCargaFilter filtro);
	
	public DetaSupeCampRechCargaDTO insertarDetaSupeCampRechCarga(DetaSupeCampRechCargaDTO detaSupeCampRechCargaDTO, List<ReleDTO> listaReles, UsuarioDTO usuarioDTO) throws DetaSupeCampRechCargaException;
	
	public DetaSupeCampRechCargaDTO actualizarDetaSupeCampRechCarga(DetaSupeCampRechCargaDTO detaSupeCampRechCargaDTO, UsuarioDTO usuarioDTO) throws DetaSupeCampRechCargaException;
	
	public DocumentoAdjuntoDTO enviarActaSiged(DocumentoAdjuntoDTO doc, ExpedienteDTO expedienteDTO, Long idPersonalSiged, String descripcionEmpresa, String ruc) throws DocumentoAdjuntoException;
	
	public List<ConfiguracionRelesDTO> listarConfiguracionReles(ConfiguracionRelesFilter filtro);
	
	public int gestionarRelesDetSupe (List<DetaSupeCampRechCargaDTO> listaRelesInsertar, 
    		List<DetaSupeCampRechCargaDTO> listaRelesActualizar, boolean inactivarCabecera, UsuarioDTO usuario) throws DetaSupeCampRechCargaException;
	
	public ReleDTO  ReporteActaInspeccion(List<ReleDTO> lista, HttpServletRequest request, HttpServletResponse response, 
			   HttpSession session, boolean esPlantilla, SupeCampRechCargaDTO objSupeCampRechCargaDTO, List<DetaSupeCampRechCargaDTO> lDetaSupeCampRechCargaDTO) throws DocumentoAdjuntoException;
		
	public List<EtapaDTO> listarEtapas(String idEmpresa, String anio, String idSubEstacion);
	
	public ConfiguracionRelesDTO registrarConfiguracionReles( ConfiguracionRelesDTO configuracionRele, UsuarioDTO usuarioDTO) throws SupeCampRechCargaException;

	public SupeCampRechCargaDTO obtenerRegistroEmpresa(Long idSupeCampRechCarga) throws SupeCampRechCargaException;
	
	public void ReporteActaInspeccionRep(List<ReleDTO> lReleDTO,HttpServletRequest request, HttpServletResponse response,
				HttpSession session, boolean esPlantilla,
				SupeCampRechCargaDTO objSupeCampRechCargaDTO,
				List<DetaSupeCampRechCargaRepDTO> lDetaSupeCampRechCargaRepDTO);
	
	public DetaConfRelesDTO detalleConfiguracionReles( DetaConfRelesDTO detaConfigRel , UsuarioDTO usuarioDTO) throws SupeCampRechCargaException;
	
	public Long existeConfiguracionRele(ConfiguracionRelesDTO configuracionReleDTO);
	
	public boolean borrarDetalleConfiguracionReles(ConfiguracionRelesDTO configuracionReleDTO);
	
	public String validaConfiguracion(String idEmpresa, String anio);

	public List<EtapaDTO> listarEtapasConfiguradas(String idEmpresa,String anio, String idSubEstacion);

	// Test
	//public List<DetaConfRelesDTO> findAllEtapaByEmpresaByAnio(String idEmpresa,String anio);
	public List<DetaConfRelesDTO> findAllEtapaByEmpresaByAnio(String idEmpresa,String anio, Long idZona);
	// Test

	public boolean borrarConfiguracionReles(ConfiguracionRelesDTO configuracionRele);

	public boolean deleteConfig(ConfiguracionRelesDTO configuracionRele);
		
}
