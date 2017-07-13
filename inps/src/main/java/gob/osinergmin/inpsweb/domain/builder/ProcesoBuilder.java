/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghProceso;
import gob.osinergmin.mdicommon.domain.dto.ProcesoDTO;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jpiro
 */
public class ProcesoBuilder {
    public static List<ProcesoDTO> toListProcesoDto(List<PghProceso> lista) {
        ProcesoDTO registroDTO;
        List<ProcesoDTO> retorno = new ArrayList<ProcesoDTO>();
        if (lista != null) {
            for (PghProceso maestro : lista) {
                registroDTO = toProcesoDto(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    } 
    public static ProcesoDTO toProcesoDto(PghProceso registro) {
        ProcesoDTO registroDTO = new ProcesoDTO();
        
        registroDTO.setIdProceso(registro.getIdProceso());
        registroDTO.setDescripcion(registro.getDescripcion());
        
        return registroDTO;
    }
}
