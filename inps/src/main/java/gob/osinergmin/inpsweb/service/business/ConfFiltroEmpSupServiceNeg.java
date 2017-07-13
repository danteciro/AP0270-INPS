/**
* Resumen
* Objeto		: ConfFiltroEmpSupServiceNeg.java
* Descripción		: Clase Interfaz de la capa de negocio que contiene la declaración de métodos a implementarse en el ConfFiltroEmpSupServiceNegImpl
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
import gob.osinergmin.inpsweb.service.exception.ConfFiltroEmpSupException;
import gob.osinergmin.mdicommon.domain.dto.ConfFiltroEmpSupDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.ConfFiltroEmpSupFilter;
import java.util.List;

/* OSINE_SFS-480 - RSIS 17 - Inicio */
public interface ConfFiltroEmpSupServiceNeg {
    public List<ConfFiltroEmpSupDTO> ConfFiltroEmpSup(ConfFiltroEmpSupFilter filtro) throws ConfFiltroEmpSupException;
    public ConfFiltroEmpSupDTO guardarConfFiltroEmpSup(ConfFiltroEmpSupDTO confFiltroEmpSupDTO, UsuarioDTO usuarioDTO) throws ConfFiltroEmpSupException;
    public ConfFiltroEmpSupDTO editarConfFiltroEmpSup(ConfFiltroEmpSupDTO confFiltroEmpSupDTO, UsuarioDTO usuarioDTO) throws ConfFiltroEmpSupException;
}
/* OSINE_SFS-480 - RSIS 17 - Fin */