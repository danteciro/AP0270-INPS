package gob.osinergmin.inpsweb.service.dao.impl;

import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import gob.osinergmin.inpsweb.domain.builder.TipificacionSancionBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.TipificacionSancionDAO;
import gob.osinergmin.inpsweb.service.exception.TipificacionSancionException;
import gob.osinergmin.mdicommon.domain.dto.TipificacionSancionDTO;
import gob.osinergmin.mdicommon.domain.ui.TipificacionSancionFilter;

@Repository("tipificacionSancionDAO")
public class TipificacionSancionDAOImpl implements TipificacionSancionDAO{
	private static final Logger LOG = LoggerFactory.getLogger(TipificacionSancionDAOImpl.class);

	@Inject
    private CrudDAO crud;
	
	@Override
	public List<TipificacionSancionDTO> find(TipificacionSancionFilter filtro) throws TipificacionSancionException {
		LOG.info("TipificacionSancionDAOImpl :find-inicio");
		List<TipificacionSancionDTO>retorno=null;
        try{
            String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT ts ");
            jpql.append(" FROM PghTipificacionSancion ts ");
            jpql.append(" WHERE ts.estado=1 ");
            jpql.append(" AND ts.idTipoSancion.estado=1 ");
            if(filtro.getIdTipificacion()!=null){
                jpql.append(" AND ts.idTipificacion.idTipificacion=:idTipificacion ");
            }
            if(filtro.getNivel()!=null){
            	jpql.append(" AND ts.nivel=:nivel ");
            }
            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            //settear parametros
            if(filtro.getIdTipificacion()!=null){
            	query.setParameter("idTipificacion",filtro.getIdTipificacion());
            }
            if(filtro.getNivel()!=null){
            	query.setParameter("nivel",filtro.getNivel());
            }
            retorno= TipificacionSancionBuilder.toListTipificacionSancionDto(query.getResultList());
        }catch(Exception e){
            LOG.error("error en find",e);
        }
        LOG.info("TipificacionSancionDAOImpl :find-fin");
        return retorno;
	}

}
