/**
* Resumen
* Objeto		: UnidadObligacionSubTipoBuilder.java
* Descripción		: Constructor de PghUnidadObligacionSubTipo
* Fecha de Creación	: 08/06/2016
* PR de Creación	: OSINE_SFS-480
* Autor			: Giancarlo Villanueva Andrade
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     23/05/2016      Giancarlo Villanueva Andrade    Crear componente de selección de "subtipo de supervisión".Relacionar y adecuar el subtipo de supervisión, el cual deberá depender del tipo de supervisión seleccionado.
* OSINE_SFS-480     06/06/2016      Giancarlo Villanueva Andrade    Adecuar  interfaz para la nueva forma de generación de órdenes de servicio (masivo).
*
*/

package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.MdiMaestroColumna;
import gob.osinergmin.inpsweb.domain.MdiUnidadSupervisada;
import gob.osinergmin.inpsweb.domain.PghObligacionSubTipo;
import gob.osinergmin.inpsweb.domain.PghUnidObliSubTipo;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.ObligacionSubTipoDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadObliSubTipoDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadSupervisadaDTO;

import java.util.ArrayList;
import java.util.List;

/* OSINE_SFS-480 - RSIS 26 - Inicio */
public class UnidadObligacionSubTipoBuilder {

	public static PghUnidObliSubTipo getObligacionSubTipo(UnidadObliSubTipoDTO registroDTO) {
		PghUnidObliSubTipo registroDAO = null;
		if(registroDTO!=null){
			registroDAO = new PghUnidObliSubTipo();
			if(registroDTO.getIdUnidObliSubtipo()!=null){
				registroDAO.setIdUnidObliSubTipo(registroDTO.getIdUnidObliSubtipo());
			}
			if(registroDTO.getEstado()!=null){
				registroDAO.setEstado(registroDTO.getEstado());
			}
			if(registroDTO.getTipoSeleccion()!=null){
				registroDAO.setTipoSeleccion(new MdiMaestroColumna(registroDTO.getTipoSeleccion().getIdMaestroColumna()));
			}
			registroDAO.setPeriodo(registroDTO.getPeriodo());
			if(registroDTO.getIdUnidadSupervisada()!=null && registroDTO.getIdUnidadSupervisada().getIdUnidadSupervisada()!=null){
				MdiUnidadSupervisada idUnidadSupervisada = new MdiUnidadSupervisada();
				idUnidadSupervisada.setIdUnidadSupervisada(registroDTO.getIdUnidadSupervisada().getIdUnidadSupervisada());
				registroDAO.setIdUnidadSupervisada(idUnidadSupervisada);
			}
			
			if(registroDTO.getIdObligacionSubTipo()!=null && registroDTO.getIdObligacionSubTipo().getIdObligacionSubTipo()!=null){
				PghObligacionSubTipo idObligacionSubTipo = new PghObligacionSubTipo();
				idObligacionSubTipo.setIdObligacionSubTipo(registroDTO.getIdObligacionSubTipo().getIdObligacionSubTipo());
				registroDAO.setIdObligacionSubTipo(idObligacionSubTipo);
			}
                        /* OSINE_SFS-480 - RSIS 27 - Inicio */
			registroDAO.setFlagSupOrdenServicio(registroDTO.getFlagSupOrdenServicio());
			/* OSINE_SFS-480 - RSIS 27 - Fin */
		}
		return registroDAO;
	}
	
	public static List<UnidadObliSubTipoDTO> toListUnidadObligacionSubTipoDto(List<PghUnidObliSubTipo> lista) {
		UnidadObliSubTipoDTO registroDTO;
        List<UnidadObliSubTipoDTO> retorno = new ArrayList<UnidadObliSubTipoDTO>();
        if (lista != null) {
            for (PghUnidObliSubTipo maestro : lista) {
                registroDTO = toObligacionSubTipoDto(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }

	public static UnidadObliSubTipoDTO toObligacionSubTipoDto(PghUnidObliSubTipo registroDAO) {
		UnidadObliSubTipoDTO registroDTO = null;
		if(registroDAO!=null){
			registroDTO = new UnidadObliSubTipoDTO();
			if(registroDAO.getIdUnidObliSubTipo()!=null){
				registroDTO.setIdUnidObliSubtipo(registroDAO.getIdUnidObliSubTipo());
			}
			if(registroDAO.getEstado()!=null){
				registroDTO.setEstado(registroDAO.getEstado());
			}			
			if(registroDAO.getTipoSeleccion()!=null){
				registroDTO.setTipoSeleccion(new MaestroColumnaDTO(registroDAO.getTipoSeleccion().getIdMaestroColumna(), registroDAO.getTipoSeleccion().getDescripcion()));
			}
			registroDTO.setPeriodo(registroDAO.getPeriodo());
			if(registroDAO.getIdObligacionSubTipo()!=null && registroDAO.getIdObligacionSubTipo().getIdObligacionSubTipo()!=null){
				ObligacionSubTipoDTO idObligacionSubTipo = new ObligacionSubTipoDTO();
				idObligacionSubTipo.setIdObligacionSubTipo(registroDAO.getIdObligacionSubTipo().getIdObligacionSubTipo());
				registroDTO.setIdObligacionSubTipo(idObligacionSubTipo);
			}
			if(registroDAO.getIdUnidadSupervisada()!=null && registroDAO.getIdUnidadSupervisada().getIdUnidadSupervisada()!=null){
				UnidadSupervisadaDTO idUnidadSupervisada = new UnidadSupervisadaDTO();
				idUnidadSupervisada.setIdUnidadSupervisada(registroDAO.getIdUnidadSupervisada().getIdUnidadSupervisada());
				registroDTO.setIdUnidadSupervisada(idUnidadSupervisada);
			}
			registroDTO.setFlagSupOrdenServicio(registroDAO.getFlagSupOrdenServicio());
		}
		return registroDTO;
	}
}
/* OSINE_SFS-480 - RSIS 26 - Fin */