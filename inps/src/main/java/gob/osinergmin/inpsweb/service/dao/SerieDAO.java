/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.mdicommon.domain.ui.ProcesoFilter;
import gob.osinergmin.inpsweb.service.exception.SerieException;
import gob.osinergmin.mdicommon.domain.dto.EtapaDTO;
import gob.osinergmin.mdicommon.domain.dto.ProcesoDTO;
import gob.osinergmin.mdicommon.domain.dto.TramiteDTO;

import java.util.List;

/**
 *
 * @author jpiro
 */
public interface SerieDAO {
    public List<ProcesoDTO> listarProceso(ProcesoFilter filtro) throws SerieException;
    public List<EtapaDTO> listarEtapa(Long idProceso) throws SerieException;
    public List<TramiteDTO> listarTramite(Long idEtapa) throws SerieException;
}
