/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao.impl;
import gob.osinergmin.inpsweb.domain.MdiRegistroHidrocarburo;
import gob.osinergmin.inpsweb.domain.builder.RegistroHidrocarburoBuilder;
import gob.osinergmin.inpsweb.dto.RegistroHidrocarburoExternoDTO;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.RegistroHidrocarburoDAO;
import gob.osinergmin.inpsweb.service.exception.RegistroHidrocarburoException;
import gob.osinergmin.inpsweb.service.exception.UnidadSupervisadaException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.RegistroHidrocarburoDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.RegistroHidrocarburoFilter;
import java.math.BigDecimal;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author zchaupis
 */
@Repository("registroHidrocarburoDAO")
public class RegistroHidrocarburoDAOImpl implements RegistroHidrocarburoDAO {

    private static final Logger LOG = LoggerFactory.getLogger(RegistroHidrocarburoDAOImpl.class);
    @Inject
    private CrudDAO crud;

    @Override
    public List<RegistroHidrocarburoDTO> getListRegistroHidrocarburo(RegistroHidrocarburoFilter filtro) throws RegistroHidrocarburoException {
      LOG.info("RegistroHidrocarburoDAOImpl: getRegistroHidrocarburo-inicio");
        List<RegistroHidrocarburoDTO> retorno = null;
        try {
            String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT rh ");
            jpql.append(" FROM MdiRegistroHidrocarburo rh ");
            jpql.append(" WHERE rh.estado=:estadoActivo and rownum = 1 ");
            if (filtro.getIdRegistroHidrocarburo()!= null) {
                jpql.append(" AND rh.idRegistroHidrocarburo=:idRegistroHidrocarburo ");
            }
            if (filtro.getIdUnidadSupervisada()!= null) {
                jpql.append(" AND rh.idUnidadSupervisada.idUnidadSupervisada=:idUnidadSupervisada ");
            }
            if (filtro.getNumeroRegistroHidrocarburo()!= null) {
                jpql.append(" AND rh.numeroRegistroHidrocarburo =:numeroRegistroHidrocarburo ");
            }

            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            //settear parametros
            query.setParameter("estadoActivo", Constantes.ESTADO_ACTIVO);
            if (filtro.getIdRegistroHidrocarburo()!= null) {
                query.setParameter("idRegistroHidrocarburo", filtro.getIdRegistroHidrocarburo());
            }
            if (filtro.getIdUnidadSupervisada()!= null) {
                query.setParameter("idUnidadSupervisada", filtro.getIdUnidadSupervisada());
            }
            if (filtro.getNumeroRegistroHidrocarburo()!= null) {
                query.setParameter("numeroRegistroHidrocarburo", filtro.getNumeroRegistroHidrocarburo());
            }
            
            retorno = RegistroHidrocarburoBuilder.toListRegistroHidrocarburoDTO(query.getResultList());
        } catch (Exception e) {
            LOG.error("error en getRegistroHidrocarburo", e);
            throw new RegistroHidrocarburoException(e.getMessage(),e);
        }
        LOG.info("RegistroHidrocarburoDAOImpl: getRegistroHidrocarburo-fin");
        return retorno; 
    }

    @Override
    public int actualizarRegistroHidrocarburoExterno(RegistroHidrocarburoExternoDTO registroHidrocarburoExternoDTO) throws RegistroHidrocarburoException {
      LOG.info("actualizarRegistroHidrocarburoExterno");
        int rpta = 0;
        try {
            StringBuilder jpql = new StringBuilder();
            jpql.append(" UPDATE SFH_CDGOS_DGH CD ");
            jpql.append(" SET CD.ESTADO = :estadoRH , ");
            jpql.append(" CD.USRIO_MDFCDOR = :usuarioActualizacion , ");
            jpql.append(" CD.FCHA_MDFCCION = :fechaActualizacion ");
            jpql.append(" WHERE ");
            jpql.append(" CD.UNIACT_ID = :idUnidadActividadExterna ");
            String queryString = jpql.toString();
            Query q = crud.getEm().createNativeQuery(queryString);
            q.setParameter("estadoRH", registroHidrocarburoExternoDTO.getEstadoRegistroHidrocarburo());
            q.setParameter("usuarioActualizacion", registroHidrocarburoExternoDTO.getNombreUsuario());
            q.setParameter("fechaActualizacion",registroHidrocarburoExternoDTO.getFechaSuspension());
            q.setParameter("idUnidadActividadExterna", registroHidrocarburoExternoDTO.getIdUnidadActividadExterna());
           
            rpta = q.executeUpdate();
            LOG.info("Se Actualizo el Registro de Hidrocarburos de la BD Externa");
        } catch (Exception ex) {
            LOG.info("Ocurrio un error " + ex.getMessage());
            LOG.error("Error en actualizarRegistroHidrocarburoExterno DAO imple", ex);
            rpta = 0;
            throw new RegistroHidrocarburoException(ex.getMessage(),ex);
        }
        return rpta; 
    }

