package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.PghComplementoDetsupValor;
import gob.osinergmin.inpsweb.domain.PghComplementoDetsupervision;
import gob.osinergmin.inpsweb.domain.builder.ComplementoDetSupValorBuilder;
import gob.osinergmin.inpsweb.domain.builder.ComplementoDetSupervisionBuilder;
import gob.osinergmin.inpsweb.service.dao.ComplementoDetSupervisionDAO;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.exception.ComplementoDetSupervisionException;
import gob.osinergmin.mdicommon.domain.dto.ComplementoDetSupValorDTO;
import gob.osinergmin.mdicommon.domain.dto.ComplementoDetSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.ComplementoDetSupValorFilter;
import gob.osinergmin.mdicommon.domain.ui.ComplementoDetSupervisionFilter;
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
@Service("complementoDetSupervisionDAO")
public class ComplementoDetSupervisionDAOImpl implements ComplementoDetSupervisionDAO {
    private static final Logger LOG = LoggerFactory.getLogger(ComplementoDetSupervisionDAOImpl.class);
    @Inject
    private CrudDAO crud;
    
    @Override
    public ComplementoDetSupervisionDTO guardarComplementoDetSupervision(ComplementoDetSupervisionDTO complementoDetSupervisionDTO, UsuarioDTO usuarioDTO) throws ComplementoDetSupervisionException{
        LOG.error("guardarComplementoDetSupervision");
        ComplementoDetSupervisionDTO retorno= new ComplementoDetSupervisionDTO();
        try {
            PghComplementoDetsupervision registroDAO = ComplementoDetSupervisionBuilder.toComplementoDetSupervisionDomain(complementoDetSupervisionDTO);
            registroDAO.setDatosAuditoria(usuarioDTO);
            crud.create(registroDAO);
            
            retorno=ComplementoDetSupervisionBuilder.toComplementoDetSupervisionDTO(registroDAO);
        } catch (Exception e) {
            LOG.error("error en guardarComplementoDetSupervision",e);
            throw new ComplementoDetSupervisionException(e.getMessage(), e);
        }
        return retorno;
    }
    
    @Override
    public ComplementoDetSupValorDTO guardarComplementoDetSupValor(ComplementoDetSupValorDTO complementoDetSupValorDTO, UsuarioDTO usuarioDTO) throws ComplementoDetSupervisionException{
        LOG.error("guardarComplementoDetSupValor");
        ComplementoDetSupValorDTO retorno= new ComplementoDetSupValorDTO();
        try {
            PghComplementoDetsupValor registroDAO = ComplementoDetSupValorBuilder.toComplementoDetSupValorDomain(complementoDetSupValorDTO);
            registroDAO.setDatosAuditoria(usuarioDTO);
            crud.create(registroDAO);
            
            retorno=ComplementoDetSupValorBuilder.toComplementoDetSupValorDTO(registroDAO);
        } catch (Exception e) {
            LOG.error("error en guardarComplementoDetSupValor",e);
            throw new ComplementoDetSupervisionException(e.getMessage(), e);
        }
        return retorno;
    }
    
    @Override
    public Long eliminaComplementoDetSupValor(Long idComplementoDetSupervision) throws ComplementoDetSupervisionException{
        LOG.error("eliminaComplementoDetSupValor");
        try {
            StringBuilder jpql = new StringBuilder();
            jpql.append(" delete pgh_complemento_detsup_valor cdsv where cdsv.id_complemento_detsupervision = :idComplementoDetSupervision ");
            String queryString = jpql.toString();
            
            Query query = crud.getEm().createNativeQuery(queryString);
            query.setParameter("idComplementoDetSupervision", idComplementoDetSupervision);
            int val = query.executeUpdate();
            LOG.info("Se Eliminaron " + val + " Registros de pgh_complemento_detsup_valor");            
        } catch (Exception e) {
            LOG.error("error en eliminaComplementoDetSupValor",e);
            throw new ComplementoDetSupervisionException(e.getMessage(), e);
        }
        return idComplementoDetSupervision;
    }
    
    @Override
    public List<ComplementoDetSupervisionDTO> find(ComplementoDetSupervisionFilter filtro) throws ComplementoDetSupervisionException{
        LOG.info("find");
        List<ComplementoDetSupervisionDTO> retorno = null;
        try {
            
            StringBuilder jpql = new StringBuilder();
            
            jpql.append(" SELECT new PghComplementoDetsupervision(cds.idComplementoDetsupervision,coco.idComentarioComplemento,co.idComplemento,co.etiquetaComentario) "
                    + "FROM PghComplementoDetsupervision cds "
                    + "left join cds.idComentarioComplemento coco "
                    + "left join coco.idComplemento co "
                    + "WHERE cds.estado=1 ");
            //WHERE
            if (filtro.getIdComentarioDetSupervision()!= null) {
                jpql.append(" AND cds.idComentarioDetsupervision.idComentarioDetsupervision = :idComentarioDetsupervision ");
            }
            Query query = this.crud.getEm().createQuery(jpql.toString());
            //settear parametros
            if (filtro.getIdComentarioDetSupervision()!= null) {
                query.setParameter("idComentarioDetsupervision", filtro.getIdComentarioDetSupervision());
            }
            retorno = ComplementoDetSupervisionBuilder.toListComplementoDetSupervisionDto(query.getResultList());
        } catch (Exception e) {
            LOG.error("error en find", e);
        }
        
        return retorno;
    }
    
    @Override
    public List<ComplementoDetSupValorDTO> findComplementoDetSupValor(ComplementoDetSupValorFilter filtro) throws ComplementoDetSupervisionException{
        LOG.info("findComplementoDetSupValor");
        List<ComplementoDetSupValorDTO> retorno = null;
        try {
            
            StringBuilder jpql = new StringBuilder();
            
            jpql.append(" SELECT cdsv FROM PghComplementoDetsupValor cdsv WHERE cdsv.estado=1 ");
            //WHERE
            if (filtro.getIdComplementoDetSupervision()!= null) {
                jpql.append(" AND cdsv.idComplementoDetsupervision.idComplementoDetsupervision = :idComplementoDetsupervision ");
            }
            Query query = this.crud.getEm().createQuery(jpql.toString());
            //settear parametros
            if (filtro.getIdComplementoDetSupervision()!= null) {
                query.setParameter("idComplementoDetsupervision", filtro.getIdComplementoDetSupervision());
            }
            retorno = ComplementoDetSupValorBuilder.toListComplementoDetSupValorDto(query.getResultList());
        } catch (Exception e) {
            LOG.error("error en findComplementoDetSupValor", e);
        }
        
        return retorno;
    }
}
