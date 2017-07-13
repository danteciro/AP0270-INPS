/**
* Resumen		
* Objeto		: ProcesoObligacionTipoDAO.java
* Descripción		: ProcesoObligacionTipoDAO
* Fecha de Creación	: 
* PR de Creación	: 
* Autor			: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE791          17/08/2016      Yadira Piskulich                Abrir Supervision DSR
* 
*/ 

package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.PghProcesoObligacionTipo;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.ProcesoObligacionTipoDAO;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
@Service("ProcesoObligacionTipoDAO")
public class ProcesoObligacionTipoDAOImpl implements ProcesoObligacionTipoDAO{
    
    @Inject
    private CrudDAO crud;
    
    public Integer buscarFlagSupervision(Long idObligacionTipo,Long idActividad,Long idProceso){
    	Integer retorno=0;
    	List<PghProcesoObligacionTipo> lista = buscarProcesoObligacionTipo(idObligacionTipo,idActividad,idProceso);
    	if (lista!=null && lista.size()>0){
    		if(lista.get(0).getFlagSupervision()!=null)
    		{retorno=Integer.parseInt(lista.get(0).getFlagSupervision().toString());}
    	}	
   	   return retorno;
    }
    
    private List<PghProcesoObligacionTipo> buscarProcesoObligacionTipo(Long idObligacionTipo,Long idActividad,Long idProceso){
        System.out.println("ingreso al metodo buscarProcesoObligacionTipo");
        StringBuilder jpql = new StringBuilder();
    	jpql.append("select m ");
    	jpql.append("from PghProcesoObligacionTipo m ");
    	jpql.append(" where m.pghProcesoObligacionTipoPK.idObligacionTipo=");
    	jpql.append(idObligacionTipo);
    	jpql.append(" and  m.pghProcesoObligacionTipoPK.idActividad=");
    	jpql.append(idActividad);
    	jpql.append(" and m.pghProcesoObligacionTipoPK.idProceso=");
    	jpql.append(idProceso);
        jpql.append(" and m.estado='1' ");
    	Query query=this.crud.getEm().createQuery(jpql.toString());
    	//LOG.info("query-->"+query);
    	List<PghProcesoObligacionTipo> lista = (List<PghProcesoObligacionTipo> )query.getResultList();
        System.out.println("cantidad |"+lista.size()+"|");
        return lista;
    }
    
    public String buscarCodigoFlujoSupervINPS(Long idObligacionTipo,Long idActividad,Long idProceso){
    	String retorno="";
    	List<PghProcesoObligacionTipo> lista = buscarProcesoObligacionTipo(idObligacionTipo,idActividad,idProceso);
    	if (lista!=null && lista.size()>0){
            if(lista.get(0).getFlujoSupervInps()!=null){
                retorno=lista.get(0).getFlujoSupervInps().getCodigo();
            }
    	}	
        return retorno;
    }
}
