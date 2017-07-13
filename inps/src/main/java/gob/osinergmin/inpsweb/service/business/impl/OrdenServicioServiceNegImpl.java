/**
* Resumen		
* Objeto		: OrdenServicioServiceNegImpl.java
* Descripción		: Clase de la capa de negocio que contiene la implementación de los métodos declarados en la clase interfaz OrdenServicioServiceNeg
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Giancarlo Villanueva Andrade
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     05/05/2016      Mario Dioses Fernandez          Registrar en BD campo FECHA_INICIO_PLAZO y FECHA_FIN_PLAZO luego de CONCLUIR_OS, considerando Plazo_Descargo por Rubro (MDI_ACTIVIDAD)
* OSINE_SFS-480     23/05/2016      Luis García Reyna               Implementar la funcionalidad de devolución de asignaciones de acuerdo a especificaciones
* OSINE_SFS-480     23/05/2016      Hernán Torres Saénz             Implementar la funcionalidad de anular orden de servicio de acuerdo a especificaciones
* OSINE791-RSIS21   29/08/2016      Alexander Vilca Narvaez     	Implementar la funcionalidad de archivar para el flujo DSR
* OSINE_SFS-791     10/10/2016      Mario Dioses Fernandez          Crear la funcionalidad "verificar levantamientos" que permita verificar el tipo de asignación para la orden de levantamiento DSR-CRITICIDAD.
* OSINE791-RSIS42   18/10/2016      Alexander Vilca Narvaez       	Adecuar la funcionalidad "CERRAR ORDEN" cuando se atiende una orden de levantamiento DSR-CRITICIDAD
* OSINE_MAN_DSR_0024|  27/06/2017   |   Carlos Quijano Chavez::ADAPTER      |    Corregir , cuando un supervisor subasana   una orden de servicio observada, INPS manda error                         
*/

package gob.osinergmin.inpsweb.service.business.impl;
import gob.osinergmin.inpsweb.service.business.CorreoServiceNeg;
import gob.osinergmin.inpsweb.service.business.DireccionUnidadSupervisadaServiceNeg;
import gob.osinergmin.inpsweb.service.business.DocumentoAdjuntoServiceNeg;
import gob.osinergmin.inpsweb.service.business.EmpresaSupervisadaServiceNeg;
import gob.osinergmin.inpsweb.service.business.ExpedienteServiceNeg;
import gob.osinergmin.inpsweb.service.business.MaestroColumnaServiceNeg;
import gob.osinergmin.inpsweb.service.business.OrdenServicioServiceNeg;
import gob.osinergmin.inpsweb.service.business.SupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.business.UnidadSupervisadaServiceNeg;
import gob.osinergmin.inpsweb.service.dao.ActividadDAO;
import gob.osinergmin.inpsweb.service.dao.DestinatarioCorreoDAO;
import gob.osinergmin.inpsweb.service.dao.DetalleSupervisionDAO;
import gob.osinergmin.inpsweb.service.dao.EstadoProcesoDAO;
import gob.osinergmin.inpsweb.service.dao.ExpedienteDAO;
import gob.osinergmin.inpsweb.service.dao.MaestroColumnaDAO;
import gob.osinergmin.inpsweb.service.dao.OrdenServicioDAO;
import gob.osinergmin.inpsweb.service.dao.PersonalDAO;
import gob.osinergmin.inpsweb.service.dao.PghDocumentoAdjuntoDAO;
import gob.osinergmin.inpsweb.service.dao.SupervisionDAO;
import gob.osinergmin.inpsweb.service.exception.OrdenServicioException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.StringUtil;
import gob.osinergmin.inpsweb.util.Utiles;
import gob.osinergmin.mdicommon.domain.dto.ActividadDTO;
import gob.osinergmin.mdicommon.domain.dto.DestinatarioCorreoDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.DireccionUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresaSupDTO;
import gob.osinergmin.mdicommon.domain.dto.EstadoProcesoDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.OrdenServicioDTO;
import gob.osinergmin.mdicommon.domain.dto.PersonalDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.UbigeoDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.dto.busqueda.BusquedaDireccionxEmpSupervisada;
import gob.osinergmin.mdicommon.domain.ui.ActividadFilter;
import gob.osinergmin.mdicommon.domain.ui.DestinatarioCorreoFilter;
import gob.osinergmin.mdicommon.domain.ui.DetalleSupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.DireccionUnidadSupervisadaFilter;
import gob.osinergmin.mdicommon.domain.ui.DocumentoAdjuntoFilter;
import gob.osinergmin.mdicommon.domain.ui.EstadoProcesoFilter;
import gob.osinergmin.mdicommon.domain.ui.ExpedienteFilter;
import gob.osinergmin.mdicommon.domain.ui.OrdenServicioFilter;
import gob.osinergmin.mdicommon.domain.ui.PersonalFilter;
import gob.osinergmin.mdicommon.domain.ui.SupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.UnidadSupervisadaFilter;
import gob.osinergmin.siged.remote.enums.InvocationResult;
import gob.osinergmin.siged.remote.rest.ro.in.ClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DireccionxClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DocumentoInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.ClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.DireccionxClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoOutRO;
import gob.osinergmin.siged.rest.util.ExpedienteInvoker;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author jpiro
 */
@Service("ordenServicioServiceNeg")
public class OrdenServicioServiceNegImpl implements OrdenServicioServiceNeg{
    private static final Logger LOG = LoggerFactory.getLogger(OrdenServicioServiceNegImpl.class);    
    
    @Value("${alfresco.host}")
    private String HOST;
    
    @Inject
    private EstadoProcesoDAO estadoProcesoDAO;
    @Inject
    private OrdenServicioDAO ordenServicioDAO;
    @Inject
    private ExpedienteDAO expedienteDAO;
    @Inject
    private PersonalDAO personalDAO;
    @Inject
    private SupervisionDAO supervisionDAO;
    @Inject
    private SupervisionServiceNeg supervisionServiceNeg;
    @Inject
    private DocumentoAdjuntoServiceNeg documentoAdjuntoServiceNeg;
   
    /* OSINE_SFS-480 - RSIS 17 - Inicio */
    @Inject 
    private ActividadDAO actividadDAO;  
    @Inject  
    private DireccionUnidadSupervisadaServiceNeg direccionUnidadSupervisadaNeg;
    @Inject
    private UnidadSupervisadaServiceNeg unidadSupervisadaServiceNeg;  
    @Inject
    private MaestroColumnaServiceNeg maestroColumnaServiceNeg; 
    /* OSINE_SFS-480 - RSIS 17 - Fin */
    /* OSINE_SFS-480 - RSIS 40 - Inicio */
    @Inject
    private ExpedienteServiceNeg expedienteServiceNeg; 
    /* OSINE_SFS-480 - RSIS 40 - Fin */
    @Inject
    private DetalleSupervisionDAO detalleSupervisionDAO; 
    @Inject 
    private EmpresaSupervisadaServiceNeg empresaSupervisadaServiceNeg;
    @Inject 
    private MaestroColumnaDAO maestroColumnaDAO;
    @Inject
    private PghDocumentoAdjuntoDAO pghDocumentoAdjuntoDAO;
    /* OSINE_SFS-791 - RSIS 33 - Inicio */
    @Inject
    private DestinatarioCorreoDAO destinatarioCorreoDAO;
    @Inject
    private CorreoServiceNeg correoServiceNeg;
    /* OSINE_SFS-791 - RSIS 33 - Fin */
    @Override
    @Transactional
    public OrdenServicioDTO atender(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,String flagSupervision,UsuarioDTO usuarioDTO) throws OrdenServicioException{
        LOG.info("atender");
        OrdenServicioDTO retorno=new OrdenServicioDTO();
        
        try{
            //validar flagCumplimiento segun flagSupervision
            if(flagSupervision.equals(Constantes.ESTADO_ACTIVO)){
                OrdenServicioDTO cumplimiento=ordenServicioDAO.veriActuFlgCumplimiento(idOrdenServicio,usuarioDTO);
                LOG.info("OrdenServicio Cumplimiento-->"+cumplimiento.getFlagCumplimiento());
            }           
            
            EstadoProcesoDTO estadoProcesoDto=estadoProcesoDAO.find(new EstadoProcesoFilter(Constantes.IDENTIFICADOR_ESTADO_PROCESO_OS_SUPERVISADO)).get(0);
            ExpedienteDTO expedientePadre=expedienteDAO.findByFilter(new ExpedienteFilter(idExpediente)).get(0);
            if(expedientePadre==null || expedientePadre.getPersonal()==null || expedientePadre.getPersonal().getIdPersonal()==null){
                throw new OrdenServicioException("La Orden de Servicio no tiene Expediente relacionado",null);
            }
            /* OSINE_SFS-480 - RSIS 40 - Inicio */
            retorno=ordenServicioDAO.cambiarEstadoProceso(idOrdenServicio,idPersonalOri,expedientePadre.getPersonal().getIdPersonal(),estadoProcesoDto.getIdEstadoProceso(),null,usuarioDTO,null,null);
            /* OSINE_SFS-480 - RSIS 40 - Fin */
        }catch(Exception e){
            LOG.error("Error atender",e);
            throw new OrdenServicioException(e.getMessage(),e);
        }
        return retorno;
    }
    
