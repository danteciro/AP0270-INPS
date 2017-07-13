/**
* Resumen		
* Objeto		: OrdenServicioDAOImpl.java
* Descripción		: Clase que contiene la implementación de los métodos declarados en la clase interfaz OrdenServicioDAO
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     09/05/2016      Mario Dioses Fernandez     	Registrar en BD campo FECHA_INICIO_PLAZO y FECHA_FIN_PLAZO luego de CONCLUIR_OS, considerando Plazo_Descargo por Rubro (MDI_ACTIVIDAD)
* OSINE_SFS-480     23/05/2016      Luis García Reyna           Implementar la funcionalidad de devolución de asignaciones de acuerdo a especificaciones.
* OSINE_SFS-480     23/05/2016      Hernán Torres Saenz         Implementar la funcionalidad de anular orden de servicio de acuerdo a especificaciones.
* OSINE_SFS-480     01/06/2016      Luis García Reyna           Implementar la funcionalidad de editar asignaciones de acuerdo a especificaciones
* OSINE791          19/08/2016      Jose Herrera                Agregar Sigla de Division al Numero Orden Servicio
* OSINE791-RSIS21   29/08/2016      Alexander Vilca Narvaez     Implementar la funcionalidad de archivar para el flujo DSR
* OSINE_SFS-791     10/10/2016      Mario Dioses Fernandez      Crear la funcionalidad "verificar levantamientos" que permita verificar el tipo de asignación para la orden de levantamiento DSR-CRITICIDAD.
*    
*/

package gob.osinergmin.inpsweb.service.dao.impl;
import gob.osinergmin.inpsweb.domain.MdiLocador;
import gob.osinergmin.inpsweb.domain.MdiMaestroColumna;
import gob.osinergmin.inpsweb.domain.MdiSupervisoraEmpresa;
import gob.osinergmin.inpsweb.domain.PghEstadoProceso;
import gob.osinergmin.inpsweb.domain.PghExpediente;
import gob.osinergmin.mdicommon.domain.dto.OrdenServicioDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.inpsweb.domain.PghOrdenServicio;
import gob.osinergmin.inpsweb.domain.builder.DetalleSupervisionBuilder;
import gob.osinergmin.inpsweb.domain.builder.OrdenServicioBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.ExpedienteDAO;
import gob.osinergmin.inpsweb.service.dao.HistoricoEstadoDAO;
import gob.osinergmin.inpsweb.service.dao.MaestroColumnaDAO;
import gob.osinergmin.inpsweb.service.dao.OrdenServicioDAO;
import gob.osinergmin.inpsweb.service.exception.OrdenServicioException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.StringUtil;
import gob.osinergmin.inpsweb.util.Utiles;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.HistoricoEstadoDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.ui.OrdenServicioFilter;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;

import org.hibernate.internal.SessionImpl;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author jpiro
 */
@Service("ordenServicioDAO")
public class OrdenServicioDAOImpl implements OrdenServicioDAO {
    private static final Logger LOG = LoggerFactory.getLogger(OrdenServicioDAOImpl.class);    
    @Inject
    private CrudDAO crud;
    @Inject
    private MaestroColumnaDAO maestroColumnaDAO;
    @Inject
    private HistoricoEstadoDAO historicoEstadoDAO;
    @Inject
    private ExpedienteDAO expedienteDAO;
    
