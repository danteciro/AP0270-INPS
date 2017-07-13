/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.builder.InfraccionBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.InfraccionDAO;
import gob.osinergmin.inpsweb.service.exception.InfraccionException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.InfraccionDTO;
import gob.osinergmin.mdicommon.domain.ui.EscenarioIncumplimientoFilter;
import gob.osinergmin.mdicommon.domain.ui.InfraccionFilter;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author zchaupis
 */
@Repository("infraccionDAO")
public class InfraccionDAOImpl implements InfraccionDAO {

    private static final Logger LOG = LoggerFactory.getLogger(InfraccionDAOImpl.class);
    @Inject
    private CrudDAO crud;

    @Override
    @Transactional(rollbackFor = InfraccionException.class)
    public List<InfraccionDTO> getListInfraccion(InfraccionFilter filtro)throws InfraccionException{
        LOG.info("InfraccionDAOImpl:getListInfraccion-inicio");
        List<InfraccionDTO> retorno = null;
        try {
            String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT s "
                    + "FROM PghInfraccion s "
                    + "WHERE s.estado=:estadoActivo ");
            if (filtro.getIdInfraccion() != null) {
                jpql.append(" and s.idInfraccion=:idInfraccion ");
            }
            if (filtro.getIdMedidaSeguridadMaestro() != null) {
                jpql.append(" and s.idMedidaSeguridadMaestro=:idMedidaSeguridadMaestro ");
            }
            if (filtro.getCodigo() != null) {
                jpql.append(" and s.codigo=:codigo ");
            }
            if (filtro.getIdObligacion() != null) {
                jpql.append(" and s.idObligacion.idObligacion =:idObligacion ");
            }
            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            //settear parametros
            query.setParameter("estadoActivo", Constantes.ESTADO_ACTIVO);
            if (filtro.getIdInfraccion() != null) {
                query.setParameter("idInfraccion", filtro.getIdInfraccion());
            }
            if (filtro.getIdMedidaSeguridadMaestro() != null) {
                query.setParameter("idMedidaSeguridadMaestro", filtro.getIdMedidaSeguridadMaestro());
            }
            if (filtro.getCodigo() != null) {
                query.setParameter("codigo", filtro.getCodigo());
            }
            if (filtro.getIdObligacion() != null) {
                query.setParameter("idObligacion", filtro.getIdObligacion());
            }
            retorno = InfraccionBuilder.toListInfraccionDTO(query.getResultList());
        } catch (Exception e) {
            LOG.error("error en getListInfraccion", e);
        }
        LOG.info("InfraccionDAOImpl:getListInfraccion-fin");
        return retorno;
    }
    /*OSINE_SFS-791 - RSIS 16 - Inicio*/
    @Override
    @Transactional(rollbackFor = InfraccionException.class)
    public List<InfraccionDTO> findByFilter(EscenarioIncumplimientoFilter filtro) throws InfraccionException {
        LOG.info("findByFilter");
        List<InfraccionDTO> retorno=null;
        try{
            Query query = getFindQuery(filtro);
            retorno = InfraccionBuilder.toListInfraccionDTO(query.getResultList());
        }catch(Exception e){
            LOG.error("Error en findByFilter",e);
        }
        return retorno;
    }

    private Query getFindQuery(EscenarioIncumplimientoFilter filtro) {
        Query query=null;
        try{
            if(filtro.getIdInfraccion()!=null){
                query = crud.getEm().createNamedQuery("PghInfraccion.findByIdInfraccion");
            }else{
                query = crud.getEm().createNamedQuery("PghInfraccion.findAll");
            }
            
            if(filtro.getIdInfraccion()!=null){
                query.setParameter("idInfraccion",filtro.getIdInfraccion());
            }            
        }catch(Exception e){
            LOG.error("Error getFindQuery",e);
        }
        return query;
    }
    /*OSINE_SFS-791 - RSIS 16 - Fin*/
}
