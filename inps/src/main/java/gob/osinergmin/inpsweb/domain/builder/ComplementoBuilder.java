package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghComplemento;
import gob.osinergmin.mdicommon.domain.dto.ComplementoDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jpiro
 */
public class ComplementoBuilder {
    public static List<ComplementoDTO> toListComplementoDto(List<PghComplemento> lista) {
        ComplementoDTO registroDTO;
        List<ComplementoDTO> retorno = new ArrayList<ComplementoDTO>();
        if (lista != null) {
            for (PghComplemento maestro : lista) {
                registroDTO = toComplementoDto(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }
    
    public static ComplementoDTO toComplementoDto(PghComplemento registro) {
        ComplementoDTO registroDTO = new ComplementoDTO();
        
        registroDTO.setIdComplemento(registro.getIdComplemento());
        registroDTO.setEtiqueta(registro.getEtiqueta());
        registroDTO.setEtiquetaComentario(registro.getEtiquetaComentario());
        registroDTO.setValidacion(registro.getValidacion());
        registroDTO.setFormato(registro.getFormato());
        registroDTO.setLongitud(registro.getLongitud());
        registroDTO.setValorMaximo(registro.getValorMaximo());
        registroDTO.setValorMinimo(registro.getValorMinimo());
        if(registro.getCodTipo()!=null){
            registroDTO.setCodTipo(new MaestroColumnaDTO(registro.getCodTipo().getIdMaestroColumna(),registro.getCodTipo().getDescripcion(),registro.getCodTipo().getCodigo()));
        }
        if(registro.getOrigenDatos()!=null){
            registroDTO.setOrigenDatos(new MaestroColumnaDTO(registro.getOrigenDatos().getIdMaestroColumna(),registro.getOrigenDatos().getDescripcion(),registro.getOrigenDatos().getCodigo()));
        }     
        registroDTO.setDominioMaestroColumna(registro.getDominioMaestroColumna());
        registroDTO.setAplicacionMaestroColumna(registro.getAplicacionMaestroColumna());
        registroDTO.setVistaNombre(registro.getVistaNombre());
        registroDTO.setVistaCampoId(registro.getVistaCampoId());
        registroDTO.setVistaCampoDesc(registro.getVistaCampoDesc());
        if(registro.getVistaCampoFiltro()!=null){
            registroDTO.setVistaCampoFiltro(new MaestroColumnaDTO(registro.getVistaCampoFiltro().getIdMaestroColumna(), registro.getVistaCampoFiltro().getDescripcion(), registro.getVistaCampoFiltro().getCodigo(),registro.getVistaCampoFiltro().getAplicacion()));
        }
        return registroDTO;
    }
}
