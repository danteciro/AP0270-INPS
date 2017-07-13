/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.mdicommon.domain.dto.PlazoDTO;
import gob.osinergmin.mdicommon.domain.ui.PlazoFilter;
import java.util.List;

/**
 *
 * @author zchaupis
 */
public interface PlazoDAO {
     public List<PlazoDTO> getListPlazo (PlazoFilter filtro);  
}
