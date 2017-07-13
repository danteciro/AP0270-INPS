/**
* Resumen       
* Objeto        : DocumentoAdjuntoServiceNegImpl.java
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo                        Fecha                   Nombre                            Descripcion
* ---------------------------------------------------------------------------------------------------
* OSINE_MANT_DSHL_003           27/06/2017              Claudio Chaucca Umana ::ADAPTER   Registra en el siged el usuario que crea el documento
* 
*/

package gob.osinergmin.inpsweb.service.business.impl;
import java.io.InputStream;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import gob.osinergmin.alfresco.rest.util.AlfrescoInvoker;
import gob.osinergmin.inpsweb.service.business.DireccionUnidadSupervisadaServiceNeg;
import gob.osinergmin.inpsweb.service.business.DocumentoAdjuntoServiceNeg;
import gob.osinergmin.inpsweb.service.business.EmpresaSupervisadaServiceNeg;
import gob.osinergmin.inpsweb.service.business.OrdenServicioServiceNeg;
import gob.osinergmin.inpsweb.service.business.UnidadSupervisadaServiceNeg;
import gob.osinergmin.inpsweb.service.dao.ExpedienteDAO;
import gob.osinergmin.inpsweb.service.dao.PghDocumentoAdjuntoDAO;
import gob.osinergmin.inpsweb.service.dao.MdiDocumentoAdjuntoDAO;
import gob.osinergmin.inpsweb.service.exception.DocumentoAdjuntoException;
import gob.osinergmin.inpsweb.service.exception.ExpedienteException;
import gob.osinergmin.inpsweb.service.exception.OrdenServicioException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.StringUtil;
import gob.osinergmin.inpsweb.util.Utiles;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.DireccionUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.DocumentoAdjuntoFilter;
import gob.osinergmin.mdicommon.domain.ui.ExpedienteFilter;
import gob.osinergmin.siged.remote.enums.InvocationResult;
import gob.osinergmin.siged.remote.rest.ro.in.ClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DireccionxClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DocumentoInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.ClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.DireccionxClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoOutRO;
import gob.osinergmin.siged.rest.util.ExpedienteInvoker;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;

