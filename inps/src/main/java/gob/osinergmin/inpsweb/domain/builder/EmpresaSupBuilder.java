package gob.osinergmin.inpsweb.domain.builder;

import java.util.ArrayList;
import java.util.List;
import org.springframework.util.CollectionUtils;

import gob.osinergmin.inpsweb.domain.MdiEmpresaSupervisada;
import gob.osinergmin.mdicommon.domain.dto.DireccionEmpSupDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresaCompletaDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresaContactoDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresaSupDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;

/**
 * Clase para convertir MdiEmpresaSupervisada a EmpresaSupDTO
 * @author Erick Ortiz
 *
 */
public class EmpresaSupBuilder {
	
	/**
	 * Devuelve solo el id de empres supervisada
	 * @param empresaSup MdiEmpresaSupervisada a convertir 
	 * @return EmpresaSupDTO convertida
	 */
	public static EmpresaSupDTO getEmpresaSupDTOSimple(MdiEmpresaSupervisada empresaSup){
		EmpresaSupDTO empresaSupDTO = null;
		if(empresaSup!=null) {
			empresaSupDTO = new EmpresaSupDTO();
			empresaSupDTO.setIdEmpresaSupervisada(empresaSup.getIdEmpresaSupervisada());
		}		
		return empresaSupDTO;
	}
	
	/**
	 * Convierte MdiEmpresaSupervisada a EmpresaSupDTO
	 * @param empresaSup EmpresaSupervisada Objeto Dominio a convertir
	 * @return DTO convertido
	 */	
	public static EmpresaSupDTO getEmpresaSupDTO(MdiEmpresaSupervisada empresaSup) {
		EmpresaSupDTO empresaSupDTO = null;
		if(empresaSup!=null) {
			empresaSupDTO = new EmpresaSupDTO();
			empresaSupDTO.setIdEmpresaSupervisada(empresaSup.getIdEmpresaSupervisada());
			empresaSupDTO.setRazonSocial(empresaSup.getRazonSocial());
			empresaSupDTO.setNroIdentificacion(empresaSup.getNumeroIdentificacion());
			empresaSupDTO.setNombreComercial(empresaSup.getNombreComercial());
			empresaSupDTO.setApellidoPaterno(empresaSup.getApellidoPaterno());
			empresaSupDTO.setApellidoMaterno(empresaSup.getApellidoMaterno());
			empresaSupDTO.setTelefono(empresaSup.getTelefono());
			empresaSupDTO.setNombre(empresaSup.getNombre());			
			empresaSupDTO.setCiiuPrincipal(MaestroColumnaBuilder.getMaestroColumnaDTO(empresaSup.getCiiuPrincipal(),false));
			empresaSupDTO.setFlagBandera(empresaSup.getFlagBandera());
			empresaSupDTO.setNaturaleza(empresaSup.getNaturaleza());
			empresaSupDTO.setOrigenInformacion(empresaSup.getOrigenInformacion());
			empresaSupDTO.setCodigoEmpresa(empresaSup.getCodigoEmpresa());
			empresaSupDTO.setRuc(empresaSup.getRuc());
			empresaSupDTO.setUtm(empresaSup.getUtm());
			empresaSupDTO.setNombreCorto(empresaSup.getNombreCorto());
			empresaSupDTO.setEstado(empresaSup.getEstado());	
			empresaSupDTO.setTipoDocumentoIdentidad(MaestroColumnaBuilder.getMaestroColumnaDTO(empresaSup.getTipoDocumentoIdentidad(), false));
//			if(empresaSup.getTipoDocumentoIdentidad()!=null) {
//				MaestroColumnaDTO tipoDocumentoIdentidad = MaestroColumnaBuilder.getMaestroColumnaDTO(empresaSup.getTipoDocumentoIdentidad(), false);
//				empresaSupDTO.setTipoDocumentoIdentidad(tipoDocumentoIdentidad);
//			}
			empresaSupDTO.setFechaCreacion(empresaSup.getFechaCreacion());
			empresaSupDTO.setUsuarioCreacion(empresaSup.getUsuarioCreacion());
		}		
		return empresaSupDTO;
	}
	
	/**
	 * Convierte lista de MdiEmpresaSupervisada a EmpresaSupDTO
	 * @param listaEmpresaSup lista MdiEmpresaSupervisada
	 * @return lista EmpresaSupDTO convertida
	 */	
	public static List<EmpresaSupDTO> getEmpresaSupDTOList(List<MdiEmpresaSupervisada> listaEmpresaSup) {
		List<EmpresaSupDTO> listaEmpresaSupDTO = null;
		if(!CollectionUtils.isEmpty(listaEmpresaSup)) {
			listaEmpresaSupDTO = new ArrayList<EmpresaSupDTO>();
			EmpresaSupDTO empresaSupDTO = null;
			for(MdiEmpresaSupervisada empresaSup : listaEmpresaSup) {
				empresaSupDTO = getEmpresaSupDTO(empresaSup);				
				listaEmpresaSupDTO.add(empresaSupDTO);
			}
		}
		return listaEmpresaSupDTO;		
	}
	