    @Override
    @Transactional
    // htorress - RSIS 18 - Inicio
    //public OrdenServicioDTO revisar(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,UsuarioDTO usuarioDTO) throws OrdenServicioException{
    public OrdenServicioDTO revisar(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,Long idPersonalDest,UsuarioDTO usuarioDTO) throws OrdenServicioException{	
    // htorress - RSIS 18 - Fin
        LOG.info("revisar");
        OrdenServicioDTO retorno=new OrdenServicioDTO();
        
        try{
            EstadoProcesoDTO estadoProcesoDto=estadoProcesoDAO.find(new EstadoProcesoFilter(Constantes.IDENTIFICADOR_ESTADO_PROCESO_OS_REVISADO)).get(0);
            ExpedienteDTO expedientePadre=expedienteDAO.findByFilter(new ExpedienteFilter(idExpediente)).get(0);
            if(expedientePadre==null || expedientePadre.getPersonal()==null || expedientePadre.getPersonal().getIdPersonal()==null){
                throw new OrdenServicioException("La Orden de Servicio no tiene Expediente relacionado",null);
            }
            // htorress - RSIS 18 - Inicio        	
            //retorno=ordenServicioDAO.cambiarEstadoProceso(idOrdenServicio,idPersonalOri,expedientePadre.getPersonal().getIdPersonal(),estadoProcesoDto.getIdEstadoProceso(),null,usuarioDTO);
            /* OSINE_SFS-480 - RSIS 40 - Inicio */
            retorno=ordenServicioDAO.cambiarEstadoProceso(idOrdenServicio,idPersonalOri,idPersonalDest,estadoProcesoDto.getIdEstadoProceso(),null,usuarioDTO,null,null);
            /* OSINE_SFS-480 - RSIS 40 - Fin */
            // htorress - RSIS 18 - Fin
            
            //Invocar a Servicio SIGED - Aprobar
           // expedienteServiceNeg.aprobarExpedienteSIGED(expedientePadre, idPersonalOri, "contenido", false);
            
        }catch(Exception e){
            LOG.error("Error revisar",e);
            throw new OrdenServicioException(e.getMessage(),e);
        }
        return retorno;
    }
    
    @Override
    @Transactional
    public OrdenServicioDTO aprobar(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,UsuarioDTO usuarioDTO) throws OrdenServicioException{
        LOG.info("aprobar");
        OrdenServicioDTO retorno=new OrdenServicioDTO();
        
        try{
            EstadoProcesoDTO estadoProcesoDto=estadoProcesoDAO.find(new EstadoProcesoFilter(Constantes.IDENTIFICADOR_ESTADO_PROCESO_OS_APROBADO)).get(0);
            ExpedienteDTO expedientePadre=expedienteDAO.findByFilter(new ExpedienteFilter(idExpediente)).get(0);
            if(expedientePadre==null || expedientePadre.getPersonal()==null || expedientePadre.getPersonal().getIdPersonal()==null){
                throw new OrdenServicioException("La Orden de Servicio no tiene Expediente relacionado",null);
            }
            /* OSINE_SFS-480 - RSIS 40 - Inicio */
            retorno=ordenServicioDAO.cambiarEstadoProceso(idOrdenServicio,idPersonalOri,expedientePadre.getPersonal().getIdPersonal(),estadoProcesoDto.getIdEstadoProceso(),null,usuarioDTO,null,null);
            /* OSINE_SFS-480 - RSIS 40 - Inicio */
        }catch(Exception e){
            LOG.error("Error aprobar",e);
            throw new OrdenServicioException(e.getMessage(),e);
        }
        return retorno;
    }
    
    @Override
    @Transactional
    public OrdenServicioDTO aprobarCriticidad(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,UsuarioDTO usuarioDTO) throws OrdenServicioException{
        LOG.info("aprobarCriticidad");
        OrdenServicioDTO retorno=new OrdenServicioDTO();        
        try{
            EstadoProcesoDTO estadoProcesoDto=estadoProcesoDAO.find(new EstadoProcesoFilter(Constantes.IDENTIFICADOR_ESTADO_PROCESO_OS_APROBADO)).get(0);
            ExpedienteDTO expedientePadre=expedienteDAO.findByFilter(new ExpedienteFilter(idExpediente)).get(0);
            if(expedientePadre==null || expedientePadre.getPersonal()==null || expedientePadre.getPersonal().getIdPersonal()==null){
                throw new OrdenServicioException("La Orden de Servicio no tiene Expediente relacionado",null);
            }
            retorno=ordenServicioDAO.cambiarEstadoProceso(idOrdenServicio,idPersonalOri,expedientePadre.getPersonal().getIdPersonal(),estadoProcesoDto.getIdEstadoProceso(),null,usuarioDTO,null,null);
            // concluir
            EstadoProcesoDTO estadoProcesoDtoConc=estadoProcesoDAO.find(new EstadoProcesoFilter(Constantes.IDENTIFICADOR_ESTADO_PROCESO_OS_CONCLUIDO)).get(0);
            retorno=ordenServicioDAO.cambiarEstadoProceso(idOrdenServicio,idPersonalOri,expedientePadre.getPersonal().getIdPersonal(),estadoProcesoDtoConc.getIdEstadoProceso(),null,usuarioDTO,null,null);            
        }catch(Exception e){
            LOG.error("Error aprobarCriticidad",e);
            throw new OrdenServicioException(e.getMessage(),e);
        }
        return retorno;
    }
    
    @Override
    @Transactional
    public OrdenServicioDTO notificar(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,UsuarioDTO usuarioDTO) throws OrdenServicioException{
        LOG.info("notificar");
        OrdenServicioDTO retorno=new OrdenServicioDTO();
        
        try{
            EstadoProcesoDTO estadoProcesoDto=estadoProcesoDAO.find(new EstadoProcesoFilter(Constantes.IDENTIFICADOR_ESTADO_PROCESO_OS_OFICIADO)).get(0);
            ExpedienteDTO expedientePadre=expedienteDAO.findByFilter(new ExpedienteFilter(idExpediente)).get(0);
            if(expedientePadre==null || expedientePadre.getPersonal()==null || expedientePadre.getPersonal().getIdPersonal()==null){
                throw new OrdenServicioException("La Orden de Servicio no tiene Expediente relacionado",null);
            }
            /* OSINE_SFS-480 - RSIS 40 - Inicio */
            retorno=ordenServicioDAO.cambiarEstadoProceso(idOrdenServicio,idPersonalOri,expedientePadre.getPersonal().getIdPersonal(),estadoProcesoDto.getIdEstadoProceso(),null,usuarioDTO,null,null);
            /* OSINE_SFS-480 - RSIS 40 - Fin */
        }catch(Exception e){
            LOG.error("Error notificar",e);
            throw new OrdenServicioException(e.getMessage(),e);
        }
        return retorno;
    }
    
    @Override
    @Transactional
    /* OSINE_SFS-480 - RSIS 17 - Inicio */
    public OrdenServicioDTO concluir(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,UsuarioDTO usuarioDTO,Long idArchivo,String fechaInicioPlazoDescargo) throws OrdenServicioException{ 
    /* OSINE_SFS-480 - RSIS 17 - Fin */
        LOG.info("concluir");
        OrdenServicioDTO retorno=new OrdenServicioDTO();
        
        try{
            //validar flagTramiteFinalizado segun flagCumplimiento        	
            ExpedienteDTO finalizado=expedienteDAO.veriActuFlgTramFinalizado(idExpediente,idOrdenServicio,usuarioDTO);
            LOG.info("Expediente Finalizado-->"+finalizado.getFlagTramiteFinalizado());
            SupervisionFilter supervisionFiltro=new SupervisionFilter();
            supervisionFiltro.setIdOrdenServicio(idOrdenServicio);
            List<SupervisionDTO> supervisionDTO= supervisionDAO.find(supervisionFiltro);
            if(!supervisionDTO.isEmpty()){
            	supervisionServiceNeg.eliminarDetalleSupervision(idOrdenServicio, usuarioDTO,idExpediente);
            }
            EstadoProcesoDTO estadoProcesoDto=estadoProcesoDAO.find(new EstadoProcesoFilter(Constantes.IDENTIFICADOR_ESTADO_PROCESO_OS_CONCLUIDO)).get(0);
            ExpedienteDTO expedientePadre=expedienteDAO.findByFilter(new ExpedienteFilter(idExpediente)).get(0);
            if(expedientePadre==null || expedientePadre.getPersonal()==null || expedientePadre.getPersonal().getIdPersonal()==null){
                throw new OrdenServicioException("La Orden de Servicio no tiene Expediente relacionado",null);
            }          
            /* OSINE791 - RSIS 21 - Inicio */
            if(idArchivo!=null && fechaInicioPlazoDescargo!=null){
                actualizarFechaPlazo(idExpediente, idOrdenServicio, usuarioDTO, idArchivo, fechaInicioPlazoDescargo); 
            }
            /* OSINE791 - RSIS 21 - Fin */
            /* OSINE_SFS-480 - RSIS 40 - Inicio */
            retorno=ordenServicioDAO.cambiarEstadoProceso(idOrdenServicio,idPersonalOri,expedientePadre.getPersonal().getIdPersonal(),estadoProcesoDto.getIdEstadoProceso(),null,usuarioDTO,null,null);
            /* OSINE_SFS-480 - RSIS 40 - Fin */
        }catch(Exception e){
            LOG.error("Error concluir",e);
            throw new OrdenServicioException(e.getMessage(),e);
        }
        return retorno;
    }
    
