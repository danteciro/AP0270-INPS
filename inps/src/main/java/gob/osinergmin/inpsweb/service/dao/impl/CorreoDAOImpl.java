/**
* Resumen		
* Objeto		: ActividadDAOImpl.java
* Descripción		: Clase que contiene la implementación de los métodos declarados en la clase interfaz ActividadDAO
* Fecha de Creación	: 12/05/2016
* PR de Creación	: OSINE_SFS-480
* Autor			: Luis García Reyna
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     12/05/2016      Luis García Reyna           Correo de Alerta a Empresa Supervisora cuando se le asigne Orden de Servicio.
*
*/

package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.builder.CorreoBuilder;
import gob.osinergmin.inpsweb.service.dao.CorreoDAO;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.exception.CorreoException;
import gob.osinergmin.mdicommon.domain.dto.CorreoDTO;
import gob.osinergmin.mdicommon.domain.ui.CorreoFilter;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/* OSINE_SFS-480 - RSIS 12 - Inicio */
@Repository("correoDAO")
public class CorreoDAOImpl implements CorreoDAO {
    private static final Logger LOG = LoggerFactory.getLogger(CorreoDAOImpl.class);  
    
    @Inject
    private CrudDAO crud;
    
    @Override
    public List<CorreoDTO> findByFilter(CorreoFilter filtro) throws CorreoException{
        LOG.info("findByFilter");
        List<CorreoDTO> retorno=null;
        try{
            Query query = getFindQuery(filtro);
            retorno = CorreoBuilder.toListCorreoDto(query.getResultList());
        }catch(Exception e){
            LOG.info("Error en findByFilter",e);
        }
        return retorno;
    }
    
    private Query getFindQuery(CorreoFilter filtro) {
        Query query=null;
        try{
            
            if(filtro.getCodigoFuncionalidad()!=null){
                query = crud.getEm().createNamedQuery("PghCorreo.findByCodigoFuncionalidad");
            }else{
                query = crud.getEm().createNamedQuery("PghCorreo.findAll");
            }
            
            if(filtro.getCodigoFuncionalidad()!=null){
                query.setParameter("codigoFuncionalidad",filtro.getCodigoFuncionalidad());
            }
        
        }catch(Exception e){
            LOG.error("Error getFindQuery: "+e.getMessage());
        }
        return query;
    }
}
/* OSINE_SFS-480 - RSIS 12 - Fin */