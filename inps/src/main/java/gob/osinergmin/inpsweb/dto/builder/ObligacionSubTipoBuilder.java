/**
* Resumen
* Objeto		: ObligacionSubTipoBuilder.java
* Descripción		: Constructor de ObligacionSubTipo
* Fecha de Creación	: 24/05/2016
* PR de Creación	: OSINE_SFS-480
* Autor			: Giancarlo Villanueva Andrade
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     24/05/2016      Giancarlo Villanueva Andrade        Crear componente de selección de "subtipo de supervisión".Relacionar y adecuar el subtipo de supervisión, el cual deberá depender del tipo de supervisión seleccionado.
*
*/

package gob.osinergmin.inpsweb.dto.builder;
import gob.osinergmin.inpsweb.domain.PghObligacionSubTipo;
import gob.osinergmin.inpsweb.domain.PghObligacionTipo;
import gob.osinergmin.mdicommon.domain.dto.ObligacionSubTipoDTO;
import gob.osinergmin.mdicommon.domain.dto.ObligacionTipoDTO;

import java.util.ArrayList;
import java.util.List;

/* OSINE_SFS-480 - RSIS 26 - Inicio */
public class ObligacionSubTipoBuilder {
	public static PghObligacionSubTipo getObligacionSubTipo(ObligacionSubTipoDTO ObligacionSubTipoDTO){
		PghObligacionSubTipo registro = null;
        if(ObligacionSubTipoDTO != null){
            registro = new PghObligacionSubTipo();
            if(ObligacionSubTipoDTO.getIdObligacionSubTipo() != null){
                registro.setIdObligacionSubTipo(ObligacionSubTipoDTO.getIdObligacionSubTipo());
            }
            registro.setNombre(ObligacionSubTipoDTO.getNombre());
            registro.setEstado(ObligacionSubTipoDTO.getEstado());
            registro.setIdentificadorSeleccion(ObligacionSubTipoDTO.getIdentificadorSeleccion());
            PghObligacionTipo pghObligacionTipo = new PghObligacionTipo();
            pghObligacionTipo.setIdObligacionTipo(ObligacionSubTipoDTO.getIdObligacionTipo().getIdObligacionTipo());
            registro.setIdObligacionTipo(pghObligacionTipo);
        }
        return registro;
    }
    
    public static List<ObligacionSubTipoDTO> toListObligacionSubTipoDto(List<PghObligacionSubTipo> lista) {
    	ObligacionSubTipoDTO registroDTO;
        List<ObligacionSubTipoDTO> retorno = new ArrayList<ObligacionSubTipoDTO>();
        if (lista != null) {
            for (PghObligacionSubTipo maestro : lista) {
                registroDTO = toObligacionSubTipoDto(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }
    
    public static ObligacionSubTipoDTO toObligacionSubTipoDto(PghObligacionSubTipo registro) {
    	ObligacionSubTipoDTO registroDTO = new ObligacionSubTipoDTO();
        
        registroDTO.setIdObligacionSubTipo(registro.getIdObligacionSubTipo());
        registroDTO.setNombre(registro.getNombre());
        registroDTO.setEstado(registro.getEstado());
        registroDTO.setIdentificadorSeleccion(registro.getIdentificadorSeleccion());
        if(registro.getIdObligacionTipo()!=null && registro.getIdObligacionTipo().getIdObligacionTipo()!=null){
        	ObligacionTipoDTO obligacionTipo = new ObligacionTipoDTO();
            obligacionTipo.setIdObligacionTipo(registro.getIdObligacionTipo().getIdObligacionTipo());
            registroDTO.setIdObligacionTipo(obligacionTipo);
        }
        
        return registroDTO;
    }
}
/* OSINE_SFS-480 - RSIS 26 - Fin */