    @Override
    @Transactional
    public OrdenServicioDTO observar(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,String motivoReasignacion,UsuarioDTO usuarioDTO) throws OrdenServicioException{
        LOG.info("observar");
        OrdenServicioDTO retorno=new OrdenServicioDTO();
        
        try{
            EstadoProcesoDTO estadoProcesoDto=estadoProcesoDAO.find(new EstadoProcesoFilter(Constantes.IDENTIFICADOR_ESTADO_PROCESO_OS_REGISTRO)).get(0);
            //buscar personal de la Orden Servicio
            OrdenServicioDTO ordenServicio=ordenServicioDAO.findByFilter(new OrdenServicioFilter(idOrdenServicio)).get(0);
            PersonalDTO personaOS=null;
            if(ordenServicio.getLocador()!=null && ordenServicio.getLocador().getIdLocador()!=null){
                LOG.info("ordenServicio.getLocador().getIdLocador()-->"+ordenServicio.getLocador().getIdLocador());
                List<PersonalDTO> listPersonal=personalDAO.find(new PersonalFilter(ordenServicio.getLocador().getIdLocador(), null));
                if(listPersonal!=null && listPersonal.size()>0){
                    personaOS=listPersonal.get(0);
                }else{
                    throw new OrdenServicioException("La Empresa Supervisora de la Orden de Servicio no tiene Persona relacionada",null);
                }
            }else if(ordenServicio.getSupervisoraEmpresa()!=null && ordenServicio.getSupervisoraEmpresa().getIdSupervisoraEmpresa()!=null){
                LOG.info("ordenServicio.getSupervisoraEmpresa().getIdSupervisoraEmpresa()-->"+ordenServicio.getSupervisoraEmpresa().getIdSupervisoraEmpresa());
                List<PersonalDTO> listPersonal=personalDAO.find(new PersonalFilter(null,ordenServicio.getSupervisoraEmpresa().getIdSupervisoraEmpresa()));
                if(listPersonal!=null && listPersonal.size()>0){
                    personaOS=listPersonal.get(0);
                }else{
                    throw new OrdenServicioException("La Empresa Supervisora de la Orden de Servicio no tiene Persona relacionada",null);
                }
            }
            if(personaOS==null || personaOS.getIdPersonal()==null){
                throw new OrdenServicioException("La Empresa Supervisora de la Orden de Servicio no tiene Persona relacionada",null);
            }
            LOG.info("personaOS-->"+personaOS.getIdPersonal());
            /* OSINE_SFS-480 - RSIS 40 - Inicio */
            retorno=ordenServicioDAO.cambiarEstadoProceso(idOrdenServicio,idPersonalOri,personaOS.getIdPersonal(),estadoProcesoDto.getIdEstadoProceso(),motivoReasignacion,usuarioDTO,null,null);
            /* OSINE_SFS-480 - RSIS 40 - Fin */
        }catch(Exception e){
            LOG.error("Error observar",e);
            throw new OrdenServicioException(e.getMessage(),e);
        }
        return retorno;
    }
    
    @Override
    @Transactional
    public OrdenServicioDTO observarAprobar(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,String motivoReasignacion,UsuarioDTO usuarioDTO) throws OrdenServicioException{
        LOG.info("observarAprobar");
        OrdenServicioDTO retorno=new OrdenServicioDTO();
        
        try{
            EstadoProcesoDTO estadoProcesoDto=estadoProcesoDAO.find(new EstadoProcesoFilter(Constantes.IDENTIFICADOR_ESTADO_PROCESO_OS_SUPERVISADO)).get(0);
            //buscar personal de la Orden Servicio
            OrdenServicioDTO ordenServicio=ordenServicioDAO.findByFilter(new OrdenServicioFilter(idOrdenServicio)).get(0);
            ExpedienteDTO expedientePadre=expedienteDAO.findByFilter(new ExpedienteFilter(idExpediente)).get(0);
            if(expedientePadre==null || expedientePadre.getPersonal()==null || expedientePadre.getPersonal().getIdPersonal()==null){
                throw new OrdenServicioException("La Orden de Servicio no tiene Expediente relacionado",null);
            }
            /* OSINE_SFS-480 - RSIS 40 - Inicio */
            retorno=ordenServicioDAO.cambiarEstadoProceso(idOrdenServicio,idPersonalOri,expedientePadre.getPersonal().getIdPersonal(),estadoProcesoDto.getIdEstadoProceso(),motivoReasignacion,usuarioDTO,null,null);
            /* OSINE_SFS-480 - RSIS 40 - Fin */
        }catch(Exception e){
            LOG.error("Error observar",e);
            throw new OrdenServicioException(e.getMessage(),e);
        }
        return retorno;
    }
    
    @Override
    @Transactional
    public OrdenServicioDTO confirmarDescargo(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,UsuarioDTO usuarioDTO) throws OrdenServicioException{
        LOG.info("confirmarDescargo");
        OrdenServicioDTO retorno=new OrdenServicioDTO();
        
        try{
            ExpedienteDTO expedientePadre=expedienteDAO.findByFilter(new ExpedienteFilter(idExpediente)).get(0);
            if(expedientePadre==null || expedientePadre.getPersonal()==null || expedientePadre.getPersonal().getIdPersonal()==null){
                throw new OrdenServicioException("La Orden de Servicio no tiene Expediente relacionado",null);
            }
            retorno=ordenServicioDAO.confirmarDescargo(idOrdenServicio,usuarioDTO);
            
            EstadoProcesoDTO estadoProcesoDto=estadoProcesoDAO.find(new EstadoProcesoFilter(Constantes.IDENTIFICADOR_ESTADO_PROCESO_EXP_DERIVADO)).get(0);
            ExpedienteDTO expedCambioEstado=expedienteDAO.cambiarEstadoProceso(idExpediente,idPersonalOri,idPersonalOri,idPersonalOri,estadoProcesoDto.getIdEstadoProceso(),null,usuarioDTO);
            LOG.info("Expediente CambioEstado-->"+expedCambioEstado.getIdExpediente());
        }catch(Exception e){
            LOG.error("Error confirmarDescargo",e);
            throw new OrdenServicioException(e.getMessage(),e);
        }
        return retorno;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<OrdenServicioDTO> listarOrdenServicio(OrdenServicioFilter filtro){
        LOG.info("listarOrdenServicio");
        List<OrdenServicioDTO> retorno=null;
        try{
            retorno = ordenServicioDAO.find(filtro);
        }catch(Exception ex){
            LOG.error("Error en listarOrdenServicio",ex);
        }
        return retorno;
    } 
    /* OSINE_SFS-480 - RSIS 17 - Inicio */
    public void actualizarFechaPlazo(Long idExpediente, Long idOrdenServicio, UsuarioDTO usuarioDTO, Long idArchivo, String fechaInicioPlazoDescargo){  
    	LOG.info("actualizarFechaPlazo");
    	Calendar c1=GregorianCalendar.getInstance();
    	SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
    	ActividadFilter filtroActividad=new ActividadFilter();
    	OrdenServicioFilter filtroOrden=new OrdenServicioFilter(); 
    	UnidadSupervisadaFilter filtroUnidad=new UnidadSupervisadaFilter();
    	DireccionUnidadSupervisadaFilter filtroDireccion=new DireccionUnidadSupervisadaFilter();
    	Date fechaInicio=null, fechaInicioDescargo=null, fechaFinDescargo=null;
    	try{
            c1.setTime(new SimpleDateFormat("dd/MM/yy").parse(fechaInicioPlazoDescargo));
            fechaInicio=(Date) c1.getTime();  
	    	c1.add(Calendar.DATE, 1);
	    	fechaInicioDescargo = (Date) c1.getTime();    	
	    	List<ExpedienteDTO> listaExpediente=expedienteDAO.findByFilter(new ExpedienteFilter(idExpediente));
    		if(listaExpediente!=null && listaExpediente.size()!=0){
    			ExpedienteDTO expediente=listaExpediente.get(0);
    			if(expediente.getUnidadSupervisada()!=null && expediente.getUnidadSupervisada().getIdUnidadSupervisada()!=null) {
	    			filtroUnidad.setIdUnidadSupervisada(expediente.getUnidadSupervisada().getIdUnidadSupervisada());
	    			List<UnidadSupervisadaDTO> listaUnidadSupervisada=unidadSupervisadaServiceNeg.listarUnidadSupervisada(filtroUnidad);
	    			if(listaUnidadSupervisada!=null && listaUnidadSupervisada.size()!=0){
		    			UnidadSupervisadaDTO unidadSupervisada=listaUnidadSupervisada.get(0);
		    			if(unidadSupervisada.getActividad()!=null && unidadSupervisada.getActividad().getIdActividad()!=null){
		    				List<MaestroColumnaDTO> listaDireUoDl = maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_DIRE_INPS_UO_DL, Constantes.APLICACION_INPS);
		    				String cadCodigosTipoDireccion=""; 
		    				Integer i=0;
		    				for(MaestroColumnaDTO registroMaestro : listaDireUoDl) {
		    					if(i==0) { cadCodigosTipoDireccion+="'"+registroMaestro.getCodigo()+"'"; }
		    					else { cadCodigosTipoDireccion+=", '"+registroMaestro.getCodigo()+"'"; }
		    					i++;
		    				}
		    				LOG.info("cadCodigosTipoDireccion " + cadCodigosTipoDireccion);
		    				filtroDireccion.setIdUnidadSupervisada(unidadSupervisada.getIdUnidadSupervisada());
		    				filtroDireccion.setCadCodigosTipoDireccion(cadCodigosTipoDireccion);
		    				List<DireccionUnidadSupervisadaDTO> listaDireccionUnidad=direccionUnidadSupervisadaNeg.buscarDireccionesUnidad(filtroDireccion);
		    				filtroActividad.setIdActividad(unidadSupervisada.getActividad().getIdActividad());
		    				List<ActividadDTO> listaActividad = actividadDAO.findBy(filtroActividad);
		    				if(listaActividad!=null && listaDireccionUnidad!=null){
		    		    		if(listaActividad.size()!=0 && listaDireccionUnidad.size()!=0){		    		    			
		    		    			DireccionUnidadSupervisadaDTO direccionUnidad=getDireccionUnidad(listaDireUoDl, listaDireccionUnidad);
		    		    			System.out.println("unidadSupervisada " + unidadSupervisada.getIdUnidadSupervisada() + " Departamento " + direccionUnidad.getDepartamento().getNombre());
		    		    			Long plazoDescargo=listaActividad.get(0).getPlazoDescargo();	    		    			
		    		    			fechaFinDescargo=ordenServicioDAO.calculoFechaFin(sdf.format(fechaInicio), direccionUnidad.getDepartamento().getNombre(), plazoDescargo);
		    		    			filtroOrden.setIdOrdenServicio(idOrdenServicio);
		    		    			filtroOrden.setIdExpediente(idExpediente);
		    		    			filtroOrden.setFechaIniPlazoDescargo(fechaInicioDescargo);
		    		    			filtroOrden.setFechaFinPlazoDescargo(fechaFinDescargo);	 
		    		    			filtroOrden.setIdArchivoPlazoDescargo(idArchivo);
		    		    			System.out.println(direccionUnidad.getDepartamento().getIdDepartamento());
		    		    			filtroOrden.setIdDepartPlazoDescargo(direccionUnidad.getDepartamento().getIdDepartamento());
		    		    			ordenServicioDAO.actualizar(filtroOrden, usuarioDTO);
		    		    		}
		    		    	}
		    			}	
	    			}
    			}	    		
	    	}	    	
    	}catch(Exception ex){
            LOG.error("Error en actualizarFechaPlazo", ex);
        }   	
    } 
    
