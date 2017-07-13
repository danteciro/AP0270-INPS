package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.builder.TipificacionBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.TipificacionDAO;
import gob.osinergmin.mdicommon.domain.dto.TipificacionDTO;
import gob.osinergmin.mdicommon.domain.ui.TipificacionFilter;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("tipificacionDAO")
public class TipificacionDAOImpl implements TipificacionDAO{
	private static final Logger LOG = LoggerFactory.getLogger(TipificacionDAOImpl.class);

	@Inject
    private CrudDAO crud;
	
	@Override
	public List<TipificacionDTO> listarTipificacion(TipificacionFilter filtro) {
		LOG.info("TipificacionDAOImpl :listarTipificacion-inicio");
		List<TipificacionDTO>retorno=null;
        try{
            String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT t ");
            jpql.append(" FROM PghObligacionTipificacion ot ");
            jpql.append(" JOIN ot.idTipificacion t ");
            jpql.append(" WHERE ot.estado=1 ");
            jpql.append(" AND ot.idTipificacion.estado=1 ");
            if(filtro.getIdObligacion()!=null){
                jpql.append(" AND ot.idObligacion.idObligacion=:idObligacion ");
            }
            if(filtro.getIdTipificacion()!=null){
                jpql.append(" AND ot.idTipificacion.idTipificacion=:idTipificacion ");
            }
            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            //settear parametros
            if(filtro.getIdObligacion()!=null){
            	query.setParameter("idObligacion",filtro.getIdObligacion());
            }
            if(filtro.getIdTipificacion()!=null){
            	query.setParameter("idTipificacion",filtro.getIdTipificacion());
            }
            retorno= TipificacionBuilder.toListTipificacionDto(query.getResultList());
        }catch(Exception e){
            LOG.error("error en listarTipificacion",e);
        }
        LOG.info("TipificacionDAOImpl :listarTipificacion-fin");
        return retorno;
	}

}
