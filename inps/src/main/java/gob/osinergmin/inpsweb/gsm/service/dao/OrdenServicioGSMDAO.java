/**
* Resumen		
* Objeto			: OrdenServicioGSMDAO.java
* Descripción		: Clase interfaz para la implementación de métodos en OrdenServicioGSMDAOImpl, gerencia GSM.
* Fecha de Creación	: 27/10/2016.
* PR de Creación	: OSINE_SFS-1344.
* Autor				: GMD.
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                              Descripción
* ---------------------------------------------------------------------------------------------------
*
*/

package gob.osinergmin.inpsweb.gsm.service.dao;

import gob.osinergmin.mdicommon.domain.dto.DmTitularMineroDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.inpsweb.service.exception.OrdenServicioException;
import gob.osinergmin.mdicommon.domain.dto.OrdenServicioDTO;
import gob.osinergmin.mdicommon.domain.ui.DmTitularMineroFilter;
import gob.osinergmin.mdicommon.domain.ui.OrdenServicioFilter;

import java.util.Date;
import java.util.List;

public interface OrdenServicioGSMDAO {

	public List<DmTitularMineroDTO> listarTitularMinero(DmTitularMineroFilter filtro);
	
}
