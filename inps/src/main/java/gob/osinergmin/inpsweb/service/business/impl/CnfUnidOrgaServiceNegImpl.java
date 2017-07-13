/**
* Resumen
* Objeto			: ArchivoServiceNeg.java
* Descripción		: Clase Interfaz de la capa de negocio que contiene la declaración de 
* 					  métodos a implementarse en el CnfUnidOrgaServiceNegImpl.
* Fecha de Creación	: 25/10/2016.
* PR de Creación	: OSINE_SFS-1344.
* Autor				: Hernán Torres.
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* 
*/

package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.service.business.ActividadServiceNeg;
import gob.osinergmin.inpsweb.service.business.CnfUnidOrgaServiceNeg;
import gob.osinergmin.inpsweb.service.dao.ActividadDAO;
import gob.osinergmin.inpsweb.service.dao.CnfUnidOrgaDAO;
import gob.osinergmin.inpsweb.service.exception.CnfUnidOrgaException;
import gob.osinergmin.inpsweb.service.exception.ExpedienteException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.CnfUnidOrgaDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresasZonaVwDTO;
import gob.osinergmin.mdicommon.domain.dto.EstadoProcesoDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.ui.CnfUnidOrgaFilter;
import gob.osinergmin.mdicommon.domain.ui.EstadoProcesoFilter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("cnfUnidOrgaServiceNeg")
public class CnfUnidOrgaServiceNegImpl implements CnfUnidOrgaServiceNeg {
	
	private static final Logger LOG = LoggerFactory.getLogger(CnfUnidOrgaServiceNeg.class);
    
	@Inject
    private CnfUnidOrgaDAO cnfUnidOrgaDAO;
	
    @Override
	public List<CnfUnidOrgaDTO> listarCnfUnidOrga(CnfUnidOrgaFilter filtro) throws CnfUnidOrgaException {
    	LOG.info("Neg listarCnfUnidOrga");
        List<CnfUnidOrgaDTO> retorno = null;
        try {
            retorno = cnfUnidOrgaDAO.listarCnfUnidOrga(filtro);

        } catch (Exception ex) {
            LOG.error("error listarCnfUnidOrga", ex);
        }
        return retorno;
    }
}
