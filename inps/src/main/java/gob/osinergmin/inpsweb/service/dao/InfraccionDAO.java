/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.inpsweb.service.exception.InfraccionException;
import gob.osinergmin.mdicommon.domain.dto.InfraccionDTO;
/*OSINE_SFS-791 - RSIS 16 - Inicio*/
import gob.osinergmin.mdicommon.domain.ui.EscenarioIncumplimientoFilter;
/*OSINE_SFS-791 - RSIS 16 - Fin*/
import gob.osinergmin.mdicommon.domain.ui.InfraccionFilter;
import java.util.List;

/**
 *
 * @author zchaupis
 */
public interface InfraccionDAO {
    public List<InfraccionDTO> getListInfraccion(InfraccionFilter filtro)throws InfraccionException;
    /*OSINE_SFS-791 - RSIS 16 - Inicio*/
    public List<InfraccionDTO> findByFilter(EscenarioIncumplimientoFilter filtro) throws InfraccionException;
    /*OSINE_SFS-791 - RSIS 16 - Fin*/
}
