/**
* Resumen		
* Objeto			: OrdenServicioGSMController.java
* Descripción		: Controla el flujo de datos del objeto OrdenServicio, gerencia GSM.
* Fecha de Creación	: 25/10/2016.
* PR de Creación	: OSINE_SFS-480
* Autor				: Giancarlo Villanueva.
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
*  
*/ 

package gob.osinergmin.inpsweb.gsm.controller;

import gob.osinergmin.inpsweb.gsm.service.business.OrdenServicioGSMServiceNeg;
import gob.osinergmin.inpsweb.gsm.util.ConstantesGSM;
import gob.osinergmin.inpsweb.service.business.DocumentoAdjuntoServiceNeg;
import gob.osinergmin.inpsweb.service.business.ExpedienteServiceNeg;
import gob.osinergmin.inpsweb.service.business.FlujoSigedServiceNeg;
import gob.osinergmin.inpsweb.service.business.HistoricoEstadoServiceNeg;
import gob.osinergmin.inpsweb.service.business.LocadorServiceNeg;
import gob.osinergmin.inpsweb.service.business.MaestroColumnaServiceNeg;
import gob.osinergmin.inpsweb.service.business.ObligacionTipoServiceNeg;
import gob.osinergmin.inpsweb.service.business.OrdenServicioServiceNeg;
import gob.osinergmin.inpsweb.service.business.SerieServiceNeg;
import gob.osinergmin.inpsweb.service.business.SupervisoraEmpresaServiceNeg;
import gob.osinergmin.inpsweb.service.business.UnidadSupervisadaServiceNeg;
// htorress - RSIS 18 - Inicio
import gob.osinergmin.inpsweb.service.business.PersonalAsignadoServiceNeg;
import gob.osinergmin.mdicommon.domain.dto.PersonalAsignadoDTO;
import gob.osinergmin.inpsweb.service.business.DetalleLevantamientoServiceNeg;
// htorress - RSIS 18 - Fin
import gob.osinergmin.inpsweb.service.business.PersonalServiceNeg;
import gob.osinergmin.inpsweb.service.business.SupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.exception.OrdenServicioException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.ConstantesWeb;
import gob.osinergmin.inpsweb.util.StringUtil;
import gob.osinergmin.mdicommon.domain.dto.ConfFiltroEmpSupDTO;
import gob.osinergmin.mdicommon.domain.dto.DmTitularMineroDTO;
import gob.osinergmin.mdicommon.domain.dto.DmUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteGSMDTO;
import gob.osinergmin.mdicommon.domain.dto.HistoricoEstadoDTO;
import gob.osinergmin.mdicommon.domain.dto.LocadorDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.OrdenServicioDTO;
import gob.osinergmin.mdicommon.domain.dto.PersonalDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisoraEmpresaDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadOrganicaDTO;
import gob.osinergmin.mdicommon.domain.dto.busqueda.BusquedaUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.ui.DmTitularMineroFilter;
import gob.osinergmin.mdicommon.domain.ui.FlujoSigedFilter;
import gob.osinergmin.mdicommon.domain.ui.HistoricoEstadoFilter;
import gob.osinergmin.mdicommon.domain.ui.LocadorFilter;
import gob.osinergmin.mdicommon.domain.ui.OrdenServicioFilter;
import gob.osinergmin.mdicommon.domain.ui.PersonalFilter;
import gob.osinergmin.mdicommon.domain.ui.ProcesoFilter;
import gob.osinergmin.mdicommon.domain.ui.SupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.UnidadOrganicaFilter;
import gob.osinergmin.mdicommon.domain.ui.UnidadSupervisadaFilter;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ordenServicioGSM")
public class OrdenServicioGSMController {
    private static final Logger LOG = LoggerFactory.getLogger(OrdenServicioGSMController.class);
    @Inject
    OrdenServicioServiceNeg ordenServicioServiceNeg;
    @Inject
    HistoricoEstadoServiceNeg historicoEstadoServiceNeg;
    @Inject
    private SupervisionServiceNeg supervisionServiceNeg;
    @Inject
    private DocumentoAdjuntoServiceNeg documentoAdjuntoServiceNeg;
    @Inject
    private ExpedienteServiceNeg expedienteServiceNeg;
    @Inject
    private PersonalServiceNeg personalServiceNeg;
    @Inject
    private PersonalAsignadoServiceNeg personalAsignadoServiceNeg;
    @Inject
    private DetalleLevantamientoServiceNeg detalleLevantamientoServiceNeg;
    
    /* OSINE_SFS-1344 - Inicio */  
    @Inject
    private FlujoSigedServiceNeg flujoSigedServiceNeg;
    @Inject
    private UnidadSupervisadaServiceNeg unidadSupervisadaServiceNeg;
    @Inject
    private SerieServiceNeg serieServiceNeg;
    @Inject
    private MaestroColumnaServiceNeg maestroColumnaServiceNeg;
    @Inject
    private ObligacionTipoServiceNeg obligacionTipoServiceNeg;
    @Inject
    private LocadorServiceNeg locadorServiceNeg;
    @Inject
    private SupervisoraEmpresaServiceNeg supervisoraEmpresaServiceNeg;
    
