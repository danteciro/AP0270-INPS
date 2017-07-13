/**
* Resumen		
* Objeto		: MaestroColumnaServiceNegImpl.java
* Descripción		: Clase de la capa de negocio que contiene la implementación de los métodos declarados en la clase interfaz MaestroColumnaServiceNeg
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     20/05/2016      Hernán Torres Saenz             Crear la opción "Anular orden de servicio" en la pestaña asigaciones  de la bandeja del especialista el cual direccionará a la interfaz "Anular orden de servicio"
* OSINE_SFS-791     06/10/2016      Mario Dioses Fernandez          Crear la funcionalidad "verificar levantamientos" que permita verificar el tipo de asignación para la orden de levantamiento DSR-CRITICIDAD.
*
*/

package gob.osinergmin.inpsweb.service.business.impl;
import gob.osinergmin.inpsweb.service.business.MaestroColumnaServiceNeg;
import gob.osinergmin.inpsweb.service.dao.MaestroColumnaDAO;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.base.BaseConstantesOutBean;
import gob.osinergmin.mdicommon.domain.in.ObtenerMaestrosColumnaInRO;
import gob.osinergmin.mdicommon.domain.out.ObtenerMaestrosColumnaOutRO;
import gob.osinergmin.mdicommon.remote.MaestroColumnaEndpoint;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;

import java.util.ArrayList;
import java.util.Calendar;
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
@Service("maestroColumnaServiceNeg")
public class MaestroColumnaServiceNegImpl implements MaestroColumnaServiceNeg {
    private static final Logger LOG = LoggerFactory.getLogger(MaestroColumnaServiceNegImpl.class);
    
    @Inject
    private MaestroColumnaDAO maestroColumnaDAO;
    
    @Inject
    private MaestroColumnaEndpoint maestroColumnaEndpoint;
    
    @Override
    @Transactional(readOnly=true)
    public List<MaestroColumnaDTO> findByDominioAplicacionCodigo(String dominio,String aplicacion,String codigo){
        List<MaestroColumnaDTO>  retorno = null; 	
    	try{    		
            retorno = maestroColumnaDAO.findMaestroColumna(dominio, aplicacion,codigo);    		
    	}catch(Exception ex){    		
            LOG.info("error buscarByDominioAplicacionCodigo",ex);
    	}    	    
    	return retorno;
    }
    
    @Override
    @Transactional(readOnly=true)
    public List<MaestroColumnaDTO> findByDominioAplicacion(String dominio,String aplicacion){
        List<MaestroColumnaDTO>  retorno = null; 	
    	try{    		
            retorno = maestroColumnaDAO.findMaestroColumna(dominio, aplicacion);    		
    	}catch(Exception ex){    		
            LOG.info("error findByDominioAplicacion",ex);
    	}    	    
    	return retorno;
    }
    
    /* OSINE_SFS-480 - RSIS 41 - Inicio */
    @Override
    @Transactional(readOnly=true)
    public List<MaestroColumnaDTO> findByIdMaestroColumna(Long idMaestroColumna){
        List<MaestroColumnaDTO>  retorno = null; 	
    	try{    		
            retorno = maestroColumnaDAO.findByIdMaestroColumna(idMaestroColumna);    		
    	}catch(Exception ex){    		
            LOG.info("error buscarByDominioAplicacionCodigo",ex);
    	}    	    
    	return retorno;
    }
    /* OSINE_SFS-480 - RSIS 41 - Fin */
    
	@Override
	public List<MaestroColumnaDTO> buscarTodos(String dominio) {
		List<MaestroColumnaDTO> listaColumna = null;
		try{
			LOG.info("inicio buscarTodos");
			ObtenerMaestrosColumnaInRO peticion = new ObtenerMaestrosColumnaInRO();
			peticion.setDominio(dominio);
			peticion.setAplicacion(Constantes.APLICACION_SGLSS);
			ObtenerMaestrosColumnaOutRO response = maestroColumnaEndpoint.obtenerMaestrosColumna(peticion);
			if(response!=null && BaseConstantesOutBean.SUCCESS.equals(response.getCodigoResultado())){
				listaColumna = response.getListaColumnas();
			}
			LOG.info("fin buscarTodos");
		}catch(Exception ex){
			LOG.error("error buscarTodos", ex);
			ex.printStackTrace();
		}		
		return listaColumna;
	}

	@Override
	public List<MaestroColumnaDTO>  buscarByDominioByAplicacionByCodigo(
			String dominio, String aplicacion,
			String codigo) {
		List<MaestroColumnaDTO>  retorno = null; 	
    	try{    		
            retorno = maestroColumnaDAO.findMaestroColumnaByCodigo(dominio,aplicacion,codigo);    		
    	}catch(Exception ex){    		
    		LOG.info("error web service",ex);
    	}    	    
    	return retorno;
	}
	
	/* OSINE_SFS-791 - RSIS 47 - Inicio */ 
	@Override
	public List<MaestroColumnaDTO> findMaestroColumnaByDominioAplicDesc(String dominio, String aplicacion, String descripcion) {
		List<MaestroColumnaDTO>  retorno = null; 
		try{    		
            retorno = maestroColumnaDAO.findMaestroColumnaByDominioAplicDesc(dominio,aplicacion,descripcion);    		
    	}catch(Exception ex){    		
    		LOG.info("error web service",ex);
    	}    	    
    	return retorno;
	}
	/* OSINE_SFS-791 - RSIS 47 - Fin */ 
        /* OSINE_SFS-791 - RSIS 41 - Inicio */ 
	@Override
	public List<MaestroColumnaDTO> findMaestroColumnaByCodigo(String dominio, String aplicacion, String codigo) {
		List<MaestroColumnaDTO>  retorno = null; 
		try{    		
            retorno = maestroColumnaDAO.findMaestroColumnaByCodigo(dominio,aplicacion,codigo);    		
    	}catch(Exception ex){    		
    		LOG.info("error web service",ex);
    	}    	    
    	return retorno;
	}
	/* OSINE_SFS-791 - RSIS 41 - Fin */ 
	/* OSINE_SFS-1063 - RSIS 10 - Inicio */
@Override
	@Transactional(readOnly=true)
	public List<MaestroColumnaDTO> findByDominioAplicacionDescripcion(String dominio, String aplicacion, String descripcion) {
		List<MaestroColumnaDTO>  retorno = new ArrayList<MaestroColumnaDTO>(); 	
    	try{    		
            retorno = maestroColumnaDAO.findMaestroColumnaByDescripcion(dominio, aplicacion,descripcion);    		
    	}catch(Exception ex){    		
            LOG.info("error buscarByDominioAplicacionCodigo",ex);
    	}    	    
    	return retorno;
	}
	/* OSINE_SFS-1063 - RSIS 10 - Fin */
}