@Service("DocumentoAdjuntoNeg")
public class DocumentoAdjuntoServiceNegImpl implements DocumentoAdjuntoServiceNeg {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentoAdjuntoServiceNegImpl.class);
    @Inject
    private MdiDocumentoAdjuntoDAO mdiDocumentoAdjuntoDAO;
    @Inject
    private PghDocumentoAdjuntoDAO pghDocumentoAdjuntoDAO;
    @Inject
    private EmpresaSupervisadaServiceNeg empresaSupervisadaServiceNeg;
    @Inject
    private ExpedienteDAO expedienteDAO;
    @Value("${alfresco.host}")
    private String HOST;
    @Value("${alfresco.download.dir}")
    private String ALFRESCO_DOWNLOAD;
    @Value("${alfresco.user}")
    private String ALFRESCO_USER;
    @Value("${alfresco.base}")
    private String ALFRESCO_BASE;
    @Value("${alfresco.space.dir.requisitos}")
    private String ALFRESCO_SPACE_REQUISITOS;
    @Value("${alfresco.space.dir.obligaciones}")
    private String ALFRESCO_SPACE_OBLIGACIONES;
    @Value("${alfresco.space.dir.generales}")
    private String ALFRESCO_SPACE_GENERALES;
    /* OSINE_SFS-791 - RSIS 29-30-31 - Inicio */     
    @Inject
    private OrdenServicioServiceNeg ordenServicioServiceNeg;
    /* OSINE_SFS-791 - RSIS 29-30-31 - Fin */ 
    @Inject
    private DireccionUnidadSupervisadaServiceNeg direccionUnidadSupervisadaServiceNeg;
    @Inject
    private UnidadSupervisadaServiceNeg unidadSupervisadaServiceNeg;

    @Override
    public InputStream descargarDatosAlfresco(DocumentoAdjuntoDTO archivo) {
        InputStream in = null;
        try {
            String alfrescoSpace = "";
            if (archivo.getAplicacionSpace().equals(Constantes.APPLICACION_SPACE_REQUISITOS)) {
                alfrescoSpace = ALFRESCO_SPACE_REQUISITOS;
            } else if (archivo.getAplicacionSpace().equals(Constantes.APPLICACION_SPACE_OBLIGACIONES)) {
                alfrescoSpace = ALFRESCO_SPACE_OBLIGACIONES;
            } else if (archivo.getAplicacionSpace().equals(Constantes.APPLICACION_SPACE_GENERALES)) {
                alfrescoSpace = ALFRESCO_SPACE_GENERALES;
            }

            String URL_ALFRESCO_DOWNLOAD = HOST + ALFRESCO_DOWNLOAD;
            in = AlfrescoInvoker.download(URL_ALFRESCO_DOWNLOAD, ALFRESCO_USER, ALFRESCO_BASE, null, null, null, alfrescoSpace, archivo.getRutaAlfresco());
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("error", ex.getMessage());
        }
        return in;
    }

    public DocumentoAdjuntoDTO buscarMdiDocumentoAdjuntoDescripcion(Long idDocumentoAdjunto) {
        DocumentoAdjuntoDTO documentoAdjuntoDTO = new DocumentoAdjuntoDTO();
        documentoAdjuntoDTO = mdiDocumentoAdjuntoDAO.get(idDocumentoAdjunto);
        return documentoAdjuntoDTO;
    }

    @Override
    @Transactional
    public DocumentoAdjuntoDTO registrarPghDocumentoAdjunto(DocumentoAdjuntoDTO documentoAdjuntoDTO, UsuarioDTO usuarioDTO)
            throws DocumentoAdjuntoException {
        LOG.info("DocumentoAdjuntoServiceNegImpl--registrarPghDocumentoAdjunto - inicio");
        DocumentoAdjuntoDTO retorno = null;
        try {
            retorno = pghDocumentoAdjuntoDAO.registrar(documentoAdjuntoDTO, usuarioDTO);
        } catch (Exception ex) {
            LOG.error("Error en registrarPghDocumentoAdjunto", ex);
            throw new DocumentoAdjuntoException(ex.getMessage(), ex);
        }
        LOG.info("DocumentoAdjuntoServiceNegImpl--registrarPghDocumentoAdjunto - fin");
        return retorno;
    }

    @Override
    @Transactional
    public int eliminarPghDocumentoAdjunto(DocumentoAdjuntoDTO documentoAdjuntoDTO) throws DocumentoAdjuntoException {
        LOG.info("DocumentoAdjuntoServiceNegImpl--eliminarPghDocumentoAdjunto - inicio");
        int resultado = 1;
        try {
            pghDocumentoAdjuntoDAO.eliminar(documentoAdjuntoDTO);
        } catch (Exception ex) {
            resultado = -1;
            LOG.error("Error en eliminarPghDocumentoAdjunto", ex);
            throw new DocumentoAdjuntoException(ex.getMessage(), ex);
        }
        LOG.info("DocumentoAdjuntoServiceNegImpl--eliminarPghDocumentoAdjunto - fin");
        return resultado;
    }

    @Override
    @Transactional
    public List<DocumentoAdjuntoDTO> listarPghDocumentoAdjunto(DocumentoAdjuntoFilter filtro) throws DocumentoAdjuntoException{
        LOG.info("DocumentoAdjuntoServiceNegImpl--listarPghDocumentoAdjunto - inicio");
        List<DocumentoAdjuntoDTO> lista = null;
        try {
            lista = pghDocumentoAdjuntoDAO.find(filtro);
        } catch (Exception ex) {
            LOG.error("Error en listarPghDocumentoAdjunto", ex);
            throw new DocumentoAdjuntoException(ex.getMessage(),ex);
        }
        LOG.info("DocumentoAdjuntoServiceNegImpl--listarPghDocumentoAdjunto - fin");
        return lista;
    }

    @Override
    @Transactional
    public List<DocumentoAdjuntoDTO> buscaPghDocumentoAdjunto(DocumentoAdjuntoFilter filtro) {
        LOG.info("SupervisionServiceNegImpl--listarDocumentoAdjunto - inicio");
        List<DocumentoAdjuntoDTO> lista = null;
        try {
            lista = pghDocumentoAdjuntoDAO.getDocumentoAdjunto(filtro);
        } catch (Exception ex) {
            LOG.error("Error en listarDocumentoAdjunto", ex);
        }
        LOG.info("SupervisionServiceNegImpl--listarDocumentoAdjunto - fin");
        return lista;
    }

    @Override
    @Transactional
    public DocumentoAdjuntoDTO enviarPghDocOrdenServicioSiged(DocumentoAdjuntoDTO doc, ExpedienteDTO expedienteDTO, Long idPersonalSiged, Long idDirccionUnidadSuprvisada) throws DocumentoAdjuntoException {
        LOG.info("enviarDocOrdenServicioSiged");
        DocumentoAdjuntoDTO docEnviarSiged = null;
        try {
//            if (expedienteDTO.getEmpresaSupervisada() == null && expedienteDTO.getEmpresaSupervisada().getIdEmpresaSupervisada() == null) {
//                throw new ExpedienteException("No existe Identificador de Empresa Supervisada.", null);
//            }
//
//            EmpresaSupDTO emprSupe = empresaSupervisadaServiceNeg.obtenerEmpresaSupervisada(new EmpresaSupDTO(expedienteDTO.getEmpresaSupervisada().getIdEmpresaSupervisada()));
//            List<BusquedaDireccionxEmpSupervisada> listarDireccionEmpresaSupervisada = empresaSupervisadaServiceNeg.listarDireccionEmpresaSupervisada(expedienteDTO.getEmpresaSupervisada().getIdEmpresaSupervisada());
//            BusquedaDireccionxEmpSupervisada direEmprSupe = listarDireccionEmpresaSupervisada != null && listarDireccionEmpresaSupervisada.size() > 0 ? listarDireccionEmpresaSupervisada.get(0) : null;
//
//            if (direEmprSupe == null) {
//                throw new ExpedienteException("No existe Direcci&oacute;n para la Empresa Supervisada.", null);
//            }

            DireccionUnidadSupervisadaDTO direUnid=null;
            if(idDirccionUnidadSuprvisada!=null){
                LOG.info("buscando direccion de unidad supervisada="+idDirccionUnidadSuprvisada);
                direUnid=direccionUnidadSupervisadaServiceNeg.findById(idDirccionUnidadSuprvisada);
            }
            
            if(direUnid==null){
                throw new ExpedienteException("No existe Direcci&oacute;n para la Unidad Supervisada.", null);
            }
            
            // htorress - RSIS 8 - Inicio
            //byte[] mapByte=pghDocumentoAdjuntoDAO.findBlobPghDocumento(doc.getIdDocumentoAdjunto());
            byte[] mapByte = doc.getArchivoAdjunto();
            // htorress - RSIS 8 - Fin

            if (mapByte == null) {
                throw new ExpedienteException("No existe Archivo fisico para el documento: " + doc.getNombreArchivo() + ".", null);
            }
            LOG.info("mapByte-->" + mapByte);
            ExpedienteInRO expedienteInRO = new ExpedienteInRO();

            expedienteInRO.setNroExpediente(expedienteDTO.getNumeroExpediente());
            DocumentoInRO documentoInRO = new DocumentoInRO();

            // htorress - RSIS 8 - Inicio
            //documentoInRO.setCodTipoDocumento(3);//3 oficio
            documentoInRO.setCodTipoDocumento(Integer.valueOf(doc.getIdTipoDocumento().getCodigo()));
            // htorress - RSIS 8 - Fin
            documentoInRO.setAsunto(expedienteDTO.getAsuntoSiged());
            documentoInRO.setAppNameInvokes("");
            documentoInRO.setPublico(Constantes.ESTADO_SIGED_SI);
            // htorress - RSIS 8 - Inicio
            //documentoInRO.setEnumerado(Constantes.ESTADO_SIGED_SI);
            documentoInRO.setEnumerado(Constantes.ESTADO_SIGED_NO);
            // htorress - RSIS 8 - Fin
            documentoInRO.setEstaEnFlujo(Constantes.ESTADO_SIGED_SI);
            // htorress - RSIS 8 - Inicio
            //documentoInRO.setFirmado(Constantes.ESTADO_SIGED_SI);
            documentoInRO.setFirmado(Constantes.ESTADO_SIGED_NO);
            // htorress - RSIS 8 - Fin
            documentoInRO.setDelExpediente(Constantes.ESTADO_SIGED_SI);
            documentoInRO.setNroFolios(1);
            // htorress - RSIS 9 - Inicio
            //documentoInRO.setCreaExpediente(Constantes.ESTADO_SIGED_SI);
            documentoInRO.setCreaExpediente(Constantes.ESTADO_SIGED_NO);
            // htorress - RSIS 9 - Fin
            ExpedienteDTO expedientePadre = expedienteDAO.findByFilter(new ExpedienteFilter(expedienteDTO.getIdExpediente())).get(0);
            //OSINE_MANT_DSHL_003:inicio
            Long idPersonalSigedReal=(idPersonalSiged!=null&&idPersonalSiged>0)?idPersonalSiged:expedientePadre.getPersonal().getIdPersonalSiged().intValue();
            //documentoInRO.setUsuarioCreador(expedientePadre.getPersonal().getIdPersonalSiged().intValue());//2582 avalos ruiz armando alexander
            //LOG.info("Id Personal Siged :-->" + expedientePadre.getPersonal().getIdPersonalSiged().intValue());
            documentoInRO.setUsuarioCreador(idPersonalSigedReal.intValue());
            LOG.info("Id Personal Siged :-->" + idPersonalSigedReal.intValue());
            //OSINE_MANT_DSHL_003:fin

            //CLIENTE
            ClienteInRO cliente1 = new ClienteInRO();
//            int codiTipoIdent = 3;
//            if (emprSupe.getRuc() != null) {
//                codiTipoIdent = 1;
//            } else if (emprSupe.getTipoDocumentoIdentidad() != null && emprSupe.getTipoDocumentoIdentidad().getCodigo().equals(Constantes.CODIGO_TIPO_DOCUMENTO_DNI)) {
//                codiTipoIdent = 2;
//            }
            if(StringUtil.isEmpty(expedienteDTO.getUnidadSupervisada().getRuc())){
                throw new ExpedienteException("Umpresa Supervisada no tiene RUC.", null);
            }
                        
//            cliente1.setCodigoTipoIdentificacion(codiTipoIdent);
            int codiTipoIdent=1;
            cliente1.setCodigoTipoIdentificacion(codiTipoIdent);
            
//            cliente1.setNroIdentificacion((codiTipoIdent == 1) ? emprSupe.getRuc() : emprSupe.getNroIdentificacion());
            cliente1.setNroIdentificacion(expedienteDTO.getUnidadSupervisada().getRuc());
            cliente1.setTipoCliente(3);
            
//            if (codiTipoIdent == 1) {
//                cliente1.setRazonSocial(emprSupe.getRazonSocial());//Obligatorio si codigoTipoIdentificacion es 1.
//            } else {
//                cliente1.setNombre(emprSupe.getNombre());//No es obligatorio si codigoTipoIdentificacion es 1.
//                cliente1.setApellidoPaterno(emprSupe.getApellidoPaterno());//No es obligatorio si codigoTipoIdentificacion es 1.
//                cliente1.setApellidoMaterno(emprSupe.getApellidoMaterno());//No es obligatorio si codigoTipoIdentificacion es 1.
//            }
            cliente1.setRazonSocial(expedienteDTO.getUnidadSupervisada().getNombreUnidad());

            DireccionxClienteInRO direccion1 = new DireccionxClienteInRO();
//            direccion1.setDireccion(direEmprSupe.getDireccionCompleta());
//            direccion1.setDireccionPrincipal(true);
            direccion1.setDireccion(direUnid.getDireccionCompleta());
            direccion1.setDireccionPrincipal(true);
            
//            direccion1.setTelefono(emprSupe.getTelefono());
            direccion1.setTelefono(direUnid.getTelefono1());
//            direccion1.setUbigeo(150104);//PENDIENTE
            direccion1.setUbigeo(Integer.parseInt(
                    Utiles.padLeft(direUnid.getDepartamento().getIdDepartamento().toString(), 2, '0')
                    + Utiles.padLeft(direUnid.getProvincia().getIdProvincia().toString(), 2, '0')
                    + Utiles.padLeft(direUnid.getDistrito().getIdDistrito().toString(), 2, '0')
            ));

            direccion1.setEstado(Constantes.ESTADO_SIGED_ACTIVO);//A: Activo, N: No Activo	

            List<DireccionxClienteInRO> listaDirCliente1 = new ArrayList<DireccionxClienteInRO>();
            listaDirCliente1.add(direccion1);
            DireccionxClienteListInRO direccionesCliente1 = new DireccionxClienteListInRO();
            direccionesCliente1.setDireccion(listaDirCliente1);
            cliente1.setDirecciones(direccionesCliente1);

            List<ClienteInRO> listaClientes = new ArrayList<ClienteInRO>();
            listaClientes.add(cliente1);
            ClienteListInRO clientes = new ClienteListInRO();
            clientes.setCliente(listaClientes);
            documentoInRO.setClientes(clientes);

            expedienteInRO.setDocumento(documentoInRO);

            //file
            File someFile = new File(doc.getNombreArchivo());
            InputStream is = null;
            if (mapByte != null) {
                FileOutputStream fos = new FileOutputStream(someFile);
                fos.write(mapByte);
                fos.flush();
                fos.close();
                is = FileUtils.openInputStream(someFile);
            }

            List<File> files = new ArrayList();
            files.add(someFile);

            DocumentoOutRO documentoOutRO = ExpedienteInvoker.addDocument(HOST + "/remote/expediente/agregarDocumento", expedienteInRO, files, false);
            // htorress - RSIS 8 - Inicio
            docEnviarSiged = new DocumentoAdjuntoDTO();
            docEnviarSiged.setEstado(String.valueOf(documentoOutRO.getResultCode()));
            //if (documentoOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode())){
            if (!(documentoOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode()))) {
                /*
                 docEnviarSiged.setNroDocumento(documentoOutRO.getCodigoDocumento().toString());
                 LOG.info("getCodigoDocumento:"+documentoOutRO.getCodigoDocumento());
                 int eliminarDoc=eliminarPghDocumentoAdjunto(new DocumentoAdjuntoDTO(doc.getIdDocumentoAdjunto()));
                 LOG.info("eliminarDoc-->"+eliminarDoc);
                 }else{
                 */
                docEnviarSiged.setComentario(documentoOutRO.getMessage());
                // htorress - RSIS 8 - Fin
                LOG.info("Error: " + documentoOutRO.getResultCode() + "-Ocurrio un error: " + documentoOutRO.getMessage());
                throw new DocumentoAdjuntoException("Error en Servicio SIGED: " + documentoOutRO.getMessage(), null);
            }
        } catch (Exception e) {
            LOG.error("error en enviarDocOrdenServicioSiged", e);
            throw new DocumentoAdjuntoException(e.getMessage(), null);
        }
        return docEnviarSiged;
    }

    @Override
    @Transactional
    public int ActualizarEstadoPghDocumentoAdjunto(DocumentoAdjuntoDTO documentoAdjuntoDTO, UsuarioDTO usuarioDTO) throws DocumentoAdjuntoException {
     LOG.info("DocumentoAdjuntoServiceNegImpl--ActualizarEstadoPghDocumentoAdjunto - inicio");
        int resultado = 1;
        try {
            pghDocumentoAdjuntoDAO.ActualizarEstadoPghDocumentoAdjunto(documentoAdjuntoDTO, usuarioDTO);
        } catch (Exception ex) {
            resultado = -1;
            LOG.error("Error en ActualizarEstadoPghDocumentoAdjunto", ex);
            throw new DocumentoAdjuntoException(ex.getMessage(), ex);
        }
        LOG.info("DocumentoAdjuntoServiceNegImpl--ActualizarEstadoPghDocumentoAdjunto - fin");
        return resultado;
    }

    @Override
    public String eliminarPghDocumentoAdjuntobyDetalleSupervision(DetalleSupervisionDTO detalleSupervisionDTO) throws DocumentoAdjuntoException {
     LOG.info("Inicio - eliminarPghDocumentoAdjuntobyDetalleSupervision");
        String retorno = "";
        try {
            retorno = pghDocumentoAdjuntoDAO.eliminarPghDocumentoAdjuntobyDetalleSupervision(detalleSupervisionDTO);
        } catch (Exception ex) {
            LOG.error("Error en eliminarPghDocumentoAdjuntobyDetalleSupervision", ex);
        }
        return retorno;
    }
    
    /* OSINE_SFS-791 - RSIS 29-30-31 - Inicio */ 
    @Override
    @Transactional
    public List<File> armarFilesEnvioPghDocAdjSiged(List<DocumentoAdjuntoDTO> lstDocs, UsuarioDTO usuarioDTO) throws DocumentoAdjuntoException {
    	LOG.info("Inicio - armarFilesEnvioPghDocAdjSiged");    
        List<File> files = null;
        try {
            if (lstDocs != null && lstDocs.size() != 0) {
            	files = new ArrayList<File>();
                for (DocumentoAdjuntoDTO doc : lstDocs) {
                    File someFile = new File(doc.getNombreArchivo());

                    List<DocumentoAdjuntoDTO> regDoc = pghDocumentoAdjuntoDAO.getDocumentoAdjunto(new DocumentoAdjuntoFilter(doc.getIdDocumentoAdjunto()));
                    if (!CollectionUtils.isEmpty(regDoc) && (StringUtil.isEmpty(regDoc.get(0).getFlagEnviadoSiged()) || !regDoc.get(0).getFlagEnviadoSiged().equals(Constantes.ESTADO_ACTIVO))) {
                        byte[] mapByte = pghDocumentoAdjuntoDAO.findBlobPghDocumento(doc.getIdDocumentoAdjunto());
                        if (mapByte == null) {
                            throw new OrdenServicioException("No existe Archivo fisico para el documento: " + doc.getNombreArchivo() + ".", null);
                        }
                        if (mapByte != null) {
                            FileOutputStream fos = new FileOutputStream(someFile);
                            fos.write(mapByte);
                            fos.flush();
                            fos.close();
                            FileUtils.openInputStream(someFile);
                        }
                        files.add(someFile);
                        //actualizar flagEnviadoSiged en pghDocumentoAdjunto
                        pghDocumentoAdjuntoDAO.changeFlagEnviadoSigedPghDocumentoAdjunto(new DocumentoAdjuntoDTO(doc.getIdDocumentoAdjunto(), Constantes.ESTADO_ACTIVO), usuarioDTO);
                    }
                }
            }            
        } catch (Exception e) {
            LOG.error("Error en armarFilesEnvioDocSiged", e);
            throw new DocumentoAdjuntoException(e.getMessage(), e);
        }
        return files;
    }
    
    @Override
    @Transactional
    public void agregarDocExpedienteSiged (List<File> files, ExpedienteDTO expediente, UsuarioDTO usuario, String codigoDocEnvioSiged) throws DocumentoAdjuntoException {
    	LOG.info("Inicio - agregarDocExpedienteSiged"); 
    	try {
    	  //Obtener Informacion para armarExpedienteInRoEnvioDocSiged	        	               
	        int usuarioCreador=(expediente.getPersonal()!=null && expediente.getPersonal().getIdPersonalSiged()!=null) ? expediente.getPersonal().getIdPersonalSiged().intValue() : 0;
	        
//	        EmpresaSupDTO emprSupe=(expediente.getEmpresaSupervisada()!=null && expediente.getEmpresaSupervisada().getIdEmpresaSupervisada()!=null) ? empresaSupervisadaServiceNeg.obtenerEmpresaSupervisada(new EmpresaSupDTO(expediente.getEmpresaSupervisada().getIdEmpresaSupervisada())) : null;
//	        List<BusquedaDireccionxEmpSupervisada> direccionEmpresaSupervisadaList=(expediente.getEmpresaSupervisada()!=null && expediente.getEmpresaSupervisada().getIdEmpresaSupervisada()!=null) ? empresaSupervisadaServiceNeg.listarDireccionEmpresaSupervisada(expediente.getEmpresaSupervisada().getIdEmpresaSupervisada()) : null;
//	        BusquedaDireccionxEmpSupervisada direEmprSupe=(!CollectionUtils.isEmpty(direccionEmpresaSupervisadaList)) ? direccionEmpresaSupervisadaList.get(0) : null;
//	        if (direEmprSupe == null) {
//	            throw new ExpedienteException("No existe Direcci&oacute;n para la Empresa Supervisada.", null);
//	        }
	        if(expediente.getUnidadSupervisada()!=null && StringUtil.isEmpty(expediente.getUnidadSupervisada().getRuc())){
                    throw new ExpedienteException("Unidad Supervisada no tiene RUC.", null);
                }
                DireccionUnidadSupervisadaDTO direUnid=unidadSupervisadaServiceNeg.buscarDireccUnidSupInps(expediente.getUnidadSupervisada().getCodigoOsinergmin());
                if(direUnid!=null && direUnid.getDepartamento()!=null && direUnid.getDepartamento().getIdDepartamento()!=null){                    
                }else{
                    throw new ExpedienteException("No existe Direcci&oacute;n para la Unidad Supervisada.", null);
                }                            
                
                if(expediente!=null && codigoDocEnvioSiged!=null && usuarioCreador!=0 && direUnid!=null && usuario!=null){
	        	ExpedienteInRO expedienteInRODocSup=ordenServicioServiceNeg.armarExpedienteInRoEnvioDocSiged(expediente, codigoDocEnvioSiged, usuarioCreador, direUnid, usuario);
	        	if(!CollectionUtils.isEmpty(files)){
	        		//Envia doc ha SIGED
	                DocumentoOutRO documentoOutRODocSup=ExpedienteInvoker.addDocument(HOST + "/remote/expediente/agregarDocumento", expedienteInRODocSup, files, false);            
	                if (!(documentoOutRODocSup.getResultCode().equals(InvocationResult.SUCCESS.getCode()))) {                
	                    LOG.info("Error: " + documentoOutRODocSup.getResultCode() + "- Ocurrio un error: " + documentoOutRODocSup.getMessage());
	                    throw new ExpedienteException("Error en Servicio SIGED: " + documentoOutRODocSup.getMessage(), null);
	                } else {
	                	LOG.info("----->getCodigoDocumento: "+documentoOutRODocSup.getCodigoDocumento());
	                	              
	                }
	            } else {
		        	throw new ExpedienteException("Error en al enviar documento al expediente SIGED", null);
		        }
	        } else {
	        	throw new ExpedienteException("Error en al enviar documento al expediente SIGED", null);
	        }
    	} catch (Exception e) {
            LOG.error("Error en agregarDocExpedienteSiged", e);
            throw new DocumentoAdjuntoException(e.getMessage(), e);
        }
    }
    /* OSINE_SFS-791 - RSIS 29-30-31 - Fin */ 
}