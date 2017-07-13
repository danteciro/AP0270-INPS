/**
* Resumen
* Objeto		: ObligacionSubTipoServiceNeg.java
* Descripción		: Clase Interfaz de la capa de negocio que contiene la declaración de métodos a implementarse en el ObligacionSubTipoServiceNegImpl
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                              Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     24/05/2016      Giancarlo Villanueva Andrade        Crear componente de selección de "subtipo de supervisión".Relacionar y adecuar el subtipo de supervisión, el cual deberá depender del tipo de supervisión seleccionado.
* 
*/

package gob.osinergmin.inpsweb.service.business;

import gob.osinergmin.mdicommon.domain.dto.ObligacionTipoDTO;
import gob.osinergmin.mdicommon.domain.ui.UnidadOrganicaFilter;

import java.util.List;

/**
 *
 * @author jpiro
 */
public interface ObligacionTipoServiceNeg {
    public List<ObligacionTipoDTO> listarObligacionTipo();
    /*OSINE_SFS-480 - RSIS 26 - Inicio */
    public List<ObligacionTipoDTO> listarObligacionTipoxSeleccionMuestral();
    /*OSINE_SFS-480 - RSIS 26 - Fin */
    /*OSINE_SFS-1344 - Inicio */
	public List<ObligacionTipoDTO> listarObligacionTipoByDivision(UnidadOrganicaFilter unidadOrganicaFilter);
	/*OSINE_SFS-1344 - Fin */
}
