/**
* Resumen		
* Objeto			: PghOrgaActiModuSecc.java
* Descripción		: Clase DTO PghOrgaActiModuSecc
* Fecha de Creación	: 14/10/2016
* PR de Creación	: OSINE_SFS-791
* Autor				: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------				
* OSINE_SFS-791     14/10/2016      Mario Dioses Fernandez          Crear la tarea automática que notifique vía correo que se debe elaborar el oficio por obligaciones incumplidas sin subsanar
* OSINE_SFS-791     14/10/2016      Mario Dioses Fernandez          Crear la tarea automática que cancele el registro de hidrocarburos
* OSINE791–RSIS40   | 11/10/2016 | Zosimo Chaupis Santur | Crear la funcionalidad para generar los resultados de una supervisión de orden de levantamiento DSR-CRITICIDAD la cual tenga todas sus obligaciones incumplidas subsanadas. 
* OSINE_791-RSIS19 | 06/09/2016 | Zosimo Chaupis Santur | Correo de Notificacion al Supervisor Regional para supervisión de una orden de supervisión DSR-CRITICIDAD
* OSINE_MAN_DSR_0022     |  16/06/2017   |   Carlos Quijano Chavez::ADAPTER      |     Cambiar el tipo de Cierre automatico a manual al generar supervision
* OSINE_MAN_DSR_0025     |  19/06/2017   |   Carlos Quijano Chavez::ADAPTER      |     Agregar la Columna Codigo y eliminar prioridad
*/ 
package gob.osinergmin.inpsweb.service.business.impl;
/* OSINE_MAN_DSR_0025 - Inicio */
import gob.osinergmin.inpsweb.dto.ObligacionDsrUpdtDTO;
/* OSINE_MAN_DSR_0025 - Fin */
import gob.osinergmin.inpsweb.dto.GenerarResultadoDTO;
import gob.osinergmin.inpsweb.dto.ReporteActaLevantamientoDTO;
import gob.osinergmin.inpsweb.dto.ReporteActaSupervisionDTO;
import gob.osinergmin.inpsweb.dto.ReporteCierreParcialDTO;
import gob.osinergmin.inpsweb.dto.ReporteDTO;
import gob.osinergmin.inpsweb.dto.ReporteMedidaDTO;
import gob.osinergmin.inpsweb.dto.ReporteProductoDTO;
import gob.osinergmin.inpsweb.service.business.ComentarioDetSupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.business.ComplementoDetSupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.business.CorreoServiceNeg;
import gob.osinergmin.inpsweb.service.business.DestinatarioCorreoServiceNeg;
import gob.osinergmin.inpsweb.service.business.DetalleLevantamientoServiceNeg;
import gob.osinergmin.inpsweb.service.business.DetalleSupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.business.DocumentoAdjuntoServiceNeg;
import gob.osinergmin.inpsweb.service.business.EmpresaSupervisadaServiceNeg;
import gob.osinergmin.inpsweb.service.business.EscenarioIncumplidoServiceNeg;
import gob.osinergmin.inpsweb.service.business.ExpedienteServiceNeg;
import gob.osinergmin.inpsweb.service.business.ExpedienteTareaServiceNeg;
import gob.osinergmin.inpsweb.service.business.InfraccionServiceNeg;
import gob.osinergmin.inpsweb.service.business.LocadorServiceNeg;
import gob.osinergmin.inpsweb.service.business.MaestroColumnaServiceNeg;
import gob.osinergmin.inpsweb.service.business.ObligacionBaseLegalServiceNeg;

import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import gob.osinergmin.inpsweb.service.business.ObligacionDsrServiceNeg;
import gob.osinergmin.inpsweb.service.business.OrdenServicioServiceNeg;
import gob.osinergmin.inpsweb.service.business.PeriodoMedidaSeguridadServiceNeg;
import gob.osinergmin.inpsweb.service.business.PlazoServiceNeg;
import gob.osinergmin.inpsweb.service.business.ProductoSuspenderServiceNeg;
import gob.osinergmin.inpsweb.service.business.RegistroHidrocarburoServiceNeg;
import gob.osinergmin.inpsweb.service.business.ResultadoSupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.business.SupervisionPersonaGralServiceNeg;
import gob.osinergmin.inpsweb.service.business.SupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.business.UbigeoServiceNeg;
import gob.osinergmin.inpsweb.service.business.UnidadSupervisadaServiceNeg;
import gob.osinergmin.inpsweb.service.dao.ObligacionDsrDAO;
import gob.osinergmin.inpsweb.service.dao.ResultadoSupervisionDAO;
import gob.osinergmin.inpsweb.service.exception.ComplementoDetSupervisionException;
import gob.osinergmin.inpsweb.service.exception.CorreoException;
import gob.osinergmin.inpsweb.service.exception.DestinatarioCorreoException;
import gob.osinergmin.inpsweb.service.exception.DetalleSupervisionException;
import gob.osinergmin.inpsweb.service.exception.DocumentoAdjuntoException;
import gob.osinergmin.inpsweb.service.exception.EscenarioIncumplidoException;
import gob.osinergmin.inpsweb.service.exception.ExpedienteException;
import gob.osinergmin.inpsweb.service.exception.ObligacionBaseLegalException;
import gob.osinergmin.inpsweb.service.exception.RegistroHidrocarburoException;
import gob.osinergmin.inpsweb.service.exception.SupervisionException;
import gob.osinergmin.inpsweb.service.exception.SupervisionPersonaGralException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.Utiles;
import gob.osinergmin.mdicommon.domain.dto.ComentarioDetSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.ComplementoDetSupValorDTO;
import gob.osinergmin.mdicommon.domain.dto.ComplementoDetSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.DestinatarioCorreoDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleLevantamientoDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresaSupDTO;
import gob.osinergmin.mdicommon.domain.dto.EscenarioIncumplidoDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteTareaDTO;
import gob.osinergmin.mdicommon.domain.dto.InfraccionDTO;
import gob.osinergmin.mdicommon.domain.dto.LocadorDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.ObligacionBaseLegalDTO;
import gob.osinergmin.mdicommon.domain.dto.ObligacionDsrDTO;
import gob.osinergmin.mdicommon.domain.dto.OrdenServicioDTO;
import gob.osinergmin.mdicommon.domain.dto.PeriodoMedidaSeguridadDTO;
import gob.osinergmin.mdicommon.domain.dto.PersonaGeneralDTO;
import gob.osinergmin.mdicommon.domain.dto.PlazoDTO;
import gob.osinergmin.mdicommon.domain.dto.ProductoDsrScopDTO;
import gob.osinergmin.mdicommon.domain.dto.RegistroHidrocarburoDTO;
import gob.osinergmin.mdicommon.domain.dto.ResultadoSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionPersonaGralDTO;
import gob.osinergmin.mdicommon.domain.dto.UbigeoDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.dto.busqueda.BusquedaDireccionxEmpSupervisada;
import gob.osinergmin.mdicommon.domain.ui.ComentarioDetSupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.ComplementoDetSupValorFilter;
import gob.osinergmin.mdicommon.domain.ui.ComplementoDetSupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.CorreoFilter;
import gob.osinergmin.mdicommon.domain.ui.DestinatarioCorreoFilter;
import gob.osinergmin.mdicommon.domain.ui.DetalleLevantamientoFilter;
import gob.osinergmin.mdicommon.domain.ui.DetalleSupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.DocumentoAdjuntoFilter;
import gob.osinergmin.mdicommon.domain.ui.EscenarioIncumplidoFilter;
import gob.osinergmin.mdicommon.domain.ui.ExpedienteFilter;
import gob.osinergmin.mdicommon.domain.ui.InfraccionFilter;
import gob.osinergmin.mdicommon.domain.ui.ObligacionBaseLegalFilter;
import gob.osinergmin.mdicommon.domain.ui.ObligacionDsrFilter;
import gob.osinergmin.mdicommon.domain.ui.OrdenServicioFilter;
import gob.osinergmin.mdicommon.domain.ui.PeriodoMedidaSeguridadFilter;
import gob.osinergmin.mdicommon.domain.ui.PlazoFilter;
import gob.osinergmin.mdicommon.domain.ui.ProductoDsrScopFilter;
import gob.osinergmin.mdicommon.domain.ui.RegistroHidrocarburoFilter;
import gob.osinergmin.mdicommon.domain.ui.ResultadoSupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.SupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.SupervisionPersonaGralFilter;
import gob.osinergmin.mdicommon.domain.ui.UbigeoFilter;
import gob.osinergmin.mdicommon.domain.ui.UnidadSupervisadaFilter;
import gob.osinergmin.siged.remote.enums.InvocationResult;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoOutRO;
import gob.osinergmin.siged.rest.util.ExpedienteInvoker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.springframework.util.CollectionUtils;

import gob.osinergmin.inpsweb.service.exception.DetalleLevantamientoException;
import gob.osinergmin.inpsweb.util.StringUtil;
import gob.osinergmin.mdicommon.domain.dto.DireccionUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.FlujoSigedDTO;
import gob.osinergmin.mdicommon.domain.dto.busqueda.BusquedaUnidadSupervisadaDTO;

@Service("ObligacionDsrServiceNeg")
public class ObligacionDsrServiceNegImpl implements ObligacionDsrServiceNeg {

    private static final Logger LOG = LoggerFactory.getLogger(ObligacionDsrServiceNegImpl.class);
    @Inject
    private ObligacionDsrDAO obligacionDsrDao;
    @Inject
    private ResultadoSupervisionDAO resultadoSupervisionDAO;
    @Inject
    private InfraccionServiceNeg infraccionServiceNeg;
    @Inject
    private DetalleSupervisionServiceNeg detalleSupervisionServiceNeg;
    @Inject
    private SupervisionServiceNeg supervisionServiceNeg;
    @Inject
    private MaestroColumnaServiceNeg maestroColumnaServiceNeg;
    @Inject
    private ResultadoSupervisionServiceNeg resultadoSupervisionServiceNeg;
    @Inject
    private DocumentoAdjuntoServiceNeg documentoAdjuntoServiceNeg;
    @Inject
    private PlazoServiceNeg plazoServiceNeg;
    @Inject
    private OrdenServicioServiceNeg ordenServicioServiceNeg;
    @Inject
    private RegistroHidrocarburoServiceNeg registroHidrocarburoServiceNeg;
    @Inject
    private PeriodoMedidaSeguridadServiceNeg periodoMedidaSeguridadServiceNeg;
    @Inject
    private UnidadSupervisadaServiceNeg unidadSupervisadaServiceNeg;
    @Inject
    private DestinatarioCorreoServiceNeg destinatarioCorreoServiceNeg;
    @Inject
    private CorreoServiceNeg correoServiceNeg;
    @Inject
    private SupervisionPersonaGralServiceNeg supervisionPersonaGralServiceNeg;
    @Inject
    private UbigeoServiceNeg ubigeoServiceNeg;
    @Inject
    private EscenarioIncumplidoServiceNeg escenarioIncumplidoServiceNeg;
    @Inject
    private ObligacionBaseLegalServiceNeg obligacionBaseLegalServiceNeg;
    @Inject
    private ComentarioDetSupervisionServiceNeg comentarioDetSupervisionServiceNeg;
    @Inject
    private LocadorServiceNeg locadorServiceNeg;
    @Inject
    private ProductoSuspenderServiceNeg productoSuspenderServiceNeg;
    @Inject
    private ComplementoDetSupervisionServiceNeg complementoDetSupervisionServiceNeg;
    /* OSINE_SFS-791 - RSIS 47 - Inicio */ 
    @Value("${alfresco.host}")
    private String HOST;
    @Inject
	private ExpedienteServiceNeg expedienteServiceNeg;
	@Inject
	private EmpresaSupervisadaServiceNeg empresaSupervisadaServiceNeg;
	@Inject
	private ExpedienteTareaServiceNeg expedienteTareaServiceNeg;
    /* OSINE_SFS-791 - RSIS 47 - Fin */ 
    /* OSINE_SFS-791 - RSIS 40 - Inicio */
    @Inject
    private DetalleLevantamientoServiceNeg detalleLevantamientoServiceNeg;
    /* OSINE_SFS-791 - RSIS 40 - Fin */

    @Override
    public List<ObligacionDsrDTO> listarObligacionDsr(ObligacionDsrFilter filtro) {
        LOG.info("listarObligacionDsr");
        List<ObligacionDsrDTO> retorno = null;
        try {
            retorno = obligacionDsrDao.find(filtro);
        } catch (Exception ex) {
            LOG.error("Error en listarObligacionDsr", ex);
        }
        return retorno;
    }
	/* OSINE_MAN_DSR_0025 - Inicio */
    @Override
    public List<ObligacionDsrUpdtDTO> listarObligacionDsrUpdt(ObligacionDsrFilter filtro) {
        LOG.info("listarObligacionDsr");
        List<ObligacionDsrUpdtDTO> retorno = null;
        try {
            retorno = obligacionDsrDao.findUpdt(filtro);
        } catch (Exception ex) {
            LOG.error("Error en listarObligacionDsr", ex);
        }
        return retorno;
    }
    /* OSINE_MAN_DSR_0025 - Fin */
    @Override
    @Transactional(readOnly = true)
    public List<ProductoDsrScopDTO> listarPdtosDsr(ObligacionDsrFilter filtro) {
        LOG.info("listarPdtosDsr");
        List<ProductoDsrScopDTO> retorno = null;
        try {
            retorno = obligacionDsrDao.findPdtos(filtro);
        } catch (Exception ex) {
            LOG.error("Error en listarPdtosDsr", ex);
        }
        return retorno;
    }

    @Override
    @Transactional(readOnly = true)
    public ObligacionDsrDTO findByPrioridad(Long idSupervision, Long prioridad) {
        LOG.info("findByPrioridad");
        ObligacionDsrDTO retorno = null;
        try {
            retorno = obligacionDsrDao.findByPrioridad(idSupervision, prioridad);
        } catch (Exception ex) {
            LOG.error("Error en findByPrioridad", ex);
        }
        return retorno;
    }

    @Override
    @Transactional()
    public ObligacionDsrDTO guardarDsrObligacion(ObligacionDsrDTO obligacionDstDTO, UsuarioDTO usuarioDto) {
        LOG.info("guardarDsrObligacion");
        ObligacionDsrDTO retorno = null;
        try {
            ResultadoSupervisionFilter filtro = new ResultadoSupervisionFilter();
            filtro.setCodigo(Constantes.CODIGO_RESULTADO_INCUMPLE);
            ResultadoSupervisionDTO resultadoSupervisionDTO = new ResultadoSupervisionDTO();
            resultadoSupervisionDTO = resultadoSupervisionDAO.getResultadoSupervision(filtro);
            DetalleSupervisionDTO detalleDTO = new DetalleSupervisionDTO();
            detalleDTO = detalleSupervisionServiceNeg.getDetalleSupervision(new DetalleSupervisionFilter(obligacionDstDTO.getIdDetalleSupervision()));
            if (resultadoSupervisionDTO.getIdResultadosupervision().equals(obligacionDstDTO.getEstadoIncumplimiento())) {
                SupervisionDTO supervision = null;
                SupervisionFilter filter = new SupervisionFilter();
                filter.setIdSupervision(detalleDTO.getSupervision().getIdSupervision());
                List<SupervisionDTO> ltasupervision = supervisionServiceNeg.buscarSupervision(filter);
                supervision = ltasupervision.get(Constantes.PRIMERO_EN_LISTA);
                InfraccionDTO infraccionDTO = infraccionServiceNeg.ComprobarMedidaSeguridadInfraccion(new InfraccionFilter(detalleDTO.getObligacion().getIdObligacion(),supervision.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getIdUnidadSupervisada()));
                obligacionDstDTO.setIdMedidaSeguridad(infraccionDTO.getIdmedidaSeguridad());
            } else {
                List<InfraccionDTO> ltainfraccionDTO = infraccionServiceNeg.getListInfraccion(new InfraccionFilter(detalleDTO.getObligacion().getIdObligacion()));
                InfraccionDTO infraccionDTO = new InfraccionDTO();
                if (ltainfraccionDTO.size() == Constantes.LISTA_UNICO_VALIR) {
                    infraccionDTO = ltainfraccionDTO.get(Constantes.PRIMERO_EN_LISTA);
                }
                obligacionDstDTO.setIdMedidaSeguridad(infraccionDTO.getIdmedidaSeguridad());
            }
            retorno = obligacionDsrDao.guardarDsrObligacion(obligacionDstDTO, usuarioDto);
        } catch (Exception ex) {
            LOG.error("Error en guardarDsrObligacion", ex);
        }
        return retorno;
    }
    
    @Override
    @Transactional()
    public ObligacionDsrDTO editarDsrComentarioSubsanacion(ObligacionDsrDTO obligacionDstDTO, UsuarioDTO usuarioDto) {
        LOG.info("editarDsrComentarioSubsanacion");
        ObligacionDsrDTO retorno = null;
        try {
            retorno = obligacionDsrDao.editarDsrComentarioSubsanacion(obligacionDstDTO, usuarioDto);
        } catch (Exception ex) {
            LOG.error("Error en editarDsrComentarioSubsanacion", ex);
        }
        return retorno;
    }

    @Override
    @Transactional()
    public ProductoDsrScopDTO guardarDsrProductoSuspender(ProductoDsrScopDTO productoDsrScopDTO, UsuarioDTO usuarioDto) {
        LOG.info("guardarDsrProductoSuspender");
        ProductoDsrScopDTO retorno = null;
        try {
            retorno = obligacionDsrDao.guardarDsrProductoSuspender(productoDsrScopDTO, usuarioDto);
        } catch (Exception ex) {
            LOG.error("Error en guardarDsrProductoSuspender", ex);
        }
        return retorno;
    }

    @Override
    @Transactional()
    public ProductoDsrScopDTO eliminarDsrProductoSuspender(ProductoDsrScopDTO productoDsrScopDTO, UsuarioDTO usuarioDto) {
        LOG.info("guardarDsrProductoSuspender");
        ProductoDsrScopDTO retorno = null;
        try {
            retorno = obligacionDsrDao.eliminarDsrProductoSuspender(productoDsrScopDTO, usuarioDto);
        } catch (Exception ex) {
            LOG.error("Error en guardarDsrProductoSuspender", ex);
        }
        return retorno;
    }

