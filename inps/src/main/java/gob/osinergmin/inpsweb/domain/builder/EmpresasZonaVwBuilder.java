/**
* Resumen
* Objeto			: EmpresasZonaVwBuilder.java
* Descripción		: Constructor de EmpresasZona.
* Fecha de Creación	: 15/09/2016.
* PR de Creación	: OSINE_SFS-1063.
* Autor				: Hernan Torres Saenz.
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
*
*/

package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.mdicommon.domain.dto.EmpresasZonaVwDTO;

import java.util.ArrayList;
import java.util.List;

public class EmpresasZonaVwBuilder {

    public static List<EmpresasZonaVwDTO> toListaEmpresasZonaVwDTO(List<Object[]> empresasZonaVw) {
    	List<EmpresasZonaVwDTO> listaEmpresasZonVwDTO = new ArrayList<EmpresasZonaVwDTO>();
        if(empresasZonaVw != null){
        	for(Object[] registro : empresasZonaVw){
        		EmpresasZonaVwDTO empresasZonVwDTO=new EmpresasZonaVwDTO();
        		
        		if(registro[0]!=null){
        			empresasZonVwDTO.setIdSupervisionRechazoCarga(new Long(registro[0].toString()));
        	    }
        		
        		if(registro[1]!=null){
        	    	empresasZonVwDTO.setIdSupeCampRechCarga(new Long(registro[1].toString()));
        	    } 
        		
        	    if(registro[2]!=null){
        	    	empresasZonVwDTO.setIdTipo(registro[2].toString());
        	    } 
        	    if(registro[3]!=null){
        	    	empresasZonVwDTO.setTipo(registro[3].toString());
        	    } 
        	    if(registro[4]!=null){
        	    	empresasZonVwDTO.setRuc(registro[4].toString());
        	    } 
        	    
        	    if(registro[4]!=null){
        	    	empresasZonVwDTO.setRucHidden(registro[4].toString());
        	    } 
        	    
        	    if(registro[5]!=null){
        	    	empresasZonVwDTO.setIdEmpresa(registro[5].toString());
        	    } 
        	    
        	    if(registro[6]!=null){
        	    	empresasZonVwDTO.setDescripcionEmpresa(registro[6].toString());
        	    } 
        	    
        	    if(registro[6]!=null){
        	    	empresasZonVwDTO.setDescripcionEmpresaHidden(registro[6].toString());
        	    }
        	    
        	    if(registro[7]!=null){
        	    	empresasZonVwDTO.setNumeroExpediente(registro[7].toString());
        	    } 
        	    
        	    if(registro[7]!=null){
        	    	empresasZonVwDTO.setNumeroExpedienteHidden(registro[7].toString());
        	    } 
        	    
        	    if(registro[8]!=null){
        	    	empresasZonVwDTO.setIdZona(registro[8].toString());
        	    } 
        	    
        	    if(registro[9]!=null){
        	    	empresasZonVwDTO.setDescripcionZona(registro[9].toString());
        	    } 
        	    
        	    if(registro[10]!=null){
					//Format formatter = new SimpleDateFormat("yyyy");
        	    	//empresasZonVwDTO.setAnio(formatter.format((java.util.Date)(registro[9])));
        	    	empresasZonVwDTO.setAnio(registro[10].toString());
        	    }
        	    
        	    if(registro[11]!=null){
        	    	empresasZonVwDTO.setIdOficio(registro[11].toString());
        	    }
        	    
        	    if(registro[12]!=null){
        	    	empresasZonVwDTO.setNombreOficio(registro[12].toString());
        	    }
        	    
        	    if(registro[13]!=null){
        	    	empresasZonVwDTO.setIdActa(registro[13].toString());
        	    }
        	    
        	    if(registro[14]!=null){
        	    	empresasZonVwDTO.setNombreActa(registro[14].toString());
        	    }
        	    
        	    if(registro[15]!=null){
        	    	empresasZonVwDTO.setFlagCerrado(registro[15].toString());
        	    }
        	    
        	    listaEmpresasZonVwDTO.add(empresasZonVwDTO);
        	}
        
        }
        return listaEmpresasZonVwDTO;
    }
	 
	 
 
}
