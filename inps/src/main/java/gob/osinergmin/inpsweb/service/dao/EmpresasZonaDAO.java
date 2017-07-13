/**
* Resumen		
* Objeto			: EmpresasZonaDAO.java
* Descripción		: Clase que proporciona una interfaz para la implementación de métodos
* 					  en EmpresasZonaDAOImpl
* Fecha de Creación	: 14/09/2016.
* PR de Creación	: OSINE_SFS-1063
* Autor				: Hernan Torres Saenz.
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
*
*/

package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.inpsweb.service.exception.DetaSupeCampRechCargaException;
import gob.osinergmin.inpsweb.service.exception.SupeCampRechCargaException;
import gob.osinergmin.mdicommon.domain.dto.ConfiguracionRelesDTO;
import gob.osinergmin.mdicommon.domain.dto.DetaConfRelesDTO;
import gob.osinergmin.mdicommon.domain.dto.DetaSupeCampRechCargaDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresasZonaVwDTO;
import gob.osinergmin.mdicommon.domain.dto.SupeCampRechCargaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.ConfiguracionRelesFilter;
import gob.osinergmin.mdicommon.domain.ui.DetaSupeCampRechCargaFilter;
import gob.osinergmin.mdicommon.domain.ui.EmpresasZonaVwFilter;
import gob.osinergmin.mdicommon.domain.ui.SupeCampRechCargaFilter;

import java.util.List;

public interface EmpresasZonaDAO {
	
	public List<EmpresasZonaVwDTO> listarEmpresasZona(EmpresasZonaVwFilter filtro);
	
	public List<SupeCampRechCargaDTO> listarSupeCampRechCarga(SupeCampRechCargaFilter filtro);
	
	public SupeCampRechCargaDTO insertarSupeCampRechCarga(SupeCampRechCargaDTO supeCampRechCargaDTO, UsuarioDTO usuarioDTO) throws SupeCampRechCargaException;
	
	public SupeCampRechCargaDTO actualizarSupeCampRechCarga(SupeCampRechCargaDTO supeCampRechCargaDTO, UsuarioDTO usuarioDTO, String pantalla) throws SupeCampRechCargaException;
	
	public List<DetaSupeCampRechCargaDTO> listarDetaSupeCampRechCarga(DetaSupeCampRechCargaFilter filtro);
	
	public DetaSupeCampRechCargaDTO insertarDetaSupeCampRechCarga(DetaSupeCampRechCargaDTO detaSupeCampRechCargaDTO, UsuarioDTO usuarioDTO) throws DetaSupeCampRechCargaException;
	
	public DetaSupeCampRechCargaDTO actualizarDetaSupeCampRechCarga(DetaSupeCampRechCargaDTO detaSupeCampRechCargaDTO, UsuarioDTO usuarioDTO) throws DetaSupeCampRechCargaException;

	public SupeCampRechCargaDTO obtenerRegistroEmpresa(Long idSupeCampRechCarga) throws SupeCampRechCargaException;
	
	public List<ConfiguracionRelesDTO> listarConfiguracionReles(ConfiguracionRelesFilter filtro);
	
	public ConfiguracionRelesDTO registrarConfiguracionReles(ConfiguracionRelesDTO configuracionReleDTO,UsuarioDTO usuarioDTO) throws SupeCampRechCargaException;
	
	public DetaConfRelesDTO detalleConfiguracionReles(DetaConfRelesDTO detaConfigRelDTO , UsuarioDTO usuarioDTO) throws SupeCampRechCargaException;
	
	public Long existeConfiguracionRele(ConfiguracionRelesDTO configuracionReleDTO);
	
	public boolean borrarDetalleConfiguracionReles(ConfiguracionRelesDTO configuracionReleDTO);
	
	public List<ConfiguracionRelesDTO> listarSubEstacionConfiguracion(String idEmpresa, String anio);

	public List<DetaConfRelesDTO> findDetaConfReles(ConfiguracionRelesDTO configuracionReleDTO);

	public void deleteDetaRele(DetaConfRelesDTO detalle);

	public ConfiguracionRelesDTO findConfiguracionRele(String idEmpresa,String anio, String idSubEstacion);

	// Test
	//public List<ConfiguracionRelesDTO> findAllCnfReles(String idEmpresa, String anio);
	public List<ConfiguracionRelesDTO> findAllCnfReles(String idEmpresa, String anio, Long idZona);
	// Test

	public void deleteCabeRele(ConfiguracionRelesDTO configuracionRele);
        
}