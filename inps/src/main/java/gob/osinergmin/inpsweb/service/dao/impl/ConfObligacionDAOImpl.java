package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.builder.ConfObligacionBuilder;
import gob.osinergmin.inpsweb.service.dao.ConfObligacionDAO;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.exception.ConfObligacionException;
import gob.osinergmin.mdicommon.domain.dto.ConfObligacionDTO;
import gob.osinergmin.mdicommon.domain.ui.ConfObligacionFilter;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("confObligacionDAO")
public class ConfObligacionDAOImpl implements ConfObligacionDAO {

    private static final Logger LOG = LoggerFactory.getLogger(ConfObligacionDAOImpl.class);
    @Inject
    private CrudDAO crud;

    @Override
    public List<ConfObligacionDTO> listarConfObligacion(ConfObligacionFilter filtro) throws ConfObligacionException {
        LOG.info("ConfObligacionDAOImpl: listarConfObligacion-inicio");
        List<ConfObligacionDTO> retorno = null;
        try {
            String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT new PghConfObligacion ");
            jpql.append(" ( ");
            jpql.append(" co.idConfObligacion,co.codTrazabilidad,co.codAccion, ");
            jpql.append(" o.idObligacion ");
            jpql.append(" ) ");
            jpql.append(" FROM PghConfObligacion co ");
            jpql.append(" JOIN co.idObligacion o ");
            jpql.append(" WHERE co.estado=1 ");
            jpql.append(" AND co.idObligacion.estado=1 ");
            if (filtro.getIdActividad() != null) {
                jpql.append(" AND co.pghProcesoObligacionTipo.pghProcesoObligacionTipoPK.idActividad=:idActividad ");
            }
            if (filtro.getIdProceso() != null) {
                jpql.append(" AND co.pghProcesoObligacionTipo.pghProcesoObligacionTipoPK.idProceso=:idProceso ");
            }
            if (filtro.getIdObligacionTipo() != null) {
                jpql.append(" AND co.pghProcesoObligacionTipo.pghProcesoObligacionTipoPK.idObligacionTipo=:idObligacionTipo ");
            }
            jpql.append(" ORDER BY o.idObligacion ASC ");
            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            //settear parametros
            if (filtro.getIdActividad() != null) {
                query.setParameter("idActividad", filtro.getIdActividad());
            }
            if (filtro.getIdProceso() != null) {
                query.setParameter("idProceso", filtro.getIdProceso());
            }
            if (filtro.getIdObligacionTipo() != null) {
                query.setParameter("idObligacionTipo", filtro.getIdObligacionTipo());
            }
            retorno = ConfObligacionBuilder.toListConfObligacionDto(query.getResultList());
        } catch (Exception e) {
            LOG.error("error en find", e);
        }
        LOG.info("ConfObligacionDAOImpl: listarConfObligacion-fin");
        return retorno;
    }
}
