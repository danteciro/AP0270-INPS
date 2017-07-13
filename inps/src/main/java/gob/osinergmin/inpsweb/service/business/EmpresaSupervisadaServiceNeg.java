package gob.osinergmin.inpsweb.service.business;

import java.util.List;

import gob.osinergmin.mdicommon.domain.dto.DireccionEmpSupDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresaContactoDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresaSupDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.dto.busqueda.BusquedaDireccionxEmpSupervisada;

public interface EmpresaSupervisadaServiceNeg {

	EmpresaSupDTO editarEmpresaSupervisada(EmpresaSupDTO empresa, gob.osinergmin.mdicommon.domain.dto.UsuarioDTO usuario);

	EmpresaSupDTO obtenerEmpresaSupervisada(EmpresaSupDTO empresa);

	List<BusquedaDireccionxEmpSupervisada> listarDireccionEmpresaSupervisada(Long idEmpresaSupervisada);
	
	public BusquedaDireccionxEmpSupervisada direccionEmpresaSupervisada(Long idDireccionEmpresaSupervisada);

	public DireccionEmpSupDTO guardarDireccionEmpresaSupervisada(DireccionEmpSupDTO direccionEmpresa, UsuarioDTO usuario);

	public DireccionEmpSupDTO eliminardireccionEmpresaSupervisada(DireccionEmpSupDTO direccion, UsuarioDTO usuario);

	DireccionEmpSupDTO actualizarDireccionEmpresaSupervisada(DireccionEmpSupDTO direccionEmpresa, UsuarioDTO usuario);

	List<EmpresaContactoDTO> listarEmpresaContactoEmpresaSupervisada(Long idEmpresaSupervisada);

	EmpresaContactoDTO obtenerEmpresaContacto(Long idEmpresaContacto);

	EmpresaContactoDTO actualizarEmpresaContactoEmpresaSupervisada(EmpresaContactoDTO empresaContacto, UsuarioDTO usuario);

	EmpresaContactoDTO eliminarEmpresaContactoEmpresaSupervisada(EmpresaContactoDTO empresaContacto, UsuarioDTO usuario);

	EmpresaContactoDTO guardarEmpresaContactoEmpresaSupervisada(EmpresaContactoDTO empresaContacto, UsuarioDTO usuario);
	
}
