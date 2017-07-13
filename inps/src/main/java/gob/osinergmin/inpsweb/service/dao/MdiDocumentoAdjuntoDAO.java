package gob.osinergmin.inpsweb.service.dao;


import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;

public interface MdiDocumentoAdjuntoDAO {
    public DocumentoAdjuntoDTO get(Long idDocumentoAdjunto);
    public DocumentoAdjuntoDTO findDocumentoAdjunto(Long idDocumentoAdjunto);    
    
}
