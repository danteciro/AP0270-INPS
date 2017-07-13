/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.business;

import gob.osinergmin.inpsweb.service.exception.InfraccionException;
import gob.osinergmin.mdicommon.domain.dto.InfraccionDTO;
import gob.osinergmin.mdicommon.domain.ui.InfraccionFilter;
import java.util.List;

/**
 *
 * @author zchaupis
 */
public interface InfraccionServiceNeg {
    public List<InfraccionDTO> getListInfraccion(InfraccionFilter filtro) throws InfraccionException;
    public InfraccionDTO ComprobarMedidaSeguridadInfraccion (InfraccionFilter filtro) throws InfraccionException;
}
