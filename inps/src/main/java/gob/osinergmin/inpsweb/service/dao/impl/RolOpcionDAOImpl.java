/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.builder.RolOpcionBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.RolOpcionDAO;
import gob.osinergmin.inpsweb.service.exception.RolOpcionException;
import gob.osinergmin.mdicommon.domain.dto.RolOpcionDTO;
import gob.osinergmin.mdicommon.domain.ui.RolOpcionFilter;
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
@Service("rolOpcionDAO")
public class RolOpcionDAOImpl implements RolOpcionDAO{
    private static final Logger LOG = LoggerFactory.getLogger(RolOpcionDAOImpl.class);
    @Inject
    private CrudDAO crud;
    
    @Override
    public List<RolOpcionDTO> find(RolOpcionFilter filtro) throws RolOpcionException{
        LOG.info("find");
        List<RolOpcionDTO> listado=null;
        try{
            Query query = getFindQuery(filtro);
            
            listado = RolOpcionBuilder.toListRolOpcionDto(query.getResultList());
        }catch(Exception e){
            LOG.info("Error en find",e);
        }
        return listado;
    }
    
    private Query getFindQuery(RolOpcionFilter filtro) {
        Query query=null;
        try{
            if(filtro.getIdentificadorRol()!=null && !filtro.getIdentificadorRol().equals("")){
                query = crud.getEm().createNamedQuery("PghRolOpcion.findByIdentificadorRol");
            }else{
                query = crud.getEm().createNamedQuery("PghRolOpcion.findAll");
            }
            
            if(filtro.getIdentificadorRol()!=null && !filtro.getIdentificadorRol().equals("")){
                query.setParameter("identificadorRol",filtro.getIdentificadorRol());
            }            
        }catch(Exception e){
            LOG.error("Error getFindQuery: "+e.getMessage());
        }
        return query;
    }
}
