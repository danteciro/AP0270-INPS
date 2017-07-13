/**
* Resumen		
* Objeto		: MaestroColumnaDAOImpl.java
* Descripción		: Clase que contiene la implementación de los métodos declarados en la clase interfaz MaestroColumnaDAO
* Fecha de Creación	: 25/03/2015 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     20/05/2016      Hernán Torres Saenz         Crear la opción "Anular orden de servicio" en la pestaña asigaciones  de la bandeja del especialista el cual direccionará a la interfaz "Anular orden de servicio"
* 
*/

package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.MdiMaestroColumna;
import gob.osinergmin.inpsweb.domain.builder.MaestroColumnaBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.MaestroColumnaDAO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jpiro
 */
@Service("maestroColumnaDAO")
public class MaestroColumnaDAOImpl implements MaestroColumnaDAO {
    private static final Logger LOG = LoggerFactory.getLogger(MaestroColumnaDAOImpl.class);
    @Inject
    private CrudDAO crud;
    
    @Override
    public List<MaestroColumnaDTO> findMaestroColumna(String dominio, String aplicacion, String codigo){
        LOG.info("findMaestroColumna");
        List<MaestroColumnaDTO> listaMaestroColumnaDTO = null;
        try {                	
            List<MdiMaestroColumna> listaMaestroColumna = null;
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("dominio", dominio);
            parameters.put("aplicacion", aplicacion);
            parameters.put("codigo", codigo);
            listaMaestroColumna = crud.findByNamedQuery("MdiMaestroColumna.findByDominioAplicacionCodigo", parameters);       	
            listaMaestroColumnaDTO = MaestroColumnaBuilder.getMaestroColumnaDTOList(listaMaestroColumna);
        } catch (Exception e) {
            LOG.error("error en findMaestroColumna", e);
        }
        return listaMaestroColumnaDTO;
    }
    
    @Override
    public List<MaestroColumnaDTO> findMaestroColumna(String dominio, String aplicacion){
        LOG.info("findMaestroColumna");
        List<MaestroColumnaDTO> listaMaestroColumnaDTO = null;
        try {                	
            List<MdiMaestroColumna> listaMaestroColumna = null;
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("dominio", dominio);
            parameters.put("aplicacion", aplicacion);
            listaMaestroColumna = crud.findByNamedQuery("MdiMaestroColumna.findByDominioAplicacion", parameters);       	
            listaMaestroColumnaDTO = MaestroColumnaBuilder.getMaestroColumnaDTOList(listaMaestroColumna);
        } catch (Exception e) {
            LOG.error("error en findMaestroColumna", e);
        }
        return listaMaestroColumnaDTO;
    }
    
    /* OSINE_SFS-480 - RSIS 41 - Inicio */
    @Override
    public List<MaestroColumnaDTO> findByIdMaestroColumna(Long idMaestroColumna){
        LOG.info("findMaestroColumna");
        List<MaestroColumnaDTO> listaMaestroColumnaDTO = null;
        try {                	
            List<MdiMaestroColumna> maestroColumna = null;
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("idMaestroColumna", idMaestroColumna);
            maestroColumna = crud.findByNamedQuery("MdiMaestroColumna.findByIdMaestroColumna", parameters);       	
            listaMaestroColumnaDTO = MaestroColumnaBuilder.getMaestroColumnaDTOList(maestroColumna);
        } catch (Exception e) {
            LOG.error("error en findMaestroColumna", e);
        }
        return listaMaestroColumnaDTO;
    }
    /* OSINE_SFS-480 - RSIS 41 - Fin */

    @Override
    @Transactional(readOnly=true)
    public List<MaestroColumnaDTO> findMaestroColumnaByCodigo(String dominio, String aplicacion, String codigo){
                    LOG.info("entro findMaestroColumna");
            List<MaestroColumnaDTO> listaMaestroColumnaDTO = null;
            try {                	
                    List<MdiMaestroColumna> listaMaestroColumna = null;
                    Map<String, Object> parameters = new HashMap<String, Object>();
                    parameters.put("dominio", dominio);
                    parameters.put("aplicacion", aplicacion);
                    parameters.put("codigo", codigo);
                    listaMaestroColumna = crud.findByNamedQuery("MdiMaestroColumna.findByDominioAplicacionCodigo", parameters);       	
                    listaMaestroColumnaDTO = MaestroColumnaBuilder.getMaestroColumnaDTOList(listaMaestroColumna);
            } catch (Exception e) {  
                    LOG.error("error", e);
                    e.printStackTrace();
            }
            return listaMaestroColumnaDTO;
        }
    
    @Override
    public List<MaestroColumnaDTO> findMaestroColumnaByDominioAplicDesc(String dominio, String aplicacion, String descripcion){
        LOG.info("findMaestroColumna");
        List<MaestroColumnaDTO> listaMaestroColumnaDTO = null;
        try {                	
            List<MdiMaestroColumna> listaMaestroColumna = null;
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("dominio", dominio);
            parameters.put("aplicacion", aplicacion);
            parameters.put("descripcion", descripcion);
            listaMaestroColumna = crud.findByNamedQuery("MdiMaestroColumna.findByDominioAplicacionDescripcion", parameters);       	
            listaMaestroColumnaDTO = MaestroColumnaBuilder.getMaestroColumnaDTOList(listaMaestroColumna);
        } catch (Exception e) {
            LOG.error("error en findMaestroColumna", e);
        }
        return listaMaestroColumnaDTO;
    }
    /* OSINE_SFS-1063 - RSIS 10 - Inicio */
	@Override
	public List<MaestroColumnaDTO> findMaestroColumnaByDescripcion(String dominio, String aplicacion, String descripcion) {
	       LOG.info("entro findMaestroColumna");
           List<MaestroColumnaDTO> listaMaestroColumnaDTO = null;
           try {                	
                   List<MdiMaestroColumna> listaMaestroColumna = null;
                   Map<String, Object> parameters = new HashMap<String, Object>();
                   parameters.put("dominio", dominio);
                   parameters.put("aplicacion", aplicacion);
                   parameters.put("descripcion", descripcion);
                   listaMaestroColumna = crud.findByNamedQuery("MdiMaestroColumna.findByDominioAplicacionDescripcion", parameters);       	
                   listaMaestroColumnaDTO = MaestroColumnaBuilder.getMaestroColumnaDTOList(listaMaestroColumna);
           } catch (Exception e) {  
                   LOG.error("error", e);
                   e.printStackTrace();
           }
           return listaMaestroColumnaDTO;
	}
	/* OSINE_SFS-1063 - RSIS 10 - Fin */
}
