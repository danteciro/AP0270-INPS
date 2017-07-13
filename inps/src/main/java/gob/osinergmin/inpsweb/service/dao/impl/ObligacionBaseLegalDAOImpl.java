/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.builder.DetalleSupervisionBuilder;
import gob.osinergmin.inpsweb.domain.builder.ObligacionBaseLegalBuilder;
import gob.osinergmin.inpsweb.service.dao.ObligacionBaseLegalDAO;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.exception.ObligacionBaseLegalException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.BaseLegalDTO;
import gob.osinergmin.mdicommon.domain.dto.ObligacionBaseLegalDTO;
import gob.osinergmin.mdicommon.domain.ui.ObligacionBaseLegalFilter;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author zchaupis
 */
@Repository("obligacionBaseLegalDAO")
public class ObligacionBaseLegalDAOImpl implements ObligacionBaseLegalDAO{

    
    private static final Logger LOG = LoggerFactory.getLogger(ObligacionBaseLegalDAOImpl.class);
    @Inject
    private CrudDAO crud;
    
    @Override
    public List<ObligacionBaseLegalDTO> getObligacionBaseLegal(ObligacionBaseLegalFilter filtro) throws ObligacionBaseLegalException {
      LOG.info("ObligacionBaseLegalDAOImpl: getObligacionBaseLegal-inicio");
        List<ObligacionBaseLegalDTO> retorno = null;
        try {
            String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT new PghObligacionBaseLegal ");
            jpql.append(" ( ");
            jpql.append(" o.idObligacion , ");
            jpql.append(" bl.idBaseLegal , ");
            jpql.append(" bl.descripcion ");
            jpql.append(" ) ");
            jpql.append(" FROM PghObligacionBaseLegal obl ");
            jpql.append(" LEFT JOIN obl.idObligacion o ");
            jpql.append(" LEFT JOIN obl.idBaseLegal bl ");
            
            jpql.append(" WHERE obl.estado=:estadoActivo ");
            if (filtro.getIdObligacion()!= null) {
                jpql.append(" AND o.idObligacion =:idObligacion ");
            }
            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            //settear parametros
            query.setParameter("estadoActivo",Constantes.ESTADO_ACTIVO);
            if (filtro.getIdObligacion()!= null) {
                query.setParameter("idObligacion", filtro.getIdObligacion());
            }
            
            retorno = ObligacionBaseLegalBuilder.toListObligacionBaseLegalDTO(query.getResultList());
        } catch (Exception e) {
            LOG.error("error en find", e);
        }
        LOG.info("DetalleSupervisionDAOImpl: find-fin");
        return retorno;
    }
    
}
