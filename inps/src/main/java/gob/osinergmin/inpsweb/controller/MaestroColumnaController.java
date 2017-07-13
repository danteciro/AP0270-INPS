/**
* Resumen		
* Objeto		: MaestroColumnaController.java
* Descripción		: Controla el flujo de datos del objeto MaestroColumna
* Fecha de Creación	: 19/05/2016
* PR de Creación	: OSINE_SFS-480
* Autor			: Luis García Reyna
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                 Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     19/05/2016      Luis García Reyna        Adaptar y preparar la relación entre "petición" y "motivo" en la tabla MDI_MAESTRO_COLUMNA
* 
*/ 

package gob.osinergmin.inpsweb.controller;

import gob.osinergmin.inpsweb.service.business.MaestroColumnaServiceNeg;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/* OSINE_SFS-480 - RSIS 39 - Inicio */
@Controller
@RequestMapping("/maestroColumna")
public class MaestroColumnaController {
    private static final Logger LOG = LoggerFactory.getLogger(MaestroColumnaController.class);
    
    @Inject
    private MaestroColumnaServiceNeg maestroColumnaServiceNeg;
    
    @RequestMapping(value = "/obtenerMotivos", method = RequestMethod.GET)
    public @ResponseBody List<MaestroColumnaDTO> obtenerMotivos (String codigo){
        List<MaestroColumnaDTO> retorno=new ArrayList<MaestroColumnaDTO>();
        try{
            retorno=maestroColumnaServiceNeg.findByDominioAplicacion(codigo, Constantes.APLICACION_INPS);
        }catch(Exception ex){
            LOG.error("Error obtenerMotivos",ex);
        }
        return retorno;
    }
}
/* OSINE_SFS-480 - RSIS 39 - Fin */