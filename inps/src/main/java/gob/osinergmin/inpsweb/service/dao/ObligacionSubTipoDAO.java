/**
* Resumen		
* Objeto		: ObligacionSubTipoDAO.java
* Descripción		: Clase interfaz para la implementación de métodos en ObligacionSubTipoDAOImpl
* Fecha de Creación	: 23/05/2016
* PR de Creación	: OSINE_SFS-480
* Autor			: Giancarlo Villanueva Andrade
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                              Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     23/05/2016      Giancarlo Villanueva Andrade        Crear componente de selección de "subtipo de supervisión".Relacionar y adecuar el subtipo de supervisión, el cual deberá depender del tipo de supervisión seleccionado.
*
*/

package gob.osinergmin.inpsweb.service.dao;
import gob.osinergmin.mdicommon.domain.dto.ObligacionSubTipoDTO;
import gob.osinergmin.mdicommon.domain.ui.ObligacionSubTipoFilter;

import java.util.List;
/* OSINE_SFS-480 - RSIS 26 - Inicio */
public interface ObligacionSubTipoDAO {
	List<ObligacionSubTipoDTO> listaObligacionSubTipo(ObligacionSubTipoFilter filtro);
}
/* OSINE_SFS-480 - RSIS 26 - Fin */