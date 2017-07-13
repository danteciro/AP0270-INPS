/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. 
 */
package gob.osinergmin.inpsweb.service.business.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import gob.osinergmin.inpsweb.service.business.EmpresaSupervisadaServiceNeg;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.base.BaseConstantesOutBean;
import gob.osinergmin.mdicommon.domain.dto.DireccionEmpSupDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresaContactoDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresaSupDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.dto.busqueda.BusquedaDireccionxEmpSupervisada;
import gob.osinergmin.mdicommon.domain.in.BuscarDireccionEmpresaInRO;
import gob.osinergmin.mdicommon.domain.in.BuscarEmpresaContactosInRO;
import gob.osinergmin.mdicommon.domain.in.EditarEmpresaContactoInpsInRO;
import gob.osinergmin.mdicommon.domain.in.EditarEmpresaSupervisadaInRO;
import gob.osinergmin.mdicommon.domain.in.EliminarEmpresaContactoInpsInRO;
import gob.osinergmin.mdicommon.domain.in.GuardarDireccionEmpresaInRO;
import gob.osinergmin.mdicommon.domain.in.GuardarEmpresaContactoInpsInRO;
import gob.osinergmin.mdicommon.domain.in.ObtenerEmpresaSupervisadaCompletoInRO;
import gob.osinergmin.mdicommon.domain.out.BuscarEmpresaContactosOutRO;
import gob.osinergmin.mdicommon.domain.out.EditarEmpresaContactoInpsOutRO;
import gob.osinergmin.mdicommon.domain.out.EditarEmpresaSupervisadaOutRO;
import gob.osinergmin.mdicommon.domain.out.EliminarEmpresaContactoInpsOutRO;
import gob.osinergmin.mdicommon.domain.out.GuardarDireccionEmpresaOutRO;
import gob.osinergmin.mdicommon.domain.out.GuardarEmpresaContactoInpsOutRO;
import gob.osinergmin.mdicommon.domain.out.ObtenerEmpresaSupervisadaCompletoOutRO;
import gob.osinergmin.mdicommon.domain.ui.EmpresaContactoInpsFilter;
import gob.osinergmin.mdicommon.remote.DireccionEmpSupEndpoint;
import gob.osinergmin.mdicommon.remote.EmpresaContactoEndpoint;
import gob.osinergmin.mdicommon.remote.EmpresaSupervisadaEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jpiro
 */
@Service("empresaSupervisadaServiceNeg")
public class EmpresaSupervisadaServiceNegImpl implements EmpresaSupervisadaServiceNeg{	
    private static final Logger LOG = LoggerFactory.getLogger(EmpresaSupervisadaServiceNegImpl.class); 
    
    @Inject
    private EmpresaSupervisadaEndpoint empresaSupervisadaEndpoint;
    @Inject
    private DireccionEmpSupEndpoint direccionEmpSupEndpoint;
    @Inject
    private EmpresaContactoEndpoint empresaContactoEndpoint;
	@Override
	@Transactional
	public EmpresaSupDTO editarEmpresaSupervisada(EmpresaSupDTO empresa,UsuarioDTO usuario) {
		LOG.info("actualizarEmpresaSupervisada");
		EmpresaSupDTO retorno = new EmpresaSupDTO();
		try {
			EditarEmpresaSupervisadaInRO peticion = new EditarEmpresaSupervisadaInRO();
			peticion.setEmpresa(empresa);
			peticion.setUsuario(usuario);
			EditarEmpresaSupervisadaOutRO response=empresaSupervisadaEndpoint.editarEmpresaSupervisada(peticion);
			if(response.getCodigoResultado().equals(BaseConstantesOutBean.SUCCESS)){
                if(response.getIdEmpresa()!=null){
                    retorno.setIdEmpresaSupervisada(response.getIdEmpresa());
                }                
            }
		} catch (Exception e) {
			LOG.error("Error en actualizar Empresa Supervisada",e);
		}
		return retorno;
	}

