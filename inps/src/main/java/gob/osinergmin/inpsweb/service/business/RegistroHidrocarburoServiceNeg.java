/**
* Resumen		
* Objeto			: RegistroHidrocarburoServiceNeg.java
* Descripción		: Clase ServiceNeg RegistroHidrocarburoServiceNeg
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-791
* Autor				: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------				
* OSINE_SFS-791     12/10/2016      Mario Dioses Fernandez          Crear la tarea automática que cancele el registro de hidrocarburos
*/ 

package gob.osinergmin.inpsweb.service.business;
import gob.osinergmin.inpsweb.service.exception.RegistroHidrocarburoException;
import gob.osinergmin.mdicommon.domain.dto.RegistroHidrocarburoDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.RegistroHidrocarburoFilter;
import java.util.List;

/**
 *
 * @author zchaupis
 */
public interface RegistroHidrocarburoServiceNeg {

    public List<RegistroHidrocarburoDTO> getListRegistroHidrocarburo(RegistroHidrocarburoFilter registroHidrocarburoDTO) throws RegistroHidrocarburoException;
    public RegistroHidrocarburoDTO ActualizarRegistroHidrocarburo(RegistroHidrocarburoDTO registroHidrocarburoDTO,UsuarioDTO usuarioDTO) throws RegistroHidrocarburoException;
    public RegistroHidrocarburoDTO SuspenderRegistroHidrocarburoExterno(UnidadSupervisadaDTO unidadSupervisadaDTO,String nombreUsuario, UsuarioDTO usuarioDTO) throws RegistroHidrocarburoException;    
    /* OSINE_SFS-791 - RSIS 47 - Inicio */ 
    public RegistroHidrocarburoDTO actualizarEstadoRHExterno(UnidadSupervisadaDTO unidadSupervisadaDTO, UsuarioDTO usuarioDTO, String estadoRH)  throws RegistroHidrocarburoException;
    /* OSINE_SFS-791 - RSIS 47 - Fin */ 
}
