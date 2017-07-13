/**
* Resumen		
* Objeto			: OrdenServicioGSMServiceNegImpl.java
* Descripción		: Clase de la capa de negocio que contiene la implementación de los métodos declarados en la clase interfaz OrdenServicioServiceNeg
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-1344
* Autor				: Giancarlo Villanueva.
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
* 
*/

package gob.osinergmin.inpsweb.gsm.service.business.impl;

import gob.osinergmin.inpsweb.gsm.service.business.OrdenServicioGSMServiceNeg;
import gob.osinergmin.inpsweb.gsm.service.dao.OrdenServicioGSMDAO;
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
import gob.osinergmin.mdicommon.domain.dto.ActividadDTO;
import gob.osinergmin.mdicommon.domain.dto.DestinatarioCorreoDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.DireccionUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.DmTitularMineroDTO;
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
import gob.osinergmin.mdicommon.domain.ui.DmTitularMineroFilter;
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

@Service("ordenServicioGSMServiceNeg")
public class OrdenServicioGSMServiceNegImpl implements OrdenServicioGSMServiceNeg{
    private static final Logger LOG = LoggerFactory.getLogger(OrdenServicioGSMServiceNegImpl.class);    
    
    @Value("${alfresco.host}")
    private String HOST;
    
    @Inject
    private EstadoProcesoDAO estadoProcesoDAO;
    @Inject
    private OrdenServicioGSMDAO ordenServicioGSMDAO;
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
    
    @Inject 
    private ActividadDAO actividadDAO;  
    @Inject  
    private DireccionUnidadSupervisadaServiceNeg direccionUnidadSupervisadaNeg;
    @Inject
    private UnidadSupervisadaServiceNeg unidadSupervisadaServiceNeg;  
    @Inject
    private MaestroColumnaServiceNeg maestroColumnaServiceNeg; 
    
    @Inject
    private ExpedienteServiceNeg expedienteServiceNeg; 
    
    @Inject
    private DetalleSupervisionDAO detalleSupervisionDAO; 
    @Inject 
    private EmpresaSupervisadaServiceNeg empresaSupervisadaServiceNeg;
    @Inject 
    private MaestroColumnaDAO maestroColumnaDAO;
    @Inject
    private PghDocumentoAdjuntoDAO pghDocumentoAdjuntoDAO;
    
    @Inject
    private DestinatarioCorreoDAO destinatarioCorreoDAO;
    @Inject
    private CorreoServiceNeg correoServiceNeg;    
    
    /* OSINE_SFS-1344 - Inicio */ 
    @Override
    @Transactional(readOnly = true)
    public List<DmTitularMineroDTO> listarTitularMinero(DmTitularMineroFilter filtro){
        LOG.info("listarTitularMinero");
        List<DmTitularMineroDTO> retorno=null;
        try{
            retorno = ordenServicioGSMDAO.listarTitularMinero(filtro);
        }catch(Exception ex){
            LOG.error("Error en listarTitularMinero",ex);
        }
        return retorno;
    } 
    /* OSINE_SFS-1344 - Fin */ 
    
    /*@Override
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
            
            retorno=ordenServicioDAO.cambiarEstadoProceso(idOrdenServicio,idPersonalOri,expedientePadre.getPersonal().getIdPersonal(),estadoProcesoDto.getIdEstadoProceso(),null,usuarioDTO,null,null);
            
        }catch(Exception e){
            LOG.error("Error atender",e);
            throw new OrdenServicioException(e.getMessage(),e);
        }
        return retorno;
    }
    
    @Override
    @Transactional
    public OrdenServicioDTO revisar(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,Long idPersonalDest,UsuarioDTO usuarioDTO) throws OrdenServicioException{	
    
        LOG.info("revisar");
        OrdenServicioDTO retorno=new OrdenServicioDTO();
        
        try{
            EstadoProcesoDTO estadoProcesoDto=estadoProcesoDAO.find(new EstadoProcesoFilter(Constantes.IDENTIFICADOR_ESTADO_PROCESO_OS_REVISADO)).get(0);
            ExpedienteDTO expedientePadre=expedienteDAO.findByFilter(new ExpedienteFilter(idExpediente)).get(0);
            if(expedientePadre==null || expedientePadre.getPersonal()==null || expedientePadre.getPersonal().getIdPersonal()==null){
                throw new OrdenServicioException("La Orden de Servicio no tiene Expediente relacionado",null);
            }
            
            retorno=ordenServicioDAO.cambiarEstadoProceso(idOrdenServicio,idPersonalOri,idPersonalDest,estadoProcesoDto.getIdEstadoProceso(),null,usuarioDTO,null,null);
            
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
            
            retorno=ordenServicioDAO.cambiarEstadoProceso(idOrdenServicio,idPersonalOri,expedientePadre.getPersonal().getIdPersonal(),estadoProcesoDto.getIdEstadoProceso(),null,usuarioDTO,null,null);
            
        }catch(Exception e){
            LOG.error("Error aprobar",e);
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
            
            retorno=ordenServicioDAO.cambiarEstadoProceso(idOrdenServicio,idPersonalOri,personaOS.getIdPersonal(),estadoProcesoDto.getIdEstadoProceso(),motivoReasignacion,usuarioDTO,null,null);
            
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
            
            retorno=ordenServicioDAO.cambiarEstadoProceso(idOrdenServicio,idPersonalOri,expedientePadre.getPersonal().getIdPersonal(),estadoProcesoDto.getIdEstadoProceso(),motivoReasignacion,usuarioDTO,null,null);
            
        }catch(Exception e){
            LOG.error("Error observar",e);
            throw new OrdenServicioException(e.getMessage(),e);
        }
        return retorno;
    }
    
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
		    				System.out.println("cadCodigosTipoDireccion " + cadCodigosTipoDireccion);
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
    
    
    */
}
