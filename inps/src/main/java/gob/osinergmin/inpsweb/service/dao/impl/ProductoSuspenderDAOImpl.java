/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.PghProductoSuspender;
import gob.osinergmin.inpsweb.domain.builder.ProductoSuspenderBuilder;
import gob.osinergmin.inpsweb.service.business.impl.ObligacionDsrServiceNegImpl;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.ObligacionDsrDAO;
import gob.osinergmin.inpsweb.service.dao.ProductoSuspenderDAO;
import gob.osinergmin.inpsweb.service.dao.ResultadoSupervisionDAO;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.ProductoDsrScopDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.ProductoDsrScopFilter;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author zchaupis
 */
@Service("ProductoSuspenderDAO")
public class ProductoSuspenderDAOImpl implements ProductoSuspenderDAO {
    
    private static final Logger LOG = LoggerFactory.getLogger(ProductoSuspenderDAOImpl.class);
    @Inject
    private CrudDAO crud;
    
    @Override
    public ProductoDsrScopDTO actualizarProductoSuspender(ProductoDsrScopDTO productoDsrScopDTO, UsuarioDTO usuarioDto) {
        ProductoDsrScopDTO producto = null;
        PghProductoSuspender pghProductoSuspender = crud.find(productoDsrScopDTO.getIdProductoSuspender(), PghProductoSuspender.class);
        pghProductoSuspender.setEstado(Constantes.ESTADO_INACTIVO);
        pghProductoSuspender.setDatosAuditoria(usuarioDto);
        crud.update(pghProductoSuspender);
        producto = ProductoSuspenderBuilder.toProductoSuspenderDTO(pghProductoSuspender);
        return producto;
    }

    @Override
    @Transactional()
    public ProductoDsrScopDTO eliminarProductoSuspender(ProductoDsrScopDTO productoDsrScopDTO) {
        ProductoDsrScopDTO producto = null;
        PghProductoSuspender pghProductoSuspender = crud.find(productoDsrScopDTO.getIdProductoSuspender(), PghProductoSuspender.class);
        crud.delete(pghProductoSuspender);
        return producto;
    }

    @Override
    @Transactional()
    public String eliminarProductoSuspenderbyDetalleSUpervision(DetalleSupervisionDTO detalleSupervisionDTO) {
     LOG.info("DocumentoAdjuntoDAOImpl : eliminarProductoSuspenderbyDetalleSUpervision-inicio");
        String rpta = "";
        try {
            StringBuilder jpql = new StringBuilder();
            jpql.append(" delete  pgh_producto_suspender pro ");
            jpql.append(" where pro.id_detalle_supervision = :idDetalleSupervision  ");
            String queryString = jpql.toString();
            Query q = crud.getEm().createNativeQuery(queryString);
            q.setParameter("idDetalleSupervision", detalleSupervisionDTO.getIdDetalleSupervision());
            int val = q.executeUpdate();
            LOG.info("Se Eliminaron " + val + " Registros de pgh_producto_suspender");
            rpta = "ok";
        } catch (Exception ex) {
            LOG.info("Ocurrio un error " + ex.getMessage());
            LOG.error("Error en eliminarProductoSuspenderbyDetalleSUpervision DAO imple", ex);
            rpta = "error";
        }
        return rpta;
    }

    @Override
    public List<ProductoDsrScopDTO> getProductoSuspender(ProductoDsrScopFilter filtro) {
     LOG.info("ProductoSuspenderDAOImpl: getProductoSuspender-inicio");
        List<ProductoDsrScopDTO> retorno = null;
        try {
            String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT distinct new PghProductoSuspender ");
            jpql.append(" ( ");            
            jpql.append(" pro.idProducto , ");
            jpql.append(" pro.nombreLargo ");
            jpql.append(" ) ");
            jpql.append(" FROM PghProductoSuspender prosus ");
            jpql.append(" LEFT JOIN prosus.idDetalleSupervision deta ");
            jpql.append(" LEFT JOIN deta.idSupervision sus ");            
            jpql.append(" LEFT JOIN prosus.idProducto  pro ");
            jpql.append(" WHERE prosus.estado = :estadoActivo ");
            jpql.append(" AND deta.estado =:estadoActivo ");
            jpql.append(" AND pro.estado =:estadoActivo ");
            if (filtro.getIdSupervision()!= null) {
                jpql.append(" AND sus.idSupervision =:idSupervision ");
            }
            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            //settear parametros
            query.setParameter("estadoActivo", Constantes.ESTADO_ACTIVO);
            if (filtro.getIdSupervision()!= null) {
                query.setParameter("idSupervision", filtro.getIdSupervision());
            }
            retorno = ProductoSuspenderBuilder.toListProductoSuspenderDto(query.getResultList());
        } catch (Exception e) {
            LOG.error("error en getProductoSuspender", e);
        }
        LOG.info("ProductoSuspenderDAOImpl: getProductoSuspender-fin");
        return retorno;
    }
}
