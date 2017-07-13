/**
* Resumen		
* Objeto		: DestinatarioCorreoServiceNeg.java
* Descripción		: Clase que proporciona una interfaz para la implementación de métodos
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-791
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
* 
*/
package gob.osinergmin.inpsweb.service.business;

import gob.osinergmin.inpsweb.service.exception.DestinatarioCorreoException;
import gob.osinergmin.mdicommon.domain.dto.DestinatarioCorreoDTO;
import gob.osinergmin.mdicommon.domain.ui.DestinatarioCorreoFilter;
import java.util.List;

/**
 *
 * @author zchaupis
 */
public interface DestinatarioCorreoServiceNeg {
    List<DestinatarioCorreoDTO> getDestinatarioCorreobyUbigeo(DestinatarioCorreoFilter filtro) throws DestinatarioCorreoException;
}
