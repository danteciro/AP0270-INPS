/**
* Resumen		
* Objeto		: DestinatarioCorreoServiceNeg.java
* Descripción		: Clase que proporciona una interfaz para la implementación de métodos
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-791
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_791-RSIS19 | 06/09/2016 | Zosimo Chaupis Santur | Correo de Notificacion al Supervisor Regional para supervisión de una orden de supervisión DSR-CRITICIDAD
 
*/
package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghDestinatarioCorreo;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.DestinatarioCorreoDTO;
import gob.osinergmin.mdicommon.domain.dto.PersonalDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zchaupis
 */
public class DestinatarioCorreoBuilder {

    /*<!--  OSINE791 - RSIS19 - Inicio -->*/
    public static DestinatarioCorreoDTO toDestinatarioCorreoDTO(PghDestinatarioCorreo registro) {
        DestinatarioCorreoDTO registroDTO = new DestinatarioCorreoDTO();
        if (registro != null) {
            registroDTO.setIdDestinatarioCorreo(registro.getIdDestinatarioCorreo());
            if (registro.getIdPersonal() != null && registro.getIdPersonal().getIdPersonal() != null) {
                PersonalDTO personalDTO = new PersonalDTO();
                personalDTO.setIdPersonal(registro.getIdPersonal().getIdPersonal());
                if (registro.getIdPersonal().getNombre() != null && !Constantes.CONSTANTE_VACIA.equals(registro.getIdPersonal().getNombre().trim())) {
                    personalDTO.setNombre(registro.getIdPersonal().getNombre());
                }
                if (registro.getIdPersonal().getApellidoPaterno() != null && !Constantes.CONSTANTE_VACIA.equals(registro.getIdPersonal().getApellidoPaterno().trim())) {
                    personalDTO.setApellidoPaterno(registro.getIdPersonal().getApellidoPaterno());
                }
                if (registro.getIdPersonal().getApellidoMaterno() != null && !Constantes.CONSTANTE_VACIA.equals(registro.getIdPersonal().getApellidoMaterno().trim())) {
                    personalDTO.setApellidoMaterno(registro.getIdPersonal().getApellidoMaterno());
                }
                if (registro.getIdPersonal().getNombreCompleto() != null && !Constantes.CONSTANTE_VACIA.equals(registro.getIdPersonal().getNombreCompleto().trim())) {
                    personalDTO.setNombreCompleto(registro.getIdPersonal().getNombreCompleto());
                }
                if (registro.getIdPersonal().getCorreoElectronico() != null && !Constantes.CONSTANTE_VACIA.equals(registro.getIdPersonal().getCorreoElectronico().trim())) {
                    personalDTO.setCorreoElectronico(registro.getIdPersonal().getCorreoElectronico());
                }
                registroDTO.setPersonalDTO(personalDTO);
            }
        }
        return registroDTO;
    }

    public static List<DestinatarioCorreoDTO> toListDestinatarioCorreoDTO(List<PghDestinatarioCorreo> lista) {
        DestinatarioCorreoDTO registroDTO;
        List<DestinatarioCorreoDTO> retorno = new ArrayList<DestinatarioCorreoDTO>();
        if (lista != null) {
            for (PghDestinatarioCorreo maestro : lista) {
                registroDTO = toDestinatarioCorreoDTO(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }
    /*<!--  OSINE791 - RSIS19 - Fin -->*/
}
