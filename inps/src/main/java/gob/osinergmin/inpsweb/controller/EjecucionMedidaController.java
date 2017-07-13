/**
* Resumen Objeto       : EjecucionMedidaController.java 
* Descripción          : Controla el flujo de datos para ejecucion Medida 
* Fecha de Creación    : 
* OSINE_SFS-4791 Autor  : Luis García
* ---------------------------------------------------------------------------------------------------
* Modificaciones       Fecha                Nombre                     Descripción
* ---------------------------------------------------------------------------------------------------
* 
*
*/


package gob.osinergmin.inpsweb.controller;

import gob.osinergmin.inpsweb.service.business.DetalleSupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.business.EscenarioIncumplidoServiceNeg;
import gob.osinergmin.inpsweb.service.business.ResultadoSupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.business.SupervisionServiceNeg;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.ConstantesWeb;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.EscenarioIncumplidoDTO;
import gob.osinergmin.mdicommon.domain.dto.ResultadoSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionDTO;
import gob.osinergmin.mdicommon.domain.ui.DetalleSupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.EscenarioIncumplidoFilter;
import gob.osinergmin.mdicommon.domain.ui.ResultadoSupervisionFilter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.eclipse.jetty.util.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/ejecucionMedida")
public class EjecucionMedidaController {
    private static final Logger LOG = LoggerFactory.getLogger(EjecucionMedidaController.class);
    @Inject
    private DetalleSupervisionServiceNeg detalleSupervisionServiceNeg;
    @Inject
    private EscenarioIncumplidoServiceNeg escenarioIncumplidoServiceNeg;
    @Inject
    private ResultadoSupervisionServiceNeg resultadoSupervisionServiceNeg;
    @Inject
    private SupervisionServiceNeg supervisionServiceNeg;
    
    @RequestMapping(value = "/abrirComentarioDetalleSupervision", method = {RequestMethod.POST,RequestMethod.GET})
    public String abrirComentarioDetalleSupervision(DetalleSupervisionFilter filtroDs,EscenarioIncumplidoFilter filtroEi,HttpSession sesion, Model model, HttpServletRequest request) {
        LOG.info("abrirComentarioDetalleSupervision");
        try {  
            
            if(filtroDs.getIdDetalleSupervision() != null){
            DetalleSupervisionDTO detalleSupervisionDTO = detalleSupervisionServiceNeg.getDetalleSupervision(filtroDs);
            model.addAttribute("detSup",detalleSupervisionDTO);
            }
            LOG.info("idEscenarioIncumplido:::.."+filtroEi.getIdEscenarioIncumplido());
            if(filtroEi.getIdEscenarioIncumplido() != null){
            EscenarioIncumplidoDTO escenarioIncumplidoDTO = escenarioIncumplidoServiceNeg.getEscenarioIncumplido(filtroEi);
            model.addAttribute("escInDo",escenarioIncumplidoDTO);
            }

        } catch (Exception e) {
            LOG.error("Error en abrirComentarioDetalleSupervision", e);     
        }
        
        return ConstantesWeb.Navegacion.PAGE_INPS_REGISTRO_COMENTARIO_EJECUCION_MEDIDA;
    }
    