	@Override
	public EmpresaSupDTO obtenerEmpresaSupervisada(EmpresaSupDTO empresa) {
		LOG.info("obtenerEmpresaSupervisada");
		EmpresaSupDTO retorno = new EmpresaSupDTO();
		try {
			LOG.info("Id Entrante -->"+empresa.getIdEmpresaSupervisada());
			ObtenerEmpresaSupervisadaCompletoInRO peticion = new ObtenerEmpresaSupervisadaCompletoInRO();
			peticion.setIdEmpresa(empresa.getIdEmpresaSupervisada());
			ObtenerEmpresaSupervisadaCompletoOutRO response=empresaSupervisadaEndpoint.obtenerEmpresaSupervisada(peticion);
			LOG.info(response.getCodigoResultado());
			if(response.getCodigoResultado().equals(BaseConstantesOutBean.SUCCESS)){
				LOG.info("Id --> "+response.getEmpresaSupervisada().getIdEmpresaSupervisada());
				if(response.getEmpresaSupervisada()!=null){
	                if(response.getEmpresaSupervisada().getIdEmpresaSupervisada()!=null){
	                    retorno.setIdEmpresaSupervisada(response.getEmpresaSupervisada().getIdEmpresaSupervisada());
	                    retorno.setRuc(response.getEmpresaSupervisada().getRuc());
	                    retorno.setRazonSocial(response.getEmpresaSupervisada().getRazonSocial());
	                    retorno.setNombreComercial(response.getEmpresaSupervisada().getNombreComercial());
	                    retorno.setTelefono(response.getEmpresaSupervisada().getTelefono());
                            retorno.setCorreoElectronico(response.getEmpresaSupervisada().getCorreoElectronico());
	                    /* OSINE_SFS-480 - RSIS 47 - Inicio */
	                    retorno.setNroIdentificacion(response.getEmpresaSupervisada().getNroIdentificacion());
	                    if(response.getEmpresaSupervisada().getTipoDocumentoIdentidad()!=null)
	                    	retorno.setTipoDocumentoIdentidad(new MaestroColumnaDTO(response.getEmpresaSupervisada().getTipoDocumentoIdentidad().getIdMaestroColumna()));
	                    /* OSINE_SFS-480 - RSIS 47 - Fin */
	                    if(response.getEmpresaSupervisada()!=null){
	                    	if(response.getEmpresaSupervisada().getCiiuPrincipal()!=null){
	                    		if(response.getEmpresaSupervisada().getCiiuPrincipal().getIdMaestroColumna()!=null){
	                    			MaestroColumnaDTO ciiuPrincipal=new MaestroColumnaDTO();
		    	                    ciiuPrincipal.setIdMaestroColumna(response.getEmpresaSupervisada().getCiiuPrincipal().getIdMaestroColumna());
		    	                    retorno.setCiiuPrincipal(ciiuPrincipal);
	                    		}                		
	                    	}
	                    }                    
	                } 
				}
            }
		} catch (Exception e) {
			LOG.error("Error en obtener Empresa Supervisada",e);
		}
		return retorno;
	}

	@Override
	public List<BusquedaDireccionxEmpSupervisada> listarDireccionEmpresaSupervisada(Long idEmpresaSupervisada) {
		LOG.info("listarDireccionEmpresaSupervisada :: Id Empresa :--> "+idEmpresaSupervisada);
		List<BusquedaDireccionxEmpSupervisada> retorno = new ArrayList<BusquedaDireccionxEmpSupervisada>();
		try {
			BuscarDireccionEmpresaInRO peticion = new BuscarDireccionEmpresaInRO();
			peticion.setIdEmpresa(idEmpresaSupervisada);
			peticion.setTodos(true);
			GuardarDireccionEmpresaOutRO response=direccionEmpSupEndpoint.buscaDireccionEmpresaSupervisada(peticion);
			LOG.info("Respuesta :--> " +response.getCodigoResultado());
			if(response.getCodigoResultado().equals(BaseConstantesOutBean.SUCCESS)){
                if(response.getListadoDireccionEmpSupervisada()!=null){
                    retorno = response.getListadoDireccionEmpSupervisada();
                }                
            }
		} catch (Exception e) {
			LOG.error("Error en actualizar Empresa Supervisada",e);
		}
		return retorno;
	}
	
	@Override
	public BusquedaDireccionxEmpSupervisada direccionEmpresaSupervisada(Long idDireccionEmpresaSupervisada) {
		LOG.info("listarDireccionEmpresaSupervisada :: Id Empresa :--> "+idDireccionEmpresaSupervisada);
		BusquedaDireccionxEmpSupervisada retorno = new BusquedaDireccionxEmpSupervisada();
		try {
			BuscarDireccionEmpresaInRO peticion = new BuscarDireccionEmpresaInRO();
			peticion.setIdDireccionEmpresa(idDireccionEmpresaSupervisada);
			peticion.setTodos(false);
			GuardarDireccionEmpresaOutRO response=direccionEmpSupEndpoint.buscaDireccionEmpresaSupervisada(peticion);
			LOG.info("Respuesta :--> " +response.getCodigoResultado());
			if(response.getCodigoResultado().equals(BaseConstantesOutBean.SUCCESS)){
                if(response.getListadoDireccionEmpSupervisada()!=null){
                    retorno = response.getListadoDireccionEmpSupervisada().get(0);
                }                
            }
		} catch (Exception e) {
			LOG.error("Error en actualizar Empresa Supervisada",e);
		}
		return retorno;
	}
	