    @Override
    public OrdenServicioDTO veriActuFlgCumplimiento(Long idOrdenServicio,UsuarioDTO usuarioDTO) throws OrdenServicioException{
        OrdenServicioDTO retorno=null;
        try{
            String queryString;
            StringBuilder jpql = new StringBuilder();
            //arma campos
            jpql.append("SELECT ds from PghDetalleSupervision ds WHERE ds.idSupervision.idOrdenServicio.idOrdenServicio=:idOrdenServicio and ds.estado=1");
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            query.setParameter("idOrdenServicio",idOrdenServicio);
            List<DetalleSupervisionDTO> detaSupe=DetalleSupervisionBuilder.toListDetalleSupervisionDto(query.getResultList());
            
            String flagCumplimiento=Constantes.ESTADO_ACTIVO;
            if(detaSupe!=null && detaSupe.size()>0){
                for(DetalleSupervisionDTO reg : detaSupe){
                    if(reg.getFlagResultado().equals(Constantes.ESTADO_INACTIVO)){
                        flagCumplimiento=Constantes.ESTADO_INACTIVO;
                        break;
                    }
                }
            }else{
                throw new OrdenServicioException("La Orden de Servicio no tiene Supervisión registrada.",null);
            }
            
            PghOrdenServicio registroDAO = crud.find(idOrdenServicio, PghOrdenServicio.class);
            registroDAO.setDatosAuditoria(usuarioDTO);
            registroDAO.setFlagCumplimiento(flagCumplimiento);
            crud.update(registroDAO);
            
            retorno=OrdenServicioBuilder.toOrdenServicioDto(registroDAO);
        }catch(Exception e){
            LOG.error("error en verificaActualizaCumplimiento",e);
            OrdenServicioException ex = new OrdenServicioException(e.getMessage(), e);
            throw ex;
        }
        return retorno;
    }
    
    @Override
    /* OSINE_SFS-480 - RSIS 40 - Inicio */
    public OrdenServicioDTO cambiarEstadoProceso(Long idOrdenServicio, Long idPersonalOri,Long idPersonalDest,Long idEstadoProceso,String motivoReasignacion,UsuarioDTO usuarioDTO,Long idPeticion, Long idMotivo) throws OrdenServicioException{ 
    /* OSINE_SFS-480 - RSIS 40 - Fin */
        LOG.info("cambiarEstadoProceso");
        OrdenServicioDTO retorno=null;
        try{
            PghOrdenServicio registroDAO = crud.find(idOrdenServicio, PghOrdenServicio.class);
            registroDAO.setDatosAuditoria(usuarioDTO);
            registroDAO.setIdEstadoProceso(new PghEstadoProceso(idEstadoProceso));
            registroDAO.setFechaEstadoProceso(new Date());
            if(idPeticion!=null){registroDAO.setIdPeticion(new MdiMaestroColumna(idPeticion));}
            if(idMotivo!=null){registroDAO.setIdMotivo(idMotivo);}
            if(motivoReasignacion!=null){registroDAO.setComentarioDevolucion(motivoReasignacion);}
            crud.update(registroDAO);
            
            MaestroColumnaDTO tipoHistorico=maestroColumnaDAO.findMaestroColumna(Constantes.DOMINIO_TIPO_COMPONENTE, Constantes.APLICACION_INPS, Constantes.CODIGO_TIPO_COMPONENTE_ORDEN_SERVICIO).get(0);
            /* OSINE_SFS-480 - RSIS 40 - Inicio */
            HistoricoEstadoDTO historicoEstado=historicoEstadoDAO.registrar(null, idOrdenServicio, idEstadoProceso, idPersonalOri, idPersonalDest, tipoHistorico.getIdMaestroColumna(),idPeticion,idMotivo,motivoReasignacion, usuarioDTO);   
            LOG.info("historicoEstado:"+historicoEstado);
            /* OSINE_SFS-480 - RSIS 40 - Fin */
            retorno=OrdenServicioBuilder.toOrdenServicioDto(registroDAO);
        }catch(Exception e){
            LOG.error("error en cambiarEstadoProceso",e);
            OrdenServicioException ex = new OrdenServicioException(e.getMessage(), e);
            throw ex;
        }
        return retorno;
    }
    
