/**
* Resumen		
* Objeto			: ExpedienteTareaServiceNegImpl.java
* Descripción		: Clase NegImpl ExpedienteTareaServiceNegImpl
* Fecha de Creación	: 23/10/2016
* PR de Creación	: OSINE_SFS-791
* Autor				: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------				
* OSINE_SFS-791     23/10/2016      Mario Dioses Fernandez          Crear la tarea automática que notifique vía correo que se debe elaborar el oficio por obligaciones incumplidas sin subsanar
* OSINE_SFS-791     23/10/2016      Mario Dioses Fernandez          Crear la tarea automática que cancele el registro de hidrocarburos
* 
*/ 
package gob.osinergmin.inpsweb.service.business.impl;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import gob.osinergmin.inpsweb.service.business.ExpedienteTareaServiceNeg;
import gob.osinergmin.inpsweb.service.dao.ExpedienteTareaDAO;
import gob.osinergmin.inpsweb.service.exception.ExpedienteTareaException;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteTareaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.ExpedienteTareaFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ExpedienteTareaServiceNeg")
public class ExpedienteTareaServiceNegImpl implements ExpedienteTareaServiceNeg {
	private static final Logger LOG = LoggerFactory.getLogger(ExpedienteTareaServiceNegImpl.class);
    @Inject
    private ExpedienteTareaDAO expedienteTareaDAO;
    
	@Override
	@Transactional(readOnly=true)
	public List<ExpedienteTareaDTO> find(ExpedienteTareaFilter filtro) throws ExpedienteTareaException {
		LOG.info("ExpedienteTareaServiceNegImpl--find - inicio");
        List<ExpedienteTareaDTO> retorno = new ArrayList<ExpedienteTareaDTO>();
        try{
            retorno=expedienteTareaDAO.find(filtro);
        }catch(Exception ex){        	
            LOG.error("Error en find", ex);
            throw new ExpedienteTareaException(ex);
        }
        return retorno;
	}

	@Override
	@Transactional(rollbackFor=ExpedienteTareaException.class)
	public ExpedienteTareaDTO registrarExpedienteTarea(ExpedienteTareaDTO ExpedienteTarea, UsuarioDTO UsuarioDTO) throws ExpedienteTareaException {
		LOG.info("ExpedienteTareaServiceNegImpl--registrarExpedienteTarea - inicio");
		ExpedienteTareaDTO retorno = null;
        try{
            retorno=expedienteTareaDAO.registrar(ExpedienteTarea, UsuarioDTO);
        }catch(Exception ex){
            LOG.error("Error en registrarExpedienteTarea", ex);
            throw new ExpedienteTareaException(ex);
        }
        return retorno;
	}

	@Override
	@Transactional(rollbackFor=ExpedienteTareaException.class)
	public ExpedienteTareaDTO actualizarExpedienteTarea(ExpedienteTareaDTO ExpedienteTarea, UsuarioDTO UsuarioDTO) throws ExpedienteTareaException {
		LOG.info("ExpedienteTareaServiceNegImpl--actualizarExpedienteTarea - inicio");
		ExpedienteTareaDTO retorno = null;
        try{
            retorno=expedienteTareaDAO.actualizar(ExpedienteTarea, UsuarioDTO);
        }catch(Exception ex){
            LOG.error("Error en actualizarExpedienteTarea", ex);
            throw new ExpedienteTareaException(ex);
        }
        return retorno;
	}
}
