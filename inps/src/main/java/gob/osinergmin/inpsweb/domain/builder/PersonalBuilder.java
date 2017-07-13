/**
* Resumen
* Objeto		: PersonalBuilder.java
* Descripción		: Constructor de Personal
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     11/05/2016      Luis García Reyna           Correo de Alerta a Empresa Supervisora cuando se le asigne Orden de Servicio.
*
*/

package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghPersonal;
import gob.osinergmin.mdicommon.domain.dto.LocadorDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.PersonalDTO;
import gob.osinergmin.mdicommon.domain.dto.PersonalUnidadOrganicaDTO;
import gob.osinergmin.mdicommon.domain.dto.RolDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisoraEmpresaDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadOrganicaDTO;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jpiro
 */
public class PersonalBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(PersonalBuilder.class);  
    
    public static List<PersonalDTO> toListPersonalDto(List<PghPersonal> lista) {
        LOG.info("Builder toListPersonalDto");
        PersonalDTO registroDTO;
        List<PersonalDTO> retorno = new ArrayList<PersonalDTO>();
        if (lista != null) {
            for (PghPersonal maestro : lista) {
                registroDTO = toPersonalDto(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    } 
    
    public static PersonalDTO toPersonalDto(PghPersonal registro) {
        LOG.info("Builder toPersonalDto");
        PersonalDTO registroDTO = new PersonalDTO();
        
        registroDTO.setIdPersonal(registro.getIdPersonal());
        registroDTO.setIdPersonalSiged(registro.getIdPersonalSiged());
        registroDTO.setNombreUsuarioSiged(registro.getNombreUsuarioSiged());
        if(registro.getIdRol()!=null){
            RolDTO rol = new RolDTO();
            rol.setNombreRol(registro.getIdRol().getNombreRol());
            rol.setIdentificadorRol(registro.getIdRol().getIdentificadorRol());
            registroDTO.setRol(rol);
        }        
        registroDTO.setNombre(registro.getNombre());
        registroDTO.setApellidoPaterno(registro.getApellidoPaterno());
        registroDTO.setApellidoMaterno(registro.getApellidoMaterno());
        /* OSINE_SFS-480 - RSIS 12 - Inicio */
        registroDTO.setNombreCompleto(registro.getNombreCompleto());
        registroDTO.setCorreoElectronico(registro.getCorreoElectronico());
        /* OSINE_SFS-480 - RSIS 12 - Fin */       
        if(registro.getIdLocador()!=null){
            LocadorDTO locador=new LocadorDTO();
            locador.setIdLocador(registro.getIdLocador().getIdLocador());
            locador.setPrimerNombre(registro.getIdLocador().getPrimerNombre());
            locador.setSegundoNombre(registro.getIdLocador().getSegundoNombre());
            locador.setApellidoPaterno(registro.getIdLocador().getApellidoPaterno());
            locador.setApellidoMaterno(registro.getIdLocador().getApellidoMaterno());
            /* OSINE_SFS-480 - RSIS 12 - Inicio */
            locador.setNombreCompleto(registro.getIdLocador().getNombreCompleto());
            /* OSINE_SFS-480 - RSIS 12 - Fin */
            if(registro.getIdLocador().getIdTipoDocumentoIdentidad()!=null){
                MaestroColumnaDTO tipoDocumentoIdentidad=new MaestroColumnaDTO();
                tipoDocumentoIdentidad.setDescripcion(registro.getIdLocador().getIdTipoDocumentoIdentidad().getDescripcion());
                locador.setIdTipoDocumento(tipoDocumentoIdentidad);
            }
            locador.setNumeroDocumento(registro.getIdLocador().getNumeroDocumento());
            registroDTO.setLocador(locador);
        }
        if(registro.getIdSupervisoraEmpresa()!=null){
            SupervisoraEmpresaDTO supervisoraEmpresa=new SupervisoraEmpresaDTO();
            supervisoraEmpresa.setIdSupervisoraEmpresa(registro.getIdSupervisoraEmpresa().getIdSupervisoraEmpresa());
            supervisoraEmpresa.setRazonSocial(registro.getIdSupervisoraEmpresa().getRazonSocial());
            supervisoraEmpresa.setNombreConsorcio(registro.getIdSupervisoraEmpresa().getNombreConsorcio());
            supervisoraEmpresa.setRuc(registro.getIdSupervisoraEmpresa().getRuc());
            registroDTO.setSupervisoraEmpresa(supervisoraEmpresa);
        }
        if(registro.getPersonalUnidadOrganicaDefault()!=null){
            PersonalUnidadOrganicaDTO personalUnidadOrganicaDTO = new PersonalUnidadOrganicaDTO();
            personalUnidadOrganicaDTO.setIdPersonalUnidadOrganica(registro.getPersonalUnidadOrganicaDefault().getIdPersonalUnidadOrganica());
            personalUnidadOrganicaDTO.setFlagDefault(registro.getPersonalUnidadOrganicaDefault().getFlagDefault());
            if(registro.getPersonalUnidadOrganicaDefault().getIdUnidadOrganica()!=null){
                personalUnidadOrganicaDTO.setUnidadOrganica(new UnidadOrganicaDTO(registro.getPersonalUnidadOrganicaDefault().getIdUnidadOrganica().getIdUnidadOrganica()));
            }            
            registroDTO.setPersonalUnidadOrganicaDefault(personalUnidadOrganicaDTO);
        }
        return registroDTO;
    }
}
