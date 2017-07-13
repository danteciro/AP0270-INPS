/**
* Resumen
* Objeto		: ActividadBuilder.java
* Descripción		: Constructor de Actividad
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     09/05/2016      Mario Dioses Fernandez      Registrar en BD campo FECHA_INICIO_PLAZO y FECHA_FIN_PLAZO luego de CONCLUIR_OS, considerando Plazo_Descargo por Rubro (MDI_ACTIVIDAD)
* 
*/

package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.MdiActividad;
import gob.osinergmin.mdicommon.domain.dto.ActividadDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jpiro
 */
public class ActividadBuilder {
    public static List<ActividadDTO> toListActividadDto(List<MdiActividad> lista) {
        ActividadDTO registroDTO;
        List<ActividadDTO> retorno = new ArrayList<ActividadDTO>();
        if (lista != null) {
            for (MdiActividad maestro : lista) {
                registroDTO = toActividadDto(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    } 
    public static ActividadDTO toActividadDto(MdiActividad registro) {
        ActividadDTO registroDTO = new ActividadDTO();
        
        registroDTO.setIdActividad(registro.getIdActividad());
        registroDTO.setNombre(registro.getNombre());
        registroDTO.setCodigo(registro.getCodigo());
        registroDTO.setIdActividadPadre(registro.getIdActividadPadre());
        /* OSINE_SFS-480 - RSIS 17 - Inicio */
        registroDTO.setPlazoDescargo(registro.getPlazoDescargo());
        /* OSINE_SFS-480 - RSIS 17 - Fin */
        return registroDTO;
    }

}