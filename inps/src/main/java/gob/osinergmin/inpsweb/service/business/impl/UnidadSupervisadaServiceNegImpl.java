/**
* Resumen		
* Objeto		: UnidadSupervisadaServiceNegImpl.java
* Descripción		: Clase de la capa de negocio que contiene la implementación de los métodos declarados en la clase interfaz UnidadSupervisadaServiceNeg
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     23/05/2016      Giancarlo Villanueva Andrade        Crear componente de selección de "subtipo de supervisión".Relacionar y adecuar el subtipo de supervisión, el cual deberá depender del tipo de supervisión seleccionado.
* OSINE_MANT_DSHL_003  27/06/2017   Claudio Chaucca Umana::ADAPTER   Agrega el parametro del idPersonal que realiza la busqueda
*/

package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.service.business.UnidadSupervisadaServiceNeg;
import gob.osinergmin.inpsweb.service.dao.MaestroColumnaDAO;
import gob.osinergmin.inpsweb.service.dao.UnidadSupervisadaDAO;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.base.BaseConstantesOutBean;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.DireccionEmpSupDTO;
import gob.osinergmin.mdicommon.domain.dto.DireccionUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.dto.busqueda.BusquedaDireccionxEmpSupervisada;
import gob.osinergmin.mdicommon.domain.dto.busqueda.BusquedaDireccionxUnidadSupervidaDTO;
import gob.osinergmin.mdicommon.domain.dto.busqueda.BusquedaUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.in.BuscarDireccionEmpresaInRO;
import gob.osinergmin.mdicommon.domain.in.BuscarDireccionUnidadInRO;
import gob.osinergmin.mdicommon.domain.in.GuardarDireccionEmpresaInRO;
import gob.osinergmin.mdicommon.domain.in.GuardarDireccionUnidadSupervisadaInRO;
import gob.osinergmin.mdicommon.domain.in.GuardarUnidadSupervisadaInRO;
import gob.osinergmin.mdicommon.domain.in.ObtenerUnidadSupervisadaInRO;
import gob.osinergmin.mdicommon.domain.out.GuardarDireccionEmpresaOutRO;
import gob.osinergmin.mdicommon.domain.out.GuardarDireccionUnidadSupervisadaOutRO;
import gob.osinergmin.mdicommon.domain.out.GuardarUnidadSupervisadaOutRO;
import gob.osinergmin.mdicommon.domain.out.ObtenerDireccionesUnidadSupervisadaOutRO;
import gob.osinergmin.mdicommon.domain.out.ObtenerUnidadSupervisadaOutRO;
import gob.osinergmin.mdicommon.domain.ui.UnidadSupervisadaFilter;
import gob.osinergmin.mdicommon.remote.DireccionEmpSupEndpoint;
import gob.osinergmin.mdicommon.remote.UnidadSupervisadaEndpoint;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jpiro
 */
@Service("unidadSupervisadaServiceNeg")
public class UnidadSupervisadaServiceNegImpl implements UnidadSupervisadaServiceNeg{
    private static final Logger LOG = LoggerFactory.getLogger(UnidadSupervisadaServiceNegImpl.class); 
    
    @Inject
    private UnidadSupervisadaEndpoint unidadSupervisadaEndpoint;
    
    @Inject
    private DireccionEmpSupEndpoint direccionEmpSupEndpoint;
    
    @Inject
    private UnidadSupervisadaDAO unidadSupervisadaDAO;
    
    /* OSINE_SFS-480 - RSIS 26 - Inicio */
    @Inject
    private MaestroColumnaDAO maestroColumnaDAO;
    /* OSINE_SFS-480 - RSIS 26 - Fin */
    //Listar Unidades Supervisadas
    @Override
    public List<UnidadSupervisadaDTO> listarUnidadSupervisada(UnidadSupervisadaFilter filtro){
        LOG.info("listarUnidadSupervisada");
        List<UnidadSupervisadaDTO> retorno=new ArrayList<UnidadSupervisadaDTO>();
        try{
            ObtenerUnidadSupervisadaInRO peticion = new ObtenerUnidadSupervisadaInRO();
            peticion.setFiltroUnidad(filtro);
            ObtenerUnidadSupervisadaOutRO response = unidadSupervisadaEndpoint.buscarUnidadSupervisadaEmpresa(peticion);
            if(response.getRespuesta().getResultado().equals(BaseConstantesOutBean.SUCCESS)){
                if(response.getListUnidadSupervisada().size()>0){
                    retorno=response.getListUnidadSupervisada();
                }                
            }
        }catch(Exception e){
            LOG.error("Error en listarUnidadSupervisada",e);
        }
        return retorno;
    }
    
