package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.service.business.CnfUnidOrgaServiceNeg;
import gob.osinergmin.inpsweb.service.business.MaestroColumnaServiceNeg;
import gob.osinergmin.inpsweb.service.business.PersonalServiceNeg;
import gob.osinergmin.inpsweb.service.business.UnidadOrganicaServiceNeg;
import gob.osinergmin.inpsweb.service.dao.UnidadOrganicaDAO;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.base.BaseConstantesOutBean;
import gob.osinergmin.mdicommon.domain.dto.CnfUnidOrgaDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.PersonalDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadOrganicaDTO;
import gob.osinergmin.mdicommon.domain.in.ObtenerUnidadesOrganicasInRO;
import gob.osinergmin.mdicommon.domain.out.ObtenerUnidadesOrganicasOutRO;
import gob.osinergmin.mdicommon.domain.ui.CnfUnidOrgaFilter;
import gob.osinergmin.mdicommon.domain.ui.PersonalFilter;
import gob.osinergmin.mdicommon.domain.ui.UnidadDivisionFilter;
import gob.osinergmin.mdicommon.domain.ui.UnidadOrganicaFilter;
import gob.osinergmin.mdicommon.remote.UnidadOrganicaEndpoint;

import java.util.List;

import javax.inject.Inject;

import org.apache.cxf.common.util.CollectionUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jpiro
 */
@Service("unidadOrganicaServiceNeg")
public class UnidadOrganicaServiceNegImpl implements UnidadOrganicaServiceNeg {
    
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(UnidadOrganicaServiceNegImpl.class);
    
    @Inject
    private UnidadOrganicaEndpoint unidadOrganica;
    @Inject
    private UnidadOrganicaDAO unidadOrganicaDAO;
    /* OSINE_SFS-1344  - Inicio */
    @Inject
    private PersonalServiceNeg personalServiceNeg;
    
    @Inject
    private CnfUnidOrgaServiceNeg cnfUnidOrgaServiceNeg;
    
    @Inject
    private MaestroColumnaServiceNeg maestroColumnaServiceNeg;
    /* OSINE_SFS-1344  - Fin */
    
    @Override
    public List<UnidadOrganicaDTO> obtenerListadoUnidadOrganica(UnidadOrganicaFilter filtro){
        ObtenerUnidadesOrganicasInRO request = new ObtenerUnidadesOrganicasInRO();
        filtro.setEstado(Constantes.ESTADO_ACTIVO);
        request.setFiltro(filtro);
        
        ObtenerUnidadesOrganicasOutRO response;
        
        response= unidadOrganica.obtenerListadoUnidadOrganica(request);  

        if(response.getCodigoResultado().equals(BaseConstantesOutBean.SUCCESS)) {
           LOG.info("cuenta lista Division (Unidad Organica): "+response.getContador().intValue());
        }
        
        if( response.getContador().intValue()>0) {
           return response.getListado();     
        }else{
           return null;
        }
    }
    
    @Override
    public Long obtenerIdUnidadOrganicaByIdEntidad(Long idEntidad){
        Long retorno=null;
        try{        
            UnidadOrganicaDTO unidOrga=new UnidadOrganicaDTO();
            if(idEntidad!=null && idEntidad.equals(Constantes.ID_ENTIDAD_GFHL)){
                unidOrga=obtenerListadoUnidadOrganica(new UnidadOrganicaFilter(Constantes.UNIDAD_ORGANICA_COD_DEP_SIGA_GFHL,Constantes.UNIDAD_ORGANICA_SIGLA_GFHL)).get(0);
            }else if(idEntidad!=null && idEntidad.equals(Constantes.ID_ENTIDAD_GO)){
                unidOrga=obtenerListadoUnidadOrganica(new UnidadOrganicaFilter(Constantes.UNIDAD_ORGANICA_COD_DEP_SIGA_GO,Constantes.UNIDAD_ORGANICA_SIGLA_GO)).get(0);            
            }
            LOG.info("--->idEntidad="+idEntidad+" idUnidOrga="+unidOrga.getIdUnidadOrganica());
            retorno=unidOrga.getIdUnidadOrganica();
        }catch(Exception e){
            LOG.error("Error obtenerIdUnidadOrganicaByIdEntidadSesion"+e);
        }        
        return retorno;
    }
    
    
	@Override
	public List<UnidadOrganicaDTO> findUnidadOrganica(UnidadOrganicaFilter filtro) {
		LOG.info("");
		List<UnidadOrganicaDTO> listado=null;
        try{
        	listado = unidadOrganicaDAO.findUnidadOrganica(filtro);             	
        }catch(Exception ex){
            LOG.error("",ex);
        }
        return listado;		
	}

	@Override
    @Transactional(readOnly=true)
    public String getBandejaDefault(Long idPersonal){
        LOG.info("getBandejaDefault");
        String bandeja = "";
        
        try{
            PersonalFilter filtroUOD = new PersonalFilter();
            filtroUOD.setIdPersonal(idPersonal);
            filtroUOD.setFlagDefault(Constantes.ESTADO_ACTIVO);
            List<PersonalDTO> listPersonaIdUOD= personalServiceNeg.findPersonal(filtroUOD);
            if(!CollectionUtils.isEmpty(listPersonaIdUOD)){
                PersonalDTO personalUOD=listPersonaIdUOD.get(0);
                
                if(personalUOD.getPersonalUnidadOrganicaDefault()!=null){
                	UnidadOrganicaDTO divUnidOrg=null;
                    List<UnidadOrganicaDTO> unidadUO = findUnidadOrganica(new UnidadOrganicaFilter(personalUOD.getPersonalUnidadOrganicaDefault().getUnidadOrganica().getIdUnidadOrganica(), null));
                    List<UnidadOrganicaDTO> subDivUO = findUnidadOrganica(new UnidadOrganicaFilter(unidadUO.get(0).getIdUnidadOrganicaSuperior(), null));
                    List<UnidadOrganicaDTO> divUO = findUnidadOrganica(new UnidadOrganicaFilter(subDivUO.get(0).getIdUnidadOrganicaSuperior(), null));
                    divUnidOrg=divUO.get(0);
                    CnfUnidOrgaFilter filtro =new CnfUnidOrgaFilter();
                    UnidadOrganicaFilter unidadFilter = new UnidadOrganicaFilter();
                    unidadFilter.setIdUnidadOrganica(divUnidOrg.getIdUnidadOrganica());
                    filtro.setIdUnidadOrganica(unidadFilter);
                    List<CnfUnidOrgaDTO> listaCnfUnidOrga= cnfUnidOrgaServiceNeg.listarCnfUnidOrga(filtro);
                    if(!CollectionUtils.isEmpty(listaCnfUnidOrga)){
                    	List<MaestroColumnaDTO> listaMaestro=maestroColumnaServiceNeg.findByIdMaestroColumna(listaCnfUnidOrga.get(0).getBandejaInps());
                    	if(!CollectionUtils.isEmpty(listaMaestro)){
                    		bandeja=listaMaestro.get(0).getCodigo();
                    	}
                    }
                    
                }
            }
            
        }catch(Exception e){
            LOG.error("Error en getBandejaDefault",e);
        }
        return bandeja;
    }
}
