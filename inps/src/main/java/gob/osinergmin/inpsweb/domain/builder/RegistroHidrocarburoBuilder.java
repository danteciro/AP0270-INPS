/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.MdiRegistroHidrocarburo;
import gob.osinergmin.mdicommon.domain.dto.RegistroHidrocarburoDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadSupervisadaDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zchaupis
 */
public class RegistroHidrocarburoBuilder {
    public static List<RegistroHidrocarburoDTO> toListRegistroHidrocarburoDTO(List<MdiRegistroHidrocarburo> lista) {
        RegistroHidrocarburoDTO registroDTO;
        List<RegistroHidrocarburoDTO> retorno = new ArrayList<RegistroHidrocarburoDTO>();
        if (lista != null) {
            for (MdiRegistroHidrocarburo maestro : lista) {
                registroDTO = toRegistroHidrocarburoDTO(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }

    public static RegistroHidrocarburoDTO toRegistroHidrocarburoDTO(MdiRegistroHidrocarburo registro) {
        RegistroHidrocarburoDTO registroDTO = new RegistroHidrocarburoDTO();
        if (registro.getIdRegistroHidrocarburo() != null) {
            registroDTO.setIdRegistroHidrocarburo(registro.getIdRegistroHidrocarburo());
        }
        if (registro.getNumeroRegistroHidrocarburo()!= null) {
            registroDTO.setNumeroRegistroHidrocarburo(registro.getNumeroRegistroHidrocarburo());
        }
        if (registro.getIdUnidadSupervisada()!= null) {
            UnidadSupervisadaDTO unidadSupervisadaDTO = new UnidadSupervisadaDTO();
            if(registro.getIdUnidadSupervisada().getIdUnidadSupervisada() != null){
                unidadSupervisadaDTO.setIdUnidadSupervisada(registro.getIdUnidadSupervisada().getIdUnidadSupervisada());
                registroDTO.setIdUnidadSupervisada(registro.getIdUnidadSupervisada().getIdUnidadSupervisada());
            }
            registroDTO.setUnidadSupervisadaDTO(unidadSupervisadaDTO);
        }
        if (registro.getFechaInicioSuspencion()!= null) {
            registroDTO.setFechaFinSuspencion(registro.getFechaFinSuspencion());
        }
        if (registro.getFechaFinSuspencion()!= null) {
            registroDTO.setFechaInicioSuspencion(registro.getFechaFinSuspencion());
        }
        if (registro.getFechaEmision()!= null) {
            registroDTO.setFechaEmision(registro.getFechaEmision());
        }
        if (registro.getFechaAprobacion()!= null) {
            registroDTO.setFechaAprobacion(registro.getFechaAprobacion());
        }
        return registroDTO;
    }
}
