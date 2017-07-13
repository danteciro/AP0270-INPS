/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.builder.PersonalAsignadoBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.PersonalAsignadoDAO;
import gob.osinergmin.inpsweb.service.exception.PersonalException;
import gob.osinergmin.mdicommon.domain.dto.PersonalAsignadoDTO;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author htorress
 */
@Service("personalAsignadoDAO")
public class PersonalAsignadoDAOImpl implements PersonalAsignadoDAO {
    private static final Logger LOG = LoggerFactory.getLogger(PersonalAsignadoDAOImpl.class);  
    @Inject
    private CrudDAO crud;
    
    @Override
    public List<PersonalAsignadoDTO> find(PersonalAsignadoDTO filtro) throws PersonalException{
        LOG.info("DAO find");
        List<PersonalAsignadoDTO> listado=null;
        try{
            Query query = getFindQuery(filtro);
            listado = PersonalAsignadoBuilder.toListPersonalAsignadoDTO(query.getResultList());
        }catch(Exception e){
            LOG.info("Error en find",e);
        }
        return listado;
    }
    
    private Query getFindQuery(PersonalAsignadoDTO filtro) {
        Query query=null;
        try{
            if(filtro.getIdPersonalJefe().getIdPersonal()!=null && !filtro.getIdPersonalJefe().getIdPersonal().equals("")){
                query = crud.getEm().createNamedQuery("PghPersonalAsignado.findByIdentificadorJefe");
            }else if(filtro.getIdPersonalSubordinado().getIdPersonal()!=null && !filtro.getIdPersonalSubordinado().getIdPersonal().equals("")){
                query = crud.getEm().createNamedQuery("PghPersonalAsignado.findByIdentificadorSubordinado");
            }else{
                query = crud.getEm().createNamedQuery("PghPersonalAsignado.findAll");
            }

            if(filtro.getIdPersonalJefe().getIdPersonal()!=null){
                query.setParameter("identificadorJefe",filtro.getIdPersonalJefe().getIdPersonal());
            }
            
            if(filtro.getIdPersonalSubordinado().getIdPersonal()!=null){
                query.setParameter("identificadorSubordinado",filtro.getIdPersonalSubordinado().getIdPersonal());
            }
            
        }catch(Exception e){
            LOG.error("Error getFindQuery: "+e.getMessage());
        }
        return query;
    }

}
