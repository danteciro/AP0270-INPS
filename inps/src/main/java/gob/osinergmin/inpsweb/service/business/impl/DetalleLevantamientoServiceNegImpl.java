/**
* Resumen		
* Objeto			: DetalleLevantamientoServiceNegImpl.java
* Descripción		: Clase ServiceImp DetalleLevantamientoServiceNegImpl
* Fecha de Creación	: 17/08/2016
* PR de Creación	: OSINE_SFS-598 - RSIS 26
* Autor				: mdiosesf
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------				
* OSINE_SFS-791     07/10/2016      Mario Dioses Fernandez      Crear la funcionalidad "verificar levantamientos" que permita verificar el tipo de asignación para la orden de levantamiento DSR-CRITICIDAD.
* OSINE_SFS-791-RSIS48  |   21/10/2016   |   Luis García Reyna   |  Registrar Detalle Levantamiento
*/ 
package gob.osinergmin.inpsweb.service.business.impl;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import gob.osinergmin.inpsweb.service.business.DetalleLevantamientoServiceNeg;
import gob.osinergmin.inpsweb.service.business.DetalleSupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.dao.DetalleLevantamientoDAO;
import gob.osinergmin.inpsweb.service.dao.OrdenServicioDAO;
import gob.osinergmin.inpsweb.service.dao.SupervisionDAO;
import gob.osinergmin.inpsweb.service.exception.DetalleLevantamientoException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.DetalleLevantamientoDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.OrdenServicioDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.DetalleLevantamientoFilter;
import gob.osinergmin.mdicommon.domain.ui.DetalleSupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.OrdenServicioFilter;
import gob.osinergmin.mdicommon.domain.ui.SupervisionFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("DetalleLevantamientoServiceNegImpl")
public class DetalleLevantamientoServiceNegImpl implements DetalleLevantamientoServiceNeg {
	private static final Logger LOG = LoggerFactory.getLogger(DetalleLevantamientoServiceNegImpl.class);    
    @Inject
    private DetalleLevantamientoDAO detalleLevantamientoDAO;
    
    /* OSINE_SFS-791 - RSIS 48 - Inicio */
    @Inject
    private OrdenServicioDAO ordenServicioDAO;
    @Inject
    private SupervisionDAO supervisionDAO;
    @Inject
    private DetalleSupervisionServiceNeg detalleSupervisionServiceNeg;
    /* OSINE_SFS-791 - RSIS 48 - Fin */
    
    @Override
	@Transactional(readOnly=true)
	public List<DetalleLevantamientoDTO> find(DetalleLevantamientoFilter filtro) throws DetalleLevantamientoException {
		LOG.info("DetalleLevantamientoServiceImpl--find - inicio");
        List<DetalleLevantamientoDTO> retorno = new ArrayList<DetalleLevantamientoDTO>();
        try{
            retorno=detalleLevantamientoDAO.find(filtro);
        }catch(Exception ex){        	
            LOG.error("Error en find", ex);
            throw new DetalleLevantamientoException(ex);
        }
        return retorno;
	}

	@Override
	@Transactional(rollbackFor=DetalleLevantamientoException.class)
	public DetalleLevantamientoDTO registrarDetalleLevantamiento(DetalleLevantamientoDTO detalleLevantamiento, UsuarioDTO UsuarioDTO) throws DetalleLevantamientoException {
		LOG.info("DetalleLevantamientoServiceImpl--registrarDetalleLevantamiento - inicio");
		DetalleLevantamientoDTO retorno = null;
        try{
            retorno=detalleLevantamientoDAO.registrar(detalleLevantamiento, UsuarioDTO);
        }catch(Exception ex){
            LOG.error("Error en registrarDetalleLevantamiento", ex);
            throw new DetalleLevantamientoException(ex);
        }
        return retorno;
	}
	
