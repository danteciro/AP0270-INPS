/**
*
* Resumen		
* Objeto		: EscenarioIncumplidoServiceNegImpl.java
* Descripción		: Clase de la capa de negocio que contiene la implementación de métodos declarados en la clase interfaz EscenarioIncumplidoServiceNeg
* Fecha de Creación	: 05/09/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-791  |  05/09/2016   |   Luis García Reyna          |     Ejecucion Medida - Listar Escenarios Incumplidos de la Supervisión.
*                |               |                              |
*                |               |                              |
* 
*/

package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.service.business.EscenarioIncumplidoServiceNeg;
import gob.osinergmin.inpsweb.service.dao.EscenarioIncumplidoDAO;
import gob.osinergmin.inpsweb.service.exception.EscenarioIncumplidoException;
import gob.osinergmin.mdicommon.domain.dto.EscenarioIncumplidoDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.EscenarioIncumplidoFilter;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import javax.inject.Inject;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service("EscenarioIncumplidoServiceNeg")
public class EscenarioIncumplidoServiceNegImpl implements EscenarioIncumplidoServiceNeg{
    private static Logger LOG = LoggerFactory.getLogger(EscenarioIncumplidoServiceNegImpl.class);
    
    @Inject
    private EscenarioIncumplidoDAO escenarioIncumplidoDAO;
    
    @Override
    @Transactional(readOnly = true)
    public List<EscenarioIncumplidoDTO> findEscenarioIncumplido(EscenarioIncumplidoFilter filtro) throws EscenarioIncumplidoException {
        LOG.info("findEscenarioIncumplido");
        List<EscenarioIncumplidoDTO> retorno = new ArrayList<EscenarioIncumplidoDTO>();
        try{
            retorno=escenarioIncumplidoDAO.find(filtro);
        }catch(Exception ex){
            LOG.error("Error en findEscenarioIncumplido",ex);
        }
        return retorno;
    }

    @Override
     @Transactional(readOnly = true)
    public EscenarioIncumplidoDTO getEscenarioIncumplido(EscenarioIncumplidoFilter filtroEi) {
        LOG.info("getEscenarioIncumplido");
        EscenarioIncumplidoDTO retorno=null;        
        try{
            List<EscenarioIncumplidoDTO> lst=escenarioIncumplidoDAO.find(filtroEi);
            if(!CollectionUtils.isEmpty(lst)){
                retorno=lst.get(0);
            }
        }catch(Exception e){
            LOG.error("Error en getEscenarioIncumplido",e);
        }
        return retorno;
    }

    @Override
    @Transactional(rollbackFor = EscenarioIncumplidoException.class)
    public EscenarioIncumplidoDTO registroComentarioEscIncumplido(EscenarioIncumplidoDTO escenarioIncumplidoDTO, UsuarioDTO usuarioDTO) throws EscenarioIncumplidoException{
        LOG.info("registroComentarioDetSupervision");
        EscenarioIncumplidoDTO retorno=new EscenarioIncumplidoDTO();
        try {
            retorno=escenarioIncumplidoDAO.registroComentarioEscIncumplido(escenarioIncumplidoDTO,usuarioDTO);  
        } catch (Exception ex) {
            LOG.error("Error en registroComentarioDetSupervision", ex);
            throw new EscenarioIncumplidoException(ex.getMessage(), ex);
        }
        return retorno;
    }

    @Override
    @Transactional(rollbackFor = EscenarioIncumplidoException.class)
    public EscenarioIncumplidoDTO actualizarComentarioEscenarioIncumplido(EscenarioIncumplidoDTO escenarioIncumplidoDTO, UsuarioDTO usuarioDTO) throws EscenarioIncumplidoException {
        LOG.info("actualizarComentarioEscenarioIncumplido");
        EscenarioIncumplidoDTO retorno=new EscenarioIncumplidoDTO();
        try {
            retorno=escenarioIncumplidoDAO.registroComentarioEscIncumplido(escenarioIncumplidoDTO,usuarioDTO);  
        } catch (Exception ex) {
            LOG.error("Error en actualizarComentarioEscenarioIncumplido", ex);
            throw new EscenarioIncumplidoException(ex.getMessage(), ex);
        }
        return retorno;
    }
    
    @Override
    public EscenarioIncumplidoDTO guardarEscenarioIncumplido(EscenarioIncumplidoDTO escenarioIncumplidoDTO, UsuarioDTO usuarioDTO) throws EscenarioIncumplidoException{
        LOG.info("registrarEscenarioIncumplido");
        EscenarioIncumplidoDTO retorno=new EscenarioIncumplidoDTO();
        try {
            retorno=escenarioIncumplidoDAO.guardarEscenarioIncumplido(escenarioIncumplidoDTO,usuarioDTO);  
        } catch (Exception ex) {
            LOG.error("Error en registrarEscenarioIncumplido", ex);
            throw new EscenarioIncumplidoException(ex.getMessage(), ex);
        }
        return retorno;
    }
    
    @Override
    @Transactional
    public EscenarioIncumplidoDTO cambiaEstadoEscenarioIncumplido(EscenarioIncumplidoDTO escenarioIncumplidoDTO, UsuarioDTO usuarioDTO) throws EscenarioIncumplidoException{
        LOG.info("cambiaEstadoEscenarioIncumplido");
        EscenarioIncumplidoDTO retorno=new EscenarioIncumplidoDTO();
        try {
            retorno=escenarioIncumplidoDAO.cambiaEstadoEscenarioIncumplido(escenarioIncumplidoDTO,usuarioDTO);  
        } catch (Exception ex) {
            LOG.error("Error en cambiaEstadoEscenarioIncumplido", ex);
            throw new EscenarioIncumplidoException(ex.getMessage(), ex);
        }
        return retorno;
    }
}
