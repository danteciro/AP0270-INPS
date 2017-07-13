/**
* Resumen
* Objeto		: UnidadObliSubTipoServiceNeg.java
* Descripción		: Clase Interfaz de la capa de negocio que contiene la declaración de métodos a implementarse en el UnidadObliSubTipoServiceNegImpl
* Fecha de Creación	: 23/05/2016
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                              Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     23/05/2016      Giancarlo Villanueva Andrade        Crear componente de selección de "subtipo de supervisión".Relacionar y adecuar el subtipo de supervisión, el cual deberá depender del tipo de supervisión seleccionado.
* 
*/

package gob.osinergmin.inpsweb.service.business;

import gob.osinergmin.mdicommon.domain.dto.UnidadObliSubTipoDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;

import java.util.List;
/* OSINE_SFS-480 - RSIS 26 - Inicio */
public interface UnidadObliSubTipoServiceNeg {

	public UnidadObliSubTipoDTO guardarUnidadMuestral(UnidadObliSubTipoDTO unidadObliSubTipo, UsuarioDTO usuarioDTO);

	public List<UnidadObliSubTipoDTO> guardarUnidadMuestral(List<UnidadSupervisadaDTO> listaUnidadSupervisada,UsuarioDTO usuarioDTO, UnidadObliSubTipoDTO unidadObliSubTipo);

	public List<UnidadObliSubTipoDTO> listarPruebaMuestralxPeriodoxSubTipo(UnidadObliSubTipoDTO filtroPruebaMuestral);

	public UnidadObliSubTipoDTO updateUnidadMuestral(UnidadObliSubTipoDTO unidadMuestraltoUpdate, UsuarioDTO usuarioDTO);

}
/* OSINE_SFS-480 - RSIS 26 - Inicio */