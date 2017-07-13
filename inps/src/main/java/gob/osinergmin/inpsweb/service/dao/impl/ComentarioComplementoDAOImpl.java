/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.builder.ComentarioComplementoBuilder;
import gob.osinergmin.inpsweb.service.dao.ComentarioComplementoDAO;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.MaestroColumnaDAO;
import gob.osinergmin.inpsweb.service.exception.ComentarioComplementoException;
import gob.osinergmin.mdicommon.domain.dto.ComentarioComplementoDTO;
import gob.osinergmin.mdicommon.domain.dto.ComplementoDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.ui.ComentarioComplementoFilter;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author jcsar
 */
@Service("comentarioComplementoDAO")
public class ComentarioComplementoDAOImpl implements ComentarioComplementoDAO{
    private static final Logger LOG = LoggerFactory.getLogger(ComentarioComplementoDAOImpl.class);
    @Inject
    private CrudDAO crud;   
    @Inject
    private MaestroColumnaDAO maestroColumnaDAO;
    
    @Override
    public List<ComentarioComplementoDTO> getLstComentarioComplemento(ComentarioComplementoFilter filtro) throws ComentarioComplementoException{
        LOG.info("getLstComentarioComplemento");
        List<ComentarioComplementoDTO> retorno=null;
        try{
            String queryString;
            StringBuilder jpql = new StringBuilder();
            //arma campos
            jpql.append("select new PghComentarioComplemento(coco.idComentarioComplemento,co.idComplemento,co.descripcion,co.etiqueta,co.etiquetaComentario,"
                    + "co.validacion,coct.idMaestroColumna,coct.descripcion,coct.codigo,co.formato,co.longitud,co.valorMaximo,co.valorMinimo,cood.idMaestroColumna,"
                    + "cood.descripcion,cood.codigo,co.dominioMaestroColumna,co.aplicacionMaestroColumna,co.vistaNombre,co.vistaCampoId,co.vistaCampoDesc,"
                    + "covcf.idMaestroColumna,covcf.descripcion,covcf.codigo,covcf.aplicacion) "
                    + "from PghComentarioComplemento coco "
                    + "left join coco.idComplemento co "
                    + "left join co.codTipo coct "
                    + "left join co.origenDatos cood "
                    + "left join co.vistaCampoFiltro covcf "
                    + "left join coco.idComentarioIncumplimiento coinc ");
            //arma where
            jpql.append("WHERE co.estado=1 and coco.estado=1 and co.estado=1 ");
            if(filtro.getIdComentarioIncumplimiento()!=null && !filtro.getIdComentarioIncumplimiento().equals("")){
                jpql.append("and coinc.idComentarioIncumplimiento=:idComentarioIncumplimiento ");
            }
            
            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            
            //settear parametros
            if(filtro.getIdComentarioIncumplimiento()!=null && !filtro.getIdComentarioIncumplimiento().equals("")){
                query.setParameter("idComentarioIncumplimiento",filtro.getIdComentarioIncumplimiento());
            }
            
            retorno= ComentarioComplementoBuilder.toListComentarioComplementoDto(query.getResultList());
        }catch(Exception e){
            LOG.error("getLstComentarioComplemento",e);
        }
        return retorno;
    }
    
    @Override
    public List<ComplementoDTO.OpcionDTO> getOpcionVistaComplemento(ComplementoDTO complemento,ComentarioComplementoFilter filtro) throws ComentarioComplementoException{
        LOG.info("getOpcionVistaComplemento");
        List<ComplementoDTO.OpcionDTO> listOpcion = new ArrayList<ComplementoDTO.OpcionDTO>();
        try{
            String queryString;
            StringBuilder jpql = new StringBuilder();
            //arma campos
            jpql.append("SELECT "+complemento.getVistaCampoId()+","+complemento.getVistaCampoDesc());
            jpql.append(" from "+complemento.getVistaNombre());
            jpql.append(" WHERE 1=1 ");
            
            if(complemento.getVistaCampoFiltro()!=null && complemento.getVistaCampoFiltro().getCodigo()!=null && complemento.getVistaCampoFiltro().getAplicacion()!=null){
                List<MaestroColumnaDTO> lstFiltro=maestroColumnaDAO.findMaestroColumna(complemento.getVistaCampoFiltro().getCodigo(), complemento.getVistaCampoFiltro().getAplicacion());
                if(!CollectionUtils.isEmpty(lstFiltro)){
                    for(MaestroColumnaDTO reg : lstFiltro){
                        if(reg.getDescripcion()!=null && !reg.getDescripcion().equals("")){
                            String campoFiltro=reg.getDescripcion();
                            
                            char[] caracteres = campoFiltro.toCharArray();
                            caracteres[0] = Character.toUpperCase(caracteres[0]);
                            Method m = filtro.getClass().getMethod("get"+(new String(caracteres)));
                            Object valorFiltro=m.invoke(filtro);
                            jpql.append(" and "+campoFiltro+"='"+valorFiltro+"' ");
                        }
                    }
                }
            }
            //Crear QUERY
            queryString = jpql.toString();
            Query query = crud.getEm().createNativeQuery(queryString);
            List<Object[]> lres = query.getResultList();
            for (Object[] reg : lres) {
                if(reg[0] != null && reg[1] != null){
                    ComplementoDTO comp=new ComplementoDTO();
                    ComplementoDTO.OpcionDTO opcion = comp.new OpcionDTO();
                    opcion.setId(((BigDecimal)reg[0]).longValue());
                    opcion.setDesc(reg[1].toString());
                    listOpcion.add(opcion);
                }
            }            
        }catch(Exception e){
            LOG.error("getOpcionVistaComplemento",e);
            throw new ComentarioComplementoException("Error al obtener opciones de un atributo.", e);
        }
        return listOpcion;
    }
}
