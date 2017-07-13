/**
* Resumen		
* Objeto		: ObligacionSubTipoDAO.java
* Descripción		: Clase interfaz para la implementación de métodos en ObligacionSubTipoDAOImpl
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                              Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     23/05/2016      Giancarlo Villanueva Andrade        Crear componente de selección de "subtipo de supervisión".Relacionar y adecuar el subtipo de supervisión, el cual deberá depender del tipo de supervisión seleccionado.
*
*/

package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.inpsweb.service.exception.ObligacionTipoException;
import gob.osinergmin.mdicommon.domain.dto.ObligacionTipoDTO;
import gob.osinergmin.mdicommon.domain.ui.UnidadOrganicaFilter;

import java.util.List;

/**
 *
 * @author jpiro
 */
public interface ObligacionTipoDAO {
    public List<ObligacionTipoDTO> listarObligacionTipo() throws ObligacionTipoException;
    /*OSINE_SFS-480 - RSIS 26 - Inicio*/
    public List<ObligacionTipoDTO> listarObligacionTipoxSeleccionMuestral() throws ObligacionTipoException;
    /*OSINE_SFS-480 - RSIS 26 - Fin*/
    /*OSINE_SFS-1344 - Inicio*/
	public List<ObligacionTipoDTO> listarObligacionTipoxDivision(UnidadOrganicaFilter unidadOrganicaFilter) throws ObligacionTipoException;
	/*OSINE_SFS-1344 - Fin*/
}
