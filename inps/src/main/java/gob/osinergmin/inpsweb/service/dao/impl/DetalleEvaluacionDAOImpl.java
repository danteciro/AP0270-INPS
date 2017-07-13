/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao.impl;
import gob.osinergmin.mdicommon.domain.ui.DetalleEvaluacionFilter;
import gob.osinergmin.inpsweb.domain.PghDetalleEvaluacion;
import gob.osinergmin.inpsweb.domain.builder.ActividadBuilder;
import gob.osinergmin.inpsweb.domain.builder.DetalleEvaluacionBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.DetalleEvaluacionDAO;
import gob.osinergmin.inpsweb.service.exception.DetalleEvaluacionException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.DetalleEvaluacionDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mdiosesf
 */
@Service("DetalleEvaluacionDAO")
public class DetalleEvaluacionDAOImpl implements DetalleEvaluacionDAO {
    private static final Logger LOG = LoggerFactory.getLogger(DetalleEvaluacionDAOImpl.class);    
    @Inject
    private CrudDAO crud;

    @Override
    public List<DetalleEvaluacionDTO> findDetalleEvaluacion(DetalleEvaluacionFilter filtro){
    	LOG.info("DetalleEvaluacionDAOImpl : findDetalleEvaluacion-inicio");
    	Query query=null;
        List<DetalleEvaluacionDTO> detalleEvaluacionDTO = null;
        try {   
        	 String queryString;
            List<PghDetalleEvaluacion> listaDetalleEvaluacion = null;           
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT p FROM PghDetalleEvaluacion p "
            		+ "WHERE p.idDetalleSupervision.idDetalleSupervision = :idDetalleSupervision ");
            if(filtro.getIdTipificacion()!=null){
            	jpql.append("AND p.idTipificacion.idTipificacion = :idTipificacion ");
            } else {
            	jpql.append("AND p.idTipificacion.idTipificacion IS NULL ");
            }
            if(filtro.getIdCriterio()!=null){
            	jpql.append("AND p.idCriterio.idCriterio = :idCriterio ");
            } else {
            	jpql.append("AND p.idCriterio.idCriterio IS NULL ");
            } 
            if(filtro.getFlagResultado()!=null){
            	jpql.append("AND p.flagResultado = :flagResultado ");
            }
            queryString = jpql.toString();
            query = this.crud.getEm().createQuery(queryString);
            if(filtro.getIdTipificacion()!=null){
                query.setParameter("idTipificacion",filtro.getIdTipificacion());
            }
            if(filtro.getIdCriterio()!=null){
                query.setParameter("idCriterio",filtro.getIdCriterio());
            }  
            if(filtro.getIdDetalleSupervision()!=null){
                query.setParameter("idDetalleSupervision",filtro.getIdDetalleSupervision());
            }  
            if(filtro.getFlagResultado()!=null){
                query.setParameter("flagResultado",filtro.getFlagResultado());
            } 
            detalleEvaluacionDTO = DetalleEvaluacionBuilder.toListfindDetalleEvaluacionDto(query.getResultList());
        } catch (Exception e) {
            LOG.error("error en findDetalleEvaluacion", e);
        }
        return detalleEvaluacionDTO;
    }

	@Override
	public List<DetalleEvaluacionDTO> listarDetalleEvaluacion(DetalleEvaluacionFilter filtro) throws DetalleEvaluacionException {
		LOG.info("DetalleEvaluacionDAOImpl : listarDetalleEvaluacion-inicio");
		List<DetalleEvaluacionDTO> listado = null;			
		Query query = getFindQuery(filtro);
		List<Object[]> lista = query.getResultList();
		listado = DetalleEvaluacionBuilder.toListDetalleEvaluacionDto(lista);
		return listado;
	}
	
	private Query getFindQuery(DetalleEvaluacionFilter filtro) {
        Query query=null;
        try{
        	String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT "
            		+ "DE.ID_DETALLE_CRITERIO, DE.DESCRIPCION_RESULTADO, DE.ESTADO AS ESTADO_DET_EVAL, DE.ID_DETALLE_EVALUACION, DE.FLAG_REGISTRADO, DE.FLAG_RESULTADO AS DE_FLAG_RESULTADO, "
            		+ "DS.ID_DETALLE_SUPERVISION AS DS_ID_DETALLE_SUPERVISION, DS.FLAG_RESULTADO, DS.DESCRIPCION_RESULTADO AS DS_DESCRIPCION_RESULTADO, DS.ID_SUPERVISION, DS.ID_OBLIGACION, OT.ID_TIPIFICACION AS OT_ID_TIPIFICACION, DS.ID_CRITERIO AS DS_ID_CRITERIO, DS.ID_SANCION_ESPECIFICA, DS.FLAG_REGISTRADO AS DS_FLAG_REGISTRADO, DS.ID_DETALLE_SUPERVISION_ANT, DS.ESTADO AS ESTADO_DET_SUP, " 
            		+ "T.DESCRIPCION AS T_DESCRIPCION_TIPIF, T.ID_TIPO_MONEDA, T.SANCION_MONETARIA, T.ID_TIPIFICACION_PADRE, T.BASES_LEGALES AS T_BASES_LEGALES, T.ESTADO AS T_ESTADO, T.COD_TIPIFICACION, T.CLASE_SANCION, "            		  
                    + "C.DESCRIPCION AS C_DESCRIPCION_CRIT, C.ID_PADRE, C.COD_TRAZABILIDAD, C.COD_ACCION, C.TIPO_CRITERIO, C.SANCION_MONETARIA AS C_SANCION_MONETARIA, C.BASES_LEGALES AS C_BASES_LEGALES, "
            		+ "TTSAN.OTRASSANCIONES, "
                    + "(SELECT(CASE WHEN COUNT(*) = 0 THEN 0  WHEN COUNT(*) > 0 THEN 1 END) FROM PGH_OBLI_TIPI_CRITERIO OTC WHERE OTC.ID_OBLIGACION =  OT.ID_OBLIGACION  AND OTC.ID_TIPIFICACION =  OT.ID_TIPIFICACION AND OTC.ESTADO = '1')  AS TIENECRITERIO "
                	+ "FROM PGH_DETALLE_SUPERVISION DS "
                	+ "LEFT JOIN PGH_OBLIGACION_TIPIFICACION OT ON OT.ID_OBLIGACION = DS.ID_OBLIGACION AND OT.ESTADO = 1 "
                	+ "LEFT JOIN PGH_DETALLE_EVALUACION DE ON DE.ID_DETALLE_SUPERVISION = DS.ID_DETALLE_SUPERVISION AND DE.ESTADO = 1 AND DE.ID_CRITERIO IS NULL AND (DE.ID_TIPIFICACION IS NULL OR DE.ID_TIPIFICACION = OT.ID_TIPIFICACION) AND DE.FLAG_RESULTADO = :flagResultado "
                	+ "LEFT JOIN PGH_TIPIFICACION T ON T.ID_TIPIFICACION = OT.ID_TIPIFICACION AND T.ESTADO = 1 "
                	+ "LEFT JOIN "
                	+ "(SELECT TS.ID_TIPIFICACION, RTRIM(XMLAGG(XMLELEMENT(PGH_TIPO_SANCION, TSAN.ABREVIATURA ||', ')).EXTRACT('//text()'), ', ') AS OTRASSANCIONES "
                	+ "FROM PGH_TIPIFICACION_SANCION TS INNER JOIN PGH_TIPO_SANCION TSAN ON TS.ID_TIPO_SANCION = TSAN.ID_TIPO_SANCION WHERE TS.ESTADO = 1 AND TSAN.ESTADO = 1 "
                	+ "GROUP BY TS.ID_TIPIFICACION) TTSAN ON TTSAN.ID_TIPIFICACION = OT.ID_TIPIFICACION "
                	+ "LEFT JOIN PGH_CRITERIO C ON C.ID_CRITERIO = DS.ID_CRITERIO AND C.ESTADO = 1");
            jpql.append(" WHERE DS.ESTADO = 1 ");
            if(filtro.getIdDetalleSupervision()!=null){
            	jpql.append(" AND DS.ID_DETALLE_SUPERVISION = :idDetalleSupervision ");
            }
            if(filtro.getIdObligacion()!=null){
            	jpql.append(" AND DS.ID_OBLIGACION = :idObligacion ");
            }    
            queryString = jpql.toString();
            query = this.crud.getEm().createNativeQuery(queryString);
            if(filtro.getIdDetalleSupervision()!=null){
                query.setParameter("idDetalleSupervision",filtro.getIdDetalleSupervision());
            }
            if(filtro.getIdObligacion()!=null){
                query.setParameter("idObligacion",filtro.getIdObligacion());
            }  
            if(filtro.getFlagResultado()!=null){
                query.setParameter("flagResultado",filtro.getFlagResultado());
            } 
        }catch(Exception e){
            LOG.error("Error getFindQuery: "+e.getMessage());
        }
        return query;
    }
	
	@Override
    @Transactional(rollbackFor=DetalleEvaluacionException.class)
	public DetalleEvaluacionDTO actualizar(DetalleEvaluacionDTO detalleEvaluacionDTO, UsuarioDTO usuarioDTO) throws DetalleEvaluacionException {
	LOG.info("DetalleEvaluacionDAOImpl : actualizar-inicio");
		DetalleEvaluacionDTO retorno=null;
        try{
        	String queryString = "";
        	queryString += "UPDATE PghDetalleEvaluacion p SET "
            		+ " p.usuarioActualizacion = '" + usuarioDTO.getCodigo() + "' , "
    				+ " p.terminalActualizacion = '" + usuarioDTO.getTerminal() + "' , "
    				+ " p.flagRegistrado = '" + detalleEvaluacionDTO.getFlagRegistrado() + "' , "
    				+ " p.descripcionResultado = '" + detalleEvaluacionDTO.getDescripcionResultado() + "' , "
        			+ " p.flagResultado = '" + detalleEvaluacionDTO.getFlagResultado() + "' ";
		        	if(detalleEvaluacionDTO.getCriterio()!=null && detalleEvaluacionDTO.getCriterio().getIdCriterio()!=null){
		        		queryString += ", p.idCriterio.idCriterio  = " + detalleEvaluacionDTO.getCriterio().getIdCriterio() + " ";
		            }		
		    		if(detalleEvaluacionDTO.getTipificacion()!=null && detalleEvaluacionDTO.getTipificacion().getIdTipificacion()!=null){
		    			queryString += ", p.idTipificacion.idTipificacion = " + detalleEvaluacionDTO.getTipificacion().getIdTipificacion() + " ";
		            }
		    		if(detalleEvaluacionDTO.getDetalleCriterio()!=null && detalleEvaluacionDTO.getDetalleCriterio().getIdDetalleCriterio()!=null){
		    			queryString += ", p.idDetalleCriterio.idDetalleCriterio = " + detalleEvaluacionDTO.getDetalleCriterio().getIdDetalleCriterio() + " ";
		            }
		    		if(detalleEvaluacionDTO.getDetalleSupervision()!=null && detalleEvaluacionDTO.getDetalleSupervision().getIdDetalleSupervision()!=null){
		    			queryString += ", p.idDetalleSupervision.idDetalleSupervision = " + detalleEvaluacionDTO.getDetalleSupervision().getIdDetalleSupervision() + " ";
		            }
		    		queryString += " WHERE ESTADO = 1 ";
		    		if(detalleEvaluacionDTO.getCriterio()!=null && detalleEvaluacionDTO.getCriterio().getIdCriterio()!=null){
		    			queryString += "AND p.idCriterio.idCriterio = " + detalleEvaluacionDTO.getCriterio().getIdCriterio() + " ";
		            }
		    		if(detalleEvaluacionDTO.getTipificacion()!=null && detalleEvaluacionDTO.getTipificacion().getIdTipificacion()!=null){
		    			queryString += "AND p.idTipificacion.idTipificacion = " + detalleEvaluacionDTO.getTipificacion().getIdTipificacion() + " ";
		            }
		    		if(detalleEvaluacionDTO.getDetalleSupervision()!=null && detalleEvaluacionDTO.getDetalleSupervision().getIdDetalleSupervision()!=null){
		    			queryString += "AND p.idDetalleSupervision.idDetalleSupervision = " + detalleEvaluacionDTO.getDetalleSupervision().getIdDetalleSupervision() + " ";
		            } 
		    		if(detalleEvaluacionDTO.getFlagResultado()!=null && !detalleEvaluacionDTO.getFlagResultado().equals(Constantes.CONSTANTE_VACIA)){
		    			queryString += "AND p.flagResultado = " + detalleEvaluacionDTO.getFlagResultado()+ " ";
		            }  
    	    crud.getEm().createQuery(queryString).executeUpdate();
    		retorno=detalleEvaluacionDTO;
        }catch(Exception e){
            LOG.error("Error en actualizar",e);
            DetalleEvaluacionException ex = new DetalleEvaluacionException(e.getMessage(), e);
            throw ex;
        }
        LOG.info("DetalleSupervisionDAOImpl : actualizar-fin");
        return retorno;
	}
	
	@Override
	public DetalleEvaluacionDTO registrar(DetalleEvaluacionDTO detalleEvaluacionDTO, UsuarioDTO usuarioDTO) throws DetalleEvaluacionException {
		LOG.info("DetalleEvaluacionDAOImpl : registrar-inicio");
		DetalleEvaluacionDTO retorno=null;
        try{
        	PghDetalleEvaluacion detalleEvaluacion = DetalleEvaluacionBuilder.toDetalleEvaluacionDomain(detalleEvaluacionDTO);
        	detalleEvaluacion.setDatosAuditoria(usuarioDTO);
        	detalleEvaluacion.setEstado(Constantes.ESTADO_ACTIVO);
            crud.create(detalleEvaluacion);
            retorno=DetalleEvaluacionBuilder.tofindDetalleEvaluacionDto(detalleEvaluacion);
        }catch(Exception e){
            LOG.error("Error en registrar",e);
            DetalleEvaluacionException ex = new DetalleEvaluacionException(e.getMessage(), e);
            throw ex;
        }
        LOG.info("DetalleEvaluacionDAOImpl : registrar-fin");
        return retorno;
	}
}
