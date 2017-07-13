/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.service.business.ProductoUnidadSupervisadaServiceNeg;
import gob.osinergmin.inpsweb.service.dao.ProductoUnidadSupervisadaDAO;
import gob.osinergmin.inpsweb.service.exception.ProductoUnidadSupervisadaException;
import gob.osinergmin.mdicommon.domain.dto.ProductoUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.ui.ProductoUnidadSupervisadaFilter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author zchaupis
 */
@Service("ProductoUnidadSupervisadaServiceNeg")
public class ProductoUnidadSupervisadaServiceNegImpl implements ProductoUnidadSupervisadaServiceNeg {

    private static Logger LOG = LoggerFactory.getLogger(ProductoUnidadSupervisadaServiceNegImpl.class);
    @Inject
    private ProductoUnidadSupervisadaDAO productoUnidadSupervisadaDAO;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoUnidadSupervisadaDTO> getProductobyUnidadSupervisada(ProductoUnidadSupervisadaFilter filtro) throws ProductoUnidadSupervisadaException{
        LOG.info("getProductobyUnidadOperativa");
        List<ProductoUnidadSupervisadaDTO> retorno = new ArrayList<ProductoUnidadSupervisadaDTO>();
        try {
            retorno = productoUnidadSupervisadaDAO.getProductobyUnidadSupervisada(filtro);
        } catch (Exception ex) {
            LOG.error("Error en getProductobyUnidadOperativa", ex);
        }
        return retorno;
    }
}
