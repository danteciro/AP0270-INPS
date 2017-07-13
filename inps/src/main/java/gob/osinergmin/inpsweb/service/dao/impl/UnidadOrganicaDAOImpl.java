/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gob.osinergmin.inpsweb.domain.builder.UnidadOrganicaBuilder;
import gob.osinergmin.inpsweb.service.dao.UnidadOrganicaDAO;
import gob.osinergmin.mdicommon.domain.dto.UnidadOrganicaDTO;
import gob.osinergmin.mdicommon.domain.ui.UnidadDivisionFilter;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.exception.UnidadOrganicaException;
import gob.osinergmin.inpsweb.util.StringUtil;
import gob.osinergmin.mdicommon.domain.ui.UnidadOrganicaFilter;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
*
* @author l_garcia
*/


@Service
@Transactional
public class UnidadOrganicaDAOImpl implements UnidadOrganicaDAO{
	
    private static final Logger LOG = LoggerFactory.getLogger(UnidadOrganicaDAOImpl.class);

    @Inject
    private CrudDAO crud;
	
    @Override
    public List<UnidadOrganicaDTO> findUnidadOrganica(UnidadOrganicaFilter filtro) throws UnidadOrganicaException {
        
        List<UnidadOrganicaDTO> listado=new ArrayList<UnidadOrganicaDTO>();
        try{
            Query query = getFindQuery(filtro);
            listado = UnidadOrganicaBuilder.toListUnidadOrganicaDto(query.getResultList());
        }catch(Exception e){
            LOG.error("Exception en find, locadorServiceImpl");
            LOG.error(e.getMessage());
        }
        return listado;
    }
    
    private Query getFindQuery(UnidadOrganicaFilter filtro) {
        Query query;
        
        if(filtro.getIdUnidadOrganica()!=null){
            query = crud.getEm().createNamedQuery("MdiUnidadOrganica.findByIdUnidadOrganica");
        }else if(filtro.getIdUnidadOrganicaSuperior()!=null){
            query = crud.getEm().createNamedQuery("MdiUnidadOrganica.findByIdUnidadOrganicaSuperior");
        }else{
            query = crud.getEm().createNamedQuery("MdiUnidadOrganica.findAll");
        }
        
        if (filtro.getIdUnidadOrganicaSuperior()!= null) {
            query.setParameter("idUnidadOrganicaSuperior",filtro.getIdUnidadOrganicaSuperior());
        }
        if (filtro.getIdUnidadOrganica()!= null) {
            query.setParameter("idUnidadOrganica",filtro.getIdUnidadOrganica());
        }
                
        return query;
    }
        
}


