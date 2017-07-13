/**
* Resumen		
* Objeto		: SupervisionDAO.java
* Descripción		: Clase interfaz para la implementación de métodos en SupervisionDAOImpl
* Fecha de Creación	: 
* PR de Creación	: 
* Autor			: GMD
* =============================================================================================================================
* Modificaciones
* Motivo            Fecha           Nombre                              Descripción
* =============================================================================================================================
* OSINE_SFS-791  |  23/08/2016   |  Luis García Reyna          | Crear la funcionalidad para registrar otros incumplimientos
* OSINE791-RSIS04 | 05/10/2016   |  Zosimo Chaupis Santur      | CREAR LA FUNCIONALIDAD REGISTRAR SUPERVISION DE UNA ORDEN DE SUPERVISION DSR-CRITICIDAD DE LA CASUISTICA SUPERVISION NO INICIADA                      |
* OSINE_SFS-791     23/10/2016      Mario Dioses Fernandez       Crear la tarea automática que notifique vía correo que se debe elaborar el oficio por obligaciones incumplidas sin subsanar
* OSINE_SFS-791     23/10/2016      Mario Dioses Fernandez       Crear la tarea automática que cancele el registro de hidrocarburos
*/

package gob.osinergmin.inpsweb.service.dao;
import java.util.List;
import gob.osinergmin.inpsweb.service.exception.SupervisionException;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.SupervisionFilter;

public interface SupervisionDAO {
	public List<SupervisionDTO> find(SupervisionFilter filtro) throws SupervisionException;
	public SupervisionDTO registrarSupervision(SupervisionDTO supervisionDTO,UsuarioDTO usuarioDTO) throws SupervisionException;
	public SupervisionDTO actualizar(SupervisionDTO supervisionDTO, UsuarioDTO usuarioDTO) throws SupervisionException;
	public SupervisionDTO registrarDatosSupervision(SupervisionDTO supervisionDTO, UsuarioDTO usuarioDTO) throws SupervisionException;
	public SupervisionDTO registrarPersonaAtiende(SupervisionDTO supervisionDTO, UsuarioDTO usuarioDTO) throws SupervisionException;
	public SupervisionDTO cambiarSupervision(SupervisionDTO supervisionDTO, UsuarioDTO usuarioDTO) throws SupervisionException;
    /* OSINE_SFS-791 - RSIS 15 - Inicio */ 
    public SupervisionDTO registroOiSupervision(SupervisionDTO supervisionDTO, UsuarioDTO usuarioDTO) throws SupervisionException;
    /* OSINE_SFS-791 - RSIS 15 - Fin */
    /* OSINE_SFS-791 - RSIS 16 - Inicio */
    public SupervisionDTO registroEmSupervision(SupervisionDTO supervisionDTO, UsuarioDTO usuarioDTO) throws SupervisionException;
    /* OSINE_SFS-791 - RSIS 16 - Fin */
    /* OSINE791 - RSIS17 - Inicio */
    public DocumentoAdjuntoDTO registrarDocumentoEjecucionMedida(DocumentoAdjuntoDTO documentoAdjuntoDTO, UsuarioDTO usuarioDTO) throws SupervisionException;
    public List<SupervisionDTO> findSupervisionReporte(SupervisionFilter filtro) throws SupervisionException;
    /* OSINE791 - RSIS17 - Fin */
    /* OSINE791 – RSIS4 - Inicio */
    public int VerificarSupervisionObstaculizada(SupervisionDTO filtro)throws SupervisionException;
    /* OSINE791 – RSIS4 - Fin */
    /*OSINE_SFS-791 - RSIS 03 - Inicio */
    public SupervisionDTO registrarDatosFinalesSupervision(SupervisionDTO supervisionDTO, UsuarioDTO usuarioDTO) throws SupervisionException;
    /*OSINE_SFS-791 - RSIS 03 - Fin */
    /*OSINE_SFS-791 - RSIS 41 - Inicio */
    public SupervisionDTO ActualizarSupervisionTerminarSupervision(SupervisionDTO supervisionDTO, UsuarioDTO usuarioDTO) throws SupervisionException;
    /*OSINE_SFS-791 - RSIS 41 - Fin */
    /* OSINE791 - RSIS41 - Inicio */
    public DocumentoAdjuntoDTO registrarDocumentoGenerarResultados(DocumentoAdjuntoDTO documentoAdjuntoDTO, UsuarioDTO usuarioDTO) throws SupervisionException;
    /* OSINE791 - RSIS41 - fin */
    /* OSINE791 - RSIS39 - Inicio */
    public List<SupervisionDTO> verificarNroCartaVista(SupervisionFilter filtro) throws SupervisionException;
    /* OSINE791 - RSIS39 - Fin */
    /* OSINE791 - RSIS41 - Inicio */
    public List<SupervisionDTO> verificarSupervisionCierreTotalParcialTareaProgramada(SupervisionFilter filtro) throws SupervisionException;
    /* OSINE791 - RSIS41 - Fin */
    /* OSINE_SFS-791 - RSIS 46-47 - Inicio */ 
    public List<SupervisionDTO> listarSupSinSubSanar(SupervisionFilter filtro) throws SupervisionException;
    /* OSINE_SFS-791 - RSIS 46-47 - Fin */ 
}
