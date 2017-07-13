package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.builder.CriterioBuilder;
import gob.osinergmin.inpsweb.service.dao.CriterioDAO;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.exception.CriterioException;
import gob.osinergmin.mdicommon.domain.dto.CriterioDTO;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("criterioDAO")
public class CriterioDAOImpl implements CriterioDAO{
	private static final Logger LOG = LoggerFactory.getLogger(CriterioDAOImpl.class);   
    @Inject
    private CrudDAO crud;
    
    
	@Override
	public List<CriterioDTO> listarCriterio(Long idObligacion,Long idTipificacion,Long idCriterio)throws CriterioException {
		List<CriterioDTO> retorno=null;
        try{
        	String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT c ");
            jpql.append(" FROM PghObliTipiCriterio otc ");
            jpql.append(" JOIN otc.idCriterio c ");
            jpql.append(" WHERE otc.estado='1' ");
            if(idObligacion!=null){
                jpql.append(" AND otc.idObligacion.idObligacion=:idObligacion ");
            }
            if(idTipificacion!=null){
                jpql.append(" AND otc.idTipificacion.idTipificacion=:idTipificacion ");
            }
            if(idCriterio!=null){
                jpql.append(" AND c.idCriterio=:idCriterio ");
            }
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            if(idObligacion!=null){
            	query.setParameter("idObligacion",idObligacion);
            }
            if(idTipificacion!=null){
            	query.setParameter("idTipificacion",idTipificacion);
            }
            if(idCriterio!=null){
            	query.setParameter("idCriterio",idCriterio);
            }
            retorno = CriterioBuilder.toListCriterioDto(query.getResultList());
        }catch(Exception ex){
            LOG.error("error listarCriterio",ex);
        }
        return retorno;
	}

}
