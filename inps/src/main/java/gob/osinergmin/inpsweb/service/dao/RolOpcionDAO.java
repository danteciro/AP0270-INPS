/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.inpsweb.service.exception.RolOpcionException;
import gob.osinergmin.mdicommon.domain.dto.RolOpcionDTO;
import gob.osinergmin.mdicommon.domain.ui.RolOpcionFilter;
import java.util.List;

/**
 *
 * @author jpiro
 */
public interface RolOpcionDAO {
    public List<RolOpcionDTO> find(RolOpcionFilter filtro) throws RolOpcionException;
}
