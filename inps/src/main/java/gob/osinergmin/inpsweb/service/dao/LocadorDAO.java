package gob.osinergmin.inpsweb.service.dao;
import gob.osinergmin.inpsweb.service.exception.LocadorException;
import gob.osinergmin.mdicommon.domain.dto.ConfFiltroEmpSupDTO;
import gob.osinergmin.mdicommon.domain.dto.LocadorDTO;
import gob.osinergmin.mdicommon.domain.ui.LocadorFilter;

import java.util.List;

/**
 *
 * @author mdiosesf
 */
public interface LocadorDAO {
    public List<LocadorDTO> listarLocadorConfFiltros(LocadorFilter filtro, List<ConfFiltroEmpSupDTO>listaConfFiltroEmpSup) throws LocadorException;	
    public LocadorDTO getById(Long idLocador) throws LocadorException;
    
}