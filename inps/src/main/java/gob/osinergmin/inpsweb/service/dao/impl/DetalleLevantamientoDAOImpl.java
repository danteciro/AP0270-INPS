/**
* Resumen		
* Objeto			: DetalleLevantamientoDAOImpl.java
* Descripción		: Clase DAOImpl DetalleLevantamientoDAOImpl
* Fecha de Creación	: 07/10/2016
* PR de Creación	: OSINE_SFS-791
* Autor				: mdiosesf
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------				
* OSINE_SFS-791     07/10/2016      Mario Dioses Fernandez          Crear la funcionalidad "verificar levantamientos" que permita verificar el tipo de asignación para la orden de levantamiento DSR-CRITICIDAD.
* OSINE_SFS-791-RSIS48  |   21/10/2016   |   Luis García Reyna   |  Registrar Detalle Levantamiento
* OSINE_SFS-791     24/10/2016      Paul Moscoso                Crear Nuevo Campo Detalle Levantamiento Atiende
*/ 

package gob.osinergmin.inpsweb.service.dao.impl;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import gob.osinergmin.inpsweb.domain.PghDetalleLevantamiento;
import gob.osinergmin.inpsweb.domain.builder.DetalleLevantamientoBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.DetalleLevantamientoDAO;
import gob.osinergmin.inpsweb.service.exception.DetalleLevantamientoException;
import gob.osinergmin.mdicommon.domain.dto.DetalleLevantamientoDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.DetalleLevantamientoFilter;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service("DetalleLevantamientoDAO")
public class DetalleLevantamientoDAOImpl implements DetalleLevantamientoDAO {
	private static final Logger log = LoggerFactory.getLogger(DetalleLevantamientoDAOImpl.class);
	
	@Inject
    private CrudDAO crud;
	
	@Override
	@Transactional(readOnly=true)
	public List<DetalleLevantamientoDTO> find(DetalleLevantamientoFilter filtro) throws DetalleLevantamientoException {
		log.info("inicio find");
		Query query=null;
		List<DetalleLevantamientoDTO> listado=null;
        try {
            if(filtro.getIdDetalleLevantamiento()!=null){
                query = crud.getEm().createNamedQuery("PghDetalleLevantamiento.findByIdDetalleLevantamiento");
            }else if(filtro.getIdDetalleSupervision()!=null && filtro.getFlagUltimoRegistro()!=null){
            	query = crud.getEm().createNamedQuery("PghDetalleLevantamiento.findByFlagUltimoRegistro");
            }else if(filtro.getIdDetalleSupervision()!=null && filtro.getFlagUltimoRegistro()==null){
            	query = crud.getEm().createNamedQuery("PghDetalleLevantamiento.findByIdDetalleSupervision");
            }else if(filtro.getIdDetaLevaAtiende()!=null){
                query = crud.getEm().createNamedQuery("PghDetalleLevantamiento.findByIdDetaLevaAtiende");
            }else{
            	query = crud.getEm().createNamedQuery("PghDetalleLevantamiento.findAll");
            }        
            
            if(filtro.getIdDetalleLevantamiento()!=null){
    			query.setParameter("idDetalleLevantamiento", filtro.getIdDetalleLevantamiento());
    		} 
            if(filtro.getIdDetalleSupervision()!=null){
            	query.setParameter("idDetalleSupervision", filtro.getIdDetalleSupervision());
            } 
            if(filtro.getFlagUltimoRegistro()!=null){
            	query.setParameter("flagUltimoRegistro", filtro.getFlagUltimoRegistro());
            } 
            if(filtro.getIdDetaLevaAtiende()!=null){
            	query.setParameter("idDetaLevaAtiende", filtro.getIdDetaLevaAtiende() );
            } 
            
            listado = DetalleLevantamientoBuilder.toListDetalleLevantamientoDto(query.getResultList());
        } catch(Exception e){
            log.error("Error find: " + e);
            throw new DetalleLevantamientoException(e);
        }
		return listado;
	}
	
	@Override
	@Transactional(rollbackFor=DetalleLevantamientoException.class)
	public DetalleLevantamientoDTO registrar(DetalleLevantamientoDTO detalleLevantamiento, UsuarioDTO UsuarioDTO) throws DetalleLevantamientoException {
		log.info("inicio registrar");
		try{
			PghDetalleLevantamiento pghDetalleLevantamiento=DetalleLevantamientoBuilder.toDetalleLevantamientoDomain(detalleLevantamiento);
			pghDetalleLevantamiento.setDatosAuditoria(UsuarioDTO);
			pghDetalleLevantamiento=crud.create(pghDetalleLevantamiento);
			detalleLevantamiento=DetalleLevantamientoBuilder.tofindDetalleLevantamientoDto(pghDetalleLevantamiento);
			log.info("fin registrar");
		} catch(Exception e){
			log.error("error registrar", e);
			throw new DetalleLevantamientoException(e);
		}
		return detalleLevantamiento;
	}
	
	@Override
	@Transactional(rollbackFor=DetalleLevantamientoException.class)
	public DetalleLevantamientoDTO actualizar(DetalleLevantamientoDTO detalleLevantamiento, UsuarioDTO UsuarioDTO) throws DetalleLevantamientoException {
		log.info("inicio actualizar");
		try{
			PghDetalleLevantamiento pghDetalleLevantamiento=DetalleLevantamientoBuilder.toDetalleLevantamientoDomain(detalleLevantamiento);
			pghDetalleLevantamiento.setDatosAuditoria(UsuarioDTO);
			pghDetalleLevantamiento=crud.update(pghDetalleLevantamiento);
			detalleLevantamiento=DetalleLevantamientoBuilder.tofindDetalleLevantamientoDto(pghDetalleLevantamiento);
			log.info("fin actualizar");
		} catch(Exception e){
			log.error("error actualizar", e);
			throw new DetalleLevantamientoException(e);
		}
		return detalleLevantamiento;
	}
}
