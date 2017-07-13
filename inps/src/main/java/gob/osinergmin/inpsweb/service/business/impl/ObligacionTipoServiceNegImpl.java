/**
* Resumen		
* Objeto		: ObligacionTipoServiceNegImpl.java
* Descripción		: Clase de la capa de negocio que contiene la implementación de los métodos declarados en la clase interfaz ObligacionTipoServiceNeg
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     23/05/2016      Giancarlo Villanueva Andrade    Crear componente de selección de "subtipo de supervisión".Relacionar y adecuar el subtipo de supervisión, el cual deberá depender del tipo de supervisión seleccionado.
*
*/

package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.service.business.ObligacionTipoServiceNeg;
import gob.osinergmin.inpsweb.service.dao.ObligacionTipoDAO;
import gob.osinergmin.mdicommon.domain.dto.ObligacionTipoDTO;
import gob.osinergmin.mdicommon.domain.ui.UnidadOrganicaFilter;

import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author jpiro
 */
@Service("obligacionTipoServiceNeg")
public class ObligacionTipoServiceNegImpl implements ObligacionTipoServiceNeg {
    private static final Logger LOG = LoggerFactory.getLogger(ObligacionTipoServiceNegImpl.class);    
    @Inject
    private ObligacionTipoDAO obligacionTipoDAO;
    
    @Override
    public List<ObligacionTipoDTO> listarObligacionTipo(){
        LOG.info("Neg listarProceso");
        List<ObligacionTipoDTO> retorno=null;
        try{
            retorno = obligacionTipoDAO.listarObligacionTipo();
        }catch(Exception ex){
            LOG.error("error listarObligacionTipo",ex);
        }
        return retorno;
    }
        /* OSINE_SFS-480 - RSIS 26 - Inicio */
	@Override
	public List<ObligacionTipoDTO> listarObligacionTipoxSeleccionMuestral() {
		LOG.info("Neg listarProcesoxMuestra");
        List<ObligacionTipoDTO> retorno=null;
        try{
            retorno = obligacionTipoDAO.listarObligacionTipoxSeleccionMuestral();
        }catch(Exception ex){
            LOG.error("error listarObligacionTipoxSeleccionMuestral",ex);
        }
        return retorno;
	}
	/* OSINE_SFS-480 - RSIS 26 - Fin */
	@Override
	public List<ObligacionTipoDTO> listarObligacionTipoByDivision(UnidadOrganicaFilter unidadOrganicaFilter) {
		// TODO Auto-generated method stub
		LOG.info("Neg listar Tipos de Supervisión por División");
        List<ObligacionTipoDTO> retorno=null;
        try{
            retorno = obligacionTipoDAO.listarObligacionTipoxDivision(unidadOrganicaFilter);
        }catch(Exception ex){
            LOG.error("error listar Tipos de Supervisión por División",ex);
        }
		return retorno;
	}
	
}