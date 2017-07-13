/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.business;

import gob.osinergmin.mdicommon.domain.ui.ProcesoFilter;
import gob.osinergmin.mdicommon.domain.dto.EtapaDTO;
import gob.osinergmin.mdicommon.domain.dto.ProcesoDTO;
import gob.osinergmin.mdicommon.domain.dto.TramiteDTO;

import java.util.List;

/**
 *
 * @author jpiro
 */
public interface SerieServiceNeg {
    public List<ProcesoDTO> listarProceso(ProcesoFilter procesoFilter);
    public List<EtapaDTO> listarEtapa(Long idProceso);
    public List<TramiteDTO> listarTramite(Long idEtapa);
}
