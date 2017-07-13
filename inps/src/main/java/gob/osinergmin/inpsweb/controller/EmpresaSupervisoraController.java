/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.controller;
import gob.osinergmin.inpsweb.service.business.LocadorServiceNeg;
import gob.osinergmin.inpsweb.service.business.MaestroColumnaServiceNeg;
import gob.osinergmin.inpsweb.service.business.SupervisoraEmpresaServiceNeg;
import gob.osinergmin.inpsweb.service.business.UnidadOrganicaServiceNeg;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.LocadorDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisoraEmpresaDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadOrganicaDTO;
import gob.osinergmin.mdicommon.domain.ui.LocadorFilter;
import gob.osinergmin.mdicommon.domain.ui.SupervisoraEmpresaFilter;
import gob.osinergmin.mdicommon.domain.ui.UnidadDivisionFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
@RequestMapping("/empresaSupervisora")
public class EmpresaSupervisoraController {
    private static final Logger LOG = LoggerFactory.getLogger(ExpedienteController.class);
    @Inject
    UnidadOrganicaServiceNeg unidadOrganicaServiceNeg;
    @Inject
    private MaestroColumnaServiceNeg maestroColumnaServiceNeg;
    @Inject
    private LocadorServiceNeg locadorServiceNeg;
    @Inject
    private SupervisoraEmpresaServiceNeg supervisoraEmpresaServiceNeg;
    
    @RequestMapping(value = "/cargarEntidad", method = RequestMethod.GET)
    public @ResponseBody List<MaestroColumnaDTO> cargarEntidad(HttpSession session,HttpServletRequest request) {
        LOG.info("cargarEntidad");
        List<MaestroColumnaDTO> retorno=new ArrayList<MaestroColumnaDTO>();
        try{
            retorno= maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_ENTIDAD, Constantes.APLICACION_SGLSS);
        }catch(Exception e){
            LOG.error("error cargarEntidad",e);
        }
        return retorno;
    }
    
    @RequestMapping(value = "/cargarTipoEmpresaSupervisora", method = RequestMethod.GET)
    public @ResponseBody List<MaestroColumnaDTO> cargarTipoEmpresaSupervisora(HttpSession session,HttpServletRequest request) {
        LOG.info("cargarTipoEmpresaSupervisora");
        List<MaestroColumnaDTO> retorno=new ArrayList<MaestroColumnaDTO>();
        try{
            retorno= maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIPO_SUPERVISOR, Constantes.APLICACION_INPS);
        }catch(Exception e){
            LOG.error("error cargarTipoEmpresaSupervisora",e);
        }
        return retorno;
    }
    
    @RequestMapping(value = "/cargarLocador", method = RequestMethod.GET)
    public @ResponseBody List<LocadorDTO> cargarLocador(Long idObligacionTipo, Long idProceso, Long idRubro, Long idDepartamento,
    		Long idProvincia, Long idDistrito, HttpSession session,HttpServletRequest request) {
        LOG.info("cargarLocador");
        List<LocadorDTO> retorno=new ArrayList<LocadorDTO>();
        try{ 
            if(idObligacionTipo!=null && idObligacionTipo==-1) {idObligacionTipo=null;}
            if(idProceso!=null && idProceso==-1) {idProceso=null;}
            if(idRubro!=null && idRubro==-1) {idRubro=null;}
            if(idDepartamento!=null && idDepartamento==-1) {idDepartamento=null;}
            if(idProvincia!=null && idProvincia==-1) {idProvincia=null;}
            if(idDistrito!=null && idDistrito==-1) {idDistrito=null;}

            LocadorFilter filtro=new LocadorFilter();
            filtro.setIdProceso(idProceso);
            filtro.setIdObligacionTipo(idObligacionTipo);
            filtro.setIdRubro(idRubro);
            filtro.setIdDepartamento(idDepartamento);
            filtro.setIdProvincia(idProvincia);
            filtro.setIdDistrito(idDistrito);       		
            filtro.setEstado(Constantes.ESTADO_ACTIVO);
            filtro.setIdUnidadOrganica((Long) request.getSession().getAttribute(Constantes.PERSONAL_UNIDAD_ORGANICA_DIVISION));
            retorno=locadorServiceNeg.listarLocadorConfFiltros(filtro);	           
        }catch(Exception e){
            LOG.error("error cargarLocador",e);
        }        
        return retorno;
    }
    
    @RequestMapping(value = "/cargarSupervisoraEmpresa", method = RequestMethod.GET)
    public @ResponseBody List<SupervisoraEmpresaDTO> cargarSupervisoraEmpresa(Long idObligacionTipo, Long idProceso, Long idRubro, Long idDepartamento,
    		Long idProvincia, Long idDistrito, HttpSession session,HttpServletRequest request) {
        LOG.info("cargarSupervisoraEmpresa");
        List<SupervisoraEmpresaDTO> retorno=new ArrayList<SupervisoraEmpresaDTO>();
        try{
            if(idObligacionTipo!=null && idObligacionTipo==-1) {idObligacionTipo=null;}
            if(idProceso!=null && idProceso==-1) {idProceso=null;}
            if(idRubro!=null && idRubro==-1) {idRubro=null;}
            if(idDepartamento!=null && idDepartamento==-1) {idDepartamento=null;}
            if(idProvincia!=null && idProvincia==-1) {idProvincia=null;}
            if(idDistrito!=null && idDistrito==-1) {idDistrito=null;}

            SupervisoraEmpresaFilter filtro=new SupervisoraEmpresaFilter();                        
            filtro.setIdProceso(idProceso);
            filtro.setIdObligacionTipo(idObligacionTipo);
            filtro.setIdRubro(idRubro);
            filtro.setIdDepartamento(idDepartamento);
            filtro.setIdProvincia(idProvincia);
            filtro.setIdDistrito(idDistrito);       		
            filtro.setEstado(Constantes.ESTADO_ACTIVO);
            filtro.setIdUnidadOrganica((Long) request.getSession().getAttribute(Constantes.PERSONAL_UNIDAD_ORGANICA_DIVISION));
          
            retorno=supervisoraEmpresaServiceNeg.listarSupervisoraEmpresaConfFiltros(filtro);
        }catch(Exception e){
            LOG.error("error cargarSupervisoraEmpresa",e);
        }
        
        return retorno;
    } 
}
