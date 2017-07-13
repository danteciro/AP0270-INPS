/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.ProductoDsrScopDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.ProductoDsrScopFilter;
import java.util.List;

/**
 *
 * @author zchaupis
 */
public interface ProductoSuspenderDAO {

    public ProductoDsrScopDTO actualizarProductoSuspender(ProductoDsrScopDTO productoDsrScopDTO, UsuarioDTO usuarioDto);

    public ProductoDsrScopDTO eliminarProductoSuspender(ProductoDsrScopDTO productoDsrScopDTO);

    public String eliminarProductoSuspenderbyDetalleSUpervision(DetalleSupervisionDTO detalleSUpervisionDTO);
    
    public List<ProductoDsrScopDTO> getProductoSuspender(ProductoDsrScopFilter productoDsrScopFilter) ;
}
