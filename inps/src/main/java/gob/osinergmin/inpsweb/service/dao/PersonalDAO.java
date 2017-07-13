/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.mdicommon.domain.ui.PersonalFilter;
import gob.osinergmin.inpsweb.service.exception.PersonalException;
import gob.osinergmin.mdicommon.domain.dto.PersonalDTO;

import java.util.List;

/**
 *
 * @author jpiro
 */
public interface PersonalDAO {
    public List<PersonalDTO> find(PersonalFilter filtro) throws PersonalException;    
}
