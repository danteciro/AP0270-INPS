/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.business;

import gob.osinergmin.mdicommon.domain.dto.PersonalAsignadoDTO;

import java.util.List;

/**
 *
 * @author htorress
 */
public interface PersonalAsignadoServiceNeg {
    public List<PersonalAsignadoDTO> findPersonalAsignado(PersonalAsignadoDTO filtro);
}
