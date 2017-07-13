/**
* Resumen		
* Objeto            : PghDocumentoAdjuntoDAOImpl.java
* Descripción       : Clase que contiene la implementación de los métodos declarados en la clase interfaz PghDocumentoAdjuntoDAO
* Fecha de Creación : 06/10/2016
* PR de Creación    : OSINE_SFS-791
* Autor             : GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha            Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-791  |  06/10/2016   |   Luis García Reyna          | Registrar Supervisión No Iniciada
*    
*/
package gob.osinergmin.inpsweb.service.dao.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import gob.osinergmin.inpsweb.domain.PghDocumentoAdjunto;
import gob.osinergmin.inpsweb.domain.builder.PghDocumentoAdjuntoBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.PghDocumentoAdjuntoDAO;
import gob.osinergmin.inpsweb.service.exception.DocumentoAdjuntoException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.DocumentoAdjuntoFilter;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang.StringUtils;

@Service("pghDocumentoAdjuntoDAO")
public class PghDocumentoAdjuntoDAOImpl implements PghDocumentoAdjuntoDAO {

    private static final Logger LOG = LoggerFactory.getLogger(PghDocumentoAdjuntoDAOImpl.class);
    @Inject
    private CrudDAO crud;

    @Override
    public DocumentoAdjuntoDTO registrar(DocumentoAdjuntoDTO documentoAdjuntoDTO, UsuarioDTO usuarioDTO)
            throws DocumentoAdjuntoException {
        LOG.info("DocumentoAdjuntoDAOImpl : registrar-inicio");
        DocumentoAdjuntoDTO retorno = null;
        try {
            PghDocumentoAdjunto documentoAdjunto = PghDocumentoAdjuntoBuilder.toDocumentoAdjuntoDomain(documentoAdjuntoDTO);
            documentoAdjunto.setDatosAuditoria(usuarioDTO);
            documentoAdjunto.setEstado(Constantes.ESTADO_ACTIVO);
            crud.create(documentoAdjunto);
            retorno = PghDocumentoAdjuntoBuilder.toDocumentoAdjuntoDTO(documentoAdjunto);
        } catch (Exception e) {
            LOG.error("Error en registrar", e);
            DocumentoAdjuntoException ex = new DocumentoAdjuntoException(e.getMessage(), e);
            throw ex;
        }
        LOG.info("DocumentoAdjuntoDAOImpl : registrar-fin");
        return retorno;
    }

