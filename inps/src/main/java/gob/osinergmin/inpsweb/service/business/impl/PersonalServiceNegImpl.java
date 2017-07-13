/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.service.business.HistoricoEstadoServiceNeg;
import gob.osinergmin.mdicommon.domain.ui.PersonalFilter;
import gob.osinergmin.inpsweb.service.business.PersonalServiceNeg;
import gob.osinergmin.inpsweb.service.dao.PersonalDAO;
import gob.osinergmin.mdicommon.domain.dto.HistoricoEstadoDTO;
import gob.osinergmin.mdicommon.domain.dto.PersonalDTO;
import gob.osinergmin.mdicommon.domain.ui.HistoricoEstadoFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jpiro
 */
@Service("personalServiceNeg")
public class PersonalServiceNegImpl implements PersonalServiceNeg{
    private static final Logger LOG = LoggerFactory.getLogger(PersonalServiceNegImpl.class);    
    @Inject
    private PersonalDAO personalDAO;
    @Inject
    private HistoricoEstadoServiceNeg historicoEstadoServiceNeg;
    
    @Override
    @Transactional(readOnly = true)
    public List<PersonalDTO> findPersonal(PersonalFilter filtro){
        LOG.info("Neg findPersonal");
        List<PersonalDTO> retorno=new ArrayList<PersonalDTO>();
        try{
            retorno=personalDAO.find(filtro);
        }catch(Exception ex){
            LOG.error("Error en findPersonal",ex);
        }
        return retorno;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PersonalDTO obtenerUsuarioOrigen(Long idOrdenServicio){
    	LOG.info("obtenerUsuarioOrigen");
    	PersonalDTO usuarioSiged= new PersonalDTO();
    	
    	HistoricoEstadoFilter filtro = new HistoricoEstadoFilter();
        filtro.setIdOrdenServicio(idOrdenServicio);
        List<HistoricoEstadoDTO> listado=historicoEstadoServiceNeg.listarHistoricoEstado(filtro);
        
        Collections.sort(listado, new Comparator<HistoricoEstadoDTO>(){
        	@Override
        	public int compare(HistoricoEstadoDTO hist1, HistoricoEstadoDTO hist2) {                    		
        		return hist1.getFechaCreacion().compareTo(hist2.getFechaCreacion());
        	}});
        Collections.reverse(listado);
        List<PersonalDTO> destinatario = findPersonal(new PersonalFilter(listado.get(0).getPersonalOri().getIdPersonal()));
        usuarioSiged=(PersonalDTO)destinatario.get(0);

    	return usuarioSiged;
    }
}
