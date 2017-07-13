/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.PghResultadoSupervision;
import gob.osinergmin.inpsweb.domain.builder.ResultadoSupervisionBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.ResultadoSupervisionDAO;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.ResultadoSupervisionDTO;
import gob.osinergmin.mdicommon.domain.ui.ResultadoSupervisionFilter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 *
 * @author zchaupis
 */
@Service("ResultadoSupervisionDAO")
public class ResultadoSupervisionDAOImpl implements ResultadoSupervisionDAO {

    private static final Logger LOG = LoggerFactory.getLogger(ResultadoSupervisionDAOImpl.class);
    @Inject
    private CrudDAO crud;

    @Override
    public List<ResultadoSupervisionDTO> listarResultadoSupervision(ResultadoSupervisionFilter filtro) {
        List<ResultadoSupervisionDTO> milista = new ArrayList<ResultadoSupervisionDTO>();
        try {
            String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT distinct new PghResultadoSupervision ");
            jpql.append(" ( ");
            jpql.append(" res.idResultadoSupervision , ");
            jpql.append(" res.descripcion , ");
            jpql.append(" res.cssIcono , ");
            jpql.append(" res.estado , ");
            jpql.append(" res.codigo ");
            jpql.append(" ) ");
            jpql.append(" from PghResultadoSupervision res  ");
            jpql.append(" where res.estado = :estadoResultado ");

            if (filtro.getIdResultadosupervision() != null) {
                jpql.append(" AND res.idResultadoSupervision=:idResultadoSupervision ");
            }
            if (filtro.getDescripcion() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getDescripcion().trim())) {
                jpql.append(" AND  UPPER(res.descripcion) LIKE UPPER(:descripcion) ");
            }
            if (filtro.getCssIcono() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getCssIcono().trim())) {
                jpql.append(" AND  UPPER(res.cssIcono) LIKE UPPER(:cssicono) ");
            }
            if (filtro.getCodigo() != null && filtro.getCodigo() != -1) {
                jpql.append(" AND res.codigo=:codigo ");
            }
            jpql.append(" order by res.codigo asc ");

            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            //Parametros
            query.setParameter("estadoResultado", Constantes.ESTADO_ACTIVO);

            if (filtro.getIdResultadosupervision() != null) {
                query.setParameter("idResultadoSupervision", filtro.getIdResultadosupervision());
            }

            if (filtro.getDescripcion() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getDescripcion().trim())) {
                query.setParameter("descripcion", "%" + filtro.getDescripcion() + "%");
            }
            if (filtro.getCssIcono() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getCssIcono().trim())) {
                query.setParameter("cssicono", "%" + filtro.getCssIcono() + "%");
            }
            if (filtro.getCodigo() != null && filtro.getCodigo() != -1) {
                query.setParameter("codigo", filtro.getCodigo());
            }

            List<PghResultadoSupervision> lista = query.getResultList();
            milista = ResultadoSupervisionBuilder.toListResultadoSupervisionDto(lista);
        } catch (Exception ex) {
            LOG.info("error es : " + ex.getMessage());
        }
        return milista;

    }

    @Override
    public ResultadoSupervisionDTO getResultadoSupervision(ResultadoSupervisionFilter filtro) {
    ResultadoSupervisionDTO objeto = new ResultadoSupervisionDTO();
        try {
            String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT distinct new PghResultadoSupervision ");
            jpql.append(" ( ");
            jpql.append(" res.idResultadoSupervision , ");
            jpql.append(" res.descripcion , ");
            jpql.append(" res.cssIcono , ");
            jpql.append(" res.estado , ");
            jpql.append(" res.codigo ");
            jpql.append(" ) ");
            jpql.append(" from PghResultadoSupervision res  ");
            jpql.append(" where res.estado = :estadoResultado ");

            if (filtro.getIdResultadosupervision() != null) {
                jpql.append(" AND res.idResultadoSupervision=:idResultadoSupervision ");
            }
            if (filtro.getDescripcion() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getDescripcion().trim())) {
                jpql.append(" AND  UPPER(res.descripcion) LIKE UPPER(:descripcion) ");
            }
            if (filtro.getCssIcono() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getCssIcono().trim())) {
                jpql.append(" AND  UPPER(res.cssIcono) LIKE UPPER(:cssicono) ");
            }
            if (filtro.getCodigo() != null && filtro.getCodigo() != -1) {
                jpql.append(" AND res.codigo=:codigo ");
            }
            jpql.append(" order by res.codigo asc ");

            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            //Parametros
            query.setParameter("estadoResultado", Constantes.ESTADO_ACTIVO);

            if (filtro.getIdResultadosupervision() != null) {
                query.setParameter("idResultadoSupervision", filtro.getIdResultadosupervision());
            }

            if (filtro.getDescripcion() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getDescripcion().trim())) {
                query.setParameter("descripcion", "%" + filtro.getDescripcion() + "%");
            }
            if (filtro.getCssIcono() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getCssIcono().trim())) {
                query.setParameter("cssicono", "%" + filtro.getCssIcono() + "%");
            }
            if (filtro.getCodigo() != null && filtro.getCodigo() != -1) {
                query.setParameter("codigo", filtro.getCodigo());
            }

            PghResultadoSupervision reg = (PghResultadoSupervision) query.getSingleResult();
            objeto = ResultadoSupervisionBuilder.toResultadoSupervisionDTO(reg);
        } catch (Exception ex) {
            LOG.info("error es : " + ex.getMessage());
        }
        return objeto;
    }
}
