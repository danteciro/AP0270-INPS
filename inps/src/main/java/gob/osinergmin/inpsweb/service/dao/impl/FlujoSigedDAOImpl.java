/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.builder.FlujoSigedBuilder;
import gob.osinergmin.mdicommon.domain.ui.FlujoSigedFilter;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.FlujoSigedDAO;
import gob.osinergmin.inpsweb.service.exception.FlujoSigedException;
import gob.osinergmin.mdicommon.domain.dto.FlujoSigedDTO;

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
@Service("flujoSigedDAO")
public class FlujoSigedDAOImpl implements FlujoSigedDAO{
    private static final Logger LOG = LoggerFactory.getLogger(ExpedienteDAOImpl.class);    
    @Inject
    private CrudDAO crud;
    
    @Override
    public List<FlujoSigedDTO> find(FlujoSigedFilter filtro) throws FlujoSigedException {
        List<FlujoSigedDTO> listado;
        
        Query query = getFindQuery(filtro);
        listado = FlujoSigedBuilder.toListFlujoSigedDto(query.getResultList());

        return listado;
    }
    
    private Query getFindQuery(FlujoSigedFilter filtro) {
        Query query=null;
        try{
            query = crud.getEm().createNamedQuery("PghFlujoSiged.findAll");
            
        }catch(Exception e){
            LOG.error("Error getFindQuery: "+e.getMessage());
        }
        return query;
    }
    
}
