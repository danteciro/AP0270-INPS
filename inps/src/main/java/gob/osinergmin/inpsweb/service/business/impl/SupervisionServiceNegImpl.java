/**
* Resumen		
* Objeto		: SupervisionServiceNegImpl.java
* Descripción		: Clase de la capa de negocio que contiene la implementación de los métodos declarados en la clase interfaz SupervisionServiceNeg
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-791
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE791              |  17/08/2016   |   Yadira Piskulich           |     Abrir Supervision DSR
* OSINE_SFS-791         |  22/08/2016   |   Luis García Reyna          |     Crear la funcionalidad para registrar otros incumplimientos
* OSINE_SFS-791         |  29/08/2016   |   Luis García Reyna          |     Crear la funcionalidad para registrar Ejecucion Medida Supervision
* OSINE791-RSIS04       |  05/10/2016   |   Zosimo Chaupis Santur      |     CREAR LA FUNCIONALIDAD REGISTRAR SUPERVISION DE UNA ORDEN DE SUPERVISION DSR-CRITICIDAD DE LA CASUISTICA SUPERVISION NO INICIADA                      |
* OSINE_SFS-791         |  06/10/2016   |   Luis García Reyna          |     Registrar Supervisión No Iniciada   
* OSINE_SFS-791_RSIS_49 |  24/10/2016   |   Luis García Reyna          |     Registrar idDetaLevaAtiende en DetalleLevantamiento 
* OSINE_SFS-791     	   23/10/2016       Mario Dioses Fernandez           Crear la tarea automática que notifique vía correo que se debe elaborar el oficio por obligaciones incumplidas sin subsanar
* OSINE_SFS-791     	   23/10/2016       Mario Dioses Fernandez           Crear la tarea automática que cancele el registro de hidrocarburos    
* OSINE791–RSIS20       | 26/10/2016    |    Cristopher Paucar Torre   |     Validación en Registro de Supervsión por verficar y No.
* OSINE_791-069         | 02/11/2016    |   Zosimo Chaupis Santur      |     Implementar la funcionalidad para guardar el valor del campo Resultado de supervisión (REQF-0068) al registrar los datos de supervisión en la interfaz Registrar Supervisión.
* OSINE_MAN-001  	    |  15/06/2017   |   Claudio Chaucca Umana      |     Realizar la validacion de existencia de cambios desde la interface de usuario 
*/

package gob.osinergmin.inpsweb.service.business.impl;
import gob.osinergmin.inpsweb.service.business.InfraccionServiceNeg;
import gob.osinergmin.inpsweb.service.business.SupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.dao.ConfObligacionDAO;
import gob.osinergmin.inpsweb.service.dao.CriterioDAO;
import gob.osinergmin.inpsweb.service.dao.DetalleCriterioDAO;
import gob.osinergmin.inpsweb.service.dao.DetalleEvaluacionDAO;
import gob.osinergmin.inpsweb.service.dao.DetalleLevantamientoDAO;
import gob.osinergmin.inpsweb.service.dao.DetalleSupervisionDAO;
import gob.osinergmin.inpsweb.service.dao.ExpedienteDAO;
import gob.osinergmin.inpsweb.service.dao.MaestroColumnaDAO;
import gob.osinergmin.inpsweb.service.dao.MotivoNoSupervisionDAO;
import gob.osinergmin.inpsweb.service.dao.ObligacionDAO;
import gob.osinergmin.inpsweb.service.dao.OrdenServicioDAO;
import gob.osinergmin.inpsweb.service.dao.PersonaGeneralDAO;
import gob.osinergmin.inpsweb.service.dao.PghDocumentoAdjuntoDAO;
import gob.osinergmin.inpsweb.service.dao.PlantillaResultadoDAO;
import gob.osinergmin.inpsweb.service.dao.ProcesoObligacionTipoDAO;
import gob.osinergmin.inpsweb.service.dao.ResultadoSupervisionDAO;
import gob.osinergmin.inpsweb.service.dao.SupervisionDAO;
import gob.osinergmin.inpsweb.service.dao.SupervisionPersonaGralDAO;
import gob.osinergmin.inpsweb.service.dao.TipificacionDAO;
import gob.osinergmin.inpsweb.service.dao.TipificacionSancionDAO;
import gob.osinergmin.inpsweb.service.exception.DetalleSupervisionException;
import gob.osinergmin.inpsweb.service.exception.SupervisionException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.Utiles;
import gob.osinergmin.mdicommon.domain.dto.ConfObligacionDTO;
import gob.osinergmin.mdicommon.domain.dto.CriterioDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleCriterioDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleEvaluacionDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleLevantamientoDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.InfraccionDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.MotivoNoSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.ObligacionDTO;
import gob.osinergmin.mdicommon.domain.dto.OrdenServicioDTO;
import gob.osinergmin.mdicommon.domain.dto.PersonaGeneralDTO;
import gob.osinergmin.mdicommon.domain.dto.PlantillaResultadoDTO;
import gob.osinergmin.mdicommon.domain.dto.ResultadoSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionPersonaGralDTO;
import gob.osinergmin.mdicommon.domain.dto.TipificacionDTO;
import gob.osinergmin.mdicommon.domain.dto.TipificacionSancionDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.ConfObligacionFilter;
import gob.osinergmin.mdicommon.domain.ui.DetalleEvaluacionFilter;
import gob.osinergmin.mdicommon.domain.ui.DetalleLevantamientoFilter;
import gob.osinergmin.mdicommon.domain.ui.DetalleSupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.DocumentoAdjuntoFilter;
import gob.osinergmin.mdicommon.domain.ui.InfraccionFilter;
import gob.osinergmin.mdicommon.domain.ui.MotivoNoSupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.OrdenServicioFilter;
import gob.osinergmin.mdicommon.domain.ui.PersonaGeneralFilter;
import gob.osinergmin.mdicommon.domain.ui.PlantillaResultadoFilter;
import gob.osinergmin.mdicommon.domain.ui.ResultadoSupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.SupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.SupervisionPersonaGralFilter;
import gob.osinergmin.mdicommon.domain.ui.TipificacionFilter;
import gob.osinergmin.mdicommon.domain.ui.TipificacionSancionFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.apache.cxf.common.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("supervisionServiceNeg")
public class SupervisionServiceNegImpl implements SupervisionServiceNeg {

    private static final Logger LOG = LoggerFactory.getLogger(SupervisionServiceNegImpl.class);
    @Inject
    private SupervisionDAO supervisionDAO;
    @Inject
    private DetalleSupervisionDAO detalleSupervisionDAO;
    @Inject
    private ConfObligacionDAO confObligacionDAO;
    @Inject
    private ObligacionDAO obligacionDAO;
    @Inject
    private TipificacionDAO tipificacionDAO;
    @Inject
    private ProcesoObligacionTipoDAO procesoObligacionTipoDAO;
    @Inject
    private CriterioDAO criterioDAO;
    @Inject
    private DetalleCriterioDAO detalleCriterioDAO;
    @Inject
    private SupervisionPersonaGralDAO superPersonaGralDAO;
    @Inject
    private PersonaGeneralDAO personaGeneralDAO;
    @Inject
    private PlantillaResultadoDAO plantillaResultadoDAO;
    @Inject
    private OrdenServicioDAO ordenServicioDAO;
    @Inject
    private TipificacionSancionDAO tipificacionSancionDAO;
    @Inject
    private MaestroColumnaDAO maestroColumnaDAO;
    @Inject
    private MotivoNoSupervisionDAO motivoNoSupervisionDAO;
    @Inject
    private ExpedienteDAO expedienteDAO;
    @Inject
    private PghDocumentoAdjuntoDAO pghDocumentoAdjuntoDAO;
    @Inject
    private DetalleEvaluacionDAO detalleEvaluacionDAO; //mdiosesf - RSIS6
    @Inject
    private ResultadoSupervisionDAO resultadoSupervisionDAO;    
    @Inject
    private InfraccionServiceNeg infraccionServiceNeg;
    
    /*OSINE_SFS-791 - RSIS 49 - Inicio */
    @Inject
    private DetalleLevantamientoDAO detalleLevantamientoDAO;    
    /*OSINE_SFS-791 - RSIS 49 - Fin */
    
    @Override
    @Transactional
    public List<SupervisionDTO> buscarSupervision(SupervisionFilter filtro) throws SupervisionException {
        LOG.info("SupervisionServiceNegImpl--buscarSupervision - inicio");
        List<SupervisionDTO> retorno = null;
        try {
            retorno = supervisionDAO.find(filtro);
        } catch (Exception ex) {
            LOG.error("Error en buscarSupervision", ex);
            throw new  SupervisionException(ex.getMessage(),ex);
        }
        LOG.info("SupervisionServiceNegImpl--buscarSupervision - fin");
        return retorno;
    }

    @Override
    @Transactional(rollbackFor = SupervisionException.class)
    public SupervisionDTO registrarSupervision(SupervisionDTO supervisionDTO,
            UsuarioDTO usuarioDTO) throws SupervisionException {
        LOG.info("SupervisionServiceNegImpl--registrarSupervision - inicio");
        SupervisionDTO retorno = null;
        try {
            retorno = supervisionDAO.registrarSupervision(supervisionDTO, usuarioDTO);
        } catch (Exception ex) {
            LOG.error("Error en registrarSupervision", ex);
        }
        LOG.info("SupervisionServiceNegImpl--registrarSupervision - fin");
        return retorno;
    }

