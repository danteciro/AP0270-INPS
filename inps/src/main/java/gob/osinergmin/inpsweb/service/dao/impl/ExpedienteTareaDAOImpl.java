/**
* Resumen		
* Objeto			: ExpedienteTareaDAOImpl.java
* Descripción		: Clase DAOImpl ExpedienteTareaDAOImpl
* Fecha de Creación	: 23/10/2016
* PR de Creación	: OSINE_SFS-791
* Autor				: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------				
* OSINE_SFS-791     23/10/2016      Mario Dioses Fernandez      Crear la tarea automática que notifique vía correo que se debe elaborar el oficio por obligaciones incumplidas sin subsanar
* OSINE_SFS-791     23/10/2016      Mario Dioses Fernandez      Crear la tarea automática que cancele el registro de hidrocarburos
* 
*/ 
package gob.osinergmin.inpsweb.service.dao.impl;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import gob.osinergmin.inpsweb.domain.PghExpedienteTarea;
import gob.osinergmin.inpsweb.domain.builder.ExpedienteTareaBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.ExpedienteTareaDAO;
import gob.osinergmin.inpsweb.service.exception.ExpedienteTareaException;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteTareaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.ExpedienteTareaFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("ExpedienteTareaDAO")
public class ExpedienteTareaDAOImpl implements ExpedienteTareaDAO {
private static final Logger log = LoggerFactory.getLogger(ExpedienteTareaDAOImpl.class);
	
	@Inject
    private CrudDAO crud;
	
	@Override	
	public List<ExpedienteTareaDTO> find(ExpedienteTareaFilter filtro) throws ExpedienteTareaException {
		log.info("inicio find");
		Query query=null;
		List<ExpedienteTareaDTO> listado=null;
        try {
            if(filtro.getIdExpediente()!=null){
                query = crud.getEm().createNamedQuery("PghExpedienteTarea.findByIdExpediente");
            }else {
            	query = crud.getEm().createNamedQuery("PghExpedienteTarea.findAll");
            }        
            
            if(filtro.getIdExpediente()!=null){
    			query.setParameter("idExpediente", filtro.getIdExpediente());
    		}
            
            listado = ExpedienteTareaBuilder.toListExpedienteTareaDto(query.getResultList());
        } catch(Exception e){
            log.error("Error find: " + e.getMessage());
            throw new ExpedienteTareaException(e);
        }
		return listado;
	}

	@Override	
	public ExpedienteTareaDTO registrar(ExpedienteTareaDTO ExpedienteTarea, UsuarioDTO UsuarioDTO) throws ExpedienteTareaException {
		log.info("inicio registrar");
		try{
			PghExpedienteTarea pghExpedienteTarea=ExpedienteTareaBuilder.toExpedienteTareaDomain(ExpedienteTarea);
			pghExpedienteTarea.setDatosAuditoria(UsuarioDTO);
			pghExpedienteTarea=crud.create(pghExpedienteTarea);
			ExpedienteTarea=ExpedienteTareaBuilder.tofindExpedienteTareaDto(pghExpedienteTarea);
			log.info("fin registrar");
		} catch(Exception e){
			log.error("error registrar", e.getMessage());
			throw new ExpedienteTareaException(e);
		}
		return ExpedienteTarea;
	}

	@Override
	public ExpedienteTareaDTO actualizar(ExpedienteTareaDTO ExpedienteTarea, UsuarioDTO UsuarioDTO) throws ExpedienteTareaException {
		log.info("inicio actualizar");
		try{
			PghExpedienteTarea PghExpedienteTarea=ExpedienteTareaBuilder.toExpedienteTareaDomain(ExpedienteTarea);
			PghExpedienteTarea.setDatosAuditoria(UsuarioDTO);
			PghExpedienteTarea=crud.update(PghExpedienteTarea);
			ExpedienteTarea=ExpedienteTareaBuilder.tofindExpedienteTareaDto(PghExpedienteTarea);
			log.info("fin actualizar");
		} catch(Exception e){
			log.error("error actualizar", e);
			throw new ExpedienteTareaException(e);
		}
		return ExpedienteTarea;
	}
}
