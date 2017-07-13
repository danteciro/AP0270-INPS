/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.builder.ProductoUnidadSupervisadaBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.ProductoUnidadSupervisadaDAO;
import gob.osinergmin.inpsweb.service.exception.InfraccionException;
import gob.osinergmin.inpsweb.service.exception.ProductoUnidadSupervisadaException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.ProductoUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.ui.ProductoUnidadSupervisadaFilter;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author zchaupis
 */
@Repository("productoUnidadSupervisadaDAO")
public class ProductoUnidadSupervisadaDAOImpl implements ProductoUnidadSupervisadaDAO {

    private static final Logger LOG = LoggerFactory.getLogger(ProductoUnidadSupervisadaDAOImpl.class);
    @Inject
    private CrudDAO crud;

    

    @Override
    @Transactional(rollbackFor = InfraccionException.class)
    public List<ProductoUnidadSupervisadaDTO> getProductobyUnidadSupervisada(ProductoUnidadSupervisadaFilter filtro) throws ProductoUnidadSupervisadaException {
      LOG.info("ProductoUnidadSupervisadaDAOImpl:getProductobyUnidadSupervisada-inicio");
		List<ProductoUnidadSupervisadaDTO> retorno=null;
        try{
            String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT new PghProductoXUnidadSup ");
            jpql.append(" ( ");
            jpql.append(" pus.pghProductoXUnidadSupPK.idUnidadSupervisada , ");
            jpql.append(" pus.pghProductoXUnidadSupPK.idProducto, ");            
            jpql.append(" (select pro.idTipoProducto from MdiProducto pro where pro.estado=:estadoActivo and pro.idProducto = pus.pghProductoXUnidadSupPK.idProducto and rownum=1) as tipoProducto ");
            jpql.append(" ) ");
            jpql.append(" FROM PghProductoXUnidadSup pus ");
            jpql.append(" WHERE pus.estado=:estadoActivo ");
            if(filtro.getIdProducto()!=null){
                jpql.append(" and pus.pghProductoXUnidadSupPK.idProducto =:idProducto ");
            }
            if(filtro.getIdUnidadSupervisada()!=null){
            	jpql.append(" and pus.pghProductoXUnidadSupPK.idUnidadSupervisada =:idUnidadSupervisada ");
            }
            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            //settear parametros
            query.setParameter("estadoActivo",Constantes.ESTADO_ACTIVO);
            if(filtro.getIdProducto()!= null){
                query.setParameter("idProducto",filtro.getIdProducto());
            }
            if(filtro.getIdUnidadSupervisada()!= null){
            	query.setParameter("idUnidadSupervisada",filtro.getIdUnidadSupervisada());
            }
            retorno= ProductoUnidadSupervisadaBuilder.toListProductoUnidadSupervisadaDTO(query.getResultList());
        }catch(Exception e){
            LOG.error("error en getProductobyUnidadSupervisada",e);
        }
        LOG.info("ProductoUnidadSupervisadaDAOImpl:getProductobyUnidadSupervisada-fin");
        return retorno;
    }
    
}