    @Override
    @Transactional(rollbackFor=SupervisionException.class)
    public SupervisionDTO registrarSupervisionBloque(SupervisionDTO supervisionDTO,
        UsuarioDTO usuarioDTO)throws SupervisionException {
        LOG.info("SupervisionServiceNegImpl--registrarSupervisionBloque - inicio");
        SupervisionDTO registroSupervision = null;
        List<OrdenServicioDTO> listaOrden = null;
        OrdenServicioDTO ordenServicioAnt=null;
        List<SupervisionDTO> listaSupervision = null;
        SupervisionDTO supervisionAnt = null;
        try {
            if(supervisionDTO.getOrdenServicioDTO()!=null){
                registroSupervision = new SupervisionDTO();
                if(supervisionDTO.getIdSupervision() != null){
                   registroSupervision.setIdSupervision(supervisionDTO.getIdSupervision()); 
                }
                registroSupervision.setOrdenServicioDTO(new OrdenServicioDTO(supervisionDTO.getOrdenServicioDTO().getIdOrdenServicio()));
                OrdenServicioFilter ordenServicioFilter= new OrdenServicioFilter(supervisionDTO.getOrdenServicioDTO().getIdOrdenServicio());
                List<OrdenServicioDTO> listaOrdenServicio = ordenServicioDAO.findByFilter(ordenServicioFilter);
                if(!listaOrdenServicio.isEmpty()){
                    supervisionDTO.setOrdenServicioDTO(listaOrdenServicio.get(Constantes.PRIMERO_EN_LISTA));
                    if(supervisionDTO.getOrdenServicioDTO().getIteracion()>Constantes.SUPERVISION_PRIMERA_ITERACION){
                        //obtenemos la orden servicio anterior
                        OrdenServicioFilter filtro = new OrdenServicioFilter();
                        Long iteracion = (supervisionDTO.getOrdenServicioDTO().getIteracion()-1);
                        filtro.setIdExpediente(supervisionDTO.getOrdenServicioDTO().getExpediente().getIdExpediente());
                        filtro.setIteracion(iteracion);
                        listaOrden =ordenServicioDAO.find(filtro);
                        if(!listaOrden.isEmpty()){
                                ordenServicioAnt = listaOrden.get(Constantes.PRIMERO_EN_LISTA);
                                SupervisionFilter filtroSupervision = new SupervisionFilter();
                                filtroSupervision.setIdOrdenServicio(ordenServicioAnt.getIdOrdenServicio());
                                listaSupervision = supervisionDAO.find(filtroSupervision);
                                if(!listaSupervision.isEmpty()){
                                        supervisionAnt = listaSupervision.get(Constantes.PRIMERO_EN_LISTA);
                                        registroSupervision.setSupervisionAnterior(new SupervisionDTO(supervisionAnt.getIdSupervision()));
                                }
                        }
                    }
                    if(supervisionDTO.getOrdenServicioDTO().getIteracion()==Constantes.SUPERVISION_PRIMERA_ITERACION){
                        registroSupervision.setFlagSupervision(Constantes.FLAG_SUPERVISION);
                        if(registroSupervision.getIdSupervision() != null){
                          registroSupervision.setEstado(Constantes.ESTADO_ACTIVO);
                          registroSupervision = supervisionDAO.actualizar(registroSupervision, usuarioDTO);
                        }else{
                          registroSupervision = registrarSupervision(registroSupervision,usuarioDTO);
                        }                        
                        //traemos las obligaciones con el proceso,rubro,obligacion_tipo y las registramos en la tabla detalle_supervision
                        ConfObligacionFilter filtro = new ConfObligacionFilter();
                        filtro.setIdProceso(supervisionDTO.getOrdenServicioDTO().getExpediente().getProceso().getIdProceso());
                        filtro.setIdActividad(supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getActividad().getIdActividad());
                        filtro.setIdObligacionTipo(supervisionDTO.getOrdenServicioDTO().getExpediente().getObligacionTipo().getIdObligacionTipo());
                        List<ConfObligacionDTO> lista = confObligacionDAO.listarConfObligacion(filtro);
                        if(!lista.isEmpty()){
                                for(ConfObligacionDTO registro:lista){
                                        DetalleSupervisionDTO detalleSupervisionDTO = new DetalleSupervisionDTO();
                                        detalleSupervisionDTO.setSupervision(new SupervisionDTO(registroSupervision.getIdSupervision()));
                                        detalleSupervisionDTO.setObligacion(new ObligacionDTO(registro.getObligacion().getIdObligacion()));
                                        detalleSupervisionDTO.setFlagResultado(Constantes.DETALLE_SUPERVISION_CUMPLE);
                                        detalleSupervisionDTO.setFlagRegistrado(Constantes.FLAG_REGISTRADO_NO);
                                        detalleSupervisionDTO = registrarDetalleSupervision(detalleSupervisionDTO,usuarioDTO);
                                }
                        }
                    }else if(supervisionDTO.getOrdenServicioDTO().getIteracion()>Constantes.SUPERVISION_PRIMERA_ITERACION){
                        //con el expediente buscamos la iteracion anterior en la orden de servicio
                        if(ordenServicioAnt!=null && ordenServicioAnt.getIdOrdenServicio()!=null){
                                registroSupervision.setFlagSupervision(ordenServicioAnt.getFlagPresentoDescargo());
                                if (registroSupervision.getIdSupervision() != null) {
                                registroSupervision.setEstado(Constantes.ESTADO_ACTIVO);
                                registroSupervision = supervisionDAO.actualizar(registroSupervision, usuarioDTO);
                                } else {
                                registroSupervision = registrarSupervision(registroSupervision,usuarioDTO);
                                }                                
                                DetalleSupervisionFilter filtroDetaSuper = new DetalleSupervisionFilter();
                                filtroDetaSuper.setIdOrdenServicio(ordenServicioAnt.getIdOrdenServicio());
                                filtroDetaSuper.setFlagResultado(Constantes.DETALLE_SUPERVISION_INCUMPLE);
                                List<DetalleSupervisionDTO> listaDetaSuper = detalleSupervisionDAO.find(filtroDetaSuper);
                                if(!listaDetaSuper.isEmpty()){
                                        for(DetalleSupervisionDTO registro:listaDetaSuper){
                                                DetalleSupervisionDTO detalleSupervisionDTO = new DetalleSupervisionDTO();
                                                detalleSupervisionDTO.setSupervision(new SupervisionDTO(registroSupervision.getIdSupervision()));
                                                detalleSupervisionDTO.setObligacion(new ObligacionDTO(registro.getObligacion().getIdObligacion()));
                                                if(registro.getTipificacion()!=null && registro.getTipificacion().getIdTipificacion()!=null){
                                                        detalleSupervisionDTO.setTipificacion(new TipificacionDTO(registro.getTipificacion().getIdTipificacion()));
                                                }
                                                if(registro.getCriterio()!=null && registro.getCriterio().getIdCriterio()!=null){
                                                        detalleSupervisionDTO.setCriterio(new CriterioDTO(registro.getCriterio().getIdCriterio()));
                                                }
                                                detalleSupervisionDTO.setFlagRegistrado(Constantes.FLAG_REGISTRADO_NO);
                                                detalleSupervisionDTO.setIdDetalleSupervisionAnt(registro.getIdDetalleSupervision());
                                                detalleSupervisionDTO.setFlagResultado(Constantes.DETALLE_SUPERVISION_SANCIONA);
                                                detalleSupervisionDTO = registrarDetalleSupervision(detalleSupervisionDTO,usuarioDTO);
                                        }
                                }
                        }
                        registroSupervision.setSupervisionAnterior(supervisionAnt);
                    }

                }
            }
        } catch (Exception ex) {
            LOG.error("Error en registrarSupervisionBloque",ex);
            throw new SupervisionException(ex.getMessage(),ex);
        }
        LOG.info("SupervisionServiceNegImpl--registrarSupervisionBloque - fin");
        return registroSupervision;
    }
    
