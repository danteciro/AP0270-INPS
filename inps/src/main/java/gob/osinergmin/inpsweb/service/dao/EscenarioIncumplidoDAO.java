/**
* Resumen		
* Objeto		: EscenarioIncumplidoDAO.java
* Descripción		: Clase interfaz para la implementación de métodos en EscenarioIncumplidoDAOImpl
* Fecha de Creación	: 05/09/2016
* PR de Creación	: OSINE_SFS-480
* Autor			: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                    Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-791   |  05/09/2016  |  Luis García Reyna    |    Listar Escenarios Incumplidos de la Supervision
* OSINE_SFS-791   |  07/09/2016  |  Luis García Reyna    |    Registro Comentario Escenario Incumplido.
*
*/

package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.inpsweb.service.exception.DetalleSupervisionException;
import gob.osinergmin.mdicommon.domain.dto.EscenarioIncumplidoDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.EscenarioIncumplidoFilter;
import java.util.List;

public interface EscenarioIncumplidoDAO {

    public List<EscenarioIncumplidoDTO> find(EscenarioIncumplidoFilter filtro) throws DetalleSupervisionException;
     /* OSINE_SFS-791 - RSIS 16 - Inicio */
    public EscenarioIncumplidoDTO registroComentarioEscIncumplido(EscenarioIncumplidoDTO escenarioIncumplidoDTO, UsuarioDTO usuarioDTO) throws DetalleSupervisionException;
     /* OSINE_SFS-791 - RSIS 16 - Fin */
    public EscenarioIncumplidoDTO guardarEscenarioIncumplido(EscenarioIncumplidoDTO escenarioIncumplidoDTO, UsuarioDTO usuarioDTO) throws DetalleSupervisionException;
    public EscenarioIncumplidoDTO cambiaEstadoEscenarioIncumplido(EscenarioIncumplidoDTO escenarioIncumplidoDTO, UsuarioDTO usuarioDTO) throws DetalleSupervisionException;
}
