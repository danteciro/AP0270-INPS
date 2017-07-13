/**
* Resumen		
* Objeto		: ActividadDAOImpl.java
* Descripción		: Clase que contiene la implementación de los métodos declarados en la clase interfaz ActividadDAO
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     09/05/2016      Mario Dioses Fernandez          Registrar en BD campo FECHA_INICIO_PLAZO y FECHA_FIN_PLAZO luego de CONCLUIR_OS, considerando Plazo_Descargo por Rubro (MDI_ACTIVIDAD)
*
*/

package gob.osinergmin.inpsweb.service.dao.impl;
import gob.osinergmin.inpsweb.domain.builder.ActividadBuilder;
import gob.osinergmin.inpsweb.service.dao.ActividadDAO;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.mdicommon.domain.dto.ActividadDTO;
import gob.osinergmin.mdicommon.domain.ui.ActividadFilter;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author jpiro
 */
@Service("ActividadDAO")
public class ActividadDAOImpl implements ActividadDAO {
    private static final Logger LOG = LoggerFactory.getLogger(ActividadDAO.class);

    @Inject
    private CrudDAO crud;
    
	@Override
	public List<ActividadDTO> find() {
		List<ActividadDTO> listado;
		Query query = crud.getEm().createNamedQuery("MdiActividad.findAll");
		listado = ActividadBuilder.toListActividadDto(query.getResultList());
		return listado;
	}
	/* OSINE_SFS-480 - RSIS 17 - Inicio */
	@Override
	public List<ActividadDTO> findBy(ActividadFilter filtro) { 
		Query query=null;
		List<ActividadDTO> listado=null;
        try{
            if(filtro.getIdActividad()!=null){
                query = crud.getEm().createNamedQuery("MdiActividad.findByIdActividad");
            }
            if(filtro.getIdActividad()!=null){
                query.setParameter("idActividad",filtro.getIdActividad());
            }      
            listado = ActividadBuilder.toListActividadDto(query.getResultList());
            return listado;
        }catch(Exception e){
            LOG.error("Error getFindQuery: "+e.getMessage());
        }
		return listado;		
	} 
        /* OSINE_SFS-480 - RSIS 17 - Fin */
}