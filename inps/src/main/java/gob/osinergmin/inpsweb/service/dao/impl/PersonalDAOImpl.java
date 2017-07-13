/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.builder.PersonalBuilder;
import gob.osinergmin.mdicommon.domain.ui.PersonalFilter;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.PersonalDAO;
import gob.osinergmin.inpsweb.service.exception.PersonalException;
import gob.osinergmin.mdicommon.domain.dto.PersonalDTO;

//htorress - RSIS 3 - Inicio
import java.util.ArrayList;
import java.util.Arrays;
//htorress - RSIS 3 - Fin
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
@Service("personalDAO")
public class PersonalDAOImpl implements PersonalDAO {
    private static final Logger LOG = LoggerFactory.getLogger(PersonalDAOImpl.class);  
    @Inject
    private CrudDAO crud;
    
    @Override
    public List<PersonalDTO> find(PersonalFilter filtro) throws PersonalException{
        LOG.info("DAO find");
        List<PersonalDTO> listado=null;
        try{
            Query query = getFindQuery(filtro);
            listado = PersonalBuilder.toListPersonalDto(query.getResultList());
        }catch(Exception e){
            LOG.info("Error en find",e);
        }
        return listado;
    }
    
    private Query getFindQuery(PersonalFilter filtro) {
        Query query=null;
        try{
            if(filtro.getNombreUsuarioSiged()!=null && !filtro.getNombreUsuarioSiged().equals("")){
                query = crud.getEm().createNamedQuery("PghPersonal.findByNombreUsuarioSiged");
            }else if(filtro.getDescRol()!=null && !filtro.getDescRol().equals("")
                && filtro.getNombreUsuarioSiged()==null){
                query = crud.getEm().createNamedQuery("PghPersonal.findByDescRol");
            }else if(filtro.getIdLocador()!=null){
                query = crud.getEm().createNamedQuery("PghPersonal.findByIdLocador");
            }else if(filtro.getIdSupervisoraEmpresa()!=null){
                query = crud.getEm().createNamedQuery("PghPersonal.findByIdSupervisoraEmpresa");
            }else if(filtro.getFlagDefault()!=null && filtro.getIdPersonal()!=null){
                query = crud.getEm().createNamedQuery("PghPersonal.findByIdPersonalUnidOrgFlagDefault");
            }else if(filtro.getIdPersonal()!=null){
                query = crud.getEm().createNamedQuery("PghPersonal.findByIdPersonal");
            }else{
                query = crud.getEm().createNamedQuery("PghPersonal.findAll");
            }
            
            if(filtro.getNombreUsuarioSiged()!=null && !filtro.getNombreUsuarioSiged().equals("")){
                query.setParameter("nombreUsuarioSiged",filtro.getNombreUsuarioSiged());
            }
            if(filtro.getDescRol()!=null && !filtro.getDescRol().equals("")){
            	// htorress - RSIS 3 - Inicio
            	int pos = filtro.getDescRol().indexOf(",");
            	if(pos==-1){
            	// htorress - RSIS 3 - Fin
                query.setParameter("descRol",filtro.getDescRol());
                // htorress - RSIS 3 - Inicio
            	}else{
            		String[] rol = filtro.getDescRol().split(",");
            		ArrayList<Long> roles = new ArrayList(Arrays.asList(rol[0], rol[1]));
            		query.setParameter("descRol",roles);
            	}
                // htorress - RSIS 3 - Fin
            }            
            if(filtro.getIdLocador()!=null){
                query.setParameter("idLocador",filtro.getIdLocador());
            }
            if(filtro.getIdSupervisoraEmpresa()!=null){
                query.setParameter("idSupervisoraEmpresa",filtro.getIdSupervisoraEmpresa());
            }
            if(filtro.getIdPersonal()!=null){
                query.setParameter("idPersonal",filtro.getIdPersonal());
            }
            if(filtro.getFlagDefault()!=null){
                query.setParameter("flagDefault",filtro.getFlagDefault());
            }
            
        }catch(Exception e){
            LOG.error("Error getFindQuery: "+e.getMessage());
        }
        return query;
    }
}
