package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghComentarioComplemento;
import gob.osinergmin.inpsweb.domain.PghComentarioDetsupervision;
import gob.osinergmin.inpsweb.domain.PghComplementoDetsupValor;
import gob.osinergmin.inpsweb.domain.PghComplementoDetsupervision;
import gob.osinergmin.mdicommon.domain.dto.ComplementoDetSupValorDTO;
import gob.osinergmin.mdicommon.domain.dto.ComplementoDetSupervisionDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jpiro
 */
public class ComplementoDetSupValorBuilder {
    public static PghComplementoDetsupValor toComplementoDetSupValorDomain(ComplementoDetSupValorDTO registroDTO) {
        PghComplementoDetsupValor registro = new PghComplementoDetsupValor();
        
        registro.setIdComplementoDetsupervision(new PghComplementoDetsupervision(registroDTO.getIdComplementoDetSupervision()));
        if(registroDTO.getValorId()!=null){
            registro.setValorId(registroDTO.getValorId());
        }
        registro.setValorDesc(registroDTO.getValorDesc());
        registro.setEstado(registroDTO.getEstado());
        
        return registro;   
    }
    
    public static List<ComplementoDetSupValorDTO> toListComplementoDetSupValorDto(List<PghComplementoDetsupValor> lista) {
        ComplementoDetSupValorDTO registroDTO;
        List<ComplementoDetSupValorDTO> retorno = new ArrayList<ComplementoDetSupValorDTO>();
        if (lista != null) {
            for (PghComplementoDetsupValor maestro : lista) {
                registroDTO = toComplementoDetSupValorDTO(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }
    
    public static ComplementoDetSupValorDTO toComplementoDetSupValorDTO(PghComplementoDetsupValor registro) {
        ComplementoDetSupValorDTO registroDTO = new ComplementoDetSupValorDTO();
        
        registroDTO.setIdComplementoDetSupValor(registro.getIdComplementoDetsupValor());
        if(registro.getIdComplementoDetsupervision()!=null){
            registroDTO.setIdComplementoDetSupervision(registro.getIdComplementoDetsupervision().getIdComplementoDetsupervision());
        }
        registroDTO.setValorId(registro.getValorId());
        registroDTO.setValorDesc(registro.getValorDesc());
                
        return registroDTO;   
    }
}
