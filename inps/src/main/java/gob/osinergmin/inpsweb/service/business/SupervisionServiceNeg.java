/**
* Resumen		
* Objeto			: SupervisionServiceNeg.java
* Descripción		: SupervisionServiceNeg
* Fecha de Creación	: 
* PR de Creación	: 
* Autor			: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones 
* Motivo            Fecha           Nombre                           Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE791          17/08/2016       Yadira Piskulich                 Abrir Supervision DSR
* OSINE791        | 23/08/2016     | Luis García Reyna          |     Crear la funcionalidad para registrar otros incumplimientos
* OSINE791-RSIS04 | 05/10/2016     | Zosimo Chaupis Santur      |     CREAR LA FUNCIONALIDAD REGISTRAR SUPERVISION DE UNA ORDEN DE SUPERVISION DSR-CRITICIDAD DE LA CASUISTICA SUPERVISION NO INICIADA                      |
* OSINE_SFS-791     23/10/2016       Mario Dioses Fernandez           Crear la tarea automática que notifique vía correo que se debe elaborar el oficio por obligaciones incumplidas sin subsanar
* OSINE_SFS-791     23/10/2016       Mario Dioses Fernandez           Crear la tarea automática que cancele el registro de hidrocarburos 
*/ 

package gob.osinergmin.inpsweb.service.business;
import gob.osinergmin.inpsweb.service.exception.SupervisionException;
import gob.osinergmin.mdicommon.domain.dto.CriterioDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleCriterioDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.MotivoNoSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.OrdenServicioDTO;
import gob.osinergmin.mdicommon.domain.dto.PlantillaResultadoDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionPersonaGralDTO;
import gob.osinergmin.mdicommon.domain.dto.TipificacionDTO;
import gob.osinergmin.mdicommon.domain.dto.TipificacionSancionDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.DetalleSupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.OrdenServicioFilter;
import gob.osinergmin.mdicommon.domain.ui.PlantillaResultadoFilter;
import gob.osinergmin.mdicommon.domain.ui.SupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.SupervisionPersonaGralFilter;
import gob.osinergmin.mdicommon.domain.ui.TipificacionFilter;
import gob.osinergmin.mdicommon.domain.ui.TipificacionSancionFilter;
import java.util.List;
import java.util.Map;

public interface SupervisionServiceNeg {
	public List<SupervisionDTO> buscarSupervision(SupervisionFilter filtro) throws SupervisionException;
	public SupervisionDTO registrarSupervision(SupervisionDTO supervisionDTO,UsuarioDTO usuarioDTO) throws SupervisionException;
	public SupervisionDTO registrarSupervisionBloque(SupervisionDTO supervisionDTO,UsuarioDTO usuarioDTO)throws SupervisionException;
	public SupervisionDTO actualizarSupervision(SupervisionDTO supervisionDTO,UsuarioDTO usuarioDTO) throws SupervisionException;
	public SupervisionDTO cambiarSupervision(SupervisionDTO supervisionDTO,UsuarioDTO usuarioDTO) throws SupervisionException;
	public SupervisionDTO registrarDatosSupervision(SupervisionDTO supervisionDTO,UsuarioDTO usuarioDTO) throws SupervisionException;
	public SupervisionDTO registrarPersonaAtiende(SupervisionDTO supervisionDTO,UsuarioDTO usuarioDTO) throws SupervisionException;
	public DetalleSupervisionDTO registrarDetalleSupervision(DetalleSupervisionDTO detalleSupervisionDTO,UsuarioDTO usuarioDTO) throws SupervisionException;
	public DetalleSupervisionDTO actualizarDetalleSupervision(DetalleSupervisionDTO detalleSupervisionDTO, UsuarioDTO usuarioDTO)throws SupervisionException;
	public List<DetalleSupervisionDTO> findDetalleSupervision(DetalleSupervisionFilter filtro);
	public List<DetalleSupervisionDTO> findDetalleSupervisionDSHL(DetalleSupervisionFilter filtro);
	public List<TipificacionDTO> listarTipificacion(TipificacionFilter filtro);
	public List<TipificacionSancionDTO> listarTipificacionSancion(TipificacionSancionFilter filtro);
	public Integer buscarFlag(Long idObligacionTipo,Long  idActividad,Long idProceso);
	public List<CriterioDTO> listarCriterio(Long idObligacion,Long idTipificacion,Long idCriterio);
	public List<DetalleCriterioDTO> listarDetalleCriterio(Long idCriterio,Long idDetalleCriterio);
	public List<SupervisionPersonaGralDTO> listarSupervisionPersona(SupervisionPersonaGralFilter filtro);
	public List<PlantillaResultadoDTO> listarPlantillaResultado(PlantillaResultadoFilter filtro);
	
        
        
