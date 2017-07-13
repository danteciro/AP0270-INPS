/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain.builder;
import gob.osinergmin.inpsweb.domain.MdiLocador;
import gob.osinergmin.mdicommon.domain.dto.LocadorDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mdiosesf
 */
public class LocadorBuilder {
    
    public static LocadorDTO toLocadorDto(MdiLocador registro) {
        LocadorDTO registroDTO = new LocadorDTO();
        
        if(registro!=null){
            registroDTO.setIdLocador(registro.getIdLocador());
            registroDTO.setNombreCompleto(registro.getNombreCompleto());
            registroDTO.setNumeroDocumento(registro.getNumeroDocumento());
            registroDTO.setNumeroColegiatura(registro.getNumeroColegiatura());
            registroDTO.setTelefonoContacto(registro.getTelefonoContacto());
            registroDTO.setTelefonoPersonal(registro.getTelefonoPersonal());
            registroDTO.setCorreoElectronicoInstitucion(registro.getCorreoElectronicoLaboral());
            registroDTO.setCorreoElectronicoPersonal(registro.getCorreoElectronicoPersonal());
            if( registro.getIdTipoDocumentoIdentidad() != null && registro.getIdTipoDocumentoIdentidad().getCodigo() != null){
                MaestroColumnaDTO maestro = new MaestroColumnaDTO();
                maestro.setCodigo(registro.getIdTipoDocumentoIdentidad().getCodigo());
                maestro.setIdMaestroColumna(registro.getIdTipoDocumentoIdentidad().getIdMaestroColumna());
                registroDTO.setIdTipoDocumento(maestro);
            }
           
        }
        return registroDTO;
    }
	
      public static List<LocadorDTO> toListLocadorDtoBusqueda(List<MdiLocador> lista) {
      LocadorDTO locadorDTO;
        List<LocadorDTO> listaLocador = new ArrayList<LocadorDTO>();
        if (lista != null) {
			for (MdiLocador registro : lista) {	
            	locadorDTO=new LocadorDTO();
            	locadorDTO.setIdLocador(registro.getIdLocador());
            	locadorDTO.setApellidoMaterno(registro.getApellidoMaterno());
            	locadorDTO.setApellidoPaterno(registro.getApellidoMaterno());
            	locadorDTO.setSegundoNombre(registro.getSegundoNombre());
            	locadorDTO.setNumeroDocumento(registro.getNumeroDocumento());
            	locadorDTO.setRuc(registro.getRuc());
            	locadorDTO.setTelefonoContacto(registro.getTelefonoContacto());
            	locadorDTO.setTelefonoPersonal(registro.getTelefonoPersonal());
            	locadorDTO.setEstado(registro.getEstado());
            	locadorDTO.setNombreCompleto(registro.getNombreCompleto());
            	locadorDTO.setNombreCompletoArmado(registro.getNombreCompletoArmado());
            	if(registro.getIdTipoDocumentoIdentidad()!=null && registro.getIdTipoDocumentoIdentidad().getIdMaestroColumna()!=null){
	            	MaestroColumnaDTO IdTipoDocumentoIdentidad=new MaestroColumnaDTO();
	            	IdTipoDocumentoIdentidad.setIdMaestroColumna(registro.getIdTipoDocumentoIdentidad().getIdMaestroColumna());
	            	IdTipoDocumentoIdentidad.setDescripcion(registro.getIdTipoDocumentoIdentidad().getDescripcion());
	            	locadorDTO.setIdTipoDocumento(IdTipoDocumentoIdentidad);
            	}
            	locadorDTO.setFechaNacimiento(registro.getFechaNacimiento());
            	locadorDTO.setSexo(registro.getSexo());
            	locadorDTO.setCorreoElectronicoPersonal(registro.getCorreoElectronicoPersonal());
            	locadorDTO.setIdProfesion(registro.getIdProfesion());   
            	listaLocador.add(locadorDTO);
            }
        }
        return listaLocador;
    }
}
