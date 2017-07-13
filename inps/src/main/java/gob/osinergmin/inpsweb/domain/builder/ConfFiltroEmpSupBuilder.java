package gob.osinergmin.inpsweb.domain.builder;
import gob.osinergmin.inpsweb.domain.MdiMaestroColumna;
import gob.osinergmin.inpsweb.domain.PghConfFiltroEmpSup;
import gob.osinergmin.mdicommon.domain.dto.ConfFiltroEmpSupDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import java.util.ArrayList;
import java.util.List;
/**
*
* @author mdiosesf
*/
public class ConfFiltroEmpSupBuilder {

	public static List<ConfFiltroEmpSupDTO> toListConfFiltroEmpSupDto(List<PghConfFiltroEmpSup> lista) {
		List<ConfFiltroEmpSupDTO> retorno = new ArrayList<ConfFiltroEmpSupDTO>();
		if (lista != null) {
			ConfFiltroEmpSupDTO registroDTO;
            for (PghConfFiltroEmpSup registro : lista) {
            	registroDTO = new ConfFiltroEmpSupDTO();
            	if(registro.getIdFiltro()!=null){
            		MaestroColumnaDTO filtro=new MaestroColumnaDTO();
            		filtro.setIdMaestroColumna(registro.getIdFiltro().getIdMaestroColumna());
            		filtro.setDescripcion(registro.getIdFiltro().getDescripcion());
            		filtro.setCodigo(registro.getIdFiltro().getCodigo());
            		filtro.setDominio(registro.getIdFiltro().getDescripcion());
            		registroDTO.setIdFiltro(filtro);
            	}
            	registroDTO.setIdFiltroEmpSup(registro.getIdFiltroEmpSup());
            	registroDTO.setIdUnidadOrganica(registro.getIdUnidadOrganica());  
            	registroDTO.setEstado(registro.getEstado());
                retorno.add(registroDTO);
            }
		}
		return retorno;	
	}
	
	public static ConfFiltroEmpSupDTO toConfFiltroEmpSupDto(PghConfFiltroEmpSup registro) {
		ConfFiltroEmpSupDTO retorno = new ConfFiltroEmpSupDTO();
		if (registro != null) {            
			retorno = new ConfFiltroEmpSupDTO();
			if(registro.getIdFiltro()!=null){
        		MaestroColumnaDTO filtro=new MaestroColumnaDTO();
        		filtro.setIdMaestroColumna(registro.getIdFiltro().getIdMaestroColumna());
        		filtro.setDescripcion(registro.getIdFiltro().getDescripcion());
        		retorno.setIdFiltro(filtro);
        	}			
			retorno.setIdFiltroEmpSup(registro.getIdFiltroEmpSup());
			retorno.setIdUnidadOrganica(registro.getIdUnidadOrganica());    
			retorno.setEstado(registro.getEstado());
		}
		return retorno;	
	}
	
	public static PghConfFiltroEmpSup toConfFiltroEmpSupTabla(ConfFiltroEmpSupDTO registro) {
		PghConfFiltroEmpSup retorno = new PghConfFiltroEmpSup();
		if(registro!=null){ 
			if(registro.getIdFiltro()!=null){
        		MdiMaestroColumna filtro=new MdiMaestroColumna();
        		filtro.setIdMaestroColumna(registro.getIdFiltro().getIdMaestroColumna());
        		filtro.setDescripcion(registro.getIdFiltro().getDescripcion());
        		retorno.setIdFiltro(filtro);
        	}
			retorno.setIdFiltroEmpSup(registro.getIdFiltroEmpSup());
			retorno.setIdUnidadOrganica(registro.getIdUnidadOrganica());
			retorno.setEstado(registro.getEstado());
		}  
		return retorno;	
	}
}
