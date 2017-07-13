/**
* Resumen		
* Objeto			: PeriodoMedidaSeguridadDAOImpl.java
* Descripción		: Clase DAOImpl PeriodoMedidaSeguridadDAOImpl
* Fecha de Creación	: 12/10/2016
* PR de Creación	: OSINE_SFS-791
* Autor				: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------				
* OSINE_SFS-791     12/10/2016      Mario Dioses Fernandez          Crear la tarea automática que cancele el registro de hidrocarburos
*/ 
package gob.osinergmin.inpsweb.service.dao.impl;
import gob.osinergmin.inpsweb.domain.PghPeriodoMedidaSeguridad;
import gob.osinergmin.inpsweb.domain.builder.PeriodoMedidaSeguridadBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.PeriodoMedidaSeguridadDAO;
import gob.osinergmin.inpsweb.service.exception.PeriodoMedidaSeguridadException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.PeriodoMedidaSeguridadDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.PeriodoMedidaSeguridadFilter;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
/**
 *
 * @author zchaupis
 */
@Repository("periodoMedidaSeguridadDAO")
public class PeriodoMedidaSeguridadDAOImpl implements PeriodoMedidaSeguridadDAO {
    private static final Logger LOG = LoggerFactory.getLogger(PeriodoMedidaSeguridadDAOImpl.class);
    
    @Inject
    private CrudDAO crud;
    
    @Override
    public List<PeriodoMedidaSeguridadDTO> getListPeriodoMedidaSeguridad(PeriodoMedidaSeguridadFilter filtro) throws PeriodoMedidaSeguridadException{
      LOG.info("PeriodoMedidaSeguridadDAOImpl:getListPeriodoMedidaSeguridad-inicio");
		List<PeriodoMedidaSeguridadDTO> retorno=null;
        try{
            String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT s "
                + "FROM PghPeriodoMedidaSeguridad s "
                + "WHERE s.estado=:estadoActivo ");
            if(filtro.getIdPeriodoMedidaSeguridad()!=null){
                jpql.append(" and s.idPeriodoMedidaSeguridad=:idPeriodoMedidaSeguridad ");
            }
            if(filtro.getIdexpediente()!=null){
            	jpql.append(" and s.idExpediente.idExpediente =:idExpediente ");
            }
            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            //settear parametros
            query.setParameter("estadoActivo",Constantes.ESTADO_ACTIVO);
            if(filtro.getIdPeriodoMedidaSeguridad()!=null){
                query.setParameter("idPeriodoMedidaSeguridad",filtro.getIdPeriodoMedidaSeguridad());
            }
            if(filtro.getIdexpediente()!=null){
            	    query.setParameter("idExpediente",filtro.getIdexpediente());
            }
            retorno= PeriodoMedidaSeguridadBuilder.toListPeriodoMedidaSeguridadDTO(query.getResultList());
        }catch(Exception e){
            LOG.error("error en getListPeriodoMedidaSeguridad",e);
        }
        LOG.info("PeriodoMedidaSeguridadDAOImpl:getListPeriodoMedidaSeguridad-fin");
        return retorno;
    }

    @Override
    @Transactional
    public PeriodoMedidaSeguridadDTO registrarPeriodoMedidaSeguridad(PeriodoMedidaSeguridadDTO periodoMedidaSeguridadDTO, UsuarioDTO usuarioDTO) 
          throws PeriodoMedidaSeguridadException {
        LOG.info("PeriodoMedidaSeguridadDAOImpl : registrarPeriodoMedidaSeguridad-inicio");
        PeriodoMedidaSeguridadDTO retorno = null;
        try {
            PghPeriodoMedidaSeguridad periodoMedidaSeguridad = PeriodoMedidaSeguridadBuilder.toPeriodoMedidaSeguridadDomain(periodoMedidaSeguridadDTO);
            periodoMedidaSeguridad.setDatosAuditoria(usuarioDTO);
            periodoMedidaSeguridad.setEstado(Constantes.ESTADO_ACTIVO);
            crud.create(periodoMedidaSeguridad);
            retorno = PeriodoMedidaSeguridadBuilder.toPeriodoMedidaSeguridadDTO(periodoMedidaSeguridad);
        } catch (Exception e) {
            LOG.error("Error en registrar", e);
            PeriodoMedidaSeguridadException ex = new PeriodoMedidaSeguridadException(e.getMessage(), e);
            throw ex;
        }
        LOG.info("PeriodoMedidaSeguridadDAOImpl : registrarPeriodoMedidaSeguridad-fin");
        return retorno;
    }    
    
    /* OSINE_SFS-791 - RSIS 47- Inicio */ 
	public PeriodoMedidaSeguridadDTO actualizarPeriodoMedidaSeguridad(PeriodoMedidaSeguridadDTO periodoMedidaSeguridad, UsuarioDTO UsuarioDTO) throws PeriodoMedidaSeguridadException{
		LOG.info("inicio actualizarPeriodoMedidaSeguridadactualizarPeriodoMedidaSeguridad");
		try {
			PghPeriodoMedidaSeguridad pghPeriodoMedidaSeguridad=PeriodoMedidaSeguridadBuilder.toPeriodoMedidaSeguridadDomain(periodoMedidaSeguridad);
			pghPeriodoMedidaSeguridad.setDatosAuditoria(UsuarioDTO);			
			pghPeriodoMedidaSeguridad=crud.update(pghPeriodoMedidaSeguridad);			
			periodoMedidaSeguridad=PeriodoMedidaSeguridadBuilder.toPeriodoMedidaSeguridadDTO(pghPeriodoMedidaSeguridad);
			LOG.info("fin actualizar");
		} catch(Exception e){
			LOG.error("error actualizar", e);
			throw new PeriodoMedidaSeguridadException(e);
		}
		return periodoMedidaSeguridad;
	}
	/* OSINE_SFS-791 - RSIS 47- Fin */
}
