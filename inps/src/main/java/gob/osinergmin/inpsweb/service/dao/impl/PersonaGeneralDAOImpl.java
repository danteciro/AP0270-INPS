package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.PghPersonaGeneral;
import gob.osinergmin.inpsweb.domain.builder.PersonaGeneralBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.PersonaGeneralDAO;
import gob.osinergmin.inpsweb.service.exception.PersonaGeneralException;
import gob.osinergmin.inpsweb.service.exception.SupervisionException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.PersonaGeneralDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.PersonaGeneralFilter;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("personaGeneralDAO")
public class PersonaGeneralDAOImpl implements PersonaGeneralDAO {

    private static final Logger LOG = LoggerFactory.getLogger(PersonaGeneralDAOImpl.class);
    @Inject
    private CrudDAO crud;

    @Override
    public List<PersonaGeneralDTO> find(PersonaGeneralFilter filtro) throws PersonaGeneralException {
        LOG.info("PersonaGeneralDAOImpl: find-inicio");
        List<PersonaGeneralDTO> retorno = null;
        try {
            String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT pg ");
            jpql.append(" FROM PghPersonaGeneral pg ");
            jpql.append(" WHERE pg.estado = 1 ");

            if (filtro.getIdPersonaGeneral() != null) {
                jpql.append(" AND pg.idPersonaGeneral = :idPersonaGeneral ");
            }
            if (filtro.getIdTipoDocumento() != null) {
                jpql.append(" AND pg.idTipoDocumento = :idTipoDocumento ");
            }
            if (filtro.getNumeroDocumento() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getNumeroDocumento())) {
                jpql.append(" AND pg.numeroDocumento = :numeroDocumento ");
            }
            if (filtro.getFlagUltimo() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getFlagUltimo())) {
                jpql.append(" AND pg.flagUltimo = :flagUltimo ");
            }
            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            if (filtro.getIdPersonaGeneral() != null) {
                query.setParameter("idPersonaGeneral", filtro.getIdPersonaGeneral());
            }
            if (filtro.getIdTipoDocumento() != null) {
                query.setParameter("idTipoDocumento", filtro.getIdTipoDocumento());
            }
            if (filtro.getNumeroDocumento() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getNumeroDocumento())) {
                query.setParameter("numeroDocumento", filtro.getNumeroDocumento());
            }
            if (filtro.getFlagUltimo() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getFlagUltimo())) {
                query.setParameter("flagUltimo", filtro.getFlagUltimo());
            }
            retorno = PersonaGeneralBuilder.toListPersonaGeneralDTO(query.getResultList());
        } catch (Exception e) {
            LOG.error("error en find", e);
        }
        LOG.info("PersonaGeneralDAOImpl: find-fin");
        return retorno;
    }

    @Override
    public PersonaGeneralDTO registrar(PersonaGeneralDTO personaGeneralDTO, UsuarioDTO usuarioDTO) throws SupervisionException {
        LOG.info("PersonaGeneralDAOImpl : registrar-inicio");
        PersonaGeneralDTO retorno = null;
        try {
            PghPersonaGeneral personaGeneral = PersonaGeneralBuilder.toPersonaGeneralDomain(personaGeneralDTO);
            personaGeneral.setDatosAuditoria(usuarioDTO);
            personaGeneral.setEstado(Constantes.ESTADO_ACTIVO);
            personaGeneral.setFlagUltimo(Constantes.FLAG_ULTIMO_PERSONA_GENERAL);
            crud.create(personaGeneral);
            retorno = PersonaGeneralBuilder.toPersonaGeneralDTO(personaGeneral);
        } catch (Exception e) {
            LOG.error("Error en registrar", e);
            SupervisionException ex = new SupervisionException(e.getMessage(), e);
            throw ex;
        }
        LOG.info("PersonaGeneralDAOImpl : registrar-fin");
        return retorno;
    }

    @Override
    public PersonaGeneralDTO actualizar(PersonaGeneralDTO personaGeneralDTO, UsuarioDTO usuarioDTO) throws SupervisionException {
        LOG.info("PersonaGeneralDAOImpl : actualizar-inicio");
        PersonaGeneralDTO retorno = null;
        try {
            PghPersonaGeneral personaGeneral = PersonaGeneralBuilder.toPersonaGeneralDomain(personaGeneralDTO);
            personaGeneral.setDatosAuditoria(usuarioDTO);
            crud.update(personaGeneral);
            retorno = PersonaGeneralBuilder.toPersonaGeneralDTO(personaGeneral);
        } catch (Exception e) {
            LOG.error("Error en actualizar", e);
            SupervisionException ex = new SupervisionException(e.getMessage(), e);
            throw ex;
        }
        LOG.info("PersonaGeneralDAOImpl : actualizar-fin");
        return retorno;
    }
}
