/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.inpsweb.dto.RegistroHidrocarburoExternoDTO;
import gob.osinergmin.inpsweb.service.exception.RegistroHidrocarburoException;
import gob.osinergmin.inpsweb.service.exception.UnidadSupervisadaException;
import gob.osinergmin.mdicommon.domain.dto.RegistroHidrocarburoDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.RegistroHidrocarburoFilter;
import java.util.List;

/**
 *
 * @author zchaupis
 */
public interface RegistroHidrocarburoDAO {

    public List<RegistroHidrocarburoDTO> getListRegistroHidrocarburo(RegistroHidrocarburoFilter filtro) throws RegistroHidrocarburoException;

    public int actualizarRegistroHidrocarburoExterno(RegistroHidrocarburoExternoDTO registroHidrocarburoExternoDTO) throws RegistroHidrocarburoException;

    public Long veridUnidadOperativaBDExterna(UnidadSupervisadaDTO UnidadSupervisadaDTO) throws UnidadSupervisadaException;

    public RegistroHidrocarburoDTO ActualizarRegistroHidrocarburo(RegistroHidrocarburoDTO registroHidrocarburoDTO, UsuarioDTO usuarioDTO) throws RegistroHidrocarburoException;

    public RegistroHidrocarburoExternoDTO veridActividadBDExterna(UnidadSupervisadaDTO UnidadSupervisadaDTO, Long idUnidadSupervisadaExterna) throws UnidadSupervisadaException;
}
