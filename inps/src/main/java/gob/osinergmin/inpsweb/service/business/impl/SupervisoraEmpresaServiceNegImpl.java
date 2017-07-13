/**
* Resumen		
* Objeto		: SupervisoraEmpresaServiceNegImpl.java
* Descripción		: Clase de la capa de negocio que contiene la implementación de los métodos declarados en la clase interfaz SupervisoraEmpresaServiceNeg
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Giancarlo Villanueva Andrade
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     10/05/2016      Mario Dioses Fernandez          Listar Empresas Supervisoras según filtros definidos para Gerencia (Unidad Organica)
*
*/

package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.service.business.SupervisoraEmpresaServiceNeg;
import gob.osinergmin.inpsweb.service.dao.ConfFiltroEmpSupDAO;
import gob.osinergmin.inpsweb.service.dao.SupervisoraEmpresaDAO;
import gob.osinergmin.mdicommon.domain.base.BaseConstantesOutBean;
import gob.osinergmin.mdicommon.domain.dto.ConfFiltroEmpSupDTO;
import gob.osinergmin.mdicommon.domain.dto.LocadorDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisoraEmpresaDTO;
import gob.osinergmin.mdicommon.domain.in.ObtenerSupervisoraEmpresaInRO;
import gob.osinergmin.mdicommon.domain.out.ObtenerSupervisoraEmpresaOutRO;
import gob.osinergmin.mdicommon.domain.ui.ConfFiltroEmpSupFilter;
import gob.osinergmin.mdicommon.domain.ui.SupervisoraEmpresaFilter;
import gob.osinergmin.mdicommon.remote.SupervisoraEmpresaEndpoint;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jpiro
 */
@Service("supervisoraEmpresaServiceNeg")
public class SupervisoraEmpresaServiceNegImpl implements SupervisoraEmpresaServiceNeg {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(SupervisoraEmpresaServiceNeg.class);
    @Inject
    private SupervisoraEmpresaEndpoint supervisoraEmpresaEndpoint;
    /* OSINE_SFS-480 - RSIS 11 - Inicio */
    @Inject
    private ConfFiltroEmpSupDAO confFiltroEmpSupDAO; 
    @Inject
    private SupervisoraEmpresaDAO supervisoraEmpresaDAO; 
    /* OSINE_SFS-480 - RSIS 11 - Fin */
    @Override
    public List<SupervisoraEmpresaDTO> listarSupervisoraEmpresa(SupervisoraEmpresaFilter filtro) {
        ObtenerSupervisoraEmpresaInRO request = new ObtenerSupervisoraEmpresaInRO();
        request.setFiltro(filtro);
        
        ObtenerSupervisoraEmpresaOutRO response;
        
        //Llamada al servicio
        response = supervisoraEmpresaEndpoint.listarSupervisoraEmpresa(request);
        if(response.getCodigoResultado().equals(BaseConstantesOutBean.SUCCESS)) {
            LOG.info("cuenta:"+response.getContador().intValue());
        }
        
        if(response.getContador().intValue() > 0) {
            return response.getListado();
        }else
            return null;
    }
    /* OSINE_SFS-480 - RSIS 11 - Inicio */
    @Override
    @Transactional(readOnly=true)
    public List<SupervisoraEmpresaDTO> listarSupervisoraEmpresaConfFiltros(
    SupervisoraEmpresaFilter filtro) {
    LOG.info("Neg listarLocadorConfFiltros");
    List<SupervisoraEmpresaDTO> retorno=null;
    List<ConfFiltroEmpSupDTO> listaConfFiltroEmpSup = new ArrayList<ConfFiltroEmpSupDTO>();
    ConfFiltroEmpSupFilter filtroConfEmpSup=new ConfFiltroEmpSupFilter(); 
    try {
        filtroConfEmpSup.setIdUnidadOrganica(filtro.getIdUnidadOrganica());
        if(filtro.getIdUnidadOrganica()!=null && !filtro.getIdUnidadOrganica().equals("")){        
        	listaConfFiltroEmpSup=confFiltroEmpSupDAO.findConfFiltroEmpSup(filtroConfEmpSup);        
        }
        retorno = supervisoraEmpresaDAO.listarSupervisoraEmpresaConfFiltros(filtro, listaConfFiltroEmpSup);
    } catch(Exception ex){
        LOG.error("",ex);
    }
        return retorno;
    } 
    
    @Override
    @Transactional(readOnly = true)
    public SupervisoraEmpresaDTO getById(Long idSupervisoraEmpresa){
        LOG.info("getById");
        SupervisoraEmpresaDTO retorno=null;
        try {
            retorno = supervisoraEmpresaDAO.getById(idSupervisoraEmpresa);
        } catch(Exception ex){
            LOG.error("Error getById",ex);
        }
        return retorno;
    }
    /* OSINE_SFS-480 - RSIS 11 - Fin */
}