    @Override
    /* OSINE791 - RSIS1 - Inicio */
    //public OrdenServicioDTO registrar(Long idExpediente,Long idTipoAsignacion,Long idEstadoProceso,String codigoTipoSupervisor,Long idLocador,Long idSupervisoraEmpresa,UsuarioDTO usuarioDTO) throws OrdenServicioException{
    public OrdenServicioDTO registrar(Long idExpediente,Long idTipoAsignacion,Long idEstadoProceso,String codigoTipoSupervisor,Long idLocador,Long idSupervisoraEmpresa,UsuarioDTO usuarioDTO,String sigla) throws OrdenServicioException{
        /* OSINE791 - RSIS1 - Inicio */
    	LOG.error("registrar");
        OrdenServicioDTO retorno=null;
        try{
            Long iteracion=expedienteDAO.obtenerIteracionOrdenServicio(idExpediente);
            if(iteracion==null){
                throw new OrdenServicioException("Expediente no tiene Iteracion.",null);
            }
            
            PghOrdenServicio ordenServicio=new PghOrdenServicio();
            ordenServicio.setDatosAuditoria(usuarioDTO);
            ordenServicio.setIdExpediente(new PghExpediente(idExpediente));
            ordenServicio.setIdTipoAsignacion(idTipoAsignacion);
            ordenServicio.setEstado(Constantes.ESTADO_ACTIVO);
            ordenServicio.setIdEstadoProceso(new PghEstadoProceso(idEstadoProceso));
            ordenServicio.setFechaEstadoProceso(new Date());
            ordenServicio.setIteracion(iteracion);
            if(codigoTipoSupervisor.equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_NATURAL)){
                ordenServicio.setIdLocador(new MdiLocador(idLocador));
            }else if(codigoTipoSupervisor.equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_JURIDICA)){
                ordenServicio.setIdSupervisoraEmpresa(new MdiSupervisoraEmpresa(idSupervisoraEmpresa));
            }
            String numeroOrdenServicio=generarNumeroOrdenServicio(sigla);
            LOG.info("numeroOrdenServicio-->"+numeroOrdenServicio);
            ordenServicio.setNumeroOrdenServicio(numeroOrdenServicio);
            crud.create(ordenServicio);
            
