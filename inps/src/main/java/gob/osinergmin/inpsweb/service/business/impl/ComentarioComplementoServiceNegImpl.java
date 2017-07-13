package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.service.business.ComentarioComplementoServiceNeg;
import gob.osinergmin.inpsweb.service.dao.ComentarioComplementoDAO;
import gob.osinergmin.inpsweb.service.dao.MaestroColumnaDAO;
import gob.osinergmin.inpsweb.service.exception.ComentarioComplementoException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.ComentarioComplementoDTO;
import gob.osinergmin.mdicommon.domain.dto.ComplementoDTO;
import gob.osinergmin.mdicommon.domain.dto.ComplementoDTO.OpcionDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.ui.ComentarioComplementoFilter;
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
@Service("comentarioComplementoServiceNeg")
public class ComentarioComplementoServiceNegImpl implements ComentarioComplementoServiceNeg {
    private static final Logger LOG = LoggerFactory.getLogger(ComentarioComplementoServiceNegImpl.class);
    @Inject
    ComentarioComplementoDAO comentarioComplementoDAO;
    @Inject
    MaestroColumnaDAO maestroColumnaDAO;
    
    @Override
    @Transactional(readOnly = true)
    public List<ComentarioComplementoDTO> getDataLstComentarioComplemento(ComentarioComplementoFilter filtro) throws ComentarioComplementoException{
        LOG.info("getDataLstComplemento");
        List<ComentarioComplementoDTO> retorno=null;
        try{
            retorno=comentarioComplementoDAO.getLstComentarioComplemento(filtro);
            
            if(!CollectionUtils.isEmpty(retorno)){
                for(ComentarioComplementoDTO registro : retorno){
                    if(registro.getComplemento()!=null){
                        //busca valores de casos tipo lista
                        if(registro.getComplemento().getCodTipo().getCodigo()!=null && 
                            ( registro.getComplemento().getCodTipo().getCodigo().equals(Constantes.CODIGO_COMPLEMENTO_TIPO_SELEC) || 
                                registro.getComplemento().getCodTipo().getCodigo().equals(Constantes.CODIGO_COMPLEMENTO_TIPO_CHK) )
                        ){
                            if(registro.getComplemento().getOrigenDatos()!=null && registro.getComplemento().getOrigenDatos().getCodigo()!=null && 
                                registro.getComplemento().getOrigenDatos().getCodigo().equals(Constantes.CODIGO_COMPLEMENTO_DATOS_MAEST_COLU) && 
                                (registro.getComplemento().getDominioMaestroColumna()!=null && registro.getComplemento().getAplicacionMaestroColumna()!=null) 
                            ){
                                //obtener data maestro columna
                                List<MaestroColumnaDTO> opciones=maestroColumnaDAO.findMaestroColumna(registro.getComplemento().getDominioMaestroColumna(), registro.getComplemento().getAplicacionMaestroColumna());
                                //builder a tipo OpcionDTO
                                if(!CollectionUtils.isEmpty(opciones)){
                                    List<ComplementoDTO.OpcionDTO> listOpcion = new ArrayList<ComplementoDTO.OpcionDTO>();
                                    for(MaestroColumnaDTO reg : opciones){
                                        ComplementoDTO.OpcionDTO opcion = registro.getComplemento().new OpcionDTO(reg.getIdMaestroColumna(),reg.getDescripcion());
                                        listOpcion.add(opcion);
                                    }                        
                                    registro.getComplemento().setOpciones(listOpcion);
                                }
                            }else 
                            if(registro.getComplemento().getOrigenDatos()!=null && registro.getComplemento().getOrigenDatos().getCodigo()!=null && 
                                registro.getComplemento().getOrigenDatos().getCodigo().equals(Constantes.CODIGO_COMPLEMENTO_DATOS_VIEW) && 
                                (registro.getComplemento().getVistaNombre()!=null && registro.getComplemento().getVistaCampoId()!=null && registro.getComplemento().getVistaCampoDesc()!=null)
                            ){
                                //obtener data de Vista
                                List<ComplementoDTO.OpcionDTO> listOpcion = comentarioComplementoDAO.getOpcionVistaComplemento(registro.getComplemento(),filtro);
                                registro.getComplemento().setOpciones(listOpcion);
                            }
                        }
                    }
                }
            }
        }catch(Exception e){
            LOG.error("Error en getDataLstComplemento",e);
            throw new ComentarioComplementoException(e.getMessage(), e);
        }
        return retorno;
    }
}
