package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.MdiMaestroColumna;
import gob.osinergmin.inpsweb.domain.PghPersonaGeneral;
import gob.osinergmin.inpsweb.domain.PghSupervision;
import gob.osinergmin.inpsweb.domain.PghSupervisionPersonaGral;
import gob.osinergmin.inpsweb.domain.PghSupervisionPersonaGralPK;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.PersonaGeneralDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionPersonaGralDTO;

import java.util.ArrayList;
import java.util.List;

public class SupervisionPersonaGralBuilder {

    public static List<SupervisionPersonaGralDTO> toListSupervisionPersonaGralDTO(List<PghSupervisionPersonaGral> lista) {
        SupervisionPersonaGralDTO registroDTO;
        List<SupervisionPersonaGralDTO> retorno = new ArrayList<SupervisionPersonaGralDTO>();
        if (lista != null) {
            for (PghSupervisionPersonaGral maestro : lista) {
                registroDTO = toSupervisionPersonaGralDTO(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }

    public static SupervisionPersonaGralDTO toSupervisionPersonaGralDTO(PghSupervisionPersonaGral registro) {
        SupervisionPersonaGralDTO registroDTO = new SupervisionPersonaGralDTO();
        if (registro != null) {

            if (registro.getCargo() != null && registro.getCargo().getIdMaestroColumna() != null) {
                MaestroColumnaDTO cargo = new MaestroColumnaDTO();
                cargo.setIdMaestroColumna(registro.getCargo().getIdMaestroColumna());
                cargo.setDescripcion(registro.getCargo().getDescripcion());
                registroDTO.setCargo(cargo);
            }
            if (registro.getPghPersonaGeneral() != null && registro.getPghPersonaGeneral().getIdPersonaGeneral() != null) {
                PersonaGeneralDTO personaGeneral = new PersonaGeneralDTO();
                personaGeneral.setIdPersonaGeneral(registro.getPghPersonaGeneral().getIdPersonaGeneral());
                personaGeneral.setNombresPersona(registro.getPghPersonaGeneral().getNombresPersona());
                personaGeneral.setApellidoPaternoPersona(registro.getPghPersonaGeneral().getApellidoPaternoPersona());
                personaGeneral.setApellidoMaternoPersona(registro.getPghPersonaGeneral().getApellidoMaternoPersona());
                personaGeneral.setIdTipoDocumento(registro.getPghPersonaGeneral().getIdTipoDocumento());
                personaGeneral.setNumeroDocumento(registro.getPghPersonaGeneral().getNumeroDocumento());
                registroDTO.setPersonaGeneral(personaGeneral);
            }
            if (registro.getPghSupervision() != null && registro.getPghSupervision().getIdSupervision() != null) {
                SupervisionDTO supervision = new SupervisionDTO();
                supervision.setIdSupervision(registro.getPghSupervision().getIdSupervision());
                registroDTO.setSupervision(supervision);
            }
            if (registro.getCorreo() != null) {
                registroDTO.setCorreo(registro.getCorreo());
            }
        }
        return registroDTO;
    }

    public static PghSupervisionPersonaGral toSupervisionPersonaGralDomain(SupervisionPersonaGralDTO registroDTO) {
        PghSupervisionPersonaGral registro = new PghSupervisionPersonaGral();
        if (registroDTO != null) {
            if ((registroDTO.getPersonaGeneral() != null && registroDTO.getPersonaGeneral().getIdPersonaGeneral() != null)
                    && (registroDTO.getSupervision() != null && registroDTO.getSupervision().getIdSupervision() != null)) {
                registro.setPghSupervisionPersonaGralPK(new PghSupervisionPersonaGralPK(registroDTO.getSupervision().getIdSupervision(),
                        registroDTO.getPersonaGeneral().getIdPersonaGeneral()));
            }
            if (registroDTO.getCargo() != null && registroDTO.getCargo().getIdMaestroColumna() != null) {
                MdiMaestroColumna maestroColumna = new MdiMaestroColumna();
                maestroColumna.setIdMaestroColumna(registroDTO.getCargo().getIdMaestroColumna());
                registro.setCargo(maestroColumna);
            }
            if (registroDTO.getPersonaGeneral() != null && registroDTO.getPersonaGeneral().getIdPersonaGeneral() != null) {
                PghPersonaGeneral personaGeneral = new PghPersonaGeneral();
                personaGeneral.setIdPersonaGeneral(registroDTO.getPersonaGeneral().getIdPersonaGeneral());
                registro.setPghPersonaGeneral(personaGeneral);
            }
            if (registroDTO.getSupervision() != null && registroDTO.getSupervision().getIdSupervision() != null) {
                PghSupervision supervision = new PghSupervision();
                supervision.setIdSupervision(registroDTO.getSupervision().getIdSupervision());
                registro.setPghSupervision(supervision);
            }
            registro.setEstado(registroDTO.getEstado());
            if (registroDTO.getCorreo() != null) {
                registro.setCorreo(registroDTO.getCorreo());
            }

        }
        return registro;
    }
}
