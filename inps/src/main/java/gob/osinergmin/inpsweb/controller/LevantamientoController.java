/**
* Resumen		
* Objeto			: LevantamientoController.java
* Descripción		: Controla el flujo de datos del objeto Levantamiento
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-791
* Autor				: Mario Dioses Fernandez
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-791     07/10/2016      Mario Dioses Fernandez          Crear la funcionalidad "verificar levantamientos" que permita verificar el tipo de asignación para la orden de levantamiento DSR-CRITICIDAD.
*  
*/ 
package gob.osinergmin.inpsweb.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import gob.osinergmin.inpsweb.dto.GenerarResultadoDTO;
import gob.osinergmin.inpsweb.service.business.DetalleLevantamientoServiceNeg;
import gob.osinergmin.inpsweb.service.business.DetalleSupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.business.OrdenServicioServiceNeg;
import gob.osinergmin.inpsweb.service.business.SupervisionServiceNeg;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.ConstantesWeb;
import gob.osinergmin.mdicommon.domain.dto.DetalleLevantamientoDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.OrdenServicioDTO;
import gob.osinergmin.mdicommon.domain.dto.ResultadoSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.UbigeoDTO;
import gob.osinergmin.mdicommon.domain.ui.DetalleLevantamientoFilter;
import gob.osinergmin.mdicommon.domain.ui.DetalleSupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.OrdenServicioFilter;
import gob.osinergmin.mdicommon.domain.ui.SupervisionFilter;
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
@RequestMapping("/levantamiento")
public class LevantamientoController {
	private static final Logger LOG = LoggerFactory.getLogger(LevantamientoController.class);
	
	@Inject
	private DetalleSupervisionServiceNeg  detalleSupervisionServiceNeg;
	
	@Inject
	private SupervisionServiceNeg  supervisionServiceNeg;
	
	@Inject
	private DetalleLevantamientoServiceNeg  detalleLevantamientoServiceNeg;
	
	@Inject
	private OrdenServicioServiceNeg  ordenServicioServiceNeg;
	
