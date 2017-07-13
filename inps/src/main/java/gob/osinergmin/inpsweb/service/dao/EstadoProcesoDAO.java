/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.mdicommon.domain.ui.EstadoProcesoFilter;
import gob.osinergmin.inpsweb.service.exception.EstadoProcesoException;
import gob.osinergmin.mdicommon.domain.dto.EstadoProcesoDTO;

import java.util.List;

/**
 *
 * @author jpiro
 */
public interface EstadoProcesoDAO {
    public List<EstadoProcesoDTO> find(EstadoProcesoFilter filtro) throws EstadoProcesoException;
}
