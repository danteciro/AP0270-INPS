/**
* Resumen
* Objeto			: DmTitularMineroBuilder.java
* Descripción		: Objeto que transmite datos del domain al DTO y viceversa.
* PR de Creación	: OSINE_SFS-1344.
* Fecha de Creación	: 26/10/2016.
* Autor				: Hernan Torres Saenz.
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* 
*/

package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.DmTitularMinero;
import gob.osinergmin.inpsweb.domain.DmUnidadSupervisada;
import gob.osinergmin.inpsweb.domain.MdiActividad;
import gob.osinergmin.inpsweb.domain.MdiMaestroColumna;
import gob.osinergmin.inpsweb.domain.NpsSupeCampRechCarga;
import gob.osinergmin.inpsweb.domain.NpsSupervisionRechCarga;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.Utiles;
import gob.osinergmin.mdicommon.domain.dto.ActividadDTO;
import gob.osinergmin.mdicommon.domain.dto.DmTitularMineroDTO;
import gob.osinergmin.mdicommon.domain.dto.DmUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.SupeCampRechCargaDTO;

import java.util.ArrayList;
import java.util.List;

public class DmTitularMineroBuilder {
	
	public static List<DmTitularMineroDTO> toListDmTitularMineroDto(List<DmTitularMinero> lista){
		DmTitularMineroDTO dmTitularMineroDTO;
		List<DmTitularMineroDTO> retorno = new ArrayList<DmTitularMineroDTO>();
	    if(lista != null && lista.size()>0){
	    	for (DmTitularMinero maestro : lista) {
	    		dmTitularMineroDTO = toDmTitularMineroDto(maestro);
                retorno.add(dmTitularMineroDTO);
            }
	    }
	    return retorno;
	}
	
	public static DmTitularMineroDTO toDmTitularMineroDto(DmTitularMinero registro) {
		DmTitularMineroDTO registroDTO = new DmTitularMineroDTO();
       
		registroDTO.setIdTitularMinero(registro.getIdTitularMinero());
		registroDTO.setCodigoTitularMinero(registro.getCodigoTitularMinero());
		registroDTO.setNombreTitularMinero(registro.getNombreTitularMinero());
		registroDTO.setEstado(registro.getEstado());
		
		if(registro.getDmUnidadSupervisadaList()!=null && registro.getDmUnidadSupervisadaList().size()>0){
			
			List<DmUnidadSupervisadaDTO> listaUnidadDto = new ArrayList<DmUnidadSupervisadaDTO>();
			
			for(DmUnidadSupervisada unidad : registro.getDmUnidadSupervisadaList()){
				DmUnidadSupervisadaDTO unidadDto = new DmUnidadSupervisadaDTO();
				unidadDto.setIdDmUnidadSupervisada(unidad.getIdDmUnidadSupervisada());
				unidadDto.setCodigoUnidadSupervisada(unidad.getCodigoUnidadSupervisada());
				unidadDto.setNombreUnidadSupervisada(unidad.getNombreUnidadSupervisada());
				unidadDto.setEstado(unidad.getEstado());
				unidadDto.setDireccion(unidad.getDireccion());
				
				DmTitularMineroDTO titular = new DmTitularMineroDTO();
				titular.setIdTitularMinero(registro.getIdTitularMinero());
				titular.setCodigoTitularMinero(registro.getCodigoTitularMinero());
				titular.setNombreTitularMinero(registro.getNombreTitularMinero());
				unidadDto.setIdTitularMinero(titular);
				
				if(unidad.getIdTipoMinado()!=null && unidad.getIdTipoMinado().getIdMaestroColumna()!=null){
					MaestroColumnaDTO maestroMinado = new MaestroColumnaDTO();
					maestroMinado.setIdMaestroColumna(unidad.getIdTipoMinado().getIdMaestroColumna());
					maestroMinado.setDescripcion(unidad.getIdTipoMinado().getDescripcion());
					maestroMinado.setDominio(unidad.getIdTipoMinado().getDominio());
					maestroMinado.setCodigo(unidad.getIdTipoMinado().getCodigo());
					unidadDto.setIdTipoMinado(maestroMinado);
				}
				
				if(unidad.getIdEstrato()!=null && unidad.getIdEstrato().getIdMaestroColumna()!=null){
					MaestroColumnaDTO maestroEstrato = new MaestroColumnaDTO();
					maestroEstrato.setIdMaestroColumna(unidad.getIdEstrato().getIdMaestroColumna());
					maestroEstrato.setDescripcion(unidad.getIdEstrato().getDescripcion());
					maestroEstrato.setDominio(unidad.getIdEstrato().getDominio());
					maestroEstrato.setCodigo(unidad.getIdEstrato().getCodigo());
					unidadDto.setIdEstrato(maestroEstrato);
				}
				
				if(unidad.getIdActividad()!=null && unidad.getIdActividad().getIdActividad()!=null){
					ActividadDTO actividad = new ActividadDTO();
					actividad.setIdActividad(unidad.getIdActividad().getIdActividad());
					actividad.setNombre(unidad.getIdActividad().getNombre());
					unidadDto.setIdActividad(actividad);
				}
				
				listaUnidadDto.add(unidadDto);
			}
			registroDTO.setListaUnidadSupervisada(listaUnidadDto);
		}
		
        return registroDTO;
    }
	
