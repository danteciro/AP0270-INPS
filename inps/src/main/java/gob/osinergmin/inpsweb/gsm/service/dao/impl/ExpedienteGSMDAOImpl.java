/**
* Resumen		
* Objeto			: ExpedienteGSMDAOImpl.java
* Descripción		: Clase que contiene la implementación de los métodos declarados en la 
* 					  clase interfaz ExpedienteGSMDAO.
* Fecha de Creación	: 25/10/2016.
* PR de Creación	: OSINE_SFS-1344.
* Autor				: GMD.
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
*  
*/

package gob.osinergmin.inpsweb.gsm.service.dao.impl;
import gob.osinergmin.inpsweb.domain.MdiEmpresaSupervisada;
import gob.osinergmin.inpsweb.domain.MdiMaestroColumna;
import gob.osinergmin.inpsweb.domain.PghEstadoProceso;
import gob.osinergmin.inpsweb.domain.PghExpediente;
import gob.osinergmin.inpsweb.domain.builder.ExpedienteGSMBuilder;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteGSMDTO;
import gob.osinergmin.mdicommon.domain.dto.OrdenServicioDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.ExpedienteFilter;
import gob.osinergmin.inpsweb.domain.MdiUnidadSupervisada;
import gob.osinergmin.inpsweb.domain.PghFlujoSiged;
import gob.osinergmin.inpsweb.domain.PghObligacionSubTipo;
import gob.osinergmin.inpsweb.domain.PghObligacionTipo;
import gob.osinergmin.inpsweb.domain.PghOrdenServicio;
import gob.osinergmin.inpsweb.domain.PghPersonal;
import gob.osinergmin.inpsweb.domain.PghProceso;
import gob.osinergmin.inpsweb.domain.PghResultadoSupervision;
import gob.osinergmin.inpsweb.domain.PghTramite;
import gob.osinergmin.inpsweb.gsm.service.dao.ExpedienteGSMDAO;
// htorress - RSIS 1 y 2 - Inicio
import gob.osinergmin.mdicommon.domain.dto.PersonalAsignadoDTO;
// htorress - RSIS 1 y 2 - Fin
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.EstadoProcesoDAO;
import gob.osinergmin.inpsweb.service.dao.HistoricoEstadoDAO;
import gob.osinergmin.inpsweb.service.dao.MaestroColumnaDAO;
import gob.osinergmin.inpsweb.service.dao.OrdenServicioDAO;
import gob.osinergmin.inpsweb.service.dao.PersonalDAO;
import gob.osinergmin.inpsweb.service.exception.ExpedienteException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.StringUtil;
import gob.osinergmin.mdicommon.domain.dto.EstadoProcesoDTO;
import gob.osinergmin.mdicommon.domain.dto.HistoricoEstadoDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.PersonalDTO;
import gob.osinergmin.mdicommon.domain.ui.EstadoProcesoFilter;
import gob.osinergmin.mdicommon.domain.ui.PersonalFilter;
//htorress - RSIS 1 y 2 - Inicio
import java.util.ArrayList;
//htorress - RSIS 1 y 2 - Fin
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("expedienteGSMDAO")
public class ExpedienteGSMDAOImpl implements ExpedienteGSMDAO {
    private static final Logger LOG = LoggerFactory.getLogger(ExpedienteGSMDAOImpl.class);    
    @Inject
    private CrudDAO crud;
    @Inject
    private MaestroColumnaDAO maestroColumnaDAO;
    @Inject
    private HistoricoEstadoDAO historicoEstadoDAO;
    @Inject
    private EstadoProcesoDAO estadoProcesoDAO;
    @Inject
    private PersonalDAO personalDAO;
    @Inject
    private OrdenServicioDAO ordenServicioDAO;
    
    @Override
    public Long obtenerIteracionOrdenServicio(Long idExpediente) throws ExpedienteException{
        Long iteracion=new Long(0);
        
        Query query=crud.getEm().createNamedQuery("PghExpediente.countOrdenServicio");
        query.setParameter("idExpediente",idExpediente);
        iteracion = (Long) query.getSingleResult();
        iteracion++;
        LOG.info("iteracion="+iteracion);
        
        return iteracion;
    }
    
    @Override
    public ExpedienteGSMDTO veriActuFlgTramFinalizado(Long idExpediente,Long idOrdenServicio,UsuarioDTO usuarioDTO) throws ExpedienteException{
        LOG.info("veriActuFlgTramFinalizado");
        ExpedienteGSMDTO retorno=null;
        try{
            PghOrdenServicio ordeServ = crud.find(idOrdenServicio, PghOrdenServicio.class);
            
            PghExpediente expe = crud.find(idExpediente, PghExpediente.class);
            if(ordeServ.getIteracion()!=null && ordeServ.getIteracion()==Constantes.SUPERVISION_SEGUNDA_ITERACION){
                expe.setFlagTramiteFinalizado(Constantes.ESTADO_ACTIVO);
            }else if(ordeServ.getIdSupervision()==null){//OS no tiene data en PGH_SUPERVISION
                expe.setFlagTramiteFinalizado(Constantes.ESTADO_ACTIVO);
            }else{
                if(ordeServ.getFlagCumplimiento()==null){
                    throw new ExpedienteException("Casuistica no encontrada para Finalizar la Orden de Servicio. Falta Dato Cumplimiento",null);
                }else if( (ordeServ.getIdSupervision().getFlagSupervision()!=null && ordeServ.getIdSupervision().getFlagSupervision().equals(Constantes.ESTADO_INACTIVO) ) || 
                    (ordeServ.getIdSupervision().getFlagSupervision()!=null && ordeServ.getIdSupervision().getFlagSupervision().equals(Constantes.ESTADO_ACTIVO) && ordeServ.getFlagCumplimiento().equals(Constantes.ESTADO_ACTIVO))){
                    expe.setFlagTramiteFinalizado(Constantes.ESTADO_ACTIVO);
                }else if(ordeServ.getIdSupervision().getFlagSupervision()!=null && ordeServ.getIdSupervision().getFlagSupervision().equals(Constantes.ESTADO_ACTIVO) && ordeServ.getFlagCumplimiento().equals(Constantes.ESTADO_INACTIVO)){
                    expe.setFlagTramiteFinalizado(Constantes.ESTADO_INACTIVO);
                }else{
                    throw new ExpedienteException("Casuistica no encontrada para Finalizar la Orden de Servicio. Revisar Supervision.",null);
                }
            }
            expe.setDatosAuditoria(usuarioDTO);
            crud.update(expe);
            retorno=ExpedienteGSMBuilder.toExpedienteDto(expe);
        }catch(Exception e){
            LOG.error("error en veriActuFlgTramFinalizado",e);
            ExpedienteException ex = new ExpedienteException(e.getMessage(), e);
            throw ex;
        }
        return retorno;
    }
    
