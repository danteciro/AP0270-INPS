/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.service.business.PlazoServiceNeg;
import gob.osinergmin.inpsweb.service.dao.PlazoDAO;
import gob.osinergmin.mdicommon.domain.dto.PlazoDTO;
import gob.osinergmin.mdicommon.domain.ui.PlazoFilter;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author zchaupis
 */
@Service("plazoServiceNeg")
public class PlazoServiceNegImpl implements PlazoServiceNeg {
 private static final Logger LOG = LoggerFactory.getLogger(PlazoServiceNegImpl.class);
    @Inject
    private PlazoDAO plazoDAO;
    @Override
    public List<PlazoDTO> getListPlazo(PlazoFilter filtro) {
      LOG.info("PlazoServiceNegImpl--getListPlazo - inicio");
        List<PlazoDTO> retorno = null;
        try {
            retorno = plazoDAO.getListPlazo(filtro);
        } catch (Exception ex) {
            LOG.error("Error en getListPlazo", ex);
        }
        LOG.info("PlazoServiceNegImpl--getListPlazo - fin");
        return retorno;
    }
    
}