    //Carga Datos de la unidad Supervisada
    @Override
    public BusquedaUnidadSupervisadaDTO cargaDataUnidadOperativa(UnidadSupervisadaFilter filtro){
        BusquedaUnidadSupervisadaDTO retorno=null;
        
        ObtenerUnidadSupervisadaInRO peticion=new ObtenerUnidadSupervisadaInRO();
        peticion.setFiltroUnidad(filtro);
        ObtenerUnidadSupervisadaOutRO response= unidadSupervisadaEndpoint.buscarUnidadSupervisada(peticion);
        if(response.getRespuesta().getResultado().equals(BaseConstantesOutBean.SUCCESS)){
            if(response.getListUnidadSupervisadabusqueda().size()>0){
                retorno=response.getListUnidadSupervisadabusqueda().get(0);
            } 
        }
        
        return retorno;
    }
    /* OSINE_SFS-480 - RSIS 26 - Inicio */
    @Override
    public List<BusquedaUnidadSupervisadaDTO> cargaDataUnidadOperativaMasiva(UnidadSupervisadaFilter filtro) {
        LOG.info("cargaDataUnidadOperativaMasiva");
        List<BusquedaUnidadSupervisadaDTO> retorno=null;
        try {
        	MaestroColumnaDTO tipoSel=maestroColumnaDAO.findMaestroColumnaByCodigo(Constantes.DOMINIO_TIPO_SEL_ORD_SER, Constantes.APLICACION_INPS, Constantes.CODIGO_SUPERVISION_MUESTRAL_CODIGO_ASIGNACION).get(0);
        	filtro.setTipoSeleccion(tipoSel.getDescripcion());
            retorno = unidadSupervisadaDAO.buscarUnidadSupervisada(filtro);
        } catch (Exception e) {
            LOG.error("Error en cargaDataUnidadOperativaMasiva",e);
        }        
        return retorno;
    }
    /* OSINE_SFS-480 - RSIS26 - Fin */
	
	/* OSINE_MANT_DSHL_003 - Inicio */
    public List<BusquedaUnidadSupervisadaDTO> cargaDataUnidadOperativaMasivaUsuario(UnidadSupervisadaFilter filtro,Long idUsuario) {
        LOG.info("cargaDataUnidadOperativaMasiva");
        List<BusquedaUnidadSupervisadaDTO> retorno=null;
        try {
        	MaestroColumnaDTO tipoSel=maestroColumnaDAO.findMaestroColumnaByCodigo(Constantes.DOMINIO_TIPO_SEL_ORD_SER, Constantes.APLICACION_INPS, Constantes.CODIGO_SUPERVISION_MUESTRAL_CODIGO_ASIGNACION).get(0);
        	filtro.setTipoSeleccion(tipoSel.getDescripcion());
            retorno = unidadSupervisadaDAO.buscarUnidadSupervisadaUsuario(filtro,idUsuario);
        } catch (Exception e) {
            LOG.error("Error en cargaDataUnidadOperativaMasiva",e);
        }        
        return retorno;
    }
	/* OSINE_MANT_DSHL_003 - Fin */
	