    @Override
    public ExpedienteGSMDTO cambiarEstadoProceso(Long idExpediente,Long idPersonal,Long idPersonalOri, Long idPersonalDest,Long idEstadoProceso,String motivoReasignacion,UsuarioDTO usuarioDTO) throws ExpedienteException{
    	ExpedienteGSMDTO retorno=null;
        try{
            PghExpediente registroDAO = crud.find(idExpediente, PghExpediente.class);
            registroDAO.setDatosAuditoria(usuarioDTO);
            registroDAO.setIdPersonal(new PghPersonal(idPersonal));
            registroDAO.setIdEstadoProceso(new PghEstadoProceso(idEstadoProceso));
            registroDAO.setFechaEstadoProceso(new Date());
            crud.update(registroDAO);
            
            MaestroColumnaDTO tipoHistorico=maestroColumnaDAO.findMaestroColumna(Constantes.DOMINIO_TIPO_COMPONENTE, Constantes.APLICACION_INPS, Constantes.CODIGO_TIPO_COMPONENTE_EXPEDIENTE).get(0);
            /* OSINE_SFS-480 - RSIS 40 - Inicio */
            HistoricoEstadoDTO historicoEstado=historicoEstadoDAO.registrar(idExpediente, null, idEstadoProceso, idPersonalOri, idPersonalDest, tipoHistorico.getIdMaestroColumna(),null,null, motivoReasignacion, usuarioDTO); 
            LOG.info("historicoEstado:"+historicoEstado);
            /* OSINE_SFS-480 - RSIS 40 - Fin */
            retorno=ExpedienteGSMBuilder.toExpedienteDto(registroDAO);
        }catch(Exception e){
            LOG.error("error en derivar");
            ExpedienteException ex = new ExpedienteException(e.getMessage(), e);
            throw ex;
        }
        return retorno;
    }
    
    @Override
    public List<ExpedienteGSMDTO> findDerivadosByIdPersonal(ExpedienteFilter filtro) throws ExpedienteException {
        List<ExpedienteGSMDTO> listado;
        
        Query query = crud.getEm().createNamedQuery("PghExpediente.findDerivadosByIdPersonal");
        query.setParameter("idPersonalOri",filtro.getIdPersonal());
        
        listado = ExpedienteGSMBuilder.toListExpedienteDto(query.getResultList());

        return listado;
    }
    
    @Override
    public List<ExpedienteGSMDTO> findByFilter(ExpedienteFilter filtro) throws ExpedienteException{
        LOG.info("findByFilter");
        List<ExpedienteGSMDTO> retorno=null;
        try{
            Query query = getFindQuery(filtro);
            retorno = ExpedienteGSMBuilder.toListExpedienteDto(query.getResultList());
        }catch(Exception e){
            LOG.error("Error en findByFilter",e);
        }
        return retorno;
    }
    
    private Query getFindQuery(ExpedienteFilter filtro) {
        Query query=null;
        try{
            
            if(filtro.getIdExpediente()!=null){
                query = crud.getEm().createNamedQuery("PghExpediente.findByIdExpediente");
            }else{
                query = crud.getEm().createNamedQuery("PghExpediente.findAll");
            }
            
            if(filtro.getIdExpediente()!=null){
                query.setParameter("idExpediente",filtro.getIdExpediente());
            }
        
        }catch(Exception e){
            LOG.error("Error getFindQuery: "+e.getMessage());
        }
        return query;
    }
    
