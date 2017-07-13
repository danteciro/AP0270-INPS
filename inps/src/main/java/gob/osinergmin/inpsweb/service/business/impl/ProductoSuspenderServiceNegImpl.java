/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.service.business.ProductoSuspenderServiceNeg;
import gob.osinergmin.inpsweb.service.dao.ProductoSuspenderDAO;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.ProductoDsrScopDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.ProductoDsrScopFilter;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author zchaupis
 */
@Service("productoSuspenderServiceNeg")
public class ProductoSuspenderServiceNegImpl implements ProductoSuspenderServiceNeg {

    private static final Logger LOG = LoggerFactory.getLogger(ProductoSuspenderServiceNegImpl.class);
    @Inject
    private ProductoSuspenderDAO productoSuspenderDAO;

    @Override
    public ProductoDsrScopDTO actualizarProductoSuspender(ProductoDsrScopDTO productoDsrScopDTO, UsuarioDTO usuarioDto) {
        LOG.info("Neg actualizarProductoSuspender");
        ProductoDsrScopDTO retorno = null;
        try {
            retorno = productoSuspenderDAO.actualizarProductoSuspender(productoDsrScopDTO, usuarioDto);
        } catch (Exception ex) {
            LOG.error("", ex);
        }
        return retorno;
    }

    @Override
    public ProductoDsrScopDTO eliminarProductoSuspender(ProductoDsrScopDTO productoDsrScopDTO) {
     LOG.info("Neg eliminarProductoSuspender");
        ProductoDsrScopDTO retorno = null;
        try {
            retorno = productoSuspenderDAO.eliminarProductoSuspender(productoDsrScopDTO);
        } catch (Exception ex) {
            LOG.error("", ex);
        }
        return retorno;
    }

    @Override
    public String eliminarProductoSuspenderbyDetalleSUpervision(DetalleSupervisionDTO detalleSUpervisionDTO) {
      LOG.info("Inicio - eliminarProductoSuspenderbyDetalleSUpervision");
        String retorno = "";
        try {
            retorno = productoSuspenderDAO.eliminarProductoSuspenderbyDetalleSUpervision(detalleSUpervisionDTO);
        } catch (Exception ex) {
            LOG.error("Error en eliminarProductoSuspenderbyDetalleSUpervision", ex);
        }
        return retorno;
    }

    @Override
    public List<ProductoDsrScopDTO> getProductoSuspender(ProductoDsrScopFilter productoDsrScopFilter) {
     LOG.info("productoSuspenderServiceNeg--getProductoSuspender - inicio");
        List<ProductoDsrScopDTO> retorno = null;
        try {
            retorno = productoSuspenderDAO.getProductoSuspender(productoDsrScopFilter);
        } catch (Exception ex) {
            LOG.error("Error en getProductoSuspender", ex);
        }
        LOG.info("productoSuspenderServiceNeg--getProductoSuspender - fin");
        return retorno;  
    }
}
