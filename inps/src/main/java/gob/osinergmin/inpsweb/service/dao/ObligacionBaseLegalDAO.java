/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.inpsweb.service.exception.ObligacionBaseLegalException;
import gob.osinergmin.mdicommon.domain.dto.ObligacionBaseLegalDTO;
import gob.osinergmin.mdicommon.domain.ui.ObligacionBaseLegalFilter;
import java.util.List;

/**
 *
 * @author zchaupis
 */
public interface ObligacionBaseLegalDAO {
    
    public List<ObligacionBaseLegalDTO> getObligacionBaseLegal(ObligacionBaseLegalFilter filtro)throws ObligacionBaseLegalException;
}
