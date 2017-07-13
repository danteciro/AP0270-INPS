/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao.impl;
import gob.osinergmin.inpsweb.domain.MdiSupervisoraEmpresa;
import gob.osinergmin.inpsweb.domain.builder.LocadorBuilder;
import gob.osinergmin.inpsweb.domain.builder.SupervisionBuilder;
import gob.osinergmin.inpsweb.domain.builder.SupervisoraEmpresaBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.LocadorDAO;
import gob.osinergmin.inpsweb.service.dao.SupervisoraEmpresaDAO;
import gob.osinergmin.inpsweb.service.exception.LocadorException;
import gob.osinergmin.inpsweb.service.exception.SupervisoraEmpresaException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.ConfFiltroEmpSupDTO;
import gob.osinergmin.mdicommon.domain.dto.LocadorDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisoraEmpresaDTO;
import gob.osinergmin.mdicommon.domain.ui.LocadorFilter;
import gob.osinergmin.mdicommon.domain.ui.SupervisoraEmpresaFilter;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
*
* @author mdiosesf
*/

@Service
public class SupervisoraEmpresaDAOImpl implements SupervisoraEmpresaDAO{	
	private static final Logger LOG = LoggerFactory.getLogger(SupervisoraEmpresaDAOImpl.class);
	
	@Inject
    private CrudDAO crud;
	
	@Override
    public List<SupervisoraEmpresaDTO> listarSupervisoraEmpresaConfFiltros(SupervisoraEmpresaFilter filtro, List<ConfFiltroEmpSupDTO> listaConfFiltroEmpSup) throws SupervisoraEmpresaException {        
        List<SupervisoraEmpresaDTO> listado=null;
        try {
            Query query = getFindQuery(filtro, listaConfFiltroEmpSup);            
            listado = SupervisoraEmpresaBuilder.toListSupervisoraEmpresaDtoBusqueda(query.getResultList());
        }catch(Exception e){
            LOG.error("Exception en find, locadorServiceImpl");
            LOG.error(e.getMessage());
        }
        return listado;
    }
	
	private Query getFindQuery(SupervisoraEmpresaFilter filtro, List<ConfFiltroEmpSupDTO> listaConfFiltroEmpSup) {
        String queryString = null;
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT distinct new MdiSupervisoraEmpresa ("
        			+ "p.idSupervisoraEmpresa,p.razonSocial, p.nombreConsorcio,p.ruc,p.paginaWeb,p.tipoEmpresaConstitucion,p.ciiuPrincipal, "
                    + "p.origenInformacion) "                  
        		  + "FROM MdiSupervisoraEmpresa p "
        			+ "LEFT JOIN p.pghEmprSupeActiObliList epab "        		
        		  + "WHERE p.estado = 1 ");
    	for(ConfFiltroEmpSupDTO registro : listaConfFiltroEmpSup) {
    		if(registro.getIdFiltro()!=null){
    			if(registro.getIdFiltro().getCodigo().equals(Constantes.CODIGO_FILTRO_EMP_SUP_PROC)){
    				if(filtro.getIdProceso()!=null) {
	        			jpql.append("and epab.idProceso="+filtro.getIdProceso()+" ");				
	        		}
    			} else if(registro.getIdFiltro().getCodigo().equals(Constantes.CODIGO_FILTRO_EMP_SUP_OBLI)){
    				if(filtro.getIdObligacionTipo()!=null) {
	        			jpql.append("and epab.idObligacionTipo="+filtro.getIdObligacionTipo()+" ");				
	        		}
    			} else if(registro.getIdFiltro().getCodigo().equals(Constantes.CODIGO_FILTRO_EMP_SUP_RUBR)){
    				if(filtro.getIdRubro()!=null) {
	        			jpql.append("and epab.idActividad="+filtro.getIdRubro()+" ");				
	        		}
    			}       		
    		}
    	}		
		queryString = jpql.toString();
		Query query = this.crud.getEm().createQuery(queryString);
        return query;
    }
        
    @Override
    public SupervisoraEmpresaDTO getById(Long idSupervisoraEmpresa) throws SupervisoraEmpresaException {        
        SupervisoraEmpresaDTO retorno=null;
        try {
            retorno = SupervisoraEmpresaBuilder.toSupervisoraEmpresaDTO(crud.find(idSupervisoraEmpresa, MdiSupervisoraEmpresa.class));
        }catch(Exception e){
            LOG.error("Error en getById",e);
        }
        return retorno;
    }
}