/**
* Resumen		
* Objeto			: ModLevantamientoServiceNeg.java
* Descripción		: Clase Service ModLevantamientoServiceNeg
* Fecha de Creación	: 31/10/2016
* PR de Creación	: OSINE_SFS-791
* Autor				: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------				
* OSINE_SFS-791     31/10/2016      Mario Dioses Fernandez          Crear una funcionalidad de bandeja de expedientes con infracciones de supervisión DSR-CRITICIDAD para uso del Agente
* OSINE_SFS-791     31/10/2016      Mario Dioses Fernandez          Crear una funcionalidad que permita al Agente registrar el levantamiento de infracciones para expedientes de supervisión DSR-CRITICIDAD.
* OSINE_SFS-791     31/10/2016      Mario Dioses Fernandez          Crear una funcionalidad que permita al Agente consultar el levantamiento de infracciones para expedientes de supervisión DSR-CRITICIDAD.
*/ 
package gob.osinergmin.inpsweb.levantamiento.service.business;
import java.util.List;
import gob.osinergmin.inpsweb.service.exception.LevantamientoException;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.UbigeoDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;

public interface ModLevantamientoServiceNeg {
	public void enviarLevantamiento(ExpedienteDTO expediente, List<DetalleSupervisionDTO> detalleSupervisionList, SupervisionDTO supervision, UbigeoDTO ubigeo, UsuarioDTO usuario) throws LevantamientoException;
}
