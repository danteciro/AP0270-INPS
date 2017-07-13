/**
* Resumen		
* Objeto		: HistoricoEstadoDAOImpl.java
* Descripción		: Clase que contiene la implementación de los métodos declarados en la clase interfaz HistoricoEstadoDAO
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     23/05/2016      Luis García Reyna           Implementar la funcionalidad de devolución de asignaciones de acuerdo a especificaciones.
* 
*/

package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.MdiMaestroColumna;
import gob.osinergmin.inpsweb.domain.PghEstadoProceso;
import gob.osinergmin.inpsweb.domain.PghExpediente;
import gob.osinergmin.inpsweb.domain.PghHistoricoEstado;
import gob.osinergmin.inpsweb.domain.PghOrdenServicio;
import gob.osinergmin.inpsweb.domain.PghPersonal;
import gob.osinergmin.inpsweb.domain.builder.HistoricoEstadoBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.HistoricoEstadoDAO;
import gob.osinergmin.inpsweb.service.exception.HistoricoEstadoException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.HistoricoEstadoDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.HistoricoEstadoFilter;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author jpiro
 */
@Service("historicoEstadoDAO")
public class HistoricoEstadoDAOImpl implements HistoricoEstadoDAO{
    private static final Logger LOG = LoggerFactory.getLogger(HistoricoEstadoDAOImpl.class);    
    @Inject
    private CrudDAO crud;
    
    @Override
    /* OSINE_SFS-480 - RSIS 40 - Inicio */
    public HistoricoEstadoDTO registrar(Long idExpediente,Long idOrdenServicio,Long idEstadoProceso,Long idPersonalOri,Long idPersonalDest,Long idTipoHistorico,Long idPeticion,Long idMotivo,String motivoReasignacion,UsuarioDTO usuarioDTO) throws HistoricoEstadoException{  
    /* OSINE_SFS-480 - RSIS 40 - Fin */   
        HistoricoEstadoDTO retorno=null;
        try{
            
            PghHistoricoEstado historicoEstado=new PghHistoricoEstado();
            if(idExpediente!=null){
                historicoEstado.setIdExpediente(new PghExpediente(idExpediente));
            }else if(idOrdenServicio!=null){
                historicoEstado.setIdOrdenServicio(new PghOrdenServicio(idOrdenServicio));
            }
            historicoEstado.setIdEstadoProceso(new PghEstadoProceso(idEstadoProceso));
            historicoEstado.setIdPersonalOri(new PghPersonal(idPersonalOri));
            historicoEstado.setIdPersonalDest(new PghPersonal(idPersonalDest));
            historicoEstado.setIdTipoHistorico(new MdiMaestroColumna(idTipoHistorico));
            historicoEstado.setEstado(Constantes.ESTADO_ACTIVO);
            /* OSINE_SFS-480 - RSIS 40 - Inicio */
            if(idPeticion!=null){historicoEstado.setIdPeticion(new MdiMaestroColumna(idPeticion));}
            if(idMotivo!=null){historicoEstado.setIdMotivo(new MdiMaestroColumna(idMotivo));}
            /* OSINE_SFS-480 - RSIS 40 - Fin */
            if(motivoReasignacion!=null){
                historicoEstado.setMotivoReasignacion(motivoReasignacion.toUpperCase());
            }            
            historicoEstado.setDatosAuditoria(usuarioDTO);
            crud.create(historicoEstado);
            retorno=HistoricoEstadoBuilder.toHistoricoEstadoDto(historicoEstado);
        }catch(Exception e){
            LOG.error("Error en registrar",e);
            HistoricoEstadoException ex = new HistoricoEstadoException(e.getMessage(), e);
            throw ex;
        }
        return retorno;
    }
    
    @Override
    public List<HistoricoEstadoDTO> find(HistoricoEstadoFilter filtro) throws HistoricoEstadoException{
        List<HistoricoEstadoDTO> listado=null;
        try{
            Query query = getFindQuery(filtro);
            List<PghHistoricoEstado> lista = query.getResultList();
            listado = HistoricoEstadoBuilder.toListHistoricoEstadoDto(lista);
        }catch(Exception e){
            LOG.error("error en find",e);
        }
        return listado;
    }
    
    private Query getFindQuery(HistoricoEstadoFilter filtro) {
        Query query=null;
        try{
            if(filtro.getIdOrdenServicio()!=null){
                query = crud.getEm().createNamedQuery("PghHistoricoEstado.findByIdOrdenServicio");
            }else{
                query = crud.getEm().createNamedQuery("PghHistoricoEstado.findAll");
            }
            
            if(filtro.getIdOrdenServicio()!=null){
                query.setParameter("idOrdenServicio",filtro.getIdOrdenServicio());
            }            
        }catch(Exception e){
            LOG.error("Error getFindQuery: "+e.getMessage());
        }
        return query;
    }
}
