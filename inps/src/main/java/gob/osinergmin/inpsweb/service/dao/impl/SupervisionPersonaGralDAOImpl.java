package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.PghSupervisionPersonaGral;
import gob.osinergmin.inpsweb.domain.builder.SupervisionPersonaGralBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.SupervisionPersonaGralDAO;
import gob.osinergmin.inpsweb.service.exception.SupervisionException;
import gob.osinergmin.inpsweb.service.exception.SupervisionPersonaGralException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.SupervisionPersonaGralDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.SupervisionPersonaGralFilter;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("supervisionPersonaGralDAO")
public class SupervisionPersonaGralDAOImpl implements SupervisionPersonaGralDAO{
	private static final Logger LOG = LoggerFactory.getLogger(SupervisionPersonaGralDAOImpl.class);

	@Inject
    private CrudDAO crud;
	
	@Override
	public List<SupervisionPersonaGralDTO> find(SupervisionPersonaGralFilter filtro)throws SupervisionPersonaGralException {
		LOG.info("SupervisionPersonaGralDAOImpl: find-inicio");
		List<SupervisionPersonaGralDTO> retorno=null;
        try{
            String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT spg ");
            jpql.append(" FROM PghSupervisionPersonaGral spg ");
            jpql.append(" WHERE spg.estado = 1 ");
            
            if(filtro.getIdSupervision() !=null){
            	jpql.append(" AND spg.pghSupervision.idSupervision = :idSupervision ");
            }
            //if(filtro.getFlagUltimo() !=null && !Constantes.CONSTANTE_VACIA.equals(filtro.getFlagUltimo())){
           // 	jpql.append(" AND spg.pghPersonaGeneral.flagUltimo = :flagUltimo ");
           // }
            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            if(filtro.getIdSupervision() !=null){
            	query.setParameter("idSupervision",filtro.getIdSupervision());
            }
           // if(filtro.getFlagUltimo() !=null && !Constantes.CONSTANTE_VACIA.equals(filtro.getFlagUltimo())){
            //	query.setParameter("flagUltimo",filtro.getFlagUltimo());
            //}
            retorno= SupervisionPersonaGralBuilder.toListSupervisionPersonaGralDTO(query.getResultList());
        }catch(Exception e){
            LOG.error("error en find",e);
        }
        LOG.info("SupervisionPersonaGralDAOImpl: find-fin");
        return retorno;
	}

	@Override
	public SupervisionPersonaGralDTO registrar(SupervisionPersonaGralDTO supervisionPersonaGralDTO, UsuarioDTO usuarioDTO)
			throws SupervisionException {
		LOG.info("SupervisionPersonaGralDAOImpl : registrar-inicio");
		SupervisionPersonaGralDTO retorno=null;
        try{
            PghSupervisionPersonaGral supervisionPersonaGeneral = SupervisionPersonaGralBuilder.toSupervisionPersonaGralDomain(supervisionPersonaGralDTO);
            supervisionPersonaGeneral.setDatosAuditoria(usuarioDTO);
            supervisionPersonaGeneral.setEstado(Constantes.ESTADO_ACTIVO);
            crud.create(supervisionPersonaGeneral);
            retorno=SupervisionPersonaGralBuilder.toSupervisionPersonaGralDTO(supervisionPersonaGeneral);
        }catch(Exception e){
            LOG.error("Error en registrar",e);
            SupervisionException ex = new SupervisionException(e.getMessage(), e);
            throw ex;
        }
        LOG.info("SupervisionPersonaGralDAOImpl : registrar-fin");
        return retorno;
	}

	@Override
	public SupervisionPersonaGralDTO actualizar(SupervisionPersonaGralDTO supervisionPersonaGralDTO,UsuarioDTO usuarioDTO) 
			throws SupervisionException {
		LOG.info("SupervisionPersonaGralDAOImpl : actualizar-inicio");
		SupervisionPersonaGralDTO retorno=null;
        try{
            PghSupervisionPersonaGral supervisionPersonaGeneral = SupervisionPersonaGralBuilder.toSupervisionPersonaGralDomain(supervisionPersonaGralDTO);
            supervisionPersonaGeneral.setDatosAuditoria(usuarioDTO);
            crud.update(supervisionPersonaGeneral);
            retorno=SupervisionPersonaGralBuilder.toSupervisionPersonaGralDTO(supervisionPersonaGeneral);
        }catch(Exception e){
            LOG.error("Error en actualizar",e);
            SupervisionException ex = new SupervisionException(e.getMessage(), e);
            throw ex;
        }
        LOG.info("SupervisionPersonaGralDAOImpl : actualizar-fin");
        return retorno;
	}
	
	@Override
	public void eliminar(SupervisionPersonaGralDTO supervisionPersonaGralDTO)throws SupervisionException {
		LOG.info("SupervisionPersonaGralDAOImpl : eliminar-inicio");
        try{
        	PghSupervisionPersonaGral supervisionPersonaGeneral = SupervisionPersonaGralBuilder.toSupervisionPersonaGralDomain(supervisionPersonaGralDTO);
            crud.deleteCompuesto(PghSupervisionPersonaGral.class ,supervisionPersonaGeneral.getPghSupervisionPersonaGralPK());
        }catch(Exception e){
            LOG.error("Error en eliminar",e);
            SupervisionException ex = new SupervisionException(e.getMessage(), e);
            throw ex;
        }
        LOG.info("SupervisionPersonaGralDAOImpl : eliminar-fin");
	}

}
