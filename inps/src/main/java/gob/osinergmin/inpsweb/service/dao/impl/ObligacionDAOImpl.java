package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.PghObligacion;
import gob.osinergmin.inpsweb.domain.builder.ObligacionBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.ObligacionDAO;
import gob.osinergmin.inpsweb.service.exception.ObligacionException;
import gob.osinergmin.mdicommon.domain.dto.ObligacionDTO;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("obligacionDAO")
public class ObligacionDAOImpl implements ObligacionDAO {

    private static final Logger LOG = LoggerFactory.getLogger(ObligacionDAOImpl.class);
    @Inject
    private CrudDAO crud;

    @Override
    public ObligacionDTO buscarObligacion(ObligacionDTO obligacionDTO) throws ObligacionException {
        LOG.info("SupervisionDAOImpl:find-inicio");
        ObligacionDTO retorno = null;
        PghObligacion resultado = null;
        try {
            String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT o "
                    + "FROM PghObligacion o "
                    + "WHERE o.estado=1 ");
            if (obligacionDTO.getIdObligacion() != null) {
                jpql.append(" and o.idObligacion=:idObligacion ");
            }
            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            //settear parametros
            if (obligacionDTO.getIdObligacion() != null) {
                query.setParameter("idObligacion", obligacionDTO.getIdObligacion());
            }
            resultado = (PghObligacion) query.getSingleResult();
            retorno = ObligacionBuilder.toObligacionDto(resultado);
        } catch (Exception e) {
            LOG.error("error en find", e);
        }
        LOG.info("SupervisionDAOImpl:find-fin");
        return retorno;
    }
}