    @Override
    public List<ExpedienteGSMDTO> findForGrid(ExpedienteFilter filtro) throws ExpedienteException {
        LOG.info("find");
        List<ExpedienteGSMDTO> retorno=null;
        try{
            String queryString;
            StringBuilder jpql = new StringBuilder();
            //arma campos
            jpql.append("SELECT distinct new PghExpediente"
                + "("
                    + "p.idExpediente,p.numeroExpediente,p.fechaCreacionSiged,p.asuntoSiged,p.flagOrigen,p.idObligacionTipo.idObligacionTipo,p.flagTramiteFinalizado,"
                    + "tr.idTramite,et.idEtapa,pr.idProceso, pr2.idProceso,"
//                    + "us.idUnidadSupervisada,us.codigoOsinergmin,us.nombreUnidad,"
					+ "us.idDmUnidadSupervisada,us.codigoUnidadSupervisada,us.nombreUnidadSupervisada,tmin.idMaestroColumna,tmin.descripcion,test.idMaestroColumna,test.descripcion,"
//                    + "es.idEmpresaSupervisada,es.razonSocial,es.ruc,es.numeroIdentificacion,tdies.idMaestroColumna,tdies.descripcion,"
                    + "es.idTitularMinero,es.nombreTitularMinero,es.codigoTitularMinero,"
                    + "fs.idFlujoSiged,fs.nombreFlujoSiged,"
                    + "pe.idPersonal,pe.nombre,pe.apellidoPaterno,pe.apellidoMaterno, "
                    + "( select max(he.fechaCreacion) from PghHistoricoEstado he "
                    + "     where he.idExpediente.idExpediente=p.idExpediente and he.idPersonalDest.idPersonal=p.idPersonal.idPersonal "
                    + "     and he.idEstadoProceso.idEstadoProceso=:idEstadoProcesoDerivado "
                    + " ) as fechaDerivacion, "
                    + "os.idOrdenServicio, os.fechaCreacion,os.numeroOrdenServicio,os.idTipoAsignacion,os.iteracion,os.flagCumplimiento, "
                    + "ep2.idEstadoProceso, ep2.identificadorEstado,ep2.nombreEstado,"
                    + "lo.idLocador,lo.primerNombre||' '||lo.segundoNombre||' '||lo.apellidoPaterno||' '||lo.apellidoMaterno as nombreCompletoArmado,"
                    + "se.idSupervisoraEmpresa,se.razonSocial,se.nombreConsorcio,"
                    + "( select he2.motivoReasignacion from PghHistoricoEstado he2 " +
                        "where he2.idOrdenServicio.idOrdenServicio=os.idOrdenServicio " +
                        "and he2.fechaCreacion=(select max(he3.fechaCreacion) from PghHistoricoEstado he3 where he3.idOrdenServicio.idOrdenServicio=os.idOrdenServicio)"
                    + " and rownum=1) as motivoReasignacion, "
                    + "( select count(osc.idOrdenServicio) from PghOrdenServicio osc where osc.estado=1 and osc.idExpediente.idExpediente=p.idExpediente ) as nroOrdenesServicio, "
                    /* OSINE_SFS-480 - RSIS 38 - Inicio */
//                    + " (select rhw.numeroRegistroHidrocarburo from MdiRegistroHidrocarburo rhw where rhw.estado=1"
//                        + "and rhw.idUnidadSupervisada.idUnidadSupervisada=us.idDmUnidadSupervisada "
//                        + "and rhw.estadoProceso IN ("
//                            + " select mc.idMaestroColumna "
//                            + " FROM MdiMaestroColumna mc "
//                            + " where mc.aplicacion ='"+ Constantes.APLICACION_SIGUO +"' and mc.dominio='"+ Constantes.DOMINIO_ESTADO_RHO +"' and mc.codigo='"+Constantes.DOMINIO_ESTADO_RHO_CODIGO_ACTIVO+"') "
//                            + " and rownum=1 ) as numeroRegistroHidrocarburo, "
                    + "a.nombre as nombreActividad "
                    + ", ospe.idMaestroColumna, ospe.descripcion, ospe.codigo, os.idMotivo, os.comentarioDevolucion,"
                    /* OSINE_SFS-791 - RSIS 33 - Inicio */
                    + "os.flagConfirmaTipoAsignacion "
                    /* OSINE_SFS-791 - RSIS 33 - Fin */
                    + ", ( select max(ios.iteracion) from PghOrdenServicio ios where ios.estado=1 and ios.idExpediente.idExpediente=p.idExpediente ) as iteracionExpediente "
                    + ", p.flagMuestral,p.idObligacionSubTipo.idObligacionSubTipo "                    
                   /* OSINE_SFS-480 - RSIS 38 - Fin */
                    /* OSINE791 - RSIS 21 - Inicio */ 
                    + ", a.idActividad "
                    + ", (select epsgt.identificadorEstado " 
                        + "from PghProcesoObligacionTipo ot "
                        + "left join ot.idFlujoEstado fe "
                        + "left join fe.pghFlujoEstEstadoProcList feep "
                        + "left join feep.idEstadoProcesoSgt epsgt " 
                        + "where ot.mdiActividad.idActividad=a.idActividad and ot.pghProceso.idProceso=pr2.idProceso and ot.pghObligacionTipo.idObligacionTipo=p.idObligacionTipo.idObligacionTipo "
                        + "and feep.estado=1 and feep.idEstadoProceso.idEstadoProceso=ep2.idEstadoProceso and rownum=1) as os_identificador_estado_sgt "
                    /* OSINE791 - RSIS 21 - Fin */ 
                    /* OSINE791 - RSIS 24 - Inicio */ 
                    + ", (select ln.codigo " 
                        + "from PghProcesoObligacionTipo ot left join ot.flujoSupervInps ln "
                        + "where ot.mdiActividad.idActividad=a.idActividad and ot.pghProceso.idProceso=pr2.idProceso and ot.pghObligacionTipo.idObligacionTipo=p.idObligacionTipo.idObligacionTipo and rownum=1"
                        + ") as codigoFlujoSupervInps "
                    /* OSINE791 - RSIS 24 - Fin */ 
                    /* OSINE_SFS-791 - RSIS 33 - Inicio */ 
                    + ", (select ot.flagEvaluaTipoAsignacion " 
                        + "from PghProcesoObligacionTipo ot "
                        + "where ot.estado = '1' and ot.mdiActividad.idActividad=a.idActividad and ot.pghProceso.idProceso=pr2.idProceso and ot.pghObligacionTipo.idObligacionTipo=p.idObligacionTipo.idObligacionTipo and rownum=1"
                        + ") as flagEvaluaTipoAsignacion "
                    /* OSINE_SFS-791 - RSIS 33 - Fin */ 
                + ") "
                + "FROM PghExpediente p "
                + "LEFT JOIN p.idTramite tr "
                + "LEFT JOIN tr.idEtapa et "
                + "LEFT JOIN et.idProceso pr "
                + "LEFT JOIN p.idProceso pr2 "
//                + "LEFT JOIN p.idUnidadSupervisada us "
				+ "LEFT JOIN p.idDmUnidadSupervisada us "
				+ "LEFT JOIN us.idTipoMinado tmin "
				+ "LEFT JOIN us.idEstrato test "
//                + "LEFT JOIN p.idEmpresaSupervisada es "
				+ "LEFT JOIN p.idTitularMinero es "
            /*    + "LEFT JOIN es.tipoDocumentoIdentidad tdies "*/
                + "LEFT JOIN p.idEstadoProceso ep "
                + "LEFT JOIN p.idFlujoSiged fs "
                + "LEFT JOIN p.idPersonal pe "
                + "LEFT JOIN p.pghOrdenServicioList os "
                + "LEFT JOIN os.idLocador lo "
                + "LEFT JOIN os.idSupervisoraEmpresa se "
                + "LEFT JOIN os.idEstadoProceso ep2 "
                + "LEFT JOIN se.pghPersonalList pese "
                /* OSINE_SFS-480 - RSIS 38 - Inicio */
                + "LEFT JOIN us.idActividad a "    
                + "LEFT JOIN os.idPeticion ospe "        
                /* OSINE_SFS-480 - RSIS 38 - Fin */
                /* OSINE_SFS-480 - RSIS 40 - Inicio */                
                + "LEFT JOIN os.idSupervision su "
                /* OSINE_SFS-480 - RSIS 40 - Fin */    
                + "LEFT JOIN lo.pghPersonalList pelo ");
            //arma where
            /* OSINE_SFS-480 - RSIS 43 - Inicio */  
            //jpql.append("WHERE p.estado=1 and ep.identificadorEstado=:identificadorEstado and (os.estado is null or os.estado='1') ");
            jpql.append("WHERE p.estado=1 and ep.identificadorEstado=:identificadorEstado ");
            /* OSINE_SFS-791 - RSIS43 - Inicio */  
            if(filtro.getIdentificadorEstado().equals(Constantes.IDENTIFICADOR_ESTADO_PROCESO_EXP_DERIVADO)){                                    
            	jpql.append("and  ( select max(ios.iteracion) from PghOrdenServicio ios where ios.estado=1 and ios.idExpediente.idExpediente=p.idExpediente ) = os.iteracion ");
            }
            /* OSINE_SFS-791 - RSIS43 - Fin */  
            if(filtro.getCadEstado()!=null && !filtro.getCadEstado().equals("")){
                jpql.append("and (os.estado is null or os.estado in ("+filtro.getCadEstado()+")) ");
            }else{
                jpql.append("and (os.estado is null or os.estado='1') ");
            }
            /* OSINE_SFS-480 - RSIS 43 - Fin */              
            //para estado de la Orden de Servicio
            jpql.append(" and (ep2.identificadorEstado is null ");
                if(filtro.getCadIdentificadorEstadoOrdenServicio()!=null && !filtro.getCadIdentificadorEstadoOrdenServicio().equals("")){
                    jpql.append(" or ep2.identificadorEstado in ("+filtro.getCadIdentificadorEstadoOrdenServicio()+") ");
                }
            jpql.append(" ) ");
            //para filtrar por el Personal segun rol y bandejas. 
            //En Rol RESPONSABLE si se le activa alguna bandeja de ESPECIALISTA vera todos los Expedientes
            if( (filtro.getIdentificadorRol()!=null && filtro.getIdentificadorOpcion()!=null) && 
                ( filtro.getIdentificadorRol().equals(Constantes.IDENTIFICADOR_ROL_RESPONSABLE) && 
                  !filtro.getIdentificadorOpcion().equals(Constantes.IDENTIFICADOR_OPCION_RESP_RECEPCIONADO) ) //En bandeja RECEPCIONADO si debe filtrarle los Expedientes al RESPONSABLE
            ){
                LOG.info("Bandeja se muestra sin filtrar PERSONAL");
            }else{
                if(filtro.getIdentificadorRol()!=null && !filtro.getIdentificadorRol().equals("") && filtro.getIdentificadorRol().equals(Constantes.CONSTANTE_ROL_SUPERVISOR)){
                    jpql.append(" and (pese.idPersonal=:idPersonal or pelo.idPersonal=:idPersonal) ");
                }else{
                    // htorress - RSIS 1 y 2 - Inicio
                    if(filtro.getIdentificadorRol()!=null && !filtro.getIdentificadorRol().equals("")){ 
                        if((filtro.getIdentificadorRol().equals(Constantes.IDENTIFICADOR_ROL_GERENTE_DIVISION))){
                            if(filtro.getIdsPersonalAsignado().size()>0){
                                jpql.append(" and p.idPersonal.idPersonal IN (:idPersonal)  ");
                            }else{
                      		jpql.append(" and p.idPersonal.idPersonal=:idPersonal ");
                            }
                        }else{
                	// htorress - RSIS 1 y 2 - Fin
                            jpql.append(" and p.idPersonal.idPersonal=:idPersonal ");
                        // htorress - RSIS 1 y 2 - Inicio
                        }
                    }    
                    // htorress - RSIS 1 y 2 - Fin
                }
            }
            //para no mostrar Ordenes de Servicio DEVUELTAS por el supervisor
            if(filtro.getIdentificadorRol()!=null && !filtro.getIdentificadorRol().equals("") && filtro.getIdentificadorRol().equals(Constantes.CONSTANTE_ROL_SUPERVISOR)){
                /* OSINE_SFS-480 - RSIS 03 - Inicio */
                jpql.append(" and (os.idPeticion.idMaestroColumna is null and os.idMotivo is null) "); 
                /* OSINE_SFS-480 - RSIS 03 - Fin */
            }
            /* OSINE_SFS-480 - RSIS 40 - Inicio */
            if(filtro.getFlagDevolverAsignacion() !=null && !filtro.getFlagDevolverAsignacion().equals("") && filtro.getFlagDevolverAsignacion().equals(Constantes.ESTADO_ACTIVO)){
                jpql.append(" and su.idSupervision is null");
            }
            /* OSINE_SFS-480 - RSIS 40 - Fin */ 
            ////FILTROS FROM BUSQ EXPEDIENTE
            if(filtro.getNumeroExpediente()!=null && !filtro.getNumeroExpediente().equals("")){
                jpql.append(" and p.numeroExpediente like :numeroExpediente ");
            }
            if(filtro.getNumeroOrdenServicio()!=null && !filtro.getNumeroOrdenServicio().equals("")){
                jpql.append(" and os.numeroOrdenServicio like :numeroOrdenServicio ");
            }
            if(filtro.getCodigoOsinergmin() !=null && !filtro.getCodigoOsinergmin().equals("")){
                jpql.append(" and upper(us.codigoOsinergmin) like :codigoOsinergmin ");
            }
            if(filtro.getTipoEmpresaSupervisora() !=null){
            	if(filtro.getTipoEmpresaSupervisora().equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_NATURAL)){
            		jpql.append(" and lo.idLocador=:idLocador ");
            		
            	}else if(filtro.getTipoEmpresaSupervisora().equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_JURIDICA)){
            		jpql.append(" and se.idSupervisoraEmpresa=:idSupervisoraEmpresa ");
            	}
            }
            /* OSINE_SFS-480 - RSIS 29, 30 y 31 - Inicio */
            if(filtro.getNumeroRegistroHidrocarburo() !=null && !filtro.getNumeroRegistroHidrocarburo().equals("")){
                jpql.append(" and (select rhw.numeroRegistroHidrocarburo from MdiRegistroHidrocarburo rhw where rhw.estado=1"
                        + "and rhw.idUnidadSupervisada.idUnidadSupervisada=us.idDmUnidadSupervisada "
                        + "and rhw.estadoProceso IN ("
                            + " select mc.idMaestroColumna "
                            + " FROM MdiMaestroColumna mc "
                            + " where mc.aplicacion ='"+ Constantes.APLICACION_SIGUO +"' and mc.dominio='"+ Constantes.DOMINIO_ESTADO_RHO +"' and mc.codigo='"+Constantes.DOMINIO_ESTADO_RHO_CODIGO_ACTIVO+"') "
                        + " and rownum=1 ) like :numeroRegistroHidrocarburo ");               
            } 
            