    @Override
    public List<DocumentoAdjuntoDTO> find(DocumentoAdjuntoFilter filtro) throws DocumentoAdjuntoException {
        LOG.info("DocumentoAdjuntoDAOImpl: find-inicio");
        List<DocumentoAdjuntoDTO> retorno = null;
        try {
            String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT new  PghDocumentoAdjunto(");
            jpql.append(" da.idDocumentoAdjunto,da.descripcionDocumento,da.nombreDocumento, ");
            jpql.append(" td.idMaestroColumna,td.descripcion,da.fechaCreacion,td.codigo ) ");
            jpql.append(" FROM PghDocumentoAdjunto da ");
            jpql.append(" LEFT JOIN da.idTipoDocumento td ");
            jpql.append(" WHERE da.estado=1 ");
            if (filtro.getIdDetalleSupervision() != null) {
                jpql.append(" AND da.idDetalleSupervision.idDetalleSupervision=:idDetalleSupervision ");
            }else if(!StringUtils.isEmpty(filtro.getConcatIdDetSup())){
                jpql.append(" AND da.idDetalleSupervision.idDetalleSupervision in ("+filtro.getConcatIdDetSup()+") ");
            }
            if (filtro.getIdSupervision() != null) {
                jpql.append(" AND da.idSupervision.idSupervision=:idSupervision ");
            }
            if (filtro.getIdOrdenServicio() != null) {
                jpql.append(" AND da.idOrdenServicio.idOrdenServicio=:idOrdenServicio ");
            }
            if (filtro.getDescripcionDocumento() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getDescripcionDocumento().trim())) {
                jpql.append(" AND da.descripcionDocumento=:descripcionDocumento ");
            }
            if (filtro.getNombreDocumento() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getNombreDocumento().trim())) {
                jpql.append(" AND upper(da.nombreDocumento)=:nombreDocumento ");
            }
            /* OSINE791 - RSIS17 - Inicio */
            if(filtro.getIdTipoDocumento()!=null){
                jpql.append(" AND da.idTipoDocumento.idMaestroColumna=:idTipoDocumento ");
            }
            /* OSINE791 - RSIS17 - Fin */
            /* OSINE_SFS-791 - RSIS 33 - Inicio */ 
            if(filtro.getIdDetalleLevantamiento()!=null){
            	jpql.append(" AND da.idDetalleLevantamiento.idDetalleLevantamiento=:idDetalleLevantamiento ");
            }
            /* OSINE_SFS-791 - RSIS 33 - Fin */ 
            /* OSINE_SFS-791 - RSIS 33 - Inicio */ 
            if(filtro.getCodigoTipoDocumento()!=null){
                jpql.append(" AND (da.idTipoDocumento.idMaestroColumna <> (Select mc.idMaestroColumna "
                + "from MdiMaestroColumna "
                + "mc where mc.codigo=:codigoTipoDocumento) "
                + "or da.idTipoDocumento.idMaestroColumna is null) ");
            }
            /* OSINE_SFS-791 - RSIS 33 - Fin */ 
            /* OSINE-791 - RSIS 66 - Inicio */ 
            if (filtro.getFlagSoloAdjuntos() != null) {
                if (filtro.getFlagSoloAdjuntos().equals(Constantes.ESTADO_ACTIVO)) {
                    LOG.info("entro");
                    jpql.append(" AND da.idTipoDocumento is null ");
                }
            }
            
            /* OSINE-791 - RSIS 66 - Fin */ 
            jpql.append(" ORDER BY da.idDocumentoAdjunto DESC ");
            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            //settear parametros
            if (filtro.getIdDetalleSupervision() != null) {
                query.setParameter("idDetalleSupervision", filtro.getIdDetalleSupervision());
            }
            if (filtro.getIdSupervision() != null) {
                query.setParameter("idSupervision", filtro.getIdSupervision());
            }
            if (filtro.getIdOrdenServicio() != null) {
                query.setParameter("idOrdenServicio", filtro.getIdOrdenServicio());
            }
            if (filtro.getDescripcionDocumento() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getDescripcionDocumento().trim())) {
                query.setParameter("descripcionDocumento", filtro.getDescripcionDocumento());
            }
            if (filtro.getNombreDocumento() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getNombreDocumento().trim())) {
                query.setParameter("nombreDocumento", filtro.getNombreDocumento().toUpperCase());
            }
            /* OSINE791 - RSIS17 - Inicio */
            if(filtro.getIdTipoDocumento()!=null){
                query.setParameter("idTipoDocumento",filtro.getIdTipoDocumento());
            }
            /* OSINE791 - RSIS17 - Fin */
            /* OSINE_SFS-791 - RSIS 33 - Inicio */ 
            if(filtro.getIdDetalleLevantamiento()!=null){
            	query.setParameter("idDetalleLevantamiento",filtro.getIdDetalleLevantamiento());            	
            }
            /* OSINE_SFS-791 - RSIS 33 - Fin */ 
             /* OSINE_SFS-791 - RSIS 03 - Inicio */ 
            if(filtro.getCodigoTipoDocumento()!=null){
            	query.setParameter("codigoTipoDocumento",filtro.getCodigoTipoDocumento());            	
            }
            /* OSINE_SFS-791 - RSIS 03 - Fin */ 
            retorno = PghDocumentoAdjuntoBuilder.toListDocumentoAdjuntoDTO(query.getResultList());
        } catch (Exception e) {
            LOG.error("error en find", e);
        }
        LOG.info("DocumentoAdjuntoDAOImpl: find-fin");
        return retorno;
    }

    @Override
    public List<DocumentoAdjuntoDTO> getDocumentoAdjunto(DocumentoAdjuntoFilter filtro) throws DocumentoAdjuntoException {
        LOG.info("DocumentoAdjuntoDAOImpl: find-inicio");
        List<DocumentoAdjuntoDTO> retorno = null;
        try {
            String queryString;
            StringBuilder jpql = new StringBuilder();
            //jpql.append("SELECT da.idDocumentoAdjunto,da.archivoAdjunto ");
            jpql.append("SELECT da ");
            jpql.append(" FROM PghDocumentoAdjunto da ");
            jpql.append(" WHERE da.estado=1 ");
            if (filtro.getIdDocumentoAdjunto() != null) {
                jpql.append(" AND da.idDocumentoAdjunto=");
                jpql.append(filtro.getIdDocumentoAdjunto());
            }
            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            //retorno=(List<DocumentoAdjuntoDTO>)query.getResultList();
            //retorno=DocumentoAdjuntoBuilder.;
            retorno = PghDocumentoAdjuntoBuilder.toListDocumentoAdjuntoDTO(query.getResultList());
            //settear parametros
//            if(filtro.getIdDetalleSupervision()!=null){
//            	query.setParameter("idDetalleSupervision",filtro.getIdDetalleSupervision());
//            }

        } catch (Exception e) {
            LOG.error("error en find", e);
        }
        LOG.info("DocumentoAdjuntoDAOImpl: find-fin");
        return retorno;
    }

    @Override
    public void eliminar(DocumentoAdjuntoDTO documentoAdjuntoDTO) throws DocumentoAdjuntoException {
        LOG.info("DocumentoAdjuntoDAOImpl : eliminar-inicio");
        try {
            PghDocumentoAdjunto documentoAdjunto = crud.find(documentoAdjuntoDTO.getIdDocumentoAdjunto(), PghDocumentoAdjunto.class);
            crud.delete(documentoAdjunto);
        } catch (Exception e) {
            LOG.error("Error en eliminar", e);
            DocumentoAdjuntoException ex = new DocumentoAdjuntoException(e.getMessage(), e);
            throw ex;
        }
        LOG.info("DocumentoAdjuntoDAOImpl : eliminar-fin");
    }

    @Override
    public byte[] findBlobPghDocumento(Long idDocumentoAdjunto) throws DocumentoAdjuntoException {
        LOG.error("findBlobPghDocumento");
        byte[] retorno = null;
        try {
            PghDocumentoAdjunto doc = crud.find(idDocumentoAdjunto, PghDocumentoAdjunto.class);
            retorno = doc.getArchivoAdjunto();
        } catch (Exception e) {
            LOG.error("Error en findBlobPghDocumento", e);
        }
        return retorno;
    }

    @Override
    public int ActualizarEstadoPghDocumentoAdjunto(DocumentoAdjuntoDTO documentoAdjuntoDTO, UsuarioDTO usuarioDTO) throws DocumentoAdjuntoException {
        LOG.info("DocumentoAdjuntoDAOImpl : ActualizarEstadoPghDocumentoAdjunto-inicio");
        int resultado = 1;
        try {
            PghDocumentoAdjunto documentoAdjunto = crud.find(documentoAdjuntoDTO.getIdDocumentoAdjunto(), PghDocumentoAdjunto.class);
            documentoAdjunto.setEstado(Constantes.ESTADO_INACTIVO);
            documentoAdjunto.setDatosAuditoria(usuarioDTO);
            crud.update(documentoAdjunto);
            resultado = 1;
        } catch (Exception e) {
            resultado = -1;
            LOG.error("Error en ActualizarEstadoPghDocumentoAdjunto", e);
            DocumentoAdjuntoException ex = new DocumentoAdjuntoException(e.getMessage(), e);
            throw ex;
        }
        LOG.info("DocumentoAdjuntoDAOImpl : ActualizarEstadoPghDocumentoAdjunto-fin");
        return resultado;
    }

    @Override
    @Transactional
    public String eliminarPghDocumentoAdjuntobyDetalleSupervision(DetalleSupervisionDTO detalleSupervisionDTO) throws DocumentoAdjuntoException {
        LOG.info("DocumentoAdjuntoDAOImpl : eliminarPghDocumentoAdjuntobyDetalleSupervision-inicio");
        String rpta = "";
        try {
            StringBuilder jpql = new StringBuilder();
            jpql.append(" delete pgh_documento_adjunto docu ");
            jpql.append(" where docu.id_detalle_supervision = :idDetalleSupervision  ");
            String queryString = jpql.toString();
            Query q = crud.getEm().createNativeQuery(queryString);
            q.setParameter("idDetalleSupervision", detalleSupervisionDTO.getIdDetalleSupervision());
            int val = q.executeUpdate();
            LOG.info("Se Eliminaron " + val + " Registros de pgh_documento_adjunto");
            rpta = "ok";
        } catch (Exception ex) {
            LOG.info("Ocurrio un error " + ex.getMessage());
            LOG.error("Error en eliminarPghDocumentoAdjuntobyDetalleSupervision DAO imple", ex);
            rpta = "error";
        }
        return rpta;
    }
    
    @Override
    public int changeFlagEnviadoSigedPghDocumentoAdjunto(DocumentoAdjuntoDTO documentoAdjuntoDTO, UsuarioDTO usuarioDTO) throws DocumentoAdjuntoException {
        LOG.info("DocumentoAdjuntoDAOImpl : ActualizarEstadoPghDocumentoAdjunto-inicio");
        int resultado = 1;
        try {
            PghDocumentoAdjunto documentoAdjunto = crud.find(documentoAdjuntoDTO.getIdDocumentoAdjunto(), PghDocumentoAdjunto.class);
            documentoAdjunto.setFlagEnviadoSiged(documentoAdjuntoDTO.getFlagEnviadoSiged());
            documentoAdjunto.setDatosAuditoria(usuarioDTO);
            crud.update(documentoAdjunto);
            resultado = 1;
        } catch (Exception e) {
            resultado = -1;
            LOG.error("Error en changeFlagEnviadoSigedPghDocumentoAdjunto", e);
            DocumentoAdjuntoException ex = new DocumentoAdjuntoException(e.getMessage(), e);
            throw ex;
        }
        LOG.info("DocumentoAdjuntoDAOImpl : changeFlagEnviadoSigedPghDocumentoAdjunto-fin");
        return resultado;
    }
}
