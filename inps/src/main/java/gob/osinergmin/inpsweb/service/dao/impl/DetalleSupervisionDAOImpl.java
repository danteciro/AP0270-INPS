/**
*
* Resumen		
* Objeto		: DetalleSupervisionDAOImpl.java
* Descripción		: Clase que contiene la implementación de los métodos declarados en la clase interfaz DetalleSupervisionDAO
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-791
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-791  |  02/09/2016   |   Luis García Reyna          | 	Ejecucion Medida - Listar Obligaciones
* OSINE791-RSIS25|  08/09/2016   |	 Alexander Vilca Narvaez 	| 	Crear la funcionalidad para consultar el comentario de ejecución de medida ingresado para las infracciones identificadas DSR-CRITICIDAD
* OSINE_SFS-791  |  12/10/2016   |   Luis García Reyna          | 	Terminar Supervision - Listar Infracciones No Subsanadas               |               |                              |
* 
*/

package gob.osinergmin.inpsweb.service.dao.impl;
import gob.osinergmin.inpsweb.domain.PghDetalleSupervision;
import gob.osinergmin.inpsweb.domain.builder.DetalleSupervisionBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.DetalleSupervisionDAO;
import gob.osinergmin.inpsweb.service.exception.DetalleSupervisionException;
import gob.osinergmin.inpsweb.service.exception.SupervisionException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.DetalleSupervisionFilter;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("detalleSupervisionDAO")
public class DetalleSupervisionDAOImpl implements DetalleSupervisionDAO {

    private static final Logger LOG = LoggerFactory.getLogger(DetalleSupervisionDAOImpl.class);
    @Inject
    private CrudDAO crud;

    @Override
    public DetalleSupervisionDTO registrar(DetalleSupervisionDTO detalleSupervisionDTO, UsuarioDTO usuarioDTO)
            throws SupervisionException {
        LOG.info("DetalleSupervisionDAOImpl : registrar-inicio");
        DetalleSupervisionDTO retorno = null;
        try {
            PghDetalleSupervision detalleSupervision = DetalleSupervisionBuilder.toDetalleSupervisionDomain(detalleSupervisionDTO);
            detalleSupervision.setDatosAuditoria(usuarioDTO);
            detalleSupervision.setEstado(Constantes.ESTADO_ACTIVO);
            crud.create(detalleSupervision);
            retorno = DetalleSupervisionBuilder.toDetalleSupervisionDTO(detalleSupervision);
        } catch (Exception e) {
            LOG.error("Error en registrar", e);
            SupervisionException ex = new SupervisionException(e.getMessage(), e);
            throw ex;
        }
        LOG.info("DetalleSupervisionDAOImpl : registrar-fin");
        return retorno;
    }

