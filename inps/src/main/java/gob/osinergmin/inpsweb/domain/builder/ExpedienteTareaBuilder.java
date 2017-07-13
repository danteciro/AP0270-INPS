/**
* Resumen		
* Objeto			: PghOrgaActiModuSecc.java
* Descripción		: Clase DTO PghOrgaActiModuSecc
* Fecha de Creación	: 23/10/2016
* PR de Creación	: OSINE_SFS-791
* Autor				: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------				
* OSINE_SFS-791     23/10/2016      Mario Dioses Fernandez          Crear la tarea automática que notifique vía correo que se debe elaborar el oficio por obligaciones incumplidas sin subsanar
* OSINE_SFS-791     23/10/2016      Mario Dioses Fernandez          Crear la tarea automática que cancele el registro de hidrocarburos
* 
*/ 

package gob.osinergmin.inpsweb.domain.builder;
import gob.osinergmin.inpsweb.domain.PghExpedienteTarea;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteTareaDTO;
import java.util.ArrayList;
import java.util.List;

public class ExpedienteTareaBuilder {
	public static List<ExpedienteTareaDTO> toListExpedienteTareaDto(List<PghExpedienteTarea> lista) {
		ExpedienteTareaDTO registroDTO;
        List<ExpedienteTareaDTO> retorno = new ArrayList<ExpedienteTareaDTO>();
        if (lista != null) {
        	for(PghExpedienteTarea registro:lista){
                registroDTO = tofindExpedienteTareaDto(registro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }
	
	public static ExpedienteTareaDTO tofindExpedienteTareaDto(PghExpedienteTarea registro){
		ExpedienteTareaDTO expedienteTareaDTO = null;
		if(registro!=null){
			expedienteTareaDTO = new ExpedienteTareaDTO();
			expedienteTareaDTO.setIdExpediente(registro.getIdExpediente());
			expedienteTareaDTO.setFlagCorreoOficio(registro.getFlagCorreoOficio());
			expedienteTareaDTO.setFlagEstadoReghMsfh(registro.getFlagEstadoReghMsfh());
			expedienteTareaDTO.setFlagEstadoReghInps(registro.getFlagEstadoReghInps());
			expedienteTareaDTO.setFlagEnviarConstanciaSiged(registro.getFlagEnviarConstanciaSiged());
			expedienteTareaDTO.setFlagEstadoSiged(registro.getFlagEstadoSiged());
			expedienteTareaDTO.setFlagCorreoEstadoRegh(registro.getFlagCorreoEstadoRegh());
			expedienteTareaDTO.setFlagRegistraDocsInps(registro.getFlagRegistraDocsInps());
			expedienteTareaDTO.setFlagCorreoScop(registro.getFlagCorreoScop());
		}
		return expedienteTareaDTO;
	}
	
	public static PghExpedienteTarea toExpedienteTareaDomain(ExpedienteTareaDTO registro) {
		PghExpedienteTarea registroDomain = null;
		if(registro!=null){
			registroDomain = new PghExpedienteTarea();
			registroDomain.setIdExpediente(registro.getIdExpediente());
			registroDomain.setFlagCorreoOficio(registro.getFlagCorreoOficio());
			registroDomain.setFlagEstadoReghMsfh(registro.getFlagEstadoReghMsfh());
			registroDomain.setFlagEstadoReghInps(registro.getFlagEstadoReghInps());
			registroDomain.setFlagEnviarConstanciaSiged(registro.getFlagEnviarConstanciaSiged());
			registroDomain.setFlagEstadoSiged(registro.getFlagEstadoSiged());
			registroDomain.setFlagCorreoEstadoRegh(registro.getFlagCorreoEstadoRegh());
			registroDomain.setFlagRegistraDocsInps(registro.getFlagRegistraDocsInps());
			registroDomain.setFlagCorreoScop(registro.getFlagCorreoScop());			
		}		
		return registroDomain;
	}
}
