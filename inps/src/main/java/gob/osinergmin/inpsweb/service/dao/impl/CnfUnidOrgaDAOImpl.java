/**
* Resumen		
* Objeto			: CnfUnidOrgaDAOImpl.java
* Descripción		: Clase que contiene la implementación de los métodos declarados en la 
* 					  clase interfaz CnfUnidOrgaDAO.
* Fecha de Creación	: 25/10/2016.
* PR de Creación	: OSINE_SFS-1344
* Autor				: Hernán Torres.
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
*
*/

package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.builder.CnfUnidOrgaBuilder;
import gob.osinergmin.inpsweb.service.dao.CnfUnidOrgaDAO;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.mdicommon.domain.dto.CnfUnidOrgaDTO;
import gob.osinergmin.mdicommon.domain.ui.CnfUnidOrgaFilter;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("CnfUnidOrgaDAO")
public class CnfUnidOrgaDAOImpl implements CnfUnidOrgaDAO {
	
	private static final Logger LOG = LoggerFactory.getLogger(CnfUnidOrgaDAOImpl.class);

    @Inject
    private CrudDAO crud;
	
    public List<CnfUnidOrgaDTO> listarCnfUnidOrga(CnfUnidOrgaFilter filtro){
    	LOG.info("DAO listarSupeCampRechCarga");
        List<CnfUnidOrgaDTO> listado=null;
        try{
            Query query = getFindQueryListarCnfUnidOrga(filtro);
            listado = CnfUnidOrgaBuilder.toListCnfUnidOrgaDto(query.getResultList());
        }catch(Exception e){
            LOG.info("Error en listarSupeCampRechCarga",e);
        }
        return listado;
    }
    
    private Query getFindQueryListarCnfUnidOrga(CnfUnidOrgaFilter filtro) {
    	LOG.info("DAO getFindQueryListarCnfUnidOrga");
        Query query=null;
        try{
            if(filtro.getIdUnidadOrganica()!=null && !filtro.getIdUnidadOrganica().getIdUnidadOrganica().equals("")){
                query = crud.getEm().createNamedQuery("NpsCnfUnidOrga.findByIdUnidadOrganica");
            }
            
            if(filtro.getIdUnidadOrganica()!=null && !filtro.getIdUnidadOrganica().getIdUnidadOrganica().equals("")){
                query.setParameter("idUnidadOrganica",filtro.getIdUnidadOrganica().getIdUnidadOrganica());
            }
            
        }catch(Exception e){
            LOG.error("Error getFindQueryListarCnfUnidOrga: "+e.getMessage());
        }
        return query;
    }	
    
}