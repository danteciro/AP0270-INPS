/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 /**
 * Resumen 
 * Objeto				: ObligacionDsrBuilder.java 
 * Descripción	        : 
 * Fecha de Creación	: 
 * PR de Creación		: 
 * Autor				: 
 * ---------------------------------------------------------------------------------------------------
 * Modificaciones   Fecha             Nombre               Descripción
 * ---------------------------------------------------------------------------------------------------
 * OSINE_MAN_DSR_0025  | 18/06/2017     | Carlos Quijano Chavez::ADAPTER  | Agrega suspension parcial y definitiva
 */
package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghDetalleSupervision;
import gob.osinergmin.inpsweb.domain.PghProductoSuspender;
import gob.osinergmin.mdicommon.domain.dto.DireccionUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.ObligacionDsrDTO;
import gob.osinergmin.mdicommon.domain.dto.ProductoDsrScopDTO;
import gob.osinergmin.mdicommon.domain.dto.UbigeoDTO;

import java.util.ArrayList;
import java.util.List;
/* OSINE_MAN_DSR_0025 - Inicio */
import gob.osinergmin.inpsweb.dto.ObligacionDsrUpdtDTO;
/* OSINE_MAN_DSR_0025 - Fin */
/**
 *
 * @author zchaupis
 */
public class ObligacionDsrBuilder {

	/* OSINE791 – RSIS7 - Inicio */
    public static List<ObligacionDsrDTO> getObligacionDsrDTO(List<PghDetalleSupervision> lista) {
        ObligacionDsrDTO registroDTO;
        List<ObligacionDsrDTO> retorno = new ArrayList<ObligacionDsrDTO>();
        if (lista != null) {
            for (PghDetalleSupervision maestro : lista) {
                registroDTO = toObligacionDsrDTO(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }

    public static ObligacionDsrDTO toObligacionDsrDTO(PghDetalleSupervision registro) {
        ObligacionDsrDTO registroDTO = new ObligacionDsrDTO();
        if (registro != null) {
            if (registro.getIdDetalleSupervision() != null) {
                registroDTO.setIdDetalleSupervision(registro.getIdDetalleSupervision());
            }
            if (registro.getDescripcionResultado() != null) {
                registroDTO.setDescripcionInfraccion(registro.getDescripcionResultado());
            }
            
            if (registro.getIdResultadoSupervision().getCssIcono() != null) {
                registroDTO.setClasecss(registro.getIdResultadoSupervision().getCssIcono());
                registroDTO.setDescripcionResSup(registro.getIdResultadoSupervision().getDescripcion());
                registroDTO.setCodigoResSup(registro.getIdResultadoSupervision().getCodigo());
            }
            if (registro.getPrioridad() != null) {
                registroDTO.setPrioridad(registro.getPrioridad());
            }
            if (registro.getComentario() != null) {
                registroDTO.setComentarioObstaculizado(registro.getComentario());
            }
            registroDTO.setIdDetalleSupervisionAnt(registro.getIdDetalleSupervisionAnt());
            if(registro.getIdSupervision()!=null && registro.getIdSupervision().getIdOrdenServicio()!=null){
                registroDTO.setIteracion(registro.getIdSupervision().getIdOrdenServicio().getIteracion());
            }
        }
        return registroDTO;
    }
    /* OSINE791 – RSIS7 - Fin */
    /* OSINE_MAN_DSR_0025 - Inicio */
    public static List<ObligacionDsrUpdtDTO> getObligacionDsrUpdtDTO(List<PghDetalleSupervision> lista) {
        ObligacionDsrUpdtDTO registroDTO;
        List<ObligacionDsrUpdtDTO> retorno = new ArrayList<ObligacionDsrUpdtDTO>();
        if (lista != null) {
            for (PghDetalleSupervision maestro : lista) {
                registroDTO = toObligacionDsrUpdtDTO(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }

    public static ObligacionDsrUpdtDTO toObligacionDsrUpdtDTO(PghDetalleSupervision registro) {
    	ObligacionDsrUpdtDTO registroDTO = new ObligacionDsrUpdtDTO();
        if (registro != null) {
            if (registro.getIdDetalleSupervision() != null) {
                registroDTO.setIdDetalleSupervision(registro.getIdDetalleSupervision());
            }
            if (registro.getDescripcionResultado() != null) {
                registroDTO.setDescripcionInfraccion(registro.getDescripcionResultado());
            }
            
            if (registro.getIdResultadoSupervision().getCssIcono() != null) {
                registroDTO.setClasecss(registro.getIdResultadoSupervision().getCssIcono());
                registroDTO.setDescripcionResSup(registro.getIdResultadoSupervision().getDescripcion());
                registroDTO.setCodigoResSup(registro.getIdResultadoSupervision().getCodigo());
            }
            if (registro.getPrioridad() != null) {
                registroDTO.setPrioridad(registro.getPrioridad());
            }
            if (registro.getComentario() != null) {
                registroDTO.setComentarioObstaculizado(registro.getComentario());
            }
            registroDTO.setIdDetalleSupervisionAnt(registro.getIdDetalleSupervisionAnt());
            if(registro.getIdSupervision()!=null && registro.getIdSupervision().getIdOrdenServicio()!=null){
                registroDTO.setIteracion(registro.getIdSupervision().getIdOrdenServicio().getIteracion());
            }
            if(registro.getIdInfraccion()!=null)
            {
            	registroDTO.setCodigoInfraccion(registro.getIdInfraccion().getCodigo());
            }
        }
        return registroDTO;
    }
    /* OSINE_MAN_DSR_0025 - Fin */
    public static ProductoDsrScopDTO toProductoDsrDTO(PghProductoSuspender pghProductoSuspender) {
        ProductoDsrScopDTO productoDsrScopDTO = new ProductoDsrScopDTO();
        if (pghProductoSuspender != null) {
            if (pghProductoSuspender.getIdDetalleSupervision() != null) {
                productoDsrScopDTO.setIdDetalleSupervision(pghProductoSuspender.getIdDetalleSupervision().getIdDetalleSupervision());
            }
            if (pghProductoSuspender.getIdProducto() != null) {
                productoDsrScopDTO.setIdProducto(pghProductoSuspender.getIdProducto().getIdProducto());
            }
            
        }
        return productoDsrScopDTO;
    }
}
