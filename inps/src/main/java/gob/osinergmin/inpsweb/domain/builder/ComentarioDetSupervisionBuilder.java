package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghComentarioDetsupervision;
import gob.osinergmin.inpsweb.domain.PghComentarioIncumplimiento;
import gob.osinergmin.inpsweb.domain.PghDetalleSupervision;
import gob.osinergmin.inpsweb.domain.PghEscenarioIncumplido;
import gob.osinergmin.mdicommon.domain.dto.ComentarioDetSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.ComentarioIncumplimientoDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jpiro
 */
public class ComentarioDetSupervisionBuilder {
    public static PghComentarioDetsupervision toComentarioDetSupervisionDomain(ComentarioDetSupervisionDTO registroDTO) {
        PghComentarioDetsupervision registro = new PghComentarioDetsupervision();
        
        registro.setIdDetalleSupervision(new PghDetalleSupervision(registroDTO.getDetalleSupervision().getIdDetalleSupervision()));
        if(registroDTO.getIdEscenarioIncumplido()!=null){
            registro.setIdEscenarioIncumplido(new PghEscenarioIncumplido(registroDTO.getIdEscenarioIncumplido()));
        }
        registro.setIdComentarioIncumplimiento(new PghComentarioIncumplimiento(registroDTO.getComentarioIncumplimiento().getIdComentarioIncumplimiento()));
        registro.setEstado(registroDTO.getEstado());
        
        return registro;   
    }
    
    public static List<ComentarioDetSupervisionDTO> toListComentarioDetSupervisionDto(List<PghComentarioDetsupervision> lista) {
        ComentarioDetSupervisionDTO registroDTO;
        List<ComentarioDetSupervisionDTO> retorno = new ArrayList<ComentarioDetSupervisionDTO>();
        if (lista != null) {
            for (PghComentarioDetsupervision maestro : lista) {
                registroDTO = toComentarioDetSupervisionDTO(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }
    
    public static ComentarioDetSupervisionDTO toComentarioDetSupervisionDTO(PghComentarioDetsupervision registro) {
        ComentarioDetSupervisionDTO registroDTO = new ComentarioDetSupervisionDTO();
        
        registroDTO.setIdComentarioDetSupervision(registro.getIdComentarioDetsupervision());
        if(registro.getIdComentarioIncumplimiento()!=null){
            registroDTO.setComentarioIncumplimiento(new ComentarioIncumplimientoDTO(registro.getIdComentarioIncumplimiento().getIdComentarioIncumplimiento()));
        }
        if(registro.getIdDetalleSupervision()!=null){
            registroDTO.setDetalleSupervision(new DetalleSupervisionDTO(registro.getIdDetalleSupervision().getIdDetalleSupervision()));
        }
        if(registro.getIdEscenarioIncumplido()!=null){
            registroDTO.setIdEscenarioIncumplido(registro.getIdEscenarioIncumplido().getIdEscenarioIncumplido());
            if(registro.getIdEscenarioIncumplido().getIdEsceIncumplimiento()!=null){
                registroDTO.setIdEsceIncumplimiento(registro.getIdEscenarioIncumplido().getIdEsceIncumplimiento().getIdEsceIncumplimiento());
            }
        }
        if (registro.getIdComentarioIncumplimiento() != null) {
            ComentarioIncumplimientoDTO comn = new ComentarioIncumplimientoDTO();
            comn.setIdComentarioIncumplimiento(registro.getIdComentarioIncumplimiento().getIdComentarioIncumplimiento());
            if (registro.getIdComentarioIncumplimiento().getDescripcion() != null) {
                comn.setDescripcion(registro.getIdComentarioIncumplimiento().getDescripcion());
            }
            registroDTO.setComentarioIncumplimiento(comn);
            
        }
        return  registroDTO;
    }
}
