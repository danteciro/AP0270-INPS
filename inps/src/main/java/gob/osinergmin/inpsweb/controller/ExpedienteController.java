/**
 * Resumen Objeto	: ExpedienteController.java Descripción	: Controla el flujo de
 * datos del objeto Expediente Fecha de Creación	: PR de Creación	:
 * OSINE_SFS-480 Autor	: Julio Piro Gonzales
 * ---------------------------------------------------------------------------------------------------
 * Modificaciones Motivo Fecha Nombre Descripción
 * ---------------------------------------------------------------------------------------------------
 * OSINE_SFS-480 12/05/2016 Luis García Reyna Correo de Alerta a Empresa
 * Supervisora cuando se le asigne Orden de Servicio 
 * OSINE_SFS-480 27/05/2016
 * Giancarlo Villanueva Andrade Adecuar interfaz para la nueva forma de
 * generación de órdenes de servicio (masivo) 
 * OSINE_SFS-480 01/06/2016 Luis
 * García Reyna Implementar la funcionalidad de editar asignaciones de acuerdo a
 * especificaciones
 * 
*/
package gob.osinergmin.inpsweb.controller;
import gob.osinergmin.inpsweb.service.business.CorreoServiceNeg;
import gob.osinergmin.mdicommon.domain.ui.ExpedienteFilter;
import gob.osinergmin.inpsweb.service.business.EmpresaSupervisadaServiceNeg;
import gob.osinergmin.inpsweb.service.business.ExpedienteServiceNeg;
import gob.osinergmin.inpsweb.service.business.MaestroColumnaServiceNeg;
import gob.osinergmin.inpsweb.service.business.PersonalServiceNeg;
import gob.osinergmin.inpsweb.service.business.SupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.exception.ExpedienteException;
//htorress - RSIS 18 - Inicio
import gob.osinergmin.mdicommon.domain.ui.PersonalFilter;
//htorress - RSIS 18 - Fin
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.ConstantesWeb;
import gob.osinergmin.inpsweb.util.StringUtil;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.PersonalDTO;

import java.util.Collections;
import java.util.Comparator;

import gob.osinergmin.siged.remote.rest.ro.out.ConsultarMensajeriaDatosCargoOut;
import gob.osinergmin.siged.remote.rest.ro.out.ListaMensajeriaItemOut;
import gob.osinergmin.siged.remote.rest.ro.out.ExpedienteActualizarTipoProcesoOut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author jpiro
 */
@Controller
@RequestMapping("/expediente")
public class ExpedienteController {
   
    private static final Logger LOG = LoggerFactory.getLogger(ExpedienteController.class);
    
    @Inject
    private ExpedienteServiceNeg expedienteServiceNeg;
    // htorress - RSIS 18 - Inicio
    @Inject
    private PersonalServiceNeg personalServiceNeg;
    // htorress - RSIS 18 - Fin
    /* OSINE_SFS-480 - RSIS 12 - Inicio */
    @Inject
    private CorreoServiceNeg correoServiceNeg;
    /* OSINE_SFS-480 - RSIS 12 - Fin */
//    @Inject
//    private UnidadObliSubTipoServiceNeg unidadSeleccionMuestral;
    @Inject
    private MaestroColumnaServiceNeg maestroColumnaServiceNeg;
    @Inject
    private EmpresaSupervisadaServiceNeg empresaSupervisadaServiceNeg;
    
    @Inject
    private SupervisionServiceNeg supervisionServiceNeg;
   
//    @RequestMapping(value = "/generarExpedienteOrdenServicio", method = RequestMethod.POST)
//    public @ResponseBody
//    Map<String, Object> generarExpedienteOrdenServicio(ExpedienteDTO expedienteDto, String codigoTipoSupervisor, HttpSession session, HttpServletRequest request) {
//        LOG.info("generarExpedienteOrdenServicio");
//        Map<String, Object> retorno = new HashMap<String, Object>();
//        try {            
//            retorno = expedienteServiceNeg.generarExpedienteOrdenServicioPrincipal(expedienteDto, codigoTipoSupervisor, request);
//        } catch (Exception e) {
//            LOG.error("Error en generarExpedienteOrdenServicio", e);
//            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
//            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
//        }
//        return retorno;
//    }
    /* OSINE_SFS-480 - RSIS 27 - Inicio */
 
