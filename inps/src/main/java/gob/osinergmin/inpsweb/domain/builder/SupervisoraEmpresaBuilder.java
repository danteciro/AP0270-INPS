/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain.builder;
import gob.osinergmin.inpsweb.domain.MdiSupervisoraEmpresa;
import gob.osinergmin.mdicommon.domain.dto.SupervisoraEmpresaDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mdiosesf
 */
public class SupervisoraEmpresaBuilder {
	
      public static List<SupervisoraEmpresaDTO> toListSupervisoraEmpresaDtoBusqueda(List<MdiSupervisoraEmpresa> lista) {
    	  SupervisoraEmpresaDTO supervisoraEmpresaDTO;
        List<SupervisoraEmpresaDTO> listaEmpresa = new ArrayList<SupervisoraEmpresaDTO>();
        if (lista != null) {
            for (MdiSupervisoraEmpresa registro : lista) {	
            	supervisoraEmpresaDTO=new SupervisoraEmpresaDTO();    
                supervisoraEmpresaDTO.setIdSupervisoraEmpresa(registro.getIdSupervisoraEmpresa());		
            	supervisoraEmpresaDTO.setRazonSocial(registro.getRazonSocial() == null ? registro.getNombreConsorcio() : registro.getRazonSocial());
            	supervisoraEmpresaDTO.setRuc(registro.getRuc());		
            	supervisoraEmpresaDTO.setPaginaWeb(registro.getPaginaWeb());
            	supervisoraEmpresaDTO.setTipoEmpresaConstitucion(registro.getTipoEmpresaConstitucion());            	
            	supervisoraEmpresaDTO.setOrgInf(registro.getOrigenInformacion());
            	listaEmpresa.add(supervisoraEmpresaDTO);
            }
        }
        return listaEmpresa;
    }
      
    public static SupervisoraEmpresaDTO toSupervisoraEmpresaDTO(MdiSupervisoraEmpresa registro) {
		SupervisoraEmpresaDTO registroDTO = new SupervisoraEmpresaDTO();
		if(registro!=null) {
			registroDTO.setIdSupervisoraEmpresa(registro.getIdSupervisoraEmpresa());
			registroDTO.setRazonSocial(registro.getRazonSocial());
			registroDTO.setNombreConsorcio(registro.getNombreConsorcio());
		}		
		return registroDTO;
	}
}