	@Override
	public DireccionEmpSupDTO eliminardireccionEmpresaSupervisada(DireccionEmpSupDTO direccion, UsuarioDTO usuario){
		LOG.info("listarDireccionEmpresaSupervisada :: Id Empresa :--> "+direccion);
		DireccionEmpSupDTO retorno = new DireccionEmpSupDTO();
		try {
			GuardarDireccionEmpresaInRO peticion = new GuardarDireccionEmpresaInRO();
			peticion.setDireccion(direccion);
			peticion.setUsuario(usuario);
			GuardarDireccionEmpresaOutRO response=direccionEmpSupEndpoint.eliminarDireccionEmpresaSupervisada(peticion);
			LOG.info("Respuesta :--> " +response.getCodigoResultado());
			if(response.getCodigoResultado().equals(BaseConstantesOutBean.SUCCESS)){
                if(response.getListadoDireccionEmpSupervisada()!=null){
                    retorno.setIdDireccionEmpresaSup(response.getIdDireccion());
                }                
            }
		} catch (Exception e) {
			LOG.error("Error en actualizar Empresa Supervisada",e);
		}
		return retorno;
	}

	@Override
	public DireccionEmpSupDTO guardarDireccionEmpresaSupervisada(DireccionEmpSupDTO direccionEmpresa, UsuarioDTO usuario){
		LOG.info("crearDireccionEmpresaSupervisada");
		DireccionEmpSupDTO retorno = new DireccionEmpSupDTO();
		try {
			GuardarDireccionEmpresaInRO peticion = new GuardarDireccionEmpresaInRO();
			peticion.setDireccion(direccionEmpresa);
			peticion.setUsuario(usuario);
			GuardarDireccionEmpresaOutRO response= direccionEmpSupEndpoint.guardarDireccionEmpresa(peticion);
			if(response.getCodigoResultado().equals(BaseConstantesOutBean.SUCCESS)){
                if(response.getIdDireccion()!=null){
                    retorno.setIdDireccionEmpresaSup(response.getIdDireccion());
                }                
            }
		} catch (Exception e) {
			LOG.error("Error en crear Direccion Empresa Supervisada",e);
		}
		return retorno;
	}

	@Override
	public DireccionEmpSupDTO actualizarDireccionEmpresaSupervisada(DireccionEmpSupDTO direccionEmpresa, UsuarioDTO usuario) {
		LOG.info("actulizarDireccionEmpresaSupervisada");
		DireccionEmpSupDTO retorno = new DireccionEmpSupDTO();
		try {
			direccionEmpresa.setEstado(Constantes.ESTADO_ACTIVO);
			GuardarDireccionEmpresaInRO peticion = new GuardarDireccionEmpresaInRO();
			peticion.setDireccion(direccionEmpresa);
			peticion.setUsuario(usuario);
			GuardarDireccionEmpresaOutRO response= direccionEmpSupEndpoint.actualizarDireccionEmpresaSupervisada(peticion);
			if(response.getCodigoResultado().equals(BaseConstantesOutBean.SUCCESS)){
                if(response.getIdDireccion()!=null){
                    retorno.setIdDireccionEmpresaSup(response.getIdDireccion());
                }                
            }
		} catch (Exception e) {
			LOG.error("Error en actualizar Direccion Empresa Supervisada",e);
		}
		return retorno;
	}

	@Override
	public List<EmpresaContactoDTO> listarEmpresaContactoEmpresaSupervisada(Long idEmpresaSupervisada) {
		LOG.info("listarEmpresaContactoEmpresaSupervisada :: Id Empresa :--> "+idEmpresaSupervisada);
		List<EmpresaContactoDTO> retorno = new ArrayList<EmpresaContactoDTO>();
		try {
			BuscarEmpresaContactosInRO peticion = new BuscarEmpresaContactosInRO();
			EmpresaContactoInpsFilter contactoFilter = new EmpresaContactoInpsFilter();
			contactoFilter.setIdEmpresaSupervisada(idEmpresaSupervisada);
			contactoFilter.setEstado(Constantes.ESTADO_ACTIVO);
			peticion.setInpsfiltro(contactoFilter);
			LOG.info("idEmpresa" + peticion.getInpsfiltro().getIdEmpresaSupervisada());
			BuscarEmpresaContactosOutRO response=empresaContactoEndpoint.listarEmpresaContactoInps(peticion);
			LOG.info("Respuesta :--> " +response.getCodigoResultado());
			if(response.getCodigoResultado().equals(BaseConstantesOutBean.SUCCESS)){
                if(response.getListadoEmpContacto()!=null){
                    retorno = response.getListadoEmpContacto();
                }                
            }
		} catch (Exception e) {
			LOG.error("Error en listar Empresa Supervisada",e);
		}
		return retorno;
	}

