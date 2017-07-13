/**
 * Resumen Objeto	: SupervisionDsrController.java 
 * Descripción    	: Controla el flujo de datos para supervision DSR 
 * Fecha de Creación	: 17/08/2016 Creación	:
 * OSINE_791 Autor	: Zosimo Chaupis Santur
 * ---------------------------------------------------------------------------------------------------
 * Modificaciones       Fecha                Nombre                     Descripción
 * ---------------------------------------------------------------------------------------------------
 * OSINE791–RSIS5   | 18/08/2016 | Zosimo Chaupis Santur | Crear la funcionalidad para registrar los datos iniciales de una supervisión de orden de supervisión DSR-CRITICIDAD de la casuística SUPERVISIÓN REALIZADA 
 * OSINE791–RSIS7   | 19/08/2016 | Zosimo Chaupis Santur | Crear la funcionalidad de consultar las obligaciones a verificar en una supervisión de orden de supervisión DSR-CRITICIDAD de la casuística supervisión realizada 
 * OSINE_SFS-791    | 23/08/2016 | Luis García Reyna     | Crear la funcionalidad para registrar otros incumplimientos 
 * OSINE791–RSIS10  | 26/08/2016 | Zosimo Chaupis Santur | Crear la funcionalidad para registrar una obligación como "CUMPLIDA" en el registro de supervisión de una orden de supervisión DSR-CRITICIDAD 
 * OSINE791–RSIS11  | 26/08/2016 | Zosimo Chaupis Santur | Crear la funcionalidad para registrar una obligación como "NO APLICA" en el registro de supervisión de una orden de supervisión DSR-CRITICIDAD 
 * OSINE791–RSIS12  | 26/08/2016 | Zosimo Chaupis Santur | Crear la funcionalidad para registrar una obligación como "POR VERIFICAR" en el registro de supervisión de una orden de supervisión DSR-CRITICIDAD 
 * OSINE_SFS-791    | 29/08/2016 | Luis García Reyna     | Crear la funcionalidad para registrar Ejecucion Medida Supervision 
 * OSINE791–RSIS13  | 31/08/2016 | Zosimo Chaupis Santur | Crear la funcionalidad para registrar información de una obligación "OBSTACULIZADA" en el registro de supervisión de una orden de supervisión DSR-CRITICIDAD 
 * OSINE791–RSIS14  | 01/09/2016 | Zosimo Chaupis Santur | Considerar la funcionalidad para subsanar una obligación marcada como "INCUMPLIDA" de una supervisión de orden de supervisión DSR-CRITICIDAD 
 * OSINE_SFS-791    | 01/09/2016 | Luis García Reyna     | Listar Detalle Supervision 
 * OSINE_SFS-791    | 05/09/2016 | Luis García Reyna     | Buscar Escenario Incumplido por detalla supervisión.
 * OSINE791-RSIS25 	| 08/09/2016 |Alexander Vilca Narvaez| Crear la funcionalidad para consultar el comentario de ejecución de medida ingresado para las infracciones identificadas DSR-CRITICIDAD
 * OSINE_SFS-791    | 06/10/2016 | Luis García Reyna     | Registrar Supervisión No Iniciada
 * OSINE_MAN_DSR_0025  | 19/06/2017  |  Carlos Quijano Chavez::ADAPTER   | Agregar la Columna Codigo y eliminar prioridad 
 */
package gob.osinergmin.inpsweb.controller;
/* OSINE_MAN_DSR_0025 - Inicio */
import gob.osinergmin.inpsweb.dto.ObligacionDsrUpdtDTO;
/* OSINE_MAN_DSR_0025 - Fin */
import gob.osinergmin.inpsweb.dto.GenerarResultadoDTO;
import gob.osinergmin.inpsweb.service.business.ComentarioDetSupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.business.ComentarioIncumplimientoServiceNeg;
import gob.osinergmin.inpsweb.service.business.DetalleLevantamientoServiceNeg;
import gob.osinergmin.inpsweb.service.business.DetalleSupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.business.DocumentoAdjuntoServiceNeg;
import gob.osinergmin.inpsweb.service.business.EscenarioIncumplidoServiceNeg;
import gob.osinergmin.inpsweb.service.business.EscenarioIncumplimientoServiceNeg;
import gob.osinergmin.inpsweb.service.business.MaestroColumnaServiceNeg;
import gob.osinergmin.inpsweb.service.business.ObligacionDsrServiceNeg;
import gob.osinergmin.inpsweb.service.business.ProductoSuspenderServiceNeg;
import gob.osinergmin.inpsweb.service.business.ResultadoSupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.business.SupervisionPersonaGralServiceNeg;
import gob.osinergmin.inpsweb.service.business.SupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.exception.DocumentoAdjuntoException;
import gob.osinergmin.inpsweb.service.exception.SupervisionPersonaGralException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.ConstantesWeb;
import gob.osinergmin.inpsweb.util.StringUtil;
import gob.osinergmin.mdicommon.domain.dto.ComentarioDetSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.ComentarioIncumplimientoDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleLevantamientoDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.EscenarioIncumplidoDTO;
import gob.osinergmin.mdicommon.domain.dto.EscenarioIncumplimientoDTO;
import gob.osinergmin.mdicommon.domain.dto.FechaHoraDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.ObligacionDsrDTO;
import gob.osinergmin.mdicommon.domain.dto.PersonaGeneralDTO;
import gob.osinergmin.mdicommon.domain.dto.ProductoDsrScopDTO;
import gob.osinergmin.mdicommon.domain.dto.ResultadoSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionPersonaGralDTO;
import gob.osinergmin.mdicommon.domain.ui.ComentarioDetSupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.ComentarioIncumplimientoFilter;
import gob.osinergmin.mdicommon.domain.ui.DetalleLevantamientoFilter;
import gob.osinergmin.mdicommon.domain.ui.DetalleSupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.DocumentoAdjuntoFilter;
import gob.osinergmin.mdicommon.domain.ui.EscenarioIncumplidoFilter;
import gob.osinergmin.mdicommon.domain.ui.EscenarioIncumplimientoFilter;
import gob.osinergmin.mdicommon.domain.ui.ObligacionDsrFilter;
import gob.osinergmin.mdicommon.domain.ui.ResultadoSupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.SupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.SupervisionPersonaGralFilter;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/supervision/dsr")
public class SupervisionDsrController {

    private static final Logger LOG = LoggerFactory.getLogger(SupervisionDsrController.class);
    @Inject
    private ObligacionDsrServiceNeg obligacionDsrNeg;
    @Inject
    private SupervisionServiceNeg supervisionServiceNeg;
    /* OSINE_SFS-791 - RSIS 16 - Inicio */
    @Inject
    private DetalleSupervisionServiceNeg detalleSupervisionServiceNeg;
    @Inject
    private EscenarioIncumplimientoServiceNeg escenarioIncumplimientoServiceNeg;
    @Inject
    private ResultadoSupervisionServiceNeg resultadoSupervisionServiceNeg;
    @Inject
    private DocumentoAdjuntoServiceNeg documentoAdjuntoServiceNeg;
    @Inject
    private ProductoSuspenderServiceNeg productoSuspenderServiceNeg;
    @Inject
    private EscenarioIncumplidoServiceNeg escenarioIncumplidoServiceNeg;
    /* OSINE_SFS-791 - RSIS 16 - Fin */
    /*<!--  OSINE791 - RSIS19 - Inicio -->*/
    /*<!--  OSINE791 - RSIS41 - Inicio -->*/
    @Inject
    private MaestroColumnaServiceNeg maestroColumnaServiceNeg;
    /*<!--  OSINE791 - RSIS41 - Fin -->*/
    /*<!--  OSINE791 - RSIS19 - Fin -->*/
    /* OSINE791 - RSIS17 - Inicio */
//    @Inject
//    private MaestroColumnaServiceNeg maestroColumnaServiceNeg;
//    
//    @Inject
//    private UnidadSupervisadaServiceNeg unidadSupervisadaServiceNeg;
//    /* OSINE791 - RSIS17 - Fin */
//    @Inject
//    private UbigeoServiceNeg ubigeoServiceNeg;
//    
//     @Inject
//    private LocadorServiceNeg locadorServiceNeg;
//    
//    @Inject
//    private PlazoServiceNeg plazoServiceNeg;
//    @Inject
//    private OrdenServicioServiceNeg ordenServicioServiceNeg;
//    @Inject
//    private PeriodoMedidaSeguridadServiceNeg periodoMedidaSeguridadServiceNeg;
//    @Inject
//    private RegistroHidrocarburoServiceNeg registroHidrocarburoServiceNeg;
    @Inject
    private ComentarioIncumplimientoServiceNeg comentarioIncumplimientoServiceNeg;
    @Inject
    private ComentarioDetSupervisionServiceNeg comentarioDetSupervisionServiceNeg;
//     @Inject
//    private ObligacionBaseLegalServiceNeg obligacionBaseLegalServiceNeg;
    @Inject
    private SupervisionPersonaGralServiceNeg supervisionPersonaGralServiceNeg;
    @Inject
    private DetalleLevantamientoServiceNeg detalleLevantamientoServiceNeg;

