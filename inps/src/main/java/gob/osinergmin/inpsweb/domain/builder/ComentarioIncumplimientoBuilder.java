/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 /**
 * Resumen 
 * Objeto				: ComentarioIncumplimientoBuilder.java 
 * Descripción	        : 
 * Fecha de Creación	: 
 * PR de Creación		: 
 * Autor				: 
 * ---------------------------------------------------------------------------------------------------
 * Modificaciones   Fecha             Nombre               Descripción
 * ---------------------------------------------------------------------------------------------------
 * OSINE_MAN_DSR_0037  | 18/06/2017     | Carlos Quijano Chavez::ADAPTER  | Actualiza comentarios adicionales
 */
package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghComentarioIncumplimiento;
import gob.osinergmin.mdicommon.domain.dto.ComentarioIncumplimientoDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import java.util.ArrayList;
import java.util.List;
/* OSINE_MAN_DSR_0037 - Inicio */
import gob.osinergmin.inpsweb.dto.ComentarioDetalleSupervisionOpcionalDTO;
import gob.osinergmin.inpsweb.domain.PghComentarioDetsupervisionOpcional;
/* OSINE_MAN_DSR_0037 - Fin */
/**
 *
 * @author jpiro
 */
public class ComentarioIncumplimientoBuilder {
    public static List<ComentarioIncumplimientoDTO> toListComentarioIncumplimientoDto(List<PghComentarioIncumplimiento> lista) {
        ComentarioIncumplimientoDTO registroDTO;
        List<ComentarioIncumplimientoDTO> retorno = new ArrayList<ComentarioIncumplimientoDTO>();
        if (lista != null) {
            for (PghComentarioIncumplimiento maestro : lista) {
                registroDTO = toComentarioIncumplimientoDto(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }
    
    public static ComentarioIncumplimientoDTO toComentarioIncumplimientoDto(PghComentarioIncumplimiento registro) {
        ComentarioIncumplimientoDTO registroDTO = new ComentarioIncumplimientoDTO();
        
        registroDTO.setIdComentarioIncumplimiento(registro.getIdComentarioIncumplimiento());
        registroDTO.setDescripcion(registro.getDescripcion());
        registroDTO.setCountComentarioComplemento(registro.getCountComentarioComplemento());
        
        return registroDTO;
    }
	/* OSINE_MAN_DSR_0037 - Inicio */
    public static List<ComentarioDetalleSupervisionOpcionalDTO> toComentarioIncumplimientoOpcionalDto(List<PghComentarioDetsupervisionOpcional> lista) {
    	ComentarioDetalleSupervisionOpcionalDTO registroDTO;
        List<ComentarioDetalleSupervisionOpcionalDTO> retorno = new ArrayList<ComentarioDetalleSupervisionOpcionalDTO>();
        if (lista != null) {
            for (PghComentarioDetsupervisionOpcional maestro : lista) {
                registroDTO = toComentarioIncumplimientoOpcionalDto(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }
    
    public static ComentarioDetalleSupervisionOpcionalDTO toComentarioIncumplimientoOpcionalDto(PghComentarioDetsupervisionOpcional registro) {
    	ComentarioDetalleSupervisionOpcionalDTO registroDTO = new ComentarioDetalleSupervisionOpcionalDTO();
        
        registroDTO.setDescripcion(registro.getDescripcion());;
        registroDTO.setIdComentarioDetalleSupervisionOpcional(registro.getIdComentarioDetsupervisionOpcional());

        return registroDTO;
    }
    /* OSINE_MAN_DSR_0037 - Fin */
}
