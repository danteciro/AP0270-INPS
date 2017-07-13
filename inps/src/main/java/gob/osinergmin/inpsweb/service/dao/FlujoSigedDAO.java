/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.mdicommon.domain.ui.FlujoSigedFilter;
import gob.osinergmin.inpsweb.service.exception.FlujoSigedException;
import gob.osinergmin.mdicommon.domain.dto.FlujoSigedDTO;

import java.util.List;

/**
 *
 * @author jpiro
 */
public interface FlujoSigedDAO {
    public List<FlujoSigedDTO> find(FlujoSigedFilter filtro) throws FlujoSigedException;
}