    @Override
    @Transactional(rollbackFor = SupervisionException.class)
    public SupervisionDTO registrarSupervisionBloqueDsr(SupervisionDTO supervisionDTO,
        UsuarioDTO usuarioDTO) throws SupervisionException {
        LOG.info("SupervisionServiceNegImpl--registrarSupervisionBloque - inicio");
        SupervisionDTO registroSupervision = null;
        OrdenServicioDTO ordenServicioPadre = null;
        SupervisionDTO supervisionPadre = null;        
        try {
            if (supervisionDTO.getOrdenServicioDTO() != null) {
                registroSupervision = new SupervisionDTO();
                registroSupervision.setOrdenServicioDTO(new OrdenServicioDTO(supervisionDTO.getOrdenServicioDTO().getIdOrdenServicio()));
                List<OrdenServicioDTO> listaOrdenServicio = ordenServicioDAO.findByFilter(new OrdenServicioFilter(supervisionDTO.getOrdenServicioDTO().getIdOrdenServicio()));
                if (!listaOrdenServicio.isEmpty()) {
                    supervisionDTO.setOrdenServicioDTO(listaOrdenServicio.get(Constantes.PRIMERO_EN_LISTA));
                    //si es mayor a 1ra iteracion
                    if (supervisionDTO.getOrdenServicioDTO().getIteracion() > Constantes.SUPERVISION_PRIMERA_ITERACION) {
                        //obtenemos la ordenServicioPadre
                        List<OrdenServicioDTO> listaOrdenP = ordenServicioDAO.find(new OrdenServicioFilter(supervisionDTO.getOrdenServicioDTO().getExpediente().getIdExpediente(),new Long(1)));
                        if (!listaOrdenP.isEmpty()) {
                            ordenServicioPadre = listaOrdenP.get(Constantes.PRIMERO_EN_LISTA);
                            List<SupervisionDTO> listaSupervisionP = supervisionDAO.find(new SupervisionFilter(null,ordenServicioPadre.getIdOrdenServicio()));
                            if (!listaSupervisionP.isEmpty()) {
                                supervisionPadre = listaSupervisionP.get(Constantes.PRIMERO_EN_LISTA);
                                registroSupervision.setSupervisionAnterior(new SupervisionDTO(supervisionPadre.getIdSupervision()));
                            }
                        }
                    }
                    //si es igual a 1ra iteracion
                    if (supervisionDTO.getOrdenServicioDTO().getIteracion() == Constantes.SUPERVISION_PRIMERA_ITERACION) {
                        registroSupervision.setFlagSupervision(Constantes.FLAG_SUPERVISION);
                        ResultadoSupervisionDTO resultadoInicial = new ResultadoSupervisionDTO(); 
                        if(supervisionDTO.getResultadoSupervisionInicialDTO() != null){
                            if(supervisionDTO.getResultadoSupervisionInicialDTO().getIdResultadosupervision() != null){
                                resultadoInicial.setIdResultadosupervision(supervisionDTO.getResultadoSupervisionInicialDTO().getIdResultadosupervision());
                            }
                            if(supervisionDTO.getResultadoSupervisionInicialDTO().getCodigo() != null){
                                resultadoInicial.setCodigo(supervisionDTO.getResultadoSupervisionInicialDTO().getCodigo());
                            }
                            registroSupervision.setResultadoSupervisionInicialDTO(resultadoInicial);
                        }
                        if (supervisionDTO.getIdSupervision() != null) {
                            if (Constantes.CODIGO_RESULTADO_SUPERVISION_INICIAL_PORVERIFICAR.equals(resultadoInicial.getCodigo()) || Constantes.CODIGO_RESULTADO_SUPERVISION_INICIAL_OBSTACULIZADO.equals(resultadoInicial.getCodigo())) {
                                registroSupervision.setFechaInicioPorVerificar(supervisionDTO.getFechaInicio());
                            }else{
                                registroSupervision.setFechaInicioPorVerificar("");
                            }
                            registroSupervision.setIdSupervision(supervisionDTO.getIdSupervision());
                            registroSupervision.setEstado(Constantes.ESTADO_ACTIVO);                            
                            registroSupervision = supervisionDAO.actualizar(registroSupervision, usuarioDTO);
                        } else {
                            if (Constantes.CODIGO_RESULTADO_SUPERVISION_INICIAL_PORVERIFICAR.equals(resultadoInicial.getCodigo())) {
                                registroSupervision.setFechaInicioPorVerificar(supervisionDTO.getFechaInicio());
                            }
                            registroSupervision = registrarSupervision(registroSupervision, usuarioDTO);
                        }
                        if (!Constantes.CODIGO_RESULTADO_SUPERVISION_INICIAL_PORVERIFICAR.equals(resultadoInicial.getCodigo()) && !Constantes.CODIGO_RESULTADO_SUPERVISION_INICIAL_OBSTACULIZADO.equals(resultadoInicial.getCodigo())) {
                            //traemos las obligaciones con el proceso,rubro,obligacion_tipo y las registramos en la tabla detalle_supervision
                            ConfObligacionFilter filtro = new ConfObligacionFilter();
                            filtro.setIdProceso(supervisionDTO.getOrdenServicioDTO().getExpediente().getProceso().getIdProceso());
                            filtro.setIdActividad(supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getActividad().getIdActividad());
                            filtro.setIdObligacionTipo(supervisionDTO.getOrdenServicioDTO().getExpediente().getObligacionTipo().getIdObligacionTipo());
                            List<ConfObligacionDTO> lista = confObligacionDAO.listarConfObligacion(filtro);
                            ResultadoSupervisionDTO objetoEstado = resultadoSupervisionDAO.getResultadoSupervision(new ResultadoSupervisionFilter(Constantes.CODIGO_RESULTADO_INICIAL));
                            boolean siRegistro=false;
                            if (!lista.isEmpty()) {
                                int contadorPrioridad = 1;
                                for (ConfObligacionDTO registro : lista) {                                	
                                    List<InfraccionDTO> ltainfraccionDTO = infraccionServiceNeg.getListInfraccion(new InfraccionFilter(registro.getObligacion().getIdObligacion()));
                                    InfraccionDTO infraccionDTO = new InfraccionDTO();
                                    if (!CollectionUtils.isEmpty(ltainfraccionDTO)) {
                                        infraccionDTO = ltainfraccionDTO.get(Constantes.PRIMERO_EN_LISTA);
                                        
	                                    DetalleSupervisionDTO detalleSupervisionDTO = new DetalleSupervisionDTO();
	                                    detalleSupervisionDTO.setSupervision(new SupervisionDTO(registroSupervision.getIdSupervision()));
	                                    detalleSupervisionDTO.setObligacion(new ObligacionDTO(registro.getObligacion().getIdObligacion()));
	                                    //detalleSupervisionDTO.setFlagResultado(Constantes.DETALLE_SUPERVISION_CUMPLE);
	                                    detalleSupervisionDTO.setFlagRegistrado(Constantes.FLAG_REGISTRADO_NO);
	                                    detalleSupervisionDTO.setPrioridad(new Long(contadorPrioridad));
	                                    detalleSupervisionDTO.setResultadoSupervision(objetoEstado);
	                                    detalleSupervisionDTO.setIdMedidaSeguridad(infraccionDTO.getIdmedidaSeguridad());
	                                    detalleSupervisionDTO.setInfraccion(new InfraccionDTO(infraccionDTO.getIdInfraccion()));
	                                    detalleSupervisionDTO = registrarDetalleSupervision(detalleSupervisionDTO, usuarioDTO);
	                                    contadorPrioridad++;
	                                    siRegistro=true;
                                    }                                  
                                }
                            }
                            if(!siRegistro){
                            	throw new SupervisionException("No se encontraron infracciones registradas", null);
                            }
                        }
                    } else if (supervisionDTO.getOrdenServicioDTO().getIteracion() > Constantes.SUPERVISION_PRIMERA_ITERACION) {
                        //con el expediente buscamos la PRIMERA iteracion del expediente
                        //para copiar las incumplidas y nosubsanadas
                        if (ordenServicioPadre != null && ordenServicioPadre.getIdOrdenServicio() != null) {
                            registroSupervision.setFlagSupervision(ordenServicioPadre.getFlagPresentoDescargo());
                            //resultadoSupervisionInicial
                            ResultadoSupervisionDTO resSupIni = resultadoSupervisionDAO.getResultadoSupervision(new ResultadoSupervisionFilter(Constantes.CODIGO_RESULTADO_SUPERVISION_INICIAL_SI));
                            registroSupervision.setResultadoSupervisionInicialDTO(resSupIni);
                            
                            if (supervisionDTO.getIdSupervision() != null) {
                                registroSupervision.setIdSupervision(supervisionDTO.getIdSupervision());
                                registroSupervision.setEstado(Constantes.ESTADO_ACTIVO);                            
                                registroSupervision = supervisionDAO.actualizar(registroSupervision, usuarioDTO);
                            } else {
                                registroSupervision = registrarSupervision(registroSupervision, usuarioDTO);
                            }
                            
                            DetalleSupervisionFilter filtroDetaSuper = new DetalleSupervisionFilter();
                            filtroDetaSuper.setIdOrdenServicio(ordenServicioPadre.getIdOrdenServicio());
                            filtroDetaSuper.setCodigoResultadoSupervision(Constantes.CODIGO_RESULTADO_INCUMPLE);
                            filtroDetaSuper.setFlgBuscaDetaSupSubsanado(Constantes.ESTADO_INACTIVO);
                            List<DetalleSupervisionDTO> listaDetaSuper = detalleSupervisionDAO.find(filtroDetaSuper);
                            boolean siRegistro=false;
                            if (!listaDetaSuper.isEmpty()) {
                                ResultadoSupervisionDTO objetoEstado = resultadoSupervisionDAO.getResultadoSupervision(new ResultadoSupervisionFilter(Constantes.CODIGO_RESULTADO_INICIAL));
                                int contadorPrioridad = 1;
                                
                                for (DetalleSupervisionDTO registro : listaDetaSuper) {
//                                    List<InfraccionDTO> ltainfraccionDTO = infraccionServiceNeg.getListInfraccion(new InfraccionFilter(registro.getObligacion().getIdObligacion()));
//                                    InfraccionDTO infraccionDTO = new InfraccionDTO();
//                                    if (ltainfraccionDTO.size() == Constantes.LISTA_UNICO_VALIR) {
//                                        infraccionDTO = ltainfraccionDTO.get(Constantes.PRIMERO_EN_LISTA);
//                                    }
                                    DetalleSupervisionDTO detalleSupervisionDTO = new DetalleSupervisionDTO();
                                    detalleSupervisionDTO.setSupervision(new SupervisionDTO(registroSupervision.getIdSupervision()));
                                    detalleSupervisionDTO.setObligacion(new ObligacionDTO(registro.getObligacion().getIdObligacion()));
                                    if (registro.getTipificacion() != null && registro.getTipificacion().getIdTipificacion() != null) {
                                        detalleSupervisionDTO.setTipificacion(new TipificacionDTO(registro.getTipificacion().getIdTipificacion()));
                                    }
                                    if (registro.getCriterio() != null && registro.getCriterio().getIdCriterio() != null) {
                                        detalleSupervisionDTO.setCriterio(new CriterioDTO(registro.getCriterio().getIdCriterio()));
                                    }
                                    detalleSupervisionDTO.setFlagRegistrado(Constantes.FLAG_REGISTRADO_NO);
                                    detalleSupervisionDTO.setIdDetalleSupervisionAnt(registro.getIdDetalleSupervision());
                                    //detalleSupervisionDTO.setFlagResultado(Constantes.DETALLE_SUPERVISION_SANCIONA);
                                    detalleSupervisionDTO.setFlagRegistrado(Constantes.FLAG_REGISTRADO_NO);
                                    detalleSupervisionDTO.setPrioridad(new Long(contadorPrioridad));
                                    detalleSupervisionDTO.setResultadoSupervision(objetoEstado);
                                    //detalleSupervisionDTO.setIdMedidaSeguridad(infraccionDTO.getIdmedidaSeguridad());
                                    detalleSupervisionDTO.setIdMedidaSeguridad(registro.getIdMedidaSeguridad());
                                    
                                    if(registro.getInfraccion()!=null && registro.getInfraccion().getIdInfraccion()!=null){
                                    	
                                    	detalleSupervisionDTO.setInfraccion(new InfraccionDTO(registro.getInfraccion().getIdInfraccion()));
                                    	detalleSupervisionDTO = registrarDetalleSupervision(detalleSupervisionDTO, usuarioDTO);
                                    
	                                    /*OSINE_SFS-791 - RSIS 49 - Inicio */                                    
	                                    DetalleLevantamientoFilter filtroDetaLeva = new DetalleLevantamientoFilter();
	                                    filtroDetaLeva.setIdDetalleSupervision(detalleSupervisionDTO.getIdDetalleSupervisionAnt());
	                                    filtroDetaLeva.setFlagUltimoRegistro(Constantes.ESTADO_ACTIVO);
	                                    List<DetalleLevantamientoDTO> listaDetalleLevantamiento = detalleLevantamientoDAO.find(filtroDetaLeva);
	                                    LOG.info("..::..listaDetalleLevantamiento..::.."+listaDetalleLevantamiento.size()); 
	                                    if(listaDetalleLevantamiento!=null && listaDetalleLevantamiento.size()!=0){
	                                        DetalleLevantamientoDTO detalleLevantamientoDTO = listaDetalleLevantamiento.get(0);                                        
	                                        detalleLevantamientoDTO.setIdDetaLevaAtiende(detalleSupervisionDTO.getIdDetalleSupervision());
	                                        LOG.info("..::..idDetalleSupervision..::.."+detalleSupervisionDTO.getIdDetalleSupervision());
	                                        detalleLevantamientoDAO.actualizar(detalleLevantamientoDTO, usuarioDTO); 
	                                    }
	                                    /*OSINE_SFS-791 - RSIS 49 - Fin */                                    
	                                    contadorPrioridad++;
	                                    siRegistro=true;
                                    }                                    
                                }
                            }
                            if(!siRegistro){
                            	throw new SupervisionException("No se encontraron infracciones registradas", null);
                            }
                        }
                        registroSupervision.setSupervisionAnterior(supervisionPadre);
                    }

                }
            }
        } catch (Exception ex) {
            LOG.error("Error en registrarSupervisionBloque", ex);
            throw new SupervisionException(ex.getMessage(), ex);
        }
        LOG.info("SupervisionServiceNegImpl--registrarSupervisionBloque - fin");
        return registroSupervision;
    }

