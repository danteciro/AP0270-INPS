/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.mdicommon.domain.dto.PersonalAsignadoDTO;
import gob.osinergmin.inpsweb.service.exception.PersonalException;

import java.util.List;

/**
 *
 * @author htorress
 */
public interface PersonalAsignadoDAO {
    public List<PersonalAsignadoDTO> find(PersonalAsignadoDTO filtro) throws PersonalException;    
}
