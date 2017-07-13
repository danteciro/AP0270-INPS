package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.MdiUbigeo;
import gob.osinergmin.mdicommon.domain.dto.DepartamentoDTO;
import gob.osinergmin.mdicommon.domain.dto.DistritoDTO;
import gob.osinergmin.mdicommon.domain.dto.ProvinciaDTO;
import gob.osinergmin.mdicommon.domain.dto.UbigeoDTO;
import java.util.ArrayList;
import java.util.List;

public class UbigeoWSBuilder {

    public static DepartamentoDTO getDepartamentoDTO(DepartamentoDTO departamento) {
	DepartamentoDTO departamentoViewDTO = new DepartamentoDTO();
	departamentoViewDTO.setIdDepartamento(departamento.getIdDepartamento());
	departamentoViewDTO.setNombre(departamento.getNombre());
	return departamentoViewDTO;

    }

    public static List<DepartamentoDTO> listaDepartamentoViewDTO(List<DepartamentoDTO> listaDepartamentoDTO) {
	List<DepartamentoDTO> listaDepartamentoViewDTO = new ArrayList<DepartamentoDTO>();
	for (DepartamentoDTO departamentoDTO : listaDepartamentoDTO) {
	    DepartamentoDTO departamentoViewDTO = new DepartamentoDTO();
	    departamentoViewDTO.setIdDepartamento(departamentoDTO.getIdDepartamento());
	    departamentoViewDTO.setNombre(departamentoDTO.getNombre());
	    listaDepartamentoViewDTO.add(departamentoViewDTO);
	}
	return listaDepartamentoViewDTO;
    }

    public static ProvinciaDTO getProvinciaDTO(ProvinciaDTO provincia) {
	ProvinciaDTO provinciaViewDTO = new ProvinciaDTO();
	provinciaViewDTO.setIdDepartamento(provincia.getIdDepartamento());
	provinciaViewDTO.setIdProvincia(provincia.getIdProvincia());
	provinciaViewDTO.setNombre(provincia.getNombre());
	return provinciaViewDTO;

    }

    public static List<ProvinciaDTO> listaProvinciaViewDTO(List<ProvinciaDTO> listaProvinciaDTO) {
	List<ProvinciaDTO> listaProvinciaViewDTO = new ArrayList<ProvinciaDTO>();
	for (ProvinciaDTO provinciaDTO : listaProvinciaDTO) {
	    ProvinciaDTO provinciaViewDTO = new ProvinciaDTO();
	    provinciaViewDTO.setIdDepartamento(provinciaDTO.getIdDepartamento());
	    provinciaViewDTO.setIdProvincia(provinciaDTO.getIdProvincia());
	    provinciaViewDTO.setNombre(provinciaDTO.getNombre());
	    listaProvinciaViewDTO.add(provinciaViewDTO);
	}
	return listaProvinciaViewDTO;

    }

    public static DistritoDTO getDistritoViewDTO(DistritoDTO distrito) {
	DistritoDTO distritoViewDTO = new DistritoDTO();
	distritoViewDTO.setIdDepartamento(distrito.getIdDepartamento());
	distritoViewDTO.setIdDistrito(distrito.getIdDistrito());
	distritoViewDTO.setIdProvincia(distrito.getIdProvincia());
	distritoViewDTO.setNombre(distrito.getNombre());
	return distritoViewDTO;
    }

    public static List<DistritoDTO> listaDistritoViewDTO(List<DistritoDTO> listaDistrito) {
	List<DistritoDTO> listaDistritoViewDTO = new ArrayList<DistritoDTO>();
	for (DistritoDTO distritoDTO : listaDistrito) {
	    DistritoDTO distritoViewDTO = new DistritoDTO();
	    distritoViewDTO.setIdDepartamento(distritoDTO.getIdDepartamento());
	    distritoViewDTO.setIdDistrito(distritoDTO.getIdDistrito());
	    distritoViewDTO.setIdProvincia(distritoDTO.getIdProvincia());
	    distritoViewDTO.setNombre(distritoDTO.getNombre());
	    listaDistritoViewDTO.add(distritoViewDTO);
	}
	return listaDistritoViewDTO;
    }
    
    /**
	 * convierte de UbigeoDTO a UbigeoViewDTO
	 * @param ubigeoDto  UbigeoDTO a convertir
	 * @return UbigeoViewDTO convertido
	 */
	public static UbigeoDTO getUbigeoBO(UbigeoDTO ubigeoDto){
		UbigeoDTO ubigeoView = null;
		if(ubigeoDto!=null){
			ubigeoView = new UbigeoDTO();
			ubigeoView.setNombre(ubigeoDto.getNombre());
			ubigeoView.setEstado(ubigeoDto.getEstado());
			ubigeoView.setIdDepartamento(ubigeoDto.getIdDepartamento());
			ubigeoView.setIdProvincia(ubigeoDto.getIdProvincia());
			ubigeoView.setIdDistrito(ubigeoDto.getIdDistrito());
		}
		return ubigeoView;
	}
	
	/**
	 * convierte de UbigeoViewDTO a UbigeoDTO
	 * @param ubigeoView UbigeoViewDTO a convertir
	 * @return UbigeoDTO convertido
	 */
	public static UbigeoDTO getUbigeoDTO(UbigeoDTO ubigeoView){
		UbigeoDTO ubigeoDto = null;
		if(ubigeoView!=null){
			ubigeoDto = new UbigeoDTO();
			ubigeoDto.setNombre(ubigeoView.getNombre());
			ubigeoDto.setEstado(ubigeoView.getEstado());
			ubigeoDto.setIdDepartamento(ubigeoView.getIdDepartamento());
			ubigeoDto.setIdProvincia(ubigeoView.getIdProvincia());
			ubigeoDto.setIdDistrito(ubigeoView.getIdDistrito());
		}
		return ubigeoDto;
	}
    public static List<UbigeoDTO> toListUbigeoDTO(List<MdiUbigeo> lista) {
        UbigeoDTO registroDTO;
        List<UbigeoDTO> retorno = new ArrayList<UbigeoDTO>();
        if (lista != null) {
            for (MdiUbigeo maestro : lista) {
                registroDTO = toUbigeoDTO(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }

    public static UbigeoDTO toUbigeoDTO(MdiUbigeo registro) {
        UbigeoDTO registroDTO = new UbigeoDTO();
        if (registro != null) {
            if (registro.getMdiUbigeoPK().getIdDepartamento() != null ) {
                registroDTO.setIdDepartamento(registro.getMdiUbigeoPK().getIdDepartamento());              
            }
            if (registro.getMdiUbigeoPK().getIdProvincia()!= null ) {
                registroDTO.setIdProvincia(registro.getMdiUbigeoPK().getIdProvincia());              
            }
            if (registro.getMdiUbigeoPK().getIdDistrito()!= null ) {
                registroDTO.setIdDistrito(registro.getMdiUbigeoPK().getIdDistrito());              
            }
            if (registro.getNombre() != null ) {
                registroDTO.setNombre(registro.getNombre());              
            }
        }
        return registroDTO;
    }

}
