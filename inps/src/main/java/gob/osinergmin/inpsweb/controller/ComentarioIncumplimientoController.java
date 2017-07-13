/**
 * Resumen 
 * Objeto				: ComentarioIncumplimientoController.java 
 * Descripción	        : Controla los comentarios de incumplimiento. 
 * Fecha de Creación	: 
 * PR de Creación		: 
 * Autor				: 
 * ---------------------------------------------------------------------------------------------------
 * Modificaciones   Fecha             Nombre               Descripción
 * ---------------------------------------------------------------------------------------------------
 * OSINE_MAN_DSR_0037  | 18/06/2017     | Carlos Quijano Chavez::ADAPTER  | Actualiza comentarios adicionales
 */
package gob.osinergmin.inpsweb.controller;

/* OSINE_MAN_DSR_0037 - Inicio */
import gob.osinergmin.inpsweb.dto.ComentarioDetalleSupervisionOpcionalDTO;
/* OSINE_MAN_DSR_0037 - Fin */
import gob.osinergmin.inpsweb.service.business.ComentarioDetSupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.business.ComentarioIncumplimientoServiceNeg;
import gob.osinergmin.inpsweb.service.business.ComplementoDetSupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.business.EscenarioIncumplimientoServiceNeg;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.ConstantesWeb;
import gob.osinergmin.mdicommon.domain.dto.ComentarioDetSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.ComentarioIncumplimientoDTO;
import gob.osinergmin.mdicommon.domain.dto.ComplementoDetSupValorDTO;
import gob.osinergmin.mdicommon.domain.dto.ComplementoDetSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.EscenarioIncumplimientoDTO;
/*OSINE_SFS-791 - RSIS 16 - Inicio*/
import gob.osinergmin.mdicommon.domain.dto.InfraccionDTO;
/*OSINE_SFS-791 - RSIS 16 - Fin*/
import gob.osinergmin.mdicommon.domain.ui.ComentarioDetSupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.ComentarioIncumplimientoFilter;
import gob.osinergmin.mdicommon.domain.ui.ComplementoDetSupValorFilter;
import gob.osinergmin.mdicommon.domain.ui.ComplementoDetSupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.EscenarioIncumplimientoFilter;

import java.util.ArrayList;
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
@RequestMapping("/comentarioIncumplimiento")
public class ComentarioIncumplimientoController {
    private static final Logger LOG = LoggerFactory.getLogger(ComentarioIncumplimientoController.class);
    
    @Inject
    private EscenarioIncumplimientoServiceNeg escenarioIncumplimientoServiceNeg;
    @Inject
    private ComentarioIncumplimientoServiceNeg comentarioIncumplimientoServiceNeg;
    @Inject
    private ComentarioDetSupervisionServiceNeg comentarioDetSupervisionServiceNeg;
    @Inject
    private ComplementoDetSupervisionServiceNeg complementoDetSupervisionServiceNeg;
    
    @RequestMapping(value = "/abrirComentarioIncumplimiento", method = RequestMethod.POST)
    public String abrirComentarioIncumplimiento(EscenarioIncumplimientoFilter filtro,DetalleSupervisionDTO detaSupe, HttpSession sesion, HttpServletRequest request, Model model) {
        LOG.info("abrirComentarioIncumplimiento");
        try{
            EscenarioIncumplimientoDTO esceInc= new EscenarioIncumplimientoDTO();                    
            if(filtro.getIdEsceIncumplimiento()!=null){
                esceInc=escenarioIncumplimientoServiceNeg.getEscenarioIncumplimiento(new EscenarioIncumplimientoFilter(filtro.getIdEsceIncumplimiento(),null));
            }
            /*OSINE_SFS-791 - RSIS 16 - Inicio*/
            InfraccionDTO infraccion= new InfraccionDTO();
            if(filtro.getIdInfraccion()!=null){
                infraccion=escenarioIncumplimientoServiceNeg.getInfraccion(new EscenarioIncumplimientoFilter(null,filtro.getIdInfraccion()));
            }
            /*OSINE_SFS-791 - RSIS 16 - Fin*/
            
			/* OSINE_MAN_DSR_0037 - Inicio */
			ComentarioDetalleSupervisionOpcionalDTO comOpcional=new ComentarioDetalleSupervisionOpcionalDTO();
            if(esceInc.getIdEsceIncumplimiento()!=null)
            {
            	comOpcional=escenarioIncumplimientoServiceNeg.getComentarioOpciona(detaSupe.getIdDetalleSupervision(),esceInc.getIdEsceIncumplimiento());
            }
			/* OSINE_MAN_DSR_0037 - Fin */
            model.addAttribute("esceInc",esceInc);
      		/* OSINE_MAN_DSR_0037 - Inicio */
			model.addAttribute("comentarioOpcional",comOpcional);
			/* OSINE_MAN_DSR_0037 - Fin */      
			model.addAttribute("idDetalleSupervision",detaSupe.getIdDetalleSupervision());
            model.addAttribute("idInfraccion",filtro.getIdInfraccion());
            model.addAttribute("codigoOsinergmin",filtro.getCodigoOsinergmin());
            model.addAttribute("modoSupervision",filtro.getModoSupervision()); 
            /*OSINE_SFS-791 - RSIS 16 - Inicio*/
            model.addAttribute("infraccion", infraccion);
            /*OSINE_SFS-791 - RSIS 16 - Fin*/
        }catch(Exception e){
            LOG.error("Error en abrirComentarioIncumplimiento", e);        
        }
        return ConstantesWeb.Navegacion.PAGE_INPS_COMENTARIO_INCUMPLIMIENTO;
    }
    
