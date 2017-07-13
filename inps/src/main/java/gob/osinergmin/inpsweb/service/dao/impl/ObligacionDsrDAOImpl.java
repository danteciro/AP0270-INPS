/**
 * Resumen Objeto	: ObligacionDsrDAOImpl.java Descripción	: Clase que contiene
 * la implementación de los métodos declarados en la clase interfaz
 * ObligacionDsrDAO Fecha de Creación	: PR de Creación	: OSINE_791 Autor	: Erick
 * Ortiz
 * ---------------------------------------------------------------------------------------------------
 * Modificaciones Motivo Fecha Nombre Descripción
 * ---------------------------------------------------------------------------------------------------
 * OSINE_791_RSIS12 31/08/2016 Zosimo Chaupis Santur Crear la funcionalidad para
 * registrar una obligación como "POR VERIFICAR" en el registro de supervisión
 * de una orden de supervisión DSR-CRITICIDAD
 * OSINE_MAN_DSR_0025     |  19/06/2017   |   Carlos Quijano Chavez::ADAPTER      |     Agregar la Columna Codigo y eliminar prioridad
 */
package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.MdiProducto;
import gob.osinergmin.inpsweb.domain.MdiUnidadSupervisada;
import gob.osinergmin.inpsweb.domain.PghDetalleSupervision;
import gob.osinergmin.inpsweb.domain.PghProductoSuspender;
import gob.osinergmin.inpsweb.domain.PghResultadoSupervision;
import gob.osinergmin.inpsweb.domain.builder.ObligacionDsrBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.ObligacionDsrDAO;
import gob.osinergmin.inpsweb.service.dao.ResultadoSupervisionDAO;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.ObligacionDsrDTO;
import gob.osinergmin.mdicommon.domain.dto.ProductoDsrScopDTO;
import gob.osinergmin.mdicommon.domain.dto.ResultadoSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.ObligacionDsrFilter;
import gob.osinergmin.mdicommon.domain.ui.ResultadoSupervisionFilter;

import java.util.Date;

import javax.persistence.Query;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;
/* OSINE_MAN_DSR_0025 - Inicio */
import gob.osinergmin.inpsweb.dto.ObligacionDsrUpdtDTO;
/* OSINE_MAN_DSR_0025 - Fin */

@Repository("ObligacionDsrDAO")
public class ObligacionDsrDAOImpl implements ObligacionDsrDAO {

    private static final Logger LOG = LoggerFactory.getLogger(ObligacionDsrDAOImpl.class);
    @Inject
    private CrudDAO crud;
    @Inject
    private ResultadoSupervisionDAO resultadoSupervisionDAO;

