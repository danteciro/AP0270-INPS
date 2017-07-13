package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.builder.MotivoNoSupervisionBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.MotivoNoSupervisionDAO;
import gob.osinergmin.inpsweb.service.exception.MotivoNoSupervisionException;
import gob.osinergmin.mdicommon.domain.dto.MotivoNoSupervisionDTO;
import gob.osinergmin.mdicommon.domain.ui.MotivoNoSupervisionFilter;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("motivoNoSupervisionDAO")
public class MotivoNoSupervisionDAOImpl implements MotivoNoSupervisionDAO{
	private static final Logger LOG = LoggerFactory.getLogger(MotivoNoSupervisionDAOImpl.class);
	
	@Inject
	private CrudDAO crud;

	@Override
	public List<MotivoNoSupervisionDTO> findAll()throws MotivoNoSupervisionException {
		LOG.info("MotivoNoSupervisionDAOImpl : findAll-inicio");
		List<MotivoNoSupervisionDTO> retorno=null;
        try{
        	Query query = crud.getEm().createNamedQuery("PghMotivoNoSupervision.findAll");
            retorno=MotivoNoSupervisionBuilder.toListMotivoNoSupervisionDto(query.getResultList());
        }catch(Exception e){
            LOG.error("Error en findAll",e);
            MotivoNoSupervisionException ex = new MotivoNoSupervisionException(e.getMessage(), e);
            throw ex;
        }
        LOG.info("MotivoNoSupervisionDAOImpl : findAll-fin");
        return retorno;
	}

	@Override
	public List<MotivoNoSupervisionDTO> findMotivoNoSupervision(MotivoNoSupervisionFilter filtro)
			throws MotivoNoSupervisionException {
		LOG.info("MotivoNoSupervisionDAOImpl: findMotivoNoSupervision-inicio");
		List<MotivoNoSupervisionDTO> retorno=null;
        try{
            String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT mns "
                + "FROM PghMotivoNoSupervision mns "
                + "WHERE mns.estado=1 ");
            if(filtro.getIdMotivoNoSupervision()!=null){
                jpql.append(" and mns.idMotivoNoSupervision=:idMotivoNoSupervision ");
            }
            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            //settear parametros
            if(filtro.getIdMotivoNoSupervision()!=null){
            	query.setParameter("idMotivoNoSupervision",filtro.getIdMotivoNoSupervision());
            }
            retorno=MotivoNoSupervisionBuilder.toListMotivoNoSupervisionDto(query.getResultList());
        }catch(Exception e){
            LOG.error("error en findMotivoNoSupervision",e);
            MotivoNoSupervisionException ex = new MotivoNoSupervisionException(e.getMessage(), e);
            throw ex;
        }
        LOG.info("MotivoNoSupervisionDAOImpl: findMotivoNoSupervision-fin");
        return retorno;
	}
}