    @RequestMapping(value = "/findComentarioIncumplimiento", method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody
    Map<String, Object> findComentarioIncumplimiento(ComentarioIncumplimientoFilter filtro, int rows, int page, HttpSession session, HttpServletRequest request) {
        LOG.info("findComentarioIncumplimineto");

        Map<String, Object> retorno = new HashMap<String, Object>();
        int indiceInicial = -1;
        int indiceFinal = -1;
        try {
            List<ComentarioIncumplimientoDTO> listadoPaginado = null;
            List<ComentarioIncumplimientoDTO> listado = comentarioIncumplimientoServiceNeg.listarComentarioIncumplimiento(filtro);
            
            listado=setColorDescComentario(listado);
            
            if(!CollectionUtils.isEmpty(listado) && filtro.getFlagBuscaComentDetSup()!=null && filtro.getFlagBuscaComentDetSup().equals(Constantes.ESTADO_ACTIVO) && filtro.getIdDetalleSupervision()!=null){
                List<ComentarioDetSupervisionDTO> lstComenDetSup=comentarioDetSupervisionServiceNeg.findComentarioDetSupervision(new ComentarioDetSupervisionFilter(null,filtro.getIdDetalleSupervision(),filtro.getIdEsceIncumplimiento()));
                if(!CollectionUtils.isEmpty(lstComenDetSup)){
                    for(ComentarioDetSupervisionDTO regComenDetSup : lstComenDetSup){
                        for(ComentarioIncumplimientoDTO reg : listado){                        	
                            if(reg.getIdComentarioIncumplimiento().equals(regComenDetSup.getComentarioIncumplimiento().getIdComentarioIncumplimiento())){
                                reg.setFlagComentDetSupEnEsceIncdo(Constantes.ESTADO_ACTIVO);
                                reg.setIdComentarioDetSupervision(regComenDetSup.getIdComentarioDetSupervision());
                                //buscar complementoDetSupervision y sus valores para armar ComentarioDetSupArmado
                                String descripcion=reg.getDescripcion();
                                List<ComplementoDetSupervisionDTO> lstCompDetSup=complementoDetSupervisionServiceNeg.findComplementoDetSupervision(new ComplementoDetSupervisionFilter(reg.getIdComentarioDetSupervision()));
                                if(!CollectionUtils.isEmpty(lstCompDetSup)){
                                    for(ComplementoDetSupervisionDTO regCompDetSup : lstCompDetSup){
                                        List<ComplementoDetSupValorDTO> lstCompDetSupValor = complementoDetSupervisionServiceNeg.findComplementoDetSupValor(new ComplementoDetSupValorFilter(regCompDetSup.getIdComplementoDetSupervision()));
                                        if(!CollectionUtils.isEmpty(lstCompDetSupValor)){
                                            String comDetSup="";
                                            for(ComplementoDetSupValorDTO cdsv : lstCompDetSupValor){
                                                comDetSup=comDetSup+cdsv.getValorDesc()+", ";
                                            }
                                            comDetSup=comDetSup.substring(0, comDetSup.length()-2);
                                            if(regCompDetSup.getComentarioComplemento()!=null 
                                                && regCompDetSup.getComentarioComplemento().getComplemento()!=null 
                                                && regCompDetSup.getComentarioComplemento().getComplemento().getEtiquetaComentario()!=null){
                                                descripcion=descripcion.replace(regCompDetSup.getComentarioComplemento().getComplemento().getEtiquetaComentario(),comDetSup);
                                            }
                                        }                                        
                                    }
                                }
                                reg.setDescripcion(descripcion);
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
            LOG.error("Error findComentarioIncumplimineto", ex);

        }
        return retorno;
    }
    
	 /* OSINE_MAN_DSR_0037 - Inicio */
    @RequestMapping(value = "/setComentarioIncumplimientoOpcional", method = {RequestMethod.POST})
    public @ResponseBody
   	Long setComentarioIncumplimiento(ComentarioDetalleSupervisionOpcionalDTO comentario, HttpSession session, HttpServletRequest request) {
        LOG.info("setComentarioIncumpliminetoOpcional");
        Long retorno=0L;
        try {
        	if(comentario.getIdComentarioDetalleSupervisionOpcional()==0)
        	{
        		retorno=comentarioIncumplimientoServiceNeg.agregarComentarioDetalleSupervisionOpcional(comentario,request);
        	}
        	else
        	{
        		retorno=comentarioIncumplimientoServiceNeg.editarComentarioDetalleSupervisionOpcional(comentario,request);
        	}

        } catch (Exception ex) {
            LOG.error("Error setComentarioIncumpliminetoOpcional", ex);

        }
        return retorno;
    }
    /* OSINE_MAN_DSR_0037 - Fin */
		
    @RequestMapping(value = "/asignaComentarioDetSupervision", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> asignaComentarioDetSupervision(ComentarioDetSupervisionDTO comenDetSuperDto,HttpServletRequest request) {
        LOG.info("asignaComentarioDetSupervision");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {   
            ComentarioDetSupervisionDTO registroBD = comentarioDetSupervisionServiceNeg.asignarComentarioDetSupervision(comenDetSuperDto,Constantes.getUsuarioDTO(request));

            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put(ConstantesWeb.VV_MENSAJE, "Registro Guardado.");
            retorno.put("comentarioDetSup", registroBD);
        } catch (Exception e) {
            LOG.error("Error en asignaComentarioDetSupervision", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }
    
    @RequestMapping(value = "/desasignaComentarioDetSupervision", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> desasignaComentarioDetSupervision(ComentarioDetSupervisionDTO comenDetSuperDto,HttpServletRequest request) {
        LOG.info("desasignaComentarioDetSupervision");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {   
            ComentarioDetSupervisionDTO registroBD = comentarioDetSupervisionServiceNeg.desasignarComentarioDetSupervision(comenDetSuperDto,Constantes.getUsuarioDTO(request));

            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put(ConstantesWeb.VV_MENSAJE, "Registro Guardado.");
            retorno.put("comentarioDetSup", registroBD);
        } catch (Exception e) {
            LOG.error("Error en desasignaComentarioDetSupervision", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }
    
    @RequestMapping(value = "/guardarComplDetSupervision", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> guardarComplDetSupervision(ComentarioDetSupervisionDTO comenDetSuperDto,HttpServletRequest request) {
        LOG.info("guardarComplDetSupervision");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {   
            ComentarioDetSupervisionDTO registroBD = complementoDetSupervisionServiceNeg.guardarComplementoDetSupervision(comenDetSuperDto,Constantes.getUsuarioDTO(request));

            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put(ConstantesWeb.VV_MENSAJE, "Registro Guardado.");
            retorno.put("comentarioDetSup", registroBD);
        } catch (Exception e) {
            LOG.error("Error en guardarComplDetSupervision", e);
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            retorno.put(ConstantesWeb.VV_MENSAJE, e.getMessage());
        }
        return retorno;
    }
    
    public List<ComentarioIncumplimientoDTO> setColorDescComentario(List<ComentarioIncumplimientoDTO> listado){
    	List<ComentarioIncumplimientoDTO> listaNueva=new ArrayList<ComentarioIncumplimientoDTO>();
    	if(!CollectionUtils.isEmpty(listado)){
        	for(ComentarioIncumplimientoDTO reg : listado){
        		String descripcion = reg.getDescripcion();
        		//mdiosesf-Pendiente
        		descripcion=descripcion.replace("{","<span style='"+Constantes.STYLE_VARIABLE_COMENTARIO_COMPLEMENTO+"'>{");
            	descripcion=descripcion.replace("}","}</span>");            	
            	reg.setDescripcion(descripcion);
            	listaNueva.add(reg);
        	}
    	}  	    	
    	return listaNueva;
    }
}
