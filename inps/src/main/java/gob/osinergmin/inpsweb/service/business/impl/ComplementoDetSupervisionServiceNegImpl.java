package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.service.business.ComentarioDetSupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.business.ComplementoDetSupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.business.EscenarioIncumplidoServiceNeg;
import gob.osinergmin.inpsweb.service.dao.ComentarioDetSupervisionDAO;
import gob.osinergmin.inpsweb.service.dao.ComplementoDetSupervisionDAO;
import gob.osinergmin.inpsweb.service.exception.ComentarioDetSupervisionException;
import gob.osinergmin.inpsweb.service.exception.ComplementoDetSupervisionException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.ComentarioDetSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.ComplementoDetSupValorDTO;
import gob.osinergmin.mdicommon.domain.dto.ComplementoDetSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.ComplementoDetSupValorFilter;
import gob.osinergmin.mdicommon.domain.ui.ComplementoDetSupervisionFilter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author jpiro
 */
@Service("complementoDetSupervisionServiceNeg")
public class ComplementoDetSupervisionServiceNegImpl implements ComplementoDetSupervisionServiceNeg{
    private static final Logger LOG = LoggerFactory.getLogger(ComplementoDetSupervisionServiceNegImpl.class);
    @Inject
    EscenarioIncumplidoServiceNeg escenarioIncumplidoServiceNeg;
    @Inject
    ComentarioDetSupervisionDAO comentarioDetSupervisionDAO;
    @Inject
    ComentarioDetSupervisionServiceNeg comentarioDetSupervisionServiceNeg;
    @Inject
    ComplementoDetSupervisionDAO complementoDetSupervisionDAO;
    
    @Override
    @Transactional(rollbackFor=ComentarioDetSupervisionException.class)
    public ComentarioDetSupervisionDTO guardarComplementoDetSupervision(ComentarioDetSupervisionDTO comenDetSuperDto,UsuarioDTO usuarioDTO) throws ComentarioDetSupervisionException{
        ComentarioDetSupervisionDTO retorno=null;
        ComentarioDetSupervisionDTO regNuevo=null;
        try{
            if(comenDetSuperDto.getIdComentarioDetSupervision()==null){
                regNuevo = comentarioDetSupervisionServiceNeg.asignarComentarioDetSupervision(comenDetSuperDto, usuarioDTO);
                comenDetSuperDto.setIdComentarioDetSupervision(regNuevo.getIdComentarioDetSupervision());
            }
            
            if(!CollectionUtils.isEmpty(comenDetSuperDto.getLstCompDetSup())){
                for(ComplementoDetSupervisionDTO compDetSup : comenDetSuperDto.getLstCompDetSup()){
                    compDetSup.setIdComentarioDetSupervision(comenDetSuperDto.getIdComentarioDetSupervision());
                    //valida si registro complementoDetSupervision existe
                    ComplementoDetSupervisionDTO compDetSupNew=null;
                    if(compDetSup.getIdComplementoDetSupervision()==null){
                        compDetSup.setEstado(Constantes.ESTADO_ACTIVO);
                        compDetSupNew = complementoDetSupervisionDAO.guardarComplementoDetSupervision(compDetSup, usuarioDTO);
                        compDetSup.setIdComplementoDetSupervision(compDetSupNew.getIdComplementoDetSupervision());
                    }else{
                        //elimino valores ya q estos seran ingresados en las sgte lineas
                        complementoDetSupervisionDAO.eliminaComplementoDetSupValor(compDetSup.getIdComplementoDetSupervision());
                    }
                    
                    //ingreso de valores
                    for(ComplementoDetSupValorDTO compDetSupValor : compDetSup.getValor()){
                        compDetSupValor.setIdComplementoDetSupervision(compDetSup.getIdComplementoDetSupervision());
                        compDetSupValor.setEstado(Constantes.ESTADO_ACTIVO);
                        ComplementoDetSupValorDTO compDetSupValorNew = complementoDetSupervisionDAO.guardarComplementoDetSupValor(compDetSupValor, usuarioDTO);
                        LOG.info("--->getIdComplementoDetSupValor()---->"+compDetSupValorNew.getIdComplementoDetSupValor());
                    }
                }
            }                            
        }catch(Exception e){
            LOG.error("Error en guardarComplementoDetSupervision",e);
            throw new ComentarioDetSupervisionException(e.getMessage(), e);
        }
        return retorno;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ComplementoDetSupervisionDTO> findComplementoDetSupervision(ComplementoDetSupervisionFilter filtro) throws ComplementoDetSupervisionException{
        LOG.info("findComplementoDetSupervision");
        List<ComplementoDetSupervisionDTO> retorno = new ArrayList<ComplementoDetSupervisionDTO>();
        try{
            retorno=complementoDetSupervisionDAO.find(filtro);
        }catch(Exception ex){
            LOG.error("Error en findComplementoDetSupervision",ex);
        }
        return retorno;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ComplementoDetSupValorDTO> findComplementoDetSupValor(ComplementoDetSupValorFilter filtro) throws ComplementoDetSupervisionException{
        LOG.info("findComplementoDetSupValor");
        List<ComplementoDetSupValorDTO> retorno = new ArrayList<ComplementoDetSupValorDTO>();
        try{
            retorno=complementoDetSupervisionDAO.findComplementoDetSupValor(filtro);
        }catch(Exception ex){
            LOG.error("Error en findComplementoDetSupValor",ex);
        }
        return retorno;
    }
       
}
