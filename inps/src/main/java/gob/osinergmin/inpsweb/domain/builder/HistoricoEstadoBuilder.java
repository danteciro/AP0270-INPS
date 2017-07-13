/**
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         				 |  Fecha        |   Nombre                     |     Descripcion
* =====================================================================================================================================================================
* OSINE_MAN_DSHL-002  	    	 |  20/06/2017   |   Claudio Chaucca Umana::ADAPTER      |     Coloca como Personal destino asignado a la empresa o el locador asignado 
*/

package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghHistoricoEstado;
import gob.osinergmin.mdicommon.domain.dto.EstadoProcesoDTO;
import gob.osinergmin.mdicommon.domain.dto.HistoricoEstadoDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.OrdenServicioDTO;
import gob.osinergmin.mdicommon.domain.dto.PersonalDTO;
import gob.osinergmin.mdicommon.domain.dto.RolDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisoraEmpresaDTO;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jpiro
 */
public class HistoricoEstadoBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(HistoricoEstadoBuilder.class);  
    
    public static List<HistoricoEstadoDTO> toListHistoricoEstadoDto(List<PghHistoricoEstado> lista) {
        LOG.info("Builder toListHistoricoEstadoDto");
        HistoricoEstadoDTO registroDTO;
        List<HistoricoEstadoDTO> retorno = new ArrayList<HistoricoEstadoDTO>();
        if (lista != null) {
            for (PghHistoricoEstado maestro : lista) {
                registroDTO = toHistoricoEstadoDto(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    } 
    
    public static HistoricoEstadoDTO toHistoricoEstadoDto(PghHistoricoEstado registro) {
        LOG.info("Builder toHistoricoEstadoDto");
        HistoricoEstadoDTO registroDTO = new HistoricoEstadoDTO();
        
        registroDTO.setIdHistoricoEstado(registro.getIdHistoricoEstado());
        if(registro.getIdOrdenServicio()!=null){
            OrdenServicioDTO ordenServicio=new OrdenServicioDTO();
            ordenServicio.setIdOrdenServicio(registro.getIdOrdenServicio().getIdOrdenServicio());
            ordenServicio.setNumeroOrdenServicio(registro.getIdOrdenServicio().getNumeroOrdenServicio());
            registroDTO.setOrdenServicio(ordenServicio);
        }
        registroDTO.setFechaCreacion(registro.getFechaCreacion());
        if(registro.getIdPersonalOri()!=null){
            PersonalDTO personalOri=new PersonalDTO();
            personalOri.setIdPersonal(registro.getIdPersonalOri().getIdPersonal());
            personalOri.setNombre(registro.getIdPersonalOri().getNombre());
            personalOri.setApellidoPaterno(registro.getIdPersonalOri().getApellidoPaterno());
            personalOri.setApellidoMaterno(registro.getIdPersonalOri().getApellidoMaterno());
            if(registro.getIdPersonalOri().getIdRol()!=null){
                RolDTO rolOri=new RolDTO();
                rolOri.setNombreRol(registro.getIdPersonalOri().getIdRol().getNombreRol());
                personalOri.setRol(rolOri);
            }
            registroDTO.setPersonalOri(personalOri);
        }
        
        /* OSINE_MAN_DSHL-002 - Inicio */
        if(registro.getIdOrdenServicio()!=null){
        	 if(registro.getIdOrdenServicio().getIdLocador()!=null){
        		 //Locador
        		 PersonalDTO personalDest=new PersonalDTO();
        		 personalDest.setIdPersonal(registro.getIdOrdenServicio().getIdLocador().getIdLocador());
                 personalDest.setNombre(registro.getIdOrdenServicio().getIdLocador().getNombreCompleto());
                 personalDest.setApellidoPaterno(registro.getIdOrdenServicio().getIdLocador().getApellidoPaterno());
                 personalDest.setApellidoMaterno(registro.getIdOrdenServicio().getIdLocador().getApellidoMaterno());
                 registroDTO.setPersonalDest(personalDest);
        	 }else if(registro.getIdOrdenServicio().getIdSupervisoraEmpresa()!=null){
        		 //Empresa supervisora
        		 PersonalDTO personalDest=new PersonalDTO();
        		 personalDest.setIdPersonal(registro.getIdOrdenServicio().getIdSupervisoraEmpresa().getIdSupervisoraEmpresa());
                 String razsoc=registro.getIdOrdenServicio().getIdSupervisoraEmpresa().getRazonSocial()!=null?registro.getIdOrdenServicio().getIdSupervisoraEmpresa().getRazonSocial():registro.getIdOrdenServicio().getIdSupervisoraEmpresa().getNombreConsorcio();
                 personalDest.setNombre(razsoc);
                 personalDest.setApellidoPaterno("");
                 personalDest.setApellidoMaterno("");
                 registroDTO.setPersonalDest(personalDest);
        	 }else{
        		 //Por defecto Personal Destinatario
        		 PersonalDTO personalDest=new PersonalDTO();
                 personalDest.setIdPersonal(registro.getIdPersonalDest().getIdPersonal());
                 personalDest.setNombre(registro.getIdPersonalDest().getNombre());
                 personalDest.setApellidoPaterno(registro.getIdPersonalDest().getApellidoPaterno());
                 personalDest.setApellidoMaterno(registro.getIdPersonalDest().getApellidoMaterno());
                 if(registro.getIdPersonalDest().getIdRol()!=null){
                     RolDTO rolDest=new RolDTO();
                     rolDest.setNombreRol(registro.getIdPersonalDest().getIdRol().getNombreRol());
                     personalDest.setRol(rolDest);
                 }
                 registroDTO.setPersonalDest(personalDest);
        	 }
        }
        /*
        if(registro.getIdPersonalDest()!=null){
            PersonalDTO personalDest=new PersonalDTO();
            personalDest.setIdPersonal(registro.getIdPersonalDest().getIdPersonal());
            personalDest.setNombre(registro.getIdPersonalDest().getNombre());
            personalDest.setApellidoPaterno(registro.getIdPersonalDest().getApellidoPaterno());
            personalDest.setApellidoMaterno(registro.getIdPersonalDest().getApellidoMaterno());
            if(registro.getIdPersonalDest().getIdRol()!=null){
                RolDTO rolDest=new RolDTO();
                rolDest.setNombreRol(registro.getIdPersonalDest().getIdRol().getNombreRol());
                personalDest.setRol(rolDest);
            }
            registroDTO.setPersonalDest(personalDest);
        }*/
        /* OSINE_MAN_DSHL-002 - Fin */
        
        if(registro.getIdEstadoProceso()!=null){
            EstadoProcesoDTO estadoProceso=new EstadoProcesoDTO();
            estadoProceso.setNombreEstado(registro.getIdEstadoProceso().getNombreEstado());
            registroDTO.setEstadoProceso(estadoProceso);
        }
        
        if(registro.getIdPeticion()!=null){
            registroDTO.setPeticion(new MaestroColumnaDTO(registro.getIdPeticion().getIdMaestroColumna(), registro.getIdPeticion().getDescripcion()));            
        }
        if(registro.getIdMotivo()!=null){
            registroDTO.setMotivo(new MaestroColumnaDTO(registro.getIdMotivo().getIdMaestroColumna(), registro.getIdMotivo().getDescripcion()));            
        }
        
        return registroDTO;
    }
}
