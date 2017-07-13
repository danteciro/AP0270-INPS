/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.inpsweb.service.exception.RegistroSuspenHabiliException;
import gob.osinergmin.mdicommon.domain.dto.RegistroSuspenHabiliDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;

/**
 *
 * @author zchaupis
 */
public interface RegistroSuspenHabiliDAO {

    public RegistroSuspenHabiliDTO RegistrarSuspensionRegistroHidrocarburo(RegistroSuspenHabiliDTO registroSuspenHabiliDTO, UsuarioDTO usuarioDTO) throws RegistroSuspenHabiliException;
}