    /* OSINE791 – RSIS7 - Inicio */
    @RequestMapping(value = "/findObligacionesListado", method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    Map<String, Object> findObligacionesListado(ObligacionDsrFilter filtro, int rows, int page, HttpSession session, HttpServletRequest request) {
        LOG.info("findExpediente");
        Map<String, Object> retorno = new HashMap<String, Object>();
        int indiceInicial = -1;
        int indiceFinal = -1;
        try {
            /* OSINE_MAN_DSR_0025 - Inicio */
			//List<ObligacionDsrDTO> listadoPaginado = null;
            //List<ObligacionDsrDTO> listado = obligacionDsrNeg.listarObligacionDsr(filtro);
            List<ObligacionDsrUpdtDTO> listadoPaginado = null;
            List<ObligacionDsrUpdtDTO> listado = obligacionDsrNeg.listarObligacionDsrUpdt(filtro);
            /* OSINE_MAN_DSR_0025 - Fin */
            Long contador = (long) listado.size();
            Long numeroFilas = (contador / rows);

            if ((contador % rows) > 0) {
                numeroFilas = numeroFilas + 1L;
            }
            if (numeroFilas < page) {
                page = numeroFilas.intValue();
            }
            if (page == 0) {
                rows = 0;
            }
            indiceInicial = (page - 1) * rows;
            indiceFinal = indiceInicial + rows;
            listadoPaginado = listado.subList(indiceInicial > listado.size() ? listado.size() : indiceInicial, indiceFinal > listado.size() ? listado.size() : indiceFinal);
            retorno.put("total", numeroFilas);
            retorno.put("pagina", page);
            retorno.put("registros", contador);
            retorno.put("filas", listadoPaginado);

        } catch (Exception ex) {
            LOG.error("Error findObligacionesListado", ex);

        }
        return retorno;
    }

    /* OSINE791 – RSIS7 - Fin */
    /* OSINE791 – RSIS5 - Inicio */
    @RequestMapping(value = "/guardarDatosInicialSupervisionDsr", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> guardarDatosInicialSupervisionDsr(SupervisionDTO supervisionDTO, HttpSession session, HttpServletRequest request) {
        LOG.info("guardarDatosInicialSupervisionDsr - inicio");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            Map<String, Object> map = obligacionDsrNeg.guardarDatosInicialSupervisionDsr(supervisionDTO, session, request);
            
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_CREATE);
            retorno.put("supervision", map.get("supervision"));
            retorno.put("sup", map.get("sup"));
        } catch (Exception e) {
            LOG.error("Error en guardarDatosSupervision", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        LOG.info("guardarDatosSupervisionDsr - fin");
        return retorno;
    }

    @RequestMapping(value = "/obtenerFechayHoraSistema", method = RequestMethod.GET)
    public @ResponseBody
    FechaHoraDTO obtenerFechayHoraSistema() {
        LOG.info("Obtener Fecha y Hora del Sistema - SupervisorDrsController");
        Date fechaActual = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        String cadenaFecha = formatoFecha.format(fechaActual);
        SimpleDateFormat formatoHora = new SimpleDateFormat("hh:mm a");
        String cadenaHora = formatoHora.format(fechaActual);
        FechaHoraDTO objeto = new FechaHoraDTO();
        objeto.setFecha(cadenaFecha);
        objeto.setHora(cadenaHora);
        return objeto;
    }
    /* OSINE791 – RSIS5 - Fin */

    /* OSINE_SFS-791 - RSIS 15 - Inicio */
    @RequestMapping(value = "/registroOtrosIncumplimientos", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> registroOtrosIncumplimientos(SupervisionDTO supervisionDTO, HttpSession session, HttpServletRequest request) {
        LOG.info("registroOtrosIncumplimientos");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {

            SupervisionDTO registroOtrosIncumplimientos = supervisionServiceNeg.registroOiSupervision(supervisionDTO, Constantes.getUsuarioDTO(request));

            retorno.put("otrosIncumplimientos", registroOtrosIncumplimientos);           
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put(ConstantesWeb.VV_MENSAJE, "Se realiz&oacute; el registro satisfactoriamente");

        } catch (Exception e) {

            LOG.error("Error en registrar otros incumplimientos", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());

        }
        return retorno;
    }
    /* OSINE_SFS-791 - RSIS 15 - Fin */

    @RequestMapping(value = "/findObligacionPrdtos", method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    Map<String, Object> findObligacionPrdtos(ObligacionDsrFilter filtro, int rows, int page, HttpSession session, HttpServletRequest request) {
        LOG.info("findObligacionPrdtos");

        Map<String, Object> retorno = new HashMap<String, Object>();
        int indiceInicial = -1;
        int indiceFinal = -1;
        try {
            List<ProductoDsrScopDTO> listadoPaginado = null;
            List<ProductoDsrScopDTO> listado = null;
            if (filtro.getIdDetalleSupervision() != null && filtro.getIdDetalleSupervision() != -1) {
                listado = obligacionDsrNeg.listarPdtosDsr(filtro);
            } else {
                listado = new ArrayList<ProductoDsrScopDTO>();
            }

            Long contador = (long) listado.size();
            Long numeroFilas = (contador / rows);

            if ((contador % rows) > 0) {
                numeroFilas = numeroFilas + 1L;
            }
            if (numeroFilas < page) {
                page = numeroFilas.intValue();
            }
            if (page == 0) {
                rows = 0;
            }
            indiceInicial = (page - 1) * rows;
            indiceFinal = indiceInicial + rows;
            listadoPaginado = listado.subList(indiceInicial > listado.size() ? listado.size() : indiceInicial, indiceFinal > listado.size() ? listado.size() : indiceFinal);
            retorno.put("total", numeroFilas);
            retorno.put("pagina", page);
            retorno.put("registros", contador);
            retorno.put("filas", listadoPaginado);

        } catch (Exception ex) {
            LOG.error("Error findObligacionPrdtos", ex);
        }
        return retorno;
    }

    @RequestMapping(value = "/getDetalleSupervision", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getDetalleSupervision(DetalleSupervisionFilter filtro, HttpServletRequest request, HttpSession session, Model model) {
        LOG.info("getDetalleSupervision");
        Map<String, Object> retorno = new HashMap<String, Object>();
        DetalleSupervisionDTO detaSupe = null;
        try {
            List<DetalleSupervisionDTO> detalleSupervisionList = detalleSupervisionServiceNeg.listarDetalleSupervision(filtro);
            if (!CollectionUtils.isEmpty(detalleSupervisionList)) {
                detaSupe = detalleSupervisionList.get(0);
                List<DetalleSupervisionDTO> totalDetaSupeList = detalleSupervisionServiceNeg.listarDetalleSupervision(new DetalleSupervisionFilter(detaSupe.getSupervision().getIdSupervision(), null));
                detaSupe.setTotalPrioridad(new Long(totalDetaSupeList.size()));   
                if(detaSupe.getResultadoSupervisionAnt() == null ){
                    ResultadoSupervisionDTO resultadoAnt = new ResultadoSupervisionDTO();
                    resultadoAnt.setIdResultadosupervision(Constantes.ID_RESULTADO_ANTERIOR_DEFAULT);
                    resultadoAnt.setCodigo(Constantes.CODIGO_RESULTADO_ANTERIOR_DEFAULT);                
                    detaSupe.setResultadoSupervisionAnt(resultadoAnt);
                }
                
            }
              /* OSINE_SFS-791 - RSIS 38 - Inicio */
            System.out.println("enviando id detalle : |"+filtro.getIdDetalleSupervision()+"|");
            DetalleLevantamientoDTO detalleLevantamiento=null;
            DetalleLevantamientoFilter filtroLev=new DetalleLevantamientoFilter();
            //filtroLev.setIdDetalleSupervision(filtro.getIdDetalleSupervision());
            //filtroLev.setFlagUltimoRegistro(Constantes.ESTADO_ACTIVO);
            filtroLev.setIdDetaLevaAtiende(filtro.getIdDetalleSupervision());
            List<DetalleLevantamientoDTO> detalleLevantamientoist = detalleLevantamientoServiceNeg.find(filtroLev);
            System.out.println("cantidad de elementos |"+detalleLevantamientoist.size()+"|");
            if(detalleLevantamientoist!=null && detalleLevantamientoist.size()!=0){
            	detalleLevantamiento=detalleLevantamientoist.get(0);
                 /* OSINE_SFS-791 - RSIS 38  FIN */
            }
            retorno.put("detaSupe", detaSupe);
             /* OSINE_SFS-791 - RSIS 38 - Inicio */
            retorno.put("detaSupelevantamiento", detalleLevantamiento);
             /* OSINE_SFS-791 - RSIS 38  FIN */
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
        } catch (Exception e) {
            LOG.error("Error en getDetalleSupervision", e);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
        }
        return retorno;
    }
    
    @RequestMapping(value = "/findObligacionByPrioridad", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> findObligacionByPrioridad(Long idSupervision, Long prioridad, HttpServletRequest request, HttpSession session, Model model) {
        LOG.info("findObligacionByPrioridad");
        String mensaje;
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            ObligacionDsrDTO obl = obligacionDsrNeg.findByPrioridad(idSupervision, prioridad);
            retorno.put("obligacion", obl);
            mensaje = "exito";
            retorno.put(ConstantesWeb.VV_MENSAJE, mensaje);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
        }
        return retorno;
    }

    @RequestMapping(value = "/findEscenarioIncumplimiento", method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    Map<String, Object> findEscenarioIncumplimiento(EscenarioIncumplimientoFilter filtro, int rows, int page, HttpSession session, HttpServletRequest request) {
        LOG.info("findEscenarioIncumplimiento");

        Map<String, Object> retorno = new HashMap<String, Object>();
        int indiceInicial = -1;
        int indiceFinal = -1;
        try {
            List<EscenarioIncumplimientoDTO> listadoPaginado = null;
            List<EscenarioIncumplimientoDTO> listado = new ArrayList<EscenarioIncumplimientoDTO>();
            if (filtro.getIdInfraccion() != null && filtro.getIdInfraccion() > 0L) {
                listado = escenarioIncumplimientoServiceNeg.listarEscenarioIncumplimiento(filtro);
            }
            
            if(!CollectionUtils.isEmpty(listado) && filtro.getFlagBuscaIncumplido()!=null && filtro.getFlagBuscaIncumplido().equals(Constantes.ESTADO_ACTIVO) && filtro.getIdDetalleSupervision()!=null){
                List<EscenarioIncumplidoDTO> listIncdo=escenarioIncumplidoServiceNeg.findEscenarioIncumplido(new EscenarioIncumplidoFilter(null,filtro.getIdDetalleSupervision(),null));
                if(!CollectionUtils.isEmpty(listIncdo)){
                    for(EscenarioIncumplidoDTO regIncdo : listIncdo){
                        for(EscenarioIncumplimientoDTO reg : listado){
                            if(reg.getIdEsceIncumplimiento().equals(regIncdo.getEscenarioIncumplimientoDTO().getIdEsceIncumplimiento())){
                                reg.setFlagIncumplidoEnDetSup(Constantes.ESTADO_ACTIVO);
                                break;
                            }
                        }                        
                    }
                }
            }

            Long contador = (long) listado.size();
            Long numeroFilas = (contador / rows);

            if ((contador % rows) > 0) {
                numeroFilas = numeroFilas + 1L;
            }
            if (numeroFilas < page) {
                page = numeroFilas.intValue();
            }
            if (page == 0) {
                rows = 0;
            }
            indiceInicial = (page - 1) * rows;
            indiceFinal = indiceInicial + rows;
            listadoPaginado = listado.subList(indiceInicial > listado.size() ? listado.size() : indiceInicial, indiceFinal > listado.size() ? listado.size() : indiceFinal);
            retorno.put("total", numeroFilas);
            retorno.put("pagina", page);
            retorno.put("registros", contador);
            retorno.put("filas", listadoPaginado);

        } catch (Exception ex) {
            LOG.error("Error findObligacionEscenarios", ex);

        }
        return retorno;
    }

    @RequestMapping(value = "/guardarDsrProductoSuspender", method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    Map<String, Object> guardarDsrProductoSuspender(ProductoDsrScopDTO productoDsrScopDTO, HttpSession session, HttpServletRequest request) {
        LOG.info("guardarDsrObligacion");
        LOG.info("getIdDetalleSupervision " + productoDsrScopDTO.getIdDetalleSupervision());
        LOG.info("getIdProducto " + productoDsrScopDTO.getIdProducto());
        LOG.info("getIndicador " + productoDsrScopDTO.getIndicador());
        LOG.info("getIdProductoSuspender " + productoDsrScopDTO.getIdProductoSuspender());
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            ProductoDsrScopDTO productoSuspender = null;
            if (productoDsrScopDTO.getIndicador().equals("1")) {
                productoSuspender = obligacionDsrNeg.guardarDsrProductoSuspender(productoDsrScopDTO, Constantes.getUsuarioDTO(request));
                retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_CREATE);
            } else {
                productoSuspender = obligacionDsrNeg.eliminarDsrProductoSuspender(productoDsrScopDTO, Constantes.getUsuarioDTO(request));
                retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_INVALIDATE);
            }

            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);            
            retorno.put("productoSuspender", productoSuspender);
        } catch (Exception e) {
            LOG.error("Error en guardarDetalleEvaluacion", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }

    /* OSINE791 – RSIS10 - Inicio */
    /* OSINE791 – RSIS11 - Inicio */
    /* OSINE791 – RSIS12 - Inicio */
    /* OSINE791 – RSIS13 - Inicio */
    /* OSINE791 – RSIS14 - Inicio */
    @RequestMapping(value = "/guardarDsrObligacion", method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    Map<String, Object> guardarDsrObligacion(ObligacionDsrDTO obligacionDstDTO, HttpSession session, HttpServletRequest request) {
        LOG.info("guardarDsrObligacion");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            if (obligacionDstDTO.getIdResultadoSupervisionAct() == null || obligacionDstDTO.getIdResultadoSupervisionAct() == -1) {
                ResultadoSupervisionFilter fil = new ResultadoSupervisionFilter();
                fil.setCodigo(Constantes.CODIGO_RESULTADO_INICIAL);
                ResultadoSupervisionDTO res = resultadoSupervisionServiceNeg.getResultadoSupervision(fil);
                obligacionDstDTO.setIdResultadoSupervisionAct(res.getIdResultadosupervision());
            }
            ObligacionDsrDTO obligacion = obligacionDsrNeg.guardarDsrObligacion(obligacionDstDTO, Constantes.getUsuarioDTO(request));
            
            Long codigoResultadoSup=null;            
            if(obligacionDstDTO!=null && obligacionDstDTO.getIdDetalleSupervision()!=null){
            	DetalleSupervisionFilter filtro= new DetalleSupervisionFilter();
            	filtro.setIdDetalleSupervision(obligacionDstDTO.getIdDetalleSupervision());
	            List<DetalleSupervisionDTO> detalleSupervisionList=detalleSupervisionServiceNeg.listarDetalleSupervision(filtro);
	            codigoResultadoSup = (!CollectionUtils.isEmpty(detalleSupervisionList) && ((DetalleSupervisionDTO) detalleSupervisionList.get(0)).getResultadoSupervision()!=null) ? ((DetalleSupervisionDTO) detalleSupervisionList.get(0)).getResultadoSupervision().getCodigo() : null;
            }
            
            if (obligacion != null && codigoResultadoSup!=null && !codigoResultadoSup.toString().equals(Constantes.CODIGO_RESULTADO_OBSTACULIZADO.toString())) {
//                ResultadoSupervisionFilter filtroEstado = new ResultadoSupervisionFilter();
//                filtroEstado.setCodigo(Constantes.CODIGO_RESULTADO_CUMPLE);
//                ResultadoSupervisionDTO objetoEstado = resultadoSupervisionServiceNeg.getResultadoSupervision(filtroEstado);
//                if (obligacionDstDTO.getEstadoIncumplimiento() == objetoEstado.getIdResultadosupervision()) {
//                    DetalleSupervisionDTO reg = new DetalleSupervisionDTO();
//                    reg.setIdDetalleSupervision(obligacion.getIdDetalleSupervision());
                //Inactiva
                documentoAdjuntoServiceNeg.eliminarPghDocumentoAdjuntobyDetalleSupervision(new DetalleSupervisionDTO(obligacion.getIdDetalleSupervision()));
                productoSuspenderServiceNeg.eliminarProductoSuspenderbyDetalleSUpervision(new DetalleSupervisionDTO(obligacion.getIdDetalleSupervision()));
//                }
                //Inactiva escenarioincumplidos si existen
                List<EscenarioIncumplidoDTO> lstEscIndo = escenarioIncumplidoServiceNeg.findEscenarioIncumplido(new EscenarioIncumplidoFilter(null, obligacion.getIdDetalleSupervision(),null));
                if(!CollectionUtils.isEmpty(lstEscIndo)){
                    for(EscenarioIncumplidoDTO regA : lstEscIndo){
                        regA.setEstado(Constantes.ESTADO_INACTIVO);
                        escenarioIncumplidoServiceNeg.cambiaEstadoEscenarioIncumplido(regA, Constantes.getUsuarioDTO(request));
                    }
                }
                //inactiva comentarioDetSupervision si existen
                List<ComentarioDetSupervisionDTO> lstComDetSup = comentarioDetSupervisionServiceNeg.findComentarioDetSupervision(new ComentarioDetSupervisionFilter(null, obligacion.getIdDetalleSupervision(),null));
                if(!CollectionUtils.isEmpty(lstComDetSup)){
                    for(ComentarioDetSupervisionDTO regB : lstComDetSup){
                        regB.setEstado(Constantes.ESTADO_INACTIVO);
                        comentarioDetSupervisionServiceNeg.cambiaEstadoComentarioDetSupervision(regB, Constantes.getUsuarioDTO(request));
                    }
                }
            }
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_CREATE + " El comentario del hallazgo");
            retorno.put("obligacion", obligacion);
        } catch (Exception e) {
            LOG.error("Error en guardarDetalleEvaluacion", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }
    
    @RequestMapping(value = "/editarDsrComentarioSubsanacion", method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    Map<String, Object> editarDsrComentarioSubsanacion(ObligacionDsrDTO obligacionDstDTO, HttpSession session, HttpServletRequest request) {
        LOG.info("editarDsrObligacion");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            ObligacionDsrDTO obligacion = obligacionDsrNeg.editarDsrComentarioSubsanacion(obligacionDstDTO, Constantes.getUsuarioDTO(request));
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_CREATE + " El comentario del hallazgo");
            retorno.put("obligacion", obligacion);
        } catch (Exception e) {
            LOG.error("Error en editarDsrObligacion", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }
    /* OSINE791 – RSIS10 - Fin */
    /* OSINE791 – RSIS11 - Fin */
    /* OSINE791 – RSIS12 - Fin */
    /* OSINE791 – RSIS13 - Fin */
    /* OSINE791 – RSIS14 - Fin */

    /* OSINE791 – RSIS12 - Inicio */
    @RequestMapping(value = "/VerificarObstaculizados", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> VerificarObstaculizados(ObligacionDsrDTO obligacionDstDTO, HttpSession session, HttpServletRequest request) {
        LOG.info("Verificar Obligaciones Por Verificar - Obstaculizadas - VerificarObstaculizados");
        String mensaje;
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            String respuesta = obligacionDsrNeg.VerificarObstaculizados(obligacionDstDTO);
//        	obl.setFlagPoseeEscenario("1");
            retorno.put("respuesta", respuesta);
            mensaje = "exito";
            retorno.put(ConstantesWeb.VV_MENSAJE, mensaje);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
        }
        return retorno;
    }
    /* OSINE791 – RSIS12 - Fin */
    
    

    /* OSINE_SFS-791 - RSIS 16 - Inicio */
    @RequestMapping(value = "/registroEjecucionMedidaSupervision", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    Map<String, Object> registroEjecucionMedidaSupervision(SupervisionDTO supervisionDTO, HttpSession session, HttpServletRequest request) {
        LOG.info("registroEjecucionMedidaSupervision");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {

            SupervisionDTO registroEjecucionMedidaSupervision = supervisionServiceNeg.registroEmSupervision(supervisionDTO, Constantes.getUsuarioDTO(request));

            retorno.put("registroEmSup", registroEjecucionMedidaSupervision);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put(ConstantesWeb.VV_MENSAJE, "Se realiz&oacute; el registro satisfactoriamente");

        } catch (Exception e) {

            LOG.error("Error en registrar Ejecucion Medida Supervisión", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());

        }
        return retorno;
    }

    @RequestMapping(value = "/listarDetalleSupervision", method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    Map<String, Object> listarDetalleSupervision(DetalleSupervisionFilter filtro, int rows, int page, HttpSession session, HttpServletRequest request) {
        LOG.info("listarDetalleSupervision");
        Map<String, Object> retorno = new HashMap<String, Object>();
        int indiceInicial = -1;
        int indiceFinal = -1;
        try {
            List<DetalleSupervisionDTO> listado = detalleSupervisionServiceNeg.listarDetalleSupervision(filtro);
            Long contador = (long) listado.size();
            
            Long numeroFilas = (contador / rows);
            if ((contador % rows) > 0) {
                numeroFilas = numeroFilas + 1L;
            }
            if (numeroFilas < page) {
                page = numeroFilas.intValue();
            }
            if (page == 0) {
                rows = 0;
            }
            
            indiceInicial = (page - 1) * rows;
            indiceFinal = indiceInicial + rows;
            List<DetalleSupervisionDTO>  listadoPaginado = listado.subList(indiceInicial > listado.size() ? listado.size() : indiceInicial, indiceFinal > listado.size() ? listado.size() : indiceFinal);
            retorno.put("total", numeroFilas);
            retorno.put("pagina", page);
            retorno.put("registros", contador);
            retorno.put("filas", listadoPaginado);

        } catch (Exception ex) {
            LOG.error("Error findObligaciones", ex);

        }
        return retorno;
    }

    @RequestMapping(value = "/findEscenarioIncumplido", method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    Map<String, Object> findEscenarioIncumplido(EscenarioIncumplidoFilter filtro, int rows, int page, HttpSession session, HttpServletRequest request) {
        LOG.info("findEscenarioIncumplido");
        Map<String, Object> retorno = new HashMap<String, Object>();
        int indiceInicial = -1;
        int indiceFinal = -1;
        try {
            List<EscenarioIncumplidoDTO> listadoPaginado = null;
            List<EscenarioIncumplidoDTO> listado = escenarioIncumplidoServiceNeg.findEscenarioIncumplido(filtro);

            Long contador = (long) listado.size();
            Long numeroFilas = (contador / rows);

            if ((contador % rows) > 0) {
                numeroFilas = numeroFilas + 1L;
            }
            if (numeroFilas < page) {
                page = numeroFilas.intValue();
            }
            if (page == 0) {
                rows = 0;
            }
            indiceInicial = (page - 1) * rows;
            indiceFinal = indiceInicial + rows;
            listadoPaginado = listado.subList(indiceInicial > listado.size() ? listado.size() : indiceInicial, indiceFinal > listado.size() ? listado.size() : indiceFinal);
            retorno.put("total", numeroFilas);
            retorno.put("pagina", page);
            retorno.put("registros", contador);
            retorno.put("filas", listadoPaginado);

        } catch (Exception ex) {
            LOG.error("Error findEscenarioIncumplido", ex);
        }

        return retorno;
    }

    /* OSINE_SFS-791 - RSIS 16 - Fin */
    /*<!--  OSINE791 - RSIS14 - Inicio -->*/
    @RequestMapping(value = "/abrirRegistroComentarioSubsanacion", method = RequestMethod.GET)
    public String abrirRegistroComentarioSubsanacion(DetalleSupervisionDTO detSupeDTO,String flagEditar, HttpSession sesion, Model model) {
        LOG.info("abrirRegistroComentarioSubsanacion");
        model.addAttribute("ds", detSupeDTO);
        model.addAttribute("flagEditar", flagEditar);
        return ConstantesWeb.Navegacion.PAGE_INPS_SUPERVISION_DSR_COMENTARIO_SUBSANACION;
    }
    /*<!--  OSINE791 - RSIS14 - Fin -->*/
       
    
    
    /* OSINE_SFS-791 - RSIS 25 - Inicio */
    @RequestMapping(value = "/abrirEscenarioIncumplimientoEjecucionMedida", method = RequestMethod.POST)
	  public String abrirEscenarioIncumplimientoEjecucionMedida(Long idDetalleSupervision, Long idEsceIncumplimiento,  HttpSession sesion, Model model, HttpServletRequest request)  {
    	LOG.info("abrirConsComentario");  
	      Map<String, Object> retorno = new HashMap<String, Object>();
	      try {
	    	  if (idEsceIncumplimiento!=null) {
	    		  EscenarioIncumplidoFilter  escenarioIncumplidoFilter=new EscenarioIncumplidoFilter();
	    		  List<EscenarioIncumplidoDTO> listadoEscenarioIncumplido = new ArrayList<EscenarioIncumplidoDTO>();
	    		  escenarioIncumplidoFilter.setIdDetalleSupervision(idDetalleSupervision);
	    		  escenarioIncumplidoFilter.setIdEsceIncumplimiento(idEsceIncumplimiento);
	    		  listadoEscenarioIncumplido=escenarioIncumplidoServiceNeg.findEscenarioIncumplido(escenarioIncumplidoFilter);
	    		  model.addAttribute("comentario", listadoEscenarioIncumplido.get(0).getComentarioEjecucion());
	    	  }
	    	  else{
		    	  List<DetalleSupervisionDTO> listadoDetalleSupervision = new ArrayList<DetalleSupervisionDTO>();
	 	    	  DetalleSupervisionFilter detalleSupervisionFilter=new  DetalleSupervisionFilter(); 
	 	    	  detalleSupervisionFilter.setIdDetalleSupervision(idDetalleSupervision); 
	 	    	  listadoDetalleSupervision = detalleSupervisionServiceNeg.listarDetalleSupervision(detalleSupervisionFilter);
	 		      LOG.info("Cant:::" + listadoDetalleSupervision.size());  
	 	    	  model.addAttribute("comentario", listadoDetalleSupervision.get(0).getComentario());
	    	  }
	    	  
		} catch (Exception e) {
			  retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
	            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
		}
	      return ConstantesWeb.Navegacion.PAGE_INPS_CONSULTA_COMENTARIO;
	  }
    /* OSINE_SFS-791 - RSIS 25 - Fin */
    /* OSINE791 - RSIS17 - Inicio */
    @RequestMapping(value = "/generarResultadosDsr", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> generarResultados(DocumentoAdjuntoFilter filtro, int rows, int page, HttpSession session, HttpServletRequest request) {
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            LOG.info("IdSupervision->"+filtro.getIdSupervision());
            //LOG.info("IdTipoDocumento->"+filtro.getIdTipoDocumento());
            
            //List<DocumentoAdjuntoDTO> listado= documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(filtro);
            
            List<DocumentoAdjuntoDTO> listado = verDocumentosGenerarResultadoFase1SupervisionDSR(filtro.getIdSupervision());
            int indiceInicial = -1;
            int indiceFinal = -1;
            Long contador = (long) listado.size();
            Long numeroFilas = (contador / rows);
            if ((contador % rows) > 0) {
                numeroFilas = numeroFilas + 1L;
            }
            if (numeroFilas < page) {
                page = numeroFilas.intValue();
            }
            if (page == 0) {
                rows = 0;
            }
            indiceInicial = (page - 1) * rows;
            indiceFinal = indiceInicial + rows;
            List<DocumentoAdjuntoDTO> listadoPaginado = listado.subList(indiceInicial > listado.size() ? listado.size() : indiceInicial, indiceFinal > listado.size() ? listado.size() : indiceFinal);


            retorno.put("total", numeroFilas);
            retorno.put("pagina", page);
            retorno.put("registros", contador);
            retorno.put("filas", listadoPaginado);
            
        } catch (Exception ex) {
            LOG.error("Error findgenerarResultados", ex);
        }
        LOG.info("findgenerarResultados - fin");
        return retorno;

    }
    /*OSINE-791 - RSIS 41 - Inicio */
    public List<DocumentoAdjuntoDTO> verDocumentosGenerarResultadoFase1SupervisionDSR(Long idSupervision){
        List<DocumentoAdjuntoDTO> listaprincipal = new ArrayList<DocumentoAdjuntoDTO>();
        
        //para Carta de Visita Fase 1
        DocumentoAdjuntoFilter filtroCartaVisita = new DocumentoAdjuntoFilter();
        MaestroColumnaDTO idTipoDocCartaVisita = maestroColumnaServiceNeg.findMaestroColumnaByDominioAplicDesc(Constantes.DOMINIO_TIP_DOC_SUP_SIGED, Constantes.APLICACION_INPS, Constantes.DESCRIPCION_TIP_DOC_SUP_SIGED_CARTA_VISITA).get(0);
        filtroCartaVisita.setIdSupervision(idSupervision);
        filtroCartaVisita.setIdTipoDocumento(idTipoDocCartaVisita.getIdMaestroColumna());
        List<DocumentoAdjuntoDTO> ltaCartaVisita;
        try {
            ltaCartaVisita = documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(filtroCartaVisita);
        } catch (DocumentoAdjuntoException ex) {
            ltaCartaVisita = null;
        }
        //Para acta de Supervision Fase 1
        DocumentoAdjuntoFilter filtroactaSup = new DocumentoAdjuntoFilter();
        MaestroColumnaDTO idTipoDocactaSup = maestroColumnaServiceNeg.findMaestroColumnaByDominioAplicDesc(Constantes.DOMINIO_TIP_DOC_SUP_SIGED, Constantes.APLICACION_INPS, Constantes.DESCRIPCION_TIP_DOC_SUP_SIGED_ACTA_EJECUCION_MEDIDA_SEGURIDAD).get(0);
        filtroactaSup.setIdSupervision(idSupervision);
        filtroactaSup.setIdTipoDocumento(idTipoDocactaSup.getIdMaestroColumna());
        List<DocumentoAdjuntoDTO> ltaactaSup;
        try {
            ltaactaSup = documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(filtroactaSup);
        } catch (DocumentoAdjuntoException ex) {
            ltaactaSup = null;
        }
        //para Constancia de Suspension de RH
        DocumentoAdjuntoFilter filtroConstSuspenRH = new DocumentoAdjuntoFilter();
        MaestroColumnaDTO idTipoDocConstSuspenRH = maestroColumnaServiceNeg.findMaestroColumnaByDominioAplicDesc(Constantes.DOMINIO_TIP_DOC_SUP_SIGED, Constantes.APLICACION_INPS, Constantes.DESCRIPCION_TIP_DOC_SUP_SIGED_CONSTANCIA_SUSPENSION_CANCELACION_REGIS_HIDRO).get(0);
        filtroConstSuspenRH.setIdSupervision(idSupervision);
        filtroConstSuspenRH.setIdTipoDocumento(idTipoDocConstSuspenRH.getIdMaestroColumna());
        List<DocumentoAdjuntoDTO> ltaConstSuspenRH;
        try {
            ltaConstSuspenRH = documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(filtroConstSuspenRH);
        } catch (DocumentoAdjuntoException ex) {
            ltaConstSuspenRH = null;
        }        
        if(ltaCartaVisita != null && ltaCartaVisita.size() > Constantes.LISTA_VACIA){
            for (DocumentoAdjuntoDTO documentoAdjuntoDTOCartaVisita : ltaCartaVisita) {
                listaprincipal.add(documentoAdjuntoDTOCartaVisita);
            }
        }
        if(ltaactaSup != null && ltaactaSup.size() > Constantes.LISTA_VACIA){
            for (DocumentoAdjuntoDTO documentoAdjuntoDTOactaSup : ltaactaSup) {
                listaprincipal.add(documentoAdjuntoDTOactaSup);
            }
        }
        if(ltaConstSuspenRH != null && ltaConstSuspenRH.size() > Constantes.LISTA_VACIA){
            for (DocumentoAdjuntoDTO documentoAdjuntoDTOConstSuspenRH : ltaConstSuspenRH) {
                listaprincipal.add(documentoAdjuntoDTOConstSuspenRH);
            }
        }
        return listaprincipal;
    }
    /*OSINE_-791 - RSIS 41 - Fin */
    @RequestMapping(value = "/generarResultadosDocumento", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> generarResultadosDocumento(GenerarResultadoDTO generarResultadoDTO ,HttpServletRequest request, HttpSession session) {
        LOG.info("generarResultadosDsr - inicio");
        LOG.info("Idsupervision - inicio->"+generarResultadoDTO.getIdSupervision());
        LOG.info("flagInfracciones - inicio->"+generarResultadoDTO.getFlagInfracciones());
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            Map<String, Object> retornoServ = obligacionDsrNeg.generarResultadosDocumento(generarResultadoDTO,request, session);

            retorno.put("registroDocumentoEjecucionMedida", retornoServ.get("registroDocumentoEjecucionMedida"));
            retorno.put("supervision", retornoServ.get("supervision"));
            retorno.put("correos", retornoServ.get("correos"));
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put(ConstantesWeb.VV_MENSAJE, "OK");
        } catch (Exception ex) {
            retorno.put(ConstantesWeb.VV_MENSAJE, ex.getMessage());
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            LOG.error("Error generarResultadosDocumento", ex);
        }
        LOG.info("generarPdfDsr - fin");
        return retorno;
    }
    
    public Map<String, Object> setearInformacionActaSupervision2(InputStream reportStreamImage, GenerarResultadoDTO generarResultadoDTO,Boolean cierreTotal,Boolean cierreParcial) {
        Map<String, Object> reportParams = new HashMap<String, Object>();
        SupervisionFilter filtro = new SupervisionFilter();
        filtro.setIdSupervision(generarResultadoDTO.getIdSupervision());
        List<SupervisionDTO> supervisionDTOs = supervisionServiceNeg.buscarSupervisionReporte(filtro);
        SupervisionDTO supervisionDTO = new SupervisionDTO();
        supervisionDTO = supervisionDTOs.get(Constantes.PRIMERO_EN_LISTA);
        LOG.info("fechaInicio " + supervisionDTO.getFechaInicio());
        LOG.info("numeroOrdenServicio " + (supervisionDTO.getOrdenServicioDTO().getNumeroOrdenServicio()));
        LOG.info("numeroExpediente " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getNumeroExpediente()));
        LOG.info("idUnidadSupervisada " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getIdUnidadSupervisada()));
        LOG.info("codigoOsinerming " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getCodigoOsinergmin()));
        reportParams.put("rutaImagen", reportStreamImage);
        reportParams.put("nroExpediente", (supervisionDTO.getOrdenServicioDTO().getExpediente().getNumeroExpediente()));
        reportParams.put("oficinaRegional", "");
        reportParams.put("direccionOR", "");
        reportParams.put("telefonosOR", "");
        reportParams.put("cierreTotal", cierreTotal);
        reportParams.put("cierreParcial", cierreParcial);

        return reportParams;
    }
    
     public Map<String, Object> setearInformacionActaSupervision3(InputStream reportStreamImage, GenerarResultadoDTO generarResultadoDTO) {
        Map<String, Object> reportParams = new HashMap<String, Object>();
        SupervisionFilter filtro = new SupervisionFilter();
        filtro.setIdSupervision(generarResultadoDTO.getIdSupervision());
        List<SupervisionDTO> supervisionDTOs = supervisionServiceNeg.buscarSupervisionReporte(filtro);
        SupervisionDTO supervisionDTO = new SupervisionDTO();
        supervisionDTO = supervisionDTOs.get(Constantes.PRIMERO_EN_LISTA);
        LOG.info("fechaInicio " + supervisionDTO.getFechaInicio());
        LOG.info("numeroOrdenServicio " + (supervisionDTO.getOrdenServicioDTO().getNumeroOrdenServicio()));
        LOG.info("numeroExpediente " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getNumeroExpediente()));
        LOG.info("idUnidadSupervisada " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getIdUnidadSupervisada()));
        LOG.info("codigoOsinerming " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getCodigoOsinergmin()));
        SupervisionPersonaGralFilter filtroprgn = new SupervisionPersonaGralFilter();
        filtroprgn.setIdSupervision(supervisionDTO.getIdSupervision());
        List<SupervisionPersonaGralDTO> ltaSupPrson  = new ArrayList<SupervisionPersonaGralDTO>();
        String prsonarcpcion = "";
        String dniprsonarcpcion = "";
        String rlacionagnt = "";
         try {
             ltaSupPrson = supervisionPersonaGralServiceNeg.find(filtroprgn);
         } catch (SupervisionPersonaGralException ex) {
             ltaSupPrson = new ArrayList<SupervisionPersonaGralDTO>();
         }
         if(ltaSupPrson.size() > Constantes.LISTA_VACIA){
             PersonaGeneralDTO prDTO = ltaSupPrson.get(Constantes.PRIMERO_EN_LISTA).getPersonaGeneral();
             prsonarcpcion = ""+prDTO.getNombresPersona()+" "+prDTO.getApellidoPaternoPersona()+" "+prDTO.getApellidoMaternoPersona();
             dniprsonarcpcion = ""+prDTO.getNumeroDocumento();
             rlacionagnt = ltaSupPrson.get(Constantes.PRIMERO_EN_LISTA).getCargo().getDescripcion();
         }
        reportParams.put("rutaImagen", reportStreamImage);
        reportParams.put("nroExpediente", (supervisionDTO.getOrdenServicioDTO().getExpediente().getNumeroExpediente()));
        reportParams.put("oficinaRegional", "");
        reportParams.put("direccionOR", "");
        reportParams.put("telefonosOR", "");
        reportParams.put("personaRecepcionVisita", prsonarcpcion);
        reportParams.put("dniPersonaRecepcionVisita", dniprsonarcpcion);
        reportParams.put("relacionAgente", rlacionagnt);
        return reportParams;
    }
    /* OSINE791 - RSIS17 - Fin */
    /* OSINE791 - RSIS19 - Inicio */
    @RequestMapping(value = "/getResultadoSupervisionDsr", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getResultadoSupervisionDsr(SupervisionFilter supervisionfilter, HttpSession session, HttpServletRequest request) {
    LOG.info("getResultadoSupervisionDsr");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {            
            SupervisionDTO supervision = new SupervisionDTO();
            List<SupervisionDTO> lista = supervisionServiceNeg.buscarSupervision(supervisionfilter);
            if(!lista.isEmpty()){
                supervision = lista.get(Constantes.PRIMERO_EN_LISTA);
            }
            retorno.put("supervision", supervision);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put(ConstantesWeb.VV_MENSAJE, "ok");

        } catch (Exception e) {

            LOG.error("Error en getResultadoSupervisionDsr", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());

        }
        return retorno;
    }
     /* OSINE791 - RSIS19 - Fin */
   
    @RequestMapping(value = "/validarExisteComentario", method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    Map<String, Object> validarExisteComentario(ObligacionDsrFilter filtro, HttpSession session, HttpServletRequest request) {
        LOG.info("findExpediente");
        Map<String, Object> retorno = new HashMap<String, Object>();
        long contInfraRegConComentario=0;
        long contInfraReg=0;
        try {
            List<ObligacionDsrDTO> listado = obligacionDsrNeg.listarObligacionDsr(filtro);
            List<ComentarioIncumplimientoDTO> listadoIncumplimiento;
            for (int i = 0; i < listado.size(); i++) {   //Recorrer el Listado de infracciones
				if (listado.get(i).getCodigoResSup().equals(Constantes.CODIGO_RESULTADO_INCUMPLE) ) { // Validar que sean solo de tipo Infraccion Incumplida
					contInfraReg++;
					//1) Obtener Id Infraccion
					DetalleSupervisionFilter filtroDet = new DetalleSupervisionFilter();
					filtroDet.setIdDetalleSupervision(listado.get(i).getIdDetalleSupervision());
					DetalleSupervisionDTO detaSupe = null;
			         List<DetalleSupervisionDTO> detalleSupervisionList = detalleSupervisionServiceNeg.listarDetalleSupervision(filtroDet);
			            if (!CollectionUtils.isEmpty(detalleSupervisionList)) {
			                detaSupe = detalleSupervisionList.get(0);    
			            }
			        //1)
					//2)
					EscenarioIncumplimientoFilter filtroEscenario = new EscenarioIncumplimientoFilter();
					filtroEscenario.setIdDetalleSupervision(filtroDet.getIdDetalleSupervision());
					//filtroEscenario.setIdInfraccion(detaSupe.getObligacion().getInfraccion().getIdInfraccion());
					filtroEscenario.setIdInfraccion(detaSupe.getInfraccion().getIdInfraccion());
		            List<EscenarioIncumplimientoDTO> listadoEscenario = new ArrayList<EscenarioIncumplimientoDTO>();
		            if (filtroEscenario.getIdInfraccion() != null && filtroEscenario.getIdInfraccion() > 0L) {
		            	listadoEscenario = escenarioIncumplimientoServiceNeg.listarEscenarioIncumplimiento(filtroEscenario);
		            }
		            //Si encuentra un escenario, buscara comentarios
		            if (listadoEscenario.size()>0) {
		            	 //Verifica si existe en los escenarios existen comentarios registrados
			            if(!CollectionUtils.isEmpty(listadoEscenario) &&  filtroEscenario.getIdDetalleSupervision()!=null &&   filtroEscenario.getIdInfraccion()!=null){
			                List<EscenarioIncumplidoDTO> listIncdo=escenarioIncumplidoServiceNeg.findEscenarioIncumplido(new EscenarioIncumplidoFilter(null,filtroEscenario.getIdDetalleSupervision(),null));
			                if(listIncdo.size()>0){
			                	contInfraRegConComentario++;
			                }
			            }
		            } 
		            //De caso contrario buscara si existen comentarios a nivel de infraccion
		            else {
		            	ComentarioIncumplimientoFilter filtroComentarioIncum = new 	ComentarioIncumplimientoFilter();
		            	filtroComentarioIncum.setIdDetalleSupervision(filtroDet.getIdDetalleSupervision());
		            	//filtroComentarioIncum.setIdInfraccion(detaSupe.getObligacion().getInfraccion().getIdInfraccion());
		            	filtroComentarioIncum.setIdInfraccion(detaSupe.getInfraccion().getIdInfraccion());
		            	listadoIncumplimiento = comentarioIncumplimientoServiceNeg.listarComentarioIncumplimiento(filtroComentarioIncum);
		            	   if(!CollectionUtils.isEmpty(listadoIncumplimiento) &&  filtroComentarioIncum.getIdDetalleSupervision()!=null &&  filtroComentarioIncum.getIdInfraccion()!=null){
							List<ComentarioDetSupervisionDTO> lstComenDetSup=comentarioDetSupervisionServiceNeg.findComentarioDetSupervision(new ComentarioDetSupervisionFilter(null,filtroComentarioIncum.getIdDetalleSupervision(),filtroComentarioIncum.getIdEsceIncumplimiento()));
						    	if(lstComenDetSup.size()>0){
						    		contInfraRegConComentario++;
						    	}
		            	   }
		            }
					//2)
				}
			}
            if (contInfraReg==contInfraRegConComentario){
            	 retorno.put("existenComentarioRegistrados", "Si");
            }
            else {
                retorno.put("existenComentarioRegistrados", "No");
            }
        } catch (Exception ex) {
            LOG.error("Error findObligacionesListado", ex);

        }
        return retorno;
    }
    
    //validar comentario obligaciones
    @RequestMapping(value = "/validarExisteComentarioObligacion", method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    Map<String, Object> validarExisteComentarioObligacion(ObligacionDsrFilter filtro, HttpSession session, HttpServletRequest request) {
        LOG.info("findExpediente");
        Map<String, Object> retorno = new HashMap<String, Object>();
        boolean flgRes=true;
        try {
            List<ObligacionDsrDTO> listado = obligacionDsrNeg.listarObligacionDsr(filtro);
            for (int i = 0; i < listado.size(); i++) {
                if (listado.get(i).getCodigoResSup().equals(Constantes.CODIGO_RESULTADO_OBSTACULIZADO) ) {
                    DetalleSupervisionFilter filtroDet = new DetalleSupervisionFilter();
                    filtroDet.setIdDetalleSupervision(listado.get(i).getIdDetalleSupervision());
                    List<DetalleSupervisionDTO> detalleSupervisionList = detalleSupervisionServiceNeg.listarDetalleSupervision(filtroDet);
                    if (!CollectionUtils.isEmpty(detalleSupervisionList)) {
                        DetalleSupervisionDTO reg = detalleSupervisionList.get(0);
                        if(StringUtil.isEmpty(reg.getComentario())){
                            flgRes=false;
                            break;
                        }
                    }
                }
            }
            if (flgRes){
            	 retorno.put("resultado", Constantes.ESTADO_ACTIVO);
            }else {
                retorno.put("resultado", Constantes.ESTADO_INACTIVO);
            }
        } catch (Exception ex) {
            LOG.error("Error findObligacionesListado", ex);

        }
        return retorno;
    }
    /*OSINE_SFS-791 - RSIS 04 - Inicio */
    @RequestMapping(value = "/generarResultadosDocSupervisionInicial", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> generarResultadosDocSupervisionInicial(GenerarResultadoDTO generarResultadoDTO ,HttpServletRequest request, HttpSession session) {
        LOG.info("generarResultadosDocSupervisionInicial - inicio");
        LOG.info("Idsupervision - inicio->"+generarResultadoDTO.getIdSupervision());
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
          /*SupervisionFilter filtro=new SupervisionFilter();
            filtro.setIdSupervision(generarResultadoDTO.getIdSupervision());
            filtro.setCartaVisita(generarResultadoDTO.getNroCarta());
            if(supervisionServiceNeg.verificarNroCartaVista(filtro)){*/
	            Map<String, Object> retornoServ = obligacionDsrNeg.generarResultadosDocSupervisionInicial(generarResultadoDTO, request, session);
	
	            retorno.put("registroDocumentoEjecucionMedida", retornoServ.get("registroDocumentoEjecucionMedida"));
	            retorno.put("supervision", retornoServ.get("supervision"));
	            retorno.put("correos", retornoServ.get("correos"));
	            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
	            retorno.put(ConstantesWeb.VV_MENSAJE, "OK");
           /*} else {
            	retorno.put("errorNroCarta", ConstantesWeb.VV_ERROR);
                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_DUPLICATE_CARTA_VISITA);
            }*/
        } catch (Exception ex) {
            retorno.put(ConstantesWeb.VV_MENSAJE, ex.getMessage());
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            LOG.error("Error generarResultadosDocSupervisionInicial", ex);
        }
        LOG.info("generarResultadosDocSupervisionInicial - fin");
        return retorno;
    }
    /*OSINE_SFS-791 - RSIS 04 - Fin */
    
    /*OSINE_SFS-791 - RSIS 03 - Inicio */
    @RequestMapping(value = "/guardarDatosInicialesSupervision", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> guardarDatosInicialesSupervision(SupervisionDTO supervisionDTO, HttpSession session, HttpServletRequest request) {
        LOG.info("guardarDatosInicialesSupervision - inicio");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
        	/*SupervisionFilter filtro=new SupervisionFilter();
        	filtro.setIdSupervision(supervisionDTO.getIdSupervision());
        	filtro.setCartaVisita(supervisionDTO.getCartaVisita());
        	if(supervisionServiceNeg.verificarNroCartaVista(filtro)){*/
	            SupervisionDTO supervision = supervisionServiceNeg.registrarDatosFinalesSupervision(supervisionDTO, Constantes.getUsuarioDTO(request));
	            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
	            retorno.put("supervision", supervision);
        	/*} else {
        		retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_DUPLICATE_CARTA_VISITA);
        	}*/
        } catch (Exception e) {
            LOG.error("Error en guardarDatosInicialesSupervision", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        LOG.info("guardarDatosInicialesSupervision - fin");
        return retorno;
    }
    
    @RequestMapping(value = "/generarResultadoSupervision", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> generarResultadosSupervision(GenerarResultadoDTO generarResultadoDTO ,HttpServletRequest request, HttpSession session) {
        LOG.info("generarResultadosSupervision - inicio");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            
            Map<String, Object> retornoRS = obligacionDsrNeg.generarResultadoSupervision(generarResultadoDTO, request, session);

            retorno.put("registroDocumentoSupervision", retornoRS.get("registroDocumentoSupervision"));
            retorno.put("supervision", retornoRS.get("supervision"));
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put(ConstantesWeb.VV_MENSAJE, "OK");
        } catch (Exception ex) {
            retorno.put(ConstantesWeb.VV_MENSAJE, ex.getMessage());
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            LOG.error("Error generarResultadosDocSupervisionInicial", ex);
        }
        LOG.info("generarResultadosSupervision - fin");
        return retorno;
    }
    /*OSINE_SFS-791 - RSIS 03 - Fin */
    
    /* OSINE791 – RSIS4 - Inicio */
    @RequestMapping(value = "/guardarDatosInicialSupervisionDsrObstaculizada", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> guardarDatosInicialSupervisionDsrObstaculizada(SupervisionDTO supervisionDTO, HttpSession session, HttpServletRequest request) {
        LOG.info("guardarDatosInicialSupervisionDsrObstaculizada - inicio");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
        	
        	/*SupervisionFilter filtro=new SupervisionFilter();
            filtro.setIdSupervision(supervisionDTO.getIdSupervision());
            filtro.setCartaVisita(supervisionDTO.getCartaVisita());
            if(supervisionServiceNeg.verificarNroCartaVista(filtro)){*/        	
	            Map<String, Object> map = obligacionDsrNeg.guardarDatosInicialSupervisionDsr(supervisionDTO, session, request);	            
	            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
	            retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_CREATE);
	            retorno.put("supervision", map.get("supervision"));
	            retorno.put("sup", map.get("sup"));
            /*} else {
                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_DUPLICATE_CARTA_VISITA);
            }*/
        } catch (Exception e) {
            LOG.error("Error en guardarDatosInicialSupervisionDsrObstaculizada", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        LOG.info("guardarDatosInicialSupervisionDsrObstaculizada - fin");
        return retorno;
    }
    /* OSINE791 – RSIS4 - Inicio */
    
    
    @RequestMapping(value = "/validarExisteArchivo", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> validarExisteArchivo(Long idSupervision,  String nombreArchivo) {
        LOG.info("validarExisteArchivo - inicio");
        Map<String, Object> retorno = new HashMap<String, Object>();
        boolean existe = false;
        try {
        	DetalleSupervisionFilter filtro = new DetalleSupervisionFilter();
        	filtro.setIdSupervision(idSupervision);
            List<DetalleSupervisionDTO> detalleSupervisionList = detalleSupervisionServiceNeg.listarDetalleSupervision(filtro);
            if(!CollectionUtils.isEmpty(detalleSupervisionList)){
            	for(DetalleSupervisionDTO detalleSupervision: detalleSupervisionList){
            		DocumentoAdjuntoFilter filtroDocAdj = new DocumentoAdjuntoFilter();
            		filtroDocAdj.setIdDetalleSupervision(detalleSupervision.getIdDetalleSupervision());
            		List<DocumentoAdjuntoDTO> documentoAdjuntoList = documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(filtroDocAdj);
            		if(!CollectionUtils.isEmpty(documentoAdjuntoList)){
            			for(DocumentoAdjuntoDTO docAdjunto: documentoAdjuntoList){
            				if(docAdjunto.getNombreArchivo().equals(nombreArchivo.toUpperCase())){
            					existe = true;
            					break;
            				}
            			}
            		}
            	}
            }
            retorno.put("existe", existe);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            if(existe)
            	retorno.put(ConstantesWeb.VV_MENSAJE, "El nombre del archivo del Medio Probatorio ya est&aacute; registrado en otra infracci&oacute;n, corregir.");

        } catch (Exception e) {
            LOG.error("Error en validarExisteArchivo", e);
            retorno.put("existe", existe);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        LOG.info("validarExisteArchivo - fin");
        return retorno;
    }
}