            // Filtros para Ubigeo
            if((filtro.getIdDepartamento() !=null && !filtro.getIdDepartamento().equals(""))){
            	jpql.append(" and (select COUNT(*) from MdiDirccionUnidadSuprvisada dd where dd.estado=1"
                        + "and dd.idUnidadSupervisada.idUnidadSupervisada=us.idDmUnidadSupervisada "
                        + "and dd.idTipoDireccion IN  (select mc.idMaestroColumna from MdiMaestroColumna mc "
                        + "where mc.dominio='"+Constantes.DOMINIO_TIPO_DIRECCION+"' and "
                        + "mc.codigo IN (select mc1.codigo from MdiMaestroColumna mc1 "
                        + "where mc1.dominio='"+Constantes.DOMINIO_DIRE_INPS_UO+"') and rownum=1) "
                        + "and dd.mdiUbigeo.mdiUbigeoPK.idDepartamento =:idDepartamento ");
                        if(filtro.getIdProvincia() !=null && !filtro.getIdProvincia().equals("")){
                        	jpql.append(" and dd.mdiUbigeo.mdiUbigeoPK.idProvincia =:idProvincia ");
                        }
                        if(filtro.getIdDistrito() !=null && !filtro.getIdDistrito().equals("")){
                        	jpql.append(" and dd.mdiUbigeo.mdiUbigeoPK.idDistrito =:idDistrito ");
                        }
                jpql.append(") > 0");
            }

            if(filtro.getIdPeticion() !=null && !filtro.getIdPeticion().equals("")){
                /* OSINE_SFS-480 - RSIS 03 - Inicio */
                jpql.append(" and os.idPeticion.idMaestroColumna =:idPeticion "); 
                /* OSINE_SFS-480 - RSIS 03 - Fin */
            }
            /* OSINE_SFS-480 - RSIS 29, 30 y 31 - Fin */
            /* OSINE_SFS-1344 - Inicio */
            if(filtro.getNombreTitularMinero()!=null && !filtro.getNombreTitularMinero().equals("")){
                jpql.append(" and upper(es.nombreTitularMinero) like :nombreTitularMinero ");
            }
            if(filtro.getRazonSocial()!=null && !filtro.getRazonSocial().equals("")){
                jpql.append(" and upper(us.nombreUnidadSupervisada) like :nombreUnidadSupervisada ");
            }
            /* OSINE_SFS-1344 - Fin */
            //order by
            jpql.append(" order by os.fechaCreacion desc");
            /*if(filtro.getIdentificadorRol()!=null && !filtro.getIdentificadorRol().equals("") && filtro.getIdentificadorRol().equals(Constantes.CONSTANTE_ROL_SUPERVISOR)){
                jpql.append(" order by os.idOrdenServicio ");
            }else{
                jpql.append(" order by p.idExpediente ");
            }*/
            
