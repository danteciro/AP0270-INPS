/**
* Resumen		
* Objeto		: DatoPlantillaDAO.java
* Descripción		: Clase que proporciona una interfaz para la implementación de métodos en DatoPlantillaDAOImpl
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     21/05/2016      Julio Piro Gonzales             Insertar imágenes de medios probatorios en plantillas de Informe de Supervisión
*
*/

package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.inpsweb.dto.BombaIncendioDTO;
import gob.osinergmin.inpsweb.dto.DatoPlantillaDTO;
import gob.osinergmin.inpsweb.dto.TanqueDTO;
import gob.osinergmin.inpsweb.service.exception.DatoPlantillaException;
import gob.osinergmin.mdicommon.domain.dto.AlmacenamientoAguaDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.ui.DetalleSupervisionFilter;
import java.util.List;

/**
 *
 * @author jpiro
 */
public interface DatoPlantillaDAO {
    public List<DocumentoAdjuntoDTO> obtenerDocumentoSupervisionPlantilla(Long idSupervision) throws DatoPlantillaException;
    public DatoPlantillaDTO obtenerDatosPlantilla(Long idSupervision) throws DatoPlantillaException;
    public List<DetalleSupervisionDTO> obtenerDetalleSupervision(DetalleSupervisionFilter filtro) throws DatoPlantillaException;
    public DatoPlantillaDTO obtenerFechasCartasVisitas(Long idSupervision) throws DatoPlantillaException;
    public List<TanqueDTO> obtenerTanquesParaPlantilla(Long idUnidadSupervisada);
    public List<BombaIncendioDTO> obtenerBombasParaPlantilla(Long idUnidadSupervisada);
    public List<AlmacenamientoAguaDTO> listarAlmacenamientoAgua(Long idUnidadSupervisada);
    /* OSINE_SFS-480 - RSIS 01 - Inicio */
    public List<DocumentoAdjuntoDTO> obtenerImagenesHallazgo(Long idSupervision);
    /* OSINE_SFS-480 - RSIS 01 - Fin */
}
