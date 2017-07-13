package gob.osinergmin.inpsweb.controller;

import gob.osinergmin.inpsweb.service.business.ComentarioComplementoServiceNeg;
import gob.osinergmin.inpsweb.service.business.ComplementoDetSupervisionServiceNeg;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.ConstantesWeb;
import gob.osinergmin.mdicommon.domain.dto.ComentarioComplementoDTO;
import gob.osinergmin.mdicommon.domain.dto.ComentarioDetSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.ComplementoDetSupValorDTO;
import gob.osinergmin.mdicommon.domain.dto.ComplementoDetSupervisionDTO;
import gob.osinergmin.mdicommon.domain.ui.ComentarioComplementoFilter;
import gob.osinergmin.mdicommon.domain.ui.ComplementoDetSupValorFilter;
import gob.osinergmin.mdicommon.domain.ui.ComplementoDetSupervisionFilter;
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

/**
 *
 * @author jpiro
 */
@Controller
@RequestMapping("/comentarioComplemento")
public class ComentarioComplementoController {
    private static final Logger LOG = LoggerFactory.getLogger(ComentarioComplementoController.class);
    
    @Inject
    ComentarioComplementoServiceNeg comentarioComplementoServiceNeg;
    @Inject
    ComplementoDetSupervisionServiceNeg complementoDetSupervisionServiceNeg;
    
    @RequestMapping(value = "/abrirFrm", method = RequestMethod.POST)
    public String abrirFrm(ComentarioDetSupervisionDTO comeDetSup, HttpSession sesion, HttpServletRequest request, Model model) {
        LOG.info("abrirFrmComplemento");
        try{
            model.addAttribute("comeDetSup",comeDetSup);
        }catch(Exception e){
            LOG.error("Error en abrirFrmComplemento", e);        
        }
        return ConstantesWeb.Navegacion.PAGE_INPS_COMENTARIO_COMPLEMENTO;
    }
    
    @RequestMapping(value="/obtenerDataComentarioComplemento",method= RequestMethod.POST)
    public @ResponseBody Map<String, Object> obtenerDataComentarioComplemento(ComentarioComplementoFilter filtro){
        LOG.info("obtenerDataComentarioComplemento");
    	Map<String, Object> retorno = new HashMap<String, Object>();
        List<ComentarioComplementoDTO> complementos=null;
        try{
            complementos=comentarioComplementoServiceNeg.getDataLstComentarioComplemento(filtro);
            
            if(!CollectionUtils.isEmpty(complementos) && filtro.getFlagBuscaComplDetSup()!=null && filtro.getFlagBuscaComplDetSup().equals(Constantes.ESTADO_ACTIVO) && filtro.getIdComentarioDetSupervision()!=null){
                List<ComplementoDetSupervisionDTO> lstCompDetSup=complementoDetSupervisionServiceNeg.findComplementoDetSupervision(new ComplementoDetSupervisionFilter(filtro.getIdComentarioDetSupervision()));
                if(!CollectionUtils.isEmpty(lstCompDetSup)){
                    for(ComplementoDetSupervisionDTO regCompDetSup : lstCompDetSup){
                        for(ComentarioComplementoDTO reg : complementos){
                            if(reg.getIdComentarioComplemento().equals(regCompDetSup.getIdComentarioComplemento())){
                                reg.setFlagComCompEnCompDetSup(Constantes.ESTADO_ACTIVO);
                                //buscar valores
                                List<ComplementoDetSupValorDTO> lstCompDetSupValor = complementoDetSupervisionServiceNeg.findComplementoDetSupValor(new ComplementoDetSupValorFilter(regCompDetSup.getIdComplementoDetSupervision()));
                                regCompDetSup.setValor(lstCompDetSupValor);
                                reg.setComplementoDetSup(regCompDetSup);
                                break;
                            }
                        }                        
                    }
                }
            }
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put("complementos", complementos);
        }catch(Exception ex){
            LOG.error("Error en obtenerDataComentarioComplemento",ex);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, ex.getMessage());
        }
        return retorno;
    }
}
