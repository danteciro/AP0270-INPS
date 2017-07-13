/**
* Resumen
* Objeto		: ObligacionTipoBuilder.java
* Descripción		: Constructor de ObligacionTipo
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     24/05/2016      Giancarlo Villanueva Andrade        Crear componente de selección de "subtipo de supervisión".Relacionar y adecuar el subtipo de supervisión, el cual deberá depender del tipo de supervisión seleccionado.
*
*/

package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghExpediente;
import gob.osinergmin.inpsweb.domain.PghObligacionTipo;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteGSMDTO;
import gob.osinergmin.mdicommon.domain.dto.ObligacionTipoDTO;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jpiro
 */
public class ObligacionTipoBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(ObligacionTipoBuilder.class);  
    
    public static List<ObligacionTipoDTO> toListObligacionTipoDto(List<PghObligacionTipo> lista) {
        ObligacionTipoDTO registroDTO;
        List<ObligacionTipoDTO> retorno = new ArrayList<ObligacionTipoDTO>();
        if (lista != null) {
            for (PghObligacionTipo maestro : lista) {
                registroDTO = toObligacionTipoDto(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    } 
    
    public static ObligacionTipoDTO toObligacionTipoDto(PghObligacionTipo registro) {
        ObligacionTipoDTO registroDTO = new ObligacionTipoDTO();
        if(registro.getIdObligacionTipo()!=null){
        	registroDTO.setIdObligacionTipo(registro.getIdObligacionTipo());
        }
        if(registro.getNombre()!=null){
        	registroDTO.setNombre(registro.getNombre());	
        }        
        /* OSINE_SFS-480 - RSIS 26 - Inicio */
        if(registro.getTieneMuestra()!=null){
        registroDTO.setTieneMuestra(registro.getTieneMuestra());	
        }
        /* OSINE_SFS-480 - RSIS 26 - Fin */
        return registroDTO;
    }
        /* OSINE_SFS-480 - RSIS 26 - Inicio */
	public static List<ObligacionTipoDTO> toListObligacionTipoxSupervisionMuestralDto(List<Object[]> lista) {
		ObligacionTipoDTO registroDTO;
        List<ObligacionTipoDTO> retorno = new ArrayList<ObligacionTipoDTO>();
        if (lista != null) {
            for (Object[] maestro : lista) {
                registroDTO = toObligacionTipoSupervisionMuestralDto(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
	}

	private static ObligacionTipoDTO toObligacionTipoSupervisionMuestralDto(Object[] registro) {
		ObligacionTipoDTO registroDTO = new ObligacionTipoDTO();        
        registroDTO.setIdObligacionTipo(new Long(registro[0].toString()));
        registroDTO.setNombre(registro[1].toString());
        if(registro[2]!=null){
        registroDTO.setTieneMuestra(new Long(registro[2].toString()));	
        }
        
        return registroDTO;
	}
	/* OSINE_SFS-480 - RSIS 26 - Fin */
	/* OSINE_SFS-1344 - Inicio */
	public static List<ObligacionTipoDTO> toListObligacionTipoxDivisionDto(List<PghObligacionTipo> lista) {
		ObligacionTipoDTO registroDTO;
        List<ObligacionTipoDTO> retorno = new ArrayList<ObligacionTipoDTO>();
        if (lista != null) {
            for (PghObligacionTipo maestro : lista) {
                registroDTO = toObligacionTipoDto(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
	}
	/* OSINE_SFS-1344 - Fin */
}
