/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghPlazo;
import gob.osinergmin.mdicommon.domain.dto.PlazoDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zchaupis
 */
public class PlazoBuilder {

    public static List<PlazoDTO> toListPlazoDTO(List<PghPlazo> lista) {
        PlazoDTO registroDTO;
        List<PlazoDTO> retorno = new ArrayList<PlazoDTO>();
        if (lista != null) {
            for (PghPlazo maestro : lista) {
                registroDTO = toPlazoDTO(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }

    public static PlazoDTO toPlazoDTO(PghPlazo registro) {
        PlazoDTO registroDTO = new PlazoDTO();
        if (registro.getIdPlazo() != null) {
            registroDTO.setIdPlazo(registro.getIdPlazo());
        }
        if (registro.getDescripcion() != null) {
            registroDTO.setDescripcion(registro.getDescripcion());
        }
        if (registro.getCantidad() != null) {
            registroDTO.setCantidad(registro.getCantidad());
        }
        if (registro.getCodigoPlazo() != null) {
            registroDTO.setCodigoPlazo(registro.getCodigoPlazo());
        }
        if (registro.getIdUnidadMedidaMaestro() != null) {
            registroDTO.setIdUnidadMedidaMaestro(registro.getIdUnidadMedidaMaestro());
        }
        return registroDTO;
    }
}
