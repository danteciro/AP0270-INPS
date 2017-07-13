package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghObligacion;
import gob.osinergmin.inpsweb.domain.PghObligacionBaseLegal;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.ObligacionDTO;

public class ObligacionBuilder {

    public static ObligacionDTO toObligacionDto(PghObligacion registro) {
        ObligacionDTO registroDTO = new ObligacionDTO();
        String descripcionBaseLegal = "";
        if (registro != null) {
            registroDTO.setIdObligacion(registro.getIdObligacion());
            registroDTO.setCodigoObligacion(registro.getCodigoObligacion());
            registroDTO.setDescripcion(registro.getDescripcion());
            if (registro.getIdDocumentoAdjunto() != null && registro.getIdDocumentoAdjunto().getIdDocumentoAdjunto() != null) {
                registroDTO.setIdDocumentoAdjunto(registro.getIdDocumentoAdjunto().getIdDocumentoAdjunto());
            }
            if (!registro.getPghObligacionBaseLegalList().isEmpty()) {
                descripcionBaseLegal = "";
                for (PghObligacionBaseLegal listaObliBase : registro.getPghObligacionBaseLegalList()) {
                    if (listaObliBase.getEstado().equals(Constantes.ESTADO_ACTIVO)) {
                        descripcionBaseLegal += listaObliBase.getIdBaseLegal().getCodigoBaseLegal() + "-"
                                + listaObliBase.getIdBaseLegal().getDescripcion() + "\n";
                    }
                }
                if (!Constantes.CONSTANTE_VACIA.equals(descripcionBaseLegal.trim())) {
                    descripcionBaseLegal = descripcionBaseLegal.substring(0, (descripcionBaseLegal.length() - 1));
                }
                registroDTO.setDescripcionBaseLegal(descripcionBaseLegal);
            }
        }
        return registroDTO;
    }
}
