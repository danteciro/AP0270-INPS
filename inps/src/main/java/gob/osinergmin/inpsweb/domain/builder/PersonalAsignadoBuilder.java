package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghPersonalAsignado;
import gob.osinergmin.mdicommon.domain.dto.PersonalAsignadoDTO;
import gob.osinergmin.mdicommon.domain.dto.PersonalDTO;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author htorress
 */
public class PersonalAsignadoBuilder {
	private static final Logger LOG = LoggerFactory.getLogger(PersonalBuilder.class);
	
    public static List<PersonalAsignadoDTO> toListPersonalAsignadoDTO(List<PghPersonalAsignado> lista) {
    	LOG.info("Builder toListPersonalAsignadoDTO");
    	PersonalAsignadoDTO personalAsignadoDTO;
        List<PersonalAsignadoDTO> retorno = new ArrayList<PersonalAsignadoDTO>();
        if (lista != null) {
            for (PghPersonalAsignado maestro : lista) {
            	personalAsignadoDTO = toPersonalAsignadoDto(maestro);
                retorno.add(personalAsignadoDTO);
            }
        }
        return retorno;
    } 
    
    public static PersonalAsignadoDTO toPersonalAsignadoDto(PghPersonalAsignado registro) {
    	LOG.info("Builder toPersonalAsignadoDto");
    	PersonalAsignadoDTO registroDTO = new PersonalAsignadoDTO();
    	
        registroDTO.setIdPersonalAsignado(registro.getIdPersonalAsignado());
        
        if(registro.getIdPersonalJefe()!=null){
        	PersonalDTO persona = new PersonalDTO();
        	persona.setIdPersonal(registro.getIdPersonalJefe().getIdPersonal());
        	persona.setIdPersonalSiged(registro.getIdPersonalJefe().getIdPersonalSiged());
        	registroDTO.setIdPersonalJefe(persona);
        }
        
        if(registro.getIdPersonalSubordinado()!=null){
    	    PersonalDTO persona = new PersonalDTO();
        	persona.setIdPersonal(registro.getIdPersonalSubordinado().getIdPersonal());
        	persona.setIdPersonalSiged(registro.getIdPersonalJefe().getIdPersonalSiged());
        	registroDTO.setIdPersonalSubordinado(persona);;
        }
        
        return registroDTO;
    }

    
}