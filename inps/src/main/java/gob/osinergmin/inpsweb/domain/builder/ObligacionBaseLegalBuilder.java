/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghObligacionBaseLegal;
import gob.osinergmin.mdicommon.domain.dto.BaseLegalDTO;
import gob.osinergmin.mdicommon.domain.dto.ObligacionBaseLegalDTO;
import gob.osinergmin.mdicommon.domain.dto.ObligacionDTO;
import gob.osinergmin.mdicommon.domain.dto.PlazoDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zchaupis
 */
public class ObligacionBaseLegalBuilder {
    
    public static List<ObligacionBaseLegalDTO> toListObligacionBaseLegalDTO(List<PghObligacionBaseLegal> lista) {
        ObligacionBaseLegalDTO registroDTO;
        List<ObligacionBaseLegalDTO> retorno = new ArrayList<ObligacionBaseLegalDTO>();
        if (lista != null) {
            for (PghObligacionBaseLegal maestro : lista) {
                registroDTO = toObligacionBaseLegalDTO(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }

    public static ObligacionBaseLegalDTO toObligacionBaseLegalDTO(PghObligacionBaseLegal registro) {
        ObligacionBaseLegalDTO registroDTO = new ObligacionBaseLegalDTO();
        if (registro.getIdBaseLegal()!= null) {
            BaseLegalDTO baseLegalDTO = new  BaseLegalDTO();
            baseLegalDTO.setIdBaseLegal(registro.getIdBaseLegal().getIdBaseLegal());
            if (registro.getIdBaseLegal().getDescripcion()!= null) {
                baseLegalDTO.setDescripcionGeneralBaseLegal(registro.getIdBaseLegal().getDescripcion());
            }
            registroDTO.setBaseLegalDTO(baseLegalDTO);
        }
        if (registro.getIdObligacion()!= null) {
            ObligacionDTO obligacionDTO = new ObligacionDTO();
            obligacionDTO.setIdObligacion(registro.getIdObligacion().getIdObligacion());
            registroDTO.setObligacionDTO(obligacionDTO);
        }
        return registroDTO;
    }
}
