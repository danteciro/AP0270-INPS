/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao.impl;
import gob.osinergmin.inpsweb.domain.PghConfFiltroEmpSup;
import gob.osinergmin.inpsweb.domain.builder.ConfFiltroEmpSupBuilder;
import gob.osinergmin.inpsweb.service.dao.ConfFiltroEmpSupDAO;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.exception.ConfFiltroEmpSupException;
import gob.osinergmin.mdicommon.domain.dto.ConfFiltroEmpSupDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.ConfFiltroEmpSupFilter;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
*
* @author mdiosesf
*/

@Service
@Transactional
public class ConfFiltroEmpSupDAOImpl implements ConfFiltroEmpSupDAO{	
	private static final Logger LOG = LoggerFactory.getLogger(ConfFiltroEmpSupDAOImpl.class);
	
	@Inject
    private CrudDAO crud;	

	@Override
	public List<ConfFiltroEmpSupDTO> findConfFiltroEmpSup(ConfFiltroEmpSupFilter filtro) throws ConfFiltroEmpSupException {
		Query query=null;
		List<ConfFiltroEmpSupDTO> listado=null;
        try{
            if(filtro.getIdFiltroEmpSup()!=null){
                query = crud.getEm().createNamedQuery("PghConfFiltroEmpSup.findByIdFiltroEmpSup");
            } else if(filtro.getIdFiltro()!=null && filtro.getIdUnidadOrganica()!=null){
                query = crud.getEm().createNamedQuery("PghConfFiltroEmpSup.findByIdFiltroIdUnidadOrganica");
            } else if(filtro.getIdUnidadOrganica()!=null && filtro.getIdFiltro()==null){
                query = crud.getEm().createNamedQuery("PghConfFiltroEmpSup.findByIdUnidadOrganica");
            } else {
            	 query = crud.getEm().createNamedQuery("PghConfFiltroEmpSup.findAll");
            }    
            
            if(filtro.getIdFiltroEmpSup()!=null){
            	query.setParameter("idFiltroEmpSup",filtro.getIdUnidadOrganica());
            } if(filtro.getIdFiltro()!=null){
            	query.setParameter("idFiltro",filtro.getIdFiltro());
            } if(filtro.getIdUnidadOrganica()!=null){
            	query.setParameter("idUnidadOrganica",filtro.getIdUnidadOrganica());
            }       
            listado = ConfFiltroEmpSupBuilder.toListConfFiltroEmpSupDto(query.getResultList());
            return listado;
        }catch(Exception e){
            LOG.error("Error getFindQuery: "+e.getMessage());
        }
		return listado;		
	}

	@Override
	public ConfFiltroEmpSupDTO create(ConfFiltroEmpSupDTO confFiltroEmpSupDTO, UsuarioDTO usuarioDTO) throws ConfFiltroEmpSupException {
		ConfFiltroEmpSupDTO retorno = null;
        try{
        	PghConfFiltroEmpSup registro=ConfFiltroEmpSupBuilder.toConfFiltroEmpSupTabla(confFiltroEmpSupDTO);
            registro.setDatosAuditoria(usuarioDTO);
            crud.create(registro);
            retorno = ConfFiltroEmpSupBuilder.toConfFiltroEmpSupDto(registro);
        }catch(Exception ex){
            LOG.error("Error en update",ex);
        }
        return retorno;
	}

	@Override
	public ConfFiltroEmpSupDTO update(ConfFiltroEmpSupDTO confFiltroEmpSupDTO, UsuarioDTO usuarioDTO) throws ConfFiltroEmpSupException {
		ConfFiltroEmpSupDTO retorno = null;
        try{
        	PghConfFiltroEmpSup registro=ConfFiltroEmpSupBuilder.toConfFiltroEmpSupTabla(confFiltroEmpSupDTO);
            registro.setDatosAuditoria(usuarioDTO);
            crud.update(registro);
            retorno = ConfFiltroEmpSupBuilder.toConfFiltroEmpSupDto(registro);
        }catch(Exception ex){
            LOG.error("Error en update",ex);
        }
        return retorno;
	}
}