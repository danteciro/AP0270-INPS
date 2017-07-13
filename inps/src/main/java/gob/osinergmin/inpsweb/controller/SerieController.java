/**
* Resumen		
* Objeto		: SerieController.java
* Descripción		: Controla el flujo de datos del objeto Serie
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     24/05/2016      Giancarlo Villanueva Andrade    Crear componente de selección de "subtipo de supervisión".Relacionar y adecuar el subtipo de supervisión, el cual deberá depender del tipo de supervisión seleccionado.
* 
*/

package gob.osinergmin.inpsweb.controller;

import gob.osinergmin.inpsweb.service.business.ObligacionSubTipoServiceNeg;
import gob.osinergmin.inpsweb.service.business.SerieServiceNeg;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.EtapaDTO;
import gob.osinergmin.mdicommon.domain.dto.ObligacionSubTipoDTO;
import gob.osinergmin.mdicommon.domain.dto.TramiteDTO;
import gob.osinergmin.mdicommon.domain.ui.ObligacionSubTipoFilter;


import java.util.ArrayList;
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
@RequestMapping("/serie")
public class SerieController {
    private static final Logger LOG = LoggerFactory.getLogger(SerieController.class);
    
    @Inject
    SerieServiceNeg serieServiceNeg;
    @Inject
    ObligacionSubTipoServiceNeg obligacionSubTipoServiceNeg;
    
    @RequestMapping(value="/cargarEtapa",method= RequestMethod.GET)
    public @ResponseBody List<EtapaDTO> cargarEtapa(Long idProceso,HttpSession session,HttpServletRequest request){    	
        List<EtapaDTO> retorno=new ArrayList<EtapaDTO>();
        try{
            retorno=serieServiceNeg.listarEtapa(idProceso);
        }catch(Exception ex){
            LOG.error("Error cargarEtapa",ex);
        }
        return retorno;
    }
    
    @RequestMapping(value="/cargarTramite",method= RequestMethod.GET)
    public @ResponseBody List<TramiteDTO> cargarTramite(Long idEtapa,HttpSession session,HttpServletRequest request){
        List<TramiteDTO> retorno=new ArrayList<TramiteDTO>();
        try{
            retorno=serieServiceNeg.listarTramite(idEtapa);
        }catch(Exception ex){
            LOG.error("Error cargarTramite",ex);
        }
        return retorno;
    }
    /* OSINE_SFS-480 - RSIS 26 - Inicio */  
    @RequestMapping(value = "/listarObligacionSubTipo", method = RequestMethod.GET)
    public @ResponseBody
    List<ObligacionSubTipoDTO> listarObligacionSubTipo(Long idObligacionTipo) {
        List<ObligacionSubTipoDTO> listObligacionSubTipo = new ArrayList<ObligacionSubTipoDTO>();
        try{
        	ObligacionSubTipoFilter filtro = new ObligacionSubTipoFilter();
        	filtro.setIdObligacionTipo(idObligacionTipo);
        	filtro.setEstado(Constantes.ESTADO_ACTIVO);
        	filtro.setIdentificadorSeleccion(Constantes.IDENTIFICADOR_SELECCION_MUESTRAL);
        	listObligacionSubTipo=obligacionSubTipoServiceNeg.listarObligacionesSubTipo(filtro);
        }catch(Exception e){
            LOG.info("error al procesar listado de Obligaciones Sub Tipo " +e);
        }
        return listObligacionSubTipo;
    }
    /* OSINE_SFS-480 - RSIS 26 - Fin */  
}
