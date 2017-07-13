/**
* Resumen		
* Objeto		: SupervisionDAOImpl.java
* Descripción		: Clase que contiene la implementación de los métodos declarados en la clase interfaz SupervisionDAO
* Fecha de Creación	: 
* PR de Creación	: 
* Autor			: GMD
* =============================================================================================================================
* Modificaciones
* Motivo            Fecha           Nombre                              Descripción
* =============================================================================================================================
* OSINE_SFS-791  |  23/08/2016   |  Luis García Reyna          		| Crear la funcionalidad para registrar otros incumplimientos
* OSINE_SFS-791  |  29/08/2016   |  Luis García Reyna         		| Crear la funcionalidad para registrar Ejecucion Medida Supervision.
* OSINE791-RSIS04 | 05/10/2016   |  Zosimo Chaupis Santur      		| CREAR LA FUNCIONALIDAD REGISTRAR SUPERVISION DE UNA ORDEN DE SUPERVISION DSR-CRITICIDAD DE LA CASUISTICA SUPERVISION NO INICIADA                      |
* OSINE_SFS-791  |  06/10/2016   |  Luis García Reyna          		| Registrar Supervisión No Iniciada
* OSINE_SFS-791     23/10/2016      Mario Dioses Fernandez            Crear la tarea automática que notifique vía correo que se debe elaborar el oficio por obligaciones incumplidas sin subsanar
* OSINE_SFS-791     23/10/2016      Mario Dioses Fernandez         	  Crear la tarea automática que cancele el registro de hidrocarburos
*/

package gob.osinergmin.inpsweb.service.dao.impl;
import gob.osinergmin.inpsweb.domain.MdiMaestroColumna;
import gob.osinergmin.inpsweb.domain.PghDocumentoAdjunto;
import gob.osinergmin.inpsweb.domain.PghMotivoNoSupervision;
import gob.osinergmin.inpsweb.domain.PghResultadoSupervision;
import gob.osinergmin.inpsweb.domain.PghSupervision;
import gob.osinergmin.inpsweb.domain.builder.SupervisionBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.MaestroColumnaDAO;
import gob.osinergmin.inpsweb.service.dao.ResultadoSupervisionDAO;
import gob.osinergmin.inpsweb.service.dao.SupervisionDAO;
import gob.osinergmin.inpsweb.service.exception.SupervisionException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.Utiles;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.ResultadoSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.ResultadoSupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.SupervisionFilter;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import org.apache.cxf.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("supervisionDAO")
public class SupervisionDAOImpl implements SupervisionDAO{
	private static final Logger LOG = LoggerFactory.getLogger(SupervisionDAOImpl.class);

