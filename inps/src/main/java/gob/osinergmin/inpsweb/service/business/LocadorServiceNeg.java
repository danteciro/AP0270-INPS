/**
* Resumen
* Objeto		: LocadorServiceNeg.java
* Descripción		: Clase Interfaz de la capa de negocio que contiene la declaración de métodos a implementarse en el LocadorServiceNegImpl
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     10/05/2016      Mario Dioses Fernandez      Listar Empresas Supervisoras según filtros definidos para Gerencia (Unidad Organica).
* 
*/

package gob.osinergmin.inpsweb.service.business;

import gob.osinergmin.mdicommon.domain.dto.ConfFiltroEmpSupDTO;
import gob.osinergmin.mdicommon.domain.dto.LocadorDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisoraEmpresaDTO;
import gob.osinergmin.mdicommon.domain.ui.LocadorFilter;
import gob.osinergmin.mdicommon.domain.ui.SupervisoraEmpresaFilter;
import java.util.List;

/**
 *
 * @author jpiro
 */

public interface LocadorServiceNeg {
    public List<LocadorDTO> listarLocador(LocadorFilter filtro);
    /* OSINE_SFS-480 - RSIS 11 - Inicio */
    public List<LocadorDTO> listarLocadorConfFiltros(LocadorFilter filtro); 
    /* OSINE_SFS-480 - RSIS 11 - Fin */
    List<ConfFiltroEmpSupDTO> listarConfFiltros(LocadorFilter filtro);
    public LocadorDTO getById(Long idLocador);
   
}
