/**
*
* Resumen		
* Objeto		: EscenarioIncumplidoBuilder.java
* Descripción		: Constructor DetalleSupervision
* Fecha de Creación	: 05/09/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-791  |  05/09/2016   |   Luis García Reyna          |     Listar Escenario Incumplido de la Supervisión.
* OSINE791-RSIS25|  08/09/2016   |	Alexander Vilca Narvaez 	| Crear la funcionalidad para consultar el comentario de ejecución de medida ingresado para las infracciones identificadas DSR-CRITICIDAD
*                |               |                              |
* 
*/

package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.MdiMaestroColumna;
import gob.osinergmin.inpsweb.domain.PghDetalleSupervision;
import gob.osinergmin.inpsweb.domain.PghEscenarioIncumplido;
import gob.osinergmin.inpsweb.domain.PghEscenarioIncumplimiento;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.EscenarioIncumplidoDTO;
import gob.osinergmin.mdicommon.domain.dto.EscenarioIncumplimientoDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EscenarioIncumplidoBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(EscenarioIncumplidoBuilder.class);
    public static EscenarioIncumplidoDTO toEscenarioIncumplidoDTO(PghEscenarioIncumplido registro) {
        EscenarioIncumplidoDTO registroDTO = new EscenarioIncumplidoDTO();
        
        registroDTO.setIdEscenarioIncumplido(registro.getIdEscenarioIncumplido());
        
        if (registro.getIdDetalleSupervision() != null && registro.getIdDetalleSupervision().getIdDetalleSupervision() != null) {
            registroDTO.setIdDetalleSupervision(registro.getIdDetalleSupervision().getIdDetalleSupervision());
        }
        if(registro.getIdEsceIncumplimiento() != null && registro.getIdEsceIncumplimiento().getIdEsceIncumplimiento() !=null){
            EscenarioIncumplimientoDTO escenarioIncumplimientoDTO = new EscenarioIncumplimientoDTO();
            escenarioIncumplimientoDTO.setIdEsceIncumplimiento(registro.getIdEsceIncumplimiento().getIdEsceIncumplimiento());
            escenarioIncumplimientoDTO.setIdEsceIncuMaestro(new MaestroColumnaDTO(registro.getIdEsceIncumplimiento().getIdEsceIncuMaestro().getIdMaestroColumna(), registro.getIdEsceIncumplimiento().getIdEsceIncuMaestro().getDescripcion()));
            registroDTO.setEscenarioIncumplimientoDTO(escenarioIncumplimientoDTO);
        }
        registroDTO.setComentarioEjecucion(registro.getComentarioEjecucion());
        
        return registroDTO;   
    }
    
    public static List<EscenarioIncumplidoDTO> toListEscenarioIncumplidoDto(List<PghEscenarioIncumplido> lista) {
        EscenarioIncumplidoDTO registroDTO;
        List<EscenarioIncumplidoDTO> retorno = new ArrayList<EscenarioIncumplidoDTO>();
        if (lista != null) {
            for (PghEscenarioIncumplido maestro : lista) {
                registroDTO = toEscenarioIncumplidoDTO(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }
    
    public static PghEscenarioIncumplido toEscenarioIncumplidoDomain(EscenarioIncumplidoDTO registroDTO) {
        PghEscenarioIncumplido registro = new PghEscenarioIncumplido();
        
        if(registroDTO.getIdDetalleSupervision()!=null){
            registro.setIdDetalleSupervision(new PghDetalleSupervision(registroDTO.getIdDetalleSupervision()));
        }
        if(registroDTO.getEscenarioIncumplimientoDTO()!=null && registroDTO.getEscenarioIncumplimientoDTO().getIdEsceIncumplimiento()!=null){
            registro.setIdEsceIncumplimiento(new PghEscenarioIncumplimiento(registroDTO.getEscenarioIncumplimientoDTO().getIdEsceIncumplimiento()));
        }
        registro.setComentarioEjecucion(registroDTO.getComentarioEjecucion());
        registro.setEstado(registroDTO.getEstado());
        
        return registro;   
    }
    
}
