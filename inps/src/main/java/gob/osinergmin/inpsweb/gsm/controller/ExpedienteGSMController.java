/**
 * Resumen Objeto		: ExpedienteGSMController.java 
 * Descripción	        : Controla el flujo de datos del Expediente, gerencia GSM.
 * Fecha de Creación	: 24/10/2016.
 * PR de Creación		: OSINE_SFS-1344. 
 * Autor				: Hernan Torres.
 * ---------------------------------------------------------------------------------------------------
 * Modificaciones   Fecha             Nombre               Descripción
 * ---------------------------------------------------------------------------------------------------
 *
 */

package gob.osinergmin.inpsweb.gsm.controller;

import gob.osinergmin.inpsweb.gsm.util.ConstantesGSM;
import gob.osinergmin.inpsweb.service.business.CorreoServiceNeg;
import gob.osinergmin.mdicommon.domain.ui.EstadoProcesoFilter;
import gob.osinergmin.mdicommon.domain.ui.ExpedienteFilter;
import gob.osinergmin.inpsweb.service.business.EmpresaSupervisadaServiceNeg;
import gob.osinergmin.inpsweb.service.business.EstadoProcesoServiceNeg;
import gob.osinergmin.inpsweb.gsm.service.business.ExpedienteGSMServiceNeg;
import gob.osinergmin.inpsweb.service.business.MaestroColumnaServiceNeg;
import gob.osinergmin.inpsweb.service.business.PersonalServiceNeg;
import gob.osinergmin.inpsweb.service.business.UnidadObliSubTipoServiceNeg;
import gob.osinergmin.inpsweb.service.exception.ExpedienteException;
//htorress - RSIS 18 - Inicio
import gob.osinergmin.mdicommon.domain.ui.PersonalFilter;
//htorress - RSIS 18 - Fin
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.ConstantesWeb;
import gob.osinergmin.mdicommon.domain.dto.EmpresaSupDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteGSMDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.PersonalDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadObliSubTipoDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadSupervisadaDTO;// gvillanueva RSIS27
import java.util.Collections;
import java.util.Comparator;
import gob.osinergmin.siged.remote.rest.ro.out.ConsultarMensajeriaDatosCargoOut;
import gob.osinergmin.siged.remote.rest.ro.out.ListaMensajeriaItemOut;
import gob.osinergmin.siged.remote.rest.ro.out.ExpedienteActualizarTipoProcesoOut;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/expedienteGSM")
public class ExpedienteGSMController {

    private static final Logger LOG = LoggerFactory.getLogger(ExpedienteGSMController.class);
    
    @Inject
    private ExpedienteGSMServiceNeg expedienteServiceNeg;
    
    @Inject
    private PersonalServiceNeg personalServiceNeg;
    
    @Inject
    private CorreoServiceNeg correoServiceNeg;
    
    @Inject
    private UnidadObliSubTipoServiceNeg unidadSeleccionMuestral;
    /* OSINE_SFS-1344 - Inicio */
    @Inject
    private MaestroColumnaServiceNeg maestroColumnaServiceNeg;
    
    @Inject
    private EstadoProcesoServiceNeg estadoProcesoServiceNeg;
    /* OSINE_SFS-1344 - Fin */
    @Inject
    private EmpresaSupervisadaServiceNeg empresaSupervisadaServiceNeg;

