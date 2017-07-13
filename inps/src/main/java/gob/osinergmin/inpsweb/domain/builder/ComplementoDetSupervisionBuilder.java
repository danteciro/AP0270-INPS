package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghComentarioComplemento;
import gob.osinergmin.inpsweb.domain.PghComentarioDetsupervision;
import gob.osinergmin.inpsweb.domain.PghComplementoDetsupervision;
import gob.osinergmin.mdicommon.domain.dto.ComentarioComplementoDTO;
import gob.osinergmin.mdicommon.domain.dto.ComplementoDTO;
import gob.osinergmin.mdicommon.domain.dto.ComplementoDetSupervisionDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jpiro
 */
public class ComplementoDetSupervisionBuilder {
    public static PghComplementoDetsupervision toComplementoDetSupervisionDomain(ComplementoDetSupervisionDTO registroDTO) {
        PghComplementoDetsupervision registro = new PghComplementoDetsupervision();
        
        registro.setIdComentarioComplemento(new PghComentarioComplemento(registroDTO.getIdComentarioComplemento()));
        registro.setIdComentarioDetsupervision(new PghComentarioDetsupervision(registroDTO.getIdComentarioDetSupervision()));
        registro.setEstado(registroDTO.getEstado());
        
        return registro;   
    }
    
    public static List<ComplementoDetSupervisionDTO> toListComplementoDetSupervisionDto(List<PghComplementoDetsupervision> lista) {
        ComplementoDetSupervisionDTO registroDTO;
        List<ComplementoDetSupervisionDTO> retorno = new ArrayList<ComplementoDetSupervisionDTO>();
        if (lista != null) {
            for (PghComplementoDetsupervision maestro : lista) {
                registroDTO = toComplementoDetSupervisionDTO(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }
    
    public static ComplementoDetSupervisionDTO toComplementoDetSupervisionDTO(PghComplementoDetsupervision registro) {
        ComplementoDetSupervisionDTO registroDTO = new ComplementoDetSupervisionDTO();
        
        registroDTO.setIdComplementoDetSupervision(registro.getIdComplementoDetsupervision());
        if(registro.getIdComentarioComplemento()!=null){
            registroDTO.setIdComentarioComplemento(registro.getIdComentarioComplemento().getIdComentarioComplemento());
            if(registro.getIdComentarioComplemento().getIdComplemento()!=null){
                ComentarioComplementoDTO comentarioComplemento = new ComentarioComplementoDTO();
                comentarioComplemento.setIdComentarioComplemento(registro.getIdComentarioComplemento().getIdComentarioComplemento());
                ComplementoDTO complemento=new ComplementoDTO();
                complemento.setEtiquetaComentario(registro.getIdComentarioComplemento().getIdComplemento().getEtiquetaComentario());
                comentarioComplemento.setComplemento(complemento);
                registroDTO.setComentarioComplemento(comentarioComplemento);
            }
        }
        if(registro.getIdComentarioDetsupervision()!=null){
            registroDTO.setIdComentarioDetSupervision(registro.getIdComentarioDetsupervision().getIdComentarioDetsupervision());
        }
        return registroDTO;   
    }
}
