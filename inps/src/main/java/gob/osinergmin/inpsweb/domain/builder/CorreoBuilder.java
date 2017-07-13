/**
* Resumen
* Objeto		: CorreoBuilder.java
* Descripción		: Constructor de Correo
* Fecha de Creación	: 11/05/2016
* PR de Creación	: OSINE_SFS-480
* Autor			: Luis García Reyna
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     11/05/2016      Luis García Reyna           Correo de Alerta a Empresa Supervisora cuando se le asigne Orden de Servicio
* 
*/

package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghCorreo;
import gob.osinergmin.mdicommon.domain.dto.CorreoDTO;
import java.util.ArrayList;
import java.util.List;

/* OSINE_SFS-480 - RSIS 12 - Inicio */
public class CorreoBuilder {
    public static CorreoDTO toCorreoDTO(PghCorreo registro) {
        CorreoDTO registroDTO = new CorreoDTO();
        if(registro!=null){
            registroDTO.setIdCorreo(registro.getIdCorreo());
            registroDTO.setCodigoFuncionalidad(registro.getCodigoFuncionalidad());
            registroDTO.setAsunto(registro.getAsunto());
            registroDTO.setMensaje(registro.getMensaje());
        }
        return registroDTO;
    }
	
    public static List<CorreoDTO> toListCorreoDto(List<PghCorreo> lista) {
        CorreoDTO registroDTO;
        List<CorreoDTO> retorno = new ArrayList<CorreoDTO>();
        if (lista != null) {
            for (PghCorreo maestro : lista) {
                registroDTO = toCorreoDTO(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }
}
/* OSINE_SFS-480 - RSIS 12 - Fin */