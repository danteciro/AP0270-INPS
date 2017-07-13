/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.inpsweb.service.exception.HstEstadoLevantamientoException;
import gob.osinergmin.mdicommon.domain.dto.HstEstadoLevantamientoDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;

/**
 *
 * @author zchaupis
 */
public interface HstEstadoLevantamientoDAO {
    /* OSINE_SFS-791 - RSIS 41 - Inicio */
     public HstEstadoLevantamientoDTO registrarHstEstadoLevantamiento(HstEstadoLevantamientoDTO hstEstadoLevantamientoDTO,UsuarioDTO usuarioDTO) throws HstEstadoLevantamientoException;
    /* OSINE_SFS-791 - RSIS 41 - Fin */
}
