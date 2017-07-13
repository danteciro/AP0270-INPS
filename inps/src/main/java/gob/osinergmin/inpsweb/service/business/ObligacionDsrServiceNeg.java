/**
* Resumen		
* Objeto			: ObligacionDsrServiceNeg.java
* Descripción		: Clase ServiceNeg ObligacionDsrServiceNeg
* Fecha de Creación	: 14/10/2016
* PR de Creación	: OSINE_SFS-791
* Autor				: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------				
* OSINE_SFS-791     14/10/2016      Mario Dioses Fernandez          Crear la tarea automática que notifique vía correo que se debe elaborar el oficio por obligaciones incumplidas sin subsanar
* OSINE_SFS-791     14/10/2016      Mario Dioses Fernandez          Crear la tarea automática que cancele el registro de hidrocarburos
* OSINE_MAN_DSR_0025     |  19/06/2017   |   Carlos Quijano Chavez::ADAPTER      |     Agregar la Columna Codigo y eliminar prioridad
*/ 
package gob.osinergmin.inpsweb.service.business;
import gob.osinergmin.inpsweb.dto.GenerarResultadoDTO;
import gob.osinergmin.inpsweb.service.exception.DocumentoAdjuntoException;
import gob.osinergmin.inpsweb.service.exception.ExpedienteException;
import gob.osinergmin.inpsweb.service.exception.SupervisionException;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteTareaDTO;
import gob.osinergmin.mdicommon.domain.dto.ObligacionDsrDTO;
import gob.osinergmin.mdicommon.domain.dto.OrdenServicioDTO;
import gob.osinergmin.mdicommon.domain.dto.ProductoDsrScopDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.UbigeoDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.ObligacionDsrFilter;
/* OSINE_MAN_DSR_0025 - Inicio */
import gob.osinergmin.inpsweb.dto.ObligacionDsrUpdtDTO;
/* OSINE_MAN_DSR_0025 - Fin */
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface ObligacionDsrServiceNeg {	
	public List<ObligacionDsrDTO> listarObligacionDsr(ObligacionDsrFilter filtro);
	/* OSINE_MAN_DSR_0025 - Inicio */
	public List<ObligacionDsrUpdtDTO> listarObligacionDsrUpdt(ObligacionDsrFilter filtro);	
	/* OSINE_MAN_DSR_0025 - Fin */	
	public List<ProductoDsrScopDTO> listarPdtosDsr(ObligacionDsrFilter filtro);	
	public ObligacionDsrDTO findByPrioridad(Long idSupervision, Long prioridad);
	public ObligacionDsrDTO guardarDsrObligacion(ObligacionDsrDTO obligacionDstDTO, UsuarioDTO usuarioDto);	
	public ProductoDsrScopDTO guardarDsrProductoSuspender(ProductoDsrScopDTO productoDsrScopDTO, UsuarioDTO usuarioDto);	
    public ProductoDsrScopDTO eliminarDsrProductoSuspender(ProductoDsrScopDTO productoDsrScopDTO, UsuarioDTO usuarioDto);
    /* OSINE791 – RSIS12 - Inicio */
    public String  VerificarObstaculizados(ObligacionDsrDTO filtro);
    /* OSINE791 – RSIS12 - Fin */
    public ObligacionDsrDTO editarDsrComentarioSubsanacion(ObligacionDsrDTO obligacionDstDTO, UsuarioDTO usuarioDto);
    public Map<String, Object> generarResultadosDocumento(GenerarResultadoDTO generarResultadoDTO,HttpServletRequest request, HttpSession session) throws DocumentoAdjuntoException;
    /* OSINE791 – RSIS04 - Inicio */
    public Map<String, Object> generarResultadosDocSupervisionInicial(GenerarResultadoDTO generarResultadoDTO,HttpServletRequest request, HttpSession session) throws DocumentoAdjuntoException;
    /* OSINE791 – RSIS04 - Fin */
    public Map<String, Object> guardarDatosInicialSupervisionDsr(SupervisionDTO supervisionDTO, HttpSession session, HttpServletRequest request) throws SupervisionException;
    /*OSINE_SFS-791 - RSIS 03 - Inicio */
    public Map<String, Object> generarResultadoSupervision(GenerarResultadoDTO generarResultadoDTO, HttpServletRequest request, HttpSession session) throws DocumentoAdjuntoException;
    /*OSINE_SFS-791 - RSIS 03 - Fin */
    /*OSINE_SFS-791 - RSIS 40 - Inicio */
    public Map<String, Object> generarResultadosSupTerminarSupervision(GenerarResultadoDTO generarResultadoDTO, HttpServletRequest request, HttpSession session) throws DocumentoAdjuntoException;
    /*OSINE_SFS-791 - RSIS 40 - Fin */
    
    /* ---------------------------------------------------------------------------------------------------*/
    /* OSINE_SFS-791 - RSIS 46-47 - Inicio */ 
    public void procesarTareaNotiOblIncumpSubsana(ExpedienteDTO expediente, ExpedienteTareaDTO expedienteTarea, UbigeoDTO ubigeo, OrdenServicioDTO ordenServicio, UsuarioDTO usuario);
    public void procesarTareaCancelacionRH(ExpedienteDTO expediente, UnidadSupervisadaDTO unidadSupervisada, SupervisionDTO supervision, OrdenServicioDTO ordenServicio, ExpedienteTareaDTO expedienteTarea, UsuarioDTO usuario, UbigeoDTO ubigeo, ServletContext context);
    /* OSINE_SFS-791 - RSIS 46-47 - Fin */ 
    /* ---------------------------------------------------------------------------------------------------*/    
    /* OSINE_SFS-791 - RSIS 40 - Inicio */ 
    public void  procesarTareaHabilitacionCierre(UnidadSupervisadaDTO unidadSupervisada, SupervisionDTO supervision,UsuarioDTO usuario, UbigeoDTO ubigeo, ServletContext context) throws ExpedienteException;
    /* OSINE_SFS-791 - RSIS 40 - Fin */ 
}