    @Override
    @Transactional(rollbackFor=DetalleSupervisionException.class)
    public List<DetalleSupervisionDTO> find(DetalleSupervisionFilter filtro) throws SupervisionException {
        LOG.info("DetalleSupervisionDAOImpl: find-inicio");
        List<DetalleSupervisionDTO> retorno = null;
        try {
            String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT distinct new PghDetalleSupervision ");
            jpql.append(" ( ");
            jpql.append(" ds.idDetalleSupervision,ds.idDetalleSupervisionAnt,ds.flagResultado,ds.descripcionResultado,ds.flagRegistrado, ");
            /* OSINE_SFS-791 - RSIS 16 - Inicio */
            jpql.append(" ds.prioridad, ");
            jpql.append(" ds.comentario, ");
            /* OSINE_SFS-791 - RSIS 16 - Fin */
            jpql.append(" o.idObligacion, ");
            jpql.append(" s.idSupervision, ");
            jpql.append(" t.idTipificacion, ");
            jpql.append(" c.idCriterio, ");
            /* OSINE_SFS-791 - RSIS 16 - Inicio */
            //jpql.append(" (select inf1.idInfraccion from PghInfraccion inf1 where inf1.estado=1 and inf1.idObligacion=o.idObligacion and rownum=1) as idInfraccion, ");
            jpql.append(" inf.idInfraccion, ");
            //jpql.append(" (select inf2.descripcionInfraccion from PghInfraccion inf2 where inf2.estado=1 and inf2.idObligacion=o.idObligacion and rownum=1) as descripcionInfraccion, ");
            jpql.append(" inf.descripcionInfraccion, ");
            //jpql.append(" (select da3.nombreArchivo from PghInfraccion inf3 left join inf3.idDocumentoAdjunto da3 where inf3.estado=1 and inf3.idObligacion=o.idObligacion and rownum=1) as nombreArchivoInfraccion, ");
            jpql.append(" da.nombreArchivo, ");
            //jpql.append(" (select da3.rutaAlfresco from PghInfraccion inf3 left join inf3.idDocumentoAdjunto da3 where inf3.estado=1 and inf3.idObligacion=o.idObligacion and rownum=1) as rutaAlfrescoInfraccion, ");
            jpql.append(" da.rutaAlfresco, ");
            jpql.append(" rds.idResultadoSupervision, ");
            jpql.append(" rds.codigo, ");
            jpql.append(" (select count(eido.idEscenarioIncumplido) from PghEscenarioIncumplido eido "+ 
                        "where eido.estado=1 and eido.idDetalleSupervision.idDetalleSupervision=ds.idDetalleSupervision) as countEscIncumplido , ");
            jpql.append(" (select count(cds.idComentarioDetsupervision) from PghComentarioDetsupervision cds "+ 
                        "where cds.estado=1 and cds.idDetalleSupervision.idDetalleSupervision=ds.idDetalleSupervision) as countComentarioDetSupervision , ");
            /* OSINE_SFS-791 - RSIS 16 - Fin */
            /* OSINE_SFS-791 - RSIS 39 - Inicio */
            jpql.append(" (select count(eido.idEscenarioIncumplido) from PghEscenarioIncumplido eido "+ 
                        "where eido.estado=1 and eido.idDetalleSupervision.idDetalleSupervision=ds.idDetalleSupervisionAnt) as countEscIncumplidoAnt , ");
            /* OSINE_SFS-791 - RSIS 39 - Fin */
            jpql.append(" rdsant.idResultadoSupervision , ");
            jpql.append(" rdsant.codigo , ");
            jpql.append(" ds.idMedidaSeguridad, ");
            /* OSINE_SFS-791 - mdiosesf - Inicio */
            jpql.append(" (select mcmp.descripcion from MdiMaestroColumna mc, MdiMaestroColumna mcmp where mc.idMaestroColumna = ds.idMedidaSeguridad and mc.codigo = mcmp.codigo "
            		+ "and mcmp.dominio=:dominioActivaDSRProdscop) as flagMostrarProducto) ");
            /* OSINE_SFS-791 - mdiosesf - Fin */
            jpql.append(" FROM PghDetalleSupervision ds ");
            jpql.append(" LEFT JOIN ds.idObligacion o ");
            jpql.append(" LEFT JOIN ds.idObligacion o ");
            jpql.append(" LEFT JOIN ds.idObligacion o ");
            jpql.append(" LEFT JOIN ds.idInfraccion inf ");
            jpql.append(" LEFT JOIN inf.idDocumentoAdjunto da ");
            jpql.append(" LEFT JOIN ds.idSupervision s ");
            jpql.append(" LEFT JOIN ds.idTipificacion t ");
            jpql.append(" LEFT JOIN ds.idCriterio c ");
            /* OSINE791 - RSIS21 - Inicio */
            jpql.append(" LEFT JOIN ds.idResultadoSupervision rds ");
            jpql.append(" LEFT JOIN ds.idResultadoSupervisionAnt rdsant ");
            /* OSINE791 - RSIS21 - Fin */
            if (filtro.getIdTemaObligacion() != null && filtro.getIdTemaObligacion() != -1) {
                jpql.append(" LEFT JOIN o.pghTemaObligacionMaestroList tom ");
            }
            if ((filtro.getCodigoBaseLegal() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getCodigoBaseLegal().trim())) || (filtro.getDescripcionBaseLegal() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getDescripcionBaseLegal().trim()))) {
                jpql.append(" LEFT JOIN o.pghObligacionBaseLegalList obl ");
                jpql.append(" LEFT JOIN obl.idBaseLegal bl ");
            }
            jpql.append(" WHERE ds.estado=1 ");
            if (filtro.getIdDetalleSupervision() != null) {
                jpql.append(" AND ds.idDetalleSupervision=:idDetalleSupervision ");
            }
            if (filtro.getIdSupervision() != null) {
                jpql.append(" AND s.idSupervision=:idSupervision ");
            }
            if (filtro.getIdCriticidad() != null && filtro.getIdCriticidad() != -1) {
                jpql.append(" AND o.idCriticidad=:idCriticidad ");
            }
            if (filtro.getIdTemaObligacion() != null && filtro.getIdTemaObligacion() != -1) {
                jpql.append(" AND tom.idTemaObligacion=:idTemaObligacion ");
            }
            if (filtro.getCodigoBaseLegal() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getCodigoBaseLegal().trim())) {
                jpql.append(" AND UPPER(bl.codigoBaseLegal)=UPPER(:codigoBaseLegal) ");
            }
            if (filtro.getDescripcionObligacion() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getDescripcionObligacion().trim())) {
                jpql.append(" AND  UPPER(o.descripcion) LIKE UPPER(:descripcion) ");
            }
            if (filtro.getFlagResultado() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getFlagResultado().trim())) {
                jpql.append(" AND ds.flagResultado=:flagResultado  ");
            }
            if (filtro.getFlagRegistrado() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getFlagRegistrado().trim())) {
                jpql.append(" AND ds.flagRegistrado=:flagRegistrado ");
            }
            if (filtro.getIdOrdenServicio() != null) {
                jpql.append(" AND s.idOrdenServicio.idOrdenServicio=:idOrdenServicio  ");
            }
            /* OSINE791 - RSIS21 - Inicio */
            if (filtro.getCodigoResultadoSupervision()!= null) {
                jpql.append(" AND rds.codigo=:codigoResultadoSupervision ");
            }
            /* OSINE791 - RSIS21 - Fin */
            /* OSINE791 - RSIS40 - Inicio */
            if (filtro.getIdResultadoSupervision()!= null) {
                jpql.append(" AND rds.idResultadoSupervision=:idResultadoSupervision ");
            }
            /* OSINE791 - RSIS40 - Fin */
            if(filtro.getFlgBuscaDetaSupSubsanado()!=null){
                if(filtro.getFlgBuscaDetaSupSubsanado().equals(Constantes.ESTADO_INACTIVO)){
                    jpql.append(" AND ds.idDetalleSupervisionSubsana is null ");
                }else if(filtro.getFlgBuscaDetaSupSubsanado().equals(Constantes.ESTADO_ACTIVO)){
                    jpql.append(" AND ds.idDetalleSupervisionSubsana is not null ");
                }
            }
            
            if(filtro.getDescripcionBaseLegal() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getDescripcionBaseLegal().trim())){
            	jpql.append(" and upper(bl.descripcion) like upper(:descripcionBaseLegal)");
            }
            
            jpql.append(" ORDER BY o.idObligacion ASC ");

            //Crear QUERY
            queryString = jpql.toString();
            System.out.println(queryString);
            Query query = this.crud.getEm().createQuery(queryString);
            //settear parametros
            if (filtro.getIdDetalleSupervision() != null) {
                query.setParameter("idDetalleSupervision", filtro.getIdDetalleSupervision());
            }
            if (filtro.getIdSupervision() != null) {
                query.setParameter("idSupervision", filtro.getIdSupervision());
            }
            if (filtro.getIdCriticidad() != null && filtro.getIdCriticidad() != -1) {
                query.setParameter("idCriticidad", filtro.getIdCriticidad());
            }
            if (filtro.getIdTemaObligacion() != null && filtro.getIdTemaObligacion() != -1) {
                query.setParameter("idTemaObligacion", filtro.getIdTemaObligacion());
            }
            if (filtro.getCodigoBaseLegal() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getCodigoBaseLegal().trim())) {
                query.setParameter("codigoBaseLegal", filtro.getCodigoBaseLegal());
            }
            if (filtro.getDescripcionObligacion() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getDescripcionObligacion().trim())) {
                query.setParameter("descripcion", "%" + filtro.getDescripcionObligacion() + "%");
            }
            if (filtro.getFlagResultado() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getFlagResultado().trim())) {
                query.setParameter("flagResultado", filtro.getFlagResultado());
            }
            if (filtro.getFlagRegistrado() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getFlagRegistrado().trim())) {
                query.setParameter("flagRegistrado", filtro.getFlagRegistrado());
            }
            if (filtro.getIdOrdenServicio() != null) {
                query.setParameter("idOrdenServicio", filtro.getIdOrdenServicio());
            }
            if(filtro.getDescripcionBaseLegal() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getDescripcionBaseLegal().trim())){
            	query.setParameter("descripcionBaseLegal", "%" + filtro.getDescripcionBaseLegal() + "%");
            }
            /* OSINE791 - RSIS21 - Inicio */
            if (filtro.getCodigoResultadoSupervision()!= null) {
                query.setParameter("codigoResultadoSupervision", filtro.getCodigoResultadoSupervision());
            }
            /* OSINE791 - RSIS21 - Fin */
            /* OSINE791 - RSIS40 - Inicio */
            if (filtro.getIdResultadoSupervision()!= null) {
                query.setParameter("idResultadoSupervision", filtro.getIdResultadoSupervision());
            }
            /* OSINE791 - RSIS40 - Fin */
            /* OSINE_SFS-791 - mdiosesf - Inicio */
            query.setParameter("dominioActivaDSRProdscop", Constantes.DOMINIO_ACTIVA_DSR_PROD_SCOP);
            /* OSINE_SFS-791 - mdiosesf - Fin */
            retorno = DetalleSupervisionBuilder.toListDetalleSupervisionDto(query.getResultList());
        } catch (Exception e) {
            LOG.error("error en find", e);
        }
        LOG.info("DetalleSupervisionDAOImpl: find-fin");
        return retorno;
    }

    @Override
    public DetalleSupervisionDTO actualizar(DetalleSupervisionDTO detalleSupervisionDTO, UsuarioDTO usuarioDTO)
            throws SupervisionException {
        LOG.info("DetalleSupervisionDAOImpl : actualizar-inicio");
        DetalleSupervisionDTO retorno = null;
        try {
            PghDetalleSupervision detalleSupervision = DetalleSupervisionBuilder.toDetalleSupervisionDomain(detalleSupervisionDTO);
            detalleSupervision.setDatosAuditoria(usuarioDTO);
            crud.update(detalleSupervision);
            retorno = DetalleSupervisionBuilder.toDetalleSupervisionDTO(detalleSupervision);
        } catch (Exception e) {
            LOG.error("Error en actualizar", e);
            SupervisionException ex = new SupervisionException(e.getMessage(), e);
            throw ex;
        }
        LOG.info("DetalleSupervisionDAOImpl : actualizar-fin");
        return retorno;
    }

    @Override
    public long cantidadDetalleSupervision(DetalleSupervisionFilter filtro) throws SupervisionException {
        LOG.info("DetalleSupervisionDAOImpl : cantidadDetalleSupervision-inicio");
        long cantidad = -1;
        String queryString;
        StringBuilder jpql = new StringBuilder();
        try {
            jpql.append(" SELECT COUNT(ds) from PghDetalleSupervision ds ");
            jpql.append(" WHERE ds.estado=1 ");
            if (filtro.getIdSupervision() != null) {
                jpql.append(" AND ds.idSupervision.idSupervision=:idSupervision ");
            }
            if (filtro.getFlagResultado() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getFlagResultado().trim())) {
                jpql.append(" AND ds.flagResultado=:flagResultado ");
            }
            if (filtro.getCodigoResultadoSupervision()!= null ) {
                jpql.append(" AND ds.idResultadoSupervision.codigo =:codigoResultado ");
            }
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            if (filtro.getIdSupervision() != null) {
                query.setParameter("idSupervision", filtro.getIdSupervision());
            }
            if (filtro.getFlagResultado() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getFlagResultado().trim())) {
                query.setParameter("flagResultado", filtro.getFlagResultado());
            }
            if (filtro.getCodigoResultadoSupervision()!= null ) {
                query.setParameter("codigoResultado", filtro.getCodigoResultadoSupervision());
            }
            cantidad = (Long) query.getSingleResult();
        } catch (Exception e) {
            LOG.error("Error en cantidadDetalleSupervision", e);
            SupervisionException ex = new SupervisionException(e.getMessage(), e);
            throw ex;
        }
        LOG.info("DetalleSupervisionDAOImpl : cantidadDetalleSupervision-fin");
        return cantidad;
    }
    
    /* OSINE_SFS-791 - RSIS 16 - Inicio */
    @Override
    public DetalleSupervisionDTO registroComentarioDetSupervision(DetalleSupervisionDTO detalleSupervisionDTO, UsuarioDTO usuarioDTO) throws SupervisionException {
        LOG.info("registroComentarioDetSupervision");
        DetalleSupervisionDTO retorno= new DetalleSupervisionDTO();
        try {
            PghDetalleSupervision registroDAO = crud.find(detalleSupervisionDTO.getIdDetalleSupervision(), PghDetalleSupervision.class);
            registroDAO.setComentario(detalleSupervisionDTO.getComentario()); 
            registroDAO.setDatosAuditoria(usuarioDTO);
            crud.update(registroDAO);
            
            retorno=DetalleSupervisionBuilder.toDetalleSupervisionDTO(registroDAO);
            
        } catch (Exception e) {
            LOG.error("error en registroComentarioDetSupervision",e);
            throw new SupervisionException(e.getMessage(), e);
        }
        return retorno;
    }
    /* OSINE_SFS-791 - RSIS 16 - Fin */

    @Override
    public DetalleSupervisionDTO ActualizarMedidaSeguridadDetalleSupervision(DetalleSupervisionDTO detalleSupervisionDTO, UsuarioDTO usuarioDTO) throws DetalleSupervisionException {
    LOG.info("DetalleSupervisionDAOImpl : ActualizarMedidaSeguridadDetalleSupervision-inicio");
        DetalleSupervisionDTO retorno= new DetalleSupervisionDTO();
        try {
            PghDetalleSupervision registroDAO = crud.find(detalleSupervisionDTO.getIdDetalleSupervision(), PghDetalleSupervision.class);
            registroDAO.setIdMedidaSeguridad(detalleSupervisionDTO.getIdMedidaSeguridad());
            registroDAO.setDatosAuditoria(usuarioDTO);
            crud.update(registroDAO);            
            retorno=DetalleSupervisionBuilder.toDetalleSupervisionDTO(registroDAO);
        } catch (Exception e) {
            LOG.error("error en ActualizarMedidaSeguridadDetalleSupervision",e);
            throw new DetalleSupervisionException(e.getMessage(), e);
        }
        return retorno;
    }
    
    /* OSINE_SFS-791 - RSIS 33 - Inicio */    
    @Override
    public List<DetalleSupervisionDTO> listarDetSupInfLevantamiento(DetalleSupervisionFilter filtro) throws DetalleSupervisionException {
        LOG.info("listarDetSupInfLevantamiento");
        List<DetalleSupervisionDTO> detalleSupervisionList=null;
        StringBuilder jpql = new StringBuilder();
        try {
        	jpql.append("select new PghDetalleSupervision"
        			+ "(sp.idSupervision, ds.idDetalleSupervision, ob.idObligacion, inf.idInfraccion, inf.descripcionInfraccion, "
        			+ "os.idOrdenServicio, os.numeroOrdenServicio, ex.idExpediente, ex.numeroExpediente, un.idUnidadSupervisada, "        			
        			+ "un.codigoOsinergmin, "
        			+ "(select mrh.idRegistroHidrocarburo from MdiRegistroHidrocarburo mrh WHERE mrh.estado=1 and mrh.idUnidadSupervisada = un.idUnidadSupervisada "
        			+ "and mrh.estadoProceso = (select mc.idMaestroColumna FROM MdiMaestroColumna mc where mc.aplicacion ='"+ Constantes.APLICACION_SIGUO +"' and mc.dominio='"+ Constantes.DOMINIO_ESTADO_RHO +"' and mc.codigo='"+Constantes.DOMINIO_ESTADO_RHO_CODIGO_ACTIVO+"') and rownum=1) as idRegistroHidrocarburo, "
        			+ "(select mrh.numeroRegistroHidrocarburo from MdiRegistroHidrocarburo mrh WHERE mrh.estado=1 and mrh.idUnidadSupervisada = un.idUnidadSupervisada "
        			+ "and mrh.estadoProceso = (select mc.idMaestroColumna FROM MdiMaestroColumna mc where mc.aplicacion ='"+ Constantes.APLICACION_SIGUO +"' and mc.dominio='"+ Constantes.DOMINIO_ESTADO_RHO +"' and mc.codigo='"+Constantes.DOMINIO_ESTADO_RHO_CODIGO_ACTIVO+"') and rownum=1) as numeroRegistroHidrocarburo, "
        			+ "per.idPersonal, per.nombreUsuarioSiged, per.idPersonalSiged) ");
        	jpql.append("from PghDetalleSupervision ds "
        			  + "inner join ds.idSupervision sp "
        			  + "inner join sp.idOrdenServicio os "
        			  + "inner join os.idExpediente ex "
        			  + "inner join ex.idPersonal per "
        			  + "inner join ex.idUnidadSupervisada un "       			 
        			  + "inner join ds.idObligacion ob "
        			  + "inner join ob.pghInfraccionList inf ");
        	jpql.append("where ds.idResultadoSupervision.codigo = :codigoResultadoIncumple "
        			  + "and ds.estado = '1' and ob.estado = '1' and inf.estado = '1' and os.estado = '1' and ex.estado = '1' "
        			  + "and un.estado = '1' and per.estado = '1' and os.iteracion = 1 "
        			  + "and ds.idDetalleSupervisionSubsana is null ");
			if(filtro.getIdSupervision()!=null){
				jpql.append("and sp.idSupervision = :idSupervision ");
			}
			jpql.append("order by ex.numeroExpediente desc ");
			
			String queryString = jpql.toString();		
			Query query = this.crud.getEm().createQuery(queryString);
			
			if(filtro.getIdSupervision()!=null){
				query.setParameter("idSupervision", filtro.getIdSupervision());
			}
			query.setParameter("codigoResultadoIncumple",Constantes.CODIGO_RESULTADO_INCUMPLE);
			detalleSupervisionList=DetalleSupervisionBuilder.toListDetalleSupervisionDto(query.getResultList());
            
        } catch (Exception e) {
            LOG.error("error en listarDetSupInfLevantamiento",e);
            throw new DetalleSupervisionException(e);
        }
        return detalleSupervisionList;
    }
    /* OSINE_SFS-791 - RSIS 16 - Fin */   

    @Override
    @Transactional
    public DetalleSupervisionDTO ActualizarDetalleSupervisionSubsanado(DetalleSupervisionDTO detalleSupervisionDTO, UsuarioDTO usuarioDTO) throws DetalleSupervisionException {
        LOG.info("ActualizarDetalleSupervisionSubsanado : ActualizarDetalleSupervisionSubsanado-inicio");
        DetalleSupervisionDTO retorno = null;
        try {
            PghDetalleSupervision registroDAO = crud.find(detalleSupervisionDTO.getIdDetalleSupervision(), PghDetalleSupervision.class);
            registroDAO.setDatosAuditoria(usuarioDTO);
            registroDAO.setIdDetalleSupervisionSubsana(detalleSupervisionDTO.getIdDetalleSupervisionSubsana());
            crud.update(registroDAO);
            retorno = DetalleSupervisionBuilder.toDetalleSupervisionDTO(registroDAO);
        } catch (Exception e) {
            LOG.error("error en ActualizarDetalleSupervisionSubsanado",e);
            throw new DetalleSupervisionException(e.getMessage(), e);
        }
        return retorno;
    }
}
