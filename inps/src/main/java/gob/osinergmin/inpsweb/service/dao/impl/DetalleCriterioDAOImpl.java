package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.builder.DetalleCriterioBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.DetalleCriterioDAO;
import gob.osinergmin.inpsweb.service.exception.DetalleCriterioException;
import gob.osinergmin.mdicommon.domain.dto.DetalleCriterioDTO;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("detalleCriterioDAO")
public class DetalleCriterioDAOImpl implements DetalleCriterioDAO{
	private static final Logger LOG = LoggerFactory.getLogger(DetalleCriterioDAOImpl.class);

	@Inject
    private CrudDAO crud;
	
	@Override
	public List<DetalleCriterioDTO> listarDetalleCriterio(Long idCriterio,Long idDetalleCriterio) throws DetalleCriterioException {
		List<DetalleCriterioDTO> retorno=null;
        try{
        	String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT dc ");
            jpql.append(" FROM PghDetalleCriterio dc ");
            jpql.append(" WHERE dc.estado=1 ");
            jpql.append(" AND dc.estado=1 ");
            if(idCriterio!=null){
                jpql.append(" AND dc.idCriterio.idCriterio=:idCriterio ");
            }
            if(idDetalleCriterio!=null){
                jpql.append(" AND dc.idDetalleCriterio=:idDetalleCriterio ");
            }
            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            //settear parametros
            if(idCriterio!=null){
            	query.setParameter("idCriterio",idCriterio);
            }
            if(idDetalleCriterio!=null){
            	query.setParameter("idDetalleCriterio",idDetalleCriterio);
            }
        	
            retorno = DetalleCriterioBuilder.toListDetalleCriterioDto(query.getResultList());

        }catch(Exception ex){
            LOG.error("error listarTramite",ex);
        }
        return retorno;
	}

}