	@RequestMapping(value = "/listarInfaccionLevantamiento", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> listarInfaccionLevantamiento(OrdenServicioFilter filtroOrdenServicio, int rows, int page, HttpSession session, HttpServletRequest request) {        
        LOG.info("procesando POST para RequestMapping /listarInfaccionLevantamiento");
        Map<String, Object> retorno = new HashMap<String, Object>();
        List<DetalleSupervisionDTO> detalleSupervisionList=new ArrayList<DetalleSupervisionDTO>();
        List<DetalleSupervisionDTO> listadoPaginado=null;
        int indiceInicial = -1;
        int indiceFinal = -1;
        try {
        	List<OrdenServicioDTO> ordenServicioList=ordenServicioServiceNeg.findByFilter(filtroOrdenServicio);
        	if(ordenServicioList!=null && ordenServicioList.size()!=0){        	
        		Long idOrderServicio=ordenServicioList.get(0).getIdOrdenServicio();        	
	        	SupervisionFilter filtro=new SupervisionFilter();
	        	filtro.setIdOrdenServicio(idOrderServicio);
	        	filtro.setFlagOrdeByIdSupervisionDesc(Constantes.ESTADO_ACTIVO);
	        	List<SupervisionDTO> supervisionList=supervisionServiceNeg.buscarSupervision(filtro);
	        	if(supervisionList!=null &&  supervisionList.size()!=0){
	        		SupervisionDTO supervision=supervisionList.get(0);
	        		DetalleSupervisionFilter filtroDetalleSup=new DetalleSupervisionFilter();
	        		if(supervision!=null && supervision.getSupervisionAnterior()!=null){
		        		SupervisionDTO supervisionAnt=supervision.getSupervisionAnterior();
		        		if(supervisionAnt!=null && supervisionAnt.getIdSupervision()!=null){
		        			filtroDetalleSup=new DetalleSupervisionFilter();
		        			filtroDetalleSup.setIdSupervision(supervisionAnt.getIdSupervision());
		        			detalleSupervisionList = detalleSupervisionServiceNeg.listarDetSupInfLevantamiento(filtroDetalleSup);
		        		} 
	        		} else if(supervision!=null && supervision.getIdSupervision()!=null){
	        			filtroDetalleSup=new DetalleSupervisionFilter();
	        			filtroDetalleSup.setIdSupervision(supervision.getIdSupervision());
	        			detalleSupervisionList = detalleSupervisionServiceNeg.listarDetSupInfLevantamiento(filtroDetalleSup);
	        		}
	        	}
        	}
        	
            Long contador = (long) detalleSupervisionList.size();
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
            listadoPaginado = detalleSupervisionList.subList(indiceInicial > detalleSupervisionList.size() ? detalleSupervisionList.size() : indiceInicial, indiceFinal > detalleSupervisionList.size() ? detalleSupervisionList.size() : indiceFinal);
            retorno.put("total", numeroFilas);
            retorno.put("pagina", page);
            retorno.put("registros", contador);
            retorno.put("filas", listadoPaginado);
        } catch (Exception ex) {
            LOG.error("Error listarInfaccionLevantamiento", ex);
        }
        return retorno;
    }
	
	@RequestMapping(value="/abrirLevantamientoInfraccionSup",method = RequestMethod.POST)
    public String abrirLevantamientoInfraccionSup(Long idDetalleSupervision, HttpSession session, HttpServletRequest request, Model model) {
		LOG.info("procesando POST para RequestMapping /abrirLevantamientoInfraccionSup");
		DetalleSupervisionDTO detalleSupervision=null;
		DetalleLevantamientoDTO detalleLevantamiento=null;
		try{
			//getDetalleSupervision
			DetalleSupervisionFilter filtro=new DetalleSupervisionFilter();
			filtro.setIdDetalleSupervision(idDetalleSupervision);
			List<DetalleSupervisionDTO> detalleSupervisionList = detalleSupervisionServiceNeg.listarDetalleSupervision(filtro);
            if (detalleSupervisionList!=null && detalleSupervisionList.size()!=0) {
            	detalleSupervision=detalleSupervisionList.get(0);
            	if(detalleSupervision.getSupervision()!=null && detalleSupervision.getSupervision().getIdSupervision()!=null){
            		List<DetalleSupervisionDTO> totalDetaSupeList = detalleSupervisionServiceNeg.listarDetalleSupervision(new DetalleSupervisionFilter(detalleSupervision.getSupervision().getIdSupervision(), null));
            		if (totalDetaSupeList!=null) {
            			detalleSupervision.setTotalPrioridad(new Long(totalDetaSupeList.size()));  
            		}
            	}                 
                if(detalleSupervision.getResultadoSupervisionAnt() == null ){
                    ResultadoSupervisionDTO resultadoAnt = new ResultadoSupervisionDTO();
                    resultadoAnt.setIdResultadosupervision(Constantes.ID_RESULTADO_ANTERIOR_DEFAULT);
                    resultadoAnt.setCodigo(Constantes.CODIGO_RESULTADO_ANTERIOR_DEFAULT);                
                    detalleSupervision.setResultadoSupervisionAnt(resultadoAnt);
                }                
            }      
        	//getDetalleLevantamiento
            DetalleLevantamientoFilter filtroLev=new DetalleLevantamientoFilter();
            filtroLev.setIdDetalleSupervision(idDetalleSupervision);
            filtroLev.setFlagUltimoRegistro(Constantes.ESTADO_ACTIVO);
            List<DetalleLevantamientoDTO> detalleLevantamientoist = detalleLevantamientoServiceNeg.find(filtroLev);
            if(detalleLevantamientoist!=null && detalleLevantamientoist.size()!=0){
            	detalleLevantamiento=detalleLevantamientoist.get(0);
            }
            
            model.addAttribute("detalleSupervision", detalleSupervision);
            model.addAttribute("detalleLevantamiento", detalleLevantamiento);
		} catch (Exception ex) {
            LOG.error("Error abrirLevantamientoInfraccionSup", ex);
        }
        return ConstantesWeb.Navegacion.PAGE_INPS_DETALLE_LEVANTAMIENTO_INFRACCION;
    }
	
	@RequestMapping(value = "/confirmarOrdenServicioLevantamiento", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> confirmarOrdenServicioLevantamiento(OrdenServicioFilter filtro, GenerarResultadoDTO resultado, HttpServletRequest request, HttpSession session, Model model){
		LOG.info("procesando POST para RequestMapping /confirmarOrdenServicioLevantamiento");
        Map<String, Object> retorno = new HashMap<String, Object>();        
		try{       
			UbigeoDTO ubigoDTO = new UbigeoDTO();
            ubigoDTO.setIdDepartamento(resultado.getIdDepartamento());
            ubigoDTO.setIdProvincia(resultado.getIdProvincia());
            ubigoDTO.setIdDistrito(resultado.getIdDistrito());            
            
            Long idPersonal=Constantes.getIDPERSONAL(request);
            Long idPersonalSiged=Constantes.getIDPERSONALSIGED(request);
             
            ordenServicioServiceNeg.confirmarOrdenServicioLevantamiento(filtro, ubigoDTO, Constantes.getUsuarioDTO(request), idPersonal, idPersonalSiged);
                        
			retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
			retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_SUCCESS_CREATE);			 
		} catch(Exception e){
			LOG.error("Error en confirmarOrdenServicioLevantamiento ",e);
			retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_ERROR_CREATE);
		}
		return retorno;
	}	
}
