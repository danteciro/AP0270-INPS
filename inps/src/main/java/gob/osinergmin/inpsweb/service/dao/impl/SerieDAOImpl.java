/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.mdicommon.domain.ui.ProcesoFilter;
import gob.osinergmin.inpsweb.domain.builder.EtapaBuilder;
import gob.osinergmin.inpsweb.domain.builder.ProcesoBuilder;
import gob.osinergmin.inpsweb.domain.builder.TramiteBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.SerieDAO;
import gob.osinergmin.inpsweb.service.exception.SerieException;
import gob.osinergmin.mdicommon.domain.dto.EtapaDTO;
import gob.osinergmin.mdicommon.domain.dto.ProcesoDTO;
import gob.osinergmin.mdicommon.domain.dto.TramiteDTO;

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
@Service("procesoDAO")
public class SerieDAOImpl implements SerieDAO {
    private static final Logger LOG = LoggerFactory.getLogger(SerieDAOImpl.class);   
    @Inject
    private CrudDAO crud;
    
    @Override
    public List<TramiteDTO> listarTramite(Long idEtapa) throws SerieException{
        List<TramiteDTO> retorno=null;
        try{
            Query query = crud.getEm().createNamedQuery("PghTramite.findByIdEtapa");
            query.setParameter("idEtapa", idEtapa);
            retorno = TramiteBuilder.toListTramiteDto(query.getResultList());
        }catch(Exception ex){
            LOG.error("error listarTramite",ex);
        }
        return retorno;
    } 
    
    @Override
    public List<EtapaDTO> listarEtapa(Long idProceso) throws SerieException{
        List<EtapaDTO> retorno=null;
        try{
            Query query = crud.getEm().createNamedQuery("PghEtapa.findByIdProceso");
            query.setParameter("idProceso", idProceso);
            retorno = EtapaBuilder.toListEtapaDto(query.getResultList());
        }catch(Exception ex){
            LOG.error("error listarEtapa",ex);
        }
        return retorno;
    }
    
    @Override
    public List<ProcesoDTO> listarProceso(ProcesoFilter filtro) throws SerieException{
        List<ProcesoDTO> retorno=null;
        try{
       		Query query = crud.getEm().createNamedQuery("PghProceso.findByDifIdentificador");
            if(filtro.getIdentificador()!=null){
                query.setParameter("identificadorProceso", filtro.getIdentificador());
            }
            
            retorno = ProcesoBuilder.toListProcesoDto(query.getResultList());
        }catch(Exception ex){
            LOG.error("error listarProceso",ex);
        }
        return retorno;
    }


    
    
}
