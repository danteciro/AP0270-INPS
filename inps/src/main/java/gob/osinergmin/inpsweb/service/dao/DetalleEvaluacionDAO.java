/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.mdicommon.domain.ui.DetalleEvaluacionFilter;
import gob.osinergmin.inpsweb.service.exception.DetalleEvaluacionException;
import gob.osinergmin.inpsweb.service.exception.SupervisionException;
import gob.osinergmin.mdicommon.domain.dto.DetalleEvaluacionDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;

import java.util.List;

/**
 *
 * @author jpiro
 */
public interface DetalleEvaluacionDAO {
	public List<DetalleEvaluacionDTO> findDetalleEvaluacion(DetalleEvaluacionFilter filtro) throws DetalleEvaluacionException;
	public List<DetalleEvaluacionDTO> listarDetalleEvaluacion(DetalleEvaluacionFilter filtro) throws DetalleEvaluacionException;
	public DetalleEvaluacionDTO actualizar(DetalleEvaluacionDTO detalleEvaluacionDTO, UsuarioDTO usuarioDTO) throws DetalleEvaluacionException;
	public DetalleEvaluacionDTO registrar(DetalleEvaluacionDTO detalleEvaluacionDTO, UsuarioDTO usuarioDTO) throws DetalleEvaluacionException;
}