    //Obtiene Unidad Supervisada
	@Override
	@Transactional(readOnly=true)
	public BusquedaUnidadSupervisadaDTO obtenerUnidadSupervisada(UnidadSupervisadaFilter filtro) {
		LOG.info("obtenerUnidadSupervisada");
		BusquedaUnidadSupervisadaDTO retorno=new BusquedaUnidadSupervisadaDTO();
        try{
            ObtenerUnidadSupervisadaInRO peticion = new ObtenerUnidadSupervisadaInRO();
            peticion.setFiltroUnidad(filtro);
            ObtenerUnidadSupervisadaOutRO response = unidadSupervisadaEndpoint.buscarUnidadSupervisada(peticion);
            if(response.getRespuesta().getResultado().equals(BaseConstantesOutBean.SUCCESS)){
                if(response.getListUnidadSupervisadabusqueda().size()>0){
                    retorno=response.getListUnidadSupervisadabusqueda().get(0);
                }                
            }
        }catch(Exception e){
            LOG.error("Error en listarUnidadSupervisada",e);
        }
        return retorno;
	}
	
	//Elimina Unidad Supervisada
	@Override
	public UnidadSupervisadaDTO eliminarUnidadSupervisada(UnidadSupervisadaFilter filtro) {
		LOG.info("eliminarUnidadSupervisada");
        UnidadSupervisadaDTO retorno=new UnidadSupervisadaDTO();
        try{
        	GuardarUnidadSupervisadaInRO peticion = new GuardarUnidadSupervisadaInRO();
        	UnidadSupervisadaDTO unidad = new UnidadSupervisadaDTO();
        	unidad.setIdUnidadSupervisada(filtro.getIdUnidadSupervisada());
        	unidad.setEstado(Constantes.ESTADO_INACTIVO);
            peticion.setUnidadSupervisada(unidad);
            GuardarUnidadSupervisadaOutRO response = unidadSupervisadaEndpoint.actualizarUnidadSupervisada(peticion);
            if(response.getCodigoResultado().equals(BaseConstantesOutBean.SUCCESS)){
                if(response.getIdUnidadSupervisada()!=null){
                    retorno.setIdUnidadSupervisada(response.getIdUnidadSupervisada());
                }                
            }
        }catch(Exception e){
            LOG.error("Error en listarUnidadSupervisada",e);
        }
        return retorno;
	}
	
	//Lista Direcciones de Unidad Supervisada
	@Override
	public List<BusquedaDireccionxUnidadSupervidaDTO> listarDireccionUnidadSupervisada(UnidadSupervisadaDTO unidadSupervisadaDTO) {
		LOG.info("listar Direccion Unidad Supervisada :: Id Unidad :--> "+unidadSupervisadaDTO.getIdUnidadSupervisada());
		List<BusquedaDireccionxUnidadSupervidaDTO> retorno = new ArrayList<BusquedaDireccionxUnidadSupervidaDTO>();
		try {
			BuscarDireccionUnidadInRO peticion = new BuscarDireccionUnidadInRO();
			peticion.setIdUnidad(unidadSupervisadaDTO.getIdUnidadSupervisada());
			peticion.setTodos(true);
			ObtenerDireccionesUnidadSupervisadaOutRO response=unidadSupervisadaEndpoint.buscaDireccionUnidadSupervisada(peticion);
			LOG.info("Respuesta :--> " +response.getCodigoResultado());
			if(response.getCodigoResultado().equals(BaseConstantesOutBean.SUCCESS)){
                if(response.getListaDireccionUnidadSupervisada()!=null){
                    retorno = response.getListaDireccionUnidadSupervisada();
                }                
            }
		} catch (Exception e) {
			LOG.error("Error en listar Unidad Supervisada",e);
		}
		return retorno;
	}
	
