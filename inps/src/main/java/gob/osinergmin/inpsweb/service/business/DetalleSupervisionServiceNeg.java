/**

* Resumen		
* Objeto		: DetalleSupervisionServiceNeg.java
* Descripción		: Clase Interfaz de la capa de negocio que contiene la declaración de métodos a implementarse en el DetalleSupervisionServiceNegImpl
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-791
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-791  |  02/09/2016   |   Luis García Reyna          |     Ejecucion Medida - Listar Obligaciones
* OSINE_SFS-791  |  06/10/2016   |   Mario Dioses Fernandez     |     Crear la funcionalidad "verificar levantamientos" que permita verificar el tipo de asignación para la orden de levantamiento DSR-CRITICIDAD.
*                |               |                              |
* 
*/
package gob.osinergmin.inpsweb.service.business;
import gob.osinergmin.inpsweb.service.exception.DetalleSupervisionException;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.DetalleSupervisionFilter;

import java.util.List;

public interface DetalleSupervisionServiceNeg {
    public List<DetalleSupervisionDTO> listarDetalleSupervision(DetalleSupervisionFilter filtro) throws DetalleSupervisionException;
    /* OSINE_SFS-791 - RSIS 16 - Inicio */
    public DetalleSupervisionDTO registroComentarioDetSupervision(DetalleSupervisionDTO detalleSupervisionDTO, UsuarioDTO usuarioDTO) throws DetalleSupervisionException;
    public DetalleSupervisionDTO getDetalleSupervision(DetalleSupervisionFilter filtroDs);
    public DetalleSupervisionDTO actualizarComentarioDetalleSupervision(DetalleSupervisionDTO detalleSupervisionDTO, UsuarioDTO usuarioDTO) throws DetalleSupervisionException;
    /* OSINE_SFS-791 - RSIS 16 - Fin */      
    public long cantidadDetalleSupervision(DetalleSupervisionFilter filtro) throws DetalleSupervisionException;    
    public Boolean VerCierreTotalSupervision(DetalleSupervisionFilter filter,UsuarioDTO usuarioDTO,Long idUnidadSupervisada)throws DetalleSupervisionException;    
    public DetalleSupervisionDTO ActualizarMedidaSeguridadDetalleSupervision(DetalleSupervisionDTO detalleSupervisionDTO, UsuarioDTO usuarioDTO) throws DetalleSupervisionException;
    public List<DetalleSupervisionDTO> validarComentarioEjecucionMedida(DetalleSupervisionFilter filtro) throws DetalleSupervisionException;
    /* OSINE_SFS-791 - RSIS 33 - Inicio */
    public List<DetalleSupervisionDTO> listarDetSupInfLevantamiento(DetalleSupervisionFilter filtro) throws DetalleSupervisionException;
    /* OSINE_SFS-791 - RSIS 33 - Fin */
    /* OSINE_SFS-791 - RSIS 41 - Inicio */
    public List<DetalleSupervisionDTO> VerificaActualizaDetalleSupervisionSubsanados(DetalleSupervisionFilter filtro, UsuarioDTO usuarioDTO) throws DetalleSupervisionException;
    /* OSINE_SFS-791 - RSIS 41 - Fin */
}