            //Crear QUERY
            queryString = jpql.toString();
            LOG.info(queryString);
            Query query = this.crud.getEm().createQuery(queryString);
            //settear filtro personal segun ROL y BANDEJA, caso especial para rol RESPONSABLE
            if( (filtro.getIdentificadorRol()!=null && filtro.getIdentificadorOpcion()!=null) && 
                ( filtro.getIdentificadorRol().equals(Constantes.IDENTIFICADOR_ROL_RESPONSABLE) && 
                  !filtro.getIdentificadorOpcion().equals(Constantes.IDENTIFICADOR_OPCION_RESP_RECEPCIONADO) ) //En bandeja RECEPCIONADO si debe filtrarle los Expedientes al RESPONSABLE
            ){
                LOG.info("Bandeja se muestra sin filtrar PERSONAL");
            }else{
            	// htorress - RSIS 1 y 2 - Inicio
                if(filtro.getIdentificadorRol()!=null && !filtro.getIdentificadorRol().equals("")){
	                  if((filtro.getIdentificadorRol().equals(Constantes.IDENTIFICADOR_ROL_GERENTE_DIVISION))){
	                	if(filtro.getIdsPersonalAsignado().size()>0){
	                		ArrayList<Long> personal = new ArrayList<Long>();
	                		for (PersonalAsignadoDTO maestro : filtro.getIdsPersonalAsignado()) {
	                			personal.add(maestro.getIdPersonalSubordinado().getIdPersonal());
	                        }
	                		personal.add(filtro.getIdPersonal());
	                		query.setParameter("idPersonal",personal);
	                	}else{
	                		query.setParameter("idPersonal",filtro.getIdPersonal());
	                	}
	                }else{
	                // htorress - RSIS 1 y 2 - Fin
	                query.setParameter("idPersonal",filtro.getIdPersonal());
	                // htorress - RSIS 1 y 2 - Inicio
	                }
                }
                // htorress - RSIS 1 y 2 - Fin
            }
            //settear parametros
            query.setParameter("identificadorEstado",filtro.getIdentificadorEstado());
            query.setParameter("idEstadoProcesoDerivado",filtro.getIdEstadoProcesoDerivado());
            ////parametros FILTROS FROM BUSQ EXPEDIENTE
            if(filtro.getNumeroExpediente()!=null && !filtro.getNumeroExpediente().equals("")){
                query.setParameter("numeroExpediente","%"+filtro.getNumeroExpediente()+"%");
            }
            if(filtro.getNumeroOrdenServicio()!=null && !filtro.getNumeroOrdenServicio().equals("")){
                query.setParameter("numeroOrdenServicio","%"+filtro.getNumeroOrdenServicio()+"%");
            }
            /* OSINE_SFS-1344 - Inicio */
            if(filtro.getRazonSocial()!=null && !filtro.getRazonSocial().equals("")){
                query.setParameter("nombreUnidadSupervisada","%"+filtro.getRazonSocial().toUpperCase()+"%");
            }
            if(filtro.getNombreTitularMinero()!=null && !filtro.getNombreTitularMinero().equals("")){
                query.setParameter("nombreTitularMinero","%"+filtro.getNombreTitularMinero().toUpperCase()+"%");
            }
            /* OSINE_SFS-1344 - Fin */
            if(filtro.getCodigoOsinergmin() !=null && !filtro.getCodigoOsinergmin().equals("")){
                query.setParameter("codigoOsinergmin","%"+filtro.getCodigoOsinergmin().toUpperCase()+"%");
            }
            if(filtro.getTipoEmpresaSupervisora() !=null){
            	if(filtro.getTipoEmpresaSupervisora().equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_NATURAL)){
            		query.setParameter("idLocador",filtro.getIdentificadorEmpresaSupervisora());
            		
            	}else if(filtro.getTipoEmpresaSupervisora().equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_JURIDICA)){
            		query.setParameter("idSupervisoraEmpresa",filtro.getIdentificadorEmpresaSupervisora());
            	}
            }
            
            /* OSINE_SFS-480 - RSIS 29, 30 y 31 - Inicio */
            if(filtro.getNumeroRegistroHidrocarburo() !=null && !filtro.getNumeroRegistroHidrocarburo().equals("")){
            	query.setParameter("numeroRegistroHidrocarburo","%"+filtro.getNumeroRegistroHidrocarburo()+"%");
            }
            if(filtro.getIdDepartamento() !=null && !filtro.getIdDepartamento().equals("")){
            	query.setParameter("idDepartamento",filtro.getIdDepartamento());
            }
            if(filtro.getIdProvincia() !=null && !filtro.getIdProvincia().equals("")){
            	query.setParameter("idProvincia",filtro.getIdProvincia());
            }
            if(filtro.getIdDistrito() !=null && !filtro.getIdDistrito().equals("")){
            	query.setParameter("idDistrito",filtro.getIdDistrito());
            }
            if(filtro.getIdPeticion() !=null && !filtro.getIdPeticion().equals("")){
            	query.setParameter("idPeticion",filtro.getIdPeticion());
            }
            /* OSINE_SFS-480 - RSIS 29, 30 y 31 - Fin */
            
            retorno= ExpedienteGSMBuilder.toListExpedienteDto(query.getResultList());  
        }catch(Exception e){
            LOG.error("error en find",e);
        }
        return retorno;
    }
    
    @Override
    public ExpedienteGSMDTO asignarUnidadSupervisada(ExpedienteGSMDTO expedienteDTO, UsuarioDTO usuarioDTO) throws ExpedienteException{
        LOG.error("error en asignarUnidadSupervisada");
        ExpedienteGSMDTO retorno=expedienteDTO;
        try{
            PghExpediente registroDAO = crud.find(expedienteDTO.getIdExpediente(), PghExpediente.class);
            registroDAO.setDatosAuditoria(usuarioDTO);
            registroDAO.setIdUnidadSupervisada(new MdiUnidadSupervisada(expedienteDTO.getUnidadSupervisada().getIdUnidadSupervisada()));
            crud.update(registroDAO);

            retorno=ExpedienteGSMBuilder.toExpedienteDto(registroDAO);
        }catch(Exception e){
            LOG.error("error en asignarUnidadSupervisada");
            ExpedienteException ex = new ExpedienteException(e.getMessage(), e);
            throw ex;
        }
        return retorno;
    }
    
    @Override
    /* OSINE791 - RSIS1 - Inicio */
    //public ExpedienteDTO asignarOrdenServicio(ExpedienteDTO expedienteDTO,String codigoTipoSupervisor,UsuarioDTO usuarioDTO) throws ExpedienteException{
    public ExpedienteGSMDTO asignarOrdenServicio(ExpedienteGSMDTO expedienteDTO,String codigoTipoSupervisor,UsuarioDTO usuarioDTO,String sigla) throws ExpedienteException{
        /* OSINE791 - RSIS1 - Fin */
        LOG.error("asignarOrdenServicio");
        ExpedienteGSMDTO retorno=expedienteDTO;
        try{
            //cambiarEstado expediente 
        	ExpedienteGSMDTO expedCambioEstado=cambiarEstadoProceso(expedienteDTO.getIdExpediente(),expedienteDTO.getPersonal().getIdPersonal(),expedienteDTO.getPersonal().getIdPersonal(),expedienteDTO.getPersonal().getIdPersonal(),expedienteDTO.getEstadoProceso().getIdEstadoProceso(),null,usuarioDTO);
            LOG.info("expedCambioEstado:"+expedCambioEstado);
            //update expediente
            PghExpediente registroDAO = crud.find(expedienteDTO.getIdExpediente(), PghExpediente.class);
            registroDAO.setDatosAuditoria(usuarioDTO);
            if(expedienteDTO.getTramite()!=null){
                registroDAO.setIdTramite(new PghTramite(expedienteDTO.getTramite().getIdTramite()));
            }else if(expedienteDTO.getProceso()!=null){
                registroDAO.setIdProceso(new PghProceso(expedienteDTO.getProceso().getIdProceso()));
            }
            if(expedienteDTO.getObligacionTipo()!=null){
                registroDAO.setIdObligacionTipo(new PghObligacionTipo(expedienteDTO.getObligacionTipo().getIdObligacionTipo()));
            }
            if(expedienteDTO.getObligacionSubTipo()!=null && expedienteDTO.getObligacionSubTipo().getIdObligacionSubTipo()!=null){
                registroDAO.setIdObligacionSubTipo(new PghObligacionSubTipo(expedienteDTO.getObligacionSubTipo().getIdObligacionSubTipo()));
            }
            registroDAO.setIdUnidadSupervisada(new MdiUnidadSupervisada(expedienteDTO.getUnidadSupervisada().getIdUnidadSupervisada()));
            crud.update(registroDAO);
            //inserta ordenServicio
            Long idLocador=codigoTipoSupervisor.equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_NATURAL)?expedienteDTO.getOrdenServicio().getLocador().getIdLocador():null;
            Long idSupervisoraEmpresa=codigoTipoSupervisor.equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_JURIDICA)?expedienteDTO.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa():null;
            OrdenServicioDTO OrdenServicioDTO=ordenServicioDAO.registrar(expedienteDTO.getIdExpediente(), 
                    expedienteDTO.getOrdenServicio().getIdTipoAsignacion(), expedienteDTO.getOrdenServicio().getEstadoProceso().getIdEstadoProceso(), 
                    /* OSINE791 - RSIS1 - Inicio */
                    //codigoTipoSupervisor, idLocador, idSupervisoraEmpresa, usuarioDTO);
                    codigoTipoSupervisor, idLocador, idSupervisoraEmpresa, usuarioDTO,sigla);
                    /* OSINE791 - RSIS1 - Fin */
            LOG.info("OrdenServicioDTO:"+OrdenServicioDTO.getNumeroOrdenServicio());
            //busca idPersonalDest
            PersonalDTO personalDest=null;
            List<PersonalDTO> listPersonalDest=null;
            if(codigoTipoSupervisor.equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_NATURAL)){
                listPersonalDest=personalDAO.find(new PersonalFilter(expedienteDTO.getOrdenServicio().getLocador().getIdLocador(),null));
            }else if(codigoTipoSupervisor.equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_JURIDICA)){
                listPersonalDest=personalDAO.find(new PersonalFilter(null,expedienteDTO.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa()));
            }
            if(listPersonalDest==null || listPersonalDest.isEmpty() || listPersonalDest.get(0).getIdPersonal()==null){
                throw new ExpedienteException("La Empresa Supervisora no tiene un Personal asignado",null);
            }else{
                personalDest=listPersonalDest.get(0);
            }
            //inserta historico Orden Servicio
            EstadoProcesoDTO estadoProcesoDto=estadoProcesoDAO.find(new EstadoProcesoFilter(Constantes.IDENTIFICADOR_ESTADO_PROCESO_OS_REGISTRO)).get(0);
            MaestroColumnaDTO tipoHistorico=maestroColumnaDAO.findMaestroColumna(Constantes.DOMINIO_TIPO_COMPONENTE, Constantes.APLICACION_INPS, Constantes.CODIGO_TIPO_COMPONENTE_ORDEN_SERVICIO).get(0);
            /* OSINE_SFS-480 - RSIS 40 - Inicio */
            HistoricoEstadoDTO historicoEstado=historicoEstadoDAO.registrar(null, OrdenServicioDTO.getIdOrdenServicio(), estadoProcesoDto.getIdEstadoProceso(), expedienteDTO.getPersonal().getIdPersonal(), personalDest.getIdPersonal(), tipoHistorico.getIdMaestroColumna(),null,null, null, usuarioDTO); 
            LOG.info("historicoEstado:"+historicoEstado);
            /* OSINE_SFS-480 - RSIS 40 - Fin */
            retorno=ExpedienteGSMBuilder.toExpedienteDto(registroDAO);
            retorno.setOrdenServicio(new OrdenServicioDTO(OrdenServicioDTO.getIdOrdenServicio(),OrdenServicioDTO.getNumeroOrdenServicio()));
        }catch(Exception e){
            LOG.error("error en asignarOrdenServicio",e);
            ExpedienteException ex = new ExpedienteException(e.getMessage(), e);
            throw ex;
        }
        return retorno;
    }
    
    @Override
    /* OSINE791 - RSIS1 - Inicio */
    //public ExpedienteDTO generarExpedienteOrdenServicio(ExpedienteDTO expedienteDTO,String codigoTipoSupervisor,UsuarioDTO usuarioDTO) throws ExpedienteException{
    public ExpedienteGSMDTO generarExpedienteOrdenServicio(ExpedienteGSMDTO expedienteDTO,String codigoTipoSupervisor,UsuarioDTO usuarioDTO,String sigla) throws ExpedienteException{
        /* OSINE791 - RSIS1 - Fin */
        LOG.error("generarExpedienteOrdenServicio");
        ExpedienteGSMDTO retorno= new ExpedienteGSMDTO();
        try{
            expedienteDTO.setEstado(Constantes.ESTADO_ACTIVO);
            PghExpediente pghExpediente = ExpedienteGSMBuilder.toExpedienteDomain(expedienteDTO);
            pghExpediente.setFechaEstadoProceso(new Date());
            pghExpediente.setDatosAuditoria(usuarioDTO);
            crud.create(pghExpediente);
            retorno=ExpedienteGSMBuilder.toExpedienteDto(pghExpediente);
            
            //reg historicoEstado
            MaestroColumnaDTO tipoHistorico=maestroColumnaDAO.findMaestroColumna(Constantes.DOMINIO_TIPO_COMPONENTE, Constantes.APLICACION_INPS, Constantes.CODIGO_TIPO_COMPONENTE_EXPEDIENTE).get(0);
            /* OSINE_SFS-480 - RSIS 40 - Inicio */
            HistoricoEstadoDTO historicoEstado=historicoEstadoDAO.registrar(pghExpediente.getIdExpediente(), null, pghExpediente.getIdEstadoProceso().getIdEstadoProceso(), pghExpediente.getIdPersonal().getIdPersonal(), pghExpediente.getIdPersonal().getIdPersonal(), tipoHistorico.getIdMaestroColumna(),null,null, null, usuarioDTO);
            LOG.info("historicoEstado-->"+historicoEstado);
            /* OSINE_SFS-480 - RSIS 40 - Fin */
            //inserta ordenServicio
            Long idLocador=codigoTipoSupervisor.equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_NATURAL)?expedienteDTO.getOrdenServicio().getLocador().getIdLocador():null;
            Long idSupervisoraEmpresa=codigoTipoSupervisor.equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_JURIDICA)?expedienteDTO.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa():null;
            OrdenServicioDTO OrdenServicioDTO=ordenServicioDAO.registrar(retorno.getIdExpediente(), 
                    expedienteDTO.getOrdenServicio().getIdTipoAsignacion(), expedienteDTO.getOrdenServicio().getEstadoProceso().getIdEstadoProceso(), 
                    /* OSINE791 - RSIS1 - Inicio */
                    //codigoTipoSupervisor, idLocador, idSupervisoraEmpresa, usuarioDTO);
                    codigoTipoSupervisor, idLocador, idSupervisoraEmpresa, usuarioDTO,sigla);
                    /* OSINE791 - RSIS1 - Fin */
            LOG.info("OrdenServicioDTO:"+OrdenServicioDTO.getNumeroOrdenServicio());
            //busca idPersonalDest
            PersonalDTO personalDest=null;
            List<PersonalDTO> listPersonalDest=null;
            if(codigoTipoSupervisor.equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_NATURAL)){
                //personalDest=personalDAO.find(new PersonalFilter(expedienteDTO.getOrdenServicio().getLocador().getIdLocador(),null)).get(0);
                listPersonalDest=personalDAO.find(new PersonalFilter(expedienteDTO.getOrdenServicio().getLocador().getIdLocador(),null));
            }else if(codigoTipoSupervisor.equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_JURIDICA)){
                //personalDest=personalDAO.find(new PersonalFilter(null,expedienteDTO.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa())).get(0);
                listPersonalDest=personalDAO.find(new PersonalFilter(null,expedienteDTO.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa()));
            }
            if(listPersonalDest==null || listPersonalDest.isEmpty()){
                throw new ExpedienteException("La Empresa Supervisora no tiene un Personal asignado",null);
            }else{
                personalDest=listPersonalDest.get(0);
            }
            //inserta historico Orden Servicio
            EstadoProcesoDTO estadoProcesoDto=estadoProcesoDAO.find(new EstadoProcesoFilter(Constantes.IDENTIFICADOR_ESTADO_PROCESO_OS_REGISTRO)).get(0);
            MaestroColumnaDTO tipoHistoricoOS=maestroColumnaDAO.findMaestroColumna(Constantes.DOMINIO_TIPO_COMPONENTE, Constantes.APLICACION_INPS, Constantes.CODIGO_TIPO_COMPONENTE_ORDEN_SERVICIO).get(0);
            /* OSINE_SFS-480 - RSIS 40 - Inicio */
            HistoricoEstadoDTO historicoEstadoOS=historicoEstadoDAO.registrar(null, OrdenServicioDTO.getIdOrdenServicio(), estadoProcesoDto.getIdEstadoProceso(), expedienteDTO.getPersonal().getIdPersonal(), personalDest.getIdPersonal(), tipoHistoricoOS.getIdMaestroColumna(),null,null, null, usuarioDTO); 
            LOG.info("historicoEstadoOS:"+historicoEstadoOS);
            /* OSINE_SFS-480 - RSIS 40 - Fin */
            retorno.setOrdenServicio(new OrdenServicioDTO(OrdenServicioDTO.getIdOrdenServicio(),OrdenServicioDTO.getNumeroOrdenServicio()));
        }catch(Exception e){
            LOG.error("error en generarExpedienteOrdenServicio",e);
            throw new ExpedienteException(e.getMessage(), e);
        }
        return retorno;
    }
    
    @Override
    public List<ExpedienteGSMDTO> findExpedienteOperativo(Long idExpediente) throws ExpedienteException{
        LOG.info("findExpedienteOperativo");
        List<ExpedienteGSMDTO> retorno=null;
        try{
            String queryString;
            StringBuilder jpql = new StringBuilder();
            //arma campos
            jpql.append("SELECT new PghExpediente( p.idExpediente) FROM PghExpediente p ");
            jpql.append("where p.idProceso.idProceso = (select pro.idProceso from PghProceso pro where pro.descripcion = 'OPERATIVO')");

            if(idExpediente!=null){
                jpql.append(" and p.idExpediente.idExpediente=:idExpediente  ");
            }

            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            //settear parametros
            if(idExpediente!=null){
                query.setParameter("idExpediente",idExpediente);
            }
            retorno= ExpedienteGSMBuilder.toListExpedienteDto(query.getResultList());
        }catch(Exception e){
            LOG.error("error en find",e);
        }
        return retorno;

    }
    /* OSINE_SFS-480 - RSIS 47 - Inicio */
    @Override
    public ExpedienteGSMDTO editarExpedienteOrdenServicio(ExpedienteGSMDTO expedienteDTO, String codigoTipoSupervisor, PersonalDTO personalDest, UsuarioDTO usuarioDTO) throws ExpedienteException {
        LOG.error("editarExpediente");
        ExpedienteGSMDTO retorno= new ExpedienteGSMDTO();
        try {
            PghExpediente registro = crud.find(expedienteDTO.getIdExpediente(), PghExpediente.class);
            registro.setIdFlujoSiged(new PghFlujoSiged(expedienteDTO.getFlujoSiged().getIdFlujoSiged()));
            registro.setIdProceso(new PghProceso(expedienteDTO.getProceso().getIdProceso()));
            registro.setIdObligacionTipo(new PghObligacionTipo(expedienteDTO.getObligacionTipo().getIdObligacionTipo()));
            registro.setAsuntoSiged(expedienteDTO.getAsuntoSiged());
            registro.setIdUnidadSupervisada(new MdiUnidadSupervisada(expedienteDTO.getUnidadSupervisada().getIdUnidadSupervisada()));
            registro.setIdEmpresaSupervisada(new MdiEmpresaSupervisada(expedienteDTO.getEmpresaSupervisada().getIdEmpresaSupervisada()));
            registro.setDatosAuditoria(usuarioDTO);
            if(expedienteDTO.getObligacionSubTipo()!=null && expedienteDTO.getObligacionSubTipo().getIdObligacionSubTipo()!=null){
                registro.setIdObligacionSubTipo(new PghObligacionSubTipo(expedienteDTO.getObligacionSubTipo().getIdObligacionSubTipo()));
            }else{
                registro.setIdObligacionSubTipo(null);
            }
            crud.update(registro);
            retorno = ExpedienteGSMBuilder.toExpedienteDto(registro);    
            
            Long idLocador=codigoTipoSupervisor.equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_NATURAL)?expedienteDTO.getOrdenServicio().getLocador().getIdLocador():null;
            Long idSupervisoraEmpresa=codigoTipoSupervisor.equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_JURIDICA)?expedienteDTO.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa():null;
            
            String flagConfirmaTipoAsignacion=(!StringUtil.isEmpty(expedienteDTO.getFlagEvaluaTipoAsignacion())?Constantes.ESTADO_ACTIVO:null);
            OrdenServicioDTO OrdenServicioDTO=ordenServicioDAO.editarExpedienteOrdenServicio(expedienteDTO.getOrdenServicio().getIdOrdenServicio(), expedienteDTO.getOrdenServicio().getIdTipoAsignacion(), codigoTipoSupervisor, idLocador, idSupervisoraEmpresa, usuarioDTO,flagConfirmaTipoAsignacion);
                                   
            EstadoProcesoDTO estadoProcesoDto=estadoProcesoDAO.find(new EstadoProcesoFilter(Constantes.IDENTIFICADOR_ESTADO_PROCESO_OS_REGISTRO)).get(0);
            MaestroColumnaDTO tipoHistoricoOS=maestroColumnaDAO.findMaestroColumna(Constantes.DOMINIO_TIPO_COMPONENTE, Constantes.APLICACION_INPS, Constantes.CODIGO_TIPO_COMPONENTE_ORDEN_SERVICIO).get(0);
            HistoricoEstadoDTO historicoEstadoOS=historicoEstadoDAO.registrar(null, OrdenServicioDTO.getIdOrdenServicio(), estadoProcesoDto.getIdEstadoProceso(), expedienteDTO.getPersonal().getIdPersonal(), personalDest.getIdPersonal(), tipoHistoricoOS.getIdMaestroColumna(),null,null, null, usuarioDTO); 
            LOG.info("historicoEstadoOS:"+historicoEstadoOS);
            
            retorno.setOrdenServicio(new OrdenServicioDTO(OrdenServicioDTO.getIdOrdenServicio(),OrdenServicioDTO.getNumeroOrdenServicio()));
                                
        }catch(Exception e){
            LOG.error("error en editarExpediente",e);
            throw new ExpedienteException(e.getMessage(), e);
        }
        return retorno;
    }
    /* OSINE_SFS-480 - RSIS 47 - Fin */   
    /* OSINE791 - RSIS 20 - Inicio */
    @Override
    public ExpedienteGSMDTO veriActuFlgTramFinalizadoDsr(Long idExpediente,Long idOrdenServicio,Long idResultadoSupervision,UsuarioDTO usuarioDTO) throws ExpedienteException{
        LOG.info("veriActuFlgTramFinalizadoDsr");
        ExpedienteGSMDTO retorno=null;
        try{
            PghResultadoSupervision resSup = crud.find(idResultadoSupervision, PghResultadoSupervision.class);
            
            PghExpediente expe = crud.find(idExpediente, PghExpediente.class);
            if(resSup.getCodigo().equals(Constantes.CODIGO_RESULTADO_SUPERVISION_CUMPLE) || resSup.getCodigo().equals(Constantes.CODIGO_RESULTADO_SUPERVISION_CUMPLE_OBSTACULIZADO)){
                expe.setFlagTramiteFinalizado(Constantes.ESTADO_ACTIVO);
            }else if(resSup.getCodigo().equals(Constantes.CODIGO_RESULTADO_SUPERVISION_NOCUMPLE) || resSup.getCodigo().equals(Constantes.CODIGO_RESULTADO_SUPERVISION_NOCUMPLE_OBSTACULIZADO)){
                expe.setFlagTramiteFinalizado(Constantes.ESTADO_INACTIVO);
            }else{
                throw new ExpedienteException("Casuistica no encontrada para Finalizar la Orden de Servicio. Revisar Supervision.",null);
            }
            expe.setDatosAuditoria(usuarioDTO);
            crud.update(expe);
            retorno=ExpedienteGSMBuilder.toExpedienteDto(expe);
        }catch(Exception e){
            LOG.error("error en veriActuFlgTramFinalizadoDsr",e);
            ExpedienteException ex = new ExpedienteException(e.getMessage(), e);
            throw ex;
        }
        return retorno;
    }
    /* OSINE791 - RSIS 20 - Fin */
    /* OSINE791 - RSIS 47 - Inicio */
    @Override
	public ExpedienteGSMDTO actualizar(ExpedienteGSMDTO expediente, UsuarioDTO usuario) throws ExpedienteException {
    	LOG.info("inicio actualizar");
		try{
			PghExpediente pghExpediente=ExpedienteGSMBuilder.toExpedienteDomain(expediente);
			pghExpediente.setDatosAuditoria(usuario);
			pghExpediente=crud.update(pghExpediente);
			expediente=ExpedienteGSMBuilder.toExpedienteDto(pghExpediente);
		} catch(Exception e){
			LOG.error("error actualizar", e);
			throw new ExpedienteException(e);
		}
		return expediente;
	}
    /* OSINE791 - RSIS 47 - Fin */
    /* OSINE791 - RSIS 41 - Inicio */
    @Override
    @Transactional(rollbackFor=ExpedienteException.class) 
    public ExpedienteGSMDTO actualizarExpediente(ExpedienteGSMDTO expedienteDTO, UsuarioDTO usuarioDTO) throws ExpedienteException {
      LOG.info("actualizarExpediente");
      ExpedienteGSMDTO retorno=null;
        try{
            PghExpediente expe = crud.find(expedienteDTO.getIdExpediente(), PghExpediente.class);
            expe.setIdEstadoLevantamiento(new MdiMaestroColumna(expedienteDTO.getEstadoLevantamiento().getIdMaestroColumna()));
            expe.setDatosAuditoria(usuarioDTO);
            crud.update(expe);
            retorno=ExpedienteGSMBuilder.toExpedienteDto(expe);
        }catch(Exception e){
            LOG.error("error en actualizarExpediente",e);
            ExpedienteException ex = new ExpedienteException(e.getMessage(), e);
            throw ex;
        }
        return retorno; 
    }
    /* OSINE791 - RSIS 41 - Fin */
}
