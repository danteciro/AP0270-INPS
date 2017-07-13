/**
* Resumen		
* Objeto		: UnidadSupervisadaDAO.java
* Descripción		: Clase interfaz para la implementación de métodos en UnidadSupervisadaDAOImpl
* Fecha de Creación	: 25/05/2016
* PR de Creación	: OSINE_SFS-480
* Autor			: Giancarlo Villanueva Andrade
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                              Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     25/05/2016      Giancarlo Villanueva Andrade        Crear componente de selección de "subtipo de supervisión".Relacionar y adecuar el subtipo de supervisión, el cual deberá depender del tipo de supervisión seleccionado.
* OSINE_MANT_DSHL_003  27/06/2017   Claudio Chaucca Umana::ADAPTER   Agrega el parametro del idPersonal que realiza la busqueda
*/

package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.mdicommon.domain.dto.UnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.busqueda.BusquedaUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.ui.UnidadSupervisadaFilter;

import java.util.List;
/* OSINE_SFS-480 - RSIS 26 - Inicio */
public interface UnidadSupervisadaDAO {
	List<BusquedaUnidadSupervisadaDTO> buscarUnidadSupervisada(UnidadSupervisadaFilter filtro);
        
        public List<UnidadSupervisadaDTO> getUnidadSupervisadaDTO(UnidadSupervisadaFilter filtro);
        
		/* OSINE_MANT_DSHL_003 - Inicio */
        List<BusquedaUnidadSupervisadaDTO> buscarUnidadSupervisadaUsuario(UnidadSupervisadaFilter filtro,Long idUsuario);
		/* OSINE_MANT_DSHL_003 - Fin */
}
/* OSINE_SFS-480 - RSIS 26 - FIN */