        public Map<String, Object> validarRegistroSupervision(String enPantalla,Long idSupervision,SupervisionDTO supervisionPantalla) throws SupervisionException;
	public List<MotivoNoSupervisionDTO> listarMotivoNoSupervisionAll();
	public Boolean eliminarDetalleSupervision(Long idOrdenServicio,UsuarioDTO usuarioDTO,Long idExpediente) throws SupervisionException;
	public List<OrdenServicioDTO> listarOrdenServicio(OrdenServicioFilter filtro);
    /* OSINE_SFS-791 - RSIS 15 - Inicio */  
    public SupervisionDTO registroOiSupervision(SupervisionDTO supervisionDTO, UsuarioDTO usuarioDTO) throws SupervisionException;
    /* OSINE_SFS-791 - RSIS 15 - Fin */  
    /* OSINE_SFS-791 - RSIS 16 - Inicio */ 
    public SupervisionDTO registroEmSupervision(SupervisionDTO supervisionDTO, UsuarioDTO usuarioDTO) throws SupervisionException;
    /* OSINE_SFS-791 - RSIS 16 - Fin */ 
    /* OSINE791 - RSIS17 - Inicio */
    public DocumentoAdjuntoDTO registrarDocumentoEjecucionMedida(DocumentoAdjuntoDTO documentoAdjuntoDTO, UsuarioDTO usuarioDTO) throws SupervisionException;
    public List<SupervisionDTO> buscarSupervisionReporte(SupervisionFilter filtro);
    /* OSINE791 - RSIS17 - Fin */
    /* OSINE791 - RSIS20 - Inicio */
    public Map<String, Object> validarRegistroSupervisionDsr(String enPantalla,Long idSupervision,SupervisionDTO supervisionPantalla) throws SupervisionException;
    /* OSINE791 - RSIS20 - Fin */
    /* OSINE791 – RSIS4 - Inicio */
    public int VerificarSupervisionObstaculizada(SupervisionDTO filtro)throws SupervisionException;
    /* OSINE791 – RSIS4 - Fin */
    public SupervisionDTO registrarSupervisionBloqueDsr(SupervisionDTO supervisionDTO,UsuarioDTO usuarioDTO)throws SupervisionException;
    /*OSINE_SFS-791 - RSIS 03 - Inicio */
    public SupervisionDTO registrarDatosFinalesSupervision(SupervisionDTO supervisionDTO, UsuarioDTO usuarioDTO) throws SupervisionException;
    /*OSINE_SFS-791 - RSIS 03 - Fin  */
    public String buscarCodigoFlujoSupervINPS(Long idObligacionTipo, Long idActividad, Long idProceso);
    
    /*OSINE_SFS-791 - RSIS 41 - Inicio */
    public SupervisionDTO ActualizarSupervisionTerminarSupervision(SupervisionDTO supervisionDTO, UsuarioDTO usuarioDTO) throws SupervisionException;
    /*OSINE_SFS-791 - RSIS 41 - Fin */
    /* OSINE791 - RSIS41 - Inicio */
    public DocumentoAdjuntoDTO registrarDocumentoGenerarResultados(DocumentoAdjuntoDTO documentoAdjuntoDTO, UsuarioDTO usuarioDTO) throws SupervisionException;
    /* OSINE791 - RSIS41 - fin */
    /* OSINE791 - RSIS39 - Inicio */
    public boolean verificarNroCartaVista(SupervisionFilter filtro) throws SupervisionException;
    /* OSINE791 - RSIS39 - Fin */
    /* OSINE791 - RSIS41 - Inicio */
    public List<SupervisionDTO> verificarSupervisionCierreTotalParcialTareaProgramada(SupervisionFilter filtro) throws SupervisionException;
    /* OSINE791 - RSIS41 - Fin */
    /* OSINE_SFS-791 - RSIS 46-47 - Inicio */
    public List<SupervisionDTO> listarSupSinSubSanar(SupervisionFilter filtro) throws SupervisionException;
    /* OSINE_SFS-791 - RSIS 46-47 - Fin */
}