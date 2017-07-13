/**
* Resumen		
* Objeto		: DireccionUnidadSupervisadaDAO.java
* Descripción		: Clase que proporciona una interfaz para la implementación de métodos en DireccionUnidadSupervisadaDAOImpl
* Fecha de Creación	: 09/05/2016
* PR de Creación	: OSINE_SFS-480
* Autor			: Mario Dioses Fernandez
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     09/05/2016      Mario Dioses Fernandez      Registrar en BD campo FECHA_INICIO_PLAZO y FECHA_FIN_PLAZO luego de CONCLUIR_OS, considerando Plazo_Descargo por Rubro (MDI_ACTIVIDAD)
*
*/

package gob.osinergmin.inpsweb.service.dao;
import gob.osinergmin.inpsweb.service.exception.DireccionUnidadSupervisadaException;
import gob.osinergmin.mdicommon.domain.dto.DireccionUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.ui.DireccionUnidadSupervisadaFilter;
import java.util.List;

/* OSINE_SFS-480 - RSIS 17 - Inicio */
public interface DireccionUnidadSupervisadaDAO {
    public List<DireccionUnidadSupervisadaDTO> findbuscarDireccionesUnidad(DireccionUnidadSupervisadaFilter filtro) throws DireccionUnidadSupervisadaException;
    public DireccionUnidadSupervisadaDTO findById(Long idDirccionUnidadSuprvisada) throws DireccionUnidadSupervisadaException;
}
/* OSINE_SFS-480 - RSIS 17 - Fin */