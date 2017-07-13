/**
 * Resumen Objeto	: SupervisionController.java 
 * Descripción	        : Controla el flujo de datos del objeto Supervision 
 * Fecha de Creación	: 
 * PR de Creación	: OSINE_SFS-480 
 * Autor             	: GMD
 * ---------------------------------------------------------------------------------------------------
 * Modificaciones Motivo Fecha Nombre Descripción
 * ---------------------------------------------------------------------------------------------------
 * OSINE_SFS-480  |  11/05/2016   |  Hernán Torres Saenz         |     Cargar Lista de Obligaciones luego de Registrar la Fecha-Hora de Inicio del "Registro de Supervisión"
 * OSINE_791-066  |  28/10/2016   |   Zosimo Chaupis Santur      |     Modificar la funcionalidad de registro de datos de supervisión en la interfaz "Registrar Supervisión", el cual debe validar el registro obligatorio de adjuntos.
 * OSINE_791-068  |  31/10/2016   |   Zosimo Chaupis Santur      |     Crear el campo de resultado de supervisión en la sección de datos de supervisión de la interfaz Registrar Supervisión.
 * OSINE_791-069  |  02/11/2016   |   Zosimo Chaupis Santur      |     Implementar la funcionalidad para guardar el valor del campo Resultado de supervisión (REQF-0068) al registrar los datos de supervisión en la interfaz Registrar Supervisión.
 * 
*/
package gob.osinergmin.inpsweb.controller;

import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.inpsweb.service.business.DetalleEvaluacionServiceNeg;
import gob.osinergmin.inpsweb.service.business.DetalleSupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.business.DocumentoAdjuntoServiceNeg;
import gob.osinergmin.inpsweb.service.business.MaestroColumnaServiceNeg;
import gob.osinergmin.inpsweb.service.business.PersonaGeneralServiceNeg;
import gob.osinergmin.inpsweb.service.business.SupervisionServiceNeg;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.ConstantesWeb;
import gob.osinergmin.inpsweb.util.StringUtil;
import gob.osinergmin.mdicommon.domain.dto.CriterioDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleCriterioDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleEvaluacionDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.ObligacionDTO;
import gob.osinergmin.mdicommon.domain.dto.PersonaGeneralDTO;
import gob.osinergmin.mdicommon.domain.dto.PlantillaResultadoDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.TipificacionDTO;
import gob.osinergmin.mdicommon.domain.dto.TipificacionSancionDTO;
import gob.osinergmin.mdicommon.domain.ui.DetalleEvaluacionFilter;
import gob.osinergmin.mdicommon.domain.ui.DetalleSupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.DocumentoAdjuntoFilter;
import gob.osinergmin.mdicommon.domain.ui.PersonaGeneralFilter;
import gob.osinergmin.mdicommon.domain.ui.PlantillaResultadoFilter;
import gob.osinergmin.mdicommon.domain.ui.SupervisionFilter;
/* OSINE_SFS-480 - RSIS 13 - Inicio */
import gob.osinergmin.mdicommon.domain.ui.OrdenServicioFilter;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.dto.OrdenServicioDTO;
/* OSINE_SFS-480 - RSIS 13 - Fin */
import gob.osinergmin.mdicommon.domain.ui.TipificacionFilter;
import gob.osinergmin.mdicommon.domain.ui.TipificacionSancionFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/supervision")
public class SupervisionController {

    private static final Logger LOG = LoggerFactory.getLogger(SupervisionController.class);
    @Inject
    private SupervisionServiceNeg supervisionServiceNeg;
    @Inject
    private DocumentoAdjuntoServiceNeg documentoServiceNeg;
    @Inject
    private PersonaGeneralServiceNeg personaGeneralServiceNeg;
    @Inject
    private MaestroColumnaServiceNeg maestroColumnaServiceNeg;
    @Inject
    private DetalleEvaluacionServiceNeg detalleEvaluacionServiceNeg; //mdiosesf - RSIS6
    @Inject
    private DetalleSupervisionServiceNeg detalleSupervisionServiceNeg; //ZCHAUPIS - RSIS66

