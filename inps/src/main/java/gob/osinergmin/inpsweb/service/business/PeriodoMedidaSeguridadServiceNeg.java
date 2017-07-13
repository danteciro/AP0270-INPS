/**
* Resumen		
* Objeto			: PeriodoMedidaSeguridadServiceNeg.java
* Descripción		: Clase ServiceNeg PeriodoMedidaSeguridadServiceNeg
* Fecha de Creación	: 12/10/2016
* PR de Creación	: OSINE_SFS-791
* Autor				: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------				
* OSINE_SFS-791     12/10/2016      Mario Dioses Fernandez          Crear la tarea automática que cancele el registro de hidrocarburos
*/ 

package gob.osinergmin.inpsweb.service.business;
import gob.osinergmin.inpsweb.service.exception.PeriodoMedidaSeguridadException;
import gob.osinergmin.mdicommon.domain.dto.PeriodoMedidaSeguridadDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.PeriodoMedidaSeguridadFilter;
import java.util.List;

/**
 *
 * @author zchaupis
 */
public interface PeriodoMedidaSeguridadServiceNeg {
    public List<PeriodoMedidaSeguridadDTO> getListPeriodoMedidaSeguridad(PeriodoMedidaSeguridadFilter filtro)throws PeriodoMedidaSeguridadException;
    public PeriodoMedidaSeguridadDTO registrarPeriodoMedidaSeguridad(PeriodoMedidaSeguridadDTO periodoMedidaSeguridadDTO,UsuarioDTO usuarioDTO)throws PeriodoMedidaSeguridadException;    
    /* OSINE_SFS-791 - RSIS 47- Inicio */
    public PeriodoMedidaSeguridadDTO actualizarPeriodoMedidaSeguridad(PeriodoMedidaSeguridadDTO periodoMedidaSeguridad, UsuarioDTO UsuarioDTO) throws PeriodoMedidaSeguridadException;
    /* OSINE_SFS-791 - RSIS 47- Fin */
}