    @Override
    public Long veridUnidadOperativaBDExterna(UnidadSupervisadaDTO UnidadSupervisadaDTO) throws UnidadSupervisadaException {
      LOG.info("RegistroHidrocarburoDAOImpl: veridUnidadOperativaBDExterna-inicio");
        Long retorno = null;
        try {
            String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append(" SELECT uo.id,uo.uniope_tipo ");
            jpql.append(" FROM SFH_UNDDES_OPRTVAS UO ");
            jpql.append(" WHERE UO.UNIOPE_TIPO='UO' AND ");
            jpql.append(" UO.CODIGO_OSINERG = :codigoOsinergmin ");
           
            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createNativeQuery(queryString);
            query.setParameter("codigoOsinergmin", UnidadSupervisadaDTO.getCodigoOsinergmin());
            List<Object[]> lres = query.getResultList();
            if (lres.size() == Constantes.LISTA_UNICO_VALIR) {
                for (Object[] fila : lres) {
                    retorno = (fila[0] != null ? ((BigDecimal) fila[0]).longValue() : null);
                }
            }
        } catch (Exception e) {
            LOG.error("error en getRegistroHidrocarburo", e);
            throw new UnidadSupervisadaException(e.getMessage(),e);
        }
        LOG.info("RegistroHidrocarburoDAOImpl: veridUnidadOperativaBDExterna-fin");
        return retorno; 
    }

    @Override
    public RegistroHidrocarburoExternoDTO veridActividadBDExterna(UnidadSupervisadaDTO UnidadSupervisadaDTO,Long idUnidadSupervisadaExterna) throws UnidadSupervisadaException{
     LOG.info("RegistroHidrocarburoDAOImpl: veridActividadBDExterna-inicio");
        RegistroHidrocarburoExternoDTO retorno = new RegistroHidrocarburoExternoDTO();
        try {
            String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append(" SELECT UA.ID,UA.ACTIVI_ID,UA.UNIOPE_ID ");
            jpql.append(" FROM SFH_UNDDES_ACTVDDES UA ");
            jpql.append(" WHERE UA.UNIOPE_ID = :idUnidadSupervisadaExterna AND ");
            jpql.append(" UA.ESTADO='HA' ");
            jpql.append("  and UA.ACTIVI_ID = :idActividadINPS ");
            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createNativeQuery(queryString);
            query.setParameter("idUnidadSupervisadaExterna", idUnidadSupervisadaExterna);
            query.setParameter("idActividadINPS", UnidadSupervisadaDTO.getActividad().getIdActividad());
            List<Object[]> lres = query.getResultList();
            if (lres.size() == Constantes.LISTA_UNICO_VALIR) {
                for (Object[] fila : lres) {
                    retorno.setIdUnidadActividadExterna((fila[0] != null ? ((BigDecimal) fila[0]).longValue() : null));
                    retorno.setIdActividadExterno((fila[1] != null ? ((BigDecimal) fila[1]).longValue() : null));
                    retorno.setIdUnidadSupervisadaExterno((fila[2] != null ? ((BigDecimal) fila[2]).longValue() : null));
                }
            }
            
        } catch (Exception e) {
            LOG.error("error en veridActividadBDExterna", e);
        }
        LOG.info("RegistroHidrocarburoDAOImpl: veridActividadBDExterna-fin");
        return retorno; 
    }

    @Override
    @Transactional(rollbackFor=RegistroHidrocarburoException.class)
    public RegistroHidrocarburoDTO ActualizarRegistroHidrocarburo(RegistroHidrocarburoDTO registroHidrocarburoDTO, UsuarioDTO usuarioDTO) throws RegistroHidrocarburoException {
      LOG.info("RegistroHidrocarburoDAOImpl : ActualizarRegistroHidrocarburo-inicio");
        RegistroHidrocarburoDTO retorno= new RegistroHidrocarburoDTO();
        try {
            MdiRegistroHidrocarburo registroDAO = crud.find(registroHidrocarburoDTO.getIdRegistroHidrocarburo(), MdiRegistroHidrocarburo.class);
            registroDAO.setDatosAuditoria(usuarioDTO);
            /* OSINE_SFS-791 - RSIS 47 - Inicio */
            if(registroHidrocarburoDTO.getFechaInicioSuspencion() != null){
                registroDAO.setFechaInicioSuspencion(registroHidrocarburoDTO.getFechaInicioSuspencion());
            }
            /* OSINE_SFS-791 - RSIS 47 - Fin */
            if(registroHidrocarburoDTO.getFechaFinSuspencion() != null){
                registroDAO.setFechaFinSuspencion(registroHidrocarburoDTO.getFechaFinSuspencion());
            }
            if(registroHidrocarburoDTO.getEstadoProceso()!= null){
                registroDAO.setEstadoProceso(registroHidrocarburoDTO.getEstadoProceso().getIdMaestroColumna());
            }
            crud.update(registroDAO);            
            retorno=RegistroHidrocarburoBuilder.toRegistroHidrocarburoDTO(registroDAO);
        } catch (Exception e) {
            retorno = null;            
            LOG.error("error en ActualizarRegistroHidrocarburo",e);
            throw new RegistroHidrocarburoException(e.getMessage(), e);
        }
        return retorno; 
    }

}
