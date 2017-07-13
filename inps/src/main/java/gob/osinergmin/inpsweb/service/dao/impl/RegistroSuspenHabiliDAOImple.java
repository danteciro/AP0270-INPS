/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.MdiRegistroSuspenHabili;
import gob.osinergmin.inpsweb.domain.builder.DetalleSupervisionBuilder;
import gob.osinergmin.inpsweb.domain.builder.RegistroSuspenHabiliBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.RegistroSuspenHabiliDAO;
import gob.osinergmin.inpsweb.service.exception.RegistroSuspenHabiliException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.RegistroSuspenHabiliDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author zchaupis
 */
@Repository("registroSuspenHabiliDAO")
public class RegistroSuspenHabiliDAOImple implements RegistroSuspenHabiliDAO {

    private static final Logger LOG = LoggerFactory.getLogger(RegistroSuspenHabiliDAOImple.class);
    @Inject
    private CrudDAO crud;

    @Override
    @Transactional(rollbackFor = RegistroSuspenHabiliException.class)
    public RegistroSuspenHabiliDTO RegistrarSuspensionRegistroHidrocarburo(RegistroSuspenHabiliDTO registroSuspenHabiliDTO, UsuarioDTO usuarioDTO) throws RegistroSuspenHabiliException {
        LOG.info("RegistroSuspenHabiliDAOImple : RegistrarSuspensionRegistroHidrocarburo-inicio");
        RegistroSuspenHabiliDTO retorno = new RegistroSuspenHabiliDTO();
        try {
            MdiRegistroSuspenHabili registrosuspenHabili = RegistroSuspenHabiliBuilder.toRegistroSuspenHabiliDomain(registroSuspenHabiliDTO);
            registrosuspenHabili.setDatosAuditoria(usuarioDTO);
            registrosuspenHabili.setEstado(Constantes.ESTADO_ACTIVO);
            crud.create(registrosuspenHabili);
            retorno = RegistroSuspenHabiliBuilder.toRegistroSuspenHabiliDTO(registrosuspenHabili);
        } catch (Exception e) {
            LOG.error("Error en RegistrarSuspensionRegistroHidrocarburo", e);
            retorno = null;
            RegistroSuspenHabiliException ex = new RegistroSuspenHabiliException(e.getMessage(), e);
            throw ex;
        }
        LOG.info("RegistroSuspenHabiliDAOImple : RegistrarSuspensionRegistroHidrocarburo-fin");
        return retorno;
    }
}
