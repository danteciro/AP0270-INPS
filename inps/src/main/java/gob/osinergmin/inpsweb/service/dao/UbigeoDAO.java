/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.mdicommon.domain.dto.UbigeoDTO;
import gob.osinergmin.mdicommon.domain.ui.UbigeoFilter;
import java.util.List;

/**
 *
 * @author zchaupis
 */
public interface UbigeoDAO {
    public List<UbigeoDTO> obtenerUbigeo(UbigeoFilter ubigeoFilter);
    
}
