/**
* Resumen		
* Objeto			: ExpedienteTareaServiceNeg.java
* Descripción		: Clase ServiceNeg ExpedienteTareaServiceNeg
* Fecha de Creación	: 23/10/2016
* PR de Creación	: OSINE_SFS-791
* Autor				: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------				
* OSINE_SFS-791     23/10/2016      Mario Dioses Fernandez          Crear la tarea automática que notifique vía correo que se debe elaborar el oficio por obligaciones incumplidas sin subsanar
* OSINE_SFS-791     23/10/2016      Mario Dioses Fernandez          Crear la tarea automática que cancele el registro de hidrocarburos
* 
*/ 
package gob.osinergmin.inpsweb.service.business;
import java.util.List;
import gob.osinergmin.inpsweb.service.exception.ExpedienteTareaException;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteTareaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.ExpedienteTareaFilter;

public interface ExpedienteTareaServiceNeg {
	public List<ExpedienteTareaDTO> find(ExpedienteTareaFilter filtro) throws ExpedienteTareaException;
	public ExpedienteTareaDTO registrarExpedienteTarea(ExpedienteTareaDTO ExpedienteTarea, UsuarioDTO UsuarioDTO) throws ExpedienteTareaException;
	public ExpedienteTareaDTO actualizarExpedienteTarea(ExpedienteTareaDTO ExpedienteTarea, UsuarioDTO UsuarioDTO) throws ExpedienteTareaException;
}
