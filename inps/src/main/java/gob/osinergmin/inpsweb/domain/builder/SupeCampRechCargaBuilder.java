/**
* Resumen
* Objeto			: SupeCampRechCargaBuilder.java
* Descripción		: Objeto que transmite datos del domain al DTO y viceversa.
* PR de Creación	: OSINE_SFS-1063.
* Fecha de Creación	: 23/09/2016.
* Autor				: Hernan Torres Saenz.
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* 
*/

package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.NpsSupeCampRechCarga;
import gob.osinergmin.inpsweb.domain.NpsSupervisionRechCarga;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.Utiles;
import gob.osinergmin.mdicommon.domain.dto.SupeCampRechCargaDTO;

import java.util.ArrayList;
import java.util.List;

public class SupeCampRechCargaBuilder {
	
	public static List<SupeCampRechCargaDTO> toListSupeCampRechCargaDto(List<NpsSupeCampRechCarga> lista){
		SupeCampRechCargaDTO supeCampRechCargaDTO;
		List<SupeCampRechCargaDTO> retorno = new ArrayList<SupeCampRechCargaDTO>();
	    if(lista != null && lista.size()>0){
	    	for (NpsSupeCampRechCarga maestro : lista) {
	    		supeCampRechCargaDTO = toSupeCampRechCargaDto(maestro);
                retorno.add(supeCampRechCargaDTO);
            }
	    }
	    return retorno;
	}
	
	public static SupeCampRechCargaDTO toSupeCampRechCargaDto(NpsSupeCampRechCarga registro) {
		SupeCampRechCargaDTO registroDTO = new SupeCampRechCargaDTO();
        
		registroDTO.setIdSupeCampRechCarga(registro.getIdSupeCampRechCarga());
		registroDTO.setIdSupervisionRechCarga(registro.getIdSupervisionRechCarga().getIdSupervisionRechCarga());
		
		if(registro.getFechaInicio()!=null){
			registroDTO.setFechaInicio(Utiles.DateToString(registro.getFechaInicio(),Constantes.FORMATO_FECHA_CORTA));
			registroDTO.setHoraInicio((String.valueOf(registro.getFechaInicio())).substring(11, 13));
			registroDTO.setMinutoInicio((String.valueOf(registro.getFechaInicio())).substring(14, 16));
		}
		
		if(registro.getFechaFin()!=null){
			registroDTO.setFechaFin(Utiles.DateToString(registro.getFechaFin(),Constantes.FORMATO_FECHA_CORTA));
			registroDTO.setHoraFin((String.valueOf(registro.getFechaFin())).substring(11, 13));
			registroDTO.setMinutoFin((String.valueOf(registro.getFechaFin())).substring(14, 16));
		}
		
		registroDTO.setNombreSupervisorEmpresa(registro.getNombreSupervisorEmpresa());
		registroDTO.setCargoSupervisorEmpresa(registro.getCargoSupervisorEmpresa());
		registroDTO.setNombreSupervisorOsinergmin(registro.getNombreSupervisorOsinergmin());
		registroDTO.setCargoSupervisorOsinergmin(registro.getCargoSupervisorOsinergmin());
		
		registroDTO.setIdUbigeo(registro.getIdUbigeo());
		if(registro.getIdUbigeo()!=null){
			registroDTO.setIdDepartamento(registro.getIdUbigeo().substring(0, 2));
			registroDTO.setIdProvincia(registro.getIdUbigeo().substring(2, 4));
			registroDTO.setIdDistrito(registro.getIdUbigeo().substring(4));
		}
		
				
		registroDTO.setAjusteRele(registro.getAjusteRele());
		registroDTO.setHabilitacionRele(registro.getHabilitacionRele());
		registroDTO.setProtocoloRele(registro.getProtocoloRele());
		registroDTO.setReporteRele(registro.getReporteRele());
		registroDTO.setOtrasObservaciones(registro.getOtrasObservaciones());
		registroDTO.setNotasEmpresa(registro.getNotasEmpresa());
		registroDTO.setNombreActaDoc(registro.getNombreActaDoc());
		registroDTO.setIdActaDoc(registro.getIdActaDoc());
		registroDTO.setIdZona(registro.getIdZona());
		registroDTO.setNombreZona(registro.getNombreZona());
		registroDTO.setFlgCerrado(registro.getFlgCerrado());
		registroDTO.setEstado(registro.getEstado());
		
        return registroDTO;
    }
	
