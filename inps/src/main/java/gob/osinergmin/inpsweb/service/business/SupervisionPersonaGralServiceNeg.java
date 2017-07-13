/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.business;

import gob.osinergmin.inpsweb.service.exception.SupervisionPersonaGralException;
import gob.osinergmin.mdicommon.domain.dto.SupervisionPersonaGralDTO;
import gob.osinergmin.mdicommon.domain.ui.SupervisionPersonaGralFilter;
import java.util.List;

/**
 *
 * @author zchaupis
 */
public interface SupervisionPersonaGralServiceNeg {
    public List<SupervisionPersonaGralDTO> find(SupervisionPersonaGralFilter filtro) throws SupervisionPersonaGralException;
	
}