    public DireccionUnidadSupervisadaDTO getDireccionUnidad(List<MaestroColumnaDTO> listaDireUoDl, List<DireccionUnidadSupervisadaDTO> listaDireccionUnidad){
    	//Retorna la direccion segun el dominio DIRE_INPS_UO_DL >>> Notificacion - Oficina - Operativa
    	DireccionUnidadSupervisadaDTO retorno=listaDireccionUnidad.get(0);    	
    	for(MaestroColumnaDTO registroMaestro : listaDireUoDl) {
    		for(DireccionUnidadSupervisadaDTO registro : listaDireccionUnidad) {    			
    			if(registro.getIdTipoDireccion()!=null && registro.getIdTipoDireccion().getCodigo()!=null){    				
	    			if(registro.getIdTipoDireccion().getCodigo().equals(registroMaestro.getCodigo())){
	    				return registro;
	    			}
    			}
    		}
    	} 
    	return retorno;
	} 
    /* OSINE_SFS-480 - RSIS 17 - Fin */

    /* OSINE_SFS-480 - RSIS 40 - Inicio */    
    @Override
    @Transactional
    public List<OrdenServicioDTO> devolverOrdenServicioSupe(List<OrdenServicioDTO> ordenesServicio, Long idPersonalOri,Long idPersonalOriSIGED, Long idPeticion, Long idMotivo, String comentarioDevolucion, UsuarioDTO usuarioDTO) throws OrdenServicioException {
        LOG.info("devolverOrdenServicioSupe");
        List<OrdenServicioDTO> retorno=new ArrayList<OrdenServicioDTO>();
        try {
            for(OrdenServicioDTO ordenServicio : ordenesServicio){
                PersonalDTO pers=new PersonalDTO();
                pers.setIdPersonal(idPersonalOri);
                pers.setIdPersonalSiged(idPersonalOriSIGED);
                ordenServicio.getExpediente().setPersonal(pers);
                ExpedienteDTO expedienteDevueltoWS=expedienteServiceNeg.rechazarExpedienteSIGED(ordenServicio.getExpediente(), comentarioDevolucion);

                if (!expedienteDevueltoWS.getEstado().equals(InvocationResult.SUCCESS.getCode().toString())){    
                    throw new OrdenServicioException(expedienteDevueltoWS.getMensajeServicio(),null);
                }
                
                EstadoProcesoDTO estadoProcesoDto=estadoProcesoDAO.find(new EstadoProcesoFilter(Constantes.IDENTIFICADOR_ESTADO_PROCESO_OS_REGISTRO)).get(0);
                ExpedienteDTO expedientePadre=expedienteDAO.findByFilter(new ExpedienteFilter(ordenServicio.getExpediente().getIdExpediente())).get(0);
               
                if(expedientePadre==null || expedientePadre.getPersonal()==null || expedientePadre.getPersonal().getIdPersonal()==null){
                    throw new OrdenServicioException("La Orden de Servicio no tiene Expediente relacionado",null);
                }
                
                OrdenServicioDTO ordenServ=ordenServicioDAO.cambiarEstadoProceso(ordenServicio.getIdOrdenServicio(),idPersonalOri,expedientePadre.getPersonal().getIdPersonal(),estadoProcesoDto.getIdEstadoProceso(),comentarioDevolucion,usuarioDTO,idPeticion,idMotivo);
                retorno.add(ordenServ);
            }    
        } catch(Exception e){
            LOG.error("Error devolverOrdenServicioSupe",e);
            throw new OrdenServicioException(e.getMessage(),e);
        }
        return retorno;
    }
    /* OSINE_SFS-480 - RSIS 40 - Fin */    

    /* OSINE_SFS-480 - RSIS 43 - Inicio */
    @Override
    @Transactional
    public OrdenServicioDTO anularExpedienteOrden(Long idExpediente,Long idOrdenServicio,UsuarioDTO usuarioDTO, String motivoAnulacionOs, Long idPersonal) throws OrdenServicioException {
            LOG.info("anularExpedienteOrden");
    OrdenServicioDTO retorno=new OrdenServicioDTO();
    try{
            EstadoProcesoDTO estadoProcesoDto=estadoProcesoDAO.find(new EstadoProcesoFilter(Constantes.IDENTIFICADOR_ESTADO_PROCESO_EXP_DERIVADO)).get(0);
            ExpedienteDTO expedienteDTO=expedienteDAO.cambiarEstadoProceso(idExpediente,idPersonal,idPersonal,idPersonal,estadoProcesoDto.getIdEstadoProceso(),motivoAnulacionOs,usuarioDTO);
        retorno=ordenServicioDAO.anularExpedienteOrden(idExpediente, idOrdenServicio,usuarioDTO);
    }catch(Exception e){
        LOG.error("Error anularExpedienteOrden",e);
        throw new OrdenServicioException(e.getMessage(),e);
    }
    return retorno;
    }
    /* OSINE_SFS-480 - RSIS 43 - Fin */
    @Override
    public Date calculoFechaFin(String fechaInicio, String idDepartamento, Long nroDias) throws OrdenServicioException {
     LOG.info("calculoFechaFin");
        Date retorno = null;
        try{
            retorno=ordenServicioDAO.calculoFechaFin(fechaInicio,idDepartamento,nroDias);
        }catch(Exception e){
            LOG.error("Error en calculoFechaFin",e);
        }
        return retorno;
    }
    
