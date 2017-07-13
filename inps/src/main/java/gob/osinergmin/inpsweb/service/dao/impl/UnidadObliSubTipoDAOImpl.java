package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.PghUnidObliSubTipo;
import gob.osinergmin.inpsweb.domain.builder.UnidadObligacionSubTipoBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.UnidadObliSubTipoDAO;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.UnidadObliSubTipoDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service("unidadObliSubTipoDAO")
public class UnidadObliSubTipoDAOImpl implements UnidadObliSubTipoDAO{
	private static final Logger LOG = LoggerFactory.getLogger(UnidadObliSubTipoDAOImpl.class);
	
	@Inject
    private CrudDAO crud;	
	@Override
	public UnidadObliSubTipoDTO guardarUnidadMuestral(UnidadObliSubTipoDTO unidadObliSubTipo,UsuarioDTO usuarioDTO) {
		UnidadObliSubTipoDTO retorno = null;
        try{
            PghUnidObliSubTipo registroDAO = UnidadObligacionSubTipoBuilder.getObligacionSubTipo(unidadObliSubTipo);
            registroDAO.setDatosAuditoria(usuarioDTO);
            crud.create(registroDAO);
            
            retorno=UnidadObligacionSubTipoBuilder.toObligacionSubTipoDto(registroDAO);
        }catch(Exception e){
            e.printStackTrace();
        }
        return retorno;
	}
	
	@Override
	public List<UnidadObliSubTipoDTO> listarPruebaMuestralxPeriodoxSubTipo(UnidadObliSubTipoDTO filtroPruebaMuestral) {
		List<UnidadObliSubTipoDTO> listado=null;
		Query query = crud.getEm().createNamedQuery("PghUnidObliSubTipo.findByPeriodoBySubTipo");
		if(filtroPruebaMuestral.getIdObligacionSubTipoF()!=null){
			query.setParameter("idObligacionSubTipo", filtroPruebaMuestral.getIdObligacionSubTipoF());
		}
		if(filtroPruebaMuestral.getPeriodo()!=null){
			query.setParameter("periodo",filtroPruebaMuestral.getPeriodo());
		}
		if(filtroPruebaMuestral.getEstado()!=null){
			query.setParameter("estado", filtroPruebaMuestral.getEstado());
		}		
        listado = UnidadObligacionSubTipoBuilder.toListUnidadObligacionSubTipoDto(query.getResultList());        
        return listado;  
	}

	@Override
	public UnidadObliSubTipoDTO updateUnidadMuestral(UnidadObliSubTipoDTO unidadMuestraltoUpdate, UsuarioDTO usuarioDTO) {
		UnidadObliSubTipoDTO retorno = null;
        try{
        	String supervisado=unidadMuestraltoUpdate.getFlagSupOrdenServicio();
        	unidadMuestraltoUpdate.setFlagSupOrdenServicio(Constantes.ESTADO_INACTIVO);
        	List<UnidadObliSubTipoDTO> listado=null;
    		Query query = crud.getEm().createNamedQuery("PghUnidObliSubTipo.findByParameters");
    		if(unidadMuestraltoUpdate.getIdUnidadSupervisada().getIdUnidadSupervisada()!=null){
    			query.setParameter("idUnidadSupervisada", unidadMuestraltoUpdate.getIdUnidadSupervisada().getIdUnidadSupervisada());
    		}
    		if(unidadMuestraltoUpdate.getPeriodo()!=null){
    			query.setParameter("periodo",unidadMuestraltoUpdate.getPeriodo());
    		}
    		if(unidadMuestraltoUpdate.getEstado()!=null){
    			query.setParameter("estado", unidadMuestraltoUpdate.getEstado());
    		}	
    		if(unidadMuestraltoUpdate.getFlagSupOrdenServicio()!=null){
    			query.setParameter("flagSupOrdenServicio", unidadMuestraltoUpdate.getFlagSupOrdenServicio());
    		}	
            listado = UnidadObligacionSubTipoBuilder.toListUnidadObligacionSubTipoDto(query.getResultList());
            if(!listado.isEmpty()){
            	PghUnidObliSubTipo regToUpdate=crud.find(listado.get(0).getIdUnidObliSubtipo(), PghUnidObliSubTipo.class);
                regToUpdate.setFlagSupOrdenServicio(supervisado);
                regToUpdate.setDatosAuditoria(usuarioDTO);
                crud.update(regToUpdate);
                retorno=UnidadObligacionSubTipoBuilder.toObligacionSubTipoDto(regToUpdate);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return retorno;
	}

}
