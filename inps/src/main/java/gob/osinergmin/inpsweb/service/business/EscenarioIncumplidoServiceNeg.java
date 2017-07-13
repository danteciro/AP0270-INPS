
/**
* Resumen		
* Objeto		: EscenarioIncumplidoServiceNeg.java
* Descripción		: Clase Interfaz de la capa de negocio que contiene la declaración de métodos a implementarse en el EscenarioIncumplidoServiceNegImpl
* Fecha de Creación	: 05/09/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-791  |  05/09/2016   |   Luis García Reyna          |     Ejecucion Medida - Listar Escenarios Incumplidos
* OSINE_SFS-791  |  07/09/2016   |   Luis García Reyna          |     Registro Comentario Escenario Incumplido.
*                |               |                              |
* 
*/


package gob.osinergmin.inpsweb.service.business;

import gob.osinergmin.inpsweb.service.exception.EscenarioIncumplidoException;
import gob.osinergmin.mdicommon.domain.dto.EscenarioIncumplidoDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.EscenarioIncumplidoFilter;
import java.util.List;

public interface EscenarioIncumplidoServiceNeg  {
    /* OSINE_SFS-791 - RSIS 16 - Inicio */ 
    public List<EscenarioIncumplidoDTO> findEscenarioIncumplido(EscenarioIncumplidoFilter filtro) throws EscenarioIncumplidoException;
    public EscenarioIncumplidoDTO getEscenarioIncumplido(EscenarioIncumplidoFilter filtroEi);
    public EscenarioIncumplidoDTO registroComentarioEscIncumplido(EscenarioIncumplidoDTO escenarioIncumplidoDTO, UsuarioDTO usuarioDTO) throws EscenarioIncumplidoException;
    public EscenarioIncumplidoDTO actualizarComentarioEscenarioIncumplido(EscenarioIncumplidoDTO escenarioIncumplidoDTO, UsuarioDTO usuarioDTO) throws EscenarioIncumplidoException;
    /* OSINE_SFS-791 - RSIS 16 - Fin */   
    public EscenarioIncumplidoDTO guardarEscenarioIncumplido(EscenarioIncumplidoDTO escenarioIncumplidoDTO, UsuarioDTO usuarioDTO) throws EscenarioIncumplidoException;
    public EscenarioIncumplidoDTO cambiaEstadoEscenarioIncumplido(EscenarioIncumplidoDTO escenarioIncumplidoDTO, UsuarioDTO usuarioDTO) throws EscenarioIncumplidoException;
}
