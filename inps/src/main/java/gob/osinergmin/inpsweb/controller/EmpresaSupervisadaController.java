/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gob.osinergmin.inpsweb.service.business.EmpresaSupervisadaServiceNeg;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.ConstantesWeb;
import gob.osinergmin.mdicommon.domain.dto.DireccionEmpSupDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresaContactoDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresaSupDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.dto.busqueda.BusquedaDireccionxEmpSupervisada;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/empresaSupervisada")
public class EmpresaSupervisadaController {
    private static final Logger LOG = LoggerFactory.getLogger(EmpresaSupervisadaController.class);
    
    @Inject
    private EmpresaSupervisadaServiceNeg empresaSupervisadaServiceNeg;
    
    @SuppressWarnings("unused")
	@RequestMapping(value="/actualizarEmpresaSupervisada", method=RequestMethod.POST)
    public @ResponseBody Map<String, Object>  actualizarEmpresaSupervisada(EmpresaSupDTO empresa,Long ciu,HttpSession session,HttpServletRequest request ){
    	LOG.info("|| <------- Actualizar Empresa Supervisada ------------> ||");
    	LOG.info("ID Empresa Supervisada -->" + empresa.getIdEmpresaSupervisada());
    	LOG.info("Ruc Empresa Supervisada -->" + empresa.getRuc());
    	LOG.info("Razon Social Empresa Supervisada -->" + empresa.getRazonSocial());
    	LOG.info("Nombre Comercial Empresa Supervisada -->" + empresa.getNombreComercial());
    	LOG.info("Telefono Empresa Supervisada -->" + empresa.getTelefono());
    	LOG.info("CIIU Empresa Supervisada -->" + ciu);
    	Map<String, Object> salida = new HashMap<String, Object>();
    	EmpresaSupDTO retorno = new EmpresaSupDTO();
    	try {
			if(empresa!=null){
				UsuarioDTO usuario = new UsuarioDTO();
				usuario.setCodigo(Constantes.getUsuarioDTO(request).getCodigo());
				usuario.setTerminal(Constantes.getUsuarioDTO(request).getTerminal());
				MaestroColumnaDTO maestro = new MaestroColumnaDTO();
				maestro.setIdMaestroColumna(ciu);
				empresa.setCiiuPrincipal(maestro);
				retorno=empresaSupervisadaServiceNeg.editarEmpresaSupervisada(empresa,usuario);
				
				salida.put("resultado",ConstantesWeb.VV_EXITO);
			}else{
				salida.put("resultado",ConstantesWeb.VV_ADVERTENCIA);
				salida.put("mensaje", "Empresa supervisada no identificada");
			}
			
		} catch (Exception e) {
			LOG.error("Error actualizarEmpresaSupervisada",e);
			salida.put("resultado",ConstantesWeb.VV_ERROR);
			salida.put("mensaje", "Error al actualizar Empresa Supervisada");
		}
		return salida;    	
    }
    //Direcciones de Empresa Supervisada
    @RequestMapping(value = "/listarDireccionEmpresaSupervisada", method = RequestMethod.GET)
    public @ResponseBody
	Map<String, Object> listarDireccionEmpresaSupervisada(Long idEmpresaSupervisada, int rows, int page, HttpSession session,HttpServletRequest request) {
		LOG.info("Listar Direcciones por Empresa Supervisada");
        Map<String, Object> listaResultado = new HashMap<String, Object>();
        List<BusquedaDireccionxEmpSupervisada> listado = new ArrayList<BusquedaDireccionxEmpSupervisada>();
        try{
        	
            listado=empresaSupervisadaServiceNeg.listarDireccionEmpresaSupervisada(idEmpresaSupervisada);
            LOG.info("listado de direcciones" + listado);
            if(listado!=null){
            	int indiceInicial =-1;
            	int indiceFinal = -1;
            	Long contador = new Long(listado.size());
            	Long numeroFilas = (contador / rows);
	            if ((contador % rows) > 0) {
	                numeroFilas = numeroFilas + 1L;}
	            if(numeroFilas<page){page = numeroFilas.intValue();}
	            if(page == 0){rows = 0;}
            	indiceInicial = (page - 1) * rows;
	            indiceFinal = indiceInicial + rows;
	            List<BusquedaDireccionxEmpSupervisada> listaDirecciones = new ArrayList<BusquedaDireccionxEmpSupervisada>();
	            listaDirecciones = listado.subList(
	            		indiceInicial > listado.size() ? listado.size() : indiceInicial, indiceFinal > listado
	                    .size() ? listado.size()
	                    : indiceFinal); 
	            listaResultado.put("total", numeroFilas);
	            listaResultado.put("pagina", page);
	            listaResultado.put("registros", contador);
	            listaResultado.put("filas", listaDirecciones);
            }
        }catch(Exception ex){
        	LOG.error("error controller",ex);
        }
        
        return listaResultado;
    }
    @RequestMapping(value="/obtenerDireccionEmpresaSupervisada", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> obtenerDireccionEmpresaSupervisada(Long idDireccionEmpresaSupervisada, HttpSession session,HttpServletRequest request){
    	LOG.info("Obtener Unidad Supervisada");
    	Map<String, Object> salida = new HashMap<String, Object>();
    	BusquedaDireccionxEmpSupervisada direccion = new BusquedaDireccionxEmpSupervisada();
    	try {
    		if(idDireccionEmpresaSupervisada!=null){

    		LOG.info(" Id direccion : " + idDireccionEmpresaSupervisada);
    		direccion=empresaSupervisadaServiceNeg.direccionEmpresaSupervisada(idDireccionEmpresaSupervisada);
    		salida.put("direccion", direccion);
    		salida.put("resultado",ConstantesWeb.VV_EXITO);
			}else{
			salida.put("resultado",ConstantesWeb.VV_ADVERTENCIA);
			}
			
		} catch (Exception e) {
			LOG.error("error controller",e);
			salida.put("resultado",ConstantesWeb.VV_ERROR);
		}
		return salida;
    }
    
    @RequestMapping(value="/eliminarDireccionEmpresaSupervisada", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> eliminarDireccionEmpresaSupervisada(Long idDireccionEmpresaSupervisada, HttpSession session,HttpServletRequest request){
    	LOG.info("Eliminar Unidad Supervisada");
    	Map<String, Object> salida = new HashMap<String, Object>();
    	DireccionEmpSupDTO direccionEmpresa = new DireccionEmpSupDTO();
    	try {
    		if(idDireccionEmpresaSupervisada!=null){
    			UsuarioDTO usuario = new UsuarioDTO();
				usuario.setCodigo(Constantes.getUsuarioDTO(request).getCodigo());
				usuario.setTerminal(Constantes.getUsuarioDTO(request).getTerminal());
    			DireccionEmpSupDTO direccion = new DireccionEmpSupDTO();
    			direccion.setIdDireccionEmpresaSup(idDireccionEmpresaSupervisada);    			
    			direccionEmpresa = empresaSupervisadaServiceNeg.eliminardireccionEmpresaSupervisada(direccion,usuario);
    			
    		salida.put("resultado",ConstantesWeb.VV_EXITO);
			}else{
			salida.put("resultado",ConstantesWeb.VV_ADVERTENCIA);
			}
			
		} catch (Exception e) {
			LOG.error("error controller",e);
			salida.put("resultado",ConstantesWeb.VV_ERROR);
		}
		return salida;
    }
    
    @RequestMapping(value="/guardarDireccionEmpresaSupervisada", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> guardarDireccionEmpresaSupervisada(DireccionEmpSupDTO direccionEmpresa, HttpSession session,HttpServletRequest request){
    	LOG.info("Eliminar Unidad Supervisada");
    	Map<String, Object> salida = new HashMap<String, Object>();
    	DireccionEmpSupDTO direccionEmpresaSupervisada = new DireccionEmpSupDTO();
    	try {
    		if(direccionEmpresa!=null){
    			UsuarioDTO usuario = new UsuarioDTO();
				usuario.setCodigo(Constantes.getUsuarioDTO(request).getCodigo());
				usuario.setTerminal(Constantes.getUsuarioDTO(request).getTerminal());
    			direccionEmpresaSupervisada = empresaSupervisadaServiceNeg.guardarDireccionEmpresaSupervisada(direccionEmpresa,usuario);
	    		salida.put("resultado",ConstantesWeb.VV_EXITO);
			}else{
				salida.put("resultado",ConstantesWeb.VV_ADVERTENCIA);
			}
			
		} catch (Exception e) {
			LOG.error("error controller",e);
			salida.put("resultado",ConstantesWeb.VV_ERROR);
		}
		return salida;
    }
    
    @RequestMapping(value="/actualizarDireccionEmpresaSupervisada", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> actualizarDireccionEmpresaSupervisada(DireccionEmpSupDTO direccionEmpresa, HttpSession session,HttpServletRequest request){
    	LOG.info("Eliminar Unidad Supervisada");
    	Map<String, Object> salida = new HashMap<String, Object>();
    	DireccionEmpSupDTO direccionEmpresaSupervisada = new DireccionEmpSupDTO();
    	try {
    		if(direccionEmpresa.getIdDireccionEmpresaSup()!=null){
    			UsuarioDTO usuario = new UsuarioDTO();
				usuario.setCodigo(Constantes.getUsuarioDTO(request).getCodigo());
				usuario.setTerminal(Constantes.getUsuarioDTO(request).getTerminal());
    			direccionEmpresaSupervisada = empresaSupervisadaServiceNeg.actualizarDireccionEmpresaSupervisada(direccionEmpresa,usuario);
	    		salida.put("resultado",ConstantesWeb.VV_EXITO);
			}else{
				salida.put("resultado",ConstantesWeb.VV_ADVERTENCIA);
			}
			
		} catch (Exception e) {
			LOG.error("error controller",e);
			salida.put("resultado",ConstantesWeb.VV_ERROR);
		}
		return salida;
    }
    //Empresas Contacto de Empresa Supervisada
    @RequestMapping(value = "/listarEmpresaContactoEmpresaSupervisada", method = RequestMethod.GET)
    public @ResponseBody
	Map<String, Object> listarEmpresaContactoEmpresaSupervisada(Long idEmpresaSupervisada, int rows, int page, HttpSession session,HttpServletRequest request) {
		LOG.info("Listar Direcciones por Empresa Supervisada");
        Map<String, Object> listaResultado = new HashMap<String, Object>();
        List<EmpresaContactoDTO> listado = new ArrayList<EmpresaContactoDTO>();
        try{
        	
            listado=empresaSupervisadaServiceNeg.listarEmpresaContactoEmpresaSupervisada(idEmpresaSupervisada);
            LOG.info("listado de direcciones" + listado);
            if(listado!=null){
            	int indiceInicial = -1;
            	int indiceFinal = -1;
            	Long contador = new Long(listado.size());
            	Long numeroFilas = (contador / rows);
	            if ((contador % rows) > 0) { numeroFilas = numeroFilas + 1L;}
	            if(numeroFilas<page){page = numeroFilas.intValue();}
	            if(page == 0){rows = 0;}
            	indiceInicial = (page - 1) * rows;
	            indiceFinal = indiceInicial + rows;
	            List<EmpresaContactoDTO> listaEmpresasContacto = new ArrayList<EmpresaContactoDTO>();
	            listaEmpresasContacto = listado.subList(indiceInicial > listado.size() ? listado.size() : indiceInicial, indiceFinal > listado.size() ? listado.size(): indiceFinal);
	            listaResultado.put("total", numeroFilas);
	            listaResultado.put("pagina", page);
	            listaResultado.put("registros", contador);
	            listaResultado.put("filas", listaEmpresasContacto);
            }
        }catch(Exception ex){
        	LOG.error("error controller",ex);
        }
        
        return listaResultado;
    }
    @RequestMapping(value="/obtenerEmpresaContactoEmpresaSupervisada", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> obtenerEmpresaContactoEmpresaSupervisada(Long idEmpresaContacto, HttpSession session,HttpServletRequest request){
    	LOG.info("Obtener Empresa Contacto ");
    	Map<String, Object> salida = new HashMap<String, Object>();
    	EmpresaContactoDTO empresaContacto = new EmpresaContactoDTO();
    	try {
    		if(idEmpresaContacto!=null){

    		LOG.info(" Id Empresa Contacto : " + idEmpresaContacto);
    		empresaContacto=empresaSupervisadaServiceNeg.obtenerEmpresaContacto(idEmpresaContacto);
    		LOG.info("Id Empresa Contacto Retorno : "+empresaContacto.getIdEmpresaContacto());
    		salida.put("empresaContacto", empresaContacto);
    		salida.put("resultado",ConstantesWeb.VV_EXITO);
			}else{
			salida.put("resultado",ConstantesWeb.VV_ADVERTENCIA);
			}
			
		} catch (Exception e) {
			LOG.error("error controller",e);
			salida.put("resultado",ConstantesWeb.VV_ERROR);
		}
		return salida;
    }
    
    @RequestMapping(value="/actualizarEmpresaContactoEmpresaSupervisada", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> actualizarEmpresaContactoEmpresaSupervisada(EmpresaContactoDTO empresaContacto, HttpSession session,HttpServletRequest request){
    	LOG.info("Actualizar Empresa Contacto");
    	Map<String, Object> salida = new HashMap<String, Object>();
    	EmpresaContactoDTO retornoEmpresaContacto = new EmpresaContactoDTO();
    	try {
    		if(empresaContacto.getIdEmpresaContacto()!=null){
    			UsuarioDTO usuario = new UsuarioDTO();
				usuario.setCodigo(Constantes.getUsuarioDTO(request).getCodigo());
				usuario.setTerminal(Constantes.getUsuarioDTO(request).getTerminal());
				empresaContacto.setPrincipal("1");
				retornoEmpresaContacto = empresaSupervisadaServiceNeg.actualizarEmpresaContactoEmpresaSupervisada(empresaContacto,usuario);
	    		salida.put("resultado",ConstantesWeb.VV_EXITO);
			}else{
				salida.put("resultado",ConstantesWeb.VV_ADVERTENCIA);
			}
			
		} catch (Exception e) {
			LOG.error("error controller",e);
			salida.put("resultado",ConstantesWeb.VV_ERROR);
		}
		return salida;
    }
    
    @RequestMapping(value="/eliminarEmpresaContactoEmpresaSupervisada", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> eliminarEmpresaContactoEmpresaSupervisada(Long idEmpresaContacto, HttpSession session,HttpServletRequest request){
    	LOG.info("Eliminar Empresa Contacto");
    	Map<String, Object> salida = new HashMap<String, Object>();
    	EmpresaContactoDTO retornoEmpresaContacto = new EmpresaContactoDTO();
    	try {
    		if(idEmpresaContacto!=null){
    			UsuarioDTO usuario = new UsuarioDTO();
				usuario.setCodigo(Constantes.getUsuarioDTO(request).getCodigo());
				usuario.setTerminal(Constantes.getUsuarioDTO(request).getTerminal());
				EmpresaContactoDTO empresaContacto = new EmpresaContactoDTO();
				empresaContacto.setIdEmpresaContacto(idEmpresaContacto);    			
    			retornoEmpresaContacto = empresaSupervisadaServiceNeg.eliminarEmpresaContactoEmpresaSupervisada(empresaContacto,usuario);
    			
    		salida.put("resultado",ConstantesWeb.VV_EXITO);
			}else{
			salida.put("resultado",ConstantesWeb.VV_ADVERTENCIA);
			}
			
		} catch (Exception e) {
			LOG.error("error controller",e);
			salida.put("resultado",ConstantesWeb.VV_ERROR);
		}
		return salida;
    }
    
    @RequestMapping(value="/guardarEmpresaContactoEmpresaSupervisada", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> guardarEmpresaContactoEmpresaSupervisada(EmpresaContactoDTO empresaContacto, HttpSession session,HttpServletRequest request){
    	LOG.info("Guardar Empresa Contacto de la Empresa Supervisada");
    	Map<String, Object> salida = new HashMap<String, Object>();
    	EmpresaContactoDTO retornoEmpresaContacto = new EmpresaContactoDTO();
    	try {
    		if(empresaContacto!=null){
    			UsuarioDTO usuario = new UsuarioDTO();
				usuario.setCodigo(Constantes.getUsuarioDTO(request).getCodigo());
				usuario.setTerminal(Constantes.getUsuarioDTO(request).getTerminal());
				empresaContacto.setPrincipal("1");
				retornoEmpresaContacto = empresaSupervisadaServiceNeg.guardarEmpresaContactoEmpresaSupervisada(empresaContacto,usuario);
	    		salida.put("resultado",ConstantesWeb.VV_EXITO);
			}else{
				salida.put("resultado",ConstantesWeb.VV_ADVERTENCIA);
			}
			
		} catch (Exception e) {
			LOG.error("error controller",e);
			salida.put("resultado",ConstantesWeb.VV_ERROR);
		}
		return salida;
    }   
    
    
}
