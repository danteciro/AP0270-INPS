/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.service.business.SupervisionPersonaGralServiceNeg;
import gob.osinergmin.inpsweb.service.dao.SupervisionPersonaGralDAO;
import gob.osinergmin.inpsweb.service.exception.SupervisionPersonaGralException;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionPersonaGralDTO;
import gob.osinergmin.mdicommon.domain.ui.SupervisionPersonaGralFilter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author zchaupis
 */
@Service("SupervisionPersonaGralServiceNeg")
public class SupervisionPersonaGralServiceNegImpl implements SupervisionPersonaGralServiceNeg {

    private static Logger LOG = LoggerFactory.getLogger(SupervisionPersonaGralServiceNegImpl.class);
    @Inject
    private SupervisionPersonaGralDAO supervisionPersonaGralDAO;

    @Override
    @Transactional(readOnly = true)
    public List<SupervisionPersonaGralDTO> find(SupervisionPersonaGralFilter filtro) throws SupervisionPersonaGralException {
        LOG.info("find");
        List<SupervisionPersonaGralDTO> retorno = new ArrayList<SupervisionPersonaGralDTO>();
        try {
            retorno = supervisionPersonaGralDAO.find(filtro);
        } catch (Exception ex) {
            LOG.error("Error en find", ex);
        }
        return retorno;
    }
}