	@Inject
    private CrudDAO crud;
    /* OSINE791 - RSIS04 - Inicio */
    @Inject
    private ResultadoSupervisionDAO resultadoSupervisionDAO;
    /* OSINE791 - RSIS04 - Fin */
	@Override
	public List<SupervisionDTO> find(SupervisionFilter filtro) throws SupervisionException {
		LOG.info("SupervisionDAOImpl:find-inicio");
		List<SupervisionDTO> retorno=null;
        try{
            String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT s "
                + "FROM PghSupervision s "
                + "WHERE s.estado=1 ");
            if(filtro.getIdOrdenServicio()!=null){
                jpql.append(" and s.idOrdenServicio.idOrdenServicio=:idOrdenServicio ");
            }
            if(filtro.getIdSupervision()!=null){
            	jpql.append(" and s.idSupervision=:idSupervision ");
            }
            if(filtro.getFlagOrdeByIdSupervisionDesc()!=null  && filtro.getFlagOrdeByIdSupervisionDesc().equals(Constantes.ESTADO_ACTIVO)){
            	jpql.append(" order by s.idSupervision desc ");
            }
            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            //settear parametros
            if(filtro.getIdOrdenServicio()!=null){
            	query.setParameter("idOrdenServicio",filtro.getIdOrdenServicio());
            }
            if(filtro.getIdSupervision()!=null){
            	query.setParameter("idSupervision",filtro.getIdSupervision());
            }
            retorno= SupervisionBuilder.toListSupervisionDTO(query.getResultList());
        }catch(Exception e){
            LOG.error("error en find",e);
        }
        LOG.info("SupervisionDAOImpl:find-fin");
        return retorno;
	}
	
	@Override
    public SupervisionDTO registrarSupervision(SupervisionDTO supervisionDTO,UsuarioDTO usuarioDTO) throws SupervisionException{
		LOG.info("SupervisionDAOImpl:registrarSupervision-inicio");
		SupervisionDTO retorno=null;
        try{
            
            PghSupervision supervision = SupervisionBuilder.toSupervisionDomain(supervisionDTO);
            supervision.setDatosAuditoria(usuarioDTO);
            supervision.setEstado(Constantes.ESTADO_ACTIVO);
            crud.create(supervision);
            retorno=SupervisionBuilder.toSupervisionDTO(supervision);
        }catch(Exception e){
            LOG.error("Error en registrarSupervision",e);
            SupervisionException ex = new SupervisionException(e.getMessage(), e);
            throw ex;
        }
        LOG.info("SupervisionDAOImpl:registrarSupervision-fin");
        return retorno;
    }
	
	
    @Override
    public SupervisionDTO actualizar(SupervisionDTO supervisionDTO, UsuarioDTO usuarioDTO) throws SupervisionException{
		LOG.info("SupervisionDAOImpl: actualizar-inicio");
		SupervisionDTO retorno=null;
        try{
            PghSupervision supervision = SupervisionBuilder.toSupervisionDomain(supervisionDTO);
            supervision.setDatosAuditoria(usuarioDTO);
            crud.update(supervision);
            retorno=SupervisionBuilder.toSupervisionDTO(supervision);
        }catch(Exception e){
            LOG.error("error en actualizar");
            SupervisionException ex = new SupervisionException(e.getMessage(), e);
            throw ex;
        }
        LOG.info("SupervisionDAOImpl: actualizar-fin");
        return retorno;
    }
	
	@Override
    public SupervisionDTO registrarDatosSupervision(SupervisionDTO supervisionDTO, UsuarioDTO usuarioDTO) throws SupervisionException{
		LOG.info("SupervisionDAOImpl: registrarDatosSupervision-inicio");
		SupervisionDTO retorno=null;
        try{
            PghSupervision supervision = crud.find(supervisionDTO.getIdSupervision(), PghSupervision.class);
            supervision.setDatosAuditoria(usuarioDTO);
            if(!StringUtils.isEmpty(supervisionDTO.getFechaInicio())){supervision.setFechaInicio(Utiles.stringToDate(supervisionDTO.getFechaInicio(),Constantes.FORMATO_FECHA_LARGE));}
            if(!StringUtils.isEmpty(supervisionDTO.getFechaFin())){supervision.setFechaFin(Utiles.stringToDate(supervisionDTO.getFechaFin(),Constantes.FORMATO_FECHA_LARGE));}
            supervision.setActaProbatoria(supervisionDTO.getActaProbatoria());
            supervision.setCartaVisita(supervisionDTO.getCartaVisita());
            supervision.setObservacion(supervisionDTO.getObservacion());
            if(supervisionDTO.getMotivoNoSupervision()!=null){
            	supervision.setIdMotivoNoSupervision(new PghMotivoNoSupervision(supervisionDTO.getMotivoNoSupervision().getIdMotivoNoSupervision()));
            }
            supervision.setFlagCumplimientoPrevio(supervisionDTO.getFlagCumplimientoPrevio());
            supervision.setDescripcionMtvoNoSuprvsn(supervisionDTO.getDescripcionMtvoNoSuprvsn());
            crud.update(supervision);
            retorno=SupervisionBuilder.toSupervisionDTO(supervision);
        }catch(Exception e){
            LOG.error("error en registrarDatosSupervision");
            SupervisionException ex = new SupervisionException(e.getMessage(), e);
            throw ex;
        }
        LOG.info("SupervisionDAOImpl: registrarDatosSupervision-fin");
        return retorno;
    }
	
	@Override
	public SupervisionDTO registrarPersonaAtiende(SupervisionDTO supervisionDTO, UsuarioDTO usuarioDTO) throws SupervisionException{
		LOG.info("SupervisionDAOImpl: registrarPersonaAtiende-inicio");
		SupervisionDTO retorno=null;
        try{
            PghSupervision supervision = crud.find(supervisionDTO.getIdSupervision(), PghSupervision.class);
            supervision.setDatosAuditoria(usuarioDTO);
            supervision.setFlagIdentificaPersona(supervisionDTO.getFlagIdentificaPersona());
            supervision.setObservacionIdentificaPers(supervisionDTO.getObservacionIdentificaPers());
            crud.update(supervision);
            retorno=SupervisionBuilder.toSupervisionDTO(supervision);
        }catch(Exception e){
            LOG.error("error en registrarPersonaAtiende");
            SupervisionException ex = new SupervisionException(e.getMessage(), e);
            throw ex;
        }
        LOG.info("SupervisionDAOImpl: registrarPersonaAtiende-fin");
        return retorno;
    }

	@Override
	public SupervisionDTO cambiarSupervision(SupervisionDTO supervisionDTO, UsuarioDTO usuarioDTO) throws SupervisionException{
		LOG.info("SupervisionDAOImpl: cambiarSupervision-inicio");
		SupervisionDTO retorno=null;
        try{
            PghSupervision supervision = crud.find(supervisionDTO.getIdSupervision(), PghSupervision.class);
            supervision.setDatosAuditoria(usuarioDTO);
            supervision.setFlagSupervision(supervisionDTO.getFlagSupervision());
            if(supervisionDTO.getFlagSupervision().equals(Constantes.FLAG_NO_SUPERVISION)){
            	supervision.setFlagIdentificaPersona(null);
            	supervision.setObservacionIdentificaPers(null);
            }else if(supervisionDTO.getFlagSupervision().equals(Constantes.FLAG_SUPERVISION)){
            	supervision.setIdMotivoNoSupervision(null);
            	supervision.setDescripcionMtvoNoSuprvsn(null);
            }
            crud.update(supervision);
            retorno=SupervisionBuilder.toSupervisionDTO(supervision);
        }catch(Exception e){
            LOG.error("error en cambiarSupervision");
            SupervisionException ex = new SupervisionException(e.getMessage(), e);
            throw ex;
        }
        LOG.info("SupervisionDAOImpl: cambiarSupervision-fin");
        return retorno;
    }
    
    /* OSINE_SFS-791 - RSIS 15 - Inicio */ 
    @Override
    public SupervisionDTO registroOiSupervision(SupervisionDTO supervisionDTO, UsuarioDTO usuarioDTO) throws SupervisionException {
        LOG.error("registroOiSupervision");
        SupervisionDTO retorno= new SupervisionDTO();
        try {
            PghSupervision registroDAO = crud.find(supervisionDTO.getIdSupervision(), PghSupervision.class);
            registroDAO.setOtrosIncumplimientos(supervisionDTO.getOtrosIncumplimientos());
            registroDAO.setDatosAuditoria(usuarioDTO);
            
            crud.update(registroDAO);
            
            retorno=SupervisionBuilder.toSupervisionDTO(registroDAO);
            
        } catch (Exception e) {
            LOG.error("error en registroOiSupervision",e);
            throw new SupervisionException(e.getMessage(), e);
        }
        return retorno;
    }
    /* OSINE_SFS-791 - RSIS 15 - Fin */
    
    /* OSINE_SFS-791 - RSIS 16 - Inicio */
    @Override
    public SupervisionDTO registroEmSupervision(SupervisionDTO supervisionDTO, UsuarioDTO usuarioDTO) throws SupervisionException {
        LOG.error("registroEmSupervision");
        SupervisionDTO retorno= new SupervisionDTO();
        try {
            PghSupervision registroDAO = crud.find(supervisionDTO.getIdSupervision(), PghSupervision.class);
            if(supervisionDTO.getFechaFin() != null){
                registroDAO.setFechaFin(Utiles.stringToDate(supervisionDTO.getFechaFin(),Constantes.FORMATO_FECHA_LARGE));
            }
            if(supervisionDTO.getCartaVisita() != null){
                registroDAO.setCartaVisita(supervisionDTO.getCartaVisita());
            }
            if(supervisionDTO.getObservacion() != null){
                registroDAO.setObservacion(supervisionDTO.getObservacion());
            }
            if(supervisionDTO.getFlagEjecucionMedida() !=null){
                registroDAO.setFlagEjecucionMedida(supervisionDTO.getFlagEjecucionMedida());
            }
            if(supervisionDTO.getResultadoSupervisionDTO()!=null && supervisionDTO.getResultadoSupervisionDTO().getIdResultadosupervision() != null){
                PghResultadoSupervision resultado = new  PghResultadoSupervision(supervisionDTO.getResultadoSupervisionDTO().getIdResultadosupervision());
                registroDAO.setIdResultadoSupervision(resultado);
            }
            registroDAO.setDatosAuditoria(usuarioDTO);
            
            crud.update(registroDAO);
            
            retorno=SupervisionBuilder.toSupervisionDTO(registroDAO);
            
        } catch (Exception e) {
            LOG.error("error en registroEmSupervision",e);
            throw new SupervisionException(e.getMessage(), e);
        }
        return retorno;
    }
    /* OSINE_SFS-791 - RSIS 16 - Fin */
    /* OSINE791 - RSIS17 - Inicio */
    @Override
    public DocumentoAdjuntoDTO registrarDocumentoEjecucionMedida(DocumentoAdjuntoDTO documentoAdjuntoDTO, UsuarioDTO usuarioDTO) throws SupervisionException {
        LOG.error("registrarDocumentoEjecucionMedida");
        DocumentoAdjuntoDTO retorno= new DocumentoAdjuntoDTO();
        try {
            PghDocumentoAdjunto pghDocumentoAdjunto = new PghDocumentoAdjunto();
            
            pghDocumentoAdjunto.setIdSupervision(new PghSupervision(documentoAdjuntoDTO.getSupervision().getIdSupervision()));
            pghDocumentoAdjunto.setArchivoAdjunto(documentoAdjuntoDTO.getArchivoAdjunto());
            pghDocumentoAdjunto.setEstado(Constantes.ESTADO_ACTIVO);
            
            if(documentoAdjuntoDTO.getIdTipoDocumento() != null){
             pghDocumentoAdjunto.setIdTipoDocumento(new MdiMaestroColumna(documentoAdjuntoDTO.getIdTipoDocumento().getIdMaestroColumna()));   
            }               
            pghDocumentoAdjunto.setDescripcionDocumento(documentoAdjuntoDTO.getDescripcionDocumento());
            pghDocumentoAdjunto.setNombreDocumento(documentoAdjuntoDTO.getNombreArchivo());
            pghDocumentoAdjunto.setDatosAuditoria(usuarioDTO);
            
            crud.create(pghDocumentoAdjunto);
            
            retorno=SupervisionBuilder.toDocumentoAdjuntoDTO(pghDocumentoAdjunto);
            
        } catch (Exception e) {
            LOG.error("error en registrarDocumentoEjecucionMedida",e);
            throw new SupervisionException(e.getMessage(), e);
        }
        return retorno;
    }
    
    @Override
    public List<SupervisionDTO> findSupervisionReporte(SupervisionFilter filtro) throws SupervisionException {
        LOG.info("SupervisionDAOImpl:find-inicio");
        List<SupervisionDTO> retorno=null;
        try{
            String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT distinct new PghSupervision("
                    +" s.idSupervision, "
                    +" s.fechaInicio, "
                    +" s.fechaFin, "
                    +" os.idOrdenServicio,"
                    +" os.numeroOrdenServicio,"
                    +" ex.idExpediente, "
                    + "ex.numeroExpediente, "
                    + "us.idUnidadSupervisada, "
                    + "us.codigoOsinergmin, "
                    + "loca.idLocador , "
                    + "loca.nombreCompleto , "
                    + "EmSup.idSupervisoraEmpresa , "
                    + "EmSup.razonSocial, "
                    + " s.observacion "
                    + " )"
                + " FROM PghSupervision s "
                + " LEFT JOIN s.idOrdenServicio os "
                + " LEFT JOIN os.idExpediente ex "
                + " LEFT JOIN ex.idUnidadSupervisada us "
                + " LEFT JOIN os.idLocador loca "
                + " LEFT JOIN os.idSupervisoraEmpresa EmSup "
                + " WHERE s.estado=1 ");
            if(filtro.getIdSupervision()!=null){
                jpql.append(" and s.idSupervision=:idSupervision ");
            }
            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            //settear parametros
            if(filtro.getIdSupervision()!=null){
                query.setParameter("idSupervision",filtro.getIdSupervision());
            }
            
            
            retorno= SupervisionBuilder.toListSupervisionDTO(query.getResultList());
        }catch(Exception e){
            LOG.error("error en find",e);
        }
        LOG.info("SupervisionDAOImpl:find-fin");
        return retorno;
    }
    /* OSINE791 - RSIS17 - Fin */