    @RequestMapping(value = "/generarExpedienteOrdenServicio", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> generarExpedienteOrdenServicio(ExpedienteGSMDTO expedienteDto, String codigoTipoSupervisor, HttpSession session, HttpServletRequest request) {
        LOG.info("generarExpedienteOrdenServicio");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
        	ExpedienteGSMDTO expedienteSiged = expedienteServiceNeg.generarExpedienteSiged(expedienteDto, Constantes.getIDPERSONALSIGED(request));
            if (expedienteSiged == null || expedienteSiged.getNumeroExpediente() == null) {
                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                retorno.put(ConstantesWeb.VV_MENSAJE, "El servicio SIGED no se encuentra disponible.");
            } else {
                expedienteDto.setNumeroExpediente(expedienteSiged.getNumeroExpediente());
                List<PersonalDTO> destinatario = null;
                if (codigoTipoSupervisor.equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_NATURAL)) {
                    destinatario = personalServiceNeg.findPersonal(new PersonalFilter(expedienteDto.getOrdenServicio().getLocador().getIdLocador(), null));
                } else if (codigoTipoSupervisor.equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_JURIDICA)) {
                    destinatario = personalServiceNeg.findPersonal(new PersonalFilter(null, expedienteDto.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa()));
                }

                if (destinatario != null && destinatario.size() > 0) {
                	ExpedienteGSMDTO expedienteReenviarSiged = expedienteServiceNeg.reenviarExpedienteSIGED(expedienteDto, destinatario.get(0).getIdPersonalSiged(), "Prueba de Contenido.", false);
                    if (expedienteReenviarSiged.getNumeroExpediente() != null) {
                        expedienteDto.setNumeroExpediente(expedienteSiged.getNumeroExpediente());//viene del WS
                        expedienteDto.setFechaCreacionSiged(new Date());
                        expedienteDto.setFlagOrigen(Constantes.EXPEDIENTE_FLAG_ORIGEN_INPS);
                        
                        String sigla=(String) request.getSession().getAttribute(Constantes.PERSONAL_UNIDAD_ORGANICA_SIGLA);
                        ExpedienteGSMDTO expedienteBD=expedienteServiceNeg.generarExpedienteOrdenServicio(expedienteDto,codigoTipoSupervisor,Constantes.getUsuarioDTO(request),sigla);
                        
                        String msjeCorreo = "";
                        List<PersonalDTO> remitente = personalServiceNeg.findPersonal(new PersonalFilter(Constantes.getIDPERSONAL(request)));
                        LOG.info("remitente---->" + remitente);
                        expedienteDto.getOrdenServicio().setNumeroOrdenServicio(expedienteBD.getOrdenServicio().getNumeroOrdenServicio());
                        boolean rptaCorreo = true;//correoServiceNeg.enviarCorreoAsignarOS(remitente.get(0), destinatario.get(0), expedienteDto);
                        if (!rptaCorreo) {
                            msjeCorreo = " Pero no se pudo enviar correo a Empresa Supervisora.";
                        }

                        retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
                        retorno.put(ConstantesWeb.VV_MENSAJE, "Cambios realizados." + msjeCorreo);
                        retorno.put("expediente", expedienteBD);
                    } else {
                        retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                        retorno.put(ConstantesWeb.VV_MENSAJE, "El servicio reenvío SIGED Asignar orden no disponible.");
                    }
                } else {
                    retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                    retorno.put(ConstantesWeb.VV_MENSAJE, "Error en la obtención del id del Personal Siged del destinatario.");
                }
            }
        } catch (Exception e) {
            LOG.error("Error en generarExpedienteOrdenServicio", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }

    @RequestMapping(value = "/generarExpedienteOrdenServicioMasivo", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> generarExpedienteOrdenServicioMasivo(ExpedienteGSMDTO expedienteDto, String codigoTipoSupervisor, HttpSession session, HttpServletRequest request) {
        LOG.info("generarExpedienteOrdenServicio");
        Map<String, Object> retorno = new HashMap<String, Object>();
        List<ExpedienteGSMDTO> listaOrdSerCreada = new ArrayList<ExpedienteGSMDTO>();
        List<UnidadSupervisadaDTO> listaOrdSerNoGenExpSiged = new ArrayList<UnidadSupervisadaDTO>();
        List<UnidadSupervisadaDTO> listaOrdSerNoReeSiged = new ArrayList<UnidadSupervisadaDTO>();
        List<UnidadSupervisadaDTO> listaOrdSerNoIdPerSiged = new ArrayList<UnidadSupervisadaDTO>();
        String ordSerNoReeSigedMsg = null;
        String ordSerNoIdPerSigedMsg = null;
        String ordSerNoGenExpSigedMsg = null;
        /**/
        MaestroColumnaDTO periodo = maestroColumnaServiceNeg.buscarByDominioByAplicacionByCodigo(Constantes.DOMINIO_SUPERV_MUEST_PERIODO, Constantes.APLICACION_INPS, Constantes.CODIGO_PERIODO).get(0);
        Long cantPeriodos = new Long(periodo.getDescripcion());
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        Long per = obtenerPeriodoActual(cantPeriodos, mes);
        String perUnidad = year + "" + per;
        /**/
        try {
            List<UnidadSupervisadaDTO> unidades = expedienteDto.getUnidadesSupervisadas();
            for (UnidadSupervisadaDTO unidad : unidades) {
                expedienteDto.setUnidadSupervisada(unidad);
                ExpedienteGSMDTO expedienteSiged = expedienteServiceNeg.generarExpedienteSiged(expedienteDto, Constantes.getIDPERSONALSIGED(request));
                if (expedienteSiged == null || expedienteSiged.getNumeroExpediente() == null) {
                    /**/
                    expedienteDto.setMensajeServicio("<b>" + expedienteSiged.getMensajeServicio() + "</b><br>");
                    ordSerNoGenExpSigedMsg = expedienteDto.getMensajeServicio();
                    listaOrdSerNoGenExpSiged.add(unidad);                
                } else {
                    expedienteDto.setNumeroExpediente(expedienteSiged.getNumeroExpediente());
                    List<PersonalDTO> destinatario = null;
                    if (codigoTipoSupervisor.equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_NATURAL)) {
                        destinatario = personalServiceNeg.findPersonal(new PersonalFilter(expedienteDto.getOrdenServicio().getLocador().getIdLocador(), null));
                    } else if (codigoTipoSupervisor.equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_JURIDICA)) {
                        destinatario = personalServiceNeg.findPersonal(new PersonalFilter(null, expedienteDto.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa()));
                    }
                    if (destinatario != null && destinatario.size() > 0) {
                    	ExpedienteGSMDTO expedienteReenviarSiged = expedienteServiceNeg.reenviarExpedienteSIGED(expedienteDto, destinatario.get(0).getIdPersonalSiged(), "Prueba de Contenido.", false);
                        if (expedienteReenviarSiged.getNumeroExpediente() != null) {
                            expedienteDto.setNumeroExpediente(expedienteSiged.getNumeroExpediente());//viene del WS
                            expedienteDto.setFechaCreacionSiged(new Date());
                            expedienteDto.setFlagOrigen(Constantes.EXPEDIENTE_FLAG_ORIGEN_INPS);
                            expedienteDto.setEmpresaSupervisada(new EmpresaSupDTO(expedienteDto.getUnidadSupervisada().getEmpresaSupervisada().getIdEmpresaSupervisada()));
                            expedienteDto.setFlagMuestral(expedienteDto.getUnidadSupervisada().getFlagMuestral());
                            
                            String sigla=(String) request.getSession().getAttribute(Constantes.PERSONAL_UNIDAD_ORGANICA_SIGLA);
                            ExpedienteGSMDTO expedienteBD=expedienteServiceNeg.generarExpedienteOrdenServicio(expedienteDto,codigoTipoSupervisor,Constantes.getUsuarioDTO(request),sigla);
                                                        
                            if (expedienteBD != null && expedienteBD.getOrdenServicio() != null
                                    && expedienteBD.getOrdenServicio().getNumeroOrdenServicio() != null
                                    && unidad.getFlagMuestral().equals(Constantes.ESTADO_ACTIVO)) {
                                UnidadObliSubTipoDTO unidadMuestraltoUpdate = new UnidadObliSubTipoDTO();
                                UnidadSupervisadaDTO unidadMuestra = new UnidadSupervisadaDTO();
                                unidadMuestra.setIdUnidadSupervisada(expedienteDto.getUnidadSupervisada().getIdUnidadSupervisada());
                                unidadMuestraltoUpdate.setIdUnidadSupervisada(unidadMuestra);
                                unidadMuestraltoUpdate.setFlagSupOrdenServicio(Constantes.ESTADO_ACTIVO);
                                unidadMuestraltoUpdate.setPeriodo(perUnidad);
                                unidadMuestraltoUpdate.setEstado(Constantes.ESTADO_ACTIVO);
                                UnidadObliSubTipoDTO unidadMuestral = unidadSeleccionMuestral.updateUnidadMuestral(unidadMuestraltoUpdate, Constantes.getUsuarioDTO(request));
                                LOG.info("unidadMuestral update=" + unidadMuestral);
                            }
                            List<PersonalDTO> remitente = personalServiceNeg.findPersonal(new PersonalFilter(Constantes.getIDPERSONAL(request)));
                            LOG.info("remitente---->" + remitente);
                            String msjCorreo = "";
                            expedienteDto.getOrdenServicio().setNumeroOrdenServicio(expedienteBD.getOrdenServicio().getNumeroOrdenServicio());
                            boolean rptaCorreo = true;//correoServiceNeg.enviarCorreoAsignarOS(remitente.get(0), destinatario.get(0), expedienteDto);
                            if (!rptaCorreo) {
                                LOG.info("No se pudo enviar correo a Empresa Supervisora.");
                                msjCorreo = " - No se pudo enviar correo a Empresa Supervisora.";
                            }

                            expedienteBD.setMensajeServicio("<b>ORDENES DE SERVICIO CREADAS:</b><br>");
                            expedienteBD.getOrdenServicio().setNumeroOrdenServicio(expedienteBD.getOrdenServicio().getNumeroOrdenServicio() + msjCorreo);
                            listaOrdSerCreada.add(expedienteBD);
                        } else {
                            /**/
                            expedienteDto.setMensajeServicio("<b>El servicio reenvío SIGED Asignar orden no disponible.</b><br>");
                            ordSerNoReeSigedMsg = expedienteDto.getMensajeServicio();
                            listaOrdSerNoReeSiged.add(unidad);
                        }
                    } else {
                        /**/
                        expedienteDto.setMensajeServicio("<b>Error en la obtención del id del Personal Siged del destinatario.</b><br>");
                        ordSerNoIdPerSigedMsg = expedienteDto.getMensajeServicio();
                        listaOrdSerNoIdPerSiged.add(unidad);
                    }
                }
            }
            /**/
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put(ConstantesWeb.VV_MENSAJE, "Cambios realizados.");
            String ordSerCreada = concatenaOrdSerCreada(listaOrdSerCreada);
            String ordSerNoGenExpSiged = concatenaOrdSerNoGenExpSiged(listaOrdSerNoGenExpSiged);
            String ordSerNoReeSiged = concatenaOrdSerNoReeSiged(listaOrdSerNoReeSiged);
            String ordSerNoIdPerSiged = concatenaOrdSerNoIdPerSiged(listaOrdSerNoIdPerSiged);

            if (ordSerCreada != null && !ordSerCreada.equals("")) {
                retorno.put("ordSerCreadaConcatenada", ordSerCreada + "<br>");
            } else {
                retorno.put("ordSerCreadaConcatenada", "");
            }
            if (ordSerNoGenExpSiged != null && !ordSerNoGenExpSiged.equals("")) {
                retorno.put("ordSerNoGenExpSigedConcatenada", ordSerNoGenExpSiged + "<br>");
            } else {
                retorno.put("ordSerNoGenExpSigedConcatenada", "");
            }
            if (ordSerNoReeSiged != null && !ordSerNoReeSiged.equals("")) {
                retorno.put("ordSerNoReeSigedConcatenada", ordSerNoReeSiged + "<br>");
            } else {
                retorno.put("ordSerNoReeSigedConcatenada", "");
            }
            if (ordSerNoIdPerSiged != null && !ordSerNoIdPerSiged.equals("")) {
                retorno.put("ordSerNoIdPerSigedConcatenada", ordSerNoIdPerSiged + "<br>");
            } else {
                retorno.put("ordSerNoIdPerSigedConcatenada", "");
            }

            if (listaOrdSerCreada != null && listaOrdSerCreada.size() > 0) {
                retorno.put("msgOrdSerCreada", listaOrdSerCreada.get(0).getMensajeServicio());
            } else {
                retorno.put("msgOrdSerCreada", "");
            }
            if ((listaOrdSerNoReeSiged != null && listaOrdSerNoReeSiged.size() > 0) || (listaOrdSerNoGenExpSiged != null && listaOrdSerNoGenExpSiged.size() > 0) || (listaOrdSerNoIdPerSiged != null && listaOrdSerNoIdPerSiged.size() > 0)) {
                retorno.put("msgOrdSerNoCreada", "<b>ORDENES DE SERVICIO NO CREADAS:</b><br>");
            } else {
                retorno.put("msgOrdSerNoCreada", "");
            }
            if (listaOrdSerNoGenExpSiged != null && listaOrdSerNoGenExpSiged.size() > 0) {
                retorno.put("msgNoGenExpSiged", ordSerNoGenExpSigedMsg);
            } else {
                retorno.put("msgNoGenExpSiged", "");
            }
            if (listaOrdSerNoReeSiged != null && listaOrdSerNoReeSiged.size() > 0) {
                retorno.put("msgNoReeSiged", ordSerNoReeSigedMsg);
            } else {
                retorno.put("msgNoReeSiged", "");
            }
            if (listaOrdSerNoIdPerSiged != null && listaOrdSerNoIdPerSiged.size() > 0) {
                retorno.put("msgNoIdPerSiged", ordSerNoIdPerSigedMsg);
            } else {
                retorno.put("msgNoIdPerSiged", "");
            }
            /**/

        } catch (Exception e) {
            LOG.error("Error en generarExpedienteOrdenServicio", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }

    private String concatenaOrdSerCreada(List<ExpedienteGSMDTO> filtros) {
        String retorno = "";
        if (filtros != null && filtros.size() > 0) {
            String[] s = new String[filtros.size()];
            int cont = 0;
            for (ExpedienteGSMDTO maestra : filtros) {
                s[cont] = maestra.getOrdenServicio().getNumeroOrdenServicio().toString();
                cont++;
            }
            retorno = StringUtils.join(s, ",");
        }
        return retorno;
    }

    private String concatenaOrdSerNoGenExpSiged(List<UnidadSupervisadaDTO> filtros) {
        String retorno = "";
        if (filtros != null && filtros.size() > 0) {
            String[] s = new String[filtros.size()];
            int cont = 0;
            for (UnidadSupervisadaDTO maestra : filtros) {
                s[cont] = maestra.getCodigoOsinergmin().toString();
                cont++;
            }
            retorno = StringUtils.join(s, ",");
        }
        return retorno;
    }

    private String concatenaOrdSerNoReeSiged(List<UnidadSupervisadaDTO> filtros) {
        String retorno = "";
        if (filtros != null && filtros.size() > 0) {
            String[] s = new String[filtros.size()];
            int cont = 0;
            for (UnidadSupervisadaDTO maestra : filtros) {
                s[cont] = maestra.getCodigoOsinergmin().toString();
                cont++;
            }
            retorno = StringUtils.join(s, ",");
        }
        return retorno;
    }

    private String concatenaOrdSerNoIdPerSiged(List<UnidadSupervisadaDTO> filtros) {
        String retorno = "";
        if (filtros != null && filtros.size() > 0) {
            String[] s = new String[filtros.size()];
            int cont = 0;
            for (UnidadSupervisadaDTO maestra : filtros) {
                s[cont] = maestra.getCodigoOsinergmin().toString();
                cont++;
            }
            retorno = StringUtils.join(s, ",");
        }
        return retorno;
    }

    @RequestMapping(value = "/asignarOrdenServicio", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> asignarOrdenServicio(ExpedienteGSMDTO expedienteDto, String codigoTipoSupervisor, HttpSession session, HttpServletRequest request) {
        LOG.info("asignarOrdenServicio");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {

            List<PersonalDTO> destinatario = null;
            if (codigoTipoSupervisor.equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_NATURAL)) {
                destinatario = personalServiceNeg.findPersonal(new PersonalFilter(expedienteDto.getOrdenServicio().getLocador().getIdLocador(), null));
            } else if (codigoTipoSupervisor.equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_JURIDICA)) {
                destinatario = personalServiceNeg.findPersonal(new PersonalFilter(null, expedienteDto.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa()));
            }

            if (destinatario != null && destinatario.size() > 0) {
            	ExpedienteGSMDTO expedienteReenviarSiged = expedienteServiceNeg.reenviarExpedienteSIGED(expedienteDto, destinatario.get(0).getIdPersonalSiged(), "Prueba de Contenido.", false);
                if (expedienteReenviarSiged.getNumeroExpediente() != null) {

                	ExpedienteGSMDTO expediente = expedienteServiceNeg.asignarOrdenServicio(expedienteDto, codigoTipoSupervisor, Constantes.getUsuarioDTO(request),"DSR");
                    
                    String msjeCorreo = "";
                    List<PersonalDTO> remitente = personalServiceNeg.findPersonal(new PersonalFilter(Constantes.getIDPERSONAL(request)));
                    expedienteDto.getOrdenServicio().setNumeroOrdenServicio(expediente.getOrdenServicio().getNumeroOrdenServicio());
                    boolean rptaCorreo = true;//correoServiceNeg.enviarCorreoAsignarOS(remitente.get(0), destinatario.get(0), expedienteDto);
                    if (!rptaCorreo) {
                        msjeCorreo = ". No se pudo enviar correo a Empresa Supervisora.";
                        expediente.getOrdenServicio().setNumeroOrdenServicio(expediente.getOrdenServicio().getNumeroOrdenServicio() + msjeCorreo);
                    }

                    retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
                    retorno.put(ConstantesWeb.VV_MENSAJE, "Cambios realizados" + msjeCorreo);
                    retorno.put("expediente", expediente);

                } else {
                    retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                    retorno.put(ConstantesWeb.VV_MENSAJE, "El servicio reenvío SIGED Asignar orden no disponible.");
                }
            } else {
                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                retorno.put(ConstantesWeb.VV_MENSAJE, "Error en la obtención del id del Personal Siged del destinatario.");
            }
        } catch (Exception e) {
            LOG.error("Error en asignarOrdenServicio", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }
    /* OSINE_SFS-1344 - Inicio */
    @RequestMapping(value = "/findExpediente", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> findExpediente(ExpedienteFilter filtro, int rows, int page, HttpSession session, HttpServletRequest request) {
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
            List<ExpedienteGSMDTO> listadoPaginado = null;
            filtro.setIdentificadorRol(Constantes.getIDENTIFICADORROL(request));
            List<ExpedienteGSMDTO> listado = expedienteServiceNeg.listarExpediente(filtro);
            if(listado!=null && listado.size()>0){
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
            }
        } catch (Exception ex) {
            LOG.error("Error findExpediente", ex);

        }
        return retorno;
    }
    /* OSINE_SFS-1344 - Fin */
    @RequestMapping(value = "/derivar", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> derivarExpediente(String cadIdExpedientes, Long idPersonalOri, Long idPersonalDest, String motivoReasignacion, HttpSession session, HttpServletRequest request) {
        LOG.info("derivar");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            List<ExpedienteGSMDTO> expedientes = new ArrayList<ExpedienteGSMDTO>();
            for (String txtIdExpediente : cadIdExpedientes.split(",")) {
                Long idExpediente = new Long(txtIdExpediente);
                expedientes.add(new ExpedienteGSMDTO(idExpediente));
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
    Map<String, Object> asignarUnidadSupervisada(ExpedienteGSMDTO expedienteDto, HttpSession session, HttpServletRequest request) {
        LOG.info("asignarUnidadSupervisada");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
        	ExpedienteGSMDTO expediente = expedienteServiceNeg.asignarUnidadSupervisada(expedienteDto, Constantes.getUsuarioDTO(request));

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

            List<ExpedienteGSMDTO> listadoPaginado = null;
            List<ExpedienteGSMDTO> listado = expedienteServiceNeg.listarDerivadosByIdPersonal(filtro);

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
    
    @RequestMapping(value = "/editarExpedienteOrdenServicio", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> editarExpedienteOrdenServicio(ExpedienteGSMDTO expedienteDTO, String codigoTipoSupervisor, HttpSession session, HttpServletRequest request) {
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
                ExpedienteGSMDTO expedienteReenviarSiged = expedienteServiceNeg.reenviarExpedienteSIGED(expedienteDTO, personalDest.getIdPersonalSiged(), "Reenvio Siged.", false);
                if (expedienteReenviarSiged.getNumeroExpediente() == null) {
                    retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                    retorno.put(ConstantesWeb.VV_MENSAJE, "El servicio reenvío SIGED Asignar orden no disponible.");
                }
                List<PersonalDTO> remitente = personalServiceNeg.findPersonal(new PersonalFilter(Constantes.getIDPERSONAL(request)));
                boolean rptaCorreo = true;//correoServiceNeg.enviarCorreoAsignarOS(remitente.get(0), personalDest, expedienteDTO);
                if (!rptaCorreo) {
                    msjeCorreo = ", Pero no se pudo enviar correo a Empresa Supervisora.";
                }


                EmpresaSupDTO empresa = empresaSupervisadaServiceNeg.obtenerEmpresaSupervisada(new EmpresaSupDTO(expedienteDTO.getEmpresaSupervisada().getIdEmpresaSupervisada()));
                expedienteDTO.setEmpresaSupervisada(empresa);
                ExpedienteActualizarTipoProcesoOut expedienteOut = expedienteServiceNeg.editarExpedienteSIGED(expedienteDTO);
                if (expedienteOut != null && expedienteOut.getResultCode().toString().equals(Constantes.FLAG_VALIDAR_SI)) {
                	ExpedienteGSMDTO registroExpedienteEditado = expedienteServiceNeg.editarExpedienteOrdenServicio(expedienteDTO, codigoTipoSupervisor, personalDest, Constantes.getUsuarioDTO(request));
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

    private Long obtenerPeriodoActual(Long cantPeriodos, int mes) {
        Long per = new Long(0);
        Long cantMeses = 12 / cantPeriodos;
        Long contPeriodo = new Long(0);
        Long longPeriodo = new Long(0);
        ArrayList<Long> dominio = new ArrayList<Long>();
        for (int i = 0; i < cantPeriodos + 1; i++) {
            longPeriodo = cantMeses * contPeriodo;
            contPeriodo++;
            dominio.add(longPeriodo);
        }
        contPeriodo = new Long(0);
        for (int i = 0; i < cantPeriodos; i++) {
            contPeriodo++;
            if (mes > dominio.get(i) && (mes < dominio.get(i + 1) || mes == dominio.get(i + 1))) {
                per = contPeriodo;
            }
        }
        LOG.info("Array Periodos: " + dominio);
        LOG.info("Periodo: " + per);
        return per;
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
    /* OSINE_SFS-1344 - Inicio */
//    @RequestMapping(value = "/abrirOrdenServicioSupervisor", method = RequestMethod.GET)
//    public String abrirOrdenServicioSupervisor(Long idPersonal, HttpSession sesion, Model model) {
//        LOG.info("abrirOrdenServicioSupervisor");
//        model.addAttribute("p", personalServiceNeg.findPersonal(new PersonalFilter(idPersonal)).get(0));
//        MaestroColumnaDTO maestroColumna = maestroColumnaServiceNeg.findByDominioAplicacionCodigo(Constantes.DOMINIO_TIPO_COMPONENTE, Constantes.APLICACION_INPS, Constantes.CODIGO_TIPO_COMPONENTE_ORDEN_SERVICIO).get(0);
//        model.addAttribute("listadoEstadoProcesoOS", estadoProcesoServiceNeg.find(new EstadoProcesoFilter(maestroColumna.getIdMaestroColumna())));
//        return ConstantesGSM.Navegacion.PAGE_INPS_GSM_ORDEN_SERVICIO_SUPERVISOR;
//    }
//
//    
//    @RequestMapping(value = "/abrirOrdenServicioSupervisorDevolver", method = RequestMethod.GET)
//    public String abrirOrdenServicioSupervisorDevolver(Long idPersonal, HttpSession sesion, Model model) throws Exception {
//        LOG.info("abrirOrdenServicioSupervisorDevolver");
//        model.addAttribute("listadoTipoPeticion", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_PETICION, Constantes.APLICACION_INPS));
//        return ConstantesGSM.Navegacion.PAGE_INPS_GSM_ORDEN_SERVICIO_SUPERVISOR_DEVOLVER;
//    }
    /* OSINE_SFS-1344 - Fin */
}
