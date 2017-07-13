/**
* Resumen		
* Objeto		: ObligacionTipoDAOImpl.java
* Descripción		: Clase que contiene la implementación de los métodos declarados en la clase interfaz ObligacionTipoDAO
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     23/05/2016      Giancarlo Villanueva Andrade        Crear componente de selección de "subtipo de supervisión".Relacionar y adecuar el subtipo de supervisión, el cual deberá depender del tipo de supervisión seleccionado.
*
*/

package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.builder.ObligacionTipoBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.ObligacionTipoDAO;
import gob.osinergmin.inpsweb.service.exception.ObligacionTipoException;
import gob.osinergmin.mdicommon.domain.dto.ObligacionTipoDTO;
import gob.osinergmin.mdicommon.domain.ui.UnidadOrganicaFilter;

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
@Service("obligacionTipoDAO")
public class ObligacionTipoDAOImpl implements ObligacionTipoDAO{
    private static final Logger LOG = LoggerFactory.getLogger(ObligacionTipoDAOImpl.class);   
    @Inject
    private CrudDAO crud;
    
    @Override
    public List<ObligacionTipoDTO> listarObligacionTipo() throws ObligacionTipoException{
        List<ObligacionTipoDTO> retorno=null;
        try{
            Query query = crud.getEm().createNamedQuery("PghObligacionTipo.findAll");
            retorno = ObligacionTipoBuilder.toListObligacionTipoDto(query.getResultList());
        }catch(Exception ex){
            LOG.error("error listarObligacionTipo",ex);
        }
        return retorno;
    } 
    /* OSINE_SFS-480 - RSIS 26 - Inicio */
    @Override
    public List<ObligacionTipoDTO> listarObligacionTipoxSeleccionMuestral() throws ObligacionTipoException{
        List<ObligacionTipoDTO> retorno=null;
        try{
            Query query = crud.getEm().createNamedQuery("PghObligacionTipo.findSupervMuestralAll");
            retorno = ObligacionTipoBuilder.toListObligacionTipoxSupervisionMuestralDto(query.getResultList());
        }catch(Exception ex){
            LOG.error("error listarObligacionTipo",ex);
        }
        return retorno;
    } 
    /* OSINE_SFS-480 - RSIS 26 - Fin */
    /* OSINE_SFS-1344 - Inicio */
	@Override
	public List<ObligacionTipoDTO> listarObligacionTipoxDivision(UnidadOrganicaFilter unidadOrganicaFilter)	throws ObligacionTipoException {
		List<ObligacionTipoDTO> retorno=null;
        try{
        	String queryString;
            StringBuilder jpql = new StringBuilder();
            //arma campos
            jpql.append("SELECT new PghObligacionTipo(pot.idObligacionTipo, pot.nombre) " +
            			"FROM PghObligacionTipo pot " +
            			"LEFT JOIN pot.pghObliTipoUnidOrgaList otuo ");  
            //arma condiciones
            jpql.append("WHERE pot.estado = 1 ");
            jpql.append("and otuo.estado = 1 ");
            if(unidadOrganicaFilter!=null && unidadOrganicaFilter.getIdUnidadOrganica()!=null){
            	jpql.append("and otuo.idUnidadOrganica.idUnidadOrganica =:idUnidadOrganica ");
            }            
            //Crear QUERY
            queryString = jpql.toString();
            LOG.info(queryString);
            Query query = this.crud.getEm().createQuery(queryString);
            //arma filtro
            if(unidadOrganicaFilter!=null && unidadOrganicaFilter.getIdUnidadOrganica()!=null){
            	query.setParameter("idUnidadOrganica",unidadOrganicaFilter.getIdUnidadOrganica());
            }  
            retorno = ObligacionTipoBuilder.toListObligacionTipoxDivisionDto(query.getResultList());
            
        }catch(Exception ex){
            LOG.error("error listarObligacionTipo",ex);
        }
        return retorno;
	}
	/* OSINE_SFS-1344 - Fin */
}
