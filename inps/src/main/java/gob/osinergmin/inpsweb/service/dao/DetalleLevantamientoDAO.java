/**
* Resumen		
* Objeto			: DetalleLevantamientoDAO.java
* Descripción		: Clase DAO DetalleLevantamientoDAO
* Fecha de Creación	: 07/10/2016
* PR de Creación	: OSINE_SFS-791
* Autor				: mdiosesf
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------				
* OSINE_SFS-791     07/10/2016      Mario Dioses Fernandez          Crear la funcionalidad "verificar levantamientos" que permita verificar el tipo de asignación para la orden de levantamiento DSR-CRITICIDAD.
*/ 
package gob.osinergmin.inpsweb.service.dao;
import java.util.List;

import gob.osinergmin.inpsweb.service.exception.DetalleLevantamientoException;
import gob.osinergmin.mdicommon.domain.dto.DetalleLevantamientoDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.DetalleLevantamientoFilter;

public interface DetalleLevantamientoDAO {
	public List<DetalleLevantamientoDTO> find(DetalleLevantamientoFilter filtro) throws DetalleLevantamientoException;
	public DetalleLevantamientoDTO registrar(DetalleLevantamientoDTO detalleLevantamiento, UsuarioDTO UsuarioDTO) throws DetalleLevantamientoException;
	public DetalleLevantamientoDTO actualizar(DetalleLevantamientoDTO detalleLevantamiento, UsuarioDTO UsuarioDTO) throws DetalleLevantamientoException;
}
