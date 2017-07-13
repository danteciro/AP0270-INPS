/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghTramite;
import gob.osinergmin.mdicommon.domain.dto.TramiteDTO;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jpiro
 */
public class TramiteBuilder {
    public static List<TramiteDTO> toListTramiteDto(List<PghTramite> lista) {
        TramiteDTO registroDTO;
        List<TramiteDTO> retorno = new ArrayList<TramiteDTO>();
        if (lista != null) {
            for (PghTramite maestro : lista) {
                registroDTO = toTramiteDto(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    } 
    public static TramiteDTO toTramiteDto(PghTramite registro) {
        TramiteDTO registroDTO = new TramiteDTO();
        
        registroDTO.setIdTramite(registro.getIdTramite());
        registroDTO.setDescripcion(registro.getDescripcion());
        
        return registroDTO;
    }
}
