/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghRolOpcion;
import gob.osinergmin.mdicommon.domain.dto.OpcionDTO;
import gob.osinergmin.mdicommon.domain.dto.RolDTO;
import gob.osinergmin.mdicommon.domain.dto.RolOpcionDTO;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jpiro
 */
public class RolOpcionBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(RolOpcionBuilder.class);  
    
    public static List<RolOpcionDTO> toListRolOpcionDto(List<PghRolOpcion> lista) {
        LOG.info("Builder toListRolOpcionDto");
        RolOpcionDTO registroDTO;
        List<RolOpcionDTO> retorno = new ArrayList<RolOpcionDTO>();
        if (lista != null) {
            for (PghRolOpcion maestro : lista) {
                registroDTO = toRolOpcionDto(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    } 
    
    public static RolOpcionDTO toRolOpcionDto(PghRolOpcion registro) {
        LOG.info("Builder toRolOpcionDto");
        RolOpcionDTO registroDTO = new RolOpcionDTO();
        
        registroDTO.setIdRolOpcion(registro.getIdRolOpcion());
        if(registro.getIdRol()!=null){
            RolDTO rol=new RolDTO();
            rol.setIdRol(registro.getIdRol().getIdRol());
            rol.setNombreRol(registro.getIdRol().getNombreRol());
            rol.setIdentificadorRol(registro.getIdRol().getIdentificadorRol());
            registroDTO.setIdRol(rol);
        }
        if(registro.getIdOpcion()!=null){
            OpcionDTO opcion=new OpcionDTO();
            opcion.setIdOpcion(registro.getIdOpcion().getIdOpcion());
            opcion.setNombreOpcion(registro.getIdOpcion().getNombreOpcion());
            opcion.setIdentificadorOpcion(registro.getIdOpcion().getIdentificadorOpcion());
            opcion.setPageOpcion(registro.getIdOpcion().getPageOpcion());
            registroDTO.setIdOpcion(opcion);
        }
        return registroDTO;
    }
}
