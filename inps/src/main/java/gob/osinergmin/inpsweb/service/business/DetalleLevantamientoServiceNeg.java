/**
* Resumen		
* Objeto			: DetalleLevantamientoServiceNeg.java
* Descripción		: Clase Service DetalleLevantamientoServiceNeg
* Fecha de Creación	: 07/10/2016
* PR de Creación	: OSINE_SFS-791
* Autor				: mdiosesf
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------				
* OSINE_SFS-791     07/10/2016      Mario Dioses Fernandez      Crear la funcionalidad "verificar levantamientos" que permita verificar el tipo de asignación para la orden de levantamiento DSR-CRITICIDAD.
* OSINE_SFS-791-RSIS48  |   21/10/2016   |   Luis García Reyna   |  Registrar Detalle Levantamiento
*/ 

package gob.osinergmin.inpsweb.service.business;
import gob.osinergmin.inpsweb.service.exception.DetalleLevantamientoException;
import gob.osinergmin.mdicommon.domain.dto.DetalleLevantamientoDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.DetalleLevantamientoFilter;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface DetalleLevantamientoServiceNeg {
	public List<DetalleLevantamientoDTO> find(DetalleLevantamientoFilter filtro) throws DetalleLevantamientoException;
	public DetalleLevantamientoDTO registrarDetalleLevantamiento(DetalleLevantamientoDTO detalleLevantamiento, UsuarioDTO UsuarioDTO) throws DetalleLevantamientoException;
	public DetalleLevantamientoDTO actualizarDetalleLevantamiento(DetalleLevantamientoDTO detalleLevantamiento, UsuarioDTO UsuarioDTO) throws DetalleLevantamientoException;
    /* OSINE_SFS-791 - RSIS 48 - Inicio */
    public void guardarDetalleLevantamiento(ExpedienteDTO expedienteDTO, HttpSession session, HttpServletRequest request) throws DetalleLevantamientoException;
    /* OSINE_SFS-791 - RSIS 48 - Fin */
}
