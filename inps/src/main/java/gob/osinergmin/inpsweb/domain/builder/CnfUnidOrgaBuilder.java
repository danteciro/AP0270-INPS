/**
* Resumen
* Objeto			: CnfUnidOrgaBuilder.java
* Descripción		: Objeto que transmite datos del domain al DTO y viceversa.
* PR de Creación	: OSINE_SFS-1344.
* Fecha de Creación	: 25/10/2016.
* Autor				: Hernán Torres.
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* 
*/

package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.MdiUnidadOrganica;
import gob.osinergmin.inpsweb.domain.NpsCnfUnidOrga;
import gob.osinergmin.inpsweb.domain.NpsSupeCampRechCarga;
import gob.osinergmin.inpsweb.domain.NpsSupervisionRechCarga;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.Utiles;
import gob.osinergmin.mdicommon.domain.dto.CnfUnidOrgaDTO;
import gob.osinergmin.mdicommon.domain.dto.SupeCampRechCargaDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadOrganicaDTO;

import java.util.ArrayList;
import java.util.List;

public class CnfUnidOrgaBuilder {
	
	public static List<CnfUnidOrgaDTO> toListCnfUnidOrgaDto(List<NpsCnfUnidOrga> lista){
		CnfUnidOrgaDTO cnfUnidOrgaDTO;
		List<CnfUnidOrgaDTO> retorno = new ArrayList<CnfUnidOrgaDTO>();
	    if(lista != null && lista.size()>0){
	    	for (NpsCnfUnidOrga maestro : lista) {
	    		cnfUnidOrgaDTO = toCnfUnidOrgaDto(maestro);
                retorno.add(cnfUnidOrgaDTO);
            }
	    }
	    return retorno;
	}
	
	public static CnfUnidOrgaDTO toCnfUnidOrgaDto(NpsCnfUnidOrga registro) {
		CnfUnidOrgaDTO registroDTO = new CnfUnidOrgaDTO();
        
		registroDTO.setIdCnfUnidOrga(registro.getIdCnfUnidOrga());
		registroDTO.setBandejaInps(registro.getBandejaInps());
		registroDTO.setEstado(registro.getEstado());
		
		if(registro.getIdUnidadOrganica()!=null && registro.getIdUnidadOrganica().getIdUnidadOrganica()!=null){
			UnidadOrganicaDTO unidad = new UnidadOrganicaDTO();
			unidad.setIdUnidadOrganica(registro.getIdUnidadOrganica().getIdUnidadOrganica());
			registroDTO.setIdUnidadOrganica(unidad);
		}
		
        return registroDTO;
    }
	
	public static NpsCnfUnidOrga getNpsConfUnidOrga(CnfUnidOrgaDTO registroDTO) {
		NpsCnfUnidOrga registro = new NpsCnfUnidOrga();
        
		registro.setIdCnfUnidOrga(registroDTO.getIdCnfUnidOrga());
		registro.setBandejaInps(registroDTO.getBandejaInps());
		registro.setEstado(registroDTO.getEstado());
		
		if(registroDTO.getIdUnidadOrganica()!=null && registroDTO.getIdUnidadOrganica().getIdUnidadOrganica()!=null){
			MdiUnidadOrganica unidad = new MdiUnidadOrganica();
			unidad.setIdUnidadOrganica(registroDTO.getIdUnidadOrganica().getIdUnidadOrganica());
			registro.setIdUnidadOrganica(unidad);
		}
		
        return registro;
    }
}