    /* OSINE_MAN_DSR_0025 - Inicio */
    @Override
    public List<ObligacionDsrUpdtDTO> findUpdt(ObligacionDsrFilter filtro) {
        LOG.info("find");
        List<ObligacionDsrUpdtDTO> milista = new ArrayList<ObligacionDsrUpdtDTO>();
        try {
            String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT distinct new PghDetalleSupervision ");
            jpql.append(" ( ");
            jpql.append(" deta.idDetalleSupervision , infra.descripcionInfraccion , ");
            jpql.append(" deta.idResultadoSupervision.cssIcono ,deta.idResultadoSupervision.descripcion, ");
            jpql.append(" deta.prioridad , deta.idResultadoSupervision.codigo ,");
            jpql.append(" deta.idDetalleSupervisionAnt , sup.idOrdenServicio.iteracion ,deta.idInfraccion.codigo ");
            jpql.append(" ) ");
            jpql.append(" from PghDetalleSupervision deta , "
                    + "PghSupervision sup , "
                    + "PghObligacion obli, "
                    + "PghInfraccion infra ");
            jpql.append(" where sup.idSupervision = deta.idSupervision  and obli.idObligacion = deta.idObligacion ");
            jpql.append(" and infra.idObligacion = obli.idObligacion and sup.idSupervision = :idSuper ");
            jpql.append(" and deta.estado = :idEstado ");
            jpql.append(" and sup.estado = :idEstado ");
            jpql.append(" and obli.estado = :idEstado ");
            jpql.append(" and infra.estado = :idEstado ");
            jpql.append(" order by deta.idInfraccion.codigo asc ");
            
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            query.setParameter("idSuper", filtro.getIdSupervision());
            query.setParameter("idEstado", Constantes.ESTADO_ACTIVO);
            List<PghDetalleSupervision> lista = query.getResultList();
            milista = ObligacionDsrBuilder.getObligacionDsrUpdtDTO(lista);
        } catch (Exception ex) {
            LOG.info("error en find",ex);
        }
        return milista;
    }
    /* OSINE_MAN_DSR_0025 - Fin */
	/* OSINE791 – RSIS7 - Inicio */
    @Override
    public List<ObligacionDsrDTO> find(ObligacionDsrFilter filtro) {
        LOG.info("find");
        List<ObligacionDsrDTO> milista = new ArrayList<ObligacionDsrDTO>();
        try {
            String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT distinct new PghDetalleSupervision ");
            jpql.append(" ( ");
            jpql.append(" deta.idDetalleSupervision , infra.descripcionInfraccion , ");
            jpql.append(" deta.idResultadoSupervision.cssIcono ,deta.idResultadoSupervision.descripcion, ");
            jpql.append(" deta.prioridad , deta.idResultadoSupervision.codigo,");
            jpql.append(" deta.idDetalleSupervisionAnt , sup.idOrdenServicio.iteracion ");
            jpql.append(" ) ");
            jpql.append(" from PghDetalleSupervision deta , "
                    + "PghSupervision sup , "
                    + "PghObligacion obli, "
                    + "PghInfraccion infra ");
            jpql.append(" where sup.idSupervision = deta.idSupervision  and obli.idObligacion = deta.idObligacion ");
            jpql.append(" and infra.idObligacion = obli.idObligacion and sup.idSupervision = :idSuper ");
            jpql.append(" and deta.estado = :idEstado ");
            jpql.append(" and sup.estado = :idEstado ");
            jpql.append(" and obli.estado = :idEstado ");
            jpql.append(" and infra.estado = :idEstado ");
            jpql.append(" order by deta.prioridad asc ");
            
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            query.setParameter("idSuper", filtro.getIdSupervision());
            query.setParameter("idEstado", Constantes.ESTADO_ACTIVO);
            List<PghDetalleSupervision> lista = query.getResultList();
            milista = ObligacionDsrBuilder.getObligacionDsrDTO(lista);
        } catch (Exception ex) {
            LOG.info("error en find",ex);
        }
        return milista;
    }
    /* OSINE791 – RSIS7 - Fin */

    @Override
    public List<ProductoDsrScopDTO> findPdtos(ObligacionDsrFilter filtro) {
        LOG.info("findPdtos");
        List<ProductoDsrScopDTO> lsita1 = new ArrayList<ProductoDsrScopDTO>();
        try{
            Query q11 = crud.getEm().createNamedQuery(PghDetalleSupervision.BUSCAR_POR_ID);
            q11.setParameter("idDetalleSupervision", filtro.getIdDetalleSupervision());
            q11.setParameter("estado", Constantes.ESTADO_ACTIVO);

            PghDetalleSupervision ds1 = (PghDetalleSupervision) q11.getSingleResult();   

            //consultando codigo de unidad supervisada
            MdiUnidadSupervisada unidadDomain = ds1.getIdSupervision().getIdOrdenServicio().getIdExpediente().getIdUnidadSupervisada();
            String codigoOsinergmin = unidadDomain.getCodigoOsinergmin();
            LOG.info("codigoOsinergmin-->"+codigoOsinergmin);
            String sql = "select "
                    + "u.codigo_osinerg codigo_osinergmin, "
                    + "p.codigo codigo_producto, "
                    + "mp.nombre_largo nombre_producto "
                    +", mp.id_producto, "
                    +" NVL((SELECT ps.id_producto_suspender "
                    +"       FROM pgh_producto_suspender ps "
                    +"        WHERE ps.id_producto =mp.id_producto "
                    +"       AND ps.estado=1 "
                    +"       AND ps.id_detalle_supervision = :idDetalleSupervision "
                    +"       ),0) st_pro_sus "
                    + "from "
                    + "prod_unop_puo pu, "
                    + "sfh_prdctos p, "
                    + "sfh_unddes_oprtvas u, "
                    + "mdi_producto mp "
                    + "where 1 = 1 "
                    + "and pu.prod_id = p.id "
                    + "and pu.uniope_id = u.id "
                    + "and mp.codigo = p.codigo "
                    + "and u.codigo_osinerg = :codOsi1 "
                    + "and p.activo = 'SI' "
                    + "and mp.estado = 1";
            Query q = crud.getEm().createNativeQuery(sql);
            //q.setParameter("codOsi1", "38192");
            q.setParameter("codOsi1", codigoOsinergmin);
            q.setParameter("idDetalleSupervision", filtro.getIdDetalleSupervision());
            List<Object[]> lres = q.getResultList();
            ProductoDsrScopDTO es = null;
            for (Object[] fila : lres) {
                es = new ProductoDsrScopDTO();
                es.setProducto(fila[2] != null ? fila[2].toString() : "");
                es.setSuspender("1");
                es.setIdProducto(fila[3]!=null?((BigDecimal)fila[3]).longValue():null);
                es.setIdProductoSuspender(fila[4]!=null?((BigDecimal)fila[4]).longValue():null);
                lsita1.add(es);
            }
        }catch(Exception e){
            LOG.error("Error en findPdtos",e);
        }
        return lsita1;
    }