    @Override
    @Transactional(rollbackFor = SupervisionException.class)
    public SupervisionDTO actualizarSupervision(SupervisionDTO supervisionDTO,
            UsuarioDTO usuarioDTO) throws SupervisionException {
        LOG.info("SupervisionServiceNegImpl--actualizarSupervision - inicio");
        SupervisionDTO retorno = null;
        PersonaGeneralDTO personaGeneral = null;
        PersonaGeneralDTO personaAnt = null;
        List<PersonaGeneralDTO> listaPersonaGeneral = null;
        List<SupervisionPersonaGralDTO> listaSupervisionPersona = null;
        try {
            retorno = supervisionDAO.actualizar(supervisionDTO, usuarioDTO);
            listaSupervisionPersona = superPersonaGralDAO.find(new SupervisionPersonaGralFilter(retorno.getIdSupervision()));
            if (supervisionDTO.getFlagSupervision().equals(Constantes.DETALLE_SUPERVISION_CUMPLE)) {
                if (supervisionDTO.getFlagIdentificaPersona().equals(Constantes.FLAG_IDENTIFICA_PERSONA_SI)) {//si se identifica la persona
                    if (supervisionDTO.getSupervisionPersonaGral().getPersonaGeneral().getIdPersonaGeneral() == null) {
                        personaGeneral = personaGeneralDAO.registrar(supervisionDTO.getSupervisionPersonaGral().getPersonaGeneral(), usuarioDTO);
                    } else {
                        listaPersonaGeneral = personaGeneralDAO.find(new PersonaGeneralFilter(supervisionDTO.getSupervisionPersonaGral().getPersonaGeneral().getIdPersonaGeneral()));
                        if (!listaPersonaGeneral.isEmpty()) {
                            personaAnt = listaPersonaGeneral.get(Constantes.PRIMERO_EN_LISTA);
                            //valido si es distinto nombres y apellidos si:registro en personaGeneral
                            if (!(Utiles.compararCadenas(personaAnt.getNombresPersona(), supervisionDTO.getSupervisionPersonaGral().getPersonaGeneral().getNombresPersona()))
                                    || !(Utiles.compararCadenas(personaAnt.getApellidoPaternoPersona(), supervisionDTO.getSupervisionPersonaGral().getPersonaGeneral().getApellidoPaternoPersona()))
                                    || !(Utiles.compararCadenas(personaAnt.getApellidoMaternoPersona(), supervisionDTO.getSupervisionPersonaGral().getPersonaGeneral().getApellidoMaternoPersona()))) {
                                //personaAnt ponemos flag 0
                                personaAnt.setFlagUltimo(Constantes.FLAG_NO_ULTIMO_PERSONA_GENERAL);
                                personaAnt.setEstado(Constantes.ESTADO_ACTIVO);
                                personaGeneralDAO.actualizar(personaAnt, usuarioDTO);
                                //registramos
                                PersonaGeneralDTO personaRegistro = supervisionDTO.getSupervisionPersonaGral().getPersonaGeneral();
                                personaRegistro.setIdPersonaGeneral(null);
                                personaGeneral = personaGeneralDAO.registrar(supervisionDTO.getSupervisionPersonaGral().getPersonaGeneral(), usuarioDTO);
                            } else {
                                personaGeneral = supervisionDTO.getSupervisionPersonaGral().getPersonaGeneral();
                            }
                        }
                    }
                    SupervisionPersonaGralDTO supervisionPersRegistro = new SupervisionPersonaGralDTO();
                    supervisionPersRegistro = supervisionDTO.getSupervisionPersonaGral();
                    supervisionPersRegistro.setPersonaGeneral(personaGeneral);
                    supervisionPersRegistro.setSupervision(retorno);
                    supervisionPersRegistro.setEstado(Constantes.ESTADO_ACTIVO);

                    if (!listaSupervisionPersona.isEmpty()) {
                        superPersonaGralDAO.eliminar(listaSupervisionPersona.get(Constantes.PRIMERO_EN_LISTA));
                    }
                    superPersonaGralDAO.registrar(supervisionPersRegistro, usuarioDTO);
                } else {
                    if (!listaSupervisionPersona.isEmpty()) {
                        superPersonaGralDAO.eliminar(listaSupervisionPersona.get(Constantes.PRIMERO_EN_LISTA));
                    }
                }
            } else {
                if (!listaSupervisionPersona.isEmpty()) {
                    superPersonaGralDAO.eliminar(listaSupervisionPersona.get(Constantes.PRIMERO_EN_LISTA));
                }
            }
        } catch (Exception ex) {
            LOG.error("Error en actualizarSupervision", ex);
            throw new SupervisionException(ex.getMessage(), ex);
        }
        LOG.info("SupervisionServiceNegImpl--actualizarSupervision - fin");
        return retorno;
    }