	@Override
	@Transactional(rollbackFor=DetalleLevantamientoException.class)
	public DetalleLevantamientoDTO actualizarDetalleLevantamiento(DetalleLevantamientoDTO detalleLevantamiento, UsuarioDTO UsuarioDTO) throws DetalleLevantamientoException {
		LOG.info("DetalleLevantamientoServiceImpl--actualizarDetalleLevantamiento - inicio");
		DetalleLevantamientoDTO retorno = null;
        try{
            retorno=detalleLevantamientoDAO.actualizar(detalleLevantamiento, UsuarioDTO);
        }catch(Exception ex){
            LOG.error("Error en actualizarDetalleLevantamiento", ex);
            throw new DetalleLevantamientoException(ex);
        }
        return retorno;
	}
    /* OSINE_SFS-791 - RSIS 48 - Inicio */    
    @Override
    @Transactional(rollbackFor = DetalleLevantamientoException.class)
    public void guardarDetalleLevantamiento(ExpedienteDTO expedienteDTO, HttpSession session, HttpServletRequest request) throws DetalleLevantamientoException {
        LOG.info("DetalleLevantamientoServiceImpl--guardarDetalleLevantamiento - inicio");
        
        OrdenServicioDTO ordenServicioPadre = null;
        SupervisionDTO supervisionPadre = null;
        
        try {
            //Buscamos listaOrdenServicio asociadas al expediente
            List<OrdenServicioDTO> listaOrdenServicio = ordenServicioDAO.find(new OrdenServicioFilter(expedienteDTO.getIdExpediente(),new Long(Constantes.ESTADO_ACTIVO)));
            LOG.info("...:::...listaOrdenServicio..:::..."+listaOrdenServicio.size());   
                if(!listaOrdenServicio.isEmpty()){
                    //Obtenemos la ordenServicioPadre
                    ordenServicioPadre = listaOrdenServicio.get(Constantes.PRIMERO_EN_LISTA);
                    //Obtenenemos la supervisionPadre por idOrdenServicio
                    List<SupervisionDTO> listaSupervisionPadre = supervisionDAO.find(new SupervisionFilter(null,ordenServicioPadre.getIdOrdenServicio()));
                        if (!listaSupervisionPadre.isEmpty()) {
                            supervisionPadre = listaSupervisionPadre.get(Constantes.PRIMERO_EN_LISTA);
                            //Buscamos lista detalleSupervision cuyo idResultadoSupervision es Incumple y idDetalleSupervisionSubsana es null
                            DetalleSupervisionFilter filtro= new DetalleSupervisionFilter();
                            filtro.setCodigoResultadoSupervision(Constantes.CODIGO_RESULTADO_INCUMPLE);
                            filtro.setIdSupervision(supervisionPadre.getIdSupervision());
                            filtro.setFlgBuscaDetaSupSubsanado(Constantes.ESTADO_INACTIVO);
                            List<DetalleSupervisionDTO> listaDetalleSupervisionPadre=detalleSupervisionServiceNeg.listarDetalleSupervision(filtro);
                            LOG.info("..::..listaDetalleSupervisionPadre..::.. "+listaDetalleSupervisionPadre.size());
                            
                            //Buscamos listaDetalleLevantamiento para actualizar el flagUltimoRegistro='0'                           
                            for (int i = 0; i < listaDetalleSupervisionPadre.size(); i++) {
                                DetalleLevantamientoFilter filtroDetaLeva = new DetalleLevantamientoFilter();
                                filtroDetaLeva.setIdDetalleSupervision(listaDetalleSupervisionPadre.get(i).getIdDetalleSupervision());
                                List<DetalleLevantamientoDTO> listaDetalleLevantamiento = detalleLevantamientoDAO.find(filtroDetaLeva);
                                LOG.info("..::..listaDetalleLevantamiento..::.."+listaDetalleLevantamiento.size()); 
                                
                                if(listaDetalleLevantamiento!=null && listaDetalleLevantamiento.size()>0){
                                    for(DetalleLevantamientoDTO detalleLevantamientoDTO:listaDetalleLevantamiento ){
                                        detalleLevantamientoDTO.setFlagUltimoRegistro(Constantes.ESTADO_INACTIVO);
                                        detalleLevantamientoDAO.actualizar(detalleLevantamientoDTO, Constantes.getUsuarioDTO(request)); 
                                    }
                                }
                            }
                            
                            //Registramos DetalleLevantamiento para subSanadoNo con flagUltimoRegistro='1' 
                            for (DetalleSupervisionDTO detalleSupervisionDTO : listaDetalleSupervisionPadre) {                                 
                                DetalleLevantamientoDTO detalleLevantamientoDTO = new DetalleLevantamientoDTO();
                                detalleLevantamientoDTO.setIdDetalleSupervision(detalleSupervisionDTO);
                                detalleLevantamientoDTO.setEstado(Constantes.ESTADO_ACTIVO);
                                detalleLevantamientoDTO.setFlagUltimoRegistro(Constantes.ESTADO_ACTIVO);
                                detalleLevantamientoDAO.registrar(detalleLevantamientoDTO, Constantes.getUsuarioDTO(request));                                
                            }
                           /*  
                            //Registro Historico con EstadoLevantamiento "Pendiente"
                            HstEstadoLevantamientoDTO hstEstadoLevantamientoDTO = new HstEstadoLevantamientoDTO();
                            hstEstadoLevantamientoDTO.setEstado(Constantes.ESTADO_ACTIVO);
                            List<MaestroColumnaDTO> estadoLevantamiento = maestroColumnaDAO.findMaestroColumnaByCodigo(Constantes.DOMINIO_ESTADO_LEVANTAMIENTO,Constantes.APLICACION_INPS, Constantes.CODIGO_ESTADO_LEVANTAMIENTO_PENDIENTE);
                            Long idEstadoLevantamientoPendiente = null;
                            if(!CollectionUtils.isEmpty(estadoLevantamiento)){
                                idEstadoLevantamientoPendiente=estadoLevantamiento.get(0).getIdMaestroColumna();
                            }
                            hstEstadoLevantamientoDTO.setIdEstado(idEstadoLevantamientoPendiente);
                            hstEstadoLevantamientoDTO.setExpedienteDTO(expedienteDTO);
                            hstEstadoLevantamientoServiceNeg.registrarHstEstadoLevantamiento(hstEstadoLevantamientoDTO, Constantes.getUsuarioDTO(request));

                            //Actualizamos El Expediente Con El Estado Levantamiento "Pendiente"
                            ExpedienteDTO expedienteDTOAct= new ExpedienteDTO();
                            expedienteDTOAct.setIdExpediente(expedienteDTO.getIdExpediente());
                            expedienteDTOAct.setEstadoLevantamiento(estadoLevantamiento.get(0));
                            expedienteDAO.actualizarExpediente(expedienteDTOAct,Constantes.getUsuarioDTO(request));     
                            */                     
                        }                  
                }    
        } catch (Exception ex) {
            LOG.error("Error en guardarDetalleLevantamiento", ex);
            throw new DetalleLevantamientoException(ex);
        }
    }
    /* OSINE_SFS-791 - RSIS 48 - Fin */
}
