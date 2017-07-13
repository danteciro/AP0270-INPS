/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.service.business.ResultadoSupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.dao.ResultadoSupervisionDAO;
import gob.osinergmin.inpsweb.service.exception.ResultadoSupervisionException;
import gob.osinergmin.mdicommon.domain.dto.ResultadoSupervisionDTO;
import gob.osinergmin.mdicommon.domain.ui.ResultadoSupervisionFilter;
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
@Service("resultadoSupervisionServiceNeg")
public class ResultadoSupervisionServiceNegImpl implements ResultadoSupervisionServiceNeg {

    private static final Logger LOG = LoggerFactory.getLogger(ResultadoSupervisionServiceNegImpl.class);
    @Inject
    private ResultadoSupervisionDAO resultadoSupervisionDAO;

    @Override
    @Transactional(readOnly = true)
    public List<ResultadoSupervisionDTO> listarResultadoSupervision(ResultadoSupervisionFilter resultadoSupervisionFilter) throws ResultadoSupervisionException{
        LOG.info("Neg listarResultadoDetSupervision");
        List<ResultadoSupervisionDTO> retorno = null;
        try {
            retorno = resultadoSupervisionDAO.listarResultadoSupervision(resultadoSupervisionFilter);

        } catch (Exception ex) {
            LOG.error("Error en listarResultadoDetSupervision", ex);
            throw new ResultadoSupervisionException(ex.getMessage(),ex);
        }
        return retorno;
    }

    @Override
    @Transactional(readOnly = true)
    public ResultadoSupervisionDTO getResultadoSupervision(ResultadoSupervisionFilter resultadoSupervisionFilter) throws ResultadoSupervisionException{
    LOG.info("Neg getResultadoDetalleSupervision");
        ResultadoSupervisionDTO retorno = null;
        try {
            retorno = resultadoSupervisionDAO.getResultadoSupervision(resultadoSupervisionFilter);

        } catch (Exception ex) {
            LOG.error("Error en getResultadoDetalleSupervision", ex);
            throw new ResultadoSupervisionException(ex.getMessage(),ex);
        }
        return retorno;
    }
}
