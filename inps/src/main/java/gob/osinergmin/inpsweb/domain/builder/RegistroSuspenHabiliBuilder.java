/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.MdiRegistroHidrocarburo;
import gob.osinergmin.inpsweb.domain.MdiRegistroSuspenHabili;
import gob.osinergmin.inpsweb.domain.PghCriterio;
import gob.osinergmin.inpsweb.domain.PghObligacion;
import gob.osinergmin.inpsweb.domain.PghResultadoSupervision;
import gob.osinergmin.inpsweb.domain.PghSupervision;
import gob.osinergmin.inpsweb.domain.PghTipificacion;
import gob.osinergmin.mdicommon.domain.dto.RegistroHidrocarburoDTO;
import gob.osinergmin.mdicommon.domain.dto.RegistroSuspenHabiliDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zchaupis
 */
public class RegistroSuspenHabiliBuilder {

    public static MdiRegistroSuspenHabili toRegistroSuspenHabiliDomain(RegistroSuspenHabiliDTO registro) {
        MdiRegistroSuspenHabili registroDomain = new MdiRegistroSuspenHabili();
        if (registro != null) {
            if (registro.getIdRegistroSuspenHabil() != null) {
                registroDomain.setIdRegistroSuspenHabil(registro.getIdRegistroSuspenHabil());
            }
            if (registro.getFechaFinSuspension() != null) {
                registroDomain.setFechaFinSuspension(registro.getFechaFinSuspension());
            }
            if (registro.getFechaInicioSuspension() != null) {
                registroDomain.setFechaInicioSuspension(registro.getFechaInicioSuspension());
            }
            if (registro.getIdRegistroHidrocarburo() != null && registro.getIdRegistroHidrocarburo().getIdRegistroHidrocarburo() != null) {
                registroDomain.setIdRegistroHidrocarburo(new MdiRegistroHidrocarburo(registro.getIdRegistroHidrocarburo().getIdRegistroHidrocarburo()));
            }
            if (registro.getObservacion() != null) {
                registroDomain.setObservacion(registro.getObservacion());
            }
            if (registro.getProceso()!= null) {
                registroDomain.setProceso(registro.getProceso());
            }
        }
        return registroDomain;
    }

    public static List<RegistroSuspenHabiliDTO> toListRegistroSuspenHabiliDTO(List<MdiRegistroSuspenHabili> lista) {
        RegistroSuspenHabiliDTO registroDTO;
        List<RegistroSuspenHabiliDTO> retorno = new ArrayList<RegistroSuspenHabiliDTO>();
        if (lista != null) {
            for (MdiRegistroSuspenHabili maestro : lista) {
                registroDTO = toRegistroSuspenHabiliDTO(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }

    public static RegistroSuspenHabiliDTO toRegistroSuspenHabiliDTO(MdiRegistroSuspenHabili registro) {
        RegistroSuspenHabiliDTO registroDTO = new RegistroSuspenHabiliDTO();
        if (registro.getIdRegistroSuspenHabil() != null) {
            registroDTO.setIdRegistroSuspenHabil(registro.getIdRegistroSuspenHabil());
        }
        if (registro.getObservacion() != null) {
            registroDTO.setObservacion(registro.getObservacion());
        }
        if (registro.getIdRegistroHidrocarburo() != null) {
            if (registro.getIdRegistroHidrocarburo().getIdRegistroHidrocarburo() != null) {
                RegistroHidrocarburoDTO registroHidrocarburoDTO = new RegistroHidrocarburoDTO();
                registroHidrocarburoDTO.setIdRegistroHidrocarburo(registro.getIdRegistroHidrocarburo().getIdRegistroHidrocarburo());
                registroDTO.setIdRegistroHidrocarburo(registroHidrocarburoDTO);
            }
        }
        if (registro.getFechaInicioSuspension() != null) {
            registroDTO.setFechaInicioSuspension(registro.getFechaInicioSuspension());
        }
        if (registro.getFechaFinSuspension() != null) {
            registroDTO.setFechaFinSuspension(registro.getFechaFinSuspension());
        }
        if (registro.getProceso()!= null) {
            registroDTO.setProceso(registro.getProceso());
        }
        return registroDTO;
    }
}
