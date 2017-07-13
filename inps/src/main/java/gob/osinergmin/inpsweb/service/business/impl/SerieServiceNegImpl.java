/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.mdicommon.domain.ui.ProcesoFilter;
import gob.osinergmin.inpsweb.service.business.SerieServiceNeg;
import gob.osinergmin.inpsweb.service.dao.SerieDAO;
import gob.osinergmin.mdicommon.domain.dto.EtapaDTO;
import gob.osinergmin.mdicommon.domain.dto.ProcesoDTO;
import gob.osinergmin.mdicommon.domain.dto.TramiteDTO;

import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author jpiro
 */
@Service("procesoServiceNeg")
public class SerieServiceNegImpl implements SerieServiceNeg {
    private static final Logger LOG = LoggerFactory.getLogger(PersonalServiceNegImpl.class);    
    @Inject
    private SerieDAO serieDAO;
        
    @Override
	public List<ProcesoDTO> listarProceso(ProcesoFilter filtro) {
		LOG.info("Neg listarProceso");
        List<ProcesoDTO> retorno=null;
        try{
            retorno = serieDAO.listarProceso(filtro);
        }catch(Exception ex){
            LOG.error("error listarProceso",ex);
        }
        return retorno;
	}
    
    @Override
    public List<EtapaDTO> listarEtapa(Long idProceso){
        LOG.info("Neg listarEtapa");
        List<EtapaDTO> retorno=null;
        try{
            retorno = serieDAO.listarEtapa(idProceso);
        }catch(Exception ex){
            LOG.error("error listarEtapa",ex);
        }
        return retorno;
    }
    
    @Override
    public List<TramiteDTO> listarTramite(Long idEtapa){
        LOG.info("Neg listarTramite");
        List<TramiteDTO> retorno=null;
        try{
            retorno = serieDAO.listarTramite(idEtapa);
        }catch(Exception ex){
            LOG.error("error listarTramite",ex);
        }
        return retorno;
    }

    
}
