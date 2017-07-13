/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.builder.DetalleSupervisionBuilder;
import gob.osinergmin.inpsweb.domain.builder.UbigeoWSBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.UbigeoDAO;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.UbigeoDTO;
import gob.osinergmin.mdicommon.domain.ui.UbigeoFilter;
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
@Repository("ubigeoDAO")
public class UbigeoDAOImpl implements UbigeoDAO {
    private static final Logger LOG = LoggerFactory.getLogger(UbigeoDAOImpl.class);
    @Inject
    private CrudDAO crud;

    @Override
    public List<UbigeoDTO> obtenerUbigeo(UbigeoFilter filtro) {
      LOG.info("obtenerUbigeo: find-inicio");
        List<UbigeoDTO> retorno = null;
        try {
            String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append(" SELECT new MdiUbigeo( ubi.nombre ,ubi.mdiUbigeoPK.idDepartamento , ubi.mdiUbigeoPK.idProvincia , ubi.mdiUbigeoPK.idDistrito ) ");
            jpql.append(" FROM MdiUbigeo ubi ");
            
            jpql.append(" WHERE ubi.nombre is not null ");
            if (filtro.getIdDepartamento()!= null) {
                jpql.append("AND ubi.mdiUbigeoPK.idDepartamento  =:idDepartamento ");
            }
            if (filtro.getIdProvincia()!= null) {
                jpql.append(" AND ubi.mdiUbigeoPK.idProvincia =:idProvincia  ");
            }
            if (filtro.getIdDistrito()!= null) {
                jpql.append(" AND ubi.mdiUbigeoPK.idDistrito =:idDistrito ");
            }
            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            //settear parametros
            if (filtro.getIdDepartamento()!= null) {
                query.setParameter("idDepartamento", filtro.getIdDepartamento());
            }
            if (filtro.getIdProvincia()!= null) {
                query.setParameter("idProvincia", filtro.getIdProvincia());
            }
            if (filtro.getIdDistrito()!= null) {
                query.setParameter("idDistrito", filtro.getIdDistrito());
            }
            
            retorno = UbigeoWSBuilder.toListUbigeoDTO(query.getResultList());
        } catch (Exception e) {
            LOG.error("error en find", e);
        }
        LOG.info("obtenerUbigeo: find-fin");
        return retorno; 
    }
}
