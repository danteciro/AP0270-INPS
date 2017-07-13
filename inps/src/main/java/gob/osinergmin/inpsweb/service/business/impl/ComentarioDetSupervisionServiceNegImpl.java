package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.service.business.ComentarioDetSupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.business.EscenarioIncumplidoServiceNeg;
import gob.osinergmin.inpsweb.service.dao.ComentarioDetSupervisionDAO;
import gob.osinergmin.inpsweb.service.exception.ComentarioDetSupervisionException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.ComentarioDetSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.EscenarioIncumplidoDTO;
import gob.osinergmin.mdicommon.domain.dto.EscenarioIncumplimientoDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.ComentarioDetSupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.EscenarioIncumplidoFilter;
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
@Service("comentarioDetSupervisionServiceNeg")
public class ComentarioDetSupervisionServiceNegImpl implements ComentarioDetSupervisionServiceNeg{
    private static final Logger LOG = LoggerFactory.getLogger(ComentarioDetSupervisionServiceNegImpl.class);
    @Inject
    EscenarioIncumplidoServiceNeg escenarioIncumplidoServiceNeg;
    @Inject
    ComentarioDetSupervisionDAO comentarioDetSupervisionDAO;
    
    @Override
    @Transactional(rollbackFor=ComentarioDetSupervisionException.class)
    public ComentarioDetSupervisionDTO asignarComentarioDetSupervision(ComentarioDetSupervisionDTO comenDetSuperDto,UsuarioDTO usuarioDTO) throws ComentarioDetSupervisionException{
        ComentarioDetSupervisionDTO retorno=null;
        try{
            //buscar escenarioincumplido
            EscenarioIncumplidoDTO esceIncdo=null;
            if(comenDetSuperDto.getIdEsceIncumplimiento()!=null){
                esceIncdo=escenarioIncumplidoServiceNeg.getEscenarioIncumplido(new EscenarioIncumplidoFilter(null,comenDetSuperDto.getDetalleSupervision().getIdDetalleSupervision(),comenDetSuperDto.getIdEsceIncumplimiento()));
                if(esceIncdo==null || esceIncdo.getIdEscenarioIncumplido()==null){
                    esceIncdo=escenarioIncumplidoServiceNeg.guardarEscenarioIncumplido(
                            new EscenarioIncumplidoDTO(comenDetSuperDto.getDetalleSupervision().getIdDetalleSupervision(),new EscenarioIncumplimientoDTO(comenDetSuperDto.getIdEsceIncumplimiento()),Constantes.ESTADO_ACTIVO), 
                            usuarioDTO
                    );
                }
                if(esceIncdo!=null && esceIncdo.getIdEscenarioIncumplido()!=null){
                    comenDetSuperDto.setIdEscenarioIncumplido(esceIncdo.getIdEscenarioIncumplido());
                }else{
                    throw new ComentarioDetSupervisionException("Error al registrar el Escenario Incumplido.",null);
                }
            }
            comenDetSuperDto.setEstado(Constantes.ESTADO_ACTIVO);
            retorno=comentarioDetSupervisionDAO.guardarComentarioDetSupervision(comenDetSuperDto, usuarioDTO);
            
        }catch(Exception e){
            LOG.error("Error en asignarComentarioDetSupervision",e);
            throw new ComentarioDetSupervisionException(e.getMessage(), e);
        }
        return retorno;
    }
    
    @Override
    @Transactional(rollbackFor=ComentarioDetSupervisionException.class)
    public ComentarioDetSupervisionDTO desasignarComentarioDetSupervision(ComentarioDetSupervisionDTO comenDetSuperDto,UsuarioDTO usuarioDTO) throws ComentarioDetSupervisionException{
        ComentarioDetSupervisionDTO retorno=null;
        try{
            //buscar escenarioincumplido
            comenDetSuperDto.setEstado(Constantes.ESTADO_INACTIVO);
            retorno=comentarioDetSupervisionDAO.cambiaEstadoComentarioDetSupervision(comenDetSuperDto, usuarioDTO);
            
            EscenarioIncumplidoDTO esceIncdo=null;
            if(comenDetSuperDto.getIdEsceIncumplimiento()!=null){
                esceIncdo=escenarioIncumplidoServiceNeg.getEscenarioIncumplido(new EscenarioIncumplidoFilter(null,comenDetSuperDto.getDetalleSupervision().getIdDetalleSupervision(),comenDetSuperDto.getIdEsceIncumplimiento()));
                if(esceIncdo!=null && esceIncdo.getIdEscenarioIncumplido()!=null){
                    List<ComentarioDetSupervisionDTO> lstComenDetSup=findComentarioDetSupervision(new ComentarioDetSupervisionFilter(esceIncdo.getIdEscenarioIncumplido(),null,null));
                    if(CollectionUtils.isEmpty(lstComenDetSup)){
                        esceIncdo.setEstado(Constantes.ESTADO_INACTIVO);
                        escenarioIncumplidoServiceNeg.cambiaEstadoEscenarioIncumplido(esceIncdo, usuarioDTO);
                    }
                }else{
                    throw new ComentarioDetSupervisionException("Error al desasignar, no existe Escenario Incumplido.",null);
                }
            }
        }catch(Exception e){
            LOG.error("Error en asignarComentarioDetSupervision",e);
            throw new ComentarioDetSupervisionException(e.getMessage(), e);
        }
        return retorno;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ComentarioDetSupervisionDTO> findComentarioDetSupervision(ComentarioDetSupervisionFilter filtro) throws ComentarioDetSupervisionException{
        LOG.info("findComentarioDetSupervision");
        List<ComentarioDetSupervisionDTO> retorno = new ArrayList<ComentarioDetSupervisionDTO>();
        try{
            retorno=comentarioDetSupervisionDAO.find(filtro);
        }catch(Exception ex){
            LOG.error("Error en findComentarioDetSupervision",ex);
        }
        return retorno;
    }
    
    @Override
    @Transactional
    public ComentarioDetSupervisionDTO cambiaEstadoComentarioDetSupervision(ComentarioDetSupervisionDTO comenDetSuperDto,UsuarioDTO usuarioDTO) throws ComentarioDetSupervisionException{
        LOG.info("cambiaEstadoEscenarioIncumplido");
        ComentarioDetSupervisionDTO retorno=new ComentarioDetSupervisionDTO();
        try {
            retorno=comentarioDetSupervisionDAO.cambiaEstadoComentarioDetSupervision(comenDetSuperDto, usuarioDTO);
        } catch (Exception ex) {
            LOG.error("Error en cambiaEstadoEscenarioIncumplido", ex);
            throw new ComentarioDetSupervisionException(ex.getMessage(), ex);
        }
        return retorno;
    }
}
