/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghExpediente;
import gob.osinergmin.inpsweb.domain.PghHstEstadoLevantamiento;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.Utiles;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.HstEstadoLevantamientoDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zchaupis
 */
public class HstEstadoLevantamientoBuilder {
    public static PghHstEstadoLevantamiento toHstEstadoLevantamientoDomain(HstEstadoLevantamientoDTO registro) {
        PghHstEstadoLevantamiento registroDomain = new PghHstEstadoLevantamiento();
        if (registro != null) {
            if (registro.getExpedienteDTO()!= null) {                
                registroDomain.setIdExpediente(new PghExpediente(registro.getExpedienteDTO().getIdExpediente()));
            }
            if (registro.getEstado()!= null) {
                registroDomain.setEstado(registro.getEstado());
            }
            if (registro.getFechaEstado()!= null) {
                registroDomain.setFechaEstado(Utiles.stringToDate(registro.getFechaEstado(), Constantes.FORMATO_FECHA_LARGE));
            }
            if (registro.getIdEstadoLevantamiento()!= null) {
                registroDomain.setIdEstadoLevantamiento(registro.getIdEstadoLevantamiento());
            }
            if (registro.getIdEstado()!= null) {
                registroDomain.setIdEstado(registro.getIdEstado());
            }
        }
        return registroDomain;
    }
    
    public static HstEstadoLevantamientoDTO toHstEstadoLevantamientoDTO(PghHstEstadoLevantamiento registro) {
        HstEstadoLevantamientoDTO registroDTO = new HstEstadoLevantamientoDTO();
        if (registro != null) {
            if (registro.getIdExpediente()!= null) {        
                if (registro.getIdExpediente().getIdExpediente()!= null) {   
                      registroDTO.setExpedienteDTO(new ExpedienteDTO(registro.getIdExpediente().getIdExpediente()));
                }
            }
            if (registro.getEstado()!= null) {
                registroDTO.setEstado(registro.getEstado());
            }
            if (registro.getFechaEstado()!= null) {
                registroDTO.setFechaEstado(Utiles.DateToString(registro.getFechaEstado(), Constantes.FORMATO_FECHA_LARGE));
            }
            if (registro.getIdEstadoLevantamiento()!= null) {
                registroDTO.setIdEstadoLevantamiento(registro.getIdEstadoLevantamiento());
            }
            if (registro.getIdEstado()!= null) {
                registroDTO.setIdEstado(registro.getIdEstado());
            }
        }
        return  registroDTO;
    }
    
     public static List<HstEstadoLevantamientoDTO> toListHstEstadoLevantamientoDto(List<PghHstEstadoLevantamiento> lista) {
        HstEstadoLevantamientoDTO registroDTO;
        List<HstEstadoLevantamientoDTO> retorno = new ArrayList<HstEstadoLevantamientoDTO>();
        if (lista != null) {
            for (PghHstEstadoLevantamiento maestro : lista) {
                registroDTO = toHstEstadoLevantamientoDTO(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }
     
     
}
