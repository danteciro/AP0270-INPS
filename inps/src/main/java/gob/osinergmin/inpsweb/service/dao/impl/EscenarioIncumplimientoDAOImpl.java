/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.builder.EscenarioIncumplimientoBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.EscenarioIncumplimientoDAO;
import gob.osinergmin.inpsweb.service.exception.ComentarioIncumplimientoException;
import gob.osinergmin.mdicommon.domain.dto.EscenarioIncumplimientoDTO;
import gob.osinergmin.mdicommon.domain.ui.EscenarioIncumplimientoFilter;
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
@Service("escenarioIncumplimientoDAO")
public class EscenarioIncumplimientoDAOImpl implements EscenarioIncumplimientoDAO {
    private static final Logger LOG = LoggerFactory.getLogger(EscenarioIncumplimientoDAOImpl.class);
    @Inject
    private CrudDAO crud;
    
    public List<EscenarioIncumplimientoDTO> findByFilter(EscenarioIncumplimientoFilter filtro) throws ComentarioIncumplimientoException{
        LOG.info("findByFilter");
        List<EscenarioIncumplimientoDTO> retorno=null;
        try{
            Query query = getFindQuery(filtro);
            retorno = EscenarioIncumplimientoBuilder.toListEscenarioIncumplimientoDto(query.getResultList());
        }catch(Exception e){
            LOG.error("Error en findByFilter",e);
        }
        return retorno;
    }
    
    private Query getFindQuery(EscenarioIncumplimientoFilter filtro) {
        Query query=null;
        try{
            if(filtro.getIdInfraccion()!=null){
                query = crud.getEm().createNamedQuery("PghEscenarioIncumplimiento.findByIdInfraccion");
            }else if(filtro.getIdEsceIncumplimiento()!=null){
                query = crud.getEm().createNamedQuery("PghEscenarioIncumplimiento.findByIdEsceIncumplimiento");
            }else{
                query = crud.getEm().createNamedQuery("PghEscenarioIncumplimiento.findAll");
            }
            
            if(filtro.getIdInfraccion()!=null){
                query.setParameter("idInfraccion",filtro.getIdInfraccion());
            }        
            if(filtro.getIdEsceIncumplimiento()!=null){
                query.setParameter("idEsceIncumplimiento",filtro.getIdEsceIncumplimiento());
            }        
        }catch(Exception e){
            LOG.error("Error getFindQuery",e);
        }
        return query;
    }
}
