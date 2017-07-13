/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghEtapa;
import gob.osinergmin.mdicommon.domain.dto.EtapaDTO;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jpiro
 */
public class EtapaBuilder {
    public static List<EtapaDTO> toListEtapaDto(List<PghEtapa> lista) {
        EtapaDTO registroDTO;
        List<EtapaDTO> retorno = new ArrayList<EtapaDTO>();
        if (lista != null) {
            for (PghEtapa maestro : lista) {
                registroDTO = toEtapaDto(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    } 
    public static EtapaDTO toEtapaDto(PghEtapa registro) {
        EtapaDTO registroDTO = new EtapaDTO();
        
        registroDTO.setIdEtapa(registro.getIdEtapa());
        registroDTO.setDescripcion(registro.getDescripcion());
        
        return registroDTO;
    }
}
