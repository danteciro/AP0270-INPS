/**
* Resumen		
* Objeto		: DestinatarioCorreoDAO.java
* Descripción		: Clase que proporciona una interfaz para la implementación de métodos
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-791
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_791-RSIS19 | 06/09/2016 | Zosimo Chaupis Santur | Correo de Notificacion al Supervisor Regional para supervisión de una orden de supervisión DSR-CRITICIDAD
 
*/
package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.inpsweb.service.exception.DestinatarioCorreoException;
import gob.osinergmin.mdicommon.domain.dto.DestinatarioCorreoDTO;
import gob.osinergmin.mdicommon.domain.ui.DestinatarioCorreoFilter;
import java.util.List;

/**
 *
 * @author zchaupis
 */
public interface DestinatarioCorreoDAO {
    List<DestinatarioCorreoDTO> getDestinatarioCorreobyUbigeo(DestinatarioCorreoFilter filtro) throws DestinatarioCorreoException;
}
