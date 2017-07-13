/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.business;

import gob.osinergmin.inpsweb.service.exception.ResultadoSupervisionException;
import gob.osinergmin.mdicommon.domain.dto.ResultadoSupervisionDTO;
import gob.osinergmin.mdicommon.domain.ui.ResultadoSupervisionFilter;
import java.util.List;

/**
 *
 * @author zchaupis
 */
public interface ResultadoSupervisionServiceNeg {

    public List<ResultadoSupervisionDTO> listarResultadoSupervision(ResultadoSupervisionFilter resultadoSupervisionFilter) throws ResultadoSupervisionException;

    public ResultadoSupervisionDTO getResultadoSupervision(ResultadoSupervisionFilter resultadoSupervisionFilter) throws ResultadoSupervisionException;
}
