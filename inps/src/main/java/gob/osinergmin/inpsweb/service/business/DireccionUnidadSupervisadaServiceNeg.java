/**
* Resumen
* Objeto		: DireccionUnidadSupervisadaServiceNeg.java
* Descripción		: Clase Interfaz de la capa de negocio que contiene la declaración de métodos a implementarse en el DireccionUnidadSupervisadaServiceNegImpl
* Fecha de Creación	: 05/05/2016
* PR de Creación	: OSINE_SFS-480
* Autor			: Mario Dioses Fernandez
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     05/05/2016      Mario Dioses Fernandez      Registrar en BD campo FECHA_INICIO_PLAZO y FECHA_FIN_PLAZO luego de CONCLUIR_OS, considerando Plazo_Descargo por Rubro (MDI_ACTIVIDAD)
* 
*/

package gob.osinergmin.inpsweb.service.business;
import java.util.List;
import gob.osinergmin.inpsweb.service.exception.DireccionUnidadSupervisadaException;
import gob.osinergmin.mdicommon.domain.dto.DireccionUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.ui.DireccionUnidadSupervisadaFilter;

/* OSINE_SFS-480 - RSIS 17 - Inicio */
public interface DireccionUnidadSupervisadaServiceNeg {
    public List<DireccionUnidadSupervisadaDTO> buscarDireccionesUnidad(DireccionUnidadSupervisadaFilter filtro) throws DireccionUnidadSupervisadaException;
    public DireccionUnidadSupervisadaDTO findById(Long idDirccionUnidadSuprvisada) throws DireccionUnidadSupervisadaException;
}
/* OSINE_SFS-480 - RSIS 17 - Fin */