    public ObligacionDsrDTO findByPrioridad(Long idSupervision, Long prioridad) {
        ObligacionDsrDTO obl = new ObligacionDsrDTO();

        Query q11 = crud.getEm().createNamedQuery(PghDetalleSupervision.BUSCAR_POR_PRIORIDAD);
        q11.setParameter("idSupervision", idSupervision);
        q11.setParameter("estado", Constantes.ESTADO_ACTIVO);
        q11.setParameter("prioridad", prioridad);
        PghDetalleSupervision ds1 = (PghDetalleSupervision) q11.getSingleResult();

        obl = ObligacionDsrBuilder.toObligacionDsrDTO(ds1);

        return obl;
    }
    
    public ObligacionDsrDTO guardarDsrObligacion(ObligacionDsrDTO obligacionDstDTO, UsuarioDTO usuarioDto) {
        ObligacionDsrDTO oblDTO = null;
        Query q11 = crud.getEm().createNamedQuery(PghDetalleSupervision.BUSCAR_POR_ID);
        q11.setParameter("idDetalleSupervision", obligacionDstDTO.getIdDetalleSupervision());
        q11.setParameter("estado", Constantes.ESTADO_ACTIVO);
        PghDetalleSupervision ds1 = (PghDetalleSupervision) q11.getSingleResult();  
        
        PghResultadoSupervision pghResDet = new PghResultadoSupervision();
        pghResDet.setIdResultadoSupervision(obligacionDstDTO.getEstadoIncumplimiento());    
        ds1.setIdResultadoSupervision(pghResDet);
        
        PghResultadoSupervision pghResDetActual = new PghResultadoSupervision();
        pghResDetActual.setIdResultadoSupervision(obligacionDstDTO.getIdResultadoSupervisionAct());
        ds1.setIdResultadoSupervisionAnt(pghResDetActual);
        if(obligacionDstDTO.getIdMedidaSeguridad() != null){
            ds1.setIdMedidaSeguridad(obligacionDstDTO.getIdMedidaSeguridad());
        }
        ds1.setDatosAuditoria(usuarioDto);
        ds1.setComentario(obligacionDstDTO.getComentarioObstaculizado());
        ResultadoSupervisionFilter filtroEstado = new ResultadoSupervisionFilter();
        filtroEstado.setCodigo(Constantes.CODIGO_RESULTADO_PORVERIFICAR);
        ResultadoSupervisionDTO objetoEstado = resultadoSupervisionDAO.getResultadoSupervision(filtroEstado);
        if (obligacionDstDTO.getEstadoIncumplimiento() == objetoEstado.getIdResultadosupervision()) {
            ds1.setFechaInicioPorVerificar(new Date());
        }else{
            ds1.setFechaInicioPorVerificar(null);
        }
        ds1 = crud.update(ds1);
        oblDTO = ObligacionDsrBuilder.toObligacionDsrDTO(ds1);
        return oblDTO;
    }

    public ProductoDsrScopDTO guardarDsrProductoSuspender(ProductoDsrScopDTO productoDsrScopDTO, UsuarioDTO usuarioDto){
        ProductoDsrScopDTO producto = null;
        PghProductoSuspender pghProductoSuspender = new PghProductoSuspender();
        
        pghProductoSuspender.setIdProducto(new MdiProducto(productoDsrScopDTO.getIdProducto()));
        pghProductoSuspender.setIdDetalleSupervision(new PghDetalleSupervision(productoDsrScopDTO.getIdDetalleSupervision()));
        pghProductoSuspender.setEstado("1");
        pghProductoSuspender.setDatosAuditoria(usuarioDto);
        pghProductoSuspender = crud.create(pghProductoSuspender);
        producto = ObligacionDsrBuilder.toProductoDsrDTO(pghProductoSuspender);
        
        return producto;
    }
    