    /* OSINE791 – RSIS12 - Inicio */
    @Override
    public String VerificarObstaculizados(ObligacionDsrDTO filtro) {
        LOG.info("VerificarObstaculizados");
        String retorno = "";
        try {
            retorno = obligacionDsrDao.VerificarObstaculizados(filtro);
        } catch (Exception ex) {
            LOG.error("Error en VerificarObstaculizados", ex);
        }
        return retorno;
    }
    /* OSINE791 – RSIS12 - Fin */
     /*OSINE-791 - RSIS 41 - Inicio */
    public List<DocumentoAdjuntoDTO> verDocumentosGenerarResultadosSupervisionDSR1(Long idSupervision){
        List<DocumentoAdjuntoDTO> listaprincipal = new ArrayList<DocumentoAdjuntoDTO>();
        
        //para Carta de Visita Fase 1
        DocumentoAdjuntoFilter filtroCartaVisita = new DocumentoAdjuntoFilter();
        MaestroColumnaDTO idTipoDocCartaVisita = maestroColumnaServiceNeg.findMaestroColumnaByDominioAplicDesc(Constantes.DOMINIO_TIP_DOC_SUP_SIGED, Constantes.APLICACION_INPS, Constantes.DESCRIPCION_TIP_DOC_SUP_SIGED_CARTA_VISITA).get(0);
        filtroCartaVisita.setIdSupervision(idSupervision);
        filtroCartaVisita.setIdTipoDocumento(idTipoDocCartaVisita.getIdMaestroColumna());
        List<DocumentoAdjuntoDTO> ltaCartaVisita;
        try {
            ltaCartaVisita = documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(filtroCartaVisita);
        } catch (DocumentoAdjuntoException ex) {
            ltaCartaVisita = null;
        }
        //Para acta de Supervision Fase 1
        DocumentoAdjuntoFilter filtroactaSup = new DocumentoAdjuntoFilter();
        MaestroColumnaDTO idTipoDocactaSup = maestroColumnaServiceNeg.findMaestroColumnaByDominioAplicDesc(Constantes.DOMINIO_TIP_DOC_SUP_SIGED, Constantes.APLICACION_INPS, Constantes.DESCRIPCION_TIP_DOC_SUP_SIGED_ACTA_EJECUCION_MEDIDA_SEGURIDAD).get(0);
        filtroactaSup.setIdSupervision(idSupervision);
        filtroactaSup.setIdTipoDocumento(idTipoDocactaSup.getIdMaestroColumna());
        List<DocumentoAdjuntoDTO> ltaactaSup;
        try {
            ltaactaSup = documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(filtroactaSup);
        } catch (DocumentoAdjuntoException ex) {
            ltaactaSup = null;
        }
        //para Constancia de Suspension de RH
        DocumentoAdjuntoFilter filtroConstSuspenRH = new DocumentoAdjuntoFilter();
        MaestroColumnaDTO idTipoDocConstSuspenRH = maestroColumnaServiceNeg.findMaestroColumnaByDominioAplicDesc(Constantes.DOMINIO_TIP_DOC_SUP_SIGED, Constantes.APLICACION_INPS, Constantes.DESCRIPCION_TIP_DOC_SUP_SIGED_CONSTANCIA_SUSPENSION_CANCELACION_REGIS_HIDRO).get(0);
        filtroConstSuspenRH.setIdSupervision(idSupervision);
        filtroConstSuspenRH.setIdTipoDocumento(idTipoDocConstSuspenRH.getIdMaestroColumna());
        List<DocumentoAdjuntoDTO> ltaConstSuspenRH;
        try {
            ltaConstSuspenRH = documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(filtroConstSuspenRH);
        } catch (DocumentoAdjuntoException ex) {
            ltaConstSuspenRH = null;
        }        
        if(ltaCartaVisita != null && ltaCartaVisita.size() > Constantes.LISTA_VACIA){
            for (DocumentoAdjuntoDTO documentoAdjuntoDTOCartaVisita : ltaCartaVisita) {
                listaprincipal.add(documentoAdjuntoDTOCartaVisita);
            }
        }
        if(ltaactaSup != null && ltaactaSup.size() > Constantes.LISTA_VACIA){
            for (DocumentoAdjuntoDTO documentoAdjuntoDTOactaSup : ltaactaSup) {
                listaprincipal.add(documentoAdjuntoDTOactaSup);
            }
        }
        if(ltaConstSuspenRH != null && ltaConstSuspenRH.size() > Constantes.LISTA_VACIA){
            for (DocumentoAdjuntoDTO documentoAdjuntoDTOConstSuspenRH : ltaConstSuspenRH) {
                listaprincipal.add(documentoAdjuntoDTOConstSuspenRH);
            }
        }
        return listaprincipal;
    }
    /*OSINE_-791 - RSIS 41 - Fin */
    @Override
    @Transactional(rollbackFor=DocumentoAdjuntoException.class)
    public Map<String, Object> generarResultadosDocumento(GenerarResultadoDTO generarResultadoDTO,HttpServletRequest request, HttpSession session) throws DocumentoAdjuntoException{
        LOG.info("Erroe en generarResultadosDocumento");
        Map<String, Object> retorno = new HashMap<String, Object>();
        String rutaImagen = "";
        String rutaReportePrincipal="";
        InputStream reportStreamImage = null;
        InputStream reportJasper = null;
        ReporteDTO reporteDTO = new ReporteDTO();
        Map<String, Object> reportParams = null;
        DocumentoAdjuntoDTO registroDocumentoEjecucionMedida = null;
        String correos="";
        try{
            ServletContext context = session.getServletContext();
            //Pruebas Consulta
            //reportParams = setearInformacion(reportStreamImage, generarResultadoDTO);
            //DocumentoAdjuntoFilter filtro = new DocumentoAdjuntoFilter();
            //MaestroColumnaDTO idTipoDoc=maestroColumnaServiceNeg.findByDominioAplicacionCodigo(Constantes.DOMINIO_TIPO_DOC_SUPERVISION, Constantes.APLICACION_INPS, Constantes.CODIGO_GEN_RESULT).get(0);
            //filtro.setIdSupervision(generarResultadoDTO.getIdSupervision());
            //filtro.setIdTipoDocumento(idTipoDoc.getIdMaestroColumna());
            //List<DocumentoAdjuntoDTO> listado= documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(filtro);
            List<DocumentoAdjuntoDTO> listado = verDocumentosGenerarResultadosSupervisionDSR1(generarResultadoDTO.getIdSupervision());
            SupervisionDTO supervision = null;
            SupervisionFilter filter = new SupervisionFilter();
            filter.setIdSupervision(generarResultadoDTO.getIdSupervision());
            List<SupervisionDTO> ltasupervision = supervisionServiceNeg.buscarSupervision(filter);
            supervision = ltasupervision.get(Constantes.PRIMERO_EN_LISTA);
                   
            int nroInfracciones = Integer.parseInt(generarResultadoDTO.getFlagInfracciones());
            if(nroInfracciones == Constantes.NRO_INFRACCIONES_CARTA_VISITA){
                if(listado!= null && listado.size()> Constantes.LISTA_VACIA){
                    registroDocumentoEjecucionMedida = listado.get(Constantes.PRIMERO_EN_LISTA);
                    registroDocumentoEjecucionMedida.setSupervision(new SupervisionDTO(generarResultadoDTO.getIdSupervision()));
                } else {                    
                    if (ltasupervision.size() == 1) {
                        boolean validaDetalleObstaculizados = false;
                        UbigeoDTO ubigoDTO = new UbigeoDTO();
                        ubigoDTO.setIdDepartamento(generarResultadoDTO.getIdDepartamento());
                        ubigoDTO.setIdProvincia(generarResultadoDTO.getIdProvincia());
                        ubigoDTO.setIdDistrito(generarResultadoDTO.getIdDistrito());
                        Date fechafin = new Date();
                        String fechacort = Utiles.DateToString(fechafin, Constantes.FORMATO_FECHA_CORTA);
                        String horacort = Utiles.DateToString(fechafin, Constantes.FORMATO_HORA_CORTA);
                        generarResultadoDTO.setFechaFin(fechacort);
                        generarResultadoDTO.setHoraFin(horacort);
                        supervision.setFechaFin(generarResultadoDTO.getFechaFin()+" "+generarResultadoDTO.getHoraFin());
                        supervision.setCartaVisita(generarResultadoDTO.getNroCarta());
                        int nroInfraccionesObstaculizado = Integer.parseInt(generarResultadoDTO.getFlagObstaculizados());
                        ResultadoSupervisionFilter filtroResultado = new ResultadoSupervisionFilter();
                        if (nroInfraccionesObstaculizado != Constantes.VALOR_VACIO) {
                            filtroResultado.setCodigo(Constantes.CODIGO_RESULTADO_SUPERVISION_CUMPLE_OBSTACULIZADO);
                            validaDetalleObstaculizados = true;
                        } else { 
                            filtroResultado.setCodigo(Constantes.CODIGO_RESULTADO_SUPERVISION_CUMPLE);
                        }   
                        ResultadoSupervisionDTO resultadoDTO = resultadoSupervisionServiceNeg.getResultadoSupervision(filtroResultado);
                        supervision.setResultadoSupervisionDTO(resultadoDTO);
                        //UPDATE
                        supervision = supervisionServiceNeg.registroEmSupervision(supervision, Constantes.getUsuarioDTO(request));
                        if (supervision != null) {
                            rutaImagen = Constantes.RUTA_IMG_CABECERA_GENERAR_RESULTADOS;
                            rutaReportePrincipal = Constantes.RUTA_PLANTILLA_GENERAR_RESULTADOS_CARTA_VISITA;
                            reporteDTO.setDescripcionDocumento(Constantes.DESCRIPCION_DOCUMENTO_CARTA_VISITA);
                            String hora = getHoraActual();
                            String fechact = getFechaActual();
                            reporteDTO.setNombreArchivo(Constantes.NOMBRE_ARCHIVO_CARTA_VISITA +"_"+fechact+"_"+hora+"" +Constantes.EXTENSION_DOCUMENTO_CARTA_VISITA);
                            reportStreamImage = context.getResourceAsStream(rutaImagen);
                            reportJasper = context.getResourceAsStream(rutaReportePrincipal);
                            reportParams = setearInformacionCartaVisita(reportStreamImage, generarResultadoDTO);
                            //INSERT
                            MaestroColumnaDTO TipoDocumentoCartaVisitaDTO = maestroColumnaServiceNeg.findMaestroColumnaByDominioAplicDesc(Constantes.DOMINIO_TIP_DOC_SUP_SIGED, Constantes.APLICACION_INPS, Constantes.DESCRIPCION_TIP_DOC_SUP_SIGED_CARTA_VISITA).get(0);
                            registroDocumentoEjecucionMedida = registrarDocumentoBDDsr(reportJasper, reportParams, generarResultadoDTO.getIdSupervision(), request,reporteDTO,TipoDocumentoCartaVisitaDTO);
                            if (registroDocumentoEjecucionMedida != null) {
                                /* OSINE_SFS-791 - RSIS 19 - Inicio */
                                if (validaDetalleObstaculizados == true) {
                                    //CORREO NOTIFICACION DETALLES OBSTACULIZADOS
                                    correos += ",CorreoNotificacionObstaculizados";
                                    EnvioNotiInicialSupervisionDsrObstaculizacionObligaciones(ubigoDTO, supervision);
                                }
                                if(supervision.getOtrosIncumplimientos() != null){
                                    if(!supervision.getOtrosIncumplimientos().trim().equals("")){
                                        //CORREO NOTIFICACION SUPERVISION CON OTROS INCUMPLIMIENTOS
                                        correos += ",CorreoNotificacionOtrosIncumplimientos";
                                        EnvioNotiInicialSupervisionDsrOtrosIncumplimientos(ubigoDTO,supervision);
                                    }
                                }
                                /* OSINE_SFS-791 - RSIS 19 - Fin */
                            }
                        }
                    }
                }
            } else {
                if (nroInfracciones > Constantes.NRO_INFRACCIONES_CARTA_VISITA) {
                    if (listado != null && listado.size() > Constantes.LISTA_VACIA) {
                        registroDocumentoEjecucionMedida = listado.get(Constantes.PRIMERO_EN_LISTA);
                        registroDocumentoEjecucionMedida.setSupervision(new SupervisionDTO(generarResultadoDTO.getIdSupervision()));
                    } else {
                        UbigeoDTO ubigoDTO = new UbigeoDTO();
                        ubigoDTO.setIdDepartamento(generarResultadoDTO.getIdDepartamento());
                        ubigoDTO.setIdProvincia(generarResultadoDTO.getIdProvincia());
                        ubigoDTO.setIdDistrito(generarResultadoDTO.getIdDistrito());
                        /* OSINE_SFS-791 - RSIS 19 - Inicio */
                        boolean validaDetalleObstaculizados = false;
                        /* OSINE_SFS-791 - RSIS 19 - Fin */
                        Date fech = new Date();
                        String fecha1 = Utiles.DateToString(fech, Constantes.FORMATO_FECHA_CORTA);
                        generarResultadoDTO.setFechaFin(fecha1);                        
                        PlazoFilter filtroPlazo = new PlazoFilter();
                        filtroPlazo.setCodigoPlazo(Constantes.CODIGO_PLAZO_INICIAL_MEDIDA_SEGURIDAD);
                        List<PlazoDTO> ltaplazo = plazoServiceNeg.getListPlazo(filtroPlazo);
                        PlazoDTO plazoDTO = ltaplazo.get(Constantes.PRIMERO_EN_LISTA);
                        String idDepartamento = (generarResultadoDTO.getIdDepartamento() != null) ? generarResultadoDTO.getIdDepartamento() : Constantes.CONSTANTE_VACIA;
                        Date FechaFinPlaneado = ordenServicioServiceNeg.calculoFechaFin(generarResultadoDTO.getFechaFin(), idDepartamento, new Long(plazoDTO.getCantidad()));
                        Date FechaInicialHabil = ordenServicioServiceNeg.calculoFechaFin(generarResultadoDTO.getFechaFin(), idDepartamento, new Long(1));
                        DetalleSupervisionFilter fildeta = new DetalleSupervisionFilter();
                        fildeta.setIdSupervision(supervision.getIdSupervision());
                        fildeta.setCodigoResultadoSupervision(Constantes.CODIGO_RESULTADO_INCUMPLE);
                        List<DetalleSupervisionDTO> ltaDetaIncumple = new ArrayList<DetalleSupervisionDTO>();
                        LOG.info("Dias Plazo - -> " + plazoDTO.getCantidad());
                        LOG.info("idDepartamento - -> " + idDepartamento);
                        LOG.info("FechaFinPlaneado - -> " + FechaFinPlaneado);
                        LOG.info("idExpediente " + supervision.getOrdenServicioDTO().getExpediente().getIdExpediente());
                        PeriodoMedidaSeguridadDTO periodoMedidaSeguridadDTO = new PeriodoMedidaSeguridadDTO();
                        periodoMedidaSeguridadDTO.setExpedienteDTO(new ExpedienteDTO(supervision.getOrdenServicioDTO().getExpediente().getIdExpediente()));
                        periodoMedidaSeguridadDTO.setFechaFinPlaneado(FechaFinPlaneado);
                        periodoMedidaSeguridadDTO.setFechaInicio(FechaInicialHabil);
                        int nroInfraccionesObstaculizado = Integer.parseInt(generarResultadoDTO.getFlagObstaculizados());
                        ResultadoSupervisionFilter filtroResultado = new ResultadoSupervisionFilter();
                        if (nroInfraccionesObstaculizado != Constantes.VALOR_VACIO) {
                            filtroResultado.setCodigo(Constantes.CODIGO_RESULTADO_SUPERVISION_NOCUMPLE_OBSTACULIZADO);
                            /* OSINE_SFS-791 - RSIS 19 - Inicio */
                            validaDetalleObstaculizados = true;
                            /* OSINE_SFS-791 - RSIS 19 - Fin */
                        } else {
                            filtroResultado.setCodigo(Constantes.CODIGO_RESULTADO_SUPERVISION_NOCUMPLE);
                        }
                        ResultadoSupervisionDTO resultadoDTO = resultadoSupervisionServiceNeg.getResultadoSupervision(filtroResultado);
                        UnidadSupervisadaDTO unidad = new UnidadSupervisadaDTO();
                        unidad.setIdUnidadSupervisada(supervision.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getIdUnidadSupervisada());
                        unidad.setCodigoOsinergmin(supervision.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getCodigoOsinergmin());
                        unidad.setActividad(supervision.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getActividad());
                        unidad.setNombreUnidad(supervision.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getNombreUnidad());
                        String hora1 = Utiles.DateToString(fech, Constantes.FORMATO_HORA_CORTA);
                        generarResultadoDTO.setHoraFin(hora1);
                        supervision.setFechaFin(generarResultadoDTO.getFechaFin() + " " + generarResultadoDTO.getHoraFin());
                        //SELECT - UPDATE
						/* OSINE_MAN_DSR_0022 - Inicio */
                        //Boolean rptaCierreTotal = detalleSupervisionServiceNeg.VerCierreTotalSupervision(new DetalleSupervisionFilter(supervision.getIdSupervision(), 0), Constantes.getUsuarioDTO(request), supervision.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getIdUnidadSupervisada());
						Boolean rptaCierreTotal = generarResultadoDTO.getTipoCierre().equals("1");
						/* OSINE_MAN_DSR_0022 - Fin */
                        LOG.info("----->rptaCierreTotal--->"+rptaCierreTotal);
                        //if (rptaCierreTotal == true) {
                        if (rptaCierreTotal) {
                            LOG.info("Se Procedera a realizar el Cierre Total");
                            LOG.info("Se Procedera a Suspender el Registro de Hidrocarburo");
                            //UPDATE - INSERT
                            RegistroHidrocarburoDTO registroHidrocarburoDTO = registroHidrocarburoServiceNeg.SuspenderRegistroHidrocarburoExterno(unidad, Constantes.getUSUARIO(request), Constantes.getUsuarioDTO(request));
                            if (registroHidrocarburoDTO != null) { 
                                LOG.info("Se Suspendio el Registro de Hidrocarburo");
                                //SupervisionDTO auxiliarDTO = null;
                                supervision.setResultadoSupervisionDTO(resultadoDTO);
                                //UPDATE
                                supervision = supervisionServiceNeg.registroEmSupervision(supervision, Constantes.getUsuarioDTO(request));
                                if (supervision != null) {
                                    //INSERT
                                    periodoMedidaSeguridadDTO = periodoMedidaSeguridadServiceNeg.registrarPeriodoMedidaSeguridad(periodoMedidaSeguridadDTO, Constantes.getUsuarioDTO(request));
                                    if (periodoMedidaSeguridadDTO != null) {
                                        LOG.info("Unidad es : |" + supervision.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getActividad().getIdActividad() + "|");
                                        LOG.info("INICIANDO LA CONSTRUCCION DEL DOCUMENTO DE SUSPENSION DE HIDROCARBURO");
                                        rutaImagen = Constantes.RUTA_IMG_CABECERA_GENERAR_RESULTADOS;
                                        rutaReportePrincipal = Constantes.RUTA_PLANTILLA_GENERAR_RESULTADOS_ConstanciaRH;
                                        reporteDTO.setDescripcionDocumento(Constantes.DESCRIPCION_DOCUMENTO_CONSTANCIA_RH);
                                        String hora = getHoraActual();
                                        String fechact = getFechaActual();
                                        reporteDTO.setNombreArchivo(Constantes.NOMBRE_ARCHIVO_CONSTANCIA_RH +"_"+fechact+"_"+hora+"" + Constantes.EXTENSION_DOCUMENTO_CONSTANCIA_RH);
                                        reportStreamImage = context.getResourceAsStream(rutaImagen);
                                        reportJasper = context.getResourceAsStream(rutaReportePrincipal);
                                        reportParams = setearInformacionConstanciaRH(reportStreamImage, generarResultadoDTO, registroHidrocarburoDTO);
                                        //INSERT
                                        MaestroColumnaDTO TipoDocumentoConstanciaSuspensionRHDTO = maestroColumnaServiceNeg.findMaestroColumnaByDominioAplicDesc(Constantes.DOMINIO_TIP_DOC_SUP_SIGED, Constantes.APLICACION_INPS, Constantes.DESCRIPCION_TIP_DOC_SUP_SIGED_CONSTANCIA_SUSPENSION_CANCELACION_REGIS_HIDRO).get(0);
                                        registroDocumentoEjecucionMedida = registrarDocumentoBDDsr(reportJasper, reportParams, generarResultadoDTO.getIdSupervision(), request, reporteDTO,TipoDocumentoConstanciaSuspensionRHDTO);
                                        LOG.info("SE REGISTRO EL DOCUMENTO DE SUSPENSION DE REGISTRO DE HIDROCARBURO");
                                        Boolean cierreTotal = true;
                                        Boolean cierreParcial = false;
                                        ltaDetaIncumple = detalleSupervisionServiceNeg.listarDetalleSupervision(fildeta);
                                        //INSERT
                                        registroDocumentoEjecucionMedida = ReporteActaSupervision(supervision, generarResultadoDTO, session, request, cierreTotal, cierreParcial, ltaDetaIncumple);
                                        if (registroDocumentoEjecucionMedida != null) {
                                            //ACTUALIZANDO EL EXPEDIENTE PARA QUE EL AGENTE PUEDA ACCEDER AL MODULO DE LEVANTAMIENTOS
                                            LOG.info("ACTUALIZANDO EL EXPEDIENTE PARA QUE EL AGENTE PUEDA ACCEDER AL MODULO DE LEVANTAMIENTOS");
                                            ExpedienteDTO expedienteDTO = new ExpedienteDTO();
                                            expedienteDTO.setIdExpediente(supervision.getOrdenServicioDTO().getExpediente().getIdExpediente());
                                            MaestroColumnaDTO idEstadoLevantamiento = maestroColumnaServiceNeg.findByDominioAplicacionCodigo(Constantes.DOMINIO_ESTADO_LEVANTAMIENTO, Constantes.APLICACION_INPS, Constantes.CODIGO_ESTADO_LEVANTAMIENTO_PENDIENTE).get(0);
                                            expedienteDTO.setEstadoLevantamiento(idEstadoLevantamiento);
                                            expedienteDTO = expedienteServiceNeg.actualizarExpedienteHabModuloLevantamiento(expedienteDTO, Constantes.getUsuarioDTO(request));
                                            if (expedienteDTO != null) {
                                                //CORREO1
                                                correos += ",correo1";
                                                EnvioNotificacionesSupervisionDsrMedidaSeguridad(ubigoDTO, supervision.getOrdenServicioDTO());
                                                LOG.info("---->nro hidrocarburo s : |" + registroHidrocarburoDTO.getNumeroRegistroHidrocarburo() + "|");
                                                //CORREO2
                                                correos += ",correo2";
                                                EnvioNotificacionesSupervisionDsrSuspensionRH(ubigoDTO, supervision.getOrdenServicioDTO(), registroHidrocarburoDTO);
                                                //CORREO3
                                                correos += ",correo3";
                                                EnvioNotificacionesSupervisionDsrDesactivarRolComprasScop(ubigoDTO, supervision.getOrdenServicioDTO(), registroHidrocarburoDTO, unidad);
                                                /* OSINE_SFS-791 - RSIS 19 - Inicio */
                                                if (validaDetalleObstaculizados == true) {
                                                    //CORREO NOTIFICACION DETALLES OBSTACULIZADOS
                                                    correos += ",CorreoNotificacionObstaculizados";
                                                    EnvioNotiInicialSupervisionDsrObstaculizacionObligaciones(ubigoDTO, supervision);
                                                }
                                                if (supervision.getOtrosIncumplimientos() != null) {
                                                    if (!supervision.getOtrosIncumplimientos().trim().equals("")) {
                                                        //CORREO NOTIFICACION SUPERVISION CON OTROS INCUMPLIMIENTOS
                                                        correos += ",CorreoNotificacionOtrosIncumplimientos";
                                                        EnvioNotiInicialSupervisionDsrOtrosIncumplimientos(ubigoDTO, supervision);
                                                    }
                                                }
                                                if (generarResultadoDTO.getIdentificadorEjecucionMedi().equals(Constantes.DESCRIPCION_EJECUCION_MEDIDA_NO)) {
                                                    //CORREO NOTIFICACION NO EJECUCION MEDIDA
                                                    correos += ",CorreoNotificacionNoEjecucionMedida";
                                                    EnvioNotiInicialSupervisionDsrNoEjecucionMedida(ubigoDTO, supervision);
                                                }
                                                /* OSINE_SFS-791 - RSIS 19 - Fin */
                                                /* OSINE_SFS-791 - RSIS 48 - Inicio */
                                                ExpedienteDTO expedienteLevantaDTO = new ExpedienteDTO();
                                                expedienteLevantaDTO.setIdExpediente(supervision.getOrdenServicioDTO().getExpediente().getIdExpediente());
                                                detalleLevantamientoServiceNeg.guardarDetalleLevantamiento(expedienteLevantaDTO, session, request);
                                                /* OSINE_SFS-791 - RSIS 48 - Fin */
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            LOG.info("Se Procedera a realizar el Cierre Parcial");
                            //SupervisionDTO auxiliarDTO = null;
                            supervision.setResultadoSupervisionDTO(resultadoDTO);
                            //UPDATE
                            supervision = supervisionServiceNeg.registroEmSupervision(supervision, Constantes.getUsuarioDTO(request));
                            if (supervision != null) {
                                //INSERT
                                periodoMedidaSeguridadDTO = periodoMedidaSeguridadServiceNeg.registrarPeriodoMedidaSeguridad(periodoMedidaSeguridadDTO, Constantes.getUsuarioDTO(request));
                                if (periodoMedidaSeguridadDTO != null) {
                                    LOG.info("SE REALIZO EL CIERRE PARCIAL");
                                    ltaDetaIncumple = detalleSupervisionServiceNeg.listarDetalleSupervision(fildeta);
                                    Boolean cierreTotal = false;
                                    Boolean cierreParcial = true;
                                    List<RegistroHidrocarburoDTO> ltaregistroRH = registroHidrocarburoServiceNeg.getListRegistroHidrocarburo(new RegistroHidrocarburoFilter(unidad.getIdUnidadSupervisada()));
                                    RegistroHidrocarburoDTO registroRH = new RegistroHidrocarburoDTO();
                                    if(ltaregistroRH.size() == Constantes.LISTA_UNICO_VALIR){
                                        registroRH = ltaregistroRH.get(Constantes.PRIMERO_EN_LISTA);
                                    }                                    
                                    //INSERT
                                    registroDocumentoEjecucionMedida = ReporteActaSupervision(supervision, generarResultadoDTO, session, request, cierreTotal, cierreParcial, ltaDetaIncumple);
                                    if (registroDocumentoEjecucionMedida != null) {
                                        //ACTUALIZANDO EL EXPEDIENTE PARA QUE EL AGENTE PUEDA ACCEDER AL MODULO DE LEVANTAMIENTOS
                                        LOG.info("ACTUALIZANDO EL EXPEDIENTE PARA QUE EL AGENTE PUEDA ACCEDER AL MODULO DE LEVANTAMIENTOS");
                                        ExpedienteDTO expedienteDTO = new ExpedienteDTO();
                                        expedienteDTO.setIdExpediente(supervision.getOrdenServicioDTO().getExpediente().getIdExpediente());
                                        MaestroColumnaDTO idEstadoLevantamiento = maestroColumnaServiceNeg.findByDominioAplicacionCodigo(Constantes.DOMINIO_ESTADO_LEVANTAMIENTO, Constantes.APLICACION_INPS, Constantes.CODIGO_ESTADO_LEVANTAMIENTO_PENDIENTE).get(0);
                                        expedienteDTO.setEstadoLevantamiento(idEstadoLevantamiento);
                                        expedienteDTO = expedienteServiceNeg.actualizarExpedienteHabModuloLevantamiento(expedienteDTO, Constantes.getUsuarioDTO(request));
                                        if (expedienteDTO != null) {
                                            //CORREO1
                                            String correo1 = EnvioNotificacionesSupervisionDsrMedidaSeguridad(ubigoDTO, supervision.getOrdenServicioDTO());
                                            correos += ",correo1=" + correo1;
                                            //CORREO4
                                            String correo4 = EnvioNotificacionesSupervisionDsrDesactivarRolComprasScopProducto(ubigoDTO, supervision.getOrdenServicioDTO(), registroRH, unidad, session, supervision);
                                            correos += ",correo4=" + correo4;
                                            /* OSINE_SFS-791 - RSIS 19 - Inicio */
                                            if (validaDetalleObstaculizados == true) {
                                                //CORREO NOTIFICACION DETALLES OBSTACULIZADOS
                                                correos += ",CorreoNotificacionObstaculizados";
                                                EnvioNotiInicialSupervisionDsrObstaculizacionObligaciones(ubigoDTO, supervision);
                                            }
                                            if (supervision.getOtrosIncumplimientos() != null) {
                                                if (!supervision.getOtrosIncumplimientos().trim().equals("")) {
                                                    //CORREO NOTIFICACION SUPERVISION CON OTROS INCUMPLIMIENTOS
                                                    correos += ",CorreoNotificacionOtrosIncumplimientos";
                                                    EnvioNotiInicialSupervisionDsrOtrosIncumplimientos(ubigoDTO, supervision);
                                                }
                                            }
                                            if (generarResultadoDTO.getIdentificadorEjecucionMedi().equals(Constantes.DESCRIPCION_EJECUCION_MEDIDA_NO)) {
                                                //CORREO NOTIFICACION NO EJECUCION MEDIDA
                                                correos += ",CorreoNotificacionNoEjecucionMedida";
                                                EnvioNotiInicialSupervisionDsrNoEjecucionMedida(ubigoDTO, supervision);
                                            }
                                            /* OSINE_SFS-791 - RSIS 19 - Fin */
                                            /* OSINE_SFS-791 - RSIS 48 - Inicio */
                                            ExpedienteDTO expedientelevantaDTO = new ExpedienteDTO();
                                            expedientelevantaDTO.setIdExpediente(supervision.getOrdenServicioDTO().getExpediente().getIdExpediente());
                                            detalleLevantamientoServiceNeg.guardarDetalleLevantamiento(expedientelevantaDTO, session, request);
                                            /* OSINE_SFS-791 - RSIS 48 - Inicio */
                                        }                                  
                                    }
                                }
                            }
                        }
                    }
                }
            }
            retorno.put("registroDocumentoEjecucionMedida", registroDocumentoEjecucionMedida);
            retorno.put("supervision", supervision);
            retorno.put("correos", correos);
        }catch(Exception e){
            LOG.error("Error en generarResultadosDocumento",e);
            throw new DocumentoAdjuntoException(e.getMessage(),e);
        }        
        return retorno;
    }
    
    public Map<String, Object> setearInformacionCartaVisita(InputStream reportStreamImage, GenerarResultadoDTO generarResultadoDTO) {
        Map<String, Object> reportParams = new HashMap<String, Object>();
        SupervisionFilter filtro = new SupervisionFilter();
        filtro.setIdSupervision(generarResultadoDTO.getIdSupervision());
        List<SupervisionDTO> supervisionDTOs = supervisionServiceNeg.buscarSupervisionReporte(filtro);
        SupervisionDTO supervisionDTO = new SupervisionDTO();
        supervisionDTO = supervisionDTOs.get(Constantes.PRIMERO_EN_LISTA);
        LOG.info("nro Carta " + generarResultadoDTO.getNroCarta());
        LOG.info("fechaInicio " + supervisionDTO.getFechaInicio());
        LOG.info("numeroOrdenServicio " + (supervisionDTO.getOrdenServicioDTO().getNumeroOrdenServicio()));
        LOG.info("numeroExpediente " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getNumeroExpediente()));
        LOG.info("idUnidadSupervisada " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getIdUnidadSupervisada()));
        LOG.info("codigoOsinerming " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getCodigoOsinergmin()));
        UbigeoDTO filter = new UbigeoDTO();
        filter.setIdDepartamento(generarResultadoDTO.getIdDepartamento());
        filter.setIdProvincia(generarResultadoDTO.getIdProvincia());
        filter.setIdDistrito(generarResultadoDTO.getIdDistrito());
        UbigeoDTO general = ObtenerUbigeo(filter);
        UnidadSupervisadaFilter unidadfilter = new UnidadSupervisadaFilter();
        unidadfilter.setIdUnidadSupervisada(supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getIdUnidadSupervisada());
        List<UnidadSupervisadaDTO> ltaUnidad = unidadSupervisadaServiceNeg.getUnidadSupervisadaDTO(unidadfilter);
        List<MaestroColumnaDTO> ltamaestro =  maestroColumnaServiceNeg.findByDominioAplicacionCodigo(Constantes.DOMINIO_OBSERV_CARTA_VISITA,Constantes.APLICACION_INPS,Constantes.CODIGO_OBS_CARTA_VISITA_SIN_INFRACCION);
        
        LocadorDTO locadorDTO=null;
        if(supervisionDTO.getOrdenServicioDTO().getLocador()!=null && supervisionDTO.getOrdenServicioDTO().getLocador().getIdLocador()!=null){
            locadorDTO = locadorServiceNeg.getById(supervisionDTO.getOrdenServicioDTO().getLocador().getIdLocador());
        }
        if (ltaUnidad.size() == Constantes.LISTA_UNICO_VALIR) {
            UnidadSupervisadaDTO unidadDTO = ltaUnidad.get(Constantes.PRIMERO_EN_LISTA);
            reportParams.put("rutaImagen", reportStreamImage);
            reportParams.put("nroCartaVisita", generarResultadoDTO.getNroCarta());
            reportParams.put("preOperativa", false);
            reportParams.put("operativa", true);
            reportParams.put("especial", false);
            reportParams.put("planAbono", false);
            reportParams.put("comprobacionOperaciones", false);
            reportParams.put("activosInseguros", false);
            reportParams.put("pdj", false);
            reportParams.put("emergencias", false);
            reportParams.put("denuncias", false);
            reportParams.put("nroExpediente", (supervisionDTO.getOrdenServicioDTO().getExpediente().getNumeroExpediente()));
            reportParams.put("fechaInicioSupervision", (supervisionDTO.getFechaInicio()));
            reportParams.put("fechaTerminoSupervision", generarResultadoDTO.getFechaFin());
            reportParams.put("nroOrdenServicio", (supervisionDTO.getOrdenServicioDTO().getNumeroOrdenServicio()));
            reportParams.put("horaInicioSupervision", (supervisionDTO.getHoraInicio()));
            reportParams.put("horaTerminoSupervision", generarResultadoDTO.getHoraFin());
            reportParams.put("direccionOperativaAgente", generarResultadoDTO.getDireccionOperativa());
            reportParams.put("distritoDireccionOperativaAgente", general.getDescripcionDistrito());
            reportParams.put("provinciaDireccionOperativaAgente", general.getDescripcionProvincia());
            reportParams.put("departamentoDireccionOperativaAgente", general.getDescripcionDepartamento());
//            reportParams.put("razonSocialAgente", (unidadDTO.getEmpresaSupervisada().getRazonSocial() != null) ? unidadDTO.getEmpresaSupervisada().getRazonSocial() : "");
            reportParams.put("razonSocialAgente", (unidadDTO.getNombreUnidad() != null) ? unidadDTO.getNombreUnidad() : "");
//            reportParams.put("dniAgente", (unidadDTO.getEmpresaSupervisada().getTipoDocumentoIdentidad() != null && unidadDTO.getEmpresaSupervisada().getTipoDocumentoIdentidad().getCodigo().equals(Constantes.CODIGO_TIPO_DOCUMENTO_DNI)) ? unidadDTO.getEmpresaSupervisada().getNroIdentificacion() : "");
            reportParams.put("dniAgente", "");
//            reportParams.put("rucAgente", (unidadDTO.getEmpresaSupervisada().getRuc() != null) ? unidadDTO.getEmpresaSupervisada().getRuc() : "");
            reportParams.put("rucAgente", (unidadDTO.getRuc() != null) ? unidadDTO.getRuc() : "");
//            reportParams.put("telfAgente", (unidadDTO.getEmpresaSupervisada().getTelefono() != null) ? unidadDTO.getEmpresaSupervisada().getTelefono() : "");
            reportParams.put("telfAgente", "");
//            reportParams.put("emailAgente", (unidadDTO.getEmpresaSupervisada().getCorreoElectronico() != null) ? unidadDTO.getEmpresaSupervisada().getCorreoElectronico() : "");
            reportParams.put("emailAgente", "");
            reportParams.put("tipoActividadAgente",(unidadDTO.getActividad().getNombre() != null) ? unidadDTO.getActividad().getNombre()  : "");
            reportParams.put("codigoActividadAgente", (unidadDTO.getActividad().getCodigo() != null) ? unidadDTO.getActividad().getCodigo() : "");
            reportParams.put("registroHidrocarburosAgente", (unidadDTO.getNroRegistroHidrocarburo() != null) ? unidadDTO.getNroRegistroHidrocarburo() : "");
            reportParams.put("codigoOsinergminAgente", (unidadDTO.getCodigoOsinergmin()!= null) ? unidadDTO.getCodigoOsinergmin() : "");
            //reportParams.put("locador", (locadorDTO !=null && locadorDTO.getNombreCompleto() != null) ? locadorDTO.getNombreCompleto() : "");
            //Mostrar Locador o supervisora empresa
            //reportParams.put("locador", (locadorDTO !=null && locadorDTO.getNombreCompleto() != null) ? locadorDTO.getNombreCompleto() : "");
            if (locadorDTO !=null && locadorDTO.getNombreCompleto() != null)
            	reportParams.put("locador", (locadorDTO !=null && locadorDTO.getNombreCompleto() != null) ? locadorDTO.getNombreCompleto() : "-");
            else if (supervisionDTO.getOrdenServicioDTO()!=null && supervisionDTO.getOrdenServicioDTO().getSupervisoraEmpresa()!=null && supervisionDTO.getOrdenServicioDTO().getSupervisoraEmpresa().getRazonSocial()!=null)
            	reportParams.put("locador", (supervisionDTO.getOrdenServicioDTO().getSupervisoraEmpresa()!=null && supervisionDTO.getOrdenServicioDTO().getSupervisoraEmpresa().getRazonSocial()!=null) ? supervisionDTO.getOrdenServicioDTO().getSupervisoraEmpresa().getRazonSocial() : "-");
           
            reportParams.put("colegiatura", (locadorDTO !=null && locadorDTO.getNumeroColegiatura() != null) ? locadorDTO.getNumeroColegiatura() : "-");
            reportParams.put("dniLocador", (locadorDTO !=null && locadorDTO.getIdTipoDocumento()!= null && locadorDTO.getIdTipoDocumento().getCodigo().equals(Constantes.CODIGO_TIPO_DOCUMENTO_DNI) && locadorDTO.getNumeroDocumento() != null) ? locadorDTO.getNumeroDocumento() : "-");
            reportParams.put("emailLocador", (locadorDTO !=null && locadorDTO.getCorreoElectronicoInstitucion()!= null) ? locadorDTO.getCorreoElectronicoInstitucion() : "-");
            reportParams.put("telefonosLocador", (locadorDTO !=null && locadorDTO.getTelefonoContacto()!= null) ? locadorDTO.getTelefonoContacto() : "-");
            reportParams.put("faxLocador", "-");
            reportParams.put("observaciones", (ltamaestro.get(Constantes.PRIMERO_EN_LISTA).getDescripcion()!= null) ? ltamaestro.get(Constantes.PRIMERO_EN_LISTA).getDescripcion() : "");
        }
        return reportParams;
    }
    
    public DocumentoAdjuntoDTO registrarDocumentoBDDsr(InputStream reportJasper, Map<String, Object> reportParams, Long idSupervision, HttpServletRequest request,ReporteDTO reporteDTO,MaestroColumnaDTO TipoDocumentoDTO) throws DocumentoAdjuntoException{
        byte[] reporteBytes = null;
        DocumentoAdjuntoDTO registroDocumentoEjecucionMedida=null;
        try {
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportJasper, reportParams, new JREmptyDataSource());
            if (jasperPrint != null) {       
                reporteBytes = JasperExportManager.exportReportToPdf(jasperPrint);   
                
                DocumentoAdjuntoDTO documentoAdjuntoDTO = new DocumentoAdjuntoDTO();
                SupervisionDTO supervisionDTO = new SupervisionDTO();
                supervisionDTO.setIdSupervision(idSupervision);
                documentoAdjuntoDTO.setSupervision(supervisionDTO);
                documentoAdjuntoDTO.setArchivoAdjunto(reporteBytes);            
                documentoAdjuntoDTO.setNombreArchivo(reporteDTO.getNombreArchivo());
                documentoAdjuntoDTO.setDescripcionDocumento(reporteDTO.getDescripcionDocumento());
                if(TipoDocumentoDTO != null){
                   documentoAdjuntoDTO.setIdTipoDocumento(TipoDocumentoDTO); 
                }                
                //INSERT
                registroDocumentoEjecucionMedida = supervisionServiceNeg.registrarDocumentoEjecucionMedida(documentoAdjuntoDTO,  Constantes.getUsuarioDTO(request));
            }
        } catch (Exception ex) {
            LOG.error("Error registrarDocumentoBDDsr", ex);
            throw new DocumentoAdjuntoException(ex.getMessage(),ex);
        }
        return registroDocumentoEjecucionMedida;
    }
    /*OSINE_SFS-791 - RSIS 40 - Inicio */
    public DocumentoAdjuntoDTO registrarDocumentoBDGenerarResultadosDsr(InputStream reportJasper, Map<String, Object> reportParams, SupervisionDTO supervision, UsuarioDTO usuarioDTO,ReporteDTO reporteDTO,MaestroColumnaDTO tipoDocumento) throws DocumentoAdjuntoException{
        byte[] reporteBytes = null;
        DocumentoAdjuntoDTO registroDocumentoEjecucionMedida=null;
        try {
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportJasper, reportParams, new JREmptyDataSource());
            if (jasperPrint != null) {       
                reporteBytes = JasperExportManager.exportReportToPdf(jasperPrint);   
                
                DocumentoAdjuntoDTO documentoAdjuntoDTO = new DocumentoAdjuntoDTO();
                SupervisionDTO supervisionDTO = new SupervisionDTO();
                supervisionDTO.setIdSupervision(supervision.getIdSupervision());
                documentoAdjuntoDTO.setSupervision(supervisionDTO);
                documentoAdjuntoDTO.setArchivoAdjunto(reporteBytes);            
                documentoAdjuntoDTO.setNombreArchivo(reporteDTO.getNombreArchivo());
                documentoAdjuntoDTO.setDescripcionDocumento(reporteDTO.getDescripcionDocumento());
                documentoAdjuntoDTO.setIdTipoDocumento(tipoDocumento);
                //documentoAdjuntoDTO
                //INSERT
                registroDocumentoEjecucionMedida = supervisionServiceNeg.registrarDocumentoGenerarResultados(documentoAdjuntoDTO,  usuarioDTO);
            }
        } catch (Exception ex) {
            LOG.error("Error registrarDocumentoBDDsr", ex);
            throw new DocumentoAdjuntoException(ex.getMessage(),ex);
        }
        return registroDocumentoEjecucionMedida;
    }
    /*OSINE_SFS-791 - RSIS 40 - Fin */
    @SuppressWarnings("deprecation")
	public Map<String, Object> setearInformacionConstanciaRH(InputStream reportStreamImage, GenerarResultadoDTO generarResultadoDTO, RegistroHidrocarburoDTO registroHidrocarburoDTO) {
        Map<String, Object> reportParams = new HashMap<String, Object>();
        SupervisionFilter filtro = new SupervisionFilter();
        filtro.setIdSupervision(generarResultadoDTO.getIdSupervision());
        List<SupervisionDTO> supervisionDTOs = supervisionServiceNeg.buscarSupervisionReporte(filtro);
        SupervisionDTO supervisionDTO = new SupervisionDTO();
        supervisionDTO = supervisionDTOs.get(Constantes.PRIMERO_EN_LISTA);
        LOG.info("fechaInicio " + supervisionDTO.getFechaInicio());
        LOG.info("numeroOrdenServicio " + (supervisionDTO.getOrdenServicioDTO().getNumeroOrdenServicio()));
        LOG.info("numeroExpediente " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getNumeroExpediente()));
        LOG.info("idUnidadSupervisada " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getIdUnidadSupervisada()));
        LOG.info("codigoOsinerming " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getCodigoOsinergmin()));
        UnidadSupervisadaFilter unidadfilter = new UnidadSupervisadaFilter();
        unidadfilter.setIdUnidadSupervisada(supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getIdUnidadSupervisada());
        List<UnidadSupervisadaDTO> ltaUnidad = unidadSupervisadaServiceNeg.getUnidadSupervisadaDTO(unidadfilter);
        if (ltaUnidad.size() == Constantes.LISTA_UNICO_VALIR) {
            UnidadSupervisadaDTO unidadDTO = ltaUnidad.get(Constantes.PRIMERO_EN_LISTA);
            reportParams.put("rutaImagen", reportStreamImage);
            reportParams.put("nroExpediente", (supervisionDTO.getOrdenServicioDTO().getExpediente().getNumeroExpediente()));
            reportParams.put("oficinaRegional", "");
            reportParams.put("direccionOR", "");
            reportParams.put("telefonosOR", "");
//            reportParams.put("agente", (unidadDTO.getEmpresaSupervisada().getRazonSocial() != null) ? unidadDTO.getEmpresaSupervisada().getRazonSocial() : "");
            reportParams.put("agente", (unidadDTO.getNombreUnidad() != null) ? unidadDTO.getNombreUnidad() : "");
            reportParams.put("actividadAgente", (unidadDTO.getActividad().getNombre() != null) ? unidadDTO.getActividad().getNombre()  : "");                      
            reportParams.put("nroRegistro", (unidadDTO.getNroRegistroHidrocarburo() != null) ? unidadDTO.getNroRegistroHidrocarburo() : "");;
            reportParams.put("codigoOsinergmin",(unidadDTO.getCodigoOsinergmin()!= null) ? unidadDTO.getCodigoOsinergmin() : "");           
            //reportParams.put("fechaSuspension", Utiles.DateToString(registroHidrocarburoDTO.getFechaInicioSuspencion(), Constantes.FORMATO_FECHA_CORTA));                   
            //reportParams.put("horaSuspension", Utiles.DateToString(registroHidrocarburoDTO.getFechaInicioSuspencion(), Constantes.FORMATO_HORA_CORTA));
            reportParams.put("fechaSuspension", supervisionDTO.getFechaFin());                   
            reportParams.put("horaSuspension", supervisionDTO.getHoraFin());
            reportParams.put("suspension", true);
            reportParams.put("cancelacion", false);
            }
        return reportParams;
     }
    
    public DocumentoAdjuntoDTO ReporteActaSupervision(SupervisionDTO supervision,GenerarResultadoDTO generarResultadoDTO,HttpSession session,HttpServletRequest request,Boolean cierreTotal,Boolean cierreParcial,List<DetalleSupervisionDTO> ltadeta) throws DocumentoAdjuntoException{
        String rutaImagen = "";
        String rutaReportePrincipal="";
        String rutaReportePrincipal2="";
        String rutaReportePrincipal3="";
        InputStream reportStreamImage = null;
        InputStream reportStreamImage2 = null;
        InputStream reportStreamImage3 = null;
        InputStream reportJasper = null;
        InputStream reportJasper2 = null;
        InputStream reportJasper3 = null;
        ReporteDTO reporteDTO = new ReporteDTO();
        Map<String, Object> reportParams = null;
         Map<String, Object> reportParams2 = null;
         Map<String, Object> reportParams3 = null;
         DocumentoAdjuntoDTO registroDocumentoEjecucionMedida = null;
        try {
        ServletContext context = session.getServletContext();
        ServletContext context2 = session.getServletContext();
        ServletContext context3 = session.getServletContext();
        LOG.info("INICIANDO LA CONSTRUCCION DEL DOCUMENTO ACTA DE SUPERVISION");
        rutaImagen = Constantes.RUTA_IMG_CABECERA_GENERAR_RESULTADOS;
        rutaReportePrincipal = Constantes.RUTA_PLANTILLA_GENERAR_RESULTADOS_actaSupervision01;
        reporteDTO.setDescripcionDocumento(Constantes.DESCRIPCION_DOCUMENTO_ACTA_SUPERVISION);
        String hora = getHoraActual();
        String fechact = getFechaActual();
        reporteDTO.setNombreArchivo(Constantes.NOMBRE_ARCHIVO_ACTA_SUPERVISION +"_"+fechact+"_"+hora+"" + Constantes.EXTENSION_DOCUMENTO_ACTA_SUPERVISION);
        reportStreamImage = context.getResourceAsStream(rutaImagen);
        reportJasper = context.getResourceAsStream(rutaReportePrincipal);                   
        reportParams = setearInformacionActaSupervision1(reportStreamImage, generarResultadoDTO);
        //Acta de Supervision parte 02
        //if(supervision!=null && supervision.getFlagEjecucionMedida()!=null && supervision.getFlagEjecucionMedida().equals(Constantes.ESTADO_ACTIVO)){
            rutaReportePrincipal2 = Constantes.RUTA_PLANTILLA_GENERAR_RESULTADOS_actaSupervision02;    
            reportStreamImage2 = context2.getResourceAsStream(rutaImagen);
            reportJasper2 = context2.getResourceAsStream(rutaReportePrincipal2);        
            reportParams2 = setearInformacionActaSupervision2(reportStreamImage2, generarResultadoDTO,cierreTotal,cierreParcial);
        //}
        //Acta d Suprvision part 03
        rutaReportePrincipal3 = Constantes.RUTA_PLANTILLA_GENERAR_RESULTADOS_actaSupervision03;    
        reportStreamImage3 = context3.getResourceAsStream(rutaImagen);
        reportJasper3 = context3.getResourceAsStream(rutaReportePrincipal3);  
        reportParams3 = setearInformacionActaSupervision3(reportStreamImage3, generarResultadoDTO);
        
        registroDocumentoEjecucionMedida = registrarDocumentoActaSupervisionBDDsr(reportJasper,reportJasper2 ,reportJasper3 ,reportParams,reportParams2,reportParams3, generarResultadoDTO.getIdSupervision(), request, reporteDTO,ltadeta,generarResultadoDTO);
        LOG.info("SE REGISTRO EL DOCUMENTO DE ACTA DE SUPERVISION");
        }catch (Exception ex) {
            registroDocumentoEjecucionMedida = null;
            LOG.error("OCURRIO UN ERROR AL GENERAR EL DOCUMENTO DE ACTA DE SUPERVISION",ex);
            throw new DocumentoAdjuntoException(ex.getMessage(),ex);
        }
        return registroDocumentoEjecucionMedida;
    }
    
    public UbigeoDTO ObtenerUbigeo(UbigeoDTO ubi){
        UbigeoDTO ubigeneral = new UbigeoDTO();
        UbigeoFilter filtroDepa = new UbigeoFilter();
        filtroDepa.setIdDepartamento(ubi.getIdDepartamento());
        filtroDepa.setIdProvincia(Constantes.DEFAULT_PROVINCIA);
        filtroDepa.setIdDistrito(Constantes.DEFAULT_DISTRITO);
        List<UbigeoDTO> listaDepa = ubigeoServiceNeg.obtenerUbigeo(filtroDepa);
        if (listaDepa.size() == 1) {
            ubigeneral.setDescripcionDepartamento(listaDepa.get(Constantes.PRIMERO_EN_LISTA).getNombre());
            //ver provincia
            UbigeoFilter filtroProvi = new UbigeoFilter();
            filtroProvi.setIdDepartamento(ubi.getIdDepartamento());
            filtroProvi.setIdProvincia(ubi.getIdProvincia());
            filtroProvi.setIdDistrito(Constantes.DEFAULT_DISTRITO);
            List<UbigeoDTO> listaProvi = ubigeoServiceNeg.obtenerUbigeo(filtroProvi);
            if (listaProvi.size() == 1) {
                ubigeneral.setDescripcionProvincia(listaProvi.get(Constantes.PRIMERO_EN_LISTA).getNombre());
                //ver Distrito
                UbigeoFilter filtroDistri = new UbigeoFilter();
                filtroDistri.setIdDepartamento(ubi.getIdDepartamento());
                filtroDistri.setIdProvincia(ubi.getIdProvincia());
                filtroDistri.setIdDistrito(ubi.getIdDistrito());
                List<UbigeoDTO> listaDistri = ubigeoServiceNeg.obtenerUbigeo(filtroDistri);
                if (listaDistri.size() == 1) {
                    ubigeneral.setDescripcionDistrito(listaDistri.get(Constantes.PRIMERO_EN_LISTA).getNombre());
                }else{
                    ubigeneral.setDescripcionDistrito(Constantes.CONSTANTE_VACIA);
                }
            } else {
                ubigeneral.setDescripcionProvincia(Constantes.CONSTANTE_VACIA);
                ubigeneral.setDescripcionDistrito(Constantes.CONSTANTE_VACIA);
            }
        } else {
            ubigeneral.setDescripcionDepartamento(Constantes.CONSTANTE_VACIA);
            ubigeneral.setDescripcionProvincia(Constantes.CONSTANTE_VACIA);
            ubigeneral.setDescripcionDistrito(Constantes.CONSTANTE_VACIA);
        }
        return ubigeneral;
    }
    
    public Map<String, Object> setearInformacionActaSupervision1(InputStream reportStreamImage, GenerarResultadoDTO generarResultadoDTO) {
        Map<String, Object> reportParams = new HashMap<String, Object>();
        SupervisionFilter filtro = new SupervisionFilter();
        filtro.setIdSupervision(generarResultadoDTO.getIdSupervision());
        List<SupervisionDTO> supervisionDTOs = supervisionServiceNeg.buscarSupervisionReporte(filtro);
        SupervisionDTO supervisionDTO = new SupervisionDTO();
        supervisionDTO = supervisionDTOs.get(Constantes.PRIMERO_EN_LISTA);
        LOG.info("fechaInicio " + supervisionDTO.getFechaInicio());
        LOG.info("numeroOrdenServicio " + (supervisionDTO.getOrdenServicioDTO().getNumeroOrdenServicio()));
        LOG.info("numeroExpediente " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getNumeroExpediente()));
        LOG.info("idUnidadSupervisada " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getIdUnidadSupervisada()));
        LOG.info("codigoOsinerming " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getCodigoOsinergmin()));
        UnidadSupervisadaFilter unidadfilter = new UnidadSupervisadaFilter();
        unidadfilter.setIdUnidadSupervisada(supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getIdUnidadSupervisada());
        UbigeoDTO filter = new UbigeoDTO();
        filter.setIdDepartamento(generarResultadoDTO.getIdDepartamento());
        filter.setIdProvincia(generarResultadoDTO.getIdProvincia());
        filter.setIdDistrito(generarResultadoDTO.getIdDistrito());
        UbigeoDTO general = ObtenerUbigeo(filter);        
        String DescripcionAgente = "";
        if(supervisionDTO.getOrdenServicioDTO().getSupervisoraEmpresa()!= null){
            if(supervisionDTO.getOrdenServicioDTO().getSupervisoraEmpresa().getIdSupervisoraEmpresa()!= null && supervisionDTO.getOrdenServicioDTO().getSupervisoraEmpresa().getRazonSocial()!= null){
                DescripcionAgente = supervisionDTO.getOrdenServicioDTO().getSupervisoraEmpresa().getRazonSocial();
            }
        }else{
            if(supervisionDTO.getOrdenServicioDTO().getLocador()!= null){
                if(supervisionDTO.getOrdenServicioDTO().getLocador().getIdLocador()!= null && supervisionDTO.getOrdenServicioDTO().getLocador().getNombreCompleto()!= null){
                    DescripcionAgente = supervisionDTO.getOrdenServicioDTO().getLocador().getNombreCompleto();
                }
            }
        }
        List<UnidadSupervisadaDTO> ltaUnidad = unidadSupervisadaServiceNeg.getUnidadSupervisadaDTO(unidadfilter);
        if (ltaUnidad.size() == Constantes.LISTA_UNICO_VALIR) {
            UnidadSupervisadaDTO unidadDTO = ltaUnidad.get(Constantes.PRIMERO_EN_LISTA);
            reportParams.put("rutaImagen", reportStreamImage);
            reportParams.put("nroExpediente", (supervisionDTO.getOrdenServicioDTO().getExpediente().getNumeroExpediente()));
            reportParams.put("oficinaRegional", "");
            reportParams.put("direccionOR", "");
            reportParams.put("telefonosOR", "");
            reportParams.put("fechaInicioSupervision", supervisionDTO.getFechaInicio());                   
            reportParams.put("horaInicioSupervision",supervisionDTO.getHoraInicio());           
            reportParams.put("horaTerminoSupervision",supervisionDTO.getHoraFin());           
//            reportParams.put("agente", (unidadDTO.getEmpresaSupervisada().getRazonSocial() != null) ? unidadDTO.getEmpresaSupervisada().getRazonSocial() : "");
            reportParams.put("agente", (unidadDTO.getNombreUnidad() != null) ? unidadDTO.getNombreUnidad() : "");
//            reportParams.put("rucAgente", (unidadDTO.getEmpresaSupervisada().getRuc() != null) ? unidadDTO.getEmpresaSupervisada().getRuc() : "");
            reportParams.put("rucAgente", (unidadDTO.getRuc() != null) ? unidadDTO.getRuc() : "");
            reportParams.put("direccionOperativaAgente", generarResultadoDTO.getDireccionOperativa());
            reportParams.put("distritoDireccionOperativaAgente", general.getDescripcionDistrito());
            reportParams.put("provinciaDireccionOperativaAgente", general.getDescripcionProvincia());
            reportParams.put("departamentoDireccionOperativaAgente", general.getDescripcionDepartamento());
            reportParams.put("registroHidrocarburosAgente", (unidadDTO.getNroRegistroHidrocarburo() != null) ? unidadDTO.getNroRegistroHidrocarburo() : "");
            reportParams.put("empresaSupervisora", DescripcionAgente);
        }
        return reportParams;
    }
    
    public Map<String, Object> setearInformacionActaSupervision2(InputStream reportStreamImage, GenerarResultadoDTO generarResultadoDTO,Boolean cierreTotal,Boolean cierreParcial) {
        Map<String, Object> reportParams = new HashMap<String, Object>();
        SupervisionFilter filtro = new SupervisionFilter();
        filtro.setIdSupervision(generarResultadoDTO.getIdSupervision());
        List<SupervisionDTO> supervisionDTOs = supervisionServiceNeg.buscarSupervisionReporte(filtro);
        SupervisionDTO supervisionDTO = new SupervisionDTO();
        supervisionDTO = supervisionDTOs.get(Constantes.PRIMERO_EN_LISTA);
        LOG.info("fechaInicio " + supervisionDTO.getFechaInicio());
        LOG.info("numeroOrdenServicio " + (supervisionDTO.getOrdenServicioDTO().getNumeroOrdenServicio()));
        LOG.info("numeroExpediente " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getNumeroExpediente()));
        LOG.info("idUnidadSupervisada " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getIdUnidadSupervisada()));
        LOG.info("codigoOsinerming " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getCodigoOsinergmin()));
        reportParams.put("rutaImagen", reportStreamImage);
        reportParams.put("nroExpediente", (supervisionDTO.getOrdenServicioDTO().getExpediente().getNumeroExpediente()));
        reportParams.put("oficinaRegional", "");
        reportParams.put("direccionOR", "");
        reportParams.put("telefonosOR", "");
        reportParams.put("cierreTotal", cierreTotal);
        reportParams.put("cierreParcial", cierreParcial);

        return reportParams;
    }
    
    public Map<String, Object> setearInformacionActaSupervision3(InputStream reportStreamImage, GenerarResultadoDTO generarResultadoDTO) {
        Map<String, Object> reportParams = new HashMap<String, Object>();
        SupervisionFilter filtro = new SupervisionFilter();
        filtro.setIdSupervision(generarResultadoDTO.getIdSupervision());
        List<SupervisionDTO> supervisionDTOs = supervisionServiceNeg.buscarSupervisionReporte(filtro);
        SupervisionDTO supervisionDTO = new SupervisionDTO();
        supervisionDTO = supervisionDTOs.get(Constantes.PRIMERO_EN_LISTA);
        LOG.info("fechaInicio " + supervisionDTO.getFechaInicio());
        LOG.info("numeroOrdenServicio " + (supervisionDTO.getOrdenServicioDTO().getNumeroOrdenServicio()));
        LOG.info("numeroExpediente " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getNumeroExpediente()));
        LOG.info("idUnidadSupervisada " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getIdUnidadSupervisada()));
        LOG.info("codigoOsinerming " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getCodigoOsinergmin()));
        SupervisionPersonaGralFilter filtroprgn = new SupervisionPersonaGralFilter();
        LOG.info("enviando idSupervision |"+supervisionDTO.getIdSupervision()+"|");
        filtroprgn.setIdSupervision(supervisionDTO.getIdSupervision());
        List<SupervisionPersonaGralDTO> ltaSupPrson  = new ArrayList<SupervisionPersonaGralDTO>();
        String prsonarcpcion = "";
        String dniprsonarcpcion = "";
        String rlacionagnt = "";
         try {
             ltaSupPrson = supervisionPersonaGralServiceNeg.find(filtroprgn);
         } catch (SupervisionPersonaGralException ex) {
             ltaSupPrson = new ArrayList<SupervisionPersonaGralDTO>();
         }
         LOG.info("hay persona nro |"+ltaSupPrson.size()+"|");
         if(ltaSupPrson.size() > Constantes.LISTA_VACIA){
             PersonaGeneralDTO prDTO = ltaSupPrson.get(Constantes.PRIMERO_EN_LISTA).getPersonaGeneral();
             prsonarcpcion = ""+prDTO.getNombresPersona()+" "+prDTO.getApellidoPaternoPersona()+" "+prDTO.getApellidoMaternoPersona();
             dniprsonarcpcion = ""+prDTO.getNumeroDocumento();
             rlacionagnt = ltaSupPrson.get(Constantes.PRIMERO_EN_LISTA).getCargo().getDescripcion();
             String texto = "La presente diligencia se atendi\u00f3 con " + prsonarcpcion + ", identificado con DNI N° " + dniprsonarcpcion + ", quien manifest\u00f3 ser " + rlacionagnt + " .";
             reportParams.put("personaRecepcionVisitaTotal", texto);
         }else{
             String texto = "En la presente diligencia, la persona que atendi\u00f3 no quiso identificarse.";
             reportParams.put("personaRecepcionVisitaTotal", texto);
         }
        reportParams.put("rutaImagen", reportStreamImage);
        reportParams.put("nroExpediente", (supervisionDTO.getOrdenServicioDTO().getExpediente().getNumeroExpediente()));
        reportParams.put("oficinaRegional", "");
        reportParams.put("direccionOR", "");
        reportParams.put("telefonosOR", "");
        //reportParams.put("personaRecepcionVisita", prsonarcpcion);
        //reportParams.put("dniPersonaRecepcionVisita", dniprsonarcpcion);
        //reportParams.put("relacionAgente", rlacionagnt);
        return reportParams;
    }
    
    public DocumentoAdjuntoDTO registrarDocumentoActaSupervisionBDDsr(InputStream reportJasperActa1,InputStream reportJasperActa2,InputStream reportJasperActa3,Map<String, Object> reportParams,Map<String, Object> reportParams2,Map<String, Object> reportParams3, Long idSupervision, HttpServletRequest request,ReporteDTO reporteDTO,List<DetalleSupervisionDTO> ltaDetalle,GenerarResultadoDTO generarResultadoDTO) throws DocumentoAdjuntoException{
        byte[] reporteBytes = null;
        List<JasperPrint> jasperPrints = new ArrayList<JasperPrint>();
        DocumentoAdjuntoDTO registroDocumentoEjecucionMedida=null;
        try{
            //aca seteamos las obligaciones con prioridades menores a 15
            List<ReporteActaSupervisionDTO> milistapart1 = new ArrayList<ReporteActaSupervisionDTO>();
            List<ReporteActaSupervisionDTO> milistapart2 = new ArrayList<ReporteActaSupervisionDTO>();        
            List<ReporteMedidaDTO> ltajcucion = new ArrayList<ReporteMedidaDTO>();
            int conta = 0;
            for (DetalleSupervisionDTO midetalle : ltaDetalle) {
                int aux = Integer.parseInt("" + midetalle.getPrioridad());
                ReporteActaSupervisionDTO reportActSup = new ReporteActaSupervisionDTO();
                reportActSup.setIndice("" + aux);
                reportActSup.setInfraccion("" + ArmarDescripcionInfraccionReporte(midetalle));
                reportActSup.setBaseLegal(""+ArmarBaseLegalReporte(midetalle));
                reportActSup.setComentario(""+ArmarComentarioReporte(midetalle));
                if (generarResultadoDTO.getIdentificadorEjecucionMedi().equals("siEjecMed")) {
                    ReporteMedidaDTO objto = new ReporteMedidaDTO(); 
                    objto.setDescripcionMedida("" + ArmarDescripcionejecucionMedidaReporte(midetalle));
                    ltajcucion.add(objto);
                    conta++;
                }
                LOG.info("PRIORIDAD ES : |"+aux+"|");
                if (aux < Constantes.NRO_INFRACCIONES_PART1_ACTA_SUPERVISION) {
                    LOG.info("INSERTO EN PRIMERA LISTA PRIORIDAD ES : |"+aux+"|");
                    milistapart1.add(reportActSup);
                } else {
                    LOG.info("INSERTO EN SEGUNDA LISTA PRIORIDAD ES : |"+aux+"|");
                    milistapart2.add(reportActSup);
                }
            }
            if (conta == 0) {
                ReporteMedidaDTO objto = new ReporteMedidaDTO();
                objto.setDescripcionMedida("  ");
                ltajcucion.add(objto);
            }
            if(milistapart1.size() == Constantes.LISTA_VACIA){
                ReporteActaSupervisionDTO reportActSup = new ReporteActaSupervisionDTO();
                reportActSup.setIndice(" ");
                reportActSup.setInfraccion(" ");
                reportActSup.setBaseLegal(" ");
                reportActSup.setComentario(" ");
                milistapart1.add(reportActSup);
            }
            if(milistapart2.size() == Constantes.LISTA_VACIA){
                ReporteActaSupervisionDTO reportActSup = new ReporteActaSupervisionDTO();
                reportActSup.setIndice(" ");
                reportActSup.setInfraccion(" ");
                reportActSup.setBaseLegal(" ");
                reportActSup.setComentario(" ");
                milistapart2.add(reportActSup);
            }
            JRDataSource listaDS = new JRBeanCollectionDataSource(milistapart1);
            JRDataSource listaDS2 = new JRBeanCollectionDataSource(milistapart2);
            JRDataSource listaDSltajcucion = new JRBeanCollectionDataSource(ltajcucion);            
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportJasperActa1, reportParams, listaDS);
            //JasperPrint jasperPrint2=null;
            LOG.info("LA LISTA TIENE |"+milistapart2.size()+"| elementos");
            //if(reportJasper2!=null && reportParams2!=null && milistapart2!=null && milistapart2.size()!=0){
            JasperPrint jasperPrint2 = JasperFillManager.fillReport(reportJasperActa2, reportParams2, listaDS2);
            //}
            JasperPrint jasperPrint3 = JasperFillManager.fillReport(reportJasperActa3, reportParams3, listaDSltajcucion);
            jasperPrints.add(jasperPrint);
            if(jasperPrint2!=null){
                jasperPrints.add(jasperPrint2);
            }
            jasperPrints.add(jasperPrint3);

            if (jasperPrint != null) {      
                JRPdfExporter exporter = new JRPdfExporter();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrints);
                exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");            
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
                exporter.exportReport();
                reporteBytes = out.toByteArray();

                DocumentoAdjuntoDTO documentoAdjuntoDTO = new DocumentoAdjuntoDTO();
                SupervisionDTO supervisionDTO = new SupervisionDTO();
                supervisionDTO.setIdSupervision(idSupervision);
                documentoAdjuntoDTO.setSupervision(supervisionDTO);
                documentoAdjuntoDTO.setArchivoAdjunto(reporteBytes);            
                documentoAdjuntoDTO.setNombreArchivo(reporteDTO.getNombreArchivo());
                documentoAdjuntoDTO.setDescripcionDocumento(reporteDTO.getDescripcionDocumento());
                MaestroColumnaDTO TipoDocumentoActaSupDTO = maestroColumnaServiceNeg.findMaestroColumnaByDominioAplicDesc(Constantes.DOMINIO_TIP_DOC_SUP_SIGED, Constantes.APLICACION_INPS, Constantes.DESCRIPCION_TIP_DOC_SUP_SIGED_ACTA_EJECUCION_MEDIDA_SEGURIDAD).get(0);
                if(TipoDocumentoActaSupDTO != null ){
                    documentoAdjuntoDTO.setIdTipoDocumento(TipoDocumentoActaSupDTO);
                }
                registroDocumentoEjecucionMedida = supervisionServiceNeg.registrarDocumentoEjecucionMedida(documentoAdjuntoDTO,  Constantes.getUsuarioDTO(request));
            }
        } catch (Exception ex) {
            LOG.error("Error registrarDocumentoBDDsr", ex);
            throw new DocumentoAdjuntoException(ex.getMessage(),ex);
        }
        return registroDocumentoEjecucionMedida;
    }
    
    public String ArmarDescripcionInfraccionReporte(DetalleSupervisionDTO midetalle) {
        String caddscripcion = "INFRACCION:\n";
//        if (!midetalle.getObligacion().getInfraccion().getDescripcionInfraccion().equals(Constantes.CONSTANTE_VACIA)) {
//            caddscripcion += "- " + midetalle.getObligacion().getInfraccion().getDescripcionInfraccion() + " \n\n";
//        }
        if (!midetalle.getInfraccion().getDescripcionInfraccion().equals(Constantes.CONSTANTE_VACIA)) {
            caddscripcion += "- " + midetalle.getInfraccion().getDescripcionInfraccion() + " \n\n";
        }
        int conta = 0;
        String cadaux = "ESCENARIOS INCUMPLIDOS:\n";
        try {
            EscenarioIncumplidoFilter filtroobj = new EscenarioIncumplidoFilter();
            filtroobj.setIdDetalleSupervision(midetalle.getIdDetalleSupervision());
            List<EscenarioIncumplidoDTO> ltaEsIncumplido = escenarioIncumplidoServiceNeg.findEscenarioIncumplido(filtroobj);
            for (EscenarioIncumplidoDTO escenarioIncumplidoDTO : ltaEsIncumplido) {
                cadaux += "- " + escenarioIncumplidoDTO.getEscenarioIncumplimientoDTO().getIdEsceIncuMaestro().getDescripcion() + " \n";
                conta++;
            }
        } catch (EscenarioIncumplidoException ex) {
            LOG.error("Ocurrio un error",ex);
        }
        if (conta == 0) {
            cadaux = "";
        }
        return caddscripcion + cadaux;
    }
    
    public String ArmarBaseLegalReporte(DetalleSupervisionDTO midetalle) {
        String cadaux = "";
            ObligacionBaseLegalFilter filtro = new ObligacionBaseLegalFilter();
            filtro.setIdObligacion(midetalle.getObligacion().getIdObligacion());
            List<ObligacionBaseLegalDTO> lta = new ArrayList<ObligacionBaseLegalDTO>();
        try {
            
            lta = obligacionBaseLegalServiceNeg.getObligacionBaseLegal(filtro);
            for (ObligacionBaseLegalDTO obligacionBaseLegalDTO : lta) {
               cadaux+= "- "+ obligacionBaseLegalDTO.getBaseLegalDTO().getDescripcionGeneralBaseLegal()+".\n";
            }
        } catch (ObligacionBaseLegalException ex) {
          LOG.error("Ocurrio Un error",ex);
        }
        return cadaux;
    }
    
    public String ArmarComentarioReporte(DetalleSupervisionDTO midetalle) throws ComplementoDetSupervisionException {
        int conta = 0;
        String cadaux = "\n";
        try {
            ComentarioDetSupervisionFilter filtroobj = new ComentarioDetSupervisionFilter();
            filtroobj.setFlagOrderByDescripcion(Constantes.ESTADO_ACTIVO);
            filtroobj.setIdDetalleSupervision(midetalle.getIdDetalleSupervision());            
            List<ComentarioDetSupervisionDTO> ltaComntIncumpl =comentarioDetSupervisionServiceNeg.findComentarioDetSupervision(filtroobj);
            for (ComentarioDetSupervisionDTO comentariodetsupervisionDTO : ltaComntIncumpl) {
                
                /* OSINE_SFS-791 - mdiosesf - Inicio */
            	String descripcion=comentariodetsupervisionDTO.getComentarioIncumplimiento().getDescripcion();            	
                descripcion= "- "+descripcion+"\n";
                List<ComplementoDetSupervisionDTO> lstCompDetSup=complementoDetSupervisionServiceNeg.findComplementoDetSupervision(new ComplementoDetSupervisionFilter(comentariodetsupervisionDTO.getIdComentarioDetSupervision()));
            	if(!CollectionUtils.isEmpty(lstCompDetSup)){
            		for(ComplementoDetSupervisionDTO regCompDetSup : lstCompDetSup){
                        List<ComplementoDetSupValorDTO> lstCompDetSupValor = complementoDetSupervisionServiceNeg.findComplementoDetSupValor(new ComplementoDetSupValorFilter(regCompDetSup.getIdComplementoDetSupervision()));
                        if(!CollectionUtils.isEmpty(lstCompDetSupValor)){
                            String comDetSup="";
                            for(ComplementoDetSupValorDTO cdsv : lstCompDetSupValor){
                                comDetSup=comDetSup+cdsv.getValorDesc()+", ";
                            }
                            comDetSup=comDetSup.substring(0, comDetSup.length()-2);
                            if(regCompDetSup.getComentarioComplemento()!=null 
                                && regCompDetSup.getComentarioComplemento().getComplemento()!=null 
                                && regCompDetSup.getComentarioComplemento().getComplemento().getEtiquetaComentario()!=null){
                                descripcion=descripcion.replace(regCompDetSup.getComentarioComplemento().getComplemento().getEtiquetaComentario(),comDetSup);
                            }
                        }                                        
                    }
            	}            	
            	cadaux += " " +descripcion;
            	/* OSINE_SFS-791 - mdiosesf - Fin */
            	//cadaux += " " + comentariodetsupervisionDTO.getComentarioIncumplimiento().getDescripcion()+ ".";
                conta++;
            }
        } catch (Exception ex) {            
            LOG.error("Ocurrio un error",ex);
            throw new ComplementoDetSupervisionException(ex.getMessage(),ex);
        }
        if (conta == 0) {
            cadaux = "";
        }
        return cadaux;
    }
    
    public String ArmarDescripcionejecucionMedidaReporte(DetalleSupervisionDTO midetalle) {
        String caddscripcion = "";
        if (midetalle.getComentario() != null) {
            caddscripcion = "" + midetalle.getComentario() + ". ";
        } 
        int conta = 0;
        String cadaux = "";
        try {
            EscenarioIncumplidoFilter filtroobj = new EscenarioIncumplidoFilter();
            filtroobj.setIdDetalleSupervision(midetalle.getIdDetalleSupervision());
            List<EscenarioIncumplidoDTO> ltaEsIncumplido = escenarioIncumplidoServiceNeg.findEscenarioIncumplido(filtroobj);
            for (EscenarioIncumplidoDTO escenarioIncumplidoDTO : ltaEsIncumplido) {
                cadaux += " " + escenarioIncumplidoDTO.getComentarioEjecucion() + ".";
                conta++;
            }
        } catch (EscenarioIncumplidoException ex) {
            LOG.error("Ocurrio un error",ex);
        }
        if (conta == 0) {
            cadaux = "";
        }
        return caddscripcion + cadaux;
    }
 
    //CORREO1
    public String EnvioNotificacionesSupervisionDsrMedidaSeguridad(UbigeoDTO ubigeo,OrdenServicioDTO ordenServicioDTO){
        LOG.info("EnvioNotificacionesSupervisionDsrMedidaSeguridad - inicio");
        String msjeCorreo = "";
        try {
            //Envio de correo para Medida Seguridad
            DestinatarioCorreoFilter filtro1 = new DestinatarioCorreoFilter();
            filtro1.setCodigoFuncionalidadCorreo(Constantes.CODIGO_FUNCIONALIDAD_TF006);
            filtro1.setIdDepartamento(ubigeo.getIdDepartamento());
            filtro1.setIdProvincia(ubigeo.getIdProvincia());
            filtro1.setIdDistrito(ubigeo.getIdDistrito());
            List<DestinatarioCorreoDTO> milistaDestinos = destinatarioCorreoServiceNeg.getDestinatarioCorreobyUbigeo(filtro1);
            if(CollectionUtils.isEmpty(milistaDestinos)){
                throw new CorreoException("Error, No existen destinatarios de Correo.",null);
            }
            ExpedienteDTO expedienteDTO = new ExpedienteDTO();
            expedienteDTO.setIdExpediente(ordenServicioDTO.getExpediente().getIdExpediente());
            expedienteDTO.setNumeroExpediente(ordenServicioDTO.getExpediente().getNumeroExpediente());
            expedienteDTO.setOrdenServicio(ordenServicioDTO);
            boolean rptaCorreo = correoServiceNeg.enviarCorreoNotificacionMedidaSeguridad(milistaDestinos, expedienteDTO);
            if (!rptaCorreo) {
                msjeCorreo = "No envio correo MedidaSeguridad.";
            }
        } catch (Exception e) {
            LOG.error("Error en EnvioNotificacionesSupervisionDsrMedidaSeguridad", e);
        }
        LOG.info("EnvioNotificacionesSupervisionDsrMedidaSeguridad - fin");
        return msjeCorreo;
    }
    
    //CORREO2
    public String EnvioNotificacionesSupervisionDsrSuspensionRH(UbigeoDTO ubigeo,OrdenServicioDTO ordenServicioDTO,RegistroHidrocarburoDTO registroHidrocarburoDTO){
        LOG.info("EnvioNotificacionesSupervisionDsrSuspensionRH - inicio");
        String msjeCorreo = "";
        try {
            //Envio de correo para Suspension RH
            DestinatarioCorreoFilter filtro1 = new DestinatarioCorreoFilter();
            filtro1.setCodigoFuncionalidadCorreo(Constantes.CODIGO_FUNCIONALIDAD_TF007);
            filtro1.setIdDepartamento(ubigeo.getIdDepartamento());
            filtro1.setIdProvincia(ubigeo.getIdProvincia());
            filtro1.setIdDistrito(ubigeo.getIdDistrito());
            List<DestinatarioCorreoDTO> milistaDestinos = destinatarioCorreoServiceNeg.getDestinatarioCorreobyUbigeo(filtro1);
            if(CollectionUtils.isEmpty(milistaDestinos)){
                throw new CorreoException("Error, No existen destinatarios de Correo.",null);
            }
            ExpedienteDTO expedienteDTO = new ExpedienteDTO();
            expedienteDTO.setIdExpediente(ordenServicioDTO.getExpediente().getIdExpediente());
            expedienteDTO.setNumeroExpediente(ordenServicioDTO.getExpediente().getNumeroExpediente());
            expedienteDTO.setOrdenServicio(ordenServicioDTO);
            boolean rptaCorreo = correoServiceNeg.enviarCorreoNotificacionSuspensionRH(milistaDestinos, expedienteDTO, registroHidrocarburoDTO);
            if (!rptaCorreo) {
                msjeCorreo = "No envio correo SuspensionRH.";
            }
        } catch (Exception e) {
            LOG.error("Error en EnvioNotificacionesSupervisionDsrSuspensionRH", e);
        }
        LOG.info("EnvioNotificacionesSupervisionDsrSuspensionRH - fin");
        return msjeCorreo;
    }
    
    //CORREO3
    public String EnvioNotificacionesSupervisionDsrDesactivarRolComprasScop(UbigeoDTO ubigeo,OrdenServicioDTO ordenServicioDTO,RegistroHidrocarburoDTO registroHidrocarburoDTO,  UnidadSupervisadaDTO unidadSupervisadaDTO) {
        LOG.info("EnvioNotificacionesSupervisionDsrDesactivarRolComprasScop - inicio");
        String msjeCorreo = "";
        try {
            //Envio de correo para Desactivar Rol Compras Scop
            DestinatarioCorreoFilter filtro1 = new DestinatarioCorreoFilter();
            filtro1.setCodigoFuncionalidadCorreo(Constantes.CODIGO_FUNCIONALIDAD_TF008);
            filtro1.setIdDepartamento(ubigeo.getIdDepartamento());
            filtro1.setIdProvincia(ubigeo.getIdProvincia());
            filtro1.setIdDistrito(ubigeo.getIdDistrito());
            List<DestinatarioCorreoDTO> milistaDestinos = destinatarioCorreoServiceNeg.getDestinatarioCorreobyUbigeo(filtro1);
            if(CollectionUtils.isEmpty(milistaDestinos)){
                throw new CorreoException("Error, No existen destinatarios de Correo.",null);
            }
            ExpedienteDTO expedienteDTO = new ExpedienteDTO();
            expedienteDTO.setIdExpediente(ordenServicioDTO.getExpediente().getIdExpediente());
            expedienteDTO.setNumeroExpediente(ordenServicioDTO.getExpediente().getNumeroExpediente());
            expedienteDTO.setOrdenServicio(ordenServicioDTO);
            boolean rptaCorreo = correoServiceNeg.enviarCorreoNotificacionDesactivarRolComprasScop(milistaDestinos, expedienteDTO, registroHidrocarburoDTO, unidadSupervisadaDTO);
            if (!rptaCorreo) {
                msjeCorreo = "No envio correo DesactivarRolComprasScop.";
            }
        } catch (Exception e) {
            LOG.error("Error en EnvioNotificacionesSupervisionDsrDesactivarRolComprasScop", e);
        }
        LOG.info("EnvioNotificacionesSupervisionDsrDesactivarRolComprasScop - fin");
        return msjeCorreo;
    }
    
    //CORREO4
    public String EnvioNotificacionesSupervisionDsrDesactivarRolComprasScopProducto(UbigeoDTO ubigeo,OrdenServicioDTO ordenServicioDTO,RegistroHidrocarburoDTO registroHidrocarburoDTO,  UnidadSupervisadaDTO unidadSupervisadaDTO,HttpSession session,SupervisionDTO supervisionDTO) {
        LOG.info("EnvioNotificacionesSupervisionDsrDesactivarRolComprasScopProducto - inicio");
        String msjeCorreo = "";
        try {
            ServletContext context = session.getServletContext();
            InputStream reportJasper = null;
            String rutaReportePrincipal = Constantes.RUTA_PLANTILLA_GENERAR_RESULTADOS_EXCEL_PRODUCTOS;
            reportJasper = context.getResourceAsStream(rutaReportePrincipal);
            byte[] reporteBytes = null;
            List<ReporteProductoDTO> ltaproducto = new ArrayList<ReporteProductoDTO>();
            ProductoDsrScopFilter filtr = new ProductoDsrScopFilter();
            filtr.setIdSupervision(supervisionDTO.getIdSupervision());
            int conta = 0;
            List<ProductoDsrScopDTO> ltapro = productoSuspenderServiceNeg.getProductoSuspender(filtr);
            for (ProductoDsrScopDTO productoDsrScopDTO : ltapro) {
               ReporteProductoDTO obj1 = new ReporteProductoDTO();
               obj1.setDescripcionProducto(""+productoDsrScopDTO.getProductodto().getNombreLargo());
               obj1.setFechaActa(supervisionDTO.getFechaInicio());
               obj1.setNroExpediente(ordenServicioDTO.getExpediente().getNumeroExpediente());
               obj1.setNroActa(ordenServicioDTO.getExpediente().getNumeroExpediente());
               obj1.setRazonSocial(unidadSupervisadaDTO.getNombreUnidad());
               obj1.setRegistroRH(registroHidrocarburoDTO.getNumeroRegistroHidrocarburo());
               ltaproducto.add(obj1);
               conta++;
            }       
            if(conta == 0){
               ReporteProductoDTO obj1 = new ReporteProductoDTO();
               obj1.setDescripcionProducto("");
               obj1.setFechaActa("");
               obj1.setNroExpediente("");
               obj1.setNroActa("");
               obj1.setRazonSocial("");
               obj1.setRegistroRH("");
               ltaproducto.add(obj1);
            }
            JRDataSource listaD3 = new JRBeanCollectionDataSource(ltaproducto);
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportJasper, null, listaD3);
            if (jasperPrint != null) {
                JRXlsExporter exporterXLS = new JRXlsExporter();                
                ByteArrayOutputStream out = new ByteArrayOutputStream();        
                exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
                exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, out);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
                exporterXLS.exportReport();
                //aca ya trae el excel en formato bytes
                reporteBytes = out.toByteArray();
            }
            //Envio de correo para Desactivar Rol Compras Scop
            DestinatarioCorreoFilter filtro1 = new DestinatarioCorreoFilter();
            filtro1.setCodigoFuncionalidadCorreo(Constantes.CODIGO_FUNCIONALIDAD_TF009);
            filtro1.setIdDepartamento(ubigeo.getIdDepartamento());
            filtro1.setIdProvincia(ubigeo.getIdProvincia());
            filtro1.setIdDistrito(ubigeo.getIdDistrito());
            List<DestinatarioCorreoDTO> milistaDestinos = destinatarioCorreoServiceNeg.getDestinatarioCorreobyUbigeo(filtro1);
            if(CollectionUtils.isEmpty(milistaDestinos)){
                throw new CorreoException("Error, No existen destinatarios de Correo.",null);
            }
            ExpedienteDTO expedienteDTO = new ExpedienteDTO();
            expedienteDTO.setIdExpediente(ordenServicioDTO.getExpediente().getIdExpediente());
            expedienteDTO.setNumeroExpediente(ordenServicioDTO.getExpediente().getNumeroExpediente());
            expedienteDTO.setOrdenServicio(ordenServicioDTO);
            boolean rptaCorreo = correoServiceNeg.enviarCorreoNotificacionDesactivarRolComprasScopProducto(milistaDestinos, expedienteDTO, registroHidrocarburoDTO, unidadSupervisadaDTO,reporteBytes);
            if (!rptaCorreo) {
                msjeCorreo = "No envio correo DesactivarRolComprasScopProducto.";
            }
        } catch (Exception e) {
            LOG.error("Error en EnvioNotificacionesSupervisionDsrDesactivarRolComprasScopProducto", e);
        }
        LOG.info("EnvioNotificacionesSupervisionDsrDesactivarRolComprasScopProducto - fin");
        return msjeCorreo;
    }

    /*OSINE_SFS-791 - RSIS 41 - Inicio */
    public List<DocumentoAdjuntoDTO> verDocumentosSupervisionGenerarResultadosNoIniciadaInicial(Long idSupervision){
        List<DocumentoAdjuntoDTO> listaprincipal = new ArrayList<DocumentoAdjuntoDTO>();
        
        //para carta de visita para Supervision No Iniciada Inicial
        DocumentoAdjuntoFilter filtroGRNoIniciada = new DocumentoAdjuntoFilter();
        MaestroColumnaDTO idTipoDocGRNoIniciada = maestroColumnaServiceNeg.findMaestroColumnaByDominioAplicDesc(Constantes.DOMINIO_TIP_DOC_SUP_SIGED, Constantes.APLICACION_INPS, Constantes.DESCRIPCION_TIP_DOC_SUP_SIGED_CARTA_VISITA).get(0);
        filtroGRNoIniciada.setIdSupervision(idSupervision);
        filtroGRNoIniciada.setIdTipoDocumento(idTipoDocGRNoIniciada.getIdMaestroColumna());
        List<DocumentoAdjuntoDTO> ltaGRNoIniciada;
        try {
            ltaGRNoIniciada = documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(filtroGRNoIniciada);
        } catch (DocumentoAdjuntoException ex) {
            ltaGRNoIniciada = null;
        }     
        if(ltaGRNoIniciada != null && ltaGRNoIniciada.size() > Constantes.LISTA_VACIA){
            for (DocumentoAdjuntoDTO documentoAdjuntoDTOGRNoIniciada : ltaGRNoIniciada) {
                listaprincipal.add(documentoAdjuntoDTOGRNoIniciada);
            }
        }
        return listaprincipal;
    }
    public List<DocumentoAdjuntoDTO> verDocumentosSupervisionGenerarResultadosObstaculizdoInicial(Long idSupervision){
        List<DocumentoAdjuntoDTO> listaprincipal = new ArrayList<DocumentoAdjuntoDTO>();
        
        //para carta de visita para Supervision No Iniciada Inicial
        DocumentoAdjuntoFilter filtroGRObstaculizado = new DocumentoAdjuntoFilter();
        MaestroColumnaDTO idTipoDocGRObstaculizado = maestroColumnaServiceNeg.findMaestroColumnaByDominioAplicDesc(Constantes.DOMINIO_TIP_DOC_SUP_SIGED, Constantes.APLICACION_INPS, Constantes.DESCRIPCION_TIP_DOC_SUP_SIGED_CARTA_VISITA).get(0);
        filtroGRObstaculizado.setIdSupervision(idSupervision);
        filtroGRObstaculizado.setIdTipoDocumento(idTipoDocGRObstaculizado.getIdMaestroColumna());
        List<DocumentoAdjuntoDTO> ltaGRObstaculizado;
        try {
            ltaGRObstaculizado = documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(filtroGRObstaculizado);
        } catch (DocumentoAdjuntoException ex) {
            ltaGRObstaculizado = null;
        }     
        if(ltaGRObstaculizado != null && ltaGRObstaculizado.size() > Constantes.LISTA_VACIA){
            for (DocumentoAdjuntoDTO documentoAdjuntoDTOGRObstaculizado : ltaGRObstaculizado) {
                listaprincipal.add(documentoAdjuntoDTOGRObstaculizado);
            }
        }
        return listaprincipal;
    }
    /*OSINE_SFS-791 - RSIS 41 - Fin */
    /*OSINE_SFS-791 - RSIS 04 - Inicio */
    @Override
    @Transactional(rollbackFor=DocumentoAdjuntoException.class)
    public Map<String, Object> generarResultadosDocSupervisionInicial(GenerarResultadoDTO generarResultadoDTO, HttpServletRequest request, HttpSession session) throws DocumentoAdjuntoException {
        LOG.info("Error en generarResultadosDocSupervisionInicial");
        Map<String, Object> retorno = new HashMap<String, Object>();
        String rutaImagen = "";
        String rutaReportePrincipal="";
        InputStream reportStreamImage = null;
        InputStream reportJasper = null;
        ReporteDTO reporteDTO = new ReporteDTO();
        Map<String, Object> reportParams = null;
        DocumentoAdjuntoDTO registroDocumentoEjecucionMedida = null;
        String correos="";
        try{
            ServletContext context = session.getServletContext();
            //DocumentoAdjuntoFilter filtro = new DocumentoAdjuntoFilter();
            //MaestroColumnaDTO idTipoDoc=maestroColumnaServiceNeg.findByDominioAplicacionCodigo(Constantes.DOMINIO_TIPO_DOC_SUPERVISION, Constantes.APLICACION_INPS, Constantes.CODIGO_GEN_RESULT).get(0);
            //filtro.setIdSupervision(generarResultadoDTO.getIdSupervision());
            //filtro.setIdTipoDocumento(idTipoDoc.getIdMaestroColumna());
            //List<DocumentoAdjuntoDTO> listado= documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(filtro);
            List<DocumentoAdjuntoDTO> listado = verDocumentosSupervisionGenerarResultadosObstaculizdoInicial(generarResultadoDTO.getIdSupervision());
            SupervisionDTO supervision = null;
            SupervisionFilter filter = new SupervisionFilter();
            filter.setIdSupervision(generarResultadoDTO.getIdSupervision());
            List<SupervisionDTO> ltasupervision = supervisionServiceNeg.buscarSupervision(filter);
            supervision = ltasupervision.get(Constantes.PRIMERO_EN_LISTA);
            if (listado != null && listado.size() > Constantes.LISTA_VACIA) {
                registroDocumentoEjecucionMedida = listado.get(Constantes.PRIMERO_EN_LISTA);
                registroDocumentoEjecucionMedida.setSupervision(new SupervisionDTO(generarResultadoDTO.getIdSupervision()));
            } else {
                if (ltasupervision.size() == 1) { 
                    //aca se genera la carta
                    UbigeoDTO ubigoDTO = new UbigeoDTO();
                    ubigoDTO.setIdDepartamento(generarResultadoDTO.getIdDepartamento());
                    ubigoDTO.setIdProvincia(generarResultadoDTO.getIdProvincia());
                    ubigoDTO.setIdDistrito(generarResultadoDTO.getIdDistrito());
                    Date fecha = new Date();   
                    generarResultadoDTO.setFechaFin(Utiles.DateToString(fecha, Constantes.FORMATO_FECHA_CORTA));
                    generarResultadoDTO.setHoraFin(Utiles.DateToString(fecha, Constantes.FORMATO_HORA_CORTA));
                    supervision.setFechaFin(generarResultadoDTO.getFechaFin()+" "+generarResultadoDTO.getHoraFin());
                    supervision.setCartaVisita(generarResultadoDTO.getNroCarta());                        
                    ResultadoSupervisionFilter filtroResultado = new ResultadoSupervisionFilter();
                    if(generarResultadoDTO.getResultadoSupervisionInicialDTO().getCodigo().equals(Constantes.CODIGO_RESULTADO_SUPERVISION_INICIAL_OBSTACULIZADO)){
                       filtroResultado.setCodigo(Constantes.CODIGO_RESULTADO_SUPERVISION_OBSTACULIZADA);
                    }else{
                       if(generarResultadoDTO.getResultadoSupervisionInicialDTO().getCodigo().equals(Constantes.CODIGO_RESULTADO_SUPERVISION_INICIAL_NO)){
                          filtroResultado.setCodigo(Constantes.CODIGO_RESULTADO_SUPERVISION_NO);                      
                       }                       
                    }
                    ResultadoSupervisionDTO resultadoDTO = resultadoSupervisionServiceNeg.getResultadoSupervision(filtroResultado);
                    supervision.setResultadoSupervisionDTO(resultadoDTO);                    
                    supervision = supervisionServiceNeg.registroEmSupervision(supervision, Constantes.getUsuarioDTO(request));
                    if (supervision != null) {
                        rutaImagen = Constantes.RUTA_IMG_CABECERA_GENERAR_RESULTADOS;
                        rutaReportePrincipal = Constantes.RUTA_PLANTILLA_GENERAR_RESULTADOS_CARTA_VISITA;
                        reporteDTO.setDescripcionDocumento(Constantes.DESCRIPCION_DOCUMENTO_CARTA_VISITA);
                        String hora = getHoraActual();
                        String fechact = getFechaActual();
                        reporteDTO.setNombreArchivo(Constantes.NOMBRE_ARCHIVO_CARTA_VISITA +"_"+fechact+"_"+hora+"" + Constantes.EXTENSION_DOCUMENTO_CARTA_VISITA);
                        reportStreamImage = context.getResourceAsStream(rutaImagen);
                        reportJasper = context.getResourceAsStream(rutaReportePrincipal);
                        reportParams = setearInformacionCartaVisitaSupervisionInicial(reportStreamImage, generarResultadoDTO);
                        //INSERT
                        MaestroColumnaDTO TipoDocumentoCartaVisitaObstaculizadoDTO = maestroColumnaServiceNeg.findMaestroColumnaByDominioAplicDesc(Constantes.DOMINIO_TIP_DOC_SUP_SIGED, Constantes.APLICACION_INPS, Constantes.DESCRIPCION_TIP_DOC_SUP_SIGED_CARTA_VISITA).get(0);
                        registroDocumentoEjecucionMedida = registrarDocumentoBDDsr(reportJasper, reportParams, generarResultadoDTO.getIdSupervision(), request, reporteDTO,TipoDocumentoCartaVisitaObstaculizadoDTO);
                        if(generarResultadoDTO.getFlagSupervisionInicial().equals(Constantes.IDENTIFICADOR_FLAG_SUPERVISION_OBSTACULIZADO)){
                            LOG.info("ejecutando metodo de envio para obstaculizado");  
                          //enviar notificacion Obstaculizado
                            if (registroDocumentoEjecucionMedida != null) {
                                //CORREO5
                                String correo5 = EnvioNotificacionesSupervisionDsrObstaculizadaInicial(ubigoDTO, supervision.getOrdenServicioDTO(),supervision);
                                correos += ",correo5=" + correo5;
                            }                        
                        }else{
                            if(generarResultadoDTO.getFlagSupervisionInicial().equals(Constantes.IDENTIFICADOR_FLAG_SUPERVISION_NO)){
                            LOG.info("ejecutando metodo de envio para supervision NO");      
                          //enviar notificacionNO
                            }
                        }
                    
                    }
                }
            }
            retorno.put("registroDocumentoEjecucionMedida", registroDocumentoEjecucionMedida);
            retorno.put("supervision", supervision);
            retorno.put("correos", correos);
        }catch(Exception e){
            LOG.error("Error en generarResultadosDocSupervisionInicial",e);
            throw new DocumentoAdjuntoException(e.getMessage(),e);
        }  
        return retorno;
              
    }
    /*OSINE_SFS-791 - RSIS 04 - Fin */
    /*OSINE_SFS-791 - RSIS 04 - Inicio */
    public Map<String, Object> setearInformacionCartaVisitaSupervisionInicial(InputStream reportStreamImage, GenerarResultadoDTO generarResultadoDTO) {
        Map<String, Object> reportParams = new HashMap<String, Object>();
        SupervisionFilter filtro = new SupervisionFilter();
        filtro.setIdSupervision(generarResultadoDTO.getIdSupervision());
        List<SupervisionDTO> supervisionDTOs = supervisionServiceNeg.buscarSupervisionReporte(filtro);
        SupervisionDTO supervisionDTO = new SupervisionDTO();
        supervisionDTO = supervisionDTOs.get(Constantes.PRIMERO_EN_LISTA);
        LOG.info("nro Carta " + generarResultadoDTO.getNroCarta());
        LOG.info("fechaInicio " + supervisionDTO.getFechaInicio());
        LOG.info("numeroOrdenServicio " + (supervisionDTO.getOrdenServicioDTO().getNumeroOrdenServicio()));
        LOG.info("numeroExpediente " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getNumeroExpediente()));
        LOG.info("idUnidadSupervisada " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getIdUnidadSupervisada()));
        LOG.info("codigoOsinerming " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getCodigoOsinergmin()));
        UbigeoDTO filter = new UbigeoDTO();
        filter.setIdDepartamento(generarResultadoDTO.getIdDepartamento());
        filter.setIdProvincia(generarResultadoDTO.getIdProvincia());
        filter.setIdDistrito(generarResultadoDTO.getIdDistrito());
        UbigeoDTO general = ObtenerUbigeo(filter);
        UnidadSupervisadaFilter unidadfilter = new UnidadSupervisadaFilter();
        unidadfilter.setIdUnidadSupervisada(supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getIdUnidadSupervisada());
        List<UnidadSupervisadaDTO> ltaUnidad = unidadSupervisadaServiceNeg.getUnidadSupervisadaDTO(unidadfilter);
        String observaciones = "";
        if (generarResultadoDTO.getResultadoSupervisionInicialDTO() != null) {
            if (generarResultadoDTO.getResultadoSupervisionInicialDTO().getCodigo().equals(Constantes.CODIGO_RESULTADO_SUPERVISION_INICIAL_OBSTACULIZADO)) {
                observaciones = "" + supervisionDTO.getObservacion();
            /*OSINE_SFS-791 - RSIS 03 - Inicio */
            }else if (generarResultadoDTO.getResultadoSupervisionInicialDTO().getCodigo().equals(Constantes.CODIGO_RESULTADO_SUPERVISION_INICIAL_NO)) {
                observaciones = "" + supervisionDTO.getObservacion();
            /*OSINE_SFS-791 - RSIS 03 - Fin  */
            }else {
                List<MaestroColumnaDTO> ltamaestro = maestroColumnaServiceNeg.findByDominioAplicacionCodigo(Constantes.DOMINIO_OBSERV_CARTA_VISITA, Constantes.APLICACION_INPS, Constantes.CODIGO_OBS_CARTA_VISITA_SIN_INFRACCION);
                observaciones = (ltamaestro.get(Constantes.PRIMERO_EN_LISTA).getDescripcion() != null) ? ltamaestro.get(Constantes.PRIMERO_EN_LISTA).getDescripcion() : "";
            }
        }
        /*OSINE_SFS-791 - RSIS 41 - Inicio */
        if (generarResultadoDTO.getResultadoSupervisionFinalDTO() != null) {
            if (generarResultadoDTO.getResultadoSupervisionFinalDTO().getCodigo().equals(Constantes.CODIGO_RESULTADO_SUPERVISION_NOCUMPLE)) {
                observaciones = "SE IDENTIFICO DURANTE LA SUPERVISION LA EXISTENCIA DE INFRACCIONES QUE NO FUERON SUBSANADAS.";
            }
        }
        /*OSINE_SFS-791 - RSIS 41 - fIN */
        LocadorDTO locadorDTO=null;
        if(supervisionDTO.getOrdenServicioDTO().getLocador()!=null && supervisionDTO.getOrdenServicioDTO().getLocador().getIdLocador()!=null){
            locadorDTO = locadorServiceNeg.getById(supervisionDTO.getOrdenServicioDTO().getLocador().getIdLocador());
        }
        if (ltaUnidad.size() == Constantes.LISTA_UNICO_VALIR) {
            UnidadSupervisadaDTO unidadDTO = ltaUnidad.get(Constantes.PRIMERO_EN_LISTA);
            reportParams.put("rutaImagen", reportStreamImage);
            reportParams.put("nroCartaVisita", generarResultadoDTO.getNroCarta());
            reportParams.put("preOperativa", false);
            reportParams.put("operativa", true);
            reportParams.put("especial", false);
            reportParams.put("planAbono", false);
            reportParams.put("comprobacionOperaciones", false);
            reportParams.put("activosInseguros", false);
            reportParams.put("pdj", false);
            reportParams.put("emergencias", false);
            reportParams.put("denuncias", false);
            reportParams.put("nroExpediente", (supervisionDTO.getOrdenServicioDTO().getExpediente().getNumeroExpediente()));
            reportParams.put("fechaInicioSupervision", (supervisionDTO.getFechaInicio()));
            reportParams.put("fechaTerminoSupervision", generarResultadoDTO.getFechaFin());
            reportParams.put("nroOrdenServicio", (supervisionDTO.getOrdenServicioDTO().getNumeroOrdenServicio()));
            reportParams.put("horaInicioSupervision", (supervisionDTO.getHoraInicio()));
            reportParams.put("horaTerminoSupervision", generarResultadoDTO.getHoraFin());
            reportParams.put("direccionOperativaAgente", generarResultadoDTO.getDireccionOperativa());
            reportParams.put("distritoDireccionOperativaAgente", general.getDescripcionDistrito());
            reportParams.put("provinciaDireccionOperativaAgente", general.getDescripcionProvincia());
            reportParams.put("departamentoDireccionOperativaAgente", general.getDescripcionDepartamento());
//            reportParams.put("razonSocialAgente", (unidadDTO.getEmpresaSupervisada().getRazonSocial() != null) ? unidadDTO.getEmpresaSupervisada().getRazonSocial() : "");
//            reportParams.put("dniAgente", (unidadDTO.getEmpresaSupervisada().getTipoDocumentoIdentidad() != null && unidadDTO.getEmpresaSupervisada().getTipoDocumentoIdentidad().getCodigo().equals(Constantes.CODIGO_TIPO_DOCUMENTO_DNI)) ? unidadDTO.getEmpresaSupervisada().getNroIdentificacion() : "");
//            reportParams.put("rucAgente", (unidadDTO.getEmpresaSupervisada().getRuc() != null) ? unidadDTO.getEmpresaSupervisada().getRuc() : "");
//            reportParams.put("telfAgente", (unidadDTO.getEmpresaSupervisada().getTelefono() != null) ? unidadDTO.getEmpresaSupervisada().getTelefono() : "");
//            reportParams.put("emailAgente", (unidadDTO.getEmpresaSupervisada().getCorreoElectronico() != null) ? unidadDTO.getEmpresaSupervisada().getCorreoElectronico() : "");
            reportParams.put("razonSocialAgente", (unidadDTO.getNombreUnidad() != null) ? unidadDTO.getNombreUnidad() : "");
            reportParams.put("dniAgente", "");
            reportParams.put("rucAgente", (unidadDTO.getRuc() != null) ? unidadDTO.getRuc() : "");
            reportParams.put("telfAgente", "");
            reportParams.put("emailAgente", "");
            reportParams.put("tipoActividadAgente",(unidadDTO.getActividad().getNombre() != null) ? unidadDTO.getActividad().getNombre()  : "");
            reportParams.put("codigoActividadAgente", (unidadDTO.getActividad().getCodigo() != null) ? unidadDTO.getActividad().getCodigo() : "");
            reportParams.put("registroHidrocarburosAgente", (unidadDTO.getNroRegistroHidrocarburo() != null) ? unidadDTO.getNroRegistroHidrocarburo() : "");
            reportParams.put("codigoOsinergminAgente", (unidadDTO.getCodigoOsinergmin()!= null) ? unidadDTO.getCodigoOsinergmin() : "");
            //Mostrar Locador o supervisora empresa
            //reportParams.put("locador", (locadorDTO !=null && locadorDTO.getNombreCompleto() != null) ? locadorDTO.getNombreCompleto() : "");
            if (locadorDTO !=null && locadorDTO.getNombreCompleto() != null)
            	reportParams.put("locador", (locadorDTO !=null && locadorDTO.getNombreCompleto() != null) ? locadorDTO.getNombreCompleto() : "-");
            else if (supervisionDTO.getOrdenServicioDTO()!=null && supervisionDTO.getOrdenServicioDTO().getSupervisoraEmpresa()!=null && supervisionDTO.getOrdenServicioDTO().getSupervisoraEmpresa().getRazonSocial()!=null)
            	reportParams.put("locador", (supervisionDTO.getOrdenServicioDTO().getSupervisoraEmpresa()!=null && supervisionDTO.getOrdenServicioDTO().getSupervisoraEmpresa().getRazonSocial()!=null) ? supervisionDTO.getOrdenServicioDTO().getSupervisoraEmpresa().getRazonSocial() : "-");
            
            reportParams.put("colegiatura", (locadorDTO !=null && locadorDTO.getNumeroColegiatura() != null) ? locadorDTO.getNumeroColegiatura() : "-");
            reportParams.put("dniLocador", (locadorDTO !=null && locadorDTO.getIdTipoDocumento()!= null && locadorDTO.getIdTipoDocumento().getCodigo().equals(Constantes.CODIGO_TIPO_DOCUMENTO_DNI) && locadorDTO.getNumeroDocumento() != null) ? locadorDTO.getNumeroDocumento() : "-");
            reportParams.put("emailLocador", (locadorDTO !=null && locadorDTO.getCorreoElectronicoInstitucion()!= null) ? locadorDTO.getCorreoElectronicoInstitucion() : "-");
            reportParams.put("telefonosLocador", (locadorDTO !=null && locadorDTO.getTelefonoContacto()!= null) ? locadorDTO.getTelefonoContacto() : "-");
            reportParams.put("faxLocador", "-");
            reportParams.put("observaciones",observaciones); 
        }
        return reportParams;
    }
    //correo 5
    public String EnvioNotificacionesSupervisionDsrObstaculizadaInicial(UbigeoDTO ubigeo,OrdenServicioDTO ordenServicioDTO,SupervisionDTO Supervisiondto){
        LOG.info("EnvioNotificacionesSupervisionDsrObstaculizadaInicial - inicio");
        String msjeCorreo = "";
        try {
            //Envio de correo para Medida Seguridad
            DestinatarioCorreoFilter filtro1 = new DestinatarioCorreoFilter();
            filtro1.setCodigoFuncionalidadCorreo(Constantes.CODIGO_FUNCIONALIDAD_TF011);
            filtro1.setIdDepartamento(ubigeo.getIdDepartamento());
            filtro1.setIdProvincia(ubigeo.getIdProvincia());
            filtro1.setIdDistrito(ubigeo.getIdDistrito());
            List<DestinatarioCorreoDTO> milistaDestinos = destinatarioCorreoServiceNeg.getDestinatarioCorreobyUbigeo(filtro1);
            if(CollectionUtils.isEmpty(milistaDestinos)){
                throw new CorreoException("Error, No existen destinatarios de Correo.",null);
            }
            ExpedienteDTO expedienteDTO = new ExpedienteDTO();
            expedienteDTO.setIdExpediente(ordenServicioDTO.getExpediente().getIdExpediente());
            expedienteDTO.setNumeroExpediente(ordenServicioDTO.getExpediente().getNumeroExpediente());
            expedienteDTO.setOrdenServicio(ordenServicioDTO);
            boolean rptaCorreo = correoServiceNeg.EnvioNotificacionesSupervisionDsrObstaculizadaInicial(milistaDestinos, expedienteDTO,Supervisiondto);
            if (!rptaCorreo) {
                msjeCorreo = "No envio correo SupervisionDsrObstaculizadaInicial.";
            }
        } catch (Exception e) {
            LOG.error("Error en EnvioNotificacionesSupervisionDsrObstaculizadaInicial", e);
        }
        LOG.info("EnvioNotificacionesSupervisionDsrObstaculizadaInicial - fin");
        return msjeCorreo;
    }
    /*OSINE_SFS-791 - RSIS 04 - Fin */
    
    @Override
    @Transactional(rollbackFor=SupervisionException.class)
    public Map<String, Object> guardarDatosInicialSupervisionDsr(SupervisionDTO supervisionDTO, HttpSession session, HttpServletRequest request) throws SupervisionException {
        LOG.info("guardarDatosInicialSupervisionDsr - inicio");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            supervisionDTO.setEstado(Constantes.ESTADO_ACTIVO);
            if (supervisionDTO.getMotivoNoSupervision() != null && supervisionDTO.getMotivoNoSupervision().getIdMotivoNoSupervision() == -1) {
                supervisionDTO.setMotivoNoSupervision(null);
            }
            SupervisionDTO datosSupervision = cargarDatosSupervisionDsr(supervisionDTO, Constantes.getUsuarioDTO(request));
            supervisionDTO.setIdSupervision(datosSupervision.getIdSupervision());
            SupervisionDTO supervision = supervisionServiceNeg.registrarDatosSupervision(supervisionDTO, Constantes.getUsuarioDTO(request));
            retorno.put("supervision", supervision);
            retorno.put("sup", datosSupervision);
        } catch (Exception e) {
            LOG.error("Error en guardarDatosSupervision", e);
            throw new SupervisionException(e.getMessage(),e);
        }
        LOG.info("guardarDatosSupervisionDsr - fin");
        return retorno;
    }
    
    @Transactional(rollbackFor=SupervisionException.class)
    public SupervisionDTO cargarDatosSupervisionDsr(SupervisionDTO supervisionDTO, UsuarioDTO usuarioDTO) throws SupervisionException {
        LOG.info("cargarDatosSupervisionDsr - inicio");
        try {
            SupervisionFilter filtro = new SupervisionFilter();
            filtro.setIdOrdenServicio(supervisionDTO.getOrdenServicioDTO().getIdOrdenServicio());
            List<SupervisionDTO> lista = supervisionServiceNeg.buscarSupervision(filtro);

            if (supervisionDTO.getOrdenServicioDTO() != null && supervisionDTO.getOrdenServicioDTO().getIdOrdenServicio() != null) {
                List<OrdenServicioDTO> listaOrdenServicio = supervisionServiceNeg.listarOrdenServicio(new OrdenServicioFilter(supervisionDTO.getOrdenServicioDTO().getIdOrdenServicio()));
                if (!listaOrdenServicio.isEmpty()) {
                    supervisionDTO.setOrdenServicioDTO(listaOrdenServicio.get(Constantes.PRIMERO_EN_LISTA));
                }
                supervisionDTO = supervisionServiceNeg.registrarSupervisionBloqueDsr(supervisionDTO, usuarioDTO);
                lista = supervisionServiceNeg.buscarSupervision(new SupervisionFilter(supervisionDTO.getIdSupervision()));
                supervisionDTO = lista.get(Constantes.PRIMERO_EN_LISTA);
            }            
        } catch (Exception e) {
            LOG.error("Error en cargarDatosSupervision", e);
            throw new SupervisionException(e.getMessage(),e);
        }
        LOG.info("cargarDatosSupervision - fin");
        return supervisionDTO;
    }
    
    /*OSINE_SFS-791 - RSIS 03 - Inicio */
    @Override
    public Map<String, Object> generarResultadoSupervision(GenerarResultadoDTO generarResultadoDTO, HttpServletRequest request, HttpSession session) {
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            ServletContext context = session.getServletContext();
            String rutaImagen = "";
            String rutaReportePrincipal="";
            InputStream reportStreamImage = null;
            InputStream reportJasper = null;
            ReporteDTO reporteDTO = new ReporteDTO();
            Map<String, Object> reportParams = null;
            DocumentoAdjuntoDTO registroDocumentoSupervision = null;
            //Buscar Documentos Adjuntos
            //DocumentoAdjuntoFilter filtro = new DocumentoAdjuntoFilter();
            //MaestroColumnaDTO idTipoDoc=maestroColumnaServiceNeg.findByDominioAplicacionCodigo(Constantes.DOMINIO_TIPO_DOC_SUPERVISION, Constantes.APLICACION_INPS, Constantes.CODIGO_GEN_RESULT).get(0);
            //filtro.setIdSupervision(generarResultadoDTO.getIdSupervision());
            //filtro.setIdTipoDocumento(idTipoDoc.getIdMaestroColumna());
            //List<DocumentoAdjuntoDTO> listadoDocAdj= documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(filtro);
            
            List<DocumentoAdjuntoDTO> listadoDocAdj = verDocumentosSupervisionGenerarResultadosNoIniciadaInicial(generarResultadoDTO.getIdSupervision());
            //Buscar Supervision
            SupervisionDTO supervision = null;
            SupervisionFilter filter = new SupervisionFilter();
            filter.setIdSupervision(generarResultadoDTO.getIdSupervision());
            List<SupervisionDTO> listaSupervision = supervisionServiceNeg.buscarSupervision(filter);
            supervision = listaSupervision.get(Constantes.PRIMERO_EN_LISTA);
            //Si existe documento generado
            if (listadoDocAdj != null && listadoDocAdj.size() > Constantes.LISTA_VACIA) {
                registroDocumentoSupervision = listadoDocAdj.get(Constantes.PRIMERO_EN_LISTA);
                registroDocumentoSupervision.setSupervision(new SupervisionDTO(generarResultadoDTO.getIdSupervision()));
            }else{
                //listaSupervision posee unico registro
                if(listaSupervision.size() == Constantes.LISTA_UNICO_VALIR){
                    ResultadoSupervisionFilter filtroRS= new ResultadoSupervisionFilter();
                    filtroRS.setIdResultadosupervision(supervision.getResultadoSupervisionInicialDTO().getIdResultadosupervision());
                    List<ResultadoSupervisionDTO> resultadoSupervision=resultadoSupervisionServiceNeg.listarResultadoSupervision(filtroRS);
                    // GeneraResultadoSupervision - CODIGO_RESULTADO_SUPERVISION_INICIAL_NO
                    if(resultadoSupervision.get(0).getCodigo().longValue()==Constantes.CODIGO_RESULTADO_SUPERVISION_INICIAL_NO){
                    	supervision.setFechaInicio(supervision.getFechaInicio() + " " + supervision.getHoraInicio());
                        supervision.setCartaVisita(generarResultadoDTO.getNroCarta());
                        supervision.setObservacion(generarResultadoDTO.getObservacion());
                        //Obtener FechaFin + HoraFin
                        Date fecha = new Date();   
                        generarResultadoDTO.setFechaFin(Utiles.DateToString(fecha, Constantes.FORMATO_FECHA_CORTA));
                        generarResultadoDTO.setHoraFin(Utiles.DateToString(fecha, Constantes.FORMATO_HORA_CORTA));
                        supervision.setFechaFin(generarResultadoDTO.getFechaFin()+" "+generarResultadoDTO.getHoraFin());
                        
                        //Obtener IdResultadoSupervision - CODIGO_RESULTADO_SUPERVISION_NO     
                        ResultadoSupervisionFilter filtroIdRS= new ResultadoSupervisionFilter();
                        filtroIdRS.setCodigo(Constantes.CODIGO_RESULTADO_SUPERVISION_NO);
                        ResultadoSupervisionDTO resultadoDTO = resultadoSupervisionServiceNeg.getResultadoSupervision(filtroIdRS);
                        supervision.setResultadoSupervisionDTO(resultadoDTO); 
                        
                        supervision = supervisionServiceNeg.registrarDatosFinalesSupervision(supervision, Constantes.getUsuarioDTO(request));    
                        
                        if (supervision != null) {
                            rutaImagen = Constantes.RUTA_IMG_CABECERA_GENERAR_RESULTADOS;
                            rutaReportePrincipal = Constantes.RUTA_PLANTILLA_GENERAR_RESULTADOS_CARTA_VISITA;
                            reporteDTO.setDescripcionDocumento(Constantes.DESCRIPCION_DOCUMENTO_CARTA_VISITA);
                            String hora = getHoraActual();
                            String fechact = getFechaActual();
                            reporteDTO.setNombreArchivo(Constantes.NOMBRE_ARCHIVO_CARTA_VISITA +"_"+fechact+"_"+hora+"" + Constantes.EXTENSION_DOCUMENTO_CARTA_VISITA);
                            reportStreamImage = context.getResourceAsStream(rutaImagen);
                            reportJasper = context.getResourceAsStream(rutaReportePrincipal);
                            reportParams = setearInformacionCartaVisitaSupervisionInicial(reportStreamImage, generarResultadoDTO);
                            MaestroColumnaDTO TipoDocumentoCartaVisitaDTO = maestroColumnaServiceNeg.findMaestroColumnaByDominioAplicDesc(Constantes.DOMINIO_TIP_DOC_SUP_SIGED, Constantes.APLICACION_INPS, Constantes.DESCRIPCION_TIP_DOC_SUP_SIGED_CARTA_VISITA).get(0);
                            registroDocumentoSupervision = registrarDocumentoBDDsr(reportJasper, reportParams, generarResultadoDTO.getIdSupervision(), request, reporteDTO,TipoDocumentoCartaVisitaDTO);
                        }
                        
                    } 
                    
                }
            }
            
            retorno.put("registroDocumentoSupervision", registroDocumentoSupervision);
            retorno.put("supervision", supervision);
//            retorno.put("correos", correos);
            
        } catch (Exception ex) {
            LOG.error("Error en generarResultadoSupervision", ex);
        }
        LOG.info("SupervisionServiceNegImpl--generarResultadoSupervision - fin");
        return retorno;
    }
    /*OSINE_SFS-791 - RSIS 03 - Fin */
    
     /*OSINE_SFS-791 - RSIS 40 - Inicio */
    
    
    @Override
    @Transactional(rollbackFor=DocumentoAdjuntoException.class)
    public Map<String, Object> generarResultadosSupTerminarSupervision(GenerarResultadoDTO generarResultadoDTO, HttpServletRequest request, HttpSession session) throws DocumentoAdjuntoException {
      LOG.info("generarResultadosSupTerminarSupervision");
        Map<String, Object> retorno = new HashMap<String, Object>();
        String rutaImagen = "";
        String rutaReportePrincipal="";
        InputStream reportStreamImage = null;
        InputStream reportJasper = null;
        ReporteDTO reporteDTO = new ReporteDTO();
        Map<String, Object> reportParams = null;
        DocumentoAdjuntoDTO registroDocumentoSupervision = null;
        String correos="";
        int cantidad = 0;
        try{
            ServletContext context = session.getServletContext();
            //Pruebas Consulta
            //reportParams = setearInformacion(reportStreamImage, generarResultadoDTO);
            SupervisionDTO supervision = null;
            SupervisionFilter filter = new SupervisionFilter();
            filter.setIdSupervision(generarResultadoDTO.getIdSupervision());
            List<SupervisionDTO> ltasupervision = supervisionServiceNeg.buscarSupervision(filter);
            supervision = ltasupervision.get(Constantes.PRIMERO_EN_LISTA);
            int nroInfracciones = Integer.parseInt(generarResultadoDTO.getFlagInfracciones());
            if (nroInfracciones == Constantes.NRO_INFRACCIONES_CARTA_VISITA_TERMINO_SUPERVISION) {
                List<DocumentoAdjuntoDTO> listado = verDocumentosSupervision(generarResultadoDTO);
                if (listado != null && listado.size() > Constantes.LISTA_VACIA) {
                    registroDocumentoSupervision = listado.get(Constantes.PRIMERO_EN_LISTA);
                    registroDocumentoSupervision.setSupervision(new SupervisionDTO(generarResultadoDTO.getIdSupervision()));
                } else if (ltasupervision.size() == 1) {
                    LOG.info("INICIO DEL PROCESO PARA SUPERVISION CON INFRACCIONES YA SUBSANADAS");
                    UbigeoDTO ubigoDTO = new UbigeoDTO();
                    ubigoDTO.setIdDepartamento(generarResultadoDTO.getIdDepartamento());
                    ubigoDTO.setIdProvincia(generarResultadoDTO.getIdProvincia());
                    ubigoDTO.setIdDistrito(generarResultadoDTO.getIdDistrito());
                    Date fecha = new Date();
                    generarResultadoDTO.setFechaFin(Utiles.DateToString(fecha, Constantes.FORMATO_FECHA_CORTA));
                    generarResultadoDTO.setHoraFin(Utiles.DateToString(fecha, Constantes.FORMATO_HORA_CORTA));
                    supervision.setFechaFin(generarResultadoDTO.getFechaFin() + " " + generarResultadoDTO.getHoraFin());
                    //LISTAR DETALLES SUPERVISION DE ESTA SUPERVISION
                    LOG.info("LISTAR DETALLES SUPERVISION DE ESTA SUPERVISION");
                    DetalleSupervisionFilter filterdetaactual = new DetalleSupervisionFilter();
                    filterdetaactual.setIdSupervision(supervision.getIdSupervision());
                    List<DetalleSupervisionDTO> ltadetalleSupervisiondetaactual = null;
                    ltadetalleSupervisiondetaactual = detalleSupervisionServiceNeg.listarDetalleSupervision(filterdetaactual);
                    if (ltadetalleSupervisiondetaactual.size() > Constantes.LISTA_VACIA) {
                        LOG.info("LISTAR DETALLES SUPERVISION DE LA SUPERVISION ANTERIOR");
                        ResultadoSupervisionFilter filtroResultadoAnte = new ResultadoSupervisionFilter();
                        LOG.info("BUSCANDO EL RESULTADO SUPERVISION CON INCUMPLIMIENTO");
                        filtroResultadoAnte.setCodigo(Constantes.CODIGO_RESULTADO_INCUMPLE);                      
                        ResultadoSupervisionDTO resultadoAnteDTO = resultadoSupervisionServiceNeg.getResultadoSupervision(filtroResultadoAnte);
                        DetalleSupervisionFilter filterdetaAnterior = new DetalleSupervisionFilter();
                        filterdetaAnterior.setIdSupervision(supervision.getSupervisionAnterior().getIdSupervision());
                        filterdetaAnterior.setIdResultadoSupervision(resultadoAnteDTO.getIdResultadosupervision());
                        LOG.info("ENVIANDO ID SUPERVISION : |"+filterdetaAnterior.getIdSupervision()+"|");
                        LOG.info("ENVIANDO ID IdResultadoSupervision : |"+filterdetaAnterior.getIdResultadoSupervision()+"|");
                        List<DetalleSupervisionDTO> ltadetalleSupervisiondetaAnterior = null;                   
                        ltadetalleSupervisiondetaAnterior = detalleSupervisionServiceNeg.listarDetalleSupervision(filterdetaAnterior);
                        LOG.info("LA LISTA TRAJO |"+ltadetalleSupervisiondetaAnterior.size()+"| elementos");
                        if (ltadetalleSupervisiondetaAnterior.size() > Constantes.LISTA_VACIA) {
                            //COMPROBAR SI HAY AL MENOS UNO CON CIERRE TOTAL
                            LOG.info("COMPROBANDO SI HAY AL MENOS UNO CON CIERRE TOTAL");
                            boolean validarcierreTotal = verDetalleSupervisionCierreTotal(ltadetalleSupervisiondetaAnterior);
                            LOG.info("RETORNO CIERRE TOTAL EL VALOR DE  |"+validarcierreTotal+"|");
                            //BUSCANDO EL PERIODO MEDIDA SEGURIDAD
                            LOG.info("BUSCANDO EL PERIODO MEDIDA SEGURIDAD");
                            ExpedienteDTO expedienteDTO = new ExpedienteDTO();
                            expedienteDTO.setIdExpediente(supervision.getOrdenServicioDTO().getExpediente().getIdExpediente());
                            PeriodoMedidaSeguridadFilter filtropeiodomedidadseg = new PeriodoMedidaSeguridadFilter();
                            filtropeiodomedidadseg.setIdexpediente(supervision.getOrdenServicioDTO().getExpediente().getIdExpediente());
                            List<PeriodoMedidaSeguridadDTO> periodoMedidaList = periodoMedidaSeguridadServiceNeg.getListPeriodoMedidaSeguridad(filtropeiodomedidadseg);
                            PeriodoMedidaSeguridadDTO periodoMedida = null;
                            if (periodoMedidaList != null && periodoMedidaList.size() != 0) {
                                //SE ENCONTRO EL PERIODO DE MEDIDA SEGURIDAD
                                LOG.info("SE ENCONTRO EL PERIODO DE MEDIDA SEGURIDAD");
                                periodoMedida = periodoMedidaList.get(0);
                                Date fech = new Date();                                
                                //ACA SE PONE EL fecha subsanado
                                periodoMedida.setFechaSubsanado(fech);
                                String fechaActual = Utiles.DateToString(fech, Constantes.FORMATO_FECHA_CORTA);
                                String fechaplaneadafin = Utiles.DateToString(periodoMedida.getFechaFinPlaneado(), Constantes.FORMATO_FECHA_CORTA);
                                SimpleDateFormat formateador = new SimpleDateFormat(Constantes.FORMATO_FECHA_CORTA);
                                Date fechaDate1 = formateador.parse(fechaActual);
                                Date fechaDate2 = formateador.parse(fechaplaneadafin);
                                LOG.info("LA FECHA ACTUAL ES : |" + fechaDate1 + "| y fecha planeada fin es |" + fechaDate2 + "|");
                                RegistroHidrocarburoDTO registroHidrocarburoDTO = null;
                                String validarCierre = Constantes.DESCRIPCION_CIERRE_NINGUNO;
                                UnidadSupervisadaDTO unidad = verUnidadSupervisadaDTO(supervision);                                        
                                if (fechaDate1.after(fechaDate2)) {
                                    //SE PROCEDERA A VERIFICAR SI ES HABILITACION PARA CIERRE TOTAL O PARCIAL
                                    LOG.info("SE PROCEDERA A VERIFICAR SI ES HABILITACION PARA CIERRE TOTAL O PARCIAL");
                                    if (validarcierreTotal == true) {
                                        LOG.info("SE EJECUTARA habilitaciones correspondientes aplicadas para un CIERRE TOTAL");
                                        registroHidrocarburoDTO = registroHidrocarburoServiceNeg.actualizarEstadoRHExterno(unidad, Constantes.getUsuarioDTO(request), Constantes.CODIGO_ESTADO_HABILITAR_REGISTRO_HIDROCARBURO);
                                        if (registroHidrocarburoDTO != null) {
                                            LOG.info("Se Habilito el Registro de Hidrocarburo");
                                            validarCierre = Constantes.DESCRIPCION_CIERRE_TOTAL;
                                        }else{
                                            LOG.error("Error en Habilitar el RH");
                                            throw new DocumentoAdjuntoException("Error en Habilitar el RH",null);
                                        }
                                    } else {
                                        LOG.info("SE EJECUTARA las habilitaciones correspondientes aplicadas para un CIERRE PARCIAL");
                                        validarCierre = Constantes.DESCRIPCION_CIERRE_PARCIAL;
                                    }
                                } else {
                                    LOG.info("LA FECHA ACTUAL ES MENOR A LA FECHA DE FIN PLANIFICADA");
                                }
                                if (!validarCierre.equals(Constantes.DESCRIPCION_CIERRE_NINGUNO)) {
                                    LOG.info("SE LE INDICO A LA TAREA AUTOMATICA QUE NO EJECUTE ESTE REGISTRO DADO QUE YA SE REALIZO LAS HABILITACIONES");
                                    periodoMedida.setFlagActualizarAuto(Constantes.FLAG_AUTOMATICO_EJECUCION_TAREA_NO);                                    
                                } else {
                                    LOG.info("SE LE INDICO A LA TAREA AUTOMATICA QUE EJECUTE ESTE REGISTRO");
                                    periodoMedida.setFlagActualizarAuto(Constantes.FLAG_AUTOMATICO_EJECUCION_TAREA_SI);
                                }
                                //SE PROCEDERA ACTUALIZAR EL PERIODO MEDIDA SEGURIDAD
                                LOG.info("SE PROCEDERA ACTUALIZAR EL PERIDO MEDIDA SEGURIDAD");
                                periodoMedida = periodoMedidaSeguridadServiceNeg.actualizarPeriodoMedidaSeguridad(periodoMedida, Constantes.getUsuarioDTO(request));
                                if (periodoMedida != null) {
                                    //SE ACTUALIZO CORRECTAMENTE EL PERIODO MEDIDA SEGURIDAD
                                    LOG.info("SE ACTUALIZO CORRECTAMENTE EL PERIODO MEDIDA SEGURIDAD");
                                    //ACTUALIZAR LOS DETALLES SUPERVISION CON SUBSANASION SI
                                    LOG.info("ACTUALIZAR LOS DETALLES SUPERVISION CON SUBSANASION SI");
                                    DetalleSupervisionFilter filterdeta = new DetalleSupervisionFilter();
                                    filterdeta.setIdSupervision(supervision.getIdSupervision());
                                    List<DetalleSupervisionDTO> ltadetalleSupervisionActualizados = null;
                                    ltadetalleSupervisionActualizados = detalleSupervisionServiceNeg.VerificaActualizaDetalleSupervisionSubsanados(filterdeta, Constantes.getUsuarioDTO(request));
                                    if (ltadetalleSupervisionActualizados != null) {
                                        cantidad = ltadetalleSupervisionActualizados.size();
                                        if (ltadetalleSupervisiondetaactual.size() == ltadetalleSupervisionActualizados.size()) {
                                            LOG.info("SE ACTUALIZARON TODOS LOS REGISTROS");
                                            LOG.info("LISTANDO LOS DETALLES SUPERVISION PADRE");
                                            DetalleSupervisionFilter filterdetaPadre = new DetalleSupervisionFilter();
                                            filterdetaPadre.setIdSupervision(supervision.getSupervisionAnterior().getIdSupervision());
                                            filterdetaPadre.setFlgBuscaDetaSupSubsanado(Constantes.ESTADO_ACTIVO);
                                            LOG.info("BUSCANDO EL ID DE ESTADO RESULTADO INCUMPLE SI");
                                            ResultadoSupervisionFilter filtroIdINCUMPLE = new ResultadoSupervisionFilter();
                                            filtroIdINCUMPLE.setCodigo(Constantes.CODIGO_RESULTADO_INCUMPLE);
                                            ResultadoSupervisionDTO resultadoDTO = resultadoSupervisionServiceNeg.getResultadoSupervision(filtroIdINCUMPLE);
                                            filterdetaPadre.setIdResultadoSupervision(resultadoDTO.getIdResultadosupervision());
                                            List<DetalleSupervisionDTO> ltadetalleSupervisionSupPadre = null;
                                            ltadetalleSupervisionSupPadre = detalleSupervisionServiceNeg.listarDetalleSupervision(filterdetaPadre);
                                            LOG.info("LA SUPERVISION PADRE TIENE |" + ltadetalleSupervisionSupPadre.size() + "| registros incumplidos");
                                            if (ltadetalleSupervisionSupPadre.size() > Constantes.LISTA_VACIA) {
                                                ResultadoSupervisionFilter filtroIdCUMPLE = new ResultadoSupervisionFilter();
                                                filtroIdCUMPLE.setCodigo(Constantes.CODIGO_RESULTADO_SUPERVISION_CUMPLE);
                                                ResultadoSupervisionDTO resultadoDTOCUMPLE = resultadoSupervisionServiceNeg.getResultadoSupervision(filtroIdCUMPLE);
                                                supervision.setResultadoSupervisionDTO(resultadoDTOCUMPLE);
                                                //ACTUALIZAR EL RESULTADO SUPERVISION DE LA SUPERVISION DE ESTA ITERACION
                                                LOG.info("ACTUALIZAR EL RESULTADO SUPERVISION DE LA SUPERVISION DE ESTA ITERACION");
                                                supervision = supervisionServiceNeg.ActualizarSupervisionTerminarSupervision(supervision, Constantes.getUsuarioDTO(request));
                                                if (supervision != null) {                                                                                                      
                                                    //generar el acta de levantamiento                                                    
                                                    LOG.info("INICIANDO LA GENERACION DEL ACTA DE LEVANTAMIENTO");
                                                    boolean cierreTotal = false;
                                                    boolean cierreParcial = false;
                                                    if (validarcierreTotal == true) {
                                                        cierreTotal = true;
                                                    } else {
                                                        cierreParcial = true;
                                                    }
                                                    registroDocumentoSupervision = ReporteActaLevantamiento(supervision, generarResultadoDTO,cierreTotal, cierreParcial,session,request, ltadetalleSupervisionSupPadre);
                                                    if (registroDocumentoSupervision == null) {
                                                        LOG.error("Ocurrio un error al Generar el acta de levantamiento");   
                                                        throw new DocumentoAdjuntoException("Ocurrio un error al Generar el acta de levantamiento",null);
                                                    }
                                                    if (!validarCierre.equals(Constantes.DESCRIPCION_CIERRE_NINGUNO)) {
                                                        if (validarCierre.equals(Constantes.DESCRIPCION_CIERRE_TOTAL)) {
                                                            ServletContext contexto = session.getServletContext();
                                                            UsuarioDTO usuarioDTO = Constantes.getUsuarioDTO(request);
                                                            registroDocumentoSupervision = GenerarConstanciaHabilitacion(contexto,supervision, usuarioDTO);                                                            
                                                            if (registroDocumentoSupervision != null) {
                                                                LOG.info("SE REGISTRO EL DOCUMENTO DE SUSPENSION DE REGISTRO DE HIDROCARBURO");
                                                                LOG.info("---->SE PROCEDERA A ENVIAR LA NOTIFICACION DE HABILITACION DEL REGISTRO DE HIDROCARBUROS");
                                                                //ENVIANDO LA NOTIFICACION DE HABILITACION DEL REGISTRO DE HIDROCARBUROS
                                                                correos += ",correoSupervisionDsrHabilitarRH";
                                                                EnvioNotificacionesSupervisionDsrHabilitarRH(ubigoDTO, supervision);
                                                                LOG.info("---->SE PROCEDERA A ENVIAR LA NOTIFICACION DE HABILITACION DE ROL DE COMPRAS DEL SCOP");
                                                                //ENVIANDO LA NOTIFICACION DE HABILITACION DE ROL DE COMPRAS DEL SCOP
                                                                correos += ",correoSupervisionDsrHabilitarRolComprasSCOP";
                                                                EnvioNotificacionesSupervisionDsrHabilitarRolComprasScop(ubigoDTO, supervision);
                                                            } else {
                                                                LOG.error("Ocurrio un error al Generar La constancia de Habilitacion del RH");
                                                                throw new DocumentoAdjuntoException("Ocurrio un error al Generar La constancia de Habilitacion del RH", null);
                                                            }
                                                        } else if (validarCierre.equals(Constantes.DESCRIPCION_CIERRE_PARCIAL)) {
                                                            //ENVIO DE NOTIFICACION POR HABILITACION DE CIERRE PARCIAL
                                                            ServletContext contexto = session.getServletContext();
                                                            String correoCierreParcial = HabilitacionCierreParcial(supervision, ubigoDTO, contexto);
                                                            //String correoCierreParcial = EnvioNotificacionesSupervisionDsrHabilitarRolComprasScopProducto(ubigoDTO, supervision.getOrdenServicioDTO(), registroRH, unidad, contexto, supervisionAnt);
                                                            correos += ",correoCierreParcial=" + correoCierreParcial;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } 
                        }
                    }
                }
            } else if (nroInfracciones > Constantes.NRO_INFRACCIONES_CARTA_VISITA_TERMINO_SUPERVISION) {
                List<DocumentoAdjuntoDTO> listado = verDocumentosSupervision(generarResultadoDTO);
                if (listado != null && listado.size() > Constantes.LISTA_VACIA) {
                    registroDocumentoSupervision = listado.get(Constantes.PRIMERO_EN_LISTA);
                    registroDocumentoSupervision.setSupervision(new SupervisionDTO(generarResultadoDTO.getIdSupervision()));
                } else {
                    LOG.info("INICIO DEL PROCESO PARA SUPERVISION CON AUN CON INFRACCIONES");   
                    Date fecha = new Date();
                    generarResultadoDTO.setFechaFin(Utiles.DateToString(fecha, Constantes.FORMATO_FECHA_CORTA));
                    generarResultadoDTO.setHoraFin(Utiles.DateToString(fecha, Constantes.FORMATO_HORA_CORTA));
                    supervision.setFechaFin(generarResultadoDTO.getFechaFin() + " " + generarResultadoDTO.getHoraFin());
                    supervision.setCartaVisita(generarResultadoDTO.getNroCarta());
                    //Obtener IdResultadoSupervision - CODIGO_RESULTADO_SUPERVISION_NO     
                    ResultadoSupervisionFilter filtroIdRS = new ResultadoSupervisionFilter();
                    filtroIdRS.setCodigo(Constantes.CODIGO_RESULTADO_SUPERVISION_NOCUMPLE);
                    ResultadoSupervisionDTO resultadoDTO = resultadoSupervisionServiceNeg.getResultadoSupervision(filtroIdRS);
                    supervision.setResultadoSupervisionDTO(resultadoDTO);
                    generarResultadoDTO.setResultadoSupervisionFinalDTO(resultadoDTO);
                    //ACTUALIZAR EL RESULTADO SUPERVISION DE LA SUPERVISION DE ESTA ITERACION
                    LOG.info("ACTUALIZAR EL RESULTADO SUPERVISION DE LA SUPERVISION DE ESTA ITERACION");                     
                    supervision = supervisionServiceNeg.ActualizarSupervisionTerminarSupervision(supervision, Constantes.getUsuarioDTO(request));
                    if (supervision != null) {
                        //ACTUALIZAR LOS DETALLES SUPERVISION CON SUBSANASION SI
                        LOG.info("ACTUALIZAR LOS DETALLES SUPERVISION CON SUBSANASION SI");                       
                        DetalleSupervisionFilter filterdeta = new DetalleSupervisionFilter();
                        LOG.info("enviando idsup |"+supervision.getIdSupervision()+"|");
                        filterdeta.setIdSupervision(supervision.getIdSupervision());
                        List<DetalleSupervisionDTO> ltadetalleSupervisionActualizados = null;
                        ltadetalleSupervisionActualizados = detalleSupervisionServiceNeg.VerificaActualizaDetalleSupervisionSubsanados(filterdeta, Constantes.getUsuarioDTO(request));
                        cantidad = ltadetalleSupervisionActualizados.size();
                        //ACTUALIZANDO EL EXPEDIENTE PARA QUE EL AGENTE PUEDA ACCEDER AL MODULO DE LEVANTAMIENTOS
                        LOG.info("ACTUALIZANDO EL EXPEDIENTE PARA QUE EL AGENTE PUEDA ACCEDER AL MODULO DE LEVANTAMIENTOS");                       
                        ExpedienteDTO expedienteDTO = new ExpedienteDTO();
                        expedienteDTO.setIdExpediente(supervision.getOrdenServicioDTO().getExpediente().getIdExpediente());
                        MaestroColumnaDTO idEstadoLevantamiento = maestroColumnaServiceNeg.findByDominioAplicacionCodigo(Constantes.DOMINIO_ESTADO_LEVANTAMIENTO, Constantes.APLICACION_INPS, Constantes.CODIGO_ESTADO_LEVANTAMIENTO_PENDIENTE).get(0);
                        expedienteDTO.setEstadoLevantamiento(idEstadoLevantamiento);
                        expedienteDTO = expedienteServiceNeg.actualizarExpedienteHabModuloLevantamiento(expedienteDTO, Constantes.getUsuarioDTO(request));
                        if (expedienteDTO != null) {
                            //GENERANDO LA CARTA DE VISITA
                            LOG.info("GENERANDO LA CARTA DE VISITA");
                            rutaImagen = Constantes.RUTA_IMG_CABECERA_GENERAR_RESULTADOS;
                            rutaReportePrincipal = Constantes.RUTA_PLANTILLA_GENERAR_RESULTADOS_CARTA_VISITA;
                            reporteDTO.setDescripcionDocumento(Constantes.DESCRIPCION_DOCUMENTO_CARTA_VISITA);
                            String hora = getHoraActual();
                            String fechact = getFechaActual();
                            reporteDTO.setNombreArchivo(Constantes.NOMBRE_ARCHIVO_CARTA_VISITA +"_"+fechact+"_"+hora+"" + Constantes.EXTENSION_DOCUMENTO_CARTA_VISITA);
                            reportStreamImage = context.getResourceAsStream(rutaImagen);
                            reportJasper = context.getResourceAsStream(rutaReportePrincipal);
                            reportParams = setearInformacionCartaVisitaSupervisionInicial(reportStreamImage, generarResultadoDTO);
                            LOG.info("BUSCANDO EL TIPO DE DOCUMENTO CARTA DE VISITA");
                            MaestroColumnaDTO tipoDocumento=maestroColumnaServiceNeg.findMaestroColumnaByDominioAplicDesc(Constantes.DOMINIO_TIP_DOC_SUP_SIGED, Constantes.APLICACION_INPS, Constantes.DESCRIPCION_TIP_DOC_SUP_SIGED_CARTA_VISITA).get(0);
                            UsuarioDTO usuarioDTO = Constantes.getUsuarioDTO(request);
                            registroDocumentoSupervision = registrarDocumentoBDGenerarResultadosDsr(reportJasper, reportParams, supervision, usuarioDTO, reporteDTO,tipoDocumento);
                            if(registroDocumentoSupervision != null){
                               detalleLevantamientoServiceNeg.guardarDetalleLevantamiento(expedienteDTO, session, request); 
                            }                            
                        }
                    }
                }
            }
            retorno.put("registroDocumentoSupervision", registroDocumentoSupervision);
            retorno.put("cantidad", cantidad);
            retorno.put("supervision", supervision);
            retorno.put("correos", correos);
        }catch(Exception e){
            LOG.error("Error en generarResultadosSupTerminarSupervision",e);
            throw new DocumentoAdjuntoException(e.getMessage(),e);
        }        
        return retorno; 
    }
    
    public static String getHoraActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("hh_mm_ss");
        return formateador.format(ahora);
    }
    public static String getFechaActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd_MM_yy");
        return formateador.format(ahora);
    }
    public String HabilitacionCierreParcial(SupervisionDTO supervision,UbigeoDTO ubigoDTO,ServletContext contexto) throws DocumentoAdjuntoException {
        //ENVIO DE NOTIFICACION POR HABILITACION DE CIERRE PARCIAL
        LOG.info("ENVIO DE NOTIFICACION POR HABILITACION DE CIERRE PARCIAL");        
        UnidadSupervisadaDTO unidad = verUnidadSupervisadaDTO(supervision);
        List<RegistroHidrocarburoDTO> ltaregistroRH;
        String correoCierreParcial = "";
        try {
            ltaregistroRH = registroHidrocarburoServiceNeg.getListRegistroHidrocarburo(new RegistroHidrocarburoFilter(unidad.getIdUnidadSupervisada()));
            RegistroHidrocarburoDTO registroRH = new RegistroHidrocarburoDTO();
            if (ltaregistroRH.size() == Constantes.LISTA_UNICO_VALIR) {
                registroRH = ltaregistroRH.get(Constantes.PRIMERO_EN_LISTA);
            }
            SupervisionDTO supervisionAnt = null;
            SupervisionFilter filterSupAnt = new SupervisionFilter();
            filterSupAnt.setIdSupervision(supervision.getSupervisionAnterior().getIdSupervision());
            List<SupervisionDTO> ltasupervisionSupAnt = supervisionServiceNeg.buscarSupervision(filterSupAnt);
            supervisionAnt = ltasupervisionSupAnt.get(Constantes.PRIMERO_EN_LISTA);
            correoCierreParcial = EnvioNotificacionesSupervisionDsrHabilitarRolComprasScopProducto(ubigoDTO, supervision.getOrdenServicioDTO(), registroRH, unidad, contexto, supervisionAnt);
            LOG.info("correocierre es |"+correoCierreParcial);
        } catch (Exception ex) {
            correoCierreParcial = "error";
            //throw new DocumentoAdjuntoException("OCURRIO UN ERROR AL ENVIAR LA NOTIFICACION");            
        }
        return correoCierreParcial;
    }
    
    /*OSINE_SFS-791 - RSIS 40 - Fin */
    
    /*OSINE_SFS-791 - RSIS 41 - Inicio */
    public List<DocumentoAdjuntoDTO> verDocumentosSupervision(GenerarResultadoDTO generarResultadoDTO){
        List<DocumentoAdjuntoDTO> listaprincipal = new ArrayList<DocumentoAdjuntoDTO>();
        
        //para acta de habilitacion de RH
        DocumentoAdjuntoFilter filtrohabRH = new DocumentoAdjuntoFilter();
        MaestroColumnaDTO idTipoDochabRH = maestroColumnaServiceNeg.findMaestroColumnaByDominioAplicDesc(Constantes.DOMINIO_TIP_DOC_SUP_SIGED, Constantes.APLICACION_INPS, Constantes.DESCRIPCION_TIP_DOC_SUP_SIGED_CONSTANCIA_HABILITACION_REGIS_HIDRO).get(0);
        filtrohabRH.setIdSupervision(generarResultadoDTO.getIdSupervision());
        filtrohabRH.setIdTipoDocumento(idTipoDochabRH.getIdMaestroColumna());
        List<DocumentoAdjuntoDTO> listadohabRH;
        try {
            listadohabRH = documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(filtrohabRH);
        } catch (DocumentoAdjuntoException ex) {
            listadohabRH = null;
        }
        //Para acta de levantamiento
        DocumentoAdjuntoFilter filtroactaleva = new DocumentoAdjuntoFilter();
        MaestroColumnaDTO idTipoDocactaleva = maestroColumnaServiceNeg.findMaestroColumnaByDominioAplicDesc(Constantes.DOMINIO_TIP_DOC_SUP_SIGED, Constantes.APLICACION_INPS, Constantes.DESCRIPCION_TIP_DOC_SUP_SIGED_ACTA_LEVANTAMIENTO_MEDIDA_SEGURIDAD).get(0);
        filtroactaleva.setIdSupervision(generarResultadoDTO.getIdSupervision());
        filtroactaleva.setIdTipoDocumento(idTipoDocactaleva.getIdMaestroColumna());
        List<DocumentoAdjuntoDTO> listadoactaleva;
        try {
            listadoactaleva = documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(filtroactaleva);
        } catch (DocumentoAdjuntoException ex) {
            listadoactaleva = null;
        }
        //para carta de visita
        DocumentoAdjuntoFilter filtrocarVisi = new DocumentoAdjuntoFilter();
        MaestroColumnaDTO idTipoDoccarVisi = maestroColumnaServiceNeg.findMaestroColumnaByDominioAplicDesc(Constantes.DOMINIO_TIP_DOC_SUP_SIGED, Constantes.APLICACION_INPS, Constantes.DESCRIPCION_TIP_DOC_SUP_SIGED_CARTA_VISITA).get(0);
        filtrocarVisi.setIdSupervision(generarResultadoDTO.getIdSupervision());
        filtrocarVisi.setIdTipoDocumento(idTipoDoccarVisi.getIdMaestroColumna());
        List<DocumentoAdjuntoDTO> listadocarVisi;
        try {
            listadocarVisi = documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(filtrocarVisi);
        } catch (DocumentoAdjuntoException ex) {
            listadocarVisi = null;
        }        
        if(listadohabRH != null && listadohabRH.size() > Constantes.LISTA_VACIA){
            for (DocumentoAdjuntoDTO documentoAdjuntoDTOrh : listadohabRH) {
                listaprincipal.add(documentoAdjuntoDTOrh);
            }
        }
        if(listadoactaleva != null && listadoactaleva.size() > Constantes.LISTA_VACIA){
            for (DocumentoAdjuntoDTO documentoAdjuntoDTOactaleva : listadoactaleva) {
                listaprincipal.add(documentoAdjuntoDTOactaleva);
            }
        }
        if(listadocarVisi != null && listadocarVisi.size() > Constantes.LISTA_VACIA){
            for (DocumentoAdjuntoDTO documentoAdjuntoDTOcarVisi : listadocarVisi) {
                listaprincipal.add(documentoAdjuntoDTOcarVisi);
            }
        }
        return listaprincipal;
    }
    /*OSINE_SFS-791 - RSIS 41 - Fin */
    
    /* ---------------------------------------------------------------------------------------------------*/
    /* OSINE_SFS-791 - RSIS 46-47 - Inicio */     
	public void procesarTareaNotiOblIncumpSubsana(ExpedienteDTO expediente, ExpedienteTareaDTO expedienteTarea, UbigeoDTO ubigeo, OrdenServicioDTO ordenServicio, UsuarioDTO usuario)  {
		LOG.info("procesarTareaNotiOblIncumpSubsana - inicio - OSINE_SFS-791 - RSIS 46");		
		try {			
			if((expedienteTarea!=null && expedienteTarea.getFlagCorreoOficio()!=null && expedienteTarea.getFlagCorreoOficio().equals(Constantes.ESTADO_INACTIVO)) || (expedienteTarea==null)){
				//Enviar una notificación mediante correo electrónico al Especialista Legal de la OR, con el fin que este inicie la elaboración del Oficio correspondiente.
				envioNotificacionesTareaProg(expediente, expedienteTarea, ubigeo, ordenServicio, new CorreoFilter(Constantes.CODIGO_FUNCIONALIDAD_TF015), usuario);
			}
		} catch (Exception e) {
            LOG.error("Error en procesarTareaNotiOblIncumpSubsana", e);
        }
		LOG.info("procesarTareaNotiOblIncumpSubsana - fin");   
	}
	
	public void procesarTareaCancelacionRH(ExpedienteDTO expediente, UnidadSupervisadaDTO unidadSupervisada, SupervisionDTO supervision, OrdenServicioDTO ordenServicio, ExpedienteTareaDTO expedienteTarea, UsuarioDTO usuario, UbigeoDTO ubigeo, ServletContext context) {
		LOG.info("procesarTareaCancelacionRH - inicio - OSINE_SFS-791 - RSIS 47");		
		try {		   		    	
			if((expedienteTarea!=null && expedienteTarea.getFlagEstadoReghMsfh()!=null && expedienteTarea.getFlagEstadoReghMsfh().equals(Constantes.ESTADO_INACTIVO)) || (expedienteTarea==null) || (expedienteTarea!=null && expedienteTarea.getFlagEstadoReghMsfh()==null)){
				//Cancelar RH MSFH
				//Actualizar ESTADO_PROCESO INPS
				expedienteTarea=cancelarRH(expediente, unidadSupervisada, expedienteTarea, usuario);
			}			
			if((expedienteTarea!=null && expedienteTarea.getFlagEstadoReghInps()!=null && expedienteTarea.getFlagEstadoReghInps().equals(Constantes.ESTADO_INACTIVO)) || (expedienteTarea==null) || (expedienteTarea!=null && expedienteTarea.getFlagEstadoReghInps()==null)){
				//Actualizar Fecha Cancelacion RH
				//El expediente INPS deberá registrar su indicador TRAMITE FINALIZADO con valor 1 (trámite concluido, no habrá más ordenes de levantamiento para el expediente).
				expedienteTarea=actualizarINPS(expediente, expedienteTarea, usuario);
			}			
			if((expedienteTarea!=null && expedienteTarea.getFlagEnviarConstanciaSiged()!=null && expedienteTarea.getFlagEnviarConstanciaSiged().equals(Constantes.ESTADO_INACTIVO)) || (expedienteTarea==null) || (expedienteTarea!=null && expedienteTarea.getFlagEnviarConstanciaSiged()==null)){
				//Generar la constancia de cancelación 
		    	DocumentoAdjuntoDTO docAdjunto=generarConstanciaRH(supervision, ubigeo, usuario, context);
		    	//La constancia de cancelación RH generada deberá ser enviada al expediente SIGED respectivo.
		    	MaestroColumnaDTO idTipoDocCancelarRH = maestroColumnaServiceNeg.findMaestroColumnaByDominioAplicDesc(Constantes.DOMINIO_TIP_DOC_SUP_SIGED, Constantes.APLICACION_INPS, Constantes.DESCRIPCION_TIP_DOC_SUP_SIGED_CONSTANCIA_SUSPENSION_CANCELACION_REGIS_HIDRO).get(0);
                expedienteTarea=enviarConstanciaSIGED(expediente, expedienteTarea, docAdjunto, usuario,idTipoDocCancelarRH.getCodigo());
			}	    	
			if((expedienteTarea!=null && expedienteTarea.getFlagEstadoSiged()!=null && expedienteTarea.getFlagEstadoSiged().equals(Constantes.ESTADO_INACTIVO)) || (expedienteTarea==null) || (expedienteTarea!=null && expedienteTarea.getFlagEstadoSiged()==null)){
				//El expediente SIGED deberá cambiar al estado ARCHIVAR 
				expedienteTarea=archivarExpedienteSIGED(expediente, expedienteTarea, ordenServicio, usuario);	   
			}	    	
	    	if((expedienteTarea!=null && expedienteTarea.getFlagCorreoEstadoRegh()!=null && expedienteTarea.getFlagCorreoEstadoRegh().equals(Constantes.ESTADO_INACTIVO)) || (expedienteTarea==null) || (expedienteTarea!=null && expedienteTarea.getFlagCorreoEstadoRegh()==null)){
	    		//Notificar la cancelación del registro de hidrocarburos al Supervisor Regional, Especialista RH y Especialista Legal de la OR
	    		expedienteTarea=envioNotificacionesTareaProg(expediente, expedienteTarea, ubigeo, ordenServicio, new CorreoFilter(Constantes.CODIGO_FUNCIONALIDAD_TF016), usuario);
	    	}   		    	
		} catch (Exception e) {
            LOG.error("Error en procesarTareaCancelacionRH", e);
        }
		LOG.info("procesarTareaCancelacionRH - fin");     
	}
	
	public ExpedienteTareaDTO actualizarINPS(ExpedienteDTO expediente, ExpedienteTareaDTO expedienteTarea, UsuarioDTO usuario) throws ExpedienteException {
		LOG.info("actualizarINPS - inicio");		
		PeriodoMedidaSeguridadDTO periodoMedida=null;
		try {
			//SubTarea >> Actualizar Fecha Cancelacion RH
			//SubTarea >> Expediente INPS deberá registrar su indicador TRAMITE FINALIZADO con valor 1 (trámite concluido, no habrá más ordenes de levantamiento para el expediente).
			//Actualizar Fecha Cancelacion
			PeriodoMedidaSeguridadFilter filtro= new PeriodoMedidaSeguridadFilter(); 
			filtro.setIdexpediente(expediente.getIdExpediente());
			List<PeriodoMedidaSeguridadDTO> periodoMedidaList=periodoMedidaSeguridadServiceNeg.getListPeriodoMedidaSeguridad(filtro);
			if(periodoMedidaList!=null && periodoMedidaList.size()!=0){
				periodoMedida=periodoMedidaList.get(0);
				periodoMedida.setFechaCancelacion(new Date());
				PeriodoMedidaSeguridadDTO periodoMedidaSeguridad=periodoMedidaSeguridadServiceNeg.actualizarPeriodoMedidaSeguridad(periodoMedida, usuario);
				if(periodoMedidaSeguridad!=null){					
					List<ExpedienteDTO>expedienteList=expedienteServiceNeg.findByFilter(new ExpedienteFilter(expediente.getIdExpediente()));
	            	if (!CollectionUtils.isEmpty(expedienteList)) {
	            		//Actualiza flagTramiteFinalizado >> Finalizado
	            		ExpedienteDTO expedienteDto=expedienteList.get(0);
	            		expedienteDto.setFlagTramiteFinalizado(Constantes.ESTADO_ACTIVO);
	            		ExpedienteDTO finalizado=expedienteServiceNeg.actualizar(expedienteDto, usuario);                		
	            		LOG.info("Expediente finalizado?--->"+finalizado.getFlagTramiteFinalizado());
	            		//Actualizar Tarea
						if(expedienteTarea!=null){
							expedienteTarea.setFlagEstadoReghInps(Constantes.ESTADO_ACTIVO);
							expedienteTarea=expedienteTareaServiceNeg.actualizarExpedienteTarea(expedienteTarea, usuario);
						} else {
							expedienteTarea = new ExpedienteTareaDTO();
							expedienteTarea.setIdExpediente(expediente.getIdExpediente());
							expedienteTarea.setFlagEstadoReghInps(Constantes.ESTADO_INACTIVO);
							expedienteTarea=expedienteTareaServiceNeg.registrarExpedienteTarea(expedienteTarea, usuario);
						}
	            	}
				}
			}
		} catch (Exception e) {
            LOG.error("Error en actualizarINPS", e);
			throw new ExpedienteException(e);
        }
        LOG.info("actualizarINPS - fin");
        return expedienteTarea;
	}
	
	public ExpedienteTareaDTO cancelarRH(ExpedienteDTO expediente, UnidadSupervisadaDTO unidadSupervisada, ExpedienteTareaDTO expedienteTarea, UsuarioDTO usuario) throws ExpedienteException, RegistroHidrocarburoException {
		LOG.info("cancelarRH - inicio");		
		try {		
			//SubTarea >> Cancelar RH
			RegistroHidrocarburoDTO registroHidrocarburo=registroHidrocarburoServiceNeg.actualizarEstadoRHExterno(unidadSupervisada, usuario, Constantes.CODIGO_ESTADO_CANCELAR_REGISTRO_HIDROCARBURO);			
			if(registroHidrocarburo!=null){				
				if(expedienteTarea!=null){
					expedienteTarea.setFlagEstadoReghMsfh(Constantes.ESTADO_ACTIVO);
					expedienteTarea=expedienteTareaServiceNeg.actualizarExpedienteTarea(expedienteTarea, usuario);
				} else {
					expedienteTarea = new ExpedienteTareaDTO();
					expedienteTarea.setIdExpediente(expediente.getIdExpediente());
					expedienteTarea.setFlagEstadoReghMsfh(Constantes.ESTADO_ACTIVO);
					expedienteTarea=expedienteTareaServiceNeg.registrarExpedienteTarea(expedienteTarea, usuario);
				}
			}
		} catch (Exception e) {
            LOG.error("Error en cancelarRH", e);
            throw new ExpedienteException("error ", e);
        }
	    LOG.info("cancelarRH - fin");
	    return expedienteTarea;
	}
	
	public ExpedienteTareaDTO envioNotificacionesTareaProg(ExpedienteDTO expediente, ExpedienteTareaDTO expedienteTarea, UbigeoDTO ubigeo, OrdenServicioDTO ordenServicio, CorreoFilter filtroCorreo, UsuarioDTO usuario) throws DestinatarioCorreoException, ExpedienteException {
        LOG.info("envioNotificacionesTareaProg - inicio");
        boolean error=true;
        DestinatarioCorreoFilter filtro = new DestinatarioCorreoFilter();
        try {
        	//SubTarea >> Enviar una notificación mediante correo electrónico al Especialista Legal de la OR, con el fin que este inicie la elaboración del Oficio correspondiente.
        	//SubTarea >> Notificar la cancelación del registro de hidrocarburos al Supervisor Regional, Especialista RH y Especialista Legal de la OR.
	        //Envio de correo para Tareas Programadas           
	        filtro.setIdDepartamento(ubigeo.getIdDepartamento());
	        filtro.setIdProvincia(ubigeo.getIdProvincia());
	        filtro.setIdDistrito(ubigeo.getIdDistrito());
	        filtro.setCodigoFuncionalidadCorreo(filtroCorreo.getCodigoFuncionalidad());
	        List<DestinatarioCorreoDTO> milistaDestinos = destinatarioCorreoServiceNeg.getDestinatarioCorreobyUbigeo(filtro);
	        if(!CollectionUtils.isEmpty(milistaDestinos)){
	            if(ordenServicio!=null && ordenServicio.getExpediente()!=null && ordenServicio.getExpediente().getUnidadSupervisada()!=null){
		            ExpedienteDTO expedienteDTO = new ExpedienteDTO();            
		            expedienteDTO.setIdExpediente(ordenServicio.getExpediente().getIdExpediente());
		            expedienteDTO.setNumeroExpediente(ordenServicio.getExpediente().getNumeroExpediente());
		            expedienteDTO.setOrdenServicio(ordenServicio);
		            expedienteDTO.setUnidadSupervisada(ordenServicio.getExpediente().getUnidadSupervisada());		            
		            error = correoServiceNeg.enviarCorreoNotificacionTareaProg(milistaDestinos, expedienteDTO, filtroCorreo);
		            if(error){
		            	if(expedienteTarea!=null){
							expedienteTarea.setFlagCorreoEstadoRegh(Constantes.ESTADO_ACTIVO);
							expedienteTarea=expedienteTareaServiceNeg.actualizarExpedienteTarea(expedienteTarea, usuario);
						} else {
							expedienteTarea = new ExpedienteTareaDTO();
							expedienteTarea.setIdExpediente(expediente.getIdExpediente());
							expedienteTarea.setFlagCorreoEstadoRegh(Constantes.ESTADO_ACTIVO);
							expedienteTarea=expedienteTareaServiceNeg.registrarExpedienteTarea(expedienteTarea, usuario);
						}
		            }
	        	}
	        }
        } catch (Exception e) {
            LOG.error("Error en envioNotificacionesTareaProg", e);
            throw new ExpedienteException("error ", e);
       }    
       return expedienteTarea;
    }	
	
	public DocumentoAdjuntoDTO generarConstanciaRH(SupervisionDTO supervision, UbigeoDTO ubigeo, UsuarioDTO usuario, ServletContext context) throws DocumentoAdjuntoException, ExpedienteException {
		LOG.info("generarConstanciaRH - inicio");		 
		DocumentoAdjuntoDTO docAdjunto=null;
		Date fecha = new Date(); 
		ReporteDTO reporte = new ReporteDTO();
		GenerarResultadoDTO generarResultado=new GenerarResultadoDTO();
		try{
			//SubTarea >> Generar la constancia de cancelación
			generarResultado.setIdSupervision(supervision.getIdSupervision());
			generarResultado.setFechaFin(Utiles.DateToString(fecha, Constantes.FORMATO_FECHA_CORTA));
			generarResultado.setHoraFin(Utiles.DateToString(fecha, Constantes.FORMATO_HORA_CORTA));	
		
			String rutaImagen = Constantes.RUTA_IMG_CABECERA_GENERAR_RESULTADOS;
			String rutaReportePrincipal = Constantes.RUTA_PLANTILLA_GENERAR_RESULTADOS_ConstanciaRH;
			reporte.setDescripcionDocumento(Constantes.DESCRIPCION_DOCUMENTO_CONSTANCIA_RH_CANCELACION);
			String hora = getHoraActual();
                        String fechact = getFechaActual();
                        reporte.setNombreArchivo(Constantes.NOMBRE_ARCHIVO_CONSTANCIA_RH_CANCELACION +"_"+fechact+"_"+hora+"" + Constantes.EXTENSION_DOCUMENTO_CONSTANCIA_RH);
	        InputStream reportStreamImage = context.getResourceAsStream(rutaImagen);
	        InputStream reportJasper = context.getResourceAsStream(rutaReportePrincipal);				
	        Map<String, Object> reportParams = setInformacionConstanciaRH(reportStreamImage, generarResultado);
	        if(reportParams!=null){	        	
	        	docAdjunto = setDocumentoAdjunto(reportJasper, reportParams, supervision, reporte);
	        }  else {
	        	throw new ExpedienteException("Error al generar constancia", null);
	        }
		} catch (Exception e) {
            LOG.error("Error en generarConstanciaRH", e);
            throw new ExpedienteException("error ", e);
        }
		return docAdjunto;
	}
	
	@Transactional(rollbackFor=ExpedienteException.class)
	public ExpedienteTareaDTO enviarConstanciaSIGED(ExpedienteDTO expediente, ExpedienteTareaDTO expedienteTarea, DocumentoAdjuntoDTO docAdjunto, UsuarioDTO usuario,String codigoDocEnvioSiged) throws  IOException, ExpedienteException{
		LOG.info("enviarConstanciaSIGED - inicio");
		try{
			//SubTarea >> La constancia de cancelación RH generada deberá ser enviada al expediente SIGED respectivo.
			List<File> files=new ArrayList<File>();
			if(docAdjunto == null){
				throw new ExpedienteException("No existe archivo f&iacute;sico para el documento", null);
			}
			//Lista de Constancia Cancelacion RH
			File someFile = new File(docAdjunto.getNombreArchivo());
			byte[] mapByte=docAdjunto.getArchivoAdjunto();
	        if (mapByte == null) {
	            throw new ExpedienteException("No existe archivo f&iacute;sico para el documento: " + docAdjunto.getNombreArchivo() + ".", null);
	        }
	        if (mapByte != null) {
	            FileOutputStream fos = new FileOutputStream(someFile);
	            fos.write(mapByte);
	            fos.flush();
	            fos.close();
	            FileUtils.openInputStream(someFile);
	        }
	        files.add(someFile);
	        
	        //Obtener Informacion para armarExpedienteInRoEnvioDocSiged
	        List<MaestroColumnaDTO> lstTipoDocSup=maestroColumnaServiceNeg.findMaestroColumnaByCodigo(Constantes.DOMINIO_TIP_DOC_SUP_SIGED, Constantes.APLICACION_INPS, codigoDocEnvioSiged);
	        String codTipoDoc=(!CollectionUtils.isEmpty(lstTipoDocSup)) ? lstTipoDocSup.get(0).getCodigo() : null;
	        
	        List<ExpedienteDTO> expedienteList=expedienteServiceNeg.findByFilter(new ExpedienteFilter(expediente.getIdExpediente()));
	        expediente=(!CollectionUtils.isEmpty(expedienteList)) ? expedienteList.get(0) : null;
	        int usuarioCreador=(expediente.getPersonal()!=null && expediente.getPersonal().getIdPersonalSiged()!=null) ? expediente.getPersonal().getIdPersonalSiged().intValue() : 0;
	        
	        //EmpresaSupDTO emprSupe=(expediente.getEmpresaSupervisada()!=null && expediente.getEmpresaSupervisada().getIdEmpresaSupervisada()!=null) ? empresaSupervisadaServiceNeg.obtenerEmpresaSupervisada(new EmpresaSupDTO(expediente.getEmpresaSupervisada().getIdEmpresaSupervisada())) : null;
	                  
//	        List<BusquedaDireccionxEmpSupervisada> direccionEmpresaSupervisadaList=(expediente.getEmpresaSupervisada()!=null && expediente.getEmpresaSupervisada().getIdEmpresaSupervisada()!=null) ? empresaSupervisadaServiceNeg.listarDireccionEmpresaSupervisada(expediente.getEmpresaSupervisada().getIdEmpresaSupervisada()) : null;
//	        BusquedaDireccionxEmpSupervisada direEmprSupe=(!CollectionUtils.isEmpty(direccionEmpresaSupervisadaList)) ? direccionEmpresaSupervisadaList.get(0) : null;
//	        if (direEmprSupe == null) {
//	            throw new ExpedienteException("No existe Direcci&oacute;n para la Empresa Supervisada.", null);
//	        }
                
                if(expediente.getUnidadSupervisada()!=null && StringUtil.isEmpty(expediente.getUnidadSupervisada().getRuc())){
                    throw new ExpedienteException("Umpresa Supervisada no tiene RUC.", null);
                }
                DireccionUnidadSupervisadaDTO direUnid=unidadSupervisadaServiceNeg.buscarDireccUnidSupInps(expediente.getUnidadSupervisada().getCodigoOsinergmin());
                if(direUnid==null){
                    throw new ExpedienteException("No existe Direcci&oacute;n para la Unidad Supervisada.", null);
                }
                
//	        if(expediente!=null && codTipoDoc!=null && emprSupe!=null && usuarioCreador!=0 && direEmprSupe!=null && usuario!=null){
	        if(expediente!=null && codTipoDoc!=null && usuarioCreador!=0 && direUnid!=null && usuario!=null){
//	        	ExpedienteInRO expedienteInRODocSup=ordenServicioServiceNeg.armarExpedienteInRoEnvioDocSiged(expediente, codTipoDoc, emprSupe, usuarioCreador, direEmprSupe, usuario);
	        	ExpedienteInRO expedienteInRODocSup=ordenServicioServiceNeg.armarExpedienteInRoEnvioDocSiged(expediente, codTipoDoc, usuarioCreador, direUnid, usuario);
	        	if(!CollectionUtils.isEmpty(files)){
	        		//Envia Constancia cancelacion SIGED
	                DocumentoOutRO documentoOutRODocSup=ExpedienteInvoker.addDocument(HOST + "/remote/expediente/agregarDocumento", expedienteInRODocSup, files, false);            
	                if (!(documentoOutRODocSup.getResultCode().equals(InvocationResult.SUCCESS.getCode()))) {                
	                    LOG.info("Error: " + documentoOutRODocSup.getResultCode() + "- Ocurrio un error: " + documentoOutRODocSup.getMessage());
	                    throw new ExpedienteException("Error en Servicio SIGED: " + documentoOutRODocSup.getMessage(), null);
	                } else {
	                	LOG.info("----->getCodigoDocumento: "+documentoOutRODocSup.getCodigoDocumento());
	                	if(expedienteTarea!=null){
							expedienteTarea.setFlagEnviarConstanciaSiged(Constantes.ESTADO_ACTIVO);
							expedienteTarea=expedienteTareaServiceNeg.actualizarExpedienteTarea(expedienteTarea, usuario);
						} else {
							expedienteTarea = new ExpedienteTareaDTO();
							expedienteTarea.setIdExpediente(expediente.getIdExpediente());
							expedienteTarea.setFlagEnviarConstanciaSiged(Constantes.ESTADO_ACTIVO);
							expedienteTarea=expedienteTareaServiceNeg.registrarExpedienteTarea(expedienteTarea, usuario);
						}	                    
	                }
	            } else {
		        	throw new ExpedienteException("Error en al enviar documento al expediente SIGED", null);
		        }
	        } else {
	        	throw new ExpedienteException("Error en al enviar documento al expediente SIGED", null);
	        }
		} catch (Exception e) {
            LOG.error("Error en enviarConstanciaSIGED", e);
            throw new ExpedienteException("error ", e);
        }
		return expedienteTarea;
	}

        public ExpedienteTareaDTO archivarExpedienteSIGED(ExpedienteDTO expediente, ExpedienteTareaDTO expedienteTarea, OrdenServicioDTO ordenServicio, UsuarioDTO usuario) throws ExpedienteException, SupervisionException {
		LOG.info("archivarExpedienteSIGED - inicio");
		try{
			//SubTarea >> El expediente SIGED deberá cambiar al estado ARCHIVAR 	    	
			SupervisionFilter filtro = new SupervisionFilter();
	        filtro.setIdOrdenServicio(ordenServicio.getIdOrdenServicio());
	        List<SupervisionDTO> listaSupervision = supervisionServiceNeg.buscarSupervision(filtro);
	        if (!CollectionUtils.isEmpty(listaSupervision)) {
	            SupervisionDTO supervisionRegistro = listaSupervision.get(0);
	            if(supervisionRegistro.getResultadoSupervisionDTO() != null && supervisionRegistro.getResultadoSupervisionDTO().getIdResultadosupervision() != null){                    
            		//Archiva el expediente          		
                    ExpedienteDTO expedienteArchivar=expedienteServiceNeg.archivarExpedienteSIGED(String.valueOf(expediente.getNumeroExpediente()),"Archivar.");
                    LOG.info("Nro expediente Archivado. -->"+expedienteArchivar.getNumeroExpediente());
                    if(expedienteArchivar!=null){
                    	if(expedienteTarea!=null){
							expedienteTarea.setFlagEstadoSiged(Constantes.ESTADO_ACTIVO);
							expedienteTarea=expedienteTareaServiceNeg.actualizarExpedienteTarea(expedienteTarea, usuario);
						} else {
							expedienteTarea = new ExpedienteTareaDTO();
							expedienteTarea.setIdExpediente(expediente.getIdExpediente());
							expedienteTarea.setFlagEstadoSiged(Constantes.ESTADO_ACTIVO);
							expedienteTarea=expedienteTareaServiceNeg.registrarExpedienteTarea(expedienteTarea, usuario);
						}                    	
                    }	            	                                       
	            } else {
	                throw new ExpedienteException("La Supervisi&oacute;n no cuenta con resultados generados",null);
	            }
	        } else {
	            throw new ExpedienteException("La Orden de Servicio no tiene Supervisi&oacute;n relacionada",null);
	        }
		} catch (Exception e) {
            LOG.error("Error en archivarExpedienteSIGED", e);
            throw new ExpedienteException("error ", e);
        }
		return expedienteTarea;
	}

	public Map<String, Object> setInformacionConstanciaRH(InputStream reportStreamImage, GenerarResultadoDTO generarResultado) throws ExpedienteException{
		LOG.info("setInformacionConstanciaRH - inicio");
		Map<String, Object> reportParams = null;
        SupervisionDTO supervisionDTO = new SupervisionDTO();
        SupervisionFilter filtro = new SupervisionFilter();
        UnidadSupervisadaFilter unidadfilter = new UnidadSupervisadaFilter();
        try {	        
	        filtro.setIdSupervision(generarResultado.getIdSupervision());
	        List<SupervisionDTO> supervisionDTOs = supervisionServiceNeg.buscarSupervisionReporte(filtro);
	        if(supervisionDTOs!=null && supervisionDTOs.size()!=0){
		        supervisionDTO = supervisionDTOs.get(Constantes.PRIMERO_EN_LISTA);	        
		        if(supervisionDTO.getOrdenServicioDTO()!=null && supervisionDTO.getOrdenServicioDTO().getExpediente()!=null && 
		        		supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada()!=null & supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getIdUnidadSupervisada()!=null){
			        unidadfilter.setIdUnidadSupervisada(supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getIdUnidadSupervisada());
			        List<UnidadSupervisadaDTO> ltaUnidad = unidadSupervisadaServiceNeg.getUnidadSupervisadaDTO(unidadfilter);
			        if (ltaUnidad!=null && ltaUnidad.size() == Constantes.LISTA_UNICO_VALIR) {
			            UnidadSupervisadaDTO unidadDTO = ltaUnidad.get(Constantes.PRIMERO_EN_LISTA);
			            reportParams = new HashMap<String, Object>();
			            reportParams.put("rutaImagen", reportStreamImage);
			            reportParams.put("nroExpediente", (supervisionDTO.getOrdenServicioDTO().getExpediente().getNumeroExpediente()!=null) ? supervisionDTO.getOrdenServicioDTO().getExpediente().getNumeroExpediente() : "");
			            reportParams.put("oficinaRegional", "");
			            reportParams.put("direccionOR", "");
			            reportParams.put("telefonosOR", "");
//			            reportParams.put("agente", (unidadDTO.getEmpresaSupervisada().getRazonSocial() != null) ? unidadDTO.getEmpresaSupervisada().getRazonSocial() : "");
			            reportParams.put("agente", (unidadDTO.getNombreUnidad() != null) ? unidadDTO.getNombreUnidad() : "");
			            reportParams.put("actividadAgente", (unidadDTO.getActividad().getNombre() != null) ? unidadDTO.getActividad().getNombre()  : "");                      
			            reportParams.put("nroRegistro", (unidadDTO.getNroRegistroHidrocarburo() != null) ? unidadDTO.getNroRegistroHidrocarburo() : "");;
			            reportParams.put("codigoOsinergmin",(unidadDTO.getCodigoOsinergmin()!= null) ? unidadDTO.getCodigoOsinergmin() : "");           
			            reportParams.put("fechaSuspension", Utiles.DateToString(new Date(), Constantes.FORMATO_FECHA_CORTA));                   
			            reportParams.put("horaSuspension", Utiles.DateToString(new Date(), Constantes.FORMATO_HORA_CORTA));           
			            reportParams.put("suspension", false);
			            reportParams.put("cancelacion", true);
			        }
		        }
	        } 
		} catch (Exception ex) {
            LOG.error("Error setInformacionConstanciaRH", ex);
            throw new ExpedienteException("error ", ex);
        }
        return reportParams;
     }
	
	public DocumentoAdjuntoDTO setDocumentoAdjunto(InputStream reportJasper, Map<String, Object> reportParams, SupervisionDTO supervision, ReporteDTO reporte) throws DocumentoAdjuntoException{
		LOG.info("setDocumentoAdjunto - inicio");
        byte[] reporteBytes = null;
        DocumentoAdjuntoDTO documentoAdjuntoDTO=null;
        try {	            
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportJasper, reportParams, new JREmptyDataSource());
            if (jasperPrint != null) {       
                reporteBytes = JasperExportManager.exportReportToPdf(jasperPrint);  
                documentoAdjuntoDTO = new DocumentoAdjuntoDTO();
                documentoAdjuntoDTO.setSupervision(supervision);
                documentoAdjuntoDTO.setArchivoAdjunto(reporteBytes);            
                documentoAdjuntoDTO.setNombreArchivo(reporte.getNombreArchivo());
                documentoAdjuntoDTO.setDescripcionDocumento(reporte.getDescripcionDocumento());
                LOG.info("setDocumentoAdjunto - Fin");
            }
        } catch (Exception ex) {
            LOG.error("Error setDocumentoAdjunto", ex);
            throw new DocumentoAdjuntoException("error ", ex);
        }
        return documentoAdjuntoDTO;
    }
	/* OSINE_SFS-791 - RSIS 46-47 - Fin */ 
    /* ---------------------------------------------------------------------------------------------------*/
	
	/* OSINE_SFS-791 - RSIS 41 - Inicio */ 
        public DocumentoAdjuntoDTO ReporteActaLevantamiento(SupervisionDTO supervision,GenerarResultadoDTO generarResultadoDTO,boolean cierreTotal,boolean cierreParcial,HttpSession session,HttpServletRequest request,List<DetalleSupervisionDTO> ltadeta) throws DocumentoAdjuntoException{
        String rutaImagen = "";
        String rutaReportePrincipal="";
        String rutaReportePrincipal2="";
        InputStream reportStreamImage = null;
        InputStream reportStreamImage2 = null;
        InputStream reportJasper = null;
        InputStream reportJasper2 = null;
        ReporteDTO reporteDTO = new ReporteDTO();
        Map<String, Object> reportParams = null;
         Map<String, Object> reportParams2 = null;
         DocumentoAdjuntoDTO registroDocumentoEjecucionMedida = null;
         boolean inscripcionRH = cierreTotal;
        boolean usoInstalacion = cierreParcial;
        try {
        ServletContext context = session.getServletContext();
        ServletContext context2 = session.getServletContext();
        LOG.info("INICIANDO LA CONSTRUCCION DEL DOCUMENTO ACTA DE SUPERVISION");
        //Acta de levantamiento parte 01
        rutaImagen = Constantes.RUTA_IMG_CABECERA_GENERAR_RESULTADOS;
        rutaReportePrincipal = Constantes.RUTA_PLANTILLA_GENERAR_RESULTADOS_ACTA_LEVANTAMIENTO_01;
        reporteDTO.setDescripcionDocumento(Constantes.DESCRIPCION_DOCUMENTO_ACTA_LEVANTAMIENTO);
        String hora = getHoraActual();
        String fechact = getFechaActual();
        reporteDTO.setNombreArchivo(Constantes.NOMBRE_ARCHIVO_ACTA_LEVANTAMIENTO +"_"+fechact+"_"+hora+"" + Constantes.EXTENSION_DOCUMENTO_ACTA_LEVANTAMIENTO);
        reportStreamImage = context.getResourceAsStream(rutaImagen);
        reportJasper = context.getResourceAsStream(rutaReportePrincipal);                   
        reportParams = setearInformacionActaLevantamiento1(reportStreamImage, generarResultadoDTO,cierreTotal,cierreParcial,ltadeta);
        //Acta de levantamiento parte 02
        rutaReportePrincipal2 = Constantes.RUTA_PLANTILLA_GENERAR_RESULTADOS_ACTA_LEVANTAMIENTO_02;    
        reportStreamImage2 = context2.getResourceAsStream(rutaImagen);
        reportJasper2 = context2.getResourceAsStream(rutaReportePrincipal2); 
        
        reportParams2 = setearInformacionActaLevantamiento2(reportStreamImage2, generarResultadoDTO,inscripcionRH,usoInstalacion);
                
        registroDocumentoEjecucionMedida = registrarDocumentoActaLevantamientoBDDsr(reportJasper,reportJasper2 ,reportParams,reportParams2, generarResultadoDTO.getIdSupervision(), request, reporteDTO,ltadeta,generarResultadoDTO,cierreParcial);
        LOG.info("SE REGISTRO EL DOCUMENTO DE ACTA DE LEVANTAMIENTO");
        }catch (Exception ex) {
            registroDocumentoEjecucionMedida = null;
            LOG.error("OCURRIO UN ERROR AL GENERAR EL DOCUMENTO DE ACTA DE LEVANTAMIENTO",ex);
            throw new DocumentoAdjuntoException(ex.getMessage(),ex);
        }
        return registroDocumentoEjecucionMedida;
    }    
        
        public Map<String, Object> setearInformacionActaLevantamiento1(InputStream reportStreamImage, GenerarResultadoDTO generarResultadoDTO,boolean cierreTotal,boolean cierreParcial,List<DetalleSupervisionDTO> ltadeta) {
        Map<String, Object> reportParams = new HashMap<String, Object>();
        SupervisionFilter filtro = new SupervisionFilter();
        filtro.setIdSupervision(generarResultadoDTO.getIdSupervision());
        List<SupervisionDTO> supervisionDTOs = supervisionServiceNeg.buscarSupervisionReporte(filtro);
        SupervisionDTO supervisionDTO = new SupervisionDTO();
        supervisionDTO = supervisionDTOs.get(Constantes.PRIMERO_EN_LISTA);
        LOG.info("fechaInicio " + supervisionDTO.getFechaInicio());
        LOG.info("numeroOrdenServicio " + (supervisionDTO.getOrdenServicioDTO().getNumeroOrdenServicio()));
        LOG.info("numeroExpediente " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getNumeroExpediente()));
        LOG.info("idUnidadSupervisada " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getIdUnidadSupervisada()));
        LOG.info("codigoOsinerming " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getCodigoOsinergmin()));
        UnidadSupervisadaFilter unidadfilter = new UnidadSupervisadaFilter();
        unidadfilter.setIdUnidadSupervisada(supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getIdUnidadSupervisada());
        UbigeoDTO filter = new UbigeoDTO();
        filter.setIdDepartamento(generarResultadoDTO.getIdDepartamento());
        filter.setIdProvincia(generarResultadoDTO.getIdProvincia());
        filter.setIdDistrito(generarResultadoDTO.getIdDistrito());
        UbigeoDTO general = ObtenerUbigeo(filter);        
        String DescripcionAgente = "";
        if(supervisionDTO.getOrdenServicioDTO().getSupervisoraEmpresa()!= null){
            if(supervisionDTO.getOrdenServicioDTO().getSupervisoraEmpresa().getIdSupervisoraEmpresa()!= null && supervisionDTO.getOrdenServicioDTO().getSupervisoraEmpresa().getRazonSocial()!= null){
                DescripcionAgente = supervisionDTO.getOrdenServicioDTO().getSupervisoraEmpresa().getRazonSocial();
            }
        }else{
            if(supervisionDTO.getOrdenServicioDTO().getLocador()!= null){
                if(supervisionDTO.getOrdenServicioDTO().getLocador().getIdLocador()!= null && supervisionDTO.getOrdenServicioDTO().getLocador().getNombreCompleto()!= null){
                    DescripcionAgente = supervisionDTO.getOrdenServicioDTO().getLocador().getNombreCompleto();
                }
            }
        }
        String fechalevantamientos = ArmarInformacionFechaLevantamientosReporte(ltadeta);
        List<UnidadSupervisadaDTO> ltaUnidad = unidadSupervisadaServiceNeg.getUnidadSupervisadaDTO(unidadfilter);
        if (ltaUnidad.size() == Constantes.LISTA_UNICO_VALIR) {
            UnidadSupervisadaDTO unidadDTO = ltaUnidad.get(Constantes.PRIMERO_EN_LISTA);
            reportParams.put("rutaImagen", reportStreamImage);
            reportParams.put("expediente", (supervisionDTO.getOrdenServicioDTO().getExpediente().getNumeroExpediente()));
            reportParams.put("oficinaRegional", "");
            reportParams.put("direccionOR", "");
            reportParams.put("telefonosOR", "");
//            reportParams.put("agente", (unidadDTO.getEmpresaSupervisada().getRazonSocial() != null) ? unidadDTO.getEmpresaSupervisada().getRazonSocial() : "");
            reportParams.put("agente", (unidadDTO.getNombreUnidad()!= null) ? unidadDTO.getNombreUnidad() : "");
            reportParams.put("actividadAgente",(unidadDTO.getActividad().getNombre() != null) ? unidadDTO.getActividad().getNombre()  : "");
            reportParams.put("registro", (unidadDTO.getNroRegistroHidrocarburo() != null) ? unidadDTO.getNroRegistroHidrocarburo() : "");
            reportParams.put("codigoOsinergmin", (unidadDTO.getCodigoOsinergmin()!= null) ? unidadDTO.getCodigoOsinergmin() : "");
            reportParams.put("fechaInicioSupervision", supervisionDTO.getFechaInicio());                 
            reportParams.put("horaInicioSupervision",supervisionDTO.getHoraInicio());   
            reportParams.put("empresaSupervisora", DescripcionAgente);        
            reportParams.put("cierreTotal", cierreTotal);
            reportParams.put("cierreParcial", cierreParcial);
            reportParams.put("parteCierreParcial", cierreParcial);
            reportParams.put("direccionAgente", generarResultadoDTO.getDireccionOperativa());
            reportParams.put("fechasLevantamientos",fechalevantamientos);            
        }
        return reportParams;
    }
        public Map<String, Object> setearInformacionActaLevantamiento2(InputStream reportStreamImage, GenerarResultadoDTO generarResultadoDTO,boolean inscripcionRH,boolean usoInstalacion) {
        Map<String, Object> reportParams = new HashMap<String, Object>();
        SupervisionFilter filtro = new SupervisionFilter();
        filtro.setIdSupervision(generarResultadoDTO.getIdSupervision());
        List<SupervisionDTO> supervisionDTOs = supervisionServiceNeg.buscarSupervisionReporte(filtro);
        SupervisionDTO supervisionDTO = new SupervisionDTO();
        supervisionDTO = supervisionDTOs.get(Constantes.PRIMERO_EN_LISTA);
        LOG.info("fechaInicio " + supervisionDTO.getFechaInicio());
        LOG.info("numeroOrdenServicio " + (supervisionDTO.getOrdenServicioDTO().getNumeroOrdenServicio()));
        LOG.info("numeroExpediente " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getNumeroExpediente()));
        LOG.info("idUnidadSupervisada " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getIdUnidadSupervisada()));
        LOG.info("codigoOsinerming " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getCodigoOsinergmin()));
        UnidadSupervisadaFilter unidadfilter = new UnidadSupervisadaFilter();
        unidadfilter.setIdUnidadSupervisada(supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getIdUnidadSupervisada());
        UbigeoDTO filter = new UbigeoDTO();
        filter.setIdDepartamento(generarResultadoDTO.getIdDepartamento());
        filter.setIdProvincia(generarResultadoDTO.getIdProvincia());
        filter.setIdDistrito(generarResultadoDTO.getIdDistrito());
        UbigeoDTO general = ObtenerUbigeo(filter);        
        
        List<UnidadSupervisadaDTO> ltaUnidad = unidadSupervisadaServiceNeg.getUnidadSupervisadaDTO(unidadfilter);
        if (ltaUnidad.size() == Constantes.LISTA_UNICO_VALIR) {
            UnidadSupervisadaDTO unidadDTO = ltaUnidad.get(Constantes.PRIMERO_EN_LISTA);
            reportParams.put("rutaImagen", reportStreamImage);
            reportParams.put("expediente", (supervisionDTO.getOrdenServicioDTO().getExpediente().getNumeroExpediente()));
            reportParams.put("oficinaRegional", "");
            reportParams.put("direccionOR", "");
            reportParams.put("telefonosOR", "");
            reportParams.put("inscripcionRH", inscripcionRH);
            reportParams.put("usoInstalacion", usoInstalacion);
            reportParams.put("registro", (unidadDTO.getNroRegistroHidrocarburo() != null) ? unidadDTO.getNroRegistroHidrocarburo() : "");
                     
        }
        return reportParams;
    }
    
        public DocumentoAdjuntoDTO registrarDocumentoActaLevantamientoBDDsr(InputStream reportJasperActa1,InputStream reportJasper2,Map<String, Object> reportParams,Map<String, Object> reportParams2, Long idSupervision, HttpServletRequest request,ReporteDTO reporteDTO,List<DetalleSupervisionDTO> ltaDetalle,GenerarResultadoDTO generarResultadoDTO,boolean cierreParcial) throws DocumentoAdjuntoException{
        byte[] reporteBytes = null;
        List<JasperPrint> jasperPrints = new ArrayList<JasperPrint>();
        DocumentoAdjuntoDTO registroDocumentoEjecucionMedida=null;
        try{
            
            List<ReporteCierreParcialDTO> ltaCierreParcial = new ArrayList<ReporteCierreParcialDTO>();
            if (cierreParcial == true) {
                ltaCierreParcial = ArmarDataListaActaLevantamiento1(ltaDetalle);
            }

            List<ReporteActaLevantamientoDTO> ltaactalevantamiento = new ArrayList<ReporteActaLevantamientoDTO>();
            ltaactalevantamiento = ArmarDataListaActaLevantamiento2(ltaDetalle);            
            
            JRDataSource dataSource = new JRBeanCollectionDataSource(ltaCierreParcial);         
            JRDataSource listaDS = new JRBeanCollectionDataSource(ltaactalevantamiento);
            JasperPrint jasperPrintact1 = JasperFillManager.fillReport(reportJasperActa1, reportParams,dataSource);
            jasperPrints.add(jasperPrintact1);
            JasperPrint jasperPrintcat2 = JasperFillManager.fillReport(reportJasper2, reportParams2, listaDS);
            jasperPrints.add(jasperPrintcat2);            

            if (jasperPrints != null) {      
                JRPdfExporter exporter = new JRPdfExporter();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrints);
                exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
                
                exporter.exportReport();
                reporteBytes = out.toByteArray();

                DocumentoAdjuntoDTO documentoAdjuntoDTO = new DocumentoAdjuntoDTO();
                SupervisionDTO supervisionDTO = new SupervisionDTO();
                supervisionDTO.setIdSupervision(idSupervision);
                documentoAdjuntoDTO.setSupervision(supervisionDTO);
                documentoAdjuntoDTO.setArchivoAdjunto(reporteBytes);            
                documentoAdjuntoDTO.setNombreArchivo(reporteDTO.getNombreArchivo());
                documentoAdjuntoDTO.setDescripcionDocumento(reporteDTO.getDescripcionDocumento());
                MaestroColumnaDTO tipoDocumento=maestroColumnaServiceNeg.findMaestroColumnaByDominioAplicDesc(Constantes.DOMINIO_TIP_DOC_SUP_SIGED, Constantes.APLICACION_INPS, Constantes.DESCRIPCION_TIP_DOC_SUP_SIGED_ACTA_LEVANTAMIENTO_MEDIDA_SEGURIDAD).get(0);
                documentoAdjuntoDTO.setIdTipoDocumento(tipoDocumento);
                registroDocumentoEjecucionMedida = supervisionServiceNeg.registrarDocumentoGenerarResultados(documentoAdjuntoDTO,  Constantes.getUsuarioDTO(request));
            }
        } catch (Exception ex) {
            LOG.error("Error registrarDocumentoBDDsr", ex);
            throw new DocumentoAdjuntoException(ex.getMessage(),ex);
        }
        return registroDocumentoEjecucionMedida;
    }
    
        public boolean verDetalleSupervisionCierreTotal(List<DetalleSupervisionDTO> ltadetalleSupervisionSupPadre) throws DetalleSupervisionException {
        LOG.info("Inicio - Metodo - verDetalleSupervisionCierreTotal");   
        LOG.info("BUSCANDO EL ID DE CIERRE TOTAL");        
        boolean validarcierreTotal = false;
        Long idMaesColCierreTotal;
        List<MaestroColumnaDTO> ltacierreTotalDTO = maestroColumnaServiceNeg.findByDominioAplicacionCodigo(Constantes.DOMINIO_MEDIDA_SEGURIDAD, Constantes.APLICACION_MYC, Constantes.CODIGO_CIERRE_TOTAL);
        if (!CollectionUtils.isEmpty(ltacierreTotalDTO)) {
            idMaesColCierreTotal = ltacierreTotalDTO.get(Constantes.PRIMERO_EN_LISTA).getIdMaestroColumna();
        } else {
            throw new DetalleSupervisionException("No se encontro MaestroColumna de Cierre Total", null);
        }
        LOG.info("el id cierre total es : |"+idMaesColCierreTotal+"|");
        for (DetalleSupervisionDTO ltadetalleSupervisionPadre : ltadetalleSupervisionSupPadre) {
            if (idMaesColCierreTotal.equals(ltadetalleSupervisionPadre.getIdMedidaSeguridad())) {
                LOG.info("SE ENCONTRO UN CIERRE TOTAL EL ID DETALLE ES : |"+ltadetalleSupervisionPadre.getIdDetalleSupervision()+"|");        
                validarcierreTotal = true;
                break;
            }
        }
        return validarcierreTotal;
    }
        
    public UnidadSupervisadaDTO verUnidadSupervisadaDTO(SupervisionDTO supervision) {
        UnidadSupervisadaDTO unidad = new UnidadSupervisadaDTO();
        unidad.setIdUnidadSupervisada(supervision.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getIdUnidadSupervisada());
        unidad.setCodigoOsinergmin(supervision.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getCodigoOsinergmin());
        unidad.setActividad(supervision.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getActividad());
        unidad.setNombreUnidad(supervision.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getNombreUnidad());
        return unidad;
    }
    /*OSINE_SFS-791 - RSIS 40 - Fin */
    public Map<String, Object> setearInformacionConstanciaHabilitacionRH(InputStream reportStreamImage, SupervisionDTO supervisionGeneraDTO) {
        Map<String, Object> reportParams = new HashMap<String, Object>();
        SupervisionFilter filtro = new SupervisionFilter();
        filtro.setIdSupervision(supervisionGeneraDTO.getIdSupervision());
        List<SupervisionDTO> supervisionDTOs = supervisionServiceNeg.buscarSupervisionReporte(filtro);
        SupervisionDTO supervisionDTO = new SupervisionDTO();
        supervisionDTO = supervisionDTOs.get(Constantes.PRIMERO_EN_LISTA);
        LOG.info("fechaInicio " + supervisionDTO.getFechaInicio());
        LOG.info("numeroOrdenServicio " + (supervisionDTO.getOrdenServicioDTO().getNumeroOrdenServicio()));
        LOG.info("numeroExpediente " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getNumeroExpediente()));
        LOG.info("idUnidadSupervisada " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getIdUnidadSupervisada()));
        LOG.info("codigoOsinerming " + (supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getCodigoOsinergmin()));
        UnidadSupervisadaFilter unidadfilter = new UnidadSupervisadaFilter();
        unidadfilter.setIdUnidadSupervisada(supervisionDTO.getOrdenServicioDTO().getExpediente().getUnidadSupervisada().getIdUnidadSupervisada());
        List<UnidadSupervisadaDTO> ltaUnidad = unidadSupervisadaServiceNeg.getUnidadSupervisadaDTO(unidadfilter);
        if (ltaUnidad.size() == Constantes.LISTA_UNICO_VALIR) {
            UnidadSupervisadaDTO unidadDTO = ltaUnidad.get(Constantes.PRIMERO_EN_LISTA);
            reportParams.put("rutaImagen", reportStreamImage);
            reportParams.put("nroExpediente", (supervisionDTO.getOrdenServicioDTO().getExpediente().getNumeroExpediente()));
            reportParams.put("oficinaRegional", "");
            reportParams.put("direccionOR", "");
            reportParams.put("telefonosOR", "");
//            reportParams.put("agente", (unidadDTO.getEmpresaSupervisada().getRazonSocial() != null) ? unidadDTO.getEmpresaSupervisada().getRazonSocial() : "");
            reportParams.put("agente", (unidadDTO.getNombreUnidad() != null) ? unidadDTO.getNombreUnidad() : "");
            reportParams.put("actividadAgente", (unidadDTO.getActividad().getNombre() != null) ? unidadDTO.getActividad().getNombre()  : "");                      
            reportParams.put("nroRegistro", (unidadDTO.getNroRegistroHidrocarburo() != null) ? unidadDTO.getNroRegistroHidrocarburo() : "");;
            reportParams.put("codigoOsinergmin",(unidadDTO.getCodigoOsinergmin()!= null) ? unidadDTO.getCodigoOsinergmin() : "");           
            Date fecha = new Date();
            reportParams.put("fechaHabilitacion", Utiles.DateToString(fecha, Constantes.FORMATO_FECHA_CORTA));                   
            reportParams.put("horaHabilitacion", Utiles.DateToString(fecha, Constantes.FORMATO_HORA_CORTA));          
            }
        return reportParams;
     }
    //Habilitacion para habilitar el RH
    public String EnvioNotificacionesSupervisionDsrHabilitarRH(UbigeoDTO ubigeo,SupervisionDTO supervision) throws ExpedienteException{
        LOG.info("EnvioNotificacionesSupervisionDsrHabilitarRH - inicio");
        UnidadSupervisadaDTO unidad = verUnidadSupervisadaDTO(supervision);
        List<RegistroHidrocarburoDTO> ltaregistroRH;
        String msjeCorreo = "";
        try {
            ltaregistroRH = registroHidrocarburoServiceNeg.getListRegistroHidrocarburo(new RegistroHidrocarburoFilter(unidad.getIdUnidadSupervisada()));
            RegistroHidrocarburoDTO registroRH = new RegistroHidrocarburoDTO();
            if (ltaregistroRH.size() == Constantes.LISTA_UNICO_VALIR) {
                registroRH = ltaregistroRH.get(Constantes.PRIMERO_EN_LISTA);
            }
            //Envio de correo para Suspension RH
            DestinatarioCorreoFilter filtro1 = new DestinatarioCorreoFilter();
            filtro1.setCodigoFuncionalidadCorreo(Constantes.CODIGO_FUNCIONALIDAD_TF012);
            filtro1.setIdDepartamento(ubigeo.getIdDepartamento());
            filtro1.setIdProvincia(ubigeo.getIdProvincia());
            filtro1.setIdDistrito(ubigeo.getIdDistrito());
            List<DestinatarioCorreoDTO> milistaDestinos = destinatarioCorreoServiceNeg.getDestinatarioCorreobyUbigeo(filtro1);
            if(CollectionUtils.isEmpty(milistaDestinos)){
                throw new CorreoException("Error, No existen destinatarios de Correo.",null);
            }
            ExpedienteDTO expedienteDTO = new ExpedienteDTO();
            expedienteDTO.setIdExpediente(supervision.getOrdenServicioDTO().getExpediente().getIdExpediente());
            expedienteDTO.setNumeroExpediente(supervision.getOrdenServicioDTO().getExpediente().getNumeroExpediente());
            expedienteDTO.setOrdenServicio(supervision.getOrdenServicioDTO());
            boolean rptaCorreo = correoServiceNeg.enviarCorreoNotificacionHabilitacionRH(milistaDestinos, expedienteDTO, registroRH);
            if (!rptaCorreo) {
                msjeCorreo = "error";
            }
        } catch (Exception e) {
            LOG.error("Error en EnvioNotificacionesSupervisionDsrHabilitarRH", e);
            msjeCorreo = "error";
        }
        LOG.info("EnvioNotificacionesSupervisionDsrHabilitarRH - fin");
        return msjeCorreo;
    }
     //Habilitacion del rol de Compras SCOP
    public String EnvioNotificacionesSupervisionDsrHabilitarRolComprasScop(UbigeoDTO ubigeo,SupervisionDTO supervision) {
        LOG.info("EnvioNotificacionesSupervisionDsrHabilitarRolComprasScop - inicio");
        UnidadSupervisadaDTO unidad = verUnidadSupervisadaDTO(supervision);
        List<RegistroHidrocarburoDTO> ltaregistroRH;
        String msjeCorreo = "";
        try {
            ltaregistroRH = registroHidrocarburoServiceNeg.getListRegistroHidrocarburo(new RegistroHidrocarburoFilter(unidad.getIdUnidadSupervisada()));
            RegistroHidrocarburoDTO registroRH = new RegistroHidrocarburoDTO();
            if (ltaregistroRH.size() == Constantes.LISTA_UNICO_VALIR) {
                registroRH = ltaregistroRH.get(Constantes.PRIMERO_EN_LISTA);
            }
            //Envio de correo para Desactivar Rol Compras Scop
            DestinatarioCorreoFilter filtro1 = new DestinatarioCorreoFilter();
            filtro1.setCodigoFuncionalidadCorreo(Constantes.CODIGO_FUNCIONALIDAD_TF013);
            filtro1.setIdDepartamento(ubigeo.getIdDepartamento());
            filtro1.setIdProvincia(ubigeo.getIdProvincia());
            filtro1.setIdDistrito(ubigeo.getIdDistrito());
            List<DestinatarioCorreoDTO> milistaDestinos = destinatarioCorreoServiceNeg.getDestinatarioCorreobyUbigeo(filtro1);
            if(CollectionUtils.isEmpty(milistaDestinos)){
                throw new CorreoException("Error, No existen destinatarios de Correo.",null);
            }
            ExpedienteDTO expedienteDTO = new ExpedienteDTO();
            expedienteDTO.setIdExpediente(supervision.getOrdenServicioDTO().getExpediente().getIdExpediente());
            expedienteDTO.setNumeroExpediente(supervision.getOrdenServicioDTO().getExpediente().getNumeroExpediente());
            expedienteDTO.setOrdenServicio(supervision.getOrdenServicioDTO());
            boolean rptaCorreo = correoServiceNeg.enviarCorreoNotificacionHabilitacionRolComprasScop(milistaDestinos, expedienteDTO, registroRH, unidad);
            if (!rptaCorreo) {
                msjeCorreo = "error";
            }
        } catch (Exception e) {
            LOG.error("Error en EnvioNotificacionesSupervisionDsrHabilitarRolComprasScop", e);
            msjeCorreo = "error";
        }
        LOG.info("EnvioNotificacionesSupervisionDsrHabilitarRolComprasScop - fin");
        return msjeCorreo;
    }    
    //Habilitar el rol de compras del SCOP de Productos
    public String EnvioNotificacionesSupervisionDsrHabilitarRolComprasScopProducto(UbigeoDTO ubigeo,OrdenServicioDTO ordenServicioDTO,RegistroHidrocarburoDTO registroHidrocarburoDTO,  UnidadSupervisadaDTO unidadSupervisadaDTO,ServletContext contexto,SupervisionDTO supervisionAntDTO) throws DestinatarioCorreoException, ExpedienteException {
        LOG.info("EnvioNotificacionesSupervisionDsrHabilitarRolComprasScopProducto - inicio");
        String msjeCorreo = "";
        try {
            //ServletContext context = session.getServletContext();
            InputStream reportJasper = null;
            String rutaReportePrincipal = Constantes.RUTA_PLANTILLA_GENERAR_RESULTADOS_EXCEL_PRODUCTOS;
            reportJasper = contexto.getResourceAsStream(rutaReportePrincipal);
            byte[] reporteBytes = null;
            List<ReporteProductoDTO> ltaproducto = new ArrayList<ReporteProductoDTO>();
            ProductoDsrScopFilter filtr = new ProductoDsrScopFilter();
            filtr.setIdSupervision(supervisionAntDTO.getIdSupervision());
            int conta = 0;
            List<ProductoDsrScopDTO> ltapro = productoSuspenderServiceNeg.getProductoSuspender(filtr);
            for (ProductoDsrScopDTO productoDsrScopDTO : ltapro) {
               ReporteProductoDTO obj1 = new ReporteProductoDTO();
               obj1.setDescripcionProducto(""+productoDsrScopDTO.getProductodto().getNombreLargo());
               obj1.setFechaActa(supervisionAntDTO.getFechaInicio());
               obj1.setNroExpediente(ordenServicioDTO.getExpediente().getNumeroExpediente());
               obj1.setNroActa(ordenServicioDTO.getExpediente().getNumeroExpediente());
               obj1.setRazonSocial(unidadSupervisadaDTO.getNombreUnidad());
               obj1.setRegistroRH(registroHidrocarburoDTO.getNumeroRegistroHidrocarburo());
               ltaproducto.add(obj1);
               conta++;
            }       
            if(conta == 0){
               ReporteProductoDTO obj1 = new ReporteProductoDTO();
               obj1.setDescripcionProducto("");
               obj1.setFechaActa("");
               obj1.setNroExpediente("");
               obj1.setNroActa("");
               obj1.setRazonSocial("");
               obj1.setRegistroRH("");
               ltaproducto.add(obj1);
            }
            JRDataSource listaD3 = new JRBeanCollectionDataSource(ltaproducto);
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportJasper, null, listaD3);
            if (jasperPrint != null) {
                JRXlsExporter exporterXLS = new JRXlsExporter();                
                ByteArrayOutputStream out = new ByteArrayOutputStream();        
                exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
                exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, out);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
                exporterXLS.exportReport();
                //aca ya trae el excel en formato bytes
                reporteBytes = out.toByteArray();
            }
            //Envio de correo para Desactivar Rol Compras Scop
            DestinatarioCorreoFilter filtro1 = new DestinatarioCorreoFilter();
            filtro1.setCodigoFuncionalidadCorreo(Constantes.CODIGO_FUNCIONALIDAD_TF014);
            filtro1.setIdDepartamento(ubigeo.getIdDepartamento());
            filtro1.setIdProvincia(ubigeo.getIdProvincia());
            filtro1.setIdDistrito(ubigeo.getIdDistrito());
            List<DestinatarioCorreoDTO> milistaDestinos = destinatarioCorreoServiceNeg.getDestinatarioCorreobyUbigeo(filtro1);
            if(CollectionUtils.isEmpty(milistaDestinos)){
                throw new CorreoException("Error, No existen destinatarios de Correo",null);
            }
            ExpedienteDTO expedienteDTO = new ExpedienteDTO();
            expedienteDTO.setIdExpediente(ordenServicioDTO.getExpediente().getIdExpediente());
            expedienteDTO.setNumeroExpediente(ordenServicioDTO.getExpediente().getNumeroExpediente());
            expedienteDTO.setOrdenServicio(ordenServicioDTO);
            boolean rptaCorreo = correoServiceNeg.enviarCorreoNotificacionHabilitarRolComprasScopProducto(milistaDestinos, expedienteDTO, registroHidrocarburoDTO, unidadSupervisadaDTO,reporteBytes);
            if (!rptaCorreo) {
                msjeCorreo = "No envio correo HabilitarRolComprasScopProducto";
            }
        } catch (Exception e) {
            msjeCorreo = "No envio correo HabilitarRolComprasScopProducto.";
            LOG.error("Error en EnvioNotificacionesSupervisionDsrHabilitarRolComprasScopProducto", e);
            throw new ExpedienteException(e);
        }
        LOG.info("EnvioNotificacionesSupervisionDsrHabilitarRolComprasScopProducto - fin");
        return msjeCorreo;
    }

    public String ArmarInformacionLevantamientoAgenteReporte(DetalleSupervisionDTO midetalle) {
        String cadaux = "Observación Levantada:\n";
        DetalleLevantamientoFilter filtro = new DetalleLevantamientoFilter();
        filtro.setIdDetalleSupervision(midetalle.getIdDetalleSupervision());
        List<DetalleLevantamientoDTO> lta = new ArrayList<DetalleLevantamientoDTO>();
        try {
            lta = detalleLevantamientoServiceNeg.find(filtro);
            for (DetalleLevantamientoDTO detalleLevantamientoDTO : lta) {
                cadaux += "- " + detalleLevantamientoDTO.getDescripcion() + ".\n";
            }
        } catch (DetalleLevantamientoException ex) {
            LOG.error("Ocurrio Un error", ex);
        }
        return cadaux;
    }
    public String ArmarInformacionFechaLevantamientosReporte(List<DetalleSupervisionDTO> midetalle) {
        List<String> ltafechaslevantamientos = new ArrayList<String>();
        String cadaux = "";
        if (midetalle.size() > Constantes.LISTA_VACIA) {
            for (DetalleSupervisionDTO detalleSupervisionDTO : midetalle) {
                DetalleLevantamientoFilter filtro = new DetalleLevantamientoFilter();
                filtro.setIdDetalleSupervision(detalleSupervisionDTO.getIdDetalleSupervision());
                List<DetalleLevantamientoDTO> lta = new ArrayList<DetalleLevantamientoDTO>();
                try {
                    lta = detalleLevantamientoServiceNeg.find(filtro);
                    for (DetalleLevantamientoDTO detalleLevantamientoDTO : lta) {
                        ltafechaslevantamientos.add("" + detalleLevantamientoDTO.getFechaIngresoLevantamiento());
                    }
                } catch (DetalleLevantamientoException ex) {
                    LOG.error("Ocurrio Un error", ex);
                }
            }
            HashSet hs = new HashSet();
            hs.addAll(ltafechaslevantamientos);
            ltafechaslevantamientos.clear();
            ltafechaslevantamientos.addAll(hs);
            for (int i = 0; i < ltafechaslevantamientos.size(); i++) {
                cadaux += " " + ltafechaslevantamientos.get(i) + ",";
            }
        }
        return cadaux;
    }
    public List<ReporteActaLevantamientoDTO> ArmarDataListaActaLevantamiento2(List<DetalleSupervisionDTO> ltaDetalle) {
        List<ReporteActaLevantamientoDTO> ltaactalevantamiento = new ArrayList<ReporteActaLevantamientoDTO>();
        int cantidad = 0;
        for (DetalleSupervisionDTO midetalle : ltaDetalle) {
            ReporteActaLevantamientoDTO reportActLev = new ReporteActaLevantamientoDTO();
            reportActLev.setInfraccion(" " + ArmarDescripcionInfraccionReporte(midetalle));
            reportActLev.setBaseLegal(" " + ArmarBaseLegalReporte(midetalle));
            reportActLev.setInformacionLevantamientoAgente(" " + ArmarInformacionLevantamientoAgenteReporte(midetalle));
            ltaactalevantamiento.add(reportActLev);
            cantidad++;
        }
        if (cantidad == 0) {
            ReporteActaLevantamientoDTO reportActLev = new ReporteActaLevantamientoDTO();
            reportActLev.setInfraccion(" ");
            reportActLev.setBaseLegal(" ");
            reportActLev.setInformacionLevantamientoAgente(" ");
            ltaactalevantamiento.add(reportActLev);
        }
        return ltaactalevantamiento;
    }
    public List<ReporteCierreParcialDTO> ArmarDataListaActaLevantamiento1(List<DetalleSupervisionDTO> ltaDetalle) throws DocumentoAdjuntoException {
        List<ReporteCierreParcialDTO> ltaactalevantamiento2 = new ArrayList<ReporteCierreParcialDTO>();
        int cantidadDetalle = 0;
        for (DetalleSupervisionDTO midetalle : ltaDetalle) {
            
            String cadaux = "";
            int contador = 0;
            EscenarioIncumplidoFilter filtroobj = new EscenarioIncumplidoFilter();
            filtroobj.setIdDetalleSupervision(midetalle.getIdDetalleSupervision());
            try {
                List<EscenarioIncumplidoDTO> ltaEsIncumplido = escenarioIncumplidoServiceNeg.findEscenarioIncumplido(filtroobj);
                LOG.info("Hay nro elementos : |"+ltaEsIncumplido.size()+"|");
                for (EscenarioIncumplidoDTO escenarioIncumplidoDTO : ltaEsIncumplido) {
                    ReporteCierreParcialDTO reportActLev2 = new ReporteCierreParcialDTO();
                    reportActLev2.setInfraccion(" " + ArmarDescripcionInfraccionActaLevan2(midetalle));
                    String valida = "";
                    valida = "" + escenarioIncumplidoDTO.getEscenarioIncumplimientoDTO().getIdEsceIncuMaestro().getDescripcion();
                    LOG.info("cadaux es : |"+cadaux+"|");
                    LOG.info("cantidad de caracteres: |"+cadaux.trim().length()+"|");
                    if(cadaux.trim().length() == 0){
                        cadaux = "   -  ";
                    }else{
                        cadaux = "- " + escenarioIncumplidoDTO.getEscenarioIncumplimientoDTO().getIdEsceIncuMaestro().getDescripcion() + "";
                    }
                    reportActLev2.setEscenario(cadaux);
                    reportActLev2.setComentario(" " + ArmarComentarioReporteActaLevan2(midetalle,escenarioIncumplidoDTO.getIdEscenarioIncumplido()));
                    ltaactalevantamiento2.add(reportActLev2);
                    contador++;
                }
                if(contador == 0){
                    ReporteCierreParcialDTO reportActLev2 = new ReporteCierreParcialDTO();
                    reportActLev2.setInfraccion(" " + ArmarDescripcionInfraccionActaLevan2(midetalle));
                    reportActLev2.setEscenario(" ");
                    reportActLev2.setComentario(" " + ArmarComentarioReporte(midetalle));
                    ltaactalevantamiento2.add(reportActLev2);
                }
            } catch (Exception ex) {
                LOG.error("Error registrarDocumentoBDDsr", ex);
                throw new DocumentoAdjuntoException(ex.getMessage(), ex);
            }
            cantidadDetalle++;
        }
        if (cantidadDetalle == 0) {
            ReporteCierreParcialDTO reportActLev2 = new ReporteCierreParcialDTO();
            reportActLev2.setInfraccion(" ");
            reportActLev2.setEscenario(" ");
            reportActLev2.setComentario(" ");
            ltaactalevantamiento2.add(reportActLev2);
        }
        return ltaactalevantamiento2;
    }
    public String ArmarDescripcionInfraccionActaLevan2(DetalleSupervisionDTO midetalle) {
        String caddscripcion = "";
//        if (!midetalle.getObligacion().getInfraccion().getDescripcionInfraccion().equals(Constantes.CONSTANTE_VACIA)) {
//            caddscripcion += "" + midetalle.getObligacion().getInfraccion().getDescripcionInfraccion() + " ";
//        }
        if (!midetalle.getInfraccion().getDescripcionInfraccion().equals(Constantes.CONSTANTE_VACIA)) {
            caddscripcion += "" + midetalle.getInfraccion().getDescripcionInfraccion() + " ";
        }
        return caddscripcion;
    }
    public String ArmarDescripcionEscenariosActaLevan2(DetalleSupervisionDTO midetalle) {
        int conta = 0;
        String cadaux = "\n";
        try {
            EscenarioIncumplidoFilter filtroobj = new EscenarioIncumplidoFilter();
            filtroobj.setIdDetalleSupervision(midetalle.getIdDetalleSupervision());
            List<EscenarioIncumplidoDTO> ltaEsIncumplido = escenarioIncumplidoServiceNeg.findEscenarioIncumplido(filtroobj);
            for (EscenarioIncumplidoDTO escenarioIncumplidoDTO : ltaEsIncumplido) {
                cadaux += "- " + escenarioIncumplidoDTO.getEscenarioIncumplimientoDTO().getIdEsceIncuMaestro().getDescripcion() + " \n";
                conta++;
            }
        } catch (EscenarioIncumplidoException ex) {
            LOG.error("Ocurrio un error",ex);
        }
        if (conta == 0) {
            cadaux = "";
        }
        return cadaux;
    }
    
     public String ArmarComentarioReporteActaLevan2(DetalleSupervisionDTO midetalle,Long escenarioIncumplido) throws ComplementoDetSupervisionException {
        int conta = 0;
        String cadaux = "";
        try {
            ComentarioDetSupervisionFilter filtroobj = new ComentarioDetSupervisionFilter();
            filtroobj.setFlagOrderByDescripcion(Constantes.ESTADO_ACTIVO);
            filtroobj.setIdDetalleSupervision(midetalle.getIdDetalleSupervision());   
            filtroobj.setIdEscenarioIncumplido(escenarioIncumplido);
            List<ComentarioDetSupervisionDTO> ltaComntIncumpl =comentarioDetSupervisionServiceNeg.findComentarioDetSupervision(filtroobj);
            for (ComentarioDetSupervisionDTO comentariodetsupervisionDTO : ltaComntIncumpl) {
                
                /* OSINE_SFS-791 - mdiosesf - Inicio */
            	String descripcion=comentariodetsupervisionDTO.getComentarioIncumplimiento().getDescripcion();            	
                descripcion= "- "+descripcion+"\n";
                List<ComplementoDetSupervisionDTO> lstCompDetSup=complementoDetSupervisionServiceNeg.findComplementoDetSupervision(new ComplementoDetSupervisionFilter(comentariodetsupervisionDTO.getIdComentarioDetSupervision()));
            	if(!CollectionUtils.isEmpty(lstCompDetSup)){
            		for(ComplementoDetSupervisionDTO regCompDetSup : lstCompDetSup){
                        List<ComplementoDetSupValorDTO> lstCompDetSupValor = complementoDetSupervisionServiceNeg.findComplementoDetSupValor(new ComplementoDetSupValorFilter(regCompDetSup.getIdComplementoDetSupervision()));
                        if(!CollectionUtils.isEmpty(lstCompDetSupValor)){
                            String comDetSup="";
                            for(ComplementoDetSupValorDTO cdsv : lstCompDetSupValor){
                                comDetSup=comDetSup+cdsv.getValorDesc()+", ";
                            }
                            comDetSup=comDetSup.substring(0, comDetSup.length()-2);
                            if(regCompDetSup.getComentarioComplemento()!=null 
                                && regCompDetSup.getComentarioComplemento().getComplemento()!=null 
                                && regCompDetSup.getComentarioComplemento().getComplemento().getEtiquetaComentario()!=null){
                                descripcion=descripcion.replace(regCompDetSup.getComentarioComplemento().getComplemento().getEtiquetaComentario(),comDetSup);
                            }
                        }                                        
                    }
            	}            	
            	cadaux += " " +descripcion;
            	/* OSINE_SFS-791 - mdiosesf - Fin */
            	//cadaux += " " + comentariodetsupervisionDTO.getComentarioIncumplimiento().getDescripcion()+ ".";
                conta++;
            }
        } catch (Exception ex) {            
            LOG.error("Ocurrio un error",ex);
            throw new ComplementoDetSupervisionException(ex.getMessage(),ex);
        }
        if (conta == 0) {
            cadaux = "";
        }
        return cadaux;
    }

    @Override
    @Transactional(rollbackFor = ExpedienteException.class)
    public void procesarTareaHabilitacionCierre(UnidadSupervisadaDTO unidadSupervisada, SupervisionDTO supervision, UsuarioDTO usuario, UbigeoDTO ubigeo, ServletContext context) {
        ExpedienteTareaDTO expedienteTareaDTO = supervision.getOrdenServicioDTO().getExpediente().getExpedienteTareaDTO();
        boolean error = procesarHabilitacionCierre(unidadSupervisada, supervision, usuario, ubigeo, context, expedienteTareaDTO);
        if (error == false) {
            LOG.info("SE EJECUTO TODO CORRECTAMENTE");
            if (supervision.getOrdenServicioDTO().getExpediente() != null) {
                try {
                    PeriodoMedidaSeguridadFilter filter = new PeriodoMedidaSeguridadFilter();
                    filter.setIdexpediente(supervision.getOrdenServicioDTO().getExpediente().getIdExpediente());
                    List<PeriodoMedidaSeguridadDTO> ltaperiodo = periodoMedidaSeguridadServiceNeg.getListPeriodoMedidaSeguridad(filter);
                    if (ltaperiodo.size() > Constantes.LISTA_VACIA) {
                        PeriodoMedidaSeguridadDTO periodoDTO = new PeriodoMedidaSeguridadDTO();
                        periodoDTO = ltaperiodo.get(Constantes.PRIMERO_EN_LISTA);
                        periodoDTO.setFlagActualizarAuto(Constantes.FLAG_AUTOMATICO_EJECUCION_TAREA_NO);
                        periodoDTO = periodoMedidaSeguridadServiceNeg.actualizarPeriodoMedidaSeguridad(periodoDTO, usuario);
                        if (periodoDTO != null) {
                            LOG.info("SE ACTUALIZO CORRECTAMENTE EL PERIODO MEDIDA SEGURIDAD");
                        } else {
                            LOG.info("OCURRIO UN ERROR AL ACTUALIZAR EL PERIODO MEDIDA SEGURIDAD");
                        }
                    }
                } catch (Exception ex) {
                    LOG.info("OCURRIO ALGUN ERROR DURANTE LA ACTUALIZACION DEL PERIODO MEDIDA SEGURIDAD");
                }
            }
        } else {
            LOG.info("OCURRIO ALGUN ERROR DURANTE LA EJECUCION DEL PROCESO");
        }
    }

    public boolean ComprobarDetalleCierreTotal(Long idsupervisionAnt) throws DetalleSupervisionException{
        LOG.info("Inicio - Metodo - verDetalleSupervisionCierreTotal");       
        List<DetalleSupervisionDTO> ltadetalleSupervisiondetaAnterior = null;                   
        ltadetalleSupervisiondetaAnterior = verDetalleSupervisionAnteriorIncumplidos(idsupervisionAnt);
        
        LOG.info("BUSCANDO EL ID DE CIERRE TOTAL");
        boolean validarcierreTotal = false;
        Long idMaesColCierreTotal;
        List<MaestroColumnaDTO> ltacierreTotalDTO = maestroColumnaServiceNeg.findByDominioAplicacionCodigo(Constantes.DOMINIO_MEDIDA_SEGURIDAD, Constantes.APLICACION_MYC, Constantes.CODIGO_CIERRE_TOTAL);
        if (!CollectionUtils.isEmpty(ltacierreTotalDTO)) {
            idMaesColCierreTotal = ltacierreTotalDTO.get(Constantes.PRIMERO_EN_LISTA).getIdMaestroColumna();
        } else {
            throw new DetalleSupervisionException("No se encontro MaestroColumna de Cierre Total", null);
        }
        LOG.info("el id cierre total es : |"+idMaesColCierreTotal+"|");
        for (DetalleSupervisionDTO ltadetalleSupervisionPadre : ltadetalleSupervisiondetaAnterior) {
            if (idMaesColCierreTotal.equals(ltadetalleSupervisionPadre.getIdMedidaSeguridad())) {
                LOG.info("SE ENCONTRO UN CIERRE TOTAL EL ID DETALLE ES : |"+ltadetalleSupervisionPadre.getIdDetalleSupervision()+"|");        
                validarcierreTotal = true;
                break;
            }
        }
        return validarcierreTotal;
    }
    public List<DetalleSupervisionDTO> verDetalleSupervisionAnteriorIncumplidos(Long idsupervisionAnt) {
        LOG.info("LISTAR DETALLES SUPERVISION DE LA SUPERVISION ANTERIOR");
        ResultadoSupervisionFilter filtroResultadoAnte = new ResultadoSupervisionFilter();
        LOG.info("BUSCANDO EL RESULTADO SUPERVISION CON INCUMPLIMIENTO");
        filtroResultadoAnte.setCodigo(Constantes.CODIGO_RESULTADO_INCUMPLE);
        ResultadoSupervisionDTO resultadoAnteDTO;
        List<DetalleSupervisionDTO> ltadetalleSupervisiondetaAnterior = null;
        try {
            resultadoAnteDTO = resultadoSupervisionServiceNeg.getResultadoSupervision(filtroResultadoAnte);
            DetalleSupervisionFilter filterdetaAnterior = new DetalleSupervisionFilter();
            filterdetaAnterior.setIdSupervision(idsupervisionAnt);
            filterdetaAnterior.setIdResultadoSupervision(resultadoAnteDTO.getIdResultadosupervision());
            LOG.info("ENVIANDO ID SUPERVISION : |" + filterdetaAnterior.getIdSupervision() + "|");
            LOG.info("ENVIANDO ID IdResultadoSupervision : |" + filterdetaAnterior.getIdResultadoSupervision() + "|");
            ltadetalleSupervisiondetaAnterior = detalleSupervisionServiceNeg.listarDetalleSupervision(filterdetaAnterior);
        } catch (Exception ex) {
            ltadetalleSupervisiondetaAnterior = null;
        }
        return ltadetalleSupervisiondetaAnterior;
    }
    public boolean procesarHabilitacionCierre(UnidadSupervisadaDTO unidadSupervisada, SupervisionDTO supervision, UsuarioDTO usuario, UbigeoDTO ubigeo, ServletContext context,ExpedienteTareaDTO expedienteTareaDTO) {
        boolean validarcierreTotal;
        boolean error = false;
        try {
            validarcierreTotal = ComprobarDetalleCierreTotal(supervision.getSupervisionAnterior().getIdSupervision());
            if (validarcierreTotal == true) {
                LOG.info("SE PROCEDERA A REALIZAR LA HABILITACION DE CIERRE TOTAL");
                error = procesarHabilitacionRH(unidadSupervisada, supervision, usuario, ubigeo, context,expedienteTareaDTO);
            } else {
                LOG.info("SE PROCEDERA A REALIZAR LA HABILITACION DE CIERRE PARCIAL");
                error = procesarHabilitacionCierreParcial(unidadSupervisada, supervision, usuario, ubigeo, context,expedienteTareaDTO);
            }
        } catch (Exception ex) {
            error = true;
            LOG.error("Error retornando true en procesarHabilitacionCierre");
        }
        LOG.error("retornando error es : |"+error+"|");
        return error;
    }

    public boolean procesarHabilitacionRH(UnidadSupervisadaDTO unidadSupervisada, SupervisionDTO supervision, UsuarioDTO usuario, UbigeoDTO ubigeo, ServletContext context,ExpedienteTareaDTO expedienteTareaDTO) {
        boolean error = true;
        try {
            if ((expedienteTareaDTO != null && expedienteTareaDTO.getFlagEstadoReghMsfh() != null && expedienteTareaDTO.getFlagEstadoReghMsfh().equals(Constantes.ESTADO_INACTIVO)) || (expedienteTareaDTO == null) || (expedienteTareaDTO != null && expedienteTareaDTO.getFlagEstadoReghMsfh() == null)) {
                //Actualizar ESTADO_PROCESO INPS
                //Habilitacion RH
                expedienteTareaDTO = HabilitarRH(unidadSupervisada, usuario, expedienteTareaDTO, supervision);
            }
            //Generar la constancia de Habilitacion
            if ((expedienteTareaDTO != null && expedienteTareaDTO.getFlagRegistraDocsInps() != null && expedienteTareaDTO.getFlagRegistraDocsInps().equals(Constantes.ESTADO_INACTIVO)) || (expedienteTareaDTO == null) || (expedienteTareaDTO != null && expedienteTareaDTO.getFlagRegistraDocsInps() == null)) {
                //Generar la constancia de habilitacion
                DocumentoAdjuntoDTO docAdjunto = GenerarConstanciaHabilitacion(context, supervision, usuario);
                LOG.info("antes de if documento");
                if (docAdjunto != null) {
                    LOG.info("LA CONSTANCIA DE HABILITACION SE REGISTRO CORECTAMENTE");
                    if (expedienteTareaDTO != null) {
                        expedienteTareaDTO.setFlagRegistraDocsInps(Constantes.ESTADO_ACTIVO);
                        LOG.info("ACTUALIZANDO EL EXPEDIENTE TAREA");
                        expedienteTareaDTO = expedienteTareaServiceNeg.actualizarExpedienteTarea(expedienteTareaDTO, usuario);
                    } else {
                        LOG.info("REGISTRANDO EL EXPEDIENTE TAREA");
                        expedienteTareaDTO = new ExpedienteTareaDTO();
                        expedienteTareaDTO.setIdExpediente(supervision.getOrdenServicioDTO().getExpediente().getIdExpediente());
                        expedienteTareaDTO.setFlagRegistraDocsInps(Constantes.ESTADO_ACTIVO);
                        expedienteTareaDTO = expedienteTareaServiceNeg.registrarExpedienteTarea(expedienteTareaDTO, usuario);
                    }
                }else{
                    LOG.info("OCURRIO UN ERROR AL GENERAR LA CONSTANCIA DE HABILITACION");
                    error = true;
                    return error;
                }
            }
            if (supervision.getOrdenServicioDTO().getEstadoProceso() != null && supervision.getOrdenServicioDTO().getEstadoProceso().getIdentificadorEstado().equals(Constantes.IDENTIFICADOR_ESTADO_PROCESO_OS_SUPERVISADO)) {
                if ((expedienteTareaDTO != null && expedienteTareaDTO.getFlagRegistraDocsInps() != null && expedienteTareaDTO.getFlagRegistraDocsInps().equals(Constantes.ESTADO_ACTIVO))) {
                    if ((expedienteTareaDTO != null && expedienteTareaDTO.getFlagEnviarConstanciaSiged() != null && expedienteTareaDTO.getFlagEnviarConstanciaSiged().equals(Constantes.ESTADO_INACTIVO)) || (expedienteTareaDTO == null) || (expedienteTareaDTO != null && expedienteTareaDTO.getFlagEnviarConstanciaSiged() == null)) {
                        //BUSCAR LA CONSTANCIA DE HABILITACION DE RH QUE GENERO PREVIAMENTE
                        List<DocumentoAdjuntoDTO> listado = verConstanciaHabilitacionRH(supervision);
                        if (listado.size() > 0) {
                            DocumentoAdjuntoDTO miconstanciaDTO = listado.get(Constantes.PRIMERO_EN_LISTA);
                            if (supervision.getOrdenServicioDTO().getExpediente() != null) {
                                try {
                                    ExpedienteDTO expediente = new ExpedienteDTO();
                                    expediente = supervision.getOrdenServicioDTO().getExpediente();
                                    MaestroColumnaDTO idTipoDochabRH = maestroColumnaServiceNeg.findMaestroColumnaByDominioAplicDesc(Constantes.DOMINIO_TIP_DOC_SUP_SIGED, Constantes.APLICACION_INPS, Constantes.DESCRIPCION_TIP_DOC_SUP_SIGED_CONSTANCIA_HABILITACION_REGIS_HIDRO).get(0);
                                    expedienteTareaDTO = enviarConstanciaSIGED(expediente, expedienteTareaDTO, miconstanciaDTO, usuario,idTipoDochabRH.getCodigo());
                                } catch (Exception ex) {
                                    LOG.info("OCURRIO UN ERROR AL ENVIAR LA CONSTANCIA DE HABILITACION DE RH AL SIGED");
                                    error = true;
                                    return error;
                                }
                            }
                        }
                    }
                }
            }
            if ((expedienteTareaDTO != null && expedienteTareaDTO.getFlagCorreoEstadoRegh() != null && expedienteTareaDTO.getFlagCorreoEstadoRegh().equals(Constantes.ESTADO_INACTIVO)) || (expedienteTareaDTO == null) || (expedienteTareaDTO != null && expedienteTareaDTO.getFlagCorreoEstadoRegh() == null)) {
                //ENVIO DE NOTIFICACION POR HABILITACION DEL RH
                //Actualizar EL FLAG DE ENVIO DE NOTIFICACION POR HABILITACION DEL RH
                String rpta = EnvioNotificacionesSupervisionDsrHabilitarRH(ubigeo, supervision);
                LOG.info("el mensaje de notificacion Habilitacion es : |" + rpta + "|");
                if (!rpta.equals("error")) {
                    if (expedienteTareaDTO != null) {
                        expedienteTareaDTO.setFlagCorreoEstadoRegh(Constantes.ESTADO_ACTIVO);
                        expedienteTareaDTO = expedienteTareaServiceNeg.actualizarExpedienteTarea(expedienteTareaDTO, usuario);
                    } else {
                        expedienteTareaDTO = new ExpedienteTareaDTO();
                        expedienteTareaDTO.setIdExpediente(supervision.getOrdenServicioDTO().getExpediente().getIdExpediente());
                        expedienteTareaDTO.setFlagCorreoEstadoRegh(Constantes.ESTADO_ACTIVO);
                        expedienteTareaDTO = expedienteTareaServiceNeg.registrarExpedienteTarea(expedienteTareaDTO, usuario);
                    }
                } else { 
                    LOG.error("OCURRIO UN ERROR AL ENVIAR EL CORREO DE HABILITACION DE RH");
                    error = true;
                    return error;
                }
            }
            if ((expedienteTareaDTO != null && expedienteTareaDTO.getFlagCorreoScop() != null && expedienteTareaDTO.getFlagCorreoScop().equals(Constantes.ESTADO_INACTIVO)) || (expedienteTareaDTO == null) || (expedienteTareaDTO != null && expedienteTareaDTO.getFlagCorreoScop() == null)) {
                //ENVIO DE NOTIFICACION POR HABILITACION DEL SCOP
                //Actualizar EL FLAG DE ENVIO POR HABILITACION DEL SCOP
                String rpta = EnvioNotificacionesSupervisionDsrHabilitarRolComprasScop(ubigeo, supervision);
                LOG.info("el mensaje de notificacion Habilitacion SCOP es : |" + rpta + "|");
                if (!rpta.equals("error")) {
                    if (expedienteTareaDTO != null) {
                        expedienteTareaDTO.setFlagCorreoScop(Constantes.ESTADO_ACTIVO);
                        expedienteTareaDTO = expedienteTareaServiceNeg.actualizarExpedienteTarea(expedienteTareaDTO, usuario);
                    } else {
                        expedienteTareaDTO = new ExpedienteTareaDTO();
                        expedienteTareaDTO.setIdExpediente(supervision.getOrdenServicioDTO().getExpediente().getIdExpediente());
                        expedienteTareaDTO.setFlagCorreoScop(Constantes.ESTADO_ACTIVO);
                        expedienteTareaDTO = expedienteTareaServiceNeg.registrarExpedienteTarea(expedienteTareaDTO, usuario);
                    }
                } else { 
                    LOG.error("OCURRIO UN ERROR AL ENVIAR EL CORREO DE PRODUCTOS SCOP");
                    error = true;
                    return error;
                }
            }	    	
            error = false;
            LOG.error("CONCLUYO CORRECTAMENTE TODO EL PROCESO DE HABILITACION DE CIERRE TOTAL");
        } catch (Exception e) {
            error = true;
            LOG.error("Error en procesarHabilitacionRH", e);
        }
        LOG.info("procesarHabilitacionRH - fin");
        return error;
    }

    public boolean procesarHabilitacionCierreParcial(UnidadSupervisadaDTO unidadSupervisada, SupervisionDTO supervision, UsuarioDTO usuario, UbigeoDTO ubigeo, ServletContext context, ExpedienteTareaDTO expedienteTareaDTO) {
        boolean error = false;
        try {
            if ((expedienteTareaDTO != null && expedienteTareaDTO.getFlagCorreoScop() != null && expedienteTareaDTO.getFlagCorreoScop().equals(Constantes.ESTADO_INACTIVO)) || (expedienteTareaDTO == null) || (expedienteTareaDTO!=null && expedienteTareaDTO.getFlagCorreoScop()==null)){
                //ENVIO DE NOTIFICACION PARA CIERRE PARCIAL
		//Actualizar EL FLAG DE ENVIO DE NOTIFICACION PARA CIERRE PARCIAL
                String rpta = HabilitacionCierreParcial(supervision, ubigeo, context);
                LOG.info("el mensaje de envio es : |"+error+"|");
                if (!rpta.equals("error")) {
                    if (expedienteTareaDTO != null) {
                        expedienteTareaDTO.setFlagCorreoScop(Constantes.ESTADO_ACTIVO);
                        expedienteTareaServiceNeg.actualizarExpedienteTarea(expedienteTareaDTO, usuario);
                    } else {
                        ExpedienteTareaDTO expedienteTareaDTO2 = new ExpedienteTareaDTO();
                        expedienteTareaDTO2.setIdExpediente(supervision.getOrdenServicioDTO().getExpediente().getIdExpediente());
                        expedienteTareaDTO2.setFlagCorreoScop(Constantes.ESTADO_ACTIVO);
                        expedienteTareaServiceNeg.registrarExpedienteTarea(expedienteTareaDTO2, usuario);
                    }
                }else{
                    LOG.error("OCURRIO UN ERROR AL ENVIAR LA NOTIFICACION DE CIERRE PARCIAL");
                    error = true;
                }
            }
            //LOG.error("ENVIO LA NOTIFICACION DE HAB CIERRE PARCIAL CORRECTAMENTE");
        } catch (Exception e) {
            error = true;
            LOG.error("Error en procesarHabilitacionCierreParcial", e);
            LOG.error("retornando en procesarHabilitacionCierreParcial |" + error + "|");
        }
        LOG.info("procesarHabilitacionCierreParcial - fin");
        return error;
    }
    public ExpedienteTareaDTO HabilitarRH(UnidadSupervisadaDTO unidadSupervisada, UsuarioDTO usuarioDTO,ExpedienteTareaDTO expedienteTareaDTO,SupervisionDTO supervision) throws ExpedienteException, RegistroHidrocarburoException {
        LOG.info("HabilitarRH - inicio");
        try {
            //Cancelar RH
            RegistroHidrocarburoDTO registroHidrocarburo = registroHidrocarburoServiceNeg.actualizarEstadoRHExterno(unidadSupervisada, usuarioDTO, Constantes.CODIGO_ESTADO_HABILITAR_REGISTRO_HIDROCARBURO);
            if (registroHidrocarburo != null) {
                if (expedienteTareaDTO != null) {
                    expedienteTareaDTO.setFlagEstadoReghMsfh(Constantes.ESTADO_ACTIVO);
                    expedienteTareaDTO.setFlagEstadoReghInps(Constantes.ESTADO_ACTIVO);
                    LOG.info("ACTUALIZANDO EL EXPEDIENTE TAREA");
                    expedienteTareaServiceNeg.actualizarExpedienteTarea(expedienteTareaDTO, usuarioDTO);
                } else {
                     LOG.info("REGISTRANDO EL EXPEDIENTE TAREA");
                    expedienteTareaDTO = new ExpedienteTareaDTO();
                    expedienteTareaDTO.setIdExpediente(supervision.getOrdenServicioDTO().getExpediente().getIdExpediente());
                    expedienteTareaDTO.setFlagEstadoReghMsfh(Constantes.ESTADO_ACTIVO);
                    expedienteTareaDTO.setFlagEstadoReghInps(Constantes.ESTADO_ACTIVO);
                    expedienteTareaServiceNeg.registrarExpedienteTarea(expedienteTareaDTO, usuarioDTO);
                }
            }

        } catch (Exception e) {
            LOG.error("Error en HabilitarRH", e);
            throw new ExpedienteException(e);
        }
        LOG.info("HabilitarRH - fin");
        return expedienteTareaDTO;
    }
    
    public DocumentoAdjuntoDTO GenerarConstanciaHabilitacion(ServletContext context, SupervisionDTO supervision,UsuarioDTO usuarioDTO) throws ExpedienteException {
        String rutaImagen = "";
        String rutaReportePrincipal = "";
        InputStream reportStreamImage = null;
        InputStream reportJasper = null;
        ReporteDTO reporteDTO = new ReporteDTO();
        Map<String, Object> reportParams = null;
        DocumentoAdjuntoDTO registroDocumentoSupervision = null;
        try {
            //GENERANDO LA CONSTANCIA DE HABILITACION Y ENVIO DE NOTIFICACION POR HABILITACION DE CIERRE TOTAL
            LOG.info("GENERANDO LA CONSTANCIA DE HABILITACION Y ENVIO DE NOTIFICACION POR HABILITACION DE CIERRE TOTAL");
            LOG.info("INICIANDO LA CONSTRUCCION DEL DOCUMENTO DE HABILITACION DE REGISTRO HIDROCARBURO");
            rutaImagen = Constantes.RUTA_IMG_CABECERA_GENERAR_RESULTADOS;
            rutaReportePrincipal = Constantes.RUTA_PLANTILLA_GENERAR_RESULTADOS_Constancia_HabilitacionRH;
            reporteDTO.setDescripcionDocumento(Constantes.DESCRIPCION_DOCUMENTO_CONSTANCIA_HABILITARH);
            String hora = getHoraActual();
            String fechact = getFechaActual();
            reporteDTO.setNombreArchivo(Constantes.NOMBRE_ARCHIVO_CONSTANCIA_HABILITARH +"_"+fechact+"_"+hora+"" + Constantes.EXTENSION_DOCUMENTO_CONSTANCIA_HABILITARH);
            reportStreamImage = context.getResourceAsStream(rutaImagen);
            reportJasper = context.getResourceAsStream(rutaReportePrincipal);
            reportParams = setearInformacionConstanciaHabilitacionRH(reportStreamImage, supervision);
            if (reportParams != null) {
                LOG.info("BUSCANDO EL TIPO DE DOCUMENTO CONSTANCIA HABILITACION DE RH");
                MaestroColumnaDTO tipoDocumento = maestroColumnaServiceNeg.findMaestroColumnaByDominioAplicDesc(Constantes.DOMINIO_TIP_DOC_SUP_SIGED, Constantes.APLICACION_INPS, Constantes.DESCRIPCION_TIP_DOC_SUP_SIGED_CONSTANCIA_HABILITACION_REGIS_HIDRO).get(0);
                registroDocumentoSupervision = registrarDocumentoBDGenerarResultadosDsr(reportJasper, reportParams, supervision, usuarioDTO, reporteDTO, tipoDocumento);
            } else {
                throw new ExpedienteException("Error al generar constancia de habilitacion", null);
            }
            //INSERT DEL DOCUMENTO
            if (registroDocumentoSupervision != null) {
                LOG.info("SE REGISTRO EL DOCUMENTO DE SUSPENSION DE REGISTRO DE HIDROCARBURO");
            }
        } catch (Exception e) {
            registroDocumentoSupervision = null;
            LOG.error("Error en Generar la constancia de habilitacion de RH", e);
            throw new ExpedienteException(e);
        }
        return registroDocumentoSupervision;
    }
    /* OSINE_SFS-791 - RSIS 41 - Fin */
    /*OSINE_SFS-791 - RSIS 41 - Inicio */
    public List<DocumentoAdjuntoDTO> verConstanciaHabilitacionRH(SupervisionDTO supervision){
        //para acta de habilitacion de RH
        DocumentoAdjuntoFilter filtrohabRH = new DocumentoAdjuntoFilter();
        MaestroColumnaDTO idTipoDochabRH = maestroColumnaServiceNeg.findMaestroColumnaByDominioAplicDesc(Constantes.DOMINIO_TIP_DOC_SUP_SIGED, Constantes.APLICACION_INPS, Constantes.DESCRIPCION_TIP_DOC_SUP_SIGED_CONSTANCIA_HABILITACION_REGIS_HIDRO).get(0);
        filtrohabRH.setIdSupervision(supervision.getIdSupervision());
        filtrohabRH.setIdTipoDocumento(idTipoDochabRH.getIdMaestroColumna());
        List<DocumentoAdjuntoDTO> listadohabRH;
        try {
            listadohabRH = documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(filtrohabRH);
        } catch (DocumentoAdjuntoException ex) {
            listadohabRH = null;
        }
        return listadohabRH;
    }
    /*OSINE_SFS-791 - RSIS 41 - Fin */
    /*<!--  OSINE791 - RSIS19 - Inicio -->*/
    public String EnvioNotiInicialSupervisionDsrOtrosIncumplimientos(UbigeoDTO ubigeo, SupervisionDTO supervision) {
        String msj = "";
        try {
            ExpedienteDTO expediente = verExpedienteDTO(supervision);
            msj = EnvioNotificacionesSupervisionDsrOtrosIncumplimientos(ubigeo,expediente,supervision);
        } catch (Exception ex) {
         msj = "OCURRIO UN ERROR AL ENVIAR LA NOTIFICACION OtrosIncumplimientos";
         LOG.error("Error en EnvioSupervisionDsrOtrosIncumplimientos");
        }
        return msj;
    }
    public String EnvioNotificacionesSupervisionDsrOtrosIncumplimientos(UbigeoDTO ubigeo,ExpedienteDTO expediente,SupervisionDTO supervisionDTO) {
        LOG.info("EnvioNotificacionesSupervisionDsrOtrosIncumplimientos - inicio");
        String msjeCorreo = "";
        try {
            //Envio de correo para otros incumplimientos
            DestinatarioCorreoFilter filtro1 = new DestinatarioCorreoFilter();
            filtro1.setCodigoFuncionalidadCorreo(Constantes.CODIGO_FUNCIONALIDAD_TF003);
            filtro1.setIdDepartamento(ubigeo.getIdDepartamento());
            filtro1.setIdProvincia(ubigeo.getIdProvincia());
            filtro1.setIdDistrito(ubigeo.getIdDistrito());
            List<DestinatarioCorreoDTO> milistaDestinos = destinatarioCorreoServiceNeg.getDestinatarioCorreobyUbigeo(filtro1);
            boolean rptaCorreo = correoServiceNeg.enviarCorreoNotificacionOtrosIncumplimientos(milistaDestinos, expediente, supervisionDTO);
            if (!rptaCorreo) {
                msjeCorreo = " no se pudo enviar los correo .";
            }
        } catch (Exception e) {
            LOG.error("Error en EnvioNotificacionesSupervisionDsrOtrosIncumplimientos", e);
        }
        LOG.info("EnvioNotificacionesSupervisionDsrOtrosIncumplimientos - fin");
        return msjeCorreo;
    }
    public String EnvioNotiInicialSupervisionDsrNoEjecucionMedida(UbigeoDTO ubigeo, SupervisionDTO supervision) {
        String msj = "";
        try {
            ExpedienteDTO expediente = verExpedienteDTO(supervision);
            msj = EnvioNotificacionesSupervisionDsrNoEjecucionMedida(ubigeo,expediente);
        } catch (Exception ex) {
         msj = "OCURRIO UN ERROR AL ENVIAR LA NOTIFICACION DE NO EJECUCION MEDIDA";
         LOG.error("Error en EnvioNotiInicialSupervisionDsrNoEjecucionMedida");
        }
        return msj;
    }
    public String EnvioNotificacionesSupervisionDsrNoEjecucionMedida(UbigeoDTO ubigeo, ExpedienteDTO expediente) {
        LOG.info("EnvioNotificacionesSupervisionDsrNoEjecucionMedida - inicio");
        String msjeCorreo = "";
        try {
            //Envio de correo para NoEjecucionMedida
            DestinatarioCorreoFilter filtro1 = new DestinatarioCorreoFilter();
            filtro1.setCodigoFuncionalidadCorreo(Constantes.CODIGO_FUNCIONALIDAD_TF004);
            filtro1.setIdDepartamento(ubigeo.getIdDepartamento());
            filtro1.setIdProvincia(ubigeo.getIdProvincia());
            filtro1.setIdDistrito(ubigeo.getIdDistrito());
            List<DestinatarioCorreoDTO> milistaDestinos = destinatarioCorreoServiceNeg.getDestinatarioCorreobyUbigeo(filtro1);
            boolean rptaCorreo = correoServiceNeg.enviarCorreoNotificacionNoEjecucionMedida(milistaDestinos,expediente);
            if (!rptaCorreo) {
                msjeCorreo = " no se pudo enviar los correo .";
            }
        } catch (Exception e) {
            LOG.error("Error en EnvioNotificacionesSupervisionDsrNoEjecucionMedida", e);
        }
        LOG.info("EnvioNotificacionesSupervisionDsrNoEjecucionMedida - fin");
        return msjeCorreo;
    }
    public String EnvioNotiInicialSupervisionDsrObstaculizacionObligaciones(UbigeoDTO ubigeo, SupervisionDTO supervision) {
        String msj = "";
        ResultadoSupervisionFilter filtroResultado = new ResultadoSupervisionFilter();
        filtroResultado.setCodigo(Constantes.CODIGO_RESULTADO_OBSTACULIZADO);
        try {
            ResultadoSupervisionDTO resultadoDTO = resultadoSupervisionServiceNeg.getResultadoSupervision(filtroResultado);
            DetalleSupervisionFilter filtrodeta = new DetalleSupervisionFilter();
            filtrodeta.setIdSupervision(supervision.getIdSupervision());
            filtrodeta.setIdResultadoSupervision(resultadoDTO.getIdResultadosupervision());
            List<DetalleSupervisionDTO> ltaDetalleSupervisionObsta = detalleSupervisionServiceNeg.listarDetalleSupervision(filtrodeta);
            ExpedienteDTO expediente = verExpedienteDTO(supervision);
            msj = EnvioNotificacionesSupervisionDsrObstaculizacionObligaciones(ubigeo,expediente,ltaDetalleSupervisionObsta);
        } catch (Exception ex) {
         msj = "OCURRIO UN ERROR AL ENVIAR LA NOTIFICACION OBSTACULIZACION";
         LOG.error("Error en EnvioSupervisionDsrObstaculizacionObligaciones");
        }
        return msj;
    }
    public String EnvioNotificacionesSupervisionDsrObstaculizacionObligaciones(UbigeoDTO ubigeo, ExpedienteDTO expediente,List<DetalleSupervisionDTO> listadetalle) {
        LOG.info("EnvioNotificacionesSupervisionDsrObstaculizacionObligaciones - inicio");
        String msjeCorreo = "";
        try {
            //Envio de correo para otros ObstaculizacionObligaciones
            DestinatarioCorreoFilter filtro1 = new DestinatarioCorreoFilter();
            filtro1.setCodigoFuncionalidadCorreo(Constantes.CODIGO_FUNCIONALIDAD_TF005);
            filtro1.setIdDepartamento(ubigeo.getIdDepartamento());
            filtro1.setIdProvincia(ubigeo.getIdProvincia());
            filtro1.setIdDistrito(ubigeo.getIdDistrito());
            List<DestinatarioCorreoDTO> milistaDestinos = destinatarioCorreoServiceNeg.getDestinatarioCorreobyUbigeo(filtro1);
            boolean rptaCorreo = correoServiceNeg.enviarCorreoNotificacionObstaculizacionObligaciones(milistaDestinos, expediente,listadetalle);
            if (!rptaCorreo) {
                msjeCorreo = " no se pudo enviar los correo .";
            }
        } catch (Exception e) {
            LOG.error("Error en EnvioNotificacionesSupervisionDsrObstaculizacionObligaciones", e);
        }
        LOG.info("EnvioNotificacionesSupervisionDsrObstaculizacionObligaciones - fin");
        return msjeCorreo;
    }
    public ExpedienteDTO verExpedienteDTO (SupervisionDTO supervision){
        ExpedienteDTO expediente = new ExpedienteDTO();
        expediente.setNumeroExpediente(supervision.getOrdenServicioDTO().getExpediente().getNumeroExpediente());
        FlujoSigedDTO flujo = new FlujoSigedDTO();
        flujo.setNombreFlujoSiged(supervision.getOrdenServicioDTO().getExpediente().getFlujoSiged().getNombreFlujoSiged()); 
        expediente.setFlujoSiged(flujo);
        OrdenServicioDTO orden = new OrdenServicioDTO();
        orden.setNumeroOrdenServicio(supervision.getOrdenServicioDTO().getNumeroOrdenServicio());
        expediente.setOrdenServicio(orden);
        return expediente;
    }
    /*<!--  OSINE791 - RSIS19 - Fin -->*/

}