/* OSINE791 – RSIS4 - Inicio */
    @Override
    @Transactional
    public int VerificarSupervisionObstaculizada(SupervisionDTO filtro) throws SupervisionException {
        LOG.info("Iniciando metodo VerificarSupervisionObstaculizada");
        ResultadoSupervisionFilter filtroEstadoSupervisionObstaculizado = new ResultadoSupervisionFilter();
        filtroEstadoSupervisionObstaculizado.setCodigo(Constantes.CODIGO_RESULTADO_SUPERVISION_INICIAL_OBSTACULIZADO);
        ResultadoSupervisionDTO objetoEstadoSupervisionObstaculizado = resultadoSupervisionDAO.getResultadoSupervision(filtroEstadoSupervisionObstaculizado);
            
        ResultadoSupervisionFilter filtroEstadoSupervisionPorverificar = new ResultadoSupervisionFilter();
        filtroEstadoSupervisionPorverificar.setCodigo(Constantes.CODIGO_RESULTADO_SUPERVISION_INICIAL_PORVERIFICAR);
        ResultadoSupervisionDTO objetoEstadoSupervisionPorverificar = resultadoSupervisionDAO.getResultadoSupervision(filtroEstadoSupervisionPorverificar);
        try {
            StringBuilder jpql = new StringBuilder();
            jpql.append(" update pgh_supervision super  ");
            jpql.append(" set super.ID_RESULT_SUPERV_INICIAL = :idEstadoSupervisionObstaculizado ");
            jpql.append(" where super.id_supervision = :idSupervisi and ");           
            jpql.append(" super.id_supervision in ( select distinct ( superaux.id_supervision )  ");
            jpql.append(" from pgh_supervision superaux , pgh_plazo pla ");
            jpql.append(" where pla.codigo_plazo = :codPlazo and  ((select sysdate from dual) - superaux.FECHA_INICIO_PORVERIFICAR)*24*60 > (pla.cantidad*60)  ");
            jpql.append(" and superaux.ID_RESULT_SUPERV_INICIAL = :idEstadoSupervisionPorVerificar and superaux.estado = :estadoActivo  ");
            jpql.append(" and superaux.id_supervision = :idSupervisi ) ");            
            String queryString = jpql.toString();
            Query q = crud.getEm().createNativeQuery(queryString);
            q.setParameter("idEstadoSupervisionObstaculizado", objetoEstadoSupervisionObstaculizado.getIdResultadosupervision());
            q.setParameter("idSupervisi", filtro.getIdSupervision());
            q.setParameter("idEstadoSupervisionPorVerificar",objetoEstadoSupervisionPorverificar.getIdResultadosupervision());
            q.setParameter("codPlazo", Constantes.CODIGO_PLAZO_INICIAL_POR_VERIFICAR_SUPERVISION_INICIAL);
            q.setParameter("estadoActivo", Constantes.ESTADO_ACTIVO);
            int val = q.executeUpdate();
            LOG.info("Se Actualizaron " + val+" Registros");
            return val;
        } catch (Exception ex) {
            LOG.info("Ocurrio un error VerificarSupervisionObstaculizada " + ex.getMessage());
            LOG.error("Error en VerificarSupervisionObstaculizada DAO imple", ex);
            return 0; 
        }
         
        
    }
    /* OSINE791 – RSIS4 - Fin */
    
    /*OSINE_SFS-791 - RSIS 03 - Inicio */
    @Override
    public SupervisionDTO registrarDatosFinalesSupervision(SupervisionDTO supervisionDTO, UsuarioDTO usuarioDTO) throws SupervisionException {
        LOG.error("registrarDatosFinalesSupervision");
        SupervisionDTO retorno= new SupervisionDTO();
        try {
            PghSupervision registroDAO = crud.find(supervisionDTO.getIdSupervision(), PghSupervision.class);
            registroDAO.setDatosAuditoria(usuarioDTO);
            registroDAO.setFechaInicio(Utiles.stringToDate(supervisionDTO.getFechaInicio(),Constantes.FORMATO_FECHA_LARGE));
            registroDAO.setCartaVisita(supervisionDTO.getCartaVisita());
            registroDAO.setObservacion(supervisionDTO.getObservacion());
            if(supervisionDTO.getMotivoNoSupervision()!=null){
            	registroDAO.setIdMotivoNoSupervision(new PghMotivoNoSupervision(supervisionDTO.getMotivoNoSupervision().getIdMotivoNoSupervision()));
            }
            registroDAO.setDescripcionMtvoNoSuprvsn(supervisionDTO.getDescripcionMtvoNoSuprvsn());
            PghResultadoSupervision resultado = new  PghResultadoSupervision(supervisionDTO.getResultadoSupervisionInicialDTO().getIdResultadosupervision());
            registroDAO.setIdResultSupervInicial(resultado);
            
            if(supervisionDTO.getFechaFin() != null){
                registroDAO.setFechaFin(Utiles.stringToDate(supervisionDTO.getFechaFin(),Constantes.FORMATO_FECHA_LARGE));
            }
            if(supervisionDTO.getResultadoSupervisionDTO()!=null && supervisionDTO.getResultadoSupervisionDTO().getIdResultadosupervision() != null){
                PghResultadoSupervision idResultadoSupervision = new  PghResultadoSupervision(supervisionDTO.getResultadoSupervisionDTO().getIdResultadosupervision());
                registroDAO.setIdResultadoSupervision(idResultadoSupervision);
            }
            
            crud.update(registroDAO);
            
            retorno=SupervisionBuilder.toSupervisionDTO(registroDAO);
            
        } catch (Exception e) {
            LOG.error("error en registrarDatosFinalesSupervision",e);
            throw new SupervisionException(e.getMessage(), e);
        }
        return retorno;
    }
    /*OSINE_SFS-791 - RSIS 03 - Fin */

    @Override
    @Transactional
    public SupervisionDTO ActualizarSupervisionTerminarSupervision(SupervisionDTO supervisionDTO, UsuarioDTO usuarioDTO) throws SupervisionException {
      LOG.error("ActualizarSupervisionTerminarSupervision");
        SupervisionDTO retorno= new SupervisionDTO();
        try {
            PghSupervision registroDAO = crud.find(supervisionDTO.getIdSupervision(), PghSupervision.class);
            if(supervisionDTO.getFechaFin() != null){
                registroDAO.setFechaFin(Utiles.stringToDate(supervisionDTO.getFechaFin(),Constantes.FORMATO_FECHA_LARGE));
            }
            if(supervisionDTO.getCartaVisita() != null){
                registroDAO.setCartaVisita(supervisionDTO.getCartaVisita());
            }
            if(supervisionDTO.getResultadoSupervisionDTO()!=null && supervisionDTO.getResultadoSupervisionDTO().getIdResultadosupervision() != null){
                PghResultadoSupervision resultado = new  PghResultadoSupervision(supervisionDTO.getResultadoSupervisionDTO().getIdResultadosupervision());
                registroDAO.setIdResultadoSupervision(resultado);
            }
            registroDAO.setDatosAuditoria(usuarioDTO);
            
            crud.update(registroDAO);
            
            retorno=SupervisionBuilder.toSupervisionDTO(registroDAO);
            
        } catch (Exception e) {
            LOG.error("error en ActualizarSupervisionTerminarSupervision",e);
            throw new SupervisionException(e.getMessage(), e);
        }
        return retorno; 
    }
    /* OSINE791 - RSIS41 - Inicio */
    @Override
    public DocumentoAdjuntoDTO registrarDocumentoGenerarResultados(DocumentoAdjuntoDTO documentoAdjuntoDTO, UsuarioDTO usuarioDTO) throws SupervisionException {
        LOG.error("registrarDocumentoGenerarResultados");
        DocumentoAdjuntoDTO retorno= new DocumentoAdjuntoDTO();
        try {
            PghDocumentoAdjunto pghDocumentoAdjunto = new PghDocumentoAdjunto();
            
            pghDocumentoAdjunto.setIdSupervision(new PghSupervision(documentoAdjuntoDTO.getSupervision().getIdSupervision()));
            pghDocumentoAdjunto.setArchivoAdjunto(documentoAdjuntoDTO.getArchivoAdjunto());
            pghDocumentoAdjunto.setEstado(Constantes.ESTADO_ACTIVO);
            
            //MaestroColumnaDTO idTipoDoc=maestroColumnaDAO.findMaestroColumnaByCodigo(Constantes.DOMINIO_TIPO_DOC_SUPERVISION, Constantes.APLICACION_INPS, Constantes.CODIGO_GEN_RESULT).get(0);
            if (documentoAdjuntoDTO.getIdTipoDocumento() != null) {
                pghDocumentoAdjunto.setIdTipoDocumento(new MdiMaestroColumna(documentoAdjuntoDTO.getIdTipoDocumento().getIdMaestroColumna()));
            }            
            pghDocumentoAdjunto.setDescripcionDocumento(documentoAdjuntoDTO.getDescripcionDocumento());
            pghDocumentoAdjunto.setNombreDocumento(documentoAdjuntoDTO.getNombreArchivo());
            pghDocumentoAdjunto.setDatosAuditoria(usuarioDTO);
            
            crud.create(pghDocumentoAdjunto);
            
            retorno=SupervisionBuilder.toDocumentoAdjuntoDTO(pghDocumentoAdjunto);
            
        } catch (Exception e) {
            LOG.error("error en registrarDocumentoGenerarResultados",e);
            throw new SupervisionException(e.getMessage(), e);
        }
        return retorno;
    }
     /* OSINE791 - RSIS41 - Fin */
    
    /* OSINE791 - RSIS39 - Inicio */
    @Override
	public List<SupervisionDTO> verificarNroCartaVista(SupervisionFilter filtro) throws SupervisionException {
		LOG.info("SupervisionDAOImpl verificarNroCartaVista");
		List<SupervisionDTO> retorno=null;
        try{
            String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT s "
                + "FROM PghSupervision s "
                + "WHERE s.estado = 1 ");
            if(filtro.getIdSupervision()!=null){
            	jpql.append(" and s.idSupervision<>:idSupervision ");
            }
            if(filtro.getCartaVisita()!=null){
            	jpql.append(" and s.cartaVisita=:cartaVisita ");
            }            
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);            
            if(filtro.getCartaVisita()!=null){
            	query.setParameter("cartaVisita",filtro.getCartaVisita());
            }
            if(filtro.getIdSupervision()!=null){
            	query.setParameter("idSupervision",filtro.getIdSupervision());
            }
            retorno= SupervisionBuilder.toListSupervisionDTO(query.getResultList());
        }catch(Exception e){
            LOG.error("error en verificarNroCartaVista",e);
        }
        LOG.info("SupervisionDAOImpl: verificarNroCartaVista");
        return retorno;
	}
    /* OSINE791 - RSIS39 - Inicio */
 /* OSINE791 - RSIS40 - Inicio */
    @Override
    public List<SupervisionDTO> verificarSupervisionCierreTotalParcialTareaProgramada(SupervisionFilter filtro) throws SupervisionException {
        LOG.info("SupervisionDAOImpl:verificarSupervisionCierreTotalParcialTareaProgramada-inicio");
        List<SupervisionDTO> retorno = null;
        try {
            String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append(" SELECT sup ");
            //jpql.append(" SELECT new PghSupervision( ");
            //jpql.append(" sup ");
            //jpql.append(" ) ");
            jpql.append(" FROM PghSupervision sup ");
            jpql.append(" inner join sup.idOrdenServicio orden ");
            jpql.append(" inner join orden.idExpediente expe ");
            jpql.append(" inner join expe.pghPeriodoMedidaSeguridadList peri ");
            jpql.append(" WHERE sup.estado = 1  ");
            jpql.append(" and orden.estado = 1 ");
            jpql.append(" and expe.estado = 1  ");
            jpql.append(" and peri.estado = 1  ");
            if (filtro.getFlagTareaProgramadaHabCierre() != null && filtro.getFlagTareaProgramadaHabCierre().equals(Constantes.ESTADO_ACTIVO)) {
                jpql.append("and to_char(peri.fechaFinPlaneado , 'YYYY/MM/DD') < to_char(SYSDATE, 'YYYY/MM/DD') ");
            }
            jpql.append("and  ( select max(ios.iteracion) from PghOrdenServicio ios where ios.estado=1 and ios.idExpediente.idExpediente=expe.idExpediente ) = orden.iteracion ");
            if (filtro.getFlagTareaProgramadaResultadoSup() != null) {
                if (filtro.getFlagTareaProgramadaResultadoSup().equals(Constantes.ESTADO_INACTIVO)) {
                    jpql.append(" AND sup.idResultadoSupervision is null ");
                } else if (filtro.getFlagTareaProgramadaResultadoSup().equals(Constantes.ESTADO_ACTIVO)) {
                    jpql.append(" AND sup.idResultadoSupervision is not null ");
                }
            }
            if (filtro.getFlagTareaProgramadaAuto() != null) {
                jpql.append("and peri.flagActualizarAuto =  :flagTareaAutomaticaAuto ");
            }
            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            //settear parametros
            if (filtro.getFlagTareaProgramadaAuto() != null) {
                query.setParameter("flagTareaAutomaticaAuto", filtro.getFlagTareaProgramadaAuto());
            }
            retorno = SupervisionBuilder.toListSupervisionDTO(query.getResultList());
        } catch (Exception e) {
            LOG.error("error en verificarSupervisionCierreTotalParcialTareaProgramada", e);
        }
        LOG.info("SupervisionDAOImpl:verificarSupervisionCierreTotalParcialTareaProgramada-fin");
        return retorno;
    }
    /* OSINE791 - RSIS40 - Fin */
    /* OSINE_SFS-791 - RSIS 46-47 - Inicio */
    @Override
    public List<SupervisionDTO> listarSupSinSubSanar(SupervisionFilter filtro) throws SupervisionException {
        LOG.info("listarDetSupInfLevantamiento");
        List<SupervisionDTO> supervisionList=null;
        StringBuilder jpql = new StringBuilder();
        try {
        	jpql.append("select distinct new PghSupervision"
        			+ "(sp.idSupervision, os.idOrdenServicio, os.numeroOrdenServicio, ex.idExpediente, ex.numeroExpediente, un.idUnidadSupervisada, "        			
        			+ "un.codigoOsinergmin, act.idActividad, "
        			+ "(select mrh.idRegistroHidrocarburo from MdiRegistroHidrocarburo mrh WHERE mrh.estado=1 and mrh.idUnidadSupervisada = un.idUnidadSupervisada "
        			+ "and mrh.estadoProceso = (select mc.idMaestroColumna FROM MdiMaestroColumna mc where mc.aplicacion ='"+ Constantes.APLICACION_SIGUO +"' and mc.dominio='"+ Constantes.DOMINIO_ESTADO_RHO +"' and mc.codigo='"+Constantes.DOMINIO_ESTADO_RHO_CODIGO_ACTIVO+"') and rownum=1) as idRegistroHidrocarburo, "
        			+ "(select mrh.numeroRegistroHidrocarburo from MdiRegistroHidrocarburo mrh WHERE mrh.estado=1 and mrh.idUnidadSupervisada = un.idUnidadSupervisada "
        			+ "and mrh.estadoProceso = (select mc.idMaestroColumna FROM MdiMaestroColumna mc where mc.aplicacion ='"+ Constantes.APLICACION_SIGUO +"' and mc.dominio='"+ Constantes.DOMINIO_ESTADO_RHO +"' and mc.codigo='"+Constantes.DOMINIO_ESTADO_RHO_CODIGO_ACTIVO+"') and rownum=1) as numeroRegistroHidrocarburo, "
        			+ "per.idPersonal, per.nombreUsuarioSiged, per.idPersonalSiged, "
        			+ "et.flagCorreoOficio, et.flagEstadoReghMsfh, et.flagEstadoReghInps, et.flagEnviarConstanciaSiged, et.flagEstadoSiged,"
        			+ "et.flagCorreoEstadoRegh, et.flagRegistraDocsInps, et.flagCorreoScop, el.idMaestroColumna, el.descripcion, el.codigo) ");
        	jpql.append("from PghSupervision sp "
        			  + "inner join sp.idOrdenServicio os "
        			  + "inner join os.idExpediente ex "
        			  + "inner join ex.idPersonal per "
        			  + "inner join ex.idUnidadSupervisada un "
        			  + "inner join un.idActividad act "
        			  + "inner join ex.idEstadoLevantamiento el "
        			  + "left join ex.pghExpedienteTarea et ");
        	jpql.append("where sp.estado = '1' and os.estado = '1' and ex.estado = '1' and un.estado = '1' and per.estado = '1' and os.iteracion = 1 and act.estado = '1' and el.estado = '1' "
        			  + "and (select count(*) from PghDetalleSupervision ds where ds.estado = '1 ' ");
        	if(filtro.getCodigoOsinergmin()!=null && !filtro.getCodigoOsinergmin().equals(""))	{
        		jpql.append("and un.codigoOsinergmin = :codigoOsinergmin ");
        	}
        	if(filtro.getFlagTareaProgramada()!=null && filtro.getFlagTareaProgramada().equals(Constantes.ESTADO_ACTIVO)){
        		jpql.append("and ds.idResultadoSupervision.codigo = :codigoResultadoIncumple ");
        	} 
        	if (filtro.getFlagModLevantamiento()!=null && filtro.getFlagModLevantamiento().equals(Constantes.ESTADO_ACTIVO)) {
        		jpql.append("and (ds.idResultadoSupervision.codigo = :codigoResultadoIncumple or ds.idResultadoSupervision.codigo = :codigoResultadoInclumpleObtaculizado) ");        		
        	}
        	jpql.append("and ds.idDetalleSupervisionSubsana is null ");
        	jpql.append("and ds.idSupervision.idSupervision = sp.idSupervision) > 0 ");
			if(filtro.getFlagTareaProgramada()!=null && filtro.getFlagTareaProgramada().equals(Constantes.ESTADO_ACTIVO) && filtro.getPlazoNroDias()!=null){
				jpql.append("and to_char(sp.fechaFin + " + filtro.getPlazoNroDias() + ", 'YYYY/MM/DD') < to_char(SYSDATE, 'YYYY/MM/DD') ");
				if(filtro.getFlagTareaCancelacion()!=null && filtro.getFlagTareaCancelacion().equals(Constantes.ESTADO_ACTIVO)){					
					jpql.append("and (et.flagEstadoReghMsfh = '0' or et.flagEstadoReghInps = '0' or et.flagEnviarConstanciaSiged = '0' or " +
								"et.flagEstadoSiged = '0' or et.flagCorreoEstadoRegh = '0' or " +
								"et.flagEstadoReghMsfh is null or et.flagEstadoReghInps is null or et.flagEnviarConstanciaSiged is null or " + 
								"et.flagEstadoSiged  is null or et.flagCorreoEstadoRegh  is null) ");
				} else if(filtro.getFlagTareaNotiCancelacion()!=null && filtro.getFlagTareaNotiCancelacion().equals(Constantes.ESTADO_ACTIVO)){
					jpql.append("and (et.flagCorreoOficio = '0' or et.flagCorreoOficio  is null) ");
				}
			}			
			jpql.append("order by ex.numeroExpediente desc ");
			
			String queryString = jpql.toString();		
			Query query = this.crud.getEm().createQuery(queryString);
			if(filtro.getFlagTareaProgramada()!=null && filtro.getFlagTareaProgramada().equals(Constantes.ESTADO_ACTIVO)){
				query.setParameter("codigoResultadoIncumple",Constantes.CODIGO_RESULTADO_INCUMPLE);
			} 
			if (filtro.getFlagModLevantamiento()!=null && filtro.getFlagModLevantamiento().equals(Constantes.ESTADO_ACTIVO)) {
				query.setParameter("codigoResultadoIncumple",Constantes.CODIGO_RESULTADO_INCUMPLE);
				query.setParameter("codigoResultadoInclumpleObtaculizado",Constantes.CODIGO_RESULTADO_SUPERVISION_NOCUMPLE_OBSTACULIZADO);
			}
			if(filtro.getCodigoOsinergmin()!=null && !filtro.getCodigoOsinergmin().equals(""))	{
				query.setParameter("codigoOsinergmin", filtro.getCodigoOsinergmin());
        	}
			supervisionList=SupervisionBuilder.toListSupervisionDTO(query.getResultList());
            
        } catch (Exception e) {
            LOG.error("error en listarDetSupInfLevantamiento",e);
            throw new SupervisionException(e);
        }
        return supervisionList;
    }
    /* OSINE_SFS-791 - RSIS 46-47 - Fin */
}
