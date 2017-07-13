package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghComentarioComplemento;
import gob.osinergmin.mdicommon.domain.dto.ComentarioComplementoDTO;
import gob.osinergmin.mdicommon.domain.dto.ComplementoDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jpiro
 */
public class ComentarioComplementoBuilder {
    public static List<ComentarioComplementoDTO> toListComentarioComplementoDto(List<PghComentarioComplemento> lista) {
        ComentarioComplementoDTO registroDTO;
        List<ComentarioComplementoDTO> retorno = new ArrayList<ComentarioComplementoDTO>();
        if (lista != null) {
            for (PghComentarioComplemento maestro : lista) {
                registroDTO = toComentarioComplementoDto(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }
    
    public static ComentarioComplementoDTO toComentarioComplementoDto(PghComentarioComplemento registro) {
        ComentarioComplementoDTO registroDTO = new ComentarioComplementoDTO();
        
        registroDTO.setIdComentarioComplemento(registro.getIdComentarioComplemento());
        if(registro.getIdComplemento()!=null){
            ComplementoDTO complemento=ComplementoBuilder.toComplementoDto(registro.getIdComplemento());
            registroDTO.setComplemento(complemento);
        }
        
        return registroDTO;
    }
}