    /* OSINE791 RSIS20 - Inicio */
    @Override
    @Transactional
    public OrdenServicioDTO atenderDsr(ExpedienteDTO expedienteDTO,Long idPersonalOri,String flagSupervision,Long nroIteracion,UsuarioDTO usuarioDTO) throws OrdenServicioException{
        LOG.info("atenderDsr");
        OrdenServicioDTO retorno=new OrdenServicioDTO();
        try{
            SupervisionFilter filtro = new SupervisionFilter();
            filtro.setIdOrdenServicio(expedienteDTO.getOrdenServicio().getIdOrdenServicio());
            List<SupervisionDTO> listaSupervision = supervisionDAO.find(filtro);
            if (!CollectionUtils.isEmpty(listaSupervision)) {
                SupervisionDTO supervisionRegistro = listaSupervision.get(0);
                if(supervisionRegistro.getResultadoSupervisionDTO() != null && supervisionRegistro.getResultadoSupervisionDTO().getIdResultadosupervision() != null){
                    //actualiza flagTramiteFinalizado
                    ExpedienteDTO finalizado=expedienteDAO.veriActuFlgTramFinalizadoDsr(expedienteDTO.getIdExpediente(), expedienteDTO.getOrdenServicio().getIdOrdenServicio(),supervisionRegistro.getResultadoSupervisionDTO().getIdResultadosupervision(), usuarioDTO);
                    LOG.info("Expediente finalizado?--->"+finalizado.getFlagTramiteFinalizado());                        
                    //envio documentos de Generar Resultados y medios prob al SIGED (Carta,Acta y constancia)
                    SupervisionDTO supDocEnviados=enviaDocumentosDsrSiged(supervisionRegistro.getIdSupervision(),nroIteracion,expedienteDTO,usuarioDTO);
                    LOG.info("Documentos enviados a SIGED-->"+supDocEnviados.getIdSupervision());
                }else{
                    throw new OrdenServicioException("La Supervisi&oacute no cuenta con Resultados Generados",null);
                }
            }else{
                throw new OrdenServicioException("La Orden de Servicio no tiene Supervisi&oacute;n relacionada",null);
            }
            
            EstadoProcesoDTO estadoProcesoDto=estadoProcesoDAO.find(new EstadoProcesoFilter(Constantes.IDENTIFICADOR_ESTADO_PROCESO_OS_SUPERVISADO)).get(0);
            ExpedienteDTO expedientePadre=expedienteDAO.findByFilter(new ExpedienteFilter(expedienteDTO.getIdExpediente())).get(0);
            if(expedientePadre==null || expedientePadre.getPersonal()==null || expedientePadre.getPersonal().getIdPersonal()==null){
                throw new OrdenServicioException("La Orden de Servicio no tiene Expediente relacionado",null);
            }            
            retorno=ordenServicioDAO.cambiarEstadoProceso(expedienteDTO.getOrdenServicio().getIdOrdenServicio(),idPersonalOri,expedientePadre.getPersonal().getIdPersonal(),estadoProcesoDto.getIdEstadoProceso(),null,usuarioDTO,null,null);
           
        }catch(Exception e){
            LOG.error("Error atenderDsr",e);
            throw new OrdenServicioException(e.getMessage(),e);
        }
        return retorno;
    }  
    
