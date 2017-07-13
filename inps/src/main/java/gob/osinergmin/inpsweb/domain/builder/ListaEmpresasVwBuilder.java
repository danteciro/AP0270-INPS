package gob.osinergmin.inpsweb.domain.builder;

import java.util.ArrayList;
import java.util.List;

import gob.osinergmin.mdicommon.domain.dto.EmpresaViewDTO;

public class ListaEmpresasVwBuilder {

 
    public static List<EmpresaViewDTO>  toListaEmpresasVwDTO(List<Object[]> empresaVw) {
    	List<EmpresaViewDTO> retorno=new ArrayList<EmpresaViewDTO>();
    	if(empresaVw!= null){
        	EmpresaViewDTO listaEmpresasVwDTO=new EmpresaViewDTO();   
        	   for (Object[] maestro : empresaVw) {
        		   	listaEmpresasVwDTO=new EmpresaViewDTO();
               		
        		   	listaEmpresasVwDTO.setIdEmpresa(maestro[0].toString());
               		listaEmpresasVwDTO.setEmpresa(maestro[1].toString());

                	 if(maestro[2]!=null){
                		listaEmpresasVwDTO.setRuc(maestro[2].toString());
                	 }
                	 	listaEmpresasVwDTO.setIdTipo(maestro[3].toString());
                    	listaEmpresasVwDTO.setTipoEmpresa(maestro[4].toString());
                	 if(maestro[5]!=null){
                		 listaEmpresasVwDTO.setAnio(maestro[5].toString());
                	 }
                	 if(maestro[6]!=null){
                		 listaEmpresasVwDTO.setNumeroExpediente(maestro[6].toString());
                	 }
                	 if(maestro[7]!=null){
                		 listaEmpresasVwDTO.setNombreOficioDoc(maestro[7].toString());
                	 }
                	 if(maestro[8]!=null){
                    	 listaEmpresasVwDTO.setIdOficioDoc(Long.parseLong(maestro[8].toString()));
                     }
                	 
                	 if(maestro[10]!=null){
                    	 listaEmpresasVwDTO.setFlagObservado(maestro[10].toString());
                     }
                	 if(maestro[11]!=null){
                    	 listaEmpresasVwDTO.setIdInformeSupeRechCarga(Long.parseLong(maestro[11].toString()));
                     }
                	 
                	 if(maestro[12]!=null){
                    	 listaEmpresasVwDTO.setNombreInformeDoc(maestro[12].toString());
                     }
                	 
                	 if(maestro[13]!=null){
                    	 listaEmpresasVwDTO.setIdInformeDoc(maestro[13].toString());
                     }
                	 retorno.add(listaEmpresasVwDTO);
        	   }
    	}
        return retorno;
    }
	 
	 
 
}