	//Obtener Direccion de Unidad Supervisada
	@Override
	public BusquedaDireccionxUnidadSupervidaDTO obtenerDireccionUnidadSupervisada(DireccionUnidadSupervisadaDTO direccionUnidadSupervisada) {
		LOG.info("listarDireccionEmpresaSupervisada :: Id Empresa :--> "+direccionUnidadSupervisada.getIdDirccionUnidadSuprvisada());
		BusquedaDireccionxUnidadSupervidaDTO retorno = new BusquedaDireccionxUnidadSupervidaDTO();
		try {
			BuscarDireccionUnidadInRO peticion = new BuscarDireccionUnidadInRO();
			peticion.setIdDireccionUnidad(direccionUnidadSupervisada.getIdDirccionUnidadSuprvisada());
			peticion.setTodos(false);
			ObtenerDireccionesUnidadSupervisadaOutRO response=unidadSupervisadaEndpoint.buscaDireccionUnidadSupervisada(peticion);
			LOG.info("Respuesta :--> " +response.getCodigoResultado());
			if(response.getCodigoResultado().equals(BaseConstantesOutBean.SUCCESS)){
                if(response.getListaDireccionUnidadSupervisada()!=null){
                    retorno = response.getListaDireccionUnidadSupervisada().get(0);
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
	
	//Guardar Direccion de Unidad Supervisada
	@Override
	public DireccionUnidadSupervisadaDTO guardarDireccionUnidadSupervisada(DireccionUnidadSupervisadaDTO direccionUnidad, UsuarioDTO usuario) {
		LOG.info("crearDireccionUnidadSupervisada");
		DireccionUnidadSupervisadaDTO retorno = new DireccionUnidadSupervisadaDTO();
		try {
			GuardarDireccionUnidadSupervisadaInRO peticion = new GuardarDireccionUnidadSupervisadaInRO();
			peticion.setDireccion(direccionUnidad);
			peticion.setUsuario(usuario);
			GuardarDireccionUnidadSupervisadaOutRO response= unidadSupervisadaEndpoint.guardarDireccionUnidadSupervisada(peticion);
			if(response.getCodigoResultado().equals(BaseConstantesOutBean.SUCCESS)){
                if(response.getIdDireccion()!=null){
                    retorno.setIdDirccionUnidadSuprvisada(response.getIdDireccion());
                }                
            }
		} catch (Exception e) {
			LOG.error("Error en crear Direccion Empresa Supervisada",e);
		}
		return retorno;
	}
	
	// Actualiza Direccion Unidad Supervisada
	@Override
	public DireccionUnidadSupervisadaDTO actualizarDireccionUnidadSupervisada(DireccionUnidadSupervisadaDTO direccionUnidad, UsuarioDTO usuario) {
		LOG.info("actulizarDireccionEmpresaSupervisada");
		DireccionUnidadSupervisadaDTO retorno = new DireccionUnidadSupervisadaDTO();
		try {
			direccionUnidad.setEstado(Constantes.ESTADO_ACTIVO);
			GuardarDireccionUnidadSupervisadaInRO peticion = new GuardarDireccionUnidadSupervisadaInRO();
			peticion.setDireccion(direccionUnidad);
			peticion.setUsuario(usuario);
			GuardarDireccionUnidadSupervisadaOutRO response= unidadSupervisadaEndpoint.actualizarDireccionUnidadSupervisada(peticion);
			if(response.getCodigoResultado().equals(BaseConstantesOutBean.SUCCESS)){
                if(response.getIdDireccion()!=null){
                    retorno.setIdDirccionUnidadSuprvisada(response.getIdDireccion());
                }                
            }
		} catch (Exception e) {
			LOG.error("Error en actualizar Direccion Empresa Supervisada",e);
		}
		return retorno;
	}

	@Override
	public DireccionUnidadSupervisadaDTO eliminardireccionUnidadSupervisada(DireccionUnidadSupervisadaDTO direccion, UsuarioDTO usuario) {
		LOG.info("ELiminar Direccion Unidad Supervisada :: Id Empresa :--> "+direccion);
		DireccionUnidadSupervisadaDTO retorno = new DireccionUnidadSupervisadaDTO();
		try {
			GuardarDireccionUnidadSupervisadaInRO peticion = new GuardarDireccionUnidadSupervisadaInRO();
			peticion.setDireccion(direccion);
			peticion.setUsuario(usuario);
			GuardarDireccionUnidadSupervisadaOutRO response=unidadSupervisadaEndpoint.eliminarDireccionUnidadSupervisada(peticion);
			LOG.info("Respuesta :--> " +response.getCodigoResultado());
			if(response.getCodigoResultado().equals(BaseConstantesOutBean.SUCCESS)){
                if(response.getIdDireccion()!=null){
                    retorno.setIdDirccionUnidadSuprvisada(response.getIdDireccion());
                }                
            }
		} catch (Exception e) {
			LOG.error("Error en actualizar Empresa Supervisada",e);
		}
		return retorno;
	}

	@Override
	public UnidadSupervisadaDTO guardarUnidadSupervisada(UnidadSupervisadaDTO unidad, UsuarioDTO usuario) {
		LOG.info("crearDireccionUnidadSupervisada");
		UnidadSupervisadaDTO retorno = new UnidadSupervisadaDTO();
		try {
			GuardarUnidadSupervisadaInRO peticion = new GuardarUnidadSupervisadaInRO();
			peticion.setUnidadSupervisada(unidad);
			peticion.setUsuario(usuario);
			GuardarUnidadSupervisadaOutRO response= unidadSupervisadaEndpoint.guardarUnidadSupervisada(peticion);
			if(response.getCodigoResultado().equals(BaseConstantesOutBean.SUCCESS)){
                if(response.getIdUnidadSupervisada()!=null){
                    retorno.setIdUnidadSupervisada(response.getIdUnidadSupervisada());
                    retorno.setCodigoOsinergmin(response.getCodigoOsinergmin());
                }                
            }
		} catch (Exception e) {
			LOG.error("Error en crear Direccion Empresa Supervisada",e);
		}
		return retorno;
	}
	
	@Override
	public UnidadSupervisadaDTO actualizarUnidadSupervisada(UnidadSupervisadaDTO unidad, UsuarioDTO usuario) {
		LOG.info("crearDireccionUnidadSupervisada");
		UnidadSupervisadaDTO retorno = new UnidadSupervisadaDTO();
		try {
			GuardarUnidadSupervisadaInRO peticion = new GuardarUnidadSupervisadaInRO();
			peticion.setUnidadSupervisada(unidad);
			peticion.setUsuario(usuario);
			GuardarUnidadSupervisadaOutRO response= unidadSupervisadaEndpoint.actualizarUnidadSupervisada(peticion);
			if(response.getCodigoResultado().equals(BaseConstantesOutBean.SUCCESS)){
                if(response.getIdUnidadSupervisada()!=null){
                    retorno.setIdUnidadSupervisada(response.getIdUnidadSupervisada());
                }                
            }
		} catch (Exception e) {
			LOG.error("Error en crear Direccion Empresa Supervisada",e);
		}
		return retorno;
	}

    @Override
    @Transactional
    public List<UnidadSupervisadaDTO> getUnidadSupervisadaDTO(UnidadSupervisadaFilter filtro) {
      LOG.info("getUnidadSupervisadaDTO");
        List<UnidadSupervisadaDTO> retorno = new ArrayList<UnidadSupervisadaDTO>();
        try{        
            retorno=unidadSupervisadaDAO.getUnidadSupervisadaDTO(filtro);
            
        }catch(Exception ex){
            LOG.error("Error en getUnidadSupervisadaDTO",ex);
        }
        return retorno;
    }

    @Override
    public DireccionUnidadSupervisadaDTO buscarDireccUnidSupInps(String codigoOsinergmin){
        LOG.info("buscarDireccUnidSupInps");
        DireccionUnidadSupervisadaDTO retorno=null;
        try{
            MaestroColumnaDTO direUoDl=maestroColumnaDAO.findMaestroColumna(Constantes.DOMINIO_DIRE_INPS_UO, Constantes.APLICACION_INPS).get(0);
            UnidadSupervisadaFilter filtro=new UnidadSupervisadaFilter();
            filtro.setCadCodigosTipoDireccion(direUoDl.getCodigo());
            filtro.setCadCodigoOsinerg(codigoOsinergmin);
            List<BusquedaUnidadSupervisadaDTO> listaBusqueda=cargaDataUnidadOperativaMasiva(filtro);
            BusquedaUnidadSupervisadaDTO registro=null;
            if(listaBusqueda!=null && listaBusqueda.size()>0){ 
                registro=listaBusqueda.get(0);
                retorno=registro.getDireccionUnidadsupervisada();
            }
        }catch(Exception e){
            LOG.error("Error en buscarDireccUnidSup",e);
        }

        return retorno;
    }
}
