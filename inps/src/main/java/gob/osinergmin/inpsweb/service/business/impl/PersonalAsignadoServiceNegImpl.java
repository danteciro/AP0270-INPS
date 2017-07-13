/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.service.business.PersonalAsignadoServiceNeg;
import gob.osinergmin.inpsweb.service.dao.PersonalAsignadoDAO;
import gob.osinergmin.mdicommon.domain.dto.PersonalAsignadoDTO;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author htorress
 */
@Service("personalAsignadoServiceNeg")
public class PersonalAsignadoServiceNegImpl implements PersonalAsignadoServiceNeg{
    private static final Logger LOG = LoggerFactory.getLogger(PersonalAsignadoServiceNegImpl.class);    
    @Inject
    private PersonalAsignadoDAO personalAsignadoDAO;
    
    @Override
    @Transactional(readOnly = true)
    public List<PersonalAsignadoDTO> findPersonalAsignado(PersonalAsignadoDTO filtro){
        LOG.info("Neg findPersonal");
        List<PersonalAsignadoDTO> retorno=new ArrayList<PersonalAsignadoDTO>();
        try{
            retorno=personalAsignadoDAO.find(filtro);
        }catch(Exception ex){
            LOG.error("Error en findPersonal",ex);
        }
        return retorno;
    }

}
