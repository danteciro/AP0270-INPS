/**
 * Resumen 
 * Objeto				: ComentarioIncumplimientoDAOImpl.java 
 * Descripción	        : 
 * Fecha de Creación	: 
 * PR de Creación		: 
 * Autor				: 
 * ---------------------------------------------------------------------------------------------------
 * Modificaciones   Fecha             Nombre               Descripción
 * ---------------------------------------------------------------------------------------------------
 * OSINE_MAN_DSR_0037  | 18/06/2017     | Carlos Quijano Chavez::ADAPTER  | Agrega comentarios adicionales de incumplimiento
 */
package gob.osinergmin.inpsweb.service.dao.impl;
/* OSINE_MAN_DSR_0037 - Inicio */
import gob.osinergmin.inpsweb.domain.PghComentarioDetsupervisionOpcional;
import gob.osinergmin.inpsweb.domain.PghDetalleSupervision;
import gob.osinergmin.inpsweb.domain.PghEscenarioIncumplido;
import gob.osinergmin.inpsweb.dto.ComentarioDetalleSupervisionOpcionalDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import org.springframework.transaction.annotation.Transactional;
/* OSINE_MAN_DSR_0037 - Fin */
import gob.osinergmin.inpsweb.domain.builder.ComentarioIncumplimientoBuilder;
import gob.osinergmin.inpsweb.service.dao.ComentarioIncumplimientoDAO;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.exception.ComentarioIncumplimientoException;
import gob.osinergmin.mdicommon.domain.dto.ComentarioIncumplimientoDTO;
import gob.osinergmin.mdicommon.domain.ui.ComentarioIncumplimientoFilter;
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
@Service("comentarioIncumplimientoDAO")
/* OSINE_MAN_DSR_0037 - Inicio */
@Transactional
/* OSINE_MAN_DSR_0037 - Fin */
public class ComentarioIncumplimientoDAOImpl implements ComentarioIncumplimientoDAO {
    private static final Logger LOG = LoggerFactory.getLogger(EscenarioIncumplimientoDAOImpl.class);
    @Inject
    private CrudDAO crud;    
    
    @Override
    public List<ComentarioIncumplimientoDTO> findByFilter(ComentarioIncumplimientoFilter filtro) throws ComentarioIncumplimientoException{
        LOG.info("findByFilter");
        List<ComentarioIncumplimientoDTO> retorno=null;
        try{
            Query query = getFindQuery(filtro);
            retorno = ComentarioIncumplimientoBuilder.toListComentarioIncumplimientoDto(query.getResultList());
        }catch(Exception e){
            LOG.error("Error en findByFilter",e);
        }
        return retorno;
    }
    
    private Query getFindQuery(ComentarioIncumplimientoFilter filtro) {
        Query query=null;
        try{
            if(filtro.getIdEsceIncumplimiento()!=null && filtro.getIdInfraccion()!=null){
                query = crud.getEm().createNamedQuery("PghComentarioIncumplimiento.findByIdEsceIncumplimientoIdInfraccion");
            } else if(filtro.getIdEsceIncumplimiento()!=null && filtro.getIdInfraccion()==null){
                query = crud.getEm().createNamedQuery("PghComentarioIncumplimiento.findByIdEsceIncumplimiento");
            }else if(filtro.getIdInfraccion()!=null && filtro.getIdEsceIncumplimiento()==null){
                query = crud.getEm().createNamedQuery("PghComentarioIncumplimiento.findByIdInfraccion");
            }else{
                query = crud.getEm().createNamedQuery("PghComentarioIncumplimiento.findAll");
            }
            
            if(filtro.getIdEsceIncumplimiento()!=null){
                query.setParameter("idEsceIncumplimiento",filtro.getIdEsceIncumplimiento());
            }        
            if(filtro.getIdInfraccion()!=null){
                query.setParameter("idInfraccion",filtro.getIdInfraccion());
            }        
        }catch(Exception e){
            LOG.error("Error getFindQuery",e);
        }
        return query;
    }
	/* OSINE_MAN_DSR_0037 - Inicio */
	@Override
	public Long agregarComentarioDetalleSupervisionOpcional(ComentarioDetalleSupervisionOpcionalDTO comentario,UsuarioDTO usuarioDTO)	throws ComentarioIncumplimientoException {
		PghComentarioDetsupervisionOpcional nuevoComentario=null;
		try{
			nuevoComentario=new PghComentarioDetsupervisionOpcional("1", comentario.getDescripcion(), new PghEscenarioIncumplido(comentario.getIdEscenarioIncumplido()), new PghDetalleSupervision(comentario.getIdDetalleSupervision()));
			nuevoComentario.setDatosAuditoria(usuarioDTO);
			nuevoComentario=crud.create(nuevoComentario);
			
		}catch(Exception e){
            LOG.error("Error create PghComentarioDetsupervisionOpcional",e);
        }
		return nuevoComentario.getIdComentarioDetsupervisionOpcional();
	}

	@Override
	public Long actualizarComentarioDetalleSupervisionOpcional(ComentarioDetalleSupervisionOpcionalDTO comentario,UsuarioDTO usuarioDTO)throws ComentarioIncumplimientoException {
		PghComentarioDetsupervisionOpcional nuevoComentario=null;
		try{
			nuevoComentario=new PghComentarioDetsupervisionOpcional(comentario.getIdComentarioDetalleSupervisionOpcional(),"A", comentario.getDescripcion(), new PghEscenarioIncumplido(comentario.getIdEscenarioIncumplido()), new PghDetalleSupervision(comentario.getIdDetalleSupervision()));
			nuevoComentario.setDatosAuditoria(usuarioDTO);
			nuevoComentario=crud.update(nuevoComentario);
			
		}catch(Exception e){
            LOG.error("Error update PghComentarioDetsupervisionOpcional",e);
        }
		return nuevoComentario.getIdComentarioDetsupervisionOpcional();
	}

	@Override
	public List<ComentarioDetalleSupervisionOpcionalDTO> listarComentarioOpcionales(Long idDetSupervision, Long idEscenarioInc)	throws ComentarioIncumplimientoException {
		List<ComentarioDetalleSupervisionOpcionalDTO> retorno=null;
		Query query=null;
		try{   
			query = crud.getEm().createNamedQuery("PghComentarioDetsupervisionOpcional.findByIdDetSupervisionAndIdEscenario"); 
			query.setParameter("p1",new PghEscenarioIncumplido(idEscenarioInc));
			query.setParameter("p2",new PghDetalleSupervision(idDetSupervision));
			
			retorno=ComentarioIncumplimientoBuilder.toComentarioIncumplimientoOpcionalDto(query.getResultList());
        }catch(Exception e){
            LOG.error("Error FindQuery",e);
        }
		return retorno;
	}
	/* OSINE_MAN_DSR_0037 - Fin */
}
