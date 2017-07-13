/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain.builder;


import gob.osinergmin.inpsweb.domain.PghResultadoSupervision;
import gob.osinergmin.mdicommon.domain.dto.ResultadoSupervisionDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zchaupis
 */
public class ResultadoSupervisionBuilder {

    public static ResultadoSupervisionDTO toResultadoSupervisionDTO(PghResultadoSupervision registro) {
        ResultadoSupervisionDTO registroDTO = new ResultadoSupervisionDTO();
        if (registro != null) {
            registroDTO.setIdResultadosupervision(registro.getIdResultadoSupervision());
            registroDTO.setDescripcion(registro.getDescripcion());
            registroDTO.setCssIcono(registro.getCssIcono());
            registroDTO.setEstado(registro.getEstado());
            if (registro.getCodigo() != null) {
                registroDTO.setCodigo(registro.getCodigo());
            }
        }
        return registroDTO;
    }
    public static List<ResultadoSupervisionDTO> toListResultadoSupervisionDto(List<PghResultadoSupervision> lista) {
        ResultadoSupervisionDTO registroDTO;
        List<ResultadoSupervisionDTO> retorno = new ArrayList<ResultadoSupervisionDTO>();
        if (lista != null) {
            for (PghResultadoSupervision maestro : lista) {
                registroDTO = toResultadoSupervisionDTO(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }
}
