/**
* Resumen		
* Objeto		: EscenarioIncumplidoDAOImpl.java
* Descripción		: Clase que contiene la implementación de los métodos declarados en la clase interfaz EscenarioIncumplidoDAO
* Fecha de Creación	: 05/09/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha            Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-791  |  05/09/2016   |   Luis García Reyna     |     Listar Escenarios Incumplidos de la Supervision.
* OSINE791-RSIS25|  08/09/2016   | Alexander Vilca Narvaez | Crear la funcionalidad para consultar el comentario de ejecución de medida ingresado para las infracciones identificadas DSR-CRITICIDAD
*    
*/


package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.PghEscenarioIncumplido;
import gob.osinergmin.inpsweb.domain.builder.EscenarioIncumplidoBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.EscenarioIncumplidoDAO;
import gob.osinergmin.inpsweb.service.exception.DetalleSupervisionException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.EscenarioIncumplidoDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.EscenarioIncumplidoFilter;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("escenarioIncumplidoDAO")
public class EscenarioIncumplidoDAOImpl implements EscenarioIncumplidoDAO{
    
    private static final Logger LOG = LoggerFactory.getLogger(EscenarioIncumplidoDAOImpl.class);
    @Inject
    private CrudDAO crud;

    @Override
    public List<EscenarioIncumplidoDTO> find(EscenarioIncumplidoFilter filtro) throws DetalleSupervisionException{
        LOG.info("EscenarioIncumplidoDAOImpl -- find");
        List<EscenarioIncumplidoDTO> retorno = null;
        try {
            
            StringBuilder jpql = new StringBuilder();
            
            jpql.append(" SELECT new PghEscenarioIncumplido ");
            jpql.append(" ( ");
            /* OSINE791 - RSIS25 - Inicio */
            jpql.append(" eido.idEscenarioIncumplido, eido.idDetalleSupervision.idDetalleSupervision, eito.idEsceIncumplimiento.idEsceIncumplimiento, eito.idEsceIncuMaestro.idMaestroColumna, mc.descripcion, eido.comentarioEjecucion ");
            /* OSINE791 - RSIS25 - Fin */
            jpql.append(" ) ");
            jpql.append(" FROM PghEscenarioIncumplido eido ");
            jpql.append(" LEFT JOIN eido.idDetalleSupervision ds ");
            jpql.append(" LEFT JOIN eido.idEsceIncumplimiento eito ");
            jpql.append(" LEFT JOIN eito.idEsceIncuMaestro mc ");
            jpql.append(" WHERE eido.estado = 1 ");
            
            if (filtro.getIdDetalleSupervision() != null) {
                jpql.append(" AND eido.idDetalleSupervision.idDetalleSupervision = :idDetalleSupervision ");
            }
            if (filtro.getIdEscenarioIncumplido()!= null) {
                jpql.append(" AND eido.idEscenarioIncumplido = :idEscenarioIncumplido ");
            }
            if (filtro.getIdEsceIncumplimiento() != null) {
                jpql.append(" AND eido.idEsceIncumplimiento.idEsceIncumplimiento = :idEsceIncumplimiento ");
            }
            jpql.append(" ORDER BY eido.idEscenarioIncumplido ASC ");
            //Crear QUERY
            Query query = this.crud.getEm().createQuery(jpql.toString());
            //settear parametros
            if (filtro.getIdDetalleSupervision()!= null) {
                query.setParameter("idDetalleSupervision", filtro.getIdDetalleSupervision());
            }
            if (filtro.getIdEscenarioIncumplido()!= null) {
                query.setParameter("idEscenarioIncumplido", filtro.getIdEscenarioIncumplido());
            }
            if (filtro.getIdEsceIncumplimiento()!= null) {
                query.setParameter("idEsceIncumplimiento", filtro.getIdEsceIncumplimiento());
            }   
            retorno = EscenarioIncumplidoBuilder.toListEscenarioIncumplidoDto(query.getResultList());
        } catch (Exception e) {
            LOG.error("error en find", e);
        }
        
        return retorno;
    }

    @Override
    public EscenarioIncumplidoDTO registroComentarioEscIncumplido(EscenarioIncumplidoDTO escenarioIncumplidoDTO, UsuarioDTO usuarioDTO) throws DetalleSupervisionException {
        LOG.error("registroComentarioEscIncumplido");
        EscenarioIncumplidoDTO retorno= new EscenarioIncumplidoDTO();
        try {
            PghEscenarioIncumplido registroDAO = crud.find(escenarioIncumplidoDTO.getIdEscenarioIncumplido(), PghEscenarioIncumplido.class);
            registroDAO.setComentarioEjecucion(escenarioIncumplidoDTO.getComentarioEjecucion());
            registroDAO.setDatosAuditoria(usuarioDTO);
            crud.update(registroDAO);
            
            retorno=EscenarioIncumplidoBuilder.toEscenarioIncumplidoDTO(registroDAO);
        } catch (Exception e) {
            LOG.error("error en registroComentarioEscIncumplido",e);
            throw new DetalleSupervisionException(e.getMessage(), e);
        }
        return retorno;
    }
    
    @Override
    public EscenarioIncumplidoDTO guardarEscenarioIncumplido(EscenarioIncumplidoDTO escenarioIncumplidoDTO, UsuarioDTO usuarioDTO) throws DetalleSupervisionException{
        LOG.error("guardarEscenarioIncumplido");
        EscenarioIncumplidoDTO retorno= new EscenarioIncumplidoDTO();
        try {
            PghEscenarioIncumplido registroDAO = EscenarioIncumplidoBuilder.toEscenarioIncumplidoDomain(escenarioIncumplidoDTO);
            registroDAO.setDatosAuditoria(usuarioDTO);
            crud.create(registroDAO);
            
            retorno=EscenarioIncumplidoBuilder.toEscenarioIncumplidoDTO(registroDAO);
        } catch (Exception e) {
            LOG.error("error en guardarEscenarioIncumplido",e);
            throw new DetalleSupervisionException(e.getMessage(), e);
        }
        return retorno;
    }
    
    @Override
    public EscenarioIncumplidoDTO cambiaEstadoEscenarioIncumplido(EscenarioIncumplidoDTO escenarioIncumplidoDTO, UsuarioDTO usuarioDTO) throws DetalleSupervisionException{
        LOG.error("cambiaEstadoEscenarioIncumplido");
        EscenarioIncumplidoDTO retorno= new EscenarioIncumplidoDTO();
        try {
            PghEscenarioIncumplido registroDAO = crud.find(escenarioIncumplidoDTO.getIdEscenarioIncumplido(), PghEscenarioIncumplido.class);
            registroDAO.setEstado(escenarioIncumplidoDTO.getEstado());
            registroDAO.setDatosAuditoria(usuarioDTO);
            crud.update(registroDAO);
            
            retorno=EscenarioIncumplidoBuilder.toEscenarioIncumplidoDTO(registroDAO);
        } catch (Exception e) {
            LOG.error("error en cambiaEstadoEscenarioIncumplido",e);
            throw new DetalleSupervisionException(e.getMessage(), e);
        }
        return retorno;
    }
}
