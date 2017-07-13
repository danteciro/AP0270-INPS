/**
* Resumen		
* Objeto		: ActividadDAO.java
* Descripción		: Clase que proporciona una interfaz para la implementación de métodos en ActividadDAOImpl
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     09/05/2016      Mario Dioses Fernandez      Registrar en BD campo FECHA_INICIO_PLAZO y FECHA_FIN_PLAZO luego de CONCLUIR_OS, considerando Plazo_Descargo por Rubro (MDI_ACTIVIDAD)
*
*/

package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.mdicommon.domain.dto.ActividadDTO;
import gob.osinergmin.mdicommon.domain.ui.ActividadFilter;

import java.util.List;

/**
 *
 * @author jpiro
 */
public interface ActividadDAO {
	public List<ActividadDTO> find();
        /* OSINE_SFS-480 - RSIS 17 - Inicio */
	public List<ActividadDTO> findBy(ActividadFilter filtro);
        /* OSINE_SFS-480 - RSIS 17 - Fin */
        
}