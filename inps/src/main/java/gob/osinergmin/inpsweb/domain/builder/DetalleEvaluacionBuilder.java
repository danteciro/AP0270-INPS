/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain.builder;
import gob.osinergmin.inpsweb.domain.PghConfObligacion;
import gob.osinergmin.inpsweb.domain.PghCriterio;
import gob.osinergmin.inpsweb.domain.PghDetalleCriterio;
import gob.osinergmin.inpsweb.domain.PghDetalleEvaluacion;
import gob.osinergmin.inpsweb.domain.PghDetalleSupervision;
import gob.osinergmin.inpsweb.domain.PghObligacion;
import gob.osinergmin.inpsweb.domain.PghSupervision;
import gob.osinergmin.inpsweb.domain.PghTipificacion;
import gob.osinergmin.inpsweb.service.dao.SupervisionDAO;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.ConfObligacionDTO;
import gob.osinergmin.mdicommon.domain.dto.CriterioDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleCriterioDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleEvaluacionDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.ObligacionDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.TipificacionDTO;
import gob.osinergmin.mdicommon.domain.dto.TipificacionSancionDTO;
import gob.osinergmin.mdicommon.domain.dto.TipoSancionDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author jpiro
 */
public class DetalleEvaluacionBuilder {
		
	public static List<DetalleEvaluacionDTO> toListfindDetalleEvaluacionDto(List<PghDetalleEvaluacion> lista) {
    	DetalleEvaluacionDTO registroDTO;
        List<DetalleEvaluacionDTO> retorno = new ArrayList<DetalleEvaluacionDTO>();
        if (lista != null) {
        	for(PghDetalleEvaluacion maestro:lista){
                registroDTO = tofindDetalleEvaluacionDto(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }
	
    public static List<DetalleEvaluacionDTO> toListDetalleEvaluacionDto(List<Object[]> lista) {
    	DetalleEvaluacionDTO registroDTO;
        List<DetalleEvaluacionDTO> retorno = new ArrayList<DetalleEvaluacionDTO>();
        if (lista != null) {
        	for(Object[] maestro:lista){
                registroDTO = toDetalleEvaluacionDto(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    } 
    
    public static DetalleEvaluacionDTO tofindDetalleEvaluacionDto(PghDetalleEvaluacion registro){
    	DetalleEvaluacionDTO detalleEvaluacionDTO = new DetalleEvaluacionDTO();
		if(registro!=null){
			CriterioDTO criterioDTO = new CriterioDTO();
			if(registro.getIdCriterio()!=null && registro.getIdCriterio().getIdCriterio()!=null) criterioDTO.setIdCriterio(registro.getIdCriterio().getIdCriterio());
			detalleEvaluacionDTO.setCriterio(criterioDTO);
			
			TipificacionDTO tipificacionDTO = new TipificacionDTO();
			if(registro.getIdTipificacion()!=null && registro.getIdTipificacion().getIdTipificacion()!=null) criterioDTO.setIdCriterio(registro.getIdTipificacion().getIdTipificacion());
			detalleEvaluacionDTO.setTipificacion(tipificacionDTO);
			
			DetalleSupervisionDTO detalleSupervisionDTO = new DetalleSupervisionDTO();
			if(registro.getIdDetalleSupervision()!=null && registro.getIdDetalleSupervision().getIdDetalleSupervision()!=null) detalleSupervisionDTO.setIdDetalleSupervision(registro.getIdDetalleSupervision().getIdDetalleSupervision());
			detalleEvaluacionDTO.setDetalleSupervision(detalleSupervisionDTO);
			
			if(registro.getDescripcionResultado()!=null) { detalleEvaluacionDTO.setDescripcionResultado(registro.getDescripcionResultado()); } else { detalleEvaluacionDTO.setDescripcionResultado(Constantes.CONSTANTE_VACIA); }
		   	if(registro.getFlagRegistrado()!=null) detalleEvaluacionDTO.setFlagRegistrado(registro.getFlagRegistrado());
		   	if(registro.getIdDetalleEvaluacion()!=null) detalleEvaluacionDTO.setIdDetalleEvaluacion(new Long(registro.getIdDetalleEvaluacion()));
		   	if(registro.getEstado()!=null) detalleEvaluacionDTO.setEstado(registro.getEstado());  	   	 
		   	DetalleCriterioDTO detalleCriterioDTO = new DetalleCriterioDTO();
		   	if(registro.getIdDetalleCriterio()!=null && registro.getIdDetalleCriterio().getIdDetalleCriterio()!=null) detalleCriterioDTO.setIdDetalleCriterio(registro.getIdDetalleCriterio().getIdDetalleCriterio());
		   	detalleEvaluacionDTO.setDetalleCriterio(detalleCriterioDTO);
		}
		return detalleEvaluacionDTO;
	}
    
    public static DetalleEvaluacionDTO toDetalleEvaluacionDto(Object[] registro) {    	
		 DetalleEvaluacionDTO registroDTO = new DetalleEvaluacionDTO();
		 
		 CriterioDTO criterioDTO = new CriterioDTO();
		 if(registro[12]!=null) criterioDTO.setIdCriterio(new Long(registro[12].toString()));
		 if(registro[25]!=null) criterioDTO.setDescripcion(registro[25].toString());
		 if(registro[30]!=null)criterioDTO.setSancionMonetaria(registro[30].toString());
		 registroDTO.setCriterio(criterioDTO);
			 
		 TipificacionDTO tipificacionDTO = new TipificacionDTO();
		 if(registro[11]!=null) tipificacionDTO.setIdTipificacion(new Long(registro[11].toString()));
		 if(registro[23]!=null) tipificacionDTO.setCodTipificacion(registro[23].toString());
		 if(registro[17]!=null) tipificacionDTO.setDescripcion(registro[17].toString());
		 if(registro[18]!=null) tipificacionDTO.setIdTipoMoneda(new Long(registro[18].toString()));        	 
		 if(registro[19]!=null) tipificacionDTO.setSancionMonetaria(registro[19].toString());
		 if(registro[32]!=null) tipificacionDTO.setOtrasSanciones(registro[32].toString());
		 if(registro[33]!=null) tipificacionDTO.setTieneCriterio(new Long(registro[33].toString()));	
		 registroDTO.setTipificacion(tipificacionDTO);
		 			
		 DetalleSupervisionDTO detalleSupervisionDTO = new DetalleSupervisionDTO();
		 if(registro[6]!=null) detalleSupervisionDTO.setIdDetalleSupervision(new Long(registro[6].toString()));
		 if(registro[7]!=null) detalleSupervisionDTO.setFlagResultado(registro[7].toString());	   	 
		 if(registro[8]!=null) detalleSupervisionDTO.setDescripcionResultado(registro[8].toString());
		 SupervisionDTO supervisionDTO = new SupervisionDTO();
		 if(registro[9]!=null) supervisionDTO.setIdSupervision(new Long(registro[9].toString()));
		 detalleSupervisionDTO.setSupervision(supervisionDTO);
		 ObligacionDTO obligacionDTO = new ObligacionDTO();
		 if(registro[10]!=null) obligacionDTO.setIdObligacion(new Long(registro[10].toString()));
		 detalleSupervisionDTO.setObligacion(obligacionDTO);
		 detalleSupervisionDTO.setTipificacion(tipificacionDTO);    	 
		 detalleSupervisionDTO.setCriterio(criterioDTO);
		 if(registro[14]!=null) detalleSupervisionDTO.setFlagRegistrado(registro[14].toString());	  
		 if(registro[15]!=null) detalleSupervisionDTO.setIdDetalleSupervisionAnt(new Long(registro[15].toString()));
		 if(registro[16]!=null) detalleSupervisionDTO.setEstado(registro[16].toString());
	   	 registroDTO.setDetalleSupervision(detalleSupervisionDTO);
		
	   	 if(registro[1]!=null) { registroDTO.setDescripcionResultado(registro[1].toString()); } else { registroDTO.setDescripcionResultado(Constantes.CONSTANTE_VACIA); }
	   	 if(registro[4]!=null) { registroDTO.setFlagRegistrado(registro[4].toString()); } else { registroDTO.setFlagRegistrado(Constantes.FLAG_REGISTRADO_NO); }
	   	 if(registro[5]!=null) { registroDTO.setFlagResultado(registro[5].toString()); }
	   	 if(registro[3]!=null) registroDTO.setIdDetalleEvaluacion(new Long(registro[3].toString()));
	   	 if(registro[2]!=null) registroDTO.setEstado(registro[2].toString());  	   	 
	   	 DetalleCriterioDTO detalleCriterioDTO = new DetalleCriterioDTO();
	   	 if(registro[0]!=null) detalleCriterioDTO.setIdDetalleCriterio(new Long(registro[0].toString()));
	   	 registroDTO.setDetalleCriterio(detalleCriterioDTO);
	   	 
	   	 return registroDTO;
    } 
    
    public static PghDetalleEvaluacion toDetalleEvaluacionDomain(DetalleEvaluacionDTO registro) {
    	PghDetalleEvaluacion registroDomain = new PghDetalleEvaluacion();
		if(registro!=null){
			if(registro.getDescripcionResultado()!=null) { 
				registroDomain.setDescripcionResultado(registro.getDescripcionResultado()); 
			}
			registroDomain.setEstado(registro.getEstado());
			if(registro.getIdDetalleEvaluacion()!=null) { 
				registroDomain.setIdDetalleEvaluacion(registro.getIdDetalleEvaluacion());
			}
			registroDomain.setFlagRegistrado(registro.getFlagRegistrado());
			registroDomain.setFlagResultado(registro.getFlagResultado());
			PghTipificacion tipificacionDomain = null;
			if(registro.getTipificacion()!=null && registro.getTipificacion().getIdTipificacion()!=null) { 
				tipificacionDomain = new PghTipificacion();
				tipificacionDomain.setIdTipificacion(registro.getTipificacion().getIdTipificacion()); 
			}
			registroDomain.setIdTipificacion(tipificacionDomain);
			PghDetalleCriterio detalleCriterioDomain = null;
			if(registro.getDetalleCriterio()!=null && registro.getDetalleCriterio().getIdDetalleCriterio()!=null) { 
				detalleCriterioDomain = new PghDetalleCriterio();
				detalleCriterioDomain.setIdDetalleCriterio(registro.getDetalleCriterio().getIdDetalleCriterio()); 
			}
			registroDomain.setIdDetalleCriterio(detalleCriterioDomain);
			PghDetalleSupervision detalleSupervisionDomain = null;
			if(registro.getDetalleSupervision()!=null && registro.getDetalleSupervision().getIdDetalleSupervision()!=null) { 
				detalleSupervisionDomain = new PghDetalleSupervision();
				detalleSupervisionDomain.setIdDetalleSupervision(registro.getDetalleSupervision().getIdDetalleSupervision()); 
			}
			registroDomain.setIdDetalleSupervision(detalleSupervisionDomain);
			PghCriterio CriterioDomain = null;
			if(registro.getCriterio()!=null && registro.getCriterio().getIdCriterio()!=null) { 
				CriterioDomain = new PghCriterio();
				CriterioDomain.setIdCriterio(registro.getCriterio().getIdCriterio()); 
			}
			registroDomain.setIdCriterio(CriterioDomain);
		}		
		return registroDomain;
	}
}
