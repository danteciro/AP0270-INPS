/**
*
* Resumen		
* Objeto			: DetalleSupervisionDAO.java
* Descripción		: DetalleSupervisionDAO
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor				: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-791     07/10/2016       Mario Dioses Fernandez           Crear la funcionalidad "verificar levantamientos" que permita verificar el tipo de asignación para la orden de levantamiento DSR-CRITICIDAD.
*/

package gob.osinergmin.inpsweb.service.dao;
import gob.osinergmin.inpsweb.service.exception.DetalleSupervisionException;
import gob.osinergmin.inpsweb.service.exception.SupervisionException;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.DetalleSupervisionFilter;
import java.util.List;

public interface DetalleSupervisionDAO {

	public DetalleSupervisionDTO registrar(DetalleSupervisionDTO detalleSupervisionDTO,UsuarioDTO usuarioDTO) throws SupervisionException;
	public List<DetalleSupervisionDTO> find(DetalleSupervisionFilter filtro) throws SupervisionException;
	public DetalleSupervisionDTO actualizar(DetalleSupervisionDTO detalleSupervisionDTO, UsuarioDTO usuarioDTO) throws SupervisionException;
	public long cantidadDetalleSupervision(DetalleSupervisionFilter filtro) throws SupervisionException;
    /* OSINE_SFS-791 - RSIS 16 - Inicio */
    public DetalleSupervisionDTO registroComentarioDetSupervision(DetalleSupervisionDTO detalleSupervisionDTO, UsuarioDTO usuarioDTO) throws SupervisionException;
    /* OSINE_SFS-791 - RSIS 16 - Fin */  
    public DetalleSupervisionDTO ActualizarMedidaSeguridadDetalleSupervision(DetalleSupervisionDTO detalleSupervisionDTO, UsuarioDTO usuarioDTO) throws DetalleSupervisionException;
    /* OSINE_SFS-791 - RSIS 33 - Inicio */
    public List<DetalleSupervisionDTO> listarDetSupInfLevantamiento(DetalleSupervisionFilter filtro) throws DetalleSupervisionException;
    /* OSINE_SFS-791 - RSIS 33 - Fin */
    /* OSINE_SFS-791 - RSIS  40 - Inicio */
    public DetalleSupervisionDTO ActualizarDetalleSupervisionSubsanado(DetalleSupervisionDTO detalleSupervisionDTO, UsuarioDTO usuarioDTO) throws DetalleSupervisionException;
    /* OSINE_SFS-791 - RSIS 40 - Fin */
}