    @RequestMapping(value = "/registroComentarioDetalleSupervision", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> registroComentarioDetalleSupervision(DetalleSupervisionDTO detalleSupervisionDTO, HttpSession session, HttpServletRequest request) {
        LOG.info("registroComentarioDetalleSupervision");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            DetalleSupervisionDTO registroComentarioDetalleSupervision = detalleSupervisionServiceNeg.registroComentarioDetSupervision(detalleSupervisionDTO, Constantes.getUsuarioDTO(request));

            retorno.put("comentarioDetalleSupervision", registroComentarioDetalleSupervision);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put(ConstantesWeb.VV_MENSAJE, "Se realiz&oacute; el registro satisfactoriamente");

        } catch (Exception e) {
            LOG.error("Error en registrar comentario Detalle Supervision", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }
    
    @RequestMapping(value = "/registroComentarioEscenarioIncumplido", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> registroComentarioEscenarioIncumplido(EscenarioIncumplidoDTO escenarioIncumplidoDTO, HttpSession session, HttpServletRequest request) {
        LOG.info("registroComentarioEscenarioIncumplido");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            EscenarioIncumplidoDTO comentarioEscenarioIncumplido = escenarioIncumplidoServiceNeg.registroComentarioEscIncumplido(escenarioIncumplidoDTO, Constantes.getUsuarioDTO(request));

            retorno.put("comentarioEscenarioIncumplido", comentarioEscenarioIncumplido);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put(ConstantesWeb.VV_MENSAJE, "Se realiz&oacute; el registro satisfactoriamente");

        } catch (Exception e) {
            LOG.error("Error en registrar comentario Escenario Incumplido", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }
    
    @RequestMapping(value = "/eliminarComentarioEjecucionMedida", method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody
    Map<String, Object> eliminarComentarioEjecucionMedida(SupervisionDTO supervisionDTO,HttpSession session, HttpServletRequest request) {
        LOG.info("eliminarComentarioEjecucionMedida");
        Map<String, Object> retorno = new HashMap<String, Object>();   
        try{
            SupervisionDTO registroEjecucionMedidaSupervision = supervisionServiceNeg.registroEmSupervision(supervisionDTO, Constantes.getUsuarioDTO(request));
            if (supervisionDTO != null) {
                List<DetalleSupervisionDTO> lista = supervisionDTO.getListaDetalleSupervision();
                if(lista != null){ 
                    for (DetalleSupervisionDTO detalleSupervisionDTO : lista) {
                        
                        ResultadoSupervisionFilter filtro= new ResultadoSupervisionFilter();
                        filtro.setIdResultadosupervision(detalleSupervisionDTO.getResultadoSupervision().getIdResultadosupervision());
                        List<ResultadoSupervisionDTO> resultadoSupervision=resultadoSupervisionServiceNeg.listarResultadoSupervision(filtro);
                                               
                        if(detalleSupervisionDTO.getCountEscIncumplido() <1 && resultadoSupervision.get(0).getCodigo().longValue()==Constantes.CODIGO_RESULTADO_INCUMPLE){
                            LOG.info("actualizaComentarioEjecucion -- Detalle Supervision ");
                            detalleSupervisionDTO.setComentario(Constantes.CONSTANTE_VACIA);
                            DetalleSupervisionDTO comentarioDetalleSupervision = detalleSupervisionServiceNeg.actualizarComentarioDetalleSupervision(detalleSupervisionDTO, Constantes.getUsuarioDTO(request));    
                            
                            retorno.put("comentarioDetalleSupervision", comentarioDetalleSupervision);
                            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
                            retorno.put(ConstantesWeb.VV_MENSAJE, "Se realiz&oacute; el registro satisfactoriamente");
                        }
                        
                        if(detalleSupervisionDTO.getCountEscIncumplido() >0 && resultadoSupervision.get(0).getCodigo().longValue()==Constantes.CODIGO_RESULTADO_INCUMPLE){
                            LOG.info("actualizaComentarioEjecucion -- Escenario Incumplido ");
                            EscenarioIncumplidoFilter filtroEido= new EscenarioIncumplidoFilter();
                            filtroEido.setIdDetalleSupervision(detalleSupervisionDTO.getIdDetalleSupervision());
                            List<EscenarioIncumplidoDTO> listaEscenarioIncumplido=escenarioIncumplidoServiceNeg.findEscenarioIncumplido(filtroEido);
                            for(EscenarioIncumplidoDTO escenarioIncumplidoDTO:listaEscenarioIncumplido){
                                escenarioIncumplidoDTO.setComentarioEjecucion(Constantes.CONSTANTE_VACIA);
                                EscenarioIncumplidoDTO comentarioEscenarioIncumplido = escenarioIncumplidoServiceNeg.actualizarComentarioEscenarioIncumplido(escenarioIncumplidoDTO, Constantes.getUsuarioDTO(request));
                                
                                retorno.put("comentarioEscenarioIncumplido", comentarioEscenarioIncumplido);
                                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
                                retorno.put(ConstantesWeb.VV_MENSAJE, "Se realiz&oacute; el registro satisfactoriamente");
                            }
                        }      
                    }
                }
            }
            retorno.put("registroEmSup", registroEjecucionMedidaSupervision);
        } catch (Exception e) {
            LOG.error("Error en eliminar Comentario Ejecucion Medida", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }
    
}