            retorno=OrdenServicioBuilder.toOrdenServicioDto(ordenServicio);
        }catch(Exception e){
            LOG.error("error en registrar",e);
            OrdenServicioException ex = new OrdenServicioException(e.getMessage(), e);
            throw ex;
        }
        return retorno;
    }
    
    public String generarNumeroOrdenServicio(String sigla){
        //Long seq=crud.findSequence("PGH_NUMERO_OS_SEQ");
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy");
        //return Utiles.padLeft(seq.toString(), 8,'0')+"-"+formateador.format(new Date())+"-OS-GFHL";
        
        String numeroOrdenServicioGenerado = null;
        ResultSet rs = null;
		CallableStatement callableStatement = null;
		Connection con = null;
		try{
			con = ((SessionImpl)crud.getEm().getDelegate()).connection();
			callableStatement = con.prepareCall("{call PKG_SFH.SFH_GENERAR_NUMERO_CARTA_PRC(?)}");
			callableStatement.registerOutParameter(1, OracleTypes.VARCHAR);
			callableStatement.executeUpdate();
			
			numeroOrdenServicioGenerado = callableStatement.getString(1);

            }catch(Exception e) {
                LOG.error(e.getMessage(),e);
                e.printStackTrace();
         }
        return Utiles.padLeft(numeroOrdenServicioGenerado.toString(), 8,'0')+"-"+formateador.format(new Date())+"-OS-"+sigla;
    }
    
    @Override
    public List<OrdenServicioDTO> findByFilter(OrdenServicioFilter filtro) throws OrdenServicioException{
        LOG.info("findByFilter");
        List<OrdenServicioDTO> retorno=null;
        try{
            Query query = getFindQuery(filtro);
            retorno = OrdenServicioBuilder.toListOrdenServicioDto(query.getResultList());
        }catch(Exception e){
            LOG.info("Error en findByFilter",e);
        }
        return retorno;
    }
    
    private Query getFindQuery(OrdenServicioFilter filtro) {
        Query query=null;
        try{
            /* OSINE_SFS-480 - RSIS 13 - Inicio */
            if(filtro.getIteracion()!=null && filtro.getIdExpediente()!=null){
                query = crud.getEm().createNamedQuery("PghOrdenServicio.findByIteracionIdExpediente");
            }else 
            /* OSINE_SFS-480 - RSIS 13 - Fin */
                if(filtro.getIdOrdenServicio()!=null){
                query = crud.getEm().createNamedQuery("PghOrdenServicio.findByIdOrdenServicio");
            }else{
                query = crud.getEm().createNamedQuery("PghOrdenServicio.findAll");
            }
            
            if(filtro.getIdOrdenServicio()!=null){
                query.setParameter("idOrdenServicio",filtro.getIdOrdenServicio());
            }
            /* JPIRO REFACT - INICIO */
            if(filtro.getIteracion()!=null){
                query.setParameter("iteracion",filtro.getIteracion());
            }
            if(filtro.getIdExpediente()!=null){
                query.setParameter("idExpediente",filtro.getIdExpediente());
            }
            /* JPIRO REFACT - FIN */
        
        }catch(Exception e){
            LOG.error("Error getFindQuery: "+e.getMessage());
        }
        return query;
    }
    
    @Override
    public List<OrdenServicioDTO> find(OrdenServicioFilter filtro) throws OrdenServicioException{
        LOG.info("find");
        List<OrdenServicioDTO> retorno=null;
        try{
            String queryString;
            StringBuilder jpql = new StringBuilder();
            //arma campos
            jpql.append("SELECT new PghOrdenServicio(os.idOrdenServicio,os.fechaCreacion,os.numeroOrdenServicio,us.codigoOsinergmin,us.nombreUnidad,"
                    + "ex.numeroExpediente,ep.nombreEstado,os.fechaEstadoProceso,os.flagPresentoDescargo,os.iteracion,ep.idEstadoProceso) "
                    + "FROM PghOrdenServicio os "
                    + "LEFT JOIN os.idEstadoProceso ep "
                    + "LEFT JOIN os.idLocador lo "
                    + "LEFT JOIN os.idSupervisoraEmpresa se "
                    + "LEFT JOIN lo.pghPersonalList pelo "
                    + "LEFT JOIN se.pghPersonalList pese "
                    + "LEFT JOIN os.idExpediente ex "
                    + "LEFT JOIN ex.idUnidadSupervisada us "
                    + "LEFT JOIN ex.idEmpresaSupervisada es ");
            
            //arma where
            jpql.append("WHERE os.estado=1 and ex.estado ='1' ");
            if(filtro.getIdPersonal()!=null){
            	jpql.append(" and (pese.idPersonal=:idPersonal or pelo.idPersonal=:idPersonal) ");
            }
            
            if(filtro.getIdEstadoProceso()!=null){
                jpql.append(" and ep.idEstadoProceso=:idEstadoProceso ");
            }
            if(filtro.getFechaInicioEstadoProceso()!=null && filtro.getFechaFinEstadoProceso()!=null){
                jpql.append(" and os.fechaEstadoProceso between :fechaInicioEstadoProceso and :fechaFinEstadoProceso ");
            }
            if(filtro.getIdExpediente()!=null){
            	jpql.append(" and ex.idExpediente=:idExpediente ");
            }
            if(filtro.getIteracion()!=null){
            	jpql.append(" and os.iteracion=:iteracion ");
            }
            if(filtro.getNumeroExpediente()!=null && !filtro.getNumeroExpediente().equals("")){
            	jpql.append(" and ex.numeroExpediente like :numeroExpediente ");
            }
            if(filtro.getNumeroOrdenServicio()!=null && !filtro.getNumeroOrdenServicio().equals("")){
            	jpql.append(" and os.numeroOrdenServicio like :numeroOrdenServicio ");
            }
            if(filtro.getCodigoOsinergmin()!=null && !filtro.getCodigoOsinergmin().equals("")){
            	jpql.append(" and us.codigoOsinergmin like :codigoOsinergmin ");
            }
            
            //order
            jpql.append(" order by os.fechaCreacion desc ");
            
            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            //settear parametros
            if(filtro.getIdPersonal()!=null){
            	query.setParameter("idPersonal",filtro.getIdPersonal());
            }
            if(filtro.getIdEstadoProceso()!=null){
                query.setParameter("idEstadoProceso",filtro.getIdEstadoProceso());
            }
            if(filtro.getFechaInicioEstadoProceso()!=null && filtro.getFechaFinEstadoProceso()!=null){
                query.setParameter("fechaInicioEstadoProceso",filtro.getFechaInicioEstadoProceso());
                query.setParameter("fechaFinEstadoProceso",Utiles.sumarFechasDias(filtro.getFechaFinEstadoProceso(),1));
            }
            if(filtro.getIdExpediente()!=null){
            	query.setParameter("idExpediente",filtro.getIdExpediente());
            }
            if(filtro.getIteracion()!=null){
            	query.setParameter("iteracion",filtro.getIteracion());
            }
            if(filtro.getNumeroExpediente()!=null && !filtro.getNumeroExpediente().equals("")){
            	query.setParameter("numeroExpediente","%"+(filtro.getNumeroExpediente().trim().toUpperCase())+"%");
            }
            if(filtro.getNumeroOrdenServicio()!=null && !filtro.getNumeroOrdenServicio().equals("")){
            	query.setParameter("numeroOrdenServicio","%"+(filtro.getNumeroOrdenServicio().trim().toUpperCase())+"%");
            }
            if(filtro.getCodigoOsinergmin()!=null && !filtro.getCodigoOsinergmin().equals("")){
            	query.setParameter("codigoOsinergmin","%"+(filtro.getCodigoOsinergmin().trim())+"%");
            }

            retorno= OrdenServicioBuilder.toListOrdenServicioDto(query.getResultList());
        }catch(Exception e){
            LOG.error("error en find",e);
        }
        return retorno;
    }
        
    @Override
    public OrdenServicioDTO confirmarDescargo(Long idOrdenServicio,UsuarioDTO usuarioDTO) throws OrdenServicioException{
        LOG.info("confirmarDescargo");
        OrdenServicioDTO retorno=null;
        try{
            PghOrdenServicio registroDAO = crud.find(idOrdenServicio, PghOrdenServicio.class);
            registroDAO.setDatosAuditoria(usuarioDTO);
            registroDAO.setFlagPresentoDescargo(Constantes.ESTADO_ACTIVO);
            crud.update(registroDAO);
            
            retorno=OrdenServicioBuilder.toOrdenServicioDto(registroDAO);
        }catch(Exception e){
            LOG.error("error en confirmarDescargo",e);
            OrdenServicioException ex = new OrdenServicioException(e.getMessage(), e);
            throw ex;
        }
        return retorno;
    }
    /* OSINE_SFS-480 - RSIS 17 - Inicio */
    @Override
    public Date calculoFechaFin(String fechaInicio, String idDepartamento, Long nroDias) throws OrdenServicioException{ 
        LOG.info("Ejecutando el procedimiento calculoFechaFin");
        Date retorno=null;
        Query query=null;
        try{           	
        	String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT SIGED_CALCULOFECHAFIN(:fechaInicio, :idDepartamento, :nroDias) FROM DUAL");
            queryString = jpql.toString();
            query = this.crud.getEm().createNativeQuery(queryString);
            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("idDepartamento", idDepartamento);
            query.setParameter("nroDias", nroDias);
            Object obj=query.getResultList().get(0);      	
            LOG.info("fechaInicio: " + fechaInicio);
            LOG.info("idDepartamento: " + idDepartamento);
            LOG.info("nroDias: " + nroDias);  
            retorno=new SimpleDateFormat("dd/MM/yy").parse(obj.toString());
        }catch(Exception e){
            LOG.error("error en calculoFechaFin", e);
            OrdenServicioException ex = new OrdenServicioException(e.getMessage(), e);
            throw ex;
        }
        return retorno;
    } 
    
    @Override
	public OrdenServicioDTO actualizar(OrdenServicioFilter filtro, UsuarioDTO usuarioDTO) throws OrdenServicioException {
    	LOG.info("actualizar");
    	OrdenServicioDTO retorno=null;
        try{        	
        	PghOrdenServicio registroDAO = crud.find(filtro.getIdOrdenServicio(), PghOrdenServicio.class);
            registroDAO.setDatosAuditoria(usuarioDTO);
            /* OSINE_SFS-791 - RSIS 33 - Inicio */ 
            if(filtro.getFechaIniPlazoDescargo()!=null) { registroDAO.setFechaIniPlazoDescargo(filtro.getFechaIniPlazoDescargo()); }
            if(filtro.getFechaFinPlazoDescargo()!=null) { registroDAO.setFechaFinPlazoDescargo(filtro.getFechaFinPlazoDescargo()); }
            if(filtro.getIdArchivoPlazoDescargo()!=null) { registroDAO.setIdArchivoPlazoDescargo(filtro.getIdArchivoPlazoDescargo()); }
            if(filtro.getIdDepartPlazoDescargo()!=null) { registroDAO.setIdDepartPlazoDescargo(filtro.getIdDepartPlazoDescargo()); }
            if(filtro.getFlagConfirmaTipoAsignacion()!=null) { registroDAO.setFlagConfirmaTipoAsignacion(filtro.getFlagConfirmaTipoAsignacion());};
            if(filtro.getComentarios() != null){registroDAO.setComentarios(filtro.getComentarios());}
            /* OSINE_SFS-791 - RSIS 33 - Fin */ 
            crud.update(registroDAO);           
            retorno=OrdenServicioBuilder.toOrdenServicioDto(registroDAO);
        }catch(Exception e){
            LOG.error("error en actualizar", e);
        } 
        return retorno;
	} 
    /* OSINE_SFS-480 - RSIS 17 - Fin */

    /* OSINE_SFS-480 - RSIS 43 - Inicio */
	@Override
	public OrdenServicioDTO anularExpedienteOrden(Long idExpediente, Long idOrdenServicio, UsuarioDTO usuarioDTO) throws OrdenServicioException {
		LOG.info("anularExpedienteOrden");
    	OrdenServicioDTO retorno=null;
        try{        	
        	PghOrdenServicio anularDAO = crud.find(idOrdenServicio, PghOrdenServicio.class);
        	anularDAO.setEstado(Constantes.ESTADO_INACTIVO);
        	anularDAO.setDatosAuditoria(usuarioDTO);
            crud.update(anularDAO);           
            retorno=OrdenServicioBuilder.toOrdenServicioDto(anularDAO);
        }catch(Exception e){
            LOG.error("error en anularExpedienteOrden",e);
        } 
        return retorno;
	}
    /* OSINE_SFS-480 - RSIS 43 - Fin */
    
    /* OSINE_SFS-480 - RSIS 47 - Inicio */
    @Override
    public OrdenServicioDTO editarExpedienteOrdenServicio(Long idOrdenServicio, Long idTipoAsignacion, String codigoTipoSupervisor,Long idLocador, Long idSupervisoraEmpresa,UsuarioDTO usuarioDTO,String flagConfirmaTipoAsignacion) throws OrdenServicioException {
        LOG.error("editarOrdenServicio");
        OrdenServicioDTO retorno=null;
        try{
            PghOrdenServicio registro = crud.find(idOrdenServicio, PghOrdenServicio.class);                    
            registro.setIdTipoAsignacion(idTipoAsignacion);
            registro.setDatosAuditoria(usuarioDTO);            
            if(codigoTipoSupervisor.equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_NATURAL)){
                registro.setIdLocador(new MdiLocador(idLocador));
                registro.setIdSupervisoraEmpresa(null);
            }else if(codigoTipoSupervisor.equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_JURIDICA)){
                registro.setIdSupervisoraEmpresa(new MdiSupervisoraEmpresa(idSupervisoraEmpresa));
                registro.setIdLocador(null);
            }
            if(!StringUtil.isEmpty(flagConfirmaTipoAsignacion)){
                registro.setFlagConfirmaTipoAsignacion(flagConfirmaTipoAsignacion);
            }
            //se limpian estos campos al concluir la edicion
            registro.setIdPeticion(null);
            registro.setIdMotivo(null);
            registro.setComentarioDevolucion(null);
                        
            crud.update(registro);
            
            retorno=OrdenServicioBuilder.toOrdenServicioDto(registro);
        }catch(Exception e){
            LOG.error("error en editarOrdenServicio",e);
            OrdenServicioException ex = new OrdenServicioException(e.getMessage(), e);
            throw ex;
        }
        return retorno;
    }
    /* OSINE_SFS-480 - RSIS 47 - Fin */
}