	/**
	 * Convierte EmpresaSupDTO a MdiEmpresaSup
	 * @param empresaDTO EmpresaSupDTO Objeto DTO a convertir
	 * @return entidad convertida
	 */
	public static MdiEmpresaSupervisada getEmpresaSup(EmpresaSupDTO empresaDTO) {
		MdiEmpresaSupervisada empresa = null;
		if(empresaDTO!=null) {
			empresa = new MdiEmpresaSupervisada();
			empresa.setIdEmpresaSupervisada(empresaDTO.getIdEmpresaSupervisada());
			empresa.setRazonSocial(empresaDTO.getRazonSocial());
			empresa.setNumeroIdentificacion(empresaDTO.getNroIdentificacion());
			empresa.setNombreComercial(empresaDTO.getNombreComercial());
			empresa.setApellidoPaterno(empresaDTO.getApellidoPaterno());
			empresa.setApellidoMaterno(empresaDTO.getApellidoMaterno());
			empresa.setTelefono(empresaDTO.getTelefono());					
			empresa.setNombre(empresaDTO.getNombre());			
			empresa.setCiiuPrincipal(MaestroColumnaBuilder.getSimpleMdiMaestroColumna(empresaDTO.getCiiuPrincipal()));
			empresa.setFlagBandera(empresaDTO.getFlagBandera());			
			empresa.setTipoDocumentoIdentidad(MaestroColumnaBuilder.getSimpleMdiMaestroColumna(empresaDTO.getTipoDocumentoIdentidad()));								
			empresa.setNaturaleza(empresaDTO.getNaturaleza());			
			empresa.setOrigenInformacion(empresaDTO.getOrigenInformacion());			
			empresa.setCodigoEmpresa(empresaDTO.getCodigoEmpresa());
			empresa.setRuc(empresaDTO.getRuc());
			empresa.setUtm(empresaDTO.getUtm());
			empresa.setNombreCorto(empresaDTO.getNombreCorto());					
			empresa.setEstado(empresaDTO.getEstado());																							
		}
		return empresa;
	}	
	
	/**
	 * Convierte EmpresaSupDTO a MdiEmpresaSup 
	 * @param empresaDTO Objeto DTO a convertir
	 * @param usuarioDTO Objeto Auditoria 
	 * @return entidad convertida
	 */
	public static MdiEmpresaSupervisada getEmpresaSup(EmpresaSupDTO empresaDTO, UsuarioDTO usuarioDTO) {
		MdiEmpresaSupervisada empresa = null;
		empresa = getEmpresaSup(empresaDTO);
		if(empresa!=null) {
			empresa.setDatosAuditoria(usuarioDTO);		
		}
		return empresa;
	}
	
	/**
	 * Fusiona los cambios del empresaDTO a MdiEmpresaSupervisada
	 * @param empresaSaved MdiEmpresaSupervisada empresa en bd
	 * @param empresaDTO EmpresaSupDTO empresa editada
	 * @param usuarioDTO operador
	 * @return
	 */
	public static MdiEmpresaSupervisada getEmpresaSupMerged(MdiEmpresaSupervisada empresaSaved, EmpresaSupDTO empresaDTO, UsuarioDTO usuarioDTO){		
		if(empresaSaved != null){
			//no cambiar id
//			if(!StringUtil.isEmpty(empresaDTO.getRazonSocial()))
//				empresaSaved.setRazonSocial(empresaDTO.getRazonSocial());
//			if(!StringUtil.isEmpty(empresaDTO.getNroIdentificacion()))
//				empresaSaved.setNumeroIdentificacion(empresaDTO.getNroIdentificacion());
			if(empresaDTO.getNombreComercial()!=null){
				empresaSaved.setNombreComercial(empresaDTO.getNombreComercial());
			}				
//			if(!StringUtil.isEmpty(empresaDTO.getApellidoPaterno()))
//				empresaSaved.setApellidoPaterno(empresaDTO.getApellidoPaterno());
//			if(!StringUtil.isEmpty(empresaDTO.getApellidoMaterno()))
//				empresaSaved.setApellidoMaterno(empresaDTO.getApellidoMaterno());
//			if(!StringUtil.isEmpty(empresaDTO.getTelefono()))
//				empresaSaved.setTelefono(empresaDTO.getTelefono());				
			empresaSaved.setNaturaleza(empresaDTO.getNaturaleza());			
			empresaSaved.setApellidoPaterno(empresaDTO.getApellidoPaterno());
			empresaSaved.setApellidoMaterno(empresaDTO.getApellidoMaterno());
			empresaSaved.setNombre(empresaDTO.getNombre());
			empresaSaved.setTelefono(empresaDTO.getTelefono());
			empresaSaved.setCiiuPrincipal(MaestroColumnaBuilder.getSimpleMdiMaestroColumna(empresaDTO.getCiiuPrincipal()));
			empresaSaved.setTipoDocumentoIdentidad(MaestroColumnaBuilder.getSimpleMdiMaestroColumna(empresaDTO.getTipoDocumentoIdentidad()));
			empresaSaved.setNumeroIdentificacion(empresaDTO.getNroIdentificacion());
			empresaSaved.setDatosAuditoria(usuarioDTO);
			
		}
		return empresaSaved;
	}

}