	public static NpsSupeCampRechCarga getNpsSupeCampRechCarga(SupeCampRechCargaDTO registroDTO) {
		NpsSupeCampRechCarga registro = new NpsSupeCampRechCarga();
        
		NpsSupervisionRechCarga supervision = new NpsSupervisionRechCarga();
		supervision.setIdSupervisionRechCarga(registroDTO.getIdSupervisionRechCarga());
		registro.setIdSupervisionRechCarga(supervision);
		registro.setIdSupeCampRechCarga(registroDTO.getIdSupeCampRechCarga());
		if(registroDTO.getFechaInicio()!=null)
			registro.setFechaInicio(Utiles.stringToDate(registroDTO.getFechaInicio(),Constantes.FORMATO_FECHA_LARGE));
		if(registroDTO.getFechaFin()!=null)
			registro.setFechaFin(Utiles.stringToDate(registroDTO.getFechaFin(),Constantes.FORMATO_FECHA_LARGE));
		registro.setNombreSupervisorEmpresa(registroDTO.getNombreSupervisorEmpresa());
		registro.setCargoSupervisorEmpresa(registroDTO.getCargoSupervisorEmpresa());
		registro.setNombreSupervisorOsinergmin(registroDTO.getNombreSupervisorOsinergmin());
		registro.setCargoSupervisorOsinergmin(registroDTO.getCargoSupervisorOsinergmin());
		
		if(registroDTO.getIdDepartamento()!=null && registroDTO.getIdProvincia()!=null && registroDTO.getIdDistrito()!=null)
			registro.setIdUbigeo(registroDTO.getIdDepartamento()+registroDTO.getIdProvincia()+registroDTO.getIdDistrito());
		
		registro.setAjusteRele(registroDTO.getAjusteRele());
		registro.setHabilitacionRele(registroDTO.getHabilitacionRele());
		registro.setProtocoloRele(registroDTO.getProtocoloRele());
		registro.setReporteRele(registroDTO.getReporteRele());
		registro.setOtrasObservaciones(registroDTO.getOtrasObservaciones());
		registro.setNotasEmpresa(registroDTO.getNotasEmpresa());
		registro.setNombreActaDoc(registroDTO.getNombreActaDoc());
		registro.setIdActaDoc(registroDTO.getIdActaDoc());
		registro.setIdZona(registroDTO.getIdZona());
		registro.setNombreZona(registroDTO.getNombreZona());
		registro.setFlgCerrado(registroDTO.getFlgCerrado());
		registro.setEstado(registroDTO.getEstado());
		
        return registro;
    }
	
	public static List<SupeCampRechCargaDTO> toListSupeCampRechCargaActaDTO(List<Object[]> lista){
		List<SupeCampRechCargaDTO> retorno = new ArrayList<SupeCampRechCargaDTO>();
		if(lista!=null){

			for (Object[] registro : lista) {
				SupeCampRechCargaDTO supeCampRechCarga = new SupeCampRechCargaDTO();
				if(registro[0]!=null){
					supeCampRechCarga.setNombreZona(registro[0].toString());
				}  
				if(registro[1]!=null){
					supeCampRechCarga.setIdActaDoc(registro[1].toString());
				}
				
				if(registro[2]!=null){
					supeCampRechCarga.setNombreActaDoc(registro[2].toString());
				}
				 
				retorno.add(supeCampRechCarga);
			}
		}
		return retorno;
	}

	public static SupeCampRechCargaDTO toSupeCampRechCargaDto(SupeCampRechCargaDTO supeCampRechCargaDTO) {
		// TODO Auto-generated method stub
		return null;
	}
}
