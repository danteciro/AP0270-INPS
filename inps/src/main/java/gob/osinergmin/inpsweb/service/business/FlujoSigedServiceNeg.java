/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.business;

import gob.osinergmin.mdicommon.domain.ui.FlujoSigedFilter;
import gob.osinergmin.mdicommon.domain.dto.FlujoSigedDTO;

import java.util.List;

/**
 *
 * @author jpiro
 */
public interface FlujoSigedServiceNeg {
    public List<FlujoSigedDTO> listarFlujoSiged(FlujoSigedFilter filtro);
}
