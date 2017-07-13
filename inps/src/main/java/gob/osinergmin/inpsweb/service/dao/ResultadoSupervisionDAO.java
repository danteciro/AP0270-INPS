/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.mdicommon.domain.dto.ResultadoSupervisionDTO;
import gob.osinergmin.mdicommon.domain.ui.ResultadoSupervisionFilter;
import java.util.List;

/**
 *
 * @author zchaupis
 */
public interface ResultadoSupervisionDAO {
    
    public List<ResultadoSupervisionDTO> listarResultadoSupervision(ResultadoSupervisionFilter filtro);
	
    public ResultadoSupervisionDTO getResultadoSupervision(ResultadoSupervisionFilter filtro);
	
}
