/*Resumen Objeto	: BandejaController.java 
 * Descripcion	        : Controla el flujo de datos del objeto Bandeja 
 * Fecha de Creacion	: 17/06/2015 
 * OSINE_SFS-480 Autor	: Julio Piro Gonzales
 * ---------------------------------------------------------------------------------------------------
 * Modificaciones   Fecha             Nombre               Descripcion
 * ---------------------------------------------------------------------------------------------------
 * OSINE_SFS-480  09/05/2016    Hernan Torres Saenz     Cargar Lista de Obligaciones luego de Registrar la Fecha-Hora de Inicio del "Registro de Supervision".
 * OSINE_SFS-480  11/05/2016    Hernan Torres Saenz     Validacion de carga de obligaciones al abrir la pantalla Supervision en la 2da iteracion - Carga de combos de ubigeo y tipo de peticion para los filtros de busqueda
 * OSINE_SFS-480  13/05/2016    Hernan Torres Saenz     Agregar criterios al filtro de busqueda en la seccion asignaciones,evaluacion y notificacion-archivado
 * OSINE_SFS-480  19/05/2016    Luis Garcia Reyna      	Adaptar y preparar la relacion entre "peticion" y "motivo" en la tabla MDI_MAESTRO_COLUMNA 
 * OSINE_SFS-480  19/05/2016    Giancarlo Villanueva    Andrade Adecuar interfaz para la nueva forma de generacion de ordenes de servicio (masivo) 
 * OSINE_SFS-480  20/05/2016    Hernan Torres Saenz     Crear la opcion "Anular orden de servicio" en la pestana asigaciones de la bandeja del especialista el cual direccionara a la interfaz "Anular orden de servicio" 
 * OSINE_SFS-480  24/05/2016    Giancarlo Villanueva    Crear componente de seleccion de "subtipo de supervision".Relacionar y adecuar el subtipo de supervision, el cual debera depender del tipo de supervision seleccionado. 
 * OSINE_SFS-480  25/05/2016   	Mario Dioses Fernandez  Construir formulario de envio a Mensajeria, consumiendo WS 
 * OSINE_SFS-480  27/05/2016    Luis Garcia Reyna      	Funcionalidad de Datos de Mensajeria: Datos de Envio, Datos del Cargo 
 * OSINE_SFS-480  01/06/2016    Luis Garcia Reyna      	Implementar la funcionalidad de editar asignaciones de acuerdo a especificaciones
 * OSINE_SFS-480  06/06/2016   	Mario Dioses Fernandez  Funcionalidad de Datos de Mensajeria: Datos de Envio, Datos del Cargo 
 * OSINE_SFS-791  17/08/2016    Yadira Piskulich       	Abrir Supervision DSR
 * OSINE_SFS-791  08/31/2016    Cristopher Paucar Torre Registrar Medio Probatorio.
 * OSINE_SFS-791  06/10/2016    Mario Dioses Fernandez  Crear la funcionalidad "verificar levantamientos" que permita verificar el tipo de asignacion para la orden de levantamiento DSR-CRITICIDAD.
 * OSINE_SFS-791  05/10/2016    Zosimo Chaupis Santur   CREAR LA FUNCIONALIDAD REGISTRAR SUPERVISION DE UNA ORDEN DE SUPERVISION DSR-CRITICIDAD DE LA CASUISTICA SUPERVISION NO INICIADA                  
 * OSINE_SFS-791 - RSIS 1    02/11/2016                 Configurar ingreso a la Bandeja por Rol y Unidad Organica (DSHL-DSR)
*/
package gob.osinergmin.inpsweb.controller;
import gob.osinergmin.mdicommon.domain.ui.DetalleSupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.FlujoSigedFilter;
//htorress - RSIS 18 - Inicio
import gob.osinergmin.mdicommon.domain.ui.HistoricoEstadoFilter;
import gob.osinergmin.inpsweb.service.business.HistoricoEstadoServiceNeg;
import gob.osinergmin.mdicommon.domain.dto.HistoricoEstadoDTO;

import java.util.Collections;
import java.util.Comparator;

import gob.osinergmin.mdicommon.domain.ui.LocadorFilter;
//htorress - RSIS 18 - Fin
import gob.osinergmin.mdicommon.domain.ui.OrdenServicioFilter;
import gob.osinergmin.mdicommon.domain.ui.PersonalFilter;
import gob.osinergmin.mdicommon.domain.ui.ProcesoFilter;
import gob.osinergmin.mdicommon.domain.ui.SupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.SupervisionPersonaGralFilter;
import gob.osinergmin.mdicommon.domain.ui.TipificacionFilter;
import gob.osinergmin.inpsweb.service.business.ActividadServiceNeg;
import gob.osinergmin.inpsweb.service.business.EmpresaSupervisadaServiceNeg;
import gob.osinergmin.inpsweb.service.business.EstadoProcesoServiceNeg;
import gob.osinergmin.inpsweb.service.business.FlujoSigedServiceNeg;
import gob.osinergmin.inpsweb.service.business.LocadorServiceNeg;
import gob.osinergmin.inpsweb.service.business.MaestroColumnaServiceNeg;
import gob.osinergmin.inpsweb.service.business.ObligacionTipoServiceNeg;
import gob.osinergmin.inpsweb.service.business.PersonalServiceNeg;
import gob.osinergmin.inpsweb.service.business.ResultadoSupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.business.RolOpcionServiceNeg;
import gob.osinergmin.inpsweb.service.business.SerieServiceNeg;
import gob.osinergmin.inpsweb.service.business.SupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.business.SupervisoraEmpresaServiceNeg;
import gob.osinergmin.inpsweb.service.business.UbigeoServiceNeg;
import gob.osinergmin.inpsweb.service.business.UnidadOrganicaServiceNeg;
import gob.osinergmin.inpsweb.service.business.UnidadSupervisadaServiceNeg;
import gob.osinergmin.inpsweb.service.dao.EstadoProcesoDAO;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.gsm.util.ConstantesGSM;
import gob.osinergmin.inpsweb.util.ConstantesWeb;
import gob.osinergmin.inpsweb.util.StringUtil;
import gob.osinergmin.mdicommon.domain.dto.ActividadDTO;
import gob.osinergmin.mdicommon.domain.dto.ConfFiltroEmpSupDTO;
import gob.osinergmin.mdicommon.domain.dto.DepartamentoDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresaSupDTO;
import gob.osinergmin.mdicommon.domain.dto.EstadoProcesoDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.LocadorDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.OrdenServicioDTO;
import gob.osinergmin.mdicommon.domain.dto.PersonalDTO;
import gob.osinergmin.mdicommon.domain.dto.ResultadoSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionPersonaGralDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisoraEmpresaDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadOrganicaDTO;
import gob.osinergmin.mdicommon.domain.ui.EstadoProcesoFilter;
import gob.osinergmin.mdicommon.domain.ui.ResultadoSupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.RolOpcionFilter;
import gob.osinergmin.mdicommon.domain.ui.UnidadOrganicaFilter;
import gob.osinergmin.mdicommon.domain.ui.UnidadSupervisadaFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author jpiro
 */
@Controller
@RequestMapping("/bandeja")
public class BandejaController {

    private static final Logger LOG = LoggerFactory.getLogger(BandejaController.class);
    @Inject
    private PersonalServiceNeg personalServiceNeg;
    @Inject
    private FlujoSigedServiceNeg flujoSigedServiceNeg;
    @Inject
    private UnidadSupervisadaServiceNeg unidadSupervisadaServiceNeg;
    @Inject
    private EmpresaSupervisadaServiceNeg empresaSupervisadaServiceNeg;
    @Inject
    private SerieServiceNeg serieServiceNeg;
    @Inject
    private MaestroColumnaServiceNeg maestroColumnaServiceNeg;
    @Inject
    private UbigeoServiceNeg ubigeoServiceNeg;
    @Inject
    private ObligacionTipoServiceNeg obligacionTipoServiceNeg;
    @Inject
    private SupervisionServiceNeg supervisionServiceNeg;
    @Inject
    private EstadoProcesoServiceNeg estadoProcesoServiceNeg;
    @Inject
    private ActividadServiceNeg actividadServiceNeg;
    @Inject
    private UnidadOrganicaServiceNeg unidadOrganicaServiceNeg;
    @Inject
    private RolOpcionServiceNeg rolOpcionServiceNeg;
    @Inject
    private LocadorServiceNeg locadorServiceNeg;
    @Inject
    private ResultadoSupervisionServiceNeg resultadoSupervisionServiceNeg;
    // htorress - RSIS 18 - Inicio
    @Inject
    private HistoricoEstadoServiceNeg historicoEstadoServiceNeg;
    @Inject
    private SupervisoraEmpresaServiceNeg supervisoraEmpresaServiceNeg;
    // htorress - RSIS 18 - Fin
    @Inject
    private EstadoProcesoDAO estadoProcesoDAO;//mdiosesf
    @Value("${tamanio.maximo.archivo.megas}")
    private Long TAMANIO_MAXIMO_ARCHIVO_MEGAS;
    /* OSINE_SFS-480 - RSIS 39 - Inicio */
    @Autowired
    private MaestroColumnaServiceNeg maestroColumnaService;
    /* OSINE_SFS-480 - RSIS 39 - Fin */  
    

    

    @RequestMapping(method = RequestMethod.GET)
    public String bandeja(Model model, HttpServletRequest request, ServletResponse response, HttpSession session) {
        LOG.info("Controlador bandeja");
        //poniendo permisos de session en duro
        //request.getSession().setAttribute("xxp", "IN/MO/EL/CO");
        String navegacion = ConstantesWeb.Navegacion.PAGE_INPS_VISTA_DESAUTORIZADA;
        String username = Constantes.getUSUARIO(request);
        LOG.info("username:" + username);
        model.addAttribute("usuario", Constantes.getUSUARIO(request));
        model.addAttribute("fecha", Constantes.getFECHA());
        PersonalDTO personal = null;       

        List<PersonalDTO> listPersona = personalServiceNeg.findPersonal(new PersonalFilter(username, null));
        LOG.info("listPersona:" + listPersona.size());
       
        if (listPersona.size() > 0) {
            personal = listPersona.get(0);
            LOG.info("Rol:" + personal.getRol().getIdentificadorRol());
        }

        if (personal != null && personal.getRol() != null && personal.getRol().getIdentificadorRol() != null) {
            request.getSession().setAttribute("idPersonal", personal.getIdPersonal());
            request.getSession().setAttribute("idPersonalSiged", personal.getIdPersonalSiged());
            request.getSession().setAttribute("identificadorRol", personal.getRol().getIdentificadorRol());
            model.addAttribute("nombreRol", personal.getRol().getNombreRol());
            
            /* victoria */
            String codigoBandeja=null;
            codigoBandeja=unidadOrganicaServiceNeg.getBandejaDefault(personal.getIdPersonal());
            
            //momentaneo hasta que se genere el usuario
            //personal.getRol().setIdentificadorRol("ESPECIALISTA_LEGAL");
            
            if (personal.getRol().getIdentificadorRol().equals(Constantes.IDENTIFICADOR_ROL_RESPONSABLE)){
                navegacion = bandejaResponsable(model);
            }else if (personal.getRol().getIdentificadorRol().equals(Constantes.IDENTIFICADOR_ROL_ESPECIALISTA)){
	
            	if(codigoBandeja!=null && codigoBandeja.equals(Constantes.CONSTANTE_CODIGO_BANDEJA_DSE)){
                    navegacion = bandejaEspecialistaDSE();
                }else if(codigoBandeja!=null && codigoBandeja.equals(Constantes.CONSTANTE_CODIGO_BANDEJA_GSM)){
                    navegacion = bandejaEspecialistaGSM(model);
                }else{
                    navegacion = bandejaEspecialista(model);
            	}
               // htorress - RSIS 1, 2 y 3 - Inicio
            }else if (personal.getRol().getIdentificadorRol().equals(Constantes.IDENTIFICADOR_ROL_ESPECIALISTA_LEGAL)){
            	navegacion = bandejaEspecialistaLegal(model);
            }else if (personal.getRol().getIdentificadorRol().equals(Constantes.IDENTIFICADOR_ROL_JEFE_REGIONAL)) {
                navegacion = bandejaJefeRegional(model);
            } else if (personal.getRol().getIdentificadorRol().equals(Constantes.IDENTIFICADOR_ROL_JEFE_UNIDAD)) {
                navegacion = bandejaJefeUnidad(model);
            } else if (personal.getRol().getIdentificadorRol().equals(Constantes.IDENTIFICADOR_ROL_SUPERVISOR_REGIONAL)) {
                navegacion = bandejaSupervisorRegional(model);
                // htorress - RSIS 1, 2 y 3 - Fin
            } else if (personal.getRol().getIdentificadorRol().equals(Constantes.IDENTIFICADOR_ROL_SUPERVISOR)) {
                LOG.info("Controlador IDENTIFICADOR_ROL_SUPERVISOR");
            	LOG.info("Division unidad organica::::"+codigoBandeja);
                if(codigoBandeja!=null && codigoBandeja.equals(Constantes.CONSTANTE_CODIGO_BANDEJA_DSE)){
                    navegacion = bandejaSupervisorDSE();
                LOG.info("Controlador bandejaSupervisorDSE::::::::::::");
                }else if(codigoBandeja!=null && codigoBandeja.equals(Constantes.CONSTANTE_CODIGO_BANDEJA_GSM)){
                	navegacion = bandejaSupervisorGSM(model);
                }else{
                    LOG.info("Controlador bandejaSupervisor::::::::::::");
                    navegacion = bandejaSupervisor(model);
                }
                //navegacion = bandejaSupervisor(model);
            } else if( personal.getRol().getIdentificadorRol().equals(Constantes.IDENTIFICADOR_ROL_CONSULTA)){
            	navegacion = bandejaConsultaDSE();
            } else if (personal.getRol().getIdentificadorRol().equals(Constantes.IDENTIFICADOR_ROL_GERENTE_DIVISION)) {
            	navegacion = bandejaGerenteDivisionGSM(model);
            }
            
            /*else if (personal.getRol().getIdentificadorRol().equals(Constantes.IDENTIFICADOR_ROL_SUPERVISOR)&& divUnidOrg.getSigla().equals(Constantes.CONSTANTE_CODIGO_DEP_SIGA)) {
                navegacion = bandejaSupervisorGSM(model);
            }*/
        }
        model.addAttribute("personal", personal);
        model.addAttribute("idPersonal", Constantes.getIDPERSONAL(request));
        LOG.info("navegacion->" + navegacion);
        return navegacion;
    }

    //@RequestMapping(value="/especialista",method = RequestMethod.GET)
    public String bandejaSupervisorDSE(){
    	return ConstantesWeb.Navegacion.PAGE_INPS_SUPERVISOR_DSE;
    }

    public String bandejaConsultaDSE(){
    	return ConstantesWeb.Navegacion.PAGE_INPS_CONSULTA_DSE;
    }
    
    public String bandejaEspecialistaDSE() {
        return ConstantesWeb.Navegacion.PAGE_INPS_ESPECIALISTA_DSE;
    }
    
    public String bandejaEspecialistaLegal(Model model){
    	model.addAttribute("listadoOpcionEspecialista", rolOpcionServiceNeg.findRolOpcion(new RolOpcionFilter(Constantes.IDENTIFICADOR_ROL_ESPECIALISTA_LEGAL, null)));
    	return ConstantesWeb.Navegacion.PAGE_INPS_ESPECIALISTA_LEGAL;
    }
    