    @Override
    @Transactional(rollbackFor = SupervisionException.class)
    public SupervisionDTO cambiarSupervision(SupervisionDTO supervisionDTO,
            UsuarioDTO usuarioDTO) throws SupervisionException {
        LOG.info("SupervisionServiceNegImpl--cambiarSupervision - inicio");
        SupervisionDTO retorno = null;
        List<SupervisionPersonaGralDTO> listaSupervisionPersona = null;
        List<DetalleSupervisionDTO> listaDetaSupe = null;
        List<DocumentoAdjuntoDTO> listaDetaSupeDocu = null;
        try {
            retorno = supervisionDAO.cambiarSupervision(supervisionDTO, usuarioDTO);
            if (supervisionDTO.getOrdenServicioDTO().getIteracion() == Constantes.SUPERVISION_PRIMERA_ITERACION) {
                if (supervisionDTO.getFlagSupervision().equals(Constantes.FLAG_NO_SUPERVISION)) {
                    listaSupervisionPersona = superPersonaGralDAO.find(new SupervisionPersonaGralFilter(supervisionDTO.getIdSupervision()));
                    if (!listaSupervisionPersona.isEmpty()) {
                        superPersonaGralDAO.eliminar(listaSupervisionPersona.get(Constantes.PRIMERO_EN_LISTA));
                    }
                    //actualizamos el detalle como en un inicio
                    DetalleSupervisionFilter filtro = new DetalleSupervisionFilter();
                    filtro.setIdSupervision(supervisionDTO.getIdSupervision());
                    listaDetaSupe = detalleSupervisionDAO.find(filtro);
                    if (!listaDetaSupe.isEmpty()) {
                        for (DetalleSupervisionDTO registro : listaDetaSupe) {
                            DetalleSupervisionDTO detalleSuper = new DetalleSupervisionDTO();
                            detalleSuper.setIdDetalleSupervision(registro.getIdDetalleSupervision());
                            detalleSuper.setSupervision(registro.getSupervision());
                            detalleSuper.setObligacion(registro.getObligacion());
                            detalleSuper.setFlagResultado(Constantes.DETALLE_SUPERVISION_CUMPLE);
                            detalleSuper.setFlagRegistrado(Constantes.FLAG_REGISTRADO_NO);
                            detalleSuper.setEstado(Constantes.ESTADO_ACTIVO);
                            detalleSupervisionDAO.actualizar(detalleSuper, usuarioDTO);
                            //eliminamos los documentos de los detalles
                            DocumentoAdjuntoFilter filtroDocu = new DocumentoAdjuntoFilter();
                            filtroDocu.setIdDetalleSupervision(registro.getIdDetalleSupervision());
                            listaDetaSupeDocu = pghDocumentoAdjuntoDAO.find(filtroDocu);
                            if (!listaDetaSupeDocu.isEmpty()) {
                                for (DocumentoAdjuntoDTO documento : listaDetaSupeDocu) {
                                    pghDocumentoAdjuntoDAO.eliminar(documento);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOG.error("Error en cambiarSupervision", ex);
            throw new SupervisionException(ex.getMessage(), ex);
        }
        LOG.info("SupervisionServiceNegImpl--cambiarSupervision - fin");
        return retorno;
    }

    @Override
    @Transactional(rollbackFor = SupervisionException.class)
    public SupervisionDTO registrarDatosSupervision(SupervisionDTO supervisionDTO,
            UsuarioDTO usuarioDTO) throws SupervisionException {
        LOG.info("SupervisionServiceNegImpl--registrarDatosSupervision - inicio");
        SupervisionDTO retorno = null;
        try {
            retorno = supervisionDAO.registrarDatosSupervision(supervisionDTO, usuarioDTO);
        } catch (Exception ex) {
            LOG.error("Error en registrarDatosSupervision", ex);
            throw new SupervisionException(ex.getMessage(), ex);
        }
        LOG.info("SupervisionServiceNegImpl--registrarDatosSupervision - fin");
        return retorno;
    }

    @Override
    @Transactional(rollbackFor = SupervisionException.class)
    public SupervisionDTO registrarPersonaAtiende(SupervisionDTO supervisionDTO,
            UsuarioDTO usuarioDTO) throws SupervisionException {
        LOG.info("SupervisionServiceNegImpl--registrarPersonaAtiende - inicio");
        SupervisionDTO retorno = null;
        PersonaGeneralDTO personaGeneral = null;
        PersonaGeneralDTO personaAnt = null;
        List<PersonaGeneralDTO> listaPersonaGeneral = null;
        List<SupervisionPersonaGralDTO> listaSupervisionPersona = null;
        try {
            retorno = supervisionDAO.registrarPersonaAtiende(supervisionDTO, usuarioDTO);
            listaSupervisionPersona = superPersonaGralDAO.find(new SupervisionPersonaGralFilter(retorno.getIdSupervision()));
            if (supervisionDTO.getFlagIdentificaPersona().equals(Constantes.FLAG_IDENTIFICA_PERSONA_SI)) {//si se identifica la persona
                if (supervisionDTO.getSupervisionPersonaGral().getPersonaGeneral().getIdPersonaGeneral() == null) {
                    personaGeneral = personaGeneralDAO.registrar(supervisionDTO.getSupervisionPersonaGral().getPersonaGeneral(), usuarioDTO);
                } else {
                    listaPersonaGeneral = personaGeneralDAO.find(new PersonaGeneralFilter(supervisionDTO.getSupervisionPersonaGral().getPersonaGeneral().getIdPersonaGeneral()));
                    if (!listaPersonaGeneral.isEmpty()) {
                        personaAnt = listaPersonaGeneral.get(Constantes.PRIMERO_EN_LISTA);
                        //valido si es distinto nombres y apellidos si:registro en personaGeneral
                        if (!(Utiles.compararCadenas(personaAnt.getNombresPersona(), supervisionDTO.getSupervisionPersonaGral().getPersonaGeneral().getNombresPersona()))
                                || !(Utiles.compararCadenas(personaAnt.getApellidoPaternoPersona(), supervisionDTO.getSupervisionPersonaGral().getPersonaGeneral().getApellidoPaternoPersona()))
                                || !(Utiles.compararCadenas(personaAnt.getApellidoMaternoPersona(), supervisionDTO.getSupervisionPersonaGral().getPersonaGeneral().getApellidoMaternoPersona()))) {
                            //personaAnt ponemos flag 0
                            personaAnt.setFlagUltimo(Constantes.FLAG_NO_ULTIMO_PERSONA_GENERAL);
                            personaAnt.setEstado(Constantes.ESTADO_ACTIVO);
                            personaGeneralDAO.actualizar(personaAnt, usuarioDTO);
                            //registramos
                            PersonaGeneralDTO personaRegistro = supervisionDTO.getSupervisionPersonaGral().getPersonaGeneral();
                            personaRegistro.setIdPersonaGeneral(null);
                            personaGeneral = personaGeneralDAO.registrar(supervisionDTO.getSupervisionPersonaGral().getPersonaGeneral(), usuarioDTO);
                        } else {
                            personaGeneral = supervisionDTO.getSupervisionPersonaGral().getPersonaGeneral();
                        }
                    }
                }
                SupervisionPersonaGralDTO supervisionPersRegistro = new SupervisionPersonaGralDTO();
                supervisionPersRegistro = supervisionDTO.getSupervisionPersonaGral();
                supervisionPersRegistro.setPersonaGeneral(personaGeneral);
                supervisionPersRegistro.setSupervision(retorno);
                supervisionPersRegistro.setEstado(Constantes.ESTADO_ACTIVO);
                if (supervisionDTO.getSupervisionPersonaGral().getCorreo() != null) {
                    supervisionPersRegistro.setCorreo(supervisionDTO.getSupervisionPersonaGral().getCorreo());
                }

                if (!listaSupervisionPersona.isEmpty()) {
                    superPersonaGralDAO.eliminar(listaSupervisionPersona.get(Constantes.PRIMERO_EN_LISTA));
                }
                superPersonaGralDAO.registrar(supervisionPersRegistro, usuarioDTO);
            } else {
                if (!listaSupervisionPersona.isEmpty()) {
                    superPersonaGralDAO.eliminar(listaSupervisionPersona.get(Constantes.PRIMERO_EN_LISTA));
                }
            }
        } catch (Exception ex) {
            LOG.error("Error en registrarPersonaAtiende", ex);
            throw new SupervisionException(ex.getMessage(), ex);
        }
        LOG.info("SupervisionServiceNegImpl--registrarPersonaAtiende - fin");
        return retorno;
    }

    @Override
    @Transactional(rollbackFor = DetalleSupervisionException.class)
    public DetalleSupervisionDTO registrarDetalleSupervision(DetalleSupervisionDTO detalleSupervisionDTO, UsuarioDTO usuarioDTO)
            throws SupervisionException {
        LOG.info("SupervisionServiceNegImpl--registrarDetalleSupervision - inicio");
        DetalleSupervisionDTO retorno = null;
        try {
            retorno = detalleSupervisionDAO.registrar(detalleSupervisionDTO, usuarioDTO);
        } catch (Exception ex) {
            LOG.error("Error en registrarDetalleSupervision", ex);
            throw new SupervisionException(ex.getMessage(), ex);
        }
        LOG.info("SupervisionServiceNegImpl--registrarDetalleSupervision - fin");
        return retorno;
    }

    @Override
    @Transactional
    public List<DetalleSupervisionDTO> findDetalleSupervision(DetalleSupervisionFilter filtro) {
        LOG.info("SupervisionServiceNegImpl--findDetalleSupervision - inicio");
        List<DetalleSupervisionDTO> retorno = new ArrayList<DetalleSupervisionDTO>();
        List<DetalleSupervisionDTO> listaDetalleSupervision = null;
        int contador = 1;
        try {
            listaDetalleSupervision = detalleSupervisionDAO.find(filtro);
            if (!listaDetalleSupervision.isEmpty()) {
                //llenamos base legal
                retorno = new ArrayList<DetalleSupervisionDTO>();
                for (DetalleSupervisionDTO registro : listaDetalleSupervision) {
                    registro.setIndice(contador++);
                    registro.setObligacion(obligacionDAO.buscarObligacion(registro.getObligacion()));
                    retorno.add(registro);
                }
            }
        } catch (Exception ex) {
            LOG.error("Error en findDetalleSupervision", ex);
        }
        LOG.info("SupervisionServiceNegImpl--findDetalleSupervision - fin");
        return retorno;
    }
    
    @Override
    @Transactional
    public List<DetalleSupervisionDTO> findDetalleSupervisionDSHL(DetalleSupervisionFilter filtro) {
        LOG.info("SupervisionServiceNegImpl--findDetalleSupervisionDSHL - inicio");
        List<DetalleSupervisionDTO> retorno = new ArrayList<DetalleSupervisionDTO>();
        List<DetalleSupervisionDTO> listaDetalleSupervision = null;
        int contador = 1;
        try {
            listaDetalleSupervision = detalleSupervisionDAO.find(filtro);
            if (!listaDetalleSupervision.isEmpty()) {
                //llenamos base legal
                retorno = new ArrayList<DetalleSupervisionDTO>();
                for (DetalleSupervisionDTO registro : listaDetalleSupervision) {
                    registro.setObligacion(obligacionDAO.buscarObligacion(registro.getObligacion()));
                    if(registro.getObligacion() != null){
                    	if(registro.getObligacion().getDescripcionBaseLegal() != null && !registro.getObligacion().getDescripcionBaseLegal().equals("")){
                    		registro.setIndice(contador++);
                    		retorno.add(registro);
                    	}
                    }
                }
            }
        } catch (Exception ex) {
            LOG.error("Error en findDetalleSupervisionDSHL", ex);
        }
        LOG.info("SupervisionServiceNegImpl--findDetalleSupervisionDSHL - fin");
        return retorno;
    }

    @Override
    public List<TipificacionDTO> listarTipificacion(TipificacionFilter filtro) {
        LOG.info("SupervisionServiceNegImpl--listarTipificacion - inicio");
        List<TipificacionDTO> lista = null;
        try {
            lista = tipificacionDAO.listarTipificacion(filtro);
        } catch (Exception ex) {
            LOG.error("Error en listarTipificacion", ex);
        }
        LOG.info("SupervisionServiceNegImpl--listarTipificacion - fin");
        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipificacionSancionDTO> listarTipificacionSancion(TipificacionSancionFilter filtro) {
        LOG.info("SupervisionServiceNegImpl--listarTipificacionSancion - inicio");
        List<TipificacionSancionDTO> lista = null;
        try {
            lista = tipificacionSancionDAO.find(filtro);
        } catch (Exception ex) {
            LOG.error("Error en listarTipificacionSancion", ex);
        }
        LOG.info("SupervisionServiceNegImpl--listarTipificacionSancion - fin");
        return lista;
    }

    @Override
    public List<CriterioDTO> listarCriterio(Long idObligacion, Long idTipificacion, Long idCriterio) {
        LOG.info("SupervisionServiceNegImpl--listarCriterio - inicio");
        List<CriterioDTO> lista = null;
        try {
            lista = criterioDAO.listarCriterio(idObligacion, idTipificacion, idCriterio);
        } catch (Exception ex) {
            LOG.error("Error en listarCriterio", ex);
        }
        LOG.info("SupervisionServiceNegImpl--listarCriterio - fin");
        return lista;
    }

    @Override
    public List<DetalleCriterioDTO> listarDetalleCriterio(Long idCriterio, Long idDetalleCriterio) {
        LOG.info("SupervisionServiceNegImpl--listarDetalleCriterio - inicio");
        List<DetalleCriterioDTO> lista = null;
        try {
            lista = detalleCriterioDAO.listarDetalleCriterio(idCriterio, idDetalleCriterio);
        } catch (Exception ex) {
            LOG.error("Error en listarDetalleCriterio", ex);
        }
        LOG.info("SupervisionServiceNegImpl--listarDetalleCriterio - fin");
        return lista;
    }

    @Override
    @Transactional
    public DetalleSupervisionDTO actualizarDetalleSupervision(DetalleSupervisionDTO detalleSupervisionDTO, UsuarioDTO usuarioDTO)
            throws SupervisionException {
        LOG.info("SupervisionServiceNegImpl--actualizarDetalleSupervision - inicio");
        DetalleSupervisionDTO retorno = null;
        try {
            retorno = detalleSupervisionDAO.actualizar(detalleSupervisionDTO, usuarioDTO);
        } catch (Exception ex) {
            LOG.error("Error en actualizarDetalleSupervision", ex);
            throw new SupervisionException(ex.getMessage(), ex);
        }
        LOG.info("SupervisionServiceNegImpl--actualizarDetalleSupervision - fin");
        return retorno;
    }

    @Override
    @Transactional
    public Integer buscarFlag(Long idObligacionTipo, Long idActividad, Long idProceso) {
        Integer flag = 0;
        flag = procesoObligacionTipoDAO.buscarFlagSupervision(idObligacionTipo, idActividad, idProceso);
        return flag;

    }

    @Override
    @Transactional
    public List<SupervisionPersonaGralDTO> listarSupervisionPersona(SupervisionPersonaGralFilter filtro) {
        LOG.info("SupervisionServiceNegImpl--listarSupervisionPersona - inicio");
        List<SupervisionPersonaGralDTO> lista = null;
        try {
            lista = superPersonaGralDAO.find(filtro);
        } catch (Exception ex) {
            LOG.error("Error en listarSupervisionPersona", ex);
        }
        LOG.info("SupervisionServiceNegImpl--listarSupervisionPersona - fin");
        return lista;
    }

    @Override
    @Transactional
    public List<PlantillaResultadoDTO> listarPlantillaResultado(PlantillaResultadoFilter filtro) {
        LOG.info("SupervisionServiceNegImpl--listarPlantillaResultado - inicio");
        List<PlantillaResultadoDTO> lista = null;
        try {
            lista = plantillaResultadoDAO.listarPlantillaResultado(filtro);
        } catch (Exception ex) {
            LOG.error("Error en listarPlantillaResultado", ex);
        }
        LOG.info("SupervisionServiceNegImpl--listarPlantillaResultado - fin");
        return lista;
    }

    @Override
    @Transactional
    public Map<String, Object> validarRegistroSupervision(String enPantalla, Long idSupervision, SupervisionDTO supervisionPantalla) throws SupervisionException {
        LOG.info("SupervisionServiceNegImpl--validarRegistroSupervision - inicio");
        
        LOG.info("<<validarRegistroSupervision>> : enPantalla: " + enPantalla + " idSupervision : " + idSupervision + " ");
        
        List<SupervisionDTO> listaSupervision = null;
        List<SupervisionPersonaGralDTO> listaSuperPersona = null;
        SupervisionPersonaGralDTO supervisionPersona = null;
        SupervisionDTO supervisionRegistro = null;
        Map<String, Object> retorno = new HashMap<String, Object>();
        String mensaje = "";
        boolean validoSupervision = true;
        long cantidadCumple;
        long cantidadIncumple;
        try {
            listaSupervision = supervisionDAO.find(new SupervisionFilter(idSupervision));
            if (!listaSupervision.isEmpty()) {
                supervisionRegistro = listaSupervision.get(Constantes.PRIMERO_EN_LISTA);
                if (supervisionRegistro.getFlagIdentificaPersona() != null && supervisionRegistro.getFlagIdentificaPersona().equals(Constantes.FLAG_IDENTIFICA_PERSONA_SI)) {
                    SupervisionPersonaGralFilter supervisionPersonaFilter = new SupervisionPersonaGralFilter();
                    supervisionPersonaFilter.setIdSupervision(supervisionRegistro.getIdSupervision());
                    listaSuperPersona = superPersonaGralDAO.find(supervisionPersonaFilter);
                    if (!listaSuperPersona.isEmpty()) {
                        supervisionPersona = listaSuperPersona.get(Constantes.PRIMERO_EN_LISTA);
                    }
                    supervisionRegistro.setSupervisionPersonaGral(supervisionPersona);
                }
                retorno.put("validoDetalleSupervision", true);
                if (enPantalla.equals(Constantes.ESTADO_ACTIVO)) {
                	/* OSINE_MAN-001 - Inicio */
                	/*
                    if (validarExisteCambios(supervisionPantalla, supervisionRegistro)) {
                        validoSupervision = false;
                        mensaje = "Debe guardar los cambios realizados en las secciones: Datos de Supervisi\u00f3n y Persona que atiende la visita.";
                    } else if (supervisionRegistro.getOrdenServicioDTO().getIteracion() == Constantes.SUPERVISION_SEGUNDA_ITERACION
                            && supervisionRegistro.getFlagSupervision().equals(Constantes.FLAG_SUPERVISION)) {
                        if (!validarExisteDescripcionDescargo(supervisionRegistro.getIdSupervision())) {
                            validoSupervision = false;
                            mensaje = "Debe completar el registro de descargos para las obligaciones incumplidas de la supervisi\u00f3n.";
                        }
                    }
                    */
                	if (supervisionRegistro.getOrdenServicioDTO().getIteracion() == Constantes.SUPERVISION_SEGUNDA_ITERACION
                            && supervisionRegistro.getFlagSupervision().equals(Constantes.FLAG_SUPERVISION)) {
                        if (!validarExisteDescripcionDescargo(supervisionRegistro.getIdSupervision())) {
                            validoSupervision = false;
                            mensaje = "Debe completar el registro de descargos para las obligaciones incumplidas de la supervisi\u00f3n.";
                        }
                	}
                	/* OSINE_MAN-001 - Fin */
                } else if (enPantalla.equals(Constantes.ESTADO_INACTIVO)) {
                    if (validarCamposObligatorios(supervisionRegistro)) {
                        validoSupervision = false;
                        mensaje += "Falta registrar la supervisi\u00f3n.<br>";
                    }
                    if (supervisionRegistro.getOrdenServicioDTO().getIteracion() == Constantes.SUPERVISION_SEGUNDA_ITERACION
                            && supervisionRegistro.getFlagSupervision().equals(Constantes.FLAG_SUPERVISION)) {
                        if (!validarExisteDescripcionDescargo(supervisionRegistro.getIdSupervision())) {
                            validoSupervision = false;
                            mensaje += "Falta registrar Descargos.";
                        }
                    }
                    if (validoSupervision) {
                        if (supervisionRegistro.getFlagSupervision().equals(Constantes.FLAG_SUPERVISION)) {
                            DetalleSupervisionFilter filtro = new DetalleSupervisionFilter();
                            filtro.setIdSupervision(supervisionRegistro.getIdSupervision());
                            filtro.setFlagResultado(Constantes.ESTADO_ACTIVO);
                            cantidadCumple = detalleSupervisionDAO.cantidadDetalleSupervision(filtro);
                            filtro.setFlagResultado(Constantes.ESTADO_INACTIVO);
                            cantidadIncumple = detalleSupervisionDAO.cantidadDetalleSupervision(filtro);
                            mensaje += "Resumen supervisi\u00f3n: " + cantidadIncumple + " incumplimiento(s)" + "," + cantidadCumple + " cumplimiento(s).";
                        }
                    }
                }
                retorno.put("validoSupervision", validoSupervision);
                retorno.put("mensaje", mensaje);
            }
        } catch (Exception ex) {
            LOG.error("Error en validarRegistroSupervision", ex);
            throw new SupervisionException(ex.getMessage(), ex);
        }
        LOG.info("SupervisionServiceNegImpl--validarRegistroSupervision - fin");
        return retorno;
    }

    private boolean validarExisteDescripcionDescargo(Long idSupervision) { //mdiosesf - RSIS6
        boolean retorno = true;
        List<DetalleSupervisionDTO> listaDetalleSupervision = null;
        List<DetalleEvaluacionDTO> listadetalleEvaluacion = null;
        DetalleSupervisionFilter filtro = new DetalleSupervisionFilter();
        DetalleEvaluacionFilter filtroDevaluacion = new DetalleEvaluacionFilter();
        filtro.setIdSupervision(idSupervision);
        try {
            listaDetalleSupervision = detalleSupervisionDAO.find(filtro);
            if (!listaDetalleSupervision.isEmpty()) {
                for (DetalleSupervisionDTO detalleSupervision : listaDetalleSupervision) {
                    filtroDevaluacion.setIdDetalleSupervision(detalleSupervision.getIdDetalleSupervision());
                    filtroDevaluacion.setIdObligacion(detalleSupervision.getObligacion().getIdObligacion());
                    filtroDevaluacion.setFlagResultado(detalleSupervision.getFlagResultado());
                    listadetalleEvaluacion = detalleEvaluacionDAO.listarDetalleEvaluacion(filtroDevaluacion);
                    for (DetalleEvaluacionDTO detalleEvaluacion : listadetalleEvaluacion) {
                        if (detalleEvaluacion.getTipificacion() != null && detalleEvaluacion.getTipificacion().getTieneCriterio() != null) {
                            if (detalleEvaluacion.getTipificacion().getTieneCriterio().toString().equals(Constantes.DETALLE_SUPERVISION_INCUMPLE)) {
                                if (Constantes.CONSTANTE_VACIA.equals(detalleEvaluacion.getDescripcionResultado().trim()) && detalleEvaluacion.getFlagResultado() != null) {
                                    return false;
                                }
                            } else if (detalleEvaluacion.getTipificacion().getTieneCriterio().toString().equals(Constantes.DETALLE_SUPERVISION_CUMPLE) && Constantes.CONSTANTE_VACIA.equals(detalleEvaluacion.getDescripcionResultado().trim())) {
                                if (!verificarCriterioDescripcion(detalleSupervision.getObligacion().getIdObligacion(), detalleSupervision.getIdDetalleSupervision(), detalleEvaluacion)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOG.error("Error en validarRegistroSupervision", ex);
        }
        return retorno;
    }

    public Boolean verificarCriterioDescripcion(Long idObligacion, Long idDetalleSupervision, DetalleEvaluacionDTO registro) { //mdiosesf - RSIS6
        LOG.info("verificarCriterioDescripcion - inicio");
        Boolean tiene = false;
        Long idCriterio = null;
        Long idTipificacion = null;
        List<DetalleEvaluacionDTO> listaDetalleEvaluacionDTO;
        DetalleEvaluacionFilter detalleEvaluacionFilter = new DetalleEvaluacionFilter();
        try {
            if (registro.getCriterio() != null && registro.getCriterio().getIdCriterio() != null) {
                idCriterio = registro.getCriterio().getIdCriterio();
            }
            if (registro.getTipificacion() != null && registro.getTipificacion().getIdTipificacion() != null) {
                idTipificacion = registro.getTipificacion().getIdTipificacion();
            }
            List<CriterioDTO> listaCriterioDTO = listarCriterio(idObligacion, registro.getTipificacion().getIdTipificacion(), idCriterio);
            if (listaCriterioDTO != null) {
                for (CriterioDTO criterio : listaCriterioDTO) {
                    detalleEvaluacionFilter.setIdDetalleSupervision(idDetalleSupervision);
                    detalleEvaluacionFilter.setIdTipificacion(idTipificacion);
                    detalleEvaluacionFilter.setIdCriterio(criterio.getIdCriterio());
                    detalleEvaluacionFilter.setIdObligacion(idObligacion);
                    listaDetalleEvaluacionDTO = detalleEvaluacionDAO.findDetalleEvaluacion(detalleEvaluacionFilter);
                    if (listaDetalleEvaluacionDTO != null) {
                        if (listaDetalleEvaluacionDTO.size() != 0) {
                            if (!listaDetalleEvaluacionDTO.get(0).getDescripcionResultado().equals(Constantes.CONSTANTE_VACIA)) {
                                tiene = true;
                                break;
                            }
                        }
                    }
                }
            }
            return tiene;
        } catch (Exception ex) {
            LOG.error("Error verificarCriterioDescripcion", ex);
        }
        LOG.info("verificarCriterioDescripcion - fin");
        return tiene;
    }

    private boolean validarExisteCambios(SupervisionDTO supervisionPantalla, SupervisionDTO supervisionRegistro) {
        boolean retorno = false;
        MaestroColumnaDTO tipoAsignacionMaestro = null;
        String flagIdentificaPersona = "";
        String observacionIdentificaPers = "";
        String cartaVisita = "";
        String actaProbatoria = "";
        String observacion = "";
        String descripcionMtvoNoSuprvsn = "";
        String apellidoMaterno = "";
        String flagCumplimientoPrevio = "";
        if (supervisionPantalla != null && supervisionRegistro != null) {
            List<MaestroColumnaDTO> listaMaestroColumna = maestroColumnaDAO.findMaestroColumna(Constantes.DOMINIO_TIPO_ASIGNACION, Constantes.APLICACION_INPS, Constantes.CODIGO_TIPO_ASIGNACION_CON_VISITA);
            if (!listaMaestroColumna.isEmpty()) {
                tipoAsignacionMaestro = listaMaestroColumna.get(Constantes.PRIMERO_EN_LISTA);
            }
            if (!supervisionRegistro.getFechaInicio().equals(supervisionPantalla.getFechaInicio())) {
                return true;
            }
            if (!supervisionRegistro.getHoraInicio().equals(supervisionPantalla.getHoraInicio())) {
                return true;
            }
            if (!supervisionRegistro.getFechaFin().equals(supervisionPantalla.getFechaFin())) {
                return true;
            }
            if (!supervisionRegistro.getHoraFin().equals(supervisionPantalla.getHoraFin())) {
                return true;
            }
            cartaVisita = supervisionRegistro.getCartaVisita() == null ? "" : supervisionRegistro.getCartaVisita();
            if (!cartaVisita.equals(supervisionPantalla.getCartaVisita())) {
                return true;
            }
            actaProbatoria = supervisionRegistro.getActaProbatoria() == null ? "" : supervisionRegistro.getActaProbatoria();
            if (!actaProbatoria.equals(supervisionPantalla.getActaProbatoria())) {
                return true;
            }
            observacion = supervisionRegistro.getObservacion() == null ? "" : supervisionRegistro.getObservacion();
            if (!observacion.equals(supervisionPantalla.getObservacion())) {
                return true;
            }
            if (supervisionPantalla.getFlagSupervision().equals(Constantes.FLAG_NO_SUPERVISION)
                    && supervisionRegistro.getOrdenServicioDTO().getIteracion() == Constantes.SUPERVISION_PRIMERA_ITERACION) {
                if ((supervisionRegistro.getMotivoNoSupervision() == null && supervisionPantalla.getMotivoNoSupervision().getIdMotivoNoSupervision() != -1)
                        || !supervisionRegistro.getMotivoNoSupervision().getIdMotivoNoSupervision().equals(supervisionPantalla.getMotivoNoSupervision().getIdMotivoNoSupervision())) {
                    return true;
                }
                descripcionMtvoNoSuprvsn = supervisionRegistro.getDescripcionMtvoNoSuprvsn() == null ? "" : supervisionRegistro.getDescripcionMtvoNoSuprvsn();
                if (!descripcionMtvoNoSuprvsn.equals(supervisionPantalla.getDescripcionMtvoNoSuprvsn())) {
                    return true;
                }
            }
            if (supervisionPantalla.getFlagSupervision().equals(Constantes.FLAG_SUPERVISION)
                    && supervisionPantalla.getOrdenServicioDTO().getIdTipoAsignacion().equals(tipoAsignacionMaestro.getIdMaestroColumna())) {
                flagIdentificaPersona = supervisionRegistro.getFlagIdentificaPersona() == null ? "" : supervisionRegistro.getFlagIdentificaPersona();
                if (!flagIdentificaPersona.equals(supervisionPantalla.getFlagIdentificaPersona())) {
                    return true;
                } else {
                    if (supervisionPantalla.getFlagIdentificaPersona().equals(Constantes.FLAG_IDENTIFICA_PERSONA_SI)) {
                        if (!supervisionRegistro.getSupervisionPersonaGral().getPersonaGeneral().getIdTipoDocumento()
                                .equals(supervisionPantalla.getSupervisionPersonaGral().getPersonaGeneral().getIdTipoDocumento())) {
                            return true;
                        }
                        if (!supervisionRegistro.getSupervisionPersonaGral().getPersonaGeneral().getNumeroDocumento()
                                .equals(supervisionPantalla.getSupervisionPersonaGral().getPersonaGeneral().getNumeroDocumento())) {
                            return true;
                        }
                        if (!supervisionRegistro.getSupervisionPersonaGral().getPersonaGeneral().getApellidoPaternoPersona()
                                .equals(supervisionPantalla.getSupervisionPersonaGral().getPersonaGeneral().getApellidoPaternoPersona())) {
                            return true;
                        }
                        apellidoMaterno = supervisionRegistro.getSupervisionPersonaGral().getPersonaGeneral().getApellidoMaternoPersona() == null ? ""
                                : supervisionRegistro.getSupervisionPersonaGral().getPersonaGeneral().getApellidoMaternoPersona();
                        if (!apellidoMaterno.equals(supervisionPantalla.getSupervisionPersonaGral().getPersonaGeneral().getApellidoMaternoPersona())) {
                            return true;
                        }
                        if (!supervisionRegistro.getSupervisionPersonaGral().getPersonaGeneral().getNombresPersona()
                                .equals(supervisionPantalla.getSupervisionPersonaGral().getPersonaGeneral().getNombresPersona())) {
                            return true;
                        }
                        if (!supervisionRegistro.getSupervisionPersonaGral().getCargo().getIdMaestroColumna()
                                .equals(supervisionPantalla.getSupervisionPersonaGral().getCargo().getIdMaestroColumna())) {
                            return true;
                        }
                    }
                    observacionIdentificaPers = supervisionRegistro.getObservacionIdentificaPers() == null ? "" : supervisionRegistro.getObservacionIdentificaPers();
                    if (!observacionIdentificaPers.equals(supervisionPantalla.getObservacionIdentificaPers())) {
                        return true;
                    }
                }
                /* OSINE-791 - RSIS 069 - Inicio */
                flagCumplimientoPrevio = supervisionRegistro.getFlagCumplimientoPrevio() == null ? "" : supervisionRegistro.getFlagCumplimientoPrevio();
                if (!flagCumplimientoPrevio.equals(supervisionPantalla.getFlagCumplimientoPrevio())) {
                    return true;
                }
                /* OSINE-791 - RSIS 069 - Fin */
            }
        }
        return retorno;
    }

    private boolean validarCamposObligatorios(SupervisionDTO supervisionRegistro) {
        boolean respuesta = false;
        MaestroColumnaDTO tipoAsignacionMaestro = null;
        try {
            List<MaestroColumnaDTO> listaMaestroColumna = maestroColumnaDAO.findMaestroColumna(Constantes.DOMINIO_TIPO_ASIGNACION, Constantes.APLICACION_INPS, Constantes.CODIGO_TIPO_ASIGNACION_CON_VISITA);
            if (!listaMaestroColumna.isEmpty()) {
                tipoAsignacionMaestro = listaMaestroColumna.get(Constantes.PRIMERO_EN_LISTA);
            }
            if (supervisionRegistro != null) {
                if (supervisionRegistro.getFechaInicio() == null || supervisionRegistro.getFechaInicio().equals(Constantes.CONSTANTE_VACIA)) {
                    return true;
                }
                if (supervisionRegistro.getHoraInicio() == null || supervisionRegistro.getHoraInicio().equals(Constantes.CONSTANTE_VACIA)) {
                    return true;
                }
                if (supervisionRegistro.getFechaFin() == null || supervisionRegistro.getFechaFin().equals(Constantes.CONSTANTE_VACIA)) {
                    return true;
                }
                if (supervisionRegistro.getHoraFin() == null || supervisionRegistro.getHoraFin().equals(Constantes.CONSTANTE_VACIA)) {
                    return true;
                }
                if (supervisionRegistro.getFlagSupervision().equals(Constantes.FLAG_NO_SUPERVISION)
                        && supervisionRegistro.getOrdenServicioDTO().getIteracion() == Constantes.SUPERVISION_PRIMERA_ITERACION) {
                    if (supervisionRegistro.getMotivoNoSupervision() == null) {
                        return true;
                    }
                    List<MotivoNoSupervisionDTO> listaMotivoNoSuper = motivoNoSupervisionDAO.findMotivoNoSupervision(new MotivoNoSupervisionFilter(supervisionRegistro.getMotivoNoSupervision().getIdMotivoNoSupervision()));
                    if (!listaMotivoNoSuper.isEmpty()) {
                        if (listaMotivoNoSuper.get(Constantes.PRIMERO_EN_LISTA).getFlagEspecificar().equals(Constantes.ESTADO_ACTIVO)) {
                            if (supervisionRegistro.getDescripcionMtvoNoSuprvsn() == null || supervisionRegistro.getDescripcionMtvoNoSuprvsn().equals(Constantes.CONSTANTE_VACIA)) {
                                return true;
                            }
                        }
                    }
                }
                
                
                
                
                if (supervisionRegistro.getFlagSupervision().equals(Constantes.FLAG_SUPERVISION)
                        && supervisionRegistro.getOrdenServicioDTO().getIdTipoAsignacion().equals(tipoAsignacionMaestro.getIdMaestroColumna())) {
                    if (supervisionRegistro.getFlagIdentificaPersona() == null || supervisionRegistro.getFlagIdentificaPersona().equals(Constantes.CONSTANTE_VACIA)) {
                        return true;
                    } else {
                        if (supervisionRegistro.getFlagIdentificaPersona().equals(Constantes.FLAG_IDENTIFICA_PERSONA_SI)) {
                            if (supervisionRegistro.getSupervisionPersonaGral().getPersonaGeneral().getIdTipoDocumento() == null) {
                                return true;
                            }
                            if (supervisionRegistro.getSupervisionPersonaGral().getPersonaGeneral().getNumeroDocumento() == null
                                    || supervisionRegistro.getSupervisionPersonaGral().getPersonaGeneral().getNumeroDocumento().equals(Constantes.CONSTANTE_VACIA)) {
                                return true;
                            }
                            if (supervisionRegistro.getSupervisionPersonaGral().getPersonaGeneral().getApellidoPaternoPersona() == null
                                    || supervisionRegistro.getSupervisionPersonaGral().getPersonaGeneral().getApellidoPaternoPersona().equals(Constantes.CONSTANTE_VACIA)) {
                                return true;
                            }
                            if (supervisionRegistro.getSupervisionPersonaGral().getPersonaGeneral().getApellidoMaternoPersona() == null
                                    || supervisionRegistro.getSupervisionPersonaGral().getPersonaGeneral().getApellidoMaternoPersona().equals(Constantes.CONSTANTE_VACIA)) {
                                return true;
                            }
                            if (supervisionRegistro.getSupervisionPersonaGral().getPersonaGeneral().getNombresPersona() == null
                                    || supervisionRegistro.getSupervisionPersonaGral().getPersonaGeneral().getNombresPersona().equals(Constantes.CONSTANTE_VACIA)) {
                                return true;
                            }
                            if (supervisionRegistro.getSupervisionPersonaGral().getCargo().getIdMaestroColumna() == null) {
                                return true;
                            }
                        } else if (supervisionRegistro.getFlagIdentificaPersona().equals(Constantes.FLAG_IDENTIFICA_PERSONA_NO)) {
                            if (supervisionRegistro.getObservacionIdentificaPers() == null || supervisionRegistro.getObservacionIdentificaPers().equals(Constantes.CONSTANTE_VACIA)) {
                                return true;
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOG.error("Error en validarCamposObligatorios", ex);
        }
        return respuesta;
    }

    @Override
    @Transactional
    public List<MotivoNoSupervisionDTO> listarMotivoNoSupervisionAll() {
        LOG.info("SupervisionServiceNegImpl--listarMotivoNoSupervisionAll - inicio");
        List<MotivoNoSupervisionDTO> lista = null;
        try {
            lista = motivoNoSupervisionDAO.findAll();
        } catch (Exception ex) {
            LOG.error("Error en listarMotivoNoSupervisionAll", ex);
        }
        LOG.info("SupervisionServiceNegImpl--listarMotivoNoSupervisionAll - fin");
        return lista;
    }

    @Override
    @Transactional
    public Boolean eliminarDetalleSupervision(Long idOrdenServicio, UsuarioDTO usuarioDTO, Long idExpediente) throws SupervisionException {
        Boolean error = false;
        try {
            List<ExpedienteDTO> expedienteDTO = expedienteDAO.findExpedienteOperativo(idExpediente);
            OrdenServicioFilter ordenServicioFilter = new OrdenServicioFilter();
            ordenServicioFilter.setIdOrdenServicio(idOrdenServicio);
            List<OrdenServicioDTO> ordenServicioDTO = ordenServicioDAO.find(ordenServicioFilter);
            SupervisionFilter supervisionFiltro = new SupervisionFilter();
            supervisionFiltro.setIdOrdenServicio(idOrdenServicio);
            List<SupervisionDTO> supervisionDTO = supervisionDAO.find(supervisionFiltro);
            if (supervisionDTO != null && !expedienteDTO.isEmpty()) {
                if (supervisionDTO.get(0).getFlagSupervision().equals(Constantes.FLAG_NO_SUPERVISION) && ordenServicioDTO.get(0).getIteracion() >= Constantes.SUPERVISION_PRIMERA_ITERACION) {
                    DetalleSupervisionFilter detalleSupervisionFilter = new DetalleSupervisionFilter();
                    detalleSupervisionFilter.setIdSupervision(supervisionDTO.get(0).getIdSupervision());
                    List<DetalleSupervisionDTO> detalleSupervisionDTO = detalleSupervisionDAO.find(detalleSupervisionFilter);
                    for (DetalleSupervisionDTO registro : detalleSupervisionDTO) {
                        DetalleSupervisionDTO detalleSupervision = new DetalleSupervisionDTO();
                        detalleSupervision.setSupervision(registro.getSupervision());
                        detalleSupervision.setObligacion(registro.getObligacion());
                        detalleSupervision.setIdDetalleSupervision(registro.getIdDetalleSupervision());
                        detalleSupervision.setEstado(Constantes.ESTADO_INACTIVO);
                        detalleSupervisionDAO.actualizar(detalleSupervision, usuarioDTO);
                    }
                }
            }

        } catch (Exception ex) {
            error = true;
            LOG.error("Error en eliminarDetalleSupervision", ex);
            throw new SupervisionException(ex.getMessage(), ex);
        }

        return error;
    }

    @Override
    @Transactional
    public List<OrdenServicioDTO> listarOrdenServicio(OrdenServicioFilter filtro) {
        LOG.info("SupervisionServiceNegImpl--listarOrdenServicio - inicio");
        List<OrdenServicioDTO> lista = null;
        try {
            lista = ordenServicioDAO.findByFilter(filtro);
        } catch (Exception ex) {
            LOG.error("Error en listarOrdenServicio", ex);
        }
        LOG.info("SupervisionServiceNegImpl--listarOrdenServicio - fin");
        return lista;
    }
    
    /* OSINE_SFS-791 - RSIS 15 - Inicio */ 
    @Override
    @Transactional
    public SupervisionDTO registroOiSupervision(SupervisionDTO supervisionDTO, UsuarioDTO usuarioDTO) throws SupervisionException {
        LOG.info("registroOiSupervision");
        SupervisionDTO retorno=new SupervisionDTO();
        try {
            retorno=supervisionDAO.registroOiSupervision(supervisionDTO,usuarioDTO);  
        } catch (Exception ex) {
            LOG.error("Error en registroOiSupervision", ex);
            throw new SupervisionException(ex.getMessage(), ex);
        }
        return retorno;
    }   
    /* OSINE_SFS-791 - RSIS 15 - Fin */ 

    /* OSINE_SFS-791 - RSIS 16 - Inicio */ 
    @Override
    @Transactional(rollbackFor = SupervisionException.class)
    public SupervisionDTO registroEmSupervision(SupervisionDTO supervisionDTO, UsuarioDTO usuarioDTO) throws SupervisionException {
        LOG.info("registroEmSupervision");
        SupervisionDTO retorno=new SupervisionDTO();
        try {
            retorno=supervisionDAO.registroEmSupervision(supervisionDTO,usuarioDTO);  
        } catch (Exception ex) {
            LOG.error("Error en registroEmSupervision", ex);
            throw new SupervisionException(ex.getMessage(), ex);
        }
        return retorno;
    }
    /* OSINE_SFS-791 - RSIS 16 - Fin */ 
   /* OSINE791 - RSIS17 - Inicio */
    @Override
    @Transactional
    public DocumentoAdjuntoDTO registrarDocumentoEjecucionMedida(DocumentoAdjuntoDTO documentoAdjuntoDTO, UsuarioDTO usuarioDTO) throws SupervisionException {
        LOG.info("registrarDocumentoEjecucionMedida");
        DocumentoAdjuntoDTO retorno=new DocumentoAdjuntoDTO();
        try {
            retorno=supervisionDAO.registrarDocumentoEjecucionMedida(documentoAdjuntoDTO, usuarioDTO);  
        } catch (Exception ex) {
            LOG.error("Error en registrarDocumentoEjecucionMedida", ex);
            throw new SupervisionException(ex.getMessage(), ex);
        }
        return retorno;
    }
    
    @Override
    @Transactional
    public List<SupervisionDTO> buscarSupervisionReporte(SupervisionFilter filtro) {
        LOG.info("SupervisionServiceNegImpl--buscarSupervision - inicio");
        List<SupervisionDTO> retorno = null;
        try {
            retorno = supervisionDAO.findSupervisionReporte(filtro);
        } catch (Exception ex) {
            LOG.error("Error en buscarSupervision", ex);
        }
        LOG.info("SupervisionServiceNegImpl--buscarSupervision - fin");
        return retorno;
    }
   /* OSINE791 - RSIS17 - Fin */
    
    @Override
    @Transactional
    public Map<String, Object> validarRegistroSupervisionDsr(String enPantalla, Long idSupervision, SupervisionDTO supervisionPantalla) throws SupervisionException {
        LOG.info("SupervisionServiceNegImpl--validarRegistroSupervisionDsr - inicio");
        
        LOG.info("<< XXXXX validarRegistroSupervisionDsr XXXX >> : enPantalla: " + enPantalla + " idSupervision : " + idSupervision + " ");
        
        List<SupervisionDTO> listaSupervision = null;
        SupervisionDTO supervisionRegistro = null;
        Map<String, Object> retorno = new HashMap<String, Object>();
        String mensaje = "";
        boolean validoSupervision = true;
        try {
            listaSupervision = supervisionDAO.find(new SupervisionFilter(idSupervision));
            if (!listaSupervision.isEmpty()) {
                supervisionRegistro = listaSupervision.get(Constantes.PRIMERO_EN_LISTA);
                
                if(supervisionRegistro.getResultadoSupervisionDTO() != null && supervisionRegistro.getResultadoSupervisionDTO().getIdResultadosupervision() != null){
                    mensaje += "";
                /* OSINE791 - RSIS20 - Inicio */    
                }else{
                    if (supervisionRegistro.getResultadoSupervisionInicialDTO().getCodigo().equals(Constantes.CODIGO_RESULTADO_SUPERVISION_INICIAL_NO)){
                        mensaje += "Falta generar el resultado de la supervisi\u00f3n en la secci\u00f3n Datos Finales Supervisi\u00f3n.<br>";
                    }else if(supervisionRegistro.getResultadoSupervisionInicialDTO().getCodigo().equals(Constantes.CODIGO_RESULTADO_SUPERVISION_INICIAL_OBSTACULIZADO)){
                        mensaje += "Falta generar el resultado de la supervisi\u00f3n en la secci\u00f3n Registrar Obstaculizaci\u00f3n.<br>";
                    }
                    else if(supervisionRegistro.getResultadoSupervisionInicialDTO().getCodigo().equals(Constantes.CODIGO_RESULTADO_SUPERVISION_INICIAL_PORVERIFICAR)){
                        mensaje += "A\u00fan no se cumple el plazo de espera para el registro de datos de supervisi\u00f3n.";
                    }else{
                        mensaje += "Falta generar el resultado de la supervisi\u00f3n en la secci\u00f3n Ejecuci\u00f3n Medida.<br>";                                    
                    }
                /* OSINE791 - RSIS20 - Fin */    
                    validoSupervision = false;
                }
                retorno.put("validoSupervision", validoSupervision);
                retorno.put("mensaje", mensaje);                
            }
        } catch (Exception ex) {
            LOG.error("Error en validarRegistroSupervisionDsr", ex);
            throw new SupervisionException(ex.getMessage(), ex);
        }
        LOG.info("SupervisionServiceNegImpl--validarRegistroSupervisionDsr - fin");
        return retorno;
    }
    /* OSINE791 - RSIS20 - Fin */

    @Override
    public int VerificarSupervisionObstaculizada(SupervisionDTO filtro) throws SupervisionException{
     LOG.info("VerificarSupervisionObstaculizada");
        int retorno = 0;
        try {
            retorno = supervisionDAO.VerificarSupervisionObstaculizada(filtro);
        } catch (Exception ex) {
            LOG.error("Error en VerificarObstaculizados", ex);
            retorno = 0;
        }
        return retorno;
    }
    /*OSINE_SFS-791 - RSIS 03 - Inicio */
    @Override
    @Transactional(rollbackFor = SupervisionException.class)
    public SupervisionDTO registrarDatosFinalesSupervision(SupervisionDTO supervisionDTO, UsuarioDTO usuarioDTO) throws SupervisionException {
        LOG.info("SupervisionServiceNegImpl--registrarDatosFinalesSupervision - inicio");
        SupervisionDTO retorno = null;
        try {
            retorno = supervisionDAO.registrarDatosFinalesSupervision(supervisionDTO, usuarioDTO);
        } catch (Exception ex) {
            LOG.error("Error en registrarDatosFinalesSupervision", ex);
        }
        LOG.info("SupervisionServiceNegImpl--registrarDatosFinalesSupervision - fin");
        return retorno;
    }
    /*OSINE_SFS-791 - RSIS 03 - Inicio */
    
    @Override
    @Transactional
    public String buscarCodigoFlujoSupervINPS(Long idObligacionTipo, Long idActividad, Long idProceso) {
        String metodo = "";
        metodo = procesoObligacionTipoDAO.buscarCodigoFlujoSupervINPS(idObligacionTipo, idActividad, idProceso);
        return metodo;
    }

    @Override
    @Transactional(rollbackFor = SupervisionException.class)
    public SupervisionDTO ActualizarSupervisionTerminarSupervision(SupervisionDTO supervisionDTO, UsuarioDTO usuarioDTO) throws SupervisionException {
      LOG.info("registroEmSupervision");
        SupervisionDTO retorno=new SupervisionDTO();
        try {
            retorno=supervisionDAO.ActualizarSupervisionTerminarSupervision(supervisionDTO,usuarioDTO);  
        } catch (Exception ex) {
            LOG.error("Error en registroEmSupervision", ex);
            throw new SupervisionException(ex.getMessage(), ex);
        }
        return retorno;  
    }
    /* OSINE791 - RSIS40 - Inicio */
    @Override
    @Transactional
    public DocumentoAdjuntoDTO registrarDocumentoGenerarResultados(DocumentoAdjuntoDTO documentoAdjuntoDTO, UsuarioDTO usuarioDTO) throws SupervisionException {
        LOG.info("registrarDocumentoGenerarResultados");
        DocumentoAdjuntoDTO retorno=new DocumentoAdjuntoDTO();
        try {
            retorno=supervisionDAO.registrarDocumentoGenerarResultados(documentoAdjuntoDTO, usuarioDTO);  
        } catch (Exception ex) {
            LOG.error("Error en registrarDocumentoGenerarResultados", ex);
            throw new SupervisionException(ex.getMessage(), ex);
        }
        return retorno;
    }
    /* OSINE791 - RSIS41 - Fin */
    
    /* OSINE791 - RSIS39 - Inicio */
    @Override
    @Transactional(readOnly=true)
    public boolean verificarNroCartaVista(SupervisionFilter filtro) throws SupervisionException {
        LOG.info("SupervisionServiceNegImpl--verificarNroCartaVista - inicio");
        List<SupervisionDTO> SupervisionList = null;
        boolean retorno = true;
        try {
        	SupervisionList = supervisionDAO.verificarNroCartaVista(filtro);
            if(SupervisionList!=null && SupervisionList.size()!=0){
            	retorno = false;
            }
        } catch (Exception ex) {
            LOG.error("Error en verificarNroCartaVista", ex);
            throw new  SupervisionException(ex);
        }
        LOG.info("SupervisionServiceNegImpl--verificarNroCartaVista - fin");
        return retorno;
    }
    /* OSINE791 - RSIS39 - Fin */
    
    /* OSINE791 - RSIS41 - Inicio */
    @Override
    @Transactional(readOnly=true)
    public List<SupervisionDTO> verificarSupervisionCierreTotalParcialTareaProgramada(SupervisionFilter filtro) throws SupervisionException {
        LOG.info("SupervisionServiceNegImpl--verificarSupervisionCierreTotalParcialTareaProgramada - inicio");
        List<SupervisionDTO> SupervisionList = null;
        try {
            SupervisionList = supervisionDAO.verificarSupervisionCierreTotalParcialTareaProgramada(filtro);
        } catch (Exception ex) {
            LOG.error("Error en verificarSupervisionCierreTotalParcialTareaProgramada", ex);
            throw new SupervisionException(ex);
        }
        LOG.info("SupervisionServiceNegImpl--verificarSupervisionCierreTotalParcialTareaProgramada - fin");
        return SupervisionList;
    }
    /* OSINE791 - RSIS41 - Fin */

    /* OSINE_SFS-791 - RSIS 46-47 - Inicio */
    @Override
    @Transactional(readOnly=true)
	public List<SupervisionDTO> listarSupSinSubSanar(SupervisionFilter filtro) throws SupervisionException {
		LOG.info("SupervisionServiceNegImpl--listarSupSinSubSanar - inicio");
        List<SupervisionDTO> SupervisionList = null;
        try {
            SupervisionList = supervisionDAO.listarSupSinSubSanar(filtro);
        } catch (Exception ex) {
            LOG.error("Error en listarSupSinSubSanar", ex);
            throw new SupervisionException(ex);
        }
        LOG.info("SupervisionServiceNegImpl--listarSupSinSubSanar - fin");
        return SupervisionList;
	}
    /* OSINE_SFS-791 - RSIS 46-47 - Fin */
}
