package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.PghComentarioDetsupervision;
import gob.osinergmin.inpsweb.domain.builder.ComentarioDetSupervisionBuilder;
import gob.osinergmin.inpsweb.service.dao.ComentarioDetSupervisionDAO;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.exception.ComentarioDetSupervisionException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.ComentarioDetSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.ComentarioDetSupervisionFilter;
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
@Service("comentarioDetSupervisionDAO")
public class ComentarioDetSupervisionDAOImpl implements ComentarioDetSupervisionDAO{
    private static final Logger LOG = LoggerFactory.getLogger(EscenarioIncumplidoDAOImpl.class);
    @Inject
    private CrudDAO crud;
    
    @Override
    public ComentarioDetSupervisionDTO guardarComentarioDetSupervision(ComentarioDetSupervisionDTO comentarioDetSupervisionDTO, UsuarioDTO usuarioDTO) throws ComentarioDetSupervisionException{
        LOG.error("guardarComentarioDetSupervision");
        ComentarioDetSupervisionDTO retorno= new ComentarioDetSupervisionDTO();
        try {
            PghComentarioDetsupervision registroDAO = ComentarioDetSupervisionBuilder.toComentarioDetSupervisionDomain(comentarioDetSupervisionDTO);
            registroDAO.setDatosAuditoria(usuarioDTO);
            crud.create(registroDAO);
            
            retorno=ComentarioDetSupervisionBuilder.toComentarioDetSupervisionDTO(registroDAO);
        } catch (Exception e) {
            LOG.error("error en guardarComentarioDetSupervision",e);
            throw new ComentarioDetSupervisionException(e.getMessage(), e);
        }
        return retorno;
    }
    
    @Override
    public ComentarioDetSupervisionDTO cambiaEstadoComentarioDetSupervision(ComentarioDetSupervisionDTO comentarioDetSupervisionDTO, UsuarioDTO usuarioDTO) throws ComentarioDetSupervisionException{
        LOG.error("cambiaEstadoComentarioDetSupervision");
        ComentarioDetSupervisionDTO retorno= new ComentarioDetSupervisionDTO();
        try {
            PghComentarioDetsupervision registroDAO = crud.find(comentarioDetSupervisionDTO.getIdComentarioDetSupervision(), PghComentarioDetsupervision.class);
            registroDAO.setEstado(comentarioDetSupervisionDTO.getEstado());
            registroDAO.setDatosAuditoria(usuarioDTO);
            crud.update(registroDAO);
            
            retorno=ComentarioDetSupervisionBuilder.toComentarioDetSupervisionDTO(registroDAO);
        } catch (Exception e) {
            LOG.error("error en cambiaEstadoComentarioDetSupervision",e);
            throw new ComentarioDetSupervisionException(e.getMessage(), e);
        }
        return retorno;
    }
    
    public List<ComentarioDetSupervisionDTO> find(ComentarioDetSupervisionFilter filtro) throws ComentarioDetSupervisionException{
        LOG.info("find");
        List<ComentarioDetSupervisionDTO> retorno = null;
        try {
            
            StringBuilder jpql = new StringBuilder();
            
            jpql.append(" SELECT new PghComentarioDetsupervision ");
            jpql.append(" ( ");
            jpql.append(" cds.idComentarioDetsupervision, ds.idDetalleSupervision, ei.idEscenarioIncumplido, ei.idEsceIncumplimiento.idEsceIncumplimiento, ci.idComentarioIncumplimiento ,ci.descripcion ");
            jpql.append(" ) ");
            jpql.append(" FROM PghComentarioDetsupervision cds ");
            jpql.append(" LEFT JOIN cds.idDetalleSupervision ds ");
            jpql.append(" LEFT JOIN cds.idEscenarioIncumplido ei ");
            jpql.append(" LEFT JOIN cds.idComentarioIncumplimiento ci ");
            jpql.append(" WHERE cds.estado = 1 ");
            
            if (filtro.getIdDetalleSupervision() != null) {
                jpql.append(" AND ds.idDetalleSupervision = :idDetalleSupervision ");
            }
            if (filtro.getIdEsceIncumplimiento() != null) {
                jpql.append(" AND ei.idEsceIncumplimiento.idEsceIncumplimiento = :idEsceIncumplimiento ");
            }
            if (filtro.getIdEscenarioIncumplido()!= null) {
                jpql.append(" AND ei.idEscenarioIncumplido = :idEscenarioIncumplido ");
            }
            if(filtro.getFlagOrderByDescripcion()!=null && filtro.getFlagOrderByDescripcion().equals(Constantes.ESTADO_ACTIVO)){
                jpql.append(" ORDER BY ci.descripcion ");
            }
            Query query = this.crud.getEm().createQuery(jpql.toString());
            //settear parametros
            if (filtro.getIdDetalleSupervision()!= null) {
                query.setParameter("idDetalleSupervision", filtro.getIdDetalleSupervision());
            }
            if (filtro.getIdEsceIncumplimiento()!= null) {
                query.setParameter("idEsceIncumplimiento", filtro.getIdEsceIncumplimiento());
            }
            if (filtro.getIdEscenarioIncumplido()!= null) {
                query.setParameter("idEscenarioIncumplido", filtro.getIdEscenarioIncumplido());
            }
            retorno = ComentarioDetSupervisionBuilder.toListComentarioDetSupervisionDto(query.getResultList());
        } catch (Exception e) {
            LOG.error("error en find", e);
        }
        
        return retorno;
    }
}
