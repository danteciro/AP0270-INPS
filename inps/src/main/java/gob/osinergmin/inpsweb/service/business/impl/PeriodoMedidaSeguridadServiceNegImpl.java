/**
* Resumen		
* Objeto			: ServiceNegImpl.java
* Descripción		: Clase ServiceNegImpl ServiceNegImpl
* Fecha de Creación	: 12/10/2016
* PR de Creación	: OSINE_SFS-791
* Autor				: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------				
* OSINE_SFS-791     12/10/2016      Mario Dioses Fernandez          Crear la tarea automática que cancele el registro de hidrocarburos
*/ 
package gob.osinergmin.inpsweb.service.business.impl;
import gob.osinergmin.inpsweb.service.business.PeriodoMedidaSeguridadServiceNeg;
import gob.osinergmin.inpsweb.service.dao.PeriodoMedidaSeguridadDAO;
import gob.osinergmin.inpsweb.service.exception.PeriodoMedidaSeguridadException;
import gob.osinergmin.mdicommon.domain.dto.PeriodoMedidaSeguridadDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.PeriodoMedidaSeguridadFilter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author zchaupis
 */
@Service("PeriodoMedidaSeguridadServiceNeg")
public class PeriodoMedidaSeguridadServiceNegImpl implements PeriodoMedidaSeguridadServiceNeg{
    private static Logger LOG = LoggerFactory.getLogger(PeriodoMedidaSeguridadServiceNegImpl.class);

    @Inject
    private PeriodoMedidaSeguridadDAO periodoMedidaSeguridadDAO;

    @Override
	@Transactional(readOnly=true)
    public List<PeriodoMedidaSeguridadDTO> getListPeriodoMedidaSeguridad(PeriodoMedidaSeguridadFilter filtro) throws PeriodoMedidaSeguridadException{
      LOG.info("getListPeriodoMedidaSeguridad");
        List<PeriodoMedidaSeguridadDTO> retorno = new ArrayList<PeriodoMedidaSeguridadDTO>();
        try{        
            retorno=periodoMedidaSeguridadDAO.getListPeriodoMedidaSeguridad(filtro);
        }catch(Exception ex){
            LOG.error("Error en getListPeriodoMedidaSeguridad",ex);
        }
        return retorno; 
    }

    @Override
    @Transactional
    public PeriodoMedidaSeguridadDTO registrarPeriodoMedidaSeguridad(PeriodoMedidaSeguridadDTO periodoMedidaSeguridadDTO, UsuarioDTO usuarioDTO)throws PeriodoMedidaSeguridadException{
    	LOG.info("registrarPeriodoMedidaSeguridad");
        PeriodoMedidaSeguridadDTO retorno=new PeriodoMedidaSeguridadDTO();
        try {
            retorno=periodoMedidaSeguridadDAO.registrarPeriodoMedidaSeguridad(periodoMedidaSeguridadDTO,usuarioDTO);  
        } catch (Exception ex) {
            LOG.error("Error en registrarPeriodoMedidaSeguridad", ex);
            throw new PeriodoMedidaSeguridadException(ex.getMessage(), ex);
        }
        return retorno;  
    }
    
    /* OSINE_SFS-791 - RSIS 47 - Inicio */ 
    @Override
	@Transactional(rollbackFor=PeriodoMedidaSeguridadException.class)
	public PeriodoMedidaSeguridadDTO actualizarPeriodoMedidaSeguridad(PeriodoMedidaSeguridadDTO periodoMedidaSeguridad, UsuarioDTO UsuarioDTO) throws PeriodoMedidaSeguridadException {
		LOG.info("actualizarPeriodoMedidaSeguridad");
        PeriodoMedidaSeguridadDTO retorno=new PeriodoMedidaSeguridadDTO();
        try {
            retorno=periodoMedidaSeguridadDAO.actualizarPeriodoMedidaSeguridad(periodoMedidaSeguridad,UsuarioDTO);  
        } catch (Exception ex) {
            LOG.error("Error en actualizarPeriodoMedidaSeguridad", ex);
            throw new PeriodoMedidaSeguridadException(ex.getMessage(), ex);
        }
        return retorno;  
	}
	/* OSINE_SFS-791 - RSIS 47 - Fin */ 
}
