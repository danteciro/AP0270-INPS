/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.service.business.ObligacionBaseLegalServiceNeg;
import gob.osinergmin.inpsweb.service.dao.ObligacionBaseLegalDAO;
import gob.osinergmin.inpsweb.service.exception.ObligacionBaseLegalException;
import gob.osinergmin.mdicommon.domain.dto.ObligacionBaseLegalDTO;
import gob.osinergmin.mdicommon.domain.ui.ObligacionBaseLegalFilter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author zchaupis
 */
@Service("ObligacionBaseLegalServiceNeg")
public class ObligacionBaseLegalServiceNegImpl implements ObligacionBaseLegalServiceNeg {

    private static Logger LOG = LoggerFactory.getLogger(ObligacionBaseLegalServiceNegImpl.class);
    @Inject
    private ObligacionBaseLegalDAO obligacionBaseLegalDAO;

    @Override
    public List<ObligacionBaseLegalDTO> getObligacionBaseLegal(ObligacionBaseLegalFilter filtro) throws ObligacionBaseLegalException {
        LOG.info("getObligacionBaseLegal");
        List<ObligacionBaseLegalDTO> retorno = new ArrayList<ObligacionBaseLegalDTO>();
        try {
            retorno = obligacionBaseLegalDAO.getObligacionBaseLegal(filtro);
        } catch (Exception ex) {
            LOG.error("Error en getObligacionBaseLegal", ex);
        }
        return retorno;
    }
}
