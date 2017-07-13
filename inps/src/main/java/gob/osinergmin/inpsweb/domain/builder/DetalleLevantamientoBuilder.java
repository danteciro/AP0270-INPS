/**
* Resumen		
* Objeto			: DetalleLevantamientoBuilder.java
* Descripción		: Clase Builder DetalleLevantamientoBuilder
* Fecha de Creación	: 07/10/2016
* PR de Creación	: OSINE_SFS-598 - RSIS 26
* Autor				: mdiosesf
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------				
* OSINE_SFS-791     07/10/2016      Mario Dioses Fernandez      Crear la funcionalidad "verificar levantamientos" que permita verificar el tipo de asignación para la orden de levantamiento DSR-CRITICIDAD.
* OSINE_SFS-791_RSIS_49 |  24/10/2016   |   Luis García Reyna          |     Registrar idDetaLevaAtiende en DetalleLevantamiento
*/ 
package gob.osinergmin.inpsweb.domain.builder;
import gob.osinergmin.inpsweb.domain.PghDetalleLevantamiento;
import gob.osinergmin.inpsweb.domain.PghDetalleSupervision;
import gob.osinergmin.mdicommon.domain.dto.DetalleLevantamientoDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DetalleLevantamientoBuilder {
	private static final Logger LOG = LoggerFactory.getLogger(DetalleLevantamientoBuilder.class);
	public static List<DetalleLevantamientoDTO> toListDetalleLevantamientoDto(List<PghDetalleLevantamiento> lista) {
		DetalleLevantamientoDTO registroDTO;
        List<DetalleLevantamientoDTO> retorno = new ArrayList<DetalleLevantamientoDTO>();
        if (lista != null) {
        	for(PghDetalleLevantamiento registro:lista){
                registroDTO = tofindDetalleLevantamientoDto(registro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }
	
	public static DetalleLevantamientoDTO tofindDetalleLevantamientoDto(PghDetalleLevantamiento registro){
		DetalleLevantamientoDTO detalleLevantamientoDTO = new DetalleLevantamientoDTO();
		if(registro!=null){
			detalleLevantamientoDTO.setIdDetalleLevantamiento(registro.getIdDetalleLevantamiento());
			detalleLevantamientoDTO.setDescripcion(registro.getDescripcion());
			detalleLevantamientoDTO.setFechaIngresoLevantamiento(registro.getFechaIngresoLevantamiento());
			detalleLevantamientoDTO.setEstado(registro.getEstado());
                        if(registro.getIdDetaLevaAtiende()!=null && registro.getIdDetaLevaAtiende().getIdDetalleSupervision()!=null){
                                detalleLevantamientoDTO.setIdDetaLevaAtiende(registro.getIdDetaLevaAtiende().getIdDetalleSupervision());                        
                        }
			if(registro.getIdDetalleSupervision()!=null && registro.getIdDetalleSupervision().getIdDetalleSupervision()!=null){
				detalleLevantamientoDTO.setIdDetalleSupervision(new DetalleSupervisionDTO(registro.getIdDetalleSupervision().getIdDetalleSupervision()));
			}
			detalleLevantamientoDTO.setFlagUltimoRegistro(registro.getFlagUltimoRegistro());
		}
		return detalleLevantamientoDTO;
	}
	
	public static PghDetalleLevantamiento toDetalleLevantamientoDomain(DetalleLevantamientoDTO registro) {
		PghDetalleLevantamiento registroDomain = new PghDetalleLevantamiento();
		if(registro!=null){
			registroDomain.setIdDetalleLevantamiento(registro.getIdDetalleLevantamiento());
			registroDomain.setDescripcion(registro.getDescripcion());
			registroDomain.setFechaIngresoLevantamiento(registro.getFechaIngresoLevantamiento());
			registroDomain.setEstado(registro.getEstado());                        
//                        if(registro.getIdDetaLevaAtiende()!=null){
//				registroDomain.setIdDetaLevaAtiende(registroDomain.getIdDetaLevaAtiende());
//			}
                        if(registro.getIdDetalleSupervision()!=null && registro.getIdDetalleSupervision().getIdDetalleSupervision()!=null){
				registroDomain.setIdDetalleSupervision(new PghDetalleSupervision(registro.getIdDetalleSupervision().getIdDetalleSupervision()));
			}
			registroDomain.setFlagUltimoRegistro(registro.getFlagUltimoRegistro());
                        /*OSINE_SFS-791 - RSIS 49 - Inicio */
                        if(registro.getIdDetaLevaAtiende()!=null){
                            registroDomain.setIdDetaLevaAtiende(new PghDetalleSupervision(registro.getIdDetaLevaAtiende()));
                        }   
                        /*OSINE_SFS-791 - RSIS 49 - Fin */
                        
		}		
		return registroDomain;
	}
}