	public static DmTitularMinero getDmTitularMinero(DmTitularMineroDTO registroDTO) {
		DmTitularMinero registro = new DmTitularMinero();
       
		registro.setIdTitularMinero(registroDTO.getIdTitularMinero());
		registro.setCodigoTitularMinero(registroDTO.getCodigoTitularMinero());
		registro.setNombreTitularMinero(registroDTO.getNombreTitularMinero());
		registro.setEstado(registroDTO.getEstado());
		
		if(registroDTO.getListaUnidadSupervisada()!=null && registroDTO.getListaUnidadSupervisada().size()>0){
			
			List<DmUnidadSupervisada> listaUnidad = new ArrayList<DmUnidadSupervisada>();
			
			for(DmUnidadSupervisadaDTO unidadDTO : registroDTO.getListaUnidadSupervisada()){
				DmUnidadSupervisada unidad = new DmUnidadSupervisada();
				unidad.setIdDmUnidadSupervisada(unidadDTO.getIdDmUnidadSupervisada());
				unidad.setCodigoUnidadSupervisada(unidadDTO.getCodigoUnidadSupervisada());
				unidad.setNombreUnidadSupervisada(unidadDTO.getNombreUnidadSupervisada());
				unidad.setEstado(unidadDTO.getEstado());
				unidad.setDireccion(unidadDTO.getDireccion());
				
				DmTitularMinero titular = new DmTitularMinero();
				titular.setIdTitularMinero(registro.getIdTitularMinero());
				titular.setCodigoTitularMinero(registro.getCodigoTitularMinero());
				titular.setNombreTitularMinero(registro.getNombreTitularMinero());
				unidad.setIdTitularMinero(titular);
				
				if(unidadDTO.getIdTipoMinado()!=null && unidadDTO.getIdTipoMinado().getIdMaestroColumna()!=null){
					MdiMaestroColumna maestroMinado = new MdiMaestroColumna();
					maestroMinado.setIdMaestroColumna(unidadDTO.getIdTipoMinado().getIdMaestroColumna());
					maestroMinado.setDescripcion(unidadDTO.getIdTipoMinado().getDescripcion());
					maestroMinado.setDominio(unidadDTO.getIdTipoMinado().getDominio());
					maestroMinado.setCodigo(unidadDTO.getIdTipoMinado().getCodigo());
					unidad.setIdTipoMinado(maestroMinado);
				}
				if(unidadDTO.getIdEstrato()!=null && unidadDTO.getIdEstrato().getIdMaestroColumna()!=null){
					MdiMaestroColumna maestroEstrato = new MdiMaestroColumna();
					maestroEstrato.setIdMaestroColumna(unidadDTO.getIdEstrato().getIdMaestroColumna());
					maestroEstrato.setDescripcion(unidadDTO.getIdEstrato().getDescripcion());
					maestroEstrato.setDominio(unidadDTO.getIdEstrato().getDominio());
					maestroEstrato.setCodigo(unidadDTO.getIdEstrato().getCodigo());
					unidad.setIdTipoMinado(maestroEstrato);
				}
				if(unidadDTO.getIdActividad()!=null && unidadDTO.getIdActividad().getIdActividad()!=null){
					MdiActividad actividad = new MdiActividad();
					actividad.setIdActividad(unidadDTO.getIdActividad().getIdActividad());
					actividad.setNombre(unidadDTO.getIdActividad().getNombre());
					unidad.setIdActividad(actividad);
				}
				listaUnidad.add(unidad);
			}
			registro.setDmUnidadSupervisadaList(listaUnidad);
		}
		
        return registro;
    }
	
	
}
