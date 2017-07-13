/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.inpsweb.service.exception.ProductoUnidadSupervisadaException;
import gob.osinergmin.mdicommon.domain.dto.ProductoUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.ui.ProductoUnidadSupervisadaFilter;
import java.util.List;

/**
 *
 * @author zchaupis
 */
public interface ProductoUnidadSupervisadaDAO {

    public List<ProductoUnidadSupervisadaDTO> getProductobyUnidadSupervisada(ProductoUnidadSupervisadaFilter filtro)throws ProductoUnidadSupervisadaException;
}
