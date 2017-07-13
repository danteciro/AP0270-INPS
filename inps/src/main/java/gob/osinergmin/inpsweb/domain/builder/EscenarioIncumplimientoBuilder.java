/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghEscenarioIncumplimiento;
import gob.osinergmin.mdicommon.domain.dto.EscenarioIncumplimientoDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jpiro
 */
public class EscenarioIncumplimientoBuilder {
    public static List<EscenarioIncumplimientoDTO> toListEscenarioIncumplimientoDto(List<PghEscenarioIncumplimiento> lista) {
        EscenarioIncumplimientoDTO registroDTO;
        List<EscenarioIncumplimientoDTO> retorno = new ArrayList<EscenarioIncumplimientoDTO>();
        if (lista != null) {
            for (PghEscenarioIncumplimiento maestro : lista) {
                registroDTO = toEscenarioIncumplimientoDto(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }
    
    public static EscenarioIncumplimientoDTO toEscenarioIncumplimientoDto(PghEscenarioIncumplimiento registro) {
        EscenarioIncumplimientoDTO registroDTO = new EscenarioIncumplimientoDTO();
        
        registroDTO.setIdEsceIncumplimiento(registro.getIdEsceIncumplimiento());
        if(registro.getIdEsceIncuMaestro()!=null){
            MaestroColumnaDTO esceIncuMaes = new MaestroColumnaDTO(registro.getIdEsceIncuMaestro().getIdMaestroColumna(),registro.getIdEsceIncuMaestro().getDescripcion());
            registroDTO.setIdEsceIncuMaestro(esceIncuMaes);
        }
        if(registro.getIdInfraccion()!=null){
            registroDTO.setIdInfraccion(registro.getIdInfraccion().getIdInfraccion());
        }
                
        return registroDTO;
    }
}
