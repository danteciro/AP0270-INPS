/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.builder.PlazoBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.PlazoDAO;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.PlazoDTO;
import gob.osinergmin.mdicommon.domain.ui.PlazoFilter;
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
@Repository("plazoDAO")
public class PlazoDAOImpl implements PlazoDAO {

    private static final Logger LOG = LoggerFactory.getLogger(PlazoDAOImpl.class);
    @Inject
    private CrudDAO crud;

    @Override
    public List<PlazoDTO> getListPlazo(PlazoFilter filtro) {
      LOG.info("PlazoDAOImpl:getListPlazo-inicio");
		List<PlazoDTO> retorno=null;
        try{
            String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT s "
                + "FROM PghPlazo s "
                + "WHERE s.estado=:estadoActivo ");
            if(filtro.getIdPlazo()!=null){
                jpql.append(" and s.idPlazo=:idPlazo ");
            }
            if(filtro.getCodigoPlazo()!=null){
            	jpql.append(" and s.codigoPlazo=:codigoPlazo ");
            }
            if(filtro.getIdUnidadMedidaMaestro()!=null){
            	jpql.append(" and s.idUnidadMedidaMaestro=:idUnidadMedidaMaestro ");
            }
            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            //settear parametros
            query.setParameter("estadoActivo",Constantes.ESTADO_ACTIVO);
            if(filtro.getIdPlazo()!=null){
                query.setParameter("idPlazo",filtro.getIdPlazo());
            }
            if(filtro.getCodigoPlazo()!=null){
            	query.setParameter("codigoPlazo",filtro.getCodigoPlazo());
            }
            if(filtro.getIdUnidadMedidaMaestro()!=null){
            	query.setParameter("idUnidadMedidaMaestro",filtro.getIdUnidadMedidaMaestro());
            }
            retorno= PlazoBuilder.toListPlazoDTO(query.getResultList());
        }catch(Exception e){
            LOG.error("error en getListPlazo",e);
        }
        LOG.info("PlazoDAOImpl:getListPlazo-fin");
        return retorno;
    }
}