    @Inject
    private OrdenServicioGSMServiceNeg ordenServicioGSMServiceNeg;
    /* OSINE_SFS-1344 - Fin */  
    /* OSINE_SFS-1344 - Inicio */
    @RequestMapping(value = "/abrirOrdenServicioMasivo", method = RequestMethod.GET)
    public String abrirOrdenServicioMasivo(String tipo, String rolSesion, ExpedienteGSMDTO expedienteDto, String idPersonal, HttpSession sesion, Model model, HttpServletRequest request) {
        LOG.info("abrirOrdenServicio, tipo=" + tipo);
        try{
        	Long idUnidadOrganicaDivision = (Long)  request.getSession().getAttribute(Constantes.PERSONAL_UNIDAD_ORGANICA_DIVISION);
        	UnidadOrganicaFilter unidadOrganicaFilter = new UnidadOrganicaFilter();
        	unidadOrganicaFilter.setIdUnidadOrganica(idUnidadOrganicaDivision);
        	
            model.addAttribute("tipo", tipo);
            model.addAttribute("r", expedienteDto);
            model.addAttribute("listadoFlujoSiged", flujoSigedServiceNeg.listarFlujoSiged(new FlujoSigedFilter()));

            ProcesoFilter procesoFilter = new ProcesoFilter();
            procesoFilter.setIdentificador(expedienteDto.getFlagOrigen().equals(Constantes.EXPEDIENTE_FLAG_ORIGEN_SIGED) ? (Constantes.PROCESO_OPERATIVO) : (Constantes.PROCESO_PREOPERATIVO));
            model.addAttribute("listadoProceso", serieServiceNeg.listarProceso(procesoFilter));//GSM (Operativo, Especial)
            model.addAttribute("listadoTipoSupervision", obligacionTipoServiceNeg.listarObligacionTipoByDivision(unidadOrganicaFilter));//Listar Tipos de Supervisión por División
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
        return ConstantesWeb.Navegacion.PAGE_INPS_ORDEN_SERVICIO_MASIVO_GSM;
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
    
    @RequestMapping(value = "/abrirOrdenServicioAprobar", method = RequestMethod.POST)
    public String abrirOrdenServicioAprobar(String rolSesion, ExpedienteDTO expedienteDto, HttpSession sesion, Model model, HttpServletRequest request) {
        String tipo = Constantes.ORDEN_SERVICIO_APROBAR;
        LOG.info("abrirOrdenServicioAprobar");
        model.addAttribute("tipo", tipo);
        
        Long idProceso=new Long(0);
        Long idActividad=new Long(0);
        Long idObligacionTipo=new Long(0);
        if(expedienteDto.getEmpresaSupervisada()!=null && expedienteDto.getEmpresaSupervisada().getIdEmpresaSupervisada()!=null){
            UnidadSupervisadaFilter unidadSupervisadaFilter=new UnidadSupervisadaFilter();
            unidadSupervisadaFilter.setIdEmpresaSupervisada(expedienteDto.getEmpresaSupervisada().getIdEmpresaSupervisada());
            model.addAttribute("listadoUnidadSupervisada",unidadSupervisadaServiceNeg.listarUnidadSupervisada(unidadSupervisadaFilter));  
            if(expedienteDto.getUnidadSupervisada()!=null && !Constantes.CONSTANTE_VACIA.equals(expedienteDto.getUnidadSupervisada().getCodigoOsinergmin())){
            	unidadSupervisadaFilter=new UnidadSupervisadaFilter();
            	unidadSupervisadaFilter.setCodigoOsinerg(expedienteDto.getUnidadSupervisada().getCodigoOsinergmin());
            	idActividad=unidadSupervisadaServiceNeg.cargaDataUnidadOperativa(unidadSupervisadaFilter).getIdActividad();
            }
        }
        if (expedienteDto.getProceso()!=null && expedienteDto.getProceso().getIdProceso()!=null) {
            idProceso=expedienteDto.getProceso().getIdProceso();
        }
        if (expedienteDto.getObligacionTipo()!=null && expedienteDto.getObligacionTipo().getIdObligacionTipo()!=null) {
            idObligacionTipo=expedienteDto.getObligacionTipo().getIdObligacionTipo();
        }   
        
        model.addAttribute("listadoFlujoSiged",flujoSigedServiceNeg.listarFlujoSiged(new FlujoSigedFilter()));
        if(expedienteDto.getEmpresaSupervisada()!=null && expedienteDto.getEmpresaSupervisada().getIdEmpresaSupervisada()!=null){//en caso GENERAR no hay empresa
            UnidadSupervisadaFilter unidadSupervisadaFilter=new UnidadSupervisadaFilter();
            unidadSupervisadaFilter.setIdEmpresaSupervisada(expedienteDto.getEmpresaSupervisada().getIdEmpresaSupervisada());
            model.addAttribute("listadoUnidadSupervisada", unidadSupervisadaServiceNeg.listarUnidadSupervisada(unidadSupervisadaFilter));
        }

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

        return ConstantesWeb.Navegacion.PAGE_INPS_ORDEN_SERVICIO_APROBAR_GSM;
    }
    /* OSINE_SFS-1344 - Fin */ 
    /* OSINE_SFS-1344 - Inicio */ 
    @RequestMapping(value="/cargaUnidadSupervisadaTitularMinero",method= RequestMethod.POST)
    public @ResponseBody Map<String, Object> cargaUnidadSupervisadaTitularMinero(HttpSession sesion,DmTitularMineroFilter filtro,int rows, int page,HttpSession session,HttpServletRequest request){ 
    	Map<String, Object> retorno = new HashMap<String, Object>();
    	List<DmTitularMineroDTO>lista=new ArrayList<DmTitularMineroDTO>();
        try {
        	
	        lista = ordenServicioGSMServiceNeg.listarTitularMinero(filtro);
            
            if(lista!=null && lista.size()>0 && lista.get(0).getListaUnidadSupervisada()!=null && lista.get(0).getListaUnidadSupervisada().size()>0){
            	List<DmUnidadSupervisadaDTO> listaUnidad = lista.get(0).getListaUnidadSupervisada();
            	int indiceInicial=-1;
                int indiceFinal=-1;
            	Long contador = new Long(listaUnidad.size());
            	Long numeroFilas = (contador / rows);
	            if ((contador % rows) > 0) {numeroFilas = numeroFilas + 1L; }
	            if(numeroFilas<page){page = numeroFilas.intValue(); }
	            if(page == 0){rows = 0;}
            	indiceInicial = (page - 1) * rows;
	            indiceFinal = indiceInicial + rows;
	            List<DmUnidadSupervisadaDTO> listaUnidadesTitularMinero = new ArrayList<DmUnidadSupervisadaDTO>();
	            listaUnidadesTitularMinero = listaUnidad.subList(indiceInicial > listaUnidad.size() ? listaUnidad.size() : indiceInicial , indiceFinal > listaUnidad.size() ? listaUnidad.size(): indiceFinal);	        
	            retorno.put("total", numeroFilas);
	            retorno.put("pagina", page);
	            retorno.put("registros", contador);
	            retorno.put("filas", listaUnidadesTitularMinero);	            
                retorno.put("resultado",ConstantesWeb.VV_EXITO);

            }else{            	
                retorno.put("resultado",ConstantesWeb.VV_ERROR);
            }
	}catch(Exception e){
            LOG.error("Error cargaUnidadSupervisadaTitularMinero",e);
            retorno.put("resultado",ConstantesWeb.VV_ERROR);
        }
        return retorno;
    }
    /* OSINE_SFS-1344 - Fin */ 
    
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
    
    @RequestMapping(value="/atender",method= RequestMethod.GET)
    public @ResponseBody Map<String,Object> atenderOrdenServicio(ExpedienteDTO expedienteDTO,Long idPersonalOri,String flagSupervision,Long nroIteracion,HttpSession session,HttpServletRequest request){
        LOG.info("atender");
        Map<String,Object> retorno=new HashMap<String,Object>();
    	try{
            String codFlujoSup=supervisionServiceNeg.buscarCodigoFlujoSupervINPS(
                    expedienteDTO.getObligacionTipo().getIdObligacionTipo(), 
                    expedienteDTO.getUnidadSupervisada().getActividad().getIdActividad(), 
                    expedienteDTO.getProceso().getIdProceso());   
            if(!StringUtil.isEmpty(codFlujoSup) && codFlujoSup.equals(Constantes.CODIGO_FLUJO_SUPERV_INPS_DSR_CRI)){
                LOG.info("-->codFlujoSup "+Constantes.CODIGO_FLUJO_SUPERV_INPS_DSR_CRI);
                retorno=atenderDsr(expedienteDTO,idPersonalOri,flagSupervision,nroIteracion,request);                                
            }else{
                LOG.info("-->codFlujoSup Default");
                retorno=atenderDefault(expedienteDTO,idPersonalOri,flagSupervision,request);
            }
        }catch(Exception e){
            LOG.error("Error en atender",e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }
    
    public Map<String,Object> atenderDefault(ExpedienteDTO expedienteDTO,Long idPersonalOri,String flagSupervision,HttpServletRequest request){
        LOG.info("atenderDefault");
        Map<String,Object> retorno=new HashMap<String,Object>();
    	try{
            Long idExpediente=expedienteDTO.getIdExpediente();
            Long idOrdenServicio=expedienteDTO.getOrdenServicio().getIdOrdenServicio();
            //enviar archivos a Siged segun expediente
            // htorress - RSIS 8 - Inicio
            /*
            DocumentoAdjuntoFilter filtro=new DocumentoAdjuntoFilter();
            filtro.setIdOrdenServicio(idOrdenServicio);
            List<DocumentoAdjuntoDTO> listDocEnviarSiged=documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(filtro);
            boolean flgEnvioDocSiged=true;
            if(listDocEnviarSiged!=null && listDocEnviarSiged.size()>0){
                for(DocumentoAdjuntoDTO doc : listDocEnviarSiged){
                    DocumentoAdjuntoDTO docEnviado=documentoAdjuntoServiceNeg.enviarPghDocOrdenServicioSiged(doc,expedienteDTO,Constantes.getIDPERSONALSIGED(request));
                    //temporal - inicio por servicios Siged
//                    DocumentoAdjuntoDTO docEnviado=new DocumentoAdjuntoDTO();
                    //temporal - fin
                    if(docEnviado==null){flgEnvioDocSiged=false;break;}
                }
            }
            //
            if(flgEnvioDocSiged){
            */	
            // htorress - RSIS 8 - Fin
            // htorress - RSIS 18 - Inicio
            	PersonalDTO destinatario=(PersonalDTO)obtenerUsuarioOrigen(idOrdenServicio);
                
                ExpedienteDTO expedienteReenviarSiged=expedienteServiceNeg.reenviarExpedienteSIGED(expedienteDTO, destinatario.getIdPersonalSiged(), "Reenvio para Aprobar", true);
             if(expedienteReenviarSiged.getNumeroExpediente()!=null){     
             // htorress - RSIS 18 - Fin
            	OrdenServicioDTO respuesta=ordenServicioServiceNeg.atender(idExpediente,idOrdenServicio,idPersonalOri,flagSupervision,Constantes.getUsuarioDTO(request));
            	retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
                retorno.put(ConstantesWeb.VV_MENSAJE, "Cambios realizados");
                retorno.put("expediente",respuesta);
            // htorress - RSIS 8 - Inicio
            }else{
                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                retorno.put(ConstantesWeb.VV_MENSAJE, expedienteReenviarSiged.getMensajeServicio());
            }   
    		// htorress - RSIS 8 - Fin
        }catch(Exception e){
            LOG.error("Error en atenderDefault",e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }
    
    public Map<String,Object> atenderDsr(ExpedienteDTO expedienteDTO,Long idPersonalOri,String flagSupervision,Long nroIteracion,HttpServletRequest request){
        LOG.info("atenderDsr");
        Map<String,Object> retorno=new HashMap<String,Object>();
    	try{
            Long idOrdenServicio=expedienteDTO.getOrdenServicio().getIdOrdenServicio();
            
            PersonalDTO destinatario=(PersonalDTO)obtenerUsuarioOrigen(idOrdenServicio);   
            /* OSINE_SFS-791 - RSIS 42 - Inicio */ 
            if (destinatario!=null ){
            /* OSINE_SFS-791 - RSIS 42 - Fin */ 
                ExpedienteDTO expedienteReenviarSiged=expedienteServiceNeg.reenviarExpedienteSIGED(expedienteDTO, destinatario.getIdPersonalSiged(), "Reenvio para Aprobar", true);
                if(expedienteReenviarSiged.getNumeroExpediente()!=null){     
                	OrdenServicioDTO respuesta=ordenServicioServiceNeg.atenderDsr(expedienteDTO,idPersonalOri,flagSupervision,nroIteracion,Constantes.getUsuarioDTO(request));
                    retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
                    retorno.put(ConstantesWeb.VV_MENSAJE, "Cambios realizados");
                    retorno.put("expediente",respuesta);
                }else{
                    retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                    retorno.put(ConstantesWeb.VV_MENSAJE, expedienteReenviarSiged.getMensajeServicio());
                } 
            /* OSINE_SFS-791 - RSIS 42 - Inicio */ 
            }else{
                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_SIN_DESTINATARIO);
            } 
            /* OSINE_SFS-791 - RSIS 42 - Fin */ 	
    	}catch(Exception e){
            LOG.error("Error en atenderDsr",e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
        
    }
    
    @RequestMapping(value="/revisar",method= RequestMethod.GET)
    // htorress - RSIS 18 - Inicio
    //public @ResponseBody Map<String,Object> revisarOrdenServicio(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,HttpSession session,HttpServletRequest request){
    public @ResponseBody Map<String,Object> revisarOrdenServicio(Long idExpediente,Long idOrdenServicio,Long idPersonalOri, Long numeroExpediente, String asuntoSiged,HttpSession session,HttpServletRequest request){	
    // htorress - RSIS 18 - Fin
        LOG.info("revisar");
        Map<String,Object> retorno=new HashMap<String,Object>();
    	try{
            
            // htorress - RSIS 18 - Inicio
    		Long idJefe=0L;
    		Long idPersonalDest=0L;
    		if(Constantes.getIDENTIFICADORROL(request).equals(Constantes.CONSTANTE_ROL_ESPECIALISTA) ||
    		   Constantes.getIDENTIFICADORROL(request).equals(Constantes.CONSTANTE_ROL_SUPERVISOR_REGIONAL)){
    			
	    		PersonalAsignadoDTO personal = new PersonalAsignadoDTO();
	        	personal.setIdPersonalSubordinado(new PersonalDTO());
	        	personal.setIdPersonalJefe(new PersonalDTO());
	        	personal.getIdPersonalSubordinado().setIdPersonal(idPersonalOri);
	        	List<PersonalAsignadoDTO> listPersona=personalAsignadoServiceNeg.findPersonalAsignado(personal);
	        	if(listPersona != null && listPersona.size()>0){
	    		idJefe = listPersona.get(0).getIdPersonalJefe().getIdPersonalSiged();
	    		idPersonalDest= listPersona.get(0).getIdPersonalJefe().getIdPersonal();
	        	}else{
	        	       throw new OrdenServicioException("Se requiere configuracion previa: Usuario no tiene Jefe Asignado solicitar configuracion al servicio tecnico ", null); 
	        	}
    		}else if(Constantes.getIDENTIFICADORROL(request).equals(Constantes.CONSTANTE_ROL_JEFE_REGIONAL) ||
    				Constantes.getIDENTIFICADORROL(request).equals(Constantes.CONSTANTE_ROL_JEFE_UNIDAD)){
    			
    			idJefe=Constantes.getIDPERSONALSIGED(request);
    			idPersonalDest = Constantes.getIDPERSONAL(request);
    		}
            ExpedienteDTO expedienteDTO = new ExpedienteDTO();
            expedienteDTO.setNumeroExpediente(String.valueOf(numeroExpediente));
            expedienteDTO.setAsuntoSiged(asuntoSiged);

            ExpedienteDTO expedienteAprobar=expedienteServiceNeg.aprobarExpedienteSIGED(expedienteDTO, idJefe, "Aprobar", true);
            if(expedienteAprobar.getNumeroExpediente()!=null){
	            //ExpedienteDTO expedienteReenviarSiged=expedienteServiceNeg.reenviarExpedienteSIGED(expedienteDTO, idJefe, "Prueba de Contenido Reenvio Revisado Esp a Jefe.", true);
		        //if(expedienteReenviarSiged.getNumeroExpediente()!=null){     
		        	//OrdenServicioDTO respuesta=ordenServicioServiceNeg.revisar(idExpediente,idOrdenServicio,idPersonalOri,Constantes.getUsuarioDTO(request));
            		OrdenServicioDTO respuesta=ordenServicioServiceNeg.revisar(idExpediente,idOrdenServicio,idPersonalOri,idPersonalDest,Constantes.getUsuarioDTO(request));
		        // htorress - RSIS 18 - Fin
		            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
		            retorno.put(ConstantesWeb.VV_MENSAJE, "Cambios realizados");
		            retorno.put("expediente",respuesta);
		        // htorress - RSIS 18 - Inicio
		        /*}else{
		        	LOG.error("Error en revisar");
	                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
	                retorno.put(ConstantesWeb.VV_MENSAJE, expedienteReenviarSiged.getMensajeServicio());
		        }*/
            }else{
            	LOG.error("Error en aprobar para su revisión");
                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                retorno.put(ConstantesWeb.VV_MENSAJE, expedienteAprobar.getMensajeServicio());
            }
            // htorress - RSIS 18 - Fin
        }catch(Exception e){
            LOG.error("Error en derivar",e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }
    
    @RequestMapping(value="/aprobar",method= RequestMethod.GET)
    public @ResponseBody Map<String,Object> aprobarOrdenServicio(OrdenServicioDTO ordenServicioDTO,Long idPersonalOri,String motivoReasignacion,HttpSession session,HttpServletRequest request){	
        LOG.info("aprobar");
        Map<String,Object> retorno=new HashMap<String,Object>();
    	try{
            String codFlujoSup=supervisionServiceNeg.buscarCodigoFlujoSupervINPS(
                    ordenServicioDTO.getExpediente().getObligacionTipo().getIdObligacionTipo(), 
                    ordenServicioDTO.getExpediente().getUnidadSupervisada().getActividad().getIdActividad(), 
                    ordenServicioDTO.getExpediente().getProceso().getIdProceso());   
            if(!StringUtil.isEmpty(codFlujoSup) && codFlujoSup.equals(Constantes.CODIGO_FLUJO_SUPERV_INPS_DSR_CRI)){
                LOG.info("-->codFlujoSup "+Constantes.CODIGO_FLUJO_SUPERV_INPS_DSR_CRI);
                retorno=expedienteServiceNeg.aprobarOrdenServicioDsrCri(ordenServicioDTO.getExpediente().getIdExpediente(),ordenServicioDTO.getIdOrdenServicio(),idPersonalOri, ordenServicioDTO.getExpediente().getNumeroExpediente(), ordenServicioDTO.getExpediente().getAsuntoSiged(),request);
            }else{
                LOG.info("-->codFlujoSup Default");
                retorno=aprobarOrdenServicioDefault(ordenServicioDTO.getExpediente().getIdExpediente(),ordenServicioDTO.getIdOrdenServicio(),idPersonalOri, ordenServicioDTO.getExpediente().getNumeroExpediente(), ordenServicioDTO.getExpediente().getAsuntoSiged(),request);
            }
        }catch(Exception e){
            LOG.error("Error en aprobar",e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }
    
    private Map<String,Object> aprobarOrdenServicioDefault(Long idExpediente,Long idOrdenServicio,Long idPersonalOri, String numeroExpediente, String asuntoSiged,HttpServletRequest request){
        Map<String,Object> retorno=new HashMap<String,Object>();
        try{
            // htorress - RSIS 18 - Inicio
            ExpedienteDTO expediente = new ExpedienteDTO();
            expediente.setIdExpediente(idExpediente);
            expediente.setAsuntoSiged(asuntoSiged);
            expediente.setNumeroExpediente(numeroExpediente);

            PersonalDTO destinatario=(PersonalDTO) obtenerUsuarioOrigen(idOrdenServicio);

            ExpedienteDTO expedienteAprobar=expedienteServiceNeg.aprobarExpedienteSIGED(expediente, destinatario.getIdPersonalSiged(), "Aprobar.", true);
            if(expedienteAprobar.getNumeroExpediente()!=null){ 
            // htorress - RSIS 18 - Fin
                OrdenServicioDTO respuesta=ordenServicioServiceNeg.aprobar(idExpediente,idOrdenServicio,idPersonalOri,Constantes.getUsuarioDTO(request));

                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
                retorno.put(ConstantesWeb.VV_MENSAJE, "Cambios realizados");
                retorno.put("expediente",respuesta);
            // htorress - RSIS 18 - Inicio
            }else{
                LOG.error("Error en aprobar");
                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                retorno.put(ConstantesWeb.VV_MENSAJE, expedienteAprobar.getMensajeServicio());
            }
        }catch(Exception e){
            LOG.error("Error en aprobarOrdenServicioDefault",e);
        }
        // htorress - RSIS 18 - Fin
        return retorno;
    }
//    /* OSINE791 - RSIS21 - Inicio */
//    public OrdenServicioDTO ejecutarOtrasFunciones(String funcion,Long idExpediente,Long idOrdenServicio,Long numeroExpediente,Long idPersonalOri,HttpServletRequest request){
//        LOG.info("ejecutarOtrasFunciones");
//        OrdenServicioDTO retorno = null;        
//        try{
//            if(funcion!=null && funcion.equals(Constantes.ORDEN_SERVICIO_CONCLUIR)){
//                DetalleSupervisionFilter filtro=new DetalleSupervisionFilter();
//                filtro.setIdOrdenServicio(idOrdenServicio);
//                filtro.setCodigoResultadoSupervision(Constantes.CODIGO_RESULTADO_DETSUPERVISION_INCUMPLE);
//                List<DetalleSupervisionDTO> listaIncumplidas=supervisionServiceNeg.findDetalleSupervision(filtro);
//                LOG.info("---------->"+listaIncumplidas);
//                if(listaIncumplidas!=null && listaIncumplidas.size()==0){
//                    ExpedienteDTO expedienteArchivar=expedienteServiceNeg.archivarExpedienteSIGED(String.valueOf(numeroExpediente),"Archivar.");
//                    if(expedienteArchivar.getNumeroExpediente()!=null){
//                        retorno=ordenServicioServiceNeg.concluir(idExpediente,idOrdenServicio,idPersonalOri,Constantes.getUsuarioDTO(request), null, null); 
//                    }
//                }
//            }
//        }catch(Exception e){
//            LOG.error("Error en ejecutarOtrasFunciones",e);
//        }
//        return retorno;
//    }
//    /* OSINE791 - RSIS21 - Fin */
            
    @RequestMapping(value="/notificar",method= RequestMethod.GET)
    public @ResponseBody Map<String,Object> notificarOrdenServicio(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,HttpSession session,HttpServletRequest request){
        LOG.info("notificar");
        Map<String,Object> retorno=new HashMap<String,Object>();
    	try{
            OrdenServicioDTO respuesta=ordenServicioServiceNeg.notificar(idExpediente,idOrdenServicio,idPersonalOri,Constantes.getUsuarioDTO(request));
            
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put(ConstantesWeb.VV_MENSAJE, "Cambios realizados");
            retorno.put("expediente",respuesta);
        }catch(Exception e){
            LOG.error("Error en derivar",e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }
    
    @RequestMapping(value="/concluir",method= RequestMethod.GET)
    /* OSINE_SFS-480 - RSIS 17 - Inicio*/
    public @ResponseBody Map<String,Object> concluirOrdenServicio(Long idExpediente,Long idOrdenServicio,Long idPersonalOri, Long numeroExpediente, Long idArchivo, String fechaInicioPlazoDescargo, HttpSession session,HttpServletRequest request){
    /* OSINE_SFS-480 - RSIS 17 - Fin */
        LOG.info("concluir");
        Map<String,Object> retorno=new HashMap<String,Object>();
    	try{
            // htorress - RSIS 18 - Inicio
            ExpedienteDTO expedienteArchivar=expedienteServiceNeg.archivarExpedienteSIGED(String.valueOf(numeroExpediente),"Archivar.");
            if(expedienteArchivar.getNumeroExpediente()!=null){
            // htorress - RSIS 18 - Fin
                /* OSINE_SFS-480 - RSIS 17 - Inicio */
            	OrdenServicioDTO respuesta=ordenServicioServiceNeg.concluir(idExpediente,idOrdenServicio,idPersonalOri,Constantes.getUsuarioDTO(request), idArchivo, fechaInicioPlazoDescargo); 
	        /* OSINE_SFS-480 - RSIS 17 - Fin */    
                    retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
	            retorno.put(ConstantesWeb.VV_MENSAJE, "Cambios realizados");
	            retorno.put("expediente",respuesta);
            // htorress - RSIS 18 - Inicio
            }else{
            	LOG.error("Error en concluir");
                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                retorno.put(ConstantesWeb.VV_MENSAJE, expedienteArchivar.getMensajeServicio());
            }
            // htorress - RSIS 18 - Fin
        }catch(Exception e){
            LOG.error("Error en concluir",e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }
    
    @RequestMapping(value="/observar",method= RequestMethod.POST)
    // htorress - RSIS 18 - Inicio
    //public @ResponseBody Map<String,Object> observarOrdenServicio(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,String motivoReasignacion,HttpSession session,HttpServletRequest request){
    public @ResponseBody Map<String,Object> observarOrdenServicio(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,String asuntoSiged, Long numeroExpediente,String motivoReasignacion,HttpSession session,HttpServletRequest request){	
    // htorress - RSIS 18 - Fin
        LOG.info("observar");
        Map<String,Object> retorno=new HashMap<String,Object>();
    	try{
            // htorress - RSIS 18 - Inicio
            ExpedienteDTO expediente = new ExpedienteDTO();
            expediente.setAsuntoSiged(asuntoSiged);
            expediente.setNumeroExpediente(String.valueOf(numeroExpediente));
            expediente.setPersonal(new PersonalDTO());
            /* OSINE_SFS-480 - RSIS 40 - Inicio */
            expediente.getPersonal().setIdPersonalSiged(Constantes.getIDPERSONALSIGED(request));  
            /* OSINE_SFS-480 - RSIS 40 - Fin */
            PersonalDTO personaOrigen=(PersonalDTO) obtenerUsuarioOrigen(idOrdenServicio);

            
            // Usuario Origen: Jefe, entonces el especialista o Jefe regional devuelven a su vez al supervisor (reenvian).
            if(!(personaOrigen.getRol().getNombreRol().equals(Constantes.CONSTANTE_ROL_SUPERVISOR))){

            	// Obtención del idPersonalSiged del Supervisor
            	HistoricoEstadoFilter filtro = new HistoricoEstadoFilter();
                filtro.setIdOrdenServicio(idOrdenServicio);
                List<HistoricoEstadoDTO> listado=historicoEstadoServiceNeg.listarHistoricoEstado(filtro);
                
                Collections.sort(listado, new Comparator<HistoricoEstadoDTO>(){
                	@Override
                	public int compare(HistoricoEstadoDTO hist1, HistoricoEstadoDTO hist2) {                    		
                		return hist1.getFechaCreacion().compareTo(hist2.getFechaCreacion());
                	}});
                Collections.reverse(listado);
            	int cantidad = listado.size();
            	
            	List<PersonalDTO> destinatarioSupervisor = personalServiceNeg.findPersonal(new PersonalFilter(listado.get(cantidad-2).getPersonalOri().getIdPersonal()));
            	Long idSupervisor=destinatarioSupervisor.get(0).getIdPersonalSiged();
            	
            	ExpedienteDTO expedienteReenviarSiged=expedienteServiceNeg.reenviarExpedienteSIGED(expediente, idSupervisor , motivoReasignacion, false);
            	if(expedienteReenviarSiged.getNumeroExpediente()!=null){
	            	OrdenServicioDTO respuesta=ordenServicioServiceNeg.observar(idExpediente,idOrdenServicio,idPersonalOri,motivoReasignacion,Constantes.getUsuarioDTO(request));
		            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
		            retorno.put(ConstantesWeb.VV_MENSAJE, "Observación enviada.");
		            retorno.put("expediente",respuesta);
	            }else{
	            	LOG.error("Error en devolver");
	                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
	                retorno.put(ConstantesWeb.VV_MENSAJE, expedienteReenviarSiged.getMensajeServicio());
	            }
            	
            }else{
            	/*HistoricoEstadoFilter filtro = new HistoricoEstadoFilter();
                filtro.setIdOrdenServicio(idOrdenServicio);
            	List<HistoricoEstadoDTO> listado=historicoEstadoServiceNeg.listarHistoricoEstado(filtro);
            	if(Constantes.getIDPERSONALSIGED(request).equals(listado.get)){
            		
            	}*/
	            ExpedienteDTO expedienteDevolver=expedienteServiceNeg.rechazarExpedienteSIGED(expediente, motivoReasignacion);
	            if(expedienteDevolver.getEstado().equals(Constantes.FLAG_REGISTRADO_SI)){
		        // htorress - RSIS 18 - Fin
	            	OrdenServicioDTO respuesta=ordenServicioServiceNeg.observar(idExpediente,idOrdenServicio,idPersonalOri,motivoReasignacion,Constantes.getUsuarioDTO(request));
		            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
		            retorno.put(ConstantesWeb.VV_MENSAJE, "Observación enviada.");
		            retorno.put("expediente",respuesta);
		        // htorress - RSIS 18 - Inicio
	            }else{
	            	LOG.error("Error en devolver");
	                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
	                retorno.put(ConstantesWeb.VV_MENSAJE, expedienteDevolver.getMensajeServicio());
	            }

	            
            }
            // htorress - RSIS 18 - Fin
        }catch(Exception e){
            LOG.error("Error en observar",e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }
    
    @RequestMapping(value="/observarAprobar",method= RequestMethod.POST)
    //public @ResponseBody Map<String,Object> observAprobar(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,String asuntoSiged, Long numeroExpediente,String motivoReasignacion,HttpSession session,HttpServletRequest request){	
    public @ResponseBody Map<String,Object> observAprobar(OrdenServicioDTO ordenServicioDTO,Long idPersonalOri,String motivoReasignacion,HttpSession session,HttpServletRequest request){	
        LOG.info("observAprobar");
        Map<String,Object> retorno=new HashMap<String,Object>();
    	try{
            String codFlujoSup=supervisionServiceNeg.buscarCodigoFlujoSupervINPS(
                    ordenServicioDTO.getExpediente().getObligacionTipo().getIdObligacionTipo(), 
                    ordenServicioDTO.getExpediente().getUnidadSupervisada().getActividad().getIdActividad(), 
                    ordenServicioDTO.getExpediente().getProceso().getIdProceso());   
            if(!StringUtil.isEmpty(codFlujoSup) && codFlujoSup.equals(Constantes.CODIGO_FLUJO_SUPERV_INPS_DSR_CRI)){
                LOG.info("-->codFlujoSup "+Constantes.CODIGO_FLUJO_SUPERV_INPS_DSR_CRI);
                retorno=observAprobarDsrCri(ordenServicioDTO.getExpediente().getIdExpediente(),ordenServicioDTO.getIdOrdenServicio(),idPersonalOri,ordenServicioDTO.getExpediente().getAsuntoSiged(), ordenServicioDTO.getExpediente().getNumeroExpediente(),motivoReasignacion,request);
            }else{
                LOG.info("-->codFlujoSup Default");
                retorno=observAprobarDefault(ordenServicioDTO.getExpediente().getIdExpediente(),ordenServicioDTO.getIdOrdenServicio(),idPersonalOri,ordenServicioDTO.getExpediente().getAsuntoSiged(), ordenServicioDTO.getExpediente().getNumeroExpediente(),motivoReasignacion,request);
            }
        }catch(Exception e){
            LOG.error("Error en observAprobar",e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }

    // htorress - RSIS 16 - Inicio
    public Map<String,Object> observAprobarDefault(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,String asuntoSiged, String numeroExpediente,String motivoReasignacion,HttpServletRequest request){	
        LOG.info("observAprobarDefault");
        Map<String,Object> retorno=new HashMap<String,Object>();
    	try{
            ExpedienteDTO expediente = new ExpedienteDTO();
            expediente.setAsuntoSiged(asuntoSiged);
            expediente.setNumeroExpediente(numeroExpediente);
            expediente.setPersonal(new PersonalDTO());
            /* OSINE_SFS-480 - RSIS 40 - Inicio */ 
            expediente.getPersonal().setIdPersonalSiged(Constantes.getIDPERSONALSIGED(request)); 
            /* OSINE_SFS-480 - RSIS 40 - Fin */ 
            /*ExpedienteDTO expedienteAprobar=expedienteServiceNeg.aprobarExpedienteSIGED(expediente, idPersSIGED, "Validar Contenido a enviar.", false);
            if(expedienteAprobar.getNumeroExpediente()!=null){*/
            
	            ExpedienteDTO expedienteDevolver=expedienteServiceNeg.rechazarExpedienteSIGED(expediente, motivoReasignacion);
	            if(expedienteDevolver.getEstado().equals(Constantes.FLAG_REGISTRADO_SI)){
	            	OrdenServicioDTO respuesta=ordenServicioServiceNeg.observarAprobar(idExpediente,idOrdenServicio,idPersonalOri,motivoReasignacion,Constantes.getUsuarioDTO(request));
	            	retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
		            retorno.put(ConstantesWeb.VV_MENSAJE, "Observación enviada.");
		            retorno.put("expediente",respuesta);
	            }else{
	            	LOG.error("Error en devolver");
	                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
	                retorno.put(ConstantesWeb.VV_MENSAJE, expedienteDevolver.getMensajeServicio());
	            }
            /*}else{
            	LOG.error("Error en aprobar");
                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                retorno.put(ConstantesWeb.VV_MENSAJE, expedienteAprobar.getMensajeServicio());
            }*/
        }catch(Exception e){
            LOG.error("Error en observAprobarDefault",e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }
    // htorress - RSIS 16 - Fin
    
    public Map<String,Object> observAprobarDsrCri(Long idExpediente,Long idOrdenServicio,Long idPersonalOri,String asuntoSiged, String numeroExpediente,String motivoReasignacion,HttpServletRequest request){	
        LOG.info("observAprobarDsrCri");
        Map<String,Object> retorno=new HashMap<String,Object>();
    	try{
            ExpedienteDTO expediente = new ExpedienteDTO();
            expediente.setAsuntoSiged(asuntoSiged);
            expediente.setNumeroExpediente(numeroExpediente);
            expediente.setPersonal(new PersonalDTO());
            expediente.getPersonal().setIdPersonalSiged(Constantes.getIDPERSONALSIGED(request));  
            
            ExpedienteDTO expedienteDevolver=expedienteServiceNeg.rechazarExpedienteSIGED(expediente, motivoReasignacion);
            if(expedienteDevolver.getEstado().equals(Constantes.FLAG_REGISTRADO_SI)){
                OrdenServicioDTO respuesta=ordenServicioServiceNeg.observar(idExpediente,idOrdenServicio,idPersonalOri,motivoReasignacion,Constantes.getUsuarioDTO(request));
                    retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
                    retorno.put(ConstantesWeb.VV_MENSAJE, "Observación enviada.");
                    retorno.put("expediente",respuesta);
            }else{
                LOG.error("Error en devolver");
                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                retorno.put(ConstantesWeb.VV_MENSAJE, expedienteDevolver.getMensajeServicio());
            }            
        }catch(Exception e){
            LOG.error("Error en observAprobarDsrCri",e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }
    
    // htorress - RSIS 18 - Inicio
    public PersonalDTO obtenerUsuarioOrigen(Long idOrdenServicio){
    	LOG.info("obtenerUsuarioOrigen");
    	PersonalDTO usuarioSiged=null;
    	
    	HistoricoEstadoFilter filtro = new HistoricoEstadoFilter();
        filtro.setIdOrdenServicio(idOrdenServicio);
        List<HistoricoEstadoDTO> listado=historicoEstadoServiceNeg.listarHistoricoEstado(filtro);
        
        Collections.sort(listado, new Comparator<HistoricoEstadoDTO>(){
        	@Override
        	public int compare(HistoricoEstadoDTO hist1, HistoricoEstadoDTO hist2) {                    		
        		return hist1.getFechaCreacion().compareTo(hist2.getFechaCreacion());
        	}});
        Collections.reverse(listado);
        List<PersonalDTO> destinatario = personalServiceNeg.findPersonal(new PersonalFilter(listado.get(0).getPersonalOri().getIdPersonal()));
        /* OSINE_SFS-791 - RSIS 42 - Inicio */ 
        if( destinatario!=null && destinatario.size()>0) {
        	usuarioSiged=(PersonalDTO)destinatario.get(0);	
        }
        /* OSINE_SFS-791 - RSIS 42 - Fin */ 
    	return usuarioSiged;
    }
    // htorress - RSIS 18 - Fin
    @RequestMapping(value="/confirmarDescargo",method= RequestMethod.GET)
    public @ResponseBody Map<String,Object> confirmarDescargoOrdenServicio(Long idExpediente,Long idOrdenServicio,Long idPersonalOri, Long numeroExpediente,HttpSession session,HttpServletRequest request){
        LOG.info("concluir");
        Map<String,Object> retorno=new HashMap<String,Object>();
    	try{
    	
    		ExpedienteDTO expedienteReabrir=expedienteServiceNeg.reabrirExpedienteSIGED(String.valueOf(numeroExpediente));
    		
    		if(expedienteReabrir.getNumeroExpediente()!=null){
            	
            OrdenServicioDTO respuesta=ordenServicioServiceNeg.confirmarDescargo(idExpediente,idOrdenServicio,idPersonalOri,Constantes.getUsuarioDTO(request));
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put(ConstantesWeb.VV_MENSAJE, "Cambios realizados");
            retorno.put("expediente",respuesta);
            }else{
            	LOG.error("Error en reabrir");
                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                retorno.put(ConstantesWeb.VV_MENSAJE, expedienteReabrir.getMensajeServicio());
            }
        }catch(Exception e){
            LOG.error("Error en concluir",e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }
    
    /* OSINE_SFS-480 - RSIS 43 - Inicio */
    @RequestMapping(value="/anular",method= RequestMethod.GET)
    public @ResponseBody Map<String,Object> anularOrdenServicio(ExpedienteDTO expedienteDTO,Long idPersonalOri,String flagSupervision,HttpSession session,HttpServletRequest request){
        LOG.info("anular");
        Map<String,Object> retorno=new HashMap<String,Object>();
    	try{
        	OrdenServicioDTO respuesta=ordenServicioServiceNeg.anularExpedienteOrden(expedienteDTO.getIdExpediente(), expedienteDTO.getOrdenServicio().getIdOrdenServicio(),Constantes.getUsuarioDTO(request),expedienteDTO.getOrdenServicio().getComentarioDevolucion(),Constantes.getIDPERSONAL(request));            	
        	retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_ANULAR_OS);
            retorno.put("expediente",respuesta);
        }catch(Exception e){
            LOG.error("Error en anular",e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
    	return retorno;
    }  
    /* OSINE_SFS-480 - RSIS 43 - Fin */
    
    @RequestMapping(value="/cargarTrazabilidad",method= RequestMethod.GET)
    public @ResponseBody Map<String,Object> cargarTrazabilidad(HistoricoEstadoFilter filtro,int rows, int page,HttpSession session,HttpServletRequest request){
        LOG.info("cargarTrazabilidad");
    	
        Map<String,Object> retorno=new HashMap<String,Object>();
        try{
        	int indiceInicial=-1;
            int indiceFinal=-1;
            List<HistoricoEstadoDTO> listadoPaginado = null;
        	
            List<HistoricoEstadoDTO> listado=historicoEstadoServiceNeg.listarHistoricoEstado(filtro);
            
            Long contador = (long) listado.size();
            Long numeroFilas = (contador / rows);
            if ((contador % rows) > 0) { numeroFilas = numeroFilas + 1L;}
            if(numeroFilas<page){page = numeroFilas.intValue();}
            if(page==0){rows=0;}
            indiceInicial = (page - 1) * rows;
            indiceFinal = indiceInicial + rows;
            listadoPaginado = listado.subList(indiceInicial > listado.size() ? listado.size() : indiceInicial, indiceFinal > listado.size() ? listado.size() : indiceFinal );           
            retorno.put("total", numeroFilas);
            retorno.put("pagina", page);
            retorno.put("registros", contador);
            retorno.put("filas", listadoPaginado);
        }catch(Exception ex){
            LOG.error("Error cargarTrazabilidad",ex);
        }
        return retorno;
    }
    
    @RequestMapping(value ="/buscarProcesoFlag", method = RequestMethod.GET)
    public String buscarProcesoFlag(Long IdObligacionTipo,Long idactividad,Long IdProceso,HttpSession sesion,Model model){
        String retorno="0";
    	Integer flagSupervision=supervisionServiceNeg.buscarFlag(IdObligacionTipo, idactividad, IdProceso);
    	retorno=flagSupervision.toString();
    	return retorno;
    }
    /* OSINE_SFS-1344 - Inicio */   
    @RequestMapping(value="/findOrdenServicio",method= RequestMethod.POST)
    public @ResponseBody Map<String,Object> findOrdenServicio(OrdenServicioFilter filtro,int rows, int page,HttpSession session,HttpServletRequest request){
        LOG.info("findOrdenServicio");
        LOG.info("Finicio->"+filtro.getFechaInicioEstadoProceso());
        LOG.info("Ffin-"+filtro.getFechaFinEstadoProceso());
        
        Map<String,Object> retorno=new HashMap<String,Object>();
        try{
        	int indiceInicial=-1;
            int indiceFinal=-1;
            List<OrdenServicioDTO> listadoPaginado= null;
            List<OrdenServicioDTO> listado=ordenServicioServiceNeg.listarOrdenServicio(filtro);
            
            Long contador = (long) listado.size();
            Long numeroFilas = (contador / rows);
            if ((contador % rows) > 0) {numeroFilas = numeroFilas + 1L;}
            if(numeroFilas<page){page = numeroFilas.intValue();}
            if(page==0){rows=0;}
            indiceInicial = (page - 1) * rows;
            indiceFinal = indiceInicial + rows;
            listadoPaginado = listado.subList(indiceInicial > listado.size() ? listado.size() : indiceInicial,indiceFinal > listado.size() ? listado.size() : indiceFinal );
               
            retorno.put("total", numeroFilas);
            retorno.put("pagina", page);
            retorno.put("registros", contador);
            retorno.put("filas", listadoPaginado);
        }catch(Exception ex){
            LOG.error("Error findOrdenServicio",ex);
        }
        return retorno;
    }
    /* OSINE_SFS-1344 - Fin */   
    @RequestMapping(value = "/devolverOrdenServicio", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> devolverOrdenServicio(OrdenServicioFilter osFilter,Long idPeticion,Long idMotivo,String comentarioDevolucion,HttpSession session,HttpServletRequest request){
        LOG.info("procesando devolver");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try { 
            List<OrdenServicioDTO> registroDevueltos=ordenServicioServiceNeg.devolverOrdenServicioSupe(osFilter.getOrdenesServicio(),Constantes.getIDPERSONAL(request),Constantes.getIDPERSONALSIGED(request),idPeticion,idMotivo,comentarioDevolucion,Constantes.getUsuarioDTO(request));;                

            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put(ConstantesWeb.VV_MENSAJE, "Cambios realizados");
            retorno.put("orden servicio",registroDevueltos);
        }catch(Exception e){
            LOG.error("Error en devolver",e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;    
    }
    
    @InitBinder
    public void binder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
        public void setAsText(String value) {
            try {
                setValue(new SimpleDateFormat("dd/MM/yyyy").parse(value));
            } catch (ParseException e) {
                setValue(null);
            }
        }   

        public String getAsText() {
            return new SimpleDateFormat("dd/MM/yyyy")
                    .format((Date) getValue());
        }

        });
    } 
    
    /* OSINE_SFS-1344 - Inicio */    
    @RequestMapping(value = "/abrirOrdenServicioAtender", method = RequestMethod.POST)
    public String abrirOrdenServicioAtender(String rolSesion, ExpedienteGSMDTO expedienteDto, HttpSession sesion, Model model, HttpServletRequest request) {
        LOG.info("ordenServicioAtender - GSM");
        String tipo=Constantes.ORDEN_SERVICIO_ATENDER;
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
        return ConstantesGSM.Navegacion.PAGE_INPS_GSM_ORDEN_SERVICIO_ATENDER;
    }
    /* OSINE_SFS-1344 - Fin */
    /* OSINE_SFS-1344 - Inicio */  
    @RequestMapping(value="/cargaDataUnidadOperativa",method= RequestMethod.GET)
    public @ResponseBody Map<String, Object> cargaDataUnidadOperativa(UnidadSupervisadaFilter filtro,HttpSession session,HttpServletRequest request){ 
        LOG.info("cargaDataUnidadOperativa");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            List<MaestroColumnaDTO> listaDireUoDl = maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_DIRE_INPS_UO, Constantes.APLICACION_INPS);
            if(listaDireUoDl!=null && listaDireUoDl.size()!=0) {
                filtro.setCadCodigosTipoDireccion(listaDireUoDl.get(0).getCodigo());
            }
            //BusquedaUnidadSupervisadaDTO registro=unidadSupervisadaServiceNeg.cargaDataUnidadOperativa(filtro);
            List<BusquedaUnidadSupervisadaDTO> listaBusqueda=unidadSupervisadaServiceNeg.cargaDataUnidadOperativaMasiva(filtro);
            BusquedaUnidadSupervisadaDTO registro=null;
            if(listaBusqueda!=null && listaBusqueda.size()>0){ 
                registro=listaBusqueda.get(0);
            }
            
            if(registro==null){
                retorno.put("resultado",ConstantesWeb.VV_ERROR);
            }else{
                retorno.put("resultado",ConstantesWeb.VV_EXITO);
                retorno.put("registro", registro);
            }            
	}catch(Exception e){
            LOG.error("Error cargaDataUnidadOperativa",e);
            retorno.put("resultado",ConstantesWeb.VV_ERROR);
        }
        return retorno;
    }
    /* OSINE_SFS-1344 - Fin */
}