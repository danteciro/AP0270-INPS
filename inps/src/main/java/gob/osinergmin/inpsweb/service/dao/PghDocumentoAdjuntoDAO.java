package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.inpsweb.service.exception.DocumentoAdjuntoException;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.DocumentoAdjuntoFilter;

import java.util.List;

public interface PghDocumentoAdjuntoDAO {

    public DocumentoAdjuntoDTO registrar(DocumentoAdjuntoDTO documentoAdjuntoDTO, UsuarioDTO usuarioDTO) throws DocumentoAdjuntoException;

    public void eliminar(DocumentoAdjuntoDTO documentoAdjuntoDTO) throws DocumentoAdjuntoException;

    public List<DocumentoAdjuntoDTO> find(DocumentoAdjuntoFilter filtro) throws DocumentoAdjuntoException;

    public List<DocumentoAdjuntoDTO> getDocumentoAdjunto(DocumentoAdjuntoFilter filtro) throws DocumentoAdjuntoException;

    public byte[] findBlobPghDocumento(Long idDocumentoAdjunto) throws DocumentoAdjuntoException;

    public int ActualizarEstadoPghDocumentoAdjunto(DocumentoAdjuntoDTO documentoAdjuntoDTO, UsuarioDTO usuarioDTO) throws DocumentoAdjuntoException;

    public String eliminarPghDocumentoAdjuntobyDetalleSupervision(DetalleSupervisionDTO detalleSupervisionDTO) throws DocumentoAdjuntoException;
    public int changeFlagEnviadoSigedPghDocumentoAdjunto(DocumentoAdjuntoDTO documentoAdjuntoDTO, UsuarioDTO usuarioDTO) throws DocumentoAdjuntoException;
}
