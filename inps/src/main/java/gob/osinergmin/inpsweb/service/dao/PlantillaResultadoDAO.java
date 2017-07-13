package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.inpsweb.service.exception.PlantillaResultadoException;
import gob.osinergmin.mdicommon.domain.dto.PlantillaResultadoDTO;
import gob.osinergmin.mdicommon.domain.ui.PlantillaResultadoFilter;

import java.util.List;

public interface PlantillaResultadoDAO {
    public List<PlantillaResultadoDTO> listarPlantillaResultado(PlantillaResultadoFilter filtro);
    public PlantillaResultadoDTO getPlantillaResultado(Long idPlantillaResultado) throws PlantillaResultadoException;
}
