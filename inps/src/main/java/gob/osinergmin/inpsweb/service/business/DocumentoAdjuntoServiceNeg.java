package gob.osinergmin.inpsweb.service.business;
import gob.osinergmin.inpsweb.service.exception.DocumentoAdjuntoException;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.DocumentoAdjuntoFilter;
import java.io.File;
import java.io.InputStream;
import java.util.List;

public interface DocumentoAdjuntoServiceNeg {

    InputStream descargarDatosAlfresco(DocumentoAdjuntoDTO archivo);

    public DocumentoAdjuntoDTO buscarMdiDocumentoAdjuntoDescripcion(Long idDocumentoAdjunto);

    public DocumentoAdjuntoDTO registrarPghDocumentoAdjunto(DocumentoAdjuntoDTO documentoAdjuntoDTO, UsuarioDTO usuarioDTO) throws DocumentoAdjuntoException;

    public int eliminarPghDocumentoAdjunto(DocumentoAdjuntoDTO documentoAdjuntoDTO) throws DocumentoAdjuntoException;

    public List<DocumentoAdjuntoDTO> listarPghDocumentoAdjunto(DocumentoAdjuntoFilter filtro) throws DocumentoAdjuntoException;

    public List<DocumentoAdjuntoDTO> buscaPghDocumentoAdjunto(DocumentoAdjuntoFilter filtro);

    public DocumentoAdjuntoDTO enviarPghDocOrdenServicioSiged(DocumentoAdjuntoDTO doc, ExpedienteDTO expedienteDTO, Long idPersonalSiged, Long idDirccionUnidadSuprvisada) throws DocumentoAdjuntoException;
    
    public int ActualizarEstadoPghDocumentoAdjunto(DocumentoAdjuntoDTO documentoAdjuntoDTO, UsuarioDTO usuarioDTO) throws DocumentoAdjuntoException;

    public String eliminarPghDocumentoAdjuntobyDetalleSupervision(DetalleSupervisionDTO detalleSupervisionDTO) throws DocumentoAdjuntoException;
    
    /* OSINE_SFS-791 - RSIS 29-30-31 - Inicio */ 
    public List<File> armarFilesEnvioPghDocAdjSiged(List<DocumentoAdjuntoDTO> lstDocs, UsuarioDTO usuarioDTO) throws DocumentoAdjuntoException;
    public void agregarDocExpedienteSiged (List<File> files, ExpedienteDTO expediente, UsuarioDTO usuario, String codigoDocEnvioSiged) throws DocumentoAdjuntoException;
    /* OSINE_SFS-791 - RSIS 29-30-31 - Fin */ 
}