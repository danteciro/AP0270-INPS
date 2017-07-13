/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghInfraccion;
import gob.osinergmin.mdicommon.domain.dto.InfraccionDTO;
import gob.osinergmin.mdicommon.domain.dto.ObligacionDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zchaupis
 */
public class InfraccionBuilder {
     public static List<InfraccionDTO> toListInfraccionDTO(List<PghInfraccion> lista) {
        InfraccionDTO registroDTO;
        List<InfraccionDTO> retorno = new ArrayList<InfraccionDTO>();
        if (lista != null) {
            for (PghInfraccion maestro : lista) {
                registroDTO = toPlazoDTO(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }

    public static InfraccionDTO toPlazoDTO(PghInfraccion registro) {
        InfraccionDTO registroDTO = new InfraccionDTO();
        if (registro.getIdInfraccion()!= null) {
            registroDTO.setIdInfraccion(registro.getIdInfraccion());
        }
        if (registro.getCodigo()!= null) {
            registroDTO.setCodigo(registro.getCodigo());
        }
        if (registro.getIdMedidaSeguridadMaestro()!= null) {
            registroDTO.setIdmedidaSeguridad(registro.getIdMedidaSeguridadMaestro());
        }
        if (registro.getIdObligacion() != null && registro.getIdObligacion().getIdObligacion() != null) {
                ObligacionDTO obligacionDTO = new ObligacionDTO();
                obligacionDTO.setIdObligacion(registro.getIdObligacion().getIdObligacion());
                obligacionDTO.setCodigoObligacion(registro.getIdObligacion().getCodigoObligacion());
                obligacionDTO.setDescripcion(registro.getIdObligacion().getDescripcion());
                registroDTO.setObligacionDTO(obligacionDTO);
        }
        /*OSINE_SFS-791 - RSIS 16 - Inicio*/
        if(registro.getDescripcionInfraccion()!=null){
            registroDTO.setDescripcionInfraccion(registro.getDescripcionInfraccion());
        }
        /*OSINE_SFS-791 - RSIS 16 - Fin*/
        return registroDTO;
    }
}
