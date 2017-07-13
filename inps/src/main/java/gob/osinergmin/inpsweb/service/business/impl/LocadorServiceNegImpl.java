/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.service.business.LocadorServiceNeg;
import gob.osinergmin.inpsweb.service.dao.ConfFiltroEmpSupDAO;
import gob.osinergmin.inpsweb.service.dao.LocadorDAO;
import gob.osinergmin.mdicommon.domain.base.BaseConstantesOutBean;
import gob.osinergmin.mdicommon.domain.dto.ConfFiltroEmpSupDTO;
import gob.osinergmin.mdicommon.domain.dto.LocadorDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisoraEmpresaDTO;
import gob.osinergmin.mdicommon.domain.in.ObtenerLocadoresInRO;
import gob.osinergmin.mdicommon.domain.in.ObtenerSupervisoraEmpresaInRO;
import gob.osinergmin.mdicommon.domain.out.ObtenerLocadoresOutRO;
import gob.osinergmin.mdicommon.domain.out.ObtenerSupervisoraEmpresaOutRO;
import gob.osinergmin.mdicommon.domain.ui.ConfFiltroEmpSupFilter;
import gob.osinergmin.mdicommon.domain.ui.LocadorFilter;
import gob.osinergmin.mdicommon.domain.ui.SupervisoraEmpresaFilter;
import gob.osinergmin.mdicommon.remote.LocadorEndpoint;
import gob.osinergmin.mdicommon.remote.SupervisoraEmpresaEndpoint;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jpiro
 */
@Service("locadorServiceNeg")
public class LocadorServiceNegImpl implements LocadorServiceNeg  {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(LocadorServiceNegImpl.class);
    @Inject
    private LocadorEndpoint locadorEndpoint;
    @Inject
    private LocadorDAO locadorDAO;
    @Inject
    private ConfFiltroEmpSupDAO confFiltroEmpSupDAO;
        
    @Override
    public List<LocadorDTO> listarLocador(LocadorFilter filtro){
        ObtenerLocadoresInRO request = new ObtenerLocadoresInRO();        
        request.setFiltro(filtro);
        ObtenerLocadoresOutRO response;
        response= locadorEndpoint.listarLocador(request);
        if(response.getCodigoResultado().equals(BaseConstantesOutBean.SUCCESS)) {
            LOG.info("cuenta lista locador: "+response.getContador().intValue());
        }
        if( response.getContador().intValue()>0) {
            return response.getListado();     
        }else{
            return null;
        }
    }

	@Override
        @Transactional(readOnly = true)
	public List<LocadorDTO> listarLocadorConfFiltros(LocadorFilter filtro) {
		LOG.info("Neg listarLocadorConfFiltros");
        List<LocadorDTO> retorno=null;
        ConfFiltroEmpSupFilter filtroConfEmpSup=new ConfFiltroEmpSupFilter(); 
        try {
        	filtroConfEmpSup.setIdUnidadOrganica(filtro.getIdUnidadOrganica());
        	List<ConfFiltroEmpSupDTO> listaConfFiltroEmpSup=confFiltroEmpSupDAO.findConfFiltroEmpSup(filtroConfEmpSup);
        	retorno = locadorDAO.listarLocadorConfFiltros(filtro, listaConfFiltroEmpSup);
        } catch(Exception ex){
            LOG.error("",ex);
        }
		return retorno;
	}
	@Override
	public List<ConfFiltroEmpSupDTO> listarConfFiltros(LocadorFilter filtro) {
		LOG.info("Neg listarConfFiltros");
        List<ConfFiltroEmpSupDTO> retorno=null;
        ConfFiltroEmpSupFilter filtroConfEmpSup=new ConfFiltroEmpSupFilter(); 
        try {
        	filtroConfEmpSup.setIdUnidadOrganica(filtro.getIdUnidadOrganica());
        	retorno=confFiltroEmpSupDAO.findConfFiltroEmpSup(filtroConfEmpSup);
        } catch(Exception ex){
            LOG.error("",ex);
        }
		return retorno;
	}
        
    @Override
    @Transactional(readOnly = true)
    public LocadorDTO getById(Long idLocador){
        LOG.info("getById");
        LocadorDTO retorno=null;
        try {
            retorno = locadorDAO.getById(idLocador);
        } catch(Exception ex){
            LOG.error("",ex);
        }
        return retorno;
    }

}
