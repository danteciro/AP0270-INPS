package gob.osinergmin.inpsweb.controller;

import gob.osinergmin.inpsweb.service.business.DetalleSupervisionServiceNeg;
import gob.osinergmin.inpsweb.util.ConstantesWeb;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.ui.DetalleSupervisionFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author jpiro
 */
@Controller
@RequestMapping("/detalleSupervision")
public class DetalleSupervisionController {
    private static final Logger LOG = LoggerFactory.getLogger(DetalleSupervisionController.class);
    @Inject
    DetalleSupervisionServiceNeg detalleSupervisionServiceNeg;
    
    @RequestMapping(value = "/findDetalleSupervision", method = RequestMethod.POST)
    public @ResponseBody List<DetalleSupervisionDTO> findDetalleSupervision(DetalleSupervisionFilter filtro) {
        LOG.info("findDetalleSupervision");
        List<DetalleSupervisionDTO> retorno=new ArrayList<DetalleSupervisionDTO>();
        try {
            retorno = detalleSupervisionServiceNeg.listarDetalleSupervision(filtro);
        } catch (Exception ex) {
            LOG.error("Error findObligaciones", ex);
        }
        return retorno;
    }
    
    @RequestMapping(value = "/findResultadoSupervision", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> findResultadoSupervision(DetalleSupervisionFilter filtro) {
        LOG.info("findResultadoSupervision");
        Map<String, Object> retorno = new HashMap<String, Object>();
        Long cantidad = new Long(0);
        try {
            cantidad = detalleSupervisionServiceNeg.cantidadDetalleSupervision(filtro);
            retorno.put("CantidadResultadoSupervision", cantidad);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put(ConstantesWeb.VV_MENSAJE, "ok");
        } catch (Exception ex) {
            LOG.error("Error findResultadoSupervision", ex);
        }
        return retorno;
    }
    
    @RequestMapping(value = "/validarComentarioEjecucionMedida", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> validarComentarioEjecucionMedida(DetalleSupervisionFilter filtro) {
        LOG.info("validarComentarioEjecucionMedida");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            List<DetalleSupervisionDTO> detSup = detalleSupervisionServiceNeg.validarComentarioEjecucionMedida(filtro);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put(ConstantesWeb.VV_MENSAJE, "Ok");
            retorno.put("detSup", detSup);
        } catch (Exception ex) {
            LOG.error("Error validarComentarioEjecucionMedida", ex);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, ex.getMessage());
        }
        return retorno;
    }
}