	@Override
	public EmpresaContactoDTO obtenerEmpresaContacto(Long idEmpresaContacto) {
		LOG.info("ObtenerEmpresaContactoEmpresaSupervisada :: Id Empresa :--> "+idEmpresaContacto);
		EmpresaContactoDTO retorno = new EmpresaContactoDTO();
		try {
			BuscarEmpresaContactosInRO peticion = new BuscarEmpresaContactosInRO();
			EmpresaContactoInpsFilter contactoFilter = new EmpresaContactoInpsFilter();
			contactoFilter.setIdEmpresaContacto(idEmpresaContacto);
			peticion.setInpsfiltro(contactoFilter);
			LOG.info("idEmpresa" + peticion.getInpsfiltro().getIdEmpresaSupervisada());
			BuscarEmpresaContactosOutRO response=empresaContactoEndpoint.listarEmpresaContactoInps(peticion);
			LOG.info("Respuesta :--> " +response.getCodigoResultado());
			if(response.getCodigoResultado().equals(BaseConstantesOutBean.SUCCESS)){
                if(response.getListadoEmpContacto()!=null){
                    retorno = response.getListadoEmpContacto().get(0);
                }                
            }
		} catch (Exception e) {
			LOG.error("Error en listar Empresa Supervisada",e);
		}
		LOG.info("Id de regreso :->"+retorno.getIdEmpresaContacto());
		return retorno;
	}

	@Override
	public EmpresaContactoDTO actualizarEmpresaContactoEmpresaSupervisada(EmpresaContactoDTO empresaContacto, UsuarioDTO usuario) {
		LOG.info("actulizar EmpresaContacto EmpresaSupervisada");
		EmpresaContactoDTO retorno = new EmpresaContactoDTO();
		try {
			empresaContacto.setEstado(Constantes.ESTADO_ACTIVO);
			EditarEmpresaContactoInpsInRO peticion = new EditarEmpresaContactoInpsInRO();
			peticion.setContacto(empresaContacto);
			peticion.setUsuario(usuario);
			EditarEmpresaContactoInpsOutRO response= empresaContactoEndpoint.editarEmpresaContactoInps(peticion);
			if(response.getCodigoResultado().equals(BaseConstantesOutBean.SUCCESS)){
                if(response.getIdEmpresaContacto()!=null){
                    retorno.setIdEmpresaContacto(response.getIdEmpresaContacto());
                }                
            }
		} catch (Exception e) {
			LOG.error("Error en actualizar Empresa Contacto",e);
		}
		return retorno;
	}

	@Override
	public EmpresaContactoDTO eliminarEmpresaContactoEmpresaSupervisada(EmpresaContactoDTO empresaContacto, UsuarioDTO usuario) {
		LOG.info(":: Id Empresa Contacto ::::--> "+empresaContacto.getIdEmpresaContacto());
		EmpresaContactoDTO retorno = new EmpresaContactoDTO();
		try {
			EliminarEmpresaContactoInpsInRO peticion = new EliminarEmpresaContactoInpsInRO();
			peticion.setIdEmpresaContacto(empresaContacto.getIdEmpresaContacto());
			peticion.setUsuario(usuario);
			EliminarEmpresaContactoInpsOutRO response=empresaContactoEndpoint.eliminarEmpresaContactoInps(peticion);
			LOG.info("Respuesta :--> " +response.getCodigoResultado());
			if(response.getCodigoResultado().equals(BaseConstantesOutBean.SUCCESS)){
                if(response.getIdEmpresaContacto()!=null){
                    retorno.setIdEmpresaContacto(response.getIdEmpresaContacto());
                }                
            }
		} catch (Exception e) {
			LOG.error("Error en eliminar Empresa Contacto",e);
		}
		return retorno;
	}

	@Override
	public EmpresaContactoDTO guardarEmpresaContactoEmpresaSupervisada(EmpresaContactoDTO empresaContacto, UsuarioDTO usuario) {
		LOG.info("Crear Empresa Contacto Empresa Supervisada");
		EmpresaContactoDTO retorno = new EmpresaContactoDTO();
		try {
			GuardarEmpresaContactoInpsInRO peticion = new GuardarEmpresaContactoInpsInRO();
			peticion.setContacto(empresaContacto);
			peticion.setUsuario(usuario);
			GuardarEmpresaContactoInpsOutRO response= empresaContactoEndpoint.guardarEmpresaContactoInps(peticion);
			if(response.getCodigoResultado().equals(BaseConstantesOutBean.SUCCESS)){
                if(response.getIdEmpresaContacto()!=null){
                    retorno.setIdEmpresaContacto(response.getIdEmpresaContacto());
                }                
            }
		} catch (Exception e) {
			LOG.error("Error en crear Direccion Empresa Supervisada",e);
		}
		return retorno;
	}		    
	
}
