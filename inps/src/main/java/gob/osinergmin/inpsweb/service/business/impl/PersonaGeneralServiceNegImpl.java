package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.service.business.PersonaGeneralServiceNeg;
import gob.osinergmin.inpsweb.service.dao.PersonaGeneralDAO;
import gob.osinergmin.mdicommon.domain.dto.PersonaGeneralDTO;
import gob.osinergmin.mdicommon.domain.ui.PersonaGeneralFilter;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("personaGeneralServiceNeg")
public class PersonaGeneralServiceNegImpl implements PersonaGeneralServiceNeg{
	private static final Logger LOG = LoggerFactory.getLogger(PersonaGeneralServiceNegImpl.class);
	
	@Inject
	private PersonaGeneralDAO personaGeneral;
	
	@Override
	public List<PersonaGeneralDTO> find(PersonaGeneralFilter filtro) {
		LOG.info("PersonaGeneralServiceNegImpl--find - inicio");
		List<PersonaGeneralDTO> retorno=null;
        try{
            retorno = personaGeneral.find(filtro);
        }catch(Exception ex){
            LOG.error("Error en find",ex);
        }
        LOG.info("PersonaGeneralServiceNegImpl--find - fin");
        return retorno;
	}

}