    public ProductoDsrScopDTO eliminarDsrProductoSuspender(ProductoDsrScopDTO productoDsrScopDTO, UsuarioDTO usuarioDto){
        ProductoDsrScopDTO producto = null;
        PghProductoSuspender pghProductoSuspender = crud.find(productoDsrScopDTO.getIdProductoSuspender(), PghProductoSuspender.class);
        crud.delete(pghProductoSuspender);
        
        return producto;
    }
    
    /* OSINE791 – RSIS12 - Inicio */
    @Override
    @Transactional
    public String VerificarObstaculizados(ObligacionDsrDTO filtro) {
        ResultadoSupervisionFilter filtroEstadoObstaculizado = new ResultadoSupervisionFilter();
        filtroEstadoObstaculizado.setCodigo(Constantes.CODIGO_RESULTADO_OBSTACULIZADO);
        ResultadoSupervisionDTO objetoEstadoObstaculizado = resultadoSupervisionDAO.getResultadoSupervision(filtroEstadoObstaculizado);
            
        ResultadoSupervisionFilter filtroEstadoPorverificar = new ResultadoSupervisionFilter();
        filtroEstadoPorverificar.setCodigo(Constantes.CODIGO_RESULTADO_PORVERIFICAR);
        ResultadoSupervisionDTO objetoEstadoPorverificar = resultadoSupervisionDAO.getResultadoSupervision(filtroEstadoPorverificar);
         
        String rpta = "";
        try {
            StringBuilder jpql = new StringBuilder();
            jpql.append(" update pgh_detalle_supervision deta ");
            jpql.append(" set deta.id_resultado_supervision = :idEstadoObstaculizado ");
            jpql.append(" where deta.id_supervision = :idSuperv and ");
            jpql.append(" deta.id_detalle_supervision in ( select distinct ( ds.id_detalle_supervision )  ");
            jpql.append(" from pgh_detalle_supervision ds , pgh_plazo pla ");
            jpql.append(" where pla.codigo_plazo = :codPlazo and  ((select sysdate from dual) - ds.fecha_inicio_porverificar)*24*60 > (pla.cantidad*60)  ");
            jpql.append(" and ds.id_resultado_supervision = :idEstadoPorVerificar and ds.estado = :estadoActivo  ");
            jpql.append(" and ds.id_supervision = :idSupervisi ) ");
            String queryString = jpql.toString();
            Query q = crud.getEm().createNativeQuery(queryString);
            q.setParameter("idEstadoObstaculizado", objetoEstadoObstaculizado.getIdResultadosupervision());
            q.setParameter("idSuperv", filtro.getIdSupervision());
            q.setParameter("idEstadoPorVerificar",objetoEstadoPorverificar.getIdResultadosupervision());
            q.setParameter("codPlazo", Constantes.CODIGO_PLAZO_INICIAL_POR_VERIFICAR_OBLIGACION);
            q.setParameter("estadoActivo", Constantes.ESTADO_ACTIVO);
            q.setParameter("idSupervisi", filtro.getIdSupervision());
            int val = q.executeUpdate();
            LOG.info("Se Actualizaron " + val+" Registros");
            rpta = "ok";
        } catch (Exception ex) {
            LOG.info("Ocurrio un error " + ex.getMessage());
            LOG.error("Error en VerificarObstaculizados DAO imple", ex);
            rpta = "error";
        }
        return rpta;
    }
    /* OSINE791 – RSIS12 - Fin */
    @Override
    public ObligacionDsrDTO editarDsrComentarioSubsanacion(ObligacionDsrDTO obligacionDstDTO, UsuarioDTO usuarioDto) {
        ObligacionDsrDTO oblDTO = null;
        try{
            PghDetalleSupervision reg=crud.find(obligacionDstDTO.getIdDetalleSupervision(), PghDetalleSupervision.class);
            reg.setDatosAuditoria(usuarioDto);
            reg.setComentario(obligacionDstDTO.getComentarioObstaculizado());
            reg = crud.update(reg);
            oblDTO = ObligacionDsrBuilder.toObligacionDsrDTO(reg);
        }catch(Exception e){
            LOG.error("Error en editarDsrComentarioSubsanacion",e);
        }
        return oblDTO;
    }
}