    @RequestMapping(value = "/findDetalleSupervision", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> findDetalleSupervision(DetalleSupervisionFilter filtro, int rows, int page, HttpSession session, HttpServletRequest request) {
        LOG.info("findDetalleSupervision - inicio");
        Map<String, Object> retorno = new HashMap<String, Object>();
        int indiceInicial = -1;
        int indiceFinal = -1;
        try {

            List<DetalleSupervisionDTO> listadoPaginado = null;
            List<DetalleSupervisionDTO> listado = supervisionServiceNeg.findDetalleSupervision(filtro);

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
            LOG.error("Error findDetalleSupervision", ex);
        }
        LOG.info("findDetalleSupervision - fin");
        return retorno;
    }

    @RequestMapping(value = "/findTipificacion", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> findTipificacion(DetalleSupervisionFilter filtro, int rows, int page, HttpSession session, HttpServletRequest request) {
        LOG.info("findDetalleSupervision - inicio");

        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            int indiceInicial = -1;
            int indiceFinal = -1;
            List<DetalleSupervisionDTO> listado = supervisionServiceNeg.findDetalleSupervision(filtro);
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
            List<DetalleSupervisionDTO> listadoPaginado = listado.subList(indiceInicial > listado.size() ? listado.size() : indiceInicial, indiceFinal > listado.size() ? listado.size() : indiceFinal);

            retorno.put("total", numeroFilas);
            retorno.put("pagina", page);
            retorno.put("registros", contador);
            retorno.put("filas", listadoPaginado);
        } catch (Exception ex) {
            LOG.error("Error findDetalleSupervision", ex);
        }
        LOG.info("findDetalleSupervision - fin");
        return retorno;
    }

    @RequestMapping(value = "/cargarCriterio", method = RequestMethod.GET)
    public @ResponseBody
    List<CriterioDTO> cargarCriterio(Long idTipificacion, Long idCriterio, Long idObligacion, HttpSession session, HttpServletRequest request) {
        List<CriterioDTO> retorno = new ArrayList<CriterioDTO>();
        if (idTipificacion == -1) {
            idTipificacion = null;
        }
        if (idObligacion == -1) {
            idObligacion = null;
        }
        try {
            retorno = supervisionServiceNeg.listarCriterio(idObligacion, idTipificacion, idCriterio);
        } catch (Exception ex) {
            LOG.error("Error cargarCriterio", ex);
        }
        return retorno;
    }

    @RequestMapping(value = "/findCriterio", method = RequestMethod.GET) //mdiosesf-RSIS6
    public @ResponseBody
    Map<String, Object> findCriterio(String tipoDH, Long idDetalleSupervision, Long idTipificacion, Long idCriterio, Long idObligacion, int rows, int page, HttpSession session, HttpServletRequest request) {
        LOG.info("findCriterio - inicio");
        Map<String, Object> retorno = new HashMap<String, Object>();
        List<CriterioDTO> listaCriterioDTO;
        List<DetalleEvaluacionDTO> listaDetalleEvaluacionDTO;
        DetalleEvaluacionFilter detalleEvaluacionFilter = new DetalleEvaluacionFilter();
        try {
            if (idCriterio == -1) {
                idCriterio = null;
            }
            if (idTipificacion == -1) {
                idTipificacion = null;
            }
            if (idObligacion == -1) {
                idObligacion = null;
            }

            listaCriterioDTO = supervisionServiceNeg.listarCriterio(idObligacion, idTipificacion, idCriterio);
            Iterator<CriterioDTO> it = listaCriterioDTO.iterator();
            while (it.hasNext()) {
                CriterioDTO criterio = it.next();
                criterio.setDescripcionResultado(Constantes.CONSTANTE_VACIA);
                detalleEvaluacionFilter.setIdDetalleSupervision(idDetalleSupervision);
                detalleEvaluacionFilter.setIdTipificacion(idTipificacion);
                if (criterio != null && criterio.getIdCriterio() != null) {
                    detalleEvaluacionFilter.setIdCriterio(criterio.getIdCriterio());
                }
                detalleEvaluacionFilter.setIdObligacion(idObligacion);
                listaDetalleEvaluacionDTO = detalleEvaluacionServiceNeg.findDetalleEvaluacion(detalleEvaluacionFilter);
                if (listaDetalleEvaluacionDTO != null) {
                    if (listaDetalleEvaluacionDTO.size() != 0) {
                        criterio.setDescripcionResultado(listaDetalleEvaluacionDTO.get(0).getDescripcionResultado());
                        if (tipoDH.equals(Constantes.SUPERVISION_MODO_CONSULTA)) {
                            if (criterio.getDescripcionResultado().equals(Constantes.CONSTANTE_VACIA)) {
                                it.remove();
                            }
                        }
                    } else {
                        if (tipoDH.equals(Constantes.SUPERVISION_MODO_CONSULTA)) {
                            it.remove();
                        }
                    }
                }
            }

            int indiceInicial = -1;
            int indiceFinal = -1;
            Long contador = 0L;
            if (listaCriterioDTO != null) {
                contador = (long) listaCriterioDTO.size();
            }
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
            List<CriterioDTO> listadoPaginado = listaCriterioDTO.subList(indiceInicial > listaCriterioDTO.size() ? listaCriterioDTO.size() : indiceInicial, indiceFinal > listaCriterioDTO.size() ? listaCriterioDTO.size() : indiceFinal);
            retorno.put("total", numeroFilas);
            retorno.put("pagina", page);
            retorno.put("registros", contador);
            retorno.put("filas", listadoPaginado);
        } catch (Exception ex) {
            LOG.error("Error findCriterio", ex);
        }
        LOG.info("findCriterio - fin");
        return retorno;
    }

    @RequestMapping(value = "/cargarSancionEspecifica", method = RequestMethod.GET)
    public @ResponseBody
    List<DetalleCriterioDTO> cargarSancionEspecifica(Long idCriterio, Long idDetalleCriterio, HttpSession session, HttpServletRequest request) {
        List<DetalleCriterioDTO> retorno = new ArrayList<DetalleCriterioDTO>();
        try {
            retorno = supervisionServiceNeg.listarDetalleCriterio(idCriterio, idDetalleCriterio);
        } catch (Exception ex) {
            LOG.error("Error cargarSancionEspecifica", ex);
        }
        return retorno;
    }

    @RequestMapping(value = "/cargarTipoSancion", method = RequestMethod.GET)
    public @ResponseBody
    TipificacionDTO cargarTipoSancion(TipificacionFilter filtro, HttpSession session, HttpServletRequest request) {
        TipificacionDTO retorno = new TipificacionDTO();
        List<TipificacionSancionDTO> listaTipificacionSancion = null;
        Long nivel = null;
        String otrasSanciones = "";
        try {
            List<TipificacionDTO> listaRetorno = supervisionServiceNeg.listarTipificacion(filtro);
            if (!listaRetorno.isEmpty()) {
                retorno = listaRetorno.get(Constantes.PRIMERO_EN_LISTA);
                List<MaestroColumnaDTO> listaMaestro = maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_NIVEL_TIPIFICACION, Constantes.APLICACION_MYC);
                if (!listaMaestro.isEmpty()) {
                    nivel = listaMaestro.get(Constantes.PRIMERO_EN_LISTA).getIdMaestroColumna();
                    TipificacionSancionFilter tipiSancionFiltro = new TipificacionSancionFilter();
                    tipiSancionFiltro.setIdTipificacion(filtro.getIdTipificacion());
                    tipiSancionFiltro.setNivel(nivel);
                    listaTipificacionSancion = supervisionServiceNeg.listarTipificacionSancion(tipiSancionFiltro);
                    if (!listaTipificacionSancion.isEmpty()) {
                        for (TipificacionSancionDTO tipiSancion : listaTipificacionSancion) {
                            if (tipiSancion.getTipoSancion() != null && tipiSancion.getTipoSancion().getIdTipoSancion() != null) {
                                otrasSanciones += tipiSancion.getTipoSancion().getAbreviatura() + ",";
                            }
                        }
                        if (!Constantes.CONSTANTE_VACIA.equals(otrasSanciones.trim())) {
                            otrasSanciones = otrasSanciones.substring(0, (otrasSanciones.length() - 1));
                        }
                        retorno.setOtrasSanciones(otrasSanciones);
                    }
                }
            }
        } catch (Exception ex) {
            LOG.error("Error cargarTipoSancion", ex);
        }
        return retorno;
    }

