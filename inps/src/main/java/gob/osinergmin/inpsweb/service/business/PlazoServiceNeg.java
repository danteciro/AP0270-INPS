/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.business;

import gob.osinergmin.mdicommon.domain.dto.PlazoDTO;
import gob.osinergmin.mdicommon.domain.ui.PlazoFilter;
import java.util.List;

/**
 *
 * @author zchaupis
 */
public interface PlazoServiceNeg {
    public List<PlazoDTO> getListPlazo (PlazoFilter filtro);    
}
