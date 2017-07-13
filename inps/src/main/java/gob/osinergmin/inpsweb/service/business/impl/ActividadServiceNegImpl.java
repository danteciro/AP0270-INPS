package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.service.business.ActividadServiceNeg;
import gob.osinergmin.inpsweb.service.dao.ActividadDAO;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.ActividadDTO;
import gob.osinergmin.mdicommon.domain.ui.ActividadFilter;

import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("ActividadServiceNeg")
public class ActividadServiceNegImpl implements ActividadServiceNeg {

    private static final Logger LOG = LoggerFactory.getLogger(ActividadServiceNeg.class);
    @Inject
    private ActividadDAO actividadDAO;

    @Override
    public List<ActividadDTO> listarActividad(int[] auxiliar) {
        LOG.info("Neg listarActividad");
        List<ActividadDTO> retorno = null;
        try {
            retorno = actividadDAO.find();

        } catch (Exception ex) {
            LOG.error("", ex);
        }
        return retorno;
    }

    @Override
    public List<ActividadDTO> findBy(ActividadFilter filtro) {
        LOG.info("Neg findBy");
        List<ActividadDTO> retorno = null;
        try {
            retorno = actividadDAO.findBy(filtro);

        } catch (Exception ex) {
            LOG.error("findBy", ex);
        }
        return retorno;
    }
}