    @RequestMapping(value = "/guardarDatosSupervision", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    Map<String, Object> guardarDatosSupervision(SupervisionDTO supervisionDTO, HttpSession session, HttpServletRequest request) {
        LOG.info("guardarDatosSupervision - inicio");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            supervisionDTO.setEstado(Constantes.ESTADO_ACTIVO);
            if (supervisionDTO.getMotivoNoSupervision() != null && supervisionDTO.getMotivoNoSupervision().getIdMotivoNoSupervision() == -1) {
                supervisionDTO.setMotivoNoSupervision(null);
            }
            LOG.info("flagsupervision : |"+supervisionDTO.getFlagSupervision()+"| y iteracion |"+supervisionDTO.getOrdenServicioDTO().getIteracion()+"|");
            if (supervisionDTO.getFlagSupervision().equals(Constantes.ESTADO_ACTIVO) && supervisionDTO.getOrdenServicioDTO().getIteracion().equals(new Long(Constantes.SUPERVISION_PRIMERA_ITERACION))) {
                LOG.info("entro en if de flag activo");
                DocumentoAdjuntoFilter filter = new DocumentoAdjuntoFilter();
                filter.setIdSupervision(supervisionDTO.getIdSupervision());
                filter.setFlagSoloAdjuntos(Constantes.ESTADO_ACTIVO);
                List<DocumentoAdjuntoDTO> ltaDocumentos = documentoServiceNeg.listarPghDocumentoAdjunto(filter);
                LOG.info("cantidad de archivos es : |"+ltaDocumentos.size()+"|");
                if (ltaDocumentos.size() > 0) {
                    /* OSINE_SFS-480 - RSIS 13 - Inicio */
                    SupervisionDTO datosSupervision = cargarDatosSupervision(supervisionDTO, Constantes.getUsuarioDTO(request));
                    supervisionDTO.setIdSupervision(datosSupervision.getIdSupervision());
                    retorno.put("sup", datosSupervision);
                    /* OSINE_SFS-480 - RSIS 13 - Fin */
                    SupervisionDTO supervision = supervisionServiceNeg.registrarDatosSupervision(supervisionDTO, Constantes.getUsuarioDTO(request));
                    retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
                    retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_CREATE);
                    retorno.put("supervision", supervision);
                } else {
                    LOG.error("ERROR NO SE PUEDE GUARDAR LA SUPERVISION PORQUE NO TIENE NINGUN ADJUNTO ASOCIADO");
                    retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                    retorno.put(ConstantesWeb.VV_MENSAJE, "Debe registrar al menos un adjunto de supervisi&oacute;n, verificar");
                }

            } else {
                LOG.info("no entro en if de flag activo");
                /* OSINE_SFS-480 - RSIS 13 - Inicio */
                SupervisionDTO datosSupervision = cargarDatosSupervision(supervisionDTO, Constantes.getUsuarioDTO(request));
                supervisionDTO.setIdSupervision(datosSupervision.getIdSupervision());
                retorno.put("sup", datosSupervision);
                /* OSINE_SFS-480 - RSIS 13 - Fin */
                SupervisionDTO supervision = supervisionServiceNeg.registrarDatosSupervision(supervisionDTO, Constantes.getUsuarioDTO(request));
                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
                retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_CREATE);
                retorno.put("supervision", supervision);
            }      
            
        } catch (Exception e) {
            LOG.error("Error en guardarDatosSupervision", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        LOG.info("guardarDatosSupervision - fin");
        return retorno;
    }

    /* OSINE_SFS-480 - RSIS 13 - Inicio */
    public SupervisionDTO cargarDatosSupervision(SupervisionDTO supervisionDTO, UsuarioDTO usuarioDTO) {
        LOG.info("cargarDatosSupervision - inicio");
        try {
            SupervisionFilter filtro = new SupervisionFilter();
            filtro.setIdOrdenServicio(supervisionDTO.getOrdenServicioDTO().getIdOrdenServicio());
            List<SupervisionDTO> lista = supervisionServiceNeg.buscarSupervision(filtro);

            //if (lista.isEmpty()) {
            LOG.info("FLAG ES : |"+supervisionDTO.getFlagSupervision()+"| Y ITERACIONES : |"+supervisionDTO.getOrdenServicioDTO().getIteracion()+"|");
             if (supervisionDTO.getFlagSupervision().equals(Constantes.ESTADO_INACTIVO) && supervisionDTO.getOrdenServicioDTO().getIteracion().equals(Constantes.SUPERVISION_PRIMERA_ITERACION)) {
                    lista = supervisionServiceNeg.buscarSupervision(new SupervisionFilter(supervisionDTO.getIdSupervision()));
                    supervisionDTO = lista.get(Constantes.PRIMERO_EN_LISTA);
             }else{
               if (supervisionDTO.getOrdenServicioDTO() != null && supervisionDTO.getOrdenServicioDTO().getIdOrdenServicio() != null) {
                    //traemos datos orden servicio
                    List<OrdenServicioDTO> listaOrdenServicio = supervisionServiceNeg.listarOrdenServicio(new OrdenServicioFilter(supervisionDTO.getOrdenServicioDTO().getIdOrdenServicio()));
                    if (!listaOrdenServicio.isEmpty()) {
                        supervisionDTO.setOrdenServicioDTO(listaOrdenServicio.get(Constantes.PRIMERO_EN_LISTA));
                    }
                    DetalleSupervisionFilter detallefilter = new DetalleSupervisionFilter();
                    detallefilter.setIdSupervision(supervisionDTO.getIdSupervision());
                    LOG.info("ENVIANDO ID SUPERVISION |"+supervisionDTO.getIdSupervision()+"|");
                    List<DetalleSupervisionDTO> ltaDetalleSupervision = detalleSupervisionServiceNeg.listarDetalleSupervision(detallefilter);
                    LOG.info("LA LISTA TRAJO |"+ltaDetalleSupervision.size()+"|");
                    if (ltaDetalleSupervision.size() > Constantes.LISTA_VACIA  || supervisionDTO.getFlagSupervision().equals(Constantes.ESTADO_INACTIVO) ) {
                       LOG.info("YA EXISTEN DETALLE SUPERVISION DE OBLIGACIONES");
                    } else {
                       supervisionDTO = supervisionServiceNeg.registrarSupervisionBloque(supervisionDTO, usuarioDTO);  // AQUI
                    }
                    //traemos los datos de la supervision
                    lista = supervisionServiceNeg.buscarSupervision(new SupervisionFilter(supervisionDTO.getIdSupervision()));
                    supervisionDTO = lista.get(Constantes.PRIMERO_EN_LISTA);
                }  
             }
                
           // }
        } catch (Exception e) {
            LOG.error("Error en cargarDatosSupervision", e);
        }
        LOG.info("cargarDatosSupervision - fin");
        return supervisionDTO;
    }
    /* OSINE_SFS-480 - RSIS 13 - Fin */

    @RequestMapping(value = "/guardarPersonaAtiende", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    Map<String, Object> guardarDatosPersonaAtiende(SupervisionDTO supervisionDTO, HttpSession session, HttpServletRequest request) {
        LOG.info("guardarDatosPersonaAtiende - inicio");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            supervisionDTO.setEstado(Constantes.ESTADO_ACTIVO);
            SupervisionDTO supervision = supervisionServiceNeg.registrarPersonaAtiende(supervisionDTO, Constantes.getUsuarioDTO(request));
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_CREATE);
            retorno.put("supervision", supervision);
        } catch (Exception e) {
            LOG.error("Error en guardarDatosPersonaAtiende", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        LOG.info("guardarDatosPersonaAtiende - fin");
        return retorno;
    }

    @RequestMapping(value = "/cambiarSupervision", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> cambiarSupervision(SupervisionDTO supervisionDTO, HttpSession session, HttpServletRequest request) {
        LOG.info("cambiarSupervision - inicio");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            supervisionDTO.setEstado(Constantes.ESTADO_ACTIVO);
            SupervisionDTO supervision = supervisionServiceNeg.cambiarSupervision(supervisionDTO, Constantes.getUsuarioDTO(request));
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_CREATE + "Supervisión");
            retorno.put("supervision", supervision);
        } catch (Exception e) {
            LOG.error("Error en cambiarSupervision", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        LOG.info("cambiarSupervision - fin");
        return retorno;
    }

    @RequestMapping(value = "/guardarDescHallazgo", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> guardarDescHallazgo(DetalleSupervisionDTO detalleSupervisionDTO, HttpSession session, HttpServletRequest request) {
        LOG.info("guardarDescHallazgo");
        Map<String, Object> retorno = new HashMap<String, Object>();
        List<DocumentoAdjuntoDTO> listaDocumento = null;
        if (detalleSupervisionDTO.getTipificacion().getIdTipificacion() == -1) {
            detalleSupervisionDTO.setTipificacion(null);
        }
        if (detalleSupervisionDTO.getCriterio().getIdCriterio() == -1) {
            detalleSupervisionDTO.setCriterio(null);
        }
        DocumentoAdjuntoFilter filtro = new DocumentoAdjuntoFilter();
        filtro.setIdDetalleSupervision(detalleSupervisionDTO.getIdDetalleSupervision());
        try {
            detalleSupervisionDTO.setEstado(Constantes.ESTADO_ACTIVO);
            DetalleSupervisionDTO detalleSupervisionAnt = supervisionServiceNeg.findDetalleSupervision(new DetalleSupervisionFilter(detalleSupervisionDTO.getIdDetalleSupervision()))
                    .get(Constantes.PRIMERO_EN_LISTA);
            DetalleSupervisionDTO detalleSupervision = supervisionServiceNeg.actualizarDetalleSupervision(detalleSupervisionDTO, Constantes.getUsuarioDTO(request));
            if (!detalleSupervisionAnt.getFlagResultado().equals(detalleSupervision.getFlagResultado())) {
                listaDocumento = documentoServiceNeg.listarPghDocumentoAdjunto(filtro);
                if (!listaDocumento.isEmpty()) {
                    for (DocumentoAdjuntoDTO documento : listaDocumento) {
                        documentoServiceNeg.eliminarPghDocumentoAdjunto(documento);
                    }
                }
            }
            //validamos el registrado
            if ((detalleSupervision.getTipificacion() != null && detalleSupervision.getTipificacion().getIdTipificacion() != null)
                    || (detalleSupervision.getDescripcionResultado() != null && !Constantes.CONSTANTE_VACIA.equals(detalleSupervision.getDescripcionResultado().trim()))) {
                detalleSupervision.setFlagRegistrado(Constantes.FLAG_REGISTRADO_SI);
            } else {
                //buscamos los documentos adjuntos del detalle supervision
                listaDocumento = documentoServiceNeg.listarPghDocumentoAdjunto(filtro);
                if (!listaDocumento.isEmpty()) {
                    detalleSupervision.setFlagRegistrado(Constantes.FLAG_REGISTRADO_SI);
                } else {
                    detalleSupervision.setFlagRegistrado(Constantes.FLAG_REGISTRADO_NO);
                }
            }
            detalleSupervision.setEstado(Constantes.ESTADO_ACTIVO);
            detalleSupervision = supervisionServiceNeg.actualizarDetalleSupervision(detalleSupervision, Constantes.getUsuarioDTO(request));
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_CREATE + " El comentario del hallazgo");
            retorno.put("detalleSupervision", detalleSupervision);
        } catch (Exception e) {
            LOG.error("Error en guardarDescHallazgo", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }

    @RequestMapping(value = "/guardarDetalleEvaluacion", method = RequestMethod.POST) //mdiosesf - RSIS6
    public @ResponseBody
    Map<String, Object> guardarDetalleEvaluacion(String tipoCumple, Long idDetalleSupervision, ObligacionDTO obligacionDTO, HttpSession session, HttpServletRequest request) {
        LOG.info("guardarDetalleEvaluacion");
        Map<String, Object> retorno = new HashMap<String, Object>();
        DocumentoAdjuntoFilter filtro = new DocumentoAdjuntoFilter();
        DetalleSupervisionDTO detalleSupervisionDTO = new DetalleSupervisionDTO();
        List<DocumentoAdjuntoDTO> listaDocumento = null;
        DetalleEvaluacionFilter detalleEvaluacionFilter = null;
        List<DetalleEvaluacionDTO> listaDetalleEvaluacion = null;
        boolean existe = false;
        try {
            if (obligacionDTO != null) {
                List<DetalleEvaluacionDTO> lista = obligacionDTO.getListaDEvaluacion();
                if (lista != null) {
                    if (idDetalleSupervision != -1) {
                        filtro.setIdDetalleSupervision(idDetalleSupervision);
                    } else {
                        filtro.setIdDetalleSupervision(null);
                    }
                    detalleSupervisionDTO.setObligacion(obligacionDTO);
                    detalleSupervisionDTO.setIdDetalleSupervision(idDetalleSupervision);
                    detalleSupervisionDTO.setTipificacion(null);
                    detalleSupervisionDTO.setCriterio(null);
                    detalleSupervisionDTO.setEstado(Constantes.ESTADO_ACTIVO);
                    detalleSupervisionDTO.setFlagResultado(tipoCumple);

                    DetalleSupervisionDTO detalleSupervisionAnt = supervisionServiceNeg.findDetalleSupervision(new DetalleSupervisionFilter(idDetalleSupervision)).get(Constantes.PRIMERO_EN_LISTA);
                    SupervisionDTO supervisionDTO = new SupervisionDTO();
                    supervisionDTO.setIdSupervision(detalleSupervisionAnt.getSupervision().getIdSupervision());
                    detalleSupervisionDTO.setSupervision(supervisionDTO);
                    if (detalleSupervisionAnt.getIdDetalleSupervisionAnt() != null) {
                        detalleSupervisionDTO.setIdDetalleSupervisionAnt(detalleSupervisionAnt.getIdDetalleSupervisionAnt());
                    }
                    if (!detalleSupervisionAnt.getFlagResultado().equals(detalleSupervisionDTO.getFlagResultado())) {
                        listaDocumento = documentoServiceNeg.listarPghDocumentoAdjunto(filtro);
                        if (!listaDocumento.isEmpty()) {
                            for (DocumentoAdjuntoDTO documento : listaDocumento) {
                                documentoServiceNeg.eliminarPghDocumentoAdjunto(documento);
                            }
                        }
                    }

                    for (DetalleEvaluacionDTO detalleEvaluacionDTO : obligacionDTO.getListaDEvaluacion()) {
                        detalleEvaluacionFilter = new DetalleEvaluacionFilter();
                        detalleEvaluacionFilter.setIdDetalleSupervision(idDetalleSupervision);
                        if (detalleEvaluacionDTO.getTipificacion() != null && detalleEvaluacionDTO.getTipificacion().getIdTipificacion() != null) {
                            detalleEvaluacionFilter.setIdTipificacion(detalleEvaluacionDTO.getTipificacion().getIdTipificacion());
                        }
                        if (detalleEvaluacionDTO.getCriterio() != null && detalleEvaluacionDTO.getCriterio().getIdCriterio() != null) {
                            detalleEvaluacionFilter.setIdCriterio(detalleEvaluacionDTO.getCriterio().getIdCriterio());
                        }
                        listaDetalleEvaluacion = detalleEvaluacionServiceNeg.findDetalleEvaluacion(detalleEvaluacionFilter);
                        if (listaDetalleEvaluacion != null) {
                            existe = false;
                            if (listaDetalleEvaluacion.size() != 0) {
                                existe = true;
                            }
                        }
                        detalleEvaluacionDTO.setFlagResultado(tipoCumple);
                        if (detalleEvaluacionDTO.getTipificacion() != null && detalleEvaluacionDTO.getTipificacion().getTieneCriterio() != 1) {
                            detalleSupervisionDTO.setEstado(Constantes.ESTADO_ACTIVO);
                            if ((detalleEvaluacionDTO.getTipificacion() != null && detalleEvaluacionDTO.getTipificacion().getIdTipificacion() != null) || detalleEvaluacionDTO.getDescripcionResultado() != null && !Constantes.CONSTANTE_VACIA.equals(detalleEvaluacionDTO.getDescripcionResultado().trim())) {
                                detalleSupervisionDTO.setFlagRegistrado(Constantes.FLAG_REGISTRADO_SI);
                            } else {
                                listaDocumento = documentoServiceNeg.listarPghDocumentoAdjunto(filtro);
                                if (!listaDocumento.isEmpty()) {
                                    detalleSupervisionDTO.setFlagRegistrado(Constantes.FLAG_REGISTRADO_SI);
                                } else {
                                    detalleSupervisionDTO.setFlagRegistrado(Constantes.FLAG_REGISTRADO_NO);
                                }
                            }
                            if (!existe) {
                                detalleEvaluacionDTO.setIdDetalleEvaluacion(null);
                                detalleEvaluacionDTO.setFlagRegistrado(Constantes.FLAG_REGISTRADO_SI);
                                detalleEvaluacionDTO.setEstado(Constantes.ESTADO_ACTIVO);
                                detalleEvaluacionDTO = detalleEvaluacionServiceNeg.registrarDetalleEvaluacion(detalleEvaluacionDTO, Constantes.getUsuarioDTO(request));
                            } else {
                                detalleEvaluacionDTO.setFlagRegistrado(Constantes.FLAG_REGISTRADO_SI);
                                detalleEvaluacionDTO = detalleEvaluacionServiceNeg.actualizarDetalleEvaluacion(detalleEvaluacionDTO, Constantes.getUsuarioDTO(request));
                            }
                        }
                    }
                    detalleSupervisionDTO = supervisionServiceNeg.actualizarDetalleSupervision(detalleSupervisionDTO, Constantes.getUsuarioDTO(request));
                    retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
                    retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_CREATE + " El comentario del hallazgo");
                    retorno.put("detalleEvaluacion", detalleSupervisionDTO);
                } else {
                    retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                    retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_ERROR_DEVALUACION);
                    retorno.put("detalleEvaluacion", null);
                }
            }
        } catch (Exception e) {
            LOG.error("Error en guardarDetalleEvaluacion", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }

    @RequestMapping(value = "/guardarEvalDescargo", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> guardarEvalDescargo(DetalleSupervisionDTO detalleSupervisionDTO, HttpSession session, HttpServletRequest request) {
        LOG.info("guardarEvalDescargo");
        Map<String, Object> retorno = new HashMap<String, Object>();
        List<DocumentoAdjuntoDTO> listaDocumento = null;
        DocumentoAdjuntoFilter filtro = new DocumentoAdjuntoFilter();
        filtro.setIdDetalleSupervision(detalleSupervisionDTO.getIdDetalleSupervision());
        if (detalleSupervisionDTO.getTipificacion().getIdTipificacion() == -1) {
            detalleSupervisionDTO.setTipificacion(null);
        }
        if (detalleSupervisionDTO.getCriterio().getIdCriterio() == -1) {
            detalleSupervisionDTO.setCriterio(null);
        }
        try {
            detalleSupervisionDTO.setEstado(Constantes.ESTADO_ACTIVO);
            DetalleSupervisionDTO detalleSupervisionAnt = supervisionServiceNeg.findDetalleSupervision(new DetalleSupervisionFilter(detalleSupervisionDTO.getIdDetalleSupervision()))
                    .get(Constantes.PRIMERO_EN_LISTA);
            DetalleSupervisionDTO detalleSupervision = supervisionServiceNeg.actualizarDetalleSupervision(detalleSupervisionDTO, Constantes.getUsuarioDTO(request));
            if (!detalleSupervisionAnt.getFlagResultado().equals(detalleSupervision.getFlagResultado())) {
                listaDocumento = documentoServiceNeg.listarPghDocumentoAdjunto(filtro);
                if (!listaDocumento.isEmpty()) {
                    for (DocumentoAdjuntoDTO documento : listaDocumento) {
                        documentoServiceNeg.eliminarPghDocumentoAdjunto(documento);
                    }
                }
            }
            //validamos el registrado
            if (detalleSupervision.getDescripcionResultado() != null && !Constantes.CONSTANTE_VACIA.equals(detalleSupervision.getDescripcionResultado().trim())) {
                detalleSupervision.setFlagRegistrado(Constantes.FLAG_REGISTRADO_SI);
            } else {
                //buscamos los documentos adjuntos del detalle supervision
                listaDocumento = documentoServiceNeg.listarPghDocumentoAdjunto(filtro);
                if (!listaDocumento.isEmpty()) {
                    detalleSupervision.setFlagRegistrado(Constantes.FLAG_REGISTRADO_SI);
                } else {
                    detalleSupervision.setFlagRegistrado(Constantes.FLAG_REGISTRADO_NO);
                }
            }
            detalleSupervision.setEstado(Constantes.ESTADO_ACTIVO);
            detalleSupervision = supervisionServiceNeg.actualizarDetalleSupervision(detalleSupervision, Constantes.getUsuarioDTO(request));
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_CREATE + " la evaluación de descargo");
            retorno.put("detalleSupervision", detalleSupervision);
        } catch (Exception e) {
            LOG.error("Error en guardarEvalDescargo", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }

    @RequestMapping(value = "/buscarPersona", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> buscarPersona(PersonaGeneralFilter filtro, HttpServletRequest request, HttpSession session, Model model) {
        LOG.info("buscarPersona");
        String mensaje;
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            List<PersonaGeneralDTO> listaPersonaGeneral = personaGeneralServiceNeg.find(filtro);
            PersonaGeneralDTO persona = null;
            if (!listaPersonaGeneral.isEmpty()) {
                persona = listaPersonaGeneral.get(Constantes.PRIMERO_EN_LISTA);
                mensaje = "Encontr\u00f3 : " + persona.getNombresPersona() + " " + persona.getApellidoPaternoPersona() + " " + persona.getApellidoMaternoPersona();
            } else {
                mensaje = "No se ha encontrado persona";
            }
            retorno.put("persona", persona);
            retorno.put(ConstantesWeb.VV_MENSAJE, mensaje);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
        }
        return retorno;
    }

    @RequestMapping(value = "/obtenerClasificacion", method = RequestMethod.GET)
    public @ResponseBody
    List<MaestroColumnaDTO> obtenerClasificacion() {
        LOG.info("obtenerClasificacion - ObligacionesController");
        List<MaestroColumnaDTO> lstClasificacion = maestroColumnaServiceNeg
                .findByDominioAplicacion(Constantes.DOMINIO_OBLIG_CLASIF,
                Constantes.APLICACION_OBLIGACIONES);
        return lstClasificacion;
    }

    @RequestMapping(value = "/obtenerCriticidad", method = RequestMethod.GET)
    public @ResponseBody
    List<MaestroColumnaDTO> obtenerCriticidad() {
        LOG.info("obtenerCriticidad - ObligacionesController");
        List<MaestroColumnaDTO> lstClasificacion = maestroColumnaServiceNeg
                .findByDominioAplicacion(Constantes.DOMINIO_OBLIG_CRITIC,
                Constantes.APLICACION_OBLIGACIONES);
        return lstClasificacion;
    }

    @RequestMapping(value = "/obtenerCumple", method = RequestMethod.GET)
    public @ResponseBody
    List<String> obtenerCumple() {
        LOG.info("obtenerCriticidad - ObligacionesController");
        List<String> lstClasificacion = new ArrayList<String>();
        lstClasificacion.add(Constantes.DETALLE_SUPERVISION_INCUMPLE);
        lstClasificacion.add(Constantes.DETALLE_SUPERVISION_CUMPLE);
        return lstClasificacion;
    }

    @RequestMapping(value = "/obtenerRegistrado", method = RequestMethod.GET)
    public @ResponseBody
    List<String> obtenerRegistrado() {
        List<String> lstClasificacion = new ArrayList<String>();
        lstClasificacion.add(Constantes.DETALLE_SUPERVISION_NOREGISTRADO);
        lstClasificacion.add(Constantes.DETALLE_SUPERVISION_REGISTRADO);
        return lstClasificacion;
    }

    @SuppressWarnings("unused")
    @RequestMapping(value = "/generarResultados", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> generarResultados(PlantillaResultadoFilter filtro, int rows, int page, HttpSession session, HttpServletRequest request) {
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            SupervisionFilter supervisionFilter = new SupervisionFilter();
            supervisionFilter.setIdSupervision(filtro.getIdSupervision());
            List<SupervisionDTO> supervisionDTO = supervisionServiceNeg.buscarSupervision(supervisionFilter);
            if (supervisionDTO != null) {
                filtro.setFlagSupervision(supervisionDTO.get(0).getFlagSupervision());
            }
            DetalleSupervisionFilter filtroSupervicion = new DetalleSupervisionFilter();
            filtroSupervicion.setFlagResultado(Constantes.DETALLE_SUPERVISION_INCUMPLE.toString());
            filtroSupervicion.setIdSupervision(filtro.getIdSupervision());
            List<DetalleSupervisionDTO> detalle = supervisionServiceNeg.findDetalleSupervision(filtroSupervicion);
            if (detalle == null || detalle.isEmpty()) {
                filtro.setFlagResultado(Constantes.DETALLE_SUPERVISION_CUMPLE.toString());
            } else {
                filtro.setFlagResultado(Constantes.DETALLE_SUPERVISION_INCUMPLE.toString());
            }
            if (filtro.getIteracion() != 2) {
                filtro.setIdTipoAsignacion(null);
            }
            if (filtro.getFlagSupervision().equals(Constantes.FLAG_NO_SUPERVISION)) {
                filtro.setFlagResultado(null);
            }

            List<PlantillaResultadoDTO> listado = supervisionServiceNeg.listarPlantillaResultado(filtro);
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
            List<PlantillaResultadoDTO> listadoPaginado = listado.subList(indiceInicial > listado.size() ? listado.size() : indiceInicial, indiceFinal > listado.size() ? listado.size() : indiceFinal);


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

    @RequestMapping(value = "/findTipificacionMultiple", method = RequestMethod.GET) //mdiosesf-RSIS6
    public @ResponseBody
    Map<String, Object> findTipificacionMultiple(String tipoDH, String tipo, Long idDetalleSupervision, Long idObligacion, Long idTipificacion, Long idCriterio, int rows, int page, HttpSession session, HttpServletRequest request) {
        LOG.info("findTipificacion - inicio");
        Map<String, Object> retorno = new HashMap<String, Object>();
        List<DetalleEvaluacionDTO> detalleEvaluacionDTO;
        DetalleEvaluacionFilter detalleEvaluacionFilter = new DetalleEvaluacionFilter();
        try {
            detalleEvaluacionFilter.setIdDetalleSupervision(idDetalleSupervision);
            detalleEvaluacionFilter.setIdTipificacion(idTipificacion);
            detalleEvaluacionFilter.setIdCriterio(idCriterio);
            detalleEvaluacionFilter.setIdObligacion(idObligacion);
            detalleEvaluacionFilter.setFlagResultado(tipo);
            if (tipo.equals(Constantes.CONSTANTE_VACIA) || tipo == null) {
                DetalleSupervisionDTO detalleSupervision = supervisionServiceNeg.findDetalleSupervision(new DetalleSupervisionFilter(idDetalleSupervision)).get(Constantes.PRIMERO_EN_LISTA);
                detalleEvaluacionFilter.setFlagResultado(detalleSupervision.getFlagResultado());
            }
            detalleEvaluacionDTO = detalleEvaluacionServiceNeg.listarDetalleEvaluacion(detalleEvaluacionFilter);
            Iterator<DetalleEvaluacionDTO> it = detalleEvaluacionDTO.iterator();
            while (it.hasNext()) {
                DetalleEvaluacionDTO registro = it.next();
                if (tipoDH.equals(Constantes.SUPERVISION_MODO_CONSULTA)) {
                    if (registro.getTipificacion() != null && registro.getTipificacion().getTieneCriterio() != null) {
                        if (registro.getTipificacion().getTieneCriterio().toString().equals(Constantes.DETALLE_SUPERVISION_INCUMPLE) && Constantes.CONSTANTE_VACIA.equals(registro.getDescripcionResultado().trim())) {
                            it.remove();
                        } else if (registro.getTipificacion().getTieneCriterio().toString().equals(Constantes.DETALLE_SUPERVISION_CUMPLE)) {
                            if (!verificarCriterioDescripcion(idObligacion, idDetalleSupervision, registro)) {
                                it.remove();
                            }
                        }
                    }
                } else if (tipoDH.equals(Constantes.SUPERVISION_MODO_REGISTRO)) {
                    if (registro.getTipificacion().getTieneCriterio().toString().equals(Constantes.DETALLE_SUPERVISION_INCUMPLE)) {
                        if (registro.getTipificacion() != null && registro.getTipificacion().getIdTipificacion() == null && Constantes.CONSTANTE_VACIA.equals(registro.getDescripcionResultado().trim())) {
                            it.remove();
                        }
                    }
                }
            }

            int indiceInicial = -1;
            int indiceFinal = -1;
            Long contador = 0L;
            if (detalleEvaluacionDTO != null) {
                contador = (long) detalleEvaluacionDTO.size();
            }
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
            List<DetalleEvaluacionDTO> listadoPaginado = detalleEvaluacionDTO.subList(indiceInicial > detalleEvaluacionDTO.size() ? detalleEvaluacionDTO.size() : indiceInicial, indiceFinal > detalleEvaluacionDTO.size() ? detalleEvaluacionDTO.size() : indiceFinal);
            retorno.put("total", numeroFilas);
            retorno.put("pagina", page);
            retorno.put("registros", contador);
            retorno.put("filas", listadoPaginado);

        } catch (Exception ex) {
            LOG.error("Error findTipificacion", ex);
        }
        LOG.info("findTipificacion - fin");
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
            List<CriterioDTO> listaCriterioDTO = supervisionServiceNeg.listarCriterio(idObligacion, registro.getTipificacion().getIdTipificacion(), idCriterio);
            if (listaCriterioDTO != null) {
                for (CriterioDTO criterio : listaCriterioDTO) {
                    detalleEvaluacionFilter.setIdDetalleSupervision(idDetalleSupervision);
                    detalleEvaluacionFilter.setIdTipificacion(idTipificacion);
                    detalleEvaluacionFilter.setIdCriterio(criterio.getIdCriterio());
                    detalleEvaluacionFilter.setIdObligacion(idObligacion);
                    listaDetalleEvaluacionDTO = detalleEvaluacionServiceNeg.findDetalleEvaluacion(detalleEvaluacionFilter);
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

    @RequestMapping(value = "/findTipifica", method = RequestMethod.GET)
    public @ResponseBody
    List<TipificacionDTO> findTipifica(TipificacionFilter tipificacionFilter) {
        LOG.info("findDetalleSupervision - inicio");
        List<TipificacionDTO> lTipificacion = new ArrayList<TipificacionDTO>();
        try {
            lTipificacion = supervisionServiceNeg.listarTipificacion(tipificacionFilter);

        } catch (Exception ex) {
            LOG.error("Error findDetalleSupervision", ex);
        }
        LOG.info("findDetalleSupervision - fin");
        return lTipificacion;
    }

    @RequestMapping(value = "/buscarDetalleSupervision", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> buscarDetalleSupervision(DetalleSupervisionFilter filtro, HttpSession session) {
        LOG.info("buscarDetalleSupervision - inicio");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            List<DetalleSupervisionDTO> listado = supervisionServiceNeg.findDetalleSupervision(filtro);
            retorno.put("listaDetalleSupervision", listado);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
        } catch (Exception ex) {
            retorno.put(ConstantesWeb.VV_MENSAJE, "Error al traer Detalle Supervision");
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            LOG.error("Error buscarDetalleSupervision", ex);
        }
        LOG.info("buscarDetalleSupervision - fin");
        return retorno;
    }

    @RequestMapping(value = "/validarRegistroSupervision", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> validarRegistroSupervision(String enPantalla, Long idSupervision, SupervisionDTO supervisionPantalla) {
        LOG.info("validarRegistroSupervision - inicio");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
        	 /* OSINE_SFS-791 - RSIS 42 - Inicio */  
//            retorno = supervisionServiceNeg.validarRegistroSupervision(enPantalla, idSupervision, supervisionPantalla);
//            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
        	 /* OSINE_SFS-791 - RSIS 42 - Fin */  
            String codFlujoSup=supervisionServiceNeg.buscarCodigoFlujoSupervINPS(
                    supervisionPantalla.getOrdenServicioDTO().getExpediente().getObligacionTipo().getIdObligacionTipo(), 
                    supervisionPantalla.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getActividad().getIdActividad(), 
                    supervisionPantalla.getOrdenServicioDTO().getExpediente().getProceso().getIdProceso());   
            if(!StringUtil.isEmpty(codFlujoSup) && codFlujoSup.equals(Constantes.CODIGO_FLUJO_SUPERV_INPS_DSR_CRI)){
                LOG.info("-->codFlujoSup "+Constantes.CODIGO_FLUJO_SUPERV_INPS_DSR_CRI);
                retorno=validarRegistroSupervisionDsr(enPantalla, idSupervision, supervisionPantalla);
            }else{
                LOG.info("-->codFlujoSup Default");
                retorno=validarRegistroSupervisionDefault(enPantalla, idSupervision, supervisionPantalla);
            }            
        } catch (Exception ex) {
            retorno.put(ConstantesWeb.VV_MENSAJE, "Error al validar Registro Supervision");
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            LOG.error("Error validarRegistroSupervision", ex);
        }
        LOG.info("validarRegistroSupervision - fin");
        return retorno;
    }
    
    public Map<String, Object> validarRegistroSupervisionDefault(String enPantalla, Long idSupervision, SupervisionDTO supervisionPantalla) {
        LOG.info("validarRegistroSupervisionDefault - inicio");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            retorno = supervisionServiceNeg.validarRegistroSupervision(enPantalla, idSupervision, supervisionPantalla);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
        } catch (Exception ex) {
            retorno.put(ConstantesWeb.VV_MENSAJE, "Error al validar Registro Supervision");
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            LOG.error("Error validarRegistroSupervisionDefault", ex);
        }
        LOG.info("validarRegistroSupervisionDefault - fin");
        return retorno;
    }
    /* OSINE791 - RSIS20 - Inicio */
    public Map<String, Object> validarRegistroSupervisionDsr(String enPantalla, Long idSupervision, SupervisionDTO supervisionPantalla) {
            LOG.info("validarRegistroSupervisionDsr - inicio");
            Map<String, Object> retorno = new HashMap<String, Object>();
            try {
                retorno = supervisionServiceNeg.validarRegistroSupervisionDsr(enPantalla, idSupervision, supervisionPantalla);
                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            } catch (Exception ex) {
                retorno.put(ConstantesWeb.VV_MENSAJE, "Error al validar Registro Supervision DSR");
                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                LOG.error("Error validarRegistroSupervisionDsr", ex);
            }
            LOG.info("validarRegistroSupervisionDsr - fin");
            return retorno;
        }
    /* OSINE791 - RSIS20 - Fin */

    @RequestMapping(value = "/findSupervision", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> findSupervision(SupervisionFilter filtro, HttpServletRequest request) {
        LOG.info("findSupervision - inicio");
        Map<String, Object> retorno = new HashMap<String, Object>();
        SupervisionDTO respuesta = null;
        try {
            List<SupervisionDTO> lista = supervisionServiceNeg.buscarSupervision(filtro);
            if (!lista.isEmpty()) {
                respuesta = lista.get(Constantes.PRIMERO_EN_LISTA);
            }
            /* OSINE_SFS-480 - RSIS 13 - Inicio */
            if (respuesta != null) {
                /* OSINE_SFS-480 - RSIS 13 - Fin */
                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
                retorno.put(ConstantesWeb.VV_MENSAJE, "Encuentra Supervision");
                retorno.put("supervision", respuesta);
                /* OSINE_SFS-480 - RSIS 13 - Inicio */
            } else {
                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                retorno.put(ConstantesWeb.VV_MENSAJE, "Falta registrar la Supervisi&oacute;n.");
            }
            /* OSINE_SFS-480 - RSIS 13 - Fin */
        } catch (Exception e) {
            LOG.error("Error en findSupervision", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        LOG.info("findSupervision - fin");
        return retorno;
    }

    @RequestMapping(value = "/findTiposArchivo", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> findTiposArchivo(SupervisionFilter filtro, HttpServletRequest request) {
        LOG.info("findTiposArchivo - inicio");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            List<MaestroColumnaDTO> lista = maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_ADJ_SUPERVISION, Constantes.APLICACION_INPS);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put("listaTipoArchivo", lista);
        } catch (Exception e) {
            LOG.error("Error en findTiposArchivo", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        LOG.info("findTiposArchivo - fin");
        return retorno;
    }
    
//    @RequestMapping(value = "/listarDetalleSupervisionIncumplidas", method = {RequestMethod.POST, RequestMethod.GET})
//    public @ResponseBody
//    Map<String, Object> listarDetalleSupervisionIncumplidas(DetalleSupervisionFilter filtro,Model model ,HttpSession session, HttpServletRequest request) {
//        LOG.info("listarDetalleSupervisionIncumplidas");
//        Map<String, Object> retorno = new HashMap<String, Object>();
//                LOG.info("id " +filtro.getIdOrdenServicio());
//        LOG.info("codigo " +filtro.getCodigoResultadoSupervision());
//        try {
//            String flagInfracciones = "";
//
//            if (filtro.getIdOrdenServicio() != null) {
//                DetalleSupervisionFilter filtroInfracciones = new DetalleSupervisionFilter();
//                filtroInfracciones.setIdOrdenServicio(filtro.getIdOrdenServicio());
//                filtroInfracciones.setCodigoResultadoSupervision(Constantes.CODIGO_RESULTADO_DETSUPERVISION_INCUMPLE);
//                List<DetalleSupervisionDTO> listaIncumplidas = supervisionServiceNeg.findDetalleSupervision(filtroInfracciones);
//                if (listaIncumplidas != null && listaIncumplidas.size() == 0) {
//                    flagInfracciones = Constantes.FLAG_OBLIGACIONES_NO_INCUMPLIDAS;
//                } else {
//                    flagInfracciones = Constantes.FLAG_OBLIGACIONES_SI_INCUMPLIDAS;
//                }
//            }
//            LOG.info("flagInfracciones : " + flagInfracciones);
//            model.addAttribute("flagInfracciones", flagInfracciones);
//            retorno.put("flagInfracciones", flagInfracciones);
//            
//        } catch (Exception ex) {
//            LOG.error("Error listarDetalleSupervisionIncumplidas", ex);
//
//        }
//        return retorno;
//    }
    /* OSINE-791 - RSIS 069 - Inicio */
    @RequestMapping(value = "/findDetalleSupervisionDSHL", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> findDetalleSupervisionDSHL(DetalleSupervisionFilter filtro, int rows, int page, HttpSession session, HttpServletRequest request) {
        LOG.info("findDetalleSupervisionDSHL - inicio");
        Map<String, Object> retorno = new HashMap<String, Object>();
        int indiceInicial = -1;
        int indiceFinal = -1;
        try {
            List<DetalleSupervisionDTO> listadoPaginado = null;
            List<DetalleSupervisionDTO> listado = null;
            LOG.info("flagsup es : |"+filtro.getFlagSupervision()+"|");
            if(filtro.getFlagSupervision().equals(Constantes.ESTADO_INACTIVO)){
              LOG.info("Entro a inactivo");
              listado = new ArrayList<DetalleSupervisionDTO>();
            }else{
              LOG.info("es distinto a inactivoo");
              listado = supervisionServiceNeg.findDetalleSupervisionDSHL(filtro);
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
            LOG.error("Error findDetalleSupervisionDSHL", ex);
        }
        LOG.info("findDetalleSupervisionDSHL - fin");
        return retorno;
    }
    /* OSINE-791 - RSIS 069 - Fin */
}
