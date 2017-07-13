/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghEstadoProceso;
import gob.osinergmin.mdicommon.domain.dto.EstadoProcesoDTO;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jpiro
 */
public class EstadoProcesoBuilder {
    public static List<EstadoProcesoDTO> toListEstadoProcesoDto(List<PghEstadoProceso> lista) {
        EstadoProcesoDTO registroDTO;
        List<EstadoProcesoDTO> retorno = new ArrayList<EstadoProcesoDTO>();
        if (lista != null) {
            for (PghEstadoProceso maestro : lista) {
                registroDTO = toEstadoProcesoDto(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    } 
    
    public static EstadoProcesoDTO toEstadoProcesoDto(PghEstadoProceso registro) {
        EstadoProcesoDTO registroDTO = new EstadoProcesoDTO();
        
        registroDTO.setIdEstadoProceso(registro.getIdEstadoProceso());
        registroDTO.setIdTipoEstadoProceso(registro.getIdTipoEstadoProceso());
        registroDTO.setIdentificadorEstado(registro.getIdentificadorEstado());
        registroDTO.setNombreEstado(registro.getNombreEstado());
        
        return registroDTO;
    }
}
