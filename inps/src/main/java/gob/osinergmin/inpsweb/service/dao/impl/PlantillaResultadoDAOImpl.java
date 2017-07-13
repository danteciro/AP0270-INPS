package gob.osinergmin.inpsweb.service.dao.impl;
import gob.osinergmin.inpsweb.domain.PghPlantillaResultado;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.Query;

import gob.osinergmin.inpsweb.domain.builder.PlantillaResultadoBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.PlantillaResultadoDAO;
import gob.osinergmin.inpsweb.service.exception.PlantillaResultadoException;
import gob.osinergmin.mdicommon.domain.dto.PlantillaResultadoDTO;
import gob.osinergmin.mdicommon.domain.ui.PlantillaResultadoFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("PlantillaResultadoDAO")
public class PlantillaResultadoDAOImpl implements PlantillaResultadoDAO{
    private static final Logger LOG = LoggerFactory.getLogger(PlantillaResultadoDAOImpl.class);
    @Inject
    private CrudDAO crud;
    
    @Override
    public PlantillaResultadoDTO getPlantillaResultado(Long idPlantillaResultado) throws PlantillaResultadoException{
        PlantillaResultadoDTO retorno=PlantillaResultadoBuilder.toPlantillaResultadoDto(crud.find(idPlantillaResultado,PghPlantillaResultado.class));
        return retorno;
    }
    
    @Override
    public List<PlantillaResultadoDTO> listarPlantillaResultado(PlantillaResultadoFilter filtro) {
    List<PlantillaResultadoDTO> retorno=null;
    try{
        String queryString;
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT new PghPlantillaResultado ");
        jpql.append(" ( ");
        jpql.append(" pr.idPlantillaResultado,pr.nombreDocumento,pr.descripcionDocumento,pr.iteracion,pr.flagResultado,pr.estado, ");
        jpql.append(" p.idProceso, ");
        jpql.append(" ot.idObligacionTipo, ");
        jpql.append(" a.idActividad ");
        jpql.append(" ) ");
        jpql.append(" FROM PghPlantillaResultado pr");
        jpql.append(" LEFT JOIN pr.idActividad a ");
        jpql.append(" LEFT JOIN pr.idProceso p ");
        jpql.append(" LEFT JOIN pr.idObligacionTipo ot ");
        jpql.append(" WHERE pr.estado=1 ");
        if(filtro.getIdActividad()!=null){
            jpql.append(" AND a.idActividad=:idActividad ");
        }
        if(filtro.getIdProceso()!=null){
            jpql.append(" AND p.idProceso=:idProceso ");
        }
        if(filtro.getIdObligacionTipo()!=null){
            jpql.append(" AND ot.idObligacionTipo=:idObligacionTipo ");
        }
        if(filtro.getIteracion()>0){
            jpql.append(" AND pr.iteracion=:iteracion ");
        }
        if(filtro.getFlagResultado()!=null){
            jpql.append(" AND pr.flagResultado=:flagResultado ");
        }
        if(filtro.getFlagSupervision()!=null){
            jpql.append(" AND pr.flagSupervision=:flagSupervision ");            
            if(filtro.getFlagSupervision().equals("0")){ //mdiosesf - RSIS5
            	jpql.append(" AND pr.idMotivoNoSupervision=:idMotivoNoSupervision ");
            }
        }        
        jpql.append(" ORDER BY pr.idPlantillaResultado ASC ");

        //Crear QUERY
        queryString = jpql.toString();
        Query query = this.crud.getEm().createQuery(queryString);
//            //settear parametros
        if(filtro.getIdActividad()!=null){
            query.setParameter("idActividad",filtro.getIdActividad());
        }
        if(filtro.getIdProceso()!=null){
            query.setParameter("idProceso",filtro.getIdProceso());
        }
        if(filtro.getIdObligacionTipo()!=null){
            query.setParameter("idObligacionTipo",filtro.getIdObligacionTipo());
        }
        if(filtro.getIteracion()>0){
            query.setParameter("iteracion",filtro.getIteracion());
        }
        if(filtro.getFlagResultado()!=null){
            query.setParameter("flagResultado",filtro.getFlagResultado());
        }
        if(filtro.getFlagSupervision()!=null){
            query.setParameter("flagSupervision",filtro.getFlagSupervision());
            if(filtro.getFlagSupervision().equals("0")){ //mdiosesf //mdiosesf - RSIS5
                query.setParameter("idMotivoNoSupervision",filtro.getIdMotivoNoSupervision());
            }
        }        

        retorno= PlantillaResultadoBuilder.toListPlantillaResultadoDto(query.getResultList());
    }catch(Exception e){
        LOG.error("error en find",e);
    }
        LOG.info("PlantillaResultadoDAOImpl: listarPlantillaResultado-fin");
        return retorno;
    }	
    
}
