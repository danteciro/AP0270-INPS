package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.mdicommon.domain.dto.EmpresaSupervisadaViewDTO;

import java.util.ArrayList;
import java.util.List;

public class EmpresaSupervisadaViewBuilder {
	
	public List<EmpresaSupervisadaViewDTO> toListObjectEmpresaDTO(List<Object[]> lista){
		List<EmpresaSupervisadaViewDTO> retorno = new ArrayList<EmpresaSupervisadaViewDTO>();
		if(lista!=null){
			for (Object[] registro : lista) {
				EmpresaSupervisadaViewDTO empresavw = new EmpresaSupervisadaViewDTO();
				if(registro[0]!=null){
					empresavw.setEmpresa(registro[0].toString());
				}
				if(registro[1]!=null){
					empresavw.setInforme(registro[1].toString());
				}
				if(registro[2]!=null){
					empresavw.setNroExpediente(registro[2].toString());
				}
				if(registro[3]!=null){
					empresavw.setOficio(registro[3].toString());
				}
				if(registro[4]!=null){
					empresavw.setRuc(registro[4].toString());
				}
				if(registro[5]!=null){
					empresavw.setTipoEmpresa(registro[5].toString());
				}
				if(registro[6]!=null){
					empresavw.setActaInspeccion(registro[6].toString());
				}
				retorno.add(empresavw);
			}
		}
		return retorno;
	}
}