    public SupervisionDTO enviaDocumentosDsrSiged(Long idSupervision,Long nroIteracion,ExpedienteDTO expedienteDTO,UsuarioDTO usuarioDTO) throws OrdenServicioException{
        LOG.info("enviaDocumentosDsrSiged");
        SupervisionDTO retorno=new SupervisionDTO(idSupervision);
        try{
            if(expedienteDTO.getUnidadSupervisada()!=null && StringUtil.isEmpty(expedienteDTO.getUnidadSupervisada().getRuc())){
                throw new OrdenServicioException("Unidad Supervisada no tiene RUC.", null);
            }
            DireccionUnidadSupervisadaDTO direUnid=unidadSupervisadaServiceNeg.buscarDireccUnidSupInps(expedienteDTO.getUnidadSupervisada().getCodigoOsinergmin());
            if(direUnid!=null && direUnid.getDepartamento()!=null && direUnid.getDepartamento().getIdDepartamento()!=null){                    
            }else{
                throw new OrdenServicioException("No existe Direcci&oacute;n para la Unidad Supervisada.", null);
            }
            
            //obtener empresa supervisada
//            if (expedienteDTO.getEmpresaSupervisada()==null || expedienteDTO.getEmpresaSupervisada().getIdEmpresaSupervisada() == null) {
//                throw new OrdenServicioException("No existe Identificador de Empresa Supervisada.", null);
//            }
//            EmpresaSupDTO emprSupe = empresaSupervisadaServiceNeg.obtenerEmpresaSupervisada(new EmpresaSupDTO(expedienteDTO.getEmpresaSupervisada().getIdEmpresaSupervisada()));
            //obtener direcc de empreSup
//            List<BusquedaDireccionxEmpSupervisada> listarDireccionEmpresaSupervisada = empresaSupervisadaServiceNeg.listarDireccionEmpresaSupervisada(expedienteDTO.getEmpresaSupervisada().getIdEmpresaSupervisada());           
//            BusquedaDireccionxEmpSupervisada direEmprSupe = listarDireccionEmpresaSupervisada != null && listarDireccionEmpresaSupervisada.size() > 0 ? listarDireccionEmpresaSupervisada.get(0) : null;
//            if (direEmprSupe == null) {
//                throw new OrdenServicioException("No existe Direcci&oacute;n para la Empresa Supervisada.", null);
//            }
            
            //obtener usuario creador
            /* OSINE_SFS-Ajustes - mdiosesf - Inicio */
            //ExpedienteDTO expedientePadre = expedienteDAO.findByFilter(new ExpedienteFilter(expedienteDTO.getIdExpediente())).get(0);
            PersonalFilter filtro=new PersonalFilter();
            filtro.setNombreUsuarioSiged(usuarioDTO.getCodigo());
            List<PersonalDTO> listPersonal = personalDAO.find(filtro);
            int usuarioCreador = (!CollectionUtils.isEmpty(listPersonal)) ? Integer.parseInt(personalDAO.find(filtro).get(0).getIdPersonalSiged().toString()) : null;
            //int usuarioCreador=expedientePadre.getPersonal().getIdPersonalSiged().intValue();
            /* OSINE_SFS-Ajustes - mdiosesf - Fin */
            
            if (nroIteracion==Constantes.SUPERVISION_PRIMERA_ITERACION){ 
                String concatIdDetSup=getConcatIdDetalleSupervision(idSupervision);
                LOG.info("----->"+concatIdDetSup);              
                                                          
                /* OSINE_SFS-Ajustes - mdiosesf - Inicio */
                String tipoDocMultimedia="";    
                List<MaestroColumnaDTO> lstTipoDocMultimedia= maestroColumnaDAO.findMaestroColumnaByDominioAplicDesc(Constantes.DOMINIO_TIP_DOC_SUP_SIGED, Constantes.APLICACION_INPS, Constantes.DESCRIPCION_TIP_DOC_SUP_SIGED_DOCUMENTO_MULTIMEDIA);
                if(!CollectionUtils.isEmpty(lstTipoDocMultimedia)){
                	tipoDocMultimedia=lstTipoDocMultimedia.get(0).getCodigo();
                }
                //---------------------------------------------------------------------------------    
                //--------------------DOCUMENTOS GENERADOS DE SUPERVISION--------------------------                
                //---------------------------------------------------------------------------------
                
                List<DocumentoAdjuntoDTO> lstDocsSup=documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(new DocumentoAdjuntoFilter(null,idSupervision,null));
                LOG.info(!CollectionUtils.isEmpty(lstDocsSup)?"----->lstDocsSupervision data->"+lstDocsSup.size():"----->lstDocsSupervision sin data");
                                
                if(!CollectionUtils.isEmpty(lstDocsSup)){
                	List<DocumentoAdjuntoDTO> listAdjDocsSup=null;                	
                	for(DocumentoAdjuntoDTO doc : lstDocsSup) {
                		List<DocumentoAdjuntoDTO> listDocsSup=new ArrayList<DocumentoAdjuntoDTO>();
                		String tipoDoc=(doc.getIdTipoDocumento()!=null && doc.getIdTipoDocumento().getCodigo()!=null) ? doc.getIdTipoDocumento().getCodigo() : null;                		
	                	if(tipoDoc!=null) {
	                		listDocsSup.add(doc);
		                	List<File> filesDocSup = armarFilesEnvioPghDocAdjSiged(listDocsSup,usuarioDTO);
	                		ExpedienteInRO expedienteInRODocSup = armarExpedienteInRoEnvioDocSiged(expedienteDTO,tipoDoc,usuarioCreador,direUnid,usuarioDTO);	                	                
	                		/* OSINE_MAN_DSR_0024  - Inicio */
	                		if(!CollectionUtils.isEmpty(filesDocSup))
	                		{
		                		DocumentoOutRO documentoOutRODocSup = ExpedienteInvoker.addDocument(HOST + "/remote/expediente/agregarDocumento", expedienteInRODocSup, filesDocSup, false);            
			                    if (!(documentoOutRODocSup.getResultCode().equals(InvocationResult.SUCCESS.getCode()))) {                
			                        LOG.info("Error: " + documentoOutRODocSup.getResultCode() + "-Ocurrio un error: " + documentoOutRODocSup.getMessage());
			                        throw new OrdenServicioException("Error en Servicio SIGED: " + documentoOutRODocSup.getMessage(), null);
			                    } else{
			                        LOG.info("----->getCodigoDocumento:"+documentoOutRODocSup.getCodigoDocumento());
			                    }
	                		}
	                		/* OSINE_MAN_DSR_0024  - Fin */
	                	} else {
	                		//Documentos Adjuntos de Supervision
	                		if(listAdjDocsSup==null) listAdjDocsSup=new ArrayList<DocumentoAdjuntoDTO>();
	                		listAdjDocsSup.add(doc);
	                	}
                	}
                	if(!CollectionUtils.isEmpty(listAdjDocsSup)){
                		//Envia a SIGED Documentos Adjuntos de Supervision
                		List<File> filesAdjDocSup = armarFilesEnvioPghDocAdjSiged(listAdjDocsSup,usuarioDTO);
                		ExpedienteInRO expedienteInRODocSup = armarExpedienteInRoEnvioDocSiged(expedienteDTO,tipoDocMultimedia,usuarioCreador,direUnid,usuarioDTO);
                		DocumentoOutRO documentoOutRODocSup = ExpedienteInvoker.addDocument(HOST + "/remote/expediente/agregarDocumento", expedienteInRODocSup, filesAdjDocSup, false);            
	                    if (!(documentoOutRODocSup.getResultCode().equals(InvocationResult.SUCCESS.getCode()))) {                
	                        LOG.info("Error: " + documentoOutRODocSup.getResultCode() + "-Ocurrio un error: " + documentoOutRODocSup.getMessage());
	                        throw new OrdenServicioException("Error en Servicio SIGED: " + documentoOutRODocSup.getMessage(), null);
	                    } else{
	                        LOG.info("----->getCodigoDocumento:"+documentoOutRODocSup.getCodigoDocumento());
	                    }
                	}
                }
                //---------------------------------------------------------------------------------    
                //--------------------DOCUMENTOS ADJUNTOS DE MEDIOS PROBATORIOS--------------------           
                //---------------------------------------------------------------------------------
                                
                List<DocumentoAdjuntoDTO> lstDocsDetSup=concatIdDetSup!=null && !concatIdDetSup.equals("") ? documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(new DocumentoAdjuntoFilter(null,null,concatIdDetSup)) : null;
                LOG.info(!CollectionUtils.isEmpty(lstDocsDetSup)?"----->lstDocsDetSup data->"+lstDocsDetSup.size():"----->lstDocsDetSup sin data");
                
                List<File> filesDocMedioProb = armarFilesEnvioPghDocAdjSiged(lstDocsDetSup,usuarioDTO);
                ExpedienteInRO expedienteInRODocMedioProb = armarExpedienteInRoEnvioDocSiged(expedienteDTO,tipoDocMultimedia,usuarioCreador,direUnid,usuarioDTO);
                
                if(!CollectionUtils.isEmpty(filesDocMedioProb)){
                    DocumentoOutRO documentoOutRODocMedioProb = ExpedienteInvoker.addDocument(HOST + "/remote/expediente/agregarDocumento", expedienteInRODocMedioProb, filesDocMedioProb, false);            
                    if (!(documentoOutRODocMedioProb.getResultCode().equals(InvocationResult.SUCCESS.getCode()))) {                
                        LOG.info("Error: " + documentoOutRODocMedioProb.getResultCode() + "-Ocurrio un error: " + documentoOutRODocMedioProb.getMessage());
                        throw new OrdenServicioException("Error en Servicio SIGED: " + documentoOutRODocMedioProb.getMessage(), null);
                    }else{
                        LOG.info("----->getCodigoDocumento:"+documentoOutRODocMedioProb.getCodigoDocumento());
                    }
                }
                /* OSINE_SFS-Ajustes - mdiosesf - Fin */
            }
            else if (nroIteracion>Constantes.SUPERVISION_PRIMERA_ITERACION){
                /* OSINE791 RSIS42 - Inicio */

                String tipoDocCartaVisita="";
                String tipoDocActaLevanMedSeg="";
                String tipoDocConsHabilRegHidro="";
                String tipoDocMultimedia="";
            	//obtener tipoDocumento
                List<MaestroColumnaDTO> lstTipoCartaVisita = maestroColumnaDAO.findMaestroColumnaByDominioAplicDesc(Constantes.DOMINIO_TIP_DOC_SUP_SIGED, Constantes.APLICACION_INPS, Constantes.DESCRIPCION_TIP_DOC_SUP_SIGED_CARTA_VISITA);
                List<MaestroColumnaDTO> lstTipoActaLevanMedSeg = maestroColumnaDAO.findMaestroColumnaByDominioAplicDesc(Constantes.DOMINIO_TIP_DOC_SUP_SIGED, Constantes.APLICACION_INPS, Constantes.DESCRIPCION_TIP_DOC_SUP_SIGED_ACTA_LEVANTAMIENTO_MEDIDA_SEGURIDAD);
                List<MaestroColumnaDTO> lstTipoConsHabilRegHidro = maestroColumnaDAO.findMaestroColumnaByDominioAplicDesc(Constantes.DOMINIO_TIP_DOC_SUP_SIGED, Constantes.APLICACION_INPS, Constantes.DESCRIPCION_TIP_DOC_SUP_SIGED_CONSTANCIA_HABILITACION_REGIS_HIDRO);
                List<MaestroColumnaDTO> lstTipoDocMultimedia = maestroColumnaDAO.findMaestroColumnaByDominioAplicDesc(Constantes.DOMINIO_TIP_DOC_SUP_SIGED, Constantes.APLICACION_INPS, Constantes.DESCRIPCION_TIP_DOC_SUP_SIGED_DOCUMENTO_MULTIMEDIA);
                
                if(!CollectionUtils.isEmpty(lstTipoCartaVisita)){
                	tipoDocCartaVisita=lstTipoCartaVisita.get(0).getCodigo();
                }
                
                if(!CollectionUtils.isEmpty(lstTipoActaLevanMedSeg)){
                	tipoDocActaLevanMedSeg=lstTipoActaLevanMedSeg.get(0).getCodigo();
                }
                
                if(!CollectionUtils.isEmpty(lstTipoConsHabilRegHidro)){
                	tipoDocConsHabilRegHidro=lstTipoConsHabilRegHidro.get(0).getCodigo();
                }
                
                if(!CollectionUtils.isEmpty(lstTipoDocMultimedia)){
                	tipoDocMultimedia=lstTipoDocMultimedia.get(0).getCodigo();
                }

                LOG.info("-----tipoDocMedioProbNoSubsanado>"+tipoDocCartaVisita);
                String concatIdDetSup=getConcatIdDetalleSupervision(idSupervision);
                LOG.info("concatIdDetSup----->"+concatIdDetSup);
               //obtener listado documentos a enviar a siged
               List<DocumentoAdjuntoDTO> lstDocsSup=documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(new DocumentoAdjuntoFilter(null,idSupervision,null));
               LOG.info(!CollectionUtils.isEmpty(lstDocsSup)?"----->lstDocsSupervision data->"+lstDocsSup.size():"----->lstDocsSupervision sin data");
               
               List<DocumentoAdjuntoDTO> lstDocsDetSuper=documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(new DocumentoAdjuntoFilter(null,null,concatIdDetSup));
               LOG.info(!CollectionUtils.isEmpty(lstDocsDetSuper)?"----->lstDocsDetSup data->"+lstDocsDetSuper.size():"----->lstDocsDetSup sin data");
               
               
               //validar tipo de documento
               for (int i = 0; i < lstDocsSup.size(); i++) {
        		   List<File> fileDoc = null;
        		   String tipoDocSiged="";
            	   if (lstDocsSup.get(i).getIdTipoDocumento().getCodigo().equals(tipoDocCartaVisita)){
            		   tipoDocSiged= tipoDocCartaVisita;
            	   } 
            	   else if (lstDocsSup.get(i).getIdTipoDocumento().getCodigo().equals(tipoDocActaLevanMedSeg) ){
            		   tipoDocSiged=tipoDocActaLevanMedSeg;
            	   }
            	   else if (lstDocsSup.get(i).getIdTipoDocumento().getCodigo().equals(tipoDocConsHabilRegHidro) ){
            		   tipoDocSiged=tipoDocConsHabilRegHidro;
            	   }
    

        		   //armar data pa enviar al SIGED
                   fileDoc = envioPghDocAdjSiged(lstDocsSup.get(i),usuarioDTO);
        		   ExpedienteInRO expedienteInRODocSup = armarExpedienteInRoEnvioDocSiged(expedienteDTO,tipoDocSiged,usuarioCreador,direUnid,usuarioDTO);
                   
                   if(!CollectionUtils.isEmpty(fileDoc)){
                       DocumentoOutRO documentoOutRODocSup = ExpedienteInvoker.addDocument(HOST + "/remote/expediente/agregarDocumento", expedienteInRODocSup, fileDoc, false);            
                       if (!(documentoOutRODocSup.getResultCode().equals(InvocationResult.SUCCESS.getCode()))) {                
                           LOG.info("Error: " + documentoOutRODocSup.getResultCode() + "-Ocurrio un error: " + documentoOutRODocSup.getMessage());
                           throw new OrdenServicioException("Error en Servicio SIGED: " + documentoOutRODocSup.getMessage(), null);
                       }else{
                           LOG.info("----->getCodigoDocumento:"+documentoOutRODocSup.getCodigoDocumento());
                       }
                   }
               	}
              
               
               List<File> filesDocMedioProb = armarFilesEnvioPghDocAdjSiged(lstDocsDetSuper,usuarioDTO);
                ExpedienteInRO expedienteInRODocMedioProb = armarExpedienteInRoEnvioDocSiged(expedienteDTO,tipoDocMultimedia,usuarioCreador,direUnid,usuarioDTO);
               
               if(!CollectionUtils.isEmpty(filesDocMedioProb)){
                   DocumentoOutRO documentoOutRODocMedioProb = ExpedienteInvoker.addDocument(HOST + "/remote/expediente/agregarDocumento", expedienteInRODocMedioProb, filesDocMedioProb, false);            
                   if (!(documentoOutRODocMedioProb.getResultCode().equals(InvocationResult.SUCCESS.getCode()))) {                
                       LOG.info("Error: " + documentoOutRODocMedioProb.getResultCode() + "-Ocurrio un error: " + documentoOutRODocMedioProb.getMessage());
                       throw new OrdenServicioException("Error en Servicio SIGED: " + documentoOutRODocMedioProb.getMessage(), null);
                   }else{
                       LOG.info("----->getCodigoDocumento:"+documentoOutRODocMedioProb.getCodigoDocumento());
                   }
               }
                /* OSINE791 RSIS42 - fIN */
            }
 
        }catch(Exception e){
            LOG.error("enviaDocumentosDsrSiged",e);
            throw new OrdenServicioException(e.getMessage(),e);
        }
        return retorno;
    }
    private List<File> armarFilesEnvioPghDocAdjSiged(List<DocumentoAdjuntoDTO> lstDocs, UsuarioDTO usuarioDTO) throws OrdenServicioException {
        LOG.info("armarFilesEnvioPghDocAdjSiged");
        List<File> files = new ArrayList<File>();
        try {
            if (lstDocs != null) {
                if (lstDocs.size() > 0) {
                    for (DocumentoAdjuntoDTO doc : lstDocs) {
                        File someFile = new File(doc.getNombreArchivo());

                        List<DocumentoAdjuntoDTO> regDoc = pghDocumentoAdjuntoDAO.getDocumentoAdjunto(new DocumentoAdjuntoFilter(doc.getIdDocumentoAdjunto()));
                        if (!CollectionUtils.isEmpty(regDoc) && (StringUtil.isEmpty(regDoc.get(0).getFlagEnviadoSiged()) || !regDoc.get(0).getFlagEnviadoSiged().equals(Constantes.ESTADO_ACTIVO))) {
                            byte[] mapByte = pghDocumentoAdjuntoDAO.findBlobPghDocumento(doc.getIdDocumentoAdjunto());
                            if (mapByte == null) {
                                throw new OrdenServicioException("No existe Archivo fisico para el documento: " + doc.getNombreArchivo() + ".", null);
                            }
                            if (mapByte != null) {
                                FileOutputStream fos = new FileOutputStream(someFile);
                                fos.write(mapByte);
                                fos.flush();
                                fos.close();
                                FileUtils.openInputStream(someFile);
                            }
                            files.add(someFile);
                            //actualizar flagEnviadoSiged en pghDocumentoAdjunto
                            pghDocumentoAdjuntoDAO.changeFlagEnviadoSigedPghDocumentoAdjunto(new DocumentoAdjuntoDTO(doc.getIdDocumentoAdjunto(), Constantes.ESTADO_ACTIVO), usuarioDTO);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("Error en armarFilesEnvioDocSiged", e);
            throw new OrdenServicioException(e.getMessage(), e);
        }
        return files;
    }
    /* OSINE791 RSIS42 - Inicio */
    private List<File> envioPghDocAdjSiged(DocumentoAdjuntoDTO doc,UsuarioDTO usuarioDTO) throws OrdenServicioException{
        List<File> files = new ArrayList();
        try{
             
            File someFile = new File(doc.getNombreArchivo());
            
            List<DocumentoAdjuntoDTO> regDoc=pghDocumentoAdjuntoDAO.getDocumentoAdjunto(new DocumentoAdjuntoFilter(doc.getIdDocumentoAdjunto()));
            if(!CollectionUtils.isEmpty(regDoc) && (StringUtil.isEmpty(regDoc.get(0).getFlagEnviadoSiged()) || !regDoc.get(0).getFlagEnviadoSiged().equals(Constantes.ESTADO_ACTIVO))){
                byte[] mapByte=pghDocumentoAdjuntoDAO.findBlobPghDocumento(doc.getIdDocumentoAdjunto());
                if (mapByte == null) {
                    throw new OrdenServicioException("No existe Archivo fisico para el documento: " + doc.getNombreArchivo() + ".", null);
                }
                if (mapByte != null) {
                    FileOutputStream fos = new FileOutputStream(someFile);
                    fos.write(mapByte);
                    fos.flush();
                    fos.close();
                    FileUtils.openInputStream(someFile);
                }
                files.add(someFile);
                //actualizar flagEnviadoSiged en pghDocumentoAdjunto
                pghDocumentoAdjuntoDAO.changeFlagEnviadoSigedPghDocumentoAdjunto(new DocumentoAdjuntoDTO(doc.getIdDocumentoAdjunto(),Constantes.ESTADO_ACTIVO), usuarioDTO);
            }

        }catch(Exception e){
            LOG.error("Error en armarFilesEnvioDocSiged",e);
            throw new OrdenServicioException(e.getMessage(),e);
        }
        return files;
    } 
    /* OSINE791 RSIS42 - Fin */
    
//    public ExpedienteInRO armarExpedienteInRoEnvioDocSiged(ExpedienteDTO expedienteDTO, String codTipoDoc,EmpresaSupDTO emprSupe,int usuarioCreador,BusquedaDireccionxEmpSupervisada direEmprSupe,UsuarioDTO usuarioDTO){
    public ExpedienteInRO armarExpedienteInRoEnvioDocSiged(ExpedienteDTO expedienteDTO, String codTipoDoc,int usuarioCreador,DireccionUnidadSupervisadaDTO direUnid,UsuarioDTO usuarioDTO){
        ExpedienteInRO expedienteInRO = new ExpedienteInRO();
        try{
            expedienteInRO.setNroExpediente(expedienteDTO.getNumeroExpediente());
            DocumentoInRO documentoInRO = new DocumentoInRO();

            documentoInRO.setCodTipoDocumento(Integer.valueOf(codTipoDoc));
            documentoInRO.setAsunto(expedienteDTO.getAsuntoSiged());
            documentoInRO.setAppNameInvokes("");
            documentoInRO.setPublico(Constantes.ESTADO_SIGED_SI);
            documentoInRO.setEnumerado(Constantes.ESTADO_SIGED_NO);
            documentoInRO.setEstaEnFlujo(Constantes.ESTADO_SIGED_SI);
            documentoInRO.setFirmado(Constantes.ESTADO_SIGED_NO);        
            documentoInRO.setDelExpediente(Constantes.ESTADO_SIGED_SI);
            documentoInRO.setNroFolios(1);
            documentoInRO.setCreaExpediente(Constantes.ESTADO_SIGED_NO);
            documentoInRO.setUsuarioCreador(usuarioCreador);
            //CLIENTE
            ClienteInRO cliente1 = new ClienteInRO();
//            int codiTipoIdent = 3;
//            if (emprSupe.getRuc() != null) {
//                codiTipoIdent = 1;
//            } else if (emprSupe.getTipoDocumentoIdentidad() != null && emprSupe.getTipoDocumentoIdentidad().getCodigo().equals(Constantes.CODIGO_TIPO_DOCUMENTO_DNI)) {
//                codiTipoIdent = 2;
//            }
            int codiTipoIdent=1;
            cliente1.setCodigoTipoIdentificacion(codiTipoIdent);
            
//            cliente1.setNroIdentificacion((codiTipoIdent == 1) ? emprSupe.getRuc() : emprSupe.getNroIdentificacion());
            cliente1.setNroIdentificacion(expedienteDTO.getUnidadSupervisada().getRuc());
            cliente1.setTipoCliente(3);
            
//            if (codiTipoIdent == 1) {
//                cliente1.setRazonSocial(emprSupe.getRazonSocial());//Obligatorio si codigoTipoIdentificacion es 1.
//            } else {
//                cliente1.setNombre(emprSupe.getNombre());//No es obligatorio si codigoTipoIdentificacion es 1.
//                cliente1.setApellidoPaterno(emprSupe.getApellidoPaterno());//No es obligatorio si codigoTipoIdentificacion es 1.
//                cliente1.setApellidoMaterno(emprSupe.getApellidoMaterno());//No es obligatorio si codigoTipoIdentificacion es 1.
//            }
            cliente1.setRazonSocial(expedienteDTO.getUnidadSupervisada().getNombreUnidad());

            DireccionxClienteInRO direccion1 = new DireccionxClienteInRO();
//            direccion1.setDireccion(direEmprSupe.getDireccionCompleta());
            direccion1.setDireccion(direUnid.getDireccionCompleta());
            direccion1.setDireccionPrincipal(true);
//            direccion1.setTelefono(emprSupe.getTelefono());
            direccion1.setTelefono(direUnid.getTelefono1());
            
            //RSIS Ajustes - mdiosesf - Inicio
            //direccion1.setUbigeo(150104); //Pendiente
            direccion1.setUbigeo(Integer.parseInt(
                    Utiles.padLeft(direUnid.getDepartamento().getIdDepartamento().toString(), 2, '0')
                    + Utiles.padLeft(direUnid.getProvincia().getIdProvincia().toString(), 2, '0')
                    + Utiles.padLeft(direUnid.getDistrito().getIdDistrito().toString(), 2, '0')
            ));
            //RSIS Ajustes - mdiosesf - Fin
            
            direccion1.setEstado(Constantes.ESTADO_SIGED_ACTIVO);//A: Activo, N: No Activo	

            List<DireccionxClienteInRO> listaDirCliente1 = new ArrayList<DireccionxClienteInRO>();
            listaDirCliente1.add(direccion1);
            DireccionxClienteListInRO direccionesCliente1 = new DireccionxClienteListInRO();
            direccionesCliente1.setDireccion(listaDirCliente1);
            cliente1.setDirecciones(direccionesCliente1);

            List<ClienteInRO> listaClientes = new ArrayList<ClienteInRO>();
            listaClientes.add(cliente1);
            ClienteListInRO clientes = new ClienteListInRO();
            clientes.setCliente(listaClientes);
            documentoInRO.setClientes(clientes);

            expedienteInRO.setDocumento(documentoInRO);
        }catch(Exception e){
            LOG.error("Error en armarExpedienteInRoEnvioDocSiged",e);
        }
        return expedienteInRO;
    }
    
    /* OSINE791 RSIS42 - Inicio */
    private String getInfracciones(Long idSupervision,Long codigoResultadoSupervision){
        String concatIdDetSup="";
        try{
            List<DetalleSupervisionDTO> lstDetSup=detalleSupervisionDAO.find(new DetalleSupervisionFilter(idSupervision,codigoResultadoSupervision, null));
            if(!CollectionUtils.isEmpty(lstDetSup)){
                for(DetalleSupervisionDTO reg : lstDetSup){
                    concatIdDetSup=concatIdDetSup+reg.getIdDetalleSupervision()+",";
                }
                concatIdDetSup=concatIdDetSup.substring(0, concatIdDetSup.length()-1);                
            }
        }catch(Exception e){
            LOG.error("enviaDocumentosDsrSiged",e);
        }
        return concatIdDetSup;
    }
    
     /* OSINE791 RSIS42 - Fin */
    
    private String getConcatIdDetalleSupervision(Long idSupervision){
        String concatIdDetSup="";
        try{
            List<DetalleSupervisionDTO> lstDetSup=detalleSupervisionDAO.find(new DetalleSupervisionFilter(idSupervision, null));
            if(!CollectionUtils.isEmpty(lstDetSup)){
                for(DetalleSupervisionDTO reg : lstDetSup){
                    concatIdDetSup=concatIdDetSup+reg.getIdDetalleSupervision()+",";
                }
                concatIdDetSup=concatIdDetSup.substring(0, concatIdDetSup.length()-1);                
            }
        }catch(Exception e){
            LOG.error("enviaDocumentosDsrSiged",e);
        }
        return concatIdDetSup;
    }
    /* OSINE791 RSIS20 - Fin */

    /* OSINE_SFS-791 - RSIS 33 - Inicio */
    @Override
	@Transactional(rollbackFor=OrdenServicioException.class)
	public OrdenServicioDTO actualizar(OrdenServicioFilter filtro, UsuarioDTO usuarioDTO) throws OrdenServicioException {
    	LOG.info("inicio actualizar");
    	OrdenServicioDTO ordenServicio=null;
		try {
			ordenServicio=ordenServicioDAO.actualizar(filtro, usuarioDTO);			
			LOG.info("fin actualizar");
		} catch(Exception e){
			LOG.error("error actualizar", e);
			throw new OrdenServicioException(e);
		}
		return ordenServicio;
	}

    @Override
	@Transactional(rollbackFor=OrdenServicioException.class)
	public OrdenServicioDTO confirmarOrdenServicioLevantamiento(OrdenServicioFilter filtro, UbigeoDTO ubigoDTO, UsuarioDTO usuarioDTO, Long idPersonal, Long idPersonalSiged) throws OrdenServicioException {
		LOG.info("inicio confirmarOrdenServicioLevantamiento");
    	OrdenServicioDTO ordenServicio=null;
    	List<OrdenServicioDTO> ordenServicioList=new ArrayList<OrdenServicioDTO>();
		try {
			//Actualizar Tipo de Asignación (Si Confirmado, No Confirmado) Orden de Levantamiento
			ordenServicio=actualizar(filtro, usuarioDTO);
			if(ordenServicio!=null && !filtro.getFlagConfirmaTipoAsignacion().equals(Constantes.ESTADO_ACTIVO)){
				//No confirma
				List<MaestroColumnaDTO> maestroPeticionList=maestroColumnaDAO.findMaestroColumna(Constantes.DOMINIO_TIPO_PETICION,Constantes.APLICACION_INPS,Constantes.CODIGO_TIPO_PETICION_MOTIVO_EDITAR);
				List<MaestroColumnaDTO> maestroMotivoList=maestroColumnaDAO.findMaestroColumna(Constantes.DOMINIO_MOTIVO_EDITAR,Constantes.APLICACION_INPS,Constantes.CODIGO_MOTIVO_EDITAR_CAMBIAR_ASIGNACION);
				String comentarioDevolucion="ACTUALIZAR EL TIPO DE ASIGNACION, NO CORRESPONDE EL TIPO DE ASIGNACION SOLICITADO, DEBERIA ASIGNARSE "+filtro.getDescTipoAsignacion();
				if(maestroPeticionList!=null && maestroPeticionList.size()!=0 && maestroMotivoList!=null && maestroMotivoList.size()!=0){
					ordenServicioList.add(ordenServicio);
					//Devolver OrdenServicio
					devolverOrdenServicioSupe(ordenServicioList, idPersonal, idPersonalSiged, maestroPeticionList.get(0).getIdMaestroColumna(), maestroMotivoList.get(0).getIdMaestroColumna(), comentarioDevolucion, usuarioDTO);
					//Envio de correo > NOTIFICACION: SOLICITA CAMBIAR TIPO DE ASIGNACION
                    ordenServicio.setDescTipoAsignacion(filtro.getDescTipoAsignacion());
					EnvioNotificacionesSupervisionDsrTipoAsignacion(ubigoDTO, ordenServicio);
				}
			}
		} catch(Exception e){
			LOG.error("error confirmarOrdenServicioLevantamiento", e);
			throw new OrdenServicioException(e.getMessage(),e);
		}
		return ordenServicio;
	}
	
	public String EnvioNotificacionesSupervisionDsrTipoAsignacion(UbigeoDTO ubigeo, OrdenServicioDTO ordenServicioDTO) throws OrdenServicioException {
		//SOLICITA CAMBIAR TIPO DE ASIGNACION
        LOG.info("EnvioNotificacionesSupervisionDsrTipoAsignacion - inicio");
        String msjeCorreo = "";
        try {
            //Envio de correo para Cambio Tipo Asignacion
            DestinatarioCorreoFilter filtro = new DestinatarioCorreoFilter();
            filtro.setCodigoFuncionalidadCorreo(Constantes.CODIGO_FUNCIONALIDAD_TF010);
            filtro.setIdDepartamento(ubigeo.getIdDepartamento());
            filtro.setIdProvincia(ubigeo.getIdProvincia());
            filtro.setIdDistrito(ubigeo.getIdDistrito());
            List<DestinatarioCorreoDTO> milistaDestinos = destinatarioCorreoDAO.getDestinatarioCorreobyUbigeo(filtro);
            if(CollectionUtils.isEmpty(milistaDestinos)){
                throw new OrdenServicioException("Error, No existen destinatarios de Correo.",null);
            }else {
                if(ordenServicioDTO.getExpediente()!=null & ordenServicioDTO.getExpediente()!=null){
	            ExpedienteDTO expedienteDTO = new ExpedienteDTO();            
	            expedienteDTO.setIdExpediente(ordenServicioDTO.getExpediente().getIdExpediente());
	            expedienteDTO.setNumeroExpediente(ordenServicioDTO.getExpediente().getNumeroExpediente());
	            expedienteDTO.setOrdenServicio(ordenServicioDTO);
	            boolean rptaCorreo = correoServiceNeg.enviarCorreoNotificacionTipoAsignacion(milistaDestinos, expedienteDTO);
	            if (!rptaCorreo) {
	                msjeCorreo = "No envio correo Tipo Asignacion.";	     
	                LOG.info(msjeCorreo);
	            }
        	}
            }
        } catch (Exception e) {
            LOG.error("Error en EnvioNotificacionesSupervisionDsrTipoAsignacion", e);
			throw new OrdenServicioException(e.getMessage(),e);
        }
        LOG.info("EnvioNotificacionesSupervisionDsrTipoAsignacion - fin");
        return msjeCorreo;
    }

	@Override
	@Transactional(readOnly=true)
	public List<OrdenServicioDTO> findByFilter(OrdenServicioFilter filtro) throws OrdenServicioException {
		LOG.info("inicio findByFilter");
		List<OrdenServicioDTO> ordenServicioList=null;
		try {
			ordenServicioList=ordenServicioDAO.findByFilter(filtro);			
			LOG.info("fin findByFilter");
		} catch(Exception e){
			LOG.error("error findByFilter", e);
			throw new OrdenServicioException(e);
		}
		return ordenServicioList;
	}
	/* OSINE_SFS-791 - RSIS 33 - Fin */
}
