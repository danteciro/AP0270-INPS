/**
* Resumen		
* Objeto			: PeriodoMedidaSeguridadBuilder.java
* Descripción		: Clase DTO PeriodoMedidaSeguridadBuilder
* Fecha de Creación	: 23/10/2016
* PR de Creación	: OSINE_SFS-791
* Autor				: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------				
* 
*/ 
package gob.osinergmin.inpsweb.domain.builder;
import gob.osinergmin.inpsweb.domain.PghExpediente;
import gob.osinergmin.inpsweb.domain.PghPeriodoMedidaSeguridad;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.PeriodoMedidaSeguridadDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zchaupis
 */
public class PeriodoMedidaSeguridadBuilder {
    
    public static PghPeriodoMedidaSeguridad toPeriodoMedidaSeguridadDomain(PeriodoMedidaSeguridadDTO registro) {
        PghPeriodoMedidaSeguridad registroDomain = new PghPeriodoMedidaSeguridad();
        if (registro != null) {
            if (registro.getIdPeriodoMedidaSeguridad()!= null) {
                registroDomain.setIdPeriodoMedidaSeguridad(registro.getIdPeriodoMedidaSeguridad());
            }
            if (registro.getFlagActualizarAuto()!= null) {
                registroDomain.setFlagActualizarAuto(registro.getFlagActualizarAuto());
            }
            if (registro.getFechaInicio()!= null) {
                registroDomain.setFechaInicio(registro.getFechaInicio());
            }
            if (registro.getFechaSubsanado()!= null) {
                registroDomain.setFechaSubsanado(registro.getFechaSubsanado());
            }
            if (registro.getFechaFinPlaneado()!= null) {
                registroDomain.setFechaFinPlaneado(registro.getFechaFinPlaneado());
            }
            if (registro.getFechaCancelacion()!= null) {
                registroDomain.setFechaCancelacion(registro.getFechaCancelacion());
            }
            if (registro.getEstado()!= null) {
                registroDomain.setEstado(registro.getEstado());
            }
            if (registro.getExpedienteDTO()!= null && registro.getExpedienteDTO().getIdExpediente() != null) {
                registroDomain.setIdExpediente(new PghExpediente(registro.getExpedienteDTO().getIdExpediente()));
            }
        }
        return registroDomain;
    }
    
    public static List<PeriodoMedidaSeguridadDTO> toListPeriodoMedidaSeguridadDTO(List<PghPeriodoMedidaSeguridad> lista) {
        PeriodoMedidaSeguridadDTO registroDTO;
        List<PeriodoMedidaSeguridadDTO> retorno = new ArrayList<PeriodoMedidaSeguridadDTO>();
        if (lista != null) {
            for (PghPeriodoMedidaSeguridad maestro : lista) {
                registroDTO = toPeriodoMedidaSeguridadDTO(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }

    public static PeriodoMedidaSeguridadDTO toPeriodoMedidaSeguridadDTO(PghPeriodoMedidaSeguridad registro) {
        PeriodoMedidaSeguridadDTO registroDTO=null;
        if(registro!=null){
        	registroDTO = new PeriodoMedidaSeguridadDTO();
	        if (registro.getIdPeriodoMedidaSeguridad()!= null) {
	            registroDTO.setIdPeriodoMedidaSeguridad(registro.getIdPeriodoMedidaSeguridad());
	        }
	        if (registro.getEstado()!= null) {
	            registroDTO.setEstado(registro.getEstado());
	        }
	        if (registro.getFechaCancelacion()!= null) {
	            registroDTO.setFechaCancelacion(registro.getFechaCancelacion());
	        }
	        if (registro.getFechaFinPlaneado()!= null) {
	            registroDTO.setFechaFinPlaneado(registro.getFechaFinPlaneado());
	        }
	        if (registro.getFechaSubsanado()!= null) {
	            registroDTO.setFechaSubsanado(registro.getFechaSubsanado());
	        }
	        if (registro.getFechaInicio()!= null) {
	            registroDTO.setFechaInicio(registro.getFechaInicio());
	        }
	        if (registro.getFlagActualizarAuto()!= null) {
	            registroDTO.setFlagActualizarAuto(registro.getFlagActualizarAuto());
	        }
	        if (registro.getIdExpediente()!= null) {
	            ExpedienteDTO expedienteDTO = new  ExpedienteDTO();
	            if(registro.getIdExpediente().getIdExpediente() != null){
	                expedienteDTO.setIdExpediente(registro.getIdExpediente().getIdExpediente());
	            }            
	            registroDTO.setExpedienteDTO(expedienteDTO);
	        }
        }
        return registroDTO;
    }
}
