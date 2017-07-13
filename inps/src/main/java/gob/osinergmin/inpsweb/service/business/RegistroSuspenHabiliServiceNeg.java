/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.business;

import gob.osinergmin.inpsweb.service.exception.RegistroSuspenHabiliException;
import gob.osinergmin.mdicommon.domain.dto.RegistroSuspenHabiliDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;

/**
 *
 * @author zchaupis
 */
public interface RegistroSuspenHabiliServiceNeg {

    public RegistroSuspenHabiliDTO RegistrarSuspensionRegistroHidrocarburo(RegistroSuspenHabiliDTO registroSuspenHabiliDTO,UsuarioDTO usuarioDTO) throws RegistroSuspenHabiliException;
}
