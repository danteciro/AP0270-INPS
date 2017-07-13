package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.mdicommon.domain.dto.EmpresaViewDTO;

import java.util.ArrayList;
import java.util.List;

public class EmpresaViewBuilder {
	
	public static List<EmpresaViewDTO> toListObjectEmpresaDTO(List<Object[]> lista){
		List<EmpresaViewDTO> retorno = new ArrayList<EmpresaViewDTO>();
		
		if(lista!=null){
			for (Object[] registro : lista) {
				EmpresaViewDTO empresavw = new EmpresaViewDTO();
			   
				if(registro[0]!=null){
					empresavw.setIdSupervisionRechCarga(registro[0].toString());
				}
			
				if(registro[1]!=null){
					empresavw.setIdInformeSupeRechCarga(Long.parseLong(registro[1].toString()));
				}
				
				if(registro[2]!=null){
					empresavw.setTipoEmpresa(registro[2].toString());
				}
				if(registro[3]!=null){
					empresavw.setRuc(registro[3].toString());
				}
				if(registro[4]!=null){
					empresavw.setEmpresa(registro[4].toString());
				}
				
				if(registro[5]!=null){
					empresavw.setNumeroExpediente(registro[5].toString());
				}
				if(registro[6]!=null){
					empresavw.setIdEmpresa(registro[6].toString());
				}
				if(registro[7]!=null){
					empresavw.setIdTipo(registro[7].toString());
				}
				
				if(registro[8]!=null){
					empresavw.setIdInformeDoc(registro[8].toString());
				}
				if(registro[9]!=null){
					empresavw.setNombreInformeDoc(registro[9].toString());
				}
				if(registro[10]!=null){
					empresavw.setIdOficioDoc(Long.parseLong(registro[10].toString()));
				}
				if(registro[11]!=null){
					empresavw.setNombreOficioDoc(registro[11].toString());
				}
				if(registro[12]!=null){
					empresavw.setAnio(registro[12].toString());
				}
				retorno.add(empresavw);
			}
		}
		return retorno;
	}
}
