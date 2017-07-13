/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghFlujoSiged;
import gob.osinergmin.mdicommon.domain.dto.FlujoSigedDTO;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jpiro
 */
public class FlujoSigedBuilder {
    public static List<FlujoSigedDTO> toListFlujoSigedDto(List<PghFlujoSiged> lista) {
        FlujoSigedDTO registroDTO;
        List<FlujoSigedDTO> retorno = new ArrayList<FlujoSigedDTO>();
        if (lista != null) {
            for (PghFlujoSiged maestro : lista) {
                registroDTO = toFlujoSigedDto(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    } 
    
    public static FlujoSigedDTO toFlujoSigedDto(PghFlujoSiged registro) {
        FlujoSigedDTO registroDTO = new FlujoSigedDTO();
        
        registroDTO.setIdFlujoSiged(registro.getIdFlujoSiged());
        registroDTO.setNombreFlujoSiged(registro.getNombreFlujoSiged());
        registroDTO.setCodigoSiged(registro.getCodigoSiged());
        
        return registroDTO;
    }
}
