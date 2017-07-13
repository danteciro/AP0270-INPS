package gob.osinergmin.inpsweb.domain.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import gob.osinergmin.inpsweb.domain.MdiMaestroColumna;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;


/**
 * Clase para convertir MdiMaestroColumna a MaestroColumnaDTO
 * @author dmedrano
 *
 */
public class MaestroColumnaBuilder {

    public static MaestroColumnaDTO getMaestroColumnaDTO(MdiMaestroColumna maestroColumna, boolean includeMaestroTabla) {
        MaestroColumnaDTO maestroColumnaDTO = null;
        if(maestroColumna!=null) {
            maestroColumnaDTO = new MaestroColumnaDTO();
            maestroColumnaDTO.setEstado(maestroColumna.getEstado());			
            maestroColumnaDTO.setCodigo(maestroColumna.getCodigo());
            maestroColumnaDTO.setDescripcion(maestroColumna.getDescripcion());
            maestroColumnaDTO.setIdMaestroColumna(maestroColumna.getIdMaestroColumna());	
        }
        return maestroColumnaDTO;
    }

    public static MaestroColumnaDTO getMaestroColumnaDTO(MdiMaestroColumna maestroColumna) {
        return getMaestroColumnaDTO(maestroColumna, true);
    }

    public static List<MaestroColumnaDTO> getMaestroColumnaDTOList(List<MdiMaestroColumna> listaMaestroColumna) {
        List<MaestroColumnaDTO> listaMaestroColumnaDTO = new ArrayList<MaestroColumnaDTO>();
        if(!CollectionUtils.isEmpty(listaMaestroColumna)) {
            MaestroColumnaDTO maestroColumnaDTO = null;
            for(MdiMaestroColumna maestroColumna : listaMaestroColumna) {
                    maestroColumnaDTO = getMaestroColumnaDTO(maestroColumna);
                    listaMaestroColumnaDTO.add(maestroColumnaDTO);
            }
        }
        return listaMaestroColumnaDTO;
    }

    public static MdiMaestroColumna getMdiMaestroColumna(MaestroColumnaDTO maestroColumnaDTO) {
        MdiMaestroColumna maestroColumna = null;
        if(maestroColumnaDTO!=null){
            maestroColumna = new MdiMaestroColumna();
            maestroColumna.setDescripcion(maestroColumnaDTO.getDescripcion());
            maestroColumna.setCodigo(maestroColumnaDTO.getCodigo());
            maestroColumna.setIdMaestroColumna(maestroColumnaDTO.getIdMaestroColumna());
            maestroColumna.setEstado(maestroColumnaDTO.getEstado());
        }
        return maestroColumna;
    }

    public static MdiMaestroColumna getSimpleMdiMaestroColumna(MaestroColumnaDTO maestroColumnaDTO){
        MdiMaestroColumna maestroColumna = null;
        if(maestroColumnaDTO!=null){
            maestroColumna = new MdiMaestroColumna();
            maestroColumna.setIdMaestroColumna(maestroColumnaDTO.getIdMaestroColumna());			
        }
        return maestroColumna;
    }
}
