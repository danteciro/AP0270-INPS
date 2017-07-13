/**
* Resumen		
* Objeto		: ObligacionSubTipoServiceNegImpl.java
* Descripción		: Clase de la capa de negocio que contiene la implementación de los métodos declarados en la clase interfaz ObligacionSubTipoServiceNeg
* Fecha de Creación	: 23/05/2016
* PR de Creación	: OSINE_SFS-480
* Autor			: Giancarlo Villanueva Andrade
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     23/05/2016      Giancarlo Villanueva Andrade    Crear componente de selección de "subtipo de supervisión".Relacionar y adecuar el subtipo de supervisión, el cual deberá depender del tipo de supervisión seleccionado.
*
*/

package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.service.business.ObligacionSubTipoServiceNeg;
import gob.osinergmin.inpsweb.service.dao.ObligacionSubTipoDAO;
import gob.osinergmin.mdicommon.domain.dto.ObligacionSubTipoDTO;
import gob.osinergmin.mdicommon.domain.ui.ObligacionSubTipoFilter;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/* OSINE_SFS-480 - RSIS 26 - Inicio */
@Service("ObligacionSubTipoServiceNeg")
public class ObligacionSubTipoServiceNegImpl implements  ObligacionSubTipoServiceNeg{
	private static final Logger LOG = LoggerFactory.getLogger(ObligacionSubTipoServiceNegImpl.class);
	
	@Inject 
	private ObligacionSubTipoDAO obligacionSubTipoDAO;
	
	@Override
	@Transactional(readOnly=true)
	public List<ObligacionSubTipoDTO> listarObligacionesSubTipo(ObligacionSubTipoFilter filtro) {
            LOG.info("listarObligacionesSubTipo");
            List<ObligacionSubTipoDTO> retorno=null;
            try{	        	
                retorno = obligacionSubTipoDAO.listaObligacionSubTipo(filtro);
            }catch(Exception ex){
                LOG.error("Error en listarObligacionesSubTipo",ex);
            }
            return retorno;
	}
}
/* OSINE_SFS-480 - RSIS 26 - Fin */