    public String bandejaEspecialista(Model model) {
        model.addAttribute("listadoOpcionEspecialista", rolOpcionServiceNeg.findRolOpcion(new RolOpcionFilter(Constantes.IDENTIFICADOR_ROL_ESPECIALISTA, null)));
        return ConstantesWeb.Navegacion.PAGE_INPS_ESPECIALISTA;
    }
    //@RequestMapping(value="/especialista",method = RequestMethod.GET)
    public String bandejaEspecialistaGSM(Model model) {
        //model.addAttribute("listadoOpcionEspecialista", rolOpcionServiceNeg.findRolOpcion(new RolOpcionFilter(Constantes.IDENTIFICADOR_ROL_ESPECIALISTA, null)));
        return ConstantesGSM.Navegacion.PAGE_INPS_GSM_ESPECIALISTA;
    }

    // htorress - RSIS 1, 2 y 3 - Inicio
    public String bandejaJefeRegional(Model model) {
        model.addAttribute("listadoOpcionJefe", rolOpcionServiceNeg.findRolOpcion(new RolOpcionFilter(Constantes.IDENTIFICADOR_ROL_JEFE_REGIONAL, null)));
        return ConstantesWeb.Navegacion.PAGE_INPS_JEFE_REGIONAL;
    }

    public String bandejaJefeUnidad(Model model) {
        model.addAttribute("listadoOpcionJefe", rolOpcionServiceNeg.findRolOpcion(new RolOpcionFilter(Constantes.IDENTIFICADOR_ROL_JEFE_UNIDAD, null)));
        return ConstantesWeb.Navegacion.PAGE_INPS_JEFE_UNIDAD;
    }
    
    public String bandejaGerenteDivisionGSM(Model model) {
        //model.addAttribute("listadoOpcionJefe", rolOpcionServiceNeg.findRolOpcion(new RolOpcionFilter(Constantes.IDENTIFICADOR_ROL_JEFE_DIVISION, null)));
        return ConstantesGSM.Navegacion.PAGE_INPS_GSM_GERENTE_DIVISION;
    }

    public String bandejaSupervisorRegional(Model model) {
        model.addAttribute("listadoOpcionSupervisorRegional", rolOpcionServiceNeg.findRolOpcion(new RolOpcionFilter(Constantes.IDENTIFICADOR_ROL_SUPERVISOR_REGIONAL, null)));
        return ConstantesWeb.Navegacion.PAGE_INPS_SUPERVISOR_REGIONAL;
    }
    // htorress - RSIS 1, 2 y 3 - Fin
//    @RequestMapping(value="/supervisor",method = RequestMethod.GET)

    public String bandejaSupervisor(Model model) {
        model.addAttribute("listadoOpcionSupervisor", rolOpcionServiceNeg.findRolOpcion(new RolOpcionFilter(Constantes.IDENTIFICADOR_ROL_SUPERVISOR, null)));
        return ConstantesWeb.Navegacion.PAGE_INPS_SUPERVISOR;
//        return ConstantesGSM.Navegacion.PAGE_INPS_GSM_SUPERVISOR;
    }
    
    public String bandejaSupervisorGSM(Model model) {
        model.addAttribute("listadoOpcionSupervisor", rolOpcionServiceNeg.findRolOpcion(new RolOpcionFilter(Constantes.IDENTIFICADOR_ROL_SUPERVISOR, null)));
        return ConstantesGSM.Navegacion.PAGE_INPS_GSM_SUPERVISOR;
    }

    //@RequestMapping(value="/responsable",method = RequestMethod.GET)
    public String bandejaResponsable(Model model) {
        // htorress - RSIS 3 - Inicio
        //model.addAttribute("listadoDestinatario", personalServiceNeg.findPersonal(new PersonalFilter(null,Constantes.CONSTANTE_ROL_ESPECIALISTA)));
        model.addAttribute("listadoDestinatario", personalServiceNeg.findPersonal(new PersonalFilter(null, (Constantes.CONSTANTE_ROL_ESPECIALISTA + "," + Constantes.CONSTANTE_ROL_SUPERVISOR_REGIONAL))));
        // htorress - RSIS 3 - Fin
        model.addAttribute("listadoOpcionResponsable", rolOpcionServiceNeg.findRolOpcion(new RolOpcionFilter(Constantes.IDENTIFICADOR_ROL_RESPONSABLE, null)));
        //model.addAttribute("listadoOpcionResponsable", rolOpcionServiceNeg.findRolOpcion(new RolOpcionFilter(Constantes.IDENTIFICADOR_ROL_RESPONSABLE,null)));
        return ConstantesWeb.Navegacion.PAGE_INPS_RESPONSABLE;
    }