    @RequestMapping(value = "/generarExpedienteOrdenServicioMasivo", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> generarExpedienteOrdenServicioMasivo(ExpedienteDTO expedienteDto, String codigoTipoSupervisor, HttpSession session, HttpServletRequest request) {
        LOG.info("generarExpedienteOrdenServicio");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
        	//Validacion SIGLA
        	String sigla=(String) request.getSession().getAttribute(Constantes.PERSONAL_UNIDAD_ORGANICA_SIGLA);
        	if(!StringUtils.isEmpty(sigla)){
	            retorno = expedienteServiceNeg.generarExpedienteOrdenServicioMasivoPrincipal(expedienteDto.getUnidadesSupervisadas(), expedienteDto, codigoTipoSupervisor, sigla, request);
        	} else {
           	 	retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                retorno.put(ConstantesWeb.VV_MENSAJE, "No se encontro Sigla de Unidad Org&aacute;nica.");
           }
        } catch (Exception e) {
            LOG.error("Error en generarExpedienteOrdenServicio", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }

    @RequestMapping(value = "/asignarOrdenServicio", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> asignarOrdenServicio(ExpedienteDTO expedienteDto, String codigoTipoSupervisor, HttpSession session, HttpServletRequest request) {
        LOG.info("asignarOrdenServicio");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
        	String sigla=(String) request.getSession().getAttribute(Constantes.PERSONAL_UNIDAD_ORGANICA_SIGLA);
            if(!StringUtils.isEmpty(sigla)){
            	retorno = expedienteServiceNeg.asignarOrdenServicioPrincipal(expedienteDto, codigoTipoSupervisor, Constantes.getUsuarioDTO(request), sigla, request);
            } else {
               	  retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
	              retorno.put(ConstantesWeb.VV_MENSAJE, "No se encontro Sigla de Unidad Org&aacute;nica.");
	        }
        } catch (Exception e) {
            LOG.error("Error en asignarOrdenServicio", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }

    @RequestMapping(value = "/findExpediente", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> findExpediente(ExpedienteFilter filtro,int rows, int page, HttpSession session, HttpServletRequest request) {
        LOG.info("findExpediente");
        Map<String, Object> retorno = new HashMap<String, Object>();
        int indiceInicial = -1;
        int indiceFinal = -1;
        if (filtro.getIdentificadorEmpresaSupervisora() != null) {
            if (filtro.getIdentificadorEmpresaSupervisora().equals(new Long(-1))) {
                filtro.setTipoEmpresaSupervisora(null);
                filtro.setIdentificadorEmpresaSupervisora(null);
            }
        }
        try {
            List<ExpedienteDTO> listadoPaginado = null;
            filtro.setIdentificadorRol(Constantes.getIDENTIFICADORROL(request));
            List<ExpedienteDTO> listado = expedienteServiceNeg.listarExpediente(filtro);
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
            LOG.error("Error findExpediente", ex);

        }
        return retorno;
    }

    @RequestMapping(value = "/derivar", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> derivarExpediente(String cadIdExpedientes, Long idPersonalOri, Long idPersonalDest, String motivoReasignacion, HttpSession session, HttpServletRequest request) {
        LOG.info("derivar");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            List<ExpedienteDTO> expedientes = new ArrayList<ExpedienteDTO>();
            for (String txtIdExpediente : cadIdExpedientes.split(",")) {
                Long idExpediente = new Long(txtIdExpediente);
                expedientes.add(new ExpedienteDTO(idExpediente));
            }
            PersonalDTO respuesta = expedienteServiceNeg.derivar(expedientes, idPersonalOri, idPersonalDest, motivoReasignacion, Constantes.getUsuarioDTO(request));

            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put(ConstantesWeb.VV_MENSAJE, "Cambios realizados");
            retorno.put("personal", respuesta);
        } catch (Exception e) {
            LOG.error("Error en derivar", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }

    @RequestMapping(value = "/asignarUnidadSupervisada", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> asignarUnidadSupervisada(ExpedienteDTO expedienteDto, HttpSession session, HttpServletRequest request) {
        LOG.info("asignarUnidadSupervisada");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            ExpedienteDTO expediente = expedienteServiceNeg.asignarUnidadSupervisada(expedienteDto, Constantes.getUsuarioDTO(request));

            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put(ConstantesWeb.VV_MENSAJE, "Cambios realizados");
            retorno.put("expediente", expediente);
        } catch (Exception e) {
            LOG.error("Error en asignarUnidadSupervisada", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }

    @RequestMapping(value = "/findDerivados", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> findDerivados(ExpedienteFilter filtro, int rows, int page, HttpSession session, HttpServletRequest request) {
        LOG.info("findDerivados");

        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            int indiceInicial = -1;
            int indiceFinal = -1;

            List<ExpedienteDTO> listadoPaginado = null;
            List<ExpedienteDTO> listado = expedienteServiceNeg.listarDerivadosByIdPersonal(filtro);

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
            LOG.error("Error findDerivados", ex);
        }
        return retorno;
    }

    /* OSINE_SFS-480 - RSIS 47 - Inicio */
    @RequestMapping(value = "/editarExpedienteOrdenServicio", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> editarExpedienteOrdenServicio(ExpedienteDTO expedienteDTO, String codigoTipoSupervisor, HttpSession session, HttpServletRequest request) {
        LOG.info("editarExpedienteOrdenServicio");
        Map<String, Object> retorno = new HashMap<String, Object>();
        PersonalDTO personalDest = null;
        List<PersonalDTO> listPersonalDest = null;

        try {
            if (codigoTipoSupervisor.equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_NATURAL)) {
                listPersonalDest = personalServiceNeg.findPersonal(new PersonalFilter(expedienteDTO.getOrdenServicio().getLocador().getIdLocador(), null));
            } else if (codigoTipoSupervisor.equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_JURIDICA)) {
                listPersonalDest = personalServiceNeg.findPersonal(new PersonalFilter(null, expedienteDTO.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa()));
            }

            if (listPersonalDest == null || listPersonalDest.isEmpty()) {
                throw new ExpedienteException("La Empresa Supervisora no tiene un Personal asignado", null);
            } else {
                personalDest = listPersonalDest.get(0);
                String msjeCorreo = "";
       
                LOG.info("expedienteDTO.getFlagCambioEmpresaSupervisora()--->" + expedienteDTO.getFlagCambioEmpresaSupervisora());
                
                /* OSINE_SFS-Ajustes - mdiosesf - Inicio */
                //Ajuste al reenviar expediente SIGED >> dominio COM_REEN_EXP_SIGED.
            	List<MaestroColumnaDTO> listComRenExpSiged=maestroColumnaServiceNeg.findByDominioAplicacionCodigo(Constantes.DOMINIO_COM_REEN_EXP_SIGED, Constantes.APLICACION_INPS, Constantes.CODIGO_COM_REEN_EXP_SIGED_EDIT_EXP_OS);
    	        String comRenExpSiged=(!CollectionUtils.isEmpty(listComRenExpSiged)) ? listComRenExpSiged.get(0).getDescripcion().replace(Constantes.VARIABLE_TEXT_REEN_EXP_SIG, expedienteDTO.getNumeroExpediente()) : "--";
    	        comRenExpSiged=(expedienteDTO.getOrdenServicio()!=null && expedienteDTO.getOrdenServicio().getNumeroOrdenServicio()!=null) ? comRenExpSiged.replace(Constantes.VARIABLE_TEXT_REEN_ORD_SIG, expedienteDTO.getOrdenServicio().getNumeroOrdenServicio()) : comRenExpSiged;
    	        
    	        ExpedienteDTO expedienteReenviarSiged = expedienteServiceNeg.reenviarExpedienteSIGED(expedienteDTO, personalDest.getIdPersonalSiged(), comRenExpSiged, false);
                //ExpedienteDTO expedienteReenviarSiged = expedienteServiceNeg.reenviarExpedienteSIGED(expedienteDTO, personalDest.getIdPersonalSiged(), "Reenvio Siged.", false);
    	        /* OSINE_SFS-Ajustes - mdiosesf - Fin */
                if (expedienteReenviarSiged.getNumeroExpediente() == null) {
                    retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                    retorno.put(ConstantesWeb.VV_MENSAJE, "El servicio reenvío SIGED Asignar orden no disponible.");
                }
                List<PersonalDTO> remitente = personalServiceNeg.findPersonal(new PersonalFilter(Constantes.getIDPERSONAL(request)));
               /* OSINE-791 - RSIS 34 - Inicio */
                String codFlujoSup=supervisionServiceNeg.buscarCodigoFlujoSupervINPS(
                    expedienteDTO.getObligacionTipo().getIdObligacionTipo(), 
                    expedienteDTO.getUnidadSupervisada().getActividad().getIdActividad(), 
                    expedienteDTO.getProceso().getIdProceso()
                );   
            if(!StringUtil.isEmpty(codFlujoSup) && codFlujoSup.equals(Constantes.CODIGO_FLUJO_SUPERV_INPS_DSR_CRI)){
                boolean rptaCorreo = correoServiceNeg.enviarCorreoConfirmTipoAsig(remitente.get(0), personalDest, expedienteDTO);
                if (!rptaCorreo) {
                    msjeCorreo = ", Pero no se pudo enviar correo a Empresa Supervisora.";
                }
            /* OSINE-791 - RSIS 34 - Fin */
            }else{
                boolean rptaCorreo = correoServiceNeg.enviarCorreoAsignarOS(remitente.get(0), personalDest, expedienteDTO);
                if (!rptaCorreo) {
                    msjeCorreo = ", Pero no se pudo enviar correo a Empresa Supervisora.";
            }
                 }
               

//                EmpresaSupDTO empresa = empresaSupervisadaServiceNeg.obtenerEmpresaSupervisada(new EmpresaSupDTO(expedienteDTO.getEmpresaSupervisada().getIdEmpresaSupervisada()));
//                expedienteDTO.setEmpresaSupervisada(empresa);
                if(StringUtils.isEmpty(expedienteDTO.getUnidadSupervisada().getRuc())){
                    throw new ExpedienteException("No se encuentra Ruc de Unidad SUpervisada.", null);
                }
                ExpedienteActualizarTipoProcesoOut expedienteOut = expedienteServiceNeg.editarExpedienteSIGED(expedienteDTO);
                if (expedienteOut != null && expedienteOut.getResultCode().toString().equals(Constantes.FLAG_VALIDAR_SI)) {
                    ExpedienteDTO registroExpedienteEditado = expedienteServiceNeg.editarExpedienteOrdenServicio(expedienteDTO, codigoTipoSupervisor, personalDest, Constantes.getUsuarioDTO(request));
                    retorno.put("ordenServicio", registroExpedienteEditado);
                    retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
                    retorno.put(ConstantesWeb.VV_MENSAJE, "Se actualiz&oacute; el registro satisfactoriamente" + msjeCorreo);
                } else {
                    retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                    retorno.put(ConstantesWeb.VV_MENSAJE, expedienteOut.getMessage());
                }
            }
        } catch (Exception e) {
            LOG.error("Error en editarExpedienteOrdenServicio ", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }

    @RequestMapping(value = "/findMensajeria", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> findMensajeria(int rows, int page, HttpSession session, HttpServletRequest request) {
        LOG.info("findMensajeria");

        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            int indiceInicial = -1;
            int indiceFinal = -1;

            List<ListaMensajeriaItemOut> listadoPaginado = null;
            List<ListaMensajeriaItemOut> listado = expedienteServiceNeg.listarMensajeria(String.valueOf(Constantes.getIDPERSONALSIGED(request)));

            Collections.sort(listado, new Comparator<ListaMensajeriaItemOut>() {
                @Override
                public int compare(ListaMensajeriaItemOut mensajeria1, ListaMensajeriaItemOut mensajeria2) {
                    return mensajeria1.getFechaDerivacion().compareTo(mensajeria2.getFechaDerivacion());
                }
            });
            Collections.reverse(listado);

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
            LOG.error("Error findMensajeria", ex);
        }
        return retorno;
    }

    @RequestMapping(value = "/cargaDatosCargo", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> cargaDatosCargo(String idMensajeria, HttpSession sesion, Model model, HttpServletRequest request) {
        LOG.info("cargaDatosCargo");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            ConsultarMensajeriaDatosCargoOut consultarMensajeriaDatosCargoOut = expedienteServiceNeg.cargaDatosCargo(idMensajeria);

            if (consultarMensajeriaDatosCargoOut.getFechaVisita1() != null
                    && consultarMensajeriaDatosCargoOut.getFechaVisita1() != Constantes.CONSTANTE_VACIA
                    && consultarMensajeriaDatosCargoOut.getFechaVisita1().length() > 0) {
                consultarMensajeriaDatosCargoOut.setFechaVisita1(formatoFecha(consultarMensajeriaDatosCargoOut.getFechaVisita1()));
            } else {
                consultarMensajeriaDatosCargoOut.setFechaVisita1(Constantes.CONSTANTE_VACIA);
            }

            if (consultarMensajeriaDatosCargoOut.getFechaVisita2() != null
                    && consultarMensajeriaDatosCargoOut.getFechaVisita2() != Constantes.CONSTANTE_VACIA
                    && consultarMensajeriaDatosCargoOut.getFechaVisita2().length() > 0) {
                consultarMensajeriaDatosCargoOut.setFechaVisita2(formatoFecha(consultarMensajeriaDatosCargoOut.getFechaVisita2()));
            } else {
                consultarMensajeriaDatosCargoOut.setFechaVisita2(Constantes.CONSTANTE_VACIA);
            }

            if (consultarMensajeriaDatosCargoOut.getFechaDevolucionCargo() != null
                    && consultarMensajeriaDatosCargoOut.getFechaDevolucionCargo() != Constantes.CONSTANTE_VACIA
                    && consultarMensajeriaDatosCargoOut.getFechaDevolucionCargo().length() > 0) {
                consultarMensajeriaDatosCargoOut.setFechaDevolucionCargo(formatoFecha(consultarMensajeriaDatosCargoOut.getFechaDevolucionCargo()));
            } else {
                consultarMensajeriaDatosCargoOut.setFechaDevolucionCargo(Constantes.CONSTANTE_VACIA);
            }

            if (consultarMensajeriaDatosCargoOut.getFechaEntregaDestinatario() != null
                    && consultarMensajeriaDatosCargoOut.getFechaEntregaDestinatario() != Constantes.CONSTANTE_VACIA
                    && consultarMensajeriaDatosCargoOut.getFechaEntregaDestinatario().length() > 0) {

                consultarMensajeriaDatosCargoOut.setHoraEntregaDestinatario(consultarMensajeriaDatosCargoOut.getFechaEntregaDestinatario().substring(12, 19));
                consultarMensajeriaDatosCargoOut.setFechaEntregaDestinatario(formatoFecha(consultarMensajeriaDatosCargoOut.getFechaEntregaDestinatario()));
            } else {
                consultarMensajeriaDatosCargoOut.setFechaEntregaDestinatario(Constantes.CONSTANTE_VACIA);
            }

            if (consultarMensajeriaDatosCargoOut.getFechaDevolucionuf() != null
                    && consultarMensajeriaDatosCargoOut.getFechaDevolucionuf() != Constantes.CONSTANTE_VACIA
                    && consultarMensajeriaDatosCargoOut.getFechaDevolucionuf().length() > 0) {
                consultarMensajeriaDatosCargoOut.setFechaDevolucionuf(formatoFecha(consultarMensajeriaDatosCargoOut.getFechaDevolucionuf()));
            } else {
                consultarMensajeriaDatosCargoOut.setFechaDevolucionuf(Constantes.CONSTANTE_VACIA);
            }

            if (consultarMensajeriaDatosCargoOut.getResultCode() != 1) {
                retorno.put("resultado", ConstantesWeb.VV_ERROR);
            } else {
                retorno.put("resultado", ConstantesWeb.VV_EXITO);
                retorno.put("registro", consultarMensajeriaDatosCargoOut);
            }
        } catch (Exception e) {
            LOG.error("Error cargaDatosCargo", e);
            retorno.put("resultado", ConstantesWeb.VV_ERROR);
        }
        return retorno;
    }

    public String formatoFecha(String fecha) {
        String resultadoString = fecha.substring(8, 10) + "-" + fecha.substring(5, 7) + "-" + fecha.substring(0, 4);
        return resultadoString;
    }
    /* OSINE_SFS-480 - RSIS 06 - Fin */
}
