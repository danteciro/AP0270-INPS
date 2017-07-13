/**
* Resumen		
* Objeto		: CorreoDAO.java
* Descripción		: Clase que proporciona una interfaz para la implementación de métodos en CorreoDAOImpl
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     12/05/2016      Luis García Reyna               Correo de Alerta a Empresa Supervisora cuando se le asigne Orden de Servicio.
*
*/

package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.inpsweb.service.exception.CorreoException;
import gob.osinergmin.mdicommon.domain.dto.CorreoDTO;
import gob.osinergmin.mdicommon.domain.ui.CorreoFilter;
import java.util.List;

/* OSINE_SFS-480 - RSIS 12 - Inicio */
public interface CorreoDAO {
    public List<CorreoDTO> findByFilter(CorreoFilter filtro) throws CorreoException;
}
/* OSINE_SFS-480 - RSIS 12 - Fin */