    @RequestMapping(value = "/abrirOrdenServicio", method = RequestMethod.POST)
    public String abrirOrdenServicio(String tipo, String rolSesion, ExpedienteDTO expedienteDto, HttpSession sesion, Model model, HttpServletRequest request) {
        LOG.info("abrirOrdenServicio, tipo=" + tipo);
        try{
            Long idProceso = new Long(0);
            Long idActividad = new Long(0);
            Long idObligacionTipo = new Long(0);
            //Long flagSigedInps=new Long(expedienteDto.getFlagOrigen());
            model.addAttribute("tipo", tipo);
            model.addAttribute("listadoFlujoSiged", flujoSigedServiceNeg.listarFlujoSiged(new FlujoSigedFilter()));
            
            if (expedienteDto.getEmpresaSupervisada() != null && expedienteDto.getEmpresaSupervisada().getIdEmpresaSupervisada() != null) {//en caso GENERAR no hay empresa
                UnidadSupervisadaFilter unidadSupervisadaFilter = new UnidadSupervisadaFilter();
                unidadSupervisadaFilter.setIdEmpresaSupervisada(expedienteDto.getEmpresaSupervisada().getIdEmpresaSupervisada());
                model.addAttribute("listadoUnidadSupervisada", unidadSupervisadaServiceNeg.listarUnidadSupervisada(unidadSupervisadaFilter));
//JPIRO EMPSUP                if (expedienteDto.getUnidadSupervisada() != null && !Constantes.CONSTANTE_VACIA.equals(expedienteDto.getUnidadSupervisada().getCodigoOsinergmin())) {
//                    unidadSupervisadaFilter = new UnidadSupervisadaFilter();
//                    unidadSupervisadaFilter.setCodigoOsinerg(expedienteDto.getUnidadSupervisada().getCodigoOsinergmin());
//                    idActividad = unidadSupervisadaServiceNeg.cargaDataUnidadOperativa(unidadSupervisadaFilter).getIdActividad();
//                }
            }
            //JPIRO EMPSUP
            if (expedienteDto.getUnidadSupervisada() != null && !Constantes.CONSTANTE_VACIA.equals(expedienteDto.getUnidadSupervisada().getCodigoOsinergmin())) {
                UnidadSupervisadaFilter unidadSupervisadaFilter = new UnidadSupervisadaFilter();
                unidadSupervisadaFilter.setCodigoOsinerg(expedienteDto.getUnidadSupervisada().getCodigoOsinergmin());
                idActividad = unidadSupervisadaServiceNeg.cargaDataUnidadOperativa(unidadSupervisadaFilter).getIdActividad();
            }
            
            if (expedienteDto.getProceso() != null && expedienteDto.getProceso().getIdProceso() != null) {
                idProceso = expedienteDto.getProceso().getIdProceso();
            }
            if (expedienteDto.getObligacionTipo() != null && expedienteDto.getObligacionTipo().getIdObligacionTipo() != null) {
                idObligacionTipo = expedienteDto.getObligacionTipo().getIdObligacionTipo();
            }
            ProcesoFilter procesoFilter = new ProcesoFilter();
            procesoFilter.setIdentificador(expedienteDto.getFlagOrigen().equals(Constantes.EXPEDIENTE_FLAG_ORIGEN_SIGED) ? (Constantes.PROCESO_OPERATIVO) : (Constantes.PROCESO_PREOPERATIVO));
            model.addAttribute("listadoProceso", serieServiceNeg.listarProceso(procesoFilter));
            model.addAttribute("listadoTipoSupervision", obligacionTipoServiceNeg.listarObligacionTipoxSeleccionMuestral());
            model.addAttribute("listadoTipoAsignacion", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_ASIGNACION, Constantes.APLICACION_INPS));
            model.addAttribute("listadoTipoSupervisor", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_SUPERVISOR, Constantes.APLICACION_INPS));
            model.addAttribute("listadoEntidad", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_ENTIDAD, Constantes.APLICACION_SGLSS));
            model.addAttribute("especialista", Constantes.CONSTANTE_ROL_ESPECIALISTA);
            model.addAttribute("supervisor", Constantes.CONSTANTE_ROL_SUPERVISOR);

            /* OSINE_SFS-480 - RSIS 41 - Inicio */
            if (tipo.equals(Constantes.TIPO_ACCION_ORDEN_SERVICO_ANULAR)
                    /* OSINE_SFS-480 - RSIS 47 - Inicio */
                    || tipo.equals(Constantes.TIPO_ACCION_ORDEN_SERVICO_EDITAR)) {
                /* OSINE_SFS-480 - RSIS 47 - Fin */
                List<MaestroColumnaDTO> tipoMotivo = new ArrayList<MaestroColumnaDTO>();
                tipoMotivo = maestroColumnaServiceNeg.findByIdMaestroColumna(expedienteDto.getOrdenServicio().getIdMotivo());
                model.addAttribute("tipoMotivo", tipoMotivo);
            }
            /* OSINE_SFS-480 - RSIS 41 - Fin */

            // htorress - RSIS 18 - Inicio
            String destinatario = "0";
            if (expedienteDto.getOrdenServicio() != null && expedienteDto.getOrdenServicio().getIdOrdenServicio() != null) {
                HistoricoEstadoFilter filtro = new HistoricoEstadoFilter();
                filtro.setIdOrdenServicio(expedienteDto.getOrdenServicio().getIdOrdenServicio());
                List<HistoricoEstadoDTO> listado = historicoEstadoServiceNeg.listarHistoricoEstado(filtro);

                Collections.sort(listado, new Comparator<HistoricoEstadoDTO>() {
                    @Override
                    public int compare(HistoricoEstadoDTO hist1, HistoricoEstadoDTO hist2) {
                        return hist1.getFechaCreacion().compareTo(hist2.getFechaCreacion());
                    }
                });
                Collections.reverse(listado);

                LOG.info("ID PERSONA LOGUEADA : : : : " + Constantes.getIDPERSONAL(request).longValue());
                if (listado.get(0).getPersonalDest().getIdPersonal().longValue() == (Constantes.getIDPERSONAL(request).longValue())) {
                    destinatario = "1";
                }
            }
            model.addAttribute("destinatario", destinatario);
            // htorress - RSIS 18 - Fin

            Integer flagSupervision = 0;
            if (rolSesion.toString().equals(Constantes.CONSTANTE_ROL_SUPERVISOR)) {
                flagSupervision=supervisionServiceNeg.buscarFlag(idObligacionTipo, idActividad, idProceso);               
            // htorress - RSIS 1, 2 y 3 - Inicio    
            //}else if(rolSesion.toString().equals(Constantes.CONSTANTE_ROL_ESPECIALISTA)){
            }else if(rolSesion.toString().equals(Constantes.CONSTANTE_ROL_ESPECIALISTA)  || rolSesion.toString().equals(Constantes.CONSTANTE_ROL_JEFE_REGIONAL) 
                            || rolSesion.toString().equals(Constantes.CONSTANTE_ROL_JEFE_UNIDAD) || rolSesion.toString().equals(Constantes.CONSTANTE_ROL_SUPERVISOR_REGIONAL)){	
            // htorress - RSIS 1, 2 y 3 - Fin
                    if(expedienteDto.getOrdenServicio()!=null){
                            SupervisionFilter supervisionFiltro = new SupervisionFilter();
                            supervisionFiltro.setIdOrdenServicio(expedienteDto.getOrdenServicio().getIdOrdenServicio());
                            if(supervisionFiltro.getIdOrdenServicio()!=null){
                                    List<SupervisionDTO> listaSupervision = supervisionServiceNeg.buscarSupervision(supervisionFiltro);
                                    if(!listaSupervision.isEmpty()){
                                            flagSupervision=Constantes.FLAG_REALIZA_SUPERVISION;                                            
                                    }
                            }
                    }
            }
            model.addAttribute("flagSupervision", flagSupervision);
            /* OSINE_SFS-480 - RSIS 26 - Inicio */
            Long idUnidadOrganica = (Long) request.getSession().getAttribute(Constantes.PERSONAL_UNIDAD_ORGANICA_DIVISION);
            List<ConfFiltroEmpSupDTO> listaConfFiltros = new ArrayList<ConfFiltroEmpSupDTO>();
            LocadorFilter filtro = new LocadorFilter();
            filtro.setEstado(Constantes.ESTADO_ACTIVO);
            filtro.setIdUnidadOrganica(idUnidadOrganica);
            if (idUnidadOrganica != null && !idUnidadOrganica.equals("")) {
                listaConfFiltros = locadorServiceNeg.listarConfFiltros(filtro);
            }

            String confFiltrosConcatenada = concatenaFiltros(listaConfFiltros);
            String nombreXfiltroConfigurado = concatenaFiltrosDescripcion(listaConfFiltros);

            model.addAttribute("confFiltros", confFiltrosConcatenada);
            model.addAttribute("nombreXfiltroConfigurado", nombreXfiltroConfigurado);
            /* OSINE_SFS-480 - RSIS 26 - Fin */

            if (!tipo.equals(Constantes.TIPO_ACCION_ORDEN_SERVICO_ASIGNAR)
                    && !tipo.equals(Constantes.TIPO_ACCION_ORDEN_SERVICO_GENERAR)
                    && !tipo.equals(Constantes.TIPO_ACCION_ORDEN_SERVICO_EDITAR)) {
                //se envia datos de Empresa Supervisora
                LocadorDTO AsignadoLocador = null;
                SupervisoraEmpresaDTO AsignadoSupeEmp = null;
                if (expedienteDto.getOrdenServicio().getLocador().getIdLocador() != null) {
                    AsignadoLocador = locadorServiceNeg.getById(expedienteDto.getOrdenServicio().getLocador().getIdLocador());
                    LOG.info("----------->" + AsignadoLocador.getNombreCompleto());
                    expedienteDto.getOrdenServicio().getLocador().setNombreCompleto(AsignadoLocador.getNombreCompleto());
                } else if (expedienteDto.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa() != null) {
                    AsignadoSupeEmp = supervisoraEmpresaServiceNeg.getById(expedienteDto.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa());
                    LOG.info("------>AsignadoEmpSup--->" + AsignadoSupeEmp);
                    LOG.info("----------->" + AsignadoSupeEmp.getRazonSocial());
                    expedienteDto.getOrdenServicio().getSupervisoraEmpresa().setRazonSocial(AsignadoSupeEmp.getRazonSocial());
                }
            }
            model.addAttribute("r", expedienteDto);
        }catch(Exception e){
            LOG.error(e.getMessage(),e);
        }
        return ConstantesWeb.Navegacion.PAGE_INPS_ORDEN_SERVICIO;
    }
    /* OSINE_SFS-480 - RSIS 27 - Inicio */

    @RequestMapping(value = "/abrirOrdenServicioMasivo", method = RequestMethod.GET)
    public String abrirOrdenServicioMasivo(String tipo, String rolSesion, ExpedienteDTO expedienteDto, String idPersonal, HttpSession sesion, Model model, HttpServletRequest request) {
        LOG.info("abrirOrdenServicio, tipo=" + tipo);
        try{
            model.addAttribute("tipo", tipo);
            model.addAttribute("r", expedienteDto);
            model.addAttribute("rol",rolSesion);
            model.addAttribute("listadoFlujoSiged", flujoSigedServiceNeg.listarFlujoSiged(new FlujoSigedFilter()));

            ProcesoFilter procesoFilter = new ProcesoFilter();
            procesoFilter.setIdentificador(expedienteDto.getFlagOrigen().equals(Constantes.EXPEDIENTE_FLAG_ORIGEN_SIGED) ? (Constantes.PROCESO_OPERATIVO) : (Constantes.PROCESO_PREOPERATIVO));
            model.addAttribute("listadoProceso", serieServiceNeg.listarProceso(procesoFilter));
            /*OSINE_SFS-480 - RSIS 26 - Inicio*/
            model.addAttribute("listadoTipoSupervision", obligacionTipoServiceNeg.listarObligacionTipoxSeleccionMuestral());
            /*OSINE_SFS-480 - RSIS 26 - Fin*/
            model.addAttribute("listadoTipoAsignacion", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_ASIGNACION, Constantes.APLICACION_INPS));
            model.addAttribute("listadoTipoSupervisor", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_SUPERVISOR, Constantes.APLICACION_INPS));

            Long idUnidadOrganica = (Long) request.getSession().getAttribute(Constantes.PERSONAL_UNIDAD_ORGANICA_DIVISION);
            List<ConfFiltroEmpSupDTO> listaConfFiltros = new ArrayList<ConfFiltroEmpSupDTO>();
            LocadorFilter filtro = new LocadorFilter();
            filtro.setEstado(Constantes.ESTADO_ACTIVO);
            filtro.setIdUnidadOrganica(idUnidadOrganica);
            if (idUnidadOrganica != null && !idUnidadOrganica.equals("")) {
                listaConfFiltros = locadorServiceNeg.listarConfFiltros(filtro);
            }
            String confFiltrosConcatenada = concatenaFiltros(listaConfFiltros);
            String nombreXfiltroConfigurado = concatenaFiltrosDescripcion(listaConfFiltros);

            model.addAttribute("confFiltros", confFiltrosConcatenada);
            model.addAttribute("nombreXfiltroConfigurado", nombreXfiltroConfigurado);
            model.addAttribute("idPersonal", idPersonal);
        }catch(Exception e){
            LOG.error(e.getMessage(),e);
        }
        return ConstantesWeb.Navegacion.PAGE_INPS_ORDEN_SERVICIO_MASIVO;
    }

    private String concatenaFiltrosDescripcion(List<ConfFiltroEmpSupDTO> filtros) {
        String retorno = "";
        if (filtros != null && filtros.size() > 0) {
            String[] s = new String[filtros.size()];
            int cont = 0;
            for (ConfFiltroEmpSupDTO maestra : filtros) {
                s[cont] = maestra.getIdFiltro().getDescripcion().toString();
                cont++;
            }
            retorno = StringUtils.join(s, ",");
        }
        return retorno;
    }

    public static String concatenaFiltros(List<ConfFiltroEmpSupDTO> filtros) {
        String retorno = "";
        if (filtros != null && filtros.size() > 0) {
            String[] s = new String[filtros.size()];
            int cont = 0;
            for (ConfFiltroEmpSupDTO maestra : filtros) {
                s[cont] = maestra.getIdFiltro().getCodigo().toString();
                cont++;
            }
            retorno = StringUtils.join(s, ",");
        }
        return retorno;
    }
    /* OSINE_SFS-480 - RSIS 27 - Fin */

    @RequestMapping(value = "/abrirDocumentoExpediente", method = RequestMethod.GET)
    public String abrirDocumentoExpediente(ExpedienteDTO expedienteDto, HttpSession sesion, Model model) {
        LOG.info("abrirDocumentoExpediente");
        model.addAttribute("r", expedienteDto);
        return ConstantesWeb.Navegacion.PAGE_INPS_DOCUMENTO_EXPEDIENTE;
    }

    @RequestMapping(value = "/abrirTrazabilidadOrdeServ", method = RequestMethod.GET)
    public String abrirTrazabilidadOrdeServ(ExpedienteDTO expedienteDto, HttpSession sesion, Model model) {
        LOG.info("abrirTrazabilidadOrdeServ");
        model.addAttribute("r", expedienteDto);
        return ConstantesWeb.Navegacion.PAGE_INPS_TRAZABILIDAD_ORDEN_SERVICIO;
    }
    /* OSINE_SFS-480 - RSIS 06 - Inicio */

    @RequestMapping(value = "/abrirConsMensajeriaExpediente", method = RequestMethod.GET)
    public String abrirConsMensajeriaExpediente(HttpSession sesion, Model model, HttpServletRequest request) {
        LOG.info("abrirConsMensajeriaExpediente");

        //model.addAttribute("r", expedienteDto);
        return ConstantesWeb.Navegacion.PAGE_INPS_CONSULTA_MENSAJERIA_EXPEDIENTE;
    }
    /* OSINE_SFS-480 - RSIS 06 - Fin */

    @RequestMapping(value = "/abrirMantUnidadOperativa", method = RequestMethod.GET)
    public String abrirMantUnidadOperativa(String tipo, ExpedienteDTO expedienteDto, Long idEmpresaSupervisada, HttpSession sesion, Model model) {
        LOG.info("abrirMantUnidadOperativa");
        model.addAttribute("tipo", tipo);
        model.addAttribute("r", expedienteDto);
        //Obtener Ciiu
        List<MaestroColumnaDTO> listaCiius = maestroColumnaServiceNeg.buscarTodos("CIIU");
        model.addAttribute("listaCiius", listaCiius);
        //Obtener Tipo Documentos
        List<MaestroColumnaDTO> listaTipoDocumentos = maestroColumnaServiceNeg.buscarTodos("TIPO_DOCUMENTO");
        model.addAttribute("listaTipoDocumentos", listaTipoDocumentos);
        //Obtener Tipo Direccion
        List<MaestroColumnaDTO> listaTipoDirecciones = maestroColumnaServiceNeg.findByDominioAplicacion("TIPO_DIRE_EMPRESA", Constantes.APLICACION_INPS);
        List<MaestroColumnaDTO> listaTipoDireccionesUS = maestroColumnaServiceNeg.findByDominioAplicacion("TIPO_DIRE_UNIDAD", Constantes.APLICACION_INPS);
        model.addAttribute("listaTipoDirecciones", listaTipoDirecciones);
        model.addAttribute("listaTipoDireccionesUS", listaTipoDireccionesUS);
        //Obtener Ubigeo
        List<DepartamentoDTO> departamentos = ubigeoServiceNeg.obtenerDepartamentos();
        List<DepartamentoDTO> departamentosUS = ubigeoServiceNeg.obtenerDepartamentos();
        model.addAttribute("listaDepartamentos", departamentos);
        model.addAttribute("listaDepartamentosUS", departamentosUS);
        //Obtener Tipo Via
        List<MaestroColumnaDTO> listaTipoVias = maestroColumnaServiceNeg.buscarTodos("TIPO_VIA");
        List<MaestroColumnaDTO> listaTipoViasUS = maestroColumnaServiceNeg.buscarTodos("TIPO_VIA");
        model.addAttribute("listaTipoVias", listaTipoVias);
        model.addAttribute("listaTipoViasUS", listaTipoViasUS);
        //Obtener Empresa Supervisada
        LOG.info("Id Empresa -->" + idEmpresaSupervisada);
        EmpresaSupDTO empresa = new EmpresaSupDTO();
        empresa.setIdEmpresaSupervisada(idEmpresaSupervisada);
        empresa = empresaSupervisadaServiceNeg.obtenerEmpresaSupervisada(empresa);
        model.addAttribute("empresa", empresa);

        return ConstantesWeb.Navegacion.PAGE_INPS_MANTENIMIENTO_UNIDAD_OPERATIVA;
    }

    @RequestMapping(value = "/abrirUnidadOperativa", method = RequestMethod.GET)
    public String abrirUnidadOperativa(ExpedienteDTO expedienteDto, HttpSession sesion, Model model) {
        LOG.info("abrirMantUnidadOperativa");
        model.addAttribute("listadoFlujoSiged", flujoSigedServiceNeg.listarFlujoSiged(new FlujoSigedFilter()));
        model.addAttribute("r", expedienteDto);

        UnidadSupervisadaFilter unidadSupervisadaFilter = new UnidadSupervisadaFilter();
        unidadSupervisadaFilter.setIdEmpresaSupervisada(expedienteDto.getEmpresaSupervisada().getIdEmpresaSupervisada());
        model.addAttribute("listadoUnidadSupervisada", unidadSupervisadaServiceNeg.listarUnidadSupervisada(unidadSupervisadaFilter));

        return ConstantesWeb.Navegacion.PAGE_INPS_UNIDAD_OPERATIVA;
    }

    @RequestMapping(value = "/abrirReasignarExpediente", method = RequestMethod.GET)
    public String abrirReasignarExpediente(ExpedienteDTO expedienteDTO, HttpSession sesion, Model model) {
        LOG.info("abrirReasignarExpediente");
        model.addAttribute("r", expedienteDTO);
        model.addAttribute("listadoDestinatario", personalServiceNeg.findPersonal(new PersonalFilter(null, Constantes.CONSTANTE_ROL_ESPECIALISTA)));
        return ConstantesWeb.Navegacion.PAGE_INPS_REASIGNAR_EXPEDIENTE;
    }

    @RequestMapping(value = "/abrirOrdenServicioSupervisor", method = RequestMethod.GET)
    public String abrirOrdenServicioSupervisor(Long idPersonal, HttpSession sesion, Model model) {
        LOG.info("abrirOrdenServicioSupervisor");
        model.addAttribute("p", personalServiceNeg.findPersonal(new PersonalFilter(idPersonal)).get(0));
        MaestroColumnaDTO maestroColumna = maestroColumnaServiceNeg.findByDominioAplicacionCodigo(Constantes.DOMINIO_TIPO_COMPONENTE, Constantes.APLICACION_INPS, Constantes.CODIGO_TIPO_COMPONENTE_ORDEN_SERVICIO).get(0);
        model.addAttribute("listadoEstadoProcesoOS", estadoProcesoServiceNeg.find(new EstadoProcesoFilter(maestroColumna.getIdMaestroColumna())));
        return ConstantesWeb.Navegacion.PAGE_INPS_ORDEN_SERVICIO_SUPERVISOR;
    }

    /* OSINE_SFS-480 - RSIS 39 - Inicio */
    @RequestMapping(value = "/abrirOrdenServicioSupervisorDevolver", method = RequestMethod.GET)
    public String abrirOrdenServicioSupervisorDevolver(Long idPersonal, HttpSession sesion, Model model) throws Exception {
        LOG.info("abrirOrdenServicioSupervisorDevolver");
        model.addAttribute("listadoTipoPeticion", maestroColumnaService.findByDominioAplicacion(Constantes.DOMINIO_TIPO_PETICION, Constantes.APLICACION_INPS));
        return ConstantesWeb.Navegacion.PAGE_INPS_ORDEN_SERVICIO_SUPERVISOR_DEVOLVER;
    }
    /* OSINE_SFS-480 - RSIS 39 - Fin */

    @RequestMapping(value = "/abrirSubirDocumento", method = RequestMethod.GET)
    // htorress - RSIS 8 - Inicio
    //public String abrirSubirDocumento (Long numeroExpediente,Long idOrdenServicio, HttpSession sesion,Model model ) {
    public String abrirSubirDocumento(Long idExpediente, Long numeroExpediente, Long idOrdenServicio, String asuntoSiged, Long codigoSiged, Long idDirccionUnidadSuprvisada,Long idUnidadSupervisada, HttpSession sesion, Model model) {
        // htorress - RSIS 8 - Fin	
        LOG.info("abrirSubirDocumento");
        model.addAttribute("numeroExpediente", numeroExpediente);
        model.addAttribute("idOrdenServicio", idOrdenServicio);
        // htorress - RSIS 8 - Inicio
//        model.addAttribute("idSupervisoraEmpresa", idSupervisoraEmpresa);
        model.addAttribute("idExpediente", idExpediente);
        model.addAttribute("asuntoSiged", asuntoSiged);
        model.addAttribute("codigoSiged", codigoSiged);
        model.addAttribute("idDirccionUnidadSuprvisada", idDirccionUnidadSuprvisada);
        model.addAttribute("idUnidadSupervisada", idUnidadSupervisada);
        // htorress - RSIS 8 - Fin
        model.addAttribute("listadoTipoDocumento", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_DOC_ADJUNTO, Constantes.APLICACION_INPS));

        return ConstantesWeb.Navegacion.PAGE_INPS_SUBIR_DOCUMENTO;
    }

    @RequestMapping(value = "/abrirSupervision", method = RequestMethod.POST)
    public String abrirSupervision(OrdenServicioDTO ordenServicioDTO, String rol, String tipoEmpresa, String empresaSupervisora, HttpSession sesion, HttpServletRequest request, Model model) {
        LOG.info("abrirSupervision -- Inicio");
        LOG.info("Rol Usuario -->" + rol);
        LOG.info("idOrdenServicio -->" + ordenServicioDTO.getIdOrdenServicio());
        String retorno="";
        try{
            String codFlujoSup=supervisionServiceNeg.buscarCodigoFlujoSupervINPS(
                    ordenServicioDTO.getExpediente().getObligacionTipo().getIdObligacionTipo(), 
                    ordenServicioDTO.getExpediente().getUnidadSupervisada().getActividad().getIdActividad(), 
                    ordenServicioDTO.getExpediente().getProceso().getIdProceso());   
            if(!StringUtil.isEmpty(codFlujoSup) && codFlujoSup.equals(Constantes.CODIGO_FLUJO_SUPERV_INPS_DSR_CRI)){
                LOG.info("-->codFlujoSup "+Constantes.CODIGO_FLUJO_SUPERV_INPS_DSR_CRI);
                retorno=abrirSupervisionDsr(ordenServicioDTO, rol, tipoEmpresa, empresaSupervisora, request, model);
            }else{
                LOG.info("-->codFlujoSup Default");
                retorno=abrirSupervisionDefault(ordenServicioDTO, rol, tipoEmpresa, empresaSupervisora, request, model);
            }
        }catch(Exception e){
            LOG.error(e.getMessage(),e);
        }
        return retorno;
    }
    
    public String abrirSupervisionDefault(OrdenServicioDTO ordenServicioDTO, String rol, String tipoEmpresa, String empresaSupervisora, HttpServletRequest request, Model model) {
        try{
            String modo = "";
            int tipoAsignacion = -1;
            SupervisionDTO supervisionDTO = null;
            List<SupervisionDTO> lista = null;
            List<SupervisionPersonaGralDTO> listaSuperPersona = null;
            List<OrdenServicioDTO> listaOrdenServicio = null;
            SupervisionPersonaGralDTO supervisionPersona = null;
            SupervisionFilter filtro = new SupervisionFilter();
            filtro.setIdOrdenServicio(ordenServicioDTO.getIdOrdenServicio());
            lista = supervisionServiceNeg.buscarSupervision(filtro);
            MaestroColumnaDTO tipoAsignacionMaestro = null;
            SupervisionDTO supervisionDTOAnt = null;
            if (!lista.isEmpty()) {
                supervisionDTO = lista.get(Constantes.PRIMERO_EN_LISTA);
            } else {
                supervisionDTO = new SupervisionDTO();
                supervisionDTO.setOrdenServicioDTO(ordenServicioDTO);
                /* OSINE_SFS-480 - RSIS 13 - Inicio */
    //                supervisionDTO = registrarSupervision(supervisionDTO,request);
    //			//traemos los datos de la supervision
    //			lista = supervisionServiceNeg.buscarSupervision(new SupervisionFilter(supervisionDTO.getIdSupervision()));
    //			supervisionDTO = lista.get(Constantes.PRIMERO_EN_LISTA);
    //                
    //	        }
                if (ordenServicioDTO.getIteracion() > Constantes.SUPERVISION_PRIMERA_ITERACION) {
                    //buscar ordenservicio anterior () getIteracion-1
                    LOG.info("ENTRO EN EL IF DE BUSQUE DA SEGUNDA ITRACION");
                    OrdenServicioFilter filtOSAnt = new OrdenServicioFilter();
                    LOG.info("getIteracion---->" + ordenServicioDTO.getIteracion());
                    LOG.info("idExpediente---->" + ordenServicioDTO.getExpediente().getIdExpediente());
                    filtOSAnt.setIteracion((ordenServicioDTO.getIteracion() - 1));
                    filtOSAnt.setIdExpediente(ordenServicioDTO.getExpediente().getIdExpediente());
                    List<OrdenServicioDTO> listOSAnt = supervisionServiceNeg.listarOrdenServicio(filtOSAnt);
                    OrdenServicioDTO OrdServAnt = null;
                    if (listOSAnt != null && !listOSAnt.isEmpty()) {
                        LOG.info("listOSAnt---->" + listOSAnt.get(Constantes.PRIMERO_EN_LISTA).getNumeroOrdenServicio());
                        OrdServAnt = listOSAnt.get(Constantes.PRIMERO_EN_LISTA);
                        SupervisionFilter filtroSupAnt = new SupervisionFilter();
                        filtroSupAnt.setIdOrdenServicio(OrdServAnt.getIdOrdenServicio());
                        List<SupervisionDTO> listaSupAnt = supervisionServiceNeg.buscarSupervision(filtroSupAnt);
                        if (listaSupAnt != null && !listaSupAnt.isEmpty()) {
                            supervisionDTO.setSupervisionAnterior(listaSupAnt.get(Constantes.PRIMERO_EN_LISTA));
                            supervisionDTOAnt = listaSupAnt.get(Constantes.PRIMERO_EN_LISTA);
                        }
                    }
                }
                /* OSINE_SFS-480 - RSIS 13 - Fin */
            }
            if (supervisionDTO.getSupervisionAnterior() != null && supervisionDTO.getSupervisionAnterior().getIdSupervision() != null) {
                //consultamos la supervision anterior
                lista = supervisionServiceNeg.buscarSupervision(new SupervisionFilter(supervisionDTO.getSupervisionAnterior().getIdSupervision()));
                if (!lista.isEmpty()) {
                    supervisionDTO.setSupervisionAnterior(lista.get(Constantes.PRIMERO_EN_LISTA));
                    if (supervisionDTO.getSupervisionAnterior().getFlagIdentificaPersona() != null && supervisionDTO.getSupervisionAnterior().getFlagIdentificaPersona().equals(Constantes.FLAG_IDENTIFICA_PERSONA_SI)) {
                        SupervisionPersonaGralFilter supervisionPersonaFilter = new SupervisionPersonaGralFilter();
                        supervisionPersonaFilter.setIdSupervision(supervisionDTO.getSupervisionAnterior().getIdSupervision());
                        listaSuperPersona = supervisionServiceNeg.listarSupervisionPersona(supervisionPersonaFilter);
                        if (!listaSuperPersona.isEmpty()) {
                            supervisionPersona = listaSuperPersona.get(Constantes.PRIMERO_EN_LISTA);
                        }
                        supervisionDTO.getSupervisionAnterior().setSupervisionPersonaGral(supervisionPersona);
                        
                        //esto es para 2da vez q abreSupervision, en la 1ra vez q abresupervision recien se registra el id_supervision
                        if(supervisionDTOAnt==null){supervisionDTOAnt=new SupervisionDTO();}
                        
                        supervisionDTOAnt.setSupervisionPersonaGral(supervisionPersona);
                    }
                    model.addAttribute("listadoEntidadSuper", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_ENTIDAD, Constantes.APLICACION_SGLSS));
                    model.addAttribute("listadoTipoSupervisorSuper", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_SUPERVISOR, Constantes.APLICACION_INPS));
                }
            }
            if (rol != null) {
                // htorress - RSIS 8 - Inicio
                //if(rol.toUpperCase().equals(Constantes.CONSTANTE_ROL_ESPECIALISTA)){
                if (rol.toUpperCase().equals(Constantes.CONSTANTE_ROL_ESPECIALISTA) || rol.toUpperCase().equals(Constantes.CONSTANTE_ROL_ESPECIALISTA_LEGAL) || rol.toUpperCase().equals(Constantes.CONSTANTE_ROL_SUPERVISOR_REGIONAL)
                        || rol.toUpperCase().equals(Constantes.CONSTANTE_ROL_JEFE_UNIDAD) || rol.toUpperCase().equals(Constantes.CONSTANTE_ROL_JEFE_REGIONAL)) {
                    // htorress - RSIS 8 - Fin
                    model.addAttribute("tipoEmpresa", tipoEmpresa);
                    model.addAttribute("empresaSupervisora", empresaSupervisora);
                    modo = "consulta";
                } else if (rol.toUpperCase().equals(Constantes.CONSTANTE_ROL_SUPERVISOR)) {
                    modo = "registro";
                }
            }

            listaOrdenServicio = supervisionServiceNeg.listarOrdenServicio(new OrdenServicioFilter(ordenServicioDTO.getIdOrdenServicio()));
            if (!listaOrdenServicio.isEmpty()) {
                supervisionDTO.setOrdenServicioDTO(listaOrdenServicio.get(Constantes.PRIMERO_EN_LISTA));
            }
            List<MaestroColumnaDTO> listaMaestroColumna = maestroColumnaServiceNeg.findByDominioAplicacionCodigo(Constantes.DOMINIO_TIPO_ASIGNACION, Constantes.APLICACION_INPS, Constantes.CODIGO_TIPO_ASIGNACION_CON_VISITA);
            if (!listaMaestroColumna.isEmpty()) {
                tipoAsignacionMaestro = listaMaestroColumna.get(Constantes.PRIMERO_EN_LISTA);
            }
            if (supervisionDTO.getOrdenServicioDTO().getIdTipoAsignacion().equals(tipoAsignacionMaestro.getIdMaestroColumna())) {
                tipoAsignacion = Constantes.CON_VISITA;
            }
            if (supervisionDTO.getFlagIdentificaPersona() != null && supervisionDTO.getFlagIdentificaPersona().equals(Constantes.FLAG_IDENTIFICA_PERSONA_SI)) {
                SupervisionPersonaGralFilter supervisionPersonaFilter = new SupervisionPersonaGralFilter();
                supervisionPersonaFilter.setIdSupervision(supervisionDTO.getIdSupervision());
                listaSuperPersona = supervisionServiceNeg.listarSupervisionPersona(supervisionPersonaFilter);
                if (!listaSuperPersona.isEmpty()) {
                    supervisionPersona = listaSuperPersona.get(Constantes.PRIMERO_EN_LISTA);
                }
                supervisionDTO.setSupervisionPersonaGral(supervisionPersona);
            }           
            /* OSINE791 - RSIS69 - Inicio */     
            //if (supervisionDTO.getIdSupervision() == null && ordenServicioDTO.getIteracion().equals(new Long(Constantes.SUPERVISION_PRIMERA_ITERACION))) {
            if (supervisionDTO.getIdSupervision() == null && ordenServicioDTO.getIteracion().equals(new Long(Constantes.SUPERVISION_PRIMERA_ITERACION))) {
                LOG.info("SE PROCEDERA A CREAR LA SUPERVISION Y LA ITERACION ES 1");
                supervisionDTO = supervisionServiceNeg.registrarSupervision(supervisionDTO, Constantes.getUsuarioDTO(request));
            } else {
                if (supervisionDTO.getIdSupervision() == null && ordenServicioDTO.getIteracion() > Constantes.SUPERVISION_PRIMERA_ITERACION) {
                  LOG.info("NO ESTA CREADA LA SUPERVISION Y ES DE ITERACION 2");
                  if(supervisionDTO.getSupervisionAnterior() != null && supervisionDTOAnt != null){
                      LOG.info("SUPERVISION ANT ES DISTINTA A NULL");
                      supervisionDTO.setSupervisionAnterior(supervisionDTOAnt);
                      supervisionDTO = supervisionServiceNeg.registrarSupervision(supervisionDTO, Constantes.getUsuarioDTO(request));
                      if(supervisionDTO != null){
                        supervisionDTO.setSupervisionAnterior(supervisionDTOAnt);  
                      }else{
                         LOG.info("LA SUPERVISION NO SE PUDO CREAR"); 
                      }
                  }else{
                      LOG.info("LA SUPERVISION ANTERIOR ES NULL");
                  } 
                }else{
                    LOG.info("SI HAY UNA SUPERVISION |" + supervisionDTO.getIdSupervision() + "|");
                }                
            }
            /* OSINE791 - RSIS69 - Fin */
            model.addAttribute("modo", modo);
            model.addAttribute("sup", supervisionDTO);
            model.addAttribute("asignacion", tipoAsignacion);
            model.addAttribute("listadoTipoDocumento", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_DOCUMENTO, Constantes.APLICACION_SGLSS));
            model.addAttribute("listadoCargo", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_CARGOS, Constantes.APLICACION_INPS));
            model.addAttribute("listadoMotivoNoSuprvsn", supervisionServiceNeg.listarMotivoNoSupervisionAll());
            model.addAttribute("rol", rol == null ? "" : rol);
            LOG.info("abrirSupervision -- Fin");
        }catch(Exception e){
            LOG.error(e.getMessage(),e);
        }
        return ConstantesWeb.Navegacion.PAGE_INPS_SUPERVISION;
    }

    /* OSINE791 - RSIS2 - Inicio */
    public String abrirSupervisionDsr(OrdenServicioDTO ordenServicioDTO, String rol, String tipoEmpresa, String empresaSupervisora, HttpServletRequest request, Model model) {
        try{
            String modo = "";
            int tipoAsignacion = -1;
            SupervisionDTO supervisionDTO = null;
            List<SupervisionDTO> lista = null;
            List<SupervisionPersonaGralDTO> listaSuperPersona = null;
            List<OrdenServicioDTO> listaOrdenServicio = null;
            SupervisionPersonaGralDTO supervisionPersona = null;
            SupervisionFilter filtro = new SupervisionFilter();
            filtro.setIdOrdenServicio(ordenServicioDTO.getIdOrdenServicio());
            lista = supervisionServiceNeg.buscarSupervision(filtro);
            MaestroColumnaDTO tipoAsignacionMaestro = null;
            if (!lista.isEmpty()) {
                LOG.info("Supervision ya existe");
                supervisionDTO = lista.get(Constantes.PRIMERO_EN_LISTA);
            } else {
                LOG.info("Supervision no existe, se procede a crear");
                supervisionDTO = new SupervisionDTO();
                supervisionDTO.setOrdenServicioDTO(ordenServicioDTO);
                if (ordenServicioDTO.getIteracion() > Constantes.SUPERVISION_PRIMERA_ITERACION) {
                    OrdenServicioFilter filtOSAnt = new OrdenServicioFilter();
                    LOG.info("getIteracion---->" + ordenServicioDTO.getIteracion());
                    LOG.info("idExpediente---->" + ordenServicioDTO.getExpediente().getIdExpediente());
                    filtOSAnt.setIteracion(new Long(1));
                    filtOSAnt.setIdExpediente(ordenServicioDTO.getExpediente().getIdExpediente());
                    List<OrdenServicioDTO> listOSAnt = supervisionServiceNeg.listarOrdenServicio(filtOSAnt);
                    OrdenServicioDTO OrdServAnt = null;
                    if (listOSAnt != null && !listOSAnt.isEmpty()) {
                        LOG.info("listOSAnt---->" + listOSAnt.get(Constantes.PRIMERO_EN_LISTA).getNumeroOrdenServicio());
                        OrdServAnt = listOSAnt.get(Constantes.PRIMERO_EN_LISTA);
                        SupervisionFilter filtroSupAnt = new SupervisionFilter();
                        filtroSupAnt.setIdOrdenServicio(OrdServAnt.getIdOrdenServicio());
                        List<SupervisionDTO> listaSupAnt = supervisionServiceNeg.buscarSupervision(filtroSupAnt);
                        if (listaSupAnt != null && !listaSupAnt.isEmpty()) {
                            supervisionDTO.setSupervisionAnterior(listaSupAnt.get(Constantes.PRIMERO_EN_LISTA));                            
                        }
                    }
                }
                //crea registro supervision
                supervisionDTO.setFlagSupervision(Constantes.FLAG_SUPERVISION);
                supervisionDTO = supervisionServiceNeg.registrarSupervision(supervisionDTO, Constantes.getUsuarioDTO(request));                
            }
            if (supervisionDTO.getSupervisionAnterior() != null && supervisionDTO.getSupervisionAnterior().getIdSupervision() != null) {
                //consultamos la supervision anterior
                lista = supervisionServiceNeg.buscarSupervision(new SupervisionFilter(supervisionDTO.getSupervisionAnterior().getIdSupervision()));
                if (!lista.isEmpty()) {
                    supervisionDTO.setSupervisionAnterior(lista.get(Constantes.PRIMERO_EN_LISTA));
                    if (supervisionDTO.getSupervisionAnterior().getFlagIdentificaPersona() != null && supervisionDTO.getSupervisionAnterior().getFlagIdentificaPersona().equals(Constantes.FLAG_IDENTIFICA_PERSONA_SI)) {
                        SupervisionPersonaGralFilter supervisionPersonaFilter = new SupervisionPersonaGralFilter();
                        supervisionPersonaFilter.setIdSupervision(supervisionDTO.getSupervisionAnterior().getIdSupervision());
                        listaSuperPersona = supervisionServiceNeg.listarSupervisionPersona(supervisionPersonaFilter);
                        if (!listaSuperPersona.isEmpty()) {
                            supervisionPersona = listaSuperPersona.get(Constantes.PRIMERO_EN_LISTA);
                        }
                        supervisionDTO.getSupervisionAnterior().setSupervisionPersonaGral(supervisionPersona);
                    }
                    model.addAttribute("listadoEntidadSuper", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_ENTIDAD, Constantes.APLICACION_SGLSS));
                    model.addAttribute("listadoTipoSupervisorSuper", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_SUPERVISOR, Constantes.APLICACION_INPS));
                }
            }
            if (rol != null) {
                if (rol.toUpperCase().equals(Constantes.CONSTANTE_ROL_ESPECIALISTA) || rol.toUpperCase().equals(Constantes.CONSTANTE_ROL_SUPERVISOR_REGIONAL)
                        || rol.toUpperCase().equals(Constantes.CONSTANTE_ROL_JEFE_UNIDAD) || rol.toUpperCase().equals(Constantes.CONSTANTE_ROL_JEFE_REGIONAL)) {
                    model.addAttribute("tipoEmpresa", tipoEmpresa);
                    model.addAttribute("empresaSupervisora", empresaSupervisora);
                    modo = "consulta";
                } else if (rol.toUpperCase().equals(Constantes.CONSTANTE_ROL_SUPERVISOR)) {
                    modo = "registro";
                }
            }

            listaOrdenServicio = supervisionServiceNeg.listarOrdenServicio(new OrdenServicioFilter(ordenServicioDTO.getIdOrdenServicio()));
            if (!listaOrdenServicio.isEmpty()) {
                supervisionDTO.setOrdenServicioDTO(listaOrdenServicio.get(Constantes.PRIMERO_EN_LISTA));
            }
            List<MaestroColumnaDTO> listaMaestroColumna = maestroColumnaServiceNeg.findByDominioAplicacionCodigo(Constantes.DOMINIO_TIPO_ASIGNACION, Constantes.APLICACION_INPS, Constantes.CODIGO_TIPO_ASIGNACION_CON_VISITA);
            if (!listaMaestroColumna.isEmpty()) {
                tipoAsignacionMaestro = listaMaestroColumna.get(Constantes.PRIMERO_EN_LISTA);
            }
            if (supervisionDTO.getOrdenServicioDTO().getIdTipoAsignacion().equals(tipoAsignacionMaestro.getIdMaestroColumna())) {
                tipoAsignacion = Constantes.CON_VISITA;
            }
            if (supervisionDTO.getFlagIdentificaPersona() != null && supervisionDTO.getFlagIdentificaPersona().equals(Constantes.FLAG_IDENTIFICA_PERSONA_SI)) {
                SupervisionPersonaGralFilter supervisionPersonaFilter = new SupervisionPersonaGralFilter();
                supervisionPersonaFilter.setIdSupervision(supervisionDTO.getIdSupervision());
                listaSuperPersona = supervisionServiceNeg.listarSupervisionPersona(supervisionPersonaFilter);
                if (!listaSuperPersona.isEmpty()) {
                    supervisionPersona = listaSuperPersona.get(Constantes.PRIMERO_EN_LISTA);
                }
                supervisionDTO.setSupervisionPersonaGral(supervisionPersona);
            }

            /* OSINE_SFS-791 - RSIS 16 - Inicio */
            String flagInfracciones = "";

            if (ordenServicioDTO.getIdOrdenServicio() != null) {
                DetalleSupervisionFilter filtroInfracciones = new DetalleSupervisionFilter();
                filtroInfracciones.setIdOrdenServicio(ordenServicioDTO.getIdOrdenServicio());
                filtroInfracciones.setCodigoResultadoSupervision(Constantes.CODIGO_RESULTADO_INCUMPLE);
                List<DetalleSupervisionDTO> listaIncumplidas = supervisionServiceNeg.findDetalleSupervision(filtroInfracciones);
                if (listaIncumplidas != null && listaIncumplidas.size() == 0) {
                    flagInfracciones = Constantes.FLAG_OBLIGACIONES_NO_INCUMPLIDAS;
                } else {
                    flagInfracciones = Constantes.FLAG_OBLIGACIONES_SI_INCUMPLIDAS;
                }
            }
            LOG.info("flagInfracciones : " + flagInfracciones);
            model.addAttribute("flagInfracciones", flagInfracciones);
            /* OSINE_SFS-791 - RSIS 16 - Fin */

            //probando lista resultado
            List<ResultadoSupervisionDTO> listaestados = resultadoSupervisionServiceNeg.listarResultadoSupervision(new ResultadoSupervisionFilter());

            model.addAttribute("ValorradioDsrOblDelIncumplimientoNo",listaestados.get(1).getIdResultadosupervision());
            model.addAttribute("CodigoradioDsrOblDelIncumplimientoNo",listaestados.get(1).getCodigo());

            model.addAttribute("ValorradioDsrOblDelIncumplimientoSi",listaestados.get(2).getIdResultadosupervision());
            model.addAttribute("CodigoradioDsrOblDelIncumplimientoSi",listaestados.get(2).getCodigo());

            model.addAttribute("ValorradioDsrOblDelIncumplimientoNoAplica",listaestados.get(3).getIdResultadosupervision());
            model.addAttribute("CodigoradioDsrOblDelIncumplimientoNoAplica",listaestados.get(3).getCodigo());

            model.addAttribute("ValorradioDsrOblDelIncumplimientoPorVerificar",listaestados.get(4).getIdResultadosupervision());
            model.addAttribute("CodigoradioDsrOblDelIncumplimientoPorVerificar",listaestados.get(4).getCodigo());

            model.addAttribute("ValorradioDsrOblDelIncumplimientoObstaculizado",listaestados.get(5).getIdResultadosupervision());
            model.addAttribute("CodigoradioDsrOblDelIncumplimientoObstaculizado",listaestados.get(5).getCodigo());

            model.addAttribute("ValorradioSupervisionInicialSi",listaestados.get(10).getIdResultadosupervision());
            model.addAttribute("CodigoradioSupervisionInicialSi",listaestados.get(10).getCodigo());
            
            model.addAttribute("ValorradioSupervisionInicialNo",listaestados.get(11).getIdResultadosupervision());
            model.addAttribute("CodigoradioSupervisionInicialNo",listaestados.get(11).getCodigo());
            
            model.addAttribute("ValorradioSupervisionInicialPorVerificar",listaestados.get(12).getIdResultadosupervision());
            model.addAttribute("CodigoradioSupervisionInicialPorVerificar",listaestados.get(12).getCodigo());

            model.addAttribute("ValorradioSupervisionInicialObstaculizado",listaestados.get(13).getIdResultadosupervision());
            model.addAttribute("CodigoradioSupervisionInicialObstaculizado",listaestados.get(13).getCodigo());
            
            model.addAttribute("ValorradioSupervisionSubsanadoSi",listaestados.get(16).getIdResultadosupervision());
            model.addAttribute("CodigoradioSupervisionSubsanadoSi",listaestados.get(16).getCodigo());
            
            model.addAttribute("ValorradioSupervisionSubsanadoNo",listaestados.get(17).getIdResultadosupervision());
            model.addAttribute("CodigoradioSupervisionSubsanadoNo",listaestados.get(17).getCodigo());

            if (supervisionDTO.getResultadoSupervisionInicialDTO() != null) {
                ResultadoSupervisionFilter filtroEstadoSupervisionPorverifi = new ResultadoSupervisionFilter();
                filtroEstadoSupervisionPorverifi.setCodigo(Constantes.CODIGO_RESULTADO_SUPERVISION_INICIAL_PORVERIFICAR);
                ResultadoSupervisionDTO objetoEstadoSupervisionPorVerific = resultadoSupervisionServiceNeg.getResultadoSupervision(filtroEstadoSupervisionPorverifi);
                if (supervisionDTO.getResultadoSupervisionInicialDTO().getIdResultadosupervision().equals(objetoEstadoSupervisionPorVerific.getIdResultadosupervision())) {
                    int respuesta = 0;
                    respuesta = VerificarSupervisionObstaculizada(supervisionDTO);
                    if (respuesta > 0) {
                        ResultadoSupervisionFilter filtroEstadoSupervisionObstaculizado = new ResultadoSupervisionFilter();
                        filtroEstadoSupervisionObstaculizado.setCodigo(Constantes.CODIGO_RESULTADO_SUPERVISION_INICIAL_OBSTACULIZADO);
                        ResultadoSupervisionDTO objetoEstadoSupervisionObstaculizado = resultadoSupervisionServiceNeg.getResultadoSupervision(filtroEstadoSupervisionObstaculizado);
                        supervisionDTO.setResultadoSupervisionInicialDTO(objetoEstadoSupervisionObstaculizado);
                    }
                }
            }
            model.addAttribute("modo", modo);
            model.addAttribute("sup", supervisionDTO);
            model.addAttribute("asignacion", tipoAsignacion);
            model.addAttribute("listadoTipoDocumento", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_DOCUMENTO, Constantes.APLICACION_SGLSS));
            model.addAttribute("listadoCargo", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_CARGOS, Constantes.APLICACION_INPS));
            model.addAttribute("listadoMotivoNoSuprvsn", supervisionServiceNeg.listarMotivoNoSupervisionAll());
        }catch(Exception e){
            LOG.error(e.getMessage(),e);
        }
        return ConstantesWeb.Navegacion.PAGE_INPS_SUPERVISION_DSR;
    }
    /* OSINE791 - RSIS2 - Fin */
    /* OSINE791 - RSIS04 - Inicio */
    public int VerificarSupervisionObstaculizada(SupervisionDTO supervisionDTO) {
        LOG.info("Verificar Supervision Inicial Por Verificar - Obstaculizadas - VerificarSupervisionObstaculizada");
        int rpta = 0;
        try {
            rpta = supervisionServiceNeg.VerificarSupervisionObstaculizada(supervisionDTO);
            return rpta;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            rpta = 0;
        }
        return rpta;
    }
     /*OSINE791-RSIS04 - Fin */
    

    private SupervisionDTO registrarSupervision(SupervisionDTO supervisionDTO, HttpServletRequest request) {
        SupervisionDTO supervisionDTOAux = null;
        try {
            if (supervisionDTO.getOrdenServicioDTO() != null && supervisionDTO.getOrdenServicioDTO().getIdOrdenServicio() != null) {
                supervisionDTOAux = supervisionServiceNeg.registrarSupervisionBloque(supervisionDTO, Constantes.getUsuarioDTO(request));
            }
        } catch (Exception e) {
            LOG.error("Error en registrarSupervision", e);
        }
        return supervisionDTOAux;
    }

    @RequestMapping(value = "/abrirDescripcionHallazgo", method = RequestMethod.GET) //mdiosesf - RSIS6
    public String abrirDescripcionHallazgo(String tipoCumple, String tipo, String tipoRespaldo, String divProbatorio, DetalleSupervisionDTO detalleSupervisionDTO, HttpSession sesion, Model model) {
        LOG.info("abrirDescripcionHallazgo");
        List<DetalleSupervisionDTO> listaDetalleSuper = null;
        String registado = "";
        String archivosPermitidos = "";
        if (Constantes.RESPALDO_DETALLE_SUPERVISION.equals(tipoRespaldo.trim())) {
            if (detalleSupervisionDTO.getIdDetalleSupervisionAnt() != null) {
                listaDetalleSuper = supervisionServiceNeg.findDetalleSupervision(new DetalleSupervisionFilter(detalleSupervisionDTO.getIdDetalleSupervisionAnt()));
                if (!listaDetalleSuper.isEmpty()) {
                    DetalleSupervisionDTO detalleSuper = listaDetalleSuper.get(Constantes.PRIMERO_EN_LISTA);
                    model.addAttribute("ds", detalleSuper);
                    tipoCumple = detalleSuper.getFlagResultado();
                }
            }
        } else {
            model.addAttribute("ds", detalleSupervisionDTO);
            if (tipoCumple.equals(Constantes.ESTADO_INACTIVO)) {
                DetalleSupervisionFilter filtro = new DetalleSupervisionFilter();
                filtro.setIdDetalleSupervision(detalleSupervisionDTO.getIdDetalleSupervision());
                filtro.setFlagResultado(tipoCumple);
                List<DetalleSupervisionDTO> listado = supervisionServiceNeg.findDetalleSupervision(filtro);
                if (listado.isEmpty()) {
                    registado = "0";
                } else {
                    registado = "1";
                }
            }
        }
        List<MaestroColumnaDTO> lista = maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_ADJ_SUPERVISION, Constantes.APLICACION_INPS);
        if (!lista.isEmpty()) {
            for (MaestroColumnaDTO registro : lista) {
                archivosPermitidos += registro.getCodigo() + ", ";
            }
            if (!Constantes.CONSTANTE_VACIA.equals(archivosPermitidos.trim())) {
                archivosPermitidos = archivosPermitidos.substring(0, (archivosPermitidos.length() - 2));
            }
        }
        model.addAttribute("registado", registado);
        model.addAttribute("tipoDH", tipo);
        model.addAttribute("divProbatorio", divProbatorio); //mdiosesf - RSIS6
        model.addAttribute("archivosPermitidos", archivosPermitidos.toUpperCase());
        model.addAttribute("tamanioAdjuntoMB", TAMANIO_MAXIMO_ARCHIVO_MEGAS);
        model.addAttribute("tipoCumple", tipoCumple);
        if (tipoCumple.equals(Constantes.DETALLE_SUPERVISION_INCUMPLE)) {
            model.addAttribute("listadoTipificaciones", supervisionServiceNeg.listarTipificacion(new TipificacionFilter(detalleSupervisionDTO.getObligacion().getIdObligacion())));
        }
        return ConstantesWeb.Navegacion.PAGE_INPS_SUPERVISION_DESC_HALLAZGO;
    }

    @RequestMapping(value = "/abrirEvaluacionDescargo", method = RequestMethod.GET) //mdiosesf - RSIS6
    public String abrirEvaluacionDescargo(String tipoSancion, String tipo, String divProbatorio, DetalleSupervisionDTO detalleSupervisionDTO, HttpSession sesion, Model model) {
        LOG.info("abrirEvaluacionDescargo");
        String registado = "";
        String archivosPermitidos = "";
        DetalleSupervisionFilter filtro = new DetalleSupervisionFilter();
        filtro.setIdDetalleSupervision(detalleSupervisionDTO.getIdDetalleSupervision());
        filtro.setFlagResultado(tipoSancion);
        if (tipoSancion.equals(Constantes.CONSTANTE_VACIA) || tipoSancion == null) {
            DetalleSupervisionDTO detalleSupervision = supervisionServiceNeg.findDetalleSupervision(new DetalleSupervisionFilter(detalleSupervisionDTO.getIdDetalleSupervision())).get(Constantes.PRIMERO_EN_LISTA);
            filtro.setFlagResultado(detalleSupervision.getFlagResultado());
            tipoSancion = detalleSupervision.getFlagResultado();
        }
        filtro.setFlagRegistrado(Constantes.ESTADO_ACTIVO);
        List<DetalleSupervisionDTO> listado = supervisionServiceNeg.findDetalleSupervision(filtro);
        if (listado.isEmpty()) {
            registado = "0";
        } else {
            registado = "1";
        }
        List<MaestroColumnaDTO> lista = maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_ADJ_SUPERVISION, Constantes.APLICACION_INPS);
        if (!lista.isEmpty()) {
            for (MaestroColumnaDTO registro : lista) {
                archivosPermitidos += registro.getCodigo() + ", ";
            }
            if (!Constantes.CONSTANTE_VACIA.equals(archivosPermitidos.trim())) {
                archivosPermitidos = archivosPermitidos.substring(0, (archivosPermitidos.length() - 2));
            }
        }
        model.addAttribute("registado", registado);
        model.addAttribute("dsED", detalleSupervisionDTO);
        model.addAttribute("tipoED", tipo);
        model.addAttribute("divProbatorio", divProbatorio); //mdiosesf - RSIS6
        model.addAttribute("archivosPermitidos", archivosPermitidos.toUpperCase());
        model.addAttribute("tamanioAdjuntoMB", TAMANIO_MAXIMO_ARCHIVO_MEGAS);
        model.addAttribute("tipoSancion", tipoSancion);
        return ConstantesWeb.Navegacion.PAGE_INPS_SUPERVISION_EVAL_DESCARGO;
    }

    @RequestMapping(value = "/abrirAdjuntoSupervision", method = RequestMethod.GET)
    public String abrirAdjuntoSupervision(String tipo, SupervisionDTO supervisionDTO, HttpSession sesion, Model model) {
        LOG.info("abrirAdjuntoSupervision");
        String archivosPermitidos = "";
        List<MaestroColumnaDTO> lista = maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_ADJ_SUPERVISION, Constantes.APLICACION_INPS);
        if (!lista.isEmpty()) {
            for (MaestroColumnaDTO registro : lista) {
                archivosPermitidos += registro.getCodigo() + ", ";
            }
            if (!Constantes.CONSTANTE_VACIA.equals(archivosPermitidos.trim())) {
                archivosPermitidos = archivosPermitidos.substring(0, (archivosPermitidos.length() - 2));
            }
        }
        model.addAttribute("s", supervisionDTO);
        model.addAttribute("modoDA", tipo);
        model.addAttribute("archivosPermitidos", archivosPermitidos.toUpperCase());
        model.addAttribute("tamanioAdjuntoMB", TAMANIO_MAXIMO_ARCHIVO_MEGAS);
        return ConstantesWeb.Navegacion.PAGE_INPS_SUPERVISION_DOCU_ADJUNTO;
    }
    
    @RequestMapping(value = "/verAdjuntoSupervision", method = RequestMethod.GET)
    public String verAdjuntoSupervision(String tipo, SupervisionDTO supervisionDTO, HttpSession sesion, Model model) {
        LOG.info("abrirAdjuntoSupervision");
        String archivosPermitidos = "";
        List<MaestroColumnaDTO> lista = maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_ADJ_SUPERVISION, Constantes.APLICACION_INPS);
        if (!lista.isEmpty()) {
            for (MaestroColumnaDTO registro : lista) {
                archivosPermitidos += registro.getCodigo() + ", ";
            }
            if (!Constantes.CONSTANTE_VACIA.equals(archivosPermitidos.trim())) {
                archivosPermitidos = archivosPermitidos.substring(0, (archivosPermitidos.length() - 2));
            }
        }
        model.addAttribute("s", supervisionDTO);
        model.addAttribute("modoDA", tipo);
        model.addAttribute("archivosPermitidos", archivosPermitidos.toUpperCase());
        model.addAttribute("tamanioAdjuntoMB", TAMANIO_MAXIMO_ARCHIVO_MEGAS);
        model.addAttribute("modo","VER");
        return ConstantesWeb.Navegacion.PAGE_INPS_SUPERVISION_DOCU_ADJUNTO;
    }

    @RequestMapping(value = "/abrirExpeDeriResponsable", method = RequestMethod.GET)
    public String abrirExpeDeriResponsable(HttpSession sesion, Model model) {
        LOG.info("abrirExpeDeriResponsable");
        return ConstantesWeb.Navegacion.PAGE_INPS_RESPONSABLE_DERIVADOS;
    }

    /**
     * @param seleccion, "multiple" o "individual" tipo de seleccion en el arbol
     * de actividades
     */
    @RequestMapping(value = "/abrirPopupSeleccActividad", method = RequestMethod.GET)
    public String abrirPopupSeleccActividad(String seleccion, String sufijo, HttpSession sesion, HttpServletRequest request, Model model) {
        LOG.info("abrirPopupSeleccActividad");
        model.addAttribute("seleccion", seleccion);
        model.addAttribute("sufijo", sufijo);

        String arbolActividades = "<li id='0' class='folder'>ACTIVIDADES"
                + "<ul>";
        arbolActividades += "</li>";
        model.addAttribute("arbolActividades", arbolActividades);
        return ConstantesWeb.Navegacion.PAGE_INPS_FRM_SELECT_ACTIVIDADES;
    }

    @RequestMapping(value = "/loadActividad", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> loadActividad() {
        LOG.info("procesando loadActividad");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            int[] auxiliar = new int[1];
            auxiliar[0] = 0;
            List<ActividadDTO> listado;
            listado = actividadServiceNeg.listarActividad(auxiliar);
            retorno.put("filas", listado);
            retorno.put("total", auxiliar[0]);
        } catch (Exception ex) {
            LOG.error("", ex);
        }
        return retorno;
    }

    //Busca_Division-Unidad: MDI_Unidad_Organica -- lgarcia
    @RequestMapping(value = "/findUnidadDivisionPersonal", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> findUnidadDivisionPersonal(PersonalFilter filtro, HttpSession sesion, HttpServletRequest request) {
        LOG.info("Funcion: Find Unidad-Division -- Controller -- Metodo-> findUnidadDivision");
        Map<String, Object> listaResultado = new HashMap<String, Object>();
        try {
            filtro.setFlagDefault(Constantes.ESTADO_ACTIVO);
            List<PersonalDTO> personalUnidOrgDefault = personalServiceNeg.findPersonal(filtro);
            if (personalUnidOrgDefault != null && personalUnidOrgDefault.size() > 0
                    && personalUnidOrgDefault.get(0).getPersonalUnidadOrganicaDefault() != null
                    && personalUnidOrgDefault.get(0).getPersonalUnidadOrganicaDefault().getUnidadOrganica() != null) {

                List<UnidadOrganicaDTO> unidadUO = unidadOrganicaServiceNeg.findUnidadOrganica(new UnidadOrganicaFilter(personalUnidOrgDefault.get(0).getPersonalUnidadOrganicaDefault().getUnidadOrganica().getIdUnidadOrganica(), null));
                List<UnidadOrganicaDTO> subDivUO = unidadOrganicaServiceNeg.findUnidadOrganica(new UnidadOrganicaFilter(unidadUO.get(0).getIdUnidadOrganicaSuperior(), null));
                List<UnidadOrganicaDTO> divUO = unidadOrganicaServiceNeg.findUnidadOrganica(new UnidadOrganicaFilter(subDivUO.get(0).getIdUnidadOrganicaSuperior(), null));

                /* OSINE791 - RSIS1 Inicio */
                request.getSession().setAttribute(Constantes.PERSONAL_UNIDAD_ORGANICA_SIGLA,divUO.get(0).getSigla());
                /* OSINE791 - RSIS1 Fin */
                request.getSession().setAttribute(Constantes.PERSONAL_UNIDAD_ORGANICA_DIVISION, divUO.get(0).getIdUnidadOrganica());
                listaResultado.put("division", divUO.get(0).getDescripcion());
                listaResultado.put("unidad", unidadUO.get(0).getDescripcion());
            }
        } catch (Exception ex) {
            LOG.error("error controller", ex);
        }
        return listaResultado;
    }

    @RequestMapping(value = "/cargarEstadoProceso", method = RequestMethod.GET) //mdiosesf
    public @ResponseBody
    List<EstadoProcesoDTO> cargarEstadoProceso(HttpSession session, HttpServletRequest request) {
        LOG.info("cargarEstadoProceso");
        List<EstadoProcesoDTO> retorno = new ArrayList<EstadoProcesoDTO>();
        try {
            EstadoProcesoFilter estadoProcesoFilter = new EstadoProcesoFilter();
            estadoProcesoFilter.setIdTipoEstadoProceso(Constantes.IDENTIFICADOR_TIPO_ESTADO_PROCESO_OS);
            retorno = estadoProcesoDAO.find(estadoProcesoFilter);
        } catch (Exception e) {
            LOG.error("error cargarEstadoProceso", e);
        }
        return retorno;
    }
    
    @RequestMapping(value = "/cargarEstadoProcesoOS", method = RequestMethod.GET) //mdiosesf
    public @ResponseBody
    List<EstadoProcesoDTO> cargarEstadoProcesoOS(HttpSession session, HttpServletRequest request) {
        LOG.info("cargarEstadoProceso");
        List<EstadoProcesoDTO> retorno = new ArrayList<EstadoProcesoDTO>();
        try {
            EstadoProcesoFilter estadoProcesoFilter = new EstadoProcesoFilter();
            estadoProcesoFilter.setIdTipoEstadoProceso(Constantes.IDENTIFICADOR_TIPO_ESTADO_PROCESO_ORDEN_SERVICIO);
            retorno = estadoProcesoDAO.find(estadoProcesoFilter);
        } catch (Exception e) {
            LOG.error("error cargarEstadoProcesoOS", e);
        }
        return retorno;
    }
    /* OSINE_SFS-480 - RSIS 29, 30 y 31 - Inicio */

    @RequestMapping(value = "/cargarDepartamento", method = RequestMethod.GET)
    public @ResponseBody
    List<DepartamentoDTO> cargarDepartamento(HttpSession session, HttpServletRequest request, Model model) {
        LOG.info("cargarDepartamento");
        List<DepartamentoDTO> retorno = new ArrayList<DepartamentoDTO>();
        try {
            List<DepartamentoDTO> departamentos = ubigeoServiceNeg.obtenerDepartamentos();
            retorno = (List<DepartamentoDTO>) departamentos;
        } catch (Exception e) {
            LOG.error("error cargarDepartamento", e);
        }
        return retorno;
    }

    @RequestMapping(value = "/cargarPeticion", method = RequestMethod.GET)
    public @ResponseBody
    List<MaestroColumnaDTO> cargarPeticion(HttpSession session, HttpServletRequest request) {
        LOG.info("cargarPeticion");
        List<MaestroColumnaDTO> retorno = new ArrayList<MaestroColumnaDTO>();
        try {
            retorno = maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_PETICION, Constantes.APLICACION_INPS);
        } catch (Exception e) {
            LOG.error("error cargarPeticion", e);
        }
        return retorno;
    }
    /* OSINE_SFS-480 - RSIS 29, 30 y 31 - Fin */
    /* OSINE_SFS-480 - RSIS 03 - Inicio */

    @RequestMapping(value = "/abrirEnviarMensajeria", method = RequestMethod.GET)
    public String abrirEnviarMensajeria(ExpedienteDTO expedienteDto, String flagNotificar, HttpSession sesion, Model model, HttpServletRequest request) {
        LOG.info("abrirEnviarMensajeria");
        model.addAttribute("idPersonalSIGED", Constantes.getIDPERSONALSIGED(request));
        model.addAttribute("expDto", expedienteDto);
        model.addAttribute("flagNotificar", flagNotificar);
        return ConstantesWeb.Navegacion.PAGE_INPS_ORDEN_SERVICIO_ENVIA_MENSAJERIA;
    }
    /* OSINE_SFS-480 - RSIS 03 - Fin */

    @RequestMapping(value = "/abrirOrdenServicioAsignar", method = RequestMethod.POST)
    public String abrirOrdenServicioAsignar(String rolSesion, ExpedienteDTO expedienteDto, HttpSession sesion, Model model, HttpServletRequest request) {
        LOG.info("abrirOrdenServiciosignar");
        String tipo=Constantes.ORDEN_SERVICIO_ASIGNAR;
        
            model.addAttribute("tipo", tipo);
            model.addAttribute("listadoFlujoSiged", flujoSigedServiceNeg.listarFlujoSiged(new FlujoSigedFilter()));
            
            if (expedienteDto.getEmpresaSupervisada() != null && expedienteDto.getEmpresaSupervisada().getIdEmpresaSupervisada() != null) {//en caso GENERAR no hay empresa
                UnidadSupervisadaFilter unidadSupervisadaFilter = new UnidadSupervisadaFilter();
                unidadSupervisadaFilter.setIdEmpresaSupervisada(expedienteDto.getEmpresaSupervisada().getIdEmpresaSupervisada());
                model.addAttribute("listadoUnidadSupervisada", unidadSupervisadaServiceNeg.listarUnidadSupervisada(unidadSupervisadaFilter));
            }
            
            ProcesoFilter procesoFilter = new ProcesoFilter();
            procesoFilter.setIdentificador(expedienteDto.getFlagOrigen().equals(Constantes.EXPEDIENTE_FLAG_ORIGEN_SIGED) ? (Constantes.PROCESO_OPERATIVO) : (Constantes.PROCESO_PREOPERATIVO));
            model.addAttribute("listadoProceso", serieServiceNeg.listarProceso(procesoFilter));
            model.addAttribute("listadoTipoSupervision", obligacionTipoServiceNeg.listarObligacionTipoxSeleccionMuestral());
            model.addAttribute("listadoTipoAsignacion", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_ASIGNACION, Constantes.APLICACION_INPS));
            model.addAttribute("listadoTipoSupervisor", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_SUPERVISOR, Constantes.APLICACION_INPS));
            model.addAttribute("listadoEntidad", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_ENTIDAD, Constantes.APLICACION_SGLSS));
            
            Long idUnidadOrganica = (Long) request.getSession().getAttribute(Constantes.PERSONAL_UNIDAD_ORGANICA_DIVISION);
            List<ConfFiltroEmpSupDTO> listaConfFiltros = new ArrayList<ConfFiltroEmpSupDTO>();
            LocadorFilter filtro = new LocadorFilter();
            filtro.setEstado(Constantes.ESTADO_ACTIVO);
            filtro.setIdUnidadOrganica(idUnidadOrganica);
            if (idUnidadOrganica != null && !idUnidadOrganica.equals("")) {
                listaConfFiltros = locadorServiceNeg.listarConfFiltros(filtro);
            }

            String confFiltrosConcatenada = concatenaFiltros(listaConfFiltros);
            String nombreXfiltroConfigurado = concatenaFiltrosDescripcion(listaConfFiltros);

            model.addAttribute("confFiltros", confFiltrosConcatenada);
            model.addAttribute("nombreXfiltroConfigurado", nombreXfiltroConfigurado);
            
            model.addAttribute("r", expedienteDto);
        
        return ConstantesWeb.Navegacion.PAGE_INPS_ORDEN_SERVICIO_ASIGNAR;
    }
    
    @RequestMapping(value = "/abrirOrdenServicioAtender", method = RequestMethod.POST)
    public String abrirOrdenServicioAtender(String rolSesion, ExpedienteDTO expedienteDto, HttpSession sesion, Model model, HttpServletRequest request) {
        LOG.info("ordenServicioAtender");
        String tipo=Constantes.ORDEN_SERVICIO_ATENDER;
        Long idProceso=new Long(0);
        Long idActividad=new Long(0);
        Long idObligacionTipo=new Long(0);
        model.addAttribute("tipo", tipo);
        model.addAttribute("listadoFlujoSiged", flujoSigedServiceNeg.listarFlujoSiged(new FlujoSigedFilter()));
//        if (expedienteDto.getEmpresaSupervisada() != null && expedienteDto.getEmpresaSupervisada().getIdEmpresaSupervisada() != null) {
//            UnidadSupervisadaFilter unidadSupervisadaFilter = new UnidadSupervisadaFilter();
//            unidadSupervisadaFilter.setIdEmpresaSupervisada(expedienteDto.getEmpresaSupervisada().getIdEmpresaSupervisada());
//            model.addAttribute("listadoUnidadSupervisada", unidadSupervisadaServiceNeg.listarUnidadSupervisada(unidadSupervisadaFilter));
//            if (expedienteDto.getUnidadSupervisada() != null && !Constantes.CONSTANTE_VACIA.equals(expedienteDto.getUnidadSupervisada().getCodigoOsinergmin())) {
//                unidadSupervisadaFilter = new UnidadSupervisadaFilter();
//                unidadSupervisadaFilter.setCodigoOsinerg(expedienteDto.getUnidadSupervisada().getCodigoOsinergmin());
//                idActividad = unidadSupervisadaServiceNeg.cargaDataUnidadOperativa(unidadSupervisadaFilter).getIdActividad();
//            }
//        }
        if (expedienteDto.getUnidadSupervisada() != null && !Constantes.CONSTANTE_VACIA.equals(expedienteDto.getUnidadSupervisada().getCodigoOsinergmin())) {
            UnidadSupervisadaFilter unidadSupervisadaFilter = new UnidadSupervisadaFilter();
            unidadSupervisadaFilter.setCodigoOsinerg(expedienteDto.getUnidadSupervisada().getCodigoOsinergmin());
            idActividad = unidadSupervisadaServiceNeg.cargaDataUnidadOperativa(unidadSupervisadaFilter).getIdActividad();
        }
        if (expedienteDto.getProceso() != null && expedienteDto.getProceso().getIdProceso() != null) {
            idProceso = expedienteDto.getProceso().getIdProceso();
        }
        if (expedienteDto.getObligacionTipo()!=null && expedienteDto.getObligacionTipo().getIdObligacionTipo()!=null) {
            idObligacionTipo=expedienteDto.getObligacionTipo().getIdObligacionTipo();
        }    
        
        ProcesoFilter procesoFilter = new ProcesoFilter();
        procesoFilter.setIdentificador(expedienteDto.getFlagOrigen().equals(Constantes.EXPEDIENTE_FLAG_ORIGEN_SIGED) ? (Constantes.PROCESO_OPERATIVO) : (Constantes.PROCESO_PREOPERATIVO));
        model.addAttribute("listadoProceso", serieServiceNeg.listarProceso(procesoFilter));
        model.addAttribute("listadoTipoSupervision", obligacionTipoServiceNeg.listarObligacionTipoxSeleccionMuestral());
        model.addAttribute("listadoTipoAsignacion", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_ASIGNACION, Constantes.APLICACION_INPS));

        Integer flagSupervision = 0;
        
        if (rolSesion.toString().equals(Constantes.CONSTANTE_ROL_SUPERVISOR)) {            
            flagSupervision=supervisionServiceNeg.buscarFlag(idObligacionTipo, idActividad, idProceso);               
        }
        Long idOrden = expedienteDto.getOrdenServicio().getIdOrdenServicio();
        System.out.println("idorden servicioes : |"+idOrden+"|");
        model.addAttribute("flagSupervision", flagSupervision);
        
        LocadorDTO AsignadoLocador = null;
        SupervisoraEmpresaDTO AsignadoSupeEmp = null;
        if (expedienteDto.getOrdenServicio().getLocador().getIdLocador() != null) {
            AsignadoLocador = locadorServiceNeg.getById(expedienteDto.getOrdenServicio().getLocador().getIdLocador());
            expedienteDto.getOrdenServicio().getLocador().setNombreCompleto(AsignadoLocador.getNombreCompleto());
        } else if (expedienteDto.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa() != null) {
            AsignadoSupeEmp = supervisoraEmpresaServiceNeg.getById(expedienteDto.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa());
            expedienteDto.getOrdenServicio().getSupervisoraEmpresa().setRazonSocial(AsignadoSupeEmp.getRazonSocial());
        }

        model.addAttribute("r", expedienteDto);
        return ConstantesWeb.Navegacion.PAGE_INPS_ORDEN_SERVICIO_ATENDER;
    }

    @RequestMapping(value = "/abrirOrdenServicioConsultar", method = RequestMethod.POST)
    public String abrirOrdenServicioConsultar(String rolSesion, ExpedienteDTO expedienteDto, HttpSession sesion, Model model, HttpServletRequest request) {
        LOG.info("abrirOrdenServicioConsultar");
        String tipo = Constantes.ORDEN_SERVICIO_CONSULTAR;
        model.addAttribute("tipo", tipo);
        model.addAttribute("comentario",expedienteDto.getComentarios() == null ? " " : expedienteDto.getComentarios());
        model.addAttribute("listadoFlujoSiged",flujoSigedServiceNeg.listarFlujoSiged(new FlujoSigedFilter()));

        ProcesoFilter procesoFilter = new ProcesoFilter();
        procesoFilter.setIdentificador(expedienteDto.getFlagOrigen().equals(Constantes.EXPEDIENTE_FLAG_ORIGEN_SIGED) ? (Constantes.PROCESO_OPERATIVO) : (Constantes.PROCESO_PREOPERATIVO));
        model.addAttribute("listadoProceso", serieServiceNeg.listarProceso(procesoFilter));
        model.addAttribute("listadoTipoSupervision", obligacionTipoServiceNeg.listarObligacionTipoxSeleccionMuestral());
        model.addAttribute("listadoTipoAsignacion", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_ASIGNACION, Constantes.APLICACION_INPS));
        model.addAttribute("listadoTipoSupervisor", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_SUPERVISOR, Constantes.APLICACION_INPS));
        
        Integer flagSupervision=0;
        if(expedienteDto.getOrdenServicio()!=null && expedienteDto.getOrdenServicio().getIdOrdenServicio() !=null){
            flagSupervision=obtieneFlagSupervision(expedienteDto.getOrdenServicio().getIdOrdenServicio());
        }
        model.addAttribute("flagSupervision", flagSupervision);
        
        //se envia datos de Empresa Supervisora
        LocadorDTO AsignadoLocador = null;
        SupervisoraEmpresaDTO AsignadoSupeEmp = null;
        if (expedienteDto.getOrdenServicio().getLocador().getIdLocador() != null) {
            AsignadoLocador = locadorServiceNeg.getById(expedienteDto.getOrdenServicio().getLocador().getIdLocador());
            expedienteDto.getOrdenServicio().getLocador().setNombreCompleto(AsignadoLocador.getNombreCompleto());
        } else if (expedienteDto.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa() != null) {
            AsignadoSupeEmp = supervisoraEmpresaServiceNeg.getById(expedienteDto.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa());
            expedienteDto.getOrdenServicio().getSupervisoraEmpresa().setRazonSocial(AsignadoSupeEmp.getRazonSocial());
        }

        model.addAttribute("r", expedienteDto);
        return ConstantesWeb.Navegacion.PAGE_INPS_ORDEN_SERVICIO_CONSULTAR;
    }

    @RequestMapping(value = "/abrirOrdenServicioRevisar", method = RequestMethod.POST)
    public String abrirOrdenServicioRevisar(String rolSesion, ExpedienteDTO expedienteDto, HttpSession sesion, Model model, HttpServletRequest request) {
        String tipo = Constantes.ORDEN_SERVICIO_REVISAR;
        LOG.info("abrirOrdenServicioRevisar");
        model.addAttribute("tipo", tipo);
        
        
        model.addAttribute("listadoFlujoSiged",flujoSigedServiceNeg.listarFlujoSiged(new FlujoSigedFilter()));
        
        ProcesoFilter procesoFilter = new ProcesoFilter();
        procesoFilter.setIdentificador(expedienteDto.getFlagOrigen().equals(Constantes.EXPEDIENTE_FLAG_ORIGEN_SIGED) ? (Constantes.PROCESO_OPERATIVO) : (Constantes.PROCESO_PREOPERATIVO));
        model.addAttribute("listadoProceso", serieServiceNeg.listarProceso(procesoFilter));
        model.addAttribute("listadoTipoSupervision", obligacionTipoServiceNeg.listarObligacionTipoxSeleccionMuestral());
        model.addAttribute("listadoTipoAsignacion", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_ASIGNACION, Constantes.APLICACION_INPS));
        model.addAttribute("listadoTipoSupervisor", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_SUPERVISOR, Constantes.APLICACION_INPS));

        String destinatario = "0";
        if (expedienteDto.getOrdenServicio() != null && expedienteDto.getOrdenServicio().getIdOrdenServicio() != null) {
            destinatario = obtieneFlagDestinatario(expedienteDto.getOrdenServicio().getIdOrdenServicio(), Constantes.getIDPERSONAL(request).longValue());
        }
        model.addAttribute("destinatario", destinatario);
        
        Integer flagSupervision=0;
        if(expedienteDto.getOrdenServicio()!=null && expedienteDto.getOrdenServicio().getIdOrdenServicio() !=null){
            flagSupervision=obtieneFlagSupervision(expedienteDto.getOrdenServicio().getIdOrdenServicio());
        }
        model.addAttribute("flagSupervision", flagSupervision);
        
        LocadorDTO AsignadoLocador=null;
        SupervisoraEmpresaDTO AsignadoSupeEmp=null;
        if(expedienteDto.getOrdenServicio().getLocador().getIdLocador()!=null){
            AsignadoLocador=locadorServiceNeg.getById(expedienteDto.getOrdenServicio().getLocador().getIdLocador());
            expedienteDto.getOrdenServicio().getLocador().setNombreCompleto(AsignadoLocador.getNombreCompleto());                
        }else if(expedienteDto.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa()!=null){
            AsignadoSupeEmp=supervisoraEmpresaServiceNeg.getById(expedienteDto.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa());
            expedienteDto.getOrdenServicio().getSupervisoraEmpresa().setRazonSocial(AsignadoSupeEmp.getRazonSocial());                
        }
        model.addAttribute("r", expedienteDto);

        return ConstantesWeb.Navegacion.PAGE_INPS_ORDEN_SERVICIO_REVISAR;
    }

    @RequestMapping(value = "/abrirOrdenServicioAprobar", method = RequestMethod.POST)
    public String abrirOrdenServicioAprobar(String rolSesion, ExpedienteDTO expedienteDto, HttpSession sesion, Model model, HttpServletRequest request) {
        String tipo = Constantes.ORDEN_SERVICIO_APROBAR;
        LOG.info("abrirOrdenServicioAprobar");
        model.addAttribute("tipo", tipo);
        
        model.addAttribute("listadoFlujoSiged",flujoSigedServiceNeg.listarFlujoSiged(new FlujoSigedFilter()));
        
        ProcesoFilter procesoFilter = new ProcesoFilter();
        procesoFilter.setIdentificador(expedienteDto.getFlagOrigen().equals(Constantes.EXPEDIENTE_FLAG_ORIGEN_SIGED) ? (Constantes.PROCESO_OPERATIVO) : (Constantes.PROCESO_PREOPERATIVO));
        model.addAttribute("listadoProceso", serieServiceNeg.listarProceso(procesoFilter));
        model.addAttribute("listadoTipoSupervision", obligacionTipoServiceNeg.listarObligacionTipoxSeleccionMuestral());
        model.addAttribute("listadoTipoAsignacion", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_ASIGNACION, Constantes.APLICACION_INPS));
        model.addAttribute("listadoTipoSupervisor", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_SUPERVISOR, Constantes.APLICACION_INPS));

        String destinatario = "0";
        if (expedienteDto.getOrdenServicio() != null && expedienteDto.getOrdenServicio().getIdOrdenServicio() != null) {
            destinatario = obtieneFlagDestinatario(expedienteDto.getOrdenServicio().getIdOrdenServicio(), Constantes.getIDPERSONAL(request).longValue());
        }
        model.addAttribute("destinatario", destinatario);
        
        Integer flagSupervision=0;
        if(expedienteDto.getOrdenServicio()!=null && expedienteDto.getOrdenServicio().getIdOrdenServicio() !=null){
            flagSupervision=obtieneFlagSupervision(expedienteDto.getOrdenServicio().getIdOrdenServicio());
        }
        model.addAttribute("flagSupervision", flagSupervision);
        
        LocadorDTO AsignadoLocador=null;
        SupervisoraEmpresaDTO AsignadoSupeEmp=null;
        if(expedienteDto.getOrdenServicio().getLocador().getIdLocador()!=null){
            AsignadoLocador=locadorServiceNeg.getById(expedienteDto.getOrdenServicio().getLocador().getIdLocador());
            expedienteDto.getOrdenServicio().getLocador().setNombreCompleto(AsignadoLocador.getNombreCompleto());                
        }else if(expedienteDto.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa()!=null){
            AsignadoSupeEmp=supervisoraEmpresaServiceNeg.getById(expedienteDto.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa());
            expedienteDto.getOrdenServicio().getSupervisoraEmpresa().setRazonSocial(AsignadoSupeEmp.getRazonSocial());                
        }
        model.addAttribute("r", expedienteDto);

        return ConstantesWeb.Navegacion.PAGE_INPS_ORDEN_SERVICIO_APROBAR;
    }

    @RequestMapping(value = "/abrirOrdenServicioNotificar", method = RequestMethod.POST)
    public String abrirOrdenServicioNotificar(String rolSesion, ExpedienteDTO expedienteDto, HttpSession sesion, Model model, HttpServletRequest request) {
        String tipo = Constantes.ORDEN_SERVICIO_NOTIFICAR;
        LOG.info("abrirOrdenServicioNotificar");
        model.addAttribute("tipo", tipo);
        
        model.addAttribute("listadoFlujoSiged",flujoSigedServiceNeg.listarFlujoSiged(new FlujoSigedFilter()));
        
        ProcesoFilter procesoFilter = new ProcesoFilter();
        procesoFilter.setIdentificador(expedienteDto.getFlagOrigen().equals(Constantes.EXPEDIENTE_FLAG_ORIGEN_SIGED) ? (Constantes.PROCESO_OPERATIVO) : (Constantes.PROCESO_PREOPERATIVO));
        model.addAttribute("listadoProceso", serieServiceNeg.listarProceso(procesoFilter));
        model.addAttribute("listadoTipoSupervision", obligacionTipoServiceNeg.listarObligacionTipoxSeleccionMuestral());
        model.addAttribute("listadoTipoAsignacion", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_ASIGNACION, Constantes.APLICACION_INPS));
        model.addAttribute("listadoTipoSupervisor", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_SUPERVISOR, Constantes.APLICACION_INPS));

        String destinatario = "0";
        if (expedienteDto.getOrdenServicio() != null && expedienteDto.getOrdenServicio().getIdOrdenServicio() != null) {
            destinatario = obtieneFlagDestinatario(expedienteDto.getOrdenServicio().getIdOrdenServicio(), Constantes.getIDPERSONAL(request).longValue());
        }
        model.addAttribute("destinatario", destinatario);
        
        Integer flagSupervision=0;
        if(expedienteDto.getOrdenServicio()!=null && expedienteDto.getOrdenServicio().getIdOrdenServicio() !=null){
            flagSupervision=obtieneFlagSupervision(expedienteDto.getOrdenServicio().getIdOrdenServicio());
        }
        model.addAttribute("flagSupervision", flagSupervision);
        
        LocadorDTO AsignadoLocador=null;
        SupervisoraEmpresaDTO AsignadoSupeEmp=null;
        if(expedienteDto.getOrdenServicio().getLocador().getIdLocador()!=null){
            AsignadoLocador=locadorServiceNeg.getById(expedienteDto.getOrdenServicio().getLocador().getIdLocador());
            expedienteDto.getOrdenServicio().getLocador().setNombreCompleto(AsignadoLocador.getNombreCompleto());                
        }else if(expedienteDto.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa()!=null){
            AsignadoSupeEmp=supervisoraEmpresaServiceNeg.getById(expedienteDto.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa());
            expedienteDto.getOrdenServicio().getSupervisoraEmpresa().setRazonSocial(AsignadoSupeEmp.getRazonSocial());                
        }
        model.addAttribute("r", expedienteDto);

        return ConstantesWeb.Navegacion.PAGE_INPS_ORDEN_SERVICIO_NOTIFICAR;
    }

    @RequestMapping(value = "/abrirOrdenServicioConcluir", method = RequestMethod.POST)
    public String abrirOrdenServicioConcluir(String rolSesion, ExpedienteDTO expedienteDto, HttpSession sesion, Model model, HttpServletRequest request) {
        String tipo = Constantes.ORDEN_SERVICIO_CONCLUIR;
        LOG.info("abrirOrdenServicioConcluir");
        model.addAttribute("tipo", tipo);
        
        model.addAttribute("listadoFlujoSiged",flujoSigedServiceNeg.listarFlujoSiged(new FlujoSigedFilter()));
        
        ProcesoFilter procesoFilter = new ProcesoFilter();
        procesoFilter.setIdentificador(expedienteDto.getFlagOrigen().equals(Constantes.EXPEDIENTE_FLAG_ORIGEN_SIGED) ? (Constantes.PROCESO_OPERATIVO) : (Constantes.PROCESO_PREOPERATIVO));
        model.addAttribute("listadoProceso", serieServiceNeg.listarProceso(procesoFilter));
        model.addAttribute("listadoTipoSupervision", obligacionTipoServiceNeg.listarObligacionTipoxSeleccionMuestral());
        model.addAttribute("listadoTipoAsignacion", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_ASIGNACION, Constantes.APLICACION_INPS));
        model.addAttribute("listadoTipoSupervisor", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_SUPERVISOR, Constantes.APLICACION_INPS));

        String destinatario = "0";
        if (expedienteDto.getOrdenServicio() != null && expedienteDto.getOrdenServicio().getIdOrdenServicio() != null) {
            destinatario = obtieneFlagDestinatario(expedienteDto.getOrdenServicio().getIdOrdenServicio(), Constantes.getIDPERSONAL(request).longValue());
        }
        model.addAttribute("destinatario", destinatario);
        
        Integer flagSupervision=0;
        if(expedienteDto.getOrdenServicio()!=null && expedienteDto.getOrdenServicio().getIdOrdenServicio() !=null){
            flagSupervision=obtieneFlagSupervision(expedienteDto.getOrdenServicio().getIdOrdenServicio());
        }
        model.addAttribute("flagSupervision", flagSupervision);
        
        LocadorDTO AsignadoLocador=null;
        SupervisoraEmpresaDTO AsignadoSupeEmp=null;
        if(expedienteDto.getOrdenServicio().getLocador().getIdLocador()!=null){
            AsignadoLocador=locadorServiceNeg.getById(expedienteDto.getOrdenServicio().getLocador().getIdLocador());
            expedienteDto.getOrdenServicio().getLocador().setNombreCompleto(AsignadoLocador.getNombreCompleto());                
        }else if(expedienteDto.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa()!=null){
            AsignadoSupeEmp=supervisoraEmpresaServiceNeg.getById(expedienteDto.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa());
            expedienteDto.getOrdenServicio().getSupervisoraEmpresa().setRazonSocial(AsignadoSupeEmp.getRazonSocial());                
        }


        model.addAttribute("r", expedienteDto);
        return ConstantesWeb.Navegacion.PAGE_INPS_ORDEN_SERVICIO_CONCLUIR;
    }
    
    @RequestMapping(value = "/abrirOrdenServicioConfirmarDescargo", method = RequestMethod.POST)
    public String abrirOrdenServicioConfirmarDescargo(String rolSesion, ExpedienteDTO expedienteDto, HttpSession sesion, Model model, HttpServletRequest request) {
        String tipo = Constantes.ORDEN_SERVICIO_CONFIRMAR_DESCARGO;
        LOG.info("abrirOrdenServicioConfirmarDescargo");
        model.addAttribute("tipo", tipo);
        
        model.addAttribute("listadoFlujoSiged",flujoSigedServiceNeg.listarFlujoSiged(new FlujoSigedFilter()));
        
        ProcesoFilter procesoFilter = new ProcesoFilter();
        procesoFilter.setIdentificador(expedienteDto.getFlagOrigen().equals(Constantes.EXPEDIENTE_FLAG_ORIGEN_SIGED) ? (Constantes.PROCESO_OPERATIVO) : (Constantes.PROCESO_PREOPERATIVO));
        model.addAttribute("listadoProceso", serieServiceNeg.listarProceso(procesoFilter));
        model.addAttribute("listadoTipoSupervision", obligacionTipoServiceNeg.listarObligacionTipoxSeleccionMuestral());
        model.addAttribute("listadoTipoAsignacion", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_ASIGNACION, Constantes.APLICACION_INPS));
        model.addAttribute("listadoTipoSupervisor", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_SUPERVISOR, Constantes.APLICACION_INPS));

        String destinatario = "0";
        if (expedienteDto.getOrdenServicio() != null && expedienteDto.getOrdenServicio().getIdOrdenServicio() != null) {
            destinatario = obtieneFlagDestinatario(expedienteDto.getOrdenServicio().getIdOrdenServicio(), Constantes.getIDPERSONAL(request).longValue());
        }
        model.addAttribute("destinatario", destinatario);
        
        Integer flagSupervision=0;
        if(expedienteDto.getOrdenServicio()!=null && expedienteDto.getOrdenServicio().getIdOrdenServicio() !=null){
            flagSupervision=obtieneFlagSupervision(expedienteDto.getOrdenServicio().getIdOrdenServicio());
        }
        model.addAttribute("flagSupervision", flagSupervision);
        
        LocadorDTO AsignadoLocador=null;
        SupervisoraEmpresaDTO AsignadoSupeEmp=null;
        if(expedienteDto.getOrdenServicio().getLocador().getIdLocador()!=null){
            AsignadoLocador=locadorServiceNeg.getById(expedienteDto.getOrdenServicio().getLocador().getIdLocador());
            expedienteDto.getOrdenServicio().getLocador().setNombreCompleto(AsignadoLocador.getNombreCompleto());                
        }else if(expedienteDto.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa()!=null){
            AsignadoSupeEmp=supervisoraEmpresaServiceNeg.getById(expedienteDto.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa());
            expedienteDto.getOrdenServicio().getSupervisoraEmpresa().setRazonSocial(AsignadoSupeEmp.getRazonSocial());                
        }


        model.addAttribute("r", expedienteDto);
        return ConstantesWeb.Navegacion.PAGE_INPS_ORDEN_SERVICIO_CONFIRMAR_DESCARGO;
    }

    public String obtieneFlagDestinatario(Long idOrdenServicio, Long idPersonal) {
        String flagDestinatario = "0";
        HistoricoEstadoFilter filtro = new HistoricoEstadoFilter();
        filtro.setIdOrdenServicio(idOrdenServicio);
        List<HistoricoEstadoDTO> listado = historicoEstadoServiceNeg.listarHistoricoEstado(filtro);

        Collections.sort(listado, new Comparator<HistoricoEstadoDTO>() {
            @Override
            public int compare(HistoricoEstadoDTO hist1, HistoricoEstadoDTO hist2) {
                return hist1.getFechaCreacion().compareTo(hist2.getFechaCreacion());
            }
        });
        Collections.reverse(listado);

        if (listado.get(0).getPersonalDest().getIdPersonal().longValue() == idPersonal) {
            flagDestinatario = "1";
        }
        return flagDestinatario;
    }

    public Integer obtieneFlagSupervision(Long idOrdenServicio) {
        Integer flagSupervision = 0;
        try{
            SupervisionFilter supervisionFiltro = new SupervisionFilter();
            supervisionFiltro.setIdOrdenServicio(idOrdenServicio);
            List<SupervisionDTO> listaSupervision = supervisionServiceNeg.buscarSupervision(supervisionFiltro);
            if (!listaSupervision.isEmpty()) {
                flagSupervision = Constantes.FLAG_REALIZA_SUPERVISION;
            }
        }catch(Exception e){
            LOG.error(e.getMessage(),e);
        }
        return flagSupervision;
    }
    /*<!--  OSINE791 - RSIS8 - Inicio -->*/
    @RequestMapping(value = "/abrirRegistroMedioProbatorios", method = RequestMethod.GET)
    public String abrirRegistroMedioProbatorios(DetalleSupervisionDTO detSupeDTO, HttpSession sesion, Model model) {
        LOG.info("abrirRegistroMedioProbatorios");
        String archivosPermitidos = "";
        List<MaestroColumnaDTO> lista = maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_ADJ_SUPERVISION, Constantes.APLICACION_INPS);
        if (!lista.isEmpty()) {
            for (MaestroColumnaDTO registro : lista) {
                archivosPermitidos += registro.getCodigo() + ", ";
            }
            if (!Constantes.CONSTANTE_VACIA.equals(archivosPermitidos.trim())) {
                archivosPermitidos = archivosPermitidos.substring(0, (archivosPermitidos.length() - 2));
            }
        }
        model.addAttribute("ds", detSupeDTO);
        model.addAttribute("archivosPermitidos", archivosPermitidos.toUpperCase());
        model.addAttribute("tamanioAdjuntoMB", TAMANIO_MAXIMO_ARCHIVO_MEGAS);
        return ConstantesWeb.Navegacion.PAGE_INPS_SUPERVISION_REG_MEDIO_PROBATORIO;
    }
    /*<!--  OSINE791 - RSIS8 - Fin -->*/
    
    /*OSINE_SFS-791 - RSIS 03 - Inicio */
    @RequestMapping(value = "/abrirAdjuntarArchivosSupervision", method = RequestMethod.GET)
    public String abrirAdjuntarArchivosSupervision(String tipo, SupervisionDTO supervisionDTO, HttpSession sesion, Model model) {
        LOG.info("abrirAdjuntarArchivosSupervision");
        String archivosPermitidos = "";
        List<MaestroColumnaDTO> lista = maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_ADJ_SUPERVISION, Constantes.APLICACION_INPS);
        if (!lista.isEmpty()) {
            for (MaestroColumnaDTO registro : lista) {
                archivosPermitidos += registro.getCodigo() + ", ";
            }
            if (!Constantes.CONSTANTE_VACIA.equals(archivosPermitidos.trim())) {
                archivosPermitidos = archivosPermitidos.substring(0, (archivosPermitidos.length() - 2));
            }
        }
        model.addAttribute("s", supervisionDTO);
        model.addAttribute("modoDA", tipo);
        model.addAttribute("archivosPermitidos", archivosPermitidos.toUpperCase());
        model.addAttribute("tamanioAdjuntoMB", TAMANIO_MAXIMO_ARCHIVO_MEGAS);
        return ConstantesWeb.Navegacion.PAGE_INPS_SUPERVISION_ADJ_SUPERVISION;
    }
    /*OSINE_SFS-791 - RSIS 03 - Inicio */
    
    /* OSINE_SFS-791 - RSIS 33 - Inicio */
    @RequestMapping(value = "/abrirOrdenServicioLevantamiento", method = RequestMethod.POST)
    public String abrirOrdenServicioLevantamiento(String rolSesion, ExpedienteDTO expedienteDto, HttpSession sesion, Model model, HttpServletRequest request) {
        LOG.info("abrirOrdenServicioLevantamiento");
        String tipo=Constantes.ORDEN_SERVICIO_LEVANTAMIENTO;
        Long idProceso=new Long(0);
        Long idActividad=new Long(0);
        Long idObligacionTipo=new Long(0);
        model.addAttribute("tipo", tipo);
        model.addAttribute("listadoFlujoSiged", flujoSigedServiceNeg.listarFlujoSiged(new FlujoSigedFilter()));
        if (expedienteDto.getEmpresaSupervisada() != null && expedienteDto.getEmpresaSupervisada().getIdEmpresaSupervisada() != null) {
            UnidadSupervisadaFilter unidadSupervisadaFilter = new UnidadSupervisadaFilter();
            unidadSupervisadaFilter.setIdEmpresaSupervisada(expedienteDto.getEmpresaSupervisada().getIdEmpresaSupervisada());
            model.addAttribute("listadoUnidadSupervisada", unidadSupervisadaServiceNeg.listarUnidadSupervisada(unidadSupervisadaFilter));
            if (expedienteDto.getUnidadSupervisada() != null && !Constantes.CONSTANTE_VACIA.equals(expedienteDto.getUnidadSupervisada().getCodigoOsinergmin())) {
                unidadSupervisadaFilter = new UnidadSupervisadaFilter();
                unidadSupervisadaFilter.setCodigoOsinerg(expedienteDto.getUnidadSupervisada().getCodigoOsinergmin());
                idActividad = unidadSupervisadaServiceNeg.cargaDataUnidadOperativa(unidadSupervisadaFilter).getIdActividad();
            }
        }
        if (expedienteDto.getProceso() != null && expedienteDto.getProceso().getIdProceso() != null) {
            idProceso = expedienteDto.getProceso().getIdProceso();
        }
        if (expedienteDto.getObligacionTipo()!=null && expedienteDto.getObligacionTipo().getIdObligacionTipo()!=null) {
            idObligacionTipo=expedienteDto.getObligacionTipo().getIdObligacionTipo();
        }    
        
        ProcesoFilter procesoFilter = new ProcesoFilter();
        procesoFilter.setIdentificador(expedienteDto.getFlagOrigen().equals(Constantes.EXPEDIENTE_FLAG_ORIGEN_SIGED) ? (Constantes.PROCESO_OPERATIVO) : (Constantes.PROCESO_PREOPERATIVO));
        model.addAttribute("listadoProceso", serieServiceNeg.listarProceso(procesoFilter));
        model.addAttribute("listadoTipoSupervision", obligacionTipoServiceNeg.listarObligacionTipoxSeleccionMuestral());
        model.addAttribute("listadoTipoAsignacion", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_ASIGNACION, Constantes.APLICACION_INPS));
        model.addAttribute("listadoTipoSupervisor", maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_SUPERVISOR, Constantes.APLICACION_INPS));

        Integer flagSupervision = 0;        
        
        if (rolSesion.toString().equals(Constantes.CONSTANTE_ROL_SUPERVISOR)) {            
            flagSupervision=supervisionServiceNeg.buscarFlag(idObligacionTipo, idActividad, idProceso);           
        }
        
        Long idOrden = expedienteDto.getOrdenServicio().getIdOrdenServicio();
        LOG.info("idorden servicioes : |"+idOrden+"|");
        model.addAttribute("flagSupervision", flagSupervision);
       
        LocadorDTO AsignadoLocador = null;
        SupervisoraEmpresaDTO AsignadoSupeEmp = null;
        if (expedienteDto.getOrdenServicio().getLocador().getIdLocador() != null) {
            AsignadoLocador = locadorServiceNeg.getById(expedienteDto.getOrdenServicio().getLocador().getIdLocador());
            expedienteDto.getOrdenServicio().getLocador().setNombreCompleto(AsignadoLocador.getNombreCompleto());
        } else if (expedienteDto.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa() != null) {
            AsignadoSupeEmp = supervisoraEmpresaServiceNeg.getById(expedienteDto.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa());
            expedienteDto.getOrdenServicio().getSupervisoraEmpresa().setRazonSocial(AsignadoSupeEmp.getRazonSocial());
        }

        model.addAttribute("r", expedienteDto);
        return ConstantesWeb.Navegacion.PAGE_INPS_ORDEN_SERVICIO_LEVANTAMIENTO;
    }
    /* OSINE_SFS-791 - RSIS 33 - Fin */
}
