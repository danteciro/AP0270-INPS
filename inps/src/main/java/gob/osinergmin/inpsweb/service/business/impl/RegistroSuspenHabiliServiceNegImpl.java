/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.service.business.RegistroSuspenHabiliServiceNeg;
import gob.osinergmin.inpsweb.service.dao.RegistroSuspenHabiliDAO;
import gob.osinergmin.inpsweb.service.exception.RegistroSuspenHabiliException;
import gob.osinergmin.mdicommon.domain.dto.RegistroSuspenHabiliDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author zchaupis
 */
@Service("RegistroSuspenHabiliServiceNeg")
public class RegistroSuspenHabiliServiceNegImpl implements RegistroSuspenHabiliServiceNeg{

    private static Logger LOG = LoggerFactory.getLogger(RegistroSuspenHabiliServiceNegImpl.class);
    @Inject
    private RegistroSuspenHabiliDAO registroSuspenHabiliDAO;
    
    @Override
    @Transactional(rollbackFor = RegistroSuspenHabiliException.class)
    public RegistroSuspenHabiliDTO RegistrarSuspensionRegistroHidrocarburo(RegistroSuspenHabiliDTO registroSuspenHabiliDTO,UsuarioDTO usuarioDTO)throws RegistroSuspenHabiliException {
      LOG.info("RegistrarSuspensionRegistroHidrocarburo");
        RegistroSuspenHabiliDTO retorno=new RegistroSuspenHabiliDTO();
        try {
            retorno=registroSuspenHabiliDAO.RegistrarSuspensionRegistroHidrocarburo(registroSuspenHabiliDTO,usuarioDTO);  
        } catch (Exception ex) {
            LOG.error("Error en RegistrarSuspensionRegistroHidrocarburo", ex);
            throw new RegistroSuspenHabiliException(ex.getMessage(), ex);
        }
        return retorno; 
    }
    
}
