/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.mdicommon.domain.ui.EstadoProcesoFilter;
import gob.osinergmin.inpsweb.domain.builder.EstadoProcesoBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.EstadoProcesoDAO;
import gob.osinergmin.inpsweb.service.exception.EstadoProcesoException;
import gob.osinergmin.mdicommon.domain.dto.EstadoProcesoDTO;

import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author jpiro
 */
@Service("estadoProcesoDAO")
public class EstadoProcesoDAOImpl implements EstadoProcesoDAO {
    private static final Logger LOG = LoggerFactory.getLogger(EstadoProcesoDAOImpl.class);    
    @Inject
    private CrudDAO crud;
    
    public List<EstadoProcesoDTO> find(EstadoProcesoFilter filtro) throws EstadoProcesoException{
        List<EstadoProcesoDTO> listado;
        
        Query query = getFindQuery(filtro);
        listado = EstadoProcesoBuilder.toListEstadoProcesoDto(query.getResultList());

        return listado;
    }
    
    private Query getFindQuery(EstadoProcesoFilter filtro) {
        Query query=null;
        try{
            if(filtro.getIdentificadorEstado()!=null){
                query = crud.getEm().createNamedQuery("PghEstadoProceso.findByIdentificadorEstado");
            }else if(filtro.getIdTipoEstadoProceso()!=null){
                query = crud.getEm().createNamedQuery("PghEstadoProceso.findByIdTipoEstadoProceso");
            }else{
                query = crud.getEm().createNamedQuery("PghEstadoProceso.findAll");
            }
            
            if(filtro.getIdentificadorEstado()!=null){
                query.setParameter("identificadorEstado",filtro.getIdentificadorEstado());
            }      
            if(filtro.getIdTipoEstadoProceso()!=null){
                query.setParameter("idTipoEstadoProceso", filtro.getIdTipoEstadoProceso());
            }
        }catch(Exception e){
            LOG.error("Error getFindQuery: "+e.getMessage());
        }
        return query;
    }
    
}
