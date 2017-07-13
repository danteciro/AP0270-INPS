/**
* Resumen		
* Objeto		: LocadorDAOImpl.java
* Descripcion		: Clase que contiene la implementacion de los metodos declarados en la clase interfaz LocadorDAO
* Fecha de Creacion	: 09/06/2016
* PR de Creacion	: OSINE_SFS-480
* Autor			: Mario Dioses Fernandez
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripcion
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     09/06/2016      Mario Dioses Fernandez      Listar Empresas Supervisoras segon filtros definidos para Gerencia (Unidad Organica).
* OSINE_MAN_DSR_0032  | 25/06/2017   |   Carlos Quijano Chavez      |      La asignacion de orden de servicio no debe ser restringida por la ubicacion de destaque del supervisor
*/

package gob.osinergmin.inpsweb.service.dao.impl;
import gob.osinergmin.inpsweb.domain.MdiLocador;
import gob.osinergmin.inpsweb.domain.builder.LocadorBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.LocadorDAO;
import gob.osinergmin.inpsweb.service.exception.LocadorException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.ConfFiltroEmpSupDTO;
import gob.osinergmin.mdicommon.domain.dto.LocadorDTO;
import gob.osinergmin.mdicommon.domain.ui.LocadorFilter;

import java.util.List;

import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class LocadorDAOImpl implements LocadorDAO{	
	private static final Logger LOG = LoggerFactory.getLogger(LocadorDAOImpl.class);
	
	@Inject
    private CrudDAO crud;
	
	@Override
    public List<LocadorDTO> listarLocadorConfFiltros(LocadorFilter filtro, List<ConfFiltroEmpSupDTO> listaConfFiltroEmpSup) throws LocadorException {        
        List<LocadorDTO> listado=null;
        try {
            Query query = getFindQuery(filtro, listaConfFiltroEmpSup);            
            listado = LocadorBuilder.toListLocadorDtoBusqueda(query.getResultList());
        }catch(Exception e){
            LOG.error("Exception en find, locadorServiceImpl");
            LOG.error(e.getMessage());
        }
        return listado;
    }
	
	private Query getFindQuery(LocadorFilter filtro, List<ConfFiltroEmpSupDTO> listaConfFiltroEmpSup) {
        String queryString = null;
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT distinct new MdiLocador("
        			+ "p.idLocador,p.apellidoMaterno,p.apellidoPaterno,p.primerNombre,p.segundoNombre,p.numeroDocumento, "
                    + "p.ruc,p.telefonoContacto,p.telefonoPersonal,p.estado,p.nombreCompleto, p.primerNombre||' '||p.segundoNombre||' '||p.apellidoPaterno||' '||p.apellidoMaterno as nombreCompletoArmado, "
                    + "td.idMaestroColumna, "
        			+ "td.descripcion,p.fechaNacimiento,p.sexo,p.correoElectronicoPersonal,p.idProfesion) "
        		  + "FROM MdiLocador p "
        			+ "LEFT JOIN p.pghEmprSupeActiObliList epab "
        			+ "LEFT JOIN p.idTipoDocumentoIdentidad td "
        			+ "LEFT JOIN p.mdiContratoEmpresaLocadorList cel "
        			+ "LEFT JOIN cel.mdiLocadorDestaqueList ld "
        			+ "LEFT JOIN ld.mdiMacroRegionXRegion mrr "
        			+ "LEFT JOIN mrr.mdiRegion r "
        			+ "LEFT JOIN r.pghUbigeoRegionList ur "
        			+ "LEFT JOIN ur.mdiUbigeo ub "
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
    			/* OSINE_MAN_DSR_0032 - Inicio */
//    			else if(registro.getIdFiltro().getCodigo().equals(Constantes.CODIGO_FILTRO_EMP_SUP_UBIG)){
//    				if(filtro.getIdDepartamento()!=null) {
//	        			jpql.append("and ub.mdiUbigeoPK.idDepartamento="+filtro.getIdDepartamento()+" ");				
//	        		}
//    				if(filtro.getIdProvincia()!=null) {
//	        			jpql.append("and ub.mdiUbigeoPK.idProvincia="+filtro.getIdProvincia()+" ");				
//	        		}
//    				if(filtro.getIdDistrito()!=null) {
//	        			jpql.append("and ub.mdiUbigeoPK.idDistrito="+filtro.getIdDistrito()+" ");				
//	        		}
//    			} 
    			/* OSINE_MAN_DSR_0032 - Inicio */
    		}
    	}
    	jpql.append("order by p.nombreCompleto ");
		queryString = jpql.toString();
		Query query = this.crud.getEm().createQuery(queryString);
        return query;
    }
        
    @Override
    public LocadorDTO getById(Long idLocador) throws LocadorException {        
        LocadorDTO retorno=null;
        try {
            retorno = LocadorBuilder.toLocadorDto(crud.find(idLocador, MdiLocador.class));
        }catch(Exception e){
            LOG.error("Error en getById",e);
        }
        return retorno;
    }

    
